

package com.srm.curahealth.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.srm.curahealth.utils.DriverFactory;
import com.srm.curahealth.utils.ExtentManager;
import com.srm.curahealth.utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentManager.getExtentReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentManager.setTest(ExtentManager.getExtentReports().createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().log(Status.PASS, "Test passed");
        ExtentManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ScreenshotUtil.captureScreenshot(DriverFactory.getDriver(), result.getMethod().getMethodName());
        ExtentManager.getTest().fail(result.getThrowable());
        try {
            ExtentManager.getTest().fail("Screenshot on failure",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (Exception exception) {
            ExtentManager.getTest().log(Status.WARNING, "Unable to attach screenshot: " + exception.getMessage());
        }
        ExtentManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.getTest().log(Status.SKIP, "Test skipped: " + result.getThrowable());
        ExtentManager.unload();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getExtentReports().flush();
    }
}
