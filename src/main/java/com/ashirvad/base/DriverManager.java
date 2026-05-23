package com.ashirvad.base;

import io.appium.java_client.android.AndroidDriver;

/**
 * Thread-safe AndroidDriver holder using ThreadLocal.
 * Ensures each test thread has its own driver instance.
 */
public final class DriverManager 
{

    private static final ThreadLocal<AndroidDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private DriverManager() 
    {
        // Utility class — no instantiation
    }

    public static AndroidDriver getDriver() 
    {
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void setDriver(AndroidDriver driver) 
    {
        DRIVER_THREAD_LOCAL.set(driver);
    }

    public static void removeDriver() 
    {
        DRIVER_THREAD_LOCAL.remove();
    }
}
