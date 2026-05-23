package com.ashirvad.pages;

import com.ashirvad.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object: Registration Form Screen
 *
 * <p>Screen observed in video frames 6–9 and 15–17.
 * The form is split into two logical sections visible in the video:
 *
 * <h3>Section A – Initial fields (frames 6–9):</h3>
 * <ul>
 *   <li>Ashirvad Sales Personnel Mobile Number* (read-only, pre-filled)</li>
 *   <li>Validation error: "Please Enter 10 digit number" (inline)</li>
 *   <li>Ashirvad Sales Personnel Name* (text input)</li>
 *   <li>Select Document Type* (spinner / dropdown)</li>
 *   <li>"Next" button (disabled until fields are valid)</li>
 * </ul>
 *
 * <h3>Section B – Post DigiLocker verification (frames 15–17):</h3>
 * <ul>
 *   <li>Contact Number* (pre-filled, read-only)</li>
 *   <li>Select Document Type (auto-filled "Aadhaar Card")</li>
 *   <li>Name* (auto-filled from DigiLocker)</li>
 *   <li>Date of Birth* (auto-filled)</li>
 *   <li>Gender* (dropdown, default "Male")</li>
 *   <li>Enter ID Number*</li>
 *   <li>Permanent Address → Address*, PIN Code*, State*, City*, District*</li>
 *   <li>PIN Code*</li>
 *   <li>Party Type* (pre-filled "Plumber")</li>
 *   <li>Vertical* (pre-filled "Plumbing")</li>
 *   <li>Preferred language for communication* (dropdown, "Hindi")</li>
 *   <li>Club Name* (pre-filled "General")</li>
 *   <li>Referral Code (optional)</li>
 *   <li>Terms &amp; Condition checkbox</li>
 *   <li>Submit button</li>
 * </ul>
 */
public class RegistrationFormPage {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationFormPage.class);

    // ─────────────────────────────────────────────────────────────────────────
    // Section A Locators
    // ─────────────────────────────────────────────────────────────────────────

    /** "Registration Form" toolbar title */
    private final By registrationFormTitle =
            AppiumBy.xpath("//android.widget.TextView[@text='Registration Form']");

    /** Ashirvad Sales Personnel Mobile Number field (read-only, pre-filled) */
    private final By salesPersonnelMobileNumberField =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'sales_mobile') "
                    + "or contains(@hint,'Ashirvad Sales Personnel Mobile')]");

    /** Inline validation error: "Please Enter 10 digit number" */
    private final By mobileValidationError =
            AppiumBy.xpath("//android.widget.TextView[@text='Please Enter 10 digit number']");

    /** Ashirvad Sales Personnel Name* text input */
    private final By salesPersonnelNameInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'sales_name') "
                    + "or contains(@hint,'Ashirvad Sales Personnel Name')]");

    /** Select Document Type dropdown/spinner */
    private final By documentTypeDropdown =
            AppiumBy.xpath("//android.widget.Spinner[contains(@resource-id,'document_type')]");

    /** "Next" / "Next" CTA button in Section A */
    private final By nextButton =
            AppiumBy.xpath("//android.widget.Button[@text='Next' or @text='NEXT']");

    // ─────────────────────────────────────────────────────────────────────────
    // Section B Locators (post DigiLocker auto-fill)
    // ─────────────────────────────────────────────────────────────────────────

    /** Name field (auto-populated from DigiLocker) */
    private final By nameInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'name') "
                    + "and contains(@hint,'Name')]");

    /** Date of Birth field */
    private final By dateOfBirthField =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'dob') "
                    + "or contains(@hint,'Date of Birth')]");

    /** Gender dropdown */
    private final By genderDropdown =
            AppiumBy.xpath("//android.widget.Spinner[contains(@resource-id,'gender')]");

    /** Enter ID Number field */
    private final By idNumberInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'id_number') "
                    + "or contains(@hint,'Enter ID Number')]");

    /** Address field */
    private final By addressInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'address') "
                    + "or contains(@hint,'Address')]");

    /** PIN Code field */
    private final By pinCodeInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'pin_code') "
                    + "or contains(@hint,'PIN Code')]");

    /** State field */
    private final By stateInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'state') "
                    + "or contains(@hint,'State')]");

    /** City field */
    private final By cityInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'city') "
                    + "or contains(@hint,'City')]");

    /** Preferred language for communication dropdown */
    private final By preferredLanguageDropdown =
            AppiumBy.xpath("//android.widget.Spinner[contains(@resource-id,'preferred_language') "
                    + "or contains(@resource-id,'communication_language')]");

    /** Referral Code input (optional) */
    private final By referralCodeInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'referral_code') "
                    + "or contains(@hint,'Referral Code')]");

    /** Terms & Condition checkbox */
    private final By termsAndConditionCheckbox =
            AppiumBy.xpath("//android.widget.CheckBox[contains(@text,'terms and condition') "
                    + "or contains(@resource-id,'terms_checkbox')]");

    /** "Please wait…" progress dialog shown during form submission */
    private final By pleaseWaitDialog =
            AppiumBy.xpath("//android.widget.TextView[@text='Please wait...']");

    /** Final submit button */
    private final By submitButton =
            AppiumBy.xpath("//android.widget.Button[@text='Submit' or @text='SUBMIT']");

    // ─────────────────────────────────────────────────────────────────────────
    // Actions – Section A
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Confirms the Registration Form screen is shown.
     *
     * @return {@code true} if the title is visible
     */
    public boolean isDisplayed() {
        try {
            WaitUtils.waitForVisibility(registrationFormTitle);
            LOG.info("RegistrationFormPage is displayed.");
            return true;
        } catch (Exception e) {
            LOG.error("RegistrationFormPage NOT displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Reads the mobile validation error message.
     * Returns the error text if visible, or an empty string if not present.
     *
     * @return validation error text
     */
    public String getMobileValidationErrorText() {
        try {
            WebElement error = WaitUtils.waitForVisibility(
                    mobileValidationError, WaitUtils.SHORT_TIMEOUT);
            return error.getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Enters the Ashirvad Sales Personnel Name.
     *
     * @param name sales rep's name
     * @return this page for fluent chaining
     */
    public RegistrationFormPage enterSalesPersonnelName(String name) {
        LOG.info("Entering Sales Personnel Name: {}", name);
        WaitUtils.waitAndSendKeys(salesPersonnelNameInput, name);
        return this;
    }

    /**
     * Selects the document type from the dropdown.
     * As seen in the video: "Aadhaar Card".
     *
     * @param documentType visible text of the document type option
     * @return this page for fluent chaining
     */
    public RegistrationFormPage selectDocumentType(String documentType) {
        LOG.info("Selecting document type: {}", documentType);
        WaitUtils.waitAndClick(documentTypeDropdown);
        By option = AppiumBy.xpath(
                "//android.widget.TextView[@text='" + documentType + "']");
        WaitUtils.waitAndClick(option);
        return this;
    }

    /**
     * Taps the "Next" button to proceed.
     * This triggers DigiLocker verification flow.
     *
     * @return {@link DigiLockerPage}
     */
    public DigiLockerPage tapNext() {
        LOG.info("Tapping Next on RegistrationFormPage Section A.");
        WaitUtils.waitAndClick(nextButton);
        return new DigiLockerPage();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Actions – Section B (post DigiLocker, back on Registration Form)
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Selects gender from the dropdown.
     *
     * @param gender e.g. "Male" or "Female"
     * @return this page for fluent chaining
     */
    public RegistrationFormPage selectGender(String gender) {
        LOG.info("Selecting gender: {}", gender);
        WaitUtils.waitAndClick(genderDropdown);
        By option = AppiumBy.xpath(
                "//android.widget.TextView[@text='" + gender + "']");
        WaitUtils.waitAndClick(option);
        return this;
    }

    /**
     * Enters the ID number (Aadhaar / document number).
     *
     * @param idNumber document ID string
     * @return this page for fluent chaining
     */
    public RegistrationFormPage enterIdNumber(String idNumber) {
        LOG.info("Entering ID Number.");
        WaitUtils.waitAndSendKeys(idNumberInput, idNumber);
        return this;
    }

    /**
     * Enters a referral code if provided.
     *
     * @param referralCode optional referral code
     * @return this page for fluent chaining
     */
    public RegistrationFormPage enterReferralCode(String referralCode) {
        if (referralCode != null && !referralCode.isBlank()) {
            LOG.info("Entering referral code.");
            WaitUtils.waitAndSendKeys(referralCodeInput, referralCode);
        }
        return this;
    }

    /**
     * Selects the preferred communication language.
     *
     * @param language e.g. "Hindi"
     * @return this page for fluent chaining
     */
    public RegistrationFormPage selectPreferredLanguage(String language) {
        LOG.info("Selecting preferred language: {}", language);
        WaitUtils.waitAndClick(preferredLanguageDropdown);
        By option = AppiumBy.xpath(
                "//android.widget.TextView[@text='" + language + "']");
        WaitUtils.waitAndClick(option);
        return this;
    }

    /**
     * Checks the "I accept the terms and condition" checkbox.
     *
     * @return this page for fluent chaining
     */
    public RegistrationFormPage acceptTermsAndCondition() {
        LOG.info("Accepting terms and condition.");
        WebElement checkbox = WaitUtils.waitForVisibility(termsAndConditionCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    /**
     * Taps the final Submit button.
     * Waits for "Please wait…" dialog to appear and then disappear.
     *
     * @return {@link DashboardPage}
     */
    public DashboardPage submitRegistration() {
        LOG.info("Submitting registration form.");
        WaitUtils.waitAndClick(submitButton);
        // Wait for the "Please wait..." loader to appear then clear
        WaitUtils.waitForInvisibility(pleaseWaitDialog, WaitUtils.LONG_TIMEOUT);
        return new DashboardPage();
    }
}
