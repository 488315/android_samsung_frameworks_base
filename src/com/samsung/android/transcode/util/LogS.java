package com.samsung.android.transcode.util;

import android.util.Log;
import java.io.File;

/* loaded from: classes6.dex */
public class LogS {
    public static final String TAG = "TranscodeLib";
    private static final String DEBUG_FILE = "/storage/emulated/0/DCIM/transcodelib.debug";
    private static boolean DEBUG = new File(DEBUG_FILE).exists();

    public static void v(String str, String str2) {
        if (DEBUG) {
            Log.i(str, str2);
        } else {
            Log.v(str, str2);
        }
    }

    public static void d(String str, String str2) {
        if (DEBUG) {
            Log.i(str, str2);
        } else {
            Log.d(str, str2);
        }
    }

    public static void i(String str, String str2) {
        Log.i(str, str2);
    }

    public static void w(String str, String str2) {
        Log.w(str, str2);
    }

    public static void e(String str, String str2) {
        Log.e(str, str2);
    }

    public static void stackTrace(String str) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        int length = stackTrace.length;
        for (int i = 2; i < length; i++) {
            sb.append(stackTrace[i].toString());
            sb.append('\n');
        }
        Log.i(str, "------------ Stacktrace ---------------");
        Log.i(str, sb.toString());
    }
}
