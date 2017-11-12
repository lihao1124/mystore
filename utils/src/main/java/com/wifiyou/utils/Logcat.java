package com.wifiyou.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Default Log Utils
 *
 * <p><strong>Warning:</strong>
 * all log should use this util instead of logcat fo system
 *
 * Created by yuym on 16/10/27.
 */
public class Logcat {

    // whether the log can output
    private static boolean mLogModel = false;

    // default log tag for logcat
    private static String mDefaultLogTag = "WiFiYou";

    public static void init(boolean logModel, String defaultLogTag) {
        mLogModel = logModel;
        mDefaultLogTag = defaultLogTag;
    }

    /**
     * log.d
     * 
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) {
            return;
        }
        if (mLogModel) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mLogModel) {
            Log.d(mDefaultLogTag, msg);
        }
    }

    /**
     * log.e
     *
     * @param msg
     */
    public static void e(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mLogModel) {
            Log.e(mDefaultLogTag, msg);
        }
    }

    /**
     * log.e
     * 
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) {
            return;
        }
        if (mLogModel) {
            Log.e(tag, msg);
        }
    }

    /**
     * log.e
     * 
     * @param msg
     */
    public static void e(String msg, Throwable throwable) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mLogModel) {
            Log.e(mDefaultLogTag, msg, throwable);
        }
    }

    /**
     * log.w
     * 
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) {
            return;
        }
        if (mLogModel) {
            Log.w(tag, msg);
        }
    }

    /**
     * Log.i
     * 
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) {
            return;
        }
        if (mLogModel) {
            Log.i(tag, msg);
        }
    }

    /**
     * log.v
     * 
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) {
            return;
        }
        if (mLogModel) {
            Log.v(tag, msg);
        }
    }
}
