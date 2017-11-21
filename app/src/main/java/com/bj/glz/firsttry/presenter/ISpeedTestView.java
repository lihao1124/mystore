package com.bj.glz.firsttry.presenter;


import com.bj.glz.speedtestlib.Sampler;
import com.bj.glz.speedtestlib.ping.PingTester;

/**
 * Created by 45011 on 2017/9/14.
 */

public interface ISpeedTestView {
    void onTestStart();

    void onPingTest(PingTester.PingInfo pingInfo);

    void onDownTest(Sampler.SamplerInfo samplerInfo, boolean finish);

    void onUpTest(Sampler.SamplerInfo samplerInfo, boolean finish);

    void onTestFinish();
}
