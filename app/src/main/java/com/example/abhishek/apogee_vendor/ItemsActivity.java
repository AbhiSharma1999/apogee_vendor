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
import android.widget.Toast;

import com.example.abhishek.apogee_vendor.adapter.itemListAdapter;
import com.example.abhishek.apogee_vendor.model.advance_post;
import com.example.abhishek.apogee_vendor.model.decline_post;
import com.example.abhishek.apogee_vendor.model.items_model;
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

public class ItemsActivity extends AppCompatActivity {
     private DatabaseReference database;
    // private items_model obj=new items_model();
     private ArrayList<items_model> nlist=new ArrayList<>();
     RecyclerView recyclerView;
     itemListAdapter adapter;
     int orderIdvalue;
     private APIService mAPIService;
    public Button bAccept ,bDecline , bReady ,bFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);




       bAccept=(Button)findViewById(R.id.button_accept);
       bDecline=(Button)findViewById(R.id.button_decline);
       bFinish=(Button)findViewById(R.id.button_finish);
       bReady = (Button)findViewById(R.id.button_ready);

        final items_model obj = new items_model();
        recyclerView=findViewById(R.id.itemsRecycler);
        adapter=new itemListAdapter(this,nlist);
        recyclerView.setAdapter(adapter);
        Intent intent=getIntent();
        final String orderId=intent.getStringExtra("orderId");
        final int status = intent.getIntExtra("status",0);
        orderIdvalue = Integer.parseInt(orderId.replaceAll("[^0-9]",""));
        SharedPreferences prefs = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        final String vid=prefs.getString("ID"," ");
        final String JWT = "JWT ".concat(prefs.getString("JWT",""));
        database= FirebaseDatabase.getInstance().getReference("vendors");
       if(!orderId.equals("")) {

           database.child("vendors").child("vendor - ".concat(vid)).child("orders").child(orderId)
                   .child("items").addChildEventListener(new ChildEventListener() {



               @Override
               public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               }

               @Override
               public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                   nlist.clear();
                   Log.d("check", dataSnapshot.toString());
                   for (DataSnapshot ds : dataSnapshot.getChildren()) {


                       int key = Integer.parseInt(ds.getKey());
                       String item_name = ds.child("vendors").child("vendor - ".concat(vid)).child("menu").child(""+key).child("name").getValue().toString();
                       obj.setItemName(item_name);
                       obj.setItemId(ds.getKey());

                       obj.setItemVal(ds.getValue().toString());
                       Log.d("check", obj.toString());
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
       else
       {
           //handle error
       }
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

                sendAdvacePost(orderIdvalue,JWT,status);
                bDecline.setClickable(false);

           }
       });
       bReady.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               sendAdvacePost(orderIdvalue,JWT,status);
           }
       });
       bFinish.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               sendAdvacePost(orderIdvalue,JWT,status);
           }
       });
       bDecline.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               sendDeclinePost(orderIdvalue,JWT,status);
               bAccept.setClickable(false);
           }
       });


    }

    public void sendAdvacePost(int orderIdvalue , String JWT , final int status)
    {
        mAPIService.saveadvance_post(orderIdvalue,JWT).enqueue(new Callback<advance_post>() {
            @Override
            public void onResponse(Call<advance_post> call, Response<advance_post> response) {
                if(response.isSuccessful())
                {
                    advance_post responselogin =new advance_post();
                    String displayMessage = responselogin.getDisplayMessage();
                    Toast.makeText(ItemsActivity.this,displayMessage,Toast.LENGTH_SHORT).show();
                   if(response.code()==200)
                   {
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
                        bFinish.setVisibility(View.VISIBLE);
                    }
                    else if(status==2)
                    {
                        bAccept.setVisibility(View.GONE);
                        bDecline.setVisibility(View.GONE);
                    }
                }}
            }

            @Override
            public void onFailure(Call<advance_post> call, Throwable t) {
                Toast.makeText(ItemsActivity.this,"Failed to send request",Toast.LENGTH_SHORT).show();
                if(status==0)
                {
                    bDecline.setClickable(true);
                }


            }
        });
    }

    public void sendDeclinePost(int orderIdvalue, String JWT, final int status)
    {
        mAPIService.savedecline_post(orderIdvalue,JWT ).enqueue(new Callback<decline_post>() {
            @Override
            public void onResponse(Call<decline_post> call, Response<decline_post> response) {
                if(response.isSuccessful())
                {
                    advance_post responselogin =new advance_post();
                    String displayMessage = responselogin.getDisplayMessage();
                    Toast.makeText(ItemsActivity.this,displayMessage,Toast.LENGTH_SHORT).show();
                    if(response.code()==200)
                    {
                        bAccept.setVisibility(View.GONE);
                        bDecline.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<decline_post> call, Throwable t) {
                Toast.makeText(ItemsActivity.this,"Failed to send request",Toast.LENGTH_SHORT).show();
            if(status==0)
            {
                bAccept.setClickable(true);
            }
            }
        });
    }
}
