package com.jacob.model;

public class Product {
    private String pictureUrl;
    private String title;
    private double price;
    private String link;
    private String source;

    public Product(String pictureUrl, String title, double price, String link, String source) {
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.price = price;
        this.link = link;
        this.source = source;
    }

    // Getters and setters

    public String getSource() {
        return source;
    }

    public void setSource(String link) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pictureUrl='" + pictureUrl + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", link='" + link + '\''   +
                ", source='" + source + '\''   +
                '}';
    }
}
