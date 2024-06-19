package com.jacob;

public class Product {
    private String pictureUrl;
    private String title;
    private String price;

    public Product(String pictureUrl, String title, String price) {
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.price = price;
    }

    // Getters and setters
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pictureUrl='" + pictureUrl + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
