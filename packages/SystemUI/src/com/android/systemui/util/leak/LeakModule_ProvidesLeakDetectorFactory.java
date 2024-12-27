package com.android.systemui.util.leak;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

public final class LeakModule_ProvidesLeakDetectorFactory implements Provider {
    private final javax.inject.Provider collectionsProvider;
    private final javax.inject.Provider dumpManagerProvider;
    private final LeakModule module;

    public LeakModule_ProvidesLeakDetectorFactory(LeakModule leakModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        this.module = leakModule;
        this.dumpManagerProvider = provider;
        this.collectionsProvider = provider2;
    }

    public static LeakModule_ProvidesLeakDetectorFactory create(LeakModule leakModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new LeakModule_ProvidesLeakDetectorFactory(leakModule, provider, provider2);
    }

    public static LeakDetector providesLeakDetector(LeakModule leakModule, DumpManager dumpManager, TrackedCollections trackedCollections) {
        LeakDetector providesLeakDetector = leakModule.providesLeakDetector(dumpManager, trackedCollections);
        Preconditions.checkNotNullFromProvides(providesLeakDetector);
        return providesLeakDetector;
    }

    @Override // javax.inject.Provider
    public LeakDetector get() {
        return providesLeakDetector(this.module, (DumpManager) this.dumpManagerProvider.get(), (TrackedCollections) this.collectionsProvider.get());
    }
}
