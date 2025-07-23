package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.internal.Provider;
import dagger.internal.Providers;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubscreenNotificationListCoordinator_Factory implements Provider {
    private final Provider bubblesOptionalProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider pluginLockMediatorProvider;
    private final Provider statusBarStateControllerProvider;
    private final Provider subscreenControllerProvider;

    public SubscreenNotificationListCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.subscreenControllerProvider = provider;
        this.statusBarStateControllerProvider = provider2;
        this.lockscreenUserManagerProvider = provider3;
        this.pluginLockMediatorProvider = provider4;
        this.bubblesOptionalProvider = provider5;
    }

    public static SubscreenNotificationListCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new SubscreenNotificationListCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static SubscreenNotificationListCoordinator newInstance(SubscreenNotificationController subscreenNotificationController, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, PluginLockMediator pluginLockMediator, Optional<Bubbles> optional) {
        return new SubscreenNotificationListCoordinator(subscreenNotificationController, sysuiStatusBarStateController, notificationLockscreenUserManager, pluginLockMediator, optional);
    }

    public static SubscreenNotificationListCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new SubscreenNotificationListCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public SubscreenNotificationListCoordinator get() {
        return newInstance((SubscreenNotificationController) this.subscreenControllerProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (PluginLockMediator) this.pluginLockMediatorProvider.get(), (Optional) this.bubblesOptionalProvider.get());
    }
}
