package com.bj.glz.firsttry.common.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lihao on 2017/12/4.
 */

public class HistoryBean implements Serializable{
    /**
     * 0 wifi 1 mobile -1 没网
     */
    public int type;

    public Date date;

    public long upLoad;

    public long downLoad;

    public int ping;
}
