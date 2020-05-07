package com.fmrnz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fmrnz.adapter.ViewPagerAdapter;
import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.model.CarRentalListModel;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

/**
 * Created by eurysinfosystems on 02/05/18.
 */

public class CarRentalDeatilActivity extends BaseActivity  implements ObservableScrollViewCallbacks, View.OnClickListener {

    CarRentalListModel carRentalListModel;
    ImageLoader imageLoader;
    ViewPager viewPager;
    LinearLayout sliderDotspanel,relativeLayout11,relativeLayout21,relativeLayout31,relativeLayout41;
    private int dotscount;
    private ImageView[] dots;
    private String[] imageArray;
    NetworkImageView userrImageView;
    Button bookNow;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mParallaxImageHeight;
    String startDate,endDate;
    String carID;
    RatingBar ratingBar;
    AVLoadingIndicatorView progressIndicatorView;
    TextView ratingBarTV,engine12,distance,carName,year,petrol1,automatic1,seat1,hour1,daily1,week1,carType,carModel,carMake,regYear,consumption,doors,odometer,diver,features,desc,ownerName,gender1,ratingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        setActionBar();
        fetchIntentData();
        setUpView();
        setUpListeners();
        setUpData();
        setUpViewPagerData();
        //setRating();
    }

    private void setActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.bacground_gradient));
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    private void fetchIntentData(){
        carID = getIntent().getStringExtra("carID");
        carRentalListModel = (CarRentalListModel) getIntent().getParcelableExtra("RentalData");
        startDate = getIntent().getStringExtra("Start Date");
        endDate = getIntent().getStringExtra("End Date");
        imageLoader = networkController.getImageLoader();
        ImageLoader imageLoader = networkController.getImageLoader();

    }

    private void setUpView(){
        mToolbarView = findViewById(R.id.toolbar);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));
        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
        progressIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));
        ratingBarTV = (TextView)findViewById(R.id.ratingBarTextView);
        ratingBar = (RatingBar)findViewById(R.id.carRatingBar);
         carName = (TextView) findViewById(R.id.carName);
         year = (TextView) findViewById(R.id.year);
         petrol1 = (TextView) findViewById(R.id.petrol1);
         automatic1 = (TextView) findViewById(R.id.automatic1);
         seat1 = (TextView) findViewById(R.id.seat1);
         hour1 = (TextView) findViewById(R.id.hour1);
         daily1 = (TextView) findViewById(R.id.daily1);
         week1 = (TextView) findViewById(R.id.week1);
         carType = (TextView) findViewById(R.id.carType);
         carModel = (TextView) findViewById(R.id.carModel);
         carMake = (TextView) findViewById(R.id.carMake);
         regYear = (TextView) findViewById(R.id.regYear);
         consumption = (TextView) findViewById(R.id.consumption);
         doors = (TextView) findViewById(R.id.doors);
         odometer = (TextView) findViewById(R.id.odometer);
         diver = (TextView) findViewById(R.id.diver);
         features = (TextView) findViewById(R.id.features);
         desc = (TextView) findViewById(R.id.desc);
         ownerName = (TextView) findViewById(R.id.ownerName);
         gender1 = (TextView) findViewById(R.id.gender1);
        engine12 = (TextView)findViewById(R.id.engine12);
        distance = (TextView)findViewById(R.id.distance);
        userrImageView =(NetworkImageView) findViewById(R.id.userrImageView);
        bookNow = (Button) findViewById(R.id.bookNow);
        relativeLayout11 = (LinearLayout)findViewById(R.id.relativelayout11);
        relativeLayout21 = (LinearLayout)findViewById(R.id.relativeLayout21);

        relativeLayout31 = (LinearLayout)findViewById(R.id.relativeLayout31);

        relativeLayout41 = (LinearLayout)findViewById(R.id.relativeLayout41);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
    }
    private void setUpListeners(){
        bookNow.setOnClickListener(this);
        userrImageView.setOnClickListener(this);
    }
    private void setUpData(){
        carName.setText(carRentalListModel.getCar_name());
        year.setText(carRentalListModel.getRegistration_year());
//        petrol1.setText(carRentalListModel.getFuel_type());
//        automatic1.setText(carRentalListModel.getCar_type());
//        seat1.setText(carRentalListModel.getCar_seater());
        hour1.setText(carRentalListModel.getHourly_price());
        daily1.setText(carRentalListModel.getDaily_price());
        week1.setText(carRentalListModel.getWeekly_price());
        carType.setText(carRentalListModel.getVehicle_type());
        carModel.setText(carRentalListModel.getVehicle_model());
        carMake.setText(carRentalListModel.getVehicle_make());
        regYear.setText(carRentalListModel.getRegistration_year());
        consumption.setText(carRentalListModel.getConsumption());
        doors.setText(carRentalListModel.getDoors());
        odometer.setText(carRentalListModel.getOdometer());
        diver.setText(carRentalListModel.getDrive_train());
        features.setText(carRentalListModel.getFeature());
        ownerName.setText(carRentalListModel.getOwner_name());
//        engine12.setText(carRentalListModel.getEngine());
        desc.setText(carRentalListModel.getDescription());
        distance.setText(String.format("%.2f", carRentalListModel.getDistanceBetweenCar()) + " KM");

        gender1.setText(carRentalListModel.getOwner_type());

        if(carRentalListModel.getOwner_type() != null) {
            if (carRentalListModel.getOwner_type().equalsIgnoreCase("male") ) {
                gender1.setTextColor(Color.parseColor("#21b9f4"));
            }
            else if (carRentalListModel.getOwner_type().equalsIgnoreCase("female")){
                gender1.setTextColor(Color.parseColor("#f2226d"));

            }
            else{
                gender1.setTextColor(Color.parseColor("#4da94d"));
            }

        }

        String petrol = carRentalListModel.getFuel_type();
        if(!TextUtils.isEmpty(petrol)){
            petrol1.setText(petrol);
        }else{
            //nameTV.setText("NA");
            relativeLayout11.setVisibility(View.GONE);
        }

        String auto = carRentalListModel.getTransmission();
        if(!TextUtils.isEmpty(auto)){
            automatic1.setText(auto);
        }else{
            //nameTV.setText("NA");
            relativeLayout21.setVisibility(View.GONE);
        }

        String seat = carRentalListModel.getCar_seater();
        if(!TextUtils.isEmpty(seat)){
            seat1.setText(seat);
        }else{
            //nameTV.setText("NA");
            relativeLayout31.setVisibility(View.GONE);
        }

        String engine = carRentalListModel.getEngine();
        if(!TextUtils.isEmpty(engine)){
            engine12.setText(engine);
        }else{
            //nameTV.setText("NA");
            relativeLayout41.setVisibility(View.GONE);
        }


        userrImageView.setImageUrl(carRentalListModel.getOwner_image(), imageLoader);
//        ratingBar.setRating(Float.parseFloat(carRentalListModel.getRating()));
//        ratingBarTV.setText(carRentalListModel.getRating()+"/5");

        if (!TextUtils.isEmpty(carRentalListModel.getRating())) {
            ratingBar.setRating(Float.parseFloat(carRentalListModel.getRating()));

        }

        if (!TextUtils.isEmpty(carRentalListModel.getRating())) {
            ratingBarTV.setText(carRentalListModel.getRating());
            ratingBarTV.setText(carRentalListModel.getRating()+"/5");

        }


//        if(!TextUtils.isEmpty(carRentalListModel.getRating())){
//            holder.ratingBar.setVisibility(View.VISIBLE);
//            holder.ratingTextView.setVisibility(View.VISIBLE);
//            holder.ratingBar.setRating(Float.parseFloat(carRentalListModel.getRating()));
//            holder.ratingTextView.setText(carRentalListModel.getRating()+"/5");
//        }
//        else{
//            holder.ratingBar.setVisibility(View.INVISIBLE);
//            holder.ratingTextView.setVisibility(View.VISIBLE);
//            holder.ratingTextView.setText("No Rating Available");
//        }

    }

    private void setUpViewPagerData(){
//        imageArray = new String[10];
        String image1 = carRentalListModel.getCar_image1();
        String image2 = carRentalListModel.getCar_image2();
        String image3 = carRentalListModel.getCar_image3();
        String image4 = carRentalListModel.getCar_image4();
        String image5 = carRentalListModel.getCar_image5();
        String image6 = carRentalListModel.getCar_image6();
        String image7 = carRentalListModel.getCar_image7();
        String image8 = carRentalListModel.getCar_image8();
        String image9 = carRentalListModel.getCar_image9();
        String image10 = carRentalListModel.getCar_image10();
        ArrayList<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(image1)){
            list.add(image1);
        }
        if (!TextUtils.isEmpty(image2)){
            list.add(image2);
        }
        if (!TextUtils.isEmpty(image3)){
            list.add(image3);
        }
        if (!TextUtils.isEmpty(image4)){
            list.add(image4);
        }
        if (!TextUtils.isEmpty(image5)){
            list.add(image5);
        }
        if (!TextUtils.isEmpty(image6)){
            list.add(image6);
        }
        if (!TextUtils.isEmpty(image7)){
            list.add(image7);
        }
        if (!TextUtils.isEmpty(image8)){
            list.add(image8);
        }
        if (!TextUtils.isEmpty(image9)){
            list.add(image9);
        }
        if (!TextUtils.isEmpty(image10)){
            list.add(image10);
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, list, imageLoader);
        viewPager.setAdapter(viewPagerAdapter);
//        dotscount = viewPagerAdapter.getCount();
        dotscount = list.size();
        dots = new ImageView[dotscount];
        for (int j = 0; j < dotscount; j++) {

            dots[j] = new ImageView(this);
            dots[j].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[j], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//        private void setRating(){
//        Utils.showProgressBar(progressIndicatorView);
//        if (ConnectionDetector.checkConnection(context)) {
//            ESAppRequest mgCartRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.FETCH_RATE);
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put("car_id",  carRentalListModel.getCar_id());
//            mgCartRequest.requestMap = hashMap;
//            networkController.sendNetworkRequest(mgCartRequest);
//        }
//        else{
//
//        }
//    }

//    public void handleNetworkEvent(int eventType, ESNetworkResponse networkResponse) {
//        Utils.hideProgressBar(progressIndicatorView);
//        switch (eventType) {
//            case ESNetworkRequest.NetworkEventType.FETCH_RATE:
//                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
//                    String resultRespose = networkResponse.responseMessage;
//                    if(!TextUtils.isEmpty(resultRespose)){
//                        ratingBar.setRating(Float.parseFloat(resultRespose));
//                        ratingBarTV.setText(resultRespose+"/5");
//                    }
//                }
//                else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
//                    String responseMessage = networkResponse.responseMessage;
//                    failureSweetDialg("Failure", responseMessage);
//                } else {
//                    String responseMessage = networkResponse.responseMessage;
//                    failureSweetDialg("Failure", responseMessage);
//                }
//
//        }
//    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) scrollY / mParallaxImageHeight);
        mToolbarView.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(viewPager, scrollY / 2);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bookNow:
                Intent intent = new Intent(CarRentalDeatilActivity.this,BookingActivity.class);
                intent.putExtra("RentalData",carRentalListModel);
                intent.putExtra("Start Date",startDate);
                intent.putExtra("End Date",endDate);
                intent.putExtra("carID",carID);
                startActivity(intent);

                break;
            case R.id.userrImageView:
                Intent intent1 = new Intent(CarRentalDeatilActivity.this, DialogActivity.class);
                String carID = carRentalListModel.getCar_id();
                intent1.putExtra("RentalData",carRentalListModel);
                intent1.putExtra("Start Date",startDate);
                intent1.putExtra("End Date",endDate);
                intent1.putExtra("carID",carID);
                startActivity(intent1);
        }
    }
}
