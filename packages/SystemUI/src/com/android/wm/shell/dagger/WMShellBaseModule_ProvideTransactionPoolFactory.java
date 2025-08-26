package com.android.wm.shell.dagger;

import com.android.wm.shell.shared.TransactionPool;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideTransactionPoolFactory implements Provider {
    public static TransactionPool provideTransactionPool() {
        return new TransactionPool();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new TransactionPool();
    }
}
