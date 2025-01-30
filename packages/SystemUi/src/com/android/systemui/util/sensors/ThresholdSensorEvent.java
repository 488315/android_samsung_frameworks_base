package com.android.systemui.util.sensors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ThresholdSensorEvent {
    public final boolean mBelow;
    public final long mTimestampNs;

    public ThresholdSensorEvent(boolean z, long j) {
        this.mBelow = z;
        this.mTimestampNs = j;
    }

    public final String toString() {
        return String.format(null, "{near=%s, timestamp_ns=%d}", Boolean.valueOf(this.mBelow), Long.valueOf(this.mTimestampNs));
    }
}
