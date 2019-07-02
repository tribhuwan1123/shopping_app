package com.example.android.shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.shop.model.ImageLoader;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GoogleDetail extends AppCompatActivity {

    TextView name, email,gName;
    Button logout, fbLogout;
    ImageView imageFB;
    String googleName, gEmail, firstName, lastName;
    String userFrom,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_detail);
        name = (TextView) findViewById(R.id.name);
        gName=(TextView)findViewById(R.id.gName);
        email = (TextView) findViewById(R.id.email);
        imageFB = (ImageView) findViewById(R.id.imageFB);
        logout = (Button) findViewById(R.id.logout);
        fbLogout = (Button) findViewById(R.id.fbLogout);
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        googleName= getIntent().getStringExtra("name");
        gName.setText(googleName);
       image= getIntent().getStringExtra("image");
        Glide.with(GoogleDetail.this).load(image).into(imageFB);



        gEmail = getIntent().getStringExtra("email");
        email.setText(gEmail);
        userFrom = getIntent().getStringExtra("userFrom");
        System.out.println("userFrom " + userFrom);
        if (userFrom.equals("google")) {
            logout.setVisibility(View.VISIBLE);
            gName.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Auth.GoogleSignInApi.signOut(LoginActivity.googleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            Intent intent = new Intent(GoogleDetail.this, LoginActivity.class);
                            startActivity(intent);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            finish();
                        }
                    });
                }
            });
        } else if (userFrom.equals("fb")) {
            fbLogout.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            name.setText(firstName + " " + lastName);
            fbLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(GoogleDetail.this, LoginActivity.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                }
            });
        }

    }

    @Override
    protected void onStart() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        LoginActivity.googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
        LoginActivity.googleApiClient.connect();
        super.onStart();
    }
}
