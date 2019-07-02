package com.example.android.shop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.android.shop.R;

import com.bumptech.glide.request.RequestOptions;
import com.example.android.shop.Fragment.HomeFragment;
import com.example.android.shop.model.ImageLoader;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    ImageView googleIcon, facebookIcon;
    ImageView imageFB;
    SignInButton googleLogin;
    ImageLoader imageLoader;
    LoginButton facebookLogin;
    String firstNameFB, lastNameFB, userEmailFB, imageUrl;

    public static GoogleApiClient googleApiClient;
    int REQ_CODE = 1;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        googleIcon = findViewById(R.id.googleIcon);
        facebookIcon = findViewById(R.id.facebookIcon);
        googleLogin = (SignInButton) findViewById(R.id.googleLogin);
        facebookLogin = (LoginButton) findViewById(R.id.facebookLogin);
        imageFB = (ImageView) findViewById(R.id.imageFB);



        facebookLogin.setReadPermissions("email", "public_profile");

        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Connection Successfull", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "There has been an error", Toast.LENGTH_SHORT).show();
            }
        });

        googleLogin.setOnClickListener(this);

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();


    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {
                Toast.makeText(LoginActivity.this, "User Logged Out", Toast.LENGTH_SHORT).show();
            } else {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken currentAccessToken) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    firstNameFB = object.getString("first_name");
                    lastNameFB = object.getString("last_name");
                    userEmailFB = object.getString("email");
                    String userIdFB = object.getString("id");

                    imageUrl = "https://graph.facebook.com/" + userIdFB + "/picture?type=normal";
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    // Glide.with(LoginActivity.this).load(imageUrl).into(imageFB);

                    System.out.println("firstNameFB " + firstNameFB + " " + lastNameFB);
                    Intent intent = new Intent(LoginActivity.this, BottomNavigation.class);
                    intent.putExtra("firstName",firstNameFB);
                    intent.putExtra("lastName",lastNameFB);
                    intent.putExtra("email",userEmailFB);
                    intent.putExtra("image", imageUrl);
                    intent.putExtra("userFrom","fb");
                    startActivity(intent);
                    finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,id,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE) {
            GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(signInResult);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void handleResult(GoogleSignInResult signInResult) {

        if (signInResult.isSuccess()) {
            GoogleSignInAccount account = signInResult.getSignInAccount();
            final String name = account.getDisplayName();
            final String email = account.getEmail();
            final String userId = account.getId();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("userFrom","google");
            startActivity(intent);



        }
    }

    @Override
    public void onClick(View view) {
        if (view == googleIcon) {
            signIn();
        } else if (view == facebookIcon) {
            facebookLogin.performClick();
        }
    }

}