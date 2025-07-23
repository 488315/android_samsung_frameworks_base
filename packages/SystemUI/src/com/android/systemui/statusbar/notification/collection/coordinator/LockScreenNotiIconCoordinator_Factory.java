package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LockScreenNotiIconCoordinator_Factory implements Provider {
    private final Provider ambientStateProvider;
    private final Provider configurationControllerProvider;
    private final Provider groupMembershipManagerProvider;
    private final Provider keyguardStateControllerProvider;
    private final Provider lockscreenNotificationManagerProvider;
    private final Provider lockscreenShadeTransitionControllerProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider pluginLockMediatorProvider;
    private final Provider statusBarStateControllerProvider;
    private final Provider subscreenControllerProvider;

    public LockScreenNotiIconCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.lockscreenNotificationManagerProvider = provider;
        this.keyguardStateControllerProvider = provider2;
        this.lockscreenUserManagerProvider = provider3;
        this.groupMembershipManagerProvider = provider4;
        this.pluginLockMediatorProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.lockscreenShadeTransitionControllerProvider = provider7;
        this.ambientStateProvider = provider8;
        this.configurationControllerProvider = provider9;
        this.subscreenControllerProvider = provider10;
    }

    public static LockScreenNotiIconCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10) {
        return new LockScreenNotiIconCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9), Providers.asDaggerProvider(provider10));
    }

    public static LockScreenNotiIconCoordinator newInstance(LockscreenNotificationManager lockscreenNotificationManager, KeyguardStateController keyguardStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, GroupMembershipManager groupMembershipManager, PluginLockMediator pluginLockMediator, StatusBarStateController statusBarStateController, LockscreenShadeTransitionController lockscreenShadeTransitionController, AmbientState ambientState, ConfigurationController configurationController, SubscreenNotificationController subscreenNotificationController) {
        return new LockScreenNotiIconCoordinator(lockscreenNotificationManager, keyguardStateController, notificationLockscreenUserManager, groupMembershipManager, pluginLockMediator, statusBarStateController, lockscreenShadeTransitionController, ambientState, configurationController, subscreenNotificationController);
    }

    public static LockScreenNotiIconCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        return new LockScreenNotiIconCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    @Override // javax.inject.Provider
    public LockScreenNotiIconCoordinator get() {
        return newInstance((LockscreenNotificationManager) this.lockscreenNotificationManagerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (GroupMembershipManager) this.groupMembershipManagerProvider.get(), (PluginLockMediator) this.pluginLockMediatorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (LockscreenShadeTransitionController) this.lockscreenShadeTransitionControllerProvider.get(), (AmbientState) this.ambientStateProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (SubscreenNotificationController) this.subscreenControllerProvider.get());
    }
}
