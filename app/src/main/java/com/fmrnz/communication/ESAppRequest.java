package com.fmrnz.communication;


import com.android.volley.Request;
import com.android.volley.Response;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by UPEN on 13/04/17.
 */
public class ESAppRequest extends ESNetworkRequest {
    @Override
    Request getNetworkRequest(int eventType) {
        Request request = null;
        url = getUrl();
        switch (networkEventType) {

            case NetworkEventType.LOGIN:
            case NetworkEventType.SIGNUP:
            case NetworkEventType.RENTAL_LIST:
            case NetworkEventType.BOOKING:
            case NetworkEventType.CONFIRM_BOOKING:
            case NetworkEventType.ON_GOING_RIDE:
            case NetworkEventType.UPCOMING_FRAGMNET:
            case NetworkEventType.FINISHED_FRAGMENT:
            case NetworkEventType.END_TRIP:
            case NetworkEventType.END_OTP:
            case NetworkEventType.ADD_RATE:
//            case NetworkEventType.UPDATE_PROFILE:
            case NetworkEventType.FETCH_RATE:
            case NetworkEventType.START_TRIP:
            case NetworkEventType.START_OTP:
            case NetworkEventType.SENT_INVITES:
            case NetworkEventType.FILTER_CAR:
            case NetworkEventType.CANCEL_RIDE:
            case NetworkEventType.CANCELLED_FRAGMENT:
            case NetworkEventType.FORGET_PASSWORD:
            case NetworkEventType.RESET_PASSWORD:
            case NetworkEventType.FMR_POINTS:
            case NetworkEventType.SUPPORT:
            case NetworkEventType.ALL_BOOKINGID:
            case NetworkEventType.FEEDBACK_OTP:
            case NetworkEventType.COUPON:
            case NetworkEventType.PAYMENT_INITIAL:





                requestMethod = Request.Method.POST;
                request = getStringRequest();
                break;


            case NetworkEventType.LICENCE_DETAIL:
                requestMethod = Request.Method.POST;
                request = getVolleyMultipartRequest(requestMap.get("user_id"),requestMap.get("dl_country"),requestMap.get("dl_country_issue"),requestMap.get("dl_issue_date"),requestMap.get("dlexpiry_date"),requestMap.get("customer_name"),requestMap.get("dl_number"),requestMap.get("customer_dob"),requestMap.get("alternate_number"),requestMap.get("complete_address"),"licence");
                break;
            case NetworkEventType.LICENCE_UPDATE:
                requestMethod = Request.Method.POST;
                request = getVolleyMultipartRequest(requestMap.get("user_id"),requestMap.get("dl_country"),requestMap.get("dl_country_issue"),requestMap.get("dl_issue_date"),requestMap.get("dlexpiry_date"),requestMap.get("customer_name"),requestMap.get("dl_number"),requestMap.get("customer_dob"),requestMap.get("alternate_number"),requestMap.get("complete_address"),"licence");
                break;
            case NetworkEventType.UPDATE_PROFILE:
                requestMethod = Request.Method.POST;
                request = getVolleyMultipartRequest(requestMap.get("user_id"),requestMap.get("name"),requestMap.get("mobile"),requestMap.get("address"),requestMap.get("gender"),requestMap.get("about"),requestMap.get("nationality"),"","","","profile");
                break;

            case NetworkEventType.FMR_OFFERS:
                requestMethod = Request.Method.GET;
                request = getStringRequest();
                break;


            default:
                break;
        }
        request.setShouldCache(false);

        return request;
    }

    @Override
    String getUrl() {
        switch (networkEventType) {

            case NetworkEventType.LOGIN:
                url ="http://technowhizzit.com/findmyride/Ride/login";
                break;

            case NetworkEventType.SIGNUP:
                url ="http://technowhizzit.com/findmyride/Ride/signup";
                break;

            case NetworkEventType.RENTAL_LIST:
                url ="http://technowhizzit.com/findmyride/Ride/car";
                break;

            case NetworkEventType.BOOKING:
                url ="http://technowhizzit.com/findmyride/Ride/ListCarbooking";
                break;

            case NetworkEventType.LICENCE_DETAIL:
                url ="http://technowhizzit.com/findmyride/Ride/Licencedetails";
                break;

            case NetworkEventType.CONFIRM_BOOKING:
                url ="http://technowhizzit.com/findmyride/Ride/booking";
                break;
            case NetworkEventType.PAYMENT_INITIAL:
                url ="http://technowhizzit.com/findmyride/Ride/Payment";
                break;

            case NetworkEventType.ON_GOING_RIDE:
            case NetworkEventType.UPCOMING_FRAGMNET:
            case NetworkEventType.FINISHED_FRAGMENT:
            case NetworkEventType.CANCELLED_FRAGMENT:
                url ="http://technowhizzit.com/findmyride/Ride/BookingDetails";

                break;
            case NetworkEventType.END_OTP:
                url ="http://technowhizzit.com/findmyride/Ride/Endtotp";

                break;

            case NetworkEventType.END_TRIP:
                url ="http://technowhizzit.com/findmyride/Ride/Endride";

                break;

            case NetworkEventType.ADD_RATE:
                url ="http://technowhizzit.com/findmyride/Ride/AddRating";

                break;

            case NetworkEventType.UPDATE_PROFILE:
                url ="http://technowhizzit.com/findmyride/Ride/updateuser";

                break;
            case NetworkEventType.FETCH_RATE:
                url ="http://technowhizzit.com/findmyride/Ride/Rating";

                break;

            case NetworkEventType.START_OTP:
                url ="http://technowhizzit.com/findmyride/Ride/Startotp";

                break;
            case NetworkEventType.START_TRIP:
                url ="http://technowhizzit.com/findmyride/Ride/startride";

                break;
            case NetworkEventType.SENT_INVITES:
                url ="http://technowhizzit.com/findmyride/Ride/invites";
                break;
            case NetworkEventType.FILTER_CAR:
                url ="http://technowhizzit.com/findmyride/Ride/filter";
                break;

            case NetworkEventType.CANCEL_RIDE:
                url ="http://technowhizzit.com/findmyride/Ride/cancelride";
                break;

            case NetworkEventType.FORGET_PASSWORD:
                url ="http://technowhizzit.com/findmyride/Ride/forget";
                break;
            case NetworkEventType.RESET_PASSWORD:
                url ="http://technowhizzit.com/findmyride/Ride/passwordchange";
                break;

            case NetworkEventType.FMR_POINTS:
                url ="http://technowhizzit.com/findmyride/Ride/Getpoints";
                break;
            case NetworkEventType.SUPPORT:
                url ="http://technowhizzit.com/findmyride/Ride/support";
                break;

            case NetworkEventType.ALL_BOOKINGID:
                url ="http://technowhizzit.com/findmyride/Ride/Alluserbooking";
                break;

            case NetworkEventType.LICENCE_UPDATE:
                url ="http://technowhizzit.com/findmyride/Ride/UpdateLicencedetails";
                break;

            case NetworkEventType.FMR_OFFERS:
                url ="http://technowhizzit.com/findmyride/Ride/Fmroffers";
                break;

            case NetworkEventType.FEEDBACK_OTP:
                url ="http://technowhizzit.com/findmyride/Ride/matchotp";
                break;

            case NetworkEventType.COUPON:
                url ="http://technowhizzit.com/findmyride/Ride/fetch_coupon";
                break;



        }

        return url;
    }

    @Override
    HashMap<String, String> getRequestHeaders(int eventType) {
        HashMap<String, String> params = new HashMap<String, String>();
        return params;
    }

    @Override
    String getJsonBody() {
        return null;
    }

    @Override
    Response parseNetworkResponse(Object response) {
        ESNetworkResponse networkResponse = new ESNetworkResponse();
        switch (networkEventType) {
            case NetworkEventType.LOGIN:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    String responseMessage = jsonObject.getString("response_message");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        JSONObject userObject = null;
                        String json = jsonObject.getString("result");
                        Object objectjson = new JSONTokener(json).nextValue();
                        if (objectjson instanceof JSONObject) {
                            if (!jsonObject.isNull("result")) {
                                userObject = jsonObject.getJSONObject("result");
                                networkResponse.userModel = parseLoginData(userObject);
                            }
                        }
                        else if (objectjson instanceof JSONArray) {
                            if (!jsonObject.isNull("result")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("result");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    networkResponse.userModel = parseLoginData(jsonObject1);
                                }

                            }
                        }


                        if(!jsonObject.isNull("licence")){
                            JSONArray array = jsonObject.getJSONArray("licence");
                            networkResponse.licenceModelArrayList = parseLicenceData(array);
                        }
                    }

                    else if (responseCode.equals("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.ERROR;
                        networkResponse.responseMessage = responseMessage;

                    } else {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        networkResponse.responseMessage = responseMessage;

                    }
                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case NetworkEventType.SIGNUP:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    String responseMessage = jsonObject.getString("response_message");
                    if (responseCode.equalsIgnoreCase("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        networkResponse.responseMessage = responseMessage;

                    } else if (responseCode.equalsIgnoreCase("1")) {
                        JSONObject userObject = null;
                        if (!jsonObject.isNull("result")) {
                            networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                            userObject = jsonObject.getJSONObject("result");
                            networkResponse.userModel = parseLoginData(userObject);
                        }
                        if(!jsonObject.isNull("licence")){
                            JSONArray array = jsonObject.getJSONArray("licence");
                            networkResponse.licenceModelArrayList = parseLicenceData(array);
                        }
                        else{
                            networkResponse.responseCode = ESNetworkResponse.ResponseCode.ERROR;
                        }

                        networkResponse.responseMessage = responseMessage;
                    } else {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                    }
                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case NetworkEventType.RENTAL_LIST:
            case NetworkEventType.FILTER_CAR:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        if (!jsonObject.isNull("result")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            networkResponse.rentallistModelArrayList = parseRentalData(jsonArray);
                        }

                        if (!jsonObject.isNull("startdate")) {
                            String startdate = jsonObject.getString("startdate");
                            networkResponse.startDate = startdate;
                        }
                        if (!jsonObject.isNull("end")) {
                            String endDate = jsonObject.getString("end");
                            networkResponse.endDate = endDate;
                            networkResponse.endDate = endDate;
                        }

                    } else if(responseCode.equals("1")){
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }
                    else{
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }

                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case NetworkEventType.ALL_BOOKINGID:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        if (!jsonObject.isNull("result")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            networkResponse.carBookingModelArrayList = parseSupportData(jsonArray);
                        }
                        else{
                            if(!jsonObject.isNull("response_message")){
                                networkResponse.responseMessage = jsonObject.getString("response_message");
                            }
                        }
                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }
                    else{
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }

                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


            case NetworkEventType.BOOKING:
                networkResponse.stringResponse = response.toString();
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                String responseCode = jsonObject.getString("response_code");
                if (responseCode.equals("0")) {
                    networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                    if (!jsonObject.isNull("prebook")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("prebook");
                        networkResponse.prebookinglistModelArrayList = parseBookingData(jsonArray);
                    }
                    else{
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }
                      if (!jsonObject.isNull("postbook")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("postbook");
                        networkResponse.postbookinglistModelArrayList = parseBookingData(jsonArray);
                    }
                      else{
                          if(!jsonObject.isNull("response_message")){
                              networkResponse.responseMessage = jsonObject.getString("response_message");
                          }
                      }

                } else if (responseCode.equalsIgnoreCase("1")) {
                    networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                    if(!jsonObject.isNull("response_message")){
                        networkResponse.responseMessage = jsonObject.getString("response_message");
                    }
                }
                else{
                    networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                    if(!jsonObject.isNull("response_message")){
                        networkResponse.responseMessage = jsonObject.getString("response_message");
                    }
                }

                networkController.notifyUI(networkEventType, networkResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            break;

            case NetworkEventType.LICENCE_DETAIL:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        if (!jsonObject.isNull("Licenceid")) {
                            String licenceID = jsonObject.getString("Licenceid");
                            networkResponse.licenceID = licenceID;
                        }
                        else{
                            if (!jsonObject.isNull("response_message")) {
                                String respMessage = jsonObject.getString("response_message");
                                networkResponse.responseMessage = respMessage;
                            }
                        }
                    } else if(responseCode.equals("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                        if (!jsonObject.isNull("response_message")) {
                            String respMessage = jsonObject.getString("response_message");
                            networkResponse.responseMessage = respMessage;
                        }
                    }
                    else{
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        if (!jsonObject.isNull("response_message")) {
                            String respMessage = jsonObject.getString("response_message");
                            networkResponse.responseMessage = respMessage;
                        }
                    }

                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case NetworkEventType.CONFIRM_BOOKING:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        if (!jsonObject.isNull("BookingID")) {
                            String bookingID = jsonObject.getString("BookingID");
                            networkResponse.bookingID = bookingID;
                        }
                        else{
                            if (!jsonObject.isNull("response_message")) {
                                String respMessage = jsonObject.getString("response_message");
                                networkResponse.responseMessage = respMessage;
                            }
                        }
                    } else if (responseCode.equals("1")){
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                        if (!jsonObject.isNull("response_message")) {
                            String respMessage = jsonObject.getString("response_message");
                            networkResponse.responseMessage = respMessage;
                        }
                    }
                    else{
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        if (!jsonObject.isNull("response_message")) {
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }

                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case NetworkEventType.ON_GOING_RIDE:
            case NetworkEventType.UPCOMING_FRAGMNET:
            case NetworkEventType.FINISHED_FRAGMENT:
            case NetworkEventType.CANCELLED_FRAGMENT:

                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        if (!jsonObject.isNull("result")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            networkResponse.ridelistModelArrayList = parseRideData(jsonArray);
                        }
                        else if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }
                    else if (responseCode.equals("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                        if (!jsonObject.isNull("response_message")) {
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }
                    else {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        if (!jsonObject.isNull("response_message")) {
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }

                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case NetworkEventType.END_OTP:
            case NetworkEventType.START_OTP:
            case NetworkEventType.PAYMENT_INITIAL:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    String responseMessage = jsonObject.getString("response_message");
                    if (responseCode.equalsIgnoreCase("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        networkResponse.responseMessage = responseMessage;
//                        if(!jsonObject.isNull("otp")){
//                            networkResponse.stringResponse = jsonObject.getString("otp");
//                        }

                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.ERROR;
                        networkResponse.responseMessage = responseMessage;
                    } else {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                    }
                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case NetworkEventType.END_TRIP:
            case NetworkEventType.START_TRIP:
            case NetworkEventType.SENT_INVITES:
            case NetworkEventType.CANCEL_RIDE:
            case NetworkEventType.SUPPORT:
            case NetworkEventType.RESET_PASSWORD:
                case NetworkEventType.FEEDBACK_OTP:

                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    String responseMessage = jsonObject.getString("response_message");
                    if (responseCode.equalsIgnoreCase("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        networkResponse.responseMessage = responseMessage;

                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.ERROR;
                        networkResponse.responseMessage = responseMessage;
                    } else {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                    }
                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case NetworkEventType.ADD_RATE:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    String responseMessage = jsonObject.getString("response_message");
                    if (responseCode.equalsIgnoreCase("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        networkResponse.responseMessage = responseMessage;

                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.ERROR;
                        networkResponse.responseMessage = responseMessage;
                    } else {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                    }
                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case NetworkEventType.UPDATE_PROFILE:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    String responseMessage = jsonObject.getString("response_message");
                    if (responseCode.equalsIgnoreCase("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        networkResponse.responseMessage = responseMessage;

                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.ERROR;
                        networkResponse.responseMessage = responseMessage;
                    } else {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                    }
                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case NetworkEventType.FETCH_RATE:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    String responseMessage = jsonObject.getString("response_message");
                    if (responseCode.equalsIgnoreCase("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        networkResponse.responseMessage = responseMessage;
                        if(!jsonObject.isNull("result")){
                            networkResponse.responseMessage = jsonObject.getString("result");
                        }

                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.ERROR;
                        networkResponse.responseMessage = responseMessage;
                    } else {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                    }
                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case NetworkEventType.FMR_POINTS:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        if (!jsonObject.isNull("result")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            networkResponse.fmrlistModelArrayList = parseFmrData(jsonArray);
                        }
                        else{
                            if(!jsonObject.isNull("response_message")){
                                networkResponse.responseMessage = jsonObject.getString("response_message");
                            }
                        }
                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }
                    else{
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }

                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


            case NetworkEventType.FORGET_PASSWORD:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        if (!jsonObject.isNull("result")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            networkResponse.forgetlistModelArrayList = parseForgetData(jsonArray);
                        }
                        else{
                            if(!jsonObject.isNull("response_message")){
                                networkResponse.responseMessage = jsonObject.getString("response_message");
                            }
                        }
                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }
                    else{
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }

                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case NetworkEventType.LICENCE_UPDATE:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    String responseMessage = jsonObject.getString("response_message");
                    if (responseCode.equalsIgnoreCase("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        networkResponse.responseMessage = responseMessage;

                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.ERROR;
                        networkResponse.responseMessage = responseMessage;
                    } else {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                    }
                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case NetworkEventType.FMR_OFFERS:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        if (!jsonObject.isNull("result")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            networkResponse.fmrofferlistModelArrayList = parseFmrOfferData(jsonArray);
                        }
                        else{
                            if(!jsonObject.isNull("response_message")){
                                networkResponse.responseMessage = jsonObject.getString("response_message");
                            }
                        }
                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }
                    else{
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }

                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


            case NetworkEventType.COUPON:
                networkResponse.stringResponse = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String responseCode = jsonObject.getString("response_code");
                    if (responseCode.equals("0")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.SUCCESS;
                        if (!jsonObject.isNull("result")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("result");
                            networkResponse.fmrcouponlistModelArrayList = parseFmrCouponData(jsonArray);
                        }
                        else{
                            if(!jsonObject.isNull("response_message")){
                                networkResponse.responseMessage = jsonObject.getString("response_message");
                            }
                        }
                    } else if (responseCode.equalsIgnoreCase("1")) {
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.NODATA;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }
                    else{
                        networkResponse.responseCode = ESNetworkResponse.ResponseCode.FAILURE;
                        if(!jsonObject.isNull("response_message")){
                            networkResponse.responseMessage = jsonObject.getString("response_message");
                        }
                    }

                    networkController.notifyUI(networkEventType, networkResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;


        }
        return null;
    }



    private ArrayList<LicenceModel> parseLicenceData(JSONArray liceneceArray){
        ArrayList<LicenceModel> licenceModelArrayList = new ArrayList<>();
        String driving_detail_id = null;
        String dl_country = null;
        String dl_country_issue = null;
        String dl_issue_date = null;
        String dlexpiry_date = null;
        String customer_name = null;
        String dl_number = null;
        String customer_dob = null;
        String alternate_number = null;
        String complete_address = null;
        String user_id = null;
        String driverImage = null;
        for (int i=0; i<liceneceArray.length();i++){
            try {
                JSONObject object = liceneceArray.getJSONObject(i);
                LicenceModel licenceModel = new LicenceModel();
                if (!object.isNull("driving_detail_id")) {
                    driving_detail_id = object.getString("driving_detail_id");
                }
                if (!object.isNull("dl_country")) {
                    dl_country = object.getString("dl_country");
                }
                if (!object.isNull("dl_country_issue")) {
                    dl_country_issue = object.getString("dl_country_issue");
                }
                if (!object.isNull("dl_issue_date")) {
                    dl_issue_date = object.getString("dl_issue_date");
                }
                if (!object.isNull("dlexpiry_date")) {
                    dlexpiry_date = object.getString("dlexpiry_date");
                }

                if (!object.isNull("customer_name")) {
                    customer_name = object.getString("customer_name");
                }

                if (!object.isNull("dl_number")) {
                    dl_number = object.getString("dl_number");
                }

                if (!object.isNull("customer_dob")) {
                    customer_dob = object.getString("customer_dob");
                }

                if (!object.isNull("alternate_number")) {
                    alternate_number = object.getString("alternate_number");
                }

                if (!object.isNull("complete_address")) {
                    complete_address = object.getString("complete_address");
                }

                if (!object.isNull("user_id")) {
                    user_id = object.getString("user_id");
                }

                if (!object.isNull("image")) {
                    driverImage = object.getString("image");
                }
                licenceModel.setDriving_detail_id(driving_detail_id);
                licenceModel.setDl_country(dl_country);
                licenceModel.setDl_country_issue(dl_country_issue);
                licenceModel.setDl_issue_date(dl_issue_date);
                licenceModel.setDlexpiry_date(dlexpiry_date);
                licenceModel.setCustomer_name(customer_name);
                licenceModel.setDl_number(dl_number);
                licenceModel.setCustomer_dob(customer_dob);
                licenceModel.setAlternate_number(alternate_number);
                licenceModel.setComplete_address(complete_address);
                licenceModel.setUser_id(user_id);
                licenceModel.setLicenceImage(driverImage);
                licenceModelArrayList.add(licenceModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return licenceModelArrayList;
    }


    private UserModel parseLoginData(JSONObject jsonObject) {
        UserModel userModel = new UserModel();
        String id = null;
        String name = null;
        String email = null;
        String mobile = null;
        String password = null;
        String token = null;
        String login_type = null;
         String address_user = null;
         String gender = null;
         String about = null;
         String image = null;
         String member = null;
        String nationality = null;
        String pay_status = null;

        try {



            if (!jsonObject.isNull("id")) {
                id = jsonObject.getString("id");
            }
            if (!jsonObject.isNull("pay_status")) {
                pay_status = jsonObject.getString("pay_status");
            }
            if (!jsonObject.isNull("nationality")) {
                nationality = jsonObject.getString("nationality");
            }
            if (!jsonObject.isNull("name")) {
                name = jsonObject.getString("name");
            }
            if (!jsonObject.isNull("email")) {
                email = jsonObject.getString("email");
            }
            if (!jsonObject.isNull("mobile")) {
                mobile = jsonObject.getString("mobile");
            }
            if (!jsonObject.isNull("password")) {
                password = jsonObject.getString("password");
            }
            if (!jsonObject.isNull("token")) {
                token = jsonObject.getString("token");
            }
            if (!jsonObject.isNull("login_type")) {
                login_type = jsonObject.getString("login_type");
            }

            if (!jsonObject.isNull("address_user")) {
                address_user = jsonObject.getString("address_user");
            }

            if (!jsonObject.isNull("gender")) {
                gender = jsonObject.getString("gender");
            }

            if (!jsonObject.isNull("about")) {
                about = jsonObject.getString("about");
            }

            if (!jsonObject.isNull("image")) {
                image = jsonObject.getString("image");
            }

            if (!jsonObject.isNull("member")) {
                member = jsonObject.getString("member");
            }




            userModel.setName(name);
            userModel.setPassword(password);
            userModel.setEmail(email);
            userModel.setId(id);
            userModel.setMobile(mobile);
            userModel.setToken(token);
            userModel.setLogin_type(login_type);
            userModel.setAddress_user(address_user);
            userModel.setGender(gender);
            userModel.setAbout(about);
            userModel.setImage(image);
            userModel.setMember(member);
            userModel.setNationality(nationality);
            userModel.setPay_status(pay_status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        networkController.userModel = userModel;
        return userModel;
    }


    private ArrayList<CarRentalListModel> parseRentalData(JSONArray jsonArray) {
        ArrayList<CarRentalListModel> rentallistDataArrayList = new ArrayList<>();
        CarRentalListModel rentallistmodelData = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                rentallistmodelData = new CarRentalListModel();
                String car_id = null;
                String car_name = null;
                String car_model = null;
                String car_lat = null;
                String car_long = null;
                String fuel_type = null;
                String car_type = null;
                String car_seater = null;
                String hourly_price = null;
                String daily_price = null;
                String weekly_price = null;
                String vehicle_type = null;
                String vehicle_make = null;
                String vehicle_model = null;
                String consumption = null;
                String registration_year = null;
                String doors = null;
                String odometer = null;
                String drive_train = null;
                String feature = null;
                String description = null;
                String car_image1 = null;
                String car_image2 = null;
                String car_image3 = null;
                String car_image4 = null;
                String car_image5 = null;
                String car_image6 = null;
                String car_image7 = null;
                String car_image8 = null;
                String car_image9 = null;
                String car_image10 = null;
                String car_owner_id = null;
                String owner_id = null;
                String owner_name = null;
                String owner_type = null;
                String owner_image = null;
                String drop_location1 = null;
                String drop_location2 = null;
                String rating = null;

                String phone_number = null;
                String member_since = null;
                String address = null;
                String pickup_location = null;
                String email = null;
                String engine = null;
                String transmission = null;

                if (!(jsonObject.isNull("phone_number"))) {
                    phone_number = jsonObject.getString("phone_number");
                }

                if (!(jsonObject.isNull("member_since"))) {
                    member_since = jsonObject.getString("member_since");
                }

                if (!(jsonObject.isNull("address"))) {
                    address = jsonObject.getString("address");
                }

                if (!(jsonObject.isNull("car_id"))) {
                    car_id = jsonObject.getString("car_id");
                }
                if (!(jsonObject.isNull("car_name"))) {
                    car_name = jsonObject.getString("car_name");
                }
                if (!(jsonObject.isNull("car_model"))) {
                    car_model = jsonObject.getString("car_model");
                }
                if (!(jsonObject.isNull("car_lat"))) {
                    car_lat = jsonObject.getString("car_lat");
                }
                if (!(jsonObject.isNull("car_long"))) {
                    car_long = jsonObject.getString("car_long");
                }
                if (!(jsonObject.isNull("fuel_type"))) {
                    fuel_type = jsonObject.getString("fuel_type");
                }
                if (!(jsonObject.isNull("car_type"))) {
                    car_type = jsonObject.getString("car_type");
                }
                if (!(jsonObject.isNull("car_seater"))) {
                    car_seater = jsonObject.getString("car_seater");
                }
                if (!(jsonObject.isNull("hourly_price"))) {
                    hourly_price = jsonObject.getString("hourly_price");
                }
                if (!(jsonObject.isNull("daily_price"))) {
                    daily_price = jsonObject.getString("daily_price");
                }
                if (!(jsonObject.isNull("weekly_price"))) {
                    weekly_price = jsonObject.getString("weekly_price");
                }
                if (!(jsonObject.isNull("vehicle_type"))) {
                    vehicle_type = jsonObject.getString("vehicle_type");
                }
                if (!(jsonObject.isNull("vehicle_make"))) {
                    vehicle_make = jsonObject.getString("vehicle_make");
                }
                if (!(jsonObject.isNull("vehicle_model"))) {
                    vehicle_model = jsonObject.getString("vehicle_model");
                }
                if (!(jsonObject.isNull("consumption"))) {
                    consumption = jsonObject.getString("consumption");
                }
                if (!(jsonObject.isNull("registration_year"))) {
                    registration_year = jsonObject.getString("registration_year");
                }


                if (!(jsonObject.isNull("doors"))) {
                    doors = jsonObject.getString("doors");
                }
                if (!(jsonObject.isNull("odometer"))) {
                    odometer = jsonObject.getString("odometer");
                }
                if (!(jsonObject.isNull("drive_train"))) {
                    drive_train = jsonObject.getString("drive_train");
                }
                if (!(jsonObject.isNull("feature"))) {
                    feature = jsonObject.getString("feature");
                }
                if (!(jsonObject.isNull("description"))) {
                    description = jsonObject.getString("description");
                }
                if (!(jsonObject.isNull("car_image1"))) {
                    car_image1 = jsonObject.getString("car_image1");
                }
                if (!(jsonObject.isNull("car_image2"))) {
                    car_image2 = jsonObject.getString("car_image2");
                }
                if (!(jsonObject.isNull("car_image3"))) {
                    car_image3 = jsonObject.getString("car_image3");
                }
                if (!(jsonObject.isNull("car_image4"))) {
                    car_image4 = jsonObject.getString("car_image4");
                }
                if (!(jsonObject.isNull("car_image5"))) {
                    car_image5 = jsonObject.getString("car_image5");
                }
                if (!(jsonObject.isNull("car_image6"))) {
                    car_image6 = jsonObject.getString("car_image6");
                }
                if (!(jsonObject.isNull("car_image7"))) {
                    car_image7 = jsonObject.getString("car_image7");
                }
                if (!(jsonObject.isNull("car_image8"))) {
                    car_image8 = jsonObject.getString("car_image8");
                }
                if (!(jsonObject.isNull("car_image9"))) {
                    car_image9 = jsonObject.getString("car_image9");
                }
                if (!(jsonObject.isNull("car_image10"))) {
                    car_image10 = jsonObject.getString("car_image10");
                }
                if (!(jsonObject.isNull("car_owner_id"))) {
                    car_owner_id = jsonObject.getString("car_owner_id");
                }
                if (!(jsonObject.isNull("owner_id"))) {
                    owner_id = jsonObject.getString("owner_id");
                }
                if (!(jsonObject.isNull("owner_name"))) {
                    owner_name = jsonObject.getString("owner_name");
                }
                if (!(jsonObject.isNull("owner_type"))) {
                    owner_type = jsonObject.getString("owner_type");
                }
                if (!(jsonObject.isNull("owner_image"))) {
                    owner_image = jsonObject.getString("owner_image");
                }
                if (!(jsonObject.isNull("drop_location1"))) {
                    drop_location1 = jsonObject.getString("drop_location1");
                }
                if (!(jsonObject.isNull("drop_location2"))) {
                    drop_location2 = jsonObject.getString("drop_location2");
                }
                if (!(jsonObject.isNull("rating"))) {
                    rating = jsonObject.getString("rating");
                }
                if (!(jsonObject.isNull("pickup_location"))) {
                    pickup_location = jsonObject.getString("pickup_location");
                }
                if (!(jsonObject.isNull("email"))) {
                    email = jsonObject.getString("email");
                }
                if (!(jsonObject.isNull("engine"))) {
                    engine = jsonObject.getString("engine");
                }
                if (!(jsonObject.isNull("transmission"))) {
                    transmission = jsonObject.getString("transmission");
                }




                rentallistmodelData.setCar_id(car_id);
                rentallistmodelData.setCar_name(car_name);
                rentallistmodelData.setCar_model(car_model);
                rentallistmodelData.setCar_lat(car_lat);
                rentallistmodelData.setCar_long(car_long);
                rentallistmodelData.setFuel_type(fuel_type);
                rentallistmodelData.setCar_type(car_type);
                rentallistmodelData.setCar_seater(car_seater);
                rentallistmodelData.setHourly_price(hourly_price);
                rentallistmodelData.setDaily_price(daily_price);
                rentallistmodelData.setWeekly_price(weekly_price);
                rentallistmodelData.setVehicle_type(vehicle_type);
                rentallistmodelData.setVehicle_make(vehicle_make);
                rentallistmodelData.setVehicle_model(vehicle_model);
                rentallistmodelData.setConsumption(consumption);
                rentallistmodelData.setRegistration_year(registration_year);
                rentallistmodelData.setDoors(doors);
                rentallistmodelData.setOdometer(odometer);
                rentallistmodelData.setDrive_train(drive_train);
                rentallistmodelData.setFeature(feature);
                rentallistmodelData.setDescription(description);
                rentallistmodelData.setCar_image1(car_image1);
                rentallistmodelData.setCar_image2(car_image2);
                rentallistmodelData.setCar_image3(car_image3);
                rentallistmodelData.setCar_image4(car_image4);
                rentallistmodelData.setCar_image5(car_image5);
                rentallistmodelData.setCar_image6(car_image6);
                rentallistmodelData.setCar_image7(car_image7);
                rentallistmodelData.setCar_image8(car_image8);
                rentallistmodelData.setCar_image9(car_image9);
                rentallistmodelData.setCar_image10(car_image10);
                rentallistmodelData.setCar_owner_id(car_owner_id);
                rentallistmodelData.setOwner_id(owner_id);
                rentallistmodelData.setOwner_name(owner_name);
                rentallistmodelData.setOwner_type(owner_type);
                rentallistmodelData.setOwner_image(owner_image);
                rentallistmodelData.setDrop_location1(drop_location1);
                rentallistmodelData.setDrop_location2(drop_location2);
                rentallistmodelData.setRating(rating);

                rentallistmodelData.setMember_since(member_since);
                rentallistmodelData.setPhone_number(phone_number);
                rentallistmodelData.setAddress(address);
                rentallistmodelData.setPickup_location(pickup_location);
                rentallistmodelData.setEmail(email);
                rentallistmodelData.setEngine(engine);
                rentallistmodelData.setTransmission(transmission);

                rentallistDataArrayList.add(rentallistmodelData);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        networkController.rentallistDataArrayList = rentallistDataArrayList;
        return rentallistDataArrayList;

    }





    private ArrayList<BookingModel> parseBookingData(JSONArray jsonArray) {
        ArrayList<BookingModel> bookinglistDataArrayList = new ArrayList<>();
        BookingModel bookinglistmodelData = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                bookinglistmodelData = new BookingModel();
                String book_id = null;
                String user_id = null;
                String licence_id = null;
                String car_id = null;
                String book_from = null;
                String book_to = null;

                 String booking_id = null;
                 String drop_location = null;
                 String end_trip = null;
                 String pay_amount = null;
                 String pay_id = null;
                 String pay_status = null;
                 String pickup_location = null;
                 String start_trip = null;
                 String status = null;

                if (!(jsonObject.isNull("booking_id"))) {
                    booking_id = jsonObject.getString("booking_id");
                }
                if (!(jsonObject.isNull("drop_location"))) {
                    drop_location = jsonObject.getString("drop_location");
                }
                if (!(jsonObject.isNull("end_trip"))) {
                    end_trip = jsonObject.getString("end_trip");
                }
                if (!(jsonObject.isNull("pay_amount"))) {
                    pay_amount = jsonObject.getString("pay_amount");
                }
                if (!(jsonObject.isNull("pay_id"))) {
                    pay_id = jsonObject.getString("pay_id");
                }
                if (!(jsonObject.isNull("pay_status"))) {
                    pay_status = jsonObject.getString("pay_status");
                }
                if (!(jsonObject.isNull("pickup_location"))) {
                    pickup_location = jsonObject.getString("pickup_location");
                }
                if (!(jsonObject.isNull("start_trip"))) {
                    start_trip = jsonObject.getString("start_trip");
                }
                if (!(jsonObject.isNull("status"))) {
                    status = jsonObject.getString("status");
                }




                if (!(jsonObject.isNull("book_id"))) {
                    book_id = jsonObject.getString("book_id");
                }
                if (!(jsonObject.isNull("user_id"))) {
                    user_id = jsonObject.getString("user_id");
                }
                if (!(jsonObject.isNull("licence_id"))) {
                    licence_id = jsonObject.getString("licence_id");
                }
                if (!(jsonObject.isNull("car_id"))) {
                    car_id = jsonObject.getString("car_id");
                }
                if (!(jsonObject.isNull("book_from"))) {
                    book_from = jsonObject.getString("book_from");
                }
                if (!(jsonObject.isNull("book_to"))) {
                    book_to = jsonObject.getString("book_to");
                }





                bookinglistmodelData.setBook_id(book_id);
                bookinglistmodelData.setUser_id(user_id);
                bookinglistmodelData.setLicence_id(licence_id);
                bookinglistmodelData.setCar_id(car_id);
                bookinglistmodelData.setBook_from(book_from);
                bookinglistmodelData.setBook_to(book_to);
                bookinglistmodelData.setBooking_id(booking_id);
                bookinglistmodelData.setDrop_location(drop_location);
                bookinglistmodelData.setEnd_trip(end_trip);
                bookinglistmodelData.setPay_amount(pay_amount);
                bookinglistmodelData.setPay_amount(pay_amount);
                bookinglistmodelData.setPay_id(pay_id);
                bookinglistmodelData.setPay_status(pay_status);
                bookinglistmodelData.setPickup_location(pickup_location);
                bookinglistmodelData.setStart_trip(start_trip);
                bookinglistmodelData.setStatus(status);



                bookinglistDataArrayList.add(bookinglistmodelData);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        networkController.bookinglistDataArrayList = bookinglistDataArrayList;
        return bookinglistDataArrayList;

    }


    private ArrayList<RideDetailModel> parseRideData(JSONArray jsonArray) {
        ArrayList<RideDetailModel> ridelistDataArrayList = new ArrayList<>();
        RideDetailModel ridelistmodelData = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ridelistmodelData = new RideDetailModel();

                String book_id = null;
                String user_id = null;
                String licence_id = null;
                String book_from = null;
                String book_to = null;
                String drop_location = null;
                String booking_id = null;

                String car_id = null;
                String car_name = null;
                String car_model = null;
                String car_lat = null;
                String car_long = null;
                String fuel_type = null;
                String car_type = null;
                String car_seater = null;
                String hourly_price = null;
                String daily_price = null;
                String weekly_price = null;
                String vehicle_type = null;
                String vehicle_make = null;
                String vehicle_model = null;
                String consumption = null;
                String registration_year = null;
                String doors = null;
                String odometer = null;
                String drive_train = null;
                String feature = null;
                String description = null;
                String car_image1 = null;
                String car_image2 = null;
                String car_image3 = null;
                String car_image4 = null;
                String car_image5 = null;
                String car_image6 = null;
                String car_image7 = null;
                String car_image8 = null;
                String car_image9 = null;
                String car_image10 = null;
                String car_owner_id = null;
                String drop_location1 = null;
                String drop_location2 = null;
                String owner_id = null;
                String owner_name = null;
                String owner_type = null;
                String owner_image = null;
                String driving_detail_id = null;
                String dl_country = null;
                String dl_country_issue = null;
                String dl_issue_date = null;
                String dlexpiry_date = null;
                String customer_name = null;
                String dl_number = null;
                String customer_dob = null;
                String alternate_number = null;
                String complete_address = null;
                String image = null;

                String status = null;
                String start_trip = null;
                String end_trip = null;
                String phone_number = null;
                String address = null;
                String member_since = null;
                String email = null;
                String pickup_location = null;
                String book_amount = null;


                if (!(jsonObject.isNull("book_amount"))) {
                    book_amount = jsonObject.getString("book_amount");
                }

                if (!(jsonObject.isNull("book_id"))) {
                    book_id = jsonObject.getString("book_id");
                }
                if (!(jsonObject.isNull("user_id"))) {
                    user_id = jsonObject.getString("user_id");
                }
                if (!(jsonObject.isNull("licence_id"))) {
                    licence_id = jsonObject.getString("licence_id");
                }
                if (!(jsonObject.isNull("book_from"))) {
                    book_from = jsonObject.getString("book_from");
                }
                if (!(jsonObject.isNull("book_to"))) {
                    book_to = jsonObject.getString("book_to");
                }

                if (!(jsonObject.isNull("booking_id"))) {
                    booking_id = jsonObject.getString("booking_id");
                }

//                booking_id
                if (!(jsonObject.isNull("drop_location"))) {
                    drop_location = jsonObject.getString("drop_location");
                }
                if (!(jsonObject.isNull("car_id"))) {
                    car_id = jsonObject.getString("car_id");
                }
                if (!(jsonObject.isNull("car_name"))) {
                    car_name = jsonObject.getString("car_name");
                }
                if (!(jsonObject.isNull("car_model"))) {
                    car_model = jsonObject.getString("car_model");
                }
                if (!(jsonObject.isNull("car_lat"))) {
                    car_lat = jsonObject.getString("car_lat");
                }
                if (!(jsonObject.isNull("car_long"))) {
                    car_long = jsonObject.getString("car_long");
                }
                if (!(jsonObject.isNull("fuel_type"))) {
                    fuel_type = jsonObject.getString("fuel_type");
                }
                if (!(jsonObject.isNull("car_type"))) {
                    car_type = jsonObject.getString("car_type");
                }
                if (!(jsonObject.isNull("car_seater"))) {
                    car_seater = jsonObject.getString("car_seater");
                }
                if (!(jsonObject.isNull("hourly_price"))) {
                    hourly_price = jsonObject.getString("hourly_price");
                }
                if (!(jsonObject.isNull("daily_price"))) {
                    daily_price = jsonObject.getString("daily_price");
                }
                if (!(jsonObject.isNull("weekly_price"))) {
                    weekly_price = jsonObject.getString("weekly_price");
                }
                if (!(jsonObject.isNull("vehicle_type"))) {
                    vehicle_type = jsonObject.getString("vehicle_type");
                }
                if (!(jsonObject.isNull("vehicle_make"))) {
                    vehicle_make = jsonObject.getString("vehicle_make");
                }
                if (!(jsonObject.isNull("vehicle_model"))) {
                    vehicle_model = jsonObject.getString("vehicle_model");
                }
                if (!(jsonObject.isNull("consumption"))) {
                    consumption = jsonObject.getString("consumption");
                }
                if (!(jsonObject.isNull("registration_year"))) {
                    registration_year = jsonObject.getString("registration_year");
                }


                if (!(jsonObject.isNull("doors"))) {
                    doors = jsonObject.getString("doors");
                }
                if (!(jsonObject.isNull("odometer"))) {
                    odometer = jsonObject.getString("odometer");
                }
                if (!(jsonObject.isNull("drive_train"))) {
                    drive_train = jsonObject.getString("drive_train");
                }
                if (!(jsonObject.isNull("feature"))) {
                    feature = jsonObject.getString("feature");
                }
                if (!(jsonObject.isNull("description"))) {
                    description = jsonObject.getString("description");
                }
                if (!(jsonObject.isNull("car_image1"))) {
                    car_image1 = jsonObject.getString("car_image1");
                }
                if (!(jsonObject.isNull("car_image2"))) {
                    car_image2 = jsonObject.getString("car_image2");
                }
                if (!(jsonObject.isNull("car_image3"))) {
                    car_image3 = jsonObject.getString("car_image3");
                }
                if (!(jsonObject.isNull("car_image4"))) {
                    car_image4 = jsonObject.getString("car_image4");
                }
                if (!(jsonObject.isNull("car_image5"))) {
                    car_image5 = jsonObject.getString("car_image5");
                }
                if (!(jsonObject.isNull("car_image6"))) {
                    car_image6 = jsonObject.getString("car_image6");
                }
                if (!(jsonObject.isNull("car_image7"))) {
                    car_image7 = jsonObject.getString("car_image7");
                }
                if (!(jsonObject.isNull("car_image8"))) {
                    car_image8 = jsonObject.getString("car_image8");
                }
                if (!(jsonObject.isNull("car_image9"))) {
                    car_image9 = jsonObject.getString("car_image9");
                }
                if (!(jsonObject.isNull("car_image10"))) {
                    car_image10 = jsonObject.getString("car_image10");
                }
                if (!(jsonObject.isNull("car_owner_id"))) {
                    car_owner_id = jsonObject.getString("car_owner_id");
                }
                if (!(jsonObject.isNull("drop_location1"))) {
                    drop_location1 = jsonObject.getString("drop_location1");
                }
                if (!(jsonObject.isNull("drop_location2"))) {
                    drop_location2 = jsonObject.getString("drop_location2");
                }
                if (!(jsonObject.isNull("owner_id"))) {
                    owner_id = jsonObject.getString("owner_id");
                }
                if (!(jsonObject.isNull("owner_name"))) {
                    owner_name = jsonObject.getString("owner_name");
                }
                if (!(jsonObject.isNull("owner_type"))) {
                    owner_type = jsonObject.getString("owner_type");
                }
                if (!(jsonObject.isNull("owner_image"))) {
                    owner_image = jsonObject.getString("owner_image");
                }
                if (!(jsonObject.isNull("driving_detail_id"))) {
                    driving_detail_id = jsonObject.getString("driving_detail_id");
                }
                if (!(jsonObject.isNull("dl_country"))) {
                    dl_country = jsonObject.getString("dl_country");
                }
                if (!(jsonObject.isNull("dl_country_issue"))) {
                    dl_country_issue = jsonObject.getString("dl_country_issue");
                }
                if (!(jsonObject.isNull("dl_issue_date"))) {
                    dl_issue_date = jsonObject.getString("dl_issue_date");
                }
                if (!(jsonObject.isNull("dlexpiry_date"))) {
                    dlexpiry_date = jsonObject.getString("dlexpiry_date");
                }
                if (!(jsonObject.isNull("customer_name"))) {
                    customer_name = jsonObject.getString("customer_name");
                }
                if (!(jsonObject.isNull("dl_number"))) {
                    dl_number = jsonObject.getString("dl_number");
                }
                if (!(jsonObject.isNull("customer_dob"))) {
                    customer_dob = jsonObject.getString("customer_dob");
                }
                if (!(jsonObject.isNull("alternate_number"))) {
                    alternate_number = jsonObject.getString("alternate_number");
                }
                if (!(jsonObject.isNull("complete_address"))) {
                    complete_address = jsonObject.getString("complete_address");
                }
                if (!(jsonObject.isNull("image"))) {
                    image = jsonObject.getString("image");
                }

                if (!(jsonObject.isNull("status"))) {
                    status = jsonObject.getString("status");
                }
                if (!(jsonObject.isNull("start_trip"))) {
                    start_trip = jsonObject.getString("start_trip");
                }
                if (!(jsonObject.isNull("end_trip"))) {
                    end_trip = jsonObject.getString("end_trip");
                }
                if (!(jsonObject.isNull("phone_number"))) {
                    phone_number = jsonObject.getString("phone_number");
                }

                if (!(jsonObject.isNull("member_since"))) {
                    member_since  = jsonObject.getString("member_since");
                }
                if (!(jsonObject.isNull("address"))) {
                    address = jsonObject.getString("address");
                }

                if (!(jsonObject.isNull("email"))) {
                    email = jsonObject.getString("email");
                }

                if (!(jsonObject.isNull("pickup_location"))) {
                    pickup_location = jsonObject.getString("pickup_location");
                }


                    ridelistmodelData.setBook_id(book_id);
                    ridelistmodelData.setUser_id(user_id);
                    ridelistmodelData.setLicence_id(licence_id);
                    ridelistmodelData.setBook_from(book_from);
                    ridelistmodelData.setBook_to(book_to);
                    ridelistmodelData.setDrop_location(drop_location);
                    ridelistmodelData.setCar_id(car_id);
                    ridelistmodelData.setCar_name(car_name);
                    ridelistmodelData.setCar_model(car_model);
                    ridelistmodelData.setCar_lat(car_lat);
                    ridelistmodelData.setCar_long(car_long);
                    ridelistmodelData.setFuel_type(fuel_type);
                    ridelistmodelData.setCar_type(car_type);
                    ridelistmodelData.setCar_seater(car_seater);
                    ridelistmodelData.setHourly_price(hourly_price);
                    ridelistmodelData.setDaily_price(daily_price);
                    ridelistmodelData.setWeekly_price(weekly_price);
                    ridelistmodelData.setVehicle_type(vehicle_type);
                    ridelistmodelData.setVehicle_make(vehicle_make);
                    ridelistmodelData.setVehicle_model(vehicle_model);
                    ridelistmodelData.setConsumption(consumption);
                    ridelistmodelData.setRegistration_year(registration_year);
                    ridelistmodelData.setDoors(doors);
                    ridelistmodelData.setOdometer(odometer);
                    ridelistmodelData.setDrive_train(drive_train);
                    ridelistmodelData.setFeature(feature);
                    ridelistmodelData.setDescription(description);
                    ridelistmodelData.setCar_image1(car_image1);
                    ridelistmodelData.setCar_image2(car_image2);
                    ridelistmodelData.setCar_image3(car_image3);
                    ridelistmodelData.setCar_image4(car_image4);
                    ridelistmodelData.setCar_image5(car_image5);
                    ridelistmodelData.setCar_image6(car_image6);
                    ridelistmodelData.setCar_image7(car_image7);
                    ridelistmodelData.setCar_image8(car_image8);
                    ridelistmodelData.setCar_image9(car_image9);
                    ridelistmodelData.setCar_image10(car_image10);
                    ridelistmodelData.setCar_owner_id(car_owner_id);
                    ridelistmodelData.setDrop_location1(drop_location1);
                    ridelistmodelData.setDrop_location2(drop_location2);
                    ridelistmodelData.setOwner_id(owner_id);
                    ridelistmodelData.setOwner_name(owner_name);
                    ridelistmodelData.setOwner_type(owner_type);
                    ridelistmodelData.setOwner_image(owner_image);
                    ridelistmodelData.setDriving_detail_id(driving_detail_id);
                    ridelistmodelData.setDl_country(dl_country);
                    ridelistmodelData.setDl_country_issue(dl_country_issue);
                    ridelistmodelData.setDl_issue_date(dl_issue_date);
                    ridelistmodelData.setDlexpiry_date(dlexpiry_date);
                    ridelistmodelData.setCustomer_name(customer_name);
                    ridelistmodelData.setDl_number(dl_number);
                    ridelistmodelData.setCustomer_dob(customer_dob);
                    ridelistmodelData.setAlternate_number(alternate_number);
                    ridelistmodelData.setComplete_address(complete_address);
                    ridelistmodelData.setImage(image);
                ridelistmodelData.setBooking_id(booking_id);

                ridelistmodelData.setStatus(status);
                ridelistmodelData.setStart_trip(start_trip);
                ridelistmodelData.setEnd_trip(end_trip);
                ridelistmodelData.setPhone_number(phone_number);
                ridelistmodelData.setAddress(address);
                ridelistmodelData.setMember_since(member_since);
                ridelistmodelData.setEmail(email);
                ridelistmodelData.setPickup_location(pickup_location);
                ridelistmodelData.setBook_amount(book_amount);


                    ridelistDataArrayList.add(ridelistmodelData);


                } catch(JSONException e){
                    e.printStackTrace();
                }
            }
            networkController.ridelistDataArrayList = ridelistDataArrayList;
            return ridelistDataArrayList;

        }

    private ArrayList<ForgetModel> parseForgetData(JSONArray jsonArray) {
        ArrayList<ForgetModel> forgetlistDataArrayList = new ArrayList<>();
        ForgetModel forgetlistmodelData = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                forgetlistmodelData = new ForgetModel();
                String id = null;
                String email = null;
                String name = null;
                String password = null;
                String mobile = null;
                String refer_user_id = null;
                String login_type = null;
                String fcm_id = null;


                if (!(jsonObject.isNull("id"))) {
                    id = jsonObject.getString("id");
                }
                if (!(jsonObject.isNull("email"))) {
                    email = jsonObject.getString("email");
                }
                if (!(jsonObject.isNull("name"))) {
                    name = jsonObject.getString("name");
                }
                if (!(jsonObject.isNull("password"))) {
                    password = jsonObject.getString("password");
                }
                if (!(jsonObject.isNull("mobile"))) {
                    mobile = jsonObject.getString("mobile");
                }
                if (!(jsonObject.isNull("refer_user_id"))) {
                    refer_user_id = jsonObject.getString("refer_user_id");
                }
                if (!(jsonObject.isNull("login_type"))) {
                    login_type = jsonObject.getString("login_type");
                }
                if (!(jsonObject.isNull("fcm_id"))) {
                    fcm_id = jsonObject.getString("fcm_id");
                }





                forgetlistmodelData.setId(id);
                forgetlistmodelData.setEmail(email);
                forgetlistmodelData.setName(name);
                forgetlistmodelData.setPassword(password);
                forgetlistmodelData.setMobile(mobile);
                forgetlistmodelData.setRefer_user_id(refer_user_id);
                forgetlistmodelData.setLogin_type(login_type);
                forgetlistmodelData.setFcm_id(fcm_id);


                forgetlistDataArrayList.add(forgetlistmodelData);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        networkController.forgetlistDataArrayList = forgetlistDataArrayList;
        return forgetlistDataArrayList;

    }

    private ArrayList<CarBookingModel> parseSupportData(JSONArray jsonArray) {
        ArrayList<CarBookingModel> supportlistDataArrayList = new ArrayList<>();
        CarBookingModel supportlistmodelData = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                supportlistmodelData = new CarBookingModel();
                String book_id = null;
                String user_id = null;
                String booking_id = null;
                String licence_id = null;
                String car_id = null;
                String book_from = null;
                String book_to = null;

                String drop_location = null;
                String status = null;
                String start_trip = null;
                String end_trip = null;


                if (!(jsonObject.isNull("book_id"))) {
                    book_id = jsonObject.getString("book_id");
                }
                if (!(jsonObject.isNull("user_id"))) {
                    user_id = jsonObject.getString("user_id");
                }
                if (!(jsonObject.isNull("booking_id"))) {
                    booking_id = jsonObject.getString("booking_id");
                }
                if (!(jsonObject.isNull("licence_id"))) {
                    licence_id = jsonObject.getString("licence_id");
                }
                if (!(jsonObject.isNull("car_id"))) {
                    car_id = jsonObject.getString("car_id");
                }
                if (!(jsonObject.isNull("book_from"))) {
                    book_from = jsonObject.getString("book_from");
                }
                if (!(jsonObject.isNull("book_to"))) {
                    book_to = jsonObject.getString("book_to");
                }

                if (!(jsonObject.isNull("drop_location"))) {
                    drop_location = jsonObject.getString("drop_location");
                }
                if (!(jsonObject.isNull("status"))) {
                    status = jsonObject.getString("status");
                }
                if (!(jsonObject.isNull("start_trip"))) {
                    start_trip = jsonObject.getString("start_trip");
                }
                if (!(jsonObject.isNull("end_trip"))) {
                    end_trip = jsonObject.getString("end_trip");
                }





                supportlistmodelData.setBook_id(book_id);
                supportlistmodelData.setUser_id(user_id);
                supportlistmodelData.setLicence_id(licence_id);
                supportlistmodelData.setCar_id(car_id);
                supportlistmodelData.setBook_from(book_from);
                supportlistmodelData.setBook_to(book_to);
                supportlistmodelData.setBooking_id(booking_id);


                supportlistDataArrayList.add(supportlistmodelData);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        networkController.supportlistDataArrayList = supportlistDataArrayList;
        return supportlistDataArrayList;

    }

    private ArrayList<FmrModel> parseFmrData(JSONArray jsonArray) {
        ArrayList<FmrModel> fmrlistDataArrayList = new ArrayList<>();
        FmrModel fmrlistmodelData = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                fmrlistmodelData = new FmrModel();
                String point_id = null;
                String user_id = null;
                String invite_id = null;
                String points = null;


                if (!(jsonObject.isNull("point_id"))) {
                    point_id = jsonObject.getString("point_id");
                }
                if (!(jsonObject.isNull("user_id"))) {
                    user_id = jsonObject.getString("user_id");
                }
                if (!(jsonObject.isNull("invite_id"))) {
                    invite_id = jsonObject.getString("invite_id");
                }
                if (!(jsonObject.isNull("points"))) {
                    points = jsonObject.getString("points");
                }



                fmrlistmodelData.setPoint_id(point_id);
                fmrlistmodelData.setUser_id(user_id);
                fmrlistmodelData.setInvite_id(invite_id);
                fmrlistmodelData.setPoints(points);


                fmrlistDataArrayList.add(fmrlistmodelData);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        networkController.fmrlistDataArrayList = fmrlistDataArrayList;
        return fmrlistDataArrayList;

    }

    private ArrayList<FmrOfferModel> parseFmrOfferData(JSONArray jsonArray) {
        ArrayList<FmrOfferModel> fmrofferlistDataArrayList = new ArrayList<>();
        FmrOfferModel fmrofferlistmodelData = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                fmrofferlistmodelData = new FmrOfferModel();
                String offer_id = null;
                String offer_image = null;



                if (!(jsonObject.isNull("offer_id"))) {
                    offer_id = jsonObject.getString("offer_id");
                }
                if (!(jsonObject.isNull("offer_image"))) {
                    offer_image = jsonObject.getString("offer_image");
                }




                fmrofferlistmodelData.setOffer_id(offer_id);
                fmrofferlistmodelData.setOffer_image(offer_image);


                fmrofferlistDataArrayList.add(fmrofferlistmodelData);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        networkController.fmrofferlistDataArrayList = fmrofferlistDataArrayList;
        return fmrofferlistDataArrayList;

    }

    private ArrayList<FmrCouponModel> parseFmrCouponData(JSONArray jsonArray) {
        ArrayList<FmrCouponModel> fmrcouponlistDataArrayList = new ArrayList<>();
        FmrCouponModel fmrcouponlistmodelData = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                fmrcouponlistmodelData = new FmrCouponModel();
                String id = null;
                String coupon_name = null;
                String coupon_percent = null;
                String max_discount = null;


                if (!(jsonObject.isNull("id"))) {
                    id = jsonObject.getString("id");
                }
                if (!(jsonObject.isNull("coupon_name"))) {
                    coupon_name = jsonObject.getString("coupon_name");
                }
                if (!(jsonObject.isNull("coupon_percent"))) {
                    coupon_percent = jsonObject.getString("coupon_percent");
                }
                if (!(jsonObject.isNull("max_discount"))) {
                    max_discount = jsonObject.getString("max_discount");
                }



                fmrcouponlistmodelData.setId(id);
                fmrcouponlistmodelData.setCoupon_name(coupon_name);
                fmrcouponlistmodelData.setCoupon_percent(coupon_percent);
                fmrcouponlistmodelData.setMax_discount(max_discount);


                fmrcouponlistDataArrayList.add(fmrcouponlistmodelData);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        networkController.fmrcouponlistDataArrayList = fmrcouponlistDataArrayList;
        return fmrcouponlistDataArrayList;

    }










    void setNeworkError() {
        ESNetworkResponse networkResponse = new ESNetworkResponse();
        networkResponse.responseCode = ESNetworkResponse.ResponseCode.ERROR;
        networkResponse.errorMessage = "";
        networkController.notifyUI(networkEventType, networkResponse);
    }
}
