package com.fmrnz.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fmrnz.BookingActivity;
import com.fmrnz.CarRentalDeatilActivity;
import com.fmrnz.DialogActivity;
import com.fmrnz.R;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.utils.CircularNetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Upen on 10/04/17.
 */

public class RentalDayAdapter extends RecyclerView.Adapter<RentalDayAdapter.HospitalHolder> {

    private ArrayList<CarRentalListModel> rentalDayModelArrayList;
    ArrayList<CarRentalListModel> listModels;
    private Context context;
    private LayoutInflater layoutInflater;
    ImageLoader imageLoader;
    ImageView view;
    Bitmap bitmap;
    CarRentalListModel carRentModel;
    private boolean flag = false;
    private int rowPosition;

    RatingBar ratingBar;
    String startdate,enddate;
    SessionManager sessionManager;
    HashMap<String,String> hashMap;



    public RentalDayAdapter(Context context, ArrayList<CarRentalListModel>rentalDayModelArrayList, ImageLoader imageLoader,String startDate,String endDate ) {
        this.context = context;
        this.rentalDayModelArrayList = rentalDayModelArrayList;
        layoutInflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;
        startdate = startDate;
        enddate = endDate;
        listModels = new ArrayList<>();
        sessionManager = new SessionManager(context);
        hashMap = sessionManager.getUserDetails();


    }

    @Override
    public HospitalHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.row_car_list, viewGroup, false);
        HospitalHolder listHolder = new HospitalHolder(mainGroup);
        return listHolder;
    }


    @Override
    public void onBindViewHolder(HospitalHolder holder, final int position) {
       CarRentalListModel carRentalListModel = rentalDayModelArrayList.get(position);
        rowPosition = position;
        if(carRentalListModel != null){

            holder.gender.setText(carRentalListModel.getOwner_type());
            holder.automatic.setText(carRentalListModel.getTransmission());
            holder.seat.setText(carRentalListModel.getCar_seater());
            holder.userName.setText(carRentalListModel.getOwner_name());
            holder.petrol.setText(carRentalListModel.getFuel_type());
            holder.hour.setText(carRentalListModel.getHourly_price());
            holder.daily.setText(carRentalListModel.getDaily_price());
            holder.week.setText(carRentalListModel.getWeekly_price());

            if(!TextUtils.isEmpty(carRentalListModel.getRating())){
                holder.ratingBar.setVisibility(View.VISIBLE);
                holder.ratingTextView.setVisibility(View.VISIBLE);
                holder.ratingBar.setRating(Float.parseFloat(carRentalListModel.getRating()));
                holder.ratingTextView.setText(carRentalListModel.getRating()+"/5");
            }
            else{
//                holder.ratingBar.setVisibility(View.INVISIBLE);
//                holder.ratingTextView.setVisibility(View.VISIBLE);
//                holder.ratingTextView.setText("No Rating Available");
            }



            String name = carRentalListModel.getOwner_name();
            if(!TextUtils.isEmpty(name)){
                holder.userName.setText(name);
            }
            else{
                holder.userName.setText(" ");
            }

            if(carRentalListModel.getOwner_type() != null) {
                if (carRentalListModel.getOwner_type().equalsIgnoreCase("male") ) {
                    holder.gender.setTextColor(Color.parseColor("#21b9f4"));
                }
                else if (carRentalListModel.getOwner_type().equalsIgnoreCase("female")){
                    holder.gender.setTextColor(Color.parseColor("#f2226d"));

                }
                else{
                    holder.gender.setTextColor(Color.parseColor("#4da94d"));
                }

            }

            String petrol = carRentalListModel.getFuel_type();
            if(!TextUtils.isEmpty(petrol)){
                holder.petrol.setText(petrol);
            }
            else{
//                holder.petrol.setText(" ");
                holder.relative1.setVisibility(View.GONE);

            }

            String automatic = carRentalListModel.getTransmission();
            if(!TextUtils.isEmpty(automatic)){
                holder.automatic.setText(automatic);
            }
            else{
//                holder.automatic.setText(" ");
                holder.relative2.setVisibility(View.GONE);

            }

            String seat = carRentalListModel.getCar_seater();
            if(!TextUtils.isEmpty(seat)){
                holder.seat.setText(seat);
            }
            else{
//                holder.seat.setText(" ");
                holder.relative3.setVisibility(View.GONE);

            }

            String engine = carRentalListModel.getEngine();
            if(!TextUtils.isEmpty(engine)){
                holder.textView15.setText(engine);
            }
            else{
//                holder.seat.setText(" ");
                holder.relative15.setVisibility(View.GONE);

            }

            String hour = carRentalListModel.getHourly_price();
            if(!TextUtils.isEmpty(hour)){
                holder.hour.setText(hour);
            }
            else{
                holder.hour.setText(" ");
            }

            String daily = carRentalListModel.getDaily_price();
            if(!TextUtils.isEmpty(daily)){
                holder.daily.setText(daily);
            }
            else{
                holder.daily.setText(" ");
            }

            String week = carRentalListModel.getWeekly_price();
            if(!TextUtils.isEmpty(week)){
                holder.week.setText(week);
            }
            else{
                holder.week.setText(" ");
            }

            String model1 = carRentalListModel.getRegistration_year();
            if(!TextUtils.isEmpty(model1)){
                holder.model1.setText(model1);
            }
            else{
                holder.model1.setText(" ");
            }

            String model = carRentalListModel.getCar_name();
            if(!TextUtils.isEmpty(model)){
                holder.model.setText(model);
            }
            else{
                holder.model.setText(" ");
            }

            String image = carRentalListModel.getOwner_image();
            if(!TextUtils.isEmpty(image)){
                holder.userrImageView.setImageUrl(carRentalListModel.getOwner_image(),imageLoader);
            }

            String image1 = carRentalListModel.getCar_image1();
            if(!TextUtils.isEmpty(image1)){
                Picasso.get().load(carRentalListModel.getCar_image1()).resize(300,200).into( holder.carImageView);
               // holder.carImageView.setImageUrl(carRentalListModel.getCar_image1(),imageLoader);
            }
            holder.distance.setText(String.format("%.2f", carRentalListModel.getDistanceBetweenCar()) + " KM");
           // holder.distance.setText( carRentalListModel.getDistanceBetweenCar()+ " KM");

            holder.bookNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carRentModel = rentalDayModelArrayList.get(position);
                    openDetail();
                }
            });

            holder.userrImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carRentModel = rentalDayModelArrayList.get(position);
                    openProfile();
                    
                }
            });

            holder.baseLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carRentModel = rentalDayModelArrayList.get(position);
                    openRentalDetail();

                }
            });
        }
    }

    private void openDetail(){
        Intent intent = new Intent(context, BookingActivity.class);
        String carID = carRentModel.getCar_id();
        intent.putExtra("RentalData",carRentModel);
        intent.putExtra("Start Date",startdate);
        intent.putExtra("End Date",enddate);
        intent.putExtra("carID",carID);
        context.startActivity(intent);
    }

    private void openProfile(){
        Intent intent = new Intent(context, DialogActivity.class);
        String carID = carRentModel.getCar_id();
        intent.putExtra("RentalData",carRentModel);
        intent.putExtra("Start Date",startdate);
        intent.putExtra("End Date",enddate);
        intent.putExtra("carID",carID);
        context.startActivity(intent);
    }

    private void openRentalDetail(){
        Intent intent = new Intent(context, CarRentalDeatilActivity.class);
        String carID = carRentModel.getCar_id();
        intent.putExtra("RentalData",carRentModel);
        intent.putExtra("Start Date",startdate);
        intent.putExtra("End Date",enddate);
        intent.putExtra("carID",carID);

        context.startActivity(intent);
    }






    @Override
    public int getItemCount() {
        return rentalDayModelArrayList.size();
    }


    public static class HospitalHolder extends RecyclerView.ViewHolder  {

        TextView userName,model1,model,distance,week,daily,hour,seat,automatic,gender,petrol,year1,ratingTextView,textView15;
        ImageView carImageView;
        RatingBar ratingBar;
        Button bookNowBtn;
        LinearLayout baseLayout,relative1,relative2,relative3,relative15;

        CircularNetworkImageView userrImageView;

        public HospitalHolder(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.userName);
            petrol = (TextView) itemView.findViewById(R.id.petrol);
            baseLayout = (LinearLayout)itemView.findViewById(R.id.baseLayout);

            gender = (TextView)itemView.findViewById(R.id.gender);
            automatic = (TextView) itemView.findViewById(R.id.automatic);
            seat = (TextView)itemView.findViewById(R.id.seat);
            hour = (TextView) itemView.findViewById(R.id.hour);
            daily = (TextView)itemView.findViewById(R.id.daily);
            week = (TextView) itemView.findViewById(R.id.week);
            distance = (TextView) itemView.findViewById(R.id.distance);
            model = (TextView)itemView.findViewById(R.id.model);
            model1 = (TextView) itemView.findViewById(R.id.model1);
            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar1);
            userrImageView =(CircularNetworkImageView)itemView.findViewById(R.id.userrImageView);
            carImageView =(ImageView)itemView.findViewById(R.id.carImageView);
            bookNowBtn = (Button)itemView.findViewById(R.id.bookNowButton);
            relative1 = (LinearLayout)itemView.findViewById(R.id.relativelayout1);
            relative2 = (LinearLayout)itemView.findViewById(R.id.relativelayout2);
            textView15 = (TextView)itemView.findViewById(R.id.textView15);

            relative3 = (LinearLayout)itemView.findViewById(R.id.relativelayout3);

            relative15 = (LinearLayout)itemView.findViewById(R.id.relativelayout15);

        }
    }
}
