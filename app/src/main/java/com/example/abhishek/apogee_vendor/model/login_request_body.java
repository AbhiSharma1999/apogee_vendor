package com.example.abhishek.apogee_vendor.model;

import android.util.Log;

/**
 * Created by abhishek on 24/3/19.
 */

public class login_request_body {
    final String username;
    final String password;
    final String reg_token;

    public login_request_body(String username , String password , String reg_token)
    {
        this.username = username;
        this.password = password;
        this.reg_token=reg_token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
