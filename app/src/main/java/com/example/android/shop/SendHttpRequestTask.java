package com.example.android.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;

    public SendHttpRequestTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override

    protected Bitmap doInBackground(String... strings) {
        String urlDisplay = strings[0];
        Bitmap bitmap = null;

        try {
            InputStream inputStream = new URL(urlDisplay).openStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
