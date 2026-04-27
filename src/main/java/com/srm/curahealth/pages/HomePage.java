
package com.srm.curahealth.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.srm.curahealth.base.BasePage;
import com.srm.curahealth.utils.ConfigReader;

public class HomePage extends BasePage {

    private static final By MAKE_APPOINTMENT_BUTTON = By.id("btn-make-appointment");
    private static final By MENU_TOGGLE = By.id("menu-toggle");
    private static final By HOMEPAGE_HEADING = By.xpath("//h1[contains(normalize-space(),'CURA Healthcare Service')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(HOMEPAGE_HEADING);
    }

    public LoginPage clickMakeAppointment() {
        click(MAKE_APPOINTMENT_BUTTON);
        return new LoginPage(driver);
    }

    public SidebarPage openSidebar() {
        click(MENU_TOGGLE);
        return new SidebarPage(driver);
    }

    public LoginPage openProtectedAppointmentPage() {
        driver.get(ConfigReader.getBaseUrl() + "appointment.php#appointment");
        return new LoginPage(driver);
    }
}
