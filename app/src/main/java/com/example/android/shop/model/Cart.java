package com.example.android.shop.model;

import android.graphics.Bitmap;

public class Cart {

    private String pName,pPrice;
    private Bitmap imageCart;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpPrice() {
        return pPrice;
    }

    public void setpPrice(String pPrice) {
        this.pPrice = pPrice;
    }

    public Bitmap getImageCart() {
        return imageCart;
    }

    public void setImageCart(Bitmap imageCart) {
        this.imageCart = imageCart;
    }
}
