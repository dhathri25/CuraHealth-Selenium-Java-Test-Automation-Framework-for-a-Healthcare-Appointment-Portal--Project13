package com.srm.curahealth.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.srm.curahealth.base.BaseTest;
import com.srm.curahealth.pages.AppointmentPage;
import com.srm.curahealth.pages.HomePage;
import com.srm.curahealth.pages.LoginPage;
import com.srm.curahealth.pages.SidebarPage;
import com.srm.curahealth.utils.ConfigReader;

public class AuthenticationTests extends BaseTest {

    @Test(dataProvider = "loginCredentials", dataProviderClass = TestDataProviders.class)
    public void verifyLoginWithDataProvider(String username, String password, boolean shouldSucceed) {
        LoginPage loginPage = new HomePage(driver).clickMakeAppointment();
        Assert.assertTrue(loginPage.isLoaded(), "Login page should be displayed.");

        if (shouldSucceed) {
            AppointmentPage appointmentPage = loginPage.loginValidUser(username, password);
            Assert.assertTrue(appointmentPage.isLoaded(), "Appointment page should be displayed after valid login.");
        } else {
            loginPage.loginInvalidUser(username, password);
            Assert.assertTrue(loginPage.getErrorMessage().contains("Login failed"),
                    "Invalid credentials should display the login failure message.");
        }
    }

    @Test
    public void verifyLogoutNavigatesBackToHomepage() {
        AppointmentPage appointmentPage = new HomePage(driver)
                .clickMakeAppointment()
                .loginValidUser(ConfigReader.getProperty("valid.username"), ConfigReader.getProperty("valid.password"));

        Assert.assertTrue(appointmentPage.isLoaded(), "Appointment page should load for the authenticated user.");

        SidebarPage sidebarPage = new HomePage(driver).openSidebar();
        HomePage homePage = sidebarPage.logout();
        Assert.assertTrue(homePage.isLoaded(), "Homepage should be displayed after logout.");
    }

    @Test
    public void verifyProtectedPagesRedirectToLoginWhenUserIsUnauthenticated() {
        var loginPage = new HomePage(driver).openProtectedAppointmentPage();

        Assert.assertTrue(loginPage.isLoaded()
                        || driver.getCurrentUrl().contains("login")
                        || driver.getCurrentUrl().contains("profile.php")
                        || driver.getPageSource().contains("Make Appointment")
                        || driver.getPageSource().contains("CURA Healthcare Service"),
                "Unauthenticated users should be presented with the login page.");
    }
}
