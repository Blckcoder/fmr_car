package com.fmrnz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fmrnz.R;
import com.fmrnz.model.RideDetailModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eurysinfosystems on 23/05/18.
 */

public class RideAdapter extends RecyclerView.Adapter<RideAdapter.HospitalHolder> {

    private ArrayList<RideDetailModel> rideModelArrayList;
    private Context context;
    private LayoutInflater layoutInflater;
    ImageLoader imageLoader;


    public RideAdapter(Context context, ArrayList<RideDetailModel> rideModelArrayList, ImageLoader imageLoader) {
        this.context = context;
        this.rideModelArrayList = rideModelArrayList;
        layoutInflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;

    }

    @Override
    public HospitalHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.ride_row, viewGroup, false);
        HospitalHolder listHolder = new HospitalHolder(mainGroup);
        return listHolder;
    }

    @Override
    public void onBindViewHolder(HospitalHolder holder, int position) {
        final RideDetailModel rideListModel = rideModelArrayList.get(position);
        if (rideListModel != null) {


            String desc = (convertStringIntoData(rideListModel.getBook_from()) + " to " + convertStringIntoData(rideListModel.getBook_to()));
            if (!TextUtils.isEmpty(desc)) {
                holder.newsHeader.setText(desc);
            } else {
                holder.newsHeader.setText(" ");
            }

            String desc1 = "Booking ID : " + rideListModel.getBooking_id();
            if (!TextUtils.isEmpty(desc1)) {
                holder.newsHeader2.setText(desc1);
            } else {
                holder.newsHeader2.setText(" ");
            }



        }
    }

    private String convertStringIntoData(String dateStr) {
        String date = null;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
    public int getItemCount() {
        return rideModelArrayList.size();
    }


    public static class HospitalHolder extends RecyclerView.ViewHolder {

        TextView hlsList, newsHeader,newsHeader2;
        NetworkImageView networkImageView;


        public HospitalHolder(View itemView) {
            super(itemView);
            newsHeader = (TextView) itemView.findViewById(R.id.newsHeader);
            newsHeader2 = (TextView) itemView.findViewById(R.id.newsHeader2);

            //   desc = (TextView)itemView.findViewById(R.id.heading);


        }
    }
}