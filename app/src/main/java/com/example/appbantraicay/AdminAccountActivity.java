package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAccountActivity extends AppCompatActivity {

    Button save, cancel;
    EditText name, password, phone;
    ImageView hinhanh;
    ProgressDialog loadingBar;
    Uri imageUri;
    String dowloadImage, input_tentk, input_password, input_phone;
    DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("Users");
    private static final int GalleryPick = 1;
    private StorageReference accountImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

        matching();
        hinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_tentk = name.getText().toString();
                input_password = password.getText().toString().trim();
                input_phone = phone.getText().toString().trim();

                if(imageUri==null){
                    Toast.makeText(AdminAccountActivity.this, "Account image is mandatory", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(input_tentk)){

                    Toast.makeText(AdminAccountActivity.this, "Please write your name...", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(input_password)){
                    Toast.makeText(AdminAccountActivity.this, "Please write your password...", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(input_phone)){
                    Toast.makeText(AdminAccountActivity.this, "Please write your phonenumber...", Toast.LENGTH_SHORT).show();
                }
                else{
                    ValidateAccount();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    private void matching() {
     accountImageRef= FirebaseStorage.getInstance().getReference().child("hinhanh");
     loadingBar = new ProgressDialog(this);
     cancel= (Button) findViewById(R.id.btn_cancel_newaccount);
     save =(Button) findViewById(R.id.btn_save_newaccount);
     name =(EditText) findViewById(R.id.et_addnewaccount);
     password =(EditText) findViewById(R.id.et_password_themtk);
     phone =(EditText) findViewById(R.id.et_phone_themtk);
     hinhanh =(ImageView) findViewById(R.id.iv_themtk_hinhanh);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //noinspection deprecation
        startActivityForResult(intent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            hinhanh.setImageURI(imageUri);
        }
    }

    private void ValidateAccount() {
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StorageReference filePath = accountImageRef.child(input_phone + ".jpg");
                final UploadTask uploadTask = filePath.putFile(imageUri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminAccountActivity.this, "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if(!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                dowloadImage = filePath.getDownloadUrl().toString();
                                return filePath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()) {
                                    dowloadImage = task.getResult().toString();
                                    saveAccount();
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveAccount() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", input_tentk);
        map.put("password", input_password);
        map.put("phone", input_phone);
        map.put("hinhanh", dowloadImage);
        Ref.child(input_phone).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()) {
                    Toast.makeText(AdminAccountActivity.this, "Thêm tài khoản thất bại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AdminAccountActivity.this, "Thêm tài khoản thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

}