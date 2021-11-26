package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appbantraicay.ViewHolder.DonHangViewHolder;
import com.example.appbantraicay.model.DonHang;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminOrderActivity extends AppCompatActivity {

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("DonHang");
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button themdh, huy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);
        matching();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<DonHang> options = new FirebaseRecyclerOptions.Builder<DonHang>().setQuery(ref, DonHang.class).build();
        FirebaseRecyclerAdapter<DonHang, DonHangViewHolder> adapter = new FirebaseRecyclerAdapter<DonHang, DonHangViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DonHangViewHolder holder, int position, @NonNull DonHang model) {
                holder.tensp.setText("Tên sản phẩm: " + model.getTenSP());
                holder.soluong.setText("Số lượng sản phẩm: " + model.getSoLuong());
                holder.dongia.setText("Đơn giá: " + model.getDonGia());
                holder.tennguoimua.setText("Tên người mua: " + model.getTen());
                holder.Sdt.setText("Số điện thoại: " + model.getSDT());
                holder.Diachi.setText("Địa chỉ: " + model.getDiachi());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminOrderActivity.this, UpdateOrderActivity.class);
                        intent.putExtra("ID", model.getId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_admin, parent, false);
                DonHangViewHolder holder = new DonHangViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void matching() {
        themdh = (Button) findViewById(R.id.btn_Themdonhang);
        huy = (Button) findViewById(R.id.btn_Huy);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_admin_order);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        themdh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminOrderActivity.this, AddOrderActivity.class);
                startActivity(intent);
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}