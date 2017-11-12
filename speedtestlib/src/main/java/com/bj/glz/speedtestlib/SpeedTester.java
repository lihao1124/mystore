package com.bj.glz.speedtestlib;


import com.bj.glz.speedtestlib.download.DownloadConfig;
import com.bj.glz.speedtestlib.download.Downloader;
import com.bj.glz.speedtestlib.uploader.UploadConfig;
import com.bj.glz.speedtestlib.uploader.Uploader;

public class SpeedTester {
    private DownloadConfig mDownloadConfig;
    private UploadConfig mUploadConfig;
    private boolean mIsSampling = false;
    private Sampler.OnIntervalListener mDownloadTestInfoListener;
    private Downloader mDownloader;
    private Uploader mUploader;
    private Sampler mSampler;

    public void setDownloadConfig(DownloadConfig downloadConfig) {
        mDownloadConfig = downloadConfig;
    }

    public void setUpConfig(UploadConfig uploadConfig) {
        mUploadConfig = uploadConfig;
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
        mSampler = new Sampler(mDownloadConfig.getTestDuration(), mDownloadConfig.getCallbackInterval());
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
        mUploader = new Uploader();
        mUploader.start();
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
        if (mUploader != null) {
            mUploader.safeStop();
        }
    }

    public void setListener(Sampler.OnIntervalListener downloadTestInfoListener) {
        mDownloadTestInfoListener = downloadTestInfoListener;
    }
}
