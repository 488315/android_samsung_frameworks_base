package com.android.server.selinux;

import com.android.internal.os.Clock;
import java.time.Duration;
import java.time.Instant;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
