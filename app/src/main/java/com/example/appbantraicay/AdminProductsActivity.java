package com.example.appbantraicay;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbantraicay.ViewHolder.SanphamViewHolder;
import com.example.appbantraicay.model.SanPham;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminProductsActivity extends AppCompatActivity {
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SanPham");
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button themsp, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products);

        matching();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<SanPham> options = new FirebaseRecyclerOptions.Builder<SanPham>().setQuery(ref, SanPham.class).build();
        FirebaseRecyclerAdapter<SanPham, SanphamViewHolder> adapter = new FirebaseRecyclerAdapter<SanPham, SanphamViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SanphamViewHolder holder, int position, @NonNull SanPham model) {
                holder.tensp.setText("Tên sản phẩm: " + model.getTen());
                holder.dongia.setText("Đơn giá: " + model.getDonGia());
                holder.mota.setText("Mô tả: " + model.getMoTa());
                Picasso.get().load(model.getHinhAnh()).into(holder.hinhanh);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminProductsActivity.this, UpdateProductActivity.class);
                        intent.putExtra("id", model.getid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public SanphamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sanpham_admin, parent, false);
                SanphamViewHolder holder = new SanphamViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void matching() {
        themsp = (Button) findViewById(R.id.btn_sanpham_themsp);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_admin_sanpham);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        home = (Button) findViewById(R.id.btn_sanpham_menu);

        themsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminProductsActivity.this, AddNewProductActivity.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}