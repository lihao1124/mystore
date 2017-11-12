package com.bj.glz.firsttry.base;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.bj.glz.firsttry.R;

/**
 * Created by lihao on 2017/10/30.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity{

    protected T dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int layoutId = getLayoutId();
        if (layoutId != -1) {
            dataBinding = DataBindingUtil.setContentView(this, layoutId);
            readIntent();
            initToolBar();
            initControls(savedInstanceState);
            setListeners();
        }
    }

    private void initToolBar() {
        Toolbar toolbar = getActivityToolBar();
        if (toolbar != null) {
            String title = getActivityTitle();
            if (!TextUtils.isEmpty(title)) {
                toolbar.setTitle(title);
            } else {
                toolbar.setTitle(R.string.app_name);
            }
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected abstract String getActivityTitle();

    protected abstract Toolbar getActivityToolBar();

    protected abstract int getLayoutId();

    protected abstract void setListeners();

    protected abstract void initControls(Bundle savedInstanceState);

    protected abstract void readIntent();

}
