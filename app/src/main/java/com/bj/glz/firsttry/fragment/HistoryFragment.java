package com.bj.glz.firsttry.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.base.BaseFragment;
import com.bj.glz.firsttry.common.adapter.HistoryAdapter;
import com.bj.glz.firsttry.common.bean.HistoryBean;
import com.bj.glz.firsttry.common.utils.HistoryUtils;
import com.bj.glz.firsttry.databinding.FragmentHistoryBinding;
import com.wifiyou.utils.MainThreadPostUtils;

import java.util.ArrayList;

/**
 * Created by i on 2017/11/15.
 */

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding> {

    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryBean> history;
    private MyRunnable runnable;
    private boolean isReading;

    @Override
    protected void setListener() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        historyAdapter = new HistoryAdapter(getActivity(), history);
        dataBinding.lvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataBinding.lvHistory.setAdapter(historyAdapter);
        runnable = new MyRunnable();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_history;
    }

    private void fetchHistory() {
        if (isReading) return;
        isReading = true;
        new Thread(runnable).start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            fetchHistory();
        }
        // TODO: 2017/12/4 加广告
    }

    private class MyRunnable implements Runnable {

        @Override
        public void run() {
            history = HistoryUtils.getHistory(getActivity());
            isReading = false;
            MainThreadPostUtils.post(new Runnable() {
                @Override
                public void run() {
                    historyAdapter.setData(history);
                }
            });
        }
    }
}
