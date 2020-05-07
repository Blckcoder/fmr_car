package com.fmrnz.fragment;

/**
 * Created by abhiandroid on 9/10/17.
 */

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fmrnz.CarByDayActivity;
import com.fmrnz.ConnectionDetector;
import com.fmrnz.GPEditableActivity;
import com.fmrnz.GPSTracker;
import com.fmrnz.LocationBaseActivity;
import com.fmrnz.NZApplication;
import com.fmrnz.R;
import com.fmrnz.Utils;
import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.interfaces.LocationInterface;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.utils.AppConstant;
import com.fmrnz.utils.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

public class CarRentalFragment extends BaseFragment implements View.OnClickListener {

    Button bookDay, bookHour;
    //    EditText startDate,endDate;
    TextView startDate, endDate, starttime, endtime;
    TextView currentLocation;
    TextView currentLocation_hour;
    //    EditText startDate,endDate;
    String locationAddress;
    TextView tv_rent_by_hour,textView3;

    GPSTracker gps;


    String pickDate, dropDate, startDate1, endDate1;
    DatePickerDialog mDatePickerDialog;
    LinearLayout linearStart, linearEnd, linearStar, linearEn;
    AVLoadingIndicatorView progressIndicatorView;
    LinearLayout edLocation, edLocation_hour;
    ImageView pickUpImageView, pickUpImageView_hour, dropOfImageView, locationImageView, loctaion_image_hour, dropofimageview_hour_drop;

    private double userLatitude, userLongitude;


    public CarRentalFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = FragemntType.CAR_RENTAL_FRAGMENT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_rental, container, false);
        setActionBar();
        setUpView(view);
        setUpListeners();

        setUpData();
        ((LocationBaseActivity) getActivity()).updateApi(new LocationInterface() {

            @Override
            public void fetchLocation() {
                setUpData();
            }
        });
        return view;

    }

    private void setActionBar() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private void setUpView(View view) {
        progressIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));
        bookDay = (Button) view.findViewById(R.id.bookDay);
        bookHour = (Button) view.findViewById(R.id.bookHour);
        startDate = (TextView) view.findViewById(R.id.startDate);
        starttime = (TextView) view.findViewById(R.id.starttime);
        endDate = (TextView) view.findViewById(R.id.endDate);
        endtime = (TextView) view.findViewById(R.id.endtime);
        tv_rent_by_hour = (TextView) view.findViewById(R.id.tv_rent_by_hour);
        tv_rent_by_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_Alert_For_rent_By_Hour();

            }
        });
        textView3 = (TextView) view.findViewById(R.id.textView3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_Alert_For_rent_By_Day();

            }
        });
        currentLocation = (TextView) view.findViewById(R.id.currentlocationRentByDay);
        currentLocation_hour = (TextView) view.findViewById(R.id.currentlocationRentByHour);
        linearEnd = (LinearLayout) view.findViewById(R.id.linearEnd);
        linearEn = (LinearLayout) view.findViewById(R.id.linearEn);
        linearStart = (LinearLayout) view.findViewById(R.id.linearStart);
        linearStar = (LinearLayout) view.findViewById(R.id.linearStar);
        edLocation = (LinearLayout) view.findViewById(R.id.edLocation);
        edLocation_hour = (LinearLayout) view.findViewById(R.id.edLocation_hour);
        pickUpImageView = (ImageView) view.findViewById(R.id.pickUpImage);
        pickUpImageView_hour = (ImageView) view.findViewById(R.id.pickupimageview_hour);
        dropOfImageView = (ImageView) view.findViewById(R.id.dropOffImage);
        dropOfImageView = (ImageView) view.findViewById(R.id.dropOffImage);
        dropofimageview_hour_drop = (ImageView) view.findViewById(R.id.dropofimageview_hour_drop);
        locationImageView = (ImageView) view.findViewById(R.id.loctaion_image_hour);
        loctaion_image_hour = (ImageView) view.findViewById(R.id.locationImage);
    }

    private void setUpListeners() {
        bookDay.setOnClickListener(this);
        bookHour.setOnClickListener(this);
        linearStart.setOnClickListener(this);
        linearStar.setOnClickListener(this);
        linearEnd.setOnClickListener(this);
        linearEn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        pickDate = "";
        dropDate = "";
        startDate.setText("Pickup Date");
        starttime.setText("Pickup Time");
        endDate.setText("Dropoff Date");
        endtime.setText("Dropoff Time");

        startDate.setTextColor(getResources().getColor(R.color.gray_btn_bg_pressed_color));
        starttime.setTextColor(getResources().getColor(R.color.gray_btn_bg_pressed_color));
        pickUpImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.gray_btn_bg_pressed_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        pickUpImageView_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.gray_btn_bg_pressed_color), android.graphics.PorterDuff.Mode.MULTIPLY);

        endDate.setTextColor(getResources().getColor(R.color.gray_btn_bg_pressed_color));
        endtime.setTextColor(getResources().getColor(R.color.gray_btn_bg_pressed_color));
        dropOfImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.gray_btn_bg_pressed_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        dropofimageview_hour_drop.setColorFilter(ContextCompat.getColor(getActivity(), R.color.gray_btn_bg_pressed_color), android.graphics.PorterDuff.Mode.MULTIPLY);


        edLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent igploc = new Intent(getActivity(), GPEditableActivity.class);
                igploc.putExtra("CLOC", currentLocation.getText().toString());
                startActivityForResult(igploc, 1000);
            }
        });

        edLocation_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent igploc = new Intent(getActivity(), GPEditableActivity.class);
                igploc.putExtra("CLOC", currentLocation_hour.getText().toString());
                startActivityForResult(igploc, 2000);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 1000) {

            if (data != null) {
                Bundle bundle = data.getBundleExtra("Data");
                if (bundle != null) {
                    boolean flag = bundle.getBoolean("flag");

                    if (flag) {
                       String locationvalue = bundle.getString("currentLocation");

                        if (!TextUtils.isEmpty(locationvalue)) {
                            currentLocation.setText(locationvalue);
                            currentLocation.setTextColor(getResources().getColor(R.color.buttonColor));
                            locationImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
                        } else {
                            currentLocation.setText("Your Pickup Location");
                            currentLocation.setTextColor(getResources().getColor(R.color.colorcontent));
                            locationImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorcontent), android.graphics.PorterDuff.Mode.MULTIPLY);
                        }

//                        userLatitude = AppConstant.latitude;
//                        userLongitude = AppConstant.longitude;
                    } else {
                        String locationvalue = bundle.getString("location");
                        AppConstant.latitude=bundle.getDouble("Latitude");
                        AppConstant.longitude=bundle.getDouble("Longitude");
                        if (!TextUtils.isEmpty(locationvalue)) {

                            currentLocation.setText(locationvalue);
                            currentLocation.setTextColor(getResources().getColor(R.color.buttonColor));
                            locationImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
                        } else {
                            currentLocation.setText("Your Pickup Location");
                            currentLocation.setTextColor(getResources().getColor(R.color.colorcontent));
                            locationImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorcontent), android.graphics.PorterDuff.Mode.MULTIPLY);
                        }

//                        double latDouble = bundle.getDouble("Latitude");
//                        double longDouble = bundle.getDouble("Longitude");
//                        userLatitude = latDouble;
//                        userLongitude = longDouble;
                    }


                } else {
                    if (!TextUtils.isEmpty(locationAddress)) {
                        currentLocation.setText(AppConstant.user_current_location);
                        currentLocation.setTextColor(getResources().getColor(R.color.buttonColor));
                        locationImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
                    } else {
                        currentLocation.setText(AppConstant.user_current_location);
                        currentLocation.setTextColor(getResources().getColor(R.color.colorcontent));
                        locationImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorcontent), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }


                }

            } else {

                currentLocation.setText(NZApplication.mDataLocation);
                currentLocation.setTextColor(getResources().getColor(R.color.buttonColor));
                locationImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);


            }

        }
        if (requestCode == 2000) {

            if (data != null) {
                Bundle bundle = data.getBundleExtra("Data");
                if (bundle != null) {
                    boolean flag = bundle.getBoolean("flag");

                    if (flag) {
                        String locationvalue = bundle.getString("currentLocation");
                        if (!TextUtils.isEmpty(locationvalue)) {
                            AppConstant.user_current_location= locationvalue;
                            currentLocation_hour.setText(locationvalue);
                            currentLocation_hour.setTextColor(getResources().getColor(R.color.buttonColor));
                            loctaion_image_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
                        } else {
                            currentLocation_hour.setText("Your Pickup Location");
                            currentLocation_hour.setTextColor(getResources().getColor(R.color.colorcontent));
                            loctaion_image_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorcontent), android.graphics.PorterDuff.Mode.MULTIPLY);
                        }

//                        userLatitude = AppConstant.latitude;
//                        userLongitude = AppConstant.longitude;
                    } else {
                        String locationvalue = bundle.getString("location");
                        AppConstant.latitude=bundle.getDouble("Latitude");
                        AppConstant.longitude=bundle.getDouble("Longitude");
                        if (!TextUtils.isEmpty(locationvalue)) {
                            currentLocation_hour.setText(locationvalue);
                            currentLocation_hour.setTextColor(getResources().getColor(R.color.buttonColor));
                            loctaion_image_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
                        } else {
                            currentLocation_hour.setText("Your Pickup Location");
                            currentLocation_hour.setTextColor(getResources().getColor(R.color.colorcontent));
                            loctaion_image_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorcontent), android.graphics.PorterDuff.Mode.MULTIPLY);
                        }

//                        double latDouble = bundle.getDouble("Latitude");
//                        double longDouble = bundle.getDouble("Longitude");
//                        userLatitude = latDouble;
//                        userLongitude = longDouble;
                    }


                } else {
                    if (!TextUtils.isEmpty(locationAddress)) {
                        currentLocation_hour.setText(AppConstant.user_current_location);
                        currentLocation_hour.setTextColor(getResources().getColor(R.color.buttonColor));
                        loctaion_image_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
                    } else {
                        currentLocation_hour.setText(AppConstant.user_current_location);
                        currentLocation_hour.setTextColor(getResources().getColor(R.color.colorcontent));
                        loctaion_image_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorcontent), android.graphics.PorterDuff.Mode.MULTIPLY);
                    }


                }

            } else {

                currentLocation_hour.setText(NZApplication.mDataLocation);
                currentLocation_hour.setTextColor(getResources().getColor(R.color.buttonColor));
                loctaion_image_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);


            }

        }
    }

    private void setUpData() {
        currentLocation.setText(AppConstant.user_current_location);
        currentLocation_hour.setText(AppConstant.user_current_location);
        NZApplication.mDataLocation = AppConstant.user_current_location;
        currentLocation.setTextColor(getResources().getColor(R.color.buttonColor));
        currentLocation_hour.setTextColor(getResources().getColor(R.color.buttonColor));
        locationImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);
        loctaion_image_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);

    }


    @Override
    public void onClick(View v) {
        Calendar newDate = Calendar.getInstance();
        int yaer = 1970;
        int month = 1;
        int day = 1;
        newDate.set(yaer, month, day);

        switch (v.getId()) {
            case R.id.bookDay:
                if (endDate.getText().toString().equals("Dropoff Date")) {
                    failureNotifySweetDialg("Error Message", "Please select Dropoff Date");
                } else {
                    pickDate = startDate.getText().toString();
                    pickDate = Utility.convertStringIntoDataWithTime(pickDate);
                    dropDate = endDate.getText().toString();
                    dropDate = Utility.convertStringIntoDataWithTime(dropDate);
                    if (TextUtils.isEmpty(pickDate) && TextUtils.isEmpty(dropDate)) {
                        failureNotifySweetDialg("Error Message", "Please select Pickup/Dropoff Date");
                        startDate.setEnabled(true);
                        startDate.setClickable(true);
                        endDate.setEnabled(true);
                        endDate.setClickable(true);
                        return;
                    } else {
                        startDate.setEnabled(false);
                        startDate.setClickable(false);
                        endDate.setEnabled(false);
                        endDate.setClickable(false);
                        checkConnectivity();
                    }
                }


                break;
            case R.id.bookHour:
                if (endtime.getText().toString().equals("Dropoff Time")) {
                    failureNotifySweetDialg("Error Message", "Please select Dropoff Time");
                } else {
                    pickDate = starttime.getText().toString();
                    pickDate = Utility.convertStringIntoDataWithTime(pickDate);
                    // pickDate= Utility.convertStringIntoDataWithTimeForHour(pickDate);
                    dropDate = endtime.getText().toString();
                    dropDate = Utility.convertStringIntoDataWithTime(dropDate);

                    Date pickdateHour=null;
                    Date dropdateHour=null;

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    try {
                        pickdateHour = format.parse(pickDate);
                        dropdateHour = format.parse(dropDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long diff = dropdateHour.getTime() - pickdateHour.getTime();

                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    if(diffDays>0||diffHours>24 || diffHours<1){

                        AlertDialog alertDialog = new AlertDialog.Builder(
                                getActivity()).create();

                        // Setting Dialog Title
                        alertDialog.setTitle("Choose our Rent By Day Section!");

                        // Setting Dialog Message
                        alertDialog.setMessage("Booking here is allowed for a minimum of 1 hour and maximum of 23 hours, hourly pricing will be applicable here!");

                        // Setting Icon to Dialog
                        alertDialog.setIcon(R.drawable.tick);

                        // Setting OK Button
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();
                    }
                    else{
                        checkConnectivity();
                    }

                }


                break;
//            case R.id.bookHour:
//                if (endtime.getText().toString().equals("Dropoff Time")) {
//                    failureNotifySweetDialg("Error Message", "Please select Dropoff Time");
//                } else {
//                    pickDate= starttime.getText().toString();
//                    pickDate = Utility.convertStringIntoDataWithTime(pickDate);
//                  // pickDate= Utility.convertStringIntoDataWithTimeForHour(pickDate);
//                    dropDate = endtime.getText().toString();
//                   dropDate = Utility.convertStringIntoDataWithTimeForHour(dropDate);
//                    checkConnectivity();
//                }


            //break;

            case R.id.linearStart:

                setDataPickerData(startDate, System.currentTimeMillis(), 0);
                break;
            case R.id.linearStar:
                setDataPickerDataForHour(starttime, System.currentTimeMillis(), 0);
                //  rentHourStartTimePicker();


                break;

            case R.id.linearEnd:
                if (startDate.getText().toString().equals("Pickup Date")) {
                    failureNotifySweetDialg("Error Message", "Please select Pickup Date first !");
                    return;
                } else {
                    Date date = setMinDatForDropDate();
                    setDataPickerDataDrop(endDate, date.getTime(), 0);
                }

                break;
            case R.id.linearEn:
                if (starttime.getText().toString().equals("Pickup Time")) {
                    failureNotifySweetDialg("Error Message", "Please select Pickup Time first !");
                    return;
                } else {
                    // rentHourEndTimePicker();
                    Date date = setMinDatForDropDateForHour();
                    setDataPickerDataDropForHour(endtime, date.getTime(), 0, date);

                }

                break;
        }
    }

    private void rentHourEndTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time = selectedHour + ":" + selectedMinute;
                AppConstant.dropTime = time;
                endtime.setText(selectedHour + ":" + selectedMinute);
                endtime.setTextColor(getResources().getColor(R.color.buttonColor));
                dropofimageview_hour_drop.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);

            }
        }, hour, minute, false);//Yes 12 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void rentHourStartTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String time = selectedHour + ":" + selectedMinute;
                AppConstant.pickTime = time;
                starttime.setText(selectedHour + ":" + selectedMinute);
                starttime.setTextColor(getResources().getColor(R.color.buttonColor));
                pickUpImageView_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);

            }
        }, hour, minute, false);//Yes 12 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private Date setMinDatForDropDate() {
        Date date = null;
//        String pick = Utility.convertStringIntoData1(startDate.getText().toString());
        String startdate = startDate.getText().toString();
        String pick = Utility.convertStringIntoData(startdate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(pick);

//            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
//            date = dateFormat1.parse(pick);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private Date setMinDatForDropDateForHour() {
        Date date = null;
//        String pick = Utility.convertStringIntoData1(startDate.getText().toString());
        String startdate = starttime.getText().toString();
        String pick = Utility.convertStringIntoData(startdate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(pick);

//            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
//            date = dateFormat1.parse(pick);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void setDataPickerData(final TextView editText, long minDate, long maxDate) {
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
                DatePickerDialog(getActivity(), new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        setTimePickerData(editText, dateFormatter.format(newDate.getTime()));
                        if (!endDate.getText().equals("Dropoff Date")) {
                            endDate.setText("Dropoff Date");
                        }

                        //editText.setText(dateFormatter.format(newDate.getTime()));

                    }
                }, mYaer, mMonth, mDay);
        mDatePickerDialog.setTitle("Select Date");
        Calendar newDate = Calendar.getInstance();
        int yaer = 1970;
        int month = 1;
        int day = 1;
        newDate.set(yaer, month, day);

        if (maxDate != 0) {
            mDatePickerDialog.getDatePicker().setMaxDate(maxDate);
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 90);
        Date d1 = c.getTime();

        mDatePickerDialog.getDatePicker().setMaxDate(d1.getTime());
        mDatePickerDialog.getDatePicker().setMinDate(minDate);
        mDatePickerDialog.show();

    }

    private void setDataPickerDataForHour(final TextView editText, long minDate, long maxDate) {
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
                DatePickerDialog(getActivity(), new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        setTimePickerDataForHour(editText, dateFormatter.format(newDate.getTime()));
                        if (!endtime.getText().equals("Dropoff Time")) {
                            endtime.setText("Dropoff Time");
                        }

                        //editText.setText(dateFormatter.format(newDate.getTime()));

                    }
                }, mYaer, mMonth, mDay);
        mDatePickerDialog.setTitle("Select Date");
        Calendar newDate = Calendar.getInstance();
        int yaer = 1970;
        int month = 1;
        int day = 1;
        newDate.set(yaer, month, day);

        if (maxDate != 0) {
            mDatePickerDialog.getDatePicker().setMaxDate(maxDate);
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 90);
        Date d1 = c.getTime();

        mDatePickerDialog.getDatePicker().setMaxDate(d1.getTime());
        mDatePickerDialog.getDatePicker().setMinDate(minDate);
        mDatePickerDialog.show();

    }

    private void setDataPickerDataDrop(final TextView editText, long minDate, long maxDate) {
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
                DatePickerDialog(getActivity(), new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        setTimePickerDataDrop(editText, dateFormatter.format(newDate.getTime()));
                        //editText.setText(dateFormatter.format(newDate.getTime()));

                    }
                }, mYaer, mMonth, mDay);
        mDatePickerDialog.setTitle("Select Date");
        Calendar newDate = Calendar.getInstance();
        int yaer = 1970;
        int month = 1;
        int day = 1;
        newDate.set(yaer, month, day);


//        if(maxDate != 0){
//            mDatePickerDialog.getDatePicker().setMaxDate(maxDate);
//        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(setMinDatForDropDate());
        cal.add(Calendar.DATE, 1);
        Date minimumDate = cal.getTime();
        mDatePickerDialog.getDatePicker().setMinDate(minimumDate.getTime());


        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 90);
        Date d1 = c.getTime();
        mDatePickerDialog.getDatePicker().setMaxDate(d1.getTime());

        mDatePickerDialog.show();

    }

    private void setDataPickerDataDropForHour(final TextView editText, long minDate, long maxDate, Date dateforNext) {
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
                DatePickerDialog(getActivity(), new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int month, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        setTimePickerDataDropForHour(editText, dateFormatter.format(newDate.getTime()));
                        //editText.setText(dateFormatter.format(newDate.getTime()));

                    }
                }, mYaer, mMonth, mDay);
        mDatePickerDialog.setTitle("Select Date");
        Calendar newDate = Calendar.getInstance();
        int yaer = 1970;
        int month = 1;
        int day = 1;
        newDate.set(yaer, month, day);


//        if(maxDate != 0){
//            mDatePickerDialog.getDatePicker().setMaxDate(maxDate);
//        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(setMinDatForDropDateForHour());
        cal.add(Calendar.DATE, 0);
        Date minimumDate = cal.getTime();
        mDatePickerDialog.getDatePicker().setMinDate(minimumDate.getTime());


        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 90);
        Date d1 = c.getTime();
        mDatePickerDialog.getDatePicker().setMaxDate(d1.getTime());

        mDatePickerDialog.show();

    }

    private void setTimePickerData(final TextView editText, final CharSequence date) {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        final int mSec = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String time = hourOfDay + ":" + minute;
                        AppConstant.pickTime = time;
                        editText.setText(date + " " + time);
                        editText.setTextColor(getResources().getColor(R.color.buttonColor));
                        pickUpImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void setTimePickerDataForHour(final TextView editText, final CharSequence date) {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        final int mSec = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String time = hourOfDay + ":" + minute;
                        AppConstant.pickTime = time;
                        editText.setText(date + " " + time);
                        editText.setTextColor(getResources().getColor(R.color.buttonColor));
                        pickUpImageView_hour.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);


                    }
                }, mHour, mMinute, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private void setTimePickerDataDrop(final TextView editText, final CharSequence date) {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        final int mSec = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String time = hourOfDay + ":" + minute;
                        AppConstant.dropTime = time;
                        view.setIs24HourView(false);
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(date);
                        buffer.append(" ");
                        buffer.append(time);

                        //  editText.setText(date + " " + time);
                        editText.setText(buffer.toString());
                        editText.setTextColor(getResources().getColor(R.color.buttonColor));
                        dropOfImageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);

                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    private void setTimePickerDataDropForHour(final TextView editText, final CharSequence date) {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        final int mSec = c.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String time = hourOfDay + ":" + minute;
                        AppConstant.dropTime = time;
                        view.setIs24HourView(false);
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(date);
                        buffer.append(" ");
                        buffer.append(time);


                        // editText.setText(date + " " + time);
                        editText.setText(buffer.toString());
                        editText.setTextColor(getResources().getColor(R.color.buttonColor));
                        dropofimageview_hour_drop.setColorFilter(ContextCompat.getColor(getActivity(), R.color.buttonColor), android.graphics.PorterDuff.Mode.MULTIPLY);

                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    private void checkConnectivity() {

        if (ConnectionDetector.checkConnection(getActivity())) {
            fetchCarData();
        } else {
            startDate.setEnabled(true);
            startDate.setClickable(true);
            endDate.setEnabled(true);
            endDate.setClickable(true);
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
            Utils.hideProgressBar(progressIndicatorView);
        }
    }


    private void fetchCarData() {
        Utils.showProgressBar(progressIndicatorView);

        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.RENTAL_LIST);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("start", pickDate);
        hashMap.put("end", dropDate);
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }


    public void updateDataonOnNetworkResponse(int eventType, ESNetworkResponse networkResponse) {

        Utils.hideProgressBar(progressIndicatorView);

        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.RENTAL_LIST:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;
                    ArrayList<CarRentalListModel> rentalDataModelArrayList = networkResponse.rentallistModelArrayList;
                    if (rentalDataModelArrayList != null && rentalDataModelArrayList.size() > 0) {
                        startDate.setEnabled(false);
                        startDate.setClickable(false);
                        endDate.setEnabled(false);
                        endDate.setClickable(false);
                        startDate1 = networkResponse.startDate;
                        endDate1 = networkResponse.endDate;
                        Intent intent = new Intent(getActivity(), CarByDayActivity.class);
                        intent.putParcelableArrayListExtra("RentalCarData", rentalDataModelArrayList);
                        intent.putExtra("Start Date", startDate1);
                        intent.putExtra("End Date", endDate1);
                        startActivity(intent);
                       // getActivity().finish();

                    }

                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                    startDate.setEnabled(true);
                    startDate.setClickable(true);
                    endDate.setEnabled(true);
                    endDate.setClickable(true);
                    String responseMessage = networkResponse.responseMessage;
                    failureSweetDialg("Failure", responseMessage);

                } else {
                    startDate.setEnabled(true);
                    startDate.setClickable(true);
                    endDate.setEnabled(true);
                    endDate.setClickable(true);
                    String responseMessage = networkResponse.responseMessage;
                    failureSweetDialg("Failure", responseMessage);
                }
                break;


        }
    }

    public void show_Alert_For_rent_By_Day()
    {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Rent By day Info!");
        dialog.setContentView(R.layout.information);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);


        // if button is clicked, close the custom dialog
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    public void show_Alert_For_rent_By_Hour()
    {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Rent By Hour Info!");
        dialog.setContentView(R.layout.information_hour);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);


        // if button is clicked, close the custom dialog
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

}
