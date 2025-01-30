package com.android.systemui.keyguard.bouncer.domain.interactor;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class BouncerMessageAuditLoggerKt {
    public static final String TAG;

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(BouncerMessageAuditLogger.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
    }
}
