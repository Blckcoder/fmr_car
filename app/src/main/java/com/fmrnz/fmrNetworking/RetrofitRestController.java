package com.fmrnz.fmrNetworking;

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRestController {
  //  http://thebhakti.in/SamastWebServices/products.php

    public static final String BASE_URL_GETNEWS = "http://technowhizzit.com/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_GETNEWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getLogBuilder().build())  // TODO to enable full log functionality in
                    .build();
        }
        return retrofit;
    }
    //http://atechnos.net/cms/news_api/api/get_news.php?lan=null&cat=Topstories
    @NonNull
    private static OkHttpClient.Builder getLogBuilder() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient;
    }
}