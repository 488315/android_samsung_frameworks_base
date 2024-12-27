package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.policy.AppLockNotificationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SensitiveContentCoordinatorImpl_Factory implements Provider {
    private final javax.inject.Provider appLockNotificationControllerProvider;
    private final javax.inject.Provider dynamicPrivacyControllerProvider;
    private final javax.inject.Provider keyguardStateControllerProvider;
    private final javax.inject.Provider keyguardUpdateMonitorProvider;
    private final javax.inject.Provider lockscreenUserManagerProvider;
    private final javax.inject.Provider selectedUserInteractorProvider;
    private final javax.inject.Provider sensitiveNotificationProtectionControllerProvider;
    private final javax.inject.Provider settingsHelperProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;

    public SensitiveContentCoordinatorImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9) {
        this.dynamicPrivacyControllerProvider = provider;
        this.lockscreenUserManagerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.statusBarStateControllerProvider = provider4;
        this.keyguardStateControllerProvider = provider5;
        this.selectedUserInteractorProvider = provider6;
        this.sensitiveNotificationProtectionControllerProvider = provider7;
        this.settingsHelperProvider = provider8;
        this.appLockNotificationControllerProvider = provider9;
    }

    public static SensitiveContentCoordinatorImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9) {
        return new SensitiveContentCoordinatorImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static SensitiveContentCoordinatorImpl newInstance(DynamicPrivacyController dynamicPrivacyController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, SelectedUserInteractor selectedUserInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, SettingsHelper settingsHelper, AppLockNotificationController appLockNotificationController) {
        return new SensitiveContentCoordinatorImpl(dynamicPrivacyController, notificationLockscreenUserManager, keyguardUpdateMonitor, statusBarStateController, keyguardStateController, selectedUserInteractor, sensitiveNotificationProtectionController, settingsHelper, appLockNotificationController);
    }

    @Override // javax.inject.Provider
    public SensitiveContentCoordinatorImpl get() {
        return newInstance((DynamicPrivacyController) this.dynamicPrivacyControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (SensitiveNotificationProtectionController) this.sensitiveNotificationProtectionControllerProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (AppLockNotificationController) this.appLockNotificationControllerProvider.get());
    }
}
