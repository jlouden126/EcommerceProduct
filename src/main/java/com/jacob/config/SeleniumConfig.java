package com.jacob.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumConfig {

    public static WebDriver getLocalChromeDriver() {
        // Set ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        // Ensure ChromeDriver binary is accessible
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver"); // Update the path if necessary

        // Return a new ChromeDriver instance
        return new ChromeDriver(options);
    }
}