package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TotalActivity extends AppCompatActivity {

    String a="check";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        final TextView earning = (TextView)findViewById(R.id.earnings);
        final ProgressBar total_progressbar = (ProgressBar)findViewById(R.id.total_progressbar);
        total_progressbar.setVisibility(View.VISIBLE);



        DatabaseReference mDatabase;
        final String vendorId;
        SharedPreferences preferences = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        vendorId = "vendor - "+preferences.getInt("ID",0);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("vendors").child(vendorId).child("earnings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // Toast.makeText(TotalActivity.this , "datasnapshot:"+dataSnapshot.toString() , Toast.LENGTH_LONG).show();
                a = dataSnapshot.getValue().toString();
                earning.setText("Total Earnings:Rs."+a);
                total_progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //earning.setText(a);

    }
}
