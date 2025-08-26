package com.android.systemui.keyguard.domain.interactor;

import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public abstract class KeyguardTransitionAuditLoggerKt {
    public static final String TAG;

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(KeyguardTransitionAuditLogger.class).getSimpleName();
        simpleName.getClass();
        TAG = simpleName;
    }
}
