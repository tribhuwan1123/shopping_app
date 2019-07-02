package com.example.android.shop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.shop.model.Product;

public class ProductEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_entry);

       final EditText name,price,desc;
        Button save;

        name=(EditText)findViewById(R.id.name);
        price=(EditText)findViewById(R.id.price);
        desc= (EditText)findViewById(R.id.desc);
        save=(Button)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Product product= new Product();
                Intent intent= new Intent(ProductEntry.this,AsyncExampleActivity.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("price",price.getText().toString());
                intent.putExtra("desc",desc.getText().toString());
                startActivity(intent);
            }
        });
    }
}
