package com.android.wm.shell.dagger;

import com.android.wm.shell.common.LaunchAdjacentController;
import com.android.wm.shell.common.SyncTransactionQueue;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellBaseModule_ProvideLaunchAdjacentControllerFactory implements Provider {
    public final Provider syncQueueProvider;

    public WMShellBaseModule_ProvideLaunchAdjacentControllerFactory(Provider provider) {
        this.syncQueueProvider = provider;
    }

    public static LaunchAdjacentController provideLaunchAdjacentController(SyncTransactionQueue syncTransactionQueue) {
        return new LaunchAdjacentController(syncTransactionQueue);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new LaunchAdjacentController((SyncTransactionQueue) this.syncQueueProvider.get());
    }
}
