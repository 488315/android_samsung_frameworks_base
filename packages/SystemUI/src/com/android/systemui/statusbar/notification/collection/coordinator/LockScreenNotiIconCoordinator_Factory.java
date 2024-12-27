package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.LockscreenNotificationManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockScreenNotiIconCoordinator_Factory implements Provider {
    private final javax.inject.Provider ambientStateProvider;
    private final javax.inject.Provider configurationControllerProvider;
    private final javax.inject.Provider groupMembershipManagerProvider;
    private final javax.inject.Provider keyguardStateControllerProvider;
    private final javax.inject.Provider lockscreenNotificationManagerProvider;
    private final javax.inject.Provider lockscreenShadeTransitionControllerProvider;
    private final javax.inject.Provider lockscreenUserManagerProvider;
    private final javax.inject.Provider pluginLockMediatorProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;

    public LockScreenNotiIconCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9) {
        this.lockscreenNotificationManagerProvider = provider;
        this.keyguardStateControllerProvider = provider2;
        this.lockscreenUserManagerProvider = provider3;
        this.groupMembershipManagerProvider = provider4;
        this.pluginLockMediatorProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.lockscreenShadeTransitionControllerProvider = provider7;
        this.ambientStateProvider = provider8;
        this.configurationControllerProvider = provider9;
    }

    public static LockScreenNotiIconCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9) {
        return new LockScreenNotiIconCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static LockScreenNotiIconCoordinator newInstance(LockscreenNotificationManager lockscreenNotificationManager, KeyguardStateController keyguardStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, GroupMembershipManager groupMembershipManager, PluginLockMediator pluginLockMediator, StatusBarStateController statusBarStateController, LockscreenShadeTransitionController lockscreenShadeTransitionController, AmbientState ambientState, ConfigurationController configurationController) {
        return new LockScreenNotiIconCoordinator(lockscreenNotificationManager, keyguardStateController, notificationLockscreenUserManager, groupMembershipManager, pluginLockMediator, statusBarStateController, lockscreenShadeTransitionController, ambientState, configurationController);
    }

    @Override // javax.inject.Provider
    public LockScreenNotiIconCoordinator get() {
        return newInstance((LockscreenNotificationManager) this.lockscreenNotificationManagerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (GroupMembershipManager) this.groupMembershipManagerProvider.get(), (PluginLockMediator) this.pluginLockMediatorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (LockscreenShadeTransitionController) this.lockscreenShadeTransitionControllerProvider.get(), (AmbientState) this.ambientStateProvider.get(), (ConfigurationController) this.configurationControllerProvider.get());
    }
}
