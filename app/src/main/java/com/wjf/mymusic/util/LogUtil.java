package com.wjf.mymusic.util;

import android.text.TextUtils;
import android.util.Log;

public class LogUtil {

    public static void d(String tag, String msg) {
        if (CommonConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (CommonConfig.DEBUG) {
            if (!TextUtils.isEmpty(msg))
                Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (CommonConfig.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (CommonConfig.DEBUG) {
            Log.w(tag, tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (CommonConfig.DEBUG) {
            Log.w(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (CommonConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(Object obj, String msg) {
        if (CommonConfig.DEBUG) {
            Log.d(obj.getClass().getSimpleName(), msg);
        }
    }

    public static void e(Object obj, String msg) {
        if (CommonConfig.DEBUG) {
            Log.e(obj.getClass().getSimpleName(), msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (CommonConfig.DEBUG) {
            Log.e(tag, msg, tr);
        }
    }
}
