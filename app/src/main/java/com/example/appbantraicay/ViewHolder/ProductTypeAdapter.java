package com.example.appbantraicay.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbantraicay.AddProductTypeActivity;
import com.example.appbantraicay.ProductTypeAdminActivity;
import com.example.appbantraicay.R;
import com.example.appbantraicay.model.ProductType;

import java.util.ArrayList;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.ViewHolder> {
    ArrayList<ProductType> list;
    Context myContext;
    public ProductTypeAdapter(Context context, ArrayList<ProductType> list){
        this.list = list;
        this.myContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_type_viewholder, parent, false);
        return new ProductTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductType pro = list.get(position);
        holder.name.setText(pro.getName());
        holder.description.setText(pro.getDescription());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, AddProductTypeActivity.class);
                intent.putExtra("ID", pro.getId());
                myContext.startActivity(intent);
                ((ProductTypeAdminActivity)myContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layout;
        TextView name, description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_product_type);
            name = itemView.findViewById(R.id.tv_name_viewholder_productType);
            description = itemView.findViewById(R.id.tv_description_viewholder_productType);
        }
    }
}
