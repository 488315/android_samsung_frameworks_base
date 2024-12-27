package com.android.systemui.biometrics.domain.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class BiometricOperationInfo {
    public final long gatekeeperChallenge;

    public BiometricOperationInfo() {
        this(0L, 1, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof BiometricOperationInfo) && this.gatekeeperChallenge == ((BiometricOperationInfo) obj).gatekeeperChallenge;
    }

    public final int hashCode() {
        return Long.hashCode(this.gatekeeperChallenge);
    }

    public final String toString() {
        return "BiometricOperationInfo(gatekeeperChallenge=" + this.gatekeeperChallenge + ")";
    }

    public BiometricOperationInfo(long j) {
        this.gatekeeperChallenge = j;
    }

    public /* synthetic */ BiometricOperationInfo(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? -1L : j);
    }
}
