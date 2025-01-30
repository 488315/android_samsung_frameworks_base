package com.android.p038wm.shell.common;

import android.util.Pools;
import android.view.SurfaceControl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TransactionPool {
    public final Pools.SynchronizedPool mTransactionPool = new Pools.SynchronizedPool(4);

    public final SurfaceControl.Transaction acquire() {
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mTransactionPool.acquire();
        return transaction == null ? new SurfaceControl.Transaction() : transaction;
    }

    public final void release(SurfaceControl.Transaction transaction) {
        if (this.mTransactionPool.release(transaction)) {
            return;
        }
        transaction.close();
    }
}
