package com.fmrnz.communication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fmrnz.BaseActivity;
import com.fmrnz.model.BookingModel;
import com.fmrnz.model.CarBookingModel;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.model.FmrCouponModel;
import com.fmrnz.model.FmrModel;
import com.fmrnz.model.FmrOfferModel;
import com.fmrnz.model.ForgetModel;
import com.fmrnz.model.RideDetailModel;
import com.fmrnz.model.UserModel;

import java.util.ArrayList;

/**
 * Created by upen on 13/04/17.
 */

public class ESNetworkController {

    private static ESNetworkController networkInstance;

    public boolean isInitialized = false;
    public boolean isLoggedIn;

    public RequestQueue responseText;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private BaseActivity currentUI;
    public UserModel userModel;

    public String successResponse;
    public String userId;

//    public ArrayList<ArticleListModel> articlelistDataArrayList;

    public ArrayList<CarRentalListModel> rentallistDataArrayList;
    public ArrayList<BookingModel> bookinglistDataArrayList;
    public ArrayList<RideDetailModel>ridelistDataArrayList;
    public ArrayList<ForgetModel>forgetlistDataArrayList;
    public ArrayList<CarBookingModel>supportlistDataArrayList;
    public ArrayList<FmrModel>fmrlistDataArrayList;
    public ArrayList<FmrOfferModel>fmrofferlistDataArrayList;
    public ArrayList<FmrCouponModel>fmrcouponlistDataArrayList;







    public static ESNetworkController getInstance() {
        if (networkInstance == null) {
            networkInstance = new ESNetworkController();
        }
        return networkInstance;
    }


    public void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new ISIDLruBitmapCache(ISIDLruBitmapCache.getCacheSize(context)));

        isInitialized = true;
        isLoggedIn = false;
    }

    public void setCurrentUI(BaseActivity uiPtr) {
        currentUI = uiPtr;
    }

    public ESNetworkRequest getNetworkRequestInstance(int eventType) {
        ESNetworkRequest networkRequest = null;

        switch (eventType) {
            case ESNetworkRequest.NetworkEventType.LOGIN:
            case ESNetworkRequest.NetworkEventType.SIGNUP:
            case ESNetworkRequest.NetworkEventType.RENTAL_LIST:
            case ESNetworkRequest.NetworkEventType.BOOKING:
            case ESNetworkRequest.NetworkEventType.LICENCE_DETAIL:
            case ESNetworkRequest.NetworkEventType.CONFIRM_BOOKING:
            case ESNetworkRequest.NetworkEventType.ON_GOING_RIDE:
            case ESNetworkRequest.NetworkEventType.UPCOMING_FRAGMNET:
            case ESNetworkRequest.NetworkEventType.FINISHED_FRAGMENT:
            case ESNetworkRequest.NetworkEventType.END_TRIP:
            case ESNetworkRequest.NetworkEventType.END_OTP:
            case ESNetworkRequest.NetworkEventType.ADD_RATE:
            case ESNetworkRequest.NetworkEventType.UPDATE_PROFILE:
            case ESNetworkRequest.NetworkEventType.FETCH_RATE:
            case ESNetworkRequest.NetworkEventType.START_TRIP:
            case ESNetworkRequest.NetworkEventType.START_OTP:
            case ESNetworkRequest.NetworkEventType.SENT_INVITES:
            case ESNetworkRequest.NetworkEventType.CANCEL_RIDE:
            case ESNetworkRequest.NetworkEventType.CANCELLED_FRAGMENT:
            case ESNetworkRequest.NetworkEventType.FORGET_PASSWORD:
            case ESNetworkRequest.NetworkEventType.RESET_PASSWORD:
            case ESNetworkRequest.NetworkEventType.SUPPORT:
            case ESNetworkRequest.NetworkEventType.FMR_POINTS:
            case ESNetworkRequest.NetworkEventType.ALL_BOOKINGID:
            case ESNetworkRequest.NetworkEventType.LICENCE_UPDATE:
            case ESNetworkRequest.NetworkEventType.FMR_OFFERS:
            case ESNetworkRequest.NetworkEventType.FEEDBACK_OTP:
            case ESNetworkRequest.NetworkEventType.COUPON:
            case ESNetworkRequest.NetworkEventType.PAYMENT_INITIAL:





            case ESNetworkRequest.NetworkEventType.FILTER_CAR:
                networkRequest = new ESAppRequest();
                break;

        }
        networkRequest.networkEventType = eventType;
        return networkRequest;
    }


    public void sendNetworkRequest(ESNetworkRequest networkRequest) {

        Request request = networkRequest.getNetworkRequest(networkRequest.networkEventType);
        requestQueue.add(request);
    }


    public void notifyUI(int eventType, ESNetworkResponse networkResponse) {
        currentUI.handleNetworkEvent(eventType, networkResponse);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }


}

