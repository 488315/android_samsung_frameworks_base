package com.samsung.context.sdk.samsunganalytics.internal.util;

import android.os.Build;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Debug {
    public static void LogD(String str) {
        Log.d("SamsungAnalytics605033", str);
    }

    public static void LogE(String str) {
        Log.e("SamsungAnalytics605033", str);
    }

    public static void LogENG(String str) {
        if (Build.TYPE.equals("eng")) {
            AbstractC0000x2c234b15.m3m("[ENG ONLY] ", str, "SamsungAnalytics605033");
        }
    }

    public static void LogException(Class cls, Exception exc) {
        Log.w("SamsungAnalytics605033", "[" + cls.getSimpleName() + "] " + exc.getClass().getSimpleName() + " " + exc.getMessage());
    }

    public static void LogD(String str, String str2) {
        LogD("[" + str + "] " + str2);
    }
}
