package com.example.android.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.shop.Helper.CartHelper;
import com.example.android.shop.adapter.CartAdapter;
import com.example.android.shop.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<Cart> cartList = new ArrayList<>();
    String pname;
    String pPrice;
    int pImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvCart);

        pname = getIntent().getStringExtra("name");
        pPrice = getIntent().getStringExtra("price");
        pImage = getIntent().getIntExtra("image", 0);
        byte img=(byte) pImage;
       // Bitmap b = BitmapFactory.decodeResource(getResources(), pImage);



        CartHelper cartHelper = new CartHelper(getApplicationContext(), null, null, 0);
        cartHelper.insertCart(pname, pPrice, img);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        cartList=cartHelper.getAllProducts();
        CartAdapter cartAdapter = new CartAdapter(cartList);
        recyclerView.setAdapter(cartAdapter);
    }
}