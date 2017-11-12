package com.wifiyou.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;

import java.util.ArrayList;
import java.util.List;

public class TrafficStatsUtil {
    public static List<TrafficInfo> getWifiAllTraffic(Context context) throws Exception {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> lais = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<TrafficInfo> applicationInfos = new ArrayList<>(lais.size());
        for (ApplicationInfo ai : lais) {
            long rcv = TrafficStats.getUidRxBytes(ai.uid);
            long send = TrafficStats.getUidTxBytes(ai.uid);
            TrafficInfo trafficInfo = new TrafficInfo();
            trafficInfo.applicationPackageName = ai.packageName;
            trafficInfo.receive = rcv;
            trafficInfo.send = send;
            applicationInfos.add(trafficInfo);

        }
        return applicationInfos;
    }

    public static class TrafficInfo {

        private long receive;

        private long send;

        private String applicationPackageName;

        public long getReceive() {
            return receive;
        }

        public long getSend() {
            return send;
        }

        public String getApplicationPackageName() {
            return applicationPackageName;
        }
    }
}