package com.samsung.android.knox.analytics.util;

import android.os.SemSystemProperties;

/* loaded from: classes6.dex */
public final class Log {
    static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);

    public static void d(String tag, String message) {
        if (DEBUG) {
            android.util.Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            android.util.Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable tr) {
        if (DEBUG) {
            android.util.Log.e(tag, message, tr);
        }
    }
}
