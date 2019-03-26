package com.example.abhishek.apogee_vendor.model;

/**
 * Created by abhishek on 26/3/19.
 */

public class static_menu_model {
    String key;
    String value;

    public static_menu_model(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {

        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "("+ key + ", " + value + ")";
    }
}
