package com.example.abhishek.apogee_vendor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abhishek on 20/3/19.
 */

public class decline_post {

    @SerializedName("new_status")
    @Expose
    private Integer newStatus;
    @SerializedName("display_message")
    @Expose
    private String displayMessage;

    public Integer getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Integer newStatus) {
        this.newStatus = newStatus;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

}
