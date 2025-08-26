package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.policy.AppLockNotificationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class SensitiveContentCoordinatorImpl_Factory implements Provider {
    private final Provider appLockNotificationControllerProvider;
    private final Provider deviceEntryInteractorProvider;
    private final Provider dynamicPrivacyControllerProvider;
    private final Provider keyguardStateControllerProvider;
    private final Provider keyguardUpdateMonitorProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider sceneInteractorProvider;
    private final Provider scopeProvider;
    private final Provider selectedUserInteractorProvider;
    private final Provider sensitiveNotificationProtectionControllerProvider;
    private final Provider settingsHelperProvider;
    private final Provider statusBarStateControllerProvider;

    public SensitiveContentCoordinatorImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.dynamicPrivacyControllerProvider = provider;
        this.lockscreenUserManagerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.statusBarStateControllerProvider = provider4;
        this.keyguardStateControllerProvider = provider5;
        this.selectedUserInteractorProvider = provider6;
        this.sensitiveNotificationProtectionControllerProvider = provider7;
        this.deviceEntryInteractorProvider = provider8;
        this.sceneInteractorProvider = provider9;
        this.scopeProvider = provider10;
        this.settingsHelperProvider = provider11;
        this.appLockNotificationControllerProvider = provider12;
    }

    public static SensitiveContentCoordinatorImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12) {
        return new SensitiveContentCoordinatorImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9), Providers.asDaggerProvider(provider10), Providers.asDaggerProvider(provider11), Providers.asDaggerProvider(provider12));
    }

    public static SensitiveContentCoordinatorImpl newInstance(DynamicPrivacyController dynamicPrivacyController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, SelectedUserInteractor selectedUserInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, DeviceEntryInteractor deviceEntryInteractor, SceneInteractor sceneInteractor, CoroutineScope coroutineScope, SettingsHelper settingsHelper, AppLockNotificationController appLockNotificationController) {
        return new SensitiveContentCoordinatorImpl(dynamicPrivacyController, notificationLockscreenUserManager, keyguardUpdateMonitor, statusBarStateController, keyguardStateController, selectedUserInteractor, sensitiveNotificationProtectionController, deviceEntryInteractor, sceneInteractor, coroutineScope, settingsHelper, appLockNotificationController);
    }

    public static SensitiveContentCoordinatorImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        return new SensitiveContentCoordinatorImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    @Override // javax.inject.Provider
    public SensitiveContentCoordinatorImpl get() {
        return newInstance((DynamicPrivacyController) this.dynamicPrivacyControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (SensitiveNotificationProtectionController) this.sensitiveNotificationProtectionControllerProvider.get(), (DeviceEntryInteractor) this.deviceEntryInteractorProvider.get(), (SceneInteractor) this.sceneInteractorProvider.get(), (CoroutineScope) this.scopeProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (AppLockNotificationController) this.appLockNotificationControllerProvider.get());
    }
}
