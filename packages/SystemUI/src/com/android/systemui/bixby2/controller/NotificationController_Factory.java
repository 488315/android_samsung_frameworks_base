package com.android.systemui.bixby2.controller;

import android.app.KeyguardManager;
import android.content.Context;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.util.DesktopManager;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes.dex */
public final class NotificationController_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider desktopManagerProvider;
    private final Provider displayLifecycleProvider;
    private final Provider keyguardManagerProvider;
    private final Provider notifCollectionProvider;
    private final Provider notifPipelineProvider;

    public NotificationController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.notifPipelineProvider = provider2;
        this.notifCollectionProvider = provider3;
        this.desktopManagerProvider = provider4;
        this.displayLifecycleProvider = provider5;
        this.keyguardManagerProvider = provider6;
    }

    public static NotificationController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new NotificationController_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6));
    }

    public static NotificationController newInstance(Context context, NotifPipeline notifPipeline, NotifCollection notifCollection, DesktopManager desktopManager, DisplayLifecycle displayLifecycle, KeyguardManager keyguardManager) {
        return new NotificationController(context, notifPipeline, notifCollection, desktopManager, displayLifecycle, keyguardManager);
    }

    public static NotificationController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new NotificationController_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    @Override // javax.inject.Provider
    public NotificationController get() {
        return newInstance((Context) this.contextProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (DesktopManager) this.desktopManagerProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (KeyguardManager) this.keyguardManagerProvider.get());
    }
}
