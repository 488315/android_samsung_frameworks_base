package com.android.server.wm;

import android.window.TaskSnapshot;

public final class ActivitySnapshotCache extends SnapshotCache {
    @Override // com.android.server.wm.SnapshotCache
    public final void putSnapshot(TaskSnapshot taskSnapshot, ActivityRecord activityRecord) {
        int identityHashCode = System.identityHashCode(activityRecord);
        taskSnapshot.addReference(2);
        synchronized (this.mLock) {
            try {
                SnapshotCache.CacheEntry cacheEntry =
                        (SnapshotCache.CacheEntry)
                                this.mRunningCache.get(Integer.valueOf(identityHashCode));
                if (cacheEntry != null) {
                    this.mAppIdMap.remove(cacheEntry.topApp);
                    cacheEntry.snapshot.removeReference(2);
                }
                this.mAppIdMap.put(activityRecord, Integer.valueOf(identityHashCode));
                this.mRunningCache.put(
                        Integer.valueOf(identityHashCode),
                        new SnapshotCache.CacheEntry(taskSnapshot, activityRecord));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
