package com.example.smallbasket.model;

import java.io.Serializable;

public class Grocery implements Serializable {

    String imgUrl,ProductName,Description,Measurement,gid;
    int ProductStock,ProductPrice,Order;
    public Grocery(){

    }

    public Grocery(String imgUrl, String productName, String description, String measurement, String gid, int productStock, int productPrice) {
        this.imgUrl = imgUrl;
        ProductName = productName;
        Description = description;
        Measurement = measurement;
        this.gid = gid;
        ProductStock = productStock;
        ProductPrice = productPrice;
    }
    public Grocery(String imgUrl, String productName, String description, String measurement, String gid, int productStock, int productPrice,int Order) {
        this.imgUrl = imgUrl;
        ProductName = productName;
        Description = description;
        Measurement = measurement;
        this.gid = gid;
        Order = Order;
        ProductStock = productStock;
        ProductPrice = productPrice;
    }



    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getProductStock() {
        return ProductStock;
    }

    public void setProductStock(int productStock) {
        ProductStock = productStock;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }
}