package com.coverfox.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            String path = "output/reports/ExtentReport-" + DateTimeUtil.timestamp() + ".html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setReportName("Coverfox Automation Report");
            reporter.config().setDocumentTitle("Automation Results");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Project", "Coverfox");
        }
        return extent;
    }
}
