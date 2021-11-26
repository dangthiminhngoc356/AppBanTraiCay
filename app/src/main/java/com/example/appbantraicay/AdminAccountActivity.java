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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdminAccountActivity extends AppCompatActivity {
    Button savenewaccount,cancelnewaccount;
    EditText name,password,phonenumber;
    ImageView AccountImageView;
    ProgressDialog loadingBar;
    Uri imageUri;
    String downloadImage, input_tentk, input_password, input_phonenummber;
    private static final int GalleryPick = 1;
    private StorageReference accountImageRef;
    private DatabaseReference  AccountRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);



        matching();
        AccountImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              openGalerry();
            }
        });
        savenewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ValidateAccount();

            }
        });
        cancelnewaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    private void matching() {
     accountImageRef= FirebaseStorage.getInstance().getReference().child("Account image");
     AccountRef = FirebaseDatabase.getInstance().getReference().child("Users");
     loadingBar=new ProgressDialog(this);
     cancelnewaccount= (Button) findViewById(R.id.btn_cancel_newaccount);
   savenewaccount =(Button) findViewById(R.id.btn_save_newaccount);
    name =(EditText) findViewById(R.id.et_addnewaccount);
    password =(EditText) findViewById(R.id.et_password_themtk);
    phonenumber =(EditText) findViewById(R.id.et_phone_themtk);
    AccountImageView =(ImageView) findViewById(R.id.iv_themtk_hinhanh);

    }
    private void openGalerry() {
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
            AccountImageView.setImageURI(imageUri);
        }
    }
    private void ValidateAccount() {
        input_tentk=name.getText().toString();
        input_password=password.getText().toString();
        input_phonenummber=phonenumber.getText().toString();


        if(imageUri==null){
            Toast.makeText(this, "Account image is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(input_tentk)){
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(input_password)){
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty( input_phonenummber)){
            Toast.makeText(this, "Please write your phonenumber...", Toast.LENGTH_SHORT).show();
        }
        else{
            storeAccountInformation();
        }
    }

    private void storeAccountInformation() {
        loadingBar.setTitle("Add new Account");
        loadingBar.setMessage("Please wait for a minute");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        StorageReference filepath= accountImageRef.child(imageUri.getLastPathSegment());
        final UploadTask uploadTask = filepath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAccountActivity.this, "Error"+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAccountActivity.this, "Upload image successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImage=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImage =task.getResult().toString();
                            Toast.makeText(AdminAccountActivity.this, "Account image save to database successfully", Toast.LENGTH_SHORT).show();

                            SaveAccountInformation();
                        }
                    }
                });
            }
        });
    }

    private void SaveAccountInformation() {
        HashMap<String,Object> accountMap=new HashMap<>();
       accountMap.put("name", input_tentk);
        accountMap.put("password", input_password);
        accountMap.put("phonenumber", input_phonenummber);
//        productMap.put("HinhAnh", dowloadImage);
        AccountRef.child(input_phonenummber).updateChildren(accountMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(AdminAccountActivity.this,AdminActivity.class);
                               startActivity(intent);
                    loadingBar.dismiss();
                    Toast.makeText(AdminAccountActivity.this, "Account is add successfully  ", Toast.LENGTH_SHORT).show();
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(AdminAccountActivity.this,"Account is add failed", Toast.LENGTH_LONG).show();
                    finish();

                }
            }
        });
    }
}