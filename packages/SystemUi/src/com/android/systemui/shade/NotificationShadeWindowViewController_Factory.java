package com.android.systemui.shade;

import com.android.keyguard.KeyguardPresentationDisabler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.p009ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.keyguard.p009ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationInsetsController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.util.time.SystemClock;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationShadeWindowViewController_Factory implements Provider {
    public final Provider ambientStateProvider;
    public final Provider centralSurfacesProvider;
    public final Provider clockProvider;
    public final Provider controllerProvider;
    public final Provider depthControllerProvider;
    public final Provider dockManagerProvider;
    public final Provider falsingCollectorProvider;
    public final Provider featureFlagsProvider;
    public final Provider keyguardBouncerComponentFactoryProvider;
    public final Provider keyguardBouncerViewModelProvider;
    public final Provider keyguardTransitionInteractorProvider;
    public final Provider keyguardUnlockAnimationControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider lockIconViewControllerProvider;
    public final Provider mPresentationDisablerProvider;
    public final Provider multiShadeInteractorProvider;
    public final Provider multiShadeMotionEventInteractorProvider;
    public final Provider notificationInsetsControllerProvider;
    public final Provider notificationPanelViewControllerProvider;
    public final Provider notificationShadeWindowViewProvider;
    public final Provider notificationStackScrollLayoutControllerProvider;
    public final Provider pluginLockStarManagerProvider;
    public final Provider primaryBouncerToGoneTransitionViewModelProvider;
    public final Provider pulsingGestureListenerProvider;
    public final Provider shadeExpansionStateManagerProvider;
    public final Provider statusBarKeyguardViewManagerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider statusBarWindowStateControllerProvider;
    public final Provider transitionControllerProvider;
    public final Provider unfoldTransitionProgressProvider;

    public NotificationShadeWindowViewController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30) {
        this.pluginLockStarManagerProvider = provider;
        this.keyguardUpdateMonitorProvider = provider2;
        this.transitionControllerProvider = provider3;
        this.falsingCollectorProvider = provider4;
        this.statusBarStateControllerProvider = provider5;
        this.dockManagerProvider = provider6;
        this.depthControllerProvider = provider7;
        this.notificationShadeWindowViewProvider = provider8;
        this.notificationPanelViewControllerProvider = provider9;
        this.shadeExpansionStateManagerProvider = provider10;
        this.notificationStackScrollLayoutControllerProvider = provider11;
        this.statusBarKeyguardViewManagerProvider = provider12;
        this.statusBarWindowStateControllerProvider = provider13;
        this.lockIconViewControllerProvider = provider14;
        this.centralSurfacesProvider = provider15;
        this.controllerProvider = provider16;
        this.unfoldTransitionProgressProvider = provider17;
        this.keyguardUnlockAnimationControllerProvider = provider18;
        this.notificationInsetsControllerProvider = provider19;
        this.ambientStateProvider = provider20;
        this.pulsingGestureListenerProvider = provider21;
        this.keyguardBouncerViewModelProvider = provider22;
        this.keyguardBouncerComponentFactoryProvider = provider23;
        this.keyguardTransitionInteractorProvider = provider24;
        this.primaryBouncerToGoneTransitionViewModelProvider = provider25;
        this.featureFlagsProvider = provider26;
        this.multiShadeInteractorProvider = provider27;
        this.clockProvider = provider28;
        this.multiShadeMotionEventInteractorProvider = provider29;
        this.mPresentationDisablerProvider = provider30;
    }

    public static NotificationShadeWindowViewController newInstance(PluginLockStarManager pluginLockStarManager, KeyguardUpdateMonitor keyguardUpdateMonitor, LockscreenShadeTransitionController lockscreenShadeTransitionController, FalsingCollector falsingCollector, SysuiStatusBarStateController sysuiStatusBarStateController, DockManager dockManager, NotificationShadeDepthController notificationShadeDepthController, NotificationShadeWindowView notificationShadeWindowView, NotificationPanelViewController notificationPanelViewController, ShadeExpansionStateManager shadeExpansionStateManager, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, StatusBarWindowStateController statusBarWindowStateController, SecLockIconViewController secLockIconViewController, CentralSurfaces centralSurfaces, NotificationShadeWindowController notificationShadeWindowController, Optional optional, KeyguardUnlockAnimationController keyguardUnlockAnimationController, NotificationInsetsController notificationInsetsController, AmbientState ambientState, PulsingGestureListener pulsingGestureListener, KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerComponent.Factory factory, KeyguardTransitionInteractor keyguardTransitionInteractor, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, FeatureFlags featureFlags, Provider provider, SystemClock systemClock, Provider provider2) {
        return new NotificationShadeWindowViewController(pluginLockStarManager, keyguardUpdateMonitor, lockscreenShadeTransitionController, falsingCollector, sysuiStatusBarStateController, dockManager, notificationShadeDepthController, notificationShadeWindowView, notificationPanelViewController, shadeExpansionStateManager, notificationStackScrollLayoutController, statusBarKeyguardViewManager, statusBarWindowStateController, secLockIconViewController, centralSurfaces, notificationShadeWindowController, optional, keyguardUnlockAnimationController, notificationInsetsController, ambientState, pulsingGestureListener, keyguardBouncerViewModel, factory, keyguardTransitionInteractor, primaryBouncerToGoneTransitionViewModel, featureFlags, provider, systemClock, provider2);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        NotificationShadeWindowViewController newInstance = newInstance((PluginLockStarManager) this.pluginLockStarManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (LockscreenShadeTransitionController) this.transitionControllerProvider.get(), (FalsingCollector) this.falsingCollectorProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (DockManager) this.dockManagerProvider.get(), (NotificationShadeDepthController) this.depthControllerProvider.get(), (NotificationShadeWindowView) this.notificationShadeWindowViewProvider.get(), (NotificationPanelViewController) this.notificationPanelViewControllerProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (NotificationStackScrollLayoutController) this.notificationStackScrollLayoutControllerProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (StatusBarWindowStateController) this.statusBarWindowStateControllerProvider.get(), (SecLockIconViewController) this.lockIconViewControllerProvider.get(), (CentralSurfaces) this.centralSurfacesProvider.get(), (NotificationShadeWindowController) this.controllerProvider.get(), (Optional) this.unfoldTransitionProgressProvider.get(), (KeyguardUnlockAnimationController) this.keyguardUnlockAnimationControllerProvider.get(), (NotificationInsetsController) this.notificationInsetsControllerProvider.get(), (AmbientState) this.ambientStateProvider.get(), (PulsingGestureListener) this.pulsingGestureListenerProvider.get(), (KeyguardBouncerViewModel) this.keyguardBouncerViewModelProvider.get(), (KeyguardBouncerComponent.Factory) this.keyguardBouncerComponentFactoryProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get(), (PrimaryBouncerToGoneTransitionViewModel) this.primaryBouncerToGoneTransitionViewModelProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), this.multiShadeInteractorProvider, (SystemClock) this.clockProvider.get(), this.multiShadeMotionEventInteractorProvider);
        newInstance.mPresentationDisabler = (KeyguardPresentationDisabler) this.mPresentationDisablerProvider.get();
        return newInstance;
    }
}
