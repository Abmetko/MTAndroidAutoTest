package app.frontend.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class StartScreen extends BaseScreen {

    @AndroidFindBy(id = "tv_login_email")
    public AndroidElement Login;

    @AndroidFindBy(id = "tv_register")
    public AndroidElement Register;

    @AndroidFindBy(xpath = "//*[@text='WE DO NOT PROVIDE SERVICES IN YOUR COUNTRY']")
    public AndroidElement WE_DO_NOT_PROVIDE;

    public StartScreen(AppiumDriver<AndroidElement> driver){
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
    }

    @Override
    public void waitScreenLoaded(){
        try {
            driverWait.until(ExpectedConditions.elementToBeClickable(Login));
        }catch (TimeoutException e)  {
            throw new TimeoutException();
        }
    }

    public boolean clickLoginButton(){
        try{
            waitGetClickableElement(Login).click();
            return true;
        }catch (WebDriverException e){
            e.printStackTrace();
            return false;
        }
    }
}