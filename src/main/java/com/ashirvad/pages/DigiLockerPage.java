package com.ashirvad.pages;

import com.ashirvad.base.DriverManager;
import com.ashirvad.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object: DigiLocker / Meri Pehchaan Sign-In Screen
 *
 * <p>Screen observed in video frames 11–14.
 * This is a WebView / in-app browser rendering the National Meri Pehchaan
 * Single Sign-On (SSO) portal used for Aadhaar-based KYC verification.
 *
 * <h3>Flow observed in video:</h3>
 * <ol>
 *   <li>SSO page loads: "Meri Pehchaan – Single Sign-On Service"</li>
 *   <li>"Other ID" tab selected (Aadhaar)</li>
 *   <li>Aadhaar number entered</li>
 *   <li>PIN-less authentication + terms of use checkboxes ticked</li>
 *   <li>OTP dispatched → "Verify OTP" screen</li>
 *   <li>OTP entered → DigiLocker consent screen ("Allow" / "Deny")</li>
 *   <li>"Allow" → "Digilocker Verification Complete" screen (frame 14)</li>
 *   <li>App returns to Registration Form with KYC data pre-filled</li>
 * </ol>
 *
 * <p><b>WebView context:</b> The SSO portal renders inside a WebView.
 * {@link #switchToWebView()} must be called before interacting with SSO
 * elements; {@link #switchToNativeApp()} restores native context afterwards.
 */
public class DigiLockerPage {

    private static final Logger LOG = LoggerFactory.getLogger(DigiLockerPage.class);

    private static final String NATIVE_APP_CONTEXT      = "NATIVE_APP";
    private static final String WEBVIEW_CONTEXT_PREFIX  = "WEBVIEW_com.ashirvadapp";

    // ── Locators ─────────────────────────────────────────────────────────────

    /** "Meri Pehchaan" heading (rendered inside WebView) */
    private final By meriPehchaanHeading =
            AppiumBy.xpath("//*[contains(@content-desc,'Meri Pehchaan') "
                    + "or contains(@text,'Meri Pehchaan')]");

    /** "Other ID" tab on the SSO login form */
    private final By otherIdTab =
            AppiumBy.xpath("//*[@text='Other ID']");

    /** Aadhaar number input field inside SSO form */
    private final By aadhaarNumberInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'aadhaar') "
                    + "or contains(@hint,'Aadhaar')]");

    /** "PIN less authentication" checkbox */
    private final By pinLessAuthCheckbox =
            AppiumBy.xpath("//android.widget.CheckBox[contains(@resource-id,'pin_less') "
                    + "or following-sibling::*[contains(@text,'PIN less')]]");

    /** "I consent to terms of use" checkbox */
    private final By consentToTermsCheckbox =
            AppiumBy.xpath("//android.widget.CheckBox[contains(@resource-id,'consent_terms') "
                    + "or following-sibling::*[contains(@text,'terms of use')]]");

    /** Next / Sign In button on the SSO login form */
    private final By ssoNextButton =
            AppiumBy.xpath("//*[@text='Next' or @text='Sign In']");

    /** "Verify OTP" heading on the DigiLocker OTP sub-screen */
    private final By verifyOtpHeading =
            AppiumBy.xpath("//*[@text='Verify OTP']");

    /** OTP input on the DigiLocker OTP screen */
    private final By digiLockerOtpInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'otp')]");

    /** "Sign In" CTA on the Verify OTP screen */
    private final By digiLockerSignInButton =
            AppiumBy.xpath("//*[@text='Sign In']");

    /** "Allow" button on the DigiLocker consent screen (frame 13) */
    private final By consentAllowButton =
            AppiumBy.xpath("//*[@text='Allow' or @text='ALLOW']");

    /** "Deny" button on the DigiLocker consent screen */
    private final By consentDenyButton =
            AppiumBy.xpath("//*[@text='Deny' or @text='DENY']");

    /** "Digilocker Verification Complete" success label (frame 14, native) */
    private final By verificationCompleteLabel =
            AppiumBy.xpath("//android.widget.TextView[@text='Digilocker Verification Complete']");

    // ── Context Helpers ───────────────────────────────────────────────────────

    /**
     * Switches the driver to the first available WebView context.
     */
    private void switchToWebView() {
        AndroidDriver driver = (AndroidDriver) DriverManager.getDriver();
        for (String ctx : driver.getContextHandles()) {
            if (ctx.startsWith(WEBVIEW_CONTEXT_PREFIX)) {
                driver.context(ctx);
                LOG.info("Switched to WebView context: {}", ctx);
                return;
            }
        }
        LOG.warn("No WebView context found matching '{}'; staying in current context.",
                WEBVIEW_CONTEXT_PREFIX);
    }

    /** Switches the driver back to the native app context. */
    private void switchToNativeApp() {
        AndroidDriver driver = (AndroidDriver) DriverManager.getDriver();
        driver.context(NATIVE_APP_CONTEXT);
        LOG.info("Switched back to NATIVE_APP context.");
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    /**
     * Confirms the DigiLocker / Meri Pehchaan SSO page has loaded.
     *
     * @return {@code true} if visible
     */
    public boolean isDisplayed() {
        try {
            switchToWebView();
            WaitUtils.waitForVisibility(meriPehchaanHeading, WaitUtils.LONG_TIMEOUT);
            LOG.info("DigiLockerPage (SSO) is displayed.");
            return true;
        } catch (Exception e) {
            LOG.error("DigiLockerPage NOT displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Taps the "Other ID" (Aadhaar) tab.
     *
     * @return this page
     */
    public DigiLockerPage selectOtherIdTab() {
        LOG.info("Selecting 'Other ID' tab on SSO page.");
        WaitUtils.waitAndClick(otherIdTab);
        return this;
    }

    /**
     * Enters the 12-digit Aadhaar number.
     *
     * @param aadhaarNumber 12-digit Aadhaar string
     * @return this page
     */
    public DigiLockerPage enterAadhaarNumber(String aadhaarNumber) {
        LOG.info("Entering Aadhaar number.");
        WaitUtils.waitAndSendKeys(aadhaarNumberInput, aadhaarNumber);
        return this;
    }

    /**
     * Enables the "PIN-less authentication" checkbox if not already checked.
     *
     * @return this page
     */
    public DigiLockerPage enablePinLessAuth() {
        LOG.info("Enabling PIN-less authentication.");
        WebElement cb = WaitUtils.waitForVisibility(pinLessAuthCheckbox);
        if (!cb.isSelected()) {
            cb.click();
        }
        return this;
    }

    /**
     * Accepts the DigiLocker "terms of use" checkbox.
     *
     * @return this page
     */
    public DigiLockerPage acceptTermsOfUse() {
        LOG.info("Accepting DigiLocker terms of use.");
        WebElement cb = WaitUtils.waitForVisibility(consentToTermsCheckbox);
        if (!cb.isSelected()) {
            cb.click();
        }
        return this;
    }

    /**
     * Taps the SSO form's Next / Sign In button to trigger OTP dispatch.
     *
     * @return this page
     */
    public DigiLockerPage tapSsoNext() {
        LOG.info("Tapping Next on SSO form (dispatches OTP).");
        WaitUtils.waitAndClick(ssoNextButton);
        return this;
    }

    /**
     * Enters the OTP on the "Verify OTP" sub-screen.
     *
     * @param otp 6-digit OTP
     * @return this page
     */
    public DigiLockerPage enterDigiLockerOtp(String otp) {
        LOG.info("Entering DigiLocker OTP.");
        WaitUtils.waitForVisibility(verifyOtpHeading, WaitUtils.LONG_TIMEOUT);
        WaitUtils.waitAndSendKeys(digiLockerOtpInput, otp);
        return this;
    }

    /**
     * Taps "Sign In" to verify the DigiLocker OTP.
     *
     * @return this page (transitions to consent screen)
     */
    public DigiLockerPage tapSignIn() {
        LOG.info("Tapping Sign In to verify DigiLocker OTP.");
        WaitUtils.waitAndClick(digiLockerSignInButton);
        return this;
    }

    /**
     * Taps "Allow" on the DigiLocker data-sharing consent screen.
     * Waits for the native "Digilocker Verification Complete" screen,
     * then returns to the Registration Form (data pre-filled by KYC).
     *
     * @return {@link RegistrationFormPage}
     */
    public RegistrationFormPage tapAllow() {
        LOG.info("Tapping Allow on DigiLocker consent screen.");
        WaitUtils.waitAndClick(consentAllowButton);
        switchToNativeApp();
        WaitUtils.waitForVisibility(verificationCompleteLabel, WaitUtils.LONG_TIMEOUT);
        LOG.info("DigiLocker verification complete. Returning to Registration Form.");
        return new RegistrationFormPage();
    }

    /**
     * Taps "Deny" on the consent screen.
     *
     * @return {@link RegistrationFormPage} without KYC data
     */
    public RegistrationFormPage tapDeny() {
        LOG.info("Tapping Deny on DigiLocker consent screen.");
        WaitUtils.waitAndClick(consentDenyButton);
        switchToNativeApp();
        return new RegistrationFormPage();
    }

    /**
     * Convenience: runs the entire DigiLocker verification in one call.
     *
     * @param aadhaarNumber  12-digit Aadhaar number
     * @param digiLockerOtp  OTP received on Aadhaar-linked mobile
     * @return {@link RegistrationFormPage} with pre-filled KYC data
     */
    public RegistrationFormPage completeVerification(String aadhaarNumber,
                                                     String digiLockerOtp) {
        return selectOtherIdTab()
                .enterAadhaarNumber(aadhaarNumber)
                .enablePinLessAuth()
                .acceptTermsOfUse()
                .tapSsoNext()
                .enterDigiLockerOtp(digiLockerOtp)
                .tapSignIn()
                .tapAllow();
    }
}
