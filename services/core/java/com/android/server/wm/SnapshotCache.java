package com.android.server.wm;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.window.TaskSnapshot;

public abstract class SnapshotCache {
    public static int sMaxSnapshotCache = 5;
    public final String mName;
    public final Object mLock = new Object();
    public final ArrayMap mAppIdMap = new ArrayMap();
    public final ArrayMap mRunningCache = new ArrayMap();

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
