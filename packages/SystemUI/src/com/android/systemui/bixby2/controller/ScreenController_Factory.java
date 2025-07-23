package com.android.systemui.bixby2.controller;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DesktopManager;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScreenController_Factory implements Provider {
    private final Provider broadcastDispatcherProvider;
    private final Provider desktopManagerProvider;
    private final Provider displayLifecycleProvider;
    private final Provider secBrightnessMirrorControllerProvider;

    public ScreenController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.secBrightnessMirrorControllerProvider = provider;
        this.desktopManagerProvider = provider2;
        this.displayLifecycleProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
    }

    public static ScreenController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4) {
        return new ScreenController_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4));
    }

    public static ScreenController newInstance(Lazy lazy, DesktopManager desktopManager, DisplayLifecycle displayLifecycle, BroadcastDispatcher broadcastDispatcher) {
        return new ScreenController(lazy, desktopManager, displayLifecycle, broadcastDispatcher);
    }

    public static ScreenController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new ScreenController_Factory(provider, provider2, provider3, provider4);
    }

    @Override // javax.inject.Provider
    public ScreenController get() {
        return newInstance(DoubleCheck.lazy(this.secBrightnessMirrorControllerProvider), (DesktopManager) this.desktopManagerProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
