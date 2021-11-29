package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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

public class ThemNhaPhanPhoiActivity extends AppCompatActivity {
    EditText  NPPID, DIACHI, EMAIL, SDT,  NGAY, SOLUONG, TENSP, TENNPP;
    Button luu, huy;
    String input_diachiNPP, input_sdtNPP, input_emailNPP, input_tenSp, input_soLuong, input_ngayNPP, input_tennppNPP,idnpp;
    DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("NhaPhanPhoi");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nha_phan_phoi);
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
                input_diachiNPP = DIACHI.getText().toString();
                input_emailNPP = EMAIL.getText().toString();
                input_ngayNPP = NGAY.getText().toString();
                input_sdtNPP = SDT.getText().toString();
                input_soLuong = SOLUONG.getText().toString();
                input_tenSp = TENSP.getText().toString();
                input_tennppNPP = TENNPP.getText().toString();
                if (TextUtils.isEmpty(input_diachiNPP)) {
                    Toast.makeText(ThemNhaPhanPhoiActivity.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_emailNPP)) {
                    Toast.makeText(ThemNhaPhanPhoiActivity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_ngayNPP)) {
                    Toast.makeText(ThemNhaPhanPhoiActivity.this, "Vui lòng nhập ngày", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_sdtNPP)) {
                    Toast.makeText(ThemNhaPhanPhoiActivity.this, "Vui lòng nhập tsố điện thoại", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_soLuong)) {
                    Toast.makeText(ThemNhaPhanPhoiActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_tenSp)) {
                    Toast.makeText(ThemNhaPhanPhoiActivity.this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_tennppNPP)) {
                    Toast.makeText(ThemNhaPhanPhoiActivity.this, "Vui lòng nhập tên nhà phân phối", Toast.LENGTH_SHORT).show();
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
                saveOrder();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveOrder() {
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
                    Toast.makeText(ThemNhaPhanPhoiActivity.this, "Thêm nhà phân phối thất bại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ThemNhaPhanPhoiActivity.this, "Thêm nhà phân phối thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void matching() {
        NPPID = (EditText) findViewById(R.id.et_id);
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

