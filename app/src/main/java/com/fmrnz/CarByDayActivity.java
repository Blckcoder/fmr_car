package com.fmrnz;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.toolbox.ImageLoader;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.adapter.RentalDayAdapter;
import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.model.FmrModel;
import com.fmrnz.utils.AppConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

public class CarByDayActivity extends BaseActivity {
    Handler mHandler;
    ArrayList<CarRentalListModel> rentalDataModelArrayList,tempRentalList;
    AVLoadingIndicatorView progressIndicatorView;
    ImageLoader imageLoader;
    RecyclerView recyclerView;
    MenuInflater menuInflater;
    String startDate,endDate;
    RentalDayAdapter rentalDayAdapter;
    CarRentalListModel carRentalListModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_day);
        setActionBar();
        fetchIntentData();
        setUpView();
        setUpListeners();
//        setUpData();
        if(!TextUtils.isEmpty(sessionManager.getUserDetails().get(SessionManager.KEY_ID))){
            fetchFMRPoints();
        }

            setUpData();

    }

    private void setActionBar() {
        ActionBar ab =getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
    }

    private void fetchIntentData(){
        rentalDataModelArrayList = getIntent().getParcelableArrayListExtra("RentalCarData");
        startDate = getIntent().getStringExtra("Start Date");
        endDate = getIntent().getStringExtra("End Date");
    }

    private void setUpView(){
        tempRentalList = new ArrayList<>();
        this.mHandler = new Handler();
        progressIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        imageLoader = networkController.getImageLoader();
    }

    private void setUpListeners(){
//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(CarByDayActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                CarRentalListModel carRentalListModel = rentalDataModelArrayList.get(position);
//                Intent intent = new Intent(CarByDayActivity.this, CarRentalDeatilActivity.class);
//                String carID = carRentalListModel.getCar_id();
//                intent.putExtra("RentalData",carRentalListModel);
//                intent.putExtra("Start Date",startDate);
//                intent.putExtra("End Date",endDate);
//                intent.putExtra("carID",carID);
//                startActivity(intent);
//            }
//        }));
    }

    private void setUpData(){
        tempRentalList.clear();
        for(int i=0; i<rentalDataModelArrayList.size();i++){
             carRentalListModel = rentalDataModelArrayList.get(i);
            double distance = fetchDistance();
            carRentalListModel.setDistanceBetweenCar(distance);
            tempRentalList.add(carRentalListModel);
        }

//        Collections.sort(tempRentalList, new Comparator<CarRentalListModel>() {
//            @Override
//            public int compare(CarRentalListModel carRentalListModel1, CarRentalListModel carRentalListModel2) {
//                return (int)carRentalListModel1.getDistanceBetweenCar() - (int)carRentalListModel2.getDistanceBetweenCar();
//            }
//        });



        rentalDayAdapter = new RentalDayAdapter(CarByDayActivity.this,tempRentalList,imageLoader,startDate,endDate);
        recyclerView.setAdapter(rentalDayAdapter);
    }

    private void fetchFMRPoints() {
        if (ConnectionDetector.checkConnection(context)) {
            fetchFMRPointsRequest();
        } else {
            failureSweetDialgForNoConnection("No Internet Connection", "Please check your Internet Connection");
        }
    }

    private void fetchFMRPointsRequest(){
        ESAppRequest esLoginRequest = (ESAppRequest)networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.FMR_POINTS);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user_id",sessionManager.getUserDetails().get(SessionManager.KEY_ID));
        esLoginRequest.requestMap = hashMap;
        networkController.sendNetworkRequest(esLoginRequest);
    }


    private double fetchDistance(){
        double carLatitude = 0.0;
        double carLongitude = 0.0;

        if(!carRentalListModel.getCar_lat().equals("lat") && !TextUtils.isEmpty(carRentalListModel.getCar_lat()) && carRentalListModel.getCar_lat() != null){
            carLatitude = Double.parseDouble(carRentalListModel.getCar_lat());
        }
        if(!carRentalListModel.getCar_long().equals("long") && !TextUtils.isEmpty(carRentalListModel.getCar_long()) && carRentalListModel.getCar_long() != null){
            carLongitude = Double.parseDouble(carRentalListModel.getCar_long());
        }

        double myLatitude = AppConstant.latitude;
        double myLongitude = AppConstant.longitude;
//
//        double myLatitude = Double.parseDouble("28.5355");
//        double myLongitude = Double.parseDouble("77.3910");

        Location myLocationData = new Location("");
        Location carLocationData = new Location("");

        myLocationData.setLatitude(myLatitude);
        myLocationData.setLatitude(myLongitude);

        carLocationData.setLatitude(carLatitude);
        carLocationData.setLatitude(carLongitude);

        double distanceGap = myLocationData.distanceTo(carLocationData)/1000.00;
        return distanceGap;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.main, menu);
//     //   final MenuItem item1 = menu.findItem(R.id.action_cart);
//        MenuItemCompat.setActionView(item1, R.layout.badge_layout);
//        item1.getActionView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CarByDayActivity.this.onOptionsItemSelected(item1);
//            }
//        });
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
//            case R.id.action_cart:
//                Intent intent = new Intent(this,FilterCarActivity.class);
//                startActivityForResult(intent,1000);
////                startActivity(new Intent(this, FilterCarActivity.class));
//                overridePendingTransition(R.anim.enter, R.anim.exit);
//                break;
        }
        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000)
        {
            if(data!=null){
                Bundle bundle = data.getBundleExtra("Data");
                if(bundle != null){
                    rentalDataModelArrayList = bundle.getParcelableArrayList("FilterList");
                    setUpData();
                }


            }


        }
    }

    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {

        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.FMR_POINTS:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    ArrayList<FmrModel> fmrModelsArrayList = networkResponse.fmrlistModelArrayList;
                    if (fmrModelsArrayList != null && fmrModelsArrayList.size() != 0) {
                        FmrModel fmrModel = fmrModelsArrayList.get(0);
                        AppConstant.fmrPoints = fmrModel.getPoints();
                        setUpData();
                    }
                    else{
                        AppConstant.fmrPoints = "0";
//                        failureSweetDialg("Error",networkResponse.responseMessage);
                    }
                }
                else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                    if(networkResponse.responseMessage.equals("No Points Available")){
                        AppConstant.fmrPoints = "0";
                    }
//                    failureSweetDialg("Error",networkResponse.responseMessage);

                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.FAILURE){
//                    failureSweetDialg("Error",networkResponse.responseMessage);
                    AppConstant.fmrPoints = "0";

                }
                break;
        }
    }

}
