package com.example.abhishek.apogee_vendor.model;

/**
 * Created by abhishek on 19/3/19.
 */

public class orders_model {
    int status , user_id , otp , price ;
    String timestamp , order_id;
    boolean otp_seen;


    public orders_model(String order_id , String timestamp,int  status, int user_id, int otp, int price,boolean otp_seen) {
        this.status = status;
        this.user_id = user_id;
        this.otp = otp;
        this.price = price;
        this.order_id = order_id;
        this.otp_seen=otp_seen;
        this.timestamp = timestamp;
    }

    public orders_model() {
    }

    public void setOtp_seen(boolean otp_seen) {
        this.otp_seen = otp_seen;
    }

    public boolean getOtp_seen() {
        return otp_seen;
    }

    public void setTimestamp(String timestamp)
    {
            this.timestamp = timestamp;
    }

    public String getTimestamp()
    {
        return timestamp;
    }
    public void setOrder_id(String order_id)
    {
        this.order_id = order_id;
    }

    public String getOrder_id()
    {
        return order_id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {

        return status;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getOtp() {
        return otp;
    }

    public int getPrice() {
        return price;
    }
}
