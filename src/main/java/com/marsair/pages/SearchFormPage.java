package com.marsair.pages;

import com.marsair.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Flight search form on the MarsAir home page.
 */
public class SearchFormPage extends BasePage {

    private final By departingDropdown = By.id("departing");
    private final By returningDropdown = By.id("returning");
    private final By promotionalCodeInput = By.id("promotional_code");
    private final By searchButton = By.cssSelector("input[type='submit'][value='Search']");

    public SearchFormPage(WebDriver driver) {
        super(driver);
    }

    public SearchFormPage open() {
        driver.get(Config.baseUrl());
        visible(departingDropdown);
        return this;
    }

    public SearchFormPage selectDeparting(String optionText) {
        new Select(visible(departingDropdown)).selectByVisibleText(optionText);
        return this;
    }

    public SearchFormPage selectReturning(String optionText) {
        new Select(visible(returningDropdown)).selectByVisibleText(optionText);
        return this;
    }

    public SearchFormPage enterPromotionalCode(String code) {
        visible(promotionalCodeInput).clear();
        visible(promotionalCodeInput).sendKeys(code);
        return this;
    }

    public SearchResultPage search() {
        // Wait for the form page to be replaced, so the result page isn't read from the old DOM
        WebElement form = visible(departingDropdown);
        click(searchButton);
        wait.until(ExpectedConditions.stalenessOf(form));
        return new SearchResultPage(driver);
    }
}
