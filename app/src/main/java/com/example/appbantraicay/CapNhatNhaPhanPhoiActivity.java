package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CapNhatNhaPhanPhoiActivity extends AppCompatActivity {
    Button update, delete, cancel;
    EditText NPPID, DIACHI, EMAIL, SDT, NGAY, SOLUONG, TENSP, TENNPP;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_nha_phan_phoi);
        matching();
        getContactDetail();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef = database.getReference("NhaPhanPhoi");

                String NhaPhanPhoiId = key;

                String saddress = DIACHI.getText().toString();
                String semail = EMAIL.getText().toString();
                String sphone = SDT.getText().toString();
                String sngay = NGAY.getText().toString();
                String ssoluong = SOLUONG.getText().toString();
                String stensp = TENSP.getText().toString();
                String stennpp = TENNPP.getText().toString();


                myRef.child(NhaPhanPhoiId).child("DiaChi").setValue(saddress);
                myRef.child(NhaPhanPhoiId).child("Email").setValue(semail);
                myRef.child(NhaPhanPhoiId).child("SDT").setValue(sphone);
                myRef.child(NhaPhanPhoiId).child("Ngay").setValue(sngay);
                myRef.child(NhaPhanPhoiId).child("SoLuong").setValue(ssoluong);
                myRef.child(NhaPhanPhoiId).child("TenSP").setValue(stensp);
                myRef.child(NhaPhanPhoiId).child("NPP01").setValue(NhaPhanPhoiId);
                myRef.child(NhaPhanPhoiId).child("TenNPP").setValue(stennpp);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("NhaPhanPhoi");

                myRef.child(key).removeValue();
                finish();
            }
        });
    }

    private void getContactDetail() {
        Intent intent = getIntent();

        final String key = intent.getStringExtra("KEY");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("NhaPhanPhoi");

        myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    NPPID.setText(key);
                    DIACHI.setText(hashMap.get("DiaChi").toString());
                    EMAIL.setText(hashMap.get("Email").toString());
                    SDT.setText(hashMap.get("SDT").toString());
                    NGAY.setText(hashMap.get("Ngay").toString());
                    SOLUONG.setText(hashMap.get("SoLuong").toString());
                    TENSP.setText(hashMap.get("TenSP").toString());
                    TENNPP.setText(hashMap.get("TenNPP").toString());

                } catch (Exception e) {
                    Log.d("LOI_JSON", e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("LOI_CHITIET", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    private void matching() {
        NPPID = (EditText) findViewById(R.id.et_id);
        DIACHI = (EditText) findViewById(R.id.et_diachi);
        EMAIL = (EditText) findViewById(R.id.et_email);
        SDT = (EditText) findViewById(R.id.et_phone);
        SOLUONG = (EditText) findViewById(R.id.et_soluong);
        NGAY = (EditText) findViewById(R.id.et_date);
        TENSP = (EditText) findViewById(R.id.et_tensp);
        TENNPP = (EditText) findViewById(R.id.et_tennpp);
        update = findViewById(R.id.btn_Update);
        delete = findViewById(R.id.btn_Delete);
        cancel = findViewById(R.id.btn_Cancel1);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

