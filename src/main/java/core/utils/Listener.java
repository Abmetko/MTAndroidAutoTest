package core.utils;

import core.screen_driver.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Listener implements ITestListener {

    @Override
    public void onFinish(ITestContext Result) {

    }

    @Override
    public void onStart(ITestContext Result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {

    }

    @Override
    public void onTestFailure(ITestResult Result) {
        AndroidDriver<AndroidElement> driver = DriverFactory.getDriver();
        File file  = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File fileDir = new File("src/main/resources/");
        File fileAbsolutePath = new File(new File(fileDir, Result.getName() + "-" + Result.getEndMillis() + ".jpg").getAbsolutePath());
        try {
            FileUtils.copyFile(file, fileAbsolutePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String errorTrace = "error";
        if (null != Result.getThrowable()) {
            errorTrace = Result.getThrowable().getMessage();
            System.out.println(errorTrace);
        }
    }

    @Override
    public void onTestSkipped(ITestResult Result) {

    }

    @Override
    public void onTestStart(ITestResult Result) {

    }

    @Override
    public void onTestSuccess(ITestResult Result) {

    }

    public static class DateClass{
        public static String transformTime(long millis){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(millis);
            return new SimpleDateFormat("HH:mm:ss:SSS").format(cal.getTime());
        }
    }
}