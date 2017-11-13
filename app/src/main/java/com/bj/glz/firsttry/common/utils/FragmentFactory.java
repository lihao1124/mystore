package com.bj.glz.firsttry.common.utils;

import com.bj.glz.firsttry.fragment.SpeedTestFragment;

/**
 * Created by i on 2017/11/13.
 */

public class FragmentFactory {

    private FragmentFactory() {
    }

    public static final SpeedTestFragment getSpeedTestFragment() {
        return SpeedTestFragmentHolder.INSTANCE;
    }

    private static class SpeedTestFragmentHolder {
        private static final SpeedTestFragment INSTANCE = new SpeedTestFragment();
    }
}
