package com.example.abhishek.apogee_vendor.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.abhishek.apogee_vendor.R;
import com.example.abhishek.apogee_vendor.model.menu_model;

import java.util.ArrayList;

public class menuAdapter extends RecyclerView.Adapter<menuAdapter.ViewHolder> {
     ArrayList<menu_model> menlist=new ArrayList<>();
    public menuAdapter(ArrayList<menu_model>  nmenlist) {
        menlist.addAll(nmenlist);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.Name.setText(menlist.get(position).getMenuname());
       holder.Price.setText(menlist.get(position).getPrice());
    }


    @Override
    public int getItemCount() {
        return menlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Price;
        ConstraintLayout parent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Price = itemView.findViewById(R.id.price);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
