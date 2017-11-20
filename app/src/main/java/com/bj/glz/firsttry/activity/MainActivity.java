package com.bj.glz.firsttry.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseActivity;
import com.bj.glz.firsttry.common.manager.LhFragmentManager;
import com.bj.glz.firsttry.common.utils.FragmentFactory;
import com.bj.glz.firsttry.databinding.ActivityMainBinding;
import com.bj.glz.speedtestlib.SpeedTester;
import com.wifiyou.utils.Logcat;
import com.wifiyou.utils.MainThreadPostUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener, View.OnFocusChangeListener {

    private long lastBackPressedTime = 0;
    private LhFragmentManager lhFragmentManager;

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
        dataBinding.btnSpeedtest.setOnFocusChangeListener(this);
        dataBinding.btnHistory.setOnFocusChangeListener(this);
        dataBinding.btnSetting.setOnFocusChangeListener(this);
    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        lhFragmentManager = new LhFragmentManager(this);
        lhFragmentManager.showFragment(FragmentFactory.Type.TAB_MAIN);
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.btn_speedtest:
                if (hasFocus) {
                    lhFragmentManager.showFragment(FragmentFactory.Type.TAB_MAIN);
                } else {
                    lhFragmentManager.hideFragment(FragmentFactory.Type.TAB_MAIN);
                }
                break;
            case R.id.btn_history:
                if (hasFocus) {
                    lhFragmentManager.showFragment(FragmentFactory.Type.TAB_HISTORY);
                } else {
                    lhFragmentManager.hideFragment(FragmentFactory.Type.TAB_HISTORY);
                }
                break;
            case R.id.btn_setting:
                if (hasFocus) {
                    lhFragmentManager.showFragment(FragmentFactory.Type.TAB_SETTING);
                } else {
                    lhFragmentManager.hideFragment(FragmentFactory.Type.TAB_SETTING);
                }
                break;
        }
    }
}
