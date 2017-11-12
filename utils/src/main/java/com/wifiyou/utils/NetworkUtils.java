/**
 * @(#)NetworkUtils.java, 2013-5-10. Copyright 2013 Yodao, Inc. All rights
 *                        reserved. YODAO PROPRIETARY/CONFIDENTIAL. Use is
 *                        subject to license terms.
 */
package com.wifiyou.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 说明：
 * 
 * @author jianyu E-mail: yangjianyu@rd.netease.com
 * @version 创建时间：2013-5-10 下午1:29:25
 */
public class NetworkUtils {

    public static final String TAG = "NetworkUtils";

    /** 没有网络 */
    public static final int NETWORKTYPE_INVALID = 0;

    /** wap网络 */
    public static final int NETWORKTYPE_WAP = 1;

    /** 2G网络 */
    public static final int NETWORKTYPE_2G = 2;

    /** 3G和3G以上网络，或统称为快速网络 */
    public static final int NETWORKTYPE_3G = 3;

    /** wifi网络 */
    public static final int NETWORKTYPE_WIFI = 4;

    public static boolean checkNetwork(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager.getActiveNetworkInfo() != null) {
                return manager.getActiveNetworkInfo().isAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @param context
     * @return
     */
    public static boolean isWifiAvailable(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            State state = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState();
            if (state != null && state == State.CONNECTED) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean is3G(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = manager.getActiveNetworkInfo();
            return activeNetInfo != null
                    && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    public static int getNetWorkType(Context context) {
        int mNetWorkType = 0;
        try {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                String type = networkInfo.getTypeName();
                if (type.equalsIgnoreCase("WIFI")) {
                    mNetWorkType = NETWORKTYPE_WIFI;
                } else if (type.equalsIgnoreCase("MOBILE")) {
                    String proxyHost = android.net.Proxy.getDefaultHost();
                    mNetWorkType = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G
                            : NETWORKTYPE_2G)
                            : NETWORKTYPE_WAP;
                }
            } else {
                mNetWorkType = NETWORKTYPE_INVALID;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mNetWorkType;
    }

    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
    
    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
                return info.isConnected();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
