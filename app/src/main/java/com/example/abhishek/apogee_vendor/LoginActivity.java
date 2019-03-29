package com.example.abhishek.apogee_vendor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abhishek.apogee_vendor.model.login_post;
import com.example.abhishek.apogee_vendor.model.login_request_body;
import com.example.abhishek.apogee_vendor.remote.APIService;
import com.example.abhishek.apogee_vendor.remote.ApiUtils;
import com.example.abhishek.apogee_vendor.service.MyFirebaseInstanceIdService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ProgressBar login_progressbar;
    String final_token;
    String user;
    String pass;

    private APIService mAPIService;
    String JWT="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
          login_progressbar = (ProgressBar)findViewById(R.id.login_progressbar);


        Button button_login ;
        final EditText et_password , et_username;

        startService(new Intent(this , MyFirebaseInstanceIdService.class));

        button_login = (Button)findViewById(R.id.button_login);
        et_password = (EditText)findViewById(R.id.password);
        et_username = (EditText)findViewById(R.id.username);
        final String username = et_username.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        SharedPreferences preferences = this.getSharedPreferences("Data", Context.MODE_PRIVATE);
        final String reg_token = preferences.getString("fcm","a");







        mAPIService = ApiUtils.getAPIService();


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = et_username.getText().toString().trim();
                pass = et_password.getText().toString().trim();
                final_token = "";
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        final_token = instanceIdResult.getToken();
                        Log.d("Test Token" , "Token = " + final_token);
                        final login_request_body request_body = new login_request_body(user,pass,final_token);
                        if(user.equals("")||pass.equals(""))
                        {
                            Toast.makeText(LoginActivity.this , "username and password cannot be left blank",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            login_progressbar.setVisibility(View.VISIBLE);
                            sendPOST(request_body);
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this , "Failed to get registration token" , Toast.LENGTH_LONG).show();
                    }
                });


            }

        });





    }

    public void sendPOST(final login_request_body requestbody)
    {
        mAPIService.savelogin_post(requestbody).enqueue(new Callback<login_post>() {
            @Override
            public void onResponse(Call<login_post> call, Response<login_post> response) {
                login_progressbar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                  response.body();
                  int userID = response.body().getUserId();
                  String JWT = response.body().getJWT();
                    Log.d("post submitted to API", ""+response.body().toString());


                    SharedPreferences sp = LoginActivity.this.getSharedPreferences("Data",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("JWT",JWT);
                    editor.putInt("ID",userID);
                    editor.commit();

                    startActivity(new Intent(LoginActivity.this , MainActivity.class));
                    finish();
                }
                else
                {
                    Log.d("bad response",""+response.code());
                    Toast.makeText(LoginActivity.this , "response code"+response.code(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<login_post> call, Throwable t) {
                login_progressbar.setVisibility(View.GONE);
                Log.d("status","Cannot submit the post request");
            }
        });
    }

}
