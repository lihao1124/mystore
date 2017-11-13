package com.bj.glz.firsttry.fragment;

import android.os.Bundle;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseFragment;
import com.bj.glz.firsttry.databinding.FragmentSpeedTestBinding;

/**
 * Created by i on 2017/11/13.
 */

public class SpeedTestFragment extends BaseFragment<FragmentSpeedTestBinding> {
    @Override
    protected void initView(Bundle savedInstanceState) {
        dataBinding.tvTittle.setText("dada");
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_speed_test;
    }
}
