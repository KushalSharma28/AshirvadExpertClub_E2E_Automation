package com.ashirvad.pages;

import com.ashirvad.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object: OTP Verification Screen (Ashirvad app)
 *
 * <p>Screen observed in video frames 4–5:
 * <ul>
 *   <li>Animated image carousel (e.g., "Upaharon ki Bauchar", "Family connect")</li>
 *   <li>"Enter OTP" label</li>
 *   <li>OTP input field (6-digit numeric, e.g. "659368")</li>
 *   <li>Numeric keyboard auto-raised</li>
 *   <li>After OTP entry → navigates to Registration Form</li>
 * </ul>
 *
 * <p><b>Note on OTP automation:</b>  In a real CI environment the OTP is
 * intercepted from SMS via ADB or a test-dedicated backend endpoint.
 * For local testing, pass the OTP value directly into
 * {@link #enterOtp(String)}.
 */
public class OtpVerificationPage {

    private static final Logger LOG = LoggerFactory.getLogger(OtpVerificationPage.class);

    // ── Locators ──────────────────────────────────────────────────────────────

    /** "Enter OTP" label visible on the screen */
    private final By enterOtpLabel =
            AppiumBy.xpath("//android.widget.TextView[@text='Enter OTP']");

    /**
     * OTP input field.
     * The video shows a plain EditText that accepts numeric input.
     */
    private final By otpInputField =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'otp')]");

    /** Back / up arrow */
    private final By backArrow =
            AppiumBy.accessibilityId("Navigate up");

    // ── Actions ───────────────────────────────────────────────────────────────

    /**
     * Verifies this screen is displayed by checking the "Enter OTP" label.
     *
     * @return {@code true} if visible
     */
    public boolean isDisplayed() {
        try {
            WaitUtils.waitForVisibility(enterOtpLabel, WaitUtils.LONG_TIMEOUT);
            LOG.info("OtpVerificationPage is displayed.");
            return true;
        } catch (Exception e) {
            LOG.error("OtpVerificationPage NOT displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Enters the 6-digit OTP.
     *
     * @param otp 6-digit OTP string, e.g. "659368"
     * @return this page for fluent chaining
     */
    public OtpVerificationPage enterOtp(String otp) {
        LOG.info("Entering OTP.");
        WaitUtils.waitAndSendKeys(otpInputField, otp);
        return this;
    }

    /**
     * Submits the OTP by pressing the keyboard Done / tick action.
     * If the mobile number is new → navigates to {@link RegistrationFormPage}.
     * If already registered → navigates to {@link DashboardPage}.
     *
     * <p>The caller is responsible for deciding which page to return based
     * on the test scenario.  This method returns {@link RegistrationFormPage}
     * for the registration flow.
     *
     * @return {@link RegistrationFormPage}
     */
    public RegistrationFormPage submitOtpForNewUser() {
        LOG.info("Submitting OTP for new-user registration flow.");
        WaitUtils.waitForVisibility(otpInputField).submit();
        return new RegistrationFormPage();
    }

    /**
     * Submits the OTP for an existing/returning user → goes to Dashboard.
     *
     * @return {@link DashboardPage}
     */
    public DashboardPage submitOtpForExistingUser() {
        LOG.info("Submitting OTP for existing-user login flow.");
        WaitUtils.waitForVisibility(otpInputField).submit();
        return new DashboardPage();
    }

    /** Navigates back to the Mobile Number entry screen. */
    public MobileNumberPage tapBack() {
        LOG.info("Tapping back on OtpVerificationPage.");
        WaitUtils.waitAndClick(backArrow);
        return new MobileNumberPage();
    }
}
