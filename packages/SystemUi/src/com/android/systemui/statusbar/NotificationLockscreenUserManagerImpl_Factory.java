package com.android.systemui.statusbar;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Handler;
import android.os.UserManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationLockscreenUserManagerImpl_Factory implements Provider {
    public final Provider broadcastDispatcherProvider;
    public final Provider clickNotifierProvider;
    public final Provider commonNotifCollectionLazyProvider;
    public final Provider contextProvider;
    public final Provider devicePolicyManagerProvider;
    public final Provider deviceProvisionedControllerProvider;
    public final Provider dumpManagerProvider;
    public final Provider featureFlagsProvider;
    public final Provider keyguardManagerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider mPluginAODManagerLazyProvider;
    public final Provider mainHandlerProvider;
    public final Provider overviewProxyServiceLazyProvider;
    public final Provider secureSettingsProvider;
    public final Provider settingsHelperLazyProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider userManagerProvider;
    public final Provider userTrackerProvider;
    public final Provider visibilityProviderLazyProvider;

    public NotificationLockscreenUserManagerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.devicePolicyManagerProvider = provider3;
        this.userManagerProvider = provider4;
        this.userTrackerProvider = provider5;
        this.visibilityProviderLazyProvider = provider6;
        this.commonNotifCollectionLazyProvider = provider7;
        this.clickNotifierProvider = provider8;
        this.overviewProxyServiceLazyProvider = provider9;
        this.keyguardManagerProvider = provider10;
        this.statusBarStateControllerProvider = provider11;
        this.mainHandlerProvider = provider12;
        this.deviceProvisionedControllerProvider = provider13;
        this.keyguardStateControllerProvider = provider14;
        this.secureSettingsProvider = provider15;
        this.dumpManagerProvider = provider16;
        this.lockPatternUtilsProvider = provider17;
        this.featureFlagsProvider = provider18;
        this.settingsHelperLazyProvider = provider19;
        this.mPluginAODManagerLazyProvider = provider20;
    }

    public static NotificationLockscreenUserManagerImpl newInstance(Context context, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, UserManager userManager, UserTracker userTracker, Lazy lazy, Lazy lazy2, NotificationClickNotifier notificationClickNotifier, Lazy lazy3, KeyguardManager keyguardManager, StatusBarStateController statusBarStateController, Handler handler, DeviceProvisionedController deviceProvisionedController, KeyguardStateController keyguardStateController, SecureSettings secureSettings, DumpManager dumpManager, LockPatternUtils lockPatternUtils, FeatureFlags featureFlags, Lazy lazy4) {
        return new NotificationLockscreenUserManagerImpl(context, broadcastDispatcher, devicePolicyManager, userManager, userTracker, lazy, lazy2, notificationClickNotifier, lazy3, keyguardManager, statusBarStateController, handler, deviceProvisionedController, keyguardStateController, secureSettings, dumpManager, lockPatternUtils, featureFlags, lazy4);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        NotificationLockscreenUserManagerImpl newInstance = newInstance((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DevicePolicyManager) this.devicePolicyManagerProvider.get(), (UserManager) this.userManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), DoubleCheck.lazy(this.visibilityProviderLazyProvider), DoubleCheck.lazy(this.commonNotifCollectionLazyProvider), (NotificationClickNotifier) this.clickNotifierProvider.get(), DoubleCheck.lazy(this.overviewProxyServiceLazyProvider), (KeyguardManager) this.keyguardManagerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (Handler) this.mainHandlerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), DoubleCheck.lazy(this.settingsHelperLazyProvider));
        newInstance.mPluginAODManagerLazy = DoubleCheck.lazy(this.mPluginAODManagerLazyProvider);
        return newInstance;
    }
}
