package com.jacob;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.NoSuchElementException;
import java.lang.Thread;


import java.util.ArrayList;
import java.util.List;



public class AmazonScraper {

    public static void main(String[] args) {
        
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.amazon.com/dp");

        WebElement textArea = driver.findElement(By.xpath("//input[@id=\"twotabsearchtextbox\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//input[@id='nav-search-submit-button']"));
        textArea.click();
        textArea.sendKeys("Basketball");
        searchButton.click();

        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
            System.out.println("got interrupted!");
        }

        List<Product> productList = new ArrayList<>();

        for (int index = 3; index < 10; index++) {
            try {
                String productBase = "//div[@data-index='"+index+"']";
                
                // Initialize variables
                String pictureUrl = null;
                String title = null;
                String price = null;
                

                WebElement pictureElement = driver.findElement(By.xpath(productBase+"//img"));
                WebElement titleElement = driver.findElement(By.xpath(productBase + "//span[@class='a-size-base-plus a-color-base a-text-normal']"));
                WebElement priceElementDollar = driver.findElement(By.xpath(productBase + "//span[@class='a-price-whole']"));
                WebElement priceElementCent = driver.findElement(By.xpath(productBase + "//span[@class='a-price-fraction']"));

                
                pictureUrl = pictureElement.getAttribute("src");
                title = titleElement.getText().trim();
                price = priceElementDollar.getText().trim() + '.' + priceElementCent.getText().trim();

                
                
                Product product = new Product(pictureUrl, title, price);
                productList.add(product);

            } catch (NoSuchElementException e) {
                System.out.println("Item for index" + index + "is not a product");
            }
            
        }

        for (Product product : productList) {
            System.out.println(product);
        }

        

    }
}

