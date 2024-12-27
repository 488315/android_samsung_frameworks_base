package com.android.systemui.deviceentry.shared.model;

import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class FaceDetectionStatus {
    public final long createdAt;
    public final boolean isStrongBiometric;
    public final int sensorId;
    public final int userId;

    public FaceDetectionStatus(int i, int i2, boolean z, long j) {
        this.sensorId = i;
        this.userId = i2;
        this.isStrongBiometric = z;
        this.createdAt = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FaceDetectionStatus)) {
            return false;
        }
        FaceDetectionStatus faceDetectionStatus = (FaceDetectionStatus) obj;
        return this.sensorId == faceDetectionStatus.sensorId && this.userId == faceDetectionStatus.userId && this.isStrongBiometric == faceDetectionStatus.isStrongBiometric && this.createdAt == faceDetectionStatus.createdAt;
    }

    public final int hashCode() {
        return Long.hashCode(this.createdAt) + TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.userId, Integer.hashCode(this.sensorId) * 31, 31), 31, this.isStrongBiometric);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("FaceDetectionStatus(sensorId=");
        sb.append(this.sensorId);
        sb.append(", userId=");
        sb.append(this.userId);
        sb.append(", isStrongBiometric=");
        sb.append(this.isStrongBiometric);
        sb.append(", createdAt=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.createdAt, ")", sb);
    }

    public /* synthetic */ FaceDetectionStatus(int i, int i2, boolean z, long j, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, z, (i3 & 8) != 0 ? SystemClock.elapsedRealtime() : j);
    }
}
