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
import com.android.systemui.keyguard.domain.interactor.DozeInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DozeParameters_Factory implements Provider {
    public final Provider alwaysOnDisplayPolicyProvider;
    public final Provider ambientDisplayConfigurationProvider;
    public final Provider batteryControllerProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider dozeInteractorProvider;
    public final Provider dumpManagerProvider;
    public final Provider handlerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider pluginAODManagerLazyProvider;
    public final Provider powerManagerProvider;
    public final Provider resourcesProvider;
    public final Provider screenOffAnimationControllerProvider;
    public final Provider secureSettingsProvider;
    public final Provider selectedUserInteractorProvider;
    public final Provider settingsHelperProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider sysUiUnfoldComponentProvider;
    public final Provider transitionInteractorProvider;
    public final Provider tunerServiceProvider;
    public final Provider unlockedScreenOffAnimationControllerProvider;
    public final Provider userTrackerProvider;

    public DozeParameters_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23) {
        this.selectedUserInteractorProvider = provider;
        this.settingsHelperProvider = provider2;
        this.pluginAODManagerLazyProvider = provider3;
        this.lockPatternUtilsProvider = provider4;
        this.contextProvider = provider5;
        this.handlerProvider = provider6;
        this.resourcesProvider = provider7;
        this.ambientDisplayConfigurationProvider = provider8;
        this.alwaysOnDisplayPolicyProvider = provider9;
        this.powerManagerProvider = provider10;
        this.batteryControllerProvider = provider11;
        this.tunerServiceProvider = provider12;
        this.dumpManagerProvider = provider13;
        this.screenOffAnimationControllerProvider = provider14;
        this.sysUiUnfoldComponentProvider = provider15;
        this.unlockedScreenOffAnimationControllerProvider = provider16;
        this.keyguardUpdateMonitorProvider = provider17;
        this.configurationControllerProvider = provider18;
        this.statusBarStateControllerProvider = provider19;
        this.userTrackerProvider = provider20;
        this.dozeInteractorProvider = provider21;
        this.transitionInteractorProvider = provider22;
        this.secureSettingsProvider = provider23;
    }

    public static DozeParameters newInstance(SelectedUserInteractor selectedUserInteractor, SettingsHelper settingsHelper, Lazy lazy, LockPatternUtils lockPatternUtils, Context context, Handler handler, Resources resources, AmbientDisplayConfiguration ambientDisplayConfiguration, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy, PowerManager powerManager, BatteryController batteryController, TunerService tunerService, DumpManager dumpManager, ScreenOffAnimationController screenOffAnimationController, Optional optional, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, StatusBarStateController statusBarStateController, UserTracker userTracker, DozeInteractor dozeInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, SecureSettings secureSettings) {
        return new DozeParameters(selectedUserInteractor, settingsHelper, lazy, lockPatternUtils, context, handler, resources, ambientDisplayConfiguration, alwaysOnDisplayPolicy, powerManager, batteryController, tunerService, dumpManager, screenOffAnimationController, optional, unlockedScreenOffAnimationController, keyguardUpdateMonitor, configurationController, statusBarStateController, userTracker, dozeInteractor, keyguardTransitionInteractor, secureSettings);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new DozeParameters((SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), DoubleCheck.lazy(this.pluginAODManagerLazyProvider), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (Context) this.contextProvider.get(), (Handler) this.handlerProvider.get(), (Resources) this.resourcesProvider.get(), (AmbientDisplayConfiguration) this.ambientDisplayConfigurationProvider.get(), (AlwaysOnDisplayPolicy) this.alwaysOnDisplayPolicyProvider.get(), (PowerManager) this.powerManagerProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (TunerService) this.tunerServiceProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (ScreenOffAnimationController) this.screenOffAnimationControllerProvider.get(), (Optional) this.sysUiUnfoldComponentProvider.get(), (UnlockedScreenOffAnimationController) this.unlockedScreenOffAnimationControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (DozeInteractor) this.dozeInteractorProvider.get(), (KeyguardTransitionInteractor) this.transitionInteractorProvider.get(), (SecureSettings) this.secureSettingsProvider.get());
    }
}
