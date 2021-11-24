package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbantraicay.model.ProductType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddProductTypeActivity extends AppCompatActivity {
    EditText name, descrip;
    Button save, cancel;
    String ID;
    TextView xoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_type);
        matching();
        xoa.setVisibility(View.INVISIBLE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ID = bundle.getString("ID");
            if (!ID.equals("Add")) {
                load();
                xoa.setVisibility(View.VISIBLE);
            }
            Luu();
            Xoa();
        }
    }

    private void Xoa() {
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference loaisp = FirebaseDatabase.getInstance().getReference("LoaiSP");
                loaisp.child(ID).removeValue();
                Toast.makeText(AddProductTypeActivity.this, "Đã xóa loại sản phẩm!", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(AddProductTypeActivity.this, ProductTypeAdminActivity.class));
            }
        });
    }

    private void Luu() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference loaisp = FirebaseDatabase.getInstance().getReference("LoaiSP");
                if (ID.equals("Add")) {
                    if (name.getText().equals("") || descrip.getText().equals("")) {
                        Toast.makeText(AddProductTypeActivity.this, "Hãy nhập đầy đủ các trường!", Toast.LENGTH_LONG).show();
                    } else {
                        loaisp.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int maLoaiSp = 0;
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    maLoaiSp = Integer.valueOf(data.getKey().split("LSP")[1]) + 1;
                                }
                                loaisp.child("LSP" + maLoaiSp).child("MoTa").setValue(descrip.getText().toString());
                                loaisp.child("LSP" + maLoaiSp).child("TenLoai").setValue(name.getText().toString());
                                Toast.makeText(AddProductTypeActivity.this, "Đã thêm loại sản phẩm mới!", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(AddProductTypeActivity.this, ProductTypeAdminActivity.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                } else {
                    loaisp.child(ID).child("MoTa").setValue(descrip.getText().toString());
                    loaisp.child(ID).child("TenLoai").setValue(name.getText().toString());
                    Toast.makeText(AddProductTypeActivity.this, "Đã lưu chỉnh sửa!", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(AddProductTypeActivity.this, ProductTypeAdminActivity.class));
                }
            }
        });
    }

    private void load() {
        DatabaseReference loaisp = FirebaseDatabase.getInstance().getReference("LoaiSP");
        loaisp.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                name.setText(hashMap.get("TenLoai").toString());
                descrip.setText(hashMap.get("MoTa").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void matching() {
        name = (EditText) findViewById(R.id.et_productTypeName);
        descrip = (EditText) findViewById(R.id.et_productTypeDescription);
        save = (Button) findViewById(R.id.btn_add_save_productType);
        cancel = (Button) findViewById(R.id.btn_add_cancel_productType);
        xoa = (TextView) findViewById(R.id.tv_delete_product_type);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AddProductTypeActivity.this, ProductTypeAdminActivity.class));
            }
        });
    }
}