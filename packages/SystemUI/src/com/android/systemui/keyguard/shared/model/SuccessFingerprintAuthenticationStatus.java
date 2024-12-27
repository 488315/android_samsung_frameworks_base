package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SuccessFingerprintAuthenticationStatus extends FingerprintAuthenticationStatus {
    public final boolean isStrongBiometric;
    public final int userId;

    public SuccessFingerprintAuthenticationStatus(int i, boolean z) {
        super(Boolean.FALSE, null);
        this.userId = i;
        this.isStrongBiometric = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SuccessFingerprintAuthenticationStatus)) {
            return false;
        }
        SuccessFingerprintAuthenticationStatus successFingerprintAuthenticationStatus = (SuccessFingerprintAuthenticationStatus) obj;
        return this.userId == successFingerprintAuthenticationStatus.userId && this.isStrongBiometric == successFingerprintAuthenticationStatus.isStrongBiometric;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isStrongBiometric) + (Integer.hashCode(this.userId) * 31);
    }

    public final String toString() {
        return "SuccessFingerprintAuthenticationStatus(userId=" + this.userId + ", isStrongBiometric=" + this.isStrongBiometric + ")";
    }
}
