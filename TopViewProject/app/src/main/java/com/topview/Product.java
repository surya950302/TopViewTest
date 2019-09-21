package com.topview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private  String id;
    @SerializedName("name")
    @Expose
    private  String name;
    @SerializedName("price")
    @Expose
    private  double price;
    @SerializedName("image")
    @Expose
    private  String image;
    @SerializedName("product_type")
    @Expose
    private  String type;

    /*public Product() {
        this.id = "";
        this.name= "";
        this.image= "";
        this.price=0.0;
        this.type="";
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
