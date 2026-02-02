package com.coverfox.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {
    private static final Logger log = LogManager.getLogger(TestListener.class);
    private static ExtentReports extent = ExtentManager.getReporter();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest t = extent.createTest(result.getMethod().getMethodName());
        test.set(t);
        log.info("Starting test: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, result.getThrowable());
        Object driverObj = result.getTestContext().getAttribute("driver");
        if (driverObj instanceof WebDriver) {
            WebDriver driver = (WebDriver) driverObj;
            String path = ScreenshotUtil.capture(driver, result.getMethod().getMethodName());
            if (path != null) test.get().addScreenCaptureFromPath(path);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        log.info("Test execution finished");
    }
}
