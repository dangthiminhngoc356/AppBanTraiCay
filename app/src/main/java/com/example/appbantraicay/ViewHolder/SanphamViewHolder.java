package com.example.appbantraicay.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbantraicay.Interface.ItemClickListener;
import com.example.appbantraicay.R;

public class SanphamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tensp, mota, dongia;
    public ImageView hinhanh;
    public ItemClickListener listener;

    public SanphamViewHolder(@NonNull View itemView) {
        super(itemView);

        hinhanh = (ImageView) itemView.findViewById(R.id.iv_admin_sanpham_hinhanh);
        tensp = (TextView) itemView.findViewById(R.id.txt_admin_sanpham_tensp);
        mota = (TextView) itemView.findViewById(R.id.txt_admin_sanpham_mota);
        dongia = (TextView) itemView.findViewById(R.id.txt_admin_sanpham_dongia);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
