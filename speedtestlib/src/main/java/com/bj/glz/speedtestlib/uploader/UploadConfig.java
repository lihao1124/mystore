package com.bj.glz.speedtestlib.uploader;

import java.util.Arrays;
import java.util.List;

/**
 * @author dengzhiren@wifiyou.net (Dean)
 *         5/4/17
 */

public class UploadConfig {

    private List<String> mUploadUrl;

    private long mTestDuration;

    private long mCallbackInterval;

    public List<String> getUploadUrl() {
        return mUploadUrl;
    }

    public void setUploadUrl(List<String> uploadUrl) {
        mUploadUrl = uploadUrl;
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

    public static UploadConfig getDefaultConfig() {
        UploadConfig uploadConfig = new UploadConfig();
        uploadConfig.setCallbackInterval(1000);
        uploadConfig.setTestDuration(10000);
        uploadConfig.setUploadUrl(Arrays.asList("http://speedtest.tele2.net/10GB.zip",
                "http://www.bing.com",
                "http://stackoverflow.com",
                "https://www.facebook.com",
                "https://www.google.com"));
        return uploadConfig;
    }
}
