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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
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
