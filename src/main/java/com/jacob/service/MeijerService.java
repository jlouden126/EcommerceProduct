package com.jacob.service;

import com.jacob.model.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeijerService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Product> getMeijerProducts(String productName) {
        
       
            // API URL for Meijer products
        String url = "https://ac.cnstrc.com/search/" + productName + 
            "?c=ciojs-client-2.42.2&key=key_GdYuTcnduTUtsZd6&i=29494219-dc82-41c5-8bdd-e228e06e15a6" +
            "&s=1&us=web&page=1&num_results_per_page=25&filters[availableInStores]=20" +
            "&sort_by=relevance&sort_order=descending&fmt_options[groups_max_depth]=3" +
            "&fmt_options[groups_start]=current&_dt=1726066238273";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String jsonResponse = response.getBody();
        
            // Parse the "results" field from the JSON response
            List<Product> productList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
    
            try {
                JsonNode root = objectMapper.readTree(jsonResponse);  // Parse the JSON
                JsonNode resultsNode = root.path("response").path("results");  // Extract the "results" field

                for (JsonNode productNode : resultsNode) {

                    String pictureUrl = productNode.path("data").path("image_url").asText();
                    String title = productNode.path("data").path("summary").asText();
                    double price = productNode.path("data").path("price").asDouble();
                    String id = productNode.path("data").path("id").asText();
                    String formattedProductName = title.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
 
                    String link = "https://www.meijer.com/shopping/product/" + formattedProductName + "/" + id + ".html";
                    String source = "Meijer"; 
                    Product product = new Product(pictureUrl, title, price, link, source);
                    productList.add(product);
                }
                
            } catch (Exception e) {
                e.printStackTrace();  // Log any parsing errors
            }
            System.out.println(productList);
            return productList;
            
        }

    
}