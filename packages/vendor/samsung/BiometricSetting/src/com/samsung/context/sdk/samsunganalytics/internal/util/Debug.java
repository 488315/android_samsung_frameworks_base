package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.os.Build;
import android.util.Log;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public abstract class Debug {
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
        Log.w(
                "SamsungAnalytics605015",
                "["
                        + cls.getSimpleName()
                        + "] "
                        + exc.getClass().getSimpleName()
                        + " "
                        + exc.getMessage());
    }

    public static void LogD(String str, String str2) {
        LogD("[" + str + "] " + str2);
    }
}
