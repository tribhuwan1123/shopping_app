package com.example.android.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.shop.adapter.CartAdapter;
import com.example.android.shop.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    int count =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        final String pName,price,desc;
        final int image;
        Button addToCart,save;


        addToCart=(Button)findViewById(R.id.addToCart);
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        TextView displayName,displayPrice,displayDescription;
        final ImageView imageView;

        imageView=(ImageView)findViewById(R.id.imageView);
        image=getIntent().getIntExtra("image",404);
        imageView.setImageResource(image);

        displayName=(TextView)findViewById(R.id.displayName);
        pName=getIntent().getStringExtra("name");
        displayName.setText(pName);

        displayPrice=(TextView)findViewById(R.id.displayPrice);
        price=getIntent().getStringExtra("price");
        displayPrice.setText(price);

        displayDescription=(TextView)findViewById(R.id.displayDesc);
        desc=getIntent().getStringExtra("description");
        displayDescription.setText(desc);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent= new Intent(DisplayActivity.this, CartActivity.class);
//                intent.putExtra("name",pName);
//                intent.putExtra("price",price);
//                intent.putExtra("image",image);
//                startActivity(intent);

            }
        });

    }
}
