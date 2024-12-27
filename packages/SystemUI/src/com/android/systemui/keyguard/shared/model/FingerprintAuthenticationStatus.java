package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class FingerprintAuthenticationStatus {
    public final Boolean isEngaged;

    public /* synthetic */ FingerprintAuthenticationStatus(Boolean bool, DefaultConstructorMarker defaultConstructorMarker) {
        this(bool);
    }

    private FingerprintAuthenticationStatus(Boolean bool) {
        this.isEngaged = bool;
    }
}
