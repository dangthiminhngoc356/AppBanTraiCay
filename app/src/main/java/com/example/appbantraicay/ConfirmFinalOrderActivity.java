package com.example.appbantraicay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText name, phonenumber, address;
    private Button orderconfirm;

    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = " + totalAmount, Toast.LENGTH_SHORT).show();
        matching();
    }
    private void matching(){
        name = (EditText) findViewById(R.id.et_Shipment_name);
        phonenumber = (EditText) findViewById(R.id.et_Shipment_phone_number);
        address = (EditText) findViewById(R.id.et_Shipment_address);
        orderconfirm = (Button) findViewById(R.id.btn_Order_Confirm);
    }

//    nut nhan ben gio hang.setOnclickListener(new View.OnClickListener(){
//        @Override
//        public void onClick(View view)
//        {
//            txtTotalAmount.setText("Total Price = " + String.ValueOf(overTotalPrice));
//
//            Intent intent = new Intent(trang gio hang.this, CofnirmFinalOrderActivity.class)
//            intent.putExtra("Total Price", string.ValueOf(overTotalPrice));
//            startActivity(intent);
//            finish();
//        }
//    });
}