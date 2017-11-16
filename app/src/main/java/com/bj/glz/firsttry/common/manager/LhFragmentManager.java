package com.bj.glz.firsttry.common.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.common.utils.FragmentFactory;

/**
 * Created by i on 2017/11/16.
 */

public class LhFragmentManager {
    private FragmentManager fm;

    public LhFragmentManager(FragmentActivity activity) {
        fm = activity.getSupportFragmentManager();
    }

    public void showFragment(String type) {
        Fragment fragmentByTag = fm.findFragmentByTag(type);
        if (fragmentByTag == null) {
            Fragment fragment = FragmentFactory.getFragment(type);
            fm.beginTransaction().add(R.id.fragment_container, fragment, type).commitAllowingStateLoss();
        } else {
            fm.beginTransaction().show(fragmentByTag).commitAllowingStateLoss();
        }
    }

    public void hideFragment(String type) {
        Fragment fragmentByTag = fm.findFragmentByTag(type);
        if (fragmentByTag != null) {
            fm.beginTransaction().hide(fragmentByTag).commitAllowingStateLoss();
        }
    }
}
