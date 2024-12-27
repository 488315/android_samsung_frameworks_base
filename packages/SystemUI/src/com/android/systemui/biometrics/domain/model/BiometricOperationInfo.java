package com.android.systemui.biometrics.domain.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
