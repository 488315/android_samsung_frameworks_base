package com.android.systemui.dagger;

import android.app.AlarmManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Looper;
import android.os.UserManager;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.app.IBatteryStats;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecRotationWatcher;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.FaceHelpMessageDeferralFactory;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFingerprintAuthInteractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.util.IndicationHelper;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.KeyguardSecIndicationController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.user.domain.interactor.UserLogoutInteractor;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class SamsungServicesModule_ProvideKeyguardIndicationControllerFactory implements Provider {
    public final Provider accessibilityManagerProvider;
    public final Provider alarmManagerProvider;
    public final Provider alternateBouncerInteractorProvider;
    public final Provider authControllerProvider;
    public final Provider bgExecutorProvider;
    public final Provider biometricMessageInteractorProvider;
    public final Provider bouncerMessageInteractorProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;
    public final Provider deviceEntryFaceAuthInteractorProvider;
    public final Provider deviceEntryFingerprintAuthInteractorProvider;
    public final Provider devicePolicyManagerProvider;
    public final Provider dockManagerProvider;
    public final Provider executorProvider;
    public final Provider faceHelpMessageDeferralFactoryProvider;
    public final Provider falsingManagerProvider;
    public final Provider featureFlagsProvider;
    public final Provider iActivityManagerProvider;
    public final Provider iBatteryStatsProvider;
    public final Provider indicationHelperProvider;
    public final Provider keyguardBypassControllerProvider;
    public final Provider keyguardEditModeControllerProvider;
    public final Provider keyguardInteractorProvider;
    public final Provider keyguardLoggerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider mainLooperProvider;
    public final Provider pluginLockDataProvider;
    public final Provider pluginLockMediatorProvider;
    public final Provider pluginLockStarManagerProvider;
    public final Provider rotationWatcherProvider;
    public final Provider screenLifecycleProvider;
    public final Provider selectedUserInteractorProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider userLogoutInteractorProvider;
    public final Provider userManagerProvider;
    public final Provider userTrackerProvider;
    public final Provider wakeLockBuilderProvider;

    public SamsungServicesModule_ProvideKeyguardIndicationControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39) {
        this.contextProvider = provider;
        this.mainLooperProvider = provider2;
        this.wakeLockBuilderProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
        this.statusBarStateControllerProvider = provider5;
        this.keyguardUpdateMonitorProvider = provider6;
        this.dockManagerProvider = provider7;
        this.broadcastDispatcherProvider = provider8;
        this.devicePolicyManagerProvider = provider9;
        this.iBatteryStatsProvider = provider10;
        this.userManagerProvider = provider11;
        this.executorProvider = provider12;
        this.bgExecutorProvider = provider13;
        this.falsingManagerProvider = provider14;
        this.authControllerProvider = provider15;
        this.lockPatternUtilsProvider = provider16;
        this.screenLifecycleProvider = provider17;
        this.iActivityManagerProvider = provider18;
        this.keyguardBypassControllerProvider = provider19;
        this.accessibilityManagerProvider = provider20;
        this.faceHelpMessageDeferralFactoryProvider = provider21;
        this.keyguardLoggerProvider = provider22;
        this.alternateBouncerInteractorProvider = provider23;
        this.alarmManagerProvider = provider24;
        this.userTrackerProvider = provider25;
        this.bouncerMessageInteractorProvider = provider26;
        this.featureFlagsProvider = provider27;
        this.indicationHelperProvider = provider28;
        this.keyguardInteractorProvider = provider29;
        this.biometricMessageInteractorProvider = provider30;
        this.deviceEntryFingerprintAuthInteractorProvider = provider31;
        this.deviceEntryFaceAuthInteractorProvider = provider32;
        this.userLogoutInteractorProvider = provider33;
        this.selectedUserInteractorProvider = provider34;
        this.rotationWatcherProvider = provider35;
        this.pluginLockMediatorProvider = provider36;
        this.pluginLockDataProvider = provider37;
        this.pluginLockStarManagerProvider = provider38;
        this.keyguardEditModeControllerProvider = provider39;
    }

    public static KeyguardSecIndicationController provideKeyguardIndicationController(Context context, Looper looper, WakeLock.Builder builder, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, IBatteryStats iBatteryStats, UserManager userManager, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, FalsingManager falsingManager, AuthController authController, LockPatternUtils lockPatternUtils, ScreenLifecycle screenLifecycle, KeyguardBypassController keyguardBypassController, AccessibilityManager accessibilityManager, FaceHelpMessageDeferralFactory faceHelpMessageDeferralFactory, KeyguardLogger keyguardLogger, AlternateBouncerInteractor alternateBouncerInteractor, AlarmManager alarmManager, UserTracker userTracker, BouncerMessageInteractor bouncerMessageInteractor, FeatureFlags featureFlags, IndicationHelper indicationHelper, KeyguardInteractor keyguardInteractor, BiometricMessageInteractor biometricMessageInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, UserLogoutInteractor userLogoutInteractor, SelectedUserInteractor selectedUserInteractor, SecRotationWatcher secRotationWatcher, PluginLockMediator pluginLockMediator, PluginLockData pluginLockData, PluginLockStarManager pluginLockStarManager, KeyguardEditModeController keyguardEditModeController) {
        return new KeyguardSecIndicationController(context, looper, builder, keyguardStateController, statusBarStateController, keyguardUpdateMonitor, dockManager, broadcastDispatcher, devicePolicyManager, iBatteryStats, userManager, delayableExecutor, delayableExecutor2, falsingManager, authController, lockPatternUtils, screenLifecycle, keyguardBypassController, accessibilityManager, faceHelpMessageDeferralFactory, keyguardLogger, alternateBouncerInteractor, alarmManager, userTracker, bouncerMessageInteractor, featureFlags, indicationHelper, keyguardInteractor, biometricMessageInteractor, deviceEntryFingerprintAuthInteractor, deviceEntryFaceAuthInteractor, userLogoutInteractor, selectedUserInteractor, secRotationWatcher, pluginLockMediator, pluginLockData, pluginLockStarManager, keyguardEditModeController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Context context = (Context) this.contextProvider.get();
        Looper looper = (Looper) this.mainLooperProvider.get();
        WakeLock.Builder builder = (WakeLock.Builder) this.wakeLockBuilderProvider.get();
        KeyguardStateController keyguardStateController = (KeyguardStateController) this.keyguardStateControllerProvider.get();
        StatusBarStateController statusBarStateController = (StatusBarStateController) this.statusBarStateControllerProvider.get();
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get();
        DockManager dockManager = (DockManager) this.dockManagerProvider.get();
        BroadcastDispatcher broadcastDispatcher = (BroadcastDispatcher) this.broadcastDispatcherProvider.get();
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.devicePolicyManagerProvider.get();
        IBatteryStats iBatteryStats = (IBatteryStats) this.iBatteryStatsProvider.get();
        UserManager userManager = (UserManager) this.userManagerProvider.get();
        DelayableExecutor delayableExecutor = (DelayableExecutor) this.executorProvider.get();
        DelayableExecutor delayableExecutor2 = (DelayableExecutor) this.bgExecutorProvider.get();
        FalsingManager falsingManager = (FalsingManager) this.falsingManagerProvider.get();
        AuthController authController = (AuthController) this.authControllerProvider.get();
        LockPatternUtils lockPatternUtils = (LockPatternUtils) this.lockPatternUtilsProvider.get();
        ScreenLifecycle screenLifecycle = (ScreenLifecycle) this.screenLifecycleProvider.get();
        return new KeyguardSecIndicationController(context, looper, builder, keyguardStateController, statusBarStateController, keyguardUpdateMonitor, dockManager, broadcastDispatcher, devicePolicyManager, iBatteryStats, userManager, delayableExecutor, delayableExecutor2, falsingManager, authController, lockPatternUtils, screenLifecycle, (KeyguardBypassController) this.keyguardBypassControllerProvider.get(), (AccessibilityManager) this.accessibilityManagerProvider.get(), (FaceHelpMessageDeferralFactory) this.faceHelpMessageDeferralFactoryProvider.get(), (KeyguardLogger) this.keyguardLoggerProvider.get(), (AlternateBouncerInteractor) this.alternateBouncerInteractorProvider.get(), (AlarmManager) this.alarmManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (BouncerMessageInteractor) this.bouncerMessageInteractorProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (IndicationHelper) this.indicationHelperProvider.get(), (KeyguardInteractor) this.keyguardInteractorProvider.get(), (BiometricMessageInteractor) this.biometricMessageInteractorProvider.get(), (DeviceEntryFingerprintAuthInteractor) this.deviceEntryFingerprintAuthInteractorProvider.get(), (DeviceEntryFaceAuthInteractor) this.deviceEntryFaceAuthInteractorProvider.get(), (UserLogoutInteractor) this.userLogoutInteractorProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (SecRotationWatcher) this.rotationWatcherProvider.get(), (PluginLockMediator) this.pluginLockMediatorProvider.get(), (PluginLockData) this.pluginLockDataProvider.get(), (PluginLockStarManager) this.pluginLockStarManagerProvider.get(), (KeyguardEditModeController) this.keyguardEditModeControllerProvider.get());
    }
}
