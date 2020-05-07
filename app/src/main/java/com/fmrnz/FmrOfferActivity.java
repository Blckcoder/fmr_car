package com.fmrnz;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.android.volley.toolbox.ImageLoader;
import com.fmrnz.adapter.FmrOfferAdapter;
import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.FmrOfferModel;

import java.util.ArrayList;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

public class FmrOfferActivity extends BaseActivity {

    RecyclerView recyclerView;

    ImageLoader imageLoader;
    AVLoadingIndicatorView progressIndicatorView;

    ArrayList<FmrOfferModel> articleDataArrayList;

    FmrOfferAdapter articleAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmr_offer);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        progressIndicatorView = (AVLoadingIndicatorView)findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));



        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        imageLoader = networkController.getImageLoader();
        checkConnectivity();
    }

    private void checkConnectivity(){
        if(ConnectionDetector.checkConnection(this)){
            fetchArticelData();
        }
        else{
            failureSweetDialgForNoConnection("No Internet Connection","Please check your Internet Connection");
            Utils.hideProgressBar(progressIndicatorView);
        }
    }


    private void fetchArticelData(){
        Utils.showProgressBar(progressIndicatorView);
        ESAppRequest esLoginRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.FMR_OFFERS);
        networkController.sendNetworkRequest(esLoginRequest);
    }

    public void handleNetworkEvent(int eventType , ESNetworkResponse networkResponse) {
        Utils.hideProgressBar(progressIndicatorView);

        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.FMR_OFFERS:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;

                    articleDataArrayList = networkResponse.fmrofferlistModelArrayList;
                    if (articleDataArrayList != null && articleDataArrayList.size() > 0) {
//                        Constants.articleLists = articleDataArrayList;
                        articleAdapter = new FmrOfferAdapter(this, articleDataArrayList, imageLoader);

                        recyclerView.setAdapter(articleAdapter);

                    }
                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.FAILURE) {

                }
                break;

        }
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
