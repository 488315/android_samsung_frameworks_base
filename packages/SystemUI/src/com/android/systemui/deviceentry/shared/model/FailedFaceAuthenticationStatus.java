package com.android.systemui.deviceentry.shared.model;

import android.os.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class FailedFaceAuthenticationStatus extends FaceAuthenticationStatus {
    public final long createdAt;

    public FailedFaceAuthenticationStatus() {
        this(0L, 1, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FailedFaceAuthenticationStatus) && this.createdAt == ((FailedFaceAuthenticationStatus) obj).createdAt;
    }

    public final int hashCode() {
        return Long.hashCode(this.createdAt);
    }

    public final String toString() {
        return "FailedFaceAuthenticationStatus(createdAt=" + this.createdAt + ")";
    }

    public /* synthetic */ FailedFaceAuthenticationStatus(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? SystemClock.elapsedRealtime() : j);
    }

    public FailedFaceAuthenticationStatus(long j) {
        super(null);
        this.createdAt = j;
    }
}
