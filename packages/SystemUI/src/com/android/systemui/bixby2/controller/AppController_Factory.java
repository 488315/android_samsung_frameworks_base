package com.android.systemui.bixby2.controller;

import android.content.Context;
import com.android.systemui.bixby2.util.ActivityLauncher;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.DesktopManager;
import dagger.internal.Provider;

public final class AppController_Factory implements Provider {
    private final javax.inject.Provider activityLauncherProvider;
    private final javax.inject.Provider broadcastDispatcherProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider desktopManagerProvider;
    private final javax.inject.Provider displayLifecycleProvider;

    public AppController_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.contextProvider = provider;
        this.activityLauncherProvider = provider2;
        this.displayLifecycleProvider = provider3;
        this.desktopManagerProvider = provider4;
        this.broadcastDispatcherProvider = provider5;
    }

    public static AppController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new AppController_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static AppController newInstance(Context context, ActivityLauncher activityLauncher, DisplayLifecycle displayLifecycle, DesktopManager desktopManager, BroadcastDispatcher broadcastDispatcher) {
        return new AppController(context, activityLauncher, displayLifecycle, desktopManager, broadcastDispatcher);
    }

    @Override // javax.inject.Provider
    public AppController get() {
        return newInstance((Context) this.contextProvider.get(), (ActivityLauncher) this.activityLauncherProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (DesktopManager) this.desktopManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}
