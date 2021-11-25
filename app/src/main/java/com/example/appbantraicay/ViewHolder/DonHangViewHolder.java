package com.example.appbantraicay.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbantraicay.Interface.ItemClickListener;
import com.example.appbantraicay.R;

public class DonHangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView tensp, soluong, dongia, tennguoimua, Sdt, Diachi;
    public ItemClickListener listener;

    public DonHangViewHolder(@NonNull View itemView){
        super(itemView);
        tensp = (TextView) itemView.findViewById(R.id.txt_admin_donhang_tensp);
        soluong = (TextView) itemView.findViewById(R.id.txt_admin_donhang_soluong);
        dongia = (TextView) itemView.findViewById(R.id.txt_admin_donhang_dongia);
        tennguoimua = (TextView) itemView.findViewById(R.id.txt_admin_donhang_tennguoimua);
        Sdt = (TextView) itemView.findViewById(R.id.txt_admin_donhang_sdt);
        Diachi = (TextView) itemView.findViewById(R.id.txt_admin_donhang_diachi);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
