package com.fmrnz;

import android.view.View;
import android.widget.Button;

import com.fmrnz.avlIndicatorLoding.AVLoadingIndicatorView;


/**
 * Created by eurysinfosystems on 20/04/17.
 */

public class Utils {

    //public static String userId;

//    public static UserModel userModel;


    public static void showProgressBar(AVLoadingIndicatorView progressIndicatorView) {
        progressIndicatorView.setVisibility(View.VISIBLE);

    }

    public static void hideProgressBar(AVLoadingIndicatorView progressIndicatorView) {
        if (progressIndicatorView != null)
            progressIndicatorView.setVisibility(View.GONE);
    }


    public static void showProgressBar(AVLoadingIndicatorView progressIndicatorView, Button signInButton) {
        if (progressIndicatorView != null) {

            progressIndicatorView.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
        }
    }

    public static void hideProgressBar(AVLoadingIndicatorView progressIndicatorView, Button signInButton) {
        if (progressIndicatorView != null) {

            progressIndicatorView.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
        }
    }

    public static double distanceBetweenAddress(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

//    public static void addGoogleAnalytics(Activity context, String screenName) {
//        NHPApplication application = (NHPApplication) (context.getApplication());
//        application.trackScreenView(screenName);
//    }
//
//    public static void addAnalyticsEvent(Activity context, String paramValue, String paramKey) {
//        Tracker t = ((NHPApplication) (context.getApplication())).getDefaultTracker();
//
//        t.send(new HitBuilders.EventBuilder().
//                setCategory("Events")
//                .setAction(paramKey)
//                .setLabel(paramValue).build()
//
//        );
//    }
}
