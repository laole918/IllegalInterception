package com.android.core;

import android.os.FileObserver;
import android.util.Log;

/**
 * Created by laole918 on 2016/5/10 0010.
 */
public class ProcObserver extends FileObserver {

    public static final String TAG = ProcObserver.class.getSimpleName();

    public ProcObserver() {
        super("/proc");
    }

    @Override
    public void onEvent(int event, String path) {
        switch (event) {
            case FileObserver.ALL_EVENTS:
                Log.d(TAG, "all path:" + path);
                break;
            case 1073742080:
                Log.d(TAG, "create path:" + path);
                break;
        }
    }
}
