package com.ashirvad.tests;

import com.ashirvad.base.AppiumConfig;
import com.ashirvad.base.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * BaseTest
 *
 * <p>All TestNG test classes must extend this class.  It owns the
 * driver lifecycle: create before the class runs, quit after.
 *
 * <p>Key design decisions:
 * <ul>
 *   <li>Driver is stored in {@link DriverManager} (ThreadLocal) — safe for
 *       parallel execution with {@code parallel="classes"} in TestNG.</li>
 *   <li>No implicit waits are ever set; all waits use {@code WebDriverWait}
 *       via {@link com.ashirvad.utils.WaitUtils}.</li>
 *   <li>No deprecated {@code DesiredCapabilities} or {@code MobileElement}.</li>
 * </ul>
 */
public abstract class BaseTest 
{

    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Initialises the {@link AndroidDriver} before any {@code @Test} method
     * in the class runs.
     */
    @BeforeClass(alwaysRun = true)
    public void setUp() 
    {
        log.info("========== [BeforeClass] Setting up AndroidDriver ==========");
        AndroidDriver driver = AppiumConfig.createDriver();
        DriverManager.setDriver(driver);
        log.info("AndroidDriver initialised successfully.");
    }

    /**
     * Quits the {@link AndroidDriver} after all {@code @Test} methods in
     * the class have finished.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() 
    {
        log.info("========== [AfterClass] Tearing down AndroidDriver ==========");
        AndroidDriver driver = (AndroidDriver) DriverManager.getDriver();
        if (driver != null) 
        {
            driver.quit();
            log.info("AndroidDriver quit successfully.");
        }
        DriverManager.removeDriver();
    }
}
