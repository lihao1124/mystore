package com.wifiyou.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         4/28/16
 */
public class ActivityUtils {

    public static boolean isForegroundRunning(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (activity.hasWindowFocus()) {
            return true;
        }
        return false;
    }

    public static boolean isPackageOnStackTop(Context context, String checkPackageName) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
            String packageName;
            if (Build.VERSION.SDK_INT > 21) {
                packageName = am.getRunningAppProcesses().get(0).processName;
            } else {
                packageName = am.getRunningTasks(1).get(0).topActivity.getPackageName();
            }
            if (TextUtils.equals(checkPackageName, packageName)) {
                return true;
            }
        } catch (Exception e) {
            //Security Exception  taskInfo may size == 0
        }
        return false;
    }

    public static void goToGooglePlay(Context context, String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.android.vending");
        intent.setData(Uri.parse(uri));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            try {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
            }
        } else {
            MainThreadPostUtils.toast(com.wifiyou.utils.R.string.google_play_not_found);
        }
    }
}