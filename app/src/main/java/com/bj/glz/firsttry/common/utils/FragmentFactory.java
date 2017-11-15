package com.bj.glz.firsttry.common.utils;

import android.support.v4.app.Fragment;

import com.bj.glz.firsttry.fragment.HistoryFragment;
import com.bj.glz.firsttry.fragment.SettingFragment;
import com.bj.glz.firsttry.fragment.SpeedTestFragment;

/**
 * Created by i on 2017/11/13.
 */

public class FragmentFactory {
    public static enum Type {
        MAIN, HISTORY, SETTING
    }

    private FragmentFactory() {
    }

    public static Fragment getFragment(Type type) {
        if (type == Type.MAIN) {
            return new SpeedTestFragment();
        } else if (type == Type.HISTORY) {
            return new HistoryFragment();
        } else if (type == Type.SETTING) {
            return new SettingFragment();
        }
        return null;
    }

    public static Class<? extends Fragment> getFragmentClass(Type type) {
        if (type == Type.MAIN) {
            return SpeedTestFragment.class;
        } else if (type == Type.HISTORY) {
            return HistoryFragment.class;
        } else if (type == Type.SETTING) {
            return SettingFragment.class;
        }
        return null;
    }

}
