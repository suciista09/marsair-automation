package com.marsair.steps;

import com.marsair.data.PromoCodeGenerator;
import com.marsair.driver.DriverFactory;
import com.marsair.pages.SearchFormPage;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Data-driven promo code checks: codes are generated at runtime instead of hard-coded.
 * Every generated code is written to the Cucumber report so a failure can be reproduced.
 */
public class DynamicPromoCodeSteps {

    private final SearchFormPage searchForm = new SearchFormPage(DriverFactory.getDriver());
    // generated code -> page text after searching with it
    private final Map<String, String> results = new LinkedHashMap<>();
    private Scenario scenario;

    @Before
    public void captureScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @When("the user searches from {string} to {string} with {int} generated valid promotional codes")
    public void searchWithGeneratedValidCodes(String departing, String returning, int count) {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            codes.add(PromoCodeGenerator.validCode());
        }
        searchWithEach(departing, returning, codes);
    }

    @When("the user searches from {string} to {string} with {int} generated invalid promotional codes")
    public void searchWithGeneratedInvalidCodes(String departing, String returning, int count) {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // rotate through the three kinds of invalid code
            switch (i % 3) {
                case 0 -> codes.add(PromoCodeGenerator.codeWithWrongCheckDigit());
                case 1 -> codes.add(PromoCodeGenerator.codeWithZeroDiscount());
                default -> codes.add(PromoCodeGenerator.codeWithoutDashes());
            }
        }
        searchWithEach(departing, returning, codes);
    }

    @Then("each result shows the promotional code used with its discount")
    public void eachResultShowsDiscount() {
        assertAll(results.entrySet().stream().map(entry -> () -> {
            String code = entry.getKey();
            String expected = String.format("Promotional code %s used: %d%% discount!",
                    code, PromoCodeGenerator.discountOf(code));
            assertTrue(entry.getValue().contains(expected),
                    "Code " + code + ": expected \"" + expected + "\" but page showed: " + entry.getValue());
        }));
    }

    @Then("each result shows the promotional code is not valid")
    public void eachResultShowsNotValid() {
        assertAll(results.entrySet().stream().map(entry -> () -> {
            String code = entry.getKey();
            String expected = String.format("Sorry, code %s is not valid", code);
            assertTrue(entry.getValue().contains(expected),
                    "Code " + code + ": expected \"" + expected + "\" but page showed: " + entry.getValue());
        }));
    }

    private void searchWithEach(String departing, String returning, List<String> codes) {
        for (String code : codes) {
            scenario.log("Generated code: " + code + " (oracle says valid: " + PromoCodeGenerator.isValid(code) + ")");
            String pageText = searchForm.open()
                    .selectDeparting(departing)
                    .selectReturning(returning)
                    .enterPromotionalCode(code)
                    .search()
                    .contentText();
            results.put(code, pageText);
        }
    }
}
