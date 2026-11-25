****👨🏻‍💻UI and API Test Automation Framework****

🔁Automated UI and API tests with a CI pipeline that runs on every push and publishes a live Allure report.

**📊Live test report:** https://laxmikanthreddyjitta.github.io/ui-api-test-automation-framework/

Just want to see results? Open the live report, no setup needed.

**🛠️Tech stack:**

Language:	Java 17

UI automation:	Selenium WebDriver

API automation:	Rest Assured

Test runner:	TestNG

Build tool:	Maven (Surefire plugin)

Reporting:	Allure Report

CI/CD:	GitHub Actions, GitHub Pages

**☑️What is tested:**

UI tests on automationexercise.com (4 tests)

1) User registration
2) Login with invalid credentials
3) Product search
4) Add product to cart

API tests on restful-api.dev (7 tests)

1) GET all objects
2) POST: create an object
4) GET: fetch the object by id
5) PUT: replace the object
6) PATCH: update part of the object
7) DELETE: remove the object
8) GET: confirm the deleted object returns 404

Tests are grouped in the report by epic, feature and severity using Allure annotations.

**🔗Project structure:**

.github/workflows/tests.yml  :        CI pipeline (GitHub Actions)

src/test/java/com/ecommerce/qa/ui  :  UI tests (Selenium)

src/test/java/com/ecommerce/qa/api : API tests (Rest Assured)

testng.xml                         : Test suite configuration

pom.xml                            : Dependencies and plugin setup

**How it works:**

--> A push to main starts the GitHub Actions workflow.

--> The workflow sets up Java, then runs mvn clean test with Chrome in headless mode.

--> Maven's Surefire plugin runs testng.xml, which runs the UI and API tests.

--> The Allure report is built and published to GitHub Pages, even if some tests fail.

**🎯Challenges solved**

❖ Flaky UI clicks: ads on the demo site covered buttons, so clicks failed in some runs. Fixed by clicking through JavaScript.

❖ Chrome crashing on CI: the CI machine has no screen. Fixed by running Chrome headless when the CI environment variable is set.

❖ Rate-limited public API: the API has a daily limit, a reminder that test stability depends on the system under test as well as the test code.

**🙎Author**

Laxmikanth Reddy Jitta

https://www.linkedin.com/in/laxmikanth-reddy-jitta-12189b1a1/
