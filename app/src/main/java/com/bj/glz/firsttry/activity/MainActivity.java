package com.bj.glz.firsttry.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseActivity;
import com.bj.glz.firsttry.common.manager.LhFragmentManager;
import com.bj.glz.firsttry.common.utils.FragmentFactory;
import com.bj.glz.firsttry.databinding.ActivityMainBinding;
import com.bj.glz.speedtestlib.SpeedTester;
import com.wifiyou.utils.Logcat;
import com.wifiyou.utils.MainThreadPostUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

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
        dataBinding.radioGroup.setOnCheckedChangeListener(this);
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getCheckedRadioButtonId()){
            case R.id.btn_speedtest:
                lhFragmentManager.showFragment(FragmentFactory.Type.TAB_MAIN);
                lhFragmentManager.hideFragment(FragmentFactory.Type.TAB_HISTORY);
                lhFragmentManager.hideFragment(FragmentFactory.Type.TAB_SETTING);
                break;
            case R.id.btn_history:
                lhFragmentManager.showFragment(FragmentFactory.Type.TAB_HISTORY);
                lhFragmentManager.hideFragment(FragmentFactory.Type.TAB_MAIN);
                lhFragmentManager.hideFragment(FragmentFactory.Type.TAB_SETTING);
                break;
            case R.id.btn_setting:
                lhFragmentManager.showFragment(FragmentFactory.Type.TAB_SETTING);
                lhFragmentManager.hideFragment(FragmentFactory.Type.TAB_HISTORY);
                lhFragmentManager.hideFragment(FragmentFactory.Type.TAB_MAIN);
                break;
        }
    }

}
