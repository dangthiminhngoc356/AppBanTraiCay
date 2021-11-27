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

//    ImageView hinhanh;
//    Uri imageUri;
    String dowloadImage, input_tentk, input_password, input_phone,idtk;
    DatabaseReference Ref;
//    private static final int GalleryPick = 1;
//    private StorageReference productImageRef;

    ImageView hinhanh;
    Uri imageUri;
    String dowloadImage, input_tentk, input_password, input_phone,idtk;
    DatabaseReference Ref;
    private static final int GalleryPick = 1;
    private StorageReference productImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        matching();
        thongTinTaiKhoan();

//        hinhanh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectImage();
//            }
//        });

        hinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


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

//                if(imageUri == null) {
//                    Toast.makeText(UpdateAccountActivity.this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
             //   }
                 if (TextUtils.isEmpty(input_tentk)) {

                if(imageUri == null) {
                    Toast.makeText(UpdateAccountActivity.this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_tentk)) {

                    Toast.makeText(UpdateAccountActivity.this, "Vui lòng nhập tên ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_password)) {
                    Toast.makeText(UpdateAccountActivity.this, "Vui lòng nhập password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty( input_phone)) {
                    Toast.makeText(UpdateAccountActivity.this, "Vui lòng nhập số điện thoai", Toast.LENGTH_SHORT).show();
                } else {
                    validateProduct();
                }
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

                        Toast.makeText(UpdateAccountActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();

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

                   // String shinhanh = snapshot.child("HinhAnh").getValue().toString();

                    String shinhanh = snapshot.child("HinhAnh").getValue().toString();


                    name.setText(stentk);
                    password.setText(spassword);
                    phone.setText(sphone);

                   // Picasso.get().load(shinhanh).into(hinhanh);

                    Picasso.get().load(shinhanh).into(hinhanh);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void validateProduct() {


        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               // StorageReference filePath = productImageRef.child(idtk + ".jpg");
               // final UploadTask uploadTask = filePath.putFile(imageUri);
//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(UpdateAccountActivity.this, "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                            @Override
//                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                if(!task.isSuccessful()) {
//                                    throw task.getException();
//                                }
//                                dowloadImage = filePath.getDownloadUrl().toString();
//                                return filePath.getDownloadUrl();
//                            }
//                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                if(task.isSuccessful()) {
//                                    dowloadImage = task.getResult().toString();
//                                    saveAccount();
//                                }
//                            }
//                        });
//                    }
//                });

                StorageReference filePath = productImageRef.child(idtk + ".jpg");
                final UploadTask uploadTask = filePath.putFile(imageUri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateAccountActivity.this, "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
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

        map.put("Ten", input_tentk);
        map.put("DonGia", input_password);
        map.put("MoTa", input_phone);

     //   map.put("HinhAnh", dowloadImage);
=======
        map.put("HinhAnh", dowloadImage);

        Ref.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()) {

                    Toast.makeText(UpdateAccountActivity.this, "Cập nhật tài khoản thất bại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UpdateAccountActivity.this, "Cập nhật tài khoản thành công", Toast.LENGTH_LONG).show();

                    Toast.makeText(UpdateAccountActivity.this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UpdateAccountActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_LONG).show();

                    finish();
                }
            }
        });
    }


//    private void selectImage() {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//       // intent.setType("image/*");
//        //noinspection deprecation
//        //startActivityForResult(intent, GalleryPick);
//    }

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

//        if(requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
//            imageUri = data.getData();
//            hinhanh.setImageURI(imageUri);
//        }

        if(requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            hinhanh.setImageURI(imageUri);
        }

    }

    private void matching() {
        idtk = getIntent().getStringExtra("phone");

        Ref = FirebaseDatabase.getInstance().getReference().child("Users").child(idtk);

        Ref = FirebaseDatabase.getInstance().getReference().child("SanPham").child(idtk);


        save = (Button) findViewById(R.id.btn_updatesp_save);
        cancel = (Button) findViewById(R.id.btn_updatesp_cancel);
        delete = (Button) findViewById(R.id.btn_updatesp_delete);
        name = (EditText) findViewById(R.id.et_updatesp_tensp);
        password = (EditText) findViewById(R.id.et_updatesp_mota);
        phone = (EditText) findViewById(R.id.et_updatesp_dongia);

       // hinhanh = (ImageView) findViewById(R.id.iv_updatesp_hinhanh);
       // productImageRef = FirebaseStorage.getInstance().getReference().child("Users");

        hinhanh = (ImageView) findViewById(R.id.iv_updatesp_hinhanh);
        productImageRef = FirebaseStorage.getInstance().getReference().child("sanpham");

    }
}