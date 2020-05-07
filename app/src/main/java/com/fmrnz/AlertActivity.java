package com.fmrnz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

public class AlertActivity extends BaseActivity {

    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_activity);
        String notificationType = getIntent().getStringExtra("Type");
        if(!TextUtils.isEmpty(notificationType)){
            if(notificationType.equalsIgnoreCase("offer")){
                Intent offerintent = new Intent(this, FmrOfferActivity.class);
                startActivity(offerintent);
                finish();

            }
            else if(notificationType.equalsIgnoreCase("referearn")){
                Intent referintent = new Intent(this, ReferActivity.class);
                startActivity(referintent);
                finish();
            }
            else{
                Intent referintent = new Intent(this, MainActivity.class);
                startActivity(referintent);
                finish();
            }
        }
        else{
            if(sessionManager.getLoggedIn()){
                Intent referintent = new Intent(this, MainActivity.class);
                startActivity(referintent);
                finish();
            }
        }

//        fetchIntentData();
    }
}
