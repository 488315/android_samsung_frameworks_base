package com.android.systemui.keyguard.shared.model;

import com.android.systemui.biometrics.shared.model.AuthenticationReason;
import kotlin.jvm.internal.Intrinsics;

public final class AcquiredFingerprintAuthenticationStatus extends FingerprintAuthenticationStatus {
    public final int acquiredInfo;
    public final AuthenticationReason authenticationReason;
    public final boolean fingerprintCaptureCompleted;
    public final boolean fingerprintCaptureStarted;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AcquiredFingerprintAuthenticationStatus(com.android.systemui.biometrics.shared.model.AuthenticationReason r3, int r4) {
        /*
            r2 = this;
            r0 = 0
            r1 = 7
            if (r4 == 0) goto L10
            if (r4 == r1) goto Ld
            r1 = 8
            if (r4 == r1) goto L10
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            goto L11
        Ld:
            java.lang.Boolean r1 = java.lang.Boolean.TRUE
            goto L11
        L10:
            r1 = r0
        L11:
            r2.<init>(r1, r0)
            r2.authenticationReason = r3
            r2.acquiredInfo = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.shared.model.AcquiredFingerprintAuthenticationStatus.<init>(com.android.systemui.biometrics.shared.model.AuthenticationReason, int):void");
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
