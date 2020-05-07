package com.fmrnz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.communication.ESNetworkController;
import com.fmrnz.communication.ESNetworkResponse;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;


public class BaseActivity extends AppCompatActivity  {
    HashMap<String,String > loginHashMap;


    final Context context = this;
   public ESNetworkController networkController = ESNetworkController.getInstance();
    SessionManager sessionManager;

    private Location mylocation;
    GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    String locationAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getApplicationContext());

        if(!networkController.isInitialized)
             networkController.init(this);

        networkController.setCurrentUI(this);
//        setUpGClient();

    }


    @Override
    protected void onResume() {
        super.onResume();

        networkController.setCurrentUI(this);
    }


       public void showDialogue(String title, String msg)
       {
           //new AlertDialog.Builder(this,R.style.AlertDialog)
           new AlertDialog.Builder(this)
                   .setTitle(title)
                   .setMessage(msg)
                   .setCancelable(false)
                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   }).create().show();
       }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
    }



    public void failureSweetDialg(String title, String msg){
        new SweetAlertDialog(this)
                .setTitleText(title)
                .setContentText(msg)
                .show();
    }
//
//    public void failureNotifySweetDialg(String title, String msg){
//        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
//                .setTitleText(title)
//                .setContentText(msg)
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.dismiss();
//                        finish();
//
//                    }
//                })
//                .show();
//    }
//
//
//
    public void failureSweetDialgForNoConnection(String title, String msg){
        new SweetAlertDialog(this)
                .setTitleText(title)
                .setContentText(msg)
                .show();
    }



//    private synchronized void setUpGClient() {
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, 0, this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
//                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
//                .build();
//        googleApiClient.connect();
//    }
//
//
//
//    @Override
//    public void onLocationChanged(Location location) {
//        mylocation = location;
//        if (mylocation != null) {
//            double latitude=mylocation.getLatitude();
//            double longitude=mylocation.getLongitude();
//            AppConstant.latitude = latitude;
//            AppConstant.longitude = longitude;
//            LocationAddress locationAddress = new LocationAddress();
//            locationAddress.getAddressFromLocation(latitude, longitude,
//                    getApplicationContext(), new GeocoderHandler());
//            //Or Do whatever you want with your location
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    private class GeocoderHandler extends Handler {
//        @Override
//        public void handleMessage(Message message) {
//
//            switch (message.what) {
//                case 1:
//                    Bundle bundle = message.getData();
//                    locationAddress = bundle.getString("address");
//                    AppConstant.user_current_location = locationAddress;
//                    break;
//                default:
//                    locationAddress = null;
//            }
//            AppConstant.user_current_location = locationAddress;
//        }
//    }

   /* @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Do whatever you need
        //You can display a message here
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //You can display a message here
    }

    private void getMyLocation(){
        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(BaseActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(BaseActivity.this,
                                                    android.Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(BaseActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case REQUEST_CHECK_SETTINGS_GPS:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        getMyLocation();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        finish();
//                        break;
//                }
//                break;
//        }
//    }
//
    private void checkPermissions(){
        int permissionLocation = ContextCompat.checkSelfPermission(BaseActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }else{
            getMyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(BaseActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }*/




    public void suuccessSweetDialgofForSuccess(String title, String msg){
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finish();

                    }
                })
                .show();
    }

}
