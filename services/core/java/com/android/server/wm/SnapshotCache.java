package com.android.server.wm;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.window.TaskSnapshot;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public abstract class SnapshotCache {
    public static int sMaxSnapshotCache = 5;
    public final String mName;
    public final WindowManagerService mService;
    public final ArrayMap mAppIdMap = new ArrayMap();
    public final ArrayMap mRunningCache = new ArrayMap();

    public abstract void putSnapshot(WindowContainer windowContainer, TaskSnapshot taskSnapshot);

    public SnapshotCache(WindowManagerService windowManagerService, String str) {
        this.mService = windowManagerService;
        this.mName = str;
    }

    public void clearRunningCache() {
        this.mRunningCache.clear();
    }

    public final TaskSnapshot getSnapshot(Integer num) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                CacheEntry cacheEntry = (CacheEntry) this.mRunningCache.get(num);
                if (cacheEntry == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return null;
                }
                TaskSnapshot taskSnapshot = cacheEntry.snapshot;
                WindowManagerService.resetPriorityAfterLockedSection();
                return taskSnapshot;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public void onAppRemoved(ActivityRecord activityRecord) {
        Integer num = (Integer) this.mAppIdMap.get(activityRecord);
        if (num != null) {
            removeRunningEntry(num);
        }
    }

    public void onAppDied(ActivityRecord activityRecord) {
        Integer num = (Integer) this.mAppIdMap.get(activityRecord);
        if (num != null) {
            removeRunningEntry(num);
        }
    }

    public void onIdRemoved(Integer num) {
        removeRunningEntry(num);
    }

    public void removeRunningEntry(Integer num) {
        CacheEntry cacheEntry = (CacheEntry) this.mRunningCache.get(num);
        if (cacheEntry != null) {
            this.mAppIdMap.remove(cacheEntry.topApp);
            this.mRunningCache.remove(num);
        }
    }

    public void dump(PrintWriter printWriter, String str) {
        String str2 = str + "  ";
        String str3 = str2 + "  ";
        printWriter.println(str + "SnapshotCache " + this.mName);
        for (int size = this.mRunningCache.size() + (-1); size >= 0; size += -1) {
            CacheEntry cacheEntry = (CacheEntry) this.mRunningCache.valueAt(size);
            printWriter.println(str2 + "Entry token=" + this.mRunningCache.keyAt(size));
            printWriter.println(str3 + "topApp=" + cacheEntry.topApp);
            printWriter.println(str3 + "snapshot=" + cacheEntry.snapshot);
        }
        printWriter.print(str);
        printWriter.print("MaxSnapshotCache=");
        printWriter.println(sMaxSnapshotCache);
    }

    public final class CacheEntry {
        public final TaskSnapshot snapshot;
        public final long timestamp = SystemClock.elapsedRealtime();
        public final ActivityRecord topApp;

        public CacheEntry(TaskSnapshot taskSnapshot, ActivityRecord activityRecord) {
            this.snapshot = taskSnapshot;
            this.topApp = activityRecord;
        }
    }
}
