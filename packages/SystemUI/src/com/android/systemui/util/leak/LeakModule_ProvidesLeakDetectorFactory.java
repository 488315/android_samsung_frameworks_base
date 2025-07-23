package com.android.systemui.util.leak;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LeakModule_ProvidesLeakDetectorFactory implements Provider {
    private final Provider collectionsProvider;
    private final Provider dumpManagerProvider;
    private final LeakModule module;

    public LeakModule_ProvidesLeakDetectorFactory(LeakModule leakModule, Provider provider, Provider provider2) {
        this.module = leakModule;
        this.dumpManagerProvider = provider;
        this.collectionsProvider = provider2;
    }

    public static LeakModule_ProvidesLeakDetectorFactory create(LeakModule leakModule, javax.inject.Provider provider, javax.inject.Provider provider2) {
        return new LeakModule_ProvidesLeakDetectorFactory(leakModule, Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2));
    }

    public static LeakDetector providesLeakDetector(LeakModule leakModule, DumpManager dumpManager, TrackedCollections trackedCollections) {
        LeakDetector providesLeakDetector = leakModule.providesLeakDetector(dumpManager, trackedCollections);
        providesLeakDetector.getClass();
        return providesLeakDetector;
    }

    public static LeakModule_ProvidesLeakDetectorFactory create(LeakModule leakModule, Provider provider, Provider provider2) {
        return new LeakModule_ProvidesLeakDetectorFactory(leakModule, provider, provider2);
    }

    @Override // javax.inject.Provider
    public LeakDetector get() {
        return providesLeakDetector(this.module, (DumpManager) this.dumpManagerProvider.get(), (TrackedCollections) this.collectionsProvider.get());
    }
}
