
package com.srm.curahealth.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.srm.curahealth.constants.FrameworkConstants;

public final class ExtentManager {

    private static final ExtentReports EXTENT_REPORTS = createExtentReports();
    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();

    private ExtentManager() {
    }

    private static ExtentReports createExtentReports() {
        try {
            Files.createDirectories(FrameworkConstants.REPORTS_DIRECTORY);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to create reports directory.", exception);
        }

        String timestamp = LocalDateTime.now().format(FrameworkConstants.TIMESTAMP_FORMAT);
        Path reportPath = FrameworkConstants.REPORTS_DIRECTORY.resolve("ExtentReport_" + timestamp + ".html");

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath.toString());
        sparkReporter.config().setReportName("CURA Healthcare Automation Report");
        sparkReporter.config().setDocumentTitle("CURA Healthcare Execution Results");

        ExtentReports reports = new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("Framework", "Selenium Java TestNG POM");
        reports.setSystemInfo("Browser", ConfigReader.getBrowser());
        reports.setSystemInfo("Base URL", ConfigReader.getBaseUrl());
        return reports;
    }

    public static ExtentReports getExtentReports() {
        return EXTENT_REPORTS;
    }

    public static void setTest(ExtentTest test) {
        EXTENT_TEST.set(test);
    }

    public static ExtentTest getTest() {
        return EXTENT_TEST.get();
    }

    public static void unload() {
        EXTENT_TEST.remove();
    }
}
