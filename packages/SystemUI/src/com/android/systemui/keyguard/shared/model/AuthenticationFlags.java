package com.android.systemui.keyguard.shared.model;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class AuthenticationFlags {
    public final int flag;
    public final boolean isInUserLockdown;
    public final boolean isPrimaryAuthRequiredAfterDpmLockdown;
    public final boolean isPrimaryAuthRequiredAfterLockout;
    public final boolean isPrimaryAuthRequiredAfterReboot;
    public final boolean isPrimaryAuthRequiredAfterTimeout;
    public final boolean isPrimaryAuthRequiredForUnattendedUpdate;
    public final boolean isSomeAuthRequiredAfterAdaptiveAuthRequest;
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
        this.isPrimaryAuthRequiredAfterLockout = AuthenticationFlagsKt.access$containsFlag(i2, 8);
        this.isPrimaryAuthRequiredAfterDpmLockdown = AuthenticationFlagsKt.access$containsFlag(i2, 2);
        this.someAuthRequiredAfterUserRequest = AuthenticationFlagsKt.access$containsFlag(i2, 4);
        this.someAuthRequiredAfterTrustAgentExpired = AuthenticationFlagsKt.access$containsFlag(i2, 256);
        this.isPrimaryAuthRequiredForUnattendedUpdate = AuthenticationFlagsKt.access$containsFlag(i2, 64);
        this.strongerAuthRequiredAfterNonStrongBiometricsTimeout = AuthenticationFlagsKt.access$containsFlag(i2, 128);
        this.isSomeAuthRequiredAfterAdaptiveAuthRequest = AuthenticationFlagsKt.access$containsFlag(i2, 512);
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
        return Anchor$$ExternalSyntheticOutline0.m(this.flag, ")", sb);
    }
}
