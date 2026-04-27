
package com.srm.curahealth.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.srm.curahealth.constants.FrameworkConstants;

public final class ScreenshotUtil {

    private ScreenshotUtil() {
    }

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            Files.createDirectories(FrameworkConstants.SCREENSHOTS_DIRECTORY);
            String timestamp = LocalDateTime.now().format(FrameworkConstants.TIMESTAMP_FORMAT);
            String fileName = testName + "_" + timestamp + ".png";
            Path screenshotPath = FrameworkConstants.SCREENSHOTS_DIRECTORY.resolve(fileName);
            Files.copy(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath(), screenshotPath,
                    StandardCopyOption.REPLACE_EXISTING);
            return screenshotPath.toAbsolutePath().toString();
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to capture screenshot for test: " + testName, exception);
        }
    }
}
