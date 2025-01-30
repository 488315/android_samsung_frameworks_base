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
import com.android.systemui.biometrics.FaceHelpMessageDeferral;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockData;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.KeyguardSecIndicationController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.wakelock.WakeLock;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServicesModule_ProvideKeyguardIndicationControllerFactory implements Provider {
    public final Provider accessibilityManagerProvider;
    public final Provider alarmManagerProvider;
    public final Provider alternateBouncerInteractorProvider;
    public final Provider authControllerProvider;
    public final Provider bgExecutorProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;
    public final Provider devicePolicyManagerProvider;
    public final Provider dockManagerProvider;
    public final Provider executorProvider;
    public final Provider faceHelpMessageDeferralProvider;
    public final Provider falsingManagerProvider;
    public final Provider featureFlagsProvider;
    public final Provider iActivityManagerProvider;
    public final Provider iBatteryStatsProvider;
    public final Provider keyguardBypassControllerProvider;
    public final Provider keyguardEditModeControllerProvider;
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
    public final Provider statusBarStateControllerProvider;
    public final Provider userManagerProvider;
    public final Provider userTrackerProvider;
    public final Provider wakeLockBuilderProvider;

    public SamsungServicesModule_ProvideKeyguardIndicationControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31) {
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
        this.faceHelpMessageDeferralProvider = provider21;
        this.keyguardLoggerProvider = provider22;
        this.alternateBouncerInteractorProvider = provider23;
        this.alarmManagerProvider = provider24;
        this.userTrackerProvider = provider25;
        this.featureFlagsProvider = provider26;
        this.rotationWatcherProvider = provider27;
        this.pluginLockMediatorProvider = provider28;
        this.pluginLockDataProvider = provider29;
        this.pluginLockStarManagerProvider = provider30;
        this.keyguardEditModeControllerProvider = provider31;
    }

    public static KeyguardSecIndicationController provideKeyguardIndicationController(Context context, Looper looper, WakeLock.Builder builder, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, IBatteryStats iBatteryStats, UserManager userManager, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, FalsingManager falsingManager, AuthController authController, LockPatternUtils lockPatternUtils, ScreenLifecycle screenLifecycle, KeyguardBypassController keyguardBypassController, AccessibilityManager accessibilityManager, FaceHelpMessageDeferral faceHelpMessageDeferral, KeyguardLogger keyguardLogger, AlternateBouncerInteractor alternateBouncerInteractor, AlarmManager alarmManager, UserTracker userTracker, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, PluginLockMediator pluginLockMediator, PluginLockData pluginLockData, PluginLockStarManager pluginLockStarManager, KeyguardEditModeController keyguardEditModeController) {
        return new KeyguardSecIndicationController(context, looper, builder, keyguardStateController, statusBarStateController, keyguardUpdateMonitor, dockManager, broadcastDispatcher, devicePolicyManager, iBatteryStats, userManager, delayableExecutor, delayableExecutor2, falsingManager, authController, lockPatternUtils, screenLifecycle, keyguardBypassController, accessibilityManager, faceHelpMessageDeferral, keyguardLogger, alternateBouncerInteractor, alarmManager, userTracker, featureFlags, secRotationWatcher, pluginLockMediator, pluginLockData, pluginLockStarManager, keyguardEditModeController);
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
        return provideKeyguardIndicationController(context, looper, builder, keyguardStateController, statusBarStateController, keyguardUpdateMonitor, dockManager, broadcastDispatcher, devicePolicyManager, iBatteryStats, userManager, delayableExecutor, delayableExecutor2, falsingManager, authController, lockPatternUtils, screenLifecycle, (KeyguardBypassController) this.keyguardBypassControllerProvider.get(), (AccessibilityManager) this.accessibilityManagerProvider.get(), (FaceHelpMessageDeferral) this.faceHelpMessageDeferralProvider.get(), (KeyguardLogger) this.keyguardLoggerProvider.get(), (AlternateBouncerInteractor) this.alternateBouncerInteractorProvider.get(), (AlarmManager) this.alarmManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (SecRotationWatcher) this.rotationWatcherProvider.get(), (PluginLockMediator) this.pluginLockMediatorProvider.get(), (PluginLockData) this.pluginLockDataProvider.get(), (PluginLockStarManager) this.pluginLockStarManagerProvider.get(), (KeyguardEditModeController) this.keyguardEditModeControllerProvider.get());
    }
}
