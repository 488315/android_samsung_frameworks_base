package com.android.systemui.util;

import android.os.Handler;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.HeapDumpHelper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MemoryMonitor_Factory implements Provider {
    private final javax.inject.Provider bootCompleteCacheProvider;
    private final javax.inject.Provider dumpManagerProvider;
    private final javax.inject.Provider heapDumpHelperProvider;
    private final javax.inject.Provider mainHandlerProvider;
    private final javax.inject.Provider notifCollectionProvider;
    private final javax.inject.Provider preHandlerManagerProvider;

    public MemoryMonitor_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        this.mainHandlerProvider = provider;
        this.bootCompleteCacheProvider = provider2;
        this.heapDumpHelperProvider = provider3;
        this.dumpManagerProvider = provider4;
        this.preHandlerManagerProvider = provider5;
        this.notifCollectionProvider = provider6;
    }

    public static MemoryMonitor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new MemoryMonitor_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static MemoryMonitor newInstance(Handler handler, BootCompleteCache bootCompleteCache, HeapDumpHelper heapDumpHelper, DumpManager dumpManager, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager, CommonNotifCollection commonNotifCollection) {
        return new MemoryMonitor(handler, bootCompleteCache, heapDumpHelper, dumpManager, uncaughtExceptionPreHandlerManager, commonNotifCollection);
    }

    @Override // javax.inject.Provider
    public MemoryMonitor get() {
        return newInstance((Handler) this.mainHandlerProvider.get(), (BootCompleteCache) this.bootCompleteCacheProvider.get(), (HeapDumpHelper) this.heapDumpHelperProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (UncaughtExceptionPreHandlerManager) this.preHandlerManagerProvider.get(), (CommonNotifCollection) this.notifCollectionProvider.get());
    }
}
