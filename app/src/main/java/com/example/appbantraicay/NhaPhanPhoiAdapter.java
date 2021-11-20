package com.example.appbantraicay;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class NhaPhanPhoiAdapter extends ArrayAdapter<nhaphanphoi> {
    @NonNull
   private Activity activity;
   private int resource;
    @NonNull
   private List<nhaphanphoi> objects;

    public NhaPhanPhoiAdapter(@NonNull Activity activity, int resource, @NonNull List<nhaphanphoi> objects) {
        super(activity, resource, objects);
        this.activity=activity;
        this.resource=resource;
        this.objects=objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View view = inflater.inflate(this.resource,null);
        TextView txtTen=view.findViewById(R.id.txtTen);
        TextView txtEmail=view.findViewById(R.id.txtEmail);
        nhaphanphoi nhaphanPhoi=this.objects.get(position);
        txtTen.setText(nhaphanPhoi.getTenNPP());
        txtEmail.setText(nhaphanPhoi.getEmail());
        ImageView btnMenu=view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu= new PopupMenu(activity,view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.item_them_npp){
                            //Toast.makeText(activity,"Bạn đã nhấn nút Thêm", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity,ThemNhaPhanPhoiActivity.class);
                            activity.startActivity(intent);

                        }
                        else if(menuItem.getItemId()==R.id.item_sua_npp){
                        Toast.makeText(activity,"Bạn đã nhấn nút sửa: " + nhaphanPhoi.getTenNPP(), Toast.LENGTH_LONG).show();
                    }
                         else if(menuItem.getItemId()==R.id.item_xoa_npp){
                                Toast.makeText(activity,"Bạn đã nhấn nút xóa: " + nhaphanPhoi.getTenNPP(), Toast.LENGTH_LONG).show();
                            }

                        return false;
                    }
                });
                try{
                    Field field = popupMenu.getClass().getDeclaredField("mPopup");
                    field.setAccessible(true);
                    Object popUpMenuHelper = field.get(popupMenu);
                    Class<?> cls = Class.forName("com.android.internal.view.menu.MenuPopupHelper");
                    Method method = cls.getDeclaredMethod("setForceShowIcon", new Class[] {boolean.class});
                    method.setAccessible(true);
                    method.invoke(popUpMenuHelper,new Object[]{true});
                }catch (Exception e){
                    Log.d("MYTAG", "onClick: " + e.toString());
                }
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
            }
        });
        return view;
    }
        }