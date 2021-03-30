import app.frontend.screens.LoginScreen;
import app.frontend.screens.MainScreen;
import app.frontend.screens.StartScreen;
import core.utils.APIClient;
import core.utils.PropertyLoader;
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
    private final String INSTRUMENT_SET = PropertyLoader.getProperty("instrument.set");
    private final String GROUP = PropertyLoader.getProperty("group.set");
    private final String VOLUME_SET = PropertyLoader.getProperty("volume.set");
    private final String VOLUME_DEFAULT = PropertyLoader.getProperty("volume.default");
    private String TP_SET;
    private String SL_SET;
    private String ORDER_NUMBER;
    private double OPEN_PRICE = 0;
    private final String OPERATION_TYPE = PropertyLoader.getProperty("operation.type");
    private boolean ORDER_WILL_NOT_BE_CLOSED = false;
    private String ASSET_LEVERAGE;

    @Test
    public void checkStartScreenIsOpen(){
        System.out.println("[DEBUG] -------------------------------------------------------");
        System.out.println("[DEBUG] T E S T  S T A R T S");
        System.out.println("[DEBUG] -------------------------------------------------------");
        start = new StartScreen(driver);
        assertTrue(start.waitIsScreenLoaded());
        System.out.println("[DEBUG] Start screen is open.");
    }

    @Test(dependsOnMethods = {"checkStartScreenIsOpen"})
    public void openLoginScreen(){
        assertTrue(start.clickLoginButton());
        login = new LoginScreen(driver);
        assertTrue(login.waitIsScreenLoaded());
        System.out.println("[DEBUG] Login screen is open.");
    }

    @Test(dependsOnMethods = {"openLoginScreen"})
    public void makeLogin(){
        assertTrue(login.makeLogin());
        main = new MainScreen(driver);
        assertTrue(main.waitIsScreenLoaded());
        System.out.println("[DEBUG] Main screen is open.");
    }

    @Test(priority = 1, dependsOnMethods = {"makeLogin"})
    public void setGroup() {
        main.selected_group.click();
        main.scrollToElementByAndroidUIAutomator(GROUP);
        main.CRYPTOS.click();
        assertEquals(GROUP, main.selected_group.getText());
        System.out.println("[DEBUG] Group: " + GROUP + " is set.");
    }

    @Test(dependsOnMethods = {"setGroup"})
    public void setInstrument() {
        while (!main.current_instrument.getText().equals(INSTRUMENT_SET)) {
            main.next_instrument.click();
        }
        assertEquals(main.current_instrument.getText(), INSTRUMENT_SET);
        System.out.println("[DEBUG] Instrument: " + INSTRUMENT_SET + " is set.");
    }

    @Test(dependsOnMethods = {"setInstrument"})
    public void checkInstrumentIsAvailable() {
        assertTrue(main.checkInstrumentIsAvailable());
    }

    @Test(dependsOnMethods = {"checkInstrumentIsAvailable"})
    public void openPositionOpeningDialog() {
        main.waitGetClickableElement(main.BUY).click();
        assertTrue(main.waitIsElementVisible(main.volume_lots));
        System.out.println("[DEBUG] Position opening dialog is open.");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void checkDefaultVolumeLots(){
        double actualVolume = Double.parseDouble(main.volume_lots.getText());
        double expectedVolume = Double.parseDouble(VOLUME_DEFAULT);
        assertEquals(actualVolume,expectedVolume);
        System.out.println("[DEBUG] Default volume lots is correct.");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void checkDefaultVolumeUnits(){
        assertEquals(Double.parseDouble(main.volume_units.getText()), Double.parseDouble(VOLUME_DEFAULT)*100);
        System.out.println("[DEBUG] Default volume units is correct.");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"}, enabled = false)
    public void checkAssetLeverage(){
        ASSET_LEVERAGE = String.valueOf(APIClient.getAssetLeverage());
        assertEquals(main.asset_leverage.getText(), "1:" + ASSET_LEVERAGE);
        System.out.println("[DEBUG] Asset leverage is correct.");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void checkSwitcherTpIsNotChecked() {
        assertEquals(main.Close_at_Profit.getAttribute("checked"), "false");
        System.out.println("[DEBUG] TP is disabled.");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void checkSwitcherSlIsNotChecked() {
        assertEquals(main.Close_at_Loss.getAttribute("checked"), "false");
        System.out.println("[DEBUG] SL is disabled.");
    }

    @Test(dependsOnMethods = {"openPositionOpeningDialog"})
    public void openNewPosition() {
        main.waitClickClearEnterDataInField(main.volume_lots, VOLUME_SET);
        System.out.println("[DEBUG] Volume input.");
        main.waitGetClickableElement(main.Close_at_Profit).click();
        main.waitGetClickableElement(main.Close_at_Loss).click();
        String tp_input = String.valueOf((double) Math.round((Double.parseDouble(main.tp_create.getText()) + 0.1) * 100000d) / 100000d);
        main.waitClickClearEnterDataInField(main.tp_create, tp_input);
        System.out.println("[DEBUG] TP input.");
        TP_SET = String.valueOf(Double.parseDouble(main.tp_create.getText()));
        String sl_input = String.valueOf((double) Math.round((Double.parseDouble(main.sl_create.getText()) - 0.1) * 100000d) / 100000d);
        main.waitClickClearEnterDataInField(main.sl_create, sl_input);
        System.out.println("[DEBUG] SL input.");
        SL_SET = String.valueOf(Double.parseDouble(main.sl_create.getText()));
        main.waitGetClickableElement(main.BUY_button).click();
        OPEN_PRICE = Double.parseDouble(main.opened_rate.getText());
        assertTrue(OPEN_PRICE != 0);
        /* debug info */
        System.out.println("[DEBUG]..............[set values]..............");
        System.out.println("[DEBUG] TP: " + TP_SET);
        System.out.println("[DEBUG] SL: " + SL_SET);
        System.out.println("[DEBUG]..............[open order]..............");
        System.out.println("[DEBUG] Order was opened successfully. OPEN PRICE(notification): " + OPEN_PRICE);
    }

    @Test(dependsOnMethods = {"openNewPosition"})
    public void openPortfolio() {
        main.Portfolio.click();
        assertTrue(main.waitGetVisibleElement(main.OPEN).isSelected());
        System.out.println("[DEBUG] 'Portfolio' is selected.");
    }

    @Test(dependsOnMethods = {"openPortfolio"})
    public void checkOrderIsInListOfOpenOrders() {
        List<AndroidElement> orders = main.opened_order;
        boolean found = false;
        for (MobileElement i : orders) {
            if (Double.parseDouble(i.getText()) == (OPEN_PRICE)) {
                found = true;
                i.click();
                ORDER_NUMBER = main.order_number.getText();
                break;
            }
        }
        assertTrue(found);
        System.out.println("[DEBUG] Order is in list of open orders('OPEN'). Dialog is open.");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkInstrumentInOpenOrder() {
        assertEquals(main.instrument_order.getText(), INSTRUMENT_SET);
        System.out.println("[DEBUG] Instrument is correct.");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkVolumeLotsInOpenOrder(){
        assertEquals(Double.parseDouble(main.volume_open.getText()), Double.parseDouble(VOLUME_SET));
        System.out.println("[DEBUG] Volume lots is correct.");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkVolumeUnitsInOpenOrder(){
        assertEquals(Double.parseDouble(main.volume_units.getText()), Double.parseDouble(VOLUME_SET)*100);
        System.out.println("[DEBUG] Volume units is correct.");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"}, enabled = false)
    public void checkAssetLeverageInOpenOrder(){
        ASSET_LEVERAGE = String.valueOf(APIClient.getAssetLeverage());
        assertEquals(main.asset_leverage.getText(), "1:" + ASSET_LEVERAGE);
        System.out.println("[DEBUG] Asset leverage is correct.");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkTpInOpenOrder() {
        assertEquals(String.valueOf(Double.parseDouble(main.tp_create.getText())), TP_SET);
        System.out.println("[DEBUG] TP is correct.");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkSlInOpenOrder() {
        assertEquals(String.valueOf(Double.parseDouble(main.sl_create.getText())), SL_SET);
        System.out.println("[DEBUG] SL is correct.");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkOperationTypeInOpenOrder() {
        assertEquals(main.operation_open.getText(), OPERATION_TYPE);
        System.out.println("[DEBUG] Operation type is correct.");
    }

    @Test(dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void checkOpenPriceInOpenOrder() {
        assertEquals(Double.parseDouble(main.order_price_open.getText().split("at ")[1]), OPEN_PRICE);
        System.out.println("[DEBUG] Open price is correct.");
    }

    @Test(priority = 2, dependsOnMethods = {"checkOrderIsInListOfOpenOrders"})
    public void closeOrder() {
        String PROFIT = null;
        main.waitGetClickableElement(main.Close_Position).click();
        try {
            PROFIT = main.profit_notification.getText();
            /* debug info */
            System.out.println("[DEBUG]..............[close order].............");
            System.out.println("[DEBUG] Order was closed successfully. PROFIT(notification): " + PROFIT);
        } catch (NoSuchElementException e) {
            if (main.No_quotes.getText().equals("No quotes")) {
                System.out.println("[DEBUG] Order cannot be closed. The reason is: 'No quotes'.");
                ORDER_WILL_NOT_BE_CLOSED = true;
                PROFIT = "";
                main.OK.click();
            } else {
                System.out.println("[ERROR] Order closing message could not be found. Message 'No quotes' not found.");
            }
        }
        assertNotNull(PROFIT);
    }

    @Test(dependsOnMethods = {"closeOrder"})
    public void openHistory() {
        main.waitGetClickableElement(main.HISTORY).click();
        assertTrue(main.waitIsElementVisible(main.history_item) & main.waitGetVisibleElement(main.HISTORY).isSelected());
        System.out.println("[DEBUG] 'HISTORY' is open.");
    }

    @Test(dependsOnMethods = {"openHistory"})
    public void checkClosedOrderIsInHistory() {
        List<AndroidElement> orders = main.info;
        boolean found = false;
        if (orders.size() > 0) {
            for (int i = 0; i < orders.size() & i < 5; i++) {
                orders.get(i).click();
                String orderInfo = main.info_order_sub_string.getText();
                if (!ORDER_WILL_NOT_BE_CLOSED) {
                    if (orderInfo.contains(ORDER_NUMBER) &
                            Double.parseDouble(orderInfo.split("Rate: ")[1].split(" Order")[0]) == OPEN_PRICE) {
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
            if(ORDER_WILL_NOT_BE_CLOSED & main.No_positions_yet.isEnabled()){
                found = true;
                System.out.println("[DEBUG] List of closed orders is empty ('Nothing yet. Change the filter settings or choose an instrument to trade.').");
            }
        }
        assertTrue(found);
        System.out.println("[DEBUG] -------------------------------------------------------");
        System.out.println("[DEBUG] T E S T  F I N I S H E D");
        System.out.println("[DEBUG] -------------------------------------------------------");
    }
}