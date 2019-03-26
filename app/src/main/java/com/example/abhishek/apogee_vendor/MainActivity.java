package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.abhishek.apogee_vendor.adapter.orderListAdapter;
import com.example.abhishek.apogee_vendor.adapter.pagerAdapter;
import com.example.abhishek.apogee_vendor.fragment.finished_declined_fragment;
import com.example.abhishek.apogee_vendor.fragment.pending_accepted_fragment;
import com.example.abhishek.apogee_vendor.fragment.ready_fragment;
import com.example.abhishek.apogee_vendor.model.orders_model;
import com.example.abhishek.apogee_vendor.model.static_menu_model;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.abhishek.apogee_vendor.fragment.finished_declined_fragment.finished_declined_list;
import static com.example.abhishek.apogee_vendor.fragment.finished_declined_fragment.orderListAdapter1;
import static com.example.abhishek.apogee_vendor.fragment.pending_accepted_fragment.orderListAdapter2;
import static com.example.abhishek.apogee_vendor.fragment.pending_accepted_fragment.pending_accepted_list;
import static com.example.abhishek.apogee_vendor.fragment.ready_fragment.orderListAdapter3;
import static com.example.abhishek.apogee_vendor.fragment.ready_fragment.ready_list;


public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    public static ArrayList<String> namelist = new ArrayList<>();
    public static ArrayList<static_menu_model> namelist = new ArrayList<>();
    /*static ArrayList<orders_model> finished_declined_list= new ArrayList<>();
    static ArrayList<orders_model> pending_accepted_list = new ArrayList<>();
    static ArrayList<orders_model> ready_list = new ArrayList<>();
    static orderListAdapter orderListAdapter1 = new orderListAdapter(finished_declined_list);
    static orderListAdapter orderListAdapter2 = new orderListAdapter(pending_accepted_list);
    static orderListAdapter orderListAdapter3 = new orderListAdapter(ready_list);*/
    /*public static List<orders_model> pending_accepted_list;
    public static List<orders_model> finished_declined_list;
    public static List<orders_model> ready_list;*/
    static ViewPager viewPager;
    String vendorId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        int vendor = sp.getInt("ID" , 0);
        String JWT = sp.getString("JWT",null);
        if(JWT != null) Log.d("JWT",JWT);
        if(JWT==null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        vendorId = ""+vendor;

        final FirebaseDatabase name =FirebaseDatabase.getInstance();
        name.getReference().child("vendors").child("vendor - "+vendor).child("menu").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(namelist!=null)
                    namelist.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    namelist.add(new static_menu_model(ds.getKey(),ds.child("name").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






//        final FirebaseDatabase name =FirebaseDatabase.getInstance();
//
//        Log.d("items_vendor_id","vendor - "+vendor);
//
//
//        name.getReference().child("vendors").child("vendor - "+vendor).child("menu").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d("MenuArraylist1",dataSnapshot.toString());
//                namelist.add(dataSnapshot.child("name").getValue().toString());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d("MenuArraylist2",dataSnapshot.toString());
//                namelist.add(dataSnapshot.child("name").getValue().toString());
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });




















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


        /*finished_declined_list = new ArrayList<>();
        orderListAdapter1 = new orderListAdapter(finished_declined_list)    ;
        pending_accepted_list = new ArrayList<>();
        orderListAdapter2 = new orderListAdapter(pending_accepted_list);
        ready_list = new ArrayList<>();
        orderListAdapter3 = new orderListAdapter(ready_list);*/
        Log.d("VendorId",vendorId);



        if (finished_declined_list != null) {
            finished_declined_list.clear();
        }
        if (pending_accepted_list != null) {
            pending_accepted_list.clear();
        }
        if (ready_list != null) {
            ready_list.clear();
        }


        database.getReference().child("vendors").child("vendor - " + vendorId).child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(pending_accepted_list!=null && finished_declined_list!=null && ready_list!=null){
                finished_declined_list.clear();
                pending_accepted_list.clear();
                ready_list.clear();}
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 3 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 4) {
                    finished_declined_list.add(new orders_model(snapshot.getKey(),
                            snapshot.child("timestamp").getValue().toString(),
                            Integer.parseInt(snapshot.child("status").getValue().toString()),
                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
                            Integer.parseInt(snapshot.child("price").getValue().toString()),
                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
                    Log.d("Tag1", snapshot.toString());

                    orderListAdapter1.notifyDataSetChanged();
                } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 0 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 1) {
                    pending_accepted_list.add(new orders_model(snapshot.getKey(),
                            snapshot.child("timestamp").getValue().toString(),
                            Integer.parseInt(snapshot.child("status").getValue().toString()),
                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
                            Integer.parseInt(snapshot.child("price").getValue().toString()),
                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
                    Log.d("checkList", "q" + pending_accepted_list.size());
                    Log.d("Tag2", snapshot.toString());
                    orderListAdapter2.notifyDataSetChanged();
                } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 2) {
                    ready_list.add(new orders_model(snapshot.getKey(),
                            snapshot.child("timestamp").getValue().toString(),
                            Integer.parseInt(snapshot.child("status").getValue().toString()),
                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
                            Integer.parseInt(snapshot.child("price").getValue().toString()),
                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
                    Log.d("Tag3", snapshot.toString());
                    orderListAdapter3.notifyDataSetChanged();
                }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        database.getReference().child("vendors").child("vendor - " + vendorId).child("orders").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
//
//                Log.d("VendorId", 56 + "fwfwf");
//                Log.d("VendorId", snapshot.toString());
//
//                    /*finished_declined_list.clear();
//                    pending_accepted_list.clear();
//                    ready_list.clear();*/
//                //for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                if (snapshot.child("status").getValue() == null) {
//                    Log.d("mycheck", "NULL");
//                }
//
//                if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 3 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 4) {
//                    finished_declined_list.add(new orders_model(snapshot.getKey(),
//                            snapshot.child("timestamp").getValue().toString(),
//                            Integer.parseInt(snapshot.child("status").getValue().toString()),
//                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                            Integer.parseInt(snapshot.child("price").getValue().toString()),
//                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                    Log.d("Tag1", snapshot.toString());
//
//                    orderListAdapter1.notifyDataSetChanged();
//                } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 0 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 1) {
//                    pending_accepted_list.add(new orders_model(snapshot.getKey(),
//                            snapshot.child("timestamp").getValue().toString(),
//                            Integer.parseInt(snapshot.child("status").getValue().toString()),
//                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                            Integer.parseInt(snapshot.child("price").getValue().toString()),
//                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                    Log.d("checkList", "q" + pending_accepted_list.size());
//                    Log.d("Tag2", snapshot.toString());
//                    orderListAdapter2.notifyDataSetChanged();
//                } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 2) {
//                    ready_list.add(new orders_model(snapshot.getKey(),
//                            snapshot.child("timestamp").getValue().toString(),
//                            Integer.parseInt(snapshot.child("status").getValue().toString()),
//                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                            Integer.parseInt(snapshot.child("price").getValue().toString()),
//                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                    Log.d("Tag3", snapshot.toString());
//                    orderListAdapter3.notifyDataSetChanged();
//                }
//                // }
//            }

//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    /*finished_declined_list.clear();
//                    pending_accepted_list.clear();
//                    ready_list.clear();*/
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                    if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 3 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 4) {
//                        finished_declined_list.add(new orders_model(snapshot.getKey(),
//                                snapshot.child("timestamp").getValue().toString(),
//                                Integer.parseInt(snapshot.child("status").getValue().toString()),
//                                Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                                Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                                Integer.parseInt(snapshot.child("price").getValue().toString()),
//                                Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                        Log.d("Tag1", snapshot.toString());
//
//                        orderListAdapter1.notifyDataSetChanged();
//                    } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 0 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 1) {
//                        pending_accepted_list.add(new orders_model(snapshot.getKey(),
//                                snapshot.child("timestamp").getValue().toString(),
//                                Integer.parseInt(snapshot.child("status").getValue().toString()),
//                                Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                                Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                                Integer.parseInt(snapshot.child("price").getValue().toString()),
//                                Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                        Log.d("Tag2", snapshot.toString());
//                        orderListAdapter2.notifyDataSetChanged();
//                    } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 2) {
//                        ready_list.add(new orders_model(snapshot.getKey(),
//                                snapshot.child("timestamp").getValue().toString(),
//                                Integer.parseInt(snapshot.child("status").getValue().toString()),
//                                Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                                Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                                Integer.parseInt(snapshot.child("price").getValue().toString()),
//                                Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                        Log.d("Tag3", snapshot.toString());
//                        orderListAdapter3.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }



//    @Override
//    public void onRestart()
//    {
//        super.onRestart();
//        if (finished_declined_list != null) {
//            finished_declined_list.clear();
//        }
//        if (pending_accepted_list != null) {
//            pending_accepted_list.clear();
//        }
//        if (ready_list != null) {
//            ready_list.clear();
//        }
//
//
//        database.getReference().child("vendors").child("vendor - " + vendorId).child("orders").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String s) {
//
//                Log.d("VendorId", 56 + "fwfwf");
//                Log.d("VendorId", snapshot.toString());
//
//                    /*finished_declined_list.clear();
//                    pending_accepted_list.clear();
//                    ready_list.clear();*/
//                //for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                if (snapshot.child("status").getValue() == null) {
//                    Log.d("mycheck", "NULL");
//                }
//
//                if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 3 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 4) {
//                    finished_declined_list.add(new orders_model(snapshot.getKey(),
//                            snapshot.child("timestamp").getValue().toString(),
//                            Integer.parseInt(snapshot.child("status").getValue().toString()),
//                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                            Integer.parseInt(snapshot.child("price").getValue().toString()),
//                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                    Log.d("Tag1", snapshot.toString());
//
//                    orderListAdapter1.notifyDataSetChanged();
//                } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 0 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 1) {
//                    pending_accepted_list.add(new orders_model(snapshot.getKey(),
//                            snapshot.child("timestamp").getValue().toString(),
//                            Integer.parseInt(snapshot.child("status").getValue().toString()),
//                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                            Integer.parseInt(snapshot.child("price").getValue().toString()),
//                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                    Log.d("checkList", "q" + pending_accepted_list.size());
//                    Log.d("Tag2", snapshot.toString());
//                    orderListAdapter2.notifyDataSetChanged();
//                } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 2) {
//                    ready_list.add(new orders_model(snapshot.getKey(),
//                            snapshot.child("timestamp").getValue().toString(),
//                            Integer.parseInt(snapshot.child("status").getValue().toString()),
//                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                            Integer.parseInt(snapshot.child("price").getValue().toString()),
//                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                    Log.d("Tag3", snapshot.toString());
//                    orderListAdapter3.notifyDataSetChanged();
//                }
//                // }
//            }
//
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String s) {
//
//                    /*finished_declined_list.clear();
//                    pending_accepted_list.clear();
//                    ready_list.clear();*/
//                //for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 3 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 4) {
//                    finished_declined_list.add(new orders_model(snapshot.getKey(),
//                            snapshot.child("timestamp").getValue().toString(),
//                            Integer.parseInt(snapshot.child("status").getValue().toString()),
//                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                            Integer.parseInt(snapshot.child("price").getValue().toString()),
//                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                    Log.d("Tag1", snapshot.toString());
//
//                    orderListAdapter1.notifyDataSetChanged();
//                } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 0 || Integer.parseInt(snapshot.child("status").getValue().toString()) == 1) {
//                    pending_accepted_list.add(new orders_model(snapshot.getKey(),
//                            snapshot.child("timestamp").getValue().toString(),
//                            Integer.parseInt(snapshot.child("status").getValue().toString()),
//                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                            Integer.parseInt(snapshot.child("price").getValue().toString()),
//                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                    Log.d("Tag2", snapshot.toString());
//                    orderListAdapter2.notifyDataSetChanged();
//                } else if (Integer.parseInt(snapshot.child("status").getValue().toString()) == 2) {
//                    ready_list.add(new orders_model(snapshot.getKey(),
//                            snapshot.child("timestamp").getValue().toString(),
//                            Integer.parseInt(snapshot.child("status").getValue().toString()),
//                            Integer.parseInt(snapshot.child("user_id").getValue().toString()),
//                            Integer.parseInt(snapshot.child("otp").getValue().toString()),
//                            Integer.parseInt(snapshot.child("price").getValue().toString()),
//                            Boolean.parseBoolean(snapshot.child("otp_seen").getValue().toString())));
//                    Log.d("Tag3", snapshot.toString());
//                    orderListAdapter3.notifyDataSetChanged();
//                }
//            //}
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }




    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.items:
            {
                startActivity(new Intent(MainActivity.this,MenuActivity.class));
                break;
            }
            case R.id.signout:
            {
                SharedPreferences sharedPref = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("JWT",null);

                editor.commit();
                startActivity(new Intent(MainActivity.this , LoginActivity.class));
                finish();
                break;
            }
            case R.id.earnings:
            {
                startActivity(new Intent(MainActivity.this,TotalActivity.class));
                break;
            }
        }
        return true;




    }}
