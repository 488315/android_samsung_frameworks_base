package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Provider;

public final class ViewConfigCoordinator_Factory implements Provider {
    private final javax.inject.Provider colorUpdateLoggerProvider;
    private final javax.inject.Provider mConfigurationControllerProvider;
    private final javax.inject.Provider mGutsManagerProvider;
    private final javax.inject.Provider mKeyguardUpdateMonitorProvider;
    private final javax.inject.Provider mLockscreenUserManagerProvider;

    public ViewConfigCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        this.mConfigurationControllerProvider = provider;
        this.mLockscreenUserManagerProvider = provider2;
        this.mGutsManagerProvider = provider3;
        this.mKeyguardUpdateMonitorProvider = provider4;
        this.colorUpdateLoggerProvider = provider5;
    }

    public static ViewConfigCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new ViewConfigCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ViewConfigCoordinator newInstance(ConfigurationController configurationController, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationGutsManager notificationGutsManager, KeyguardUpdateMonitor keyguardUpdateMonitor, ColorUpdateLogger colorUpdateLogger) {
        return new ViewConfigCoordinator(configurationController, notificationLockscreenUserManager, notificationGutsManager, keyguardUpdateMonitor, colorUpdateLogger);
    }

    @Override // javax.inject.Provider
    public ViewConfigCoordinator get() {
        return newInstance((ConfigurationController) this.mConfigurationControllerProvider.get(), (NotificationLockscreenUserManager) this.mLockscreenUserManagerProvider.get(), (NotificationGutsManager) this.mGutsManagerProvider.get(), (KeyguardUpdateMonitor) this.mKeyguardUpdateMonitorProvider.get(), (ColorUpdateLogger) this.colorUpdateLoggerProvider.get());
    }
}
