
package com.srm.curahealth.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.srm.curahealth.base.BasePage;

public class ProfilePage extends BasePage {

    private static final By PROFILE_HEADING = By.xpath("//section[@id='profile']//h2[contains(normalize-space(),'Profile') or contains(normalize-space(),'CURA Healthcare')]");
    private static final By PROFILE_SECTION = By.id("profile");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForUrlContains("#profile");
        return isDisplayed(PROFILE_SECTION) || isDisplayed(PROFILE_HEADING);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
