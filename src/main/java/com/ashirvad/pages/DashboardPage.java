package com.ashirvad.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class DashboardPage extends BasePage 
{

    // Locators - Extracted from the visual UI layout in the video
    private final AppiumBy welcomeMessage = new AppiumBy.ByXPath("//android.widget.TextView[contains(@text, 'Welcome')]");
    private final AppiumBy userLevel = new AppiumBy.ByXPath("//android.widget.TextView[contains(@text, 'Level')]");
    private final AppiumBy totalPoints = new AppiumBy.ByXPath("//android.widget.TextView[contains(@text, 'Points')]");
    
    // Dashboard Tiles
    private final AppiumBy rewardsAndBenefitsTile = new AppiumBy.ByXPath("//android.widget.TextView[@text='Rewards & Benefits']");
    private final AppiumBy productsAndSchemesTile = new AppiumBy.ByXPath("//android.widget.TextView[@text='Products & Schemes']");
    
    // Internal Dashboard Screens
    private final AppiumBy viewTransactionHistoryLink = new AppiumBy.ByXPath("//android.widget.TextView[@text='View transaction history']");
    private final AppiumBy kycStatusHeader = new AppiumBy.ByXPath("//android.widget.TextView[@text='KYC status']");

    public DashboardPage(AndroidDriver driver) 
    {
        super(driver); // Initializes the AndroidDriver and WebDriverWait from BasePage
    }

    public String getWelcomeText() 
    {
        return getText(welcomeMessage);
    }

    public String getUserLevelText() 
    {
        return getText(userLevel);
    }

    public String getPointsBalance() 
    {
        return getText(totalPoints);
    }

    public void navigateToRewardsAndBenefits() 
    {
        click(rewardsAndBenefitsTile);
    }

    public void openTransactionHistory() 
    {
        click(viewTransactionHistoryLink);
    }

    /**
     * Validates that the core dashboard elements have rendered.
     */
    public boolean isDashboardLoaded() 
    {
        return isDisplayed(welcomeMessage) && isDisplayed(rewardsAndBenefitsTile);
    }
    
    public boolean isKycStatusDisplayed() 
    {
        return isDisplayed(kycStatusHeader);
    }
}