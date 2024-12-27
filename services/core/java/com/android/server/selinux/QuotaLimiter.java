package com.android.server.selinux;

import com.android.internal.os.Clock;

import java.time.Duration;

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
