package com.ashirvad.tests;

import com.ashirvad.pages.AuthFlowPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AshirvadE2ETest extends BaseTest 
{

    @Test(priority = 1, description = "End to End Registration and Login Flow")
    public void testRegistrationAndLoginFlow() 
    {
        AuthFlowPage authPage = new AuthFlowPage(driver);

        // --- SCENARIO 1: REGISTRATION FLOW ---
        authPage.selectLanguageAndConsent("English");
        authPage.enterMobileNumber("7317448807");
        authPage.selectPartyType("Plumber");
        
        // Entering OTP based on video input
        authPage.enterOTP("659368");
        
        // Filling first part of registration
        authPage.fillInitialRegistrationForm("8855071110", "Areeb Siddique");
        
        // Handling 3rd Party DigiLocker Flow
        // Security Note: Aadhar numbers and OTPs should be encrypted or pulled from secure vaults in prod.
        authPage.handleDigiLockerVerification("787707140130", "123456"); // OTP placeholder
        
        // Finalizing Form (Assuming address/dropdowns are auto-filled via DigiLocker as per video)
        authPage.submitRegistration();
        
        // --- SCENARIO 2: LOGIN FLOW ---
        // Post-registration, the app loops back to login in the video
        authPage.enterMobileNumber("7317448807");
        
        // Verify Successful Login
        Assert.assertTrue(authPage.isDashboardDisplayed(), "Dashboard was not loaded successfully post-login.");
    }
}