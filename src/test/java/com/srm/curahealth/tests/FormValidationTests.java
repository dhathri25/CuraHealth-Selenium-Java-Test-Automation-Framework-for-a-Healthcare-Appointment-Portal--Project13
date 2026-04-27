package com.srm.curahealth.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.srm.curahealth.base.BaseTest;
import com.srm.curahealth.pages.AppointmentPage;
import com.srm.curahealth.pages.HomePage;
import com.srm.curahealth.pages.LoginPage;
import com.srm.curahealth.pages.ProfilePage;
import com.srm.curahealth.utils.ConfigReader;
import com.srm.curahealth.utils.DateUtil;

public class FormValidationTests extends BaseTest {

    @Test
    public void verifyAppointmentFormWithEmptyDateShowsValidationBehavior() {
        AppointmentPage appointmentPage = loginAsValidUser();

        appointmentPage.selectFacility("Tokyo CURA Healthcare Center")
                .selectHealthcareProgram("Medicare")
                .clearVisitDate()
                .enterComment("Date validation scenario.")
                .clickBookAppointment();

        Assert.assertTrue(appointmentPage.isStillOnAppointmentPage(),
                "User should remain on the appointment page when visit date is empty.");
        Assert.assertFalse(appointmentPage.getVisitDateValidationMessage().trim().isEmpty(),
                "Visit date input should expose a validation message when submitted empty.");
    }

    @Test
    public void verifyLoginFormWithEmptyUsernameAndPasswordShowsValidationMessage() {
        LoginPage loginPage = new HomePage(driver).clickMakeAppointment();
        loginPage.submitEmptyLogin();

        Assert.assertTrue(loginPage.getErrorMessage().contains("Login failed"),
                "Submitting empty login credentials should show a validation error.");
    }

    @Test
    public void verifyCommentFieldAcceptsLongTextWithoutTruncation() {
        AppointmentPage appointmentPage = loginAsValidUser();
        String longComment = "Patient requires a detailed follow-up consultation covering prior medication response, "
                + "lifestyle guidance, allergy review, lab-result interpretation, and a longer care-plan discussion.";

        appointmentPage.selectFacility("Hongkong CURA Healthcare Center")
                .setVisitDate(DateUtil.futureDate(7))
                .enterComment(longComment);

        Assert.assertEquals(appointmentPage.getCommentValue(), longComment,
                "Comment field should preserve long text without truncation.");
    }

    @Test
    public void verifyProfilePageLoadsAfterLogin() {
        loginAsValidUser();
        ProfilePage profilePage = new HomePage(driver).openSidebar().goToProfile();

        Assert.assertTrue(profilePage.isLoaded(), "Profile page should load for authenticated users.");
    }

    private AppointmentPage loginAsValidUser() {
        LoginPage loginPage = new HomePage(driver).clickMakeAppointment();
        return loginPage.loginValidUser(ConfigReader.getProperty("valid.username"), ConfigReader.getProperty("valid.password"));
    }
}
