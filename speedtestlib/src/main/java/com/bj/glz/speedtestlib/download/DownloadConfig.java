package com.bj.glz.speedtestlib.download;

import java.util.Arrays;
import java.util.List;


public class DownloadConfig {

    private List<String> mDownloadUrl;

    private long mTestDuration;

    private long mCallbackInterval;

    public List<String> getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(List<String> downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public long getTestDuration() {
        return mTestDuration;
    }

    public void setTestDuration(long testDuration) {
        mTestDuration = testDuration;
    }

    public long getCallbackInterval() {
        return mCallbackInterval;
    }

    public void setCallbackInterval(long callbackInterval) {
        mCallbackInterval = callbackInterval;
    }

    public static DownloadConfig getDefaultConfig() {
        DownloadConfig downloadConfig = new DownloadConfig();
        downloadConfig.setCallbackInterval(1000);
        downloadConfig.setTestDuration(10000);
        downloadConfig.setDownloadUrl(Arrays.asList("http://speedtest.tele2.net/10GB.zip",
                "http://www.bing.com",
                "http://stackoverflow.com",
                "https://www.facebook.com",
                "https://www.google.com"));
        return downloadConfig;
    }
}
