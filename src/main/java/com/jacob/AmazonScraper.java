package com.jacob;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AmazonScraper {

    public static void main(String[] args) {
        
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.amazon.com/dp");

        WebElement textArea = driver.findElement(By.xpath("//input[@id=\"twotabsearchtextbox\"]"));
        WebElement searchButton = driver.findElement(By.xpath("//input[@id='nav-search-submit-button']"));

        textArea.click();
        textArea.sendKeys("Basketball");
        searchButton.click();

    }
}

