package com.bj.glz.firsttry.common.manager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.common.utils.FragmentFactory;


/**
 * Created by i on 2017/11/13.
 */

public class FragmentController {
    private static FragmentManager fm;

    private FragmentController() {
    }

    public static FragmentController getInstance(FragmentActivity context) {
        if (fm == null) {
            fm = context.getSupportFragmentManager();
        }
        return FragmentControllerHolder.FRAGMENT_CONTROLLER;
    }

    private static class FragmentControllerHolder {
        private static final FragmentController FRAGMENT_CONTROLLER = new FragmentController();
    }

    public void showFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment tab = fm.findFragmentByTag(tag);
        if (tab == null) {
            transaction.add(R.id.fragment_container, FragmentFactory.getSpeedTestFragment(), tag);
        }else {
            transaction.show(tab);
        }
        transaction.commitAllowingStateLoss();
    }

    public void hideFragment(String tag){
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment tab = fm.findFragmentByTag(tag);
        if (tab != null) {
            transaction.hide(tab);
            transaction.commitAllowingStateLoss();
        }
    }
}
