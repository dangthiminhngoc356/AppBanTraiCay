package com.example.appbantraicay.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbantraicay.Interface.ItemClickListener;
import com.example.appbantraicay.R;

public class AdminAccountHodler extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView name, password, phone;
    public ItemClickListener listener;

    public AdminAccountHodler(@NonNull View view) {
        super(view);

        name = (TextView) itemView.findViewById(R.id.tv_admin_taikhoan_tentk);
        password = (TextView) itemView.findViewById(R.id.tv_admin_taikhoan_password);
        phone = (TextView) itemView.findViewById(R.id.tv_admin_taikhoan_sdt);
    }
    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
