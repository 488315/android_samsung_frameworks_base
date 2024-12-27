package com.android.server.selinux;

import com.android.internal.os.Clock;

import java.time.Duration;
import java.time.Instant;

public final class RateLimiter {
    public final Clock mClock;
    public Instant mNextPermit;
    public final Duration mWindow;

    public RateLimiter(Clock clock, Duration duration) {
        this.mNextPermit = Instant.EPOCH;
        this.mClock = clock;
        this.mWindow = duration;
    }

    public RateLimiter(Duration duration) {
        this(Clock.SYSTEM_CLOCK, duration);
    }
}
