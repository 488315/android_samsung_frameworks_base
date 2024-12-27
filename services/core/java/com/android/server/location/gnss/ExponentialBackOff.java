package com.android.server.location.gnss;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ExponentialBackOff {
    public long mCurrentIntervalMillis = 150000;

    public final String toString() {
        return "ExponentialBackOff{mInitIntervalMillis=300000, mMaxIntervalMillis=14400000, mCurrentIntervalMillis=" + this.mCurrentIntervalMillis + '}';
    }
}
