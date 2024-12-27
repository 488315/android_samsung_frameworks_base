package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import dagger.internal.Provider;

public final class DebugModeCoordinator_Factory implements Provider {
    private final javax.inject.Provider debugModeFilterProvider;

    public DebugModeCoordinator_Factory(javax.inject.Provider provider) {
        this.debugModeFilterProvider = provider;
    }

    public static DebugModeCoordinator_Factory create(javax.inject.Provider provider) {
        return new DebugModeCoordinator_Factory(provider);
    }

    public static DebugModeCoordinator newInstance(DebugModeFilterProvider debugModeFilterProvider) {
        return new DebugModeCoordinator(debugModeFilterProvider);
    }

    @Override // javax.inject.Provider
    public DebugModeCoordinator get() {
        return newInstance((DebugModeFilterProvider) this.debugModeFilterProvider.get());
    }
}
