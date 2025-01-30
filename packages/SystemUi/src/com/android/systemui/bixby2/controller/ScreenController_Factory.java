package com.android.systemui.bixby2.controller;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DesktopManager;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public static ScreenController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
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
