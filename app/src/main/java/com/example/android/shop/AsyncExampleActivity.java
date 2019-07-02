package com.example.android.shop;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.shop.R;
import com.example.android.shop.model.Product;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncExampleActivity extends AppCompatActivity {

    private ProgressDialog progressDialog= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_example);
        final Product product= new Product();
        TextView name,price,desc;
        name=(TextView)findViewById(R.id.aName);
        price=(TextView)findViewById(R.id.aPrice);
        desc=(TextView)findViewById(R.id.aDesc);

        String dName,dPrice,dDesc;
        dName=getIntent().getStringExtra("name");
        dPrice=getIntent().getStringExtra( "price");
        dDesc=getIntent().getStringExtra("desc");

        product.setProductName(dName);
        product.setProductPrice(dPrice);
        product.setDescription(dDesc);


        Button save=(Button)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncHttp(AsyncExampleActivity.this,product).execute("Save");

            }
        });

    }
    private class AsyncHttp extends AsyncTask<String,Void,Void> {

        private Context context;
        private Product product;
        public AsyncHttp(Context context,Product product) {
            super();
            this.context=context;
            this.product=product;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url= new URL(" http://192.168.0.118:8080/save-product");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json");
                connection.setDoInput(true);
                DataOutputStream dataOutputStream= new DataOutputStream(connection.getOutputStream());
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("product_name",product.getProductName());
                jsonObject.put("brand",product.getProductPrice());
                jsonObject.put("category",product.getDescription());
                dataOutputStream.writeBytes(jsonObject.toString());
                dataOutputStream.flush();
                dataOutputStream.close();
                connection.connect();
                if(connection.getResponseCode()==201)
                {
                    Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                Toast.makeText(context, "Exception found"+e, Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog= new ProgressDialog(this.context);
            progressDialog.setMessage("Submitting Data");
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
