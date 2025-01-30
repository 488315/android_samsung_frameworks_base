package com.android.keyguard;

import android.app.AlarmManager;
import android.app.admin.DevicePolicyManager;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.DualDarInnerLockScreenController;
import com.android.keyguard.KeyguardArrowViewController;
import com.android.keyguard.KeyguardPluginControllerImpl;
import com.android.keyguard.biometrics.KeyguardBiometricViewController;
import com.android.keyguard.punchhole.KeyguardPunchHoleVIViewController;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.domain.interactor.KeyguardFaceAuthInteractor;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecSecurityContainerController_Factory implements Provider {
    public final Provider adminSecondaryLockScreenControllerFactoryProvider;
    public final Provider alarmManagerProvider;
    public final Provider audioManagerProvider;
    public final Provider configurationControllerProvider;
    public final Provider devicePolicyManagerProvider;
    public final Provider deviceProvisionedControllerProvider;
    public final Provider dualDarInnerLockScreenControllerFactoryProvider;
    public final Provider falsingA11yDelegateProvider;
    public final Provider falsingCollectorProvider;
    public final Provider falsingManagerProvider;
    public final Provider featureFlagsProvider;
    public final Provider globalSettingsProvider;
    public final Provider inputMethodManagerProvider;
    public final Provider keyguardArrowViewControllerProvider;
    public final Provider keyguardBiometricViewControllerProvider;
    public final Provider keyguardCarrierTextViewControllerProvider;
    public final Provider keyguardFaceAuthInteractorProvider;
    public final Provider keyguardPluginControllerFactoryProvider;
    public final Provider keyguardPunchHoleVIViewControllerProvider;
    public final Provider keyguardSecurityModelProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider metricsLoggerProvider;
    public final Provider rotationWatcherProvider;
    public final Provider securityViewFlipperControllerProvider;
    public final Provider sessionTrackerProvider;
    public final Provider settingsHelperProvider;
    public final Provider sideFpsControllerProvider;
    public final Provider telephonyManagerProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider userSwitcherControllerProvider;
    public final Provider viewMediatorCallbackProvider;
    public final Provider viewProvider;

    public KeyguardSecSecurityContainerController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34) {
        this.viewProvider = provider;
        this.adminSecondaryLockScreenControllerFactoryProvider = provider2;
        this.lockPatternUtilsProvider = provider3;
        this.keyguardUpdateMonitorProvider = provider4;
        this.keyguardSecurityModelProvider = provider5;
        this.metricsLoggerProvider = provider6;
        this.uiEventLoggerProvider = provider7;
        this.keyguardStateControllerProvider = provider8;
        this.securityViewFlipperControllerProvider = provider9;
        this.configurationControllerProvider = provider10;
        this.falsingCollectorProvider = provider11;
        this.falsingManagerProvider = provider12;
        this.userSwitcherControllerProvider = provider13;
        this.deviceProvisionedControllerProvider = provider14;
        this.featureFlagsProvider = provider15;
        this.globalSettingsProvider = provider16;
        this.sessionTrackerProvider = provider17;
        this.sideFpsControllerProvider = provider18;
        this.falsingA11yDelegateProvider = provider19;
        this.telephonyManagerProvider = provider20;
        this.viewMediatorCallbackProvider = provider21;
        this.audioManagerProvider = provider22;
        this.keyguardFaceAuthInteractorProvider = provider23;
        this.devicePolicyManagerProvider = provider24;
        this.inputMethodManagerProvider = provider25;
        this.alarmManagerProvider = provider26;
        this.rotationWatcherProvider = provider27;
        this.settingsHelperProvider = provider28;
        this.keyguardCarrierTextViewControllerProvider = provider29;
        this.keyguardPunchHoleVIViewControllerProvider = provider30;
        this.keyguardArrowViewControllerProvider = provider31;
        this.keyguardBiometricViewControllerProvider = provider32;
        this.keyguardPluginControllerFactoryProvider = provider33;
        this.dualDarInnerLockScreenControllerFactoryProvider = provider34;
    }

    public static KeyguardSecSecurityContainerController newInstance(KeyguardSecSecurityContainer keyguardSecSecurityContainer, AdminSecondaryLockScreenController.Factory factory, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, DeviceProvisionedController deviceProvisionedController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker, Optional optional, FalsingA11yDelegate falsingA11yDelegate, TelephonyManager telephonyManager, ViewMediatorCallback viewMediatorCallback, AudioManager audioManager, KeyguardFaceAuthInteractor keyguardFaceAuthInteractor, DevicePolicyManager devicePolicyManager, InputMethodManager inputMethodManager, AlarmManager alarmManager, SecRotationWatcher secRotationWatcher, SettingsHelper settingsHelper, KeyguardCarrierTextViewController keyguardCarrierTextViewController, KeyguardPunchHoleVIViewController keyguardPunchHoleVIViewController, Object obj, KeyguardBiometricViewController keyguardBiometricViewController, KeyguardPluginControllerImpl.Factory factory2, DualDarInnerLockScreenController.Factory factory3) {
        return new KeyguardSecSecurityContainerController(keyguardSecSecurityContainer, factory, lockPatternUtils, keyguardUpdateMonitor, keyguardSecurityModel, metricsLogger, uiEventLogger, keyguardStateController, keyguardSecurityViewFlipperController, configurationController, falsingCollector, falsingManager, userSwitcherController, deviceProvisionedController, featureFlags, globalSettings, sessionTracker, optional, falsingA11yDelegate, telephonyManager, viewMediatorCallback, audioManager, keyguardFaceAuthInteractor, devicePolicyManager, inputMethodManager, alarmManager, secRotationWatcher, settingsHelper, keyguardCarrierTextViewController, keyguardPunchHoleVIViewController, (KeyguardArrowViewController.Factory) obj, keyguardBiometricViewController, factory2, factory3);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((KeyguardSecSecurityContainer) this.viewProvider.get(), (AdminSecondaryLockScreenController.Factory) this.adminSecondaryLockScreenControllerFactoryProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (KeyguardSecurityModel) this.keyguardSecurityModelProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (KeyguardSecurityViewFlipperController) this.securityViewFlipperControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (FalsingCollector) this.falsingCollectorProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (UserSwitcherController) this.userSwitcherControllerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (SessionTracker) this.sessionTrackerProvider.get(), (Optional) this.sideFpsControllerProvider.get(), (FalsingA11yDelegate) this.falsingA11yDelegateProvider.get(), (TelephonyManager) this.telephonyManagerProvider.get(), (ViewMediatorCallback) this.viewMediatorCallbackProvider.get(), (AudioManager) this.audioManagerProvider.get(), (KeyguardFaceAuthInteractor) this.keyguardFaceAuthInteractorProvider.get(), (DevicePolicyManager) this.devicePolicyManagerProvider.get(), (InputMethodManager) this.inputMethodManagerProvider.get(), (AlarmManager) this.alarmManagerProvider.get(), (SecRotationWatcher) this.rotationWatcherProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (KeyguardCarrierTextViewController) this.keyguardCarrierTextViewControllerProvider.get(), (KeyguardPunchHoleVIViewController) this.keyguardPunchHoleVIViewControllerProvider.get(), this.keyguardArrowViewControllerProvider.get(), (KeyguardBiometricViewController) this.keyguardBiometricViewControllerProvider.get(), (KeyguardPluginControllerImpl.Factory) this.keyguardPluginControllerFactoryProvider.get(), (DualDarInnerLockScreenController.Factory) this.dualDarInnerLockScreenControllerFactoryProvider.get());
    }
}
