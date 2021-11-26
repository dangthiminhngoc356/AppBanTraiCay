package com.example.appbantraicay;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemNhaPhanPhoiActivity extends AppCompatActivity {

    EditText ID, DIACHI, EMAIL, SDT, SP1, NGAY, SOLUONG, TENSP, TENNPP;

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

        ID.setEnabled(false);
        ID.setText(String.valueOf(size));

        DIACHI.setEnabled(false);
        DIACHI.setText(String.valueOf(size));

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

        ID = (EditText) findViewById(R.id.et_id);
        DIACHI = (EditText) findViewById(R.id.et_diachi);
        EMAIL = (EditText) findViewById(R.id.et_email);
        SDT = (EditText) findViewById(R.id.et_phone);
        SP1 = (EditText) findViewById(R.id.et_idsp1);
        SOLUONG = (EditText) findViewById(R.id.et_soluong);
        NGAY = (EditText) findViewById(R.id.et_date);
        TENSP = (EditText) findViewById(R.id.et_tensp1);
        TENNPP = (EditText) findViewById(R.id.et_tennpp);
        add = (Button) findViewById(R.id.btn_Add);
        cancdel = (Button) findViewById(R.id.btn_Cancel);
    }
    private void xulyThemMoi(){
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("contact");

            ID.setEnabled(false);
            ID.setText(String.valueOf(size));
            String contactId = ID.getText().toString();

            String saddress = DIACHI.getText().toString();
            String semail = EMAIL.getText().toString();
            String sphone = SDT.getText().toString();
            String ssp1 = SP1.getText().toString();
            String sngay = NGAY.getText().toString();
            String ssoluong = SOLUONG.getText().toString();
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

            Toast.makeText(this, "Thêm liên kết thành công", Toast.LENGTH_LONG).show();
            finish();
        }catch (Exception e){
            Toast.makeText(this,"Error"+e.toString(),Toast.LENGTH_LONG);
        }
    }
}

