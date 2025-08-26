package com.google.android.mms.util;

import java.util.HashMap;

/* loaded from: classes6.dex */
public abstract class AbstractCache<K, V> {
    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int MAX_CACHED_ITEMS = 500;
    private static final String TAG = "AbstractCache";
    private final HashMap<K, CacheEntry<V>> mCacheMap = new HashMap<>();

    protected AbstractCache() {
    }

    public boolean put(K k, V v) {
        if (this.mCacheMap.size() >= 500 || k == null) {
            return false;
        }
        CacheEntry<V> cacheEntry = new CacheEntry<>();
        cacheEntry.value = v;
        this.mCacheMap.put(k, cacheEntry);
        return true;
    }

    public V get(K k) {
        CacheEntry<V> cacheEntry;
        if (k == null || (cacheEntry = this.mCacheMap.get(k)) == null) {
            return null;
        }
        cacheEntry.hit++;
        return cacheEntry.value;
    }

    public V purge(K k) {
        CacheEntry<V> cacheEntryRemove = this.mCacheMap.remove(k);
        if (cacheEntryRemove != null) {
            return cacheEntryRemove.value;
        }
        return null;
    }

    public void purgeAll() {
        this.mCacheMap.clear();
    }

    public int size() {
        return this.mCacheMap.size();
    }

    private static class CacheEntry<V> {
        int hit;
        V value;

        private CacheEntry() {
        }
    }
}
