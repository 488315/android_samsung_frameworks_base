package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavigationBar_Factory implements Provider {
    public final Provider accessibilityManagerProvider;
    public final Provider assistManagerLazyProvider;
    public final Provider autoHideControllerFactoryProvider;
    public final Provider backAnimationProvider;
    public final Provider bgExecutorProvider;
    public final Provider centralSurfacesOptionalLazyProvider;
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Provider deadZoneProvider;
    public final Provider deviceConfigProxyProvider;
    public final Provider deviceProvisionedControllerProvider;
    public final Provider displayTrackerProvider;
    public final Provider inputMethodManagerProvider;
    public final Provider lightBarControllerFactoryProvider;
    public final Provider logWrapperProvider;
    public final Provider mainAutoHideControllerProvider;
    public final Provider mainExecutorProvider;
    public final Provider mainHandlerProvider;
    public final Provider mainLightBarControllerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider navBarHelperProvider;
    public final Provider navigationBarFrameProvider;
    public final Provider navigationBarTransitionsProvider;
    public final Provider navigationBarViewProvider;
    public final Provider navigationModeControllerProvider;
    public final Provider notificationRemoteInputManagerProvider;
    public final Provider notificationShadeDepthControllerProvider;
    public final Provider overviewProxyServiceProvider;
    public final Provider pipOptionalProvider;
    public final Provider recentsOptionalProvider;
    public final Provider savedStateProvider;
    public final Provider shadeControllerProvider;
    public final Provider statusBarKeyguardViewManagerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider sysUiFlagsContainerProvider;
    public final Provider taskStackChangeListenersProvider;
    public final Provider telecomManagerOptionalProvider;
    public final Provider uiEventLoggerProvider;
    public final Provider userContextProvider;
    public final Provider userTrackerProvider;
    public final Provider wakefulnessLifecycleProvider;
    public final Provider windowManagerProvider;

    public NavigationBar_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39, Provider provider40, Provider provider41, Provider provider42) {
        this.navigationBarViewProvider = provider;
        this.navigationBarFrameProvider = provider2;
        this.savedStateProvider = provider3;
        this.contextProvider = provider4;
        this.windowManagerProvider = provider5;
        this.assistManagerLazyProvider = provider6;
        this.accessibilityManagerProvider = provider7;
        this.deviceProvisionedControllerProvider = provider8;
        this.metricsLoggerProvider = provider9;
        this.overviewProxyServiceProvider = provider10;
        this.navigationModeControllerProvider = provider11;
        this.statusBarStateControllerProvider = provider12;
        this.statusBarKeyguardViewManagerProvider = provider13;
        this.sysUiFlagsContainerProvider = provider14;
        this.userTrackerProvider = provider15;
        this.commandQueueProvider = provider16;
        this.pipOptionalProvider = provider17;
        this.recentsOptionalProvider = provider18;
        this.centralSurfacesOptionalLazyProvider = provider19;
        this.shadeControllerProvider = provider20;
        this.notificationRemoteInputManagerProvider = provider21;
        this.notificationShadeDepthControllerProvider = provider22;
        this.mainHandlerProvider = provider23;
        this.mainExecutorProvider = provider24;
        this.bgExecutorProvider = provider25;
        this.uiEventLoggerProvider = provider26;
        this.navBarHelperProvider = provider27;
        this.mainLightBarControllerProvider = provider28;
        this.lightBarControllerFactoryProvider = provider29;
        this.mainAutoHideControllerProvider = provider30;
        this.autoHideControllerFactoryProvider = provider31;
        this.telecomManagerOptionalProvider = provider32;
        this.inputMethodManagerProvider = provider33;
        this.deadZoneProvider = provider34;
        this.deviceConfigProxyProvider = provider35;
        this.navigationBarTransitionsProvider = provider36;
        this.backAnimationProvider = provider37;
        this.userContextProvider = provider38;
        this.wakefulnessLifecycleProvider = provider39;
        this.taskStackChangeListenersProvider = provider40;
        this.displayTrackerProvider = provider41;
        this.logWrapperProvider = provider42;
    }

    public static NavigationBar newInstance(NavigationBarView navigationBarView, NavigationBarFrame navigationBarFrame, Bundle bundle, Context context, WindowManager windowManager, Lazy lazy, AccessibilityManager accessibilityManager, DeviceProvisionedController deviceProvisionedController, MetricsLogger metricsLogger, OverviewProxyService overviewProxyService, NavigationModeController navigationModeController, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, SysUiState sysUiState, UserTracker userTracker, CommandQueue commandQueue, Optional optional, Optional optional2, Lazy lazy2, ShadeController shadeController, NotificationRemoteInputManager notificationRemoteInputManager, NotificationShadeDepthController notificationShadeDepthController, Handler handler, Executor executor, Executor executor2, UiEventLogger uiEventLogger, NavBarHelper navBarHelper, LightBarController lightBarController, LightBarController.Factory factory, AutoHideController autoHideController, AutoHideController.Factory factory2, Optional optional3, InputMethodManager inputMethodManager, DeadZone deadZone, DeviceConfigProxy deviceConfigProxy, NavigationBarTransitions navigationBarTransitions, Optional optional4, UserContextProvider userContextProvider, WakefulnessLifecycle wakefulnessLifecycle, TaskStackChangeListeners taskStackChangeListeners, DisplayTracker displayTracker, LogWrapper logWrapper) {
        return new NavigationBar(navigationBarView, navigationBarFrame, bundle, context, windowManager, lazy, accessibilityManager, deviceProvisionedController, metricsLogger, overviewProxyService, navigationModeController, statusBarStateController, statusBarKeyguardViewManager, sysUiState, userTracker, commandQueue, optional, optional2, lazy2, shadeController, notificationRemoteInputManager, notificationShadeDepthController, handler, executor, executor2, uiEventLogger, navBarHelper, lightBarController, factory, autoHideController, factory2, optional3, inputMethodManager, deadZone, deviceConfigProxy, navigationBarTransitions, optional4, userContextProvider, wakefulnessLifecycle, taskStackChangeListeners, displayTracker, logWrapper);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((NavigationBarView) this.navigationBarViewProvider.get(), (NavigationBarFrame) this.navigationBarFrameProvider.get(), (Bundle) this.savedStateProvider.get(), (Context) this.contextProvider.get(), (WindowManager) this.windowManagerProvider.get(), DoubleCheck.lazy(this.assistManagerLazyProvider), (AccessibilityManager) this.accessibilityManagerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (OverviewProxyService) this.overviewProxyServiceProvider.get(), (NavigationModeController) this.navigationModeControllerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (SysUiState) this.sysUiFlagsContainerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (Optional) this.pipOptionalProvider.get(), (Optional) this.recentsOptionalProvider.get(), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), (ShadeController) this.shadeControllerProvider.get(), (NotificationRemoteInputManager) this.notificationRemoteInputManagerProvider.get(), (NotificationShadeDepthController) this.notificationShadeDepthControllerProvider.get(), (Handler) this.mainHandlerProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.bgExecutorProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (NavBarHelper) this.navBarHelperProvider.get(), (LightBarController) this.mainLightBarControllerProvider.get(), (LightBarController.Factory) this.lightBarControllerFactoryProvider.get(), (AutoHideController) this.mainAutoHideControllerProvider.get(), (AutoHideController.Factory) this.autoHideControllerFactoryProvider.get(), (Optional) this.telecomManagerOptionalProvider.get(), (InputMethodManager) this.inputMethodManagerProvider.get(), (DeadZone) this.deadZoneProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get(), (NavigationBarTransitions) this.navigationBarTransitionsProvider.get(), (Optional) this.backAnimationProvider.get(), (UserContextProvider) this.userContextProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (TaskStackChangeListeners) this.taskStackChangeListenersProvider.get(), (DisplayTracker) this.displayTrackerProvider.get(), (LogWrapper) this.logWrapperProvider.get());
    }
}
