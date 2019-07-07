package com.example.android.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.shop.BottomNavigation;
import com.example.android.shop.DisplayActivity;
import com.example.android.shop.Fragment.DisplayFragment;
import com.example.android.shop.R;
import com.example.android.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.DataViewHolder> implements Filterable {

    List<Product> productList;
    List<Product> productListFull;
    Context context;
    FragmentManager fragmentManager;
      public ProductAdapter(Context context,List<Product> productList)
      {
          this.context=context;
          this.productList=productList;
          productListFull= new ArrayList<>(productList);
      }

      public ProductAdapter(FragmentManager fragmentManager)
      {
          this.fragmentManager=fragmentManager;
      }
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items,viewGroup,false);
          return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {
        final Product product= productList.get(i);
        dataViewHolder.productImage.setImageResource(product.getImageView());
        dataViewHolder.productName.setText(product.getProductName());
         dataViewHolder.productPrice.setText(product.getProductPrice());
         dataViewHolder.desc.setText(product.getDescription());

         dataViewHolder.productImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Bundle bundle= new Bundle();
                 bundle.putString("name",product.getProductName());
                 bundle.putString("price",product.getProductPrice());
                 bundle.putInt("image",product.getImageView());
                 bundle.putString("description",product.getDescription());

                 Fragment displayFragment= new DisplayFragment();
                 displayFragment.setArguments(bundle);
                 ((BottomNavigation)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, displayFragment,"DisplayFragment").addToBackStack(null).commit();

             }
         });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return productFilter;
    }
    private Filter productFilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredProduct= new ArrayList<>();
            if(constraint==null||constraint.length()==0)
            {
                    filteredProduct.addAll(productListFull);

            }
            else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for(Product item: productListFull)
                {
                    if(item.getProductName().toLowerCase().contains(filterPattern))
                    {
                        filteredProduct.add(item);
                    }
                }
            }
            FilterResults results= new FilterResults();
            results.values=filteredProduct;
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    public class DataViewHolder extends RecyclerView.ViewHolder
    {
        private TextView productName,productPrice,desc;
        private ImageView productImage;
        private CardView cardView;


        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=(ImageView) itemView.findViewById(R.id.imageView);
            productName=(TextView)itemView  .findViewById(R.id.productName);
            productPrice=(TextView)itemView  .findViewById(R.id.productPrice);
            desc=(TextView)itemView  .findViewById(R.id.description);
            cardView=(CardView) itemView.findViewById(R.id.card);

        }
    }
}
