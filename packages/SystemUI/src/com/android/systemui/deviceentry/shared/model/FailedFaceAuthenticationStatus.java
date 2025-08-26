package com.android.systemui.deviceentry.shared.model;

import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.createdAt, ")", new StringBuilder("FailedFaceAuthenticationStatus(createdAt="));
    }

    public /* synthetic */ FailedFaceAuthenticationStatus(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? SystemClock.elapsedRealtime() : j);
    }

    public FailedFaceAuthenticationStatus(long j) {
        super(null);
        this.createdAt = j;
    }
}
