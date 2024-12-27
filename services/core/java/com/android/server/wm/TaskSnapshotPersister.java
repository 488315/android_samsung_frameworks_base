package com.android.server.wm;

import android.os.Trace;
import android.util.ArraySet;
import android.window.TaskSnapshot;

import com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0;
import com.android.server.pm.UserManagerInternal;
import com.android.server.wm.SnapshotPersistQueue.DeleteWriteQueueItem;
import com.android.server.wm.SnapshotPersistQueue.StoreWriteQueueItem;

import java.io.File;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TaskSnapshotPersister {
    public final Object mLock;
    public final BaseAppSnapshotPersister$PersistInfoProvider mPersistInfoProvider;
    public final ArraySet mPersistedTaskIdsSinceLastRemoveObsolete = new ArraySet();
    public final SnapshotPersistQueue mSnapshotPersistQueue;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class RemoveObsoleteFilesQueueItem extends SnapshotPersistQueue.WriteQueueItem {
        public final ArraySet mPersistentTaskIds;
        public final int[] mRunningUserIds;

        public RemoveObsoleteFilesQueueItem(
                ArraySet arraySet,
                int[] iArr,
                BaseAppSnapshotPersister$PersistInfoProvider
                        baseAppSnapshotPersister$PersistInfoProvider) {
            super(baseAppSnapshotPersister$PersistInfoProvider, iArr.length > 0 ? iArr[0] : 0);
            this.mPersistentTaskIds = new ArraySet(arraySet);
            this.mRunningUserIds = Arrays.copyOf(iArr, iArr.length);
        }

        public int getTaskId(String str) {
            int lastIndexOf;
            if ((!str.endsWith(".proto") && !str.endsWith(".jpg"))
                    || (lastIndexOf = str.lastIndexOf(46)) == -1) {
                return -1;
            }
            String substring = str.substring(0, lastIndexOf);
            if (substring.endsWith("_reduced")) {
                substring =
                        DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(
                                8, 0, substring);
            }
            try {
                return Integer.parseInt(substring);
            } catch (NumberFormatException unused) {
                return -1;
            }
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        public final boolean isReady(UserManagerInternal userManagerInternal) {
            int[] iArr = this.mRunningUserIds;
            for (int length = iArr.length - 1; length >= 0; length--) {
                if (!userManagerInternal.isUserUnlocked(iArr[length])) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        public final void write() {
            ArraySet arraySet;
            Trace.traceBegin(32L, "RemoveObsoleteFilesQueueItem");
            synchronized (TaskSnapshotPersister.this.mLock) {
                arraySet =
                        new ArraySet(
                                TaskSnapshotPersister.this
                                        .mPersistedTaskIdsSinceLastRemoveObsolete);
            }
            for (int i : this.mRunningUserIds) {
                File directory = this.mPersistInfoProvider.getDirectory(i);
                String[] list = directory.list();
                if (list != null) {
                    for (String str : list) {
                        int taskId = getTaskId(str);
                        if (!this.mPersistentTaskIds.contains(Integer.valueOf(taskId))
                                && !arraySet.contains(Integer.valueOf(taskId))) {
                            new File(directory, str).delete();
                        }
                    }
                }
            }
            Trace.traceEnd(32L);
        }
    }

    public TaskSnapshotPersister(
            SnapshotPersistQueue snapshotPersistQueue,
            BaseAppSnapshotPersister$PersistInfoProvider
                    baseAppSnapshotPersister$PersistInfoProvider) {
        this.mSnapshotPersistQueue = snapshotPersistQueue;
        this.mPersistInfoProvider = baseAppSnapshotPersister$PersistInfoProvider;
        this.mLock = snapshotPersistQueue.mLock;
    }

    public final void persistSnapshot(int i, int i2, TaskSnapshot taskSnapshot) {
        synchronized (this.mLock) {
            this.mPersistedTaskIdsSinceLastRemoveObsolete.add(Integer.valueOf(i));
            synchronized (this.mLock) {
                SnapshotPersistQueue snapshotPersistQueue = this.mSnapshotPersistQueue;
                BaseAppSnapshotPersister$PersistInfoProvider
                        baseAppSnapshotPersister$PersistInfoProvider = this.mPersistInfoProvider;
                snapshotPersistQueue.getClass();
                snapshotPersistQueue.addToQueueInternal(
                        snapshotPersistQueue
                        .new StoreWriteQueueItem(
                                i, i2, taskSnapshot, baseAppSnapshotPersister$PersistInfoProvider),
                        false);
            }
        }
    }

    public final void removeSnapshot(int i, int i2) {
        synchronized (this.mLock) {
            this.mPersistedTaskIdsSinceLastRemoveObsolete.remove(Integer.valueOf(i));
            synchronized (this.mLock) {
                SnapshotPersistQueue snapshotPersistQueue = this.mSnapshotPersistQueue;
                BaseAppSnapshotPersister$PersistInfoProvider
                        baseAppSnapshotPersister$PersistInfoProvider = this.mPersistInfoProvider;
                snapshotPersistQueue.getClass();
                snapshotPersistQueue.addToQueueInternal(
                        snapshotPersistQueue
                        .new DeleteWriteQueueItem(
                                i, i2, baseAppSnapshotPersister$PersistInfoProvider),
                        false);
            }
        }
    }
}
