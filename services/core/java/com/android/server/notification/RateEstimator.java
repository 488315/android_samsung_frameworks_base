package com.android.server.notification;

import com.android.server.display.DisplayPowerController2;

/* loaded from: classes2.dex */
public class RateEstimator {
    public double mInterarrivalTime = 0.5d;
    public Long mLastEventTime;

    public float update(long j) {
        float f;
        if (this.mLastEventTime == null) {
            f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        } else {
            double interarrivalEstimate = getInterarrivalEstimate(j);
            this.mInterarrivalTime = interarrivalEstimate;
            f = (float) (1.0d / interarrivalEstimate);
        }
        this.mLastEventTime = Long.valueOf(j);
        return f;
    }

    public float getRate(long j) {
        return this.mLastEventTime == null ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : (float) (1.0d / getInterarrivalEstimate(j));
    }

    public final double getInterarrivalEstimate(long j) {
        return (this.mInterarrivalTime * 0.8d) + (Math.max((j - this.mLastEventTime.longValue()) / 1000.0d, 5.0E-4d) * 0.19999999999999996d);
    }
}
