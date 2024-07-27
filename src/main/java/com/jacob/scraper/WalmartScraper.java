package com.jacob.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.support.ui.WebDriverWait;




import com.jacob.config.SeleniumConfig;
import com.jacob.model.Product;

import java.lang.Thread;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.Duration;
import java.util.logging.Logger;



public class WalmartScraper {

    private static final Logger logger = Logger.getLogger(WalmartScraper.class.getName());
    

    public static List<Product> scrapeWalmartProducts(String productName) throws InterruptedException, MalformedURLException, URISyntaxException{
        // Set up Chrome options to mimic human behavior
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver"); // Update the path if necessary

        List<Product> productList = new ArrayList<>();

        // Define your proxy server
        String proxyAddress = "11.456.448.110:8080";
        
        // Set up the proxy
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyAddress);
        proxy.setSslProxy(proxyAddress);

        // Set up Chrome options to mimic human behavior
        ChromeOptions options = new ChromeOptions();
        options.setProxy(proxy);

        // Add options to disable automation flags
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // Rotating HTTP Header Information and User-Agent
        Map<String, Object> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
        options.setExperimentalOption("prefs", headers);



        WebDriver driver = new ChromeDriver(options);

        // Manage cookies to mimic a real user
       
        logger.info("Navigating to Walmart.com");
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driver.get("https://www.walmart.com/search?q="+productName);
            
            // Wait and perform actions to mimic human behavior
            TimeUnit.SECONDS.sleep(2);

            logger.info("Waiting for walmart search results to load");

        
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-item-id]")));


            
            String source = "Walmart";
            

            List<WebElement> productElements = driver.findElements(By.xpath("//div[@data-item-id]"));
            System.out.println("WALMaa"+productElements.size());

            if (productElements.size() > 40) {
                productElements = productElements.subList(0, 40);
            }
        
            System.out.println("WALM"+productElements.size());
            try {
                for (WebElement productElement : productElements) {
                    // Initialize variables
                    String pictureUrl = null;
                    String title = null;
                    double price = 0;
                    String link = null;

                    // Find the img element inside the located li (simplified XPath)
                    try {
                        WebElement pictureElement = productElement.findElement(By.xpath("//img[contains(@id,'productImage')]"));
                        pictureUrl = pictureElement.getAttribute("src");
                    } catch (NoSuchElementException e) {
                        System.out.println("Picture not found for product");
                    }

                    // Find the title element inside the located li
                    // try {
                    //     WebElement titleElement = productElement.findElement(By.xpath("//div[@data-testid='list-view']//span[@data-automation-id='product-title']"));
                    //     title = titleElement.getText().trim();
                    // } catch (NoSuchElementException e) {
                    //     System.out.println("Title not found for product");
                    //     continue;
                    // }

                    // // Find the price element inside the located li
                    // try {
                    //     WebElement priceElement = productElement.findElement(By.xpath("//div[@data-testid='list-view']//span[contains(text(),'current price')]"));
                    //     String tempPrice = priceElement.getText().trim();
                    //     int dollarSignIndex = tempPrice.indexOf('$');
                    //     tempPrice = tempPrice.substring(dollarSignIndex + 1);
                    //     try {
                    //         price = Double.parseDouble(tempPrice); 
                    //     } catch (NumberFormatException e) {
                    //         System.out.println("Could not format price double");
                    //         continue;
                    //     }
                    // } catch (NoSuchElementException e) {
                    //     System.out.println("Price not found for product");
                    // }

                    // // Find the link element inside the located li
                    // try {
                    //     WebElement linkElement = productElement.findElement(By.cssSelector("//div[@data-testid='list-view']//a[@link-identifier]"));
                    //     link = linkElement.getAttribute("href");
                    // } catch (NoSuchElementException e) {
                    //     System.out.println("Link not found for product");
                    // }
                    Product product = new Product(pictureUrl, title, price, link, source);
                    productList.add(product);
                    TimeUnit.MILLISECONDS.sleep(500);            
                }
            } finally {
                driver.quit();
            }
        
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
        return productList;
    }
}

