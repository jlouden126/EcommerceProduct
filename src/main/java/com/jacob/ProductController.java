package com.jacob;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;
import java.lang.Thread;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Controller
public class ProductController {


    @GetMapping("/")
    public String getProducts(Model model) throws InterruptedException, MalformedURLException, URISyntaxException{
        System.out.println("YAAY");
        List<Product> products = null;
        try {
            products = AmazonScraper.scrapeAmazonProducts("https://www.amazon.com/dp");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        model.addAttribute("products", products);
        return "productList";
    }
}

//     @GetMapping("/products")
//     public String getProducts(Model model) {
//         System.out.println("YAAY");
//         List<Product> products = null;
//         try {
//             products = AmazonScraper.scrapeAmazonProducts("https://www.amazon.com/s?k=soccer");
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }

//         model.addAttribute("products", products);
//         return "productList";
//     }
// }
