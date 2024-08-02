package com.jacob.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jacob.config.SeleniumConfig;
import com.jacob.model.Product;

import java.lang.Thread;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.util.logging.Logger;



public class TargetScraper {

    private static final Logger logger = Logger.getLogger(AmazonScraper.class.getName());


    public static List<Product> scrapeTargetProducts(String productName) throws InterruptedException, MalformedURLException, URISyntaxException{
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);
        // Ensure ChromeDriver binary is accessible
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver"); // Update the path if necessary

        logger.info("Navigating to Target.com");
        driver.get("https://www.target.com/s?searchTerm=" + productName);

        String source = "Target";
        logger.info("Waiting for search results to load");
        try {
            Thread.sleep(10000);  // Sleep for 10,000 milliseconds (10 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        // Wait for the main container that holds the product elements
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-test='product-grid']")));

        // Wait for the products to be loaded within the container
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-test='product-grid']//div[contains(@class, 'sc-f82024d1-0')]")));

        // Locate the parent div
        WebElement parentDiv = driver.findElement(By.xpath("//div[@data-test='product-grid']"));

        // Locate all product div elements within the parent div
        List<WebElement> productElements = parentDiv.findElements(By.xpath(".//div[contains(@class, 'sc-f82024d1-0')]"));
        System.out.println("NUMBER " + productElements.size());


        // Print the page source for debugging
        //System.out.println("PAGE SOURCE:\n" + driver.getPageSource());

        if (productElements.size() > 50) {
            productElements = productElements.subList(0, 50);
        }

        List<Product> productList = new ArrayList<>();
        try {
            for (WebElement productElement : productElements) {
                System.out.println("PRODUCT HTML: " + productElement.getAttribute("outerHTML"));

                // Initialize variables
                String pictureUrl = null;
                String title = null;
                double price = 0;
                String link = null;
                System.out.println("PRODUCT " + productElement);
                // Find the img element inside the located li (simplified XPath)
                try {
                    WebElement pictureElement = productElement.findElement(By.cssSelector("img"));
                    pictureUrl = pictureElement.getAttribute("src");
                } catch (NoSuchElementException e) {
                    System.out.println("Picture not found for product");
                }

                // Find the title element inside the located li
                try {
                    WebElement titleElement = productElement.findElement(By.xpath(".//a[@data-test='product-title']"));
                    System.out.println("TITLE ELEMENT " + titleElement);
                    title = titleElement.getText().trim();
                } catch (NoSuchElementException e) {
                    System.out.println("Title not found for product");
                    continue;
                }

                // Find the price element inside the located li
                // try {
                //     WebElement priceElement = productElement.findElement(By.cssSelector("span.s-item__price"));
                //     String tempPrice = priceElement.getText().trim().replaceAll("[^\\d.]", ""); 
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
                //     WebElement linkElement = productElement.findElement(By.cssSelector("a.s-item__link"));
                //     link = linkElement.getAttribute("href");
                // } catch (NoSuchElementException e) {
                //     System.out.println("Link not found for product");
                // }
                Product product = new Product(pictureUrl, title, price, link, source);
                productList.add(product);
        
            }
        } finally {
            driver.quit();
        }
        return productList;
        
    }
}