package com.marsair.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page shown after submitting the flight search.
 */
public class SearchResultPage extends BasePage {

    // TODO: narrow to the exact message element once the result page HTML is known
    private final By content = By.id("content");

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public String contentText() {
        return visible(content).getText();
    }
}
