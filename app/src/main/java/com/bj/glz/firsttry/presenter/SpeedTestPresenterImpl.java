package com.bj.glz.firsttry.presenter;


import com.bj.glz.speedtestlib.Sampler;
import com.bj.glz.speedtestlib.download.DownloadSpeedTester;
import com.bj.glz.speedtestlib.ping.PingTester;
import com.bj.glz.speedtestlib.uploader.Uploader;

/**
 * Created by 45011 on 2017/9/14.
 */

public class SpeedTestPresenterImpl implements ISpeedTestPresenter {
    private ISpeedTestView iView;
    private DownloadSpeedTester mDownloadSpeedTester;
    private Uploader mUploader;
    private Sampler mSampler;

    public SpeedTestPresenterImpl(ISpeedTestView view) {
        iView = view;
    }

    @Override
    public void startSpeedTest() {
        iView.onTestStart();
        pingTest();
    }

    public void pingTest() {
        PingTester pingTester = new PingTester();
        pingTester.setCallback(new PingTester.PingCallback() {
            @Override
            public void onPingCallback(PingTester.PingInfo pingInfo) {
                iView.onPingTest(pingInfo);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {
                iView.onPingTest(null);
            }
        });
        pingTester.start();
    }

    public void downloadTest() {
        mDownloadSpeedTester = new DownloadSpeedTester();
        mDownloadSpeedTester.setListener(new Sampler.OnIntervalListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onInterval(Sampler.SamplerInfo samplerInfo) {
                iView.onDownTest(samplerInfo, false);
            }

            @Override
            public void onEnd(Sampler.SamplerInfo samplerInfo) {
                iView.onDownTest(samplerInfo, true);
            }
        });
        mDownloadSpeedTester.start();
    }

    public void uploadTest() {
        mUploader = new Uploader();
        mSampler = new Sampler(10000, 1000);
        mSampler.setOnIntervalListener(new Sampler.OnIntervalListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onInterval(Sampler.SamplerInfo samplerInfo) {
                iView.onUpTest(samplerInfo, false);
            }

            @Override
            public void onEnd(Sampler.SamplerInfo samplerInfo) {
                iView.onUpTest(samplerInfo, true);
                iView.onTestFinish();
            }
        });
        mUploader.start();
        mSampler.start();
    }

    public void safeStop() {
        if (mDownloadSpeedTester != null)
            mDownloadSpeedTester.stop();
        if (mUploader != null)
            mUploader.safeStop();
        if (mSampler != null)
            mSampler.safeStop();
    }
}
