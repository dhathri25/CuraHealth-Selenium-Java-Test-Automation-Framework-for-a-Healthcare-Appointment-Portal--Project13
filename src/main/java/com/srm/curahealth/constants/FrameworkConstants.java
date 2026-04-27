
package com.srm.curahealth.constants;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public final class FrameworkConstants {

    public static final Path PROJECT_ROOT = Paths.get(System.getProperty("user.dir"));
    public static final Path REPORTS_DIRECTORY = PROJECT_ROOT.resolve("reports");
    public static final Path SCREENSHOTS_DIRECTORY = PROJECT_ROOT.resolve("screenshots");
    public static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private FrameworkConstants() {
    }
}
