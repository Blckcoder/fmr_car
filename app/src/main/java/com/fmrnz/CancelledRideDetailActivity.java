package com.fmrnz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.RideDetailModel;

import java.util.HashMap;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

public class CancelledRideDetailActivity extends BaseActivity {

    RideDetailModel rideDetailModel;
    TextView bookDateDrop,bookDatePick,pickLocation,dropLocation,totalfair,bookinID,owncontact,ownAboutRide,ownerAddRide,ownerGenderRide,memberRide,ownerNameRide;
    private Button endTrip;
    AVLoadingIndicatorView progressIndicatorView;
    String bookingID;
    LinearLayout upArrow,downArrow,content;

    Button submit;
    NetworkImageView userrImageView;

    ImageLoader imageLoader;

    int numOfDays;
    int temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_ride_detail);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
        rideDetailModel = getIntent().getParcelableExtra("CancelledData");
        bookingID = getIntent().getParcelableExtra("BookingID");
        loginHashMap = sessionManager.getUserDetails();
        imageLoader = networkController.getImageLoader();
        ImageLoader imageLoader = networkController.getImageLoader();


        setUpView();
      //  setUpListners();
        setUpData();


    }

    private void setUpView(){
//        bookDatePick = (TextView)findViewById(R.id.bookDatePick);
//        bookDateDrop = (TextView)findViewById(R.id.bookDateDrop);
//        upArrow = (LinearLayout) findViewById(R.id.upLayoutCan);
//        downArrow = (LinearLayout) findViewById(R.id.downlayoutCan);
//        content = (LinearLayout)findViewById(R.id.contentCan);
//        memberRide = (TextView)findViewById(R.id.memberRide2);
//        ownerGenderRide = (TextView)findViewById(R.id.ownerGenderRide2);
        ownerNameRide = (TextView)findViewById(R.id.ownerNameRide2);
//        ownerAddRide = (TextView)findViewById(R.id.ownerAddRide2);
//        ownAboutRide = (TextView)findViewById(R.id.ownAboutRide2);
     //   owncontact = (TextView)findViewById(R.id.owncontact2);
        bookDatePick = (TextView)findViewById(R.id.bookDatePick);
        bookDateDrop = (TextView)findViewById(R.id.bookDateDrop);
        bookinID = (TextView)findViewById(R.id.bookingID);
        totalfair = (TextView)findViewById(R.id.totalfair);
        pickLocation = (TextView)findViewById(R.id.picLocation3);
        dropLocation = (TextView)findViewById(R.id.dropLocation3);
        userrImageView = (NetworkImageView) findViewById(R.id.userrImageRide);

        progressIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));

        userrImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CancelledRideDetailActivity.this, DialogActivity3.class);
                String carID = rideDetailModel.getCar_id();
                intent1.putExtra("RideDetail",rideDetailModel);
                intent1.putExtra("carID",carID);
                startActivity(intent1);

            }
        });
    }

//    private void setUpListners(){
//
//        downArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                content.setVisibility(View.VISIBLE);
//                upArrow.setVisibility(View.VISIBLE);
//                downArrow.setVisibility(View.GONE);
//
//
//            }
//        });
//
//        upArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                content.setVisibility(View.GONE);
//                downArrow.setVisibility(View.VISIBLE);
//                upArrow.setVisibility(View.GONE);
//
//            }
//        });
//    }

//    private void setUpListners(){
//        startTrip.setOnClickListener(this);
//    }

    private void setUpData(){
        if(rideDetailModel != null){

            if(!TextUtils.isEmpty(rideDetailModel.getBook_from())){
                bookDatePick.setText(rideDetailModel.getBook_from());
            }

            if(!TextUtils.isEmpty(rideDetailModel.getBook_to())){
                bookDateDrop.setText(rideDetailModel.getBook_to());
            }

            if(!TextUtils.isEmpty(rideDetailModel.getBooking_id())){
                bookinID.setText(rideDetailModel.getBooking_id());
            }

            if(!TextUtils.isEmpty(rideDetailModel.getOwner_name())){
                ownerNameRide.setText(rideDetailModel.getOwner_name());
            }


            if(!TextUtils.isEmpty(rideDetailModel.getPickup_location())){
                pickLocation.setText(rideDetailModel.getPickup_location());
            }

            if(!TextUtils.isEmpty(rideDetailModel.getDrop_location())){
                dropLocation.setText(rideDetailModel.getDrop_location());
            }

            userrImageView.setImageUrl(rideDetailModel.getOwner_image(), imageLoader);


           /* Date start = null;
            Date end = null;
            try {
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



            }  catch (ParseException e) {
                e.printStackTrace();
            }

            if(temp < 7){
                if (!TextUtils.isEmpty(rideDetailModel.getDaily_price())) {
                    double price = Float.parseFloat(rideDetailModel.getDaily_price());
                    double finalPrice = price * temp;
                    totalfair.setText("$" + String.valueOf(finalPrice));
                }

            }
            else if(temp == 7){
                if (!TextUtils.isEmpty(rideDetailModel.getWeekly_price())) {
                    double price = Float.parseFloat(rideDetailModel.getWeekly_price());
                    double finalPrice = price * 1;
                    totalfair.setText("$" + String.valueOf(finalPrice));
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
                totalfair.setText("$" + String.valueOf(dailyValue+weeklyValue));
            } */

            if(!TextUtils.isEmpty(rideDetailModel.getBook_amount())){
                totalfair.setText("$" + rideDetailModel.getBook_amount());
            }

//
//            if(!TextUtils.isEmpty(rideDetailModel.getDaily_price())){
//                float price = Float.parseFloat(rideDetailModel.getDaily_price());
//                float finalPrice = price * temp;
//                totalfair.setText("$" + String.valueOf(finalPrice));
//            }
        }
    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.endTripButton:
//                endOTPRequest();
//
//                break;
//            default:
//                break;
//        }
//    }

    private void endOTPRequest(){
        Utils.showProgressBar(progressIndicatorView);
        ESAppRequest appRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.END_TRIP);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("booking_id", rideDetailModel.getBooking_id());
        hashMap.put("email", loginHashMap.get(SessionManager.KEY_EMAIL));
        appRequest.requestMap = hashMap;

        networkController.sendNetworkRequest(appRequest);
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {

        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.END_TRIP:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;
                    showDialogue("Success", responseMessage);
                    Intent intent = new Intent(CancelledRideDetailActivity.this, FeedbackActivity.class);
                    String bookId = rideDetailModel.getBooking_id();
                    intent.putExtra("FinishRideData",rideDetailModel);
                    intent.putExtra("BookingId",bookId);
                    intent.putExtra("otp",networkResponse.stringResponse);
                    finish();


                    startActivity(intent);
                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.ERROR) {
                    String responseMessage =
                            networkResponse.responseMessage;
                    showDialogue("Error", responseMessage);

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
}
