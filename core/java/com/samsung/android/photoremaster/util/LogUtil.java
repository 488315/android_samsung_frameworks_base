package com.samsung.android.photoremaster.util;

import android.util.Log;

/* loaded from: classes5.dex */
public class LogUtil {
    private static final String TAG_PREFIX = "Remaster-";

    private static String getLogTag(String tag) {
        return TAG_PREFIX + tag;
    }

    /* renamed from: v */
    public static void m295v(String tag, String msg) {
        Log.m100v(getLogTag(tag), msg);
    }

    /* renamed from: d */
    public static void m291d(String tag, String msg) {
        Log.m94d(getLogTag(tag), msg);
    }

    /* renamed from: i */
    public static void m294i(String tag, String msg) {
        Log.m98i(getLogTag(tag), msg);
    }

    /* renamed from: w */
    public static void m296w(String tag, String msg) {
        Log.m102w(getLogTag(tag), msg);
    }

    /* renamed from: e */
    public static void m292e(String tag, String msg) {
        Log.m96e(getLogTag(tag), msg);
    }

    /* renamed from: e */
    public static void m293e(String tag, String msg, Throwable tr) {
        Log.m97e(getLogTag(tag), msg, tr);
    }
}
