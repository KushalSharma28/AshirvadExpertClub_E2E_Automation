package com.ashirvad.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Centralised Appium driver configuration.
 *
 * <p>Uses {@link UiAutomator2Options} (Appium 2.x / java-client 8.x+).
 * NO DesiredCapabilities. NO MobileElement. NO implicit waits.
 */
public final class AppiumConfig 
{

    private static final Logger LOG = LoggerFactory.getLogger(AppiumConfig.class);

    // ──────────────────────────────────────────────────────────────────────────
    // Server & Device Configuration
    // Update these values to match your local Appium server and connected device.
    // ──────────────────────────────────────────────────────────────────────────
    private static final String APPIUM_SERVER_URL  = "http://127.0.0.1:4723";
    private static final String DEVICE_NAME        = "Android Device";          // e.g. "emulator-5554"
    private static final String PLATFORM_VERSION   = "13.0";                    // target OS version

    // ──────────────────────────────────────────────────────────────────────────
    // Ashirvad Experts' Club App Coordinates
    // ──────────────────────────────────────────────────────────────────────────
    private static final String APP_PACKAGE        = "com.ashirvadapp";
    private static final String APP_ACTIVITY       = "com.ashirvadapp.MainActivity"; // adjust if needed

    private AppiumConfig() { }

    /**
     * Builds a fresh {@link AndroidDriver} using {@link UiAutomator2Options}.
     *
     * @return a ready-to-use {@link AndroidDriver}
     */
    public static AndroidDriver createDriver() 
    {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(DEVICE_NAME)
                .setPlatformVersion(PLATFORM_VERSION)
                .setAppPackage(APP_PACKAGE)
                .setAppActivity(APP_ACTIVITY)
                .setAutomationName("UiAutomator2")
                .setNoReset(false)          // fresh app state on every test run
                .setFullReset(false)
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setAdbExecTimeout(Duration.ofSeconds(30));

        // Explicitly disable implicit wait — all waits are WebDriverWait
        // (Appium 2.x / Selenium 4.x have no setImplicitWaitTimeout on options;
        //  we simply never call driver.manage().timeouts().implicitlyWait()).

        try {
            AndroidDriver driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), options);
            LOG.info("AndroidDriver created successfully against {}", APPIUM_SERVER_URL);
            return driver;
        } 
	catch (MalformedURLException e) 
	{
            throw new IllegalStateException("Invalid Appium server URL: " + APPIUM_SERVER_URL, e);
        }
    }
}
