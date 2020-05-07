package com.fmrnz.fmrNetworking;

import com.fmrnz.pojo.RespomSIgnUpforOtpCheck;
import com.fmrnz.pojo.ResponseCaRdDetail;
import com.fmrnz.pojo.ResponseForShareAndEarn;
import com.fmrnz.pojo.ResponseToken;
import com.fmrnz.pojo.ServerResponse;
import com.fmrnz.pojo.updatepassword.UpdatePasswordResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AtechnosServerService {

    //   http://technowhizzit.com/findmyride/Paypal/index.php
    //  http://technowhizzit.com/findmyride/Ride/time
    @GET("findmyride/Paypal/index.php")
    Call<ResponseToken> getToken();


    @GET("findmyride/Ride/referearn")
    Call<ResponseForShareAndEarn> getResponseForShareAndEarn();

    @GET("findmyride/Ride/time")
    Call<ServerResponse> getServerTime();

    @FormUrlEncoded
    @POST("findmyride/Ride/isrepeat")
    Call<RespomSIgnUpforOtpCheck> postSignupRequest(@Field("email") String email, @Field("phone") String mobile);

    @FormUrlEncoded
    @POST("findmyride/Ride/isrepeat")
    Call<UpdatePasswordResponse> postUpdatepasswordVerifyRequest(@Field("email") String email,@Field("phone") String mobile);

    @FormUrlEncoded
    @POST("findmyride/Paypal/index.php")
    Call<ResponseCaRdDetail> postTokenAndAmount(@Field("NONCE") String nonce, @Field("amnt") String amnt);
//
//    @FormUrlEncoded
//    @POST("findmyride/Paypal/index.php")
//    Call<ResponseCaRdDetail> postTokenAndAmount(@Body ResponseRawDataForCard responseRawDataForCard);


}