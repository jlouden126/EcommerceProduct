package com.jacob;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;

public class SeleniumConfig {

    public static WebDriver getRemoteChromeDriver() throws MalformedURLException, URISyntaxException {
        // Set ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        // Connect to the remote Selenium server
        String seleniumUrl = System.getenv("SELENIUM_URL");
        URL remoteUrl = new URI(seleniumUrl).toURL();
    
        return new RemoteWebDriver(remoteUrl,options);
    }
}
