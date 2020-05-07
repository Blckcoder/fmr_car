package com.fmrnz;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.FmrModel;
import com.fmrnz.utils.AppConstant;

import java.util.ArrayList;
import java.util.HashMap;

public class FMRPointsActivity extends BaseActivity {

    TextView points,referPoint;
    FmrModel fmrModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmrpoints);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }

        points = (TextView) findViewById(R.id.points);
        referPoint = (TextView)findViewById(R.id.referPoint);
        if(!TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID)))
        fetchFMRPoints();
        else
            appNotLogin();

    }

    private void appNotLogin(){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setContentText("Please login to use FMR Points!!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                       finish();


                    }
                })
                .show();
    }




    private void fetchFMRPoints() {
        if (ConnectionDetector.checkConnection(this)) {
            fetchFMRPointsRequest();
        } else {
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
        }
    }

    private void setUpData(){
        if(fmrModel != null){
        if (!TextUtils.isEmpty(fmrModel.getPoints())) {
            points.setText(fmrModel.getPoints());
        }

            if (!TextUtils.isEmpty(fmrModel.getPoints())) {
                referPoint.setText(fmrModel.getPoints());
            }
        }
    }

    private void fetchFMRPointsRequest(){
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.FMR_POINTS);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user_id",sessionManager.getUserDetails().get(SessionManager.KEY_ID));
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.FMR_POINTS:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    ArrayList<FmrModel> fmrModelsArrayList = networkResponse.fmrlistModelArrayList;
                    if (fmrModelsArrayList != null && fmrModelsArrayList.size() != 0) {
                        fmrModel = fmrModelsArrayList.get(0);
                        AppConstant.fmrPoints = fmrModel.getPoints();
                        setUpData();
                    }
                    else{
                        failureSweetDialg("Error",networkResponse.responseMessage);
                    }
                }
                else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                    if(networkResponse.responseMessage.equals("No Points Available")){
                        AppConstant.fmrPoints = "0";
                    }
                    failureSweetDialg("Error",networkResponse.responseMessage);

                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.FAILURE){
                    failureSweetDialg("Error",networkResponse.responseMessage);

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

}

