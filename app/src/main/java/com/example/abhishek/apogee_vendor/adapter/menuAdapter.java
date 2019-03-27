package com.example.abhishek.apogee_vendor.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishek.apogee_vendor.R;
import com.example.abhishek.apogee_vendor.model.menu_model;
import com.example.abhishek.apogee_vendor.model.toggle_post;
import com.example.abhishek.apogee_vendor.model.toggle_request_body;
import com.example.abhishek.apogee_vendor.remote.APIService;
import com.example.abhishek.apogee_vendor.remote.ApiUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menuAdapter extends RecyclerView.Adapter<menuAdapter.ViewHolder> {
     ArrayList<menu_model> menlist=new ArrayList<>();
     Context context;
     String JWT;
     public APIService mAPIService;

    public menuAdapter(ArrayList<menu_model>  nmenlist , String JWT) {
        this.menlist = nmenlist;
        this.JWT = JWT;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
       holder.Name.setText(menlist.get(position).getMenuname());
       holder.Price.setText(menlist.get(position).getPrice());
        holder.toggle.setChecked(menlist.get(position).getAvailablity());
        mAPIService = ApiUtils.getAPIService();
       holder.toggle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               boolean requesttoggle = !menlist.get(position).getAvailablity();
               toggle_request_body request_body =new toggle_request_body(Integer.parseInt(menlist.get(position).getItem_id()));
               Log.d("qwerty",menlist.get(position).getItem_id()+JWT+requesttoggle);
               sendPost(request_body,JWT,holder,requesttoggle);
               holder.toggle.setChecked(requesttoggle);


           }
       });
    }


    @Override
    public int getItemCount() {
        return menlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        View mView;
        TextView Price;
        Switch toggle;
        //ConstraintLayout parent;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            Name = (TextView)mView.findViewById(R.id.name);
            Price = (TextView)mView.findViewById(R.id.price);
            toggle=(Switch)mView.findViewById(R.id.switch1);
            // parent = itemView.findViewById(R.id.parent);
        }
    }


    public void sendPost(toggle_request_body request_body, String JWT, final ViewHolder view, final boolean b)
    {
        mAPIService.savetoggle_post(request_body,JWT).enqueue(new Callback<toggle_post>() {
            @Override
            public void onResponse(Call<toggle_post> call, Response<toggle_post> response) {
                Log.d("toggleresponse",response.code()+"");
                response.body();
                Toast.makeText(context,"responsecode"+response.code(),Toast.LENGTH_SHORT).show();
                if(!response.isSuccessful())
                {
                    view.toggle.setChecked(!b);
                    menlist.clear();
                }


            }

            @Override
            public void onFailure(Call<toggle_post> call, Throwable t) {
                view.toggle.setChecked(!b);
                menlist.clear();

            }
        });
    }


}
