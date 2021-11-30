package com.example.appbantraicay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    TextView tenadmin;
    Button sanpham, donhang, nhaphanphoi, loaisp, admindangxuat,taikhoan;
    String loggedinusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        loggedinusername = this.getIntent().getStringExtra(Login.INTENTEXTRAKEY_LOGGEDINUSERNAME);

        Toast.makeText(this, "Welcome " + loggedinusername, Toast.LENGTH_SHORT).show();
        matching();

        admindangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AdminProductsActivity.class);
                startActivity(intent);
            }
        });

        taikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, AdminAccountlistActivity.class);
                startActivity(intent);
            }
        });
        loaisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, ProductTypeAdminActivity.class));
            }
        });

        donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminOrderActivity.class));
            }
        });

        donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, AdminOrderActivity.class));
            }
        });
        nhaphanphoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, second_main.class));
            }
        });
    }

    private void matching() {
        admindangxuat = (Button) findViewById(R.id.btn_admin_dangxuat);
        sanpham = (Button) findViewById(R.id.btn_sanpham);
        donhang = (Button) findViewById(R.id.btn_donhang);
        nhaphanphoi = (Button) findViewById(R.id.btn_nhaphanphoi);
        loaisp = (Button) findViewById(R.id.btn_loaisp);

        taikhoan=(Button) findViewById(R.id.btn_taikhoan);

        tenadmin = (TextView) findViewById(R.id.txt_tenAdmin);
        tenadmin.setText(loggedinusername);

    }
}