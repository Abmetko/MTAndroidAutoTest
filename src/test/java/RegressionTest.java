import app.frontend.screens.LoginScreen;
import app.frontend.screens.MainScreen;
import app.frontend.screens.StartScreen;
import static core.mt.utils.PropertyLoader.getProperty;
import core.mt.ProjectPackages;
import core.screen_driver.DriverFactory;
import core.mt.rest.APIClient;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import java.util.List;
import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;


public class RegressionTest extends BaseTest{

    private StartScreen start;
    private LoginScreen login;
    private MainScreen main;
    private static String instrument_set = getProperty("instrument.set");
    private static String group = getProperty("group.set");
    private static final String VOLUME_SET = getProperty("volume.set");
    private static final String VOLUME_DEFAULT = getProperty("volume.default");
    private String tp_set;
    private String sl_set;
    private String order_number;
    private double open_price = 0;
    private static final String OPERATION_TYPE = getProperty("operation.type");
    private boolean order_will_not_be_closed = false;
    private String asset_leverage;
    private static double default_volume_units = Double.parseDouble(VOLUME_DEFAULT)*100;
    private static final double DEFAULT_VOLUME_LOTS = Double.parseDouble(VOLUME_DEFAULT);

    public static void setPreconditionData(){
        if(ProjectPackages.INCEPTIAL.getValueAsList().contains(DriverFactory.package_name)){
            group = "CRYPTO_1";
            instrument_set = getProperty("instrument.set.inceptial");
            default_volume_units = Double.parseDouble(VOLUME_DEFAULT);
        }
    }

    @Test
    public void checkStartScreenIsOpen(){
        logger.debug("test starts - " + DriverFactory.project_name);
        start = new StartScreen(driver);
        assertTrue(start.waitIsScreenLoaded());
        logger.debug("Start screen is open");
    }

    @Test(dependsOnMethods = {"checkStartScreenIsOpen"})
    public void openLoginScreen(){
        assertTrue(start.clickLoginButton());
        login = new LoginScreen(driver);
        assertTrue(login.waitIsScreenLoaded());
        logger.debug("Login screen is open");
    }

    @Test(dependsOnMethods = {"openLoginScreen"})
    public void makeLogin(){
        assertTrue(login.makeLogin());
        main = new MainScreen(driver);
        assertTrue(main.waitIsScreenLoaded());
        logger.debug("Main screen is open");
    }

    @Test(priority = 1, dependsOnMethods = {"makeLogin"})
    public void setGroup() {
        setPreconditionData();
        main.selected_group.click();
        AndroidElement assetGroup = main.CRYPTOS;
        if(ProjectPackages.INCEPTIAL.getValueAsList().contains(DriverFactory.package_name)){
            assetGroup = main.CRYPTO_1;
        }
        main.scrollToElementByAndroidUIAutomator(group);
        assetGroup.click();
        assertEquals(group, main.selected_group.getText());
        logger.debug("Group: " + group + " is set");
    }

    @Test(dependsOnMethods = {"setGroup"})
    public void setInstrument() {
        while (!main.current_instrument.getText().equals(instrument_set)) {
            main.next_instrument.click();
        }
        assertEquals(main.current_instrument.getText(), instrument_set);
        logger.debug("Instrument: " + instrument_set + " is set");
    }

    @Test(dependsOnMethods = {"setInstrument"})
    public void checkInstrumentIsAvailable() {
        assertTrue(main.checkInstrumentIsAvailable());
    }

    @Test(dependsOnMethods = {"checkInstrumentIsAvailable"})
    public void openPositionOpeningDialog() {
        main.waitGetClickableElement(main.BUY).click();
        assertTrue(main.waitIsElementVisible(main.volume_lots));
        logger.debug("Position opening dialog is open");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void checkDefaultVolumeLots(){
        double actualVolume = Double.parseDouble(main.volume_lots.getText());
        assertEquals(actualVolume, DEFAULT_VOLUME_LOTS);
        logger.debug("Default volume lots is correct");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void checkDefaultVolumeUnits(){
        assertEquals(Double.parseDouble(main.volume_units.getText()), default_volume_units);
        logger.debug("Default volume units is correct");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"}, enabled = true)
    public void checkAssetLeverage(){
        asset_leverage = String.valueOf(APIClient.getAssetLeverage(DriverFactory.package_name));
        assertEquals(main.asset_leverage.getText(), "1:" + asset_leverage);
        logger.debug("Asset leverage is correct");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void checkSwitcherTpIsNotChecked() {
        assertEquals(main.Close_at_Profit.getAttribute("checked"), "false");
        logger.debug("TP is disabled");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void checkSwitcherSlIsNotChecked() {
        assertEquals(main.Close_at_Loss.getAttribute("checked"), "false");
        logger.debug("SL is disabled");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void openNewPosition() {
        main.waitClickClearEnterDataInField(main.volume_lots, VOLUME_SET);
        logger.debug("Volume input");
        main.waitGetClickableElement(main.Close_at_Profit).click();
        main.waitGetClickableElement(main.Close_at_Loss).click();
        String tp_input = String.valueOf((double) Math.round((Double.parseDouble(main.tp_create.getText()) + 0.1) * 100000d) / 100000d);
        main.waitClickClearEnterDataInField(main.tp_create, tp_input);
        logger.debug("TP input");
        tp_set = String.valueOf(Double.parseDouble(main.tp_create.getText()));
        String sl_input = String.valueOf((double) Math.round((Double.parseDouble(main.sl_create.getText()) - 0.1) * 100000d) / 100000d);
        main.waitClickClearEnterDataInField(main.sl_create, sl_input);
        logger.debug("SL input");
        sl_set = String.valueOf(Double.parseDouble(main.sl_create.getText()));
        main.waitGetClickableElement(main.BUY_button).click();
        open_price = Double.parseDouble(main.opened_rate.getText());
        assertTrue(open_price != 0);
        /* debug info */
        logger.debug("..............[set values]..............");
        logger.debug("TP: " + tp_set);
        logger.debug("SL: " + sl_set);
        logger.debug("..............[open order]..............");
        logger.debug("Order was opened successfully. OPEN PRICE(notification): " + open_price);
    }

    @Test(dependsOnMethods = {"openNewPosition"})
    public void openPortfolio() {
        main.Portfolio.click();
        assertTrue(main.waitGetVisibleElement(main.OPEN).isSelected());
        logger.debug("'Portfolio' is selected");
    }

    @Test(dependsOnMethods = {"openPortfolio"})
    public void checkOrderIsInListOfOpenOrders() {
        List<AndroidElement> orders = main.opened_order;
        boolean found = false;
        for (MobileElement i : orders) {
            if (Double.parseDouble(i.getText()) == (open_price)) {
                found = true;
                i.click();
                order_number = main.order_number.getText();
                break;
            }
        }
        assertTrue(found);
        logger.debug("Order is in list of open orders('OPEN'). Dialog is open");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkInstrumentInOpenOrder() {
        assertEquals(main.instrument_order.getText(), instrument_set);
        logger.debug("Instrument is correct");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkVolumeLotsInOpenOrder(){
        assertEquals(Double.parseDouble(main.volume_open.getText()), Double.parseDouble(VOLUME_SET));
        logger.debug("Volume lots is correct");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkVolumeUnitsInOpenOrder(){
        if(ProjectPackages.INCEPTIAL.getValueAsList().contains(DriverFactory.package_name)){
            assertEquals(Double.parseDouble(main.volume_units.getText()), Double.parseDouble(VOLUME_SET));
        }else{
            assertEquals(Double.parseDouble(main.volume_units.getText()), Double.parseDouble(VOLUME_SET)*100);
        }
        logger.debug("Volume units is correct");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"}, enabled = true)
    public void checkAssetLeverageInOpenOrder(){
        asset_leverage = String.valueOf(APIClient.getAssetLeverage(DriverFactory.package_name));
        assertEquals(main.asset_leverage.getText(), "1:" + asset_leverage);
        logger.debug("Asset leverage is correct");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkTpInOpenOrder() {
        assertEquals(String.valueOf(Double.parseDouble(main.tp_create.getText())), tp_set);
        logger.debug("TP is correct");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkSlInOpenOrder() {
        assertEquals(String.valueOf(Double.parseDouble(main.sl_create.getText())), sl_set);
        logger.debug("SL is correct");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkOperationTypeInOpenOrder() {
        assertEquals(main.operation_open.getText(), OPERATION_TYPE);
        logger.debug("Operation type is correct");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkOpenPriceInOpenOrder() {
        assertEquals(Double.parseDouble(main.order_price_open.getText().split("at ")[1]), open_price);
        logger.debug("Open price is correct");
    }

    @Test(priority = 2, dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void closeOrder() {
        String PROFIT = null;
        main.waitGetClickableElement(main.Close_Position).click();
        try {
            PROFIT = main.profit_notification.getText();
            /* debug info */
            logger.debug("..............[close order].............");
            logger.debug("Order was closed successfully. PROFIT(notification): " + PROFIT);
        } catch (NoSuchElementException e) {
            if (main.No_quotes.getText().equals("No quotes")) {
                logger.debug("Order cannot be closed. The reason is: 'No quotes'");
                order_will_not_be_closed = true;
                PROFIT = "";
                main.OK.click();
            } else {
                logger.debug("ERROR: Order closing message could not be found. Message 'No quotes' not found");
            }
        }
        assertNotNull(PROFIT);
    }

    @Test(dependsOnMethods = {"closeOrder"})
    public void openHistory() {
        main.waitGetClickableElement(main.HISTORY).click();
        assertTrue(main.waitIsElementVisible(main.history_item) & main.waitGetVisibleElement(main.HISTORY).isSelected());
        logger.debug("'HISTORY' is open");
    }

    @Test(dependsOnMethods = {"openHistory"})
    public void checkClosedOrderIsInHistory() {
        List<AndroidElement> orders = main.info;
        boolean found = false;
        if (orders.size() > 0) {
            for (int i = 0; i < orders.size() & i < 5; i++) {
                orders.get(i).click();
                String orderInfo = main.info_order_sub_string.getText();
                if (!order_will_not_be_closed) {
                    if (orderInfo.contains(order_number) &
                            Double.parseDouble(orderInfo.split("Rate: ")[1].split(" Order")[0]) == open_price) {
                        found = true;
                        break;
                    } else {
                        orders.get(i).click();
                    }
                } else {
                    if (orderInfo.contains("Opened") & orderInfo.contains("Rate") & orderInfo.contains("Order#")) {
                        found = true;
                        break;
                    }
                }
            }
        }else{
            if(order_will_not_be_closed & main.No_positions_yet.isEnabled()){
                found = true;
                logger.debug("List of closed orders is empty ('Nothing yet. Change the filter settings or choose an instrument to trade.')");
            }
        }
        assertTrue(found);
        logger.debug("test finished - " + DriverFactory.project_name);
    }
}