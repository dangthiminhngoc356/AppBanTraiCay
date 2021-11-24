package com.example.appbantraicay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class second_main extends AppCompatActivity {
    ListView contacts;
    ArrayAdapter<String> adapter;
    String TAG = "FIREBASE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        contacts = findViewById(R.id.v_contact);
        contacts.setAdapter(adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("contact");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot record:dataSnapshot.getChildren()){
                    String key = record.getKey();
                    String value = record.getValue().toString();
                    adapter.add(key+"\n"+value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = adapter.getItem(position);
                String key = data.split("\n")[0];
                Intent intent = new Intent( second_main.this, CapNhatNhaPhanPhoiActivity.class);
                intent.putExtra("KEY", key);
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mnuAdd) {
            Intent intent = new Intent(second_main.this, ThemNhaPhanPhoiActivity.class);
            int size = adapter.getCount();
            intent.putExtra("size", size);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.mnuGioiThieu)
            Toast.makeText(this, "Chào mừngbạn đến với shop \n bán trái cây", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);

    }
}

