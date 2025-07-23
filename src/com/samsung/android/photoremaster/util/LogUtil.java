package com.samsung.android.photoremaster.util;

import android.util.Log;

/* loaded from: classes6.dex */
public class LogUtil {
    private static final String TAG_PREFIX = "Remaster-";

    private static String getLogTag(String str) {
        return TAG_PREFIX + str;
    }

    public static void v(String str, String str2) {
        Log.v(getLogTag(str), str2);
    }

    public static void d(String str, String str2) {
        Log.d(getLogTag(str), str2);
    }

    public static void i(String str, String str2) {
        Log.i(getLogTag(str), str2);
    }

    public static void w(String str, String str2) {
        Log.w(getLogTag(str), str2);
    }

    public static void e(String str, String str2) {
        Log.e(getLogTag(str), str2);
    }

    public static void e(String str, String str2, Throwable th) {
        Log.e(getLogTag(str), str2, th);
    }
}
