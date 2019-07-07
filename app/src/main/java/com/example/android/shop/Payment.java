package com.example.android.shop;

import android.media.audiofx.DynamicsProcessing;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import khalti.checkOut.api.Config;
import khalti.checkOut.api.OnCheckOutListener;
import khalti.checkOut.helper.KhaltiCheckOut;
import khalti.utils.Constant;
import khalti.widget.KhaltiButton;

import static android.media.audiofx.DynamicsProcessing.*;

public class Payment extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final KhaltiButton khaltiButton=(KhaltiButton)findViewById(R.id.khaltiPay);

        HashMap<String, String> map = new HashMap<>();
        map.put("merchant_extra", "This is extra data");

        Config config = new Config(Constant.pub, "Product ID", "Product Name", "Product Url", 1100L, map, new OnCheckOutListener() {

            @Override
            public void onSuccess(HashMap<String, Object> data) {
                Log.i("Payment confirmed", data + "");
            }

            @Override
            public void onError(String action, String message) {
                Log.i(action, message);
            }
        });

        khaltiButton.setCheckOutConfig(config);

        final KhaltiCheckOut khaltiCheckOut= new KhaltiCheckOut(this,config);
        khaltiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            khaltiCheckOut.show();
            }
        });


    }
}
