package com.android.systemui.bouncer.domain.interactor;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class BouncerMessageAuditLoggerKt {
    public static final String TAG;

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(BouncerMessageAuditLogger.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        TAG = simpleName;
    }
}
