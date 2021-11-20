package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class Register extends AppCompatActivity {
    Button createnew;
    EditText username,phonenumber,password;
    ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        matching();

        createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String name= username.getText().toString();
        String phone= phonenumber.getText().toString();
        String passcreate= password.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your name",Toast.LENGTH_LONG);
        }
       else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please write your phonenumber",Toast.LENGTH_LONG);
        }
       else if(TextUtils.isEmpty(passcreate)){
            Toast.makeText(this,"Please write your password",Toast.LENGTH_LONG);
        }
        else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait for a minute");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name,phone,passcreate);
        }

    }

    private void ValidatephoneNumber(String name, String phone, String passcreate) {
        final DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("TaiKhoan").child(phone).exists())){
                    HashMap<String,Object>userdataMap= new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("name",name);
                    userdataMap.put("password",passcreate);
                    reference.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Congratulations, your account has been create", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(Register.this,Login.class);
                            startActivity(intent);
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(Register.this, "Error: Please try again later", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                }
                else {
                    Toast.makeText(Register.this, "this"+phone+"already exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Register.this, "Please using another phone number", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void matching() {
        createnew=(Button) findViewById(R.id.btn_register);
        username=(EditText) findViewById(R.id.et_username);
        phonenumber=(EditText) findViewById(R.id.et_phonenumber);
        password=(EditText) findViewById(R.id.et_password);
        loadingBar=new ProgressDialog(this);
    }
}