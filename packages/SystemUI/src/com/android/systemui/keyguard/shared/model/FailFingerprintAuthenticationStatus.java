package com.android.systemui.keyguard.shared.model;

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
