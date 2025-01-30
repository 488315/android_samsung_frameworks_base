package com.android.systemui.statusbar.phone;

import android.app.AlarmManager;
import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.p009ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import java.util.concurrent.Executor;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScrimController_Factory implements Provider {
    public final Provider alarmManagerProvider;
    public final Provider configurationControllerProvider;
    public final Provider delayedWakeLockBuilderProvider;
    public final Provider dockManagerProvider;
    public final Provider dozeParametersProvider;
    public final Provider featureFlagsProvider;
    public final Provider handlerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardTransitionInteractorProvider;
    public final Provider keyguardUnlockAnimationControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider largeScreenShadeInterpolatorProvider;
    public final Provider lightBarControllerProvider;
    public final Provider mAODAmbientWallpaperHelperProvider;
    public final Provider mSecLsScrimControlHelperProvider;
    public final Provider mainDispatcherProvider;
    public final Provider mainExecutorProvider;
    public final Provider primaryBouncerToGoneTransitionViewModelProvider;
    public final Provider screenOffAnimationControllerProvider;
    public final Provider statusBarKeyguardViewManagerProvider;

    public ScrimController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20) {
        this.lightBarControllerProvider = provider;
        this.dozeParametersProvider = provider2;
        this.alarmManagerProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
        this.delayedWakeLockBuilderProvider = provider5;
        this.handlerProvider = provider6;
        this.keyguardUpdateMonitorProvider = provider7;
        this.dockManagerProvider = provider8;
        this.configurationControllerProvider = provider9;
        this.mainExecutorProvider = provider10;
        this.screenOffAnimationControllerProvider = provider11;
        this.keyguardUnlockAnimationControllerProvider = provider12;
        this.statusBarKeyguardViewManagerProvider = provider13;
        this.primaryBouncerToGoneTransitionViewModelProvider = provider14;
        this.keyguardTransitionInteractorProvider = provider15;
        this.mainDispatcherProvider = provider16;
        this.largeScreenShadeInterpolatorProvider = provider17;
        this.featureFlagsProvider = provider18;
        this.mSecLsScrimControlHelperProvider = provider19;
        this.mAODAmbientWallpaperHelperProvider = provider20;
    }

    public static ScrimController newInstance(LightBarController lightBarController, DozeParameters dozeParameters, AlarmManager alarmManager, KeyguardStateController keyguardStateController, DelayedWakeLock.Builder builder, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, ConfigurationController configurationController, Executor executor, ScreenOffAnimationController screenOffAnimationController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, LargeScreenShadeInterpolator largeScreenShadeInterpolator, FeatureFlags featureFlags) {
        return new ScrimController(lightBarController, dozeParameters, alarmManager, keyguardStateController, builder, handler, keyguardUpdateMonitor, dockManager, configurationController, executor, screenOffAnimationController, keyguardUnlockAnimationController, statusBarKeyguardViewManager, primaryBouncerToGoneTransitionViewModel, keyguardTransitionInteractor, coroutineDispatcher, largeScreenShadeInterpolator, featureFlags);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        ScrimController newInstance = newInstance((LightBarController) this.lightBarControllerProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (AlarmManager) this.alarmManagerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (DelayedWakeLock.Builder) this.delayedWakeLockBuilderProvider.get(), (Handler) this.handlerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (DockManager) this.dockManagerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (Executor) this.mainExecutorProvider.get(), (ScreenOffAnimationController) this.screenOffAnimationControllerProvider.get(), (KeyguardUnlockAnimationController) this.keyguardUnlockAnimationControllerProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (PrimaryBouncerToGoneTransitionViewModel) this.primaryBouncerToGoneTransitionViewModelProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (LargeScreenShadeInterpolator) this.largeScreenShadeInterpolatorProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
        newInstance.mSecLsScrimControlHelper = (SecLsScrimControlHelper) this.mSecLsScrimControlHelperProvider.get();
        newInstance.mAODAmbientWallpaperHelper = (AODAmbientWallpaperHelper) this.mAODAmbientWallpaperHelperProvider.get();
        return newInstance;
    }
}
