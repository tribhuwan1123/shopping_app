package com.example.android.shop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.shop.Fragment.CartFragment;
import com.example.android.shop.Fragment.HomeFragment;
import com.example.android.shop.Fragment.SearchFragment;
import com.example.android.shop.R;

public class AdapterFragmentPager extends FragmentPagerAdapter {


    public AdapterFragmentPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                return new HomeFragment();

            case 1:
                return new CartFragment();
            case 3:
                return new SearchFragment();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
