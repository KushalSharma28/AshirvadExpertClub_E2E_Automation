package com.ashirvad.pages;

import com.ashirvad.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object: Dashboard / Home Screen
 *
 * <p>Screen observed in video frames 19–20:
 * <ul>
 *   <li>Blue header bar with user's name and tier ("Welcome Areeb! Blue Level 0 Points")</li>
 *   <li>"Help Videos" button in the header</li>
 *   <li>Notification bell icon</li>
 *   <li>Three-dot menu (overflow)</li>
 *   <li>Close / dismiss popup button (X) for the welcome overlay</li>
 *   <li>Rewards section ("Areeb Siddique – Blue Level – 0 Points")</li>
 *   <li>"View transaction history" link</li>
 *   <li>Benefits tab</li>
 *   <li>DBT reward card</li>
 * </ul>
 */
public class DashboardPage {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardPage.class);

    // ── Locators ──────────────────────────────────────────────────────────────

    /**
     * "Welcome {name}!" greeting text in the blue header.
     * Uses contains() because the name is dynamic.
     */
    private final By welcomeGreeting =
            AppiumBy.xpath("//android.widget.TextView[contains(@text,'Welcome')]");

    /** Tier label (e.g. "Blue Level") */
    private final By tierLabel =
            AppiumBy.xpath("//android.widget.TextView[@text='Blue Level']");

    /** Points label (e.g. "0 Points") */
    private final By pointsLabel =
            AppiumBy.xpath("//android.widget.TextView[contains(@text,'Points')]");

    /** "Help Videos" button in the header */
    private final By helpVideosButton =
            AppiumBy.xpath("//android.widget.Button[@text='Help Videos']"
                    + " | //android.widget.TextView[@text='Help Videos']");

    /** Notification bell icon (accessibility) */
    private final By notificationBellIcon =
            AppiumBy.accessibilityId("Notifications");

    /** Three-dot overflow menu */
    private final By overflowMenuButton =
            AppiumBy.accessibilityId("More options");

    /** Close / dismiss (X) button for the welcome popup overlay */
    private final By dismissWelcomePopupButton =
            AppiumBy.xpath("//android.widget.ImageButton[contains(@resource-id,'close') "
                    + "or contains(@content-desc,'Close')]");

    /** User's full name in the Rewards card (e.g. "Areeb Siddique") */
    private final By rewardsCardUserName =
            AppiumBy.xpath("//android.widget.TextView[contains(@resource-id,'user_name')]");

    /** "View transaction history" link */
    private final By viewTransactionHistoryLink =
            AppiumBy.xpath("//android.widget.TextView[@text='View transaction history']");

    /** "Benefits" tab label */
    private final By benefitsTab =
            AppiumBy.xpath("//android.widget.TextView[@text='Benefits']");

    /** "Tier based benefits" title on the Rewards sub-screen */
    private final By tierBasedBenefitsTitle =
            AppiumBy.xpath("//android.widget.TextView[@text='Tier based benefits']");

    // ── Actions ───────────────────────────────────────────────────────────────

    /**
     * Confirms the Dashboard is displayed by waiting for the welcome greeting.
     *
     * @return {@code true} if visible
     */
    public boolean isDisplayed() {
        try {
            WaitUtils.waitForVisibility(welcomeGreeting, WaitUtils.LONG_TIMEOUT);
            LOG.info("DashboardPage is displayed.");
            return true;
        } catch (Exception e) {
            LOG.error("DashboardPage NOT displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Returns the full welcome greeting text (e.g. "Welcome Areeb!").
     *
     * @return greeting string
     */
    public String getWelcomeGreetingText() {
        return WaitUtils.waitForVisibility(welcomeGreeting).getText();
    }

    /**
     * Returns the tier label text (e.g. "Blue Level").
     *
     * @return tier string
     */
    public String getTierText() {
        return WaitUtils.waitForVisibility(tierLabel).getText();
    }

    /**
     * Returns the points text (e.g. "0 Points").
     *
     * @return points string
     */
    public String getPointsText() {
        return WaitUtils.waitForVisibility(pointsLabel).getText();
    }

    /**
     * Dismisses the welcome popup overlay if it is present.
     *
     * @return this page
     */
    public DashboardPage dismissWelcomePopupIfPresent() {
        try {
            WebElement closeBtn = WaitUtils.waitForVisibility(
                    dismissWelcomePopupButton, WaitUtils.SHORT_TIMEOUT);
            closeBtn.click();
            LOG.info("Welcome popup dismissed.");
        } catch (Exception e) {
            LOG.debug("No welcome popup to dismiss.");
        }
        return this;
    }

    /**
     * Taps the "View transaction history" link.
     *
     * @return this page (history loads in same screen or a sub-screen)
     */
    public DashboardPage tapViewTransactionHistory() {
        LOG.info("Tapping 'View transaction history'.");
        WaitUtils.waitAndClick(viewTransactionHistoryLink);
        return this;
    }

    /**
     * Confirms the Rewards section is visible with the user's name.
     *
     * @param expectedName expected user name on the rewards card
     * @return {@code true} if the name matches
     */
    public boolean isRewardsCardDisplayedForUser(String expectedName) {
        try {
            WebElement nameEl = WaitUtils.waitForVisibility(rewardsCardUserName);
            boolean match = nameEl.getText().contains(expectedName);
            LOG.info("Rewards card user name: '{}', expected: '{}'",
                    nameEl.getText(), expectedName);
            return match;
        } catch (Exception e) {
            LOG.error("Rewards card not visible: {}", e.getMessage());
            return false;
        }
    }
}
