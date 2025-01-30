package com.android.keyguard;

import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.animator.KeyguardTouchSwipeDetector;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.vibrate.VibrationUtil;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardInputViewController_Factory_Factory implements Provider {
    public final Provider accessibilityManagerProvider;
    public final Provider configurationControllerProvider;
    public final Provider devicePostureControllerProvider;
    public final Provider emergencyButtonControllerFactoryProvider;
    public final Provider falsingCollectorProvider;
    public final Provider featureFlagsProvider;
    public final Provider inputMethodManagerProvider;
    public final Provider keyguardTouchSwipeDetectorProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider keyguardViewControllerProvider;
    public final Provider latencyTrackerProvider;
    public final Provider liftToActivateListenerProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider mainExecutorProvider;
    public final Provider messageAreaControllerFactoryProvider;
    public final Provider networkControllerProvider;
    public final Provider resourcesProvider;
    public final Provider rotationWatcherProvider;
    public final Provider screenLifecycleProvider;
    public final Provider telephonyManagerProvider;
    public final Provider vibrationUtilProvider;
    public final Provider wifiManagerProvider;

    public KeyguardInputViewController_Factory_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22) {
        this.keyguardUpdateMonitorProvider = provider;
        this.rotationWatcherProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.vibrationUtilProvider = provider4;
        this.accessibilityManagerProvider = provider5;
        this.keyguardTouchSwipeDetectorProvider = provider6;
        this.wifiManagerProvider = provider7;
        this.networkControllerProvider = provider8;
        this.screenLifecycleProvider = provider9;
        this.lockPatternUtilsProvider = provider10;
        this.latencyTrackerProvider = provider11;
        this.messageAreaControllerFactoryProvider = provider12;
        this.inputMethodManagerProvider = provider13;
        this.mainExecutorProvider = provider14;
        this.resourcesProvider = provider15;
        this.liftToActivateListenerProvider = provider16;
        this.telephonyManagerProvider = provider17;
        this.falsingCollectorProvider = provider18;
        this.emergencyButtonControllerFactoryProvider = provider19;
        this.devicePostureControllerProvider = provider20;
        this.keyguardViewControllerProvider = provider21;
        this.featureFlagsProvider = provider22;
    }

    public static KeyguardInputViewController.Factory newInstance(KeyguardUpdateMonitor keyguardUpdateMonitor, SecRotationWatcher secRotationWatcher, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardTouchSwipeDetector keyguardTouchSwipeDetector, WifiManager wifiManager, NetworkController networkController, ScreenLifecycle screenLifecycle, LockPatternUtils lockPatternUtils, LatencyTracker latencyTracker, KeyguardMessageAreaController.Factory factory, InputMethodManager inputMethodManager, DelayableExecutor delayableExecutor, Resources resources, Object obj, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController.Factory factory2, DevicePostureController devicePostureController, KeyguardViewController keyguardViewController, FeatureFlags featureFlags) {
        return new KeyguardInputViewController.Factory(keyguardUpdateMonitor, secRotationWatcher, configurationController, vibrationUtil, accessibilityManager, keyguardTouchSwipeDetector, wifiManager, networkController, screenLifecycle, lockPatternUtils, latencyTracker, factory, inputMethodManager, delayableExecutor, resources, (LiftToActivateListener) obj, telephonyManager, falsingCollector, factory2, devicePostureController, keyguardViewController, featureFlags);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (SecRotationWatcher) this.rotationWatcherProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (VibrationUtil) this.vibrationUtilProvider.get(), (AccessibilityManager) this.accessibilityManagerProvider.get(), (KeyguardTouchSwipeDetector) this.keyguardTouchSwipeDetectorProvider.get(), (WifiManager) this.wifiManagerProvider.get(), (NetworkController) this.networkControllerProvider.get(), (ScreenLifecycle) this.screenLifecycleProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (LatencyTracker) this.latencyTrackerProvider.get(), (KeyguardMessageAreaController.Factory) this.messageAreaControllerFactoryProvider.get(), (InputMethodManager) this.inputMethodManagerProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (Resources) this.resourcesProvider.get(), this.liftToActivateListenerProvider.get(), (TelephonyManager) this.telephonyManagerProvider.get(), (FalsingCollector) this.falsingCollectorProvider.get(), (EmergencyButtonController.Factory) this.emergencyButtonControllerFactoryProvider.get(), (DevicePostureController) this.devicePostureControllerProvider.get(), (KeyguardViewController) this.keyguardViewControllerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}
