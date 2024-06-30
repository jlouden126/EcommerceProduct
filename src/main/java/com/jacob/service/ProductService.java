package com.jacob.service;

import com.jacob.model.Product;
import com.jacob.scraper.AmazonScraper;
import com.jacob.scraper.EbayScraper;
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
}
