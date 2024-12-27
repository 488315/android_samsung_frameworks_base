package com.android.server.location.gnss;

public final class ExponentialBackOff {
    public long mCurrentIntervalMillis = 150000;

    public final String toString() {
        return "ExponentialBackOff{mInitIntervalMillis=300000, mMaxIntervalMillis=14400000,"
                   + " mCurrentIntervalMillis="
                + this.mCurrentIntervalMillis
                + '}';
    }
}
