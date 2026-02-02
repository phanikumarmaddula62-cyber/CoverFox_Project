package com.coverfox.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {
    public static String capture(WebDriver driver, String name) {
        String ts = DateTimeUtil.timestamp();
        String path = "Proofs/screenshots/" + name + "-" + ts + ".png";
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File dest = new File(path);
            dest.getParentFile().mkdirs();
            FileUtils.copyFile(src, dest);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
