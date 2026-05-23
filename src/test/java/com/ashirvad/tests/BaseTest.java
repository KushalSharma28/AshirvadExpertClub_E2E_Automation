package com.ashirvad.tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest 
{
    protected AndroidDriver driver;

    @BeforeClass
    public void setUp() 
    {
        try {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setDeviceName("emulator-5554") // Replace with actual device name or UDID
                    .setAutomationName("UiAutomator2")
                    .setAppPackage("com.ashirvadapp")
                    .setAppActivity("com.ashirvadapp.MainActivity") // Replace with actual launcher activity if different
                    .setNoReset(false) // Set to true if you want to keep app data between runs
                    .setNewCommandTimeout(Duration.ofSeconds(60));

            URL appiumServerUrl = new URL("http://127.0.0.1:4723");
            driver = new AndroidDriver(appiumServerUrl, options);
            
            // Implicit wait is avoided per best practices; explicit waits are used in Page Objects.
        } catch (MalformedURLException e) {
            throw new RuntimeException("Appium server URL is invalid", e);
        }
    }

    @AfterClass
    public void tearDown() 
    {
        if (driver != null) 
        {
            driver.quit();
        }
    }
}