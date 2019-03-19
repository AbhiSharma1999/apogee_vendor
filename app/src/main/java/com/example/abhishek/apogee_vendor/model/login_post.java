package com.example.abhishek.apogee_vendor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abhishek on 19/3/19.
 */

public class login_post {


    @SerializedName("occupation")
    // @Expose
    private Object occupation;
    @SerializedName("reg_token")
    //@Expose
    private String regToken;
    @SerializedName("name")
    //@Expose
    private String name;
    @SerializedName("user_id")
    //@Expose
    private Integer userId;
    @SerializedName("JWT")
    //@Expose
    private String jWT;
    @SerializedName("qr_code")
    //@Expose
    private String qrCode;
    @SerializedName("bitsian_id")
    //@Expose
    private String bitsianId;
    @SerializedName("email")
    //@Expose
    private String email;
    @SerializedName("phone")
    //@Expose
    private String phone;
    @SerializedName("status")
    //@Expose
    private Integer status;

    public Object getOccupation() {
        return occupation;
    }

    public void setOccupation(Object occupation) {
        this.occupation = occupation;
    }

    public String getRegToken() {
        return regToken;
    }

    public void setRegToken(String regToken) {
        this.regToken = regToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getJWT() {
        return jWT;
    }

    public void setJWT(String jWT) {
        this.jWT = jWT;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getBitsianId() {
        return bitsianId;
    }

    public void setBitsianId(String bitsianId) {
        this.bitsianId = bitsianId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
