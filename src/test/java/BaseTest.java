import core.application_management.ManageApp;
import core.screen_driver.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.testng.annotations.*;


public class BaseTest {

    protected AndroidDriver<AndroidElement> driver;
    protected ManageApp manageApp;

    @BeforeSuite
    @Parameters({ "app_url","app_args","run_type" })
    public void beforeSuite(String app_url, String app_args, String run_type){
        DriverFactory.APP_URL = app_url;
        DriverFactory.APP_ARGS = app_args;
        DriverFactory.RUN_TYPE = run_type;
    }

    @BeforeClass(alwaysRun=true)
    public void beforeClass(){
        driver = DriverFactory.getDriver();
        manageApp = new ManageApp(driver);
    }

    @AfterClass(alwaysRun=true)
    public void afterClass(){
        manageApp.resetApp();
    }

    @AfterSuite(alwaysRun=true)
    public void afterSuite(){
        manageApp.driverQuit();
    }
}