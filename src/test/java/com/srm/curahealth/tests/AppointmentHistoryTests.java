package com.srm.curahealth.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.srm.curahealth.base.BaseTest;
import com.srm.curahealth.model.AppointmentData;
import com.srm.curahealth.pages.AppointmentPage;
import com.srm.curahealth.pages.HistoryPage;
import com.srm.curahealth.pages.HomePage;
import com.srm.curahealth.utils.DateUtil;

public class AppointmentHistoryTests extends BaseTest {

    @Test
    public void verifyAppointmentHistoryPageLoadsAfterLogin() {
        AppointmentPage appointmentPage = openAppointmentPageForValidUser();
        Assert.assertTrue(appointmentPage.isLoaded(), "Appointment page should be displayed after login.");

        HistoryPage historyPage = new HomePage(driver).openSidebar().goToHistory();
        Assert.assertTrue(historyPage.isLoaded(), "History page should load successfully after login.");
    }

    @Test
    public void verifyMostRecentBookedAppointmentAppearsInHistory() {
        AppointmentData appointmentData = new AppointmentData(
                "Hongkong CURA Healthcare Center",
                false,
                "None",
                DateUtil.futureDate(4),
                "History verification scenario.");

        AppointmentPage appointmentPage = openAppointmentPageForValidUser();
        appointmentPage.bookAppointment(appointmentData).goToHomepage();

        HistoryPage historyPage = new HomePage(driver).openSidebar().goToHistory();
        Assert.assertTrue(historyPage.containsAppointment(appointmentData.getFacility(), appointmentData.getVisitDate()),
                "The most recently booked appointment should appear in history.");
    }

    @Test
    public void verifyHistorySectionShowsExpectedHeaders() {
        AppointmentData appointmentData = new AppointmentData(
                "Tokyo CURA Healthcare Center",
                false,
                "Medicare",
                DateUtil.futureDate(6),
                "History detail verification.");

        openAppointmentPageForValidUser().bookAppointment(appointmentData).goToHomepage();
        HistoryPage historyPage = new HomePage(driver).openSidebar().goToHistory();
        String historyContent = historyPage.getHistoryContent();

        Assert.assertTrue(historyContent.contains("History"), "History page heading should be visible.");
        Assert.assertTrue(historyContent.contains(appointmentData.getFacility())
                || historyContent.contains(appointmentData.getVisitDate())
                || historyContent.contains(appointmentData.getComment()),
                "History should expose saved appointment details for the booked record.");
    }
}
