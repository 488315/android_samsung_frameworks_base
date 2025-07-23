package com.samsung.android.knox.analytics.util;

import android.os.SemSystemProperties;

/* loaded from: classes6.dex */
public final class Log {
    static final boolean DEBUG = !SemSystemProperties.getBoolean("ro.product_ship", true);

    public static void d(String str, String str2) {
        if (DEBUG) {
            android.util.Log.d(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (DEBUG) {
            android.util.Log.e(str, str2);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (DEBUG) {
            android.util.Log.e(str, str2, th);
        }
    }
}
