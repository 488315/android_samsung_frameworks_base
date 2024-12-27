package com.android.systemui.bixby2.controller;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DesktopManager;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

public final class ScreenController_Factory implements Provider {
    private final javax.inject.Provider broadcastDispatcherProvider;
    private final javax.inject.Provider desktopManagerProvider;
    private final javax.inject.Provider displayLifecycleProvider;
    private final javax.inject.Provider secBrightnessMirrorControllerProvider;

    public ScreenController_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        this.secBrightnessMirrorControllerProvider = provider;
        this.desktopManagerProvider = provider2;
        this.displayLifecycleProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
    }

    public static ScreenController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new ScreenController_Factory(provider, provider2, provider3, provider4);
    }

    public static ScreenController newInstance(Lazy lazy, DesktopManager desktopManager, DisplayLifecycle displayLifecycle, BroadcastDispatcher broadcastDispatcher) {
        return new ScreenController(lazy, desktopManager, displayLifecycle, broadcastDispatcher);
    }

    @Override // javax.inject.Provider
    public ScreenController get() {
        return newInstance(DoubleCheck.lazy(this.secBrightnessMirrorControllerProvider), (DesktopManager) this.desktopManagerProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
