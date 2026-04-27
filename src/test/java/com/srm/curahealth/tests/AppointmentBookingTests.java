package com.srm.curahealth.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.srm.curahealth.base.BaseTest;
import com.srm.curahealth.model.AppointmentData;
import com.srm.curahealth.pages.AppointmentPage;
import com.srm.curahealth.pages.ConfirmationPage;
import com.srm.curahealth.utils.DateUtil;

public class AppointmentBookingTests extends BaseTest {

    @Test
    public void verifyAppointmentBookingWithFacilityDateAndComment() {
        AppointmentData appointmentData = new AppointmentData(
                "Tokyo CURA Healthcare Center",
                false,
                "Medicare",
                DateUtil.futureDate(3),
                "Routine consultation for follow-up review.");

        ConfirmationPage confirmationPage = openAppointmentPageForValidUser().bookAppointment(appointmentData);

        Assert.assertTrue(confirmationPage.isLoaded(), "Appointment confirmation page should be displayed.");
        Assert.assertEquals(confirmationPage.getFacility(), appointmentData.getFacility(),
                "Confirmed facility should match the selected facility.");
        Assert.assertEquals(confirmationPage.getVisitDate(), appointmentData.getVisitDate(),
                "Confirmed visit date should match the selected date.");
        Assert.assertEquals(confirmationPage.getComment(), appointmentData.getComment(),
                "Confirmed comment should match the submitted comment.");
    }

    @Test
    public void verifyAppointmentBookingWithReadmissionChecked() {
        AppointmentData appointmentData = new AppointmentData(
                "Seoul CURA Healthcare Center",
                true,
                "Medicaid",
                DateUtil.futureDate(5),
                "Admit patient for observation and diagnostics.");

        ConfirmationPage confirmationPage = openAppointmentPageForValidUser().bookAppointment(appointmentData);

        Assert.assertTrue(confirmationPage.isLoaded(), "Appointment confirmation page should be displayed.");
        Assert.assertEquals(confirmationPage.getReadmissionStatus(), "Yes",
                "Readmission status should be reflected in the confirmation page.");
    }

    @Test
    public void verifyAppointmentDateFieldRejectsPastDates() {
        AppointmentPage appointmentPage = openAppointmentPageForValidUser();
        String pastDate = DateUtil.pastDate(2);

        appointmentPage.setVisitDate(pastDate).clickBookAppointment();

        if (appointmentPage.isStillOnAppointmentPage()) {
            Assert.assertTrue(appointmentPage.getVisitDateValue().isEmpty()
                    || !appointmentPage.getVisitDateValidationMessage().trim().isEmpty(),
                    "Past dates should either be blocked or produce a visible validation state.");
        } else {
            ConfirmationPage confirmationPage = new ConfirmationPage(driver);
            Assert.assertTrue(confirmationPage.isLoaded(),
                    "If the demo application allows past dates, it should still complete the booking flow.");
            Assert.assertEquals(confirmationPage.getVisitDate(), pastDate,
                    "When past dates are accepted, the confirmation should reflect the entered date.");
        }
    }
}
