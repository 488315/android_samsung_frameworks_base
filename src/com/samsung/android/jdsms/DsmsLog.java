package com.samsung.android.jdsms;

import android.os.Build;
import android.util.Log;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class DsmsLog {
    private static final String ENG_BUILD = "eng";
    private static final boolean IS_ENG = ENG_BUILD.equals(Build.TYPE);
    public static final String TAG = "DSMS-FRAMEWORK";

    public static boolean isDebuggable() {
        return IS_ENG && Log.isLoggable(TAG, 3);
    }

    public static void d(String str) {
        if (isDebuggable()) {
            Log.d(TAG, str);
        }
    }

    public static void e(String str) {
        Log.e(TAG, str);
    }

    public static void w(String str) {
        Log.w(TAG, str);
    }

    public static void i(String str) {
        Log.i(TAG, str);
    }

    public static void d(String str, String str2) {
        if (isDebuggable()) {
            println(3, TAG, str, str2);
        }
    }

    public static void e(String str, String str2) {
        println(6, TAG, str, str2);
    }

    public static void i(String str, String str2) {
        println(4, TAG, str, str2);
    }

    public static void w(String str, String str2) {
        println(5, TAG, str, str2);
    }

    private static void println(int i, String str, String str2, String str3) {
        Log.println(i, str, String.format("[%s] %s", Objects.toString(str2, ""), Objects.toString(str3, "")));
    }
}
