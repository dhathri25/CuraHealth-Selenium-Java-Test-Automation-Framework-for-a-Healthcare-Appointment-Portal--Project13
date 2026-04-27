package com.srm.curahealth.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.srm.curahealth.base.BasePage;

public class LoginPage extends BasePage {

    private static final By USERNAME_INPUT = By.id("txt-username");
    private static final By PASSWORD_INPUT = By.id("txt-password");
    private static final By LOGIN_BUTTON = By.id("btn-login");
    private static final By ERROR_MESSAGE = By.cssSelector(".text-danger");
    private static final By LOGIN_HEADING = By.xpath("//section[@id='login']//h2[normalize-space()='Login']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(LOGIN_HEADING);
    }

    public LoginPage enterUsername(String username) {
        type(USERNAME_INPUT, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(PASSWORD_INPUT, password);
        return this;
    }

    public AppointmentPage loginValidUser(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(LOGIN_BUTTON);
        return new AppointmentPage(driver);
    }

    public LoginPage loginInvalidUser(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(LOGIN_BUTTON);
        return this;
    }

    public LoginPage submitEmptyLogin() {
        click(LOGIN_BUTTON);
        return this;
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }
}
