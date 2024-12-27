package com.android.server.wm;

import android.window.TaskSnapshot;
import com.android.server.wm.SnapshotCache;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ActivitySnapshotCache extends SnapshotCache {
    @Override // com.android.server.wm.SnapshotCache
    public final void putSnapshot(TaskSnapshot taskSnapshot, ActivityRecord activityRecord) {
        int identityHashCode = System.identityHashCode(activityRecord);
        taskSnapshot.addReference(2);
        synchronized (this.mLock) {
            try {
                SnapshotCache.CacheEntry cacheEntry = (SnapshotCache.CacheEntry) this.mRunningCache.get(Integer.valueOf(identityHashCode));
                if (cacheEntry != null) {
                    this.mAppIdMap.remove(cacheEntry.topApp);
                    cacheEntry.snapshot.removeReference(2);
                }
                this.mAppIdMap.put(activityRecord, Integer.valueOf(identityHashCode));
                this.mRunningCache.put(Integer.valueOf(identityHashCode), new SnapshotCache.CacheEntry(taskSnapshot, activityRecord));
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
