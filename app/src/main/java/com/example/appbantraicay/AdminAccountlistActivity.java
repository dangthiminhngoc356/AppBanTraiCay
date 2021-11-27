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

import com.example.appbantraicay.ViewHolder.AdminAccountHodler;
import com.example.appbantraicay.model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminAccountlistActivity extends AppCompatActivity {
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Button themtk, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_accountlist);
        matching();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Users>options= new FirebaseRecyclerOptions.Builder<Users>().setQuery(ref,Users.class).build();
        FirebaseRecyclerAdapter<Users, AdminAccountHodler> adapter = new FirebaseRecyclerAdapter<Users, AdminAccountHodler>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminAccountHodler holder, int position, @NonNull Users model) {
                holder.name.setText("name: " + model.getName());
                holder.password.setText("password: " + model.getPassword());
                holder.phone.setText("phone: " + model.getPhone());
                Picasso.get().load(model.gethinhanh()).into(holder.hinhanh);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminAccountlistActivity.this, UpdateAccountActivity.class);
                        intent.putExtra("phone", model.getPhone());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public AdminAccountHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_admin_layout, parent, false);
                AdminAccountHodler holder = new AdminAccountHodler(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void matching() {
        themtk = (Button) findViewById(R.id.btn_account_themtk);
        recyclerView = (RecyclerView) findViewById(R.id.rcv_admin_taikhoan);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        home = (Button) findViewById(R.id.btn_account_menu);

        themtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminAccountlistActivity.this, AdminAccountActivity.class);
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