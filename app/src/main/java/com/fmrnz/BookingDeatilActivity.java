package com.fmrnz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.fmrNetworking.AtechnosServerService;
import com.fmrnz.fmrNetworking.RetrofitRestController;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.pojo.ResponseCaRdDetail;
import com.fmrnz.pojo.ResponseToken;
import com.fmrnz.utils.AppConstant;
import com.fmrnz.utils.Utility;




import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eurysinfosystems on 23/05/18.
 */

public class BookingDeatilActivity extends BaseActivity {

    TextView ownerNameBook, carNameBook, pickDateBook, dropDateBook, costBook;

    ArrayList<CarRentalListModel> rentalDataModelArrayList;
    CarRentalListModel carRentalListModel;
    String startDate, endDate,carID,licenceID,spinnerDrop,pickLocation;
    ImageLoader imageLoader;
    NetworkImageView ownerImageView;
    Button done;
    HashMap<String, String> loginHashMap;
    int numOfDays;
    LinearLayout upArrow,downArrow,content;
    NetworkImageView userrImageView;
    TextView ownerNameRide,owncontact,ownAboutRide,ownerAddRide,ownerGenderRide,memberRide;
    int temp = 0;
    String payemntID = "",payemnetStatus = "",paymentAmount="";
//    private static PayPalConfiguration config = new PayPalConfiguration()
//            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
//            // or live (ENVIRONMENT_PRODUCTION)
//            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
//            .clientId(AppConstant.PAYPAL_CLIENT_ID);
    public static final int PAYPAL_REQUEST_CODE = 123;
    String finalPrice;
    String reddempont;

    private String clientToken;
    private static final int BRAINTREE_REQUEST_CODE = 4949;
    private static final String TAG = MainActivity.class.getSimpleName();


    ACProgressFlower dialog_payment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        dialog_payment = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();
        setActionBar();
        fetchIntentData();
        setUpView();
        setUpListeners();
        setUpData();
    }

    private void setActionBar(){
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
    }

    private void fetchIntentData(){
        loginHashMap = sessionManager.getUserDetails();
        imageLoader = networkController.getImageLoader();
        carID = getIntent().getStringExtra("carID");
        carRentalListModel = (CarRentalListModel) getIntent().getParcelableExtra("RentalData");
        startDate = getIntent().getStringExtra("Start Date");
        endDate = getIntent().getStringExtra("End Date");
        licenceID = getIntent().getStringExtra("LicenceID");
        if(TextUtils.isEmpty(licenceID)){
            licenceID = sessionManager.getLicenceID();
        }
        spinnerDrop = getIntent().getStringExtra("SpinnerDrop");
        finalPrice = getIntent().getStringExtra("FinalPrice");
        reddempont = getIntent().getStringExtra("reddempont");

    }

    private void setUpView(){
        ownerImageView = (NetworkImageView) findViewById(R.id.ownerImageView);
        ownerNameBook = (TextView) findViewById(R.id.ownerNameBook);
        carNameBook = (TextView) findViewById(R.id.carNameBook);
        pickDateBook = (TextView) findViewById(R.id.pickDateBook);
        dropDateBook = (TextView) findViewById(R.id.dropDateBook);
        costBook = (TextView) findViewById(R.id.costBook);
        done = (Button) findViewById(R.id.done);

//        upArrow = (LinearLayout) findViewById(R.id.upLayout);
//        downArrow = (LinearLayout) findViewById(R.id.downlayout);
//        content = (LinearLayout)findViewById(R.id.content);
//        userrImageView = (NetworkImageView) findViewById(R.id.userrImageRide);
        ownerNameRide = (TextView)findViewById(R.id.ownerNameRide);
//        memberRide = (TextView)findViewById(R.id.memberRide5);
//        ownerGenderRide = (TextView)findViewById(R.id.ownerGenderRide5);
//        ownerAddRide = (TextView)findViewById(R.id.ownerAddRide5);
//        ownAboutRide = (TextView)findViewById(R.id.ownAboutRide5);
//        owncontact = (TextView)findViewById(R.id.owncontact5);


        ownerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookingDeatilActivity.this, DialogActivity2.class);
                String carID = carRentalListModel.getCar_id();
                intent1.putExtra("RentalData",carRentalListModel);
                intent1.putExtra("carID",carID);
                startActivity(intent1);

            }
        });

    }

    private void setUpListeners(){
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!TextUtils.isEmpty(loginHashMap.get(SessionManager.KEY_MOBILE)) && !TextUtils.isEmpty(loginHashMap.get(SessionManager.KEY_ADDRESS))
                       && !TextUtils.isEmpty(loginHashMap.get(SessionManager.KEY_ABOUT))) {
                   checkConnectivity();
                }
                else{
                   Intent intent = new Intent(BookingDeatilActivity.this,ProfileActivity.class);
                   intent.putExtra("CallingFrom","BookingDetail");
                   startActivityForResult(intent,2000);
               }

            }
        });

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
//
//            }
//        });
    }



    private void setUpData(){
        if (!TextUtils.isEmpty(carRentalListModel.getOwner_image())) {
            ownerImageView.setImageUrl(carRentalListModel.getOwner_image(), imageLoader);
        }
        ownerNameBook.setText(carRentalListModel.getOwner_name());
        carNameBook.setText(carRentalListModel.getCar_name());
        if (!TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(startDate)) {
            dropDateBook.setText(Utility.dateTimeFormat(endDate));
            pickDateBook.setText(Utility.dateTimeFormat(startDate));
        }

        /*Date start = null;
        Date end = null;
        try {
            start = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(startDate);
            end = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(endDate);


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
            if (!TextUtils.isEmpty(carRentalListModel.getDaily_price())) {
                double price = Float.parseFloat(carRentalListModel.getDaily_price());
                double finalPrice = price * temp;
                costBook.setText("$" + String.valueOf(finalPrice));
            }

        }
        else if(temp == 7){
            if (!TextUtils.isEmpty(carRentalListModel.getWeekly_price())) {
                double price = Float.parseFloat(carRentalListModel.getWeekly_price());
                double finalPrice = price * 1;
                costBook.setText("$" + String.valueOf(finalPrice));
            }
        }

        else if(temp > 7){
            double dailyValue = 0,weeklyValue = 0;

            int remain = temp%7;
            int value = temp/7;

            if (!TextUtils.isEmpty(carRentalListModel.getDaily_price())) {
                dailyValue = remain * Float.parseFloat(carRentalListModel.getDaily_price());
            }

            if (!TextUtils.isEmpty(carRentalListModel.getWeekly_price())) {
                weeklyValue = value * Float.parseFloat(carRentalListModel.getWeekly_price());
            }
            costBook.setText("$" + String.valueOf(dailyValue+weeklyValue));
        } */

        costBook.setText("$" + finalPrice);




        if(!TextUtils.isEmpty(carRentalListModel.getOwner_name())){
            ownerNameRide.setText(carRentalListModel.getOwner_name());
        }


    }




    private void checkConnectivity() {
        if (ConnectionDetector.checkConnection(this)) {
            String payStatus = sessionManager.getPayStatus();
            if(payStatus.equals("0")){
               // initiatePaymentUsingPaypal();
                AlertDialog alertDialog = new AlertDialog.Builder(
                        BookingDeatilActivity.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("Verification for Booking!");

                // Setting Dialog Message
                alertDialog.setMessage("We charged $1 for verification purpose for bookong!");

                // Setting Icon to Dialog
               // alertDialog.setIcon(R.drawable.tick);

                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        fetchToen();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
                // call for fetch token

            }
            else{
                fetchDetailBookData();
            }
        } else {
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
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
//
//        DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
//        dropInRequest.collectDeviceData(true);
//        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);
    }


//    private void initiatePaymentUsingPaypal() {
//        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(1)), "NZD", "Simplified Coding Fee",
//                PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2000) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("ResponseCode");
                if(result.equals("ProfileUpdate")){
                    checkConnectivity();
                }
                else{
                    failureSweetDialg("CarRentalApp", "Your profile has not been updated");
                }
            }

        }
        if(requestCode == BRAINTREE_REQUEST_CODE){
//            if (RESULT_OK == resultCode){
////                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
////                String paymentNonce = result.getPaymentMethodNonce().getNonce();
//                //send to your server
//                Log.d(TAG, "Testing the app here");
//
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
        Call<ResponseCaRdDetail> call = Service.postTokenAndAmount(paymentNonce,"1");
        // TODO: 01/12/17 Below mentioned code is for aSynchronus call
        call.enqueue(new Callback<ResponseCaRdDetail>() {
            @Override
            public void onResponse(Call<ResponseCaRdDetail> call, Response<ResponseCaRdDetail> response) {
                String respons=response.body().getTxnid();
                if(respons!=null){
                    fetchPaymentRequestData(respons);
                }
                Toast.makeText(BookingDeatilActivity.this,"payment Success!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseCaRdDetail> call, Throwable t) {
                Toast.makeText(BookingDeatilActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void fetchPaymentRequestData(String response) {
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.PAYMENT_INITIAL);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pay_amount", "1");
        hashMap.put("user_id", sessionManager.getUserDetails().get(SessionManager.KEY_ID));
        hashMap.put("pay_status", "true");
        hashMap.put("pay_id", response);
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }


    private void fetchDetailBookData() {
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.CONFIRM_BOOKING);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", loginHashMap.get(SessionManager.KEY_ID));
        hashMap.put("licence_id", licenceID);
        hashMap.put("car_id", carID);
        hashMap.put("book_from", startDate);
        hashMap.put("book_to", endDate);
        hashMap.put("drop_location", spinnerDrop);
        hashMap.put("book_amount", finalPrice);
        hashMap.put("vendor_mail",carRentalListModel.getEmail());
        hashMap.put("pickup_location",carRentalListModel.getPickup_location());
        hashMap.put("fcm_point",reddempont);
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.PAYMENT_INITIAL:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String respnseMessage = networkResponse.responseMessage;
                    if(!TextUtils.isEmpty(respnseMessage)){
                        suuccessSweetDialgofForSuccess("Success",networkResponse.responseMessage);
                    }
                    sessionManager.setPayStatus("1");
                    fetchDetailBookData();

                }
                else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                    failureSweetDialg("Error",networkResponse.responseMessage);

                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.FAILURE){
                    failureSweetDialg("Error",networkResponse.responseMessage);

                }
                break;

            case ESNetworkRequest.NetworkEventType.CONFIRM_BOOKING:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    if (!TextUtils.isEmpty(networkResponse.bookingID)) {
                        String bookingId = networkResponse.bookingID;
                        Intent intent = new Intent(BookingDeatilActivity.this, BookingConfirmdActivity.class);
                        intent.putExtra("BookingID", bookingId);
                        startActivity(intent);
                    }
                    else{
                        failureSweetDialg("Error",networkResponse.responseMessage);
                    }
                }
                else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                    failureSweetDialg("Error",networkResponse.responseMessage);

                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.FAILURE){
                    failureSweetDialg("Error",networkResponse.responseMessage);

                }
                break;
        }
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();
            if (id == android.R.id.home) {
                onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }



}
