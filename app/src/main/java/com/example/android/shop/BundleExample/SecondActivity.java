package com.example.android.shop.BundleExample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.shop.R;

public class SecondActivity extends AppCompatActivity {

    TextView sName,sAddress,sPhone;
    String name,address,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        sName=findViewById(R.id.sName);
        sAddress=findViewById(R.id.sAddress);
        sPhone=findViewById(R.id.sPhone);

        Bundle bundle= getIntent().getExtras();
        name=bundle.getString("name");
        sName.setText(bundle.getString("name"));
        sAddress.setText(bundle.getString("address"));
        sPhone.setText(bundle.getString("phone"));

    }
}
