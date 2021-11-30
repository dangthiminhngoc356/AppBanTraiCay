package com.example.appbantraicay.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbantraicay.Interface.ItemClickListener;
import com.example.appbantraicay.Interface.ItemClickListener;
import com.example.appbantraicay.R;


public class NhaPhanPhoiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView id, diachi, email, ngay, soluong, tensp, tennpp, sdt;
    public ItemClickListener listener;
public NhaPhanPhoiViewHolder(@NonNull View itemView){
    super(itemView);
    id = (TextView) itemView.findViewById(R.id.txt_admin_nhaphanphoi_id);
    diachi = (TextView) itemView.findViewById(R.id.txt_admin_nhaphanphoi_diachi);
    email = (TextView) itemView.findViewById(R.id.txt_admin_nhaphanphoi_email);
    ngay = (TextView) itemView.findViewById(R.id.txt_admin_nhaphanphoi_ngay);
    sdt = (TextView) itemView.findViewById(R.id.txt_admin_nhaphanphoi_sdt);
    soluong = (TextView) itemView.findViewById(R.id.txt_admin_nhaphanphoi_soluong);
    tensp = (TextView) itemView.findViewById(R.id.txt_admin_nhaphanphoi_tensp);
    tennpp = (TextView) itemView.findViewById(R.id.txt_admin_nhaphanphoi_tennpp);
}
    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
