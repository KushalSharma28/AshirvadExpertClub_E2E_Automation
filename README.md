# Ashirvad Experts' Club - Mobile Automation Framework

![Java](https://img.shields.io/badge/Java-25.0.3-orange.svg)
![Appium](https://img.shields.io/badge/Appium-3.4.2-blue.svg)
![TestNG](https://img.shields.io/badge/TestNG-7.12.0-green.svg)
![Maven](https://img.shields.io/badge/Maven-Build-3.9-red.svg)

## 📌 Project Overview
This repository contains a robust, production-ready mobile test automation framework for the **Ashirvad Experts' Club** Android application. Built with modern engineering practices, it leverages **Appium 2.x** and **Java 17** to execute end-to-end (E2E) registration and login workflows. 

The framework is designed to emphasize **problem-solving impacts, high maintainability, and senior-level architectural patterns**, serving as a blueprint for scalable mobile test automation.

## 🚀 Key Architectural Impacts & Problem Solving
* **Modern Appium 2.x Standards:** Fully migrated away from deprecated `DesiredCapabilities`, utilizing strict `UiAutomator2Options` for secure and reliable session initialization.
* **Scalable Page Object Model (POM):** UI locators and business logic are strictly decoupled across `BasePage`, `AuthFlowPage`, and `DashboardPage`, significantly reducing code duplication and maintenance overhead.
* **Dynamic Element Handling:** Zero reliance on implicit waits. Implemented robust `WebDriverWait` wrapper methods to safely handle dynamic mobile UI rendering and variable network latency.
* **Complex Flow Navigation:** Successfully handles intricate multi-step workflows, including form submissions, dropdown selections, and navigating 3rd-party contexts (e.g., DigiLocker integration flows).

## 🛠️ Technology Stack
* **Language:** Java 25.0.3
* **Automation Engine:** Appium Java Client (v10.1.1)
* **Underlying API:** Selenium 4
* **Test Runner:** TestNG (v7.12.0)
* **Build Tool:** Maven (3.9)

## 📂 Project Structure
```text
ashirvad-mobile-automation/
├── pom.xml                     # Maven dependencies and build configuration
└── src/
    ├── main/java/
    │   └── com/ashirvad/pages/       
    │       ├── BasePage.java       # Core wait/interaction utilities
    │       ├── AuthFlowPage.java   # Registration & Login locators/methods
    │       └── DashboardPage.java  # Post-login validation logic
    └── test/java/
        └── com/ashirvad/tests/       
            ├── BaseTest.java       # Driver lifecycle & UiAutomator2 setup
            └── AshirvadE2ETest.java# Test execution sequence
```

```text
ashirvad-appium/
├── pom.xml
└── src/
    ├── main/java/com/ashirvad/
    │   ├── base/
    │   │   ├── AppiumConfig.java        ← UiAutomator2Options driver factory
    │   │   └── DriverManager.java       ← ThreadLocal<AndroidDriver> holder
    │   ├── pages/
    │   │   ├── LanguageSelectionPage.java
    │   │   ├── MobileNumberPage.java
    │   │   ├── OtpVerificationPage.java
    │   │   ├── RegistrationFormPage.java
    │   │   ├── DigiLockerPage.java      ← WebView + native context switching
    │   │   └── DashboardPage.java
    │   └── utils/
    │       └── WaitUtils.java           ← All explicit waits, zero implicit
    └── test/
        ├── java/com/ashirvad/tests/
        │   ├── BaseTest.java            ← @BeforeClass / @AfterClass
        │   └── RegistrationLoginTest.java ← 11 @Test methods, priority-ordered
        └── resources/
            ├── testng.xml
            └── logback-test.xml
```            