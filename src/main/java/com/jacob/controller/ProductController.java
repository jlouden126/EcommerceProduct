package com.jacob.controller;

import com.jacob.service.ProductService;
import com.jacob.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Controller
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getProducts(@RequestParam(name = "productName", required = false) String productName,
                              @RequestParam(name = "sortOrder", required = false) String sortOrder, Model model) {
        List<Product> products = new ArrayList<>();
        if (productName != null && !productName.isEmpty()) {
            CompletableFuture<List<Product>> amazonProductsFuture = productService.getAmazonProductsAsync(productName);
            CompletableFuture<List<Product>> ebayProductsFuture = productService.getEbayProductsAsync(productName);
            try {
                List<Product> amazonProducts = amazonProductsFuture.get();
                List<Product> ebayProducts = ebayProductsFuture.get();
                products.addAll(amazonProducts);
                products.addAll(ebayProducts);          

                //Server side sort
                // if (sortOrder != null) {
                //     if (sortOrder.equals("priceLowToHigh")) {
                //         products.sort(Comparator.comparingDouble(Product::getPrice));
                //     } else if (sortOrder.equals("priceHighToLow")) {
                //         products.sort(Comparator.comparingDouble(Product::getPrice).reversed());
                //     }
                // }
            } catch (Exception e) {
                logger.severe("Error fetching products: " + e.getMessage());
                e.printStackTrace();  // Log the exception
            }
        }

        // Sort products based on sortOrder parameter
        if (sortOrder != null && !products.isEmpty()) {
            if (sortOrder.equals("priceLowToHigh")) {
                products.sort(Comparator.comparingDouble(Product::getPrice));
            } else if (sortOrder.equals("priceHighToLow")) {
                products.sort(Comparator.comparingDouble(Product::getPrice).reversed());
            }
        }

        model.addAttribute("products", products);
        model.addAttribute("productName", productName);
        model.addAttribute("productCount", products.size());  // Add product count attribute
        model.addAttribute("sortOrder", sortOrder);
        return "productList";
    }
}
