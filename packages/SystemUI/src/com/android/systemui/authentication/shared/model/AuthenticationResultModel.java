package com.android.systemui.authentication.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AuthenticationResultModel {
    public final boolean isSuccessful;
    public final int lockoutDurationMs;

    public AuthenticationResultModel() {
        this(false, 0 == true ? 1 : 0, 3, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthenticationResultModel)) {
            return false;
        }
        AuthenticationResultModel authenticationResultModel = (AuthenticationResultModel) obj;
        return this.isSuccessful == authenticationResultModel.isSuccessful && this.lockoutDurationMs == authenticationResultModel.lockoutDurationMs;
    }

    public final int hashCode() {
        return Integer.hashCode(this.lockoutDurationMs) + (Boolean.hashCode(this.isSuccessful) * 31);
    }

    public final String toString() {
        return "AuthenticationResultModel(isSuccessful=" + this.isSuccessful + ", lockoutDurationMs=" + this.lockoutDurationMs + ")";
    }

    public AuthenticationResultModel(boolean z, int i) {
        this.isSuccessful = z;
        this.lockoutDurationMs = i;
    }

    public /* synthetic */ AuthenticationResultModel(boolean z, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? 0 : i);
    }
}
