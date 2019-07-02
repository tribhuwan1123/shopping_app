package com.example.android.shop;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.shop.adapter.DynamicAdapter;
import com.example.android.shop.model.Questions;

import java.util.List;

public class DynamicActivity extends AppCompatActivity {
    List<Questions> questionsList;
    ProgressDialog progressDialog= null;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        recyclerView=(RecyclerView)findViewById(R.id.rvDynamic);
        new DynamicAsyncTask(DynamicActivity.this, new IParser() {
            @Override
            public void onPreExecute() {
            progressDialog= new ProgressDialog(DynamicActivity.this);
            progressDialog.show();

            }

            @Override
            public void onPostExecute(List<Questions> resultArrayList) {
                questionsList=resultArrayList;
                LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                DynamicAdapter dynamicAdapter= new DynamicAdapter(questionsList,getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(dynamicAdapter);
                progressDialog.hide();
                Toast.makeText(DynamicActivity.this, "Questions Loaded Successfully", Toast.LENGTH_LONG).show();
            }


            @Override
            public void onPostFailure() {

            }
        }).execute();
    }
}
