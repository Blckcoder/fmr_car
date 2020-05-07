package com.fmrnz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.fmrNetworking.AtechnosServerService;
import com.fmrnz.fmrNetworking.RetrofitRestController;
import com.fmrnz.model.RideDetailModel;
import com.fmrnz.pojo.ResponseCaRdDetail;
import com.fmrnz.pojo.ResponseToken;



import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

public class OngoingRideDetailActivity extends BaseActivity implements View.OnClickListener {

    RideDetailModel rideDetailModel;
    TextView bookDateDrop,bookDatePick,pickLocation,dropLocation,totalfair,bookinID,owncontact,ownAboutRide,ownerAddRide,ownerGenderRide,memberRide,ownerNameRide;
    private Button endTrip;
    AVLoadingIndicatorView progressIndicatorView;
    String bookingID;
    public static int APP_REQUEST_CODE = 99;

    LinearLayout upArrow,downArrow,content;
    NetworkImageView userrImageView;
    float finalPrice;
    String payemntID,payemnetStatus;
    int numOfDays;
    int temp = 0;

    private String clientToken;
    private static final int BRAINTREE_REQUEST_CODE = 4949;
    private static final String TAG = MainActivity.class.getSimpleName();

    ImageLoader imageLoader;
    ACProgressFlower dialog_payment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_ride_detail);
        dialog_payment = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
        rideDetailModel = getIntent().getParcelableExtra("OngoingData");
        bookingID = getIntent().getParcelableExtra("BookingID");
//        rideDetailModel = getIntent().getParcelableExtra("FinishRideData");

        loginHashMap = sessionManager.getUserDetails();
        imageLoader = networkController.getImageLoader();
        ImageLoader imageLoader = networkController.getImageLoader();



        setUpView();
        setUpListners();
        setUpData();

//        Intent intent = new Intent(this, PayPalService.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        startService(intent);


    }

    private void setUpView(){
        bookDatePick = (TextView)findViewById(R.id.bookDatePick);
        bookDateDrop = (TextView)findViewById(R.id.bookDateDrop);
        bookinID = (TextView)findViewById(R.id.bookingID);
        totalfair = (TextView)findViewById(R.id.totalfair);
        endTrip = (Button)findViewById(R.id.endTripButton);
        ownerNameRide = (TextView)findViewById(R.id.ownerNameRide1);
        pickLocation = (TextView)findViewById(R.id.picLocation1);
        dropLocation = (TextView)findViewById(R.id.dropLocation1);
        userrImageView = (NetworkImageView) findViewById(R.id.userrImageRide1);

        progressIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));

        userrImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(OngoingRideDetailActivity.this, DialogActivity3.class);
                String carID = rideDetailModel.getCar_id();
                intent1.putExtra("RideDetail",rideDetailModel);
                intent1.putExtra("carID",carID);
                startActivity(intent1);
            }
        });
    }

    private void setUpListners(){
        endTrip.setOnClickListener(this);
        }


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

            if(!TextUtils.isEmpty(rideDetailModel.getBook_amount())){
                totalfair.setText("$" + rideDetailModel.getBook_amount());
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

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.endTripButton:
             // initiatePaymentUsingPaypal();

                // call for fetch token
                fetchToen();
                break;
            default:
                break;
        }
    }
    private void fetchToen() {
        dialog_payment.show();
        AtechnosServerService Service = RetrofitRestController.getClient().create(AtechnosServerService.class);
        Call<ResponseToken> call = Service.getToken();
        // TODO: 01/12/17 Below mentioned code is for aSynchronus call
        call.enqueue(new Callback<ResponseToken>() {
            @Override
            public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                clientToken =response.body().getToken();
                onBraintreeSubmit();
            }
            @Override
            public void onFailure(Call<ResponseToken> call, Throwable t) {
            }
        });
    }

    public void onBraintreeSubmit(){
        dialog_payment.hide();

//        DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
//        dropInRequest.collectDeviceData(true);
//        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);
    }


    private void endOTPRequest(String txnid){
        Utils.showProgressBar(progressIndicatorView);
        ESAppRequest appRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.END_OTP);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("booking_id", rideDetailModel.getBooking_id());
        hashMap.put("email", rideDetailModel.getEmail());
        hashMap.put("pay_amount",rideDetailModel.getBook_amount());
        hashMap.put("pay_status","Success");
        hashMap.put("pay_id",txnid);
        appRequest.requestMap = hashMap;

        networkController.sendNetworkRequest(appRequest);
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        Utils.hideProgressBar(progressIndicatorView);

        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.END_OTP:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;
                    Intent intent = new Intent(OngoingRideDetailActivity.this, OTPActivity2.class);
                    String bookId = rideDetailModel.getBooking_id();
                    intent.putExtra("OngoingData",rideDetailModel);
                    intent.putExtra("CallingFrom","Ongoing");
                    intent.putExtra("BookingId",bookId);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode == BRAINTREE_REQUEST_CODE){
//            if (RESULT_OK == resultCode){
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                String paymentNonce = result.getPaymentMethodNonce().getNonce();
//                //send to your server
//                Log.d(TAG, "Testing the app here");
//                sendPaymentNonceToServer(paymentNonce);
//            }else if(resultCode == Activity.RESULT_CANCELED){
//                Log.d(TAG, "User cancelled payment");
//            }else {
//                Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//                Log.d(TAG, " error exception");
//            }
        }
    }


    private void sendPaymentNonceToServer(String paymentNonce) {

        AtechnosServerService Service = RetrofitRestController.getClient().create(AtechnosServerService.class);
        Call<ResponseCaRdDetail> call = Service.postTokenAndAmount(paymentNonce,"20");
        // TODO: 01/12/17 Below mentioned code is for aSynchronus call
        call.enqueue(new Callback<ResponseCaRdDetail>() {
            @Override
            public void onResponse(Call<ResponseCaRdDetail> call, Response<ResponseCaRdDetail> response) {
                String respons=response.body().getTxnid();
                if(respons!=null){
                    endOTPRequest(respons);
                }
                Toast.makeText(OngoingRideDetailActivity.this,"payment Success!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseCaRdDetail> call, Throwable t) {
                Toast.makeText(OngoingRideDetailActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroy() {
       // stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
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
