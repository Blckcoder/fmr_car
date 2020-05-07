package com.fmrnz.services;

import android.content.Context;
import android.content.Intent;

public  class CommonUtilities {
	
	// give your server registration url here
    public static String SERVER_URL = "http://azeemkhan.me/mediman/order_notification";

    // Google project id
    public static String SENDER_ID = "119492041600";

    /**
     * Tag used on log messages.
     */
    public static String TAG = "AndroidHive GCM";

    public static String DISPLAY_MESSAGE_ACTION =
            "com.androidhive.pushnotifications.DISPLAY_MESSAGE";

    public static String EXTRA_MESSAGE = "m";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
