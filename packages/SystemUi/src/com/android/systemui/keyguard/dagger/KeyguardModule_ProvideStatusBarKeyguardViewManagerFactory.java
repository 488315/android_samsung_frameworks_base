package com.android.systemui.keyguard.dagger;

import android.content.Context;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecRotationWatcher;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.biometrics.domain.interactor.UdfpsOverlayInteractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardViewMediatorHelper;
import com.android.systemui.keyguard.data.BouncerView;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.SafeUIStatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SecStatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.SafeUIState;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardModule_ProvideStatusBarKeyguardViewManagerFactory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider alternateBouncerInteractorProvider;
    public final Provider callbackProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider dockManagerProvider;
    public final Provider dreamOverlayStateControllerProvider;
    public final Provider fastBioUnlockControllerProvider;
    public final Provider featureFlagsProvider;
    public final Provider keyguardBouncerComponentFactoryProvider;
    public final Provider keyguardBouncerViewModelProvider;
    public final Provider keyguardMessageAreaFactoryProvider;
    public final Provider keyguardSecurityModelProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardUnlockAnimationControllerLazyProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider keyguardViewMediatorHelperProvider;
    public final Provider latencyTrackerProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider navigationModeControllerProvider;
    public final Provider notificationMediaManagerProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider primaryBouncerCallbackInteractorProvider;
    public final Provider primaryBouncerInteractorProvider;
    public final Provider primaryBouncerToGoneTransitionViewModelProvider;
    public final Provider primaryBouncerViewProvider;
    public final Provider rotationWatcherProvider;
    public final Provider shadeControllerProvider;
    public final Provider sysUIUnfoldComponentProvider;
    public final Provider sysuiStatusBarStateControllerProvider;
    public final Provider udfpsOverlayInteractorProvider;

    public KeyguardModule_ProvideStatusBarKeyguardViewManagerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31) {
        this.keyguardViewMediatorHelperProvider = provider;
        this.rotationWatcherProvider = provider2;
        this.fastBioUnlockControllerProvider = provider3;
        this.keyguardUnlockAnimationControllerLazyProvider = provider4;
        this.keyguardBouncerViewModelProvider = provider5;
        this.keyguardBouncerComponentFactoryProvider = provider6;
        this.primaryBouncerToGoneTransitionViewModelProvider = provider7;
        this.contextProvider = provider8;
        this.callbackProvider = provider9;
        this.lockPatternUtilsProvider = provider10;
        this.sysuiStatusBarStateControllerProvider = provider11;
        this.configurationControllerProvider = provider12;
        this.keyguardUpdateMonitorProvider = provider13;
        this.dreamOverlayStateControllerProvider = provider14;
        this.navigationModeControllerProvider = provider15;
        this.dockManagerProvider = provider16;
        this.notificationShadeWindowControllerProvider = provider17;
        this.keyguardStateControllerProvider = provider18;
        this.notificationMediaManagerProvider = provider19;
        this.keyguardMessageAreaFactoryProvider = provider20;
        this.sysUIUnfoldComponentProvider = provider21;
        this.shadeControllerProvider = provider22;
        this.latencyTrackerProvider = provider23;
        this.keyguardSecurityModelProvider = provider24;
        this.featureFlagsProvider = provider25;
        this.primaryBouncerCallbackInteractorProvider = provider26;
        this.primaryBouncerInteractorProvider = provider27;
        this.primaryBouncerViewProvider = provider28;
        this.alternateBouncerInteractorProvider = provider29;
        this.udfpsOverlayInteractorProvider = provider30;
        this.activityStarterProvider = provider31;
    }

    public static StatusBarKeyguardViewManager provideStatusBarKeyguardViewManager(KeyguardViewMediatorHelper keyguardViewMediatorHelper, SecRotationWatcher secRotationWatcher, KeyguardFastBioUnlockController keyguardFastBioUnlockController, Lazy lazy, KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerComponent.Factory factory, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils, SysuiStatusBarStateController sysuiStatusBarStateController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, DreamOverlayStateController dreamOverlayStateController, NavigationModeController navigationModeController, DockManager dockManager, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, NotificationMediaManager notificationMediaManager, KeyguardMessageAreaController.Factory factory2, Optional optional, Lazy lazy2, LatencyTracker latencyTracker, KeyguardSecurityModel keyguardSecurityModel, FeatureFlags featureFlags, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, PrimaryBouncerInteractor primaryBouncerInteractor, BouncerView bouncerView, AlternateBouncerInteractor alternateBouncerInteractor, UdfpsOverlayInteractor udfpsOverlayInteractor, ActivityStarter activityStarter) {
        return SafeUIState.isSysUiSafeModeEnabled() ? new SafeUIStatusBarKeyguardViewManager(keyguardBouncerViewModel, factory, primaryBouncerToGoneTransitionViewModel, context, viewMediatorCallback, lockPatternUtils, sysuiStatusBarStateController, configurationController, keyguardUpdateMonitor, dreamOverlayStateController, navigationModeController, dockManager, notificationShadeWindowController, keyguardStateController, notificationMediaManager, factory2, optional, lazy2, latencyTracker, keyguardSecurityModel, featureFlags, primaryBouncerCallbackInteractor, primaryBouncerInteractor, bouncerView, alternateBouncerInteractor, udfpsOverlayInteractor, activityStarter) : new SecStatusBarKeyguardViewManager(keyguardViewMediatorHelper, secRotationWatcher, keyguardFastBioUnlockController, lazy, context, viewMediatorCallback, lockPatternUtils, sysuiStatusBarStateController, configurationController, keyguardUpdateMonitor, dreamOverlayStateController, navigationModeController, dockManager, notificationShadeWindowController, keyguardStateController, notificationMediaManager, factory2, optional, lazy2, latencyTracker, keyguardSecurityModel, featureFlags, primaryBouncerCallbackInteractor, primaryBouncerInteractor, bouncerView, alternateBouncerInteractor, udfpsOverlayInteractor, activityStarter);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideStatusBarKeyguardViewManager((KeyguardViewMediatorHelper) this.keyguardViewMediatorHelperProvider.get(), (SecRotationWatcher) this.rotationWatcherProvider.get(), (KeyguardFastBioUnlockController) this.fastBioUnlockControllerProvider.get(), DoubleCheck.lazy(this.keyguardUnlockAnimationControllerLazyProvider), (KeyguardBouncerViewModel) this.keyguardBouncerViewModelProvider.get(), (KeyguardBouncerComponent.Factory) this.keyguardBouncerComponentFactoryProvider.get(), (PrimaryBouncerToGoneTransitionViewModel) this.primaryBouncerToGoneTransitionViewModelProvider.get(), (Context) this.contextProvider.get(), (ViewMediatorCallback) this.callbackProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (SysuiStatusBarStateController) this.sysuiStatusBarStateControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (DreamOverlayStateController) this.dreamOverlayStateControllerProvider.get(), (NavigationModeController) this.navigationModeControllerProvider.get(), (DockManager) this.dockManagerProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (NotificationMediaManager) this.notificationMediaManagerProvider.get(), (KeyguardMessageAreaController.Factory) this.keyguardMessageAreaFactoryProvider.get(), (Optional) this.sysUIUnfoldComponentProvider.get(), DoubleCheck.lazy(this.shadeControllerProvider), (LatencyTracker) this.latencyTrackerProvider.get(), (KeyguardSecurityModel) this.keyguardSecurityModelProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (PrimaryBouncerCallbackInteractor) this.primaryBouncerCallbackInteractorProvider.get(), (PrimaryBouncerInteractor) this.primaryBouncerInteractorProvider.get(), (BouncerView) this.primaryBouncerViewProvider.get(), (AlternateBouncerInteractor) this.alternateBouncerInteractorProvider.get(), (UdfpsOverlayInteractor) this.udfpsOverlayInteractorProvider.get(), (ActivityStarter) this.activityStarterProvider.get());
    }
}
