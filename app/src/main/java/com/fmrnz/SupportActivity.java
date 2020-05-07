package com.fmrnz;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.CarBookingModel;
import com.fmrnz.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;

public class SupportActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    Spinner bookingSpinner;
    Button ticketSubmit,ticketSubmit1;
    EditText editTextSupport;
    String feed_message,booking_id,feed_message1;
    ArrayList<String> bookingIDList;
    LinearLayout generalLayout,raiseTicketLayout;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText generalEditText;
    Button generalSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }

        setUpView();
        fetchAllBookingIds();
        raiseTicketLayout.setVisibility(View.VISIBLE);
        generalLayout.setVisibility(View.GONE);
        setUpListeners();
        myFeedbackRequest1();
        if(!TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID))){

        }

        else{
            appNotLogin();
        }

    }
    private void appNotLogin(){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)

                .setContentText("Please login to use Support!!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finish();


                    }
                })
                .show();
    }


    private void setUpView(){
        bookingIDList = new ArrayList<>();
        bookingSpinner = (Spinner) findViewById(R.id.bookingSpinner);
        ticketSubmit1 = (Button) findViewById(R.id.ticketSubmit1);
        ticketSubmit = (Button) findViewById(R.id.ticketSubmit);
        editTextSupport = (EditText) findViewById(R.id.editTextSupport);
        generalLayout = (LinearLayout)findViewById(R.id.generalLayout);
        raiseTicketLayout = (LinearLayout)findViewById(R.id.ticketLayout);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        generalEditText = (EditText)findViewById(R.id.editTextGeneral);
        generalSubmit = (Button)findViewById(R.id.ticketSubmit1);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
//        dlCountryIsse = radioButton.getText().toString();
        if (radioButton.getText().equals("Raise Ticket")) {
            raiseTicketLayout.setVisibility(View.VISIBLE);
            generalLayout.setVisibility(View.GONE);

        } else {
            raiseTicketLayout.setVisibility(View.GONE);
            generalLayout.setVisibility(View.VISIBLE);
        }

    }

    private void setUpListeners(){
        radioGroup.setOnCheckedChangeListener(this);
        bookingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                booking_id = parent.getSelectedItem().toString();
                Log.e("spp", booking_id);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ticketSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(booking_id)){
                    failureSweetDialg("CarRental","Please select BookingID");
                    return;
                }
                else{
                    submitTicket();
                }



            }
        });

        ticketSubmit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTicket1();
            }
        });
    }

    private void fetchAllBookingIds(){
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.ALL_BOOKINGID);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getUserDetails().get(SessionManager.KEY_ID));
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }

    private void submitTicket() {

        if (ConnectionDetector.checkConnection(this)) {
            if (!validate()) {
                editTextSupport.setEnabled(true);
                ticketSubmit.setEnabled(true);

            } else {
                editTextSupport.setEnabled(false);
                ticketSubmit.setEnabled(false);
                myFeedbackRequest();
            }
        } else {
            editTextSupport.setEnabled(true);
            ticketSubmit.setEnabled(true);
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
        }
    }

    private void submitTicket1() {

        if (ConnectionDetector.checkConnection(this)) {
            if (!validate1()) {
                generalEditText.setEnabled(true);
                generalSubmit.setEnabled(true);

            } else {
                generalEditText.setEnabled(false);
                generalSubmit.setEnabled(false);
                myFeedbackRequest1();
            }
        } else {
            generalEditText.setEnabled(true);
            ticketSubmit.setEnabled(true);
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
        }
    }


    public boolean validate() {
        boolean valid = true;
        feed_message = editTextSupport.getText().toString();
        if (editTextSupport.getText().toString().trim().equals("")) {
            editTextSupport.setError("Required field");
            valid = false;
        }
        return valid;
    }

    public boolean validate1() {
        boolean valid = true;
        feed_message1 = generalEditText.getText().toString();
        if (generalEditText.getText().toString().trim().equals("")) {
            generalEditText.setError("Required field");
            valid = false;
        }
        return valid;
    }

    private void myFeedbackRequest() {
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.SUPPORT);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("book_id", booking_id);
        hashMap.put("comment", feed_message);
        hashMap.put("comment1", "Test");
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }

    private void myFeedbackRequest1() {
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.SUPPORT);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("book_id", "1");
        hashMap.put("comment", feed_message1);
        hashMap.put("comment1", "Test");
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        {

            switch (eventType) {

                case ESNetworkRequest.NetworkEventType.ALL_BOOKINGID:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                        ArrayList<CarBookingModel> carBookingModelArrayList = networkResponse.carBookingModelArrayList;
                        if (carBookingModelArrayList != null && carBookingModelArrayList.size() != 0) {
                            for(CarBookingModel carBookingModel : carBookingModelArrayList){
                                String bookingFrom = carBookingModel.getBook_from();
                                bookingFrom = Utility.convertStringIntoData1(bookingFrom);
                                String[] bookingDateArray = bookingFrom.split(" ");
                                String bookingIDDate = carBookingModel.getBooking_id() + " (" + bookingDateArray[0] + ") (" + bookingDateArray[1] + ")";

                                bookingIDList.add(bookingIDDate);

                            }
                            setDataOnSpinner();
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

                case ESNetworkRequest.NetworkEventType.SUPPORT:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                        suuccessSweetDialgofForSuccess("CarRental",networkResponse.responseMessage);
                    } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                        failureSweetDialg("Error",networkResponse.responseMessage);

                    } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.FAILURE){
                        failureSweetDialg("Error",networkResponse.responseMessage);

                    }
                    break;

            }
        }

    }

    private void setDataOnSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bookingIDList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookingSpinner.setPrompt("Select Booking ID");
        bookingSpinner.setAdapter(dataAdapter);

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
