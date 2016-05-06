package com.android.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.GoogleCore;

/**
 * Created by laole918 on 2016/5/6 0006.
 */
public class SystemBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = SystemBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action;
        if(intent != null && (action = intent.getAction()) != null) {
            if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
                GoogleCore.init(context);
            } else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
                GoogleCore.init(context);
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                GoogleCore.init(context);
            } else if (Intent.ACTION_CAMERA_BUTTON.equals(action)) {
                GoogleCore.init(context);
            } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                GoogleCore.init(context);
            }
            Log.d(TAG, action);
        }
    }
}
