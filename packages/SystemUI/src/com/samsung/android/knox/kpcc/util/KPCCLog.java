package com.samsung.android.knox.kpcc.util;

import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class KPCCLog {
    public static final String KPCC_TAG = "KPCC:";
    public static final boolean isUserShip = true;

    public static void e(String str, String str2) {
        Log.e(KPCC_TAG + str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        Log.e(KPCC_TAG + str, str2, th);
    }

    public static void d(String str, String str2) {
    }
}
