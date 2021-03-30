package core.application_management;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import java.time.Duration;
import static core.utils.PropertyLoader.getProperty;


public class ManageApp {

    private AppiumDriver<AndroidElement> driver;

    public ManageApp(AppiumDriver<AndroidElement> driver){
        this.driver = driver;
    }

    public void resetApp(){
        driver.resetApp();
    }

    public void closeApp(){
        driver.closeApp();
    }

    public void launchApp(){
        driver.launchApp();
    }

    public void driverQuit(){
        driver.quit();
    }

    public void removeApp(){
        driver.removeApp(getProperty("app.package"));
    }

    public void runAppInBackgroundAndRelaunch(){
        driver.runAppInBackground(Duration.ofSeconds(1));
    }
}