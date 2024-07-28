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



public class BestBuyScraper {

    private static final Logger logger = Logger.getLogger(BestBuyScraper.class.getName());


    public static List<Product> scrapeBestBuyProducts(String productName) throws InterruptedException, MalformedURLException, URISyntaxException{
        WebDriver driver = SeleniumConfig.getLocalChromeDriver();
        logger.info("Navigating to BestBuy.com");
        driver.get("https://www.bestbuy.com/site/searchpage.jsp?st=" + productName + "&_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=&sp=&qp=&list=n&af=true&iht=y&usc=All+Categories&ks=960&keys=keys");
        
        String source = "BestBuy";
        logger.info("Waiting for search results to load");
        List<Product> productList = new ArrayList<>();

        WebElement productCollect = driver.findElement(By.cssSelector("ol.sku-item-list"));
        List<WebElement> productElements = productCollect.findElements(By.cssSelector("li.sku-item"));

        if (productElements.size() > 40) {
            productElements = productElements.subList(0, 40);
        }
        try {
            for (WebElement item : productElements) {
                            
                    // Initialize variables
                    String pictureUrl = null;
                    String title = null;
                    double price = 0;
                    String link = null;

                    WebElement pictureElement = null;
                    WebElement titleElement = null;
                    WebElement priceElementDollar = null;
                    WebElement priceElementCent = null;
                    WebElement linkElement = null;
                    String flags = "";
                    try {
                        try {
                            pictureUrl = item.findElement(By.cssSelector("img.product-image")).getAttribute("src");
                        } catch (NoSuchElementException e) {
                            System.out.println("Item picture is not a product");
                            flags = flags + "Picture ";
                        }

                        try {
                            title = item.findElement(By.cssSelector("h4.sku-title a")).getText();
                        } catch (NoSuchElementException e) {
                            System.out.println("Item title is not a product");
                            continue;
                        }

                        // try {
                        //     priceElementDollar = driver.findElement(By.xpath(productBase + "//span[@class='a-price-whole']"));
                        //     priceElementCent = driver.findElement(By.xpath(productBase + "//span[@class='a-price-fraction']"));
                        //     String tempPrice = priceElementDollar.getText().trim().replace(",", "") + '.' + priceElementCent.getText().trim();
                        //     price = Double.parseDouble(tempPrice);
                        // } catch (NoSuchElementException e) {
                        //     System.out.println("Item price  for index" + index + "is not a product");
                        //     flags = flags + "Price ";
                        // }

                        

                        // try {
                        //     linkElement = driver.findElement(By.xpath(productBase + "//a[@tabindex='-1']"));
                        //     link = linkElement.getAttribute("href");

                        // } catch (NoSuchElementException e) {
                        //     System.out.println("Item link for index" + index + "is not a product");
                        //     flags = flags + "Link ";
                        // }

                        Product product = new Product(pictureUrl, title, price, link, source);
                        productList.add(product);
                    } catch (NoSuchElementException e) {
                        System.out.println(flags + " not added to product");
                    }
            
            }
        } finally {
            driver.quit();
        }

        return productList;
    }
}

