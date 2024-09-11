package com.jacob.service;

import com.jacob.model.Product;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.ValidatableResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TargetService {
    private static final Logger logger = Logger.getLogger(TargetService.class.getName());

    public static List<Product> getTargetProducts(String productName) {
        List<Product> products = new ArrayList<>();
        String storeIds = "2890,1287,1057,2191,1014";  // Example of valid store IDs
        String url = "https://redsky.target.com/redsky_aggregations/v1/web/plp_search_v2?key=9f36aeafbe60771e321a7cc95a78140772ab3e96&channel=WEB&count=24&default_purchasability_filter=true&include_dmc_dmr=true&include_sponsored=true&keyword=ball&new_search=true&offset=0&page=%2Fs%2Fball&platform=desktop&pricing_store_id=2890&spellcheck=true&store_ids=2890%2C1287%2C1057%2C2191%2C1014&useragent=Mozilla%2F5.0+%28Macintosh%3B+Intel+Mac+OS+X+10_15_7%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Chrome%2F128.0.0.0+Safari%2F537.36&visitor_id=0191C24E02BB020194033CAE101E6A63&zip=15845";
        
        Response response = RestAssured
            .given()
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36")
            .header("accept", "application/json")
            .header("accept-encoding", "gzip, deflate, br, zstd")
            .header("accept-language", "en-US,en;q=0.9")
            .header("cookie", "sapphire=1; visitorId=0191C24E02BB020194033CAE101E6A63;")  // Include relevant cookies if needed
            .header("origin", "https://www.target.com")
            .header("referer", "https://www.target.com/s?searchTerm=ball&tref=typeahead%7Cterm%7Cball%7C%7C%7Chistory")
            .header("sec-ch-ua", "\"Chromium\";v=\"128\", \"Not;A=Brand\";v=\"24\", \"Google Chrome\";v=\"128\"")
            .header("sec-ch-ua-mobile", "?0")
            .header("sec-ch-ua-platform", "\"macOS\"")
            .header("sec-fetch-dest", "empty")
            .header("sec-fetch-mode", "cors")
            .header("sec-fetch-site", "same-site")
            .get(url);

        // Print out the response
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    
        // // Example code for constructing the API URL dynamically with the product name
        // String baseUrl = "https://redsky.target.com/redsky_aggregations/v1/web/plp_search_v2";
        // String apiKey = "9f36aeafbe60771e321a7cc95a78140772ab3e96";
        // String parameters = "&channel=WEB&count=24&default_purchasability_filter=true&include_dmc_dmr=true&include_sponsored=true";
        // String userAgent = "&useragent=Mozilla/5.0+(Macintosh;+Intel+Mac+OS+X+10_15_7)+AppleWebKit/537.36+(KHTML,+like+Gecko)+Chrome/128.0.0.0+Safari/537.36";
        // String pricingStoreId = "&pricing_store_id=2890";
        // String visitorId = "&visitor_id=0191C24E02BB020194033CAE101E6A63";
        // String zip = "&zip=49083";

        // // String url = baseUrl + "?key=" + apiKey + parameters 
        // //                 + "&keyword=" + productName 
        // //                 + "&new_search=true&offset=0&page=%2Fs%2F" + productName 
        // //                 + "&platform=desktop" + pricingStoreId 
        // //                 + userAgent + visitorId + zip;
        // String url = "https://redsky.target.com/redsky_aggregations/v1/web/plp_search_v2?key=9f36aeafbe60771e321a7cc95a78140772ab3e96&channel=WEB&count=24&default_purchasability_filter=true&include_dmc_dmr=true&include_sponsored=true&keyword=baseball&new_search=true&offset=0&page=%2Fs%2Fbaseball&platform=desktop&pricing_store_id=2890&useragent=Mozilla/5.0+(Macintosh;+Intel+Mac+OS+X+10_15_7)+AppleWebKit/537.36+(KHTML,+like+Gecko)+Chrome/128.0.0.0+Safari/537.36&visitor_id=0191C24E02BB020194033CAE101E6A63";
        // Response response = RestAssured
        //                 .given()
        //                 .header("User-Agent", "Mozilla/5.0")
        //                 .get(url);
            
        // // You can extract the entire response as a string
        // String responseBody = response.getBody().asString();
        // System.out.println("III"+responseBody);
       
        // try {
            
        //     logger.info("Requesting Target API with URL: " + url);

        //     if (response.getStatusCode() == 200) {
        //         // Parse the response body
        //         String jsonResponse = response.getBody().asString();
        //         JSONObject jsonObject = new JSONObject(jsonResponse);

        //         // Extract "products" array from the response
        //         JSONArray productArray = jsonObject.getJSONObject("data").getJSONArray("products");

        //         for (int i = 0; i < productArray.length(); i++) {
        //             JSONObject productJson = productArray.getJSONObject(i).getJSONObject("item");

        //             // Extract necessary details for each product
        //             String title = productJson.getJSONObject("product_description").getString("title");
        //             String imageUrl = productJson.getJSONObject("enrichment").getJSONObject("images").getString("primary_image_url");
        //             double price = productJson.getJSONObject("price").getDouble("formatted_current_price");
        //             String productUrl = productJson.getJSONObject("enrichment").getString("buy_url");

        //             // Create a Product object and add to list
        //             Product product = new Product(imageUrl, title, price, productUrl, "target");
        //             products.add(product);
        //         }

        //     } else {
        //         logger.severe("Failed to fetch data from Target API: HTTP status " + response.getStatusCode());
        //     }

        // } catch (Exception e) {
        //     logger.severe("Error fetching Target products: " + e.getMessage());
        // }

        // return products;
        return products;
    }
}
