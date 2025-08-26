package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class DebugModeCoordinator_Factory implements Provider {
    private final Provider debugModeFilterProvider;

    public DebugModeCoordinator_Factory(Provider provider) {
        this.debugModeFilterProvider = provider;
    }

    public static DebugModeCoordinator_Factory create(javax.inject.Provider provider) {
        return new DebugModeCoordinator_Factory(Providers.asDaggerProvider(provider));
    }

    public static DebugModeCoordinator newInstance(DebugModeFilterProvider debugModeFilterProvider) {
        return new DebugModeCoordinator(debugModeFilterProvider);
    }

    public static DebugModeCoordinator_Factory create(Provider provider) {
        return new DebugModeCoordinator_Factory(provider);
    }

    @Override // javax.inject.Provider
    public DebugModeCoordinator get() {
        return newInstance((DebugModeFilterProvider) this.debugModeFilterProvider.get());
    }
}
