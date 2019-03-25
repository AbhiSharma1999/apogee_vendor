package com.example.abhishek.apogee_vendor.remote;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.abhishek.apogee_vendor.model.advance_post;
import com.example.abhishek.apogee_vendor.model.advance_request_body;
import com.example.abhishek.apogee_vendor.model.decline_post;
import com.example.abhishek.apogee_vendor.model.decline_request_body;
import com.example.abhishek.apogee_vendor.model.login_post;
import com.example.abhishek.apogee_vendor.model.login_request_body;
import com.example.abhishek.apogee_vendor.model.toggle_post;
import com.example.abhishek.apogee_vendor.model.toggle_request_body;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by abhishek on 19/3/19.
 */

public interface APIService {
    @POST("auth")
    @Headers({"Content-Type:application/json","X-Wallet-Token:ec123dac-339b-41ba-bca4-d3cab464083d"})
    Call<login_post> savelogin_post(@Body login_request_body jsonObject);

    @POST("vendor/orders/advance")
    @Headers({"Content-Type:application/json","X-Wallet-Token:ec123dac-339b-41ba-bca4-d3cab464083d"})
    Call<advance_post> saveadvance_post(@Body advance_request_body jsonObject, @Header("Authorization")String JWT );

    @POST("vendor/orders/decline")
    @Headers({"Content-Type:application/json","X-Wallet-Token:ec123dac-339b-41ba-bca4-d3cab464083d"} )
    Call<decline_post> savedecline_post(@Body decline_request_body jsonObject , @Header("Authorization")String JWT);

    @POST("vendor/items/availability")
    @Headers({"Content-Type:application/json","X-Wallet-Token:ec123dac-339b-41ba-bca4-d3cab464083d"} )
    Call<toggle_post>savetoggle_post(@Body toggle_request_body body , @Header("Authorization")String JWT);
}
