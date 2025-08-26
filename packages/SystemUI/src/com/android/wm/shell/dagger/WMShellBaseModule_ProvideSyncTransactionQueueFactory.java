package com.android.wm.shell.dagger;

import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.shared.TransactionPool;
import dagger.internal.Provider;

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
