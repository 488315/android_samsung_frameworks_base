package com.android.p038wm.shell.dagger;

import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.common.TransactionPool;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
