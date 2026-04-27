
package com.srm.curahealth.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = loadProperties();

    private ConfigReader() {
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        Path fallbackPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", CONFIG_FILE);
        if (Files.exists(fallbackPath)) {
            try (InputStream inputStream = Files.newInputStream(fallbackPath)) {
                properties.load(inputStream);
                return properties;
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to read fallback config from " + fallbackPath, exception);
            }
        }

        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
                return properties;
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read " + CONFIG_FILE, exception);
        }

        throw new IllegalStateException("Unable to locate " + CONFIG_FILE + " in src/main/resources or on the classpath.");
    }

    public static String getProperty(String key) {
        return Objects.requireNonNull(PROPERTIES.getProperty(key), "Missing config key: " + key).trim();
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getBaseUrl() {
        return getProperty("baseUrl");
    }

    public static long getTimeout() {
        return Long.parseLong(getProperty("timeout"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }
}
