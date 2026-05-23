package com.ashirvad.pages;

import com.ashirvad.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object: Language Selection / Landing Screen
 *
 * <p>Screen observed in video frame 1:
 * <ul>
 *   <li>"ashirvad by aliaxis" logo</li>
 *   <li>"Select your language" label</li>
 *   <li>Language dropdown (default: English)</li>
 *   <li>Consent checkbox: "I am providing my consent..."</li>
 *   <li>PRIMARY CTA: "Register/Login" button</li>
 *   <li>SECONDARY CTA: "Customercare Support" link</li>
 * </ul>
 */
public class LanguageSelectionPage {

    private static final Logger LOG = LoggerFactory.getLogger(LanguageSelectionPage.class);

    // ── Locators ──────────────────────────────────────────────────────────────

    /** "Select your language" hint label */
    private final By selectLanguageLabel =
            AppiumBy.xpath("//android.widget.TextView[@text='Select your language']");

    /** Language dropdown spinner (currently shows "English") */
    private final By languageDropdown =
            AppiumBy.xpath("//android.widget.Spinner[contains(@resource-id,'language')]");

    /**
     * Consent checkbox: "I am providing my consent to process my data
     * for the purpose as mentioned in the terms and condition"
     */
    private final By consentCheckbox =
            AppiumBy.xpath("//android.widget.CheckBox[contains(@resource-id,'consent')]");

    /** Register/Login primary button */
    private final By registerLoginButton =
            AppiumBy.xpath("//android.widget.Button[@text='Register/Login']");

    /** Customercare Support link at the bottom */
    private final By customercareSupportLink =
            AppiumBy.xpath("//android.widget.TextView[@text='Customercare Support']");

    // ── Actions ───────────────────────────────────────────────────────────────

    /**
     * Verifies the Language Selection screen has loaded by asserting the
     * "Select your language" label is visible.
     *
     * @return {@code true} if the screen is displayed
     */
    public boolean isDisplayed() {
        try {
            WaitUtils.waitForVisibility(selectLanguageLabel);
            LOG.info("LanguageSelectionPage is displayed.");
            return true;
        } catch (Exception e) {
            LOG.error("LanguageSelectionPage NOT displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Selects a language from the dropdown by its visible text.
     * Default is "English"; pass another locale string to switch.
     *
     * @param languageName visible text of the language option, e.g. "Hindi"
     */
    public LanguageSelectionPage selectLanguage(String languageName) {
        LOG.info("Selecting language: {}", languageName);
        WaitUtils.waitAndClick(languageDropdown);
        By languageOption = AppiumBy.xpath(
                "//android.widget.TextView[@text='" + languageName + "']");
        WaitUtils.waitAndClick(languageOption);
        return this;
    }

    /**
     * Ticks the consent checkbox if it is not already checked.
     */
    public LanguageSelectionPage acceptConsent() {
        LOG.info("Accepting consent checkbox.");
        WebElement checkbox = WaitUtils.waitForVisibility(consentCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    /**
     * Taps the "Register/Login" button and returns the next page object.
     *
     * @return {@link MobileNumberPage}
     */
    public MobileNumberPage tapRegisterLogin() {
        LOG.info("Tapping Register/Login button.");
        WaitUtils.waitAndClick(registerLoginButton);
        return new MobileNumberPage();
    }

    /**
     * Taps the "Customercare Support" link (secondary action).
     */
    public void tapCustomercareSupport() {
        LOG.info("Tapping Customercare Support link.");
        WaitUtils.waitAndClick(customercareSupportLink);
    }
}
