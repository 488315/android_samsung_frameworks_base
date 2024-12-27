package com.android.server.location.gnss;

import java.util.Arrays;

public final class GnssPositionMode {
    public final boolean mLowPowerMode;
    public final int mMinInterval;
    public final int mMode;
    public final int mRecurrence = 0;

    public GnssPositionMode(int i, int i2, boolean z) {
        this.mMode = i;
        this.mMinInterval = i2;
        this.mLowPowerMode = z;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof GnssPositionMode)) {
            return false;
        }
        GnssPositionMode gnssPositionMode = (GnssPositionMode) obj;
        return this.mMode == gnssPositionMode.mMode
                && this.mRecurrence == gnssPositionMode.mRecurrence
                && this.mMinInterval == gnssPositionMode.mMinInterval
                && this.mLowPowerMode == gnssPositionMode.mLowPowerMode;
    }

    public final int hashCode() {
        return Arrays.hashCode(
                new Object[] {
                    Integer.valueOf(this.mMode),
                    Integer.valueOf(this.mRecurrence),
                    Integer.valueOf(this.mMinInterval),
                    0,
                    0,
                    Boolean.valueOf(this.mLowPowerMode),
                    GnssPositionMode.class
                });
    }
}
