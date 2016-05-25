package com.android.utils;

import android.util.Log;

/**
 * Created by laole918 on 2016/5/25 0025.
 */
public class LogUtils {
    //http://stalkeryan.tumblr.com/
    public static boolean IS_DEBUG = true;

    public static void d(String tag, String msg) {
        if(IS_DEBUG) {
            Log.d(tag, msg);
        }
    }
}
