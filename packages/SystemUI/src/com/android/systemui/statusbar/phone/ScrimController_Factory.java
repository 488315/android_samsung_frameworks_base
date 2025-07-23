package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Handler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.dock.DockManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerToGoneTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import java.util.concurrent.Executor;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ScrimController_Factory implements Provider {
    public final Provider alternateBouncerToGoneTransitionViewModelProvider;
    public final Provider blurConfigProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider delayedWakeLockFactoryProvider;
    public final Provider dockManagerProvider;
    public final Provider dozeParametersProvider;
    public final Provider handlerProvider;
    public final Provider javaAdapterProvider;
    public final Provider keyguardInteractorProvider;
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
    public final Provider windowRootViewBlurInteractorProvider;

    public ScrimController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24) {
        this.lightBarControllerProvider = provider;
        this.dozeParametersProvider = provider2;
        this.keyguardStateControllerProvider = provider3;
        this.delayedWakeLockFactoryProvider = provider4;
        this.handlerProvider = provider5;
        this.keyguardUpdateMonitorProvider = provider6;
        this.dockManagerProvider = provider7;
        this.configurationControllerProvider = provider8;
        this.mainExecutorProvider = provider9;
        this.javaAdapterProvider = provider10;
        this.screenOffAnimationControllerProvider = provider11;
        this.keyguardUnlockAnimationControllerProvider = provider12;
        this.statusBarKeyguardViewManagerProvider = provider13;
        this.primaryBouncerToGoneTransitionViewModelProvider = provider14;
        this.alternateBouncerToGoneTransitionViewModelProvider = provider15;
        this.keyguardTransitionInteractorProvider = provider16;
        this.keyguardInteractorProvider = provider17;
        this.mainDispatcherProvider = provider18;
        this.largeScreenShadeInterpolatorProvider = provider19;
        this.blurConfigProvider = provider20;
        this.contextProvider = provider21;
        this.windowRootViewBlurInteractorProvider = provider22;
        this.mSecLsScrimControlHelperProvider = provider23;
        this.mAODAmbientWallpaperHelperProvider = provider24;
    }

    public static ScrimController newInstance(LightBarController lightBarController, DozeParameters dozeParameters, KeyguardStateController keyguardStateController, DelayedWakeLock.Factory factory, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, ConfigurationController configurationController, Executor executor, JavaAdapter javaAdapter, ScreenOffAnimationController screenOffAnimationController, KeyguardUnlockAnimationController keyguardUnlockAnimationController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel, AlternateBouncerToGoneTransitionViewModel alternateBouncerToGoneTransitionViewModel, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, CoroutineDispatcher coroutineDispatcher, LargeScreenShadeInterpolator largeScreenShadeInterpolator, BlurConfig blurConfig, Context context, Lazy lazy) {
        return new ScrimController(lightBarController, dozeParameters, keyguardStateController, factory, handler, keyguardUpdateMonitor, dockManager, configurationController, executor, javaAdapter, screenOffAnimationController, keyguardUnlockAnimationController, statusBarKeyguardViewManager, primaryBouncerToGoneTransitionViewModel, alternateBouncerToGoneTransitionViewModel, keyguardTransitionInteractor, keyguardInteractor, coroutineDispatcher, largeScreenShadeInterpolator, blurConfig, context, lazy);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        ScrimController scrimController = new ScrimController((LightBarController) this.lightBarControllerProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (DelayedWakeLock.Factory) this.delayedWakeLockFactoryProvider.get(), (Handler) this.handlerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (DockManager) this.dockManagerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (Executor) this.mainExecutorProvider.get(), (JavaAdapter) this.javaAdapterProvider.get(), (ScreenOffAnimationController) this.screenOffAnimationControllerProvider.get(), (KeyguardUnlockAnimationController) this.keyguardUnlockAnimationControllerProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (PrimaryBouncerToGoneTransitionViewModel) this.primaryBouncerToGoneTransitionViewModelProvider.get(), (AlternateBouncerToGoneTransitionViewModel) this.alternateBouncerToGoneTransitionViewModelProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get(), (KeyguardInteractor) this.keyguardInteractorProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (LargeScreenShadeInterpolator) this.largeScreenShadeInterpolatorProvider.get(), (BlurConfig) this.blurConfigProvider.get(), (Context) this.contextProvider.get(), DoubleCheck.lazy(this.windowRootViewBlurInteractorProvider));
        scrimController.mSecLsScrimControlHelper = (SecLsScrimControlHelper) this.mSecLsScrimControlHelperProvider.get();
        scrimController.mAODAmbientWallpaperHelper = (AODAmbientWallpaperHelper) this.mAODAmbientWallpaperHelperProvider.get();
        return scrimController;
    }
}
