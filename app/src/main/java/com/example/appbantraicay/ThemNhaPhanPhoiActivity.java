package com.example.appbantraicay;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemNhaPhanPhoiActivity extends AppCompatActivity {
private EditText etaddress, etmail, etsodienthoai, etTennpp, etTensp, etSoluong, etNgay;
private Button btnadd, btncancel, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nha_phan_phoi);
        matching();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etaddress.setText("");
                etmail.setText("");
                etsodienthoai.setText("");
                etTennpp.setText("");
                etTensp.setText("");
                etSoluong.setText("");
                etNgay.setText("");

            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diaChi = etaddress.getText().toString();
                String email = etmail.getText().toString();
                String SDT= etsodienthoai.getText().toString();
                String tenNPP = etTennpp.getText().toString();
                String tenSP = etTensp.getText().toString();
                String soLuong = etSoluong.getText().toString();
                String Ngay = etNgay.getText().toString();

                nhaphanphoi nhaphanPhoi = new nhaphanphoi(diaChi, email, SDT, tenNPP, tenSP, soLuong, Ngay);

                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference myRef=database.getReference("NhaPhanPhoi");
                String id=myRef.push().getKey();
                myRef.child(id).setValue(nhaphanPhoi).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Thêm thất bại",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void matching() {
        etaddress= (EditText) findViewById(R.id.et_address);
        etmail=(EditText) findViewById(R.id.et_email);
        etsodienthoai=(EditText) findViewById(R.id.et_phone);
        etTennpp=(EditText) findViewById(R.id.et_tennpp);
        etTensp=(EditText) findViewById(R.id.et_tensp);
        etSoluong=(EditText) findViewById(R.id.et_soluong);
        etNgay=(EditText) findViewById(R.id.et_ngay);
        btnadd=(Button) findViewById(R.id.btn_them);
        btncancel=(Button) findViewById(R.id.btn_huy);
        btnBack=(Button) findViewById(R.id.btn_back);
    }
}