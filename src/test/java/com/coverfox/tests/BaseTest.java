package com.coverfox.tests;

import com.coverfox.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.ITestContext;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    @Parameters({"baseUrl"})
    public void setUp(@Optional String baseUrl, ITestContext context) {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        context.setAttribute("driver", driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
