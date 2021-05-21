import core.application_management.ManageApp;
import core.screen_driver.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.*;


public class BaseTest {

    protected AndroidDriver<AndroidElement> driver;
    protected Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeSuite
    @Parameters({ "app_args","os_type" })
    public void beforeSuite(String app_args, String os_type){
        DriverFactory.os_type = os_type;
        DriverFactory.runAppiumServer();
        DriverFactory.project_name = app_args.split(",")[1];
        DriverFactory.package_name = app_args.split(",")[0];
    }

    @BeforeClass(alwaysRun=true)
    public void beforeClass(){
        driver = DriverFactory.getDriver();
    }

    @AfterClass(alwaysRun=true)
    public void afterClass(){
        ManageApp.resetApp(driver);
    }

    @AfterSuite(alwaysRun=true)
    public void afterSuite(){
        try{
            ManageApp.removeApp(driver);
            ManageApp.driverQuit(driver);
        }finally {
            DriverFactory.stopAppiumServer();
        }
    }

    static {
        System.out.println("mt-common: 2.3");
    }
}