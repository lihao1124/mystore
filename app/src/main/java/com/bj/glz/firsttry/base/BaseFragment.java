package com.bj.glz.firsttry.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by i on 2017/11/13.
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    private Context mContext;
    protected T dataBinding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mContext == null)
            mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        initView(savedInstanceState);
        setListener();
        return dataBinding.getRoot();
    }

    protected abstract void setListener();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getLayout();

    public Context getContext() {
        return mContext;
    }

}
