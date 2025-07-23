package com.android.net.module.util;

import android.util.LruCache;
import java.util.Objects;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class LruCacheWithExpiry<K, V> {
    private final long mExpiryDurationMs;
    private final LruCache<K, CacheValue<V>> mMap;
    private final Predicate<V> mShouldCacheValue;
    private final LongSupplier mTimeSupplier;

    public LruCacheWithExpiry(LongSupplier longSupplier, long j, int i, Predicate<V> predicate) {
        this.mTimeSupplier = longSupplier;
        this.mExpiryDurationMs = j;
        this.mMap = new LruCache<>(i);
        this.mShouldCacheValue = predicate;
    }

    public V get(K k) {
        synchronized (this.mMap) {
            CacheValue<V> cacheValue = this.mMap.get(k);
            if (cacheValue != null && !isExpired(cacheValue.timestamp)) {
                return cacheValue.entry;
            }
            this.mMap.remove(k);
            return null;
        }
    }

    public V getOrCompute(K k, Supplier<V> supplier) {
        synchronized (this.mMap) {
            V v = get(k);
            if (v != null) {
                return v;
            }
            V v2 = supplier.get();
            if (v2 != null && this.mShouldCacheValue.test(v2)) {
                put(k, v2);
            }
            return v2;
        }
    }

    public void put(K k, V v) {
        Objects.requireNonNull(v);
        synchronized (this.mMap) {
            this.mMap.put(k, new CacheValue<>(this.mTimeSupplier.getAsLong(), v));
        }
    }

    public V putIfAbsent(K k, V v) {
        V v2;
        Objects.requireNonNull(v);
        synchronized (this.mMap) {
            v2 = get(k);
            if (v2 == null) {
                put(k, v);
            }
        }
        return v2;
    }

    public void clear() {
        synchronized (this.mMap) {
            this.mMap.evictAll();
        }
    }

    private boolean isExpired(long j) {
        return this.mTimeSupplier.getAsLong() > j + this.mExpiryDurationMs;
    }

    private static class CacheValue<V> {
        public final V entry;
        public final long timestamp;

        CacheValue(long j, V v) {
            this.timestamp = j;
            this.entry = v;
        }
    }
}
