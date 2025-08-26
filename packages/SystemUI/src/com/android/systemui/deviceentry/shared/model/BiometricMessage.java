package com.android.systemui.deviceentry.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class BiometricMessage {
    public final String message;

    public /* synthetic */ BiometricMessage(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private BiometricMessage(String str) {
        this.message = str;
    }
}
