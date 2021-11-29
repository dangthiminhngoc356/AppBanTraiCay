package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appbantraicay.ViewHolder.NhaPhanPhoiViewHolder;
import com.example.appbantraicay.model.NhaPhanPhoi;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class second_main extends AppCompatActivity {
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("NhaPhanPhoi");
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button themnpp, huy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);
        matching();
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<NhaPhanPhoi> options = new FirebaseRecyclerOptions.Builder<NhaPhanPhoi>().setQuery(ref, NhaPhanPhoi.class).build();
        FirebaseRecyclerAdapter<NhaPhanPhoi, NhaPhanPhoiViewHolder> adapter = new FirebaseRecyclerAdapter<NhaPhanPhoi, NhaPhanPhoiViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NhaPhanPhoiViewHolder holder, int position, @NonNull NhaPhanPhoi model) {
                holder.id.setText("id: " + model.getId());
                holder.diachi.setText("Địa chỉ: " + model.getDiaChi());
                holder.email.setText("Email: " + model.getEmail());
                holder.ngay.setText("Ngày: " + model.getNgay());
                holder.sdt.setText("Số điện thoại: " + model.getSDT());
                holder.soluong.setText("Số lượng: " + model.getSoLuong());
                holder.tensp.setText("Tên sản phẩm: " + model.getTenSP());
                holder.tennpp.setText("Tên nhà phân phối: " + model.getTenNPP());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(second_main.this, CapNhatNhaPhanPhoiActivity.class);
                        intent.putExtra("ID", model.getId());
                        startActivity(intent);
                    }
                });
            }
            @NonNull
            @Override
            public NhaPhanPhoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nhaphanphoi_admin, parent, false);
                NhaPhanPhoiViewHolder holder = new NhaPhanPhoiViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void matching() {
        themnpp=(Button) findViewById(R.id.btn_Themnhaphanphoi);
        huy = (Button) findViewById(R.id.btn_Huy);
        recyclerView = (RecyclerView) findViewById(R.id.v_NhaPhanPhoi);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        themnpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(second_main.this, ThemNhaPhanPhoiActivity.class);
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

