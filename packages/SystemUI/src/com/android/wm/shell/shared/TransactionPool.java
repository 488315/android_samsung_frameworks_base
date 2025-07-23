package com.android.wm.shell.shared;

import android.util.Pools;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TransactionPool {
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
