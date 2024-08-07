package com.jacob.service;

import com.jacob.model.Product;
import com.jacob.scraper.AmazonScraper;
import com.jacob.scraper.EbayScraper;
import com.jacob.scraper.WalmartScraper;
import com.jacob.scraper.BestBuyScraper;
import com.jacob.scraper.TargetScraper;



import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@Service
public class ProductService {

    @Async
    public CompletableFuture<List<Product>> getAmazonProductsAsync(String productName) {
        List<Product> products = null;
        try {
            products = AmazonScraper.scrapeAmazonProducts(productName);
            
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            products = new ArrayList<>();  // Return an empty list in case of exception
        }
        return CompletableFuture.completedFuture(products);
    }

    @Async
    public CompletableFuture<List<Product>> getEbayProductsAsync(String productName) {
        List<Product> products = new ArrayList<>();
        try {
            products = EbayScraper.scrapeEbayProducts(productName);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            products = new ArrayList<>();
        }
        return CompletableFuture.completedFuture(products);
    }

    @Async
    public CompletableFuture<List<Product>> getWalmartProductsAsync(String productName) {
        List<Product> products = new ArrayList<>();
        try {
            products = WalmartScraper.scrapeWalmartProducts(productName);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            products = new ArrayList<>();
        }
        return CompletableFuture.completedFuture(products);
    }

    @Async
    public CompletableFuture<List<Product>> getBestBuyProductsAsync(String productName) {
        List<Product> products = new ArrayList<>();
        try {
            products = BestBuyScraper.scrapeBestBuyProducts(productName);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            products = new ArrayList<>();
        }
        return CompletableFuture.completedFuture(products);
    }

    @Async
    public CompletableFuture<List<Product>> getTargetProductsAsync(String productName) {
        List<Product> products = new ArrayList<>();
        try {
            products = TargetScraper.scrapeTargetProducts(productName);
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            products = new ArrayList<>();
        }
        return CompletableFuture.completedFuture(products);
    }
}
