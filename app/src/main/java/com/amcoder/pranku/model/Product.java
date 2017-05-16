package com.amcoder.pranku.model;

import java.util.ArrayList;

/**
 * Created by philips on 5/15/17.
 */

public class Product {

    String title;

    String description ;

    String price ;

    String discountPrice;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    String productID;

    ArrayList<String> urls;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public Product(String title, String description, String price, String discountPrice ,String productID) {

        this.title = title;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.productID=productID;
    }
}