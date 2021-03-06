package com.android.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.android.core.WorkThread;

/**
 * Created by laole918 on 2016/5/5 0005.
 */
public class GoogleCoreService extends Service {

    private final static int GRAY_SERVICE_ID = 1001;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, getSimpleNotification(this));//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GoogleCoreInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, getSimpleNotification(this));
        }
        WorkThread.start(this);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static Notification getSimpleNotification(Context context) {
        if (Build.VERSION.SDK_INT < 18) {
            return new Notification();
        } else {
            return new Notification.Builder(context)
                    .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                    .build();
        }
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static class GoogleCoreInnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, getSimpleNotification(this));
            //stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
