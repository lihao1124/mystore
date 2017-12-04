package com.bj.glz.firsttry.common.utils;

import android.content.Context;

import com.bj.glz.firsttry.common.bean.HistoryBean;
import com.wifiyou.utils.ObjectSaveUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 45011 on 2017/8/12.
 */

public class HistoryUtils {

    public static final String KEY_HISTORY = "history";//返回List<HistoryBean>
    private static final int MAX_HISTORY_COUNT = 20;

    //保存当前路由器扫过的设备信息，用于offline设备的显示


    public static void saveHistory(Context context, HistoryBean historyBean) {
        int connectedType = Utils.getConnectedType(context);
        if (connectedType == -1) {
            return;
        }
        historyBean.type = connectedType;
        ArrayList<HistoryBean> list = getHistory(context);
        if (list == null)
            list = new ArrayList<>();
        if (list.size() >= MAX_HISTORY_COUNT)
            list.remove(list.size() - 1);
        list.add(0, historyBean);
        try {
            ObjectSaveUtils.saveObject(context, KEY_HISTORY, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<HistoryBean> getHistory(Context context) {
        ArrayList<HistoryBean> list = null;
        try {
            list = (ArrayList<HistoryBean>) ObjectSaveUtils.retrieveObject(context, KEY_HISTORY);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
