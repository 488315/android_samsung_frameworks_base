package com.samsung.android.jdsms;

import android.os.Build;
import android.util.Log;

import java.util.Objects;

public final class DsmsLog {
    private static final String ENG_BUILD = "eng";
    private static final boolean IS_ENG = ENG_BUILD.equals(Build.TYPE);
    public static final String TAG = "DSMS-FRAMEWORK";

    public static boolean isDebuggable() {
        return IS_ENG && Log.isLoggable(TAG, 3);
    }

    public static void d(String msg) {
        if (isDebuggable()) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String subtag, String msg) {
        if (isDebuggable()) {
            println(3, TAG, subtag, msg);
        }
    }

    public static void e(String subtag, String msg) {
        println(6, TAG, subtag, msg);
    }

    public static void i(String subtag, String msg) {
        println(4, TAG, subtag, msg);
    }

    public static void w(String subtag, String msg) {
        println(5, TAG, subtag, msg);
    }

    private static void println(int priority, String tag, String subtag, String msg) {
        Log.println(
                priority,
                tag,
                String.format("[%s] %s", Objects.toString(subtag, ""), Objects.toString(msg, "")));
    }
}
