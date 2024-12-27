package com.android.systemui.deviceentry.shared.model;

import android.os.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
