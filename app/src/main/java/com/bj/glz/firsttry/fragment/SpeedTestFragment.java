package com.bj.glz.firsttry.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseFragment;
import com.bj.glz.firsttry.common.utils.Utils;
import com.bj.glz.firsttry.databinding.FragmentSpeedTestBinding;
import com.bj.glz.firsttry.presenter.ISpeedTestPresenter;
import com.bj.glz.firsttry.presenter.ISpeedTestView;
import com.bj.glz.firsttry.presenter.SpeedTestPresenterImpl;
import com.bj.glz.speedtestlib.Sampler;
import com.bj.glz.speedtestlib.ping.PingTester;
import com.wifiyou.utils.MainThreadPostUtils;

/**
 * Created by i on 2017/11/13.
 */

public class SpeedTestFragment extends BaseFragment<FragmentSpeedTestBinding> implements ISpeedTestView, View.OnClickListener {

    private ISpeedTestPresenter mPresenter;
    private boolean isTesting;
    private boolean isPingEnd;
    private boolean isDownTestEnd;
    private boolean isUpTestEnd;
    private ObjectAnimator objectAnimator;
    private int mPing;
    private float fromDegree;
    private float toDegree;
    private boolean isCheckAll;
    private int scaleNum = 1;
    private View mBreathView;

    @Override
    protected void setListener() {
        dataBinding.btnStart.setOnClickListener(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter = new SpeedTestPresenterImpl(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_speed_test;
    }


    @Override
    public void onTestStart() {
        if (isTesting) return;
        isTesting = true;
        isDownTestEnd = false;
        isUpTestEnd = false;
        isPingEnd = false;
        dataBinding.llResult.setVisibility(View.VISIBLE);
        dataBinding.btnStart.setVisibility(View.GONE);
        animationBreath(dataBinding.llPingContent);
    }

    @Override
    public void onTestFinish() {

    }

    @Override
    public void onUpTest(Sampler.SamplerInfo samplerInfo, boolean finish) {
        if (finish) {
            if (isUpTestEnd) return;
            isUpTestEnd = true;
            MainThreadPostUtils.post(new Runnable() {
                @Override
                public void run() {
                    objectAnimator.end();
                    objectAnimator = null;
                    dataBinding.llUpContent.setAlpha(1);
                    // TODO: 2017/11/21  统一改用handler传递
                }
            });
            return;
        }
        if (samplerInfo != null) {
            final String formatSpeed = Utils.formatSpeed(samplerInfo.uploadAvg);
            MainThreadPostUtils.post(new Runnable() {
                @Override
                public void run() {
                    dataBinding.tvUpload.setText(formatSpeed);
//                    upDateCirle(dataBinding.progressBarUp, formatPercent(samplerInfo.uploadAvg));
//                    setArrowDegree(dataBinding.speedTestMeterArrow, samplerInfo.uploadAvg);
                }
            });
        }
    }

    @Override
    public void onDownTest(Sampler.SamplerInfo samplerInfo, boolean finish) {
        if (finish) {
            if (isDownTestEnd) return;
            isDownTestEnd = true;
            MainThreadPostUtils.post(new Runnable() {
                @Override
                public void run() {
                    animationBreath(dataBinding.llUpContent);
                    mPresenter.uploadTest();
                }
            });
            return;
        }
        if (samplerInfo != null) {
            final String formatSpeed = Utils.formatSpeed(samplerInfo.downloadAvg);
            MainThreadPostUtils.post(new Runnable() {
                @Override
                public void run() {
                    dataBinding.tvDownload.setText(formatSpeed);
//                    upDateCirle(dataBinding.progressBarDown, formatPercent(samplerInfo.downloadAvg));
//                    setArrowDegree(dataBinding.speedTestMeterArrow, samplerInfo.downloadAvg);
                }
            });
        }
    }

    @Override
    public void onPingTest(PingTester.PingInfo pingInfo) {
        if (pingInfo == null) {
            if (isPingEnd) return;
            isPingEnd = true;
            MainThreadPostUtils.post(new Runnable() {
                @Override
                public void run() {
                    animationBreath(dataBinding.llDownContent);
                    mPresenter.downloadTest();
                }
            });
            return;
        }
        if (pingInfo.getTime() > 1000) {
            mPing = 1000;
        } else {
            mPing = (int) pingInfo.getTime();
        }
        MainThreadPostUtils.post(new Runnable() {
            @Override
            public void run() {
                dataBinding.tvPing.setText(mPing + " ms");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mPresenter.startSpeedTest();
                break;
        }
    }

    private void animationBreath(View view) {
        if (objectAnimator != null) {
            objectAnimator.end();
            if (mBreathView != null) mBreathView.setAlpha(1);
            mBreathView = null;
        }
        mBreathView = view;
        objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.1f);
        objectAnimator.setDuration(700);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start();
    }
}
