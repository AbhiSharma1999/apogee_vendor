package com.example.abhishek.apogee_vendor.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abhishek.apogee_vendor.ItemsActivity;
import com.example.abhishek.apogee_vendor.R;
import com.example.abhishek.apogee_vendor.model.orders_model;

import java.util.List;

/**
 * Created by abhishek on 19/3/19.
 */

public class orderListAdapter extends RecyclerView.Adapter<orderListAdapter.ViewHolder>{


    public List<orders_model> ordersModelList;
    public orderListAdapter(List<orders_model> ordersModelList)
    {
        this.ordersModelList = ordersModelList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_row,parent,false);
        return new ViewHolder(view);

    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.user_id.setText(ordersModelList.get(position).getUser_id()+"");
        holder.timestamp.setText(ordersModelList.get(position).getTimestamp());
        holder.order_id.setText(ordersModelList.get(position).getOrder_id());


        if((ordersModelList.get(position).getStatus()+"").equals("0"))
        {
            holder.status.setText(R.string.status0);
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        }

        if((ordersModelList.get(position).getStatus()+"").equals("1"))
        {
            holder.status.setText(R.string.status1);
            holder.status.setTextColor(Color.parseColor("##00800"));
        }

        if((ordersModelList.get(position).getStatus()+"").equals("2"))
        {
            holder.status.setText(R.string.status2);
            holder.status.setTextColor(Color.parseColor("#008000"));
        }

        if((ordersModelList.get(position).getStatus()+"").equals("3"))
        {
            holder.status.setText(R.string.status3);
            holder.status.setTextColor(Color.parseColor("#008000"));
        }

        if((ordersModelList.get(position).getStatus()+"").equals("4"))
        {
            holder.status.setText(R.string.status4);
            holder.status.setTextColor(Color.parseColor("#FF0000"));
        }

        holder.otp.setText("OTP:"+ordersModelList.get(position).getOtp());
        holder.otp.setTextColor(Color.parseColor("#3F51B5"));

        holder.price.setText(ordersModelList.get(position).getPrice()+"");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.mView.getContext() , ItemsActivity.class);
                intent.putExtra("orderId",ordersModelList.get(position).getOrder_id());
                intent.putExtra("status",ordersModelList.get(position).getStatus());
                holder.mView.getContext().startActivity(intent);

            }
        });

    }







    @Override
    public int getItemCount() {
        return 0;
    }







    public class ViewHolder extends RecyclerView.ViewHolder {
       View mView;
       public TextView otp , user_id , price , timestamp , status , order_id;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            user_id = (TextView)mView.findViewById(R.id.user_id);
            order_id =(TextView)mView.findViewById(R.id.order_id);
            price = (TextView)mView.findViewById(R.id.price);
            timestamp = (TextView)mView.findViewById(R.id.timestamp);
            status = (TextView)mView.findViewById(R.id.status);
            otp = (TextView)mView.findViewById(R.id.otp);
        }
    }
}
