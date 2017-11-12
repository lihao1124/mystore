package com.bj.glz.speedtestlib;

import android.net.TrafficStats;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;


public class Sampler extends Thread {

    private long mLastReceive;
    private long mLastSend;
    private long mInterval;
    private long mDuration;

    private OnIntervalListener mIntervalListener;
    private List<SamplerInfo> mSamplerInfoList = new ArrayList<>();

    public void setOnIntervalListener(OnIntervalListener onIntervalListener) {
        mIntervalListener = onIntervalListener;
    }

    public Sampler(long duration, long interval) {
        mInterval = interval;
        mDuration = duration;
    }

    private long getTotalRxBytes() {  //获取总的接受字节数，包含Mobile和WiFi等
        try {
            long bytes = TrafficStats.getTotalRxBytes();
            return bytes == TrafficStats.UNSUPPORTED ? 0 : bytes;
        } catch (Exception e) {
            return mLastReceive;
        }
    }

    private long getTotalTxBytes() {  //总的发送字节数，包含Mobile和WiFi等
        return TrafficStats.getTotalTxBytes() == TrafficStats.UNSUPPORTED ? mLastSend : (TrafficStats.getTotalTxBytes());
    }

    @Override
    public void run() {
        super.run();
        Thread.currentThread().setName("Sampler Thread");
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        mLastReceive = getTotalRxBytes();
        mLastSend = getTotalTxBytes();
        long startTime = System.currentTimeMillis();
        if (mIntervalListener != null) {
            mIntervalListener.onStart();
        }
        while (System.currentTimeMillis() < startTime + mDuration) {
            try {
                Thread.sleep(mInterval);
            } catch (InterruptedException e) {
            }
            if (mIntervalListener != null) {
                SamplerInfo samplerInfo = new SamplerInfo();
                long rcv = getTotalRxBytes();
                long send = getTotalTxBytes();
                samplerInfo.download = rcv - mLastReceive;
                samplerInfo.download = samplerInfo.download * 8;
                mLastReceive = rcv;
                samplerInfo.upload = send - mLastSend;
                samplerInfo.upload = samplerInfo.upload * 8;
                mLastSend = send;
                long totalDownload = 0;
                long totalUpload = 0;
                int size = mSamplerInfoList.size();
                for (SamplerInfo info : mSamplerInfoList) {
                    totalDownload += info.download;
                    totalUpload += info.upload;
                }
                if (size != 0) {
                    samplerInfo.downloadAvg = totalDownload / size;
                    samplerInfo.uploadAvg = totalUpload / size;
                }
                mSamplerInfoList.add(samplerInfo);
                mIntervalListener.onInterval(samplerInfo);
            }
        }
        if (mIntervalListener != null) {
            if (mSamplerInfoList.size() > 0) {
                mIntervalListener.onEnd(mSamplerInfoList.get(mSamplerInfoList.size() - 1));
            } else {
                mIntervalListener.onEnd(null);
            }
        }
        mSamplerInfoList.clear();
    }

    public void safeStop() {
        mDuration = 0;
    }

    public interface OnIntervalListener {
        void onStart();

        void onInterval(SamplerInfo info);

        void onEnd(SamplerInfo info);
    }

    public static class SamplerInfo {
        public long download;
        public long upload;
        public long downloadAvg;
        public long uploadAvg;
    }
}