package com.android;

import android.content.Context;
import android.content.Intent;

import com.android.service.GoogleCoreService;

/**
 * Created by laole918 on 2016/5/5 0005.
 */
public class GoogleCore {

    public static void init(Context context) {
        Intent intent = new Intent(context, GoogleCoreService.class);
        context.startService(intent);
    }
}
