
package com.srm.curahealth.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.srm.curahealth.base.BasePage;

public class SidebarPage extends BasePage {

    private static final By SIDEBAR = By.id("sidebar-wrapper");

    public SidebarPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOpen() {
        return isDisplayed(SIDEBAR);
    }

    public HistoryPage goToHistory() {
        click(By.xpath("//nav[@id='sidebar-wrapper']//a[contains(@href,'history.php#history')]"));
        return new HistoryPage(driver);
    }

    public ProfilePage goToProfile() {
        click(By.xpath("//nav[@id='sidebar-wrapper']//a[contains(@href,'profile.php#profile')]"));
        return new ProfilePage(driver);
    }

    public HomePage logout() {
        click(By.xpath("//nav[@id='sidebar-wrapper']//a[contains(@href,'authenticate.php?logout')]"));
        return new HomePage(driver);
    }
}
