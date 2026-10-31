package com.marsair.steps;

import com.marsair.driver.DriverFactory;
import com.marsair.pages.SearchFormPage;
import com.marsair.pages.SearchResultPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchSteps {

    private final SearchFormPage searchForm = new SearchFormPage(DriverFactory.getDriver());
    private SearchResultPage searchResult;

    @Given("the user is on the search page")
    public void theUserIsOnTheSearchPage() {
        searchForm.open();
    }

    @When("the user searches with the departing {string} and returning {string} dates")
    public void theUserSearchesWithDates(String departing, String returning) {
        searchResult = searchForm
                .selectDeparting(departing)
                .selectReturning(returning)
                .search();
    }

    @When("the user searches from {string} to {string} with promotional code {string}")
    public void theUserSearchesWithPromotionalCode(String departing, String returning, String code) {
        searchResult = searchForm
                .selectDeparting(departing)
                .selectReturning(returning)
                .enterPromotionalCode(code)
                .search();
    }

    @Then("the result message contains {string}")
    public void theResultMessageContains(String expected) {
        String actual = searchResult.contentText();
        assertTrue(actual.contains(expected),
                "Expected result to contain \"" + expected + "\" but page showed: " + actual);
    }

    @Then("the availability message {string} is shown")
    public void theAvailabilityMessageIsShown(String message) {
        String actual = searchResult.contentText();
        assertTrue(actual.contains(message),
                "Expected message \"" + message + "\" but page showed: " + actual);
    }
}
