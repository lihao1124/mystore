package com.bj.glz.firsttry.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseActivity;
import com.wifiyou.utils.Logcat;
import com.wifiyou.utils.MainThreadPostUtils;

public class MainActivity extends BaseActivity {

    private long lastBackPressedTime = 0;

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
        return R.layout.activity_main;//test
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initControls(Bundle savedInstanceState) {

    }

    @Override
    protected void readIntent() {

    }

    @Override
    public void onBackPressed() {
        try {
            if (System.currentTimeMillis() - lastBackPressedTime < 2000) {
                super.onBackPressed();
            } else {
                //devicesAnalytics(list);
                lastBackPressedTime = System.currentTimeMillis();
                MainThreadPostUtils.toast(R.string.exit_tip);
            }
        } catch (Exception e) {
            Logcat.e("MainActivity onBackPressed() exception", e);
        }
    }
}
