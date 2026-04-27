# CuraHealth Automation Framework

## Project Overview
CuraHealth is a robust Selenium-based Test Automation Framework built using Java, TestNG, and Maven to automate end-to-end testing of the CuraHealth web application. 

This project follows industry best practices including the Page Object Model (POM), reusable utilities, and structured reporting to ensure a scalable and maintainable automation suite.

---

## Features
* Page Object Model (POM) architecture
* TestNG-based test execution and management
* Extent Reports integration for detailed results
* Automated screenshot capture on test failure
* Centralized configuration management via properties files
* Reusable utilities including DriverFactory and ConfigReader
* Data-driven testing support via TestNG DataProviders
* Modular project structure with clean separation of concerns

---

## Tech Stack
* Programming Language: Java
* Browser Automation: Selenium WebDriver
* Test Framework: TestNG
* Build Tool: Maven
* Test Reporting: Extent Reports

---

## Project Structure
```text
CuraHealth/
├── pom.xml                         # Maven dependencies and build configuration
├── reports/                        # Test execution reports (HTML)
├── screenshots/                    # Failure screenshots with timestamps
├── src/
│   ├── main/
│   │   ├── java/com/srm/curahealth/
│   │   │   ├── base/               # Global BasePage for common actions
│   │   │   ├── constants/          # Framework-wide constant values
│   │   │   ├── listeners/          # TestNG Listeners for reporting/screenshots
│   │   │   ├── model/              # Data models for appointments
│   │   │   ├── pages/              # Encapsulated Page Object classes
│   │   │   └── utils/              # Configuration, Driver, and File utilities
│   │   └── resources/
│   │       └── config.properties   # Environment configurations
│   └── test/
│       ├── java/com/srm/curahealth/
│       │   ├── base/               # BaseTest for setup and teardown
│       │   └── tests/              # Functional test scenarios
└── testng.xml                      # Test suite execution file
```

---

## Setup Instructions

### 1. Clone the Repository
```bash
git clone https://github.com/dhathri25/CuraHealth-Selenium-Java-Automation-Project.git
cd CuraHealth
```

### 2. Configuration
Modify settings in `src/main/resources/config.properties`:
* browser: chrome, edge, or firefox
* baseUrl: application URL
* timeout: explicit wait values

---

## Running Tests
Tests can be executed through the following methods:

### Via IDE (Eclipse/IntelliJ)
* Right-click `testng.xml`
* Select Run As -> TestNG Suite

### Via Maven Command Line
```bash
mvn clean test
```

---

## Test Scenarios Covered
* Authentication: Validation of secure login and logout workflows.
* Appointment Booking: End-to-end flow of scheduling a healthcare appointment.
* Appointment History: Verification of previously booked appointments.
* Form Validation: Ensuring mandatory fields and data formats are enforced.
* Multiple Appointments: Handling and verifying sequential booking sessions.

---

## Reporting and Artifacts
* Test Reports: Extent Reports are generated in the `/reports/` folder. Open the `.html` file in a browser for a graphical representation of the pass/fail status and logs.
* Screenshots: Automatically captured upon test failure and saved in the `/screenshots/` directory for debugging.

---

## Author
Dhathri Putty