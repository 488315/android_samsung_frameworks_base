package com.android.server.wm;

import android.hardware.HardwareBuffer;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Process;
import android.os.SystemClock;
import android.os.Trace;
import android.window.TaskSnapshot;

import com.android.server.LocalServices;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.pm.UserManagerInternal;

import java.io.File;
import java.util.ArrayDeque;

public final class SnapshotPersistQueue {
    public boolean mPaused;
    public boolean mQueueIdling;
    public boolean mStarted;
    public final ArrayDeque mWriteQueue = new ArrayDeque();
    public final ArrayDeque mStoreQueueItems = new ArrayDeque();
    public final Object mLock = new Object();
    public final AnonymousClass1 mPersister =
            new Thread() { // from class: com.android.server.wm.SnapshotPersistQueue.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    WriteQueueItem writeQueueItem;
                    boolean z;
                    Process.setThreadPriority(10);
                    while (true) {
                        synchronized (SnapshotPersistQueue.this.mLock) {
                            try {
                                SnapshotPersistQueue snapshotPersistQueue =
                                        SnapshotPersistQueue.this;
                                if (snapshotPersistQueue.mPaused) {
                                    writeQueueItem = null;
                                } else {
                                    writeQueueItem =
                                            (WriteQueueItem)
                                                    snapshotPersistQueue.mWriteQueue.poll();
                                    if (writeQueueItem != null) {
                                        if (writeQueueItem.isReady(
                                                SnapshotPersistQueue.this.mUserManagerInternal)) {
                                            writeQueueItem.onDequeuedLocked();
                                            z = true;
                                        } else {
                                            SnapshotPersistQueue.this.mWriteQueue.addLast(
                                                    writeQueueItem);
                                        }
                                    }
                                }
                                z = false;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (writeQueueItem != null) {
                            if (z) {
                                writeQueueItem.write();
                            }
                            SystemClock.sleep(100L);
                        }
                        synchronized (SnapshotPersistQueue.this.mLock) {
                            boolean isEmpty = SnapshotPersistQueue.this.mWriteQueue.isEmpty();
                            if (isEmpty || SnapshotPersistQueue.this.mPaused) {
                                try {
                                    SnapshotPersistQueue snapshotPersistQueue2 =
                                            SnapshotPersistQueue.this;
                                    snapshotPersistQueue2.mQueueIdling = isEmpty;
                                    snapshotPersistQueue2.mLock.wait();
                                    SnapshotPersistQueue.this.mQueueIdling = false;
                                } catch (InterruptedException unused) {
                                }
                            }
                        }
                    }
                }
            };
    public final UserManagerInternal mUserManagerInternal =
            (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);

    public final class CloseBufferWriteQueueItem extends WriteQueueItem {
        public HardwareBuffer mHardwareBuffer;

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        public final void write() {
            HardwareBuffer hardwareBuffer = this.mHardwareBuffer;
            if (hardwareBuffer != null) {
                hardwareBuffer.close();
                this.mHardwareBuffer = null;
            }
        }
    }

    public final class DeleteWriteQueueItem extends WriteQueueItem {
        public final int mId;

        public DeleteWriteQueueItem(
                int i,
                int i2,
                BaseAppSnapshotPersister$PersistInfoProvider
                        baseAppSnapshotPersister$PersistInfoProvider) {
            super(baseAppSnapshotPersister$PersistInfoProvider, i2);
            this.mId = i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("DeleteWriteQueueItem{ID=");
            sb.append(this.mId);
            sb.append(", UserId=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mUserId, sb, "}");
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        public final void write() {
            Trace.traceBegin(32L, "DeleteWriteQueueItem");
            SnapshotPersistQueue.this.getClass();
            SnapshotPersistQueue.deleteSnapshot(this.mId, this.mUserId, this.mPersistInfoProvider);
            Trace.traceEnd(32L);
        }
    }

    public final class StoreWriteQueueItem extends WriteQueueItem {
        public final int mId;
        public final TaskSnapshot mSnapshot;

        public StoreWriteQueueItem(
                int i,
                int i2,
                TaskSnapshot taskSnapshot,
                BaseAppSnapshotPersister$PersistInfoProvider
                        baseAppSnapshotPersister$PersistInfoProvider) {
            super(baseAppSnapshotPersister$PersistInfoProvider, i2);
            this.mId = i;
            taskSnapshot.addReference(4);
            this.mSnapshot = taskSnapshot;
        }

        public final boolean equals(Object obj) {
            if (obj == null || StoreWriteQueueItem.class != obj.getClass()) {
                return false;
            }
            StoreWriteQueueItem storeWriteQueueItem = (StoreWriteQueueItem) obj;
            return this.mId == storeWriteQueueItem.mId
                    && this.mUserId == storeWriteQueueItem.mUserId
                    && this.mPersistInfoProvider == storeWriteQueueItem.mPersistInfoProvider;
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        public final void onDequeuedLocked() {
            SnapshotPersistQueue.this.mStoreQueueItems.remove(this);
        }

        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        public final void onQueuedLocked() {
            SnapshotPersistQueue snapshotPersistQueue = SnapshotPersistQueue.this;
            snapshotPersistQueue.mStoreQueueItems.remove(this);
            snapshotPersistQueue.mStoreQueueItems.offer(this);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("StoreWriteQueueItem{ID=");
            sb.append(this.mId);
            sb.append(", UserId=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mUserId, sb, "}");
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x0242  */
        @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void write() {
            /*
                Method dump skipped, instructions count: 598
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.wm.SnapshotPersistQueue.StoreWriteQueueItem.write():void");
        }
    }

    public abstract class WriteQueueItem {
        public final BaseAppSnapshotPersister$PersistInfoProvider mPersistInfoProvider;
        public final int mUserId;

        public WriteQueueItem(
                BaseAppSnapshotPersister$PersistInfoProvider
                        baseAppSnapshotPersister$PersistInfoProvider,
                int i) {
            this.mPersistInfoProvider = baseAppSnapshotPersister$PersistInfoProvider;
            this.mUserId = i;
        }

        public boolean isReady(UserManagerInternal userManagerInternal) {
            return userManagerInternal.isUserUnlocked(this.mUserId);
        }

        public void onDequeuedLocked() {}

        public void onQueuedLocked() {}

        public abstract void write();
    }

    public static void deleteSnapshot(
            int i,
            int i2,
            BaseAppSnapshotPersister$PersistInfoProvider
                    baseAppSnapshotPersister$PersistInfoProvider) {
        File protoFile = baseAppSnapshotPersister$PersistInfoProvider.getProtoFile(i, i2);
        File lowResolutionBitmapFile =
                baseAppSnapshotPersister$PersistInfoProvider.getLowResolutionBitmapFile(i, i2);
        if (protoFile.exists()) {
            protoFile.delete();
        }
        if (lowResolutionBitmapFile.exists()) {
            lowResolutionBitmapFile.delete();
        }
        File highResolutionBitmapFile =
                baseAppSnapshotPersister$PersistInfoProvider.getHighResolutionBitmapFile(i, i2);
        if (highResolutionBitmapFile.exists()) {
            highResolutionBitmapFile.delete();
        }
    }

    public final void addToQueueInternal(WriteQueueItem writeQueueItem, boolean z) {
        this.mWriteQueue.removeFirstOccurrence(writeQueueItem);
        if (z) {
            this.mWriteQueue.addFirst(writeQueueItem);
        } else {
            this.mWriteQueue.addLast(writeQueueItem);
        }
        writeQueueItem.onQueuedLocked();
        while (this.mStoreQueueItems.size() > 2) {
            StoreWriteQueueItem storeWriteQueueItem =
                    (StoreWriteQueueItem) this.mStoreQueueItems.poll();
            this.mWriteQueue.remove(storeWriteQueueItem);
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Queue is too deep! Purged item with index="),
                    storeWriteQueueItem.mId,
                    "WindowManager");
        }
        if (this.mPaused) {
            return;
        }
        this.mLock.notifyAll();
    }

    public int peekQueueSize() {
        int size;
        synchronized (this.mLock) {
            size = this.mWriteQueue.size();
        }
        return size;
    }

    public void waitForQueueEmpty() {
        while (true) {
            synchronized (this.mLock) {
                try {
                    if (this.mWriteQueue.isEmpty() && this.mQueueIdling) {
                        return;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            SystemClock.sleep(100L);
        }
    }
}
