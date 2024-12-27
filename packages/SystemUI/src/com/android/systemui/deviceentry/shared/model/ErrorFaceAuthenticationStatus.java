package com.android.systemui.deviceentry.shared.model;

import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class ErrorFaceAuthenticationStatus extends FaceAuthenticationStatus {
    public static final Companion Companion = new Companion(null);
    public final long createdAt;
    public final String msg;
    public final int msgId;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ ErrorFaceAuthenticationStatus(int i, String str, long j, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? SystemClock.elapsedRealtime() : j);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ErrorFaceAuthenticationStatus)) {
            return false;
        }
        ErrorFaceAuthenticationStatus errorFaceAuthenticationStatus = (ErrorFaceAuthenticationStatus) obj;
        return this.msgId == errorFaceAuthenticationStatus.msgId && Intrinsics.areEqual(this.msg, errorFaceAuthenticationStatus.msg) && this.createdAt == errorFaceAuthenticationStatus.createdAt;
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.msgId) * 31;
        String str = this.msg;
        return Long.hashCode(this.createdAt) + ((hashCode + (str == null ? 0 : str.hashCode())) * 31);
    }

    public final boolean isLockoutError() {
        int i = this.msgId;
        return i == 9 || i == 7;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ErrorFaceAuthenticationStatus(msgId=");
        sb.append(this.msgId);
        sb.append(", msg=");
        sb.append(this.msg);
        sb.append(", createdAt=");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.createdAt, ")", sb);
    }

    public ErrorFaceAuthenticationStatus(int i, String str, long j) {
        super(null);
        this.msgId = i;
        this.msg = str;
        this.createdAt = j;
    }
}
