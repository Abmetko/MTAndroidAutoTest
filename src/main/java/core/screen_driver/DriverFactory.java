package core.screen_driver;

import core.utils.APIClient;
import core.utils.PropertyLoader;
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
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class DriverFactory {

    private static AppiumDriverLocalService SERVICE = null;

    protected static AndroidDriver<AndroidElement> driver = null;

    private static final String appiumInstallationDir = "C:/Program Files";

    private static final String appiumNode = appiumInstallationDir + File.separator + "nodejs" + File.separator + "node.exe";

    private static final String appiumNodeModule = "C:/Users/abmetko/AppData/Roaming/npm/node_modules/appium/build/lib/main.js";

    private static final String APP_DIR = "src/main/resources";

    private static final String appName = PropertyLoader.getProperty("file.name");

    private static final String APP_FILE_ABSOLUTE_PATH = getAppFilePath();

    public static String APP_URL;

    public static String APP_ARGS;

    public static String RUN_TYPE;

    private DriverFactory(){

    }

    public static AndroidDriver<AndroidElement> getDriver() {
        if (driver != null) {
            return driver;
        }
        configureSessionForRealAndroidDevice();
        return driver;
    }

    private static void configureSessionForRealAndroidDevice() {
        if(RUN_TYPE.equals("l")){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "28a598b70804");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.0");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PropertyLoader.getProperty("app.package"));
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, PropertyLoader.getProperty("app.wait.activity"));
            capabilities.setCapability("newCommandTimeout", 180000);
            capabilities.setCapability("isHeadless", false);
            capabilities.setCapability("unicodeKeyboard", true);
            capabilities.setCapability("resetKeyboard", true);
            capabilities.setCapability("automationName", "UiAutomator2");
            capabilities.setCapability("autoGrantPermissions", true);
            capabilities.setCapability("settings[waitForIdleTimeout]", 200);
            try {
                driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }else if(RUN_TYPE.equals("r")){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            HashMap<String, Boolean> networkLogsOptions = new HashMap<>();
            capabilities.setCapability("browserstack.networkLogsOptions", networkLogsOptions);
            capabilities.setCapability("app", APP_URL);
            capabilities.setCapability("browserstack.user", PropertyLoader.getProperty("browserstack.login"));
            capabilities.setCapability("browserstack.key", PropertyLoader.getProperty("browserstack.password"));
            capabilities.setCapability("device", PropertyLoader.getProperty("device.name"));
            capabilities.setCapability("os_version", PropertyLoader.getProperty("device.os"));
            capabilities.setCapability("project", APP_ARGS.split(",")[1]);
            capabilities.setCapability("build", "Build: " + APIClient.getApplicationData(APP_URL));
            capabilities.setCapability("name", "smoke test");
            capabilities.setCapability("automationName", "UIAutomator2");
            capabilities.setCapability("newCommandTimeout", 120000);
            capabilities.setCapability("unicodeKeyboard", true);
            capabilities.setCapability("resetKeyboard", true);
            capabilities.setCapability("browserstack.appium_version", "1.18.0");
            capabilities.setCapability("settings[waitForIdleTimeout]", 200);
            capabilities.setCapability("disableAnimations", "true");
            capabilities.setCapability("browserstack.debug","true");
            networkLogsOptions.put("captureContent", true);
            capabilities.setCapability("browserstack.networkLogs", "true");
            capabilities.setCapability("browserstack.idleTimeout", "90");
            try {
                driver = new AndroidDriver<AndroidElement>(new URL("http://hub-cloud.browserstack.com/wd/hub"), capabilities);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    public static void runAppiumServer() {
        SERVICE = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File(appiumNode))
                .withAppiumJS(new File(appiumNodeModule))
                .withIPAddress("127.0.0.1").usingPort(4723));
        SERVICE.start();
    }

    public static void stopAppiumServer() {
        SERVICE.stop();
    }

    private static String getAppFilePath() {
        File appDir = new File(APP_DIR);
        return new File(appDir, appName).getAbsolutePath();
    }
}