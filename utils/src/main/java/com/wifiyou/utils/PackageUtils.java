package com.wifiyou.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         4/26/16
 */
public class PackageUtils {

    public static class ApkPackageInfo {
        public String name;
        public String packageName;
        public int versionCode = -1;
        public String versionName;
    }

    public static Drawable getPackageIconDrawable(String packageName) {
        try {
            PackageManager packageManager = GlobalConfig.getAppContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getPackageInfo(
                    packageName, 0).applicationInfo;
            return applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersion() {
        String version = "1.0.0";
        PackageManager packageManager = GlobalConfig.getAppContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    GlobalConfig.getAppContext().getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static int getVersionCode() {
        int version = 1000;
        PackageManager packageManager = GlobalConfig.getAppContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    GlobalConfig.getAppContext().getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static String getPackageName() {
        String packageName = "com.wifiyou.signal";
        PackageManager packageManager = GlobalConfig.getAppContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    GlobalConfig.getAppContext().getPackageName(), 0);
            packageName = packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    public static PackageInfo getPackageInfo() {
        try {
            final Context context = GlobalConfig.getAppContext();
            PackageManager pm = context.getPackageManager();
            return pm.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
        }

        return null;
    }

    /*
     * require <uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static boolean isMyPackageRunningOnTop() {
        ActivityManager am =
                (ActivityManager) GlobalConfig.getAppContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> infos = am.getRunningTasks(1);
        if (infos != null && !infos.isEmpty()) {
            ActivityManager.RunningTaskInfo info = infos.get(0);
            ComponentName componentName = info.topActivity;
            if (componentName != null
                    && componentName.getPackageName().equals(GlobalConfig.getAppContext().getPackageName())) {
                return true;
            }
        }
        return false;
    }


    public static Map<String, Integer> getPackageInfoById(Context context) {
        PackageManager pm = context.getPackageManager();//获取app
        List<ApplicationInfo> infos = pm.getInstalledApplications(0);
        HashMap<String, Integer> map = new HashMap<>();
        for (ApplicationInfo info : infos) {
            map.put(info.packageName, info.uid);
        }
        return map;
    }


    public static PackageInfo getPackageInfo(String packageName, Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();//获取app
        return pm.getPackageInfo(packageName, 0);
    }

    public static ApplicationInfo getApplicationInfo(String packageName, Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();//获取app
        return pm.getApplicationInfo(packageName, 0);
    }

    public static String getApplicationLabel(String packageName, Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        String appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        return appName;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        Intent mIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            return true;
        } else {
            return false;
        }
    }

    public static String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        String applicationName;
        try {
            packageManager = GlobalConfig.getAppContext().getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (Exception e) {
            applicationName = "";
        }

        return applicationName;
    }


    public static void tryStartActivityOrMarket(Context context, String pkg) {
        try {
            PackageInfo packageInfo = PackageUtils.getPackageInfo(pkg, context);
            if (packageInfo != null) {
                try {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
                    context.startActivity(intent);
                } catch (Exception e) {
                }
            } else {
                ActivityUtils.goToGooglePlay(context, "market://details?id=" + pkg);
            }
        } catch (PackageManager.NameNotFoundException e) {
            ActivityUtils.goToGooglePlay(context, "market://details?id=" + pkg);
        }
    }
}