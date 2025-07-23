package com.android.systemui.util.sensors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ThresholdSensorEvent {
    private final boolean mBelow;
    private final long mTimestampNs;

    public ThresholdSensorEvent(boolean z, long j) {
        this.mBelow = z;
        this.mTimestampNs = j;
    }

    public boolean getBelow() {
        return this.mBelow;
    }

    public long getTimestampMs() {
        return this.mTimestampNs / 1000000;
    }

    public long getTimestampNs() {
        return this.mTimestampNs;
    }

    public String toString() {
        return "{near=" + this.mBelow + ", timestamp_ns=" + this.mTimestampNs + "}";
    }
}
