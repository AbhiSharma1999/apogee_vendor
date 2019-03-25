package com.example.abhishek.apogee_vendor.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhishek.apogee_vendor.R;
import com.example.abhishek.apogee_vendor.adapter.orderListAdapter;
import com.example.abhishek.apogee_vendor.model.orders_model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek on 20/3/19.
 */

public class ready_fragment extends android.support.v4.app.Fragment{

    public static ArrayList<orders_model> ready_list;
    public static orderListAdapter orderListAdapter3;
    public ready_fragment()
    {}

    public static ready_fragment newInstance(String param1 , String param2)
    {
        return new ready_fragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.ready_tab , container , false);;
        RecyclerView mMainList3 ;
        mMainList3 = (RecyclerView)v.findViewById(R.id.ready_list);
        mMainList3.setHasFixedSize(true);
        mMainList3.setLayoutManager(new LinearLayoutManager(getContext()));
        if(ready_list==null)
        {ready_list=new ArrayList<>();}
        orderListAdapter3=new orderListAdapter(ready_list);
        mMainList3.setAdapter(orderListAdapter3);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDetach() {
        super.onDetach();
     //   ready_list.clear();


    }
}
