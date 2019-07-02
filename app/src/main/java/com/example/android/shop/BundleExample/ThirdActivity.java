package com.example.android.shop.BundleExample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.shop.R;

public class ThirdActivity extends AppCompatActivity {

    TextView tName,tAddress,tPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        tName=findViewById(R.id.tName);
        tAddress=findViewById(R.id.tAddress);
        tPhone=findViewById(R.id.tPhone);

        Bundle bundle= getIntent().getExtras();

        tName.setText(bundle.getString("name"));
        tAddress.setText(bundle.getString("address"));
        tPhone.setText(bundle.getString("phone"));
    }
}
