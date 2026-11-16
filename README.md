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

Reports (in `target/cucumber-reports/`):
- `cucumber.html`: single-file HTML report, with generated codes and failure screenshots
- `cucumber.json`: for Cucumber reporting tools and CI plugins
- `cucumber.xml`: JUnit XML, which most CI tools read for test results

## Run in parallel on Selenium Grid

Scenarios run in parallel (3 threads, set in `src/test/resources/junit-platform.properties`). Each thread gets its own browser.

1. Start the Grid (Docker Desktop must be running): 1 hub + 3 Chrome nodes.

```
docker compose up -d
```

2. Open http://localhost:4444/ui to watch the sessions.
3. Run the tests on the Grid:

```
mvn clean test -Dgrid.url=http://localhost:4444
```

4. Stop the Grid:

```
docker compose down
```

Without `-Dgrid.url`, tests run on local Chrome (still in parallel).

Change the number of threads, or turn parallel off:

```
mvn clean test -Dcucumber.execution.parallel.config.fixed.parallelism=2
mvn clean test -Dcucumber.execution.parallel.enabled=false
```

Keep the thread count the same as or lower than the number of Chrome nodes (`replicas` in `docker-compose.yml`).
