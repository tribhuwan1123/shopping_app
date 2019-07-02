package com.example.android.shop;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.shop.Fragment.CartFragment;
import com.example.android.shop.Fragment.HomeFragment;
import com.example.android.shop.adapter.ImageAdapter;
import com.example.android.shop.adapter.ProductAdapter;
import com.example.android.shop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ProductAdapter productAdapter;
    ViewPager viewPager;
    LinearLayout linearLayout;
    DrawerLayout drawerLayout;
    private int dotsCount;
    private ImageView[] dots;
    private RecyclerView recyclerView;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navDrawer);
        recyclerView = (RecyclerView) findViewById(R.id.rvMain);
        viewPager = (ViewPager) findViewById(R.id.viewPage);
        ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this);
        viewPager.setAdapter(imageAdapter);
        linearLayout = (LinearLayout) findViewById(R.id.dots);
        dotsCount = imageAdapter.getCount();
        dots = new ImageView[dotsCount];




        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(MainActivity.this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this    , R.drawable.inactive_dots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            linearLayout.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.active_dots));




        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.inactive_dots));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.active_dots));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductName("Laptop");
        product1.setProductPrice("Rs.50000");
        product1.setDescription("Nice");
        product1.setImageView(R.drawable.laptop);

        Product product2 = new Product();
        product2.setProductName("Honor 8x");
        product2.setProductPrice("Rs.32000");
        product2.setDescription("Good camera and good for gaming");
        product2.setImageView(R.drawable.honor);

        Product product3 = new Product();
        product3.setProductName("Women Casual Wear");
        product3.setProductPrice("Rs.5000");
        product3.setDescription("Suits your looks");
        product3.setImageView(R.drawable.dress);

        Product product4 = new Product();
        product4.setProductName("Goldstar Boot");
        product4.setProductPrice("Rs.1050");
        product4.setDescription("Great product.Made in Nepal");
        product4.setImageView(R.drawable.goldstar);

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);

         productAdapter = new ProductAdapter(MainActivity.this, productList);
        recyclerView.setAdapter(productAdapter);


    }
    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

           MainActivity.this.runOnUiThread(new Runnable() {


                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.search,menu);
        MenuItem searchItem= menu.findItem(R.id.searchAction);
        SearchView searchView= (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return true;


    }
}
