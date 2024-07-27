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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    @Autowired
    private ProductService productService;

    private static List<Product> cachedProducts;
    private static String lastProductName;
    private Set<String> lastSearchSites;

    @GetMapping("/")
    public String getProducts(@RequestParam(name = "productName", required = false) String productName,
    @RequestParam(name = "sortOrder", required = false) String sortOrder,
    @RequestParam(name = "searchSites", required = false) Set<String> searchSites,
    @RequestParam(name = "filterSites", required = false) Set<String> filterSites,
    Model model) {
        System.out.println(searchSites + " " + lastSearchSites);
        if (productName != null && !productName.isEmpty() && (!productName.equals(lastProductName) || !searchSites.equals(lastSearchSites))) {
            lastProductName = productName;
            lastSearchSites = searchSites;
            cachedProducts = new ArrayList<>();
            System.out.println("SEARCHED SITES " + searchSites);

            if (searchSites != null) {
                if (searchSites.contains("all") || searchSites.contains("amazon")) {
                    CompletableFuture<List<Product>> amazonProductsFuture = productService.getAmazonProductsAsync(productName);
                    try {
                        cachedProducts.addAll(amazonProductsFuture.get());
                    } catch (Exception e) {
                        logger.severe("Error fetching Amazon products: " + e.getMessage());
                    }
                }

                if (searchSites.contains("all") || searchSites.contains("ebay")) {
                    CompletableFuture<List<Product>> ebayProductsFuture = productService.getEbayProductsAsync(productName);
                    try {
                        cachedProducts.addAll(ebayProductsFuture.get());
                    } catch (Exception e) {
                        logger.severe("Error fetching eBay products: " + e.getMessage());
                    }
                }

                


                // Add more checks for other sites as needed
            }
        }
        System.out.println("filter " + filterSites);

        // Filter products based on filterSites parameter
        List<Product> productsToDisplay = new ArrayList<>(cachedProducts != null ? cachedProducts : new ArrayList<>());

        if (filterSites != null && !filterSites.isEmpty() && !filterSites.contains("all")) {
            productsToDisplay = productsToDisplay.stream()
                    .filter(product -> filterSites.contains(product.getSource()))
                    .collect(Collectors.toList());
        }
        // Sort products based on sortOrder parameter
        if (sortOrder != null && !sortOrder.isEmpty()) {
            if (sortOrder.equals("priceLowToHigh")) {
                productsToDisplay.sort(Comparator.comparingDouble(Product::getPrice));
            } else if (sortOrder.equals("priceHighToLow")) {
                productsToDisplay.sort(Comparator.comparingDouble(Product::getPrice).reversed());
            }
        }

        model.addAttribute("products", productsToDisplay);
        model.addAttribute("productCount", productsToDisplay.size());
        model.addAttribute("productName", productName);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("searchSites", searchSites != null ? searchSites : new HashSet<>());
        model.addAttribute("filterSites", filterSites != null ? filterSites : new HashSet<>());
        return "productList";
    
    }
}
