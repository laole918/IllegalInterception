package com.android;

import android.content.Context;
import android.content.Intent;

import com.android.service.GoogleCoreService;
import com.android.utils.LogUtils;

/**
 * Created by laole918 on 2016/5/5 0005.
 */
public class GoogleCore {

    public static void init(Context context) {
        Intent intent = new Intent(context, GoogleCoreService.class);
        context.startService(intent);
    }

    public static void setDebug(boolean debug) {
        LogUtils.IS_DEBUG = debug;
    }
}
