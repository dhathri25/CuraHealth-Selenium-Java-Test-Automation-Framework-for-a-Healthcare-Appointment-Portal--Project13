
package com.srm.curahealth.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.srm.curahealth.base.BasePage;

public class ConfirmationPage extends BasePage {

    private static final By CONFIRMATION_HEADING = By.xpath("//section[@id='summary']//h2[contains(normalize-space(),'Appointment Confirmation')]");
    private static final By FACILITY_VALUE = By.id("facility");
    private static final By READMISSION_VALUE = By.id("hospital_readmission");
    private static final By PROGRAM_VALUE = By.id("program");
    private static final By DATE_VALUE = By.id("visit_date");
    private static final By COMMENT_VALUE = By.id("comment");
    private static final By GO_TO_HOMEPAGE_LINK = By.linkText("Go to Homepage");

    public ConfirmationPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(CONFIRMATION_HEADING);
    }

    public String getFacility() {
        return getText(FACILITY_VALUE);
    }

    public String getReadmissionStatus() {
        return getText(READMISSION_VALUE);
    }

    public String getHealthcareProgram() {
        return getText(PROGRAM_VALUE);
    }

    public String getVisitDate() {
        return getText(DATE_VALUE);
    }

    public String getComment() {
        return getText(COMMENT_VALUE);
    }

    public HomePage goToHomepage() {
        click(GO_TO_HOMEPAGE_LINK);
        return new HomePage(driver);
    }
}
