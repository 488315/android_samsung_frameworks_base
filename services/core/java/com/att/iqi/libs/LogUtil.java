package com.att.iqi.libs;

import android.util.Log;

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
