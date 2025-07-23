package com.android.systemui.util;

import android.os.Handler;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.HeapDumpHelper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MemoryMonitor_Factory implements Provider {
    private final Provider bootCompleteCacheProvider;
    private final Provider dumpManagerProvider;
    private final Provider heapDumpHelperProvider;
    private final Provider mainHandlerProvider;
    private final Provider notifCollectionProvider;
    private final Provider preHandlerManagerProvider;

    public MemoryMonitor_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.mainHandlerProvider = provider;
        this.bootCompleteCacheProvider = provider2;
        this.heapDumpHelperProvider = provider3;
        this.dumpManagerProvider = provider4;
        this.preHandlerManagerProvider = provider5;
        this.notifCollectionProvider = provider6;
    }

    public static MemoryMonitor_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new MemoryMonitor_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6));
    }

    public static MemoryMonitor newInstance(Handler handler, BootCompleteCache bootCompleteCache, HeapDumpHelper heapDumpHelper, DumpManager dumpManager, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager, CommonNotifCollection commonNotifCollection) {
        return new MemoryMonitor(handler, bootCompleteCache, heapDumpHelper, dumpManager, uncaughtExceptionPreHandlerManager, commonNotifCollection);
    }

    public static MemoryMonitor_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new MemoryMonitor_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    @Override // javax.inject.Provider
    public MemoryMonitor get() {
        return newInstance((Handler) this.mainHandlerProvider.get(), (BootCompleteCache) this.bootCompleteCacheProvider.get(), (HeapDumpHelper) this.heapDumpHelperProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (UncaughtExceptionPreHandlerManager) this.preHandlerManagerProvider.get(), (CommonNotifCollection) this.notifCollectionProvider.get());
    }
}
