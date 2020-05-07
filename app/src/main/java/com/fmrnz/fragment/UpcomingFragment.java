package com.fmrnz.fragment;

/**
 * Created by abhiandroid on 9/10/17.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.fmrnz.ConnectionDetector;
import com.fmrnz.R;
import com.fmrnz.SharedPref.SessionManager;
import com.fmrnz.UpcomingRideDetailActivity;
import com.fmrnz.Utils;
import com.fmrnz.adapter.RideAdapter;
import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;
import com.fmrnz.communication.ESAppRequest;
import com.fmrnz.communication.ESNetworkRequest;
import com.fmrnz.communication.ESNetworkResponse;
import com.fmrnz.model.RideDetailModel;
import com.fmrnz.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView.BallPulse;

public class UpcomingFragment extends BaseFragment{


    HashMap<String, String> loginHashMap;
    RideAdapter rideAdapter;

    AVLoadingIndicatorView progressIndicatorView;

    ImageLoader imageLoader;

    RecyclerView recyclerView;
    TextView noDataTextView;
    ArrayList<RideDetailModel> rideDataModelArrayList;


    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = FragemntType.UPCOMING_FRAGMENT;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ongoing, container,
                false);

        sessionManager = new SessionManager(getActivity());

        progressIndicatorView = (AVLoadingIndicatorView) rootView.findViewById(R.id.progressLoading);
        progressIndicatorView.setType(BallPulse, getResources().getColor(R.color.colorPrimary));


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        noDataTextView = (TextView) rootView.findViewById(R.id.noOngoingData);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        checkConnectivity();


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RideDetailModel rideDetailModel = rideDataModelArrayList.get(position);
                Intent intent = new Intent(getActivity(), UpcomingRideDetailActivity.class);
                String bookId = rideDetailModel.getBook_id();
//                intent.putExtra("UpcomingData",rideDetailModel);
                intent.putExtra("UpcomingData",rideDetailModel);
                intent.putExtra("BookingID",bookId);
                startActivityForResult(intent,1000);
            }
        }));

        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK){
                int result=data.getIntExtra("ResponseCode",0);
                if(result == ESNetworkResponse.ResponseCode.SUCCESS){
                    checkConnectivity();
                }
                else{
                    failureSweetDialg("CarRentalApp", "Your ride had not been cancelled");
                }
            }

        }
    }

    private void checkConnectivity(){
        if(ConnectionDetector.checkConnection(getContext())){
            fetchOngoingData();
        }
        else{
            failureSweetDialgForNoConnection("No Internet Connection","Please check your Internet Connection");
            Utils.hideProgressBar(progressIndicatorView);
        }
    }


    private void fetchOngoingData(){
        Utils.showProgressBar(progressIndicatorView);
        ESAppRequest appRequest = (ESAppRequest) networkController.getNetworkRequestInstance(ESNetworkRequest.NetworkEventType.UPCOMING_FRAGMNET);
        HashMap<String, String> hashMap = new HashMap<String, String>();
//        hashMap.put("user_id", "1");
        hashMap.put("user_id", sessionManager.getUserDetails().get(SessionManager.KEY_ID));
        hashMap.put("status", "0");
        appRequest.requestMap = hashMap;

        networkController.sendNetworkRequest(appRequest);
    }


    public void updateDataonOnNetworkResponse1(int eventType, ESNetworkResponse networkResponse)
    {
        Utils.hideProgressBar(progressIndicatorView);
        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.UPCOMING_FRAGMNET:
                if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.SUCCESS) {
                    String responseMessage = networkResponse.responseMessage;

                    rideDataModelArrayList = networkResponse.ridelistModelArrayList;
                    if(rideDataModelArrayList != null && rideDataModelArrayList.size()  >0){
                        recyclerView.setVisibility(View.VISIBLE);
                        noDataTextView.setVisibility(View.GONE);
                        rideAdapter = new RideAdapter(getActivity(), rideDataModelArrayList, imageLoader);
                        recyclerView.setAdapter(rideAdapter);

                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        noDataTextView.setVisibility(View.VISIBLE);
                        noDataTextView.setText(responseMessage);
                    }

                } else if (networkResponse.responseCode == ESNetworkResponse.ResponseCode.NODATA) {
                    String responseMessage = networkResponse.responseMessage;
//                    failureSweetDialg("CarRentalApp", responseMessage);
                    recyclerView.setVisibility(View.GONE);
                    noDataTextView.setVisibility(View.VISIBLE);
                    noDataTextView.setText(responseMessage);


                }
                else{
                    String responseMessage = networkResponse.responseMessage;
                    recyclerView.setVisibility(View.GONE);
                    noDataTextView.setVisibility(View.VISIBLE);
                    noDataTextView.setText(responseMessage);
                }
                break;

        }
    }




    private void setViewStatus(ViewGroup vg1, int status) {
        vg1.setVisibility(status);
        //vg2.setVisibility(status);
    }








}
