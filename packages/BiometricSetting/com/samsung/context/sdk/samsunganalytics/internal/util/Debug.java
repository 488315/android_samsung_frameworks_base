package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.os.Build;
import android.util.Log;

/* loaded from: classes.dex */
public final class Debug {
    public static void LogD(String str) {
        Log.d("SamsungAnalytics605015", str);
    }

    public static void LogE(String str) {
        Log.e("SamsungAnalytics605015", str);
    }

    public static void LogENG(String str) {
        if (Build.TYPE.equals("eng")) {
            Log.d("SamsungAnalytics605015", "[ENG ONLY] " + str);
        }
    }

    public static void LogException(Class cls, Exception exc) {
        Log.w("SamsungAnalytics605015", "[" + cls.getSimpleName() + "] " + exc.getClass().getSimpleName() + " " + exc.getMessage());
    }

    public static void LogD(String str, String str2) {
        LogD("[" + str + "] " + str2);
    }
}
