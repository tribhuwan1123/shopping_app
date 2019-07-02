package com.example.android.shop.BundleExample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.shop.R;

public class FirstActivity extends AppCompatActivity {

    EditText name,address,phone;
    Button submitFirst,submitSecond;
    String eName,eAddress,ePhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        name=(EditText)findViewById(R.id.fName);
        address=(EditText)findViewById(R.id.fAddress);
        phone=(EditText) findViewById(R.id.fPhone);

        submitFirst=(Button)findViewById(R.id.submit);
        submitSecond=(Button)findViewById(R.id.submitSecond);


        submitFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FirstActivity.this,SecondActivity.class);

                eName=name.getText().toString();
                eAddress=address.getText().toString();
                ePhone=phone.getText().toString();

                intent.putExtra("name",eName);
                intent.putExtra("address",eAddress);
                intent.putExtra("phone",ePhone);

                startActivity(intent);


            }
        });
        submitSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(FirstActivity.this,ThirdActivity.class);

                eName=name.getText().toString();
                eAddress=address.getText().toString();
                ePhone=phone.getText().toString();

                intent.putExtra("name",eName);
                intent.putExtra("address",eAddress);
                intent.putExtra("phone",ePhone);

                startActivity(intent);
            }

        });

    }
}
