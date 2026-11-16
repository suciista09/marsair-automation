package com.marsair.pages;

import com.marsair.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * MarsAir home page (flight search).
 * Locators other than the AC link text are placeholders: confirm them in Chrome DevTools.
 */
public class HomePage extends BasePage {

    // Text taken from story #3 acceptance criteria
    public static final String BOOK_LINK_TEXT = "Book a ticket to the red planet now!";

    private final By bookTicketLink = By.linkText(BOOK_LINK_TEXT);
    // TODO: confirm in DevTools
    private final By logo = By.cssSelector("header a img, .logo, #logo");
    // TODO: confirm in DevTools
    private final By departingDropdown = By.id("departing");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Config.baseUrl());
        return this;
    }

    public boolean isBookTicketLinkVisible() {
        return visible(bookTicketLink).isDisplayed();
    }

    public void clickBookTicketLink() {
        click(bookTicketLink);
    }

    public void clickLogo() {
        click(logo);
    }

    public boolean isFlightSearchShown() {
        return visible(departingDropdown).isDisplayed();
    }
}
