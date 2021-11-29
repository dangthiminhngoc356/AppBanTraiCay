package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CapNhatNhaPhanPhoiActivity extends AppCompatActivity {
    EditText  NPPID, DIACHI, EMAIL, SDT,  NGAY, SOLUONG, TENSP, TENNPP;
    Button luu, huy, xoa;
    String input_diachiNPP, input_sdtNPP, input_emailNPP, input_tenSp, input_soLuong, input_ngayNPP, input_tennppNPP,idnpp;
    DatabaseReference Ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_nha_phan_phoi);
        matching();
        thongTinNhaPhanPhoi();
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
                        Intent intent = new Intent(CapNhatNhaPhanPhoiActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_diachiNPP = DIACHI.getText().toString();
                input_emailNPP = EMAIL.getText().toString();
                input_ngayNPP = NGAY.getText().toString();
                input_sdtNPP = SDT.getText().toString();
                input_soLuong = SOLUONG.getText().toString();
                input_tenSp = TENSP.getText().toString();
                input_tennppNPP = TENNPP.getText().toString();
                if (TextUtils.isEmpty(input_diachiNPP)) {
                    Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_emailNPP)) {
                    Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_ngayNPP)) {
                    Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Vui lòng nhập ngày", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_sdtNPP)) {
                    Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Vui lòng nhập tsố điện thoại", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_soLuong)) {
                    Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_tenSp)) {
                    Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_tennppNPP)) {
                    Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Vui lòng nhập tên nhà phân phối", Toast.LENGTH_SHORT).show();
                } else {
                    validateOrder();
                }
            }
        });
    }
    private void validateOrder() {
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int soluongnpp = (int) snapshot.getChildrenCount() + 1;
                idnpp = "id" + soluongnpp;
                saveNhaPhanPhoi();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void saveNhaPhanPhoi() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ID", idnpp);
        map.put("DiaChi", input_diachiNPP);
        map.put("Email", input_emailNPP);
        map.put("Ngay", input_ngayNPP);
        map.put("SDT", input_sdtNPP);
        map.put("SoLuong", input_soLuong);
        map.put("TenSP", input_tenSp);
        map.put("TenNPP", input_tennppNPP);
        Ref.child(idnpp).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Sửa thất bại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CapNhatNhaPhanPhoiActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
    private void thongTinNhaPhanPhoi() {
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String xdiachi = snapshot.child("DiaChi").getValue().toString();
                    String xemail = snapshot.child("Email").getValue().toString();
                    String xngay = snapshot.child("Ngay").getValue().toString();
                    String xsdt = snapshot.child("SDT").getValue().toString();
                    String xsoluong = snapshot.child("SoLuong").getValue().toString();
                    String xtensp = snapshot.child("TenSP").getValue().toString();
                    String xtennpp = snapshot.child("TenNPP").getValue().toString();

                    DIACHI.setText(xdiachi);
                    EMAIL.setText(xemail);
                    NGAY.setText(xngay);
                    SDT.setText(xsdt);
                    SOLUONG.setText(xsoluong);
                    TENSP.setText(xtensp);
                    TENNPP.setText(xtennpp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void matching() {
        idnpp = getIntent().getStringExtra("ID");
        Ref = FirebaseDatabase.getInstance().getReference().child("NhaPhanPhoi").child(idnpp);
        DIACHI = (EditText) findViewById(R.id.et_diachi);
        EMAIL = (EditText) findViewById(R.id.et_email);
        SDT = (EditText) findViewById(R.id.et_phone);
        NGAY = (EditText) findViewById(R.id.et_date);
        SOLUONG = (EditText) findViewById(R.id.et_soluong);
        TENSP = (EditText) findViewById(R.id.et_tensp);
        TENNPP = (EditText) findViewById(R.id.et_tennpp);
        luu = (Button) findViewById(R.id.btn_themnhaphanphoi_luu);
        huy = (Button) findViewById(R.id.btn_themnhaphanphoi_xoa);

    }
}

