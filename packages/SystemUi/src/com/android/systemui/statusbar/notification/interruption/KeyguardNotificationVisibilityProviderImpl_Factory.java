package com.android.systemui.statusbar.notification.interruption;

import android.app.INotificationManager;
import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardNotificationVisibilityProviderImpl_Factory implements Provider {
    public final Provider globalSettingsProvider;
    public final Provider handlerProvider;
    public final Provider highPriorityProvider;
    public final Provider iNotificationManagerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider lockscreenUserManagerProvider;
    public final Provider secureSettingsProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider userTrackerProvider;

    public KeyguardNotificationVisibilityProviderImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.handlerProvider = provider;
        this.keyguardStateControllerProvider = provider2;
        this.lockscreenUserManagerProvider = provider3;
        this.keyguardUpdateMonitorProvider = provider4;
        this.highPriorityProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.userTrackerProvider = provider7;
        this.secureSettingsProvider = provider8;
        this.globalSettingsProvider = provider9;
        this.iNotificationManagerProvider = provider10;
    }

    public static KeyguardNotificationVisibilityProviderImpl newInstance(Handler handler, KeyguardStateController keyguardStateController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, HighPriorityProvider highPriorityProvider, SysuiStatusBarStateController sysuiStatusBarStateController, UserTracker userTracker, SecureSettings secureSettings, GlobalSettings globalSettings, INotificationManager iNotificationManager) {
        return new KeyguardNotificationVisibilityProviderImpl(handler, keyguardStateController, notificationLockscreenUserManager, keyguardUpdateMonitor, highPriorityProvider, sysuiStatusBarStateController, userTracker, secureSettings, globalSettings, iNotificationManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((Handler) this.handlerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (HighPriorityProvider) this.highPriorityProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (INotificationManager) this.iNotificationManagerProvider.get());
    }
}
