package com.example.appbantraicay;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class second_main extends AppCompatActivity {
    private ListView lvnpp;
    private ArrayList<nhaphanphoi> nhaphanPhoiArrayList;
    private NhaPhanPhoiAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         lvnpp = findViewById(R.id.lvnpp);
         nhaphanPhoiArrayList=new ArrayList<>();
Getdata();
         //nhaphanPhoiArrayList.add(new nhaphanphoi("NPP01", "2 Bình Lợi, P.13, TP.HCM", "vuatraicaythang@gmail.com", "0707000777", "SP01", "9/9/2021", "1000","Thanh long ruột đỏ", "Vựa trái cây của Thắng"));

        //nhaphanPhoiArrayList.add(new nhaphanphoi("NPP02", "3 Bình Chánh, P.14, TP.HCM", "vuatraicaynam@gmail.com", "0808000877", "SP02", "10/9/2021", "1000","Măng cục", "Vựa trái cây của Nam"));
         adapter=new NhaPhanPhoiAdapter(this,R.layout.custom_listview_item,nhaphanPhoiArrayList);
        lvnpp.setAdapter(adapter);

    }
    private void Getdata(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("NhaPhanPhoi");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
adapter.clear();
for(DataSnapshot data : dataSnapshot.getChildren()){
    nhaphanphoi nhaphanPhoi = new nhaphanphoi();
        nhaphanPhoi.setNPP(data.getKey());
            adapter.add(nhaphanPhoi);
Log.d("MYTAG", "onDataChange: " + nhaphanPhoi.getTenNPP());
}
                    Toast.makeText(getApplicationContext(), "Load Data Success", Toast.LENGTH_LONG).show();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Load Data Failed" + databaseError.toString(), Toast.LENGTH_LONG).show();
                Log.d("MYTAG", "onClick: ");
            }

        });
    }

}
