package com.android.systemui.bixby2.controller;

import android.content.Context;
import com.android.systemui.bixby2.util.ActivityLauncher;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DesktopManager;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AppController_Factory implements Provider {
    private final Provider activityLauncherProvider;
    private final Provider broadcastDispatcherProvider;
    private final Provider contextProvider;
    private final Provider desktopManagerProvider;
    private final Provider displayLifecycleProvider;

    public AppController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.contextProvider = provider;
        this.activityLauncherProvider = provider2;
        this.displayLifecycleProvider = provider3;
        this.desktopManagerProvider = provider4;
        this.broadcastDispatcherProvider = provider5;
    }

    public static AppController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new AppController_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static AppController newInstance(Context context, ActivityLauncher activityLauncher, DisplayLifecycle displayLifecycle, DesktopManager desktopManager, BroadcastDispatcher broadcastDispatcher) {
        return new AppController(context, activityLauncher, displayLifecycle, desktopManager, broadcastDispatcher);
    }

    public static AppController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new AppController_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public AppController get() {
        return newInstance((Context) this.contextProvider.get(), (ActivityLauncher) this.activityLauncherProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (DesktopManager) this.desktopManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
