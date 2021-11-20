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

import com.example.appbantraicay.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button login;
    EditText phonenumber,password;
    ProgressDialog loadingBar;
    String parentDataname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        matching();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {
        String phone= phonenumber.getText().toString();
        String pass= password.getText().toString();

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please write your phonenumber",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please write your password",Toast.LENGTH_LONG).show();
        }
        else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait for a minute");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,pass);

        }
    }

    private void AllowAccessToAccount(String phone, String pass) {
        final DatabaseReference Ref;
        Ref= FirebaseDatabase.getInstance().getReference();
        
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDataname).child(phone).exists()){
                    Users usersdata=snapshot.child(parentDataname).child(phone).getValue(Users.class);
                    if (usersdata.getPhone().equals(phone)) {
                        if (usersdata.getPassword().equals(pass)) {
                            Toast.makeText(Login.this, "Loggin sucessfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(Login.this,HomeAcitity.class);
                            startActivity(intent);
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(Login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(Login.this, "Account with this"+phone+"doesn't exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void matching() {
        login=(Button) findViewById(R.id.btn_login_main);
        phonenumber = (EditText) findViewById(R.id.et_login_phone);
        password=(EditText) findViewById(R.id.et_password_login);
        loadingBar=new ProgressDialog(this);
    }
}