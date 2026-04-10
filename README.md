🚀 **Hybrid Automation Framework (UI & API)**

This is an automation ecosystem designed for System Integration Testing (SIT). It combines the speed of API validation with the end-to-end reliability of UI regression.

🛠 **Tech Stack**

Language: Java 17

BDD: Cucumber (Gherkin)

API Testing: RestAssured + Jackson (POJO Mapping) + JSON Schema Validation

UI Testing: Selenium WebDriver + WebDriverManager

Reporting: Extent Spark Reports + Jenkins Test Trend Analytics

CI/CD: Jenkins (Pipeline-as-Code) & AWS (EC2 Linux)

🏗 **Framework Architecture**

The framework follows the Separation of Concerns principle:

Feature Files: Plain English business requirements.

Step Definitions: Glue code that maps Gherkin steps to Java logic.

API Layer: Abstracted using RequestSpecBuilder and ResponseSpecBuilder for global configuration.

UI Layer: Implements the Page Object Model (POM) for maintainable web testing.

Models/POJOs: Type-safe Java objects mirroring API contracts to prevent brittle testing.

🏃 **How to Run Locally**
1. Prerequisites

   Maven 3.x+ installed.

   JDK 17 configured.

   Chrome Browser (for UI tests).


2. Execution Commands
   
   _Run all tests (API & UI)_:
Bash
mvn clean test -Denv=qa -Dbrowser=chrome -Dheadless=true

   _Run only API Tests_:
Bash
mvn test -Dcucumber.filter.tags="@api"

   _Run only UI Tests_:
Bash
mvn test -Dcucumber.filter.tags="@ui"

📊 **Reporting & Visibility**

Extent Reports: 
After execution, a detailed HTML report is generated at:
target/ExtentReport/SparkReport.html

Jenkins CI/CD: 
This project is fully integrated with a Jenkins Pipeline (Jenkinsfile).

Health Metrics: Includes a "Weather Report" based on build stability.

Trend Analysis: Tracks pass/fail counts over time using JUnit XML parsing.

Artifacts: Automatically archives reports for stakeholder review.

⚙️ **Configuration**

Environment-specific data is managed in src/test/resources/config/:

qa.properties: API endpoints and UI URLs for the QA environment.

dev.properties: Integration endpoints for the Dev environment.