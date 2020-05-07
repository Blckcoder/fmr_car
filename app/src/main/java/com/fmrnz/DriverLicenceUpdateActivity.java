package com.fmrnz;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.model.LicenceModel;
import com.fmrnz.utils.CircularNetworkImageView;
import com.fmrnz.utils.Utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;


public class DriverLicenceUpdateActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private static final int CAMERA_PIC_REQUEST = 2500;
    private static final int GALLERY_PIC_REQUEST = 1;
    EditText lcstartdate, lcenddate, lcdob,userLic, numberLic, contactLic, addLic;
    LinearLayout linearIssue, linearExpiry, lineardob;
    DatePickerDialog mDatePickerDialog;
    String spinnerDrop,spinnerValue="",startDate,endDate,carID,dlCountryIsse, dlissueDate, dlexparyDate, dlCustomerName, dlNumber, dlCustDob, dlAlterNumber, dlCompleteAddress;
    CarRentalListModel carRentalListModel;
//    CircularDefaultImageView imageCar;
    CircularNetworkImageView imageCarNetwork;
    ImageLoader imageLoader;
    RadioGroup radioGroup;
    RelativeLayout relativeLayout;
    RadioButton radioButton;
    Button proceedButton;
    byte[] byteArray;
    HashMap<String, String> loginHashMap;
    Spinner countrySpinner;
    HashMap<String, String> profileData;
    Bitmap bitmap;


    Typeface tfavv;

    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    AVLoadingIndicatorView progressIndicatorView;
    ImageView issueDateImageView,expiryDateImageView,dateOfBirtImageView;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);
        setActionBar();
        fetchIntentData();
        setUpView();
        setUpListeners();
     //   setUpSpinnerData();
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
        imageLoader = networkController.getImageLoader();
        loginHashMap = sessionManager.getUserDetails();
        carID = getIntent().getStringExtra("carID");
        carRentalListModel = (CarRentalListModel) getIntent().getParcelableExtra("RentalData");
        startDate = getIntent().getStringExtra("Start Date");
        endDate = getIntent().getStringExtra("End Date");
        spinnerDrop = getIntent().getStringExtra("SpinnerDrop");

        dlCountryIsse = "Having a New Zealand \n Driver's Licence";
    }

    private void setUpView(){
        progressIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));
        lcenddate = (EditText) findViewById(R.id.lcendDate);
        lcstartdate = (EditText) findViewById(R.id.lcstartDate);
        lcdob = (EditText) findViewById(R.id.lcdob);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        relativeLayout = (RelativeLayout) findViewById(R.id.spinnerRelativeLayout);
        proceedButton = (Button) findViewById(R.id.proceesButton);
        relativeLayout.setVisibility(View.GONE);
        userLic = (EditText) findViewById(R.id.userNameLic);
        numberLic = (EditText) findViewById(R.id.lcNumber);
        contactLic = (EditText) findViewById(R.id.contactNumber);
        addLic = (EditText) findViewById(R.id.licAdd);
        linearIssue = (LinearLayout) findViewById(R.id.linearIssue);
        linearExpiry = (LinearLayout) findViewById(R.id.linearExpiry);
        lineardob = (LinearLayout) findViewById(R.id.lineardob);
//        imageCar = (CircularDefaultImageView) findViewById(R.id.imageCar);
        imageCarNetwork = (CircularNetworkImageView)findViewById(R.id.imageCarNetwork);
        countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
        final String[] items = getResources().getStringArray(R.array.countries_array);
        issueDateImageView = (ImageView)findViewById(R.id.issueImageView);
        expiryDateImageView = (ImageView)findViewById(R.id.expiryImageView);
        dateOfBirtImageView = (ImageView)findViewById(R.id.dobImageView);


        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_layout,items){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
//                tfavv = Typeface.createFromAsset(getAssets(),"fonts/Roboto.ttf");
                TextView v = (TextView) super.getView(position, convertView, parent);

                TextView tv = (TextView) view;
                v.setTypeface(tfavv);
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
        countrySpinner.setAdapter(spinnerArrayAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                spinnerValue = selectedItemText;

                if(position > 0){
//                    textView.setText("Selected : " + selectedItemText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        countrySpinner.setAdapter(spinnerArrayAdapter);


    }


    private void setUpListeners() {
//        imageCar.setOnClickListener(this);
        imageCarNetwork.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        proceedButton.setOnClickListener(this);
        linearIssue.setOnClickListener(this);
        linearExpiry.setOnClickListener(this);
        lineardob.setOnClickListener(this);
    }

    private void setUpData(){
        HashMap<String,String> licenceHashMap = sessionManager.getLicenceDetails();

        if (licenceHashMap.get(SessionManager.KEY_DRI_DETAIL) != null){

            if(licenceHashMap.get(SessionManager.KEY_DRI_CUSTOMER_IMAGE) != null){
//                imageCar.setVisibility(View.GONE);
                imageCarNetwork.setVisibility(View.VISIBLE);
                imageCarNetwork.setImageUrl(licenceHashMap.get(SessionManager.KEY_DRI_CUSTOMER_IMAGE),imageLoader);
            }
//            else if(sessionManager.getImageBitmapLicence() != null){
////                imageCar.setImageBitmap(Utility.StringToBitMap(sessionManager.getImageBitmapLicence()));
////                imageCar.setVisibility(View.VISIBLE);
//                imageCarNetwork.setVisibility(View.GONE);
//            }
            else{
                imageCarNetwork.setImageBitmap(Utility.StringToBitMap(sessionManager.getImageBitmapLicence()));
//                imageCar.setVisibility(View.VISIBLE);
                imageCarNetwork.setVisibility(View.VISIBLE);
            }

            if(licenceHashMap.get(SessionManager.KEY_DL_ISSUE_DATE) != null){
                lcstartdate.setText(Utility.convertStringIntoData2(licenceHashMap.get(SessionManager.KEY_DL_ISSUE_DATE)));
            }
            else{
                lcstartdate.setText("Issuing Date");
            }

            if(licenceHashMap.get(SessionManager.KEY_DL_EXPIRY_DATE) != null){
                lcenddate.setText(Utility.convertStringIntoData2(licenceHashMap.get(SessionManager.KEY_DL_EXPIRY_DATE)));

            }
            else{
                lcenddate.setText("Expiry Date");

            }

            if(licenceHashMap.get(SessionManager.KEY_CUSTOMER_NAME) != null){
                userLic.setText(licenceHashMap.get(SessionManager.KEY_CUSTOMER_NAME));
            }
            else{
                userLic.setHint("Your Full Name (as it appears in your driver's licence)");
            }

            if(licenceHashMap.get(SessionManager.KEY_DL_NUMBER) != null){
                numberLic.setText(licenceHashMap.get(SessionManager.KEY_DL_NUMBER));

            }
            else{
                numberLic.setHint("Your Licence Number");

            }

            if(licenceHashMap.get(SessionManager.KEY_CUSTOMER_DOB) != null){
                lcdob.setText(Utility.convertStringIntoData2(licenceHashMap.get(SessionManager.KEY_CUSTOMER_DOB)));

            }
            else{
                lcdob.setText("Date Of Birth");

            }

            if(licenceHashMap.get(SessionManager.KEY_ALTERNATE_NUMBER) != null){
                contactLic.setText(licenceHashMap.get(SessionManager.KEY_ALTERNATE_NUMBER));

            }
            else{
                contactLic.setHint("Alternate Contact No.");

            }

            if(licenceHashMap.get(SessionManager.KEY_COMPLETE_ADDRESS) != null){
                addLic.setText(licenceHashMap.get(SessionManager.KEY_COMPLETE_ADDRESS));
            }
            else{
                addLic.setHint("Complete Address (as it appears in your driver's licence)");
            }


        }
    }



    private void setDataPickerData(final EditText editText, long minDate, long maxDate, final String type, final ImageView imageView){
        editText.setError(null);
        final Calendar currentDate = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

        Date d = new Date();
        dateFormatter.format(d);
        currentDate.setTime(d);

        int mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int mMonth = currentDate.get(Calendar.MONTH);
        int mYaer = currentDate.get(Calendar.YEAR);


        mDatePickerDialog = new
                DatePickerDialog(DriverLicenceUpdateActivity.this, new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        if(type.equals("dob") && !TextUtils.isEmpty(lcstartdate.getText().toString())){
                            Date issue = convertIntoDate(lcstartdate.getText().toString());
                            Date dob = convertIntoDate(dateFormatter.format(newDate.getTime()));
                            int diff = getDiffYears(issue,dob);
                            if(diff >= 18){
                                editText.setText(dateFormatter.format(newDate.getTime()));
                                editText.setTextColor(getResources().getColor(R.color.buttonColor));
                                imageView.setColorFilter(ContextCompat.getColor(DriverLicenceUpdateActivity.this, R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
                            }
                            else{
                                Toast.makeText(DriverLicenceUpdateActivity.this,"Age should be greater than 18",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(!lcdob.getText().toString().equals("Date Of Birth")){
                            Date dob = convertIntoDate(lcdob.getText().toString());
                            Date issue = convertIntoDate(dateFormatter.format(newDate.getTime()));
                            int diff = getDiffYears(issue,dob);
                            if(diff >= 18){
                                editText.setText(dateFormatter.format(newDate.getTime()));
                                editText.setTextColor(getResources().getColor(R.color.buttonColor));
                                imageView.setColorFilter(ContextCompat.getColor(DriverLicenceUpdateActivity.this, R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
                            }
                            else{
                                Toast.makeText(DriverLicenceUpdateActivity.this,"Age should be greater than 18",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            editText.setText(dateFormatter.format(newDate.getTime()));
                            editText.setTextColor(getResources().getColor(R.color.buttonColor));
                            imageView.setColorFilter(ContextCompat.getColor(DriverLicenceUpdateActivity.this, R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
                        }

                    }
                }, mYaer, mMonth, mDay);
        mDatePickerDialog.setTitle("Select Date");
        Calendar newDate = Calendar.getInstance();
        int yaer = 1930;
        int month = 1;
        int day = 1;
        newDate.set(yaer, month, day);

        if(maxDate != 0){
            mDatePickerDialog.getDatePicker().setMaxDate(maxDate);
        }

        mDatePickerDialog.getDatePicker().setMinDate(minDate);

        mDatePickerDialog.show();

    }

    public  int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = a.get(Calendar.YEAR) - b.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public  Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    private Date setMinDatForDropDate(){
        Date date = null;
        String startdate = lcstartdate.getText().toString();
        String pick = Utility.convertStringIntoData(startdate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(pick);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }



    private Date convertIntoDate(String inputDate){
        Date date = null;
        String pick = Utility.convertStringIntoData(inputDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(pick);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    @Override
    public void onClick(View v) {
        Calendar newDate = Calendar.getInstance();
        int yaer = 1930;
        int month = 1;
        int day = 1;
        newDate.set(yaer, month, day);

        switch (v.getId()) {
            case R.id.proceesButton:
                if(validate()){
                    lcstartdate.setEnabled(false);
                    lcenddate.setEnabled(false);
                    lcdob.setEnabled(false);
                    userLic.setEnabled(false);
                    numberLic.setEnabled(false);
                    contactLic.setEnabled(false);
                    addLic.setEnabled(false);
                    if(!TextUtils.isEmpty(loginHashMap.get(SessionManager.KEY_ID))) {
                        getData();
                    }
                    else {
                     //   proceedFurtherLogin();
                    }

                }
                else{
                    lcstartdate.setEnabled(true);
                    lcenddate.setEnabled(true);
                    lcdob.setEnabled(true);
                    userLic.setEnabled(true);
                    numberLic.setEnabled(true);
                    contactLic.setEnabled(true);
                    addLic.setEnabled(true);
                }

                break;
            case R.id.imageCar:
            case R.id.imageCarNetwork:
                cameraPermission();
                break;
            case R.id.lineardob:

                setDataPickerData(lcdob,newDate.getTimeInMillis(),System.currentTimeMillis(),"dob",dateOfBirtImageView);
                break;
            case R.id.linearExpiry:

                    setDataPickerExpired(lcenddate,0,0,expiryDateImageView);
                break;
            case R.id.linearIssue:
                setDataPickerData(lcstartdate,newDate.getTimeInMillis(),System.currentTimeMillis(),"issue",issueDateImageView);
                break;
            case R.id.userNameLic:
                userLic.requestFocus();
                userLic.setFocusableInTouchMode(true);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(userLic, InputMethodManager.SHOW_FORCED);

            case R.id.licAdd:

                addLic.requestFocus();
                addLic.setFocusableInTouchMode(true);

                InputMethodManager immm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                immm.showSoftInput(addLic, InputMethodManager.SHOW_FORCED);

            case R.id.lcNumber:

                numberLic.requestFocus();
                numberLic.setFocusableInTouchMode(true);

                InputMethodManager immmm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                immmm.showSoftInput(numberLic, InputMethodManager.SHOW_FORCED);
        }
    }

    public void failureNotifySweetDialg(String title, String msg){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        //   finish();

                    }
                })
                .show();
    }

    private void setDataPickerExpired(final TextView editText, long minDate, long maxDate,final ImageView imageView){
        editText.setError(null);
        final Calendar currentDate = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

        Date d = new Date();
        dateFormatter.format(d);
        currentDate.setTime(d);

        int mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int mMonth = currentDate.get(Calendar.MONTH);
        int mYaer = currentDate.get(Calendar.YEAR);


        mDatePickerDialog = new
                DatePickerDialog(this, new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        editText.setText(dateFormatter.format(newDate.getTime()));
                        editText.setTextColor(getResources().getColor(R.color.buttonColor));
                        imageView.setColorFilter(ContextCompat.getColor(DriverLicenceUpdateActivity.this, R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);                        //editText.setText(dateFormatter.format(newDate.getTime()));

                    }
                }, mYaer, mMonth, mDay);
        mDatePickerDialog.setTitle("Select Date");
        Calendar newDate = Calendar.getInstance();
        int yaer = 1970;
        int month = 1;
        int day = 1;
        newDate.set(yaer, month, day);





        Calendar cal = Calendar.getInstance();
        cal.setTime(setMinDatForDropDate());
        cal.add(Calendar.DATE,30);
        Date minimumDate = cal.getTime();
        mDatePickerDialog.getDatePicker().setMinDate(minimumDate.getTime());



        mDatePickerDialog.show();

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
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        dlCountryIsse = radioButton.getText().toString();
        if (radioButton.getText().equals("Having a New Zealand \n Driver's Licence")) {
            relativeLayout.setVisibility(View.GONE);
            spinnerValue="";

        } else {
            relativeLayout.setVisibility(View.VISIBLE);
        }

    }


    private void getData() {

        if (ConnectionDetector.checkConnection(this)) {
            sendImageDataRequest();
        }
        else{
            lcstartdate.setEnabled(true);
            lcenddate.setEnabled(true);
            lcdob.setEnabled(true);
            userLic.setEnabled(true);
            numberLic.setEnabled(true);
            contactLic.setEnabled(true);
            addLic.setEnabled(true);
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
            Utils.hideProgressBar(progressIndicatorView);

        }



    }

    public boolean validate() {
        boolean valid = true;
        dlissueDate = lcstartdate.getText().toString();
        dlissueDate = Utility.convertStringIntoMonthSate(dlissueDate);

        dlexparyDate = lcenddate.getText().toString();
        dlexparyDate = Utility.convertStringIntoMonthSate(dlexparyDate);

        dlCustDob = lcdob.getText().toString();
        dlCustDob = Utility.convertStringIntoMonthSate(dlCustDob);

        dlCustomerName = userLic.getText().toString();
        dlNumber = numberLic.getText().toString();
        dlAlterNumber = contactLic.getText().toString();
        dlCompleteAddress = addLic.getText().toString();


        if (lcstartdate.getText().toString().trim().equals("")) {
            lcstartdate.setError("Required field");
            valid = false;
        }

        if (lcenddate.getText().toString().trim().equals("")) {
            lcenddate.setError("Required field");
            valid = false;
        }

        if (lcdob.getText().toString().trim().equals("")) {
            lcdob.setError("Required field");
            valid = false;
        }
        if (userLic.getText().toString().trim().equals("")) {
            userLic.setError("Required field");
            valid = false;
        }

        if (numberLic.getText().toString().trim().equals("")) {
            numberLic.setError("Required field");
            valid = false;
        }

        if (contactLic.getText().toString().trim().equals("")) {
            contactLic.setError("Required field");
            valid = false;
        }

        if (addLic.getText().toString().trim().equals("")) {
            addLic.setError("Required field");
            valid = false;
        }
        if (dlCountryIsse.equals("Having a Foreign \n Driver's Licence")) {
            if(spinnerValue.equals("Select Issuing Country")){
                Toast.makeText(this,"Please select Country",Toast.LENGTH_SHORT).show();
                valid = false;
            }
        }




//        if(byteArray == null && TextUtils.isEmpty(sessionManager.getLicenceDetails().get(SessionManager.KEY_DRI_CUSTOMER_IMAGE))){
//            failureSweetDialg("CarRent","Please select image");
//            valid = false;
//        }


        return valid;
    }

    private void sendImageDataRequest() {
        Utils.showProgressBar(progressIndicatorView);
        ESAppRequest mgLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.LICENCE_UPDATE);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("user_id", loginHashMap.get(SessionManager.KEY_ID));
        hashMap.put("dl_country", spinnerValue);
        hashMap.put("dl_country_issue", dlCountryIsse);
        hashMap.put("dl_issue_date", dlissueDate);
        hashMap.put("dlexpiry_date", dlexparyDate);
        hashMap.put("customer_name", dlCustomerName);
        hashMap.put("dl_number", dlNumber);
        hashMap.put("customer_dob", dlCustDob);
        hashMap.put("alternate_number", dlAlterNumber);
        hashMap.put("complete_address", dlCompleteAddress);
        mgLoginRequest.byteArray = byteArray;
        mgLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(mgLoginRequest);
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        Utils.hideProgressBar(progressIndicatorView);


        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.LICENCE_UPDATE:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage =
                            networkResponse.responseMessage;
                    suuccessSweetDialgofForSuccess("Succes", responseMessage);

                    if(bitmap != null) {
                        String bitmapImage = Utility.BitMapToString(bitmap);
                        sessionManager.setImageBitmapLicence(bitmapImage);
                    }

                    LicenceModel licenceModel = new LicenceModel();

                    licenceModel.setAlternate_number(dlAlterNumber);
                    licenceModel.setComplete_address(dlCompleteAddress);
                    licenceModel.setCustomer_dob(dlCustDob);
                    licenceModel.setDl_issue_date(dlissueDate);
                    licenceModel.setDlexpiry_date(dlexparyDate);
                    licenceModel.setDl_number(dlNumber);
                    licenceModel.setCustomer_name(dlCustomerName);
                    licenceModel.setDl_country(spinnerValue);
                    licenceModel.setDl_country_issue(dlCountryIsse);
                    licenceModel.setDriving_detail_id(sessionManager.getLicenceDetails().get(SessionManager.KEY_DRI_DETAIL));
                    sessionManager.createLicenceData(licenceModel);



//                        Intent intent = new Intent(DriverLicenceUpdateActivity.this, BookingDeatilActivity.class);
//
//                        startActivity(intent);


                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                    failureSweetDialg("Error",networkResponse.responseMessage);
                    lcstartdate.setEnabled(true);
                    lcenddate.setEnabled(true);
                    lcdob.setEnabled(true);
                    userLic.setEnabled(true);
                    numberLic.setEnabled(true);
                    contactLic.setEnabled(true);
                    addLic.setEnabled(true);

                } else {
                    failureSweetDialg("Error",networkResponse.responseMessage);
                    lcstartdate.setEnabled(true);
                    lcenddate.setEnabled(true);
                    lcdob.setEnabled(true);
                    userLic.setEnabled(true);
                    numberLic.setEnabled(true);
                    contactLic.setEnabled(true);
                    addLic.setEnabled(true);
                }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility1.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        dispatchTakenPictureIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DriverLicenceUpdateActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility1.checkPermission(DriverLicenceUpdateActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        dispatchTakenPictureIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraPermission(){
        if(ContextCompat.checkSelfPermission(DriverLicenceUpdateActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            selectImage();
        }

        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)){
                Toast.makeText(getApplicationContext(), "Permission Needed.", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
        }
    }


    private void dispatchTakenPictureIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

        if (requestCode == 4000) {
            if(resultCode == Activity.RESULT_OK){
                int result=data.getIntExtra("ResponseCode",0);
                if(result == ESNetworkResponse.ResponseCode.SUCCESS){
                    getData();
                }
                else{
                    failureSweetDialg("CarRentalApp", "Your profile has not been updated");
                }
            }

        }
    }

    private void onCaptureImageResult(Intent data) {

        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byteArray = stream.toByteArray();
        imageCarNetwork.setImageBitmap(bitmap);
//        imageCar.setVisibility(View.GONE);
        imageCarNetwork.setVisibility(View.VISIBLE);


//        imageCar.setImageBitmap(bitmap);
//        imageCar.setVisibility(View.VISIBLE);
//        imageCarNetwork.setVisibility(View.GONE);



//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//
//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        imageCar.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byteArray = stream.toByteArray();
                imageCarNetwork.setImageBitmap(bitmap);
//                imageCar.setVisibility(View.GONE);
                imageCarNetwork.setVisibility(View.VISIBLE);
//                imageCar.setImageBitmap(bitmap);
//                imageCar.setVisibility(View.VISIBLE);
//                imageCarNetwork.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        imageCar.setImageBitmap(bitmap);
//        imageCarNetwork.setVisibility(View.GONE);
//        imageCarNetwork.setVisibility(View.VISIBLE);
        imageCarNetwork.setImageBitmap(bitmap);
//        imageCar.setVisibility(View.GONE);
        imageCarNetwork.setVisibility(View.VISIBLE);
    }


}






