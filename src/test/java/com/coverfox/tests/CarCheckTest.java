package com.coverfox.tests;

import com.coverfox.pages.CarCheckPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CarCheckTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(CarCheckTest.class);

    @Test
    public void carCheck_ShouldShowErrorAndTakeScreenshot() {
        CarCheckPage page = new CarCheckPage(driver);
        String err = page.runFlowAndCaptureError();
        log.info("ERROR MESSAGE: {}", err);
        Assert.assertTrue(err != null && !err.trim().isEmpty(), "Expected an error message to be visible");
    }
}
