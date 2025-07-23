package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
