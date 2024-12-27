package com.samsung.android.photoremaster.util;

import android.util.Log;

public class LogUtil {
    private static final String TAG_PREFIX = "Remaster-";

    private static String getLogTag(String tag) {
        return TAG_PREFIX + tag;
    }

    public static void v(String tag, String msg) {
        Log.v(getLogTag(tag), msg);
    }

    public static void d(String tag, String msg) {
        Log.d(getLogTag(tag), msg);
    }

    public static void i(String tag, String msg) {
        Log.i(getLogTag(tag), msg);
    }

    public static void w(String tag, String msg) {
        Log.w(getLogTag(tag), msg);
    }

    public static void e(String tag, String msg) {
        Log.e(getLogTag(tag), msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Log.e(getLogTag(tag), msg, tr);
    }
}
