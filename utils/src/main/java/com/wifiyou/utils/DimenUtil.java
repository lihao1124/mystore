package com.wifiyou.utils;

import android.content.Context;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         6/13/16
 */
public class DimenUtil {

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static float spToPixels(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }

    public static float dpToPixels(Context context, float dp) {
        float scaledDensity = context.getResources().getDisplayMetrics().density;
        return dp * scaledDensity;
    }

    public static float pixelsToDp(Context context,float px){
        float scaledDensity = context.getResources().getDisplayMetrics().density;
        return px/scaledDensity;
    }


}
