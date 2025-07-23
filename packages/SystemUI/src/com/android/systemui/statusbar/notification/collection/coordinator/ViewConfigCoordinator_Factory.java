package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ViewConfigCoordinator_Factory implements Provider {
    private final Provider colorUpdateLoggerProvider;
    private final Provider mConfigurationControllerProvider;
    private final Provider mGutsManagerProvider;
    private final Provider mKeyguardUpdateMonitorProvider;
    private final Provider mLockscreenUserManagerProvider;

    public ViewConfigCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.mConfigurationControllerProvider = provider;
        this.mLockscreenUserManagerProvider = provider2;
        this.mGutsManagerProvider = provider3;
        this.mKeyguardUpdateMonitorProvider = provider4;
        this.colorUpdateLoggerProvider = provider5;
    }

    public static ViewConfigCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5) {
        return new ViewConfigCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5));
    }

    public static ViewConfigCoordinator newInstance(ConfigurationController configurationController, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationGutsManager notificationGutsManager, KeyguardUpdateMonitor keyguardUpdateMonitor, ColorUpdateLogger colorUpdateLogger) {
        return new ViewConfigCoordinator(configurationController, notificationLockscreenUserManager, notificationGutsManager, keyguardUpdateMonitor, colorUpdateLogger);
    }

    public static ViewConfigCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new ViewConfigCoordinator_Factory(provider, provider2, provider3, provider4, provider5);
    }

    @Override // javax.inject.Provider
    public ViewConfigCoordinator get() {
        return newInstance((ConfigurationController) this.mConfigurationControllerProvider.get(), (NotificationLockscreenUserManager) this.mLockscreenUserManagerProvider.get(), (NotificationGutsManager) this.mGutsManagerProvider.get(), (KeyguardUpdateMonitor) this.mKeyguardUpdateMonitorProvider.get(), (ColorUpdateLogger) this.colorUpdateLoggerProvider.get());
    }
}
