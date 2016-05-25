package com.android.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laole918 on 2016/5/5 0005.
 */
public class Utils {

    public static final String TAG = Utils.class.getSimpleName();

    public static boolean isTopApp(Context context, String packageName) {
        if(TextUtils.isEmpty(packageName)) return false;
        ActivityManager manager = ((ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE));
        if (Build.VERSION.SDK_INT >= 21) {
            List<ActivityManager.RunningAppProcessInfo> pis = manager.getRunningAppProcesses();
            ActivityManager.RunningAppProcessInfo topAppProcess = pis.get(0);
            if (topAppProcess != null && topAppProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                String topPackageName = topAppProcess.processName;
                LogUtils.d(TAG, "TopPackageName:" + topPackageName);
                if(topPackageName.equals(packageName)) {
                    return true;
                }
            }
        } else {
            //getRunningTasks() is deprecated since API Level 21 (Android 5.0)
            List localList = manager.getRunningTasks(1);
            ActivityManager.RunningTaskInfo localRunningTaskInfo = (ActivityManager.RunningTaskInfo)localList.get(0);
            String topPackageName = localRunningTaskInfo.topActivity.getPackageName();
            LogUtils.d(TAG, "TopPackageName:" + topPackageName);
            if(topPackageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getBrowsAblePackages(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.google.com"));
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER);
        List<String> packages = new ArrayList<>();
        for (ResolveInfo info : infos) {
            LogUtils.d(TAG, "BrowsAblePackages:" + info.activityInfo.packageName);
            LogUtils.d(TAG, "BrowsAbleName:" + info.activityInfo.name);
            packages.add(info.activityInfo.packageName);
        }
        return packages;
    }

}
