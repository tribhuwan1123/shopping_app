package com.example.android.shop.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.android.shop.R;

import com.example.android.shop.adapter.ImageAdapter;
import com.example.android.shop.adapter.ProductAdapter;
import com.example.android.shop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment{

    ViewPager viewPager;
    LinearLayout linearLayout;
    NavigationView navigationView;
    private int dotsCount;
    DrawerLayout drawerLayout;
    private ImageView[] dots;
    private RecyclerView recyclerView;
    Activity activity;
    ProductAdapter productAdapter;
    TextView profileName, profileEmail;
    ImageView profileImage;

    String displayName, displayEmail, displayImage, userFrom, firstName, lastName;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "Must implement");
        } catch (NullPointerException ex) {
            throw new NullPointerException(activity.toString());
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.activity_main, container, false);
        drawerLayout = view.findViewById(R.id.drawerLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvMain);
        viewPager = (ViewPager) view.findViewById(R.id.viewPage);
        linearLayout = (LinearLayout) view.findViewById(R.id.dots);

        navigationView = view.findViewById(R.id.navDrawer);

        View headerView = navigationView.getHeaderView(0);
        profileName = (TextView) headerView.findViewById(R.id.profileName);
        profileEmail = (TextView) headerView.findViewById(R.id.profileEmail);
        profileImage = (ImageView) headerView.findViewById(R.id.profileImage);

        ImageAdapter imageAdapter = new ImageAdapter(getActivity());

        viewPager.setAdapter(imageAdapter);

        dotsCount = imageAdapter.getCount();

        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.inactive_dots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            linearLayout.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.active_dots));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.inactive_dots));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.active_dots));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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

        productAdapter = new ProductAdapter(getActivity(), productList);
        recyclerView.setAdapter(productAdapter);


        return view;
    }


    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            activity.runOnUiThread(new Runnable() {


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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.searchAction);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return true;
            }

        });

    }
}
