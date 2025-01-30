package com.android.systemui.keyguard.dagger;

import android.app.trust.TrustManager;
import android.content.Context;
import android.os.PowerManager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.wm.shell.keyguard.KeyguardTransitions;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.SafeUIKeyguardViewMediator;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.SafeUIState;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import java.util.concurrent.Executor;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardModule_NewKeyguardViewMediatorFactory implements Provider {
    public final Provider activityLaunchAnimatorProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;
    public final Provider deviceConfigProvider;
    public final Provider dismissCallbackRegistryProvider;
    public final Provider dozeParametersProvider;
    public final Provider dreamOverlayStateControllerProvider;
    public final Provider dreamingToLockscreenTransitionViewModelProvider;
    public final Provider dumpManagerProvider;
    public final Provider falsingCollectorProvider;
    public final Provider featureFlagsProvider;
    public final Provider helperProvider;
    public final Provider interactionJankMonitorProvider;
    public final Provider keyguardDisplayManagerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardTransitionsProvider;
    public final Provider keyguardUnlockAnimationControllerProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider mainDispatcherProvider;
    public final Provider navigationModeControllerProvider;
    public final Provider notificationShadeDepthControllerProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider powerManagerProvider;
    public final Provider screenOffAnimationControllerProvider;
    public final Provider screenOnCoordinatorProvider;
    public final Provider scrimControllerLazyProvider;
    public final Provider sessionTrackerProvider;
    public final Provider shadeControllerProvider;
    public final Provider statusBarKeyguardViewManagerLazyProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider systemPropertiesHelperProvider;
    public final Provider trustManagerProvider;
    public final Provider uiBgExecutorProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider updateMonitorProvider;
    public final Provider userSwitcherControllerProvider;
    public final Provider userTrackerProvider;

    public KeyguardModule_NewKeyguardViewMediatorFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37) {
        this.helperProvider = provider;
        this.contextProvider = provider2;
        this.uiEventLoggerProvider = provider3;
        this.sessionTrackerProvider = provider4;
        this.userTrackerProvider = provider5;
        this.falsingCollectorProvider = provider6;
        this.lockPatternUtilsProvider = provider7;
        this.broadcastDispatcherProvider = provider8;
        this.statusBarKeyguardViewManagerLazyProvider = provider9;
        this.dismissCallbackRegistryProvider = provider10;
        this.updateMonitorProvider = provider11;
        this.dumpManagerProvider = provider12;
        this.powerManagerProvider = provider13;
        this.trustManagerProvider = provider14;
        this.userSwitcherControllerProvider = provider15;
        this.uiBgExecutorProvider = provider16;
        this.deviceConfigProvider = provider17;
        this.navigationModeControllerProvider = provider18;
        this.keyguardDisplayManagerProvider = provider19;
        this.dozeParametersProvider = provider20;
        this.statusBarStateControllerProvider = provider21;
        this.keyguardStateControllerProvider = provider22;
        this.keyguardUnlockAnimationControllerProvider = provider23;
        this.screenOffAnimationControllerProvider = provider24;
        this.notificationShadeDepthControllerProvider = provider25;
        this.screenOnCoordinatorProvider = provider26;
        this.keyguardTransitionsProvider = provider27;
        this.interactionJankMonitorProvider = provider28;
        this.dreamOverlayStateControllerProvider = provider29;
        this.shadeControllerProvider = provider30;
        this.notificationShadeWindowControllerProvider = provider31;
        this.activityLaunchAnimatorProvider = provider32;
        this.scrimControllerLazyProvider = provider33;
        this.featureFlagsProvider = provider34;
        this.mainDispatcherProvider = provider35;
        this.dreamingToLockscreenTransitionViewModelProvider = provider36;
        this.systemPropertiesHelperProvider = provider37;
    }

    public static KeyguardViewMediator newKeyguardViewMediator(KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl, Context context, UiEventLogger uiEventLogger, SessionTracker sessionTracker, UserTracker userTracker, FalsingCollector falsingCollector, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, Lazy lazy, DismissCallbackRegistry dismissCallbackRegistry, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Executor executor, PowerManager powerManager, TrustManager trustManager, UserSwitcherController userSwitcherController, DeviceConfigProxy deviceConfigProxy, NavigationModeController navigationModeController, KeyguardDisplayManager keyguardDisplayManager, DozeParameters dozeParameters, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, Lazy lazy2, ScreenOffAnimationController screenOffAnimationController, Lazy lazy3, ScreenOnCoordinator screenOnCoordinator, KeyguardTransitions keyguardTransitions, InteractionJankMonitor interactionJankMonitor, DreamOverlayStateController dreamOverlayStateController, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, FeatureFlags featureFlags, CoroutineDispatcher coroutineDispatcher, Lazy lazy8, SystemPropertiesHelper systemPropertiesHelper) {
        return SafeUIState.isSysUiSafeModeEnabled() ? new SafeUIKeyguardViewMediator(keyguardViewMediatorHelperImpl, context, uiEventLogger, sessionTracker, userTracker, falsingCollector, lockPatternUtils, broadcastDispatcher, lazy, dismissCallbackRegistry, keyguardUpdateMonitor, dumpManager, executor, powerManager, trustManager, userSwitcherController, deviceConfigProxy, navigationModeController, keyguardDisplayManager, dozeParameters, sysuiStatusBarStateController, keyguardStateController, lazy2, screenOffAnimationController, lazy3, screenOnCoordinator, keyguardTransitions, interactionJankMonitor, dreamOverlayStateController, lazy4, lazy5, lazy6, lazy7, featureFlags, coroutineDispatcher, lazy8, systemPropertiesHelper) : new KeyguardViewMediator(keyguardViewMediatorHelperImpl, context, uiEventLogger, sessionTracker, userTracker, falsingCollector, lockPatternUtils, broadcastDispatcher, lazy, dismissCallbackRegistry, keyguardUpdateMonitor, dumpManager, executor, powerManager, trustManager, userSwitcherController, deviceConfigProxy, navigationModeController, keyguardDisplayManager, dozeParameters, sysuiStatusBarStateController, keyguardStateController, lazy2, screenOffAnimationController, lazy3, screenOnCoordinator, keyguardTransitions, interactionJankMonitor, dreamOverlayStateController, lazy4, lazy5, lazy6, lazy7, featureFlags, coroutineDispatcher, lazy8, systemPropertiesHelper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newKeyguardViewMediator((KeyguardViewMediatorHelperImpl) this.helperProvider.get(), (Context) this.contextProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (SessionTracker) this.sessionTrackerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (FalsingCollector) this.falsingCollectorProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), DoubleCheck.lazy(this.statusBarKeyguardViewManagerLazyProvider), (DismissCallbackRegistry) this.dismissCallbackRegistryProvider.get(), (KeyguardUpdateMonitor) this.updateMonitorProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (Executor) this.uiBgExecutorProvider.get(), (PowerManager) this.powerManagerProvider.get(), (TrustManager) this.trustManagerProvider.get(), (UserSwitcherController) this.userSwitcherControllerProvider.get(), (DeviceConfigProxy) this.deviceConfigProvider.get(), (NavigationModeController) this.navigationModeControllerProvider.get(), (KeyguardDisplayManager) this.keyguardDisplayManagerProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), DoubleCheck.lazy(this.keyguardUnlockAnimationControllerProvider), (ScreenOffAnimationController) this.screenOffAnimationControllerProvider.get(), DoubleCheck.lazy(this.notificationShadeDepthControllerProvider), (ScreenOnCoordinator) this.screenOnCoordinatorProvider.get(), (KeyguardTransitions) this.keyguardTransitionsProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (DreamOverlayStateController) this.dreamOverlayStateControllerProvider.get(), DoubleCheck.lazy(this.shadeControllerProvider), DoubleCheck.lazy(this.notificationShadeWindowControllerProvider), DoubleCheck.lazy(this.activityLaunchAnimatorProvider), DoubleCheck.lazy(this.scrimControllerLazyProvider), (FeatureFlags) this.featureFlagsProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), DoubleCheck.lazy(this.dreamingToLockscreenTransitionViewModelProvider), (SystemPropertiesHelper) this.systemPropertiesHelperProvider.get());
    }
}
