package com.android.systemui.keyguard.domain.interactor;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

public abstract class KeyguardTransitionAuditLoggerKt {
    public static final String TAG;

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(KeyguardTransitionAuditLogger.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
    }
}
