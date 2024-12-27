package com.android.server.selinux;

import com.android.internal.os.Clock;

import java.time.Duration;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class QuotaLimiter {
    public final Clock mClock;
    public long mCurrentWindow;
    public int mMaxPermits;
    public int mPermitsGranted;
    public final Duration mWindowSize;

    public QuotaLimiter(Clock clock, Duration duration, int i) {
        this.mClock = clock;
        this.mWindowSize = duration;
        this.mMaxPermits = i;
    }
}
