package com.android.server.wm;

import android.app.WindowConfiguration;
import android.util.Pair;
import android.window.TaskSnapshot;
import com.android.server.wm.SnapshotCache;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class TaskSnapshotCache extends SnapshotCache {
    public static int sSplitModeMaxCacheSize;
    public final AppSnapshotLoader mLoader;
    public List mTaskIdsInSplitMode;

    public TaskSnapshotCache(WindowManagerService windowManagerService, AppSnapshotLoader appSnapshotLoader) {
        super(windowManagerService, "Task");
        this.mTaskIdsInSplitMode = new ArrayList();
        this.mLoader = appSnapshotLoader;
    }

    @Override // com.android.server.wm.SnapshotCache
    public void putSnapshot(Task task, TaskSnapshot taskSnapshot) {
        SnapshotCache.CacheEntry cacheEntry = (SnapshotCache.CacheEntry) this.mRunningCache.get(Integer.valueOf(task.mTaskId));
        if (cacheEntry != null) {
            this.mAppIdMap.remove(cacheEntry.topApp);
        }
        ActivityRecord topMostActivity = task.getTopMostActivity();
        this.mAppIdMap.put(topMostActivity, Integer.valueOf(task.mTaskId));
        this.mRunningCache.put(Integer.valueOf(task.mTaskId), new SnapshotCache.CacheEntry(taskSnapshot, topMostActivity));
        removeOldestCacheIfNeeded(task);
    }

    public TaskSnapshot getSnapshot(int i, int i2, boolean z, boolean z2) {
        TaskSnapshot snapshot = getSnapshot(Integer.valueOf(i));
        if (snapshot != null) {
            return snapshot;
        }
        if (z) {
            return tryRestoreFromDisk(i, i2, z2);
        }
        return null;
    }

    public final TaskSnapshot tryRestoreFromDisk(int i, int i2, boolean z) {
        return this.mLoader.loadTask(i, i2, z);
    }

    public static void setMaxSnapshot(int i) {
        SnapshotCache.sMaxSnapshotCache = i;
        if (i <= 3) {
            sSplitModeMaxCacheSize = i * 2;
        } else {
            sSplitModeMaxCacheSize = 0;
        }
    }

    public final void removeOldestCacheIfNeeded(Task task) {
        boolean z;
        ActivityRecord activityRecord;
        if (this.mRunningCache.size() <= SnapshotCache.sMaxSnapshotCache) {
            return;
        }
        if (shouldApplySplitModeSnapshotPolicy() && WindowConfiguration.isSplitScreenWindowingMode(task.getWindowConfiguration())) {
            this.mTaskIdsInSplitMode.clear();
            z = true;
        } else {
            z = false;
        }
        Iterator it = this.mRunningCache.keySet().iterator();
        long j = Long.MAX_VALUE;
        int i = -1;
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            SnapshotCache.CacheEntry cacheEntry = (SnapshotCache.CacheEntry) this.mRunningCache.get(Integer.valueOf(intValue));
            if (cacheEntry != null) {
                if (z && (activityRecord = cacheEntry.topApp) != null && WindowConfiguration.isSplitScreenWindowingMode(activityRecord.getWindowConfiguration())) {
                    this.mTaskIdsInSplitMode.add(new Pair(Integer.valueOf(intValue), Long.valueOf(cacheEntry.timestamp)));
                } else {
                    ActivityRecord activityRecord2 = cacheEntry.topApp;
                    if (activityRecord2 == null || !activityRecord2.mKeepSnapshotCache) {
                        long j2 = cacheEntry.timestamp;
                        if (j2 < j) {
                            i = intValue;
                            j = j2;
                        }
                    }
                }
            }
        }
        if (i != -1) {
            removeRunningEntry(Integer.valueOf(i));
        }
        if (z) {
            updateSplitModeSnapshotCache();
        }
    }

    public final boolean shouldApplySplitModeSnapshotPolicy() {
        return sSplitModeMaxCacheSize > 0;
    }

    public final void updateSplitModeSnapshotCache() {
        if (!shouldApplySplitModeSnapshotPolicy() || this.mTaskIdsInSplitMode.size() <= sSplitModeMaxCacheSize) {
            return;
        }
        removeRunningEntry(Integer.valueOf(((Integer) ((Pair) ((List) this.mTaskIdsInSplitMode.stream().sorted(new Comparator() { // from class: com.android.server.wm.TaskSnapshotCache$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$updateSplitModeSnapshotCache$0;
                lambda$updateSplitModeSnapshotCache$0 = TaskSnapshotCache.lambda$updateSplitModeSnapshotCache$0((Pair) obj, (Pair) obj2);
                return lambda$updateSplitModeSnapshotCache$0;
            }
        }).collect(Collectors.toList())).get(0)).first).intValue()));
    }

    public static /* synthetic */ int lambda$updateSplitModeSnapshotCache$0(Pair pair, Pair pair2) {
        return Long.compare(((Long) pair.second).longValue(), ((Long) pair2.second).longValue());
    }
}
