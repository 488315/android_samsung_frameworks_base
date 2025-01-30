package com.android.systemui.keyguard.shared.model;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AuthenticationFlags {
    public final int flag;
    public final boolean isInUserLockdown;
    public final boolean isPrimaryAuthRequiredAfterDpmLockdown;
    public final boolean isPrimaryAuthRequiredAfterReboot;
    public final boolean isPrimaryAuthRequiredAfterTimeout;
    public final boolean primaryAuthRequiredForUnattendedUpdate;
    public final boolean someAuthRequiredAfterTrustAgentExpired;
    public final boolean someAuthRequiredAfterUserRequest;
    public final boolean strongerAuthRequiredAfterNonStrongBiometricsTimeout;
    public final int userId;

    public AuthenticationFlags(int i, int i2) {
        this.userId = i;
        this.flag = i2;
        this.isInUserLockdown = AuthenticationFlagsKt.access$containsFlag(i2, 32);
        this.isPrimaryAuthRequiredAfterReboot = AuthenticationFlagsKt.access$containsFlag(i2, 1);
        this.isPrimaryAuthRequiredAfterTimeout = AuthenticationFlagsKt.access$containsFlag(i2, 16);
        this.isPrimaryAuthRequiredAfterDpmLockdown = AuthenticationFlagsKt.access$containsFlag(i2, 2);
        this.someAuthRequiredAfterUserRequest = AuthenticationFlagsKt.access$containsFlag(i2, 4);
        this.someAuthRequiredAfterTrustAgentExpired = AuthenticationFlagsKt.access$containsFlag(i2, 256);
        this.primaryAuthRequiredForUnattendedUpdate = AuthenticationFlagsKt.access$containsFlag(i2, 64);
        this.strongerAuthRequiredAfterNonStrongBiometricsTimeout = AuthenticationFlagsKt.access$containsFlag(i2, 128);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthenticationFlags)) {
            return false;
        }
        AuthenticationFlags authenticationFlags = (AuthenticationFlags) obj;
        return this.userId == authenticationFlags.userId && this.flag == authenticationFlags.flag;
    }

    public final int hashCode() {
        return Integer.hashCode(this.flag) + (Integer.hashCode(this.userId) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AuthenticationFlags(userId=");
        sb.append(this.userId);
        sb.append(", flag=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.flag, ")");
    }
}
