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

public class EbayScraper {

    private static final Logger logger = Logger.getLogger(AmazonScraper.class.getName());

    public static List<Product> scrapeEbayProducts(String productName) throws InterruptedException, MalformedURLException, URISyntaxException {
        //List<Product> scrapeEbayProducts(String searchUrl)
        WebDriver driver = SeleniumConfig.getLocalChromeDriver();
        driver.get("https://www.ebay.com/sch/i.html?_from=R40&_trksid=p4432023.m570.l1313&_nkw="+productName+"&_sacat=0");
        logger.info("Waiting for ebay search results to load");
        String source = "Ebay";
        // WebElement textArea = driver.findElement(By.xpath("//input[@type='text']"));
        // WebElement searchButton = driver.findElement(By.xpath("//input[@type='submit']"));
        // textArea.click();
        // textArea.sendKeys("Basketball");
        // searchButton.click();

        // Locate all <li> elements with the specific class for eBay items
        List<WebElement> productElements = driver.findElements(By.xpath("//li[@data-viewport]"));
        if (productElements.size() > 40) {
            productElements = productElements.subList(0, 40);
        }
    
        List<Product> productList = new ArrayList<>();

        try {
            for (WebElement productElement : productElements) {
                // Initialize variables
                String pictureUrl = null;
                String title = null;
                double price = 0;
                String link = null;

                // Find the img element inside the located li (simplified XPath)
                try {
                    WebElement pictureElement = productElement.findElement(By.cssSelector("img"));
                    pictureUrl = pictureElement.getAttribute("src");
                } catch (NoSuchElementException e) {
                    System.out.println("Picture not found for product");
                }

                // Find the title element inside the located li
                try {
                    WebElement titleElement = productElement.findElement(By.cssSelector("div.s-item__title"));
                    title = titleElement.getText().trim();
                } catch (NoSuchElementException e) {
                    System.out.println("Title not found for product");
                    continue;
                }

                // Find the price element inside the located li
                try {
                    WebElement priceElement = productElement.findElement(By.cssSelector("span.s-item__price"));
                    String tempPrice = priceElement.getText().trim().replaceAll("[^\\d.]", ""); 
                    try {
                        price = Double.parseDouble(tempPrice); 
                    } catch (NumberFormatException e) {
                        System.out.println("Could not format price double");
                        continue;
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Price not found for product");
                }

                // Find the link element inside the located li
                try {
                    WebElement linkElement = productElement.findElement(By.cssSelector("a.s-item__link"));
                    link = linkElement.getAttribute("href");
                } catch (NoSuchElementException e) {
                    System.out.println("Link not found for product");
                }
                Product product = new Product(pictureUrl, title, price, link, source);
                productList.add(product);
        
            }
        } finally {
            driver.quit();
        }
        return productList;
        
    }
}

