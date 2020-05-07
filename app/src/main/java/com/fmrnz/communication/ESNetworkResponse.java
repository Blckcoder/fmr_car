package com.fmrnz.communication;


import com.fmrnz.model.BookingModel;
import com.fmrnz.model.CarBookingModel;
import com.fmrnz.model.CarRentalListModel;
import com.fmrnz.model.FmrCouponModel;
import com.fmrnz.model.FmrModel;
import com.fmrnz.model.FmrOfferModel;
import com.fmrnz.model.ForgetModel;
import com.fmrnz.model.LicenceModel;
import com.fmrnz.model.RideDetailModel;
import com.fmrnz.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Upen on 13/04/17.
 */
public class ESNetworkResponse {


    public interface ResponseCode {
        int ERROR = -101;
        int SUCCESS = ERROR + 1;
        int FAILURE = SUCCESS + 1;
        int NODATA = -300;


    }


    public String stringResponse;
    public HashMap responseMap;
    public int responseCode;
    public String responseMessage;
    public UserModel userModel;

    public String errorMessage;
    public String startDate,endDate,licenceID,bookingID;



    public ArrayList<CarRentalListModel> rentallistModelArrayList;
    public ArrayList<BookingModel> prebookinglistModelArrayList;
    public ArrayList<BookingModel> postbookinglistModelArrayList;

    public ArrayList<RideDetailModel>ridelistModelArrayList;
    public ArrayList<ForgetModel>forgetlistModelArrayList;
    public ArrayList<CarBookingModel>carBookingModelArrayList;
    public ArrayList<FmrModel>fmrlistModelArrayList;
    public ArrayList<FmrOfferModel>fmrofferlistModelArrayList;
    public ArrayList<FmrCouponModel>fmrcouponlistModelArrayList;
    public ArrayList<LicenceModel>licenceModelArrayList;




}
