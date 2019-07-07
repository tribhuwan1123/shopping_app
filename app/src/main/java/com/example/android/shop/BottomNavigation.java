package com.example.android.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.shop.Fragment.CartFragment;
import com.example.android.shop.Fragment.HomeFragment;
import com.example.android.shop.adapter.AdapterFragmentPager;
import com.example.android.shop.adapter.ProductAdapter;


public class BottomNavigation extends AppCompatActivity {
    Fragment selectedFragment = new HomeFragment();
    String displayName, displayImage, displayEmail, userFrom, firstName, lastName;
    DrawerLayout bDrawerLayout;
    // Bundle sendUserFromActivity;
    NavigationView navigationView;
    TextView profileName, profileEmail;
    ProductAdapter productAdapter;
    // ViewPager fragmentPager;
    ImageView profileImage;
    FragmentManager fm=getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        bDrawerLayout = (DrawerLayout) findViewById(R.id.bDrawer);
        navigationView = (NavigationView) findViewById(R.id.navDrawer);
        Toolbar bToolbar = (Toolbar) findViewById(R.id.bToolbar);
        setSupportActionBar(bToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, bDrawerLayout, bToolbar, R.string.open, R.string.close);
        bDrawerLayout.addDrawerListener(toggle);

        View headerView = navigationView.getHeaderView(0);
        profileName = (TextView) headerView.findViewById(R.id.profileName);
        profileEmail = (TextView) headerView.findViewById(R.id.profileEmail);
        profileImage = (ImageView) headerView.findViewById(R.id.profileImage);


        Intent intent = getIntent();
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        displayEmail = intent.getStringExtra("email");
        displayImage = intent.getStringExtra("image");
        System.out.println("First Name: " + firstName);

        BottomNavigation.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                displayName = firstName + " " + lastName;
                profileEmail.setText(displayEmail);
                profileName.setText(displayName);

                System.out.println("checktxr " + profileName.getText().toString());
                Glide.with(BottomNavigation.this).load(displayImage).into(profileImage);
            }
        });


        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNav);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
        productAdapter=new ProductAdapter(fm);
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.cart:
                            selectedFragment = new CartFragment();
                            break;
                        case R.id.search:
                            selectedFragment = new HomeFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).addToBackStack(null).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        if (bDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            bDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
