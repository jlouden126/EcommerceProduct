package com.jacob;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = null;
        try {
            products = AmazonScraper.scrapeAmazonProducts("https://www.amazon.com/s?k=soccer");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        model.addAttribute("products", products);
        return "productList";
    }
}
