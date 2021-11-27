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


import com.google.firebase.database.Query;


import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import java.util.HashMap;

public class AddNewProductActivity extends AppCompatActivity {

    Button save, cancel;
    EditText tensp, mota, dongia;
    ImageView hinhanh;
    Uri imageUri;
    String dowloadImage, input_tensp, input_mota, input_dongia, idsp;
    DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("SanPham");
    private static final int GalleryPick = 1;
    private StorageReference productImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        matching();
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

                if (imageUri == null) {


                if(imageUri == null) {

                    Toast.makeText(AddNewProductActivity.this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_tensp)) {
                    Toast.makeText(AddNewProductActivity.this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_dongia)) {
                    Toast.makeText(AddNewProductActivity.this, "Vui lòng nhập đơn giá sản phẩm", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(input_mota)) {
                    Toast.makeText(AddNewProductActivity.this, "Vui lòng nhập mô tả sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    validateProduct();
                }
            }
        });
    }

    private void validateProduct() {


        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int soluongsp = (int) snapshot.getChildrenCount() + 1;
                idsp = "SP" + soluongsp;

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                String saveCurrentDate = currentDate.format(calendar.getTime());
                String saveCurrentTime = currentTime.format(calendar.getTime());
                idsp = "SP" + saveCurrentDate + saveCurrentTime;

                StorageReference filePath = productImageRef.child(idsp + ".jpg");
                final UploadTask uploadTask = filePath.putFile(imageUri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewProductActivity.this, "Lỗi: " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {


                                if(!task.isSuccessful()) {

                                if (!task.isSuccessful()) {

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

                                if (task.isSuccessful()) {


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
        map.put("id", idsp);
        map.put("Ten", input_tensp);
        map.put("DonGia", input_dongia);
        map.put("MoTa", input_mota);
        map.put("HinhAnh", dowloadImage);
        Ref.child(idsp).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if(!task.isSuccessful()) {

                if (!task.isSuccessful()) {


                if(!task.isSuccessful()) {

                    Toast.makeText(AddNewProductActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddNewProductActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
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


        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {


            imageUri = data.getData();
            hinhanh.setImageURI(imageUri);
        }
    }

    private void matching() {
        save = (Button) findViewById(R.id.btn_themsp_save);
        cancel = (Button) findViewById(R.id.btn_themsp_cancel);
        tensp = (EditText) findViewById(R.id.et_themsp_tensp);
        mota = (EditText) findViewById(R.id.et_themsp_mota);
        dongia = (EditText) findViewById(R.id.et_themsp_dongia);
        hinhanh = (ImageView) findViewById(R.id.iv_themsp_hinhanh);
        productImageRef = FirebaseStorage.getInstance().getReference().child("sanpham");
    }
}