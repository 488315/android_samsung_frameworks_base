package com.android.systemui.bouncer.domain.interactor;

import kotlin.jvm.internal.Reflection;

/* loaded from: classes.dex */
public abstract class BouncerMessageAuditLoggerKt {
    public static final String TAG;

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(BouncerMessageAuditLogger.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }
}
