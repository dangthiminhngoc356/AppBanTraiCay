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
    EditText DIACHI, EMAIL, SDT, NGAY, SOLUONG, TENSP, TENNPP;
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

                DatabaseReference myRef = database.getReference("contact");

                String contactId = key;

                String semail = EMAIL.getText().toString();
                String sphone = SDT.getText().toString();
                String ssoluong = SOLUONG.getText().toString();
                String sngay = NGAY.getText().toString();
                String stensp = TENSP.getText().toString();
                String stennpp = TENNPP.getText().toString();

                myRef.child(contactId).child("email").setValue(semail);
                myRef.child(contactId).child("phone").setValue(sphone);
                myRef.child(contactId).child("số lượng").setValue(ssoluong);
                myRef.child(contactId).child("địa chỉ").setValue(contactId);
                myRef.child(contactId).child("ngày").setValue(sngay);
                myRef.child(contactId).child("tên sản phẩm").setValue(stensp);
                myRef.child(contactId).child("tên nhà phân phối").setValue(stennpp);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("contact");

                myRef.child(key).removeValue();
                finish();
            }
        });
    }

    private void getContactDetail() {
        Intent intent = getIntent();

        final String key = intent.getStringExtra("KEY");

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("contacts");

        myRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    DIACHI.setText(key);
                    EMAIL.setText(hashMap.get("email").toString());
                    SDT.setText(hashMap.get("phone").toString());
                    NGAY.setText(hashMap.get("ngày").toString());
                    SOLUONG.setText(hashMap.get("số lượng").toString());
                    TENSP.setText(hashMap.get("tên sản phẩm").toString());
                    TENNPP.setText(hashMap.get("tên nhà phân phối").toString());

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

