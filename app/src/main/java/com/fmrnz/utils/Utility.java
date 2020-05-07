package com.fmrnz.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by eurysinfosystems on 14/05/18.
 */

public class Utility {

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


    public static String convertStringIntoData1(String dateStr) {
        String date = null;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:s");
        try {
            Date parsedDate = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            date = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertStringIntoDataWithoutSecond(String dateStr) {
        String date = null;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date parsedDate = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            date = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertStringIntoDataWithTime(String dateStr) {
        String date = null;
        SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        try {
            Date parsedDate = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            date = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertStringIntoDataWithTimeForHour(String dateStr) {
        String date = null;
        SimpleDateFormat dt = new SimpleDateFormat("hh:mm");


        try {
            Date parsedDate = dt.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int months= cal.get(Calendar.MONTH);
            int dayofmonths= cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(parsedDate);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, months);
            cal.set(Calendar.DAY_OF_MONTH, dayofmonths);


            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            date = dt1.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertStringIntoData(String dateStr) {
        String date = null;
        SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date parsedDate = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
            date = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertStringIntoData2(String dateStr) {
        String date = null;
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedDate = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-yyyy");
            date = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertStringIntoMonthSate(String dateStr) {
        String date = null;
        SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date parsedDate = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
            date = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertDateTimeWithMonthName(String dateStr) {
        String date = null;
//        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            Date parsedDate = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-yyyy");
            date = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String dateTimeFormat(String dateStr) {
        String date = null;
//        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        try {
            Date parsedDate = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
            date = dt1.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
