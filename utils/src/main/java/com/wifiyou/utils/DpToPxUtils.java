package com.wifiyou.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by 45011 on 2017/5/6.
 */

public class DpToPxUtils {
    private DpToPxUtils() {
    }

    public static int getPixelsFromDp(Activity activity, int size) {

        DisplayMetrics metrics = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

    }

    public static int getDpFromPixels(Activity activity, int size) {

        DisplayMetrics metrics = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return (size * DisplayMetrics.DENSITY_DEFAULT) / metrics.densityDpi;

    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }
}
