package com.android.systemui.deviceentry.shared.model;

import android.os.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class AcquiredFaceAuthenticationStatus extends FaceAuthenticationStatus {
    public final int acquiredInfo;
    public final long createdAt;

    public /* synthetic */ AcquiredFaceAuthenticationStatus(int i, long j, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? SystemClock.elapsedRealtime() : j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AcquiredFaceAuthenticationStatus)) {
            return false;
        }
        AcquiredFaceAuthenticationStatus acquiredFaceAuthenticationStatus = (AcquiredFaceAuthenticationStatus) obj;
        return this.acquiredInfo == acquiredFaceAuthenticationStatus.acquiredInfo && this.createdAt == acquiredFaceAuthenticationStatus.createdAt;
    }

    public final int hashCode() {
        return Long.hashCode(this.createdAt) + (Integer.hashCode(this.acquiredInfo) * 31);
    }

    public final String toString() {
        return "AcquiredFaceAuthenticationStatus(acquiredInfo=" + this.acquiredInfo + ", createdAt=" + this.createdAt + ")";
    }

    public AcquiredFaceAuthenticationStatus(int i, long j) {
        super(null);
        this.acquiredInfo = i;
        this.createdAt = j;
    }
}
