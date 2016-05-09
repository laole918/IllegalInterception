package com.android.service;

import android.app.Application;

import com.android.GoogleCore;

/**
 * Created by laole918 on 2016/5/9 0009.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GoogleCore.init(this);
    }
}
