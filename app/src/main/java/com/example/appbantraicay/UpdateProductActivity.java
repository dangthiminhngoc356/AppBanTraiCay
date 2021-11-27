package com.example.appbantraicay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class UpdateProductActivity extends AppCompatActivity {

    Button save, cancel, delete;
    EditText tensp, mota, dongia;
    ImageView hinhanh;
    Uri imageUri;
    String dowloadImage, input_tensp, input_mota, input_dongia, idsp;
    DatabaseReference Ref;
    private static final int GalleryPick = 1;
    private StorageReference productImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        matching();
        thongTinSanPham();
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
                input_tensp = tensp.getText().toString();
                input_mota = mota.getText().toString();
                input_dongia = dongia.getText().toString().trim();

                if(imageUri == null) {
                    Toast.makeText(UpdateProductActivity.this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_tensp)) {
                    Toast.makeText(UpdateProductActivity.this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_dongia)) {
                    Toast.makeText(UpdateProductActivity.this, "Vui lòng nhập đơn giá sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_mota)) {
                    Toast.makeText(UpdateProductActivity.this, "Vui lòng nhập mô tả sản phẩm", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(UpdateProductActivity.this, AdminProductsActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(UpdateProductActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void thongTinSanPham() {
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String stensp = snapshot.child("Ten").getValue().toString();
                    String smota = snapshot.child("MoTa").getValue().toString();
                    String sdongia = snapshot.child("DonGia").getValue().toString();
                    String shinhanh = snapshot.child("HinhAnh").getValue().toString();

                    tensp.setText(stensp);
                    mota.setText(smota);
                    dongia.setText(sdongia);
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

                StorageReference filePath = productImageRef.child(idsp + ".jpg");
                final UploadTask uploadTask = filePath.putFile(imageUri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProductActivity.this, "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
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
                                    saveProduct();
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


    private void saveProduct() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("Ten", input_tensp);
        map.put("DonGia", input_dongia);
        map.put("MoTa", input_mota);
        map.put("HinhAnh", dowloadImage);
        Ref.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()) {
                    Toast.makeText(UpdateProductActivity.this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UpdateProductActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
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

    private void matching() {
        idsp = getIntent().getStringExtra("id");
        Ref = FirebaseDatabase.getInstance().getReference().child("SanPham").child(idsp);

        save = (Button) findViewById(R.id.btn_updatesp_save);
        cancel = (Button) findViewById(R.id.btn_updatesp_cancel);
        delete = (Button) findViewById(R.id.btn_updatesp_delete);
        tensp = (EditText) findViewById(R.id.et_updatesp_tensp);
        mota = (EditText) findViewById(R.id.et_updatesp_mota);
        dongia = (EditText) findViewById(R.id.et_updatesp_dongia);
        hinhanh = (ImageView) findViewById(R.id.iv_updatesp_hinhanh);
        productImageRef = FirebaseStorage.getInstance().getReference().child("sanpham");
    }
}