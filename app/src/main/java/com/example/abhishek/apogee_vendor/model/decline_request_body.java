package com.example.abhishek.apogee_vendor.model;

/**
 * Created by abhishek on 25/3/19.
 */

public class decline_request_body {
    final int order_id;

    public decline_request_body(int order_id)
    {
        this.order_id=order_id;
    }
}
