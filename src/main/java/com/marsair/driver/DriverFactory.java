package com.marsair.driver;

import com.marsair.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Creates and holds one ChromeDriver per thread.
 * Selenium Manager (built into Selenium 4) downloads the ChromeDriver that matches the installed Chrome.
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            ChromeOptions options = new ChromeOptions();
            if (Config.headless()) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--window-size=1366,768");
            DRIVER.set(new ChromeDriver(options));
        }
        return DRIVER.get();
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
