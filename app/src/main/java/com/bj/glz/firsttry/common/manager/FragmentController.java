package com.bj.glz.firsttry.common.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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

    public void showFragment(Class<Fragment> clazz, String tag) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment tab = fm.findFragmentByTag(tag);
        if (tab == null) {
            try {
                transaction.add(R.id.fragment_container, clazz.newInstance(), tag);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
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
