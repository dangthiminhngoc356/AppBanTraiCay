package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.sql.Ref;
import java.util.HashMap;

public class UpdateOrderActivity extends AppCompatActivity {

    EditText tenKh, sdtKh, diachiKh, tenSp, soLuong, donGia;
    Button luu, huy, xoa;
    String input_tenKh, input_sdtKh, input_diachiKh, input_tenSp, input_soLuong, input_donGia, iddh;
    DatabaseReference Ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);

        matching();
        thongTinSanPham();

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(UpdateOrderActivity.this, AdminOrderActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(UpdateOrderActivity.this, "Xóa đơn hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_tenKh = tenKh.getText().toString();
                input_sdtKh = sdtKh.getText().toString();
                input_diachiKh = diachiKh.getText().toString();
                input_tenSp = tenSp.getText().toString();
                input_soLuong = soLuong.getText().toString();
                input_donGia = donGia.getText().toString();
                if (TextUtils.isEmpty(input_tenKh)) {
                    Toast.makeText(UpdateOrderActivity.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_sdtKh)) {
                    Toast.makeText(UpdateOrderActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_diachiKh)) {
                    Toast.makeText(UpdateOrderActivity.this, "Vui lòng nhập tên địa chỉ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_tenSp)) {
                    Toast.makeText(UpdateOrderActivity.this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_soLuong)) {
                    Toast.makeText(UpdateOrderActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_donGia)) {
                    Toast.makeText(UpdateOrderActivity.this, "Vui lòng nhập đơn giá", Toast.LENGTH_SHORT).show();
                } //else {
//                    validateOrder();
//                }
//            }
//        });
//
//    }

//    private void validateOrder() {
//        Task<AuthResult> task = FirebaseAuth.getInstance().signInAnonymously();
//        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int soluongdh = (int) snapshot.getChildrenCount() + 1;
//                iddh = "DH" + soluongdh;
//                task.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(UpdateOrderActivity.this, "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
//                    }
//                });
//                task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        Task<Uri> uriTask = task.continueWithTask(new Continuation<AuthResult, Task<Uri>>() {
//                            @Override
//                            public Task<Uri> then(@NonNull Task<AuthResult> task) throws Exception {
//                                if (!task.isSuccessful()) {
//                                    throw task.getException();
//                                }
//                                return null;
//                            }
//                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                if (task.isSuccessful()) {
//                                    saveOrder();
//                                }
//                            }
//                        });
                    }
                });
            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

//    private void saveOrder() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("ID", iddh);
//        map.put("Ten", input_tenKh);
//        map.put("SDT", input_sdtKh);
//        map.put("Diachi", input_diachiKh);
//        map.put("TenSP", input_tenSp);
//        map.put("SoLuong", input_soLuong);
//        map.put("DonGia", input_donGia);
//        Ref.child(iddh).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (!task.isSuccessful()) {
//                    Toast.makeText(UpdateOrderActivity.this, "Sửa đơn hàng thất bại", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(UpdateOrderActivity.this, "Sửa đơn hàng thành công", Toast.LENGTH_LONG).show();
//                    finish();
//                }
//            }
//        });
//            }

            private void thongTinSanPham() {
                Ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String xtenkh = snapshot.child("Ten").getValue().toString();
                            String xsdt = snapshot.child("SDT").getValue().toString();
                            String xdiachi = snapshot.child("Diachi").getValue().toString();
                            String xtensp = snapshot.child("TenSP").getValue().toString();
                            String xsoluong = snapshot.child("SoLuong").getValue().toString();
                            String xdongia = snapshot.child("DonGia").getValue().toString();

                            tenKh.setText(xtenkh);
                            sdtKh.setText(xsdt);
                            diachiKh.setText(xdiachi);
                            tenSp.setText(xtensp);
                            soLuong.setText(xsoluong);
                            donGia.setText(xdongia);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void matching() {
                iddh = getIntent().getStringExtra("ID");
                Ref = FirebaseDatabase.getInstance().getReference().child("DonHang").child(iddh);
                tenKh = (EditText) findViewById(R.id.et_suadonhang_ten);
                sdtKh = (EditText) findViewById(R.id.et_suadonhang_sdt);
                diachiKh = (EditText) findViewById(R.id.et_suadonhang_diachi);
                tenSp = (EditText) findViewById(R.id.et_suadonhang_tensp);
                soLuong = (EditText) findViewById(R.id.et_suadonhang_soluong);
                donGia = (EditText) findViewById(R.id.et_suadonhang_dongia);
                luu = (Button) findViewById(R.id.btn_suadonhang_luu);
                huy = (Button) findViewById(R.id.btn_suadonhang_huy);
                xoa = (Button) findViewById(R.id.btn_suadonhang_xoa);
            }
        }