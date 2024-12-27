package com.android.systemui.util.sensors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
