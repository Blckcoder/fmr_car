package com.fmrnz.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.fmrnz.utils.AppConstant;

/**
 * Created by eurysinfosystems on 28/05/18.
 */

public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");
        if(!TextUtils.isEmpty(referrer)){
            AppConstant.referalCode = referrer;
        }
        Log.i("REFERRER",referrer);
    }
}
