package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FailFingerprintAuthenticationStatus extends FingerprintAuthenticationStatus {
    public static final FailFingerprintAuthenticationStatus INSTANCE = new FailFingerprintAuthenticationStatus();

    private FailFingerprintAuthenticationStatus() {
        super(Boolean.FALSE, null);
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof FailFingerprintAuthenticationStatus);
    }

    public final int hashCode() {
        return -1516498837;
    }

    public final String toString() {
        return "FailFingerprintAuthenticationStatus";
    }
}
