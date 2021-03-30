package app.frontend.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.*;


public abstract class BaseScreen {

    protected AppiumDriver<AndroidElement> driver;
    protected WebDriverWait driverWait;

    public BaseScreen(AppiumDriver<AndroidElement> driver) {
        this.driver = driver;
        driverWait = new WebDriverWait(driver, 30);
    }

    public boolean isElementPresent(String text){
        try{
            driver.findElementByName(text);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public boolean isElementVisible(AndroidElement element){
        try{
            return element.isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public boolean waitIsElementVisible(AndroidElement element){
        try{
            driverWait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }catch (TimeoutException e){
            e.printStackTrace();
            return false;
        }
    }

    public AndroidElement waitGetVisibleElement(AndroidElement element) throws TimeoutException {
        return (AndroidElement) driverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public AndroidElement waitGetClickableElement(AndroidElement element) throws TimeoutException {
        return (AndroidElement) driverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitClickAndClearField(AndroidElement element){
        element.click();
        element.clear();
    }

    public void waitClickEnterDataInField(AndroidElement element, String data){
        element.click();
        element.sendKeys(data);
    }

    public void waitClickClearEnterDataInField(AndroidElement element, String data){
        element.click();
        element.clear();
        element.sendKeys(data);
    }

    public void scrollToElementByAndroidUIAutomator(String text){
        driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector())." +
                "scrollIntoView(new UiSelector().text(\"" + text + "\"));"));
    }

    public void openScreen(){

    }

    public boolean waitIsScreenLoaded(){
        try {
            waitScreenLoaded ();
            return true;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void waitScreenLoaded(){
    }

    public void hideKeyBoard(){
        try{
            driver.hideKeyboard();
        }catch (WebDriverException e){
            e.getMessage();
        }
    }
}