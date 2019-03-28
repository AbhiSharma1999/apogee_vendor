package com.example.abhishek.apogee_vendor;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ImageView call1 = (ImageView)findViewById(R.id.callMe);
        ImageView call2 = (ImageView)findViewById(R.id.callSarthak);

        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String posted_by = "9587639838";

                String uri = "tel:" + posted_by.trim() ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String posted_by = "9871533158";

                String uri = "tel:" + posted_by.trim() ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

    }
}
