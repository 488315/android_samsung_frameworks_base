package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class FingerprintAuthenticationStatus {
    public final Boolean isEngaged;

    public /* synthetic */ FingerprintAuthenticationStatus(Boolean bool, DefaultConstructorMarker defaultConstructorMarker) {
        this(bool);
    }

    private FingerprintAuthenticationStatus(Boolean bool) {
        this.isEngaged = bool;
    }
}
