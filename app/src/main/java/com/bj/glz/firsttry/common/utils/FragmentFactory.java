package com.bj.glz.firsttry.common.utils;

import android.support.v4.app.Fragment;

import com.bj.glz.firsttry.fragment.HistoryFragment;
import com.bj.glz.firsttry.fragment.SettingFragment;
import com.bj.glz.firsttry.fragment.SpeedTestFragment;

/**
 * Created by i on 2017/11/13.
 */

public class FragmentFactory {
    public class Type {
        public static final String TAB_MAIN = "tab1";
        public static final String TAB_HISTORY= "tab2";
        public static final String TAB_SETTING = "tab3";
    }

    private FragmentFactory() {
    }

    public static Fragment getFragment(String type) {
        if (Type.TAB_MAIN.equals(type)) {
            return new SpeedTestFragment();
        } else if (Type.TAB_HISTORY.equals(type)) {
            return new HistoryFragment();
        } else if (Type.TAB_SETTING.equals(type)) {
            return new SettingFragment();
        }
        return null;
    }

    /*public static Class<? extends Fragment> getFragmentClass(Type type) {
        if (type == Type.MAIN) {
            return SpeedTestFragment.class;
        } else if (type == Type.HISTORY) {
            return HistoryFragment.class;
        } else if (type == Type.SETTING) {
            return SettingFragment.class;
        }
        return null;
    }*/

}
