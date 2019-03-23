package com.example.abhishek.apogee_vendor.remote;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.abhishek.apogee_vendor.model.advance_post;
import com.example.abhishek.apogee_vendor.model.decline_post;
import com.example.abhishek.apogee_vendor.model.login_post;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by abhishek on 19/3/19.
 */

public interface APIService {
   // SharedPreferences prefs = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
   // String JWT = "JWT ".concat(prefs.getString("JWT",""));

    @POST("auth")
    @Headers({"Content-Type : application/json","X-Wallet-Token:samp1e_Token"})
    Call<login_post> savelogin_post(@Field("username")String username,
                                    @Field("password")String password,
                                    @Field("reg_token")String reg_token);

    @POST("advance")
    @Headers({"Content-Type : application/json","X-Wallet-Token:samp1e_Token"})
    Call<advance_post> saveadvance_post(@Field("order_id")int order_id , @Header("Authorization")String JWT );

    @POST("decline")
    @Headers({"Content-Type : application/json","X-Wallet-Token : samp1e_Token"} )
    Call<decline_post> savedecline_post(@Field("order_id")int order_id , @Header("Authorization")String JWT);

}
