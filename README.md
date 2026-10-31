# MarsAir test automation

UI test automation for the MarsAir flight booking app.

**Stack:** Java 17 · Selenium WebDriver 4 · Chrome · Cucumber (BDD) · JUnit 5 · Maven

## Project structure

```
src/main/java/com/marsair/
  config/Config.java          reads config.properties (overridable with -D)
  driver/DriverFactory.java   creates and quits ChromeDriver
  pages/BasePage.java         shared waits and helpers
  pages/HomePage.java         home page (flight search) page object
src/main/resources/
  config.properties           base URL, headless, timeout
src/test/java/com/marsair/
  steps/                      step definitions (glue code)
  hooks/Hooks.java            screenshot on failure, quit browser
  runner/RunCucumberTest.java JUnit 5 Cucumber runner
src/test/resources/features/  feature files (Gherkin)
```

## Prerequisites

- JDK 17 or newer
- Maven 3.9+
- Google Chrome (Selenium Manager downloads the matching ChromeDriver automatically)

## Run

1. Set `base.url` in `src/main/resources/config.properties` to the MarsAir URL.
2. Run all tests:

```
mvn clean test
```

Headless, or with another URL:

```
mvn clean test -Dheadless=true -Dbase.url=https://...
```

Report: `target/cucumber-report.html`
