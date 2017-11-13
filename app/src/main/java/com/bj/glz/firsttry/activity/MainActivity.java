package com.bj.glz.firsttry.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseActivity;
import com.bj.glz.firsttry.common.utils.FragmentFactory;
import com.bj.glz.firsttry.databinding.ActivityMainBinding;
import com.bj.glz.firsttry.fragment.SpeedTestFragment;
import com.wifiyou.utils.Logcat;
import com.wifiyou.utils.MainThreadPostUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener{

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
        return R.layout.activity_main;
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment tab1 = fm.findFragmentByTag("tab1");
        if(tab1 == null)
        transaction.add(R.id.fragment_container, FragmentFactory.getSpeedTestFragment(),"tab1");

        transaction.commitAllowingStateLoss();
    }

    public void addFragment(){

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
}
