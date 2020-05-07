package com.fmrnz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.fmrNetworking.AtechnosServerService;
import com.fmrnz.fmrNetworking.RetrofitRestController;
import com.fmrnz.pojo.ResponseForShareAndEarn;
import com.fmrnz.pojo.ResponseToken;

import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferActivity extends BaseActivity {

    TextView mReferText;
    Button readAbout;
    ACProgressFlower mdialog;
    String mResultForShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        ActionBar ab =getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
        mdialog = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();

        if(!TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID)))
            sendRequestForShareRate();

        else
            appNotLogin();


        readAbout = (Button)findViewById(R.id.readAbout);
        mReferText = (TextView)findViewById(R.id.refertext);

        readAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String app_share = "https://play.google.com/store/apps/details?id=com.nhp.bloodbank&referrer=ABCPQR";
                Intent sendIntent = new Intent();
                String msgString = app_share;
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, msgString);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                sendInvitesRequest();
            }
        });


    }

    private void sendRequestForShareRate() {
        mdialog.show();
        AtechnosServerService Service = RetrofitRestController.getClient().create(AtechnosServerService.class);
        Call<ResponseForShareAndEarn> call = Service.getResponseForShareAndEarn();
        // TODO: 01/12/17 Below mentioned code is for aSynchronus call
        call.enqueue(new Callback<ResponseForShareAndEarn>() {
            @Override
            public void onResponse(Call<ResponseForShareAndEarn> call, Response<ResponseForShareAndEarn> response) {
                mResultForShare =response.body().getResult();
                mdialog.hide();
                mReferText.setText("Invite your friends to join Find My Ride NZ by using your referral link." +
                        " You get a one time reward of either on each referral joining." +
                        "1. FMR points worth"+mResultForShare+" when they hire the first vehicle" +
                        "  2. FMR points worth"+mResultForShare+" when their listed vehicle goes for first rental." +
                        " FMR Points can only be redeemed by availing discounts in form of rides" );

            }
            @Override
            public void onFailure(Call<ResponseForShareAndEarn> call, Throwable t) {
                Toast.makeText(ReferActivity.this,"loading Failed",Toast.LENGTH_SHORT).show();
                mdialog.hide();
            }
        });
    }

    private void sendInvitesRequest(){
        ESAppRequest esAppRequest = (ESAppRequest)networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.SENT_INVITES);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.fetchProfileData().get(SessionManager.KEY_ID));
        hashMap.put("refer", "ABCPQR");
        esAppRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esAppRequest);
    }


    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        switch (eventType){
            case ESNetworkRequest.NetworkEventType.SENT_INVITES:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;
                    //  showDialogue("Succes", responseMessage);
                }
                else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                    String responseMessage = networkResponse.responseMessage;
                    showDialogue("NO DATA", responseMessage);
                }
                else{
                    String responseMessage = networkResponse.responseMessage;
                    showDialogue("Failure", responseMessage);
                }

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void appNotLogin(){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)

                .setContentText("Your referral points will not be added to your account as a guest user!!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finish();


                    }
                })
                .show();
    }
}
