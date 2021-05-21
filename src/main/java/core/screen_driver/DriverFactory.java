package core.screen_driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import static core.mt.utils.PropertyLoader.getProperty;


public class DriverFactory {

    private static AppiumDriverLocalService service = null;

    protected static AndroidDriver<AndroidElement> driver = null;

    public static final String APP_DIR = "src/main/resources";

    private static final String APP_NAME = getProperty("file.name");

    private static final String APP_FILE_ABSOLUTE_PATH = getAppFilePath();

    public static String os_type;

    public static String project_name;

    public static String package_name;

    private DriverFactory(){
        throw new IllegalStateException("DriverFactory is utility class");
    }

    public static AndroidDriver<AndroidElement> getDriver() {
        if (driver == null) {
            configureSessionForRealAndroidDevice();
        }
        return driver;
    }

    private static void configureSessionForRealAndroidDevice() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", APP_FILE_ABSOLUTE_PATH);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "5200cfd8b2bfb4e5");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, package_name);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getProperty("app.wait.activity"));
        capabilities.setCapability("newCommandTimeout", 180000);
        capabilities.setCapability("isHeadless", false);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("settings[waitForIdleTimeout]", 200);
        try {
            driver = new AndroidDriver<AndroidElement>(new URL("http://192.168.140.61:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.unlockDevice();
    }

    public static void runAppiumServer() {
        String propertyOsName = "appium.node.module.macOS";
        if(os_type.equals("w")) propertyOsName = "appium.node.module.windows";
        String appiumNodeModulePath = getProperty(propertyOsName);
        service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .withAppiumJS(new File(appiumNodeModulePath))
                .withIPAddress("192.168.140.61").usingPort(4723));
        service.start();
    }

    public static void stopAppiumServer() {
        if(service != null){
            service.stop();
            service = null;
        }
    }

    public static void killDriver(){
        driver = null;
    }

    private static String getAppFilePath() {
        File appDir = new File(APP_DIR);
        return new File(appDir, APP_NAME).getAbsolutePath();
    }
}