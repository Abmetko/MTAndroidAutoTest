package app.frontend.screens;

import core.screen_driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static core.mt.ProjectPackages.*;
import static core.mt.utils.PropertyLoader.getProperty;


public class LoginScreen extends BaseScreen {

    public @AndroidFindBy(xpath = "//*[@text='Login Page' and @index='0']")
    AndroidElement Login_Page;

    public @AndroidFindBy(xpath = "//*[@class='android.widget.EditText' and (./preceding-sibling::* | ./following-sibling::*)[@text='Email']]")
    AndroidElement Email;

    public @AndroidFindBy(xpath = "//*[@class='android.widget.EditText' and (./preceding-sibling::* | ./following-sibling::*)[@text='Password']]")
    AndroidElement Password;

    public @AndroidFindBy(xpath = "//android.widget.Button[@text='Login']")
    AndroidElement Login;

    public @AndroidFindBy(xpath = "//android.view.View[@text='Invalid password']")
    AndroidElement Invalid_password;

    public @AndroidFindBy(xpath = "//android.view.View[@text='Invalid e-mail address']")
    AndroidElement Invalid_e_mail_address;

    public @AndroidFindBy(xpath = "//*[@text='Forgot password?']")
    AndroidElement Forgot_password;


    public LoginScreen(AppiumDriver<AndroidElement> driver){
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @Override
    public void waitScreenLoaded(){
        try {
            driverWait.until(ExpectedConditions.visibilityOf(Forgot_password));
        }catch (TimeoutException e)  {
            throw new TimeoutException();
        }
    }

    @Override
    public void openScreen(){
        StartScreen startScreen = new StartScreen(driver);
        startScreen.waitGetClickableElement(startScreen.Login).click();
        waitScreenLoaded();
    }

    public boolean makeLogin(){
        try{
            String packageName = DriverFactory.package_name;
            String userEmail = getProperty("user.email");
            String userPassword = getProperty("user.password");
            if(HFT_TRADING_AU.getValueAsList().contains(packageName)){
                userPassword = getProperty("user.password.hft");
            }
            else if(HFT_TRADING.getValueAsList().contains(packageName)){
                userEmail = getProperty("user.email.hft");
                userPassword = getProperty("user.password.hft");
            }
            waitClickEnterDataInField(waitGetClickableElement(Email), userEmail);
            logger.debug("Input email: " + userEmail);
            waitClickEnterDataInField(waitGetClickableElement(Password), userPassword);
            logger.debug("Input password: " + userPassword);
            waitGetClickableElement(Login).click();
            return true;
        }catch (WebDriverException e){
            e.printStackTrace();
            return false;
        }
    }
}