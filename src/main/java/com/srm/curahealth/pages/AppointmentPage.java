
package com.srm.curahealth.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.srm.curahealth.base.BasePage;
import com.srm.curahealth.model.AppointmentData;

public class AppointmentPage extends BasePage {

    private static final By APPOINTMENT_HEADING = By.xpath("//section[@id='appointment']//h2[contains(normalize-space(),'Make Appointment')]");
    private static final By FACILITY_DROPDOWN = By.id("combo_facility");
    private static final By READMISSION_CHECKBOX = By.id("chk_hospotal_readmission");
    private static final By MEDICARE_RADIO = By.id("radio_program_medicare");
    private static final By MEDICAID_RADIO = By.id("radio_program_medicaid");
    private static final By NONE_RADIO = By.id("radio_program_none");
    private static final By VISIT_DATE_INPUT = By.id("txt_visit_date");
    private static final By COMMENT_INPUT = By.id("txt_comment");
    private static final By BOOK_APPOINTMENT_BUTTON = By.id("btn-book-appointment");

    public AppointmentPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(APPOINTMENT_HEADING);
    }

    public AppointmentPage selectFacility(String facility) {
        selectByVisibleText(FACILITY_DROPDOWN, facility);
        return this;
    }

    public AppointmentPage setReadmission(boolean readmission) {
        setCheckbox(READMISSION_CHECKBOX, readmission);
        return this;
    }

    public AppointmentPage selectHealthcareProgram(String program) {
        String normalizedProgram = program.trim().toLowerCase();
        switch (normalizedProgram) {
            case "medicare":
                click(MEDICARE_RADIO);
                break;
            case "medicaid":
                click(MEDICAID_RADIO);
                break;
            default:
                click(NONE_RADIO);
                break;
        }
        return this;
    }

    public AppointmentPage setVisitDate(String visitDate) {
        scrollIntoView(VISIT_DATE_INPUT);
        WebElement visitDateInput = waitForElement(VISIT_DATE_INPUT);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('readonly');"
                        + "arguments[0].value='';"
                        + "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                visitDateInput);

        visitDateInput.click();
        visitDateInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        visitDateInput.sendKeys(visitDate);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
                        + "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
                        + "arguments[0].blur();",
                visitDateInput);

        wait.until(driver -> visitDate.equals(visitDateInput.getAttribute("value")));
        return this;
    }

    public AppointmentPage clearVisitDate() {
        WebElement visitDate = waitForElement(VISIT_DATE_INPUT);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].removeAttribute('readonly');"
                        + "arguments[0].value='';"
                        + "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
                        + "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                visitDate);
        return this;
    }

    public AppointmentPage enterComment(String comment) {
        type(COMMENT_INPUT, comment);
        return this;
    }

    public ConfirmationPage bookAppointment(AppointmentData appointmentData) {
        selectFacility(appointmentData.getFacility());
        setReadmission(appointmentData.isReadmission());
        selectHealthcareProgram(appointmentData.getHealthcareProgram());
        setVisitDate(appointmentData.getVisitDate());
        enterComment(appointmentData.getComment());
        click(BOOK_APPOINTMENT_BUTTON);
        return new ConfirmationPage(driver);
    }

    public AppointmentPage clickBookAppointment() {
        click(BOOK_APPOINTMENT_BUTTON);
        return this;
    }

    public String getVisitDateValidationMessage() {
        return getAttribute(VISIT_DATE_INPUT, "validationMessage");
    }

    public String getVisitDateValue() {
        return getAttribute(VISIT_DATE_INPUT, "value");
    }

    public String getCommentValue() {
        return getAttribute(COMMENT_INPUT, "value");
    }

    public boolean isStillOnAppointmentPage() {
        return driver.getCurrentUrl().contains("#appointment") && isLoaded();
    }
}
