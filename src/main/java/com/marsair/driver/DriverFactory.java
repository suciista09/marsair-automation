package com.marsair.driver;

import com.marsair.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

/**
 * Creates and holds one browser per thread, so scenarios can run in parallel.
 * - grid.url empty: local ChromeDriver (Selenium Manager downloads the matching driver).
 * - grid.url set:   RemoteWebDriver on Selenium Grid, e.g. http://localhost:4444
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            DRIVER.set(createDriver());
        }
        return DRIVER.get();
    }

    private static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        if (Config.headless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1366,768");

        String gridUrl = Config.gridUrl();
        if (gridUrl.isBlank()) {
            return new ChromeDriver(options);
        }
        try {
            return new RemoteWebDriver(URI.create(gridUrl).toURL(), options);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid grid.url: " + gridUrl, e);
        }
    }

    public static boolean hasDriver() {
        return DRIVER.get() != null;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
