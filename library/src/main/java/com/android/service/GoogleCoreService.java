package com.android.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.android.core.ProcObserver;
import com.android.core.WorkThread;

/**
 * Created by laole918 on 2016/5/5 0005.
 */
public class GoogleCoreService extends Service {

    private final static int GRAY_SERVICE_ID = 1001;
    private WorkThread mWorkThread;
//    private ProcObserver procObserver;

    @Override
    public void onCreate() {
        super.onCreate();
        mWorkThread = new WorkThread(this);
//        procObserver = new ProcObserver();
//        procObserver.startWatching();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GoogleCoreInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        mWorkThread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWorkThread.stop();
//        procObserver.stopWatching();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GoogleCoreInnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
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
