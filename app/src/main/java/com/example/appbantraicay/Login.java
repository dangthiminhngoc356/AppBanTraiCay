package com.example.appbantraicay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbantraicay.Prevalent.Prevalent;
import com.example.appbantraicay.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    Button login;
    EditText phonenumber,password;
    ProgressDialog loadingBar;
    String parentDataname = "Users";
    CheckBox chkBoxRememberMe;
    TextView admin, notanadmin;

    public static final String INTENTEXTRAKEY_LOGGEDINUSERNAME = "iek_loggedinusername";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Paper.init(this);
        matching();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login admin");
                admin.setVisibility(View.INVISIBLE);
                notanadmin.setVisibility(View.VISIBLE);
                parentDataname = "Admins";
            }
        });
        notanadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login");
                admin.setVisibility(View.VISIBLE);
                notanadmin.setVisibility(View.INVISIBLE);
                parentDataname = "Users";
            }
        });
    }

    private void LoginUser() {
        String phone = phonenumber.getText().toString().trim();
        String pass = password.getText().toString().trim();

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
        if(chkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,pass);
        }


        final DatabaseReference Ref;
        Ref= FirebaseDatabase.getInstance().getReference();
        
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDataname).child(phone).exists()){
                    Users usersdata = snapshot.child(parentDataname).child(phone).getValue(Users.class);
                    if (usersdata.getPhone().equals(phone)) {
                        if (usersdata.getPassword().equals(pass)) {
                            if(parentDataname.equals("Admins")){
                                Toast.makeText(Login.this, "Logged in sucessfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(Login.this, AdminActivity.class);
                                intent.putExtra(INTENTEXTRAKEY_LOGGEDINUSERNAME, usersdata.getName());
                                startActivity(intent);
                                finish();
                                startActivity(intent);
                            }
                            else if(parentDataname.equals("Users")){
                                Toast.makeText(Login.this, "Logged in sucessfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(Login.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }

                        else {
                            loadingBar.dismiss();
                            Toast.makeText(Login.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(Login.this, "Account with this "+ phone +" doesn't exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
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
        chkBoxRememberMe = (CheckBox) findViewById(R.id.checkbox_rememberme);
        admin = (TextView) findViewById(R.id.tv_admin);
        notanadmin = (TextView) findViewById(R.id.tv_notanadmin);
    }
}