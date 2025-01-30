package com.samsung.android.knox.kpcc.util;

import android.os.SemSystemProperties;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KPCCLog {
    public static final String KPCC_TAG = "KPCC:";
    public static final boolean isUserShip;

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0015, code lost:
    
        if (android.os.SemSystemProperties.getBoolean("ro.product_ship", true) != false) goto L8;
     */
    static {
        boolean z = "user".equals(SemSystemProperties.get("ro.build.type", "user"));
        isUserShip = z;
    }

    /* renamed from: d */
    public static void m251d(String str, String str2) {
        if (isUserShip) {
            return;
        }
        Log.d(KPCC_TAG + str, str2);
    }

    /* renamed from: e */
    public static void m252e(String str, String str2) {
        Log.e(KPCC_TAG + str, str2);
    }

    /* renamed from: e */
    public static void m253e(String str, String str2, Throwable th) {
        Log.e(KPCC_TAG + str, str2, th);
    }
}
