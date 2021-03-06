package com.fmrnz;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.RideDetailModel;

import java.util.HashMap;


public class OTPActivity extends BaseActivity {


    EditText licAdd,userNameLic;
    private EditText editText1,editText2,editText3,editText4,editText5,editText6;
    String str1,str2,str3,str4,str5,str6, otpText, otp;

    Button otpSubmit,otpResend;
    RideDetailModel rideDetailModel;
    TextView startBookId;

    String bookingID,callingFrom,bookingid;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        //   Utils.addGoogleAnalytics(SplashActivity.this, AppConstant.MAINACTIVITY);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
        rideDetailModel = getIntent().getParcelableExtra("FinishRideData");
      //  rideDetailModel = getIntent().getParcelableExtra("OngoingData");

        bookingID = getIntent().getStringExtra("BookingID");
        otp = getIntent().getStringExtra("otp");
        callingFrom = getIntent().getStringExtra("CallingFrom");




        editText1 = (EditText) findViewById(R.id.edit1);
        editText2 = (EditText) findViewById(R.id.edit2);
        editText3 = (EditText) findViewById(R.id.edit3);
        editText4 = (EditText) findViewById(R.id.edit4);
        editText5 = (EditText) findViewById(R.id.edit5);
        editText6 = (EditText) findViewById(R.id.edit6);
        startBookId = (TextView) findViewById(R.id.startBookId);

     //   startBookId.setText(bookingID);




        otpSubmit = (Button) findViewById(R.id.otpSubmit);
        otpResend = (Button) findViewById(R.id.otpResendSubmit);
        setIpEditText();


        otpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(str1) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4)
                        && !TextUtils.isEmpty(str5) && !TextUtils.isEmpty(str6)) {
                    checkConnectivity();
                }
                else{
                    failureSweetDialg("CarRental","Please Enter OTP for proceed!!");
                }
            }
        });

        otpResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endOTPRequest();
            }
        });

        setUpData();

    }


    private void setUpData(){
        if(rideDetailModel != null){

          startBookId.setText(rideDetailModel.getBooking_id());
        }
    }






    private void checkConnectivity()
    {
        if(ConnectionDetector.checkConnection(OTPActivity.this))
        {
            hideKeyboard();
          //  Utility.showProgressBar(progressIndicatorView, otpSubmit);
            otpText = str1+str2+str3+str4+str5+str6;
         //   otpText = otp;
            ESAppRequest otpRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESAppRequest.NetworkEventType.START_TRIP);
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("otp", otpText);
            hashMap.put("booking_id", rideDetailModel.getBooking_id());
            otpRequest.requestMap = hashMap;
            networkController.sendNetworkRequest(otpRequest);
        }
        else
        {
            showDialogue("No Internet Connection","Please check your Internet Connection");
        }


    }

    private void endOTPRequest(){
       // Utils.showProgressBar(progressIndicatorView);
        ESAppRequest appRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.START_OTP);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("booking_id", rideDetailModel.getBooking_id());
        hashMap.put("email", rideDetailModel.getEmail());
        hashMap.put("pay_amount", "");
        hashMap.put("user_id", sessionManager.getUserDetails().get(SessionManager.KEY_ID));
        hashMap.put("pay_status", "");
        hashMap.put("pay_id", "");
        appRequest.requestMap = hashMap;

        networkController.sendNetworkRequest(appRequest);
    }





    public void handleNetworkEvent(int eventType , ESNetworkResponse networkResponse) {
        {
           // Utility.hideProgressBar(progressIndicatorView, otpSubmit);
            switch (eventType) {
                case ESNetworkRequest.NetworkEventType.START_TRIP:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS)
                    {
                        String responseMessage = networkResponse.responseMessage;
                        suuccessOtpSweetDialgofForSuccess("Success", responseMessage,"Start Trip");

                        //    if(callingFrom.equals("Upcoming")){
                        //    showDialogue("Success", responseMessage);
//                            finish();
//
//                        }
//                        else if(callingFrom.equals("Ongoing")) {
//                            Intent otpIntent = new Intent(OTPActivity.this, FeedbackActivity.class);
//                            otpIntent.putExtra("FeedbackData",rideDetailModel);
//                            startActivity(otpIntent);
//                            finish();
//
//                        }


                    } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.ERROR) {
                        String responseMessage = networkResponse.responseMessage;
                        showDialogue("Error", responseMessage);
                        editText1.setText("");
                        editText2.setText("");
                        editText3.setText("");
                        editText4.setText("");
                        editText5.setText("");
                        editText6.setText("");


                    }
                    else{
                        String responseMessage =
                                networkResponse.responseMessage;
                        showDialogue("Failure", responseMessage);
                        editText1.setText("");
                        editText2.setText("");
                        editText3.setText("");
                        editText4.setText("");
                        editText5.setText("");
                        editText6.setText("");

                    }
                    break;

                case ESNetworkRequest.NetworkEventType.START_OTP:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                        String responseMessage = networkResponse.responseMessage;
                        suuccessOtpSweetDialgofForSuccess("Success", responseMessage,"OTP");
                        //        successStartTrip(networkResponse.responseCode);
//                        Intent intent = new Intent(UpcomingRideDetailActivity.this, OTPActivity.class);
//                        String bookId = rideDetailModel.getBooking_id();
//                        intent.putExtra("FinishRideData",rideDetailModel);
//                        intent.putExtra("CallingFrom","Upcoming");
//                        intent.putExtra("BookingId",bookId);

                        //  intent.putExtra("otp",networkResponse.stringResponse);
                     //   startActivity(intent);
//                    finish();
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

    }


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    private void setIpEditText()
    {
        editText1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if(editText1.getText().toString().length()==1)
                {
                    editText1.clearFocus();
                    editText2.requestFocus();
                    editText2.setCursorVisible(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str1 = s.toString();
                if(str1.equalsIgnoreCase("")){
                    editText1.clearFocus();
                    ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText1.getWindowToken(), 0);

                }
            }


        });

        editText2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText2.getText().toString().length()==1)
                {
                    editText2.clearFocus();
                    editText3.requestFocus();
                    editText3.setCursorVisible(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str2 = s.toString();
                if(str2.equalsIgnoreCase("")){
                    editText2.clearFocus();
                    editText1.requestFocus();
                    editText1.setCursorVisible(true);
                }
            }


        });


        editText3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText3.getText().toString().length() == 1) {

                    editText3.clearFocus();
                    editText4.requestFocus();
                    editText4.setCursorVisible(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str3 = s.toString();
                if(str3.equalsIgnoreCase("")){
                    editText3.clearFocus();
                    editText2.requestFocus();
                    editText2.setCursorVisible(true);
                }

            }
        });



        editText4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText4.getText().toString().length() == 1) {
                    editText4.clearFocus();
                    editText5.requestFocus();
                    editText5.setCursorVisible(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                str4 = s.toString();
                if(str4.equalsIgnoreCase("")){
                    editText4.clearFocus();
                    editText3.requestFocus();
                    editText3.setCursorVisible(true);
                }
            }


        });

        editText5.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText5.getText().toString().length() == 1) {
                    editText5.clearFocus();
                    editText6.requestFocus();
                    editText6.setCursorVisible(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str5 = s.toString();
                if(str5.equalsIgnoreCase("")){
                    editText5.clearFocus();
                    editText4.requestFocus();
                    editText4.setCursorVisible(true);
                }
            }
        });


        editText6.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText6.getText().toString().length() == 1) {
                    editText6.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            if (i == EditorInfo.IME_ACTION_DONE) {
                                editText6.clearFocus();
                                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText6.getWindowToken(), 0);
                            }
                            return false;
                        }
                    });
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str6 = s.toString();
                if(str6.equalsIgnoreCase("")){
                    editText6.clearFocus();
                    editText5.requestFocus();
                    editText5.setCursorVisible(true);
                }
            }
        });
    }


    public void suuccessOtpSweetDialgofForSuccess(String title, String msg, final String type){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        if(type.equals("Start Trip")){
                            Intent intent = new Intent(OTPActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        sweetAlertDialog.dismiss();


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

}