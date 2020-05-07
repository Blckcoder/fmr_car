package com.fmrnz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.BookingModel;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.model.FmrCouponModel;
import com.fmrnz.utils.AppConstant;
import com.fmrnz.utils.Utility;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class BookingActivity extends BaseActivity implements View.OnClickListener {


    Button next;
    TextView pickDate1, carPick, dropDate1, ownerType, reedeem, coupanCoodeTV, costWeek, ownerNameBook, alreadyBookedTV, alreadyBookedTV1, myBookingDate, costText, totalCost, daysTravel, finalCostTextView, availableReedemPointsTV;
    CarRentalListModel carRentalListModel;
    String startDate, endDate, carID, spinnerDrop;
    Spinner locationSpinner;
    ArrayList<String> locationSpinnerList = new ArrayList<>();
    private static final int LOGIN_ACTIVITY_REQUEST_CODE = 0;
    LinearLayout leftBookedLayout, rightBookedLayout;
    EditText reedemPointEditText, couponEditText;
    int numOfDays;
    double finalPrice;
    ImageView clearCoupanCode;
    TextView applyCoupanCode;
    String reddempont;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setActionBar();
        fetchIntentData();
        setUpView();
        setUpListeners();
        setUpData();
        setDataOnSpinner();
        checkConnectivity();
        reddempont = "";
    }

    private void setActionBar() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
    }

    private void fetchIntentData() {
        carID = getIntent().getStringExtra("carID");
        carRentalListModel = (CarRentalListModel) getIntent().getParcelableExtra("RentalData");
        startDate = getIntent().getStringExtra("Start Date");
        endDate = getIntent().getStringExtra("End Date");
    }

    private void setUpView() {
        clearCoupanCode = (ImageView) findViewById(R.id.clearCoupan);
        applyCoupanCode = (TextView) findViewById(R.id.applyCoupan);
        next = (Button) findViewById(R.id.next);
        pickDate1 = (TextView) findViewById(R.id.bookPickDate);
        dropDate1 = (TextView) findViewById(R.id.bookDropDate);
        ownerType = (TextView) findViewById(R.id.ownerType);
        costWeek = (TextView) findViewById(R.id.costPerWeek);
        ownerNameBook = (TextView) findViewById(R.id.ownerNameBook);
        carPick = (TextView) findViewById(R.id.carPick);
        //    totalCost = (TextView)findViewById(R.id.totalCost);
        alreadyBookedTV = (TextView) findViewById(R.id.availableDate1);
        alreadyBookedTV1 = (TextView) findViewById(R.id.availableDate2);
        myBookingDate = (TextView) findViewById(R.id.myBookingDate);
        locationSpinner = (Spinner) findViewById(R.id.spHomeSpinner);
        costText = (TextView) findViewById(R.id.costText);
        reedeem = (TextView) findViewById(R.id.reedemPointInDolorTV);
        couponEditText = (EditText) findViewById(R.id.couponEditText);
        leftBookedLayout = (LinearLayout) findViewById(R.id.leftAlreadyBooked);
        rightBookedLayout = (LinearLayout) findViewById(R.id.rightAlreadyBooked);
        daysTravel = (TextView) findViewById(R.id.travelDays);
        reedemPointEditText = (EditText) findViewById(R.id.redeemPoints);
        finalCostTextView = (TextView) findViewById(R.id.finalCostTV);
        availableReedemPointsTV = (TextView) findViewById(R.id.availableReedemPoints);
        coupanCoodeTV = (TextView) findViewById(R.id.coupanCodeText);
        carPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(carRentalListModel.getPickup_location())) {
                  //  carPick.setText(carRentalListModel.getPickup_location());
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            BookingActivity.this).create();



                    // Setting Dialog Message
                    alertDialog.setMessage(carRentalListModel.getPickup_location());



                    // Showing Alert Message
                    alertDialog.show();
                }
            }
        });
    }

    private void setUpListeners() {
        next.setOnClickListener(this);
        applyCoupanCode.setOnClickListener(this);
        clearCoupanCode.setOnClickListener(this);
        (reedemPointEditText)
                .setOnEditorActionListener(new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        // TODO Auto-generated method stub
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            if (AppConstant.fmrPoints != null) {
                                String enteredPoints = reedemPointEditText.getText().toString();
                                if (!TextUtils.isEmpty(enteredPoints)) {
                                    int enterPointNum = Integer.parseInt(enteredPoints);
                                    int availablePointNum = Integer.parseInt(AppConstant.fmrPoints);
                                    if (enterPointNum > availablePointNum) {
                                        insufficientPointAlert();
                                    } else {
                                        double availablePointInDollor = availablePointNum / 100.0;
                                        double enterdPointInDollor = enterPointNum / 100.0;
                                        reedeem.setText("$" + String.valueOf(enterdPointInDollor));
                                        String finalCost = finalCostTextView.getText().toString();
                                        finalCost = finalCost.replace("$", "");
                                        double finalCostValue = Double.parseDouble(finalCost);
                                        finalCostTextView.setText("$" + (finalCostValue - enterdPointInDollor));
                                        reedemPointEditText.setEnabled(false);

                                    }
                                }

                            } else {
                                insufficientPointAlert();
                                reedemPointEditText.setText("");
                            }

                            return true; // consume.
                        }
                        return false;
                    }
                });

        (couponEditText)
                .setOnEditorActionListener(new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        // TODO Auto-generated method stub
                        if (actionId == EditorInfo.IME_ACTION_DONE) {

//                            fetchApplyCouponData();
                            applyCoupanCode.setVisibility(View.VISIBLE);
                            hideKeyboard();

                            return true; // consume.
                        }
                        return false;
                    }
                });

        (couponEditText).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                applyCoupanCode.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //clearCoupanCode.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    private void insufficientPointAlert() {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Insufficient Points")
                .setContentText("You had insufficient points for reedem!!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        reedemPointEditText.setText("");


                    }
                })
                .show();
    }


    private void setUpData() {

        Date pickdateHour = null;
        Date dropdateHour = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            pickdateHour = format.parse(startDate);
            dropdateHour = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long differenceTime = dropdateHour.getTime() - pickdateHour.getTime();

        long diffSeconds = differenceTime / 1000 % 60;
        long diffMinutes = differenceTime / (60 * 1000) % 60;
        long diffHours = differenceTime / (60 * 60 * 1000) % 24;
        long diffDays = differenceTime / (24 * 60 * 60 * 1000);

        if (diffHours > 24 || diffDays > 0) {


            if (!TextUtils.isEmpty(carRentalListModel.getCar_name()) && !TextUtils.isEmpty(carRentalListModel.getCar_model()) && !TextUtils.isEmpty(carRentalListModel.getDaily_price())) {
                String costValue = "You are booking " + carRentalListModel.getCar_name() + " " + carRentalListModel.getCar_model() + " (or similar) at a cost  of $" + carRentalListModel.getDaily_price() + " per day";
                costText.setText(costValue);
            }
            int temp = 0;
            Date start = null;
            Date end = null;
            try {
                start = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(startDate);
                end = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(endDate);

                long secs = (end.getTime() - start.getTime()) / 1000;
                int totalMinutes = (int) (secs / 60);
                long diff = end.getTime() - start.getTime();
                numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
                int roundOfMinutes = numOfDays * 24 * 60;
                if (totalMinutes == roundOfMinutes) {
                    temp = numOfDays;
//                daysTravel.setText(String.valueOf(numOfDays) + " days");

                } else {
                    double days = totalMinutes / 24 * 60;
                    if (days >= 1) {
                        numOfDays = numOfDays + 1;
                        temp = numOfDays;
                    } else if (days < 1) {
                        numOfDays = 1;
                        temp = numOfDays;
                    } else {
                        temp = numOfDays;
                    }

                }

                if (temp < 7) {
                    daysTravel.setText(String.valueOf(temp) + " days");
                } else if (temp == 7) {
                    daysTravel.setText(String.valueOf(1) + " week");
                } else if (temp > 7) {
                    int remain = temp % 7;
                    int value = temp / 7;
                    daysTravel.setText(String.valueOf(value) + " weeks " + String.valueOf(remain) + " days");
                }

            /*long secs = (end.getTime() - start.getTime()) / 1000;
            int totalHours = (int) (secs / 3600);
            long diff =  end.getTime() - start.getTime();
             numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            int roundOfHours = numOfDays * 24;
            if(totalHours == roundOfHours)
                daysTravel.setText(String.valueOf(numOfDays) + " days");
            else{
                double days = totalHours/24;
                if(days >= 1){
                    numOfDays = numOfDays + 1;
                    daysTravel.setText(String.valueOf(numOfDays) + " days");
                }
                else if(days < 1){
                    numOfDays = 1;
                    daysTravel.setText(String.valueOf(numOfDays) + " days");
                }
                else{
                    daysTravel.setText(String.valueOf(numOfDays) + " days");
                }

            }*/

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (numOfDays < 7) {
                if (!TextUtils.isEmpty(carRentalListModel.getDaily_price())) {
                    double price = Float.parseFloat(carRentalListModel.getDaily_price());
                    finalPrice = price * numOfDays;
                    costWeek.setText("$" + String.valueOf(finalPrice));
                    finalCostTextView.setText("$" + String.valueOf(finalPrice));
                }

            } else if (numOfDays == 7) {
                if (!TextUtils.isEmpty(carRentalListModel.getWeekly_price())) {
                    double price = Float.parseFloat(carRentalListModel.getWeekly_price());
                    finalPrice = price * 1;
                    costWeek.setText("$" + String.valueOf(finalPrice));
                    finalCostTextView.setText("$" + String.valueOf(finalPrice));
                }
            } else if (numOfDays > 7) {

                double finalcost;
                double weekelycost_charge;
                if(carRentalListModel.getWeekly_price()!=null){

                    weekelycost_charge= Double.parseDouble(carRentalListModel.getWeekly_price())/7;

                    finalcost=weekelycost_charge *numOfDays;
                    DecimalFormat formatter = new DecimalFormat("#0.00");
                   // String strDouble = String.format("%.2f", String.valueOf(finalcost));
                    costWeek.setText("$"+ String.valueOf(formatter.format(finalcost)) );
                    finalCostTextView.setText("$"+ String.valueOf(formatter.format(finalcost)));


                }
             //   double dailyValue = 0, weeklyValue = 0;
//
//                int remain = numOfDays % 7;
//                int value = numOfDays / 7;
//
//                if (!TextUtils.isEmpty(carRentalListModel.getDaily_price())) {
//                    dailyValue = remain * Float.parseFloat(carRentalListModel.getDaily_price());
//                }
//
//                if (!TextUtils.isEmpty(carRentalListModel.getWeekly_price())) {
//                    weeklyValue = value * Float.parseFloat(carRentalListModel.getWeekly_price());
//                }
//                costWeek.setText("$" + String.valueOf(dailyValue + weeklyValue));
//                finalCostTextView.setText("$" + String.valueOf(dailyValue + weeklyValue));
            }
        } else {
            if (!TextUtils.isEmpty(carRentalListModel.getCar_name()) && !TextUtils.isEmpty(carRentalListModel.getCar_model()) && !TextUtils.isEmpty(carRentalListModel.getHourly_price())) {
                String costValue = "You are booking " + carRentalListModel.getCar_name() + " " + carRentalListModel.getCar_model() + " (or similar) at a cost  of $" + carRentalListModel.getHourly_price() + " per hour";
                costText.setText(costValue);
                if(diffHours>0 && diffHours<24){

                     double diffhours_double= Double.parseDouble(String.valueOf(diffHours));
                     double hourly_price= Double.parseDouble(carRentalListModel.getHourly_price());
                     double totalRate_hour= diffhours_double*hourly_price;
                    finalCostTextView.setText("$" +""+ String.valueOf(totalRate_hour));
                    daysTravel.setText(String.valueOf(diffHours+" "+"Hours"));

                }
                if(diffMinutes>0 && diffMinutes<60){

                  //  double diffhours_minutes_double= Double.parseDouble(String.valueOf(diffMinutes));
                    double hourly_price_minutes= Double.parseDouble(carRentalListModel.getHourly_price());
                    double totalRate_hour_minutess= hourly_price_minutes;
                    finalCostTextView.setText("$" +""+ String.valueOf(totalRate_hour_minutess));
                    daysTravel.setText(String.valueOf(diffMinutes+" "+"Minutes"));

                }



            }
        }


        if (!TextUtils.isEmpty(endDate) && !TextUtils.isEmpty(startDate)) {
//            dropDate1.setText(Utility.dateTimeFormat(endDate));
//            pickDate1.setText(Utility.dateTimeFormat(startDate));

            String dateTimeStart = Utility.dateTimeFormat(startDate);
            String dateTimeEnd = Utility.dateTimeFormat(endDate);

            String dateTimeStartArray[] = dateTimeStart.split(" ");
            String dateTimeEndArray[] = dateTimeEnd.split(" ");

            pickDate1.setText(dateTimeStartArray[0] + " (" + dateTimeStartArray[1] + ")");
            dropDate1.setText(dateTimeEndArray[0] + " (" + dateTimeEndArray[1] + ")");


            String start = Utility.convertStringIntoData2(startDate);
            String end = Utility.convertStringIntoData2(endDate);
            String startArray[] = start.split("-");
            String endArray[] = end.split("-");


            String range = startArray[0] + " " + startArray[1] + " " + startArray[2] + "-" + endArray[0] + " " + endArray[1] + " " + endArray[2];
            myBookingDate.setText(range);
        }


        if (!TextUtils.isEmpty(carRentalListModel.getDrop_location1())) {
            locationSpinnerList.add(carRentalListModel.getDrop_location1());
        }
        if (!TextUtils.isEmpty(carRentalListModel.getDrop_location2())) {
            locationSpinnerList.add(carRentalListModel.getDrop_location2());
        }

        if (!TextUtils.isEmpty(carRentalListModel.getPickup_location())) {
            carPick.setText(carRentalListModel.getPickup_location());
        }



        ownerType.setText(carRentalListModel.getOwner_type());



//        if (!TextUtils.isEmpty(carRentalListModel.getDaily_price())) {
//            double price = Float.parseFloat(carRentalListModel.getDaily_price());
//             finalPrice = price * numOfDays;
//            costWeek.setText("$" + String.valueOf(finalPrice));
//            finalCostTextView.setText("$" + String.valueOf(finalPrice));
//        }


        if (!TextUtils.isEmpty(carRentalListModel.getOwner_name())) {
            ownerNameBook.setText(carRentalListModel.getOwner_name());
        }

        availableReedemPointsTV.setText("You have " + AppConstant.fmrPoints + " Points To Redeem");


    }

    private void setDataOnSpinner() {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item1, locationSpinnerList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item1);
        locationSpinner.setPrompt("Select Issuing Country");
        locationSpinner.setAdapter(dataAdapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                AlertDialog alertDialog = new AlertDialog.Builder(
//                        BookingActivity.this).create();
//
//
//
//                // Setting Dialog Message
//                alertDialog.setMessage(parent.getSelectedItem().toString());
//
//
//
//                // Showing Alert Message
//                alertDialog.show();
                //Object item = parent.getItemAtPosition(position);
                spinnerDrop = parent.getSelectedItem().toString();
                Log.e("spp", spinnerDrop);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                callAnotherActivity();
                break;
            case R.id.reedemPointInDolorTV:
                reedeem.requestFocus();
                reedeem.setFocusableInTouchMode(true);

                InputMethodManager immmm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                immmm.showSoftInput(reedeem, InputMethodManager.SHOW_FORCED);
                break;

            case R.id.couponEditText:

                couponEditText.requestFocus();
                couponEditText.setFocusableInTouchMode(true);

                InputMethodManager imcoupon = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imcoupon.showSoftInput(couponEditText, InputMethodManager.SHOW_FORCED);

                clearCoupanCode.setVisibility(View.VISIBLE);
                applyCoupanCode.setVisibility(View.VISIBLE);
                break;
            case R.id.clearCoupan:
                if (!TextUtils.isEmpty(couponEditText.getText().toString())) {
                    couponEditText.setText("");
                    clearCoupanCode.setVisibility(View.GONE);
                    applyCoupanCode.setVisibility(View.GONE);
                    couponEditText.setEnabled(true);
                    String finalCost = finalCostTextView.getText().toString();
                    finalCost = finalCost.replace("$", "");
                    String coupanCodeValue = coupanCoodeTV.getText().toString();
                    coupanCodeValue = coupanCodeValue.replace("$", "");
                    double remainingValue = Double.parseDouble(finalCost) + Double.parseDouble(coupanCodeValue);
                    finalCostTextView.setText(String.valueOf(remainingValue));
                    coupanCoodeTV.setText("$0.0");

                }
                break;
            case R.id.applyCoupan:
                if (!TextUtils.isEmpty(couponEditText.getText().toString())) {
                    fetchApplyCouponData();
                } else {
                    Toast.makeText(BookingActivity.this, "Please enter Coupan Code", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }

    private void callAnotherActivity() {
//        if(!sessionManager.getLoggedIn()){
//            Intent intent = new Intent(this,LoginActivithy.class);
//            intent.putExtra("CallingFrom","Booking");
//            startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST_CODE);
//        }
//        else{

        if (TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID))) {
            proceedFurtherLogin();
        } else {
            String dlID = sessionManager.getLicenceDetails().get(SessionManager.KEY_DRI_DETAIL);
            if (TextUtils.isEmpty(dlID)) {
                String finalCost = finalCostTextView.getText().toString();
                finalCost = finalCost.replace("$", "");
                Intent intent = new Intent(BookingActivity.this, DriverLicenceActivity.class);
                intent.putExtra("FinalPrice", finalCost);
                intent.putExtra("RentalData", carRentalListModel);
                intent.putExtra("Start Date", startDate);
                intent.putExtra("End Date", endDate);
                intent.putExtra("carID", carID);
                intent.putExtra("SpinnerDrop", spinnerDrop);
                startActivity(intent);

            } else {
                String finalCost = finalCostTextView.getText().toString();
                finalCost = finalCost.replace("$", "");
                reddempont = reedemPointEditText.getText().toString();
                Intent intent = new Intent(BookingActivity.this, BookingDeatilActivity.class);
                intent.putExtra("FinalPrice", finalCost);
                intent.putExtra("RentalData", carRentalListModel);
                intent.putExtra("Start Date", startDate);
                intent.putExtra("End Date", endDate);
                intent.putExtra("carID", carID);
                intent.putExtra("LicenceID", sessionManager.getLicenceID());
                intent.putExtra("SpinnerDrop", spinnerDrop);
                intent.putExtra("reddempont", reddempont);
                startActivity(intent);
            }
        }

//        String dlID = sessionManager.getLicenceDetails().get(SessionManager.KEY_DRI_DETAIL);
//        if(TextUtils.isEmpty(dlID)){
//            if(TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID))) {
//                proceedFurtherLogin();
//            }
//            else {
//                String finalCost = finalCostTextView.getText().toString();
//                finalCost = finalCost.replace("$","");
//                Intent intent = new Intent(BookingActivity.this, DriverLicenceActivity.class);
//                intent.putExtra("FinalPrice",finalCost);
//                intent.putExtra("RentalData", carRentalListModel);
//                intent.putExtra("Start Date", startDate);
//                intent.putExtra("End Date", endDate);
//                intent.putExtra("carID", carID);
//                intent.putExtra("SpinnerDrop", spinnerDrop);
//                startActivity(intent);
//            }
//        }
//        else{
//            String finalCost = finalCostTextView.getText().toString();
//            finalCost = finalCost.replace("$","");
//            Intent intent = new Intent(BookingActivity.this, BookingDeatilActivity.class);
//            intent.putExtra("FinalPrice",finalCost);
//            intent.putExtra("RentalData", carRentalListModel);
//            intent.putExtra("Start Date", startDate);
//            intent.putExtra("End Date", endDate);
//            intent.putExtra("carID", carID);
//            intent.putExtra("LicenceID", sessionManager.getLicenceID());
//            intent.putExtra("SpinnerDrop", spinnerDrop);
//            startActivity(intent);
//        }


    }

    private void proceedFurtherLogin() {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Login Mandatory")
                .setContentText("Firstly please login into the app!!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        Intent intent = new Intent(BookingActivity.this, LoginActivithy.class);
                        intent.putExtra("CallingFrom", "DriverLicence");
                        startActivityForResult(intent, 4000);


                    }
                })
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == LOGIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                boolean flag = data.getBooleanExtra("loggedIn", false);


            }
        }

        if (requestCode == 4000) {
            if (resultCode == Activity.RESULT_OK) {
                int result = data.getIntExtra("ResponseCode", 0);
                if (result == ESNetworkResponse.ResponseCode.SUCCESS) {
                    callAnotherActivity();
//                    Intent intent = new Intent(BookingActivity.this, DriverLicenceActivity.class);
//                    intent.putExtra("RentalData", carRentalListModel);
//                    intent.putExtra("Start Date", startDate);
//                    intent.putExtra("End Date", endDate);
//                    intent.putExtra("carID", carID);
//                    intent.putExtra("SpinnerDrop", spinnerDrop);
//                    startActivity(intent);
                } else {
                    failureSweetDialg("CarRentalApp", "Your profile has not been updated");
                }
            }

        }
    }


    private void checkConnectivity() {

        if (ConnectionDetector.checkConnection(this)) {
            fetchAlreadyBookData();
        } else {
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
        }
    }


    private void fetchAlreadyBookData() {
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.BOOKING);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("car", carID);
        hashMap.put("start", startDate);
        hashMap.put("end", endDate);
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }

    private void fetchApplyCouponData() {
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.COUPON);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("coupon_name", couponEditText.getText().toString());

        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }


    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        {
            switch (eventType) {
                case ESNetworkRequest.NetworkEventType.BOOKING:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                        ArrayList<BookingModel> prebookingModelArrayList = networkResponse.prebookinglistModelArrayList;
                        ArrayList<BookingModel> postbookingModelArrayList = networkResponse.postbookinglistModelArrayList;
                        if (prebookingModelArrayList != null && prebookingModelArrayList.size() != 0) {
                            if (prebookingModelArrayList.get(0) != null) {
                                String start = Utility.convertDateTimeWithMonthName(prebookingModelArrayList.get(0).getBook_from());
                                String end = Utility.convertDateTimeWithMonthName(prebookingModelArrayList.get(0).getBook_to());
                                String startArray[] = start.split("-");
                                String endArray[] = new String[0];

                                if (!TextUtils.isEmpty(end)) {
                                    endArray = end.split("-");
                                    String range = startArray[0] + " " + startArray[1] + " " + startArray[2] + "-" + endArray[0] + " " + endArray[1] + " " + endArray[2];
                                    alreadyBookedTV.setText(range);
                                } else {
                                    String range = startArray[0] + " " + startArray[1] + " " + startArray[2];
                                    alreadyBookedTV.setText(range);
                                }
                            }
                        } else {
                            alreadyBookedTV.setText("NA");
                        }

                        if (postbookingModelArrayList != null && postbookingModelArrayList.size() != 0) {
                            if (postbookingModelArrayList.get(0) != null) {
                                String start = Utility.convertDateTimeWithMonthName(postbookingModelArrayList.get(0).getBook_from());
                                String end = Utility.convertDateTimeWithMonthName(postbookingModelArrayList.get(0).getBook_to());
                                String startArray[] = start.split("-");
                                String endArray[] = new String[0];

                                if (!TextUtils.isEmpty(end)) {
                                    endArray = end.split("-");
                                    String range = startArray[0] + " " + startArray[1] + " " + startArray[2] + "-" + endArray[0] + " " + endArray[1] + " " + endArray[2];
                                    alreadyBookedTV1.setText(range);
                                } else {
                                    String range = startArray[0] + " " + startArray[1] + " " + startArray[2];
                                    alreadyBookedTV1.setText(range);
                                }
                            }
                        } else {
                            alreadyBookedTV1.setText("NA");
                        }


                        /*ArrayList<BookingModel> bookingModelArrayList = networkResponse.bookinglistModelArrayList;
                        if (bookingModelArrayList != null && bookingModelArrayList.size() != 0) {
                            if (bookingModelArrayList.get(0) != null) {

                                String start = Utility.convertDateTimeWithMonthName(bookingModelArrayList.get(0).getBook_from());
                                String end = Utility.convertDateTimeWithMonthName(bookingModelArrayList.get(0).getBook_to());
                                String startArray [] = start.split("-");
                                String endArray [] = new String[0];

                                if(!TextUtils.isEmpty(end)){
                                     endArray = end.split("-");
                                    String range = startArray[0] + " " +  startArray[1] + " " + startArray[2] + "-" + endArray[0] + " " + endArray[1] + " " + endArray [2] ;
                                    alreadyBookedTV.setText(range);
                                }
                                else{
                                    String range = startArray[0] + " " +  startArray[1] + " " +  startArray [2];
                                    alreadyBookedTV.setText(range);
                                }


//                                alreadyBookedTV.setText(convertStringIntoData(bookingModelArrayList.get(0).getBook_from()) + "-" + convertStringIntoData(bookingModelArrayList.get(0).getBook_to()));
                            }

                            if (bookingModelArrayList.size() > 1) {
                                if (bookingModelArrayList.get(1) != null) {
                                    String start = Utility.convertDateTimeWithMonthName(bookingModelArrayList.get(1).getBook_from());
                                    String end = Utility.convertDateTimeWithMonthName(bookingModelArrayList.get(1).getBook_to());
                                    String startArray [] = start.split("-");

                                    String endArray [] = new String[0];

                                    if(!TextUtils.isEmpty(end)){
                                        endArray = end.split("-");
                                        String range = startArray[0] + " " +  startArray[1] + " "  + startArray[2] + "-" + endArray[0] + " " + endArray[1] + " " + endArray [2] ;
                                        alreadyBookedTV1.setText(range);
                                    }
                                    else{
                                        String range = startArray[0] + " " +  startArray[1] + " " +  startArray [2] ;
                                        alreadyBookedTV1.setText(range);
                                    }

//                                    alreadyBookedTV1.setText(convertStringIntoData(bookingModelArrayList.get(1).getBook_from()) + "-" + convertStringIntoData(bookingModelArrayList.get(1).getBook_to()));
                                } else {
                                    alreadyBookedTV1.setText("NA");
                                }

                            } else {
                                alreadyBookedTV1.setText("NA");
                            }

                        } else {
                            alreadyBookedTV.setText("NA");
                            alreadyBookedTV1.setText("NA");
                        }*/

                    } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
//                        failureSweetDialg("Error",networkResponse.responseMessage);
                        leftBookedLayout.setVisibility(View.VISIBLE);
                        rightBookedLayout.setVisibility(View.VISIBLE);
                        alreadyBookedTV.setText("NA");
                        alreadyBookedTV1.setText("NA");
                    } else {
//                        failureSweetDialg("Error",networkResponse.responseMessage);
                        leftBookedLayout.setVisibility(View.VISIBLE);
                        rightBookedLayout.setVisibility(View.VISIBLE);
                        alreadyBookedTV.setText("NA");
                        alreadyBookedTV1.setText("NA");
                    }
                    break;

                case ESNetworkRequest.NetworkEventType.COUPON:
                    if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                        applyCoupanCode.setVisibility(View.GONE);
                        clearCoupanCode.setVisibility(View.VISIBLE);
                        couponEditText.setEnabled(false);
                        ArrayList<FmrCouponModel> couponModelArrayList = networkResponse.fmrcouponlistModelArrayList;
                        if (couponModelArrayList != null && couponModelArrayList.size() > 0) {
                            FmrCouponModel fmrCouponModel = couponModelArrayList.get(0);
                            if (fmrCouponModel != null) {
                                String coupenPercent = fmrCouponModel.getCoupon_percent();
                                String maxDiscount = fmrCouponModel.getMax_discount();

                                int coupanPercentValue = Integer.parseInt(coupenPercent);
                                double maxDiscountValue = Double.parseDouble(maxDiscount);

                                String finalCost = finalCostTextView.getText().toString();
                                finalCost = finalCost.replace("$", "");
                                double finalCostValue = Double.parseDouble(finalCost);

                                double discountedValue = (finalCostValue * coupanPercentValue) / 100.00;
                                if (discountedValue > maxDiscountValue) {
                                    Toast.makeText(this, "We gave only $" + String.valueOf(maxDiscountValue) + " discount amount", Toast.LENGTH_SHORT).show();
                                    double value = finalCostValue - maxDiscountValue;
                                    finalCostTextView.setText("$" + String.valueOf(value));
                                    coupanCoodeTV.setText("$" + String.valueOf(maxDiscountValue));
//                                    costWeek.setText("$" + String.valueOf(maxDiscountValue));

                                } else {
                                    double value = finalCostValue - discountedValue;
                                    finalCostTextView.setText("$" + String.valueOf(value));
                                    coupanCoodeTV.setText("$" + String.valueOf(discountedValue));
//                                    costWeek.setText("$" + String.valueOf(value));
                                }


                            }
                        }

                    } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                        failureSweetDialg("", networkResponse.responseMessage);
                        couponEditText.setEnabled(true);
                        applyCoupanCode.setVisibility(View.GONE);
                        couponEditText.setText("");
                        hideKeyboard();

                    } else {
                        failureSweetDialg("", networkResponse.responseMessage);
                        couponEditText.setEnabled(true);
                        applyCoupanCode.setVisibility(View.GONE);
                        couponEditText.setText("");
                        hideKeyboard();

                    }
                    break;

            }
        }
    }


    private String convertStringIntoData(String dateStr) {
        String date = null;
//        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MMM-dd hh:mm");

        try {
            Date parsedDate = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-yyyy");
            date = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
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