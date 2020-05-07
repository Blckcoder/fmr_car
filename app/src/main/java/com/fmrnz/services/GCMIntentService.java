package com.fmrnz.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;


public class GCMIntentService  extends IntentService {
    // Sets an ID for the notification, so it can be updated
    public static final int notifyID = 9001;
    private static final String TAG = "GCMIntentService";
    private String notificationMessage;

    NotificationCompat.Builder builder;

    public GCMIntentService() {
        super(CommonUtilities.SENDER_ID);
    }



    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                Log.i("NOTI" + extras.get(CommonUtilities.EXTRA_MESSAGE),"");
                parse("" + extras.get(CommonUtilities.EXTRA_MESSAGE));
                sendNotification("" + extras.get(CommonUtilities.EXTRA_MESSAGE));

            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

//    private void parse(String message)
//    {
//        NotificationDatabase notificationDatabase = new NotificationDatabase(this);
//
//        try {
//            JSONArray searchArray = new JSONArray(message);
//            for(int i=0;i<searchArray.length();i++)
//            {
//                NotificationData notificationData = new NotificationData();
//                JSONObject object = searchArray.getJSONObject(i);
//                String medName = object.getString("medicine_name");
//                String medQua = object.getString("medicine_quantity");
//                String medPrice = object.getString("medicine_price");
//                String sumTotal = object.getString("sub_total");
//                String orderId = object.getString("order_id");
//                String orderDetailId = object.getString("order_details_id");
//                if(medName!=null)
//                {
//                    notificationData.setMedicineName(medName);
//                }
//                if(medQua!=null)
//                {
//                    notificationData.setMedicineQuantity(medQua);
//                }
//                if(medPrice!=null)
//                {
//                    notificationData.setMedicinePrice(medPrice);
//                }
//                if(sumTotal!=null)
//                {
//                    notificationData.setSubTotal(sumTotal);
//                }
//                if(orderId!=null)
//                {
//                    notificationData.setOrderId(orderId);
//                }
//                if(orderDetailId!=null)
//                {
//                    notificationData.setOrder_details_id(orderDetailId);
//                }
//                notificationDatabase.addNotification(notificationData);
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private void sendNotification(String msg) {
//        Toast.makeText(this,"GCMIntentService" + this.getPackageName().toString(), Toast.LENGTH_LONG).show();

        //Intent resultIntent = new Intent(this, MedicineBillDetail.class);
      //  Intent resultIntent = new Intent(this, GetMyMedicineActivity.class);
//       resultIntent.putExtra("jsonString", msg);
//            resultIntent.putExtra("Text","notification");
//            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
//                    resultIntent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mNotifyBuilder;
            NotificationManager mNotificationManager;

            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//            mNotifyBuilder = new NotificationCompat.Builder(this)
//                    .setContentTitle("Successful")
//                    .setContentText("Medicine request with Order Id " + notificationMessage + "has been placed successfully")
                //    .setSmallIcon(R.drawable.ic_mediman);
            // Set pending intent
         //   mNotifyBuilder.setContentIntent(resultPendingIntent);

            // Set Vibrate, Sound and Light
            int defaults = 0;
            defaults = defaults | Notification.DEFAULT_LIGHTS;
            defaults = defaults | Notification.DEFAULT_VIBRATE;
            defaults = defaults | Notification.DEFAULT_SOUND;

//            mNotifyBuilder.setDefaults(defaults);
//            // Set the content for Notification
//            mNotifyBuilder.setContentText("Medicine request with Order Id " + msg + "has been placed successfully");
//            // Set autocancel
//            mNotifyBuilder.setAutoCancel(true);
//            // Post a notification
//            mNotificationManager.notify(notifyID, mNotifyBuilder.build());
        }


    private void parse(String notificationMsg)
    {
        try {
            JSONObject jsonObject = new JSONObject(notificationMsg);
            notificationMessage = jsonObject.getString("order_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
