package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.abhishek.apogee_vendor.adapter.itemListAdapter;
import com.example.abhishek.apogee_vendor.model.items_model;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {
     private DatabaseReference database;
     private items_model obj=new items_model();
     private ArrayList<items_model> nlist=new ArrayList<>();
     RecyclerView recyclerView;
     itemListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        recyclerView=findViewById(R.id.itemsRecycler);
        adapter=new itemListAdapter(this,nlist);
        recyclerView.setAdapter(adapter);
        Intent intent=getIntent();
        String orderId=intent.getStringExtra("orderId");
        SharedPreferences prefs = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        String vid=prefs.getString("ID"," ");
        database= FirebaseDatabase.getInstance().getReference("vendors");
       database.child("vendors").child("vendor-".concat(vid)).child("orders").child("order-".concat(orderId))
               .child("items").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               Log.d("check",dataSnapshot.toString());
               for(DataSnapshot ds:dataSnapshot.getChildren()){
                   Log.d("check",ds.toString());
                   obj.setItemId(ds.getKey());
                   obj.setItemVal(ds.getValue().toString());
                   Log.d("check",obj.toString());
                   nlist.add(obj);

               }
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }
}
