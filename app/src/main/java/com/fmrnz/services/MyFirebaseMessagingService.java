package com.fmrnz.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.fmrnz.AlertActivity;
import com.fmrnz.utils.Config;
import com.fmrnz.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(remoteMessage.getData());

//                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
        }

    private void handleNotification(String message,String title) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            showNotificationMessage(getApplicationContext(), title, message, pushNotification);


           /* Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();*/

       /*    Intent intent = new Intent(this, AlertActivity.class);
            intent.putExtra("Message",message);
            getApplicationContext().startActivity(intent);*/



           /* Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();*/
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(Map<String,String> array) {

            String title = array.get("title");
            String message = array.get("body");
            String notificationType = array.get("type");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);

        Intent intent = new Intent(this, AlertActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Type",notificationType);
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
        showNotificationMessage(getApplicationContext(), title, message, intent);


//            if(notificationType.equalsIgnoreCase("offer")){
//                Intent offerintent = new Intent(MyFirebaseMessagingService.this, FmrOfferActivity.class);
//                startActivity(offerintent);
//
//            }
//            else{
//                Intent referintent = new Intent(MyFirebaseMessagingService.this, ReferActivity.class);
//                startActivity(referintent);
//            }

//                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", message);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSound();
//                showNotificationMessage(getApplicationContext(), title, message, pushNotification);






//            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//
//                Intent offerintent = new Intent(MyFirebaseMessagingService.this, AlertActivity.class);
//                offerintent.putExtra("Type",notificationType);
//                startActivity(offerintent);
//
//                // check for image attachment
//
//                showNotificationMessage(getApplicationContext(), title, message, offerintent);
//
////                // app is in foreground, broadcast the push message
////                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
////                pushNotification.putExtra("message", message);
////                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
////
////                // play notification sound
////                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
////                notificationUtils.playNotificationSound();
//            } else {
//                // app is in background, show the notification in notification tray
//
//
//                Intent offerintent = new Intent(MyFirebaseMessagingService.this, AlertActivity.class);
//                offerintent.putExtra("Type",notificationType);
//                startActivity(offerintent);
//
//                // check for image attachment
//
//                showNotificationMessage(getApplicationContext(), title, message, offerintent);
//
//            }
        }


    /*private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {

            String title = json.getString("title");
            String message = json.getString("body");
            String notificationType = json.getString("type");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);


            if(notificationType.equalsIgnoreCase("offer")){
                Intent offerintent = new Intent(MyFirebaseMessagingService.this, FmrOfferActivity.class);
                startActivity(offerintent);

            }
            else{
                Intent referintent = new Intent(MyFirebaseMessagingService.this, ReferActivity.class);
                startActivity(referintent);
            }


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment

                    showNotificationMessage(getApplicationContext(), title, message, resultIntent);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }*/

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent, imageUrl);
    }
}
