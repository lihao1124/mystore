package com.bj.glz.firsttry.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseActivity;
import com.bj.glz.firsttry.common.utils.FragmentFactory;
import com.bj.glz.firsttry.databinding.ActivityMainBinding;

/**
 * Created by i on 2017/11/16.
 */
public class TestActivity extends BaseActivity<ActivityMainBinding> {
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
    }

    @Override
    protected void readIntent() {

    }
}
