package com.android.systemui.deviceentry.shared.model;

import android.hardware.face.FaceManager;
import android.os.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SuccessFaceAuthenticationStatus extends FaceAuthenticationStatus {
    public final long createdAt;
    public final FaceManager.AuthenticationResult successResult;

    public /* synthetic */ SuccessFaceAuthenticationStatus(FaceManager.AuthenticationResult authenticationResult, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(authenticationResult, (i & 2) != 0 ? SystemClock.elapsedRealtime() : j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SuccessFaceAuthenticationStatus)) {
            return false;
        }
        SuccessFaceAuthenticationStatus successFaceAuthenticationStatus = (SuccessFaceAuthenticationStatus) obj;
        return Intrinsics.areEqual(this.successResult, successFaceAuthenticationStatus.successResult) && this.createdAt == successFaceAuthenticationStatus.createdAt;
    }

    public final int hashCode() {
        return Long.hashCode(this.createdAt) + (this.successResult.hashCode() * 31);
    }

    public final String toString() {
        return "SuccessFaceAuthenticationStatus(successResult=" + this.successResult + ", createdAt=" + this.createdAt + ")";
    }

    public SuccessFaceAuthenticationStatus(FaceManager.AuthenticationResult authenticationResult, long j) {
        super(null);
        this.successResult = authenticationResult;
        this.createdAt = j;
    }
}
