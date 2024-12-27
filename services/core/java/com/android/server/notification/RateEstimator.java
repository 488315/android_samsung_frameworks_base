package com.android.server.notification;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class RateEstimator {
    public double mInterarrivalTime;
    public Long mLastEventTime;

    public final double getInterarrivalEstimate(long j) {
        return (Math.max((j - this.mLastEventTime.longValue()) / 1000.0d, 5.0E-4d) * 0.30000000000000004d) + (this.mInterarrivalTime * 0.7d);
    }
}
