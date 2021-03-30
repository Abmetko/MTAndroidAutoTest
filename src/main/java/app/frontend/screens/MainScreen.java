package app.frontend.screens;


import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static core.utils.PropertyLoader.getProperty;


public class MainScreen extends BaseScreen{

    public @AndroidFindBy(id = "vp_cards")
    AndroidElement view_pager_cards;

    public @AndroidFindBy(id = "tv_selected_group")
    AndroidElement selected_group;

    public @AndroidFindBy(accessibility = "Open")
    AndroidElement navigation_button;

    public @AndroidFindBy(xpath = "//android.widget.TextView[@text='Trade']")
    AndroidElement Trade;

    public @AndroidFindBy(xpath = "//android.widget.TextView[@text='Portfolio']")
    AndroidElement Portfolio;

    public @AndroidFindBy(xpath = "//android.widget.TextView[@text='Deposit']")
    AndroidElement Deposit;

    public @AndroidFindBy(xpath = "//android.view.View[@text='Credit Cards']")
    AndroidElement Credit_Cards;

    public @AndroidFindBy(xpath = "//android.view.View[@text='CHOOSE YOUR PAYMENT METHOD']")
    AndroidElement CHOOSE_YOUR_PAYMENT_METHOD;

    public @AndroidFindBy(xpath = "//android.view.View[@resource-id='deposit-iframe']//android.view.View[@text='Credit and Debit Card']")
    AndroidElement Credit_and_Debit_Card;

    public @AndroidFindBy(xpath = "//android.view.View[@text='CREDIT AND DEBIT CARD']")
    AndroidElement CREDIT_AND_DEBIT_CARD;

    public @AndroidFindBy(xpath = "//android.widget.TextView[@text='Markets']")
    AndroidElement Markets;

    public @AndroidFindBy(id = "tv_change_account")
    AndroidElement to_Demo;

    public @AndroidFindBy(xpath = "//android.widget.LinearLayout[contains(@resource-id,'tab_v_main')]//android.widget.TextView[@index='1']")
    AndroidElement current_instrument;

    public @AndroidFindBy(xpath = "//android.widget.LinearLayout[contains(@resource-id,'tab_v_main')]//android.widget.TextView[@index='2']")
    AndroidElement next_instrument;

    public @AndroidFindBy(id = "ll_container_sl")
    AndroidElement SL;

    public @AndroidFindBy(id = "ll_container_tp")
    AndroidElement TP;

    public @AndroidFindBy(id = "tv_sell")
    AndroidElement SELL;

    public @AndroidFindBy(id = "tv_buy")
    AndroidElement BUY;

    public @AndroidFindBy(id = "tv_instrument_error")
    AndroidElement instrument_error;

    public @AndroidFindBy(id = "wheel_amount")
    AndroidElement volume;

    public @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@resource-id,'np_tp')]//android.widget.EditText[contains(@resource-id,'et_number')]")
    AndroidElement tp_create;

    public @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@resource-id,'np_sl')]//android.widget.EditText[contains(@resource-id,'et_number')]")
    AndroidElement sl_create;

    public @AndroidFindBy(id = "et_number")
    AndroidElement volume_lots;

    public @AndroidFindBy(id = "tv_required_volume_units")
    AndroidElement volume_units;

    public @AndroidFindBy(id = "tv_required_assets")
    AndroidElement asset_leverage;

    public @AndroidFindBy(id = "tv_required_margin")
    AndroidElement required_margin;

    public @AndroidFindBy(id = "switch_tp")
    AndroidElement Close_at_Profit;

    public @AndroidFindBy(id = "switch_sl")
    AndroidElement Close_at_Loss;

    public @AndroidFindBy(id = "tv_open")
    AndroidElement BUY_button;

    public @AndroidFindBy(id = "tv_description")
    AndroidElement Opened_on;

    public @AndroidFindBy(id = "tv_instrument")
    AndroidElement instrument;

    public @AndroidFindBy(id = "tv_currency")
    AndroidElement current_market;

    public @AndroidFindBy(id = "dialog_close_iv")
    AndroidElement close_dialog;

    public @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'tv_open_notification_info')]") //BUY 0.01 @ 0.17955
    AndroidElement open_notification_info;

    public @AndroidFindBy(id = "tv_opened_rate")//TRADE - 0.17955, after order was open(notification)
    AndroidElement opened_rate;

    public @AndroidFindBy(id = "tv_value")//PORTFOLIO:OPEN - 0.17955
    @CacheLookup
    List<AndroidElement> opened_order;

    public @AndroidFindBy(xpath = "//*[contains(@resource-id,'tv_name') and @text='CRYPTOS']")
    AndroidElement CRYPTOS;

    public @AndroidFindBy(id = "tv_opened")
    AndroidElement OPEN;

    public @AndroidFindBy(id = "tv_history")
    AndroidElement HISTORY;

    public @AndroidFindBy(id = "update_tv")
    AndroidElement Update;

    public @AndroidFindBy(id = "close_tv")
    AndroidElement Close_Position;

    public @AndroidFindBy(id = "order_number_tv")
    AndroidElement order_number;

    public @AndroidFindBy(id = "tv_operation")//SELL 0.01 - PORTFOLIO
    AndroidElement operation;

    public @AndroidFindBy(id = "operation_tv")//SELL
    AndroidElement operation_open;

    public @AndroidFindBy(id = "opened_volume_tv")//0.01
    AndroidElement volume_open;

    public @AndroidFindBy(id = "iv_info")
    List<AndroidElement> info;

    public @AndroidFindBy(id = "tv_container_sub_string")
    AndroidElement info_order_sub_string;

    public @AndroidFindBy(id = "instrument_tv")
    AndroidElement instrument_order;

    public @AndroidFindBy(id = "open_price_tv")
    AndroidElement order_price_open;

    public @AndroidFindBy(id = "tv_profit")//PORTFOLIO:OPEN - after order was closed
    AndroidElement profit_notification;

    public @AndroidFindBy(id = "tv_status")//PORTFOLIO:HISTORY
    AndroidElement profit_closed;

    public @AndroidFindBy(id = "ll_container_main_history_item")//PORTFOLIO:HISTORY
    AndroidElement history_item;

    public @AndroidFindBy(id = "tv_from_date")//28.01.2020
    AndroidElement from_date;

    public @AndroidFindBy(id = "tv_to_date")
    AndroidElement to_date;

    public @AndroidFindBy(xpath = "//android.view.View[@resource-id='android:id/month_view']/android.view.View[@checked='true']")//28.01.2020
    AndroidElement current_day;

    public @AndroidFindBy(id = "android:id/button1")
    AndroidElement OK_calendar;

    public @AndroidFindBy(id = "tv_message")
    AndroidElement No_quotes;

    public @AndroidFindBy(id = "tv_support")
    AndroidElement Oops;

    public @AndroidFindBy(id = "ll_container_dialog")
    AndroidElement dialog;

    public @AndroidFindBy(id = "tv_positive")
    AndroidElement OK;

    public @AndroidFindBy(id = "tv_empty_list")
    AndroidElement No_positions_yet;


    public MainScreen(AndroidDriver<AndroidElement> driver){
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @Override
    public void waitScreenLoaded(){
        try {
            driverWait.until(ExpectedConditions.visibilityOf(selected_group));
        }catch (TimeoutException e)  {
            throw new TimeoutException();
        }
    }

    @Override
    public void openScreen(){
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.openScreen();
        loginScreen.waitClickEnterDataInField(waitGetClickableElement(loginScreen.Email), getProperty("user.email"));
        loginScreen.waitClickEnterDataInField(waitGetClickableElement(loginScreen.Password), getProperty("user.password"));
        loginScreen.waitGetClickableElement(loginScreen.Login).click();
        waitScreenLoaded();
    }

    public void setSpinnerValue(MobileElement spinner, double value){
        final int ANIMATION_TIME = 200; // ms
        final int PRESS_TIME = 200; // ms
        Point pointStart, pointEnd;
        PointOption pointOptionStart, pointOptionEnd;
        // init screen variables
        int spinnerY = spinner.getCenter().y;
        Dimension dims = driver.manage().window().getSize();
        // init start point = center of screen
        pointStart = new Point(dims.width / 2, spinnerY + 25); //y=1525
        pointEnd = new Point(dims.width / 2, spinnerY + 80); //y=1580
        // execute swipe using TouchAction
        pointOptionStart = PointOption.point(pointStart.x, pointStart.y);
        pointOptionEnd = PointOption.point(pointEnd.x, pointEnd.y);
        for(int i = 0; i < (1 - value)*10; i++){
            try {
                new TouchAction(driver)
                        .press(pointOptionStart)
                        // a bit more reliable when we add small wait
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                        .moveTo(pointOptionEnd)
                        .release().perform();
            } catch (Exception e) {
                e.getMessage();
                return;
            }
            // always allow swipe action to complete
            try {
                Thread.sleep(ANIMATION_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkInstrumentIsAvailable(){
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        try {
            instrument_error.getText();
            System.out.println("[WARNING] Instrument is NOT AVAILABLE. There is no reason to continue test.");
            return false;
        }catch (Exception e){
            System.out.println("[DEBUG] Instrument is AVAILABLE.");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return true;
        }
    }
}