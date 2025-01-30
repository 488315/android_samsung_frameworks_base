package com.android.systemui.keyguard;

import com.android.systemui.log.LogLevel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Slog {
    /* renamed from: w */
    public static void m144w(String str, Throwable th) {
        android.util.Slog.w("KeyguardViewMediator", str, th);
        KeyguardDumpLog.log("KeyguardViewMediator", LogLevel.WARNING, str, th);
    }
}
