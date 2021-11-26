package com.example.appbantraicay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CapNhatNhaPhanPhoiActivity extends AppCompatActivity {
    Button update, delete, cancel;
    EditText ID, DIACHI, EMAIL, SDT, SP1, NGAY, SOLUONG, TENSP, TENNPP;

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



                String saddress = DIACHI.getText().toString();
                String semail = EMAIL.getText().toString();
                String sphone = SDT.getText().toString();
                String ssp1 = SP1.getText().toString();
                String ssoluong = SOLUONG.getText().toString();
                String sngay = NGAY.getText().toString();
                String stensp = TENSP.getText().toString();
                String stennpp = TENNPP.getText().toString();



                myRef.child(contactId).child("địa chỉ").setValue(saddress);
                myRef.child(contactId).child("email").setValue(semail);
                myRef.child(contactId).child("phone").setValue(sphone);
                myRef.child(contactId).child("sản phẩm 1").setValue(ssp1);
                myRef.child(contactId).child("ngày sản phẩm 1").setValue(sngay);
                myRef.child(contactId).child("số lượng sản phẩm 1").setValue(ssoluong);
                myRef.child(contactId).child("tên sản phẩm 1").setValue(stensp);
                myRef.child(contactId).child("id").setValue(contactId);


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

                    ID.setText(key);
                    DIACHI.setText(hashMap.get("địa chỉ").toString());
                    EMAIL.setText(hashMap.get("email").toString());
                    SDT.setText(hashMap.get("phone").toString());
                    SP1.setText(hashMap.get("sản phẩm 1").toString());
                    NGAY.setText(hashMap.get("ngày sản phẩm 1").toString());
                    SOLUONG.setText(hashMap.get("số lượng sản phẩm 1").toString());
                    TENSP.setText(hashMap.get("tên sản phẩm 1").toString());
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

        ID =(EditText) findViewById(R.id.et_id);
        DIACHI = (EditText) findViewById(R.id.et_diachi);
        EMAIL = (EditText) findViewById(R.id.et_email);
        SDT = (EditText) findViewById(R.id.et_phone);
        SP1 = (EditText) findViewById(R.id.et_idsp1);
        NGAY = (EditText) findViewById(R.id.et_date);
        TENSP = (EditText) findViewById(R.id.et_tensp1);
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

