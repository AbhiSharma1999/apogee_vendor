package com.example.abhishek.apogee_vendor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abhishek.apogee_vendor.model.login_post;
import com.example.abhishek.apogee_vendor.model.login_request_body;
import com.example.abhishek.apogee_vendor.remote.APIService;
import com.example.abhishek.apogee_vendor.remote.ApiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Button button_login ;
        final EditText et_password , et_username;

        button_login = (Button)findViewById(R.id.button_login);
        et_password = (EditText)findViewById(R.id.password);
        et_username = (EditText)findViewById(R.id.username);
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();






        mAPIService = ApiUtils.getAPIService();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                final login_request_body request_body = new login_request_body(username,password,"");
                if(username.equals("")||password.equals(""))
                {
                    Toast.makeText(LoginActivity.this , "username and password cannot be left blank",Toast.LENGTH_SHORT).show();
                }
                else {
                    sendPOST(request_body);

                }
            }

        });





    }

    public void sendPOST(final login_request_body requestbody)
    {
        mAPIService.savelogin_post(requestbody).enqueue(new Callback<login_post>() {
            @Override
            public void onResponse(Call<login_post> call, Response<login_post> response) {
                if(response.isSuccessful()){
                    login_post responselogin = new login_post();
                    String JWT = responselogin.getJWT();
                    int userID = 0;
                    if(responselogin.getUserId()!=0) {
                        userID = responselogin.getUserId();
                    }
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
                Log.d("status","Cannot submit the post request");
            }
        });
    }

}
