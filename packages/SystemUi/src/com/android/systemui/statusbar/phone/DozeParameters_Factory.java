package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.doze.AlwaysOnDisplayPolicy;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DozeParameters_Factory implements Provider {
    public final Provider alwaysOnDisplayPolicyProvider;
    public final Provider ambientDisplayConfigurationProvider;
    public final Provider batteryControllerProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider dumpManagerProvider;
    public final Provider handlerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider pluginAODManagerLazyProvider;
    public final Provider powerManagerProvider;
    public final Provider resourcesProvider;
    public final Provider screenOffAnimationControllerProvider;
    public final Provider settingsHelperProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider sysUiUnfoldComponentProvider;
    public final Provider tunerServiceProvider;
    public final Provider unlockedScreenOffAnimationControllerProvider;
    public final Provider userTrackerProvider;

    public DozeParameters_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19) {
        this.settingsHelperProvider = provider;
        this.pluginAODManagerLazyProvider = provider2;
        this.lockPatternUtilsProvider = provider3;
        this.contextProvider = provider4;
        this.handlerProvider = provider5;
        this.resourcesProvider = provider6;
        this.ambientDisplayConfigurationProvider = provider7;
        this.alwaysOnDisplayPolicyProvider = provider8;
        this.powerManagerProvider = provider9;
        this.batteryControllerProvider = provider10;
        this.tunerServiceProvider = provider11;
        this.dumpManagerProvider = provider12;
        this.screenOffAnimationControllerProvider = provider13;
        this.sysUiUnfoldComponentProvider = provider14;
        this.unlockedScreenOffAnimationControllerProvider = provider15;
        this.keyguardUpdateMonitorProvider = provider16;
        this.configurationControllerProvider = provider17;
        this.statusBarStateControllerProvider = provider18;
        this.userTrackerProvider = provider19;
    }

    public static DozeParameters newInstance(SettingsHelper settingsHelper, Lazy lazy, LockPatternUtils lockPatternUtils, Context context, Handler handler, Resources resources, AmbientDisplayConfiguration ambientDisplayConfiguration, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy, PowerManager powerManager, BatteryController batteryController, TunerService tunerService, DumpManager dumpManager, ScreenOffAnimationController screenOffAnimationController, Optional optional, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, StatusBarStateController statusBarStateController, UserTracker userTracker) {
        return new DozeParameters(settingsHelper, lazy, lockPatternUtils, context, handler, resources, ambientDisplayConfiguration, alwaysOnDisplayPolicy, powerManager, batteryController, tunerService, dumpManager, screenOffAnimationController, optional, unlockedScreenOffAnimationController, keyguardUpdateMonitor, configurationController, statusBarStateController, userTracker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((SettingsHelper) this.settingsHelperProvider.get(), DoubleCheck.lazy(this.pluginAODManagerLazyProvider), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (Context) this.contextProvider.get(), (Handler) this.handlerProvider.get(), (Resources) this.resourcesProvider.get(), (AmbientDisplayConfiguration) this.ambientDisplayConfigurationProvider.get(), (AlwaysOnDisplayPolicy) this.alwaysOnDisplayPolicyProvider.get(), (PowerManager) this.powerManagerProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (TunerService) this.tunerServiceProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (ScreenOffAnimationController) this.screenOffAnimationControllerProvider.get(), (Optional) this.sysUiUnfoldComponentProvider.get(), (UnlockedScreenOffAnimationController) this.unlockedScreenOffAnimationControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
