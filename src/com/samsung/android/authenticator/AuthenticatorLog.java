package com.samsung.android.authenticator;

import android.util.Log;

/* loaded from: classes6.dex */
final class AuthenticatorLog {
    private static final String TAG = "SAMgr_";
    private static final int sLogLevel = 4;

    static void d(String str, String str2) {
    }

    static void v(String str, String str2) {
    }

    private AuthenticatorLog() {
        throw new AssertionError();
    }

    static void i(String str, String str2) {
        Log.i(TAG + str, str2);
    }

    static void w(String str, String str2) {
        Log.w(TAG + str, str2);
    }

    static void e(String str, String str2) {
        Log.e(TAG + str, str2);
    }

    static String getStackTraceString(Throwable th) {
        return Log.getStackTraceString(th);
    }
}
