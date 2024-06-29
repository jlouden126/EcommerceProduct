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





// package com.jacob.config;

// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.remote.RemoteWebDriver;

// import java.net.MalformedURLException;
// import java.net.URI;
// import java.net.URL;
// import java.net.URISyntaxException;

// public class SeleniumConfig {

//     public static WebDriver getRemoteChromeDriver() throws MalformedURLException, URISyntaxException {
//         // Set ChromeOptions
//         ChromeOptions options = new ChromeOptions();
//         options.addArguments("--headless"); // Run in headless mode
//         options.addArguments("--no-sandbox");
//         options.addArguments("--disable-dev-shm-usage");
//         options.addArguments("--disable-gpu");
//         options.setCapability("browserVersion", " 124.0");
//         // Connect to the remote Selenium server
//         String seleniumUrl = System.getenv("SELENIUM_URL");
//         URL remoteUrl = new URI(seleniumUrl).toURL();
    
//         return new RemoteWebDriver(remoteUrl,options);
//     }
// }
