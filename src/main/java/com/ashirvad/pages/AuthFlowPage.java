package com.ashirvad.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthFlowPage 
{
    private AndroidDriver driver;
    private WebDriverWait wait;

    // Locators - NOTE: Replace these placeholder locators with actual IDs/XPaths from Appium Inspector
    private final AppiumBy languageDropdown = new AppiumBy.ByAccessibilityId("language_dropdown");
    private final AppiumBy consentCheckbox = new AppiumBy.ByAccessibilityId("consent_checkbox");
    private final AppiumBy registerLoginBtn = new AppiumBy.ByAccessibilityId("btn_register_login");
    private final AppiumBy mobileNumberInput = new AppiumBy.ByXPath("//android.widget.EditText[contains(@text, '+91')]");
    private final AppiumBy nextArrowBtn = new AppiumBy.ByAccessibilityId("btn_next");
    
    // Party Type Selection
    private final AppiumBy partyTypeDropdown = new AppiumBy.ByAccessibilityId("dropdown_party_type");
    private final AppiumBy partyTypePlumber = new AppiumBy.ByXPath("//android.widget.TextView[@text='Plumber']");
    
    // OTP Screen
    private final AppiumBy otpInput = new AppiumBy.ByAccessibilityId("input_otp");
    
    // Registration Form
    private final AppiumBy salesPersonnelMobileInput = new AppiumBy.ByAccessibilityId("input_sales_mobile");
    private final AppiumBy nameInput = new AppiumBy.ByAccessibilityId("input_name");
    private final AppiumBy docTypeDropdown = new AppiumBy.ByAccessibilityId("dropdown_doc_type");
    private final AppiumBy aadharOption = new AppiumBy.ByXPath("//android.widget.TextView[@text='Aadhar Card']");
    private final AppiumBy formNextBtn = new AppiumBy.ByAccessibilityId("btn_form_next");
    
    // DigiLocker Context
    private final AppiumBy digiLockerAadharInput = new AppiumBy.ByXPath("//android.widget.EditText[@resource-id='aadhaar_input']");
    private final AppiumBy digiLockerSignInBtn = new AppiumBy.ByXPath("//android.widget.Button[@text='Sign In']");
    private final AppiumBy digiLockerAllowBtn = new AppiumBy.ByXPath("//android.widget.Button[@text='Allow']");
    
    // Final Submission
    private final AppiumBy submitBtn = new AppiumBy.ByAccessibilityId("btn_submit");
    private final AppiumBy successOkayBtn = new AppiumBy.ByXPath("//android.widget.Button[@text='Okay']");
    private final AppiumBy dashboardWelcomeText = new AppiumBy.ByXPath("//android.widget.TextView[contains(@text, 'Welcome')]");

    public AuthFlowPage(AndroidDriver driver) 
    {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void selectLanguageAndConsent(String language) 
    {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(languageDropdown)).click();
            // Assuming default is English as per video, tapping outside or selecting
            wait.until(ExpectedConditions.elementToBeClickable(consentCheckbox)).click();
            wait.until(ExpectedConditions.elementToBeClickable(registerLoginBtn)).click();
        } catch (Exception e) {
            System.err.println("Failed at Language Selection and Consent: " + e.getMessage());
        }
    }

    public void enterMobileNumber(String mobileNumber) 
    {
        WebElement mobileField = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNumberInput));
        mobileField.clear();
        mobileField.sendKeys(mobileNumber);
        driver.findElement(nextArrowBtn).click();
    }

    public void selectPartyType(String partyType) 
    {
        wait.until(ExpectedConditions.elementToBeClickable(partyTypeDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(partyTypePlumber)).click();
        driver.findElement(nextArrowBtn).click();
    }

    public void enterOTP(String otp) 
    {
        // In a production environment, OTPs are often bypassed in lower environments 
        // or fetched via an API/DB. Here we enter the static one from the video.
        WebElement otpField = wait.until(ExpectedConditions.visibilityOfElementLocated(otpInput));
        otpField.sendKeys(otp);
    }

    public void fillInitialRegistrationForm(String salesMobile, String name) 
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(salesPersonnelMobileInput)).sendKeys(salesMobile);
        driver.findElement(nameInput).sendKeys(name);
        
        driver.findElement(docTypeDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(aadharOption)).click();
        
        driver.findElement(formNextBtn).click();
    }

    public void handleDigiLockerVerification(String aadharNumber, String otp) 
    {
        // Note: DigiLocker may open in a WebView or secure context. 
        // If it's a WebView, context switching might be required: driver.context("WEBVIEW_...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(digiLockerAadharInput)).sendKeys(aadharNumber);
        driver.findElement(digiLockerSignInBtn).click();
        
        // Handling OTP for DigiLocker
        WebElement digiOtpField = wait.until(ExpectedConditions.visibilityOfElementLocated(otpInput)); // Reusing OTP locator logic
        digiOtpField.sendKeys(otp);
        driver.findElement(digiLockerSignInBtn).click();
        
        wait.until(ExpectedConditions.elementToBeClickable(digiLockerAllowBtn)).click();
    }

    public void submitRegistration() 
    {
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(successOkayBtn)).click();
    }

    public boolean isDashboardDisplayed() 
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardWelcomeText)).isDisplayed();
    }
}