package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TotalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        final TextView earning = (TextView)findViewById(R.id.earnings);



        DatabaseReference mDatabase;
        final String vendorId;
        SharedPreferences preferences = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        vendorId = "vendor - "+preferences.getInt("ID",0);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("vendors").child(vendorId).child("earnings").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("TotalCheck",dataSnapshot.toString());
                int earnings = Integer.parseInt(dataSnapshot.getValue().toString());
                earning.setText(earnings);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("TotalCheck",dataSnapshot.toString());
                int earnings = Integer.parseInt(dataSnapshot.getValue().toString());
                earning.setText(earnings);

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
