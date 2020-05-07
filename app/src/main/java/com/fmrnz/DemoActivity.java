package com.fmrnz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmrnz.utils.AppConstant;

/**
 * Created by eurysinfosystems on 25/05/18.
 */

public class DemoActivity extends BaseActivity {

    private double userLatitude,userLongitude;

    LinearLayout llLocation;

    TextView currentLocation;

    double latitude,longitude;
    String locationAddress;
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);


        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        userLatitude = AppConstant.latitude;
        userLongitude = AppConstant.longitude;


        currentLocation = (TextView) findViewById(R.id.currentLocation);

        llLocation = (LinearLayout) findViewById(R.id.llLocation);

        setUpCurrentLocation();

    }

    private void setUpCurrentLocation(){

//        LocationAddress locationAddress = new LocationAddress();
//        locationAddress.getAddressFromLocation(AppConstant.latitude, AppConstant.longitude,
//                getApplicationContext(), new GeocoderHandler());


        gps = new GPSTracker(DemoActivity.this);
        if(gps.canGetLocation()){

            userLatitude = gps.getLatitude();
            userLongitude = gps.getLongitude();
            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(userLatitude, userLongitude,
                    getApplicationContext(), new GeocoderHandler());

        }else{

            // gps.showSettingsAlert();
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            currentLocation.setText(locationAddress);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==1000)
        {
            if(data!=null){
                Bundle bundle = data.getBundleExtra("Data");
                if(bundle != null){
                    boolean flag = bundle.getBoolean("flag");

                    if(flag){
                        String locationvalue = bundle.getString("currentLocation");
                        currentLocation.setText(locationvalue);
                        userLatitude = AppConstant.latitude;
                        userLongitude = AppConstant.longitude;
                    }
                    else{
                        String locationvalue = bundle.getString("location");
                        currentLocation.setText(locationvalue);
                        double latDouble = bundle.getDouble("Latitude");
                        double longDouble = bundle.getDouble("Longitude");
                        userLatitude = latDouble;
                        userLongitude = longDouble;
                    }

                   // fetchAllHospitaldata("5");

                }
                else{
                    currentLocation.setText(locationAddress);
                  //  fetchAllHospitaldata("5");
                }

            }
            else{
                currentLocation.setText(locationAddress);
             //   fetchAllHospitaldata("5");
            }

        }
    }

}
