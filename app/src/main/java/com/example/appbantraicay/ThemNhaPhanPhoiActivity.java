package com.example.appbantraicay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemNhaPhanPhoiActivity extends AppCompatActivity {
    EditText NPPID, DIACHI, EMAIL, SDT, NGAY, SOLUONG, TENSP, TENNPP;
    Button add, cancdel;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nha_phan_phoi);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            this.size = extras.getInt("size");
        }
        matching();
        NPPID.setEnabled(false);
        NPPID.setText(String.valueOf(size));
        cancdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { xulyThemMoi(); }
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
        add = (Button) findViewById(R.id.btn_Add);
        cancdel = (Button) findViewById(R.id.btn_Cancel);
    }
    private void xulyThemMoi(){
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("NhaPhanPhoi");
            NPPID.setEnabled(false);
            NPPID.setText(String.valueOf(size));
            String NhaPhanPhoiId = NPPID.getText().toString();

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

            Toast.makeText(this, "Thêm nhà phân phối thành công", Toast.LENGTH_LONG).show();
            finish();
        }catch (Exception e){
            Toast.makeText(this,"Error"+e.toString(),Toast.LENGTH_LONG);
        }
    }
}

