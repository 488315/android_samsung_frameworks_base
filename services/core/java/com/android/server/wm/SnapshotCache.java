package com.android.server.wm;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.window.TaskSnapshot;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SnapshotCache {
    public static int sMaxSnapshotCache = 5;
    public final String mName;
    public final Object mLock = new Object();
    public final ArrayMap mAppIdMap = new ArrayMap();
    public final ArrayMap mRunningCache = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CacheEntry {
        public final TaskSnapshot snapshot;
        public final long timestamp = SystemClock.elapsedRealtime();
        public final ActivityRecord topApp;

        public CacheEntry(TaskSnapshot taskSnapshot, ActivityRecord activityRecord) {
            this.snapshot = taskSnapshot;
            this.topApp = activityRecord;
        }
    }

    public SnapshotCache(String str) {
        this.mName = str;
    }

    public final TaskSnapshot getSnapshot(Integer num) {
        synchronized (this.mLock) {
            try {
                CacheEntry cacheEntry = (CacheEntry) this.mRunningCache.get(num);
                if (cacheEntry == null) {
                    return null;
                }
                return cacheEntry.snapshot;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract void putSnapshot(WindowContainer windowContainer, TaskSnapshot taskSnapshot);

    public final void removeRunningEntry(Integer num) {
        synchronized (this.mLock) {
            try {
                CacheEntry cacheEntry = (CacheEntry) this.mRunningCache.get(num);
                if (cacheEntry != null) {
                    this.mAppIdMap.remove(cacheEntry.topApp);
                    this.mRunningCache.remove(num);
                    cacheEntry.snapshot.removeReference(2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
