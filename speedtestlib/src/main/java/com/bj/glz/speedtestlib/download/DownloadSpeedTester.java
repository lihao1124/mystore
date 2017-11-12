package com.bj.glz.speedtestlib.download;


import com.bj.glz.speedtestlib.Sampler;

public class DownloadSpeedTester {

    private DownloadConfig mDownloadConfig;
    private boolean mIsSampling = false;
    private Sampler.OnIntervalListener mDownloadTestInfoListener;
    private Downloader mDownloader;
    private Sampler mSampler;

    public void setConfig(DownloadConfig downloadConfig) {
        mDownloadConfig = downloadConfig;
    }

    public boolean start() {
        if (mIsSampling) {
            return false;
        }
        mIsSampling = true;
        if (mDownloadConfig == null) {
            mDownloadConfig = DownloadConfig.getDefaultConfig();
        }
        mDownloader = new Downloader(mDownloadConfig);
        mSampler =
                new Sampler(mDownloadConfig.getTestDuration(), mDownloadConfig.getCallbackInterval());
        mSampler.setOnIntervalListener(new Sampler.OnIntervalListener() {
            @Override
            public void onStart() {
                if (mDownloadTestInfoListener != null) {
                    mDownloadTestInfoListener.onStart();
                }
            }

            @Override
            public void onInterval(Sampler.SamplerInfo info) {
                if (mDownloadTestInfoListener != null) {
                    mDownloadTestInfoListener.onInterval(info);
                }
            }

            @Override
            public void onEnd(Sampler.SamplerInfo info) {
                if (mDownloadTestInfoListener != null) {
                    mDownloadTestInfoListener.onEnd(info);
                }
                mIsSampling = false;
            }
        });
        mDownloader.start();
        mSampler.start();
        return true;
    }

    public boolean isSampling() {
        return mIsSampling;
    }

    public void stop() {
        if (mSampler != null) {
            mSampler.safeStop();
        }
        if (mDownloader != null) {
            mDownloader.safeStop();
        }
    }

    public void setListener(Sampler.OnIntervalListener downloadTestInfoListener) {
        mDownloadTestInfoListener = downloadTestInfoListener;
    }
}
