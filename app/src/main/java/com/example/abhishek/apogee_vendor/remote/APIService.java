package com.example.abhishek.apogee_vendor.remote;

import com.example.abhishek.apogee_vendor.model.login_post;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by abhishek on 19/3/19.
 */

public interface APIService {


    @POST("/auth")
    @Headers("Content-Type:application/json" +
            "X-Wallet-Token:sample_Token")
    Call<login_post> savelogin_post(@Field("username")String username,
                                    @Field("password")String password,
                                    @Field("reg_token")String reg_token);

}
