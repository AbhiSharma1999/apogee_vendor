package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abhishek.apogee_vendor.adapter.menuAdapter;
import com.example.abhishek.apogee_vendor.model.menu_model;
import com.google.firebase.database.ChildEventListener;
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
    ProgressBar menu_progressbar = (ProgressBar)findViewById(R.id.menu_progressbar);

  //  Button item_switch;

    private static int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if(menulist!=null)
        {
            menulist.clear();
        }
        menu_progressbar.setVisibility(View.VISIBLE);

     //   item_switch = (Button)findViewById(R.id.switch1);
        recyclerView=findViewById(R.id.menuRecycler);

        SharedPreferences prefs = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        String vid=prefs.getInt("ID",0)+"";
        String JWT ="JWT "+ prefs.getString("JWT",null);
       final FirebaseDatabase database = FirebaseDatabase.getInstance();

//       database.getReference().child("vendors").child("vendor - "+vid).child("menu").addChildEventListener(new ChildEventListener() {
//
//           @Override
//           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//               Log.d("MenuActivity1",dataSnapshot.toString());
//               String name = dataSnapshot.child("name").getValue().toString();
//               String price = dataSnapshot.child("price").getValue().toString();
//               String availablity = dataSnapshot.child("is_available").getValue().toString();
//               String item_id = dataSnapshot.getKey().toString();
//
//               menulist.add(new menu_model(name,price,Boolean.parseBoolean(availablity),item_id));
////               menulist.add(Integer.parseInt(dataSnapshot.getKey()), new menu_model(name,price,Boolean.parseBoolean(availablity),item_id));
//               adapter.notifyDataSetChanged();
//
//
//           }
//
//           @Override
//           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//               Log.d("MenuActivity2",dataSnapshot.toString());
//               String name = dataSnapshot.child("name").getValue().toString();
//               String price = dataSnapshot.child("price").getValue().toString();
//               String availablity = dataSnapshot.child("is_available").getValue().toString();
//               String item_id = dataSnapshot.getKey().toString();
//               menulist.add(new menu_model(name,price,Boolean.parseBoolean(availablity),item_id));
////               menulist.add(Integer.parseInt(dataSnapshot.getKey()), new menu_model(name,price,Boolean.parseBoolean(availablity),item_id));
//                adapter.notifyDataSetChanged();
//
//           }
//
//           @Override
//           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//           }
//
//           @Override
//           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError databaseError) {
//
//           }
//       });

        database.getReference().child("vendors").child("vendor - "+vid).child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  Toast.makeText(MenuActivity.this, dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                menu_progressbar.setVisibility(View.GONE);
                menulist.clear();
                for(DataSnapshot menuItemSnapshot: dataSnapshot.getChildren()) {
                    String itemId = menuItemSnapshot.getKey();
                    boolean isAvailable = (Boolean) menuItemSnapshot.child("is_available").getValue();
                    String name = menuItemSnapshot.child("name").getValue().toString();
                    String price = menuItemSnapshot.child("price").getValue().toString();
                    menulist.add(new menu_model(name, price, isAvailable, itemId));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter=new menuAdapter(menulist,JWT);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
}



}
