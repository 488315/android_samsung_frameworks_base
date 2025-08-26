package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.os.Build;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class Debug {
    public static void LogD(String str) {
        Log.d("SamsungAnalytics605073", str);
    }

    public static void LogE(String str) {
        Log.e("SamsungAnalytics605073", str);
    }

    public static void LogENG(String str) {
        if (Build.TYPE.equals("user")) {
            return;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("[DEBUG ONLY] ", str, "SamsungAnalytics605073");
    }

    public static void LogException(Class cls, Exception exc) {
        Log.w("SamsungAnalytics605073", "[" + cls.getSimpleName() + "] " + exc.getClass().getSimpleName() + " " + exc.getMessage());
    }

    public static void LogI(String str) {
        Log.i("SamsungAnalytics605073", str);
    }

    public static void logwingE(String str) {
        Log.e("SamsungAnalytics605073", "[LOGWING]" + str);
    }

    public static void logwingW(String str) {
        MotionLayout$$ExternalSyntheticOutline0.m("[LOGWING]", str, "SamsungAnalytics605073");
    }

    public static void LogD(String str, String str2) {
        LogD("[" + str + "] " + str2);
    }
}
