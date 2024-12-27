package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.internal.Provider;
import java.util.Optional;

public final class SubscreenNotificationListCoordinator_Factory implements Provider {
    private final javax.inject.Provider bubblesOptionalProvider;
    private final javax.inject.Provider lockscreenUserManagerProvider;
    private final javax.inject.Provider pluginLockMediatorProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;
    private final javax.inject.Provider subscreenControllerProvider;

    public SubscreenNotificationListCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.subscreenControllerProvider = provider;
        this.statusBarStateControllerProvider = provider2;
        this.lockscreenUserManagerProvider = provider3;
        this.pluginLockMediatorProvider = provider4;
        this.bubblesOptionalProvider = provider5;
    }

    public static SubscreenNotificationListCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new SubscreenNotificationListCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static SubscreenNotificationListCoordinator newInstance(SubscreenNotificationController subscreenNotificationController, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, PluginLockMediator pluginLockMediator, Optional<Bubbles> optional) {
        return new SubscreenNotificationListCoordinator(subscreenNotificationController, sysuiStatusBarStateController, notificationLockscreenUserManager, pluginLockMediator, optional);
    }

    @Override // javax.inject.Provider
    public SubscreenNotificationListCoordinator get() {
        return newInstance((SubscreenNotificationController) this.subscreenControllerProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (PluginLockMediator) this.pluginLockMediatorProvider.get(), (Optional) this.bubblesOptionalProvider.get());
    }
}
