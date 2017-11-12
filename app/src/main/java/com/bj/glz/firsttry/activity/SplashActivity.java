package com.bj.glz.firsttry.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseActivity;
import com.bj.glz.firsttry.databinding.ActivitySplashBinding;
import com.bj.glz.firsttry.manager.IntentManager;

/**
 * Created by lihao on 2017/10/30.
 */

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private boolean mAdLoaded;
    private static final int DELAY_TIME = 3000;
    private int count = 0;

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    protected Toolbar getActivityToolBar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setListeners() {
        dataBinding.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.startMainActivity(SplashActivity.this);
                finish();
            }
        });
    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        dataBinding.adContainerSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mAdLoaded) {
                    IntentManager.startMainActivity(SplashActivity.this);
                    finish();
                }
            }
        }, DELAY_TIME);
    }

    @Override
    protected void readIntent() {

    }

    private void onAdLoaded() {
        if (isFinishing()) return;
        mAdLoaded = true;
        dataBinding.tvTime.setVisibility(View.VISIBLE);
        dataBinding.tvTime.postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentManager.startMainActivity(SplashActivity.this);
                finish();
            }
        }, DELAY_TIME);
        /*dataBinding.tvTime.setText(DELAY_TIME / 1000 + "s");
        dataBinding.ivLogoSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (++count < DELAY_TIME / 1000) {
                    dataBinding.tvTime.setText(DELAY_TIME / 1000 - count + "s");
                    dataBinding.ivLogoSplash.postDelayed(this, 1000);
                } else {
                    IntentManager.startMainActivity(SplashActivity.this);
                }
            }
        }, 1000);*/
    }

}
