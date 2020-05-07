package com.fmrnz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

import com.fmrnz.AlertView.SweetAlertDialog;
import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.fmrNetworking.AtechnosServerService;
import com.fmrnz.fmrNetworking.RetrofitRestController;
import com.fmrnz.model.RideDetailModel;
import com.fmrnz.pojo.ResponseCaRdDetail;
import com.fmrnz.pojo.ResponseToken;
import com.fmrnz.pojo.ServerResponse;
import com.fmrnz.utils.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

public class UpcomingRideDetailActivity extends BaseActivity implements View.OnClickListener {

    RideDetailModel rideDetailModel;
    TextView bookDateDrop,bookDatePick,pickLocation,dropLocation,totalfair,bookinID,owncontact,ownAboutRide,ownerAddRide,ownerGenderRide,memberRide,ownerNameRide;
    private Button startTrip,cancelTrip;
    AVLoadingIndicatorView progressIndicatorView;
    String bookingID,bookingid;
    SweetAlertDialog globalDialog;
    int numOfDays;
    public static int APP_REQUEST_CODE = 99;
    LinearLayout upArrow,downArrow,content;
    NetworkImageView userrImageView;
    ImageLoader imageLoader;
    int temp = 0;
    ACProgressFlower dialog_payment;

    private String clientToken;
    private static final int BRAINTREE_REQUEST_CODE = 4949;

    String mRateCancel;
    Dialog mDialogCancelOptions;
    Dialog mDialogCancelOptions_noOptions;
    final String[] items = {" My schedule has been changed!",
            " I have booked a ride from another provider!",
            " I have a personal reason to cancel!",
            " No call no show from vendor/car owner!",
            " The vehicle is materially different from the description of the Booked Vehicle!",
            " The vehicle has a safety, technical or mechanical fault!",
            " I have booked the ride by mistake!"};
    final ArrayList itemsSelected = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_ride_detail);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        dialog_payment = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.DKGRAY).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
        rideDetailModel = getIntent().getParcelableExtra("UpcomingData");
        bookingID = getIntent().getStringExtra("BookingID");
        loginHashMap = sessionManager.getUserDetails();
        imageLoader = networkController.getImageLoader();
        ImageLoader imageLoader = networkController.getImageLoader();
        setUpView();
        setUpListners();
        setUpData();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to Cancel the ride !: ");
        builder.setMultiChoiceItems(items, null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedItemId,
                                        boolean isSelected) {
                        if (isSelected) {
                            itemsSelected.add(selectedItemId);
                        } else if (itemsSelected.contains(selectedItemId)) {
                            itemsSelected.remove(Integer.valueOf(selectedItemId));
                        }
                    }
                })
                .setPositiveButton("Yes Cancel!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Your logic when OK button is clicked
                        mDialogCancelOptions.dismiss();
                        fetchServerResponse();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mDialogCancelOptions.dismiss();
                    }
                });
        mDialogCancelOptions = builder.create();





    }

    private void setUpView(){
//        upArrow = (LinearLayout) findViewById(R.id.upLayout);
//        downArrow = (LinearLayout) findViewById(R.id.downlayout);
//        content = (LinearLayout)findViewById(R.id.content);
//        memberRide = (TextView)findViewById(R.id.memberRide);
//        ownerGenderRide = (TextView)findViewById(R.id.ownerGenderRide);
        ownerNameRide = (TextView)findViewById(R.id.ownerNameRide);
//        ownerAddRide = (TextView)findViewById(R.id.ownerAddRide);
//        ownAboutRide = (TextView)findViewById(R.id.ownAboutRide);
//        owncontact = (TextView)findViewById(R.id.owncontact);
        bookDatePick = (TextView)findViewById(R.id.bookDatePick);
        bookDateDrop = (TextView)findViewById(R.id.bookDateDrop);
        bookinID = (TextView)findViewById(R.id.bookingID);
        totalfair = (TextView)findViewById(R.id.totalfair);
        startTrip = (Button)findViewById(R.id.startTripButton);
        cancelTrip = (Button)findViewById(R.id.cancelTripButton);
        pickLocation = (TextView)findViewById(R.id.picLocation) ;
        dropLocation = (TextView)findViewById(R.id.dropLocation);
        userrImageView = (NetworkImageView) findViewById(R.id.userrImageRide);

        progressIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));


        userrImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UpcomingRideDetailActivity.this, DialogActivity3.class);
                String carID = rideDetailModel.getCar_id();
                intent1.putExtra("RideDetail",rideDetailModel);
                intent1.putExtra("carID",carID);
                startActivity(intent1);
            }
        });
    }

    private void setUpListners(){
        startTrip.setOnClickListener(this);
        cancelTrip.setOnClickListener(this);
     //   userrImageView.setOnClickListener(this);
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
        if(rideDetailModel != null){

            if(!TextUtils.isEmpty(rideDetailModel.getBook_from())){
                bookDatePick.setText(Utility.dateTimeFormat(rideDetailModel.getBook_from()));
            }

            if(!TextUtils.isEmpty(rideDetailModel.getBook_to())){
                bookDateDrop.setText(Utility.dateTimeFormat(rideDetailModel.getBook_to()));
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
            if(!TextUtils.isEmpty(rideDetailModel.getBook_amount())){
                totalfair.setText(rideDetailModel.getBook_amount());
            }



            userrImageView.setImageUrl(rideDetailModel.getOwner_image(), imageLoader);



//            Date start = null;
//            Date end = null;
//            try {
//                start = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(rideDetailModel.getBook_from());
//                end = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(rideDetailModel.getBook_to());
//
//                long secs = (end.getTime() - start.getTime()) / 1000;
//                int totalMinutes = (int)(secs / 60);
//                long diff =  end.getTime() - start.getTime();
//                numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
//                int roundOfMinutes = numOfDays * 24 * 60;
//                if(totalMinutes == roundOfMinutes)
//                    temp = numOfDays;
//                else{
//                    double days = totalMinutes/24 * 60;
//                    if(days >= 1){
//                        temp = numOfDays + 1;
//                    }
//                    else if(days < 1){
//                        temp = 1;
//                    }
//                    else{
//                        temp = numOfDays;
//                    }
//
//                }
//
//
//            }  catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            if(temp < 7){
//                if (!TextUtils.isEmpty(rideDetailModel.getDaily_price())) {
//                    double price = Float.parseFloat(rideDetailModel.getDaily_price());
//                    double finalPrice = price * temp;
//                    totalfair.setText("$" + String.valueOf(finalPrice));
//                }
//
//            }
//            else if(temp == 7){
//                if (!TextUtils.isEmpty(rideDetailModel.getWeekly_price())) {
//                    double price = Float.parseFloat(rideDetailModel.getWeekly_price());
//                    double finalPrice = price * 1;
//                    totalfair.setText("$" + String.valueOf(finalPrice));
//                }
//            }
//
//            else if(temp > 7){
//                double dailyValue = 0,weeklyValue = 0;
//
//                int remain = temp%7;
//                int value = temp/7;
//
//                if (!TextUtils.isEmpty(rideDetailModel.getDaily_price())) {
//                    dailyValue = remain * Float.parseFloat(rideDetailModel.getDaily_price());
//                }
//
//                if (!TextUtils.isEmpty(rideDetailModel.getWeekly_price())) {
//                    weeklyValue = value * Float.parseFloat(rideDetailModel.getWeekly_price());
//                }
//                totalfair.setText("$" + String.valueOf(dailyValue+weeklyValue));
//            }
//            if(!TextUtils.isEmpty(rideDetailModel.getBook_amount())){
//                totalfair.setText("$" + rideDetailModel.getBook_amount());
//            }



//            if(!TextUtils.isEmpty(rideDetailModel.getDaily_price())){
//                float price = Float.parseFloat(rideDetailModel.getDaily_price());
//                float finalPrice = price * temp;
//                totalfair.setText("$" + String.valueOf(finalPrice));
//            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startTripButton:
                //endOTPRequest();
                checkDateAndTime();
                break;
            case R.id.cancelTripButton:
               // deleteConfirmAlertDialog();
                mDialogCancelOptions.show();
                break;
            default:
                break;
        }
    }


    private void checkDateAndTime(){
        Date currentDate = null,tripDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String currentDateandTime = sdf.format(new Date());
        String startTrip = Utility.convertStringIntoDataWithTime(bookDatePick.getText().toString());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            currentDate = format.parse(currentDateandTime);
             tripDate = format.parse(startTrip);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tripDate);
        calendar.add(Calendar.MINUTE, -30);

        Date newDate = calendar.getTime();
        System.out.println("New Date is"+String.valueOf(newDate.getTime()));



        if (currentDate.compareTo(tripDate) > 0){
            Toast.makeText(this,"You can't start the ride,as ride time has already been over",Toast.LENGTH_SHORT).show();
        }
        else{
            if(currentDate.compareTo(newDate)>=0){
                Toast.makeText(this,"You can initiate the ride",Toast.LENGTH_SHORT).show();
                endOTPRequest();
            }
            else if(currentDate.compareTo(newDate)<0){
                Toast.makeText(this,"You can start the ride once you had left 20 minutes from Start Trip Time",Toast.LENGTH_SHORT).show();
            }
        }





//        if (currentDate.compareTo(tripDate) > 0) {
//            System.out.println("Date1 is after Date2");//NO
//        } else if (currentDate.compareTo(tripDate) < 0) {
//            System.out.println("Date1 is before Date2");//OK
//        } else if (currentDate.compareTo(tripDate) == 0) {
//            System.out.println("Date1 is equal to Date2");//OK
//        } else {
//            System.out.println("How to get here?");
//        }

    }



    private void deleteConfirmAlertDialog(){
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Do you want to cancel this ride?")
                    .setCancelText("No!")
                    .setConfirmText("Yes,delete it!")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            // reuse previous dialog instance, keep widget user state, reset them if you need
                            sDialog.setTitleText("Canceled!")
                                    .setContentText("Your Ride will not canceled!")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setCancelClickListener(null)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                           // globalDialog = sDialog;



                        }
                    })
                    .show();
    }

    private void endOTPRequest(){
        Utils.showProgressBar(progressIndicatorView);
        ESAppRequest appRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.START_OTP);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("booking_id", rideDetailModel.getBooking_id());
        hashMap.put("email", rideDetailModel.getEmail());
//        hashMap.put("pay_amount", paymentAmount);
//        hashMap.put("user_id", sessionManager.getUserDetails().get(SessionManager.KEY_ID));
//        hashMap.put("pay_status", payemnetStatus);
//        hashMap.put("pay_id", payemntID);
        appRequest.requestMap = hashMap;

        networkController.sendNetworkRequest(appRequest);
    }
    private void fetchServerResponse() {
        dialog_payment.show();
        AtechnosServerService Service = RetrofitRestController.getClient().create(AtechnosServerService.class);
        Call<ServerResponse> call = Service.getServerTime();
        // TODO: 01/12/17 Below mentioned code is for aSynchronus call
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                String serverTime=response.body().getResult();
                CalculateDateAndTimeForCancel(serverTime);
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }
    private void cancelRequest(){
        Utils.showProgressBar(progressIndicatorView);


        ESAppRequest appRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.CANCEL_RIDE);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("booking_id", rideDetailModel.getBooking_id());
        appRequest.requestMap = hashMap;

        networkController.sendNetworkRequest(appRequest);
    }

    private void CalculateDateAndTimeForCancel(String serverTime) {

        Date currentDate = null,tripDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        String currentDateandTime = null;
        String startTrip=null;

        try {
            Date parsedDate = sdf.parse(serverTime);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            currentDateandTime = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date parsedDate1 = sdf1.parse(bookDatePick.getText().toString());
            SimpleDateFormat dt2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            startTrip = dt2.format(parsedDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }


     //   String startTrip = Utility.convertStringIntoDataWithTime(bookDatePick.getText().toString());

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            currentDate = format.parse(currentDateandTime);
            tripDate = format.parse(startTrip);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // d1, d2 are dates
      //  long diff = currentDate.getTime() - tripDate.getTime();
        long diff = tripDate.getTime() - currentDate.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);


        if(diffMinutes<=-1){
            Toast.makeText(this,"Your Date is extend! you can't cancel"+diffDays,Toast.LENGTH_SHORT).show();

            AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Not Canceled!");

            // Setting Dialog Message
            alertDialog.setMessage("Your Date is extend! you can't cancel");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed

                    dialog.dismiss();
                }
            });

            alertDialog.show();



            dialog_payment.hide();
            return;
        }
       if(diffDays>= 30){
           dialog_payment.hide();
           AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

           // Setting Dialog Title
           alertDialog.setTitle("Do you want to really cancel the ride!");

           // Setting Dialog Message
           alertDialog.setMessage("You don't have to charge any cost  of Total cost ride!");

           // Setting Icon to Dialog
           alertDialog.setIcon(R.drawable.tick);

           // Setting OK Button
           alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   // Write your code here to execute after dialog closed
                   cancelRequest();
                   dialog.dismiss();
               }
           });

           alertDialog.show();




        //   Toast.makeText(this,"date diffrence is"+diffDays,Toast.LENGTH_SHORT).show();
           return;

       }

       if(diffDays<30&& diffDays>= 8){
           AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

           // Setting Dialog Title
           alertDialog.setTitle("Canceled Charge!");

           // Setting Dialog Message
           alertDialog.setMessage("You have to charge 20% of Total cost ride!");

           // Setting Icon to Dialog
           alertDialog.setIcon(R.drawable.tick);

           // Setting OK Button
           alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   // Write your code here to execute after dialog closed
                   payementforRate("20");
                   dialog.dismiss();
               }
           });

           alertDialog.show();

          // Toast.makeText(this,"date diffrence is rate 20%"+diffDays,Toast.LENGTH_SHORT).show();

           return;
       }
        if(diffDays<=7&& diffDays>=5){
            AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Canceled Charge!");

            // Setting Dialog Message
            alertDialog.setMessage("You have to charge 30% of Total cost ride!");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    payementforRate("30");
                    dialog.dismiss();
                }
            });

            alertDialog.show();

          // Toast.makeText(this,"date diffrence is rate 30%"+diffDays,Toast.LENGTH_SHORT).show();

            return;
        }
        if(diffDays<=4&& diffDays>3){
            AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Canceled Charge!");

            // Setting Dialog Message
            alertDialog.setMessage("You have to charge 40% of Total cost ride!");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    payementforRate("40");
                    dialog.dismiss();
                }
            });

            alertDialog.show();

           // Toast.makeText(this,"date diffrence is rate 40%"+diffDays,Toast.LENGTH_SHORT).show();
            return;
        }

        if(diffHours<=72&& diffHours>48){
            AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Canceled Charge!");

            // Setting Dialog Message
            alertDialog.setMessage("You have to charge 50% of Total cost ride!");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    payementforRate("50");
                    dialog.dismiss();
                }
            });

            alertDialog.show();
            //payementforRate("50");
           // Toast.makeText(this,"hours diffrence is rate 50%"+diffHours,Toast.LENGTH_SHORT).show();
            return;
        }
        if(diffHours<=48&& diffHours>24){
            AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Canceled Charge!");

            // Setting Dialog Message
            alertDialog.setMessage("You have to charge 60% of Total cost ride!");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    payementforRate("60");
                    dialog.dismiss();
                }
            });

            alertDialog.show();
         //   payementforRate("60");
          //  Toast.makeText(this,"hours diffrence is rate 60%"+diffHours,Toast.LENGTH_SHORT).show();
            return;
        }
        if(diffHours<=24&& diffHours>12){
            AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Canceled Charge!");

            // Setting Dialog Message
            alertDialog.setMessage("You have to charge 70% of Total cost ride!");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    payementforRate("70");
                    dialog.dismiss();
                }
            });

            alertDialog.show();
           // payementforRate("70");
          //  Toast.makeText(this,"hours diffrence is rate 70%"+diffHours,Toast.LENGTH_SHORT).show();
            return;
        }
        if(diffHours<=12&& diffHours>0){
            AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Canceled Charge!");

            // Setting Dialog Message
            alertDialog.setMessage("You have to charge 80% of Total cost ride!");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    payementforRate("80");
                    dialog.dismiss();
                }
            });

            alertDialog.show();
           // payementforRate("80");
           // Toast.makeText(this,"hours diffrence is rate 80%"+diffHours,Toast.LENGTH_SHORT).show();
            return;
        }
        if(diffMinutes<=60 && diffMinutes>0){
            AlertDialog alertDialog = new AlertDialog.Builder(UpcomingRideDetailActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Canceled Charge!");

            // Setting Dialog Message
            alertDialog.setMessage("You have to charge 80% of Total cost ride!");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.tick);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    payementforRate("80");
                    dialog.dismiss();
                }
            });

            alertDialog.show();
            return;
           // payementforRate("80");
           // Toast.makeText(this,"hours diffrence is rate 80%"+diffHours,Toast.LENGTH_SHORT).show();
        }


    }

    private void payementforRate(String rate) {
        mRateCancel= rate;
        fetchToen();

    }

    private void fetchToen() {

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


//        DropInRequest dropInRequest = new DropInRequest().clientToken(clientToken);
//        dropInRequest.collectDeviceData(true);
//        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode == BRAINTREE_REQUEST_CODE){
//            if (RESULT_OK == resultCode){
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                String paymentNonce = result.getPaymentMethodNonce().getNonce();
//                //send to your server
//                sendPaymentNonceToServer(paymentNonce);
//            }else if(resultCode == Activity.RESULT_CANCELED){
//
//            }else {
//                Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//
//            }
        }



    }
    private void sendPaymentNonceToServer(String paymentNonce) {

        AtechnosServerService Service = RetrofitRestController.getClient().create(AtechnosServerService.class);
        String ratePenalaty =mRateCancel;
        int mReatePenalty= Integer.parseInt(ratePenalaty);
        String totalAmount= rideDetailModel.getBook_amount();
        int mTotalAmount= Integer.parseInt(totalAmount);
        int restAmountToPay= mTotalAmount*mReatePenalty/100;
        String amountToPaypal= String.valueOf(restAmountToPay);
        Call<ResponseCaRdDetail> call = Service.postTokenAndAmount(paymentNonce,amountToPaypal);
        // TODO: 01/12/17 Below mentioned code is for aSynchronus call
        call.enqueue(new Callback<ResponseCaRdDetail>() {
            @Override
            public void onResponse(Call<ResponseCaRdDetail> call, Response<ResponseCaRdDetail> response) {
                dialog_payment.hide();
                String respons=response.body().getTxnid();
                if(respons!=null){
                    cancelRequest();
                }
                Toast.makeText(UpcomingRideDetailActivity.this,"payment Success!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseCaRdDetail> call, Throwable t) {
                Toast.makeText(UpcomingRideDetailActivity.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
        Utils.hideProgressBar(progressIndicatorView);
        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.START_OTP:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;
              //      showDialogue("Success", responseMessage);
            //        successStartTrip(networkResponse.responseCode);
                    Intent intent = new Intent(UpcomingRideDetailActivity.this, OTPActivity.class);
                    String bookId = rideDetailModel.getBooking_id();
                    intent.putExtra("FinishRideData",rideDetailModel);
                    intent.putExtra("CallingFrom","Upcoming");
                    intent.putExtra("BookingId",bookId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    //  intent.putExtra("otp",networkResponse.stringResponse);
                    startActivity(intent);
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

            case ESNetworkRequest.NetworkEventType.CANCEL_RIDE:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;

                    successCancelRide(networkResponse.responseCode);
                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.ERROR) {
                    String responseMessage =
                            networkResponse.responseMessage;
                    successCancelRide(networkResponse.responseCode);

                }
                else{
                    String responseMessage = networkResponse.responseMessage;
                    successCancelRide(ESNetworkResponse.ResponseCode.FAILURE);

                }
                break;
        }
    }

    private void successStartTrip(final int responseCode){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("ResponseCode",responseCode);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void successCancelRide(final int responseCode){
        globalDialog= new SweetAlertDialog(this);
        globalDialog.show();
        globalDialog.setTitleText("Canceled!")
                .setContentText("Your Ride has been canceled successfully!")
                .setConfirmText("OK")
                .showCancelButton(false)
                .setCancelClickListener(null)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("ResponseCode",responseCode);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                        globalDialog.dismiss();

                    }
                })
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

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
