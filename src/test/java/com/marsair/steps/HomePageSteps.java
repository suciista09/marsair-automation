package com.marsair.steps;

import com.marsair.driver.DriverFactory;
import com.marsair.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageSteps {

    private final HomePage homePage = new HomePage(DriverFactory.getDriver());

    @Given("the user is on the MarsAir home page")
    public void theUserIsOnTheHomePage() {
        homePage.open();
    }

    @When("the user clicks {string}")
    public void theUserClicksLink(String linkText) {
        if (!HomePage.BOOK_LINK_TEXT.equals(linkText)) {
            throw new IllegalArgumentException("Unknown link: " + linkText);
        }
        homePage.clickBookTicketLink();
    }

    @Then("the home page with flight search is shown")
    public void theHomePageIsShown() {
        assertTrue(homePage.isFlightSearchShown(), "Flight search is not shown on the home page");
    }
}
