package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UpdateAccountActivity extends AppCompatActivity {

    Button save, cancel, delete;
    EditText name, password, phone;
    String input_tentk, input_password, input_phone,idtk;
    DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        matching();
        thongTinTaiKhoan();



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_tentk = name.getText().toString();
                input_password = password.getText().toString();
                input_phone = phone.getText().toString().trim();

                if (TextUtils.isEmpty(input_tentk)) {

                    Toast.makeText(UpdateAccountActivity.this, "Vui lòng nhập tên ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_password)) {
                    Toast.makeText(UpdateAccountActivity.this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty( input_phone)) {
                    Toast.makeText(UpdateAccountActivity.this, "Vui lòng nhập số điện thoai", Toast.LENGTH_SHORT).show();
                }
                saveAccount();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(UpdateAccountActivity.this, AdminAccountlistActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(UpdateAccountActivity.this, "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void thongTinTaiKhoan() {
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String stentk = snapshot.child("name").getValue().toString();
                    String spassword = snapshot.child("password").getValue().toString();
                    String sphone= snapshot.child("phone").getValue().toString();


                    name.setText(stentk);
                    password.setText(spassword);
                    phone.setText(sphone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void saveAccount() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("ten", input_tentk);
        map.put("password", input_password);
        map.put("phone", input_phone);


        Ref.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()) {
                    Toast.makeText(UpdateAccountActivity.this, "Cập nhật tài khoản thất bại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UpdateAccountActivity.this, "Cập nhật tài khoản thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void matching() {
        idtk = getIntent().getStringExtra("phone");
        Ref = FirebaseDatabase.getInstance().getReference().child("Users").child(idtk);

        save = (Button) findViewById(R.id.btn_updatetk_save);
        cancel = (Button) findViewById(R.id.btn_updatetk_cancel);
        delete = (Button) findViewById(R.id.btn_updatetk_delete);
        name = (EditText) findViewById(R.id.et_updatetk_name);
        password = (EditText) findViewById(R.id.et_updatetk_password);
        phone = (EditText) findViewById(R.id.et_updatetk_phone);

    }
}