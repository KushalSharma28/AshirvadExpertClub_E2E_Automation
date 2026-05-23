package com.ashirvad.pages;

import com.ashirvad.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object: Mobile Number Entry Screen
 *
 * <p>Screen observed in video frames 2–3:
 * <ul>
 *   <li>"ashirvad by aliaxis" logo</li>
 *   <li>Phone icon illustration</li>
 *   <li>"Enter your registered mobile number" hint label</li>
 *   <li>Mobile number input field (prefix +91 shown, 10-digit entry)</li>
 *   <li>Numeric keyboard is auto-raised</li>
 *   <li>Back arrow to return to Language Selection</li>
 * </ul>
 * After the number is entered the soft keyboard's "Done / tick" submits the
 * form and the app navigates to the OTP screen.
 */
public class MobileNumberPage {

    private static final Logger LOG = LoggerFactory.getLogger(MobileNumberPage.class);

    // ── Locators ──────────────────────────────────────────────────────────────

    /** "Enter your registered mobile number" hint text label */
    private final By enterMobileHintLabel =
            AppiumBy.xpath("//android.widget.TextView[@text='Enter your registered mobile number']");

    /**
     * Mobile number input field.
     * The +91 country code is a static prefix; the user types the 10-digit number.
     */
    private final By mobileNumberInput =
            AppiumBy.xpath("//android.widget.EditText[contains(@resource-id,'mobile')]");

    /** Back / up arrow in the top-left */
    private final By backArrow =
            AppiumBy.accessibilityId("Navigate up");

    // ── Actions ───────────────────────────────────────────────────────────────

    /**
     * Verifies the Mobile Number screen has loaded.
     *
     * @return {@code true} if the hint label is visible
     */
    public boolean isDisplayed() {
        try {
            WaitUtils.waitForVisibility(enterMobileHintLabel);
            LOG.info("MobileNumberPage is displayed.");
            return true;
        } catch (Exception e) {
            LOG.error("MobileNumberPage NOT displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Enters the 10-digit mobile number (without country code).
     * As seen in the video: "7317448807".
     *
     * @param mobileNumber 10-digit number string
     * @return this page (for fluent chaining)
     */
    public MobileNumberPage enterMobileNumber(String mobileNumber) {
        LOG.info("Entering mobile number: {}", mobileNumber);
        WaitUtils.waitAndSendKeys(mobileNumberInput, mobileNumber);
        return this;
    }

    /**
     * Submits the mobile number by pressing the keyboard's Done / tick action.
     * Triggers OTP dispatch from the server.
     *
     * @return {@link OtpVerificationPage}
     */
    public OtpVerificationPage submitMobileNumber() {
        LOG.info("Submitting mobile number via keyboard action.");
        // Pressing the keyboard "Done" key (Enter / IME action)
        WaitUtils.waitForVisibility(mobileNumberInput).submit();
        return new OtpVerificationPage();
    }

    /**
     * Combined helper: enter mobile number and submit.
     *
     * @param mobileNumber 10-digit number string
     * @return {@link OtpVerificationPage}
     */
    public OtpVerificationPage enterAndSubmitMobileNumber(String mobileNumber) {
        return enterMobileNumber(mobileNumber).submitMobileNumber();
    }

    /** Navigates back to the Language Selection screen. */
    public LanguageSelectionPage tapBack() {
        LOG.info("Tapping back on MobileNumberPage.");
        WaitUtils.waitAndClick(backArrow);
        return new LanguageSelectionPage();
    }
}
