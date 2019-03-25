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

public class finished_declined_fragment extends android.support.v4.app.Fragment{


    public static ArrayList<orders_model> finished_declined_list;
    public static orderListAdapter orderListAdapter1;


    public finished_declined_fragment()
    {}

    public static finished_declined_fragment newInstance(String param1 , String param2)
    {
        return new finished_declined_fragment();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.finished_declined_tab , container , false);
        RecyclerView mMainList;
        mMainList = (RecyclerView)v.findViewById(R.id.finished_declined_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(getContext()));
        if(finished_declined_list==null)
        {finished_declined_list=new ArrayList<>();}
        orderListAdapter1 = new orderListAdapter(finished_declined_list);
        mMainList.setAdapter(orderListAdapter1);
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
     //   finished_declined_list.clear();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();
     //   finished_declined_list.clear();
    }


}
