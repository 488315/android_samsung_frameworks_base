package com.android.systemui.util.sensors;

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
