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
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abhishek.apogee_vendor.adapter.itemListAdapter;
import com.example.abhishek.apogee_vendor.model.advance_post;
import com.example.abhishek.apogee_vendor.model.advance_request_body;
import com.example.abhishek.apogee_vendor.model.decline_post;
import com.example.abhishek.apogee_vendor.model.decline_request_body;
import com.example.abhishek.apogee_vendor.model.items_model;
import com.example.abhishek.apogee_vendor.model.static_menu_model;
import com.example.abhishek.apogee_vendor.remote.APIService;
import com.example.abhishek.apogee_vendor.remote.ApiUtils;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.client.authentication.util.JsonWebToken;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.abhishek.apogee_vendor.MainActivity.namelist;

public class ItemsActivity extends AppCompatActivity {
     private FirebaseDatabase database;
     ProgressBar items_progressbar = (ProgressBar)findViewById(R.id.items_progressbar);
     ProgressBar buttons_progressbar = (ProgressBar)findViewById(R.id.buttons_progressbar);
    // private items_model obj=new items_model();
     public ArrayList<items_model> nlist=new ArrayList<>();
     RecyclerView recyclerView;
     //public  ArrayList<String> namelist = new ArrayList<>();
     itemListAdapter adapter;
     int orderIdvalue;
     boolean otp_seen;
     int status;
     private APIService mAPIService;
    public Button bAccept ,bDecline , bReady ,bFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        items_progressbar.setVisibility(View.VISIBLE);



        mAPIService = ApiUtils.getAPIService();
       bAccept=(Button)findViewById(R.id.button_accept);
       bDecline=(Button)findViewById(R.id.button_decline);
       bFinish=(Button)findViewById(R.id.button_finish);
       bReady = (Button)findViewById(R.id.button_ready);

       if(nlist!=null)
        nlist.clear();

        recyclerView=findViewById(R.id.itemsRecycler);

        Intent intent=getIntent();
        final String orderId=intent.getStringExtra("orderId");
        //final int status = intent.getIntExtra("status",0);

        orderIdvalue = Integer.parseInt(orderId.replaceAll("[^0-9]",""));
        SharedPreferences prefs = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        final int vid=prefs.getInt("ID",0);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("vendors").child("vendor - "+vid).child("orders").child(orderId).child("otp_seen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                otp_seen = (Boolean)dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mStatus =FirebaseDatabase.getInstance().getReference();

        mStatus.child("vendors").child("vendor - "+vid).child("orders").child(orderId).child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status = (Integer)dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //bFinish.setClickable(otp_seen);
        final String JWT = "JWT ".concat(prefs.getString("JWT",""));
        database= FirebaseDatabase.getInstance();
       /* final FirebaseDatabase name =FirebaseDatabase.getInstance();

            Log.d("items_vendor_id","vendor - "+vid);


        name.getReference().child("vendors").child("vendor - "+vid).child("menu").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("MenuArraylist1",dataSnapshot.toString());
                namelist.add(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("MenuArraylist2",dataSnapshot.toString());
                namelist.add(dataSnapshot.child("name").getValue().toString());

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
        });*/



          /* database.getReference().child("vendors").child("vendor - "+vid).child("orders").child(orderId)
                   .child("items").addChildEventListener(new ChildEventListener() {



               @Override
               public void onChildAdded(@NonNull DataSnapshot ds, @Nullable String s) {
                   /*nlist.clear();
                   Log.d("check", ds.toString());
                   hello world



                   int key = Integer.parseInt(ds.getKey());
                   String item_name = ds.child("vendors").child("vendor - "+vid).child("menu").child(""+key).child("name").getValue().toString();
                   obj.setItemName(item_name);
                   obj.setItemId(ds.getKey());

                   obj.setItemVal(ds.getValue().toString());
                   Log.d("check", obj.toString());
                   nlist.add(obj);*/
                  /*  Log.d("Items_check",ds.toString());
                   int key = Integer.parseInt(ds.getKey());
                   //String item_name = ds.child("vendors").child("vendor - "+vid).child("menu").child(""+key).child("name").getValue().toString();
                   String item_name = namelist.get(key-1);

                   String item_val = ds.getValue().toString();
                   nlist.add(new items_model(item_name,item_val));
                   adapter.notifyDataSetChanged();


               }

               @Override
               public void onChildChanged(@NonNull DataSnapshot ds, @Nullable String s) {

                   /*nlist.clear();
                   Log.d("check", ds.toString());



                       int key = Integer.parseInt(ds.getKey());
                       String item_name = ds.child("vendors").child("vendor - "+vid).child("menu").child(""+key).child("name").getValue().toString();
                       obj.setItemName(item_name);
                       obj.setItemId(ds.getKey());

                       obj.setItemVal(ds.getValue().toString());
                       Log.d("check", obj.toString());
                       nlist.add(obj);*/


                 /*  adapter.notifyDataSetChanged();*/
                 //  Log.d("Items_check",ds.toString());
                   /*int key = Integer.parseInt(ds.getKey());
                   String item_name = ds.child("vendors").child("vendor - "+vid).child("menu").child(""+key).child("name").getValue().toString();
                   String item_name = namelist.get(key-1);
                   String item_val = ds.getValue().toString();
                   nlist.add(new items_model(item_name,item_val));
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
           });*/
        database.getReference().child("vendors").child("vendor - "+vid).child("orders").child(orderId)
                .child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items_progressbar.setVisibility(View.GONE);
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String key =ds.getKey();
                    String item_name="Check again";
                    Toast.makeText(ItemsActivity.this,namelist.toString(),Toast.LENGTH_LONG).show();
                    for(int x=0;x<namelist.size();x++)
                    {
                        static_menu_model list = namelist.get(x);
                        if(list.getKey().equals(key))
                        {
                            item_name = list.getValue();
                        }
                    }
                    String item_val = ds.getValue().toString();
                    nlist.add(new items_model(item_name,item_val));
                    adapter.notifyDataSetChanged();
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter=new itemListAdapter(nlist);
        recyclerView.setAdapter(adapter);

       if(status==1)
       {
            bAccept.setVisibility(View.GONE);
            bDecline.setVisibility(View.GONE);
            bReady.setVisibility(View.VISIBLE);
       }
       else if(status==2)
       {
           bAccept.setVisibility(View.GONE);
           bDecline.setVisibility(View.GONE);
           bFinish.setVisibility(View.VISIBLE);
       }
       else if (status==3 || status==4)
       {
           bAccept.setVisibility(View.GONE);
           bDecline.setVisibility(View.GONE);
       }







        mAPIService = ApiUtils.getAPIService();
       bAccept.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               buttons_progressbar.setVisibility(View.VISIBLE);
               advance_request_body request_body = new advance_request_body(orderIdvalue);
               sendAdvacePost(request_body,JWT,status);
                bDecline.setClickable(false);

           }
       });
       bReady.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               buttons_progressbar.setVisibility(View.VISIBLE);
               advance_request_body request_body = new advance_request_body(orderIdvalue);
               sendAdvacePost(request_body,JWT,status);

           }
       });
       bFinish.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               buttons_progressbar.setVisibility(View.VISIBLE);
               if(otp_seen)
                   Toast.makeText(ItemsActivity.this,"Please check the otp from user",Toast.LENGTH_SHORT).show();
              else{ advance_request_body request_body = new advance_request_body(orderIdvalue);
               sendAdvacePost(request_body,JWT,status);}
           }
       });
       bDecline.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               buttons_progressbar.setVisibility(View.VISIBLE);
               decline_request_body request_body = new decline_request_body(orderIdvalue);
               sendDeclinePost(request_body,JWT,status);
               bAccept.setClickable(false);
           }
       });


    }

    public void sendAdvacePost(advance_request_body orderIdvalue , String JWT , final int status)
    {
        mAPIService.saveadvance_post(orderIdvalue,JWT).enqueue(new Callback<advance_post>() {
            @Override
            public void onResponse(Call<advance_post> call, Response<advance_post> response) {
                buttons_progressbar.setVisibility(View.GONE);
                if(response.isSuccessful())
                {
                    response.body();
                    String displayMessage = response.body().getDisplayMessage();
                    Toast.makeText(ItemsActivity.this,displayMessage,Toast.LENGTH_SHORT).show();
                    if(status==0)
                    {
                        bAccept.setVisibility(View.GONE);
                        bDecline.setVisibility(View.GONE);
                        bReady.setVisibility(View.VISIBLE);
                    }
                    else if(status==1)
                    {
                        bAccept.setVisibility(View.GONE);
                        bDecline.setVisibility(View.GONE);
                        bReady.setVisibility(View.GONE);
                        bFinish.setVisibility(View.VISIBLE);
                    }
                    else if(status==2)
                    {
                        bAccept.setVisibility(View.GONE);
                        bDecline.setVisibility(View.GONE);
                        bReady.setVisibility(View.GONE);
                        bFinish.setVisibility(View.GONE);
                    }
                }
                else
                {
                    bDecline.setClickable(true);
                    Toast.makeText(ItemsActivity.this,"Not successful"+response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<advance_post> call, Throwable t) {
                Toast.makeText(ItemsActivity.this,"Failed to send request",Toast.LENGTH_SHORT).show();
                buttons_progressbar.setVisibility(View.GONE);
                if(status==0)
                {
                    bDecline.setClickable(true);
                }


            }
        });
    }

    public void sendDeclinePost(decline_request_body orderIdvalue, String JWT, final int status)
    {
        mAPIService.savedecline_post(orderIdvalue,JWT ).enqueue(new Callback<decline_post>() {
            @Override
            public void onResponse(Call<decline_post> call, Response<decline_post> response) {
                buttons_progressbar.setVisibility(View.GONE);
                if(response.isSuccessful())
                {
                    response.body();
                    String displayMessage = response.body().getDisplayMessage();
                    Toast.makeText(ItemsActivity.this,displayMessage,Toast.LENGTH_SHORT).show();
                    bAccept.setVisibility(View.GONE);
                    bDecline.setVisibility(View.GONE);

                }
                else
                {
                    bAccept.setClickable(true);
                    Toast.makeText(ItemsActivity.this,"Not successful"+response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<decline_post> call, Throwable t) {
                buttons_progressbar.setVisibility(View.GONE);
                Toast.makeText(ItemsActivity.this,"Failed to send request",Toast.LENGTH_SHORT).show();
            if(status==0)
            {
                bAccept.setClickable(true);
            }
            }
        });
    }
}
