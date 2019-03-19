package com.example.abhishek.apogee_vendor.remote;

/**
 * Created by abhishek on 19/3/19.
 */

public class ApiUtils {
    private  ApiUtils(){}
    public  static final String BASE_URL = "http://139.59.64.214/wallet/";
    public static APIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

    }


}
