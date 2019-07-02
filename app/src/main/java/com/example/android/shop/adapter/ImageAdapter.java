package com.example.android.shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.shop.R;

public class ImageAdapter extends PagerAdapter {
        Context context;

        public ImageAdapter(Context context)
        {
            this.context=context;
        }

    private int[] image= new int[]{R.drawable.honor,R.drawable.dress,R.drawable.goldstar,R.drawable.laptop};
        @Override
        public Object instantiateItem(ViewGroup container,int position)
        {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(image[position]);
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==((ImageView)o);
    }
}
