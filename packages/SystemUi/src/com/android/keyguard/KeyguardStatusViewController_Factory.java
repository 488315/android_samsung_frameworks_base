package com.android.keyguard;

import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.phone.DcmMascotViewContainer;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardStatusViewController_Factory implements Provider {
    public final Provider configurationControllerProvider;
    public final Provider dozeParametersProvider;
    public final Provider featureFlagsProvider;
    public final Provider interactionJankMonitorProvider;
    public final Provider keyguardClockSwitchControllerProvider;
    public final Provider keyguardSliceViewControllerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardStatusViewProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider loggerProvider;
    public final Provider mPluginAODManagerLazyProvider;
    public final Provider mascotViewContainerProvider;
    public final Provider screenOffAnimationControllerProvider;

    public KeyguardStatusViewController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        this.mascotViewContainerProvider = provider;
        this.keyguardStatusViewProvider = provider2;
        this.keyguardSliceViewControllerProvider = provider3;
        this.keyguardClockSwitchControllerProvider = provider4;
        this.keyguardStateControllerProvider = provider5;
        this.keyguardUpdateMonitorProvider = provider6;
        this.configurationControllerProvider = provider7;
        this.dozeParametersProvider = provider8;
        this.screenOffAnimationControllerProvider = provider9;
        this.loggerProvider = provider10;
        this.featureFlagsProvider = provider11;
        this.interactionJankMonitorProvider = provider12;
        this.mPluginAODManagerLazyProvider = provider13;
    }

    public static KeyguardStatusViewController newInstance(DcmMascotViewContainer dcmMascotViewContainer, KeyguardStatusView keyguardStatusView, KeyguardSliceViewController keyguardSliceViewController, KeyguardClockSwitchController keyguardClockSwitchController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, DozeParameters dozeParameters, ScreenOffAnimationController screenOffAnimationController, KeyguardLogger keyguardLogger, FeatureFlags featureFlags, InteractionJankMonitor interactionJankMonitor) {
        return new KeyguardStatusViewController(dcmMascotViewContainer, keyguardStatusView, keyguardSliceViewController, keyguardClockSwitchController, keyguardStateController, keyguardUpdateMonitor, configurationController, dozeParameters, screenOffAnimationController, keyguardLogger, featureFlags, interactionJankMonitor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        KeyguardStatusViewController newInstance = newInstance((DcmMascotViewContainer) this.mascotViewContainerProvider.get(), (KeyguardStatusView) this.keyguardStatusViewProvider.get(), (KeyguardSliceViewController) this.keyguardSliceViewControllerProvider.get(), (KeyguardClockSwitchController) this.keyguardClockSwitchControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (ScreenOffAnimationController) this.screenOffAnimationControllerProvider.get(), (KeyguardLogger) this.loggerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get());
        newInstance.mPluginAODManagerLazy = DoubleCheck.lazy(this.mPluginAODManagerLazyProvider);
        return newInstance;
    }
}
