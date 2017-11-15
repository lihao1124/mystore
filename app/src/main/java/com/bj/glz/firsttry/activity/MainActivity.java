package com.bj.glz.firsttry.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseActivity;
import com.bj.glz.firsttry.common.manager.FragmentController;
import com.bj.glz.firsttry.common.utils.FragmentFactory;
import com.bj.glz.firsttry.databinding.ActivityMainBinding;
import com.bj.glz.firsttry.fragment.SpeedTestFragment;
import com.wifiyou.utils.Logcat;
import com.wifiyou.utils.MainThreadPostUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener{

    private long lastBackPressedTime = 0;
    private FragmentController mFCInstance;
    private Class mClass;
    private static final String TAB_MAIN = "tab1";
    private static final String TAB_HISTORY = "tab2";
    private static final String TAB_SETTING = "tab3";

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
        dataBinding.btnSpeedtest.setOnClickListener(this);
        dataBinding.btnHistory.setOnClickListener(this);
        dataBinding.btnSetting.setOnClickListener(this);
        dataBinding.fragmentContainer.setOnClickListener(this);
    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        mClass = FragmentFactory.getFragmentClass(FragmentFactory.Type.MAIN);
        mFCInstance = FragmentController.getInstance(this);
        mFCInstance.showFragment(mClass,TAB_MAIN);
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
        // TODO: 2017/11/15  onclick没反应
        switch (v.getId()){
            case R.id.btn_speedtest:
                FragmentController.getInstance(this).showFragment(mClass,TAB_MAIN);
                FragmentController.getInstance(this).hideFragment(TAB_HISTORY);
                FragmentController.getInstance(this).hideFragment(TAB_SETTING);
                break;
            case R.id.btn_history:
                FragmentController.getInstance(this).showFragment(mClass,TAB_HISTORY);
                FragmentController.getInstance(this).hideFragment(TAB_MAIN);
                FragmentController.getInstance(this).hideFragment(TAB_SETTING);
                break;
            case R.id.btn_setting:
                FragmentController.getInstance(this).showFragment(mClass,TAB_SETTING);
                FragmentController.getInstance(this).hideFragment(TAB_HISTORY);
                FragmentController.getInstance(this).hideFragment(TAB_MAIN);
                break;
        }
    }
}
