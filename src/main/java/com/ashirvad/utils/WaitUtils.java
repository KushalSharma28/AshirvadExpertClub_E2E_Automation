package com.ashirvad.utils;

import com.ashirvad.base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Centralised explicit-wait utilities.
 *
 * <p>All methods create a fresh {@link WebDriverWait} scoped to the current
 * thread's driver.  No implicit waits are ever set.
 */
public final class WaitUtils {

    private static final Logger LOG = LoggerFactory.getLogger(WaitUtils.class);

    /** Default wait ceiling across the suite. */
    public static final Duration DEFAULT_TIMEOUT  = Duration.ofSeconds(20);

    /** Short wait for elements that should appear almost instantly. */
    public static final Duration SHORT_TIMEOUT    = Duration.ofSeconds(5);

    /** Long wait for network-bound operations (OTP screens, API calls). */
    public static final Duration LONG_TIMEOUT     = Duration.ofSeconds(40);

    private WaitUtils() { }

    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Wait until an element identified by {@code locator} is visible,
     * then return it.
     */
    public static WebElement waitForVisibility(By locator) {
        return waitForVisibility(locator, DEFAULT_TIMEOUT);
    }

    public static WebElement waitForVisibility(By locator, Duration timeout) {
        LOG.debug("Waiting up to {} s for visibility of: {}", timeout.getSeconds(), locator);
        return new WebDriverWait(DriverManager.getDriver(), timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Wait until an element is clickable (visible + enabled), then return it.
     */
    public static WebElement waitForClickability(By locator) {
        return waitForClickability(locator, DEFAULT_TIMEOUT);
    }

    public static WebElement waitForClickability(By locator, Duration timeout) {
        LOG.debug("Waiting up to {} s for clickability of: {}", timeout.getSeconds(), locator);
        return new WebDriverWait(DriverManager.getDriver(), timeout)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Wait until an element contains the expected text.
     */
    public static WebElement waitForText(By locator, String expectedText) {
        return waitForText(locator, expectedText, DEFAULT_TIMEOUT);
    }

    public static WebElement waitForText(By locator, String expectedText, Duration timeout) {
        LOG.debug("Waiting for text '{}' on: {}", expectedText, locator);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeout);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
        return DriverManager.getDriver().findElement(locator);
    }

    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Wait for an element to disappear (useful after loaders / "Please wait").
     */
    public static void waitForInvisibility(By locator) {
        waitForInvisibility(locator, DEFAULT_TIMEOUT);
    }

    public static void waitForInvisibility(By locator, Duration timeout) {
        LOG.debug("Waiting up to {} s for invisibility of: {}", timeout.getSeconds(), locator);
        new WebDriverWait(DriverManager.getDriver(), timeout)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Convenience: wait for visibility then click in a single call.
     */
    public static void waitAndClick(By locator) {
        waitForClickability(locator).click();
    }

    /**
     * Convenience: wait for visibility then send keys in a single call.
     */
    public static void waitAndSendKeys(By locator, String text) {
        WebElement el = waitForVisibility(locator);
        el.clear();
        el.sendKeys(text);
    }
}
