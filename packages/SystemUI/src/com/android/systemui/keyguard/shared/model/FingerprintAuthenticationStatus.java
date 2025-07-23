package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
