package com.coverfox.tests;

import com.coverfox.pages.HealthMenuPage;
import com.coverfox.utils.ExcelWriter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HealthMenuTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(HealthMenuTest.class);

    @Test
    public void healthMenu_ShouldWriteAllItemsToApacheFile() throws IOException {
        HealthMenuPage page = new HealthMenuPage(driver);
        List<String> items = page.getHealthSubmenuItems();
        log.info("Total items found: {}", items.size());
        for (String s : items) log.info(s);

        String file = ExcelWriter.writeSingleColumn("HealthMenu", "Items", items, "health-menu-items");

        Assert.assertTrue(items.size() > 0, "Expected at least one health submenu item");
        Assert.assertNotNull(file, "Excel file path should not be null");
    }
}
