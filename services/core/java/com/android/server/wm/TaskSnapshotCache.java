package com.android.server.wm;

import android.app.WindowConfiguration;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Pair;
import android.window.TaskSnapshot;
import com.android.server.wm.SnapshotCache;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TaskSnapshotCache extends SnapshotCache {
    public static int sSplitModeMaxCacheSize;
    public final AppSnapshotLoader mLoader;
    public final List mTaskIdsInSplitMode;

    public TaskSnapshotCache(AppSnapshotLoader appSnapshotLoader) {
        super("Task");
        this.mTaskIdsInSplitMode = new ArrayList();
        this.mLoader = appSnapshotLoader;
    }

    public final TaskSnapshot getSnapshot(int i, int i2, boolean z, boolean z2, boolean z3) {
        AppSnapshotLoader appSnapshotLoader = this.mLoader;
        if (z2 && z3) {
            SystemClock.uptimeMillis();
            Trace.traceBegin(32L, "getSnapshot: tryRestoreFromDisk");
            TaskSnapshot loadTask = appSnapshotLoader.loadTask(i, i2, true);
            Trace.traceEnd(32L);
            if (loadTask != null) {
                return loadTask;
            }
        }
        TaskSnapshot snapshot = getSnapshot(Integer.valueOf(i));
        if (snapshot != null) {
            return snapshot;
        }
        if (z) {
            return appSnapshotLoader.loadTask(i, i2, z2);
        }
        return null;
    }

    @Override // com.android.server.wm.SnapshotCache
    public final void putSnapshot(WindowContainer windowContainer, TaskSnapshot taskSnapshot) {
        Task task = (Task) windowContainer;
        synchronized (this.mLock) {
            try {
                taskSnapshot.addReference(2);
                SnapshotCache.CacheEntry cacheEntry = (SnapshotCache.CacheEntry) this.mRunningCache.get(Integer.valueOf(task.mTaskId));
                if (cacheEntry != null) {
                    this.mAppIdMap.remove(cacheEntry.topApp);
                    cacheEntry.snapshot.removeReference(2);
                }
                ActivityRecord topMostActivity = task.getTopMostActivity();
                this.mAppIdMap.put(topMostActivity, Integer.valueOf(task.mTaskId));
                this.mRunningCache.put(Integer.valueOf(task.mTaskId), new SnapshotCache.CacheEntry(taskSnapshot, topMostActivity));
                removeOldestCacheIfNeeded(task);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeOldestCacheIfNeeded(Task task) {
        Task task2;
        if (this.mRunningCache.size() <= SnapshotCache.sMaxSnapshotCache) {
            return;
        }
        boolean z = true;
        if ((sSplitModeMaxCacheSize > 0) && WindowConfiguration.isSplitScreenWindowingMode(task.getWindowConfiguration())) {
            ((ArrayList) this.mTaskIdsInSplitMode).clear();
        } else {
            z = false;
        }
        long j = Long.MAX_VALUE;
        int i = -1;
        for (Integer num : this.mRunningCache.keySet()) {
            int intValue = num.intValue();
            SnapshotCache.CacheEntry cacheEntry = (SnapshotCache.CacheEntry) this.mRunningCache.get(num);
            if (cacheEntry != null) {
                long j2 = cacheEntry.timestamp;
                ActivityRecord activityRecord = cacheEntry.topApp;
                if (activityRecord != null) {
                    if (z && WindowConfiguration.isSplitScreenWindowingMode(activityRecord.getWindowConfiguration())) {
                        ((ArrayList) this.mTaskIdsInSplitMode).add(new Pair(num, Long.valueOf(j2)));
                    } else if (!activityRecord.mKeepSnapshotCache) {
                        if (!activityRecord.mStyleFillsParent && (task2 = activityRecord.task) != null && task2.getWindow(new Task$$ExternalSyntheticLambda0(7)) != null) {
                        }
                    }
                }
                if (j2 < j) {
                    i = intValue;
                    j = j2;
                }
            }
        }
        if (i != -1) {
            removeRunningEntry(Integer.valueOf(i));
        }
        if (!z || sSplitModeMaxCacheSize <= 0 || ((ArrayList) this.mTaskIdsInSplitMode).size() <= sSplitModeMaxCacheSize) {
            return;
        }
        Integer num2 = (Integer) ((Pair) ((List) this.mTaskIdsInSplitMode.stream().sorted(new TaskSnapshotCache$$ExternalSyntheticLambda0()).collect(Collectors.toList())).get(0)).first;
        num2.getClass();
        removeRunningEntry(num2);
    }
}
