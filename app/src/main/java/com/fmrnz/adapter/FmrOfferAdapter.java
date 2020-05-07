package com.fmrnz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fmrnz.R;
import com.fmrnz.model.FmrOfferModel;

import java.util.ArrayList;

/**
 * Created by Upen on 10/04/17.
 */

public class FmrOfferAdapter extends RecyclerView.Adapter<FmrOfferAdapter.HospitalHolder> {

    private ArrayList<FmrOfferModel> bannerModelArrayList;
    private Context context;
    private LayoutInflater layoutInflater;
    FmrOfferModel imageListModel;

    int count =0;

    ImageLoader imageLoader;




    public FmrOfferAdapter(Context context, ArrayList<FmrOfferModel>bannerModelArrayList, ImageLoader imageLoader ) {
        this.context = context;
        this.bannerModelArrayList = bannerModelArrayList;
        layoutInflater = LayoutInflater.from(context);
        this.imageLoader = imageLoader;

    }

    @Override
    public HospitalHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.fmr_offer_row, viewGroup, false);
        HospitalHolder listHolder = new HospitalHolder(mainGroup);
        return listHolder;


    }

    @Override
    public void onBindViewHolder(final HospitalHolder holder, final int position) {
        final FmrOfferModel bannerListModel = bannerModelArrayList.get(position);
        if(bannerListModel != null){



            String image = bannerListModel.getOffer_image();
            if(!TextUtils.isEmpty(image)){
                holder.networkImageView.setImageUrl(bannerListModel.getOffer_image(),imageLoader);
            }




        }
    }





    @Override
    public int getItemCount() {
        return bannerModelArrayList.size();
    }


    public static class HospitalHolder extends RecyclerView.ViewHolder  {


        NetworkImageView networkImageView;


        public HospitalHolder(View itemView) {
            super(itemView);
            networkImageView =(NetworkImageView)itemView.findViewById(R.id.teamImageView);



        }
    }
}