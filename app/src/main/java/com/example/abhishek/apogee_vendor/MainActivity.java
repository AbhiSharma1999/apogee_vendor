package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.abhishek.apogee_vendor.adapter.orderListAdapter;
import com.example.abhishek.apogee_vendor.model.orders_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<orders_model> ordersList= new ArrayList<>();
    orderListAdapter adapter = new orderListAdapter(ordersList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        String vendorId = sp.getString("ID" , " ");
        setContentView(R.layout.activity_main);
        if(!vendorId.equals(" "))
        {
            database.getReference().child("vendors").child("vendor-".concat(vendorId)).child("orders").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        ordersList.add(new orders_model(snapshot.getKey() ,
                                snapshot.child("timestamp").getValue().toString() ,
                                Integer.parseInt(snapshot.child("status").getValue().toString()) ,
                                Integer.parseInt(snapshot.child("user_id").getValue().toString()) ,
                                Integer.parseInt(snapshot.child("otp").getValue().toString()) ,
                                Integer.parseInt(snapshot.child("price").getValue().toString())));
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
        else
        {
            //Handel Error
        }

    }
}
