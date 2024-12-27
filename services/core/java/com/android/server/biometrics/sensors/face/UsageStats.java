package com.android.server.biometrics.sensors.face;

import android.content.Context;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import java.util.ArrayDeque;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UsageStats {
    public int mAcceptCount;
    public long mAcceptLatency;
    public int mAuthAttemptCount;
    public ArrayDeque mAuthenticationEvents;
    public Context mContext;
    public int mErrorCount;
    public SparseIntArray mErrorFrequencyMap;
    public long mErrorLatency;
    public SparseLongArray mErrorLatencyMap;
    public int mRejectCount;
    public long mRejectLatency;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthenticationEvent {
        public final boolean mAuthenticated;
        public final int mError;
        public final long mLatency;
        public final long mStartTime;
        public final int mUser;
        public final int mVendorError;

        public AuthenticationEvent(long j, long j2, boolean z, int i, int i2, int i3) {
            this.mStartTime = j;
            this.mLatency = j2;
            this.mAuthenticated = z;
            this.mError = i;
            this.mVendorError = i2;
            this.mUser = i3;
        }
    }

    public final void addEvent(AuthenticationEvent authenticationEvent) {
        this.mAuthAttemptCount++;
        if (this.mAuthenticationEvents.size() >= 100) {
            this.mAuthenticationEvents.removeFirst();
        }
        this.mAuthenticationEvents.add(authenticationEvent);
        boolean z = authenticationEvent.mAuthenticated;
        long j = authenticationEvent.mLatency;
        if (z) {
            this.mAcceptCount++;
            this.mAcceptLatency += j;
            return;
        }
        int i = authenticationEvent.mError;
        if (i == 0) {
            this.mRejectCount++;
            this.mRejectLatency += j;
            return;
        }
        this.mErrorCount++;
        this.mErrorLatency += j;
        SparseIntArray sparseIntArray = this.mErrorFrequencyMap;
        sparseIntArray.put(i, sparseIntArray.get(i, 0) + 1);
        SparseLongArray sparseLongArray = this.mErrorLatencyMap;
        sparseLongArray.put(i, sparseLongArray.get(i, 0L) + j);
    }
}
