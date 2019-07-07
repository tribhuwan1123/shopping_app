package com.example.android.shop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.shop.BottomNavigation;
import com.example.android.shop.DisplayActivity;
import com.example.android.shop.Helper.CartHelper;
import com.example.android.shop.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DisplayFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_display,container,false);


        final String pName,price,desc;
        final int image;
        Button addToCart,save;


        addToCart=(Button)view.findViewById(R.id.addToCart);
        save=(Button)view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Bundle bundle=this.getArguments();

        final TextView displayName,displayPrice,displayDescription;
        final ImageView imageView;

        imageView=(ImageView)view.findViewById(R.id.imageView);
        image=bundle.getInt("image");
        imageView.setImageResource(image);

        displayName=(TextView)view.findViewById(R.id.displayName);
        pName=bundle.getString("name");
        displayName.setText(pName);

        displayPrice=(TextView)view.findViewById(R.id.displayPrice);
        price=bundle.getString("price");
        displayPrice.setText(price);

        displayDescription=(TextView)view.findViewById(R.id.displayDesc);
        desc=bundle.getString("description");
        displayDescription.setText(desc);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle1= new Bundle();
//                bundle1.putString("name",displayName.getText().toString());
//                bundle1.putString("price",displayPrice.getText().toString());
////                bundle.putInt("image",image);
////                bundle.putString("description",desc);
              Fragment cartFragment= new CartFragment();
//                cartFragment.setArguments(bundle1);
             ((BottomNavigation)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,cartFragment,"CartFragment").commit();


                final CartHelper cartHelper = new CartHelper(getApplicationContext(), null, null, 0);
                cartHelper.insertCart(displayName.getText().toString(),displayPrice.getText().toString());

            }
        });
        return view;
    }
}
