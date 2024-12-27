package com.samsung.android.transcode.util;

import android.util.Log;
import java.io.File;

/* loaded from: classes6.dex */
public class LogS {
    public static final String TAG = "TranscodeLib";
    private static final String DEBUG_FILE = "/storage/emulated/0/DCIM/transcodelib.debug";
    private static boolean DEBUG = new File(DEBUG_FILE).exists();

    public static void v(String tag, String log) {
        if (DEBUG) {
            Log.i(tag, log);
        } else {
            Log.v(tag, log);
        }
    }

    public static void d(String tag, String log) {
        if (DEBUG) {
            Log.i(tag, log);
        } else {
            Log.d(tag, log);
        }
    }

    public static void i(String tag, String log) {
        Log.i(tag, log);
    }

    public static void w(String tag, String log) {
        Log.w(tag, log);
    }

    public static void e(String tag, String log) {
        Log.e(tag, log);
    }

    public static void stackTrace(String tag) {
        StringBuilder stacktrace = new StringBuilder();
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        int len = stackTrace.length;
        for (int x = 2; x < len; x++) {
            stacktrace.append(stackTrace[x].toString()).append('\n');
        }
        Log.i(tag, "------------ Stacktrace ---------------");
        Log.i(tag, stacktrace.toString());
    }
}
