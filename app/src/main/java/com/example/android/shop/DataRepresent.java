package com.example.android.shop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.service.autofill.FillEventHistory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.shop.adapter.BrandAdapter;
import com.example.android.shop.model.Brand;
import com.example.android.shop.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class DataRepresent extends AppCompatActivity {
    private ProgressDialog progressDialog= null;
    List<Brand> list= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_represent);

        RecyclerView rv=(RecyclerView)findViewById(R.id.rv);
        new AsyncHttp(DataRepresent.this).execute("");
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(linearLayoutManager);
        BrandAdapter brandAdapter= new BrandAdapter(list);
        rv.setAdapter(brandAdapter);



    }

    private class AsyncHttp extends AsyncTask<String,Void,Void> {

        private Context context;

        public AsyncHttp(Context context) {
            super();
            this.context=context;


        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url= new URL(" http://192.168.0.100:8090/brand");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoInput(true);
                connection.connect();
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(connection.getInputStream()))  ;
                String text= "";
                StringBuilder stringBuilder= new StringBuilder();

                while((text=bufferedReader.readLine())!=null)
                {
                    System.out.println(text);
                    stringBuilder.append(text);


                }
                System.out.println(stringBuilder);
                bufferedReader.close();
                JSONArray jsonArray= new JSONArray(stringBuilder.toString());

                for(int i=0;i<jsonArray.length();i++)
                {
                    Brand brand= new Brand();
                    brand.setId((Integer) jsonArray.getJSONObject(i).get("id"));
                    brand.setName((String) jsonArray.getJSONObject(i).get("brandName"));
                    brand.setDesc((String) jsonArray.getJSONObject(i).get("brandDisc"));
                    list.add(brand);
                }
                BrandAdapter brandAdapter= new BrandAdapter(list);

                for(Brand brand:list)
                {
                    System.out.println(brand.getName());
                    System.out.println(brand.getDesc());
                }

                List<String> brandList= new ArrayList<>();
                System.out.println(connection.getResponseCode());
                if(connection.getResponseCode()==302)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    });

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog= new ProgressDialog(this.context);
            progressDialog.setMessage("Getting Data");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
