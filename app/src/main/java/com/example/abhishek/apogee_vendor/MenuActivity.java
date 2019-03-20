package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.abhishek.apogee_vendor.adapter.menuAdapter;
import com.example.abhishek.apogee_vendor.model.menu_model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    menuAdapter adapter;
    ArrayList<menu_model> menulist=new ArrayList<>();
    DatabaseReference mdata;

    private static int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        recyclerView=findViewById(R.id.menuRecycler);
        adapter=new menuAdapter(menulist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        SharedPreferences prefs = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        String vid=prefs.getString("ID"," ");
       mdata= FirebaseDatabase.getInstance().getReference("vendors/vendor - ".concat("56").concat("/menu"));
       mdata.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot ds:dataSnapshot.getChildren()){
                menu_model menu=new menu_model();
                Log.d("check",ds.toString());
                menu.setMenuname(ds.child(ds.getKey()).getValue(menu_model.class).getMenuname());
                menu.setPrice(ds.child(ds.getKey()).getValue(menu_model.class).getPrice());
                Log.d("check",menu.getMenuname());
                Log.d("check",menu.getPrice());
                menulist.add(menu);
                Log.d("check",menulist.toString());
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
