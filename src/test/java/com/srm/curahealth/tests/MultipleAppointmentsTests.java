package com.srm.curahealth.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.srm.curahealth.base.BaseTest;
import com.srm.curahealth.model.AppointmentData;
import com.srm.curahealth.pages.AppointmentPage;
import com.srm.curahealth.pages.HistoryPage;
import com.srm.curahealth.pages.HomePage;
import com.srm.curahealth.utils.DateUtil;

public class MultipleAppointmentsTests extends BaseTest {

    @Test
    public void verifyTwoAppointmentsAppearInHistoryAndAreSortedByDate() {
        AppointmentPage appointmentPage = openAppointmentPageForValidUser();

        AppointmentData firstAppointment = new AppointmentData(
                "Tokyo CURA Healthcare Center",
                false,
                "Medicare",
                DateUtil.futureDate(10),
                "First multi-booking scenario.");

        AppointmentData secondAppointment = new AppointmentData(
                "Seoul CURA Healthcare Center",
                true,
                "Medicaid",
                DateUtil.futureDate(20),
                "Second multi-booking scenario.");

        appointmentPage.bookAppointment(firstAppointment).goToHomepage();
        openAppointmentPageForValidUser().bookAppointment(secondAppointment).goToHomepage();

        HistoryPage historyPage = new HomePage(driver).openSidebar().goToHistory();

        Assert.assertTrue(historyPage.containsAppointment(firstAppointment.getFacility(), firstAppointment.getVisitDate()),
                "History should contain the first appointment.");
        Assert.assertTrue(historyPage.containsAppointment(secondAppointment.getFacility(), secondAppointment.getVisitDate()),
                "History should contain the second appointment.");
        Assert.assertTrue(historyPage.areAppointmentsSortedByDate(),
                "Appointments should appear in a consistent sorted order in history.");
    }
}
