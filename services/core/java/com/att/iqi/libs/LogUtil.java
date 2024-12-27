package com.att.iqi.libs;

import android.util.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class LogUtil {
    private static String LOG_TAG = "IQIConcierge";
    private static boolean sDebug;

    public static boolean canLog() {
        return sDebug;
    }

    public static void enableLogging(boolean z) {
        sDebug = z;
    }

    public static void logd(String str) {
        if (sDebug) {
            Log.d(LOG_TAG, str);
        }
    }

    public static void logd(String str, Throwable th) {
        if (sDebug) {
            Log.d(LOG_TAG, str, th);
        }
    }

    public static void loge(String str) {
        if (sDebug) {
            Log.e(LOG_TAG, str);
        }
    }

    public static void loge(String str, Throwable th) {
        if (sDebug) {
            Log.e(LOG_TAG, str, th);
        }
    }

    public static void logw(String str) {
        if (sDebug) {
            Log.w(LOG_TAG, str);
        }
    }

    public static void logw(String str, Throwable th) {
        if (sDebug) {
            Log.w(LOG_TAG, str, th);
        }
    }
}
