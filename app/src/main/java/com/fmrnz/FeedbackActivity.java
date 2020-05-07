package com.fmrnz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.model.RideDetailModel;

import java.util.HashMap;


public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    RatingBar ratingBar;


    String ratingValue;
    String comment;
    boolean isRatingDone;
    boolean ratingFlag;
    CarRentalListModel carRentalListModel;


    String startDate, endDate;
    int numOfDays;
    int temp = 0;


    EditText licAdd,userNameLic,edit2;
    RideDetailModel rideDetailModel;
    Button submitFeedback;
    TextView ratingBarTextView,totalCoast,feedbackCost;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        //   Utils.addGoogleAnalytics(SplashActivity.this, AppConstant.MAINACTIVITY);

        ActionBar ab =getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        rideDetailModel = getIntent().getParcelableExtra("OngoingData");
        loginHashMap = sessionManager.getUserDetails();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }

        ratingBar = (RatingBar)findViewById(R.id.feedbackratingBar);
        submitFeedback = (Button)findViewById(R.id.btnRate);
        ratingBarTextView = (TextView)findViewById(R.id.ratingBarText);
        totalCoast = (TextView)findViewById(R.id.totalCoast);
        edit2 = (EditText) findViewById(R.id.edit2);
        feedbackCost = (TextView) findViewById(R.id.feedbackCost);
        submitFeedback.setOnClickListener(this);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
                ratingValue = String.valueOf(rating);
                ratingBarTextView.setText(ratingValue+"/5");
            }
        });




        setUpData();

    }

    private void setUpData() {
        if (rideDetailModel != null) {

           /* Date start = null;
            Date end = null;
            try  {
                start = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(rideDetailModel.getBook_from());
                end = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(rideDetailModel.getBook_to());

                long secs = (end.getTime() - start.getTime()) / 1000;
                int totalMinutes = (int)(secs / 60);
                long diff =  end.getTime() - start.getTime();
                numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                int roundOfMinutes = numOfDays * 24 * 60;
                if(totalMinutes == roundOfMinutes)
                    temp = numOfDays;
                else{
                    double days = totalMinutes/24 * 60;
                    if(days >= 1){
                        temp = numOfDays + 1;
                    }
                    else if(days < 1){
                        temp = 1;
                    }
                    else{
                        temp = numOfDays;
                    }

                }


            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(temp < 7){
                if (!TextUtils.isEmpty(rideDetailModel.getDaily_price())) {
                    double price = Float.parseFloat(rideDetailModel.getDaily_price());
                    double finalPrice = price * temp;
                    totalCoast.setText("$" + String.valueOf(finalPrice));
                    feedbackCost.setText(("Payment Of  " + "$"+String.valueOf(finalPrice) + " deducted from your card"));
                }

            }
            else if(temp == 7){
                if (!TextUtils.isEmpty(rideDetailModel.getWeekly_price())) {
                    double price = Float.parseFloat(rideDetailModel.getWeekly_price());
                    double finalPrice = price * 1;
                    totalCoast.setText("$" + String.valueOf(finalPrice));
                    feedbackCost.setText(("Payment Of  " + "$"+String.valueOf(finalPrice) + " deducted from your card"));
                }
            }

            else if(temp > 7){
                double dailyValue = 0,weeklyValue = 0;

                int remain = temp%7;
                int value = temp/7;

                if (!TextUtils.isEmpty(rideDetailModel.getDaily_price())) {
                    dailyValue = remain * Float.parseFloat(rideDetailModel.getDaily_price());
                }

                if (!TextUtils.isEmpty(rideDetailModel.getWeekly_price())) {
                    weeklyValue = value * Float.parseFloat(rideDetailModel.getWeekly_price());
                }
                totalCoast.setText("$" + String.valueOf(dailyValue+weeklyValue));
                feedbackCost.setText(("Payment Of  " + "$"+String.valueOf(dailyValue+weeklyValue) + " deducted from your card"));
            } */

            if (!TextUtils.isEmpty(rideDetailModel.getBook_amount())) {
                totalCoast.setText("$" + rideDetailModel.getBook_amount());
                feedbackCost.setText(("Payment Of  " + "$"+rideDetailModel.getBook_amount() + " deducted from your card"));
            }



//            if (!TextUtils.isEmpty(rideDetailModel.getDaily_price())) {
//                float price = Float.parseFloat(rideDetailModel.getDaily_price());
//                float finalPrice = price * temp;
//                totalCoast.setText("$" + String.valueOf(finalPrice));
//                feedbackCost.setText(("Payment Of  " + "$"+String.valueOf(finalPrice) + " deducted from your card"));
//
//            }


        }
    }

    private void checkConnectivity() {

        if (ConnectionDetector.checkConnection(this))
        {
            sendUserRate();
        }
        else
        {
            failureSweetDialgForNoConnection("No Internet Connection","Please check your Internet Connection");
//            Utils.hideProgressBar(progressIndicatorView);
        }


    }

    private void sendUserRate(){
//        Utils.showProgressBar(progressIndicatorView);
        comment = edit2.getText().toString();

        ESAppRequest sendRateDetail = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.ADD_RATE);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user_id",loginHashMap.get(SessionManager.KEY_ID));
        hashMap.put("rating",ratingValue);
        hashMap.put("car_id",rideDetailModel.getCar_id());
        hashMap.put("comment",comment);
        sendRateDetail.requestMap = hashMap;
        networkController.sendNetworkRequest(sendRateDetail);
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {

        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.ADD_RATE:

                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;
                    suuccessFeedbackSweetDialgofForSuccess("Success", responseMessage);
                    //finish();

                } else {
                    failureSweetDialg("UnSucessFull", networkResponse.responseMessage);


                }
                break;
        }
    }

    public void suuccessFeedbackSweetDialgofForSuccess(String title, String msg){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(FeedbackActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);

                    }
                })
                .show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRate:
                checkConnectivity();
                break;
        }
    }
}