package com.android.systemui.keyguard;

import com.android.systemui.log.LogLevel;
import com.android.systemui.util.LogUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Log {
    /* renamed from: d */
    public static void m138d(String str, String str2) {
        android.util.Log.d(str, str2);
        KeyguardDumpLog.log(str, LogLevel.DEBUG, str2, null);
    }

    /* renamed from: e */
    public static void m140e(String str, String str2) {
        android.util.Log.e(str, str2);
        KeyguardDumpLog.log(str, LogLevel.ERROR, str2, null);
    }

    /* renamed from: i */
    public static void m141i(String str, String str2) {
        android.util.Log.i(str, str2);
        KeyguardDumpLog.log(str, LogLevel.INFO, str2, null);
    }

    /* renamed from: w */
    public static void m142w(String str, String str2) {
        android.util.Log.w(str, str2);
        KeyguardDumpLog.log(str, LogLevel.WARNING, str2, null);
    }

    /* renamed from: d */
    public static void m139d(String str, String str2, Object... objArr) {
        m138d(str, LogUtil.getMsg(str2, objArr));
    }
}
