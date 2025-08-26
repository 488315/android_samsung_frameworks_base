package com.android.internal.util;

import android.os.SystemClock;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes4.dex */
public class RateLimitingCache<Value> {
    private final AtomicReference<RateLimitingCache<Value>.CachedValue> mCachedValue;
    private final int mLimit;
    private final long mPeriodMillis;
    private final long mRandomOffset;

    public interface ValueFetcher<V> {
        V fetchValue();
    }

    class CachedValue {
        AtomicInteger count;
        long timestamp;
        Value value;

        CachedValue(RateLimitingCache rateLimitingCache) {
        }
    }

    public RateLimitingCache(long j) {
        this(j, 1);
    }

    public RateLimitingCache(long j, int i) {
        this.mCachedValue = new AtomicReference<>();
        this.mPeriodMillis = j;
        this.mLimit = i;
        if (i > 1 && j > 1) {
            this.mRandomOffset = (long) (Math.random() * (j / 2));
        } else {
            this.mRandomOffset = 0L;
        }
    }

    protected long getTime() {
        return SystemClock.elapsedRealtime();
    }

    public Value get(ValueFetcher<Value> valueFetcher) {
        RateLimitingCache<Value>.CachedValue cachedValue = this.mCachedValue.get();
        if (this.mPeriodMillis < 0 && cachedValue != null && cachedValue.timestamp != 0) {
            return cachedValue.value;
        }
        long time = getTime() + this.mRandomOffset;
        boolean z = cachedValue == null || time - cachedValue.timestamp >= this.mPeriodMillis;
        if (z || cachedValue.count.getAndIncrement() < this.mLimit) {
            Value valueFetchValue = valueFetcher.fetchValue();
            if (this.mLimit > 1) {
                time -= time % this.mPeriodMillis;
            }
            RateLimitingCache<Value>.CachedValue cachedValue2 = new CachedValue(this);
            cachedValue2.value = valueFetchValue;
            cachedValue2.timestamp = time;
            if (z) {
                cachedValue2.count = new AtomicInteger(1);
            } else {
                cachedValue2.count = cachedValue.count;
            }
            this.mCachedValue.compareAndSet(cachedValue, cachedValue2);
        }
        return this.mCachedValue.get().value;
    }
}
