package com.android.wm.shell.dagger;

import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.shared.TransactionPool;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideSyncTransactionQueueFactory implements Provider {
    public final Provider mainExecutorProvider;
    public final Provider poolProvider;

    public WMShellBaseModule_ProvideSyncTransactionQueueFactory(Provider provider, Provider provider2) {
        this.poolProvider = provider;
        this.mainExecutorProvider = provider2;
    }

    public static SyncTransactionQueue provideSyncTransactionQueue(TransactionPool transactionPool, ShellExecutor shellExecutor) {
        return new SyncTransactionQueue(transactionPool, shellExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new SyncTransactionQueue((TransactionPool) this.poolProvider.get(), (ShellExecutor) this.mainExecutorProvider.get());
    }
}
