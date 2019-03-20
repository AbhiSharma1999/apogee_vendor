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

import java.util.List;

/**
 * Created by abhishek on 20/3/19.
 */

public class pending_accepted_fragment extends android.support.v4.app.Fragment{

    static List<orders_model> pending_accepted_list;
    static orderListAdapter orderListAdapter2;
    public pending_accepted_fragment()
    {}

    public static pending_accepted_fragment newInstance(String param1 , String param2)
    {
        pending_accepted_fragment fragment = new pending_accepted_fragment();
        return fragment;
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
        return inflater.inflate(R.layout.pending_accepted_tab , container , false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onDetach() {
        super.onDetach();
        RecyclerView mMainList2 ;
        mMainList2 = (RecyclerView)getActivity().findViewById(R.id.pending_accepted_list);
        mMainList2.setHasFixedSize(true);
        mMainList2.setLayoutManager(new LinearLayoutManager(getContext()));
        //orderListAdapter2 = new orderListAdapter(pending_accepted_list);
        mMainList2.setAdapter(orderListAdapter2);


    }
}
