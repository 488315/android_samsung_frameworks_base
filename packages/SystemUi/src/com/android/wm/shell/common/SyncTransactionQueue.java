package com.android.wm.shell.common;

import android.os.RemoteException;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import android.window.WindowContainerTransactionCallback;
import android.window.WindowOrganizer;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.transition.LegacyTransitions$ILegacyTransition;
import com.android.wm.shell.transition.LegacyTransitions$LegacyTransition;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SyncTransactionQueue {
    public final ShellExecutor mMainExecutor;
    public final TransactionPool mTransactionPool;
    public final ArrayList mQueue = new ArrayList();
    public SyncCallback mInFlight = null;
    public final ArrayList mRunnables = new ArrayList();
    public final SyncTransactionQueue$$ExternalSyntheticLambda0 mOnReplyTimeout = new Runnable() { // from class: com.android.wm.shell.common.SyncTransactionQueue$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            SyncTransactionQueue syncTransactionQueue = SyncTransactionQueue.this;
            synchronized (syncTransactionQueue.mQueue) {
                SyncTransactionQueue.SyncCallback syncCallback = syncTransactionQueue.mInFlight;
                if (syncCallback != null && syncTransactionQueue.mQueue.contains(syncCallback)) {
                    Slog.w("SyncTransactionQueue", "Sync Transaction timed-out: " + syncTransactionQueue.mInFlight.mWCT);
                    SyncTransactionQueue.SyncCallback syncCallback2 = syncTransactionQueue.mInFlight;
                    syncCallback2.onTransactionReady(syncCallback2.mId, new SurfaceControl.Transaction());
                }
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TransactionRunnable {
        void runWithTransaction(SurfaceControl.Transaction transaction);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.common.SyncTransactionQueue$$ExternalSyntheticLambda0] */
    public SyncTransactionQueue(TransactionPool transactionPool, ShellExecutor shellExecutor) {
        this.mTransactionPool = transactionPool;
        this.mMainExecutor = shellExecutor;
    }

    public final void queue(WindowContainerTransaction windowContainerTransaction) {
        if (windowContainerTransaction.isEmpty()) {
            return;
        }
        SyncCallback syncCallback = new SyncCallback(windowContainerTransaction);
        synchronized (this.mQueue) {
            this.mQueue.add(syncCallback);
            if (this.mQueue.size() == 1) {
                syncCallback.send();
            }
        }
    }

    public final void runInSync(TransactionRunnable transactionRunnable) {
        synchronized (this.mQueue) {
            if (this.mInFlight != null) {
                this.mRunnables.add(transactionRunnable);
                return;
            }
            SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
            transactionRunnable.runWithTransaction(acquire);
            acquire.apply();
            this.mTransactionPool.release(acquire);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SyncCallback extends WindowContainerTransactionCallback {
        public int mId;
        public final LegacyTransitions$LegacyTransition mLegacyTransition;
        public final WindowContainerTransaction mWCT;

        public SyncCallback(WindowContainerTransaction windowContainerTransaction) {
            this.mId = -1;
            this.mWCT = windowContainerTransaction;
            this.mLegacyTransition = null;
        }

        public final void onTransactionReady(final int i, final SurfaceControl.Transaction transaction) {
            ((HandlerExecutor) SyncTransactionQueue.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.common.SyncTransactionQueue$SyncCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SyncTransactionQueue.SyncCallback syncCallback = SyncTransactionQueue.SyncCallback.this;
                    int i2 = i;
                    SurfaceControl.Transaction transaction2 = transaction;
                    synchronized (SyncTransactionQueue.this.mQueue) {
                        if (syncCallback.mId != i2) {
                            Slog.e("SyncTransactionQueue", "Got an unexpected onTransactionReady. Expected " + syncCallback.mId + " but got " + i2);
                            return;
                        }
                        SyncTransactionQueue syncTransactionQueue = SyncTransactionQueue.this;
                        syncTransactionQueue.mInFlight = null;
                        ((HandlerExecutor) syncTransactionQueue.mMainExecutor).removeCallbacks(syncTransactionQueue.mOnReplyTimeout);
                        SyncTransactionQueue.this.mQueue.remove(syncCallback);
                        ArrayList arrayList = SyncTransactionQueue.this.mRunnables;
                        int size = arrayList.size();
                        for (int i3 = 0; i3 < size; i3++) {
                            ((SyncTransactionQueue.TransactionRunnable) arrayList.get(i3)).runWithTransaction(transaction2);
                        }
                        arrayList.subList(0, size).clear();
                        LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition = syncCallback.mLegacyTransition;
                        if (legacyTransitions$LegacyTransition != null) {
                            try {
                                legacyTransitions$LegacyTransition.mSyncCallback.onTransactionReady(syncCallback.mId, transaction2);
                            } catch (RemoteException e) {
                                Slog.e("SyncTransactionQueue", "Error sending callback to legacy transition: " + syncCallback.mId, e);
                            }
                        } else {
                            transaction2.apply();
                            transaction2.close();
                        }
                        if (!SyncTransactionQueue.this.mQueue.isEmpty()) {
                            ((SyncTransactionQueue.SyncCallback) SyncTransactionQueue.this.mQueue.get(0)).send();
                        }
                    }
                }
            });
        }

        public final void send() {
            SyncTransactionQueue syncTransactionQueue = SyncTransactionQueue.this;
            SyncCallback syncCallback = syncTransactionQueue.mInFlight;
            if (syncCallback == this) {
                return;
            }
            if (syncCallback != null) {
                throw new IllegalStateException("Sync Transactions must be serialized. In Flight: " + SyncTransactionQueue.this.mInFlight.mId + " - " + SyncTransactionQueue.this.mInFlight.mWCT);
            }
            syncTransactionQueue.mInFlight = this;
            if (this.mLegacyTransition != null) {
                WindowOrganizer windowOrganizer = new WindowOrganizer();
                LegacyTransitions$LegacyTransition legacyTransitions$LegacyTransition = this.mLegacyTransition;
                this.mId = windowOrganizer.startLegacyTransition(legacyTransitions$LegacyTransition.mTransit, legacyTransitions$LegacyTransition.mAdapter, this, this.mWCT);
            } else {
                this.mId = new WindowOrganizer().applySyncTransaction(this.mWCT, this);
            }
            SyncTransactionQueue syncTransactionQueue2 = SyncTransactionQueue.this;
            ((HandlerExecutor) syncTransactionQueue2.mMainExecutor).executeDelayed(5300L, syncTransactionQueue2.mOnReplyTimeout);
        }

        public SyncCallback(LegacyTransitions$ILegacyTransition legacyTransitions$ILegacyTransition, int i, WindowContainerTransaction windowContainerTransaction) {
            this.mId = -1;
            this.mWCT = windowContainerTransaction;
            this.mLegacyTransition = new LegacyTransitions$LegacyTransition(i, legacyTransitions$ILegacyTransition);
        }
    }

    public final void queue(LegacyTransitions$ILegacyTransition legacyTransitions$ILegacyTransition, WindowContainerTransaction windowContainerTransaction) {
        if (windowContainerTransaction.isEmpty()) {
            return;
        }
        SyncCallback syncCallback = new SyncCallback(legacyTransitions$ILegacyTransition, 1, windowContainerTransaction);
        synchronized (this.mQueue) {
            this.mQueue.add(syncCallback);
            if (this.mQueue.size() == 1) {
                syncCallback.send();
            }
        }
    }
}
