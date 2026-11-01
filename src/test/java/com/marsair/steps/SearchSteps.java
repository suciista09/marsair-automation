package com.marsair.steps;

import com.marsair.driver.DriverFactory;
import com.marsair.pages.SearchFormPage;
import com.marsair.pages.SearchResultPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchSteps {

    private final SearchFormPage searchForm = new SearchFormPage(DriverFactory.getDriver());
    private SearchResultPage searchResult;
    private static final String SCHEDULE_ERROR = "Unfortunately, this schedule is not possible. Please try again.";
    private List<String> options;

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

    @When("the user opens the {string} dropdown")
    public void theUserOpensTheDropdown(String dropdown) {
        options = switch (dropdown) {
            case "departing" -> searchForm.departingOptions();
            case "returning" -> searchForm.returningOptions();
            default -> throw new IllegalArgumentException("Unknown dropdown: " + dropdown);
        };
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

    @Then("the schedule error message is not shown")
    public void theScheduleErrorIsNotShown() {
        String actual = searchResult.contentText();
        assertFalse(actual.contains(SCHEDULE_ERROR),
                "Schedule error should not be shown but page showed: " + actual);
    }

    @Then("the seat availability result is shown")
    public void theSeatAvailabilityResultIsShown() {
        String actual = searchResult.contentText();
        assertTrue(actual.contains("Seats available!") || actual.contains("Sorry, there are no more seats available."),
                "Expected a seat availability result but page showed: " + actual);
    }

    @Then("it only shows July and December options")
    public void itOnlyShowsJulyAndDecember() {
        assertTrue(!options.isEmpty(), "Dropdown has no options");
        for (String option : options) {
            assertTrue(option.startsWith("July") || option.startsWith("December"),
                    "Unexpected month in option \"" + option + "\". All options: " + options);
        }
    }

    @Then("it covers the next two years:")
    public void itCoversTheNextTwoYears(List<String> expectedOptions) {
        for (String expected : expectedOptions) {
            assertTrue(options.contains(expected),
                    "Missing option \"" + expected + "\". All options: " + options);
        }
    }

    @Then("the options are in this order:")
    public void theOptionsAreInThisOrder(List<String> expectedOrder) {
        assertEquals(expectedOrder, options, "Dropdown options are not in the expected order");
    }
}
