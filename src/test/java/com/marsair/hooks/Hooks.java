package com.marsair.hooks;

import com.marsair.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    // Runs after every step; attaches the screenshot right under the step that failed
    @AfterStep
    public void screenshotOnFailedStep(Scenario scenario) {
        if (scenario.isFailed() && DriverFactory.hasDriver()) {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot at failure");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        DriverFactory.quitDriver();
    }
}
