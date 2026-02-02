package com.coverfox.tests;

import com.coverfox.pages.HealthSortPage;
import com.coverfox.utils.ExcelWriter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HealthSortTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(HealthSortTest.class);

    @Test
    public void healthSort_Top3Policies_ShouldBeWrittenToApacheFile() throws IOException {
        HealthSortPage page = new HealthSortPage(driver);
        List<String[]> top3 = page.getTopThreePoliciesLowPremium();
        log.info("--- TOP 3 POLICIES ---");
        int i = 1;
        for (String[] p : top3) {
            log.info("{}. Insurer: {} | Price: ₹{} | Premium: ₹{}", i++, p[0], p[1], p[2]);
        }

        String file = ExcelWriter.writePolicies("Top3Policies", top3, "top-3-policies");

        Assert.assertTrue(top3.size() > 0, "Expected at least one policy");
        Assert.assertNotNull(file, "Excel file path should not be null");
    }
}
