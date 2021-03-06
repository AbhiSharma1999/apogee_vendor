package com.example.abhishek.apogee_vendor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishek.apogee_vendor.R;
import com.example.abhishek.apogee_vendor.model.items_model;

import java.util.ArrayList;

public class itemListAdapter extends RecyclerView.Adapter<itemListAdapter.ViewHolder> {
    public ArrayList<items_model> mlist=new ArrayList<>();
    public itemListAdapter(ArrayList<items_model> nlist) {
        this.mlist = nlist;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
           holder.itemValue.setText(mlist.get(position).getItemVal());
           holder.itemName.setText(mlist.get(position).getItemName());



    }



    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView itemName;
        TextView itemValue;
        ConstraintLayout parent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
            itemName = (TextView)mView.findViewById(R.id.itemName);
            itemValue = (TextView)mView.findViewById(R.id.itemValue);
           // parent = itemView.findViewById(R.id.parent);
        }
    }
}
