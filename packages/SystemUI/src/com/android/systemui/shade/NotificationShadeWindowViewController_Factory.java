package com.android.systemui.shade;

import android.view.Choreographer;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.ui.binder.BouncerViewBinder;
import com.android.systemui.bouncer.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.keyevent.domain.interactor.SysUIKeyEventHandler;
import com.android.systemui.keyguard.KeyguardSysDumpTrigger;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.scene.ui.view.WindowRootViewKeyEventHandler;
import com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationInsetsController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.domain.interactor.NotificationLaunchAnimationInteractor;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.window.ui.viewmodel.WindowRootViewModel;
import dagger.internal.Provider;
import java.util.Optional;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationShadeWindowViewController_Factory implements Provider {
    public final Provider alternateBouncerInteractorProvider;
    public final Provider ambientStateProvider;
    public final Provider blurUtilsProvider;
    public final Provider bouncerViewBinderProvider;
    public final Provider brightnessMirrorShowingInteractorProvider;
    public final Provider centralSurfacesProvider;
    public final Provider choreographerProvider;
    public final Provider clockProvider;
    public final Provider configurationForwarderProvider;
    public final Provider controllerProvider;
    public final Provider depthControllerProvider;
    public final Provider dockManagerProvider;
    public final Provider dozeScrimControllerProvider;
    public final Provider dozeServiceHostProvider;
    public final Provider dumpManagerProvider;
    public final Provider falsingCollectorProvider;
    public final Provider featureFlagsClassicProvider;
    public final Provider glanceableHubContainerControllerProvider;
    public final Provider keyguardBouncerComponentFactoryProvider;
    public final Provider keyguardBouncerViewModelProvider;
    public final Provider keyguardSysDumpTriggerProvider;
    public final Provider keyguardTransitionInteractorProvider;
    public final Provider keyguardUnlockAnimationControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider lockIconViewControllerProvider;
    public final Provider mPresentationDisablerProvider;
    public final Provider mainDispatcherProvider;
    public final Provider notificationInsetsControllerProvider;
    public final Provider notificationLaunchAnimationInteractorProvider;
    public final Provider notificationShadeWindowViewProvider;
    public final Provider notificationStackScrollLayoutControllerProvider;
    public final Provider panelExpansionInteractorProvider;
    public final Provider pluginLockStarManagerProvider;
    public final Provider primaryBouncerInteractorProvider;
    public final Provider primaryBouncerToGoneTransitionViewModelProvider;
    public final Provider pulsingGestureListenerProvider;
    public final Provider quickSettingsControllerProvider;
    public final Provider shadeAnimationInteractorProvider;
    public final Provider shadeExpansionStateManagerProvider;
    public final Provider shadeLoggerProvider;
    public final Provider shadeViewControllerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider statusBarWindowStateControllerProvider;
    public final Provider sysUIKeyEventHandlerProvider;
    public final Provider transitionControllerProvider;
    public final Provider unfoldComponentProvider;
    public final Provider unfoldTransitionProgressProvider;
    public final Provider windowRootViewKeyEventHandlerProvider;
    public final Provider windowRootViewModelFactoryProvider;

    public NotificationShadeWindowViewController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36, Provider provider37, Provider provider38, Provider provider39, Provider provider40, Provider provider41, Provider provider42, Provider provider43, Provider provider44, Provider provider45, Provider provider46, Provider provider47, Provider provider48, Provider provider49) {
        this.blurUtilsProvider = provider;
        this.windowRootViewModelFactoryProvider = provider2;
        this.choreographerProvider = provider3;
        this.keyguardBouncerViewModelProvider = provider4;
        this.keyguardBouncerComponentFactoryProvider = provider5;
        this.primaryBouncerToGoneTransitionViewModelProvider = provider6;
        this.keyguardUpdateMonitorProvider = provider7;
        this.pluginLockStarManagerProvider = provider8;
        this.keyguardSysDumpTriggerProvider = provider9;
        this.transitionControllerProvider = provider10;
        this.falsingCollectorProvider = provider11;
        this.statusBarStateControllerProvider = provider12;
        this.dockManagerProvider = provider13;
        this.depthControllerProvider = provider14;
        this.notificationShadeWindowViewProvider = provider15;
        this.shadeViewControllerProvider = provider16;
        this.shadeAnimationInteractorProvider = provider17;
        this.panelExpansionInteractorProvider = provider18;
        this.shadeExpansionStateManagerProvider = provider19;
        this.notificationStackScrollLayoutControllerProvider = provider20;
        this.statusBarWindowStateControllerProvider = provider21;
        this.lockIconViewControllerProvider = provider22;
        this.centralSurfacesProvider = provider23;
        this.dozeServiceHostProvider = provider24;
        this.dozeScrimControllerProvider = provider25;
        this.controllerProvider = provider26;
        this.unfoldTransitionProgressProvider = provider27;
        this.unfoldComponentProvider = provider28;
        this.keyguardUnlockAnimationControllerProvider = provider29;
        this.notificationInsetsControllerProvider = provider30;
        this.ambientStateProvider = provider31;
        this.shadeLoggerProvider = provider32;
        this.dumpManagerProvider = provider33;
        this.pulsingGestureListenerProvider = provider34;
        this.keyguardTransitionInteractorProvider = provider35;
        this.glanceableHubContainerControllerProvider = provider36;
        this.notificationLaunchAnimationInteractorProvider = provider37;
        this.featureFlagsClassicProvider = provider38;
        this.clockProvider = provider39;
        this.windowRootViewKeyEventHandlerProvider = provider40;
        this.quickSettingsControllerProvider = provider41;
        this.primaryBouncerInteractorProvider = provider42;
        this.alternateBouncerInteractorProvider = provider43;
        this.bouncerViewBinderProvider = provider44;
        this.configurationForwarderProvider = provider45;
        this.brightnessMirrorShowingInteractorProvider = provider46;
        this.sysUIKeyEventHandlerProvider = provider47;
        this.mainDispatcherProvider = provider48;
        this.mPresentationDisablerProvider = provider49;
    }

    public static NotificationShadeWindowViewController newInstance(BlurUtils blurUtils, WindowRootViewModel.Factory factory, Choreographer choreographer, KeyguardBouncerViewModel keyguardBouncerViewModel, DaggerReferenceGlobalRootComponent.KeyguardBouncerComponentFactory keyguardBouncerComponentFactory, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardUpdateMonitor keyguardUpdateMonitor, PluginLockStarManager pluginLockStarManager, KeyguardSysDumpTrigger keyguardSysDumpTrigger, LockscreenShadeTransitionController lockscreenShadeTransitionController, FalsingCollector falsingCollector, SysuiStatusBarStateController sysuiStatusBarStateController, DockManager dockManager, NotificationShadeDepthController notificationShadeDepthController, NotificationShadeWindowView notificationShadeWindowView, ShadeViewController shadeViewController, ShadeAnimationInteractor shadeAnimationInteractor, PanelExpansionInteractor panelExpansionInteractor, ShadeExpansionStateManager shadeExpansionStateManager, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarWindowStateController statusBarWindowStateController, SecLockIconViewController secLockIconViewController, CentralSurfaces centralSurfaces, DozeServiceHost dozeServiceHost, DozeScrimController dozeScrimController, NotificationShadeWindowController notificationShadeWindowController, Optional optional, Optional optional2, KeyguardUnlockAnimationController keyguardUnlockAnimationController, NotificationInsetsController notificationInsetsController, AmbientState ambientState, ShadeLogger shadeLogger, DumpManager dumpManager, PulsingGestureListener pulsingGestureListener, KeyguardTransitionInteractor keyguardTransitionInteractor, GlanceableHubContainerController glanceableHubContainerController, NotificationLaunchAnimationInteractor notificationLaunchAnimationInteractor, FeatureFlagsClassic featureFlagsClassic, SystemClock systemClock, WindowRootViewKeyEventHandler windowRootViewKeyEventHandler, QuickSettingsController quickSettingsController, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, BouncerViewBinder bouncerViewBinder, Provider provider, BrightnessMirrorShowingInteractor brightnessMirrorShowingInteractor, SysUIKeyEventHandler sysUIKeyEventHandler, CoroutineDispatcher coroutineDispatcher) {
        return new NotificationShadeWindowViewController(blurUtils, factory, choreographer, keyguardBouncerViewModel, keyguardBouncerComponentFactory, primaryBouncerToGoneTransitionViewModel, keyguardUpdateMonitor, pluginLockStarManager, keyguardSysDumpTrigger, lockscreenShadeTransitionController, falsingCollector, sysuiStatusBarStateController, dockManager, notificationShadeDepthController, notificationShadeWindowView, shadeViewController, shadeAnimationInteractor, panelExpansionInteractor, shadeExpansionStateManager, notificationStackScrollLayoutController, statusBarWindowStateController, secLockIconViewController, centralSurfaces, dozeServiceHost, dozeScrimController, notificationShadeWindowController, optional, optional2, keyguardUnlockAnimationController, notificationInsetsController, ambientState, shadeLogger, dumpManager, pulsingGestureListener, keyguardTransitionInteractor, glanceableHubContainerController, notificationLaunchAnimationInteractor, featureFlagsClassic, systemClock, windowRootViewKeyEventHandler, quickSettingsController, primaryBouncerInteractor, alternateBouncerInteractor, bouncerViewBinder, provider, brightnessMirrorShowingInteractor, sysUIKeyEventHandler, coroutineDispatcher);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        NotificationShadeWindowViewController notificationShadeWindowViewController = new NotificationShadeWindowViewController((BlurUtils) this.blurUtilsProvider.get(), (WindowRootViewModel.Factory) this.windowRootViewModelFactoryProvider.get(), (Choreographer) this.choreographerProvider.get(), (KeyguardBouncerViewModel) this.keyguardBouncerViewModelProvider.get(), (KeyguardBouncerComponent.Factory) this.keyguardBouncerComponentFactoryProvider.get(), (PrimaryBouncerToGoneTransitionViewModel) this.primaryBouncerToGoneTransitionViewModelProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (PluginLockStarManager) this.pluginLockStarManagerProvider.get(), (KeyguardSysDumpTrigger) this.keyguardSysDumpTriggerProvider.get(), (LockscreenShadeTransitionController) this.transitionControllerProvider.get(), (FalsingCollector) this.falsingCollectorProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (DockManager) this.dockManagerProvider.get(), (NotificationShadeDepthController) this.depthControllerProvider.get(), (NotificationShadeWindowView) this.notificationShadeWindowViewProvider.get(), (ShadeViewController) this.shadeViewControllerProvider.get(), (ShadeAnimationInteractor) this.shadeAnimationInteractorProvider.get(), (PanelExpansionInteractor) this.panelExpansionInteractorProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (NotificationStackScrollLayoutController) this.notificationStackScrollLayoutControllerProvider.get(), (StatusBarWindowStateController) this.statusBarWindowStateControllerProvider.get(), (SecLockIconViewController) this.lockIconViewControllerProvider.get(), (CentralSurfaces) this.centralSurfacesProvider.get(), (DozeServiceHost) this.dozeServiceHostProvider.get(), (DozeScrimController) this.dozeScrimControllerProvider.get(), (NotificationShadeWindowController) this.controllerProvider.get(), (Optional) this.unfoldTransitionProgressProvider.get(), (Optional) this.unfoldComponentProvider.get(), (KeyguardUnlockAnimationController) this.keyguardUnlockAnimationControllerProvider.get(), (NotificationInsetsController) this.notificationInsetsControllerProvider.get(), (AmbientState) this.ambientStateProvider.get(), (ShadeLogger) this.shadeLoggerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (PulsingGestureListener) this.pulsingGestureListenerProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get(), (GlanceableHubContainerController) this.glanceableHubContainerControllerProvider.get(), (NotificationLaunchAnimationInteractor) this.notificationLaunchAnimationInteractorProvider.get(), (FeatureFlagsClassic) this.featureFlagsClassicProvider.get(), (SystemClock) this.clockProvider.get(), (WindowRootViewKeyEventHandler) this.windowRootViewKeyEventHandlerProvider.get(), (QuickSettingsController) this.quickSettingsControllerProvider.get(), (PrimaryBouncerInteractor) this.primaryBouncerInteractorProvider.get(), (AlternateBouncerInteractor) this.alternateBouncerInteractorProvider.get(), (BouncerViewBinder) this.bouncerViewBinderProvider.get(), this.configurationForwarderProvider, (BrightnessMirrorShowingInteractor) this.brightnessMirrorShowingInteractorProvider.get(), (SysUIKeyEventHandler) this.sysUIKeyEventHandlerProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get());
        return notificationShadeWindowViewController;
    }
}
