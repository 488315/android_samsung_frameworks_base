package com.samsung.android.authenticator;

import android.util.Log;

/* loaded from: classes5.dex */
final class AuthenticatorLog {
    private static final String TAG = "SAMgr_";
    private static final int sLogLevel = 4;

    private AuthenticatorLog() {
        throw new AssertionError();
    }

    /* renamed from: v */
    static void m252v(String tag, String msg) {
    }

    /* renamed from: d */
    static void m249d(String tag, String msg) {
    }

    /* renamed from: i */
    static void m251i(String tag, String msg) {
        Log.m98i(TAG + tag, msg);
    }

    /* renamed from: w */
    static void m253w(String tag, String msg) {
        Log.m102w(TAG + tag, msg);
    }

    /* renamed from: e */
    static void m250e(String tag, String msg) {
        Log.m96e(TAG + tag, msg);
    }

    static String getStackTraceString(Throwable throwable) {
        return Log.getStackTraceString(throwable);
    }
}
