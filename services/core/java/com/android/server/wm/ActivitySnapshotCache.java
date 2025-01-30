package com.android.server.wm;

import android.window.TaskSnapshot;
import com.android.server.wm.SnapshotCache;

/* loaded from: classes3.dex */
public class ActivitySnapshotCache extends SnapshotCache {
    public ActivitySnapshotCache(WindowManagerService windowManagerService) {
        super(windowManagerService, "Activity");
    }

    @Override // com.android.server.wm.SnapshotCache
    public void putSnapshot(ActivityRecord activityRecord, TaskSnapshot taskSnapshot) {
        int identityHashCode = System.identityHashCode(activityRecord);
        SnapshotCache.CacheEntry cacheEntry = (SnapshotCache.CacheEntry) this.mRunningCache.get(Integer.valueOf(identityHashCode));
        if (cacheEntry != null) {
            this.mAppIdMap.remove(cacheEntry.topApp);
        }
        this.mAppIdMap.put(activityRecord, Integer.valueOf(identityHashCode));
        this.mRunningCache.put(Integer.valueOf(identityHashCode), new SnapshotCache.CacheEntry(taskSnapshot, activityRecord));
    }
}
