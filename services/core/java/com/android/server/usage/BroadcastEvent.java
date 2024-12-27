package com.android.server.usage;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.util.LongArrayQueue;

import java.util.Objects;

public final class BroadcastEvent {
    public final long mIdForResponseEvent;
    public final int mSourceUid;
    public final String mTargetPackage;
    public final int mTargetUserId;
    public final LongArrayQueue mTimestampsMs = new LongArrayQueue();

    public BroadcastEvent(int i, int i2, long j, String str) {
        this.mSourceUid = i;
        this.mTargetPackage = str;
        this.mTargetUserId = i2;
        this.mIdForResponseEvent = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BroadcastEvent)) {
            return false;
        }
        BroadcastEvent broadcastEvent = (BroadcastEvent) obj;
        return this.mSourceUid == broadcastEvent.mSourceUid
                && this.mIdForResponseEvent == broadcastEvent.mIdForResponseEvent
                && this.mTargetUserId == broadcastEvent.mTargetUserId
                && this.mTargetPackage.equals(broadcastEvent.mTargetPackage);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mSourceUid),
                this.mTargetPackage,
                Integer.valueOf(this.mTargetUserId),
                Long.valueOf(this.mIdForResponseEvent));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BroadcastEvent {srcUid=");
        sb.append(this.mSourceUid);
        sb.append(",tgtPkg=");
        sb.append(this.mTargetPackage);
        sb.append(",tgtUser=");
        sb.append(this.mTargetUserId);
        sb.append(",id=");
        return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.mIdForResponseEvent, "}");
    }
}
