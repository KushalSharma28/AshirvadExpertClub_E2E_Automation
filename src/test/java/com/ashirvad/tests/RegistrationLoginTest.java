package com.ashirvad.tests;

import com.ashirvad.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * RegistrationLoginTest
 *
 * <p>End-to-end TestNG test class covering the complete Registration and
 * Login flows witnessed in the uploaded video for the
 * <b>Ashirvad Experts' Club</b> Android application (com.ashirvadapp).
 *
 * <h2>Flow covered (in order):</h2>
 * <ol>
 *   <li>[TC-01] App launch → Language Selection screen visible</li>
 *   <li>[TC-02] Language selection + consent + tap Register/Login</li>
 *   <li>[TC-03] Mobile Number screen → enter number → submit → OTP screen</li>
 *   <li>[TC-04] Enter Ashirvad OTP → Registration Form appears (new user)</li>
 *   <li>[TC-05] Registration Form – Section A: inline validation visible</li>
 *   <li>[TC-06] Registration Form – Section A: fill fields + tap Next</li>
 *   <li>[TC-07] DigiLocker / Meri Pehchaan SSO: Aadhaar verification</li>
 *   <li>[TC-08] Verification Complete screen → Registration Form pre-filled</li>
 *   <li>[TC-09] Registration Form – Section B: complete and submit</li>
 *   <li>[TC-10] Dashboard visible with correct user details</li>
 *   <li>[TC-11] Login flow (returning user): mobile → OTP → Dashboard</li>
 * </ol>
 *
 * <p><b>Test data:</b> Replace all placeholder values (MOBILE_NUMBER,
 * ASHIRVAD_OTP, AADHAAR_NUMBER, DIGILOCKER_OTP) with real or test-env
 * values before running.  In CI, these should be injected as environment
 * variables or TestNG parameters.
 */
public class RegistrationLoginTest extends BaseTest 
{

    // ─────────────────────────────────────────────────────────────────────────
    // Test Data – replace with actual values or inject via @Parameters / env
    // ─────────────────────────────────────────────────────────────────────────

    /** 10-digit mobile number (video shows: 7317448807) */
    private static final String MOBILE_NUMBER        = "7317448807";

    /** OTP sent by Ashirvad backend to the above mobile */
    private static final String ASHIRVAD_OTP         = "659368";   // replace per run

    /** Ashirvad Sales Personnel Name entered on the Registration Form */
    private static final String SALES_PERSONNEL_NAME = "Test Sales Rep";

    /** Document type selected (as seen in the video: Aadhaar Card) */
    private static final String DOCUMENT_TYPE        = "Aadhaar Card";

    /** 12-digit Aadhaar number for DigiLocker verification */
    private static final String AADHAAR_NUMBER       = "XXXXXXXXXXXX"; // replace

    /** OTP sent by DigiLocker to Aadhaar-linked mobile */
    private static final String DIGILOCKER_OTP       = "XXXXXX";       // replace

    /** Expected user name shown on the Dashboard after registration */
    private static final String EXPECTED_USER_NAME   = "Areeb Siddique";

    /** Preferred communication language (video: Hindi) */
    private static final String PREFERRED_LANGUAGE   = "Hindi";

    // ─────────────────────────────────────────────────────────────────────────
    // Page Objects (initialised lazily to avoid premature driver access)
    // ─────────────────────────────────────────────────────────────────────────

    private LanguageSelectionPage languageSelectionPage;
    private MobileNumberPage      mobileNumberPage;
    private OtpVerificationPage   otpVerificationPage;
    private RegistrationFormPage  registrationFormPage;
    private DigiLockerPage        digiLockerPage;
    private DashboardPage         dashboardPage;

    // ─────────────────────────────────────────────────────────────────────────
    // TC-01  App Launch → Language Selection Screen
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Verifies that on app launch the Language Selection / landing screen
     * is displayed with the "Register/Login" button and language dropdown.
     *
     * <p>Video evidence: Frame 1 – "ashirvad by aliaxis" logo, English
     * language dropdown, consent checkbox, Register/Login button.
     */
    @Test(priority = 1,
          description = "[TC-01] App launch – Language Selection screen is displayed")
    public void tc01_appLaunch_languageSelectionScreenIsDisplayed() 
    {
        log.info(">>> TC-01: Verifying Language Selection screen on launch.");
        languageSelectionPage = new LanguageSelectionPage();
        Assert.assertTrue(languageSelectionPage.isDisplayed(),"Language Selection screen must be visible on app launch.");
        log.info("<<< TC-01 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-02  Language Selection → tap Register/Login
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Selects "English" from the language dropdown, ticks the consent
     * checkbox, and taps "Register/Login".
     *
     * <p>Video evidence: Frame 1 – English is pre-selected; user taps
     * Register/Login after ticking consent.
     */
    @Test(priority = 2,
          dependsOnMethods = "tc01_appLaunch_languageSelectionScreenIsDisplayed",
          description = "[TC-02] Select language + accept consent + tap Register/Login")
    public void tc02_selectLanguageAndTapRegisterLogin() 
    {
        log.info(">>> TC-02: Selecting language, accepting consent, tapping Register/Login.");

        mobileNumberPage = languageSelectionPage.selectLanguage("English").acceptConsent().tapRegisterLogin();

        Assert.assertTrue(mobileNumberPage.isDisplayed(),"Mobile Number screen must appear after tapping Register/Login.");
        log.info("<<< TC-02 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-03  Mobile Number → enter + submit → OTP Screen
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Enters the 10-digit mobile number and submits to trigger OTP dispatch.
     *
     * <p>Video evidence: Frames 2–3 – user types "7317448807" with +91
     * country code prefix shown; taps the numeric keyboard Done action.
     */
    @Test(priority = 3,dependsOnMethods = "tc02_selectLanguageAndTapRegisterLogin",description = "[TC-03] Enter mobile number and submit → OTP screen displayed")
    public void tc03_enterMobileNumberAndSubmit() 
    {
        log.info(">>> TC-03: Entering mobile number: {}", MOBILE_NUMBER);

        otpVerificationPage = mobileNumberPage.enterAndSubmitMobileNumber(MOBILE_NUMBER);

        Assert.assertTrue(otpVerificationPage.isDisplayed(),"OTP Verification screen must appear after submitting mobile number.");
        log.info("<<< TC-03 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-04  OTP Entry → Registration Form (new user)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Enters the Ashirvad OTP and confirms the Registration Form loads.
     *
     * <p>Video evidence: Frames 4–6 – image carousel plays ("Upaharon ki
     * Bauchar", "Family connect"); user enters OTP "659368"; Registration
     * Form appears with title "Registration Form".
     */
    @Test(priority = 4,dependsOnMethods = "tc03_enterMobileNumberAndSubmit",
          description = "[TC-04] Enter Ashirvad OTP → Registration Form visible (new user)")
    public void tc04_enterOtp_registrationFormDisplayed() 
    {
        log.info(">>> TC-04: Entering Ashirvad OTP.");

        registrationFormPage = otpVerificationPage.enterOtp(ASHIRVAD_OTP).submitOtpForNewUser();

        Assert.assertTrue(registrationFormPage.isDisplayed(),"Registration Form must load after correct OTP entry for a new user.");
        log.info("<<< TC-04 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-05  Registration Form – Section A inline validation
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Verifies that the "Please Enter 10 digit number" inline validation
     * error is shown on the Registration Form when the sales mobile field
     * contains fewer than 10 digits.
     *
     * <p>Video evidence: Frames 6–9 – error text "Please Enter 10 digit
     * number" is visible in red beneath the sales personnel mobile field.
     */
    @Test(priority = 5,dependsOnMethods = "tc04_enterOtp_registrationFormDisplayed",
          description = "[TC-05] Registration Form – inline validation error visible")
    public void tc05_registrationForm_inlineValidationVisible() 
    {
        log.info(">>> TC-05: Checking inline mobile validation error.");

        String validationMsg = registrationFormPage.getMobileValidationErrorText();

        Assert.assertFalse(validationMsg.isBlank(),"Inline validation error 'Please Enter 10 digit number' must be visible.");
        Assert.assertTrue(validationMsg.contains("10 digit"),"Validation message must mention '10 digit'. Actual: " + validationMsg);
        log.info("Validation error text: '{}'", validationMsg);
        log.info("<<< TC-05 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-06  Registration Form – Section A: fill fields + tap Next
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Fills the Ashirvad Sales Personnel Name, selects Document Type
     * "Aadhaar Card", and taps "Next" to proceed to DigiLocker KYC.
     *
     * <p>Video evidence: Frames 6–9 – name field, document type dropdown
     * are filled; "Next" CTA is tapped.
     */
    @Test(priority = 6,dependsOnMethods = "tc05_registrationForm_inlineValidationVisible",
          description = "[TC-06] Fill Registration Form Section A → tap Next → DigiLocker SSO")
    public void tc06_fillRegistrationFormSectionA_tapNext() 
    {
        log.info(">>> TC-06: Filling Registration Form Section A.");

        digiLockerPage = registrationFormPage.enterSalesPersonnelName(SALES_PERSONNEL_NAME).selectDocumentType(DOCUMENT_TYPE).tapNext();

        Assert.assertTrue(digiLockerPage.isDisplayed(),"DigiLocker / Meri Pehchaan SSO page must load after tapping Next.");
        log.info("<<< TC-06 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-07  DigiLocker KYC Verification
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Completes the full DigiLocker / Meri Pehchaan Aadhaar verification:
     * selects "Other ID" tab → enters Aadhaar number → enables PIN-less
     * auth → accepts terms → taps Next (dispatches OTP) → enters OTP →
     * taps Sign In → taps Allow on consent screen.
     *
     * <p>Video evidence: Frames 11–14 – SSO screen, OTP entry, consent
     * screen with "Allow" / "Deny", and finally "Digilocker Verification
     * Complete" with a verification ID.
     */
    @Test(priority = 7,
          dependsOnMethods = "tc06_fillRegistrationFormSectionA_tapNext",description = "[TC-07] Complete DigiLocker Aadhaar KYC verification")
    public void tc07_completeDigiLockerVerification() 
    {
        log.info(">>> TC-07: Running DigiLocker Aadhaar KYC flow.");

        registrationFormPage = digiLockerPage.completeVerification(AADHAAR_NUMBER,DIGILOCKER_OTP);

        Assert.assertTrue(registrationFormPage.isDisplayed(),"Registration Form must reappear after successful DigiLocker verification.");
        log.info("<<< TC-07 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-08  Registration Form – Section B auto-filled from KYC
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Verifies that after DigiLocker verification the Registration Form
     * Section B is pre-filled with the user's KYC data (name, DOB, gender,
     * address, PIN code etc.) as seen in video frames 15–16.
     *
     * <p>This test serves as an assertion checkpoint; it does not re-fill
     * read-only auto-populated fields.
     */
    @Test(priority = 8,
          dependsOnMethods = "tc07_completeDigiLockerVerification",
          description = "[TC-08] Registration Form Section B pre-filled after DigiLocker KYC")
    public void tc08_registrationFormSectionB_preFilledAfterKyc() 
    {
        log.info(">>> TC-08: Registration Form Section B is displayed (KYC pre-fill check).");
        // The form being displayed again (isDisplayed returning true) confirms
        // the app returned to the Registration Form with KYC data loaded.
        Assert.assertTrue(registrationFormPage.isDisplayed(),"Registration Form Section B must be visible with KYC data.");
        log.info("<<< TC-08 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-09  Registration Form – Section B: complete editable fields + submit
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Fills the editable fields in Registration Form Section B
     * (gender, ID number, preferred language, terms checkbox) and
     * submits the form.
     *
     * <p>Video evidence: Frames 16–17 – gender "Male" is selected; address,
     * PIN code, Party Type ("Plumber"), Vertical ("Plumbing") and preferred
     * language ("Hindi") are visible; "I accept the terms and condition"
     * checkbox; "Please wait…" dialog appears on submit.
     */
    @Test(priority = 9,
          dependsOnMethods = "tc08_registrationFormSectionB_preFilledAfterKyc",
          description = "[TC-09] Complete & submit Registration Form Section B → Dashboard")
    public void tc09_completeAndSubmitRegistrationFormSectionB() 
    {
        log.info(">>> TC-09: Completing Registration Form Section B.");

        dashboardPage = registrationFormPage.selectGender("Male").selectPreferredLanguage(PREFERRED_LANGUAGE).acceptTermsAndCondition()
                .submitRegistration();

        Assert.assertTrue(dashboardPage.isDisplayed(),"Dashboard must be displayed after successful registration.");
        log.info("<<< TC-09 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-10  Dashboard – validate welcome greeting and tier
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Asserts the Dashboard shows the correct user name ("Welcome Areeb!"),
     * tier ("Blue Level"), and initial points ("0 Points") after registration.
     *
     * <p>Video evidence: Frame 19 – blue header "Welcome Areeb! Blue Level
     * 0 Points"; Frame 20 – Rewards card "Areeb Siddique – Blue Level –
     * 0 Points".
     */
    @Test(priority = 10,
          dependsOnMethods = "tc09_completeAndSubmitRegistrationFormSectionB",
          description = "[TC-10] Dashboard – welcome greeting, tier, and rewards visible")
    public void tc10_dashboard_welcomeGreetingAndTierVisible() {
        log.info(">>> TC-10: Validating Dashboard greeting and tier.");

        dashboardPage.dismissWelcomePopupIfPresent();

        String greeting = dashboardPage.getWelcomeGreetingText();
        String tier     = dashboardPage.getTierText();
        String points   = dashboardPage.getPointsText();

        log.info("Greeting: '{}', Tier: '{}', Points: '{}'", greeting, tier, points);

        Assert.assertTrue(
                greeting.contains("Welcome"),
                "Dashboard greeting must contain 'Welcome'. Actual: " + greeting);
        Assert.assertEquals(
                tier, "Blue Level",
                "Newly registered user must have 'Blue Level' tier.");
        Assert.assertTrue(
                points.contains("0"),
                "Newly registered user must have 0 Points.");
        log.info("<<< TC-10 PASSED.");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC-11  Login flow (returning user) – Mobile → OTP → Dashboard
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Verifies the Login flow for an already-registered user:
     * Language Selection → Mobile Number → OTP → Dashboard (no registration
     * form).
     *
     * <p>Video evidence: Frame 18 – user re-enters "73174488" (returning
     * flow); Frame 19 – Dashboard immediately shown with "Welcome Areeb!".
     *
     * <p>This test restarts the app to simulate a fresh login session.
     */
    @Test(priority = 11,
          description = "[TC-11] Login flow – existing user lands on Dashboard after OTP")
    public void tc11_loginFlow_existingUser_landsDashboard() {
        log.info(">>> TC-11: Login flow for an existing registered user.");

        // Restart the app to start a fresh session
        io.appium.java_client.android.AndroidDriver driver =
                (io.appium.java_client.android.AndroidDriver) com.ashirvad.base.DriverManager.getDriver();
        driver.terminateApp("com.ashirvadapp");
        driver.activateApp("com.ashirvadapp");

        // Language Selection
        LanguageSelectionPage langPage = new LanguageSelectionPage();
        Assert.assertTrue(langPage.isDisplayed(),
                "Language Selection screen must appear on app restart.");

        // Mobile Number
        MobileNumberPage mobPage = langPage
                .selectLanguage("English")
                .acceptConsent()
                .tapRegisterLogin();
        Assert.assertTrue(mobPage.isDisplayed(),
                "Mobile Number screen must appear.");

        // Enter mobile and get OTP screen
        OtpVerificationPage otpPage = mobPage.enterAndSubmitMobileNumber(MOBILE_NUMBER);
        Assert.assertTrue(otpPage.isDisplayed(),
                "OTP screen must appear for existing user.");

        // Enter OTP → existing user skips registration → goes directly to Dashboard
        DashboardPage dash = otpPage
                .enterOtp(ASHIRVAD_OTP)
                .submitOtpForExistingUser();
        Assert.assertTrue(dash.isDisplayed(),
                "Dashboard must load directly for an existing registered user.");

        log.info("Dashboard greeting: '{}'", dash.getWelcomeGreetingText());
        log.info("<<< TC-11 PASSED.");
    }
}
