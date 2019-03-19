package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.abhishek.apogee_vendor.adapter.orderListAdapter;
import com.example.abhishek.apogee_vendor.adapter.pagerAdapter;
import com.example.abhishek.apogee_vendor.fragment.finished_declined_fragment;
import com.example.abhishek.apogee_vendor.fragment.pending_accepted_fragment;
import com.example.abhishek.apogee_vendor.fragment.ready_fragment;
import com.example.abhishek.apogee_vendor.model.orders_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<orders_model> finished_declined_list= new ArrayList<>();
    ArrayList<orders_model> pending_accepted_list = new ArrayList<>();
    ArrayList<orders_model> ready_list = new ArrayList<>();
    orderListAdapter orderListAdapter1 = new orderListAdapter(finished_declined_list);
    orderListAdapter orderListAdapter2 = new orderListAdapter(pending_accepted_list);
    orderListAdapter orderListAdapter3 = new orderListAdapter(ready_list);
    static ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        int vendor = sp.getInt("ID" , 0);
        String vendorId = ""+vendor;



        if(!vendorId.equals(" "))
        {
            database.getReference().child("vendors").child("vendor-".concat(vendorId)).child("orders").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        finished_declined_list.clear();
                        pending_accepted_list.clear();
                        ready_list.clear();


                        if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 3 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 4) {
                            finished_declined_list.add(new orders_model(snapshot.getKey(),
                                    snapshot.child("timestamp").getValue().toString(),
                                    Integer.parseInt(snapshot.child("status").getValue().toString()),
                                    Integer.parseInt(snapshot.child("user_id").getValue().toString()),
                                    Integer.parseInt(snapshot.child("otp").getValue().toString()),
                                    Integer.parseInt(snapshot.child("price").getValue().toString())));

                            orderListAdapter1.notifyDataSetChanged();
                        } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 0 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 1) {
                            pending_accepted_list.add(new orders_model(snapshot.getKey(),
                                    snapshot.child("timestamp").getValue().toString(),
                                    Integer.parseInt(snapshot.child("status").getValue().toString()),
                                    Integer.parseInt(snapshot.child("user_id").getValue().toString()),
                                    Integer.parseInt(snapshot.child("otp").getValue().toString()),
                                    Integer.parseInt(snapshot.child("price").getValue().toString())));

                            orderListAdapter2.notifyDataSetChanged();
                        } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 2) {
                            ready_list.add(new orders_model(snapshot.getKey(),
                                    snapshot.child("timestamp").getValue().toString(),
                                    Integer.parseInt(snapshot.child("status").getValue().toString()),
                                    Integer.parseInt(snapshot.child("user_id").getValue().toString()),
                                    Integer.parseInt(snapshot.child("otp").getValue().toString()),
                                    Integer.parseInt(snapshot.child("price").getValue().toString())));

                            orderListAdapter3.notifyDataSetChanged();
                        }
                    }
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
            //Handle Error
        }

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tablayout);


        pagerAdapter pagerAdapter = new pagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new finished_declined_fragment());
        pagerAdapter.addFragment(new pending_accepted_fragment());
        pagerAdapter.addFragment(new ready_fragment());


        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.setCurrentItem(1);



    }
}
