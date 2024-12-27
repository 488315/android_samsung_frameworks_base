package com.android.server.notification;

public final class RateEstimator {
    public double mInterarrivalTime;
    public Long mLastEventTime;

    public final double getInterarrivalEstimate(long j) {
        return (Math.max((j - this.mLastEventTime.longValue()) / 1000.0d, 5.0E-4d)
                        * 0.30000000000000004d)
                + (this.mInterarrivalTime * 0.7d);
    }
}
