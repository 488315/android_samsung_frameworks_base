package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
