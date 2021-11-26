package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.appbantraicay.ViewHolder.ProductTypeAdapter;
import com.example.appbantraicay.model.ProductType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductTypeAdminActivity extends AppCompatActivity {

    RecyclerView recycle;
    RecyclerView.Adapter adapter;
    Button trove, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_type_admin);
        matching();
        Load();
        Them();
    }

    private void Load() {
        ArrayList<ProductType> list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(linearLayoutManager);
        DatabaseReference loaisp = FirebaseDatabase.getInstance().getReference("LoaiSP");
        loaisp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) data.getValue();
                    list.add(new ProductType(data.getKey(), hashMap.get("TenLoai").toString(), hashMap.get("MoTa").toString()));
                }
                adapter = new ProductTypeAdapter(ProductTypeAdminActivity.this, list);
                recycle.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Them() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductTypeAdminActivity.this, AddProductTypeActivity.class);
                intent.putExtra("ID", "Add");
                startActivity(intent);
            }
        });
    }

    private void matching() {
        recycle = (RecyclerView) findViewById(R.id.rcv_list_productType);
        trove = (Button) findViewById(R.id.btn_admin_cancel_productType);
        add = (Button) findViewById(R.id.btn_admin_add_productType);
        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}