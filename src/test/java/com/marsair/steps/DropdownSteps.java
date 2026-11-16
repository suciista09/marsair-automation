package com.marsair.steps;

import com.marsair.driver.DriverFactory;
import com.marsair.pages.SearchFormPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DropdownSteps {

    private final SearchFormPage searchForm = new SearchFormPage(DriverFactory.getDriver());
    private List<String> options;

    @When("the user opens the {string} dropdown")
    public void theUserOpensTheDropdown(String dropdown) {
        options = switch (dropdown) {
            case "departing" -> searchForm.departingOptions();
            case "returning" -> searchForm.returningOptions();
            default -> throw new IllegalArgumentException("Unknown dropdown: " + dropdown);
        };
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
