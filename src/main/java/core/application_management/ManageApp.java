package core.application_management;

import core.screen_driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import java.time.Duration;


public class ManageApp {

    public static void resetApp(AppiumDriver<AndroidElement> driver){
        driver.resetApp();
    }

    public static void closeApp(AppiumDriver<AndroidElement> driver){
        driver.closeApp();
    }

    public static void launchApp(AppiumDriver<AndroidElement> driver){
        driver.launchApp();
    }

    public static void driverQuit(AppiumDriver<AndroidElement> driver){
        driver.quit();
    }

    public static void removeApp(AppiumDriver<AndroidElement> driver){
        driver.removeApp(DriverFactory.package_name);
    }

    public static void runAppInBackgroundAndRelaunch(AppiumDriver<AndroidElement> driver){
        driver.runAppInBackground(Duration.ofSeconds(1));
    }
}