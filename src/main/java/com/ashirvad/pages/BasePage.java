package com.ashirvad.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage 
{
    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage(AndroidDriver driver) 
    {
        this.driver = driver;
        // Standardizing a 15-second explicit wait for dynamic mobile elements
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Waits for an element to be clickable before interacting.
     */
    protected void click(AppiumBy locator) 
    {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * Waits for visibility, clears existing text, and sends new input.
     */
    protected void type(AppiumBy locator, String text) 
    {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Safely checks if an element is displayed without throwing an unhandled exception if missing.
     */
    protected boolean isDisplayed(AppiumBy locator) 
    {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieves text from a visible element.
     */
    protected String getText(AppiumBy locator) 
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
}