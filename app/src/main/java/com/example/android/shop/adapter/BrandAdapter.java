package com.example.android.shop.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.shop.R;
import com.example.android.shop.model.Brand;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    List<Brand> brandList;

    public BrandAdapter(List<Brand> brandList) {
        this.brandList=brandList;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_list,viewGroup,false);
        return new BrandViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder brandViewHolder, int i) {
        Brand brand=  brandList.get(i);
        brandViewHolder.name.setText(brand.getName());
        brandViewHolder.desc.setText(brand.getDesc());

    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

   class BrandViewHolder extends RecyclerView.ViewHolder
   {

       private TextView name;
       private TextView desc;
       public BrandViewHolder(@NonNull View itemView) {
           super(itemView);
            name=(TextView)itemView.findViewById(R.id.brandName);
            desc=(TextView)itemView.findViewById(R.id.brandDesc);


       }
   }
}
