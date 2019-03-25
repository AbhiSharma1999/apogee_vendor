package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("Total",dataSnapshot.toString());
                String earnings="Total Earning:Rs."+dataSnapshot.child("vendors").child(vendorId).child("earnings").getValue().toString();

                earning.setText(earnings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
