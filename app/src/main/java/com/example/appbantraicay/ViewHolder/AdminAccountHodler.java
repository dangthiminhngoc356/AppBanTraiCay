package com.example.appbantraicay.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appbantraicay.Interface.ItemClickListener;
import com.example.appbantraicay.R;

public class AdminAccountHodler extends RecyclerView.ViewHolder {
    public TextView name, password, phone;
    public ImageView hinhanh;
    public ItemClickListener listener;

    public AdminAccountHodler(View view) {
        super(view);
        hinhanh = (ImageView) itemView.findViewById(R.id.iv_admin_account_hinhanh);
        name = (TextView) itemView.findViewById(R.id.tv_admin_taikhoan_tentk);
        password = (TextView) itemView.findViewById(R.id.tv_admin_taikhoan_password);
        phone = (TextView) itemView.findViewById(R.id.tv_admin_taikhoan_sdt);
    }
    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }


}
