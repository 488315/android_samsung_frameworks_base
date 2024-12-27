package com.android.systemui.bixby2.controller;

import android.app.KeyguardManager;
import android.content.Context;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.util.DesktopManager;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class NotificationController_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider desktopManagerProvider;
    private final javax.inject.Provider displayLifecycleProvider;
    private final javax.inject.Provider keyguardManagerProvider;
    private final javax.inject.Provider notifCollectionProvider;
    private final javax.inject.Provider notifPipelineProvider;

    public NotificationController_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        this.contextProvider = provider;
        this.notifPipelineProvider = provider2;
        this.notifCollectionProvider = provider3;
        this.desktopManagerProvider = provider4;
        this.displayLifecycleProvider = provider5;
        this.keyguardManagerProvider = provider6;
    }

    public static NotificationController_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new NotificationController_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static NotificationController newInstance(Context context, NotifPipeline notifPipeline, NotifCollection notifCollection, DesktopManager desktopManager, DisplayLifecycle displayLifecycle, KeyguardManager keyguardManager) {
        return new NotificationController(context, notifPipeline, notifCollection, desktopManager, displayLifecycle, keyguardManager);
    }

    @Override // javax.inject.Provider
    public NotificationController get() {
        return newInstance((Context) this.contextProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (DesktopManager) this.desktopManagerProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (KeyguardManager) this.keyguardManagerProvider.get());
    }
}
