package com.srm.curahealth.base;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.srm.curahealth.pages.AppointmentPage;
import com.srm.curahealth.pages.HomePage;
import com.srm.curahealth.pages.LoginPage;
import com.srm.curahealth.utils.ConfigReader;
import com.srm.curahealth.utils.DriverFactory;

public abstract class BaseTest {

    static {
        Logger.getLogger("org.openqa.selenium.devtools").setLevel(Level.SEVERE);
        Logger.getLogger("org.openqa.selenium.chromium").setLevel(Level.SEVERE);
    }

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initializeDriver();
        driver = DriverFactory.getDriver();
        driver.get(ConfigReader.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    protected AppointmentPage openAppointmentPageForValidUser() {
        new HomePage(driver).clickMakeAppointment();
        LoginPage loginPage = new LoginPage(driver);

        if (loginPage.isLoaded()) {
            return loginPage.loginValidUser(ConfigReader.getProperty("valid.username"),
                    ConfigReader.getProperty("valid.password"));
        }

        return new AppointmentPage(driver);
    }
}
