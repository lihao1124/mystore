package com.bj.glz.speedtestlib.download;

import android.os.Process;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class Downloader extends Thread {

    private DownloadConfig mDownloadConfig;
    private boolean mIsSampling = true;

    public void safeStop() {
        mIsSampling = false;
    }

    public Downloader(DownloadConfig downloadConfig) {
        mDownloadConfig = downloadConfig;
    }

    @Override
    public void run() {
        super.run();
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        long startTime = System.currentTimeMillis();
        long sampleMaxTime = mDownloadConfig.getTestDuration();
        List<String> urls = mDownloadConfig.getDownloadUrl();
        for (int i = 0; i < 10; i++) {
            try {
                URLConnection connection = new URL(urls.get(i % urls.size())).openConnection();
                connection.setUseCaches(false);
                connection.setReadTimeout(1000);
                connection.setConnectTimeout(1000);
                connection.connect();
                InputStream input = connection.getInputStream();
                try {
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) != -1 && mIsSampling && (startTime + sampleMaxTime > System.currentTimeMillis())) {
                    }
                } finally {
                    input.close();
                }
                if ((startTime + sampleMaxTime < System.currentTimeMillis()) || !mIsSampling) {
                    break;
                }
            } catch (Throwable e) {
            }
        }
    }
}
