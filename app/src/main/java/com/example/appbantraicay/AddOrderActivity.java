package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;

public class AddOrderActivity extends AppCompatActivity {

    EditText tenKh, sdtKh, diachiKh, tenSp, soLuong, donGia;
    Button luu, huy;
    String input_tenKh, input_sdtKh, input_diachiKh, input_tenSp, input_soLuong, input_donGia, iddh;
    DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("DonHang");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        matching();

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    Toast.makeText(AddOrderActivity.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_sdtKh)) {
                    Toast.makeText(AddOrderActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_diachiKh)) {
                    Toast.makeText(AddOrderActivity.this, "Vui lòng nhập tên địa chỉ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_tenSp)) {
                    Toast.makeText(AddOrderActivity.this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_soLuong)) {
                    Toast.makeText(AddOrderActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_donGia)) {
                    Toast.makeText(AddOrderActivity.this, "Vui lòng nhập đơn giá", Toast.LENGTH_SHORT).show();
                } else {
                    validateOrder();
                }
            }
        });
    }

    private void validateOrder() {
        Task<AuthResult> task = FirebaseAuth.getInstance().signInAnonymously();
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int soluongdh = (int) snapshot.getChildrenCount() + 1;
                iddh = "DH" + soluongdh;
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddOrderActivity.this, "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Task<Uri> uriTask = task.continueWithTask(new Continuation<AuthResult, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<AuthResult> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                return null;
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {

                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveOrder() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ID", iddh);
        map.put("Ten", input_tenKh);
        map.put("SDT", input_sdtKh);
        map.put("Diachi", input_diachiKh);
        map.put("TenSP", input_tenSp);
        map.put("SoLuong", input_soLuong);
        map.put("DonGia", input_donGia);
        Ref.child(iddh).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(AddOrderActivity.this, "Thêm đơn hàng thất bại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddOrderActivity.this, "Thêm đơn hàng thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void matching() {
        tenKh = (EditText) findViewById(R.id.et_themdonhang_ten);
        sdtKh = (EditText) findViewById(R.id.et_themdonhang_sdt);
        diachiKh = (EditText) findViewById(R.id.et_themdonhang_diachi);
        tenSp = (EditText) findViewById(R.id.et_themdonhang_tensp);
        soLuong = (EditText) findViewById(R.id.et_themdonhang_soluong);
        donGia = (EditText) findViewById(R.id.et_themdonhang_dongia);
        luu = (Button) findViewById(R.id.btn_themdonhang_luu);
        huy = (Button) findViewById(R.id.btn_themdonhang_huy);
    }
}