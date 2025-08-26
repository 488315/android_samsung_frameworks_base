package com.android.systemui.keyguard.shared.model;

import com.android.systemui.biometrics.shared.model.AuthenticationReason;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class AcquiredFingerprintAuthenticationStatus extends FingerprintAuthenticationStatus {
    public final int acquiredInfo;
    public final AuthenticationReason authenticationReason;

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0010  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AcquiredFingerprintAuthenticationStatus(AuthenticationReason authenticationReason, int i) {
        Boolean bool;
        if (i == 0) {
            bool = null;
        } else if (i == 7) {
            bool = Boolean.TRUE;
        } else if (i != 8) {
            bool = Boolean.FALSE;
        }
        super(bool, null);
        this.authenticationReason = authenticationReason;
        this.acquiredInfo = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AcquiredFingerprintAuthenticationStatus)) {
            return false;
        }
        AcquiredFingerprintAuthenticationStatus acquiredFingerprintAuthenticationStatus = (AcquiredFingerprintAuthenticationStatus) obj;
        return Intrinsics.areEqual(this.authenticationReason, acquiredFingerprintAuthenticationStatus.authenticationReason) && this.acquiredInfo == acquiredFingerprintAuthenticationStatus.acquiredInfo;
    }

    public final int hashCode() {
        return Integer.hashCode(this.acquiredInfo) + (this.authenticationReason.hashCode() * 31);
    }

    public final String toString() {
        return "AcquiredFingerprintAuthenticationStatus(authenticationReason=" + this.authenticationReason + ", acquiredInfo=" + this.acquiredInfo + ")";
    }
}
