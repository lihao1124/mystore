package com.bj.glz.firsttry.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bj.glz.firsttry.R;
import com.bj.glz.firsttry.common.bean.HistoryBean;
import com.bj.glz.firsttry.common.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lihao on 2017/12/4.
 */

public class HistoryAdapter extends RecyclerView.Adapter {
    private ArrayList<HistoryBean> list;
    private Context context;

    public HistoryAdapter(Context context, ArrayList<HistoryBean> list) {
        this.list = list;
        this.context = context;
    }

    public void setData(ArrayList<HistoryBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_history_list, parent);
        return new HistoryViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HistoryViewHolder viewHolder = (HistoryViewHolder) holder;
        HistoryBean historyBean = list.get(position);
        if (historyBean.type == 0) {
            // TODO: 2017/12/4
        } else if (historyBean.type == 1) {
            // TODO: 2017/12/4
        }
        Date date = historyBean.date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String time = formatter.format(date);
        viewHolder.tvDate.setText(time);
        int ping = historyBean.ping;
        viewHolder.tvPing.setText(ping+" ms");
        viewHolder.tvDown.setText(Utils.formatSpeed(historyBean.downLoad));
        viewHolder.tvUp.setText(Utils.formatSpeed(historyBean.upLoad));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView tvType;
        private TextView tvDate;
        private TextView tvUp;
        private TextView tvDown;
        private TextView tvPing;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            tvType = (ImageView) itemView.findViewById(R.id.iv_type);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvDown = (TextView) itemView.findViewById(R.id.tv_down);
            tvUp = (TextView) itemView.findViewById(R.id.tv_up);
            tvPing = (TextView) itemView.findViewById(R.id.tv_ping);
        }
    }

}
