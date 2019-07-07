package com.example.android.shop.adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.shop.R;
import com.example.android.shop.model.Cart;
import com.example.android.shop.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<Cart> cartList;

    public CartAdapter(List<Cart> cartList) {
    this.cartList=cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_items,viewGroup,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {
        Cart cart= cartList.get(i);
        cartViewHolder.pName.setText(cart.getpName());
        cartViewHolder.price.setText(cart.getpPrice());
      //  cartViewHolder.imageCart.setImageBitmap(cart.getImageCart());
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder
    {
        TextView pName,price;
        //ImageView imageCart;
        ImageButton remove;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            pName=(TextView)itemView.findViewById(R.id.pName);
            price=(TextView)itemView.findViewById(R.id.pPrice);
           // imageCart=(ImageView)itemView.findViewById(R.id.imageCart);
            remove=(ImageButton)itemView.findViewById(R.id.remove);

        }
    }
}
