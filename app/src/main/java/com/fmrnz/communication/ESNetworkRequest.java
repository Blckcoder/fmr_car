package com.fmrnz.communication;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fmrnz.utils.DataPart;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Upen on 13/04/17.
 */

public abstract class ESNetworkRequest {

    public int requestMethod;
    public String url;
    public int networkEventType;
    public HashMap<String, String> requestMap = null;

    public byte[] byteArray;
    String userID,updateType;

    public ESNetworkController networkController = ESNetworkController.getInstance();

    protected void setHeader(NetworkResponse response) {
    }




    public interface NetworkEventType {
        int LOGIN = 101;
        int SIGNUP = 102;
        int RENTAL_LIST = 103;
        int BOOKING = 104;
        int LICENCE_DETAIL = 105;
        int CONFIRM_BOOKING = 106;
        int ON_GOING_RIDE = 107;
        int UPCOMING_FRAGMNET = 108;
        int FINISHED_FRAGMENT = 109;
        int END_TRIP = 110;
        int END_OTP = 111;
        int ADD_RATE = 112;
        int UPDATE_PROFILE = 113;
        int FETCH_RATE = 114;
        int START_TRIP = 115;
        int START_OTP = 116;
        int SENT_INVITES = 117;
        int FILTER_CAR = 118;
        int CANCEL_RIDE = 119;
        int CANCELLED_FRAGMENT = 120;
        int FORGET_PASSWORD = 121;
        int RESET_PASSWORD = 122;
        int SUPPORT = 123;
        int FMR_POINTS = 124;
        int ALL_BOOKINGID = 125;
        int LICENCE_UPDATE = 126;
        int FMR_OFFERS = 127;
        int FEEDBACK_OTP = 128;
        int COUPON = 129;
        int PAYMENT_INITIAL = 130;




    }


    protected StringRequest getStringRequest() {
        StringRequest stringRequest = new StringRequest(requestMethod, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseNetworkResponse(response);

                Log.i("Response: ", response);
            }
        }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {
                try {
                    setNeworkError();
                    Log.e("Error: ", error.getLocalizedMessage());
                } catch (Exception e) {
                    Log.e("Error: ", e.getLocalizedMessage());
                }
            }
        })


        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                switch (networkEventType) {
                    case NetworkEventType.LOGIN:
                        params.put("email", requestMap.get("email"));
                        params.put("password", requestMap.get("password"));
                        params.put("fcm_id", requestMap.get("fcm_id"));
                        params.put("login_type", requestMap.get("login_type"));
                        break;
                    case NetworkEventType.SIGNUP:
                        params.put("email", requestMap.get("email"));
                        params.put("password", requestMap.get("password"));
                        params.put("name", requestMap.get("name"));
                        params.put("mobile", requestMap.get("mobile"));
                        params.put("refer", requestMap.get("refer"));
                        params.put("fcm_id", requestMap.get("fcm_id"));
                        params.put("login_type", requestMap.get("login_type"));
                        break;
                    case NetworkEventType.RENTAL_LIST:
                        params.put("start", requestMap.get("start"));
                        params.put("end", requestMap.get("end"));
                        break;
                    case NetworkEventType.BOOKING:
                        params.put("car", requestMap.get("car"));
                        params.put("start", requestMap.get("start"));
                        params.put("end", requestMap.get("end"));
                        break;


                    case NetworkEventType.CONFIRM_BOOKING:
                        params.put("user_id", requestMap.get("user_id"));
                        params.put("licence_id", requestMap.get("licence_id"));
                        params.put("car_id", requestMap.get("car_id"));
                        params.put("book_from", requestMap.get("book_from"));
                        params.put("book_to", requestMap.get("book_to"));
                        params.put("drop_location", requestMap.get("drop_location"));
                        params.put("vendor_mail", requestMap.get("vendor_mail"));
                        params.put("pickup_location", requestMap.get("pickup_location"));
                        params.put("book_amount", requestMap.get("book_amount"));
                        params.put("fcm_point", requestMap.get("fcm_point"));
                        break;

                    case NetworkEventType.ON_GOING_RIDE:
                    case NetworkEventType.UPCOMING_FRAGMNET:
                    case NetworkEventType.FINISHED_FRAGMENT:
                    case NetworkEventType.CANCELLED_FRAGMENT:
                        params.put("user_id", requestMap.get("user_id"));
                        params.put("status", requestMap.get("status"));
                        break;

                    case NetworkEventType.END_OTP:
                        params.put("booking_id", requestMap.get("booking_id"));
                        params.put("email", requestMap.get("email"));
                        params.put("pay_amount", requestMap.get("pay_amount"));
                        params.put("pay_status", requestMap.get("pay_status"));
                        params.put("pay_id", requestMap.get("pay_id"));
                        break;
                    case NetworkEventType.PAYMENT_INITIAL:
                        params.put("user_id", requestMap.get("user_id"));
                        params.put("pay_amount", requestMap.get("pay_amount"));
                        params.put("pay_status", requestMap.get("pay_status"));
                        params.put("pay_id", requestMap.get("pay_id"));
                        break;

                    case NetworkEventType.END_TRIP:
                        params.put("booking_id", requestMap.get("booking_id"));
                        params.put("otp", requestMap.get("otp"));
                        break;

                    case NetworkEventType.START_OTP:
                        params.put("booking_id", requestMap.get("booking_id"));
                        params.put("email", requestMap.get("email"));
//                        params.put("pay_amount", requestMap.get("pay_amount"));
//                        params.put("pay_status", requestMap.get("pay_status"));
//                        params.put("pay_id", requestMap.get("pay_id"));
//                        params.put("user_id", requestMap.get("user_id"));
                        break;

                    case NetworkEventType.START_TRIP:
                        params.put("booking_id", requestMap.get("booking_id"));
                        params.put("otp", requestMap.get("otp"));
                        break;

                    case NetworkEventType.ADD_RATE:
                        params.put("user_id", requestMap.get("user_id"));
                        params.put("car_id", requestMap.get("car_id"));
                        params.put("rating", requestMap.get("rating"));
                        params.put("comment", requestMap.get("comment"));

                        break;


                    case NetworkEventType.FETCH_RATE:
                        params.put("car_id", requestMap.get("car_id"));
                        break;
                    case NetworkEventType.SENT_INVITES:
                        params.put("user_id", requestMap.get("user_id"));
                        params.put("refer", requestMap.get("refer"));
                        break;
                    case NetworkEventType.FILTER_CAR:
                        HashMap<String,String> hashMap = new HashMap<>();
                        for(String key : requestMap.keySet()){
                            params.put(key, requestMap.get(key));
                        }
                       /* params.put("vehicle_type", requestMap.get("vehicle_type"));
                        params.put("vehicle_make", requestMap.get("vehicle_make"));
                        params.put("transmition", requestMap.get("transmition"));
                        params.put("fuel_type", requestMap.get("fuel_type"));
                        params.put("owner_type", requestMap.get("owner_type"));
                        params.put("seats", requestMap.get("seats"));
                        params.put("rating", requestMap.get("rating"));*/
                        break;

                    case NetworkEventType.CANCEL_RIDE:
                        params.put("booking_id", requestMap.get("booking_id"));
                        break;

                    case NetworkEventType.FORGET_PASSWORD:
                        params.put("email", requestMap.get("email"));
                        break;

                    case NetworkEventType.RESET_PASSWORD:
                        params.put("email", requestMap.get("email"));
                        params.put("password", requestMap.get("password"));
                        break;

                    case NetworkEventType.SUPPORT:
                        params.put("book_id", requestMap.get("book_id"));
                        params.put("comment", requestMap.get("comment"));
                        params.put("comment1", requestMap.get("comment1"));
                        break;

                    case NetworkEventType.FMR_POINTS:
                        params.put("user_id", requestMap.get("user_id"));
                        break;

                    case NetworkEventType.ALL_BOOKINGID:
                        params.put("user_id", requestMap.get("user_id"));
                        break;

                    case NetworkEventType.FEEDBACK_OTP:
                        params.put("email", requestMap.get("email"));
                        params.put("otp", requestMap.get("otp"));
                        break;

                    case NetworkEventType.COUPON:
//                        params.put("email", requestMap.get("email"));
                        params.put("coupon_name", requestMap.get("coupon_name"));
                        break;



                    default:

                        return null;
                }

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }


    protected JsonObjectRequest getJsonObjectRequest(HashMap<String, String> requestMap) {


        JSONObject jsonObject = new JSONObject(requestMap);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestMethod, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseNetworkResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    setNeworkError();
                    Log.e("Error: ", error.getLocalizedMessage());
                } catch (Exception e) {
                    Log.e("Error: ", e.getLocalizedMessage());
                }
            }
        });


        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        return jsonObjectRequest;
    }


    public VolleyMultipartRequest getVolleyMultipartRequest(final String user_id, final String dl_country,  final String dl_country_issue, final String dl_issue_date, final String dlexpiry_date, final String customer_name,final  String dl_number, final String customer_dob, final String alternate_number, final String complete_address,final String type){
        this.userID = user_id;
        this.updateType = type;
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                parseNetworkResponse(resultResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                setNeworkError();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                switch (networkEventType) {
                    case NetworkEventType.LICENCE_DETAIL:
                    case NetworkEventType.LICENCE_UPDATE:
                        params.put("user_id", user_id);
                        params.put("dl_country", dl_country);
                        params.put("dl_country_issue", dl_country_issue);
                        params.put("dl_issue_date", dl_issue_date);
                        params.put("dlexpiry_date", dlexpiry_date);
                        params.put("customer_name", customer_name);
                        params.put("dl_number", dl_number);
                        params.put("customer_dob", customer_dob);
                        params.put("alternate_number", alternate_number);
                        params.put("complete_address", complete_address);
                        break;
                    case NetworkEventType.UPDATE_PROFILE:
                        params.put("user_id", user_id);
                        params.put("name", dl_country);
                        params.put("mobile", dl_country_issue);
                        params.put("address", dl_issue_date);
                        params.put("gender", dlexpiry_date);
                        params.put("about", customer_name);
                        params.put("nationality",dl_number );
                        break;
                    default:

                        return null;
                }

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                if(byteArray != null)
                params.put("file", new DataPart(userID + "_"+ updateType + "_profile_image.jpg",byteArray, "image/jpeg"));


                return params;
            }
        };
        return multipartRequest;
    }

    abstract Request getNetworkRequest(int eventType);

    abstract String getUrl();

    abstract HashMap<String, String> getRequestHeaders(int eventType);

    abstract String getJsonBody();

    abstract Response parseNetworkResponse(Object response);

    abstract void setNeworkError();


}
