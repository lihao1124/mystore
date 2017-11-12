package com.wifiyou.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         4/22/16
 */
public class GlobalConfig {
    private static Context sContext;

    private static WifiManager sSystemWifiManager;

    private static ConnectivityManager sSystemConnectivityManager;

    private static String sUdid;

    public static final String UDID = "udid";

    public static void init(Context context) {
        sContext = context;
        sUdid = UDIDUtil.getUDID(sContext);
    }

    public static Context getAppContext() {
        return sContext;
    }

    public static String getUdId() {
        return sUdid;
    }

    public static WifiManager getSystemWifiManager() {
        if (sSystemWifiManager == null) {
            sSystemWifiManager = (WifiManager) sContext.getSystemService(Context.WIFI_SERVICE);
        }
        return sSystemWifiManager;
    }

    public static ConnectivityManager getSystemConnectivityManager() {
        if (sSystemConnectivityManager == null) {
            sSystemConnectivityManager =
                    (ConnectivityManager) GlobalConfig.getAppContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);
        }
        return sSystemConnectivityManager;
    }
}
