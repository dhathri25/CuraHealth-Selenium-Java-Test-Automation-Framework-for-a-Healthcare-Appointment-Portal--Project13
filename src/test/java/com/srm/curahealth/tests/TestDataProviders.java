package com.srm.curahealth.tests;

import org.testng.annotations.DataProvider;

import com.srm.curahealth.utils.ConfigReader;

public final class TestDataProviders {

    private TestDataProviders() {
    }

    @DataProvider(name = "loginCredentials")
    public static Object[][] loginCredentials() {
        return new Object[][] {
                { ConfigReader.getProperty("valid.username"), ConfigReader.getProperty("valid.password"), true },
                { ConfigReader.getProperty("invalid.username"), ConfigReader.getProperty("invalid.password"), false }
        };
    }
}
