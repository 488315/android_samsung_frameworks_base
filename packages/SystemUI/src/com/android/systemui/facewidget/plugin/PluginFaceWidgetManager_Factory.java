package com.android.systemui.facewidget.plugin;

import android.content.Context;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.blur.SecQpBlurController;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.IndicatorGardenPresenter;
import com.android.systemui.statusbar.phone.KeyguardClockPositionAlgorithm;
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.wallpaper.KeyguardWallpaper;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class PluginFaceWidgetManager_Factory implements Provider {
    public final Provider bootAnimationFinishedCacheProvider;
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider dozeParametersProvider;
    public final Provider externalClockProvider;
    public final Provider faceWidgetContainerWrapperProvider;
    public final Provider faceWidgetDisplayLifeCycleWrapperProvider;
    public final Provider faceWidgetKeyguardStatusCallbackWrapperProvider;
    public final Provider faceWidgetKeyguardUpdateMonitorWrapperProvider;
    public final Provider faceWidgetKnoxStateMonitorWrapperProvider;
    public final Provider faceWidgetLockPatternUtilsWrapperProvider;
    public final Provider faceWidgetLockscreenShadeTransitionControllerWrapperProvider;
    public final Provider faceWidgetNotificationControllerWrapperProvider;
    public final Provider faceWidgetPluginLockManagerWrapperProvider;
    public final Provider faceWidgetWakefulnessLifecycleWrapperProvider;
    public final Provider faceWidgetWallpaperUtilsWrapperProvider;
    public final Provider foldControllerProvider;
    public final Provider indicatorGardenPresenterProvider;
    public final Provider javaAdapterProvider;
    public final Provider keyguardEditModeControllerProvider;
    public final Provider keyguardFastBioUnlockControllerProvider;
    public final Provider keyguardInteractorProvider;
    public final Provider keyguardStatusBarNioLayoutRepositoryProvider;
    public final Provider keyguardStatusViewAlphaChangeControllerWrapperProvider;
    public final Provider keyguardWallpaperProvider;
    public final Provider mBatteryMeterViewControllerFactoryProvider;
    public final Provider mediaDataManagerProvider;
    public final Provider mediaDataRepositoryProvider;
    public final Provider mediaOutputControllerProvider;
    public final Provider pluginAODManagerLazyProvider;
    public final Provider pluginManagerProvider;
    public final Provider positionAlgorithmProvider;
    public final Provider secQpBlurControllerProvider;
    public final Provider selectedUserInteractorProvider;
    public final Provider settingsHelperProvider;
    public final Provider soundCraftControllerProvider;

    public PluginFaceWidgetManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35, Provider provider36) {
        this.contextProvider = provider;
        this.pluginManagerProvider = provider2;
        this.foldControllerProvider = provider3;
        this.positionAlgorithmProvider = provider4;
        this.faceWidgetContainerWrapperProvider = provider5;
        this.faceWidgetKeyguardStatusCallbackWrapperProvider = provider6;
        this.faceWidgetKeyguardUpdateMonitorWrapperProvider = provider7;
        this.faceWidgetDisplayLifeCycleWrapperProvider = provider8;
        this.faceWidgetWakefulnessLifecycleWrapperProvider = provider9;
        this.faceWidgetKnoxStateMonitorWrapperProvider = provider10;
        this.faceWidgetLockPatternUtilsWrapperProvider = provider11;
        this.faceWidgetWallpaperUtilsWrapperProvider = provider12;
        this.faceWidgetPluginLockManagerWrapperProvider = provider13;
        this.faceWidgetNotificationControllerWrapperProvider = provider14;
        this.faceWidgetLockscreenShadeTransitionControllerWrapperProvider = provider15;
        this.externalClockProvider = provider16;
        this.keyguardFastBioUnlockControllerProvider = provider17;
        this.pluginAODManagerLazyProvider = provider18;
        this.mediaDataManagerProvider = provider19;
        this.bootAnimationFinishedCacheProvider = provider20;
        this.keyguardWallpaperProvider = provider21;
        this.keyguardEditModeControllerProvider = provider22;
        this.dozeParametersProvider = provider23;
        this.keyguardStatusViewAlphaChangeControllerWrapperProvider = provider24;
        this.indicatorGardenPresenterProvider = provider25;
        this.mediaDataRepositoryProvider = provider26;
        this.mediaOutputControllerProvider = provider27;
        this.soundCraftControllerProvider = provider28;
        this.secQpBlurControllerProvider = provider29;
        this.configurationControllerProvider = provider30;
        this.keyguardStatusBarNioLayoutRepositoryProvider = provider31;
        this.settingsHelperProvider = provider32;
        this.javaAdapterProvider = provider33;
        this.selectedUserInteractorProvider = provider34;
        this.keyguardInteractorProvider = provider35;
        this.mBatteryMeterViewControllerFactoryProvider = provider36;
    }

    public static PluginFaceWidgetManager newInstance(Context context, PluginManager pluginManager, KeyguardFoldController keyguardFoldController, KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm, FaceWidgetContainerWrapper faceWidgetContainerWrapper, FaceWidgetKeyguardStatusCallbackWrapper faceWidgetKeyguardStatusCallbackWrapper, FaceWidgetKeyguardUpdateMonitorWrapper faceWidgetKeyguardUpdateMonitorWrapper, FaceWidgetDisplayLifeCycleWrapper faceWidgetDisplayLifeCycleWrapper, FaceWidgetWakefulnessLifecycleWrapper faceWidgetWakefulnessLifecycleWrapper, FaceWidgetKnoxStateMonitorWrapper faceWidgetKnoxStateMonitorWrapper, FaceWidgetLockPatternUtilsWrapper faceWidgetLockPatternUtilsWrapper, FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper, FaceWidgetPluginLockManagerWrapper faceWidgetPluginLockManagerWrapper, FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper, FaceWidgetLockscreenShadeTransitionControllerWrapper faceWidgetLockscreenShadeTransitionControllerWrapper, ExternalClockProvider externalClockProvider, KeyguardFastBioUnlockController keyguardFastBioUnlockController, Lazy lazy, MediaDataManager mediaDataManager, BootAnimationFinishedCache bootAnimationFinishedCache, KeyguardWallpaper keyguardWallpaper, KeyguardEditModeController keyguardEditModeController, DozeParameters dozeParameters, KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper, IndicatorGardenPresenter indicatorGardenPresenter, MediaDataRepository mediaDataRepository, Provider provider, Provider provider2, SecQpBlurController secQpBlurController, ConfigurationController configurationController, KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository, Provider provider3, JavaAdapter javaAdapter, SelectedUserInteractor selectedUserInteractor, KeyguardInteractor keyguardInteractor) {
        return new PluginFaceWidgetManager(context, pluginManager, keyguardFoldController, keyguardClockPositionAlgorithm, faceWidgetContainerWrapper, faceWidgetKeyguardStatusCallbackWrapper, faceWidgetKeyguardUpdateMonitorWrapper, faceWidgetDisplayLifeCycleWrapper, faceWidgetWakefulnessLifecycleWrapper, faceWidgetKnoxStateMonitorWrapper, faceWidgetLockPatternUtilsWrapper, faceWidgetWallpaperUtilsWrapper, faceWidgetPluginLockManagerWrapper, faceWidgetNotificationControllerWrapper, faceWidgetLockscreenShadeTransitionControllerWrapper, externalClockProvider, keyguardFastBioUnlockController, lazy, mediaDataManager, bootAnimationFinishedCache, keyguardWallpaper, keyguardEditModeController, dozeParameters, keyguardStatusViewAlphaChangeControllerWrapper, indicatorGardenPresenter, mediaDataRepository, provider, provider2, secQpBlurController, configurationController, keyguardStatusBarNioLayoutRepository, provider3, javaAdapter, selectedUserInteractor, keyguardInteractor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        Context context = (Context) this.contextProvider.get();
        PluginManager pluginManager = (PluginManager) this.pluginManagerProvider.get();
        KeyguardFoldController keyguardFoldController = (KeyguardFoldController) this.foldControllerProvider.get();
        KeyguardClockPositionAlgorithm keyguardClockPositionAlgorithm = (KeyguardClockPositionAlgorithm) this.positionAlgorithmProvider.get();
        FaceWidgetContainerWrapper faceWidgetContainerWrapper = (FaceWidgetContainerWrapper) this.faceWidgetContainerWrapperProvider.get();
        FaceWidgetKeyguardStatusCallbackWrapper faceWidgetKeyguardStatusCallbackWrapper = (FaceWidgetKeyguardStatusCallbackWrapper) this.faceWidgetKeyguardStatusCallbackWrapperProvider.get();
        FaceWidgetKeyguardUpdateMonitorWrapper faceWidgetKeyguardUpdateMonitorWrapper = (FaceWidgetKeyguardUpdateMonitorWrapper) this.faceWidgetKeyguardUpdateMonitorWrapperProvider.get();
        FaceWidgetDisplayLifeCycleWrapper faceWidgetDisplayLifeCycleWrapper = (FaceWidgetDisplayLifeCycleWrapper) this.faceWidgetDisplayLifeCycleWrapperProvider.get();
        FaceWidgetWakefulnessLifecycleWrapper faceWidgetWakefulnessLifecycleWrapper = (FaceWidgetWakefulnessLifecycleWrapper) this.faceWidgetWakefulnessLifecycleWrapperProvider.get();
        FaceWidgetKnoxStateMonitorWrapper faceWidgetKnoxStateMonitorWrapper = (FaceWidgetKnoxStateMonitorWrapper) this.faceWidgetKnoxStateMonitorWrapperProvider.get();
        FaceWidgetLockPatternUtilsWrapper faceWidgetLockPatternUtilsWrapper = (FaceWidgetLockPatternUtilsWrapper) this.faceWidgetLockPatternUtilsWrapperProvider.get();
        FaceWidgetWallpaperUtilsWrapper faceWidgetWallpaperUtilsWrapper = (FaceWidgetWallpaperUtilsWrapper) this.faceWidgetWallpaperUtilsWrapperProvider.get();
        FaceWidgetPluginLockManagerWrapper faceWidgetPluginLockManagerWrapper = (FaceWidgetPluginLockManagerWrapper) this.faceWidgetPluginLockManagerWrapperProvider.get();
        FaceWidgetNotificationControllerWrapper faceWidgetNotificationControllerWrapper = (FaceWidgetNotificationControllerWrapper) this.faceWidgetNotificationControllerWrapperProvider.get();
        FaceWidgetLockscreenShadeTransitionControllerWrapper faceWidgetLockscreenShadeTransitionControllerWrapper = (FaceWidgetLockscreenShadeTransitionControllerWrapper) this.faceWidgetLockscreenShadeTransitionControllerWrapperProvider.get();
        ExternalClockProvider externalClockProvider = (ExternalClockProvider) this.externalClockProvider.get();
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = (KeyguardFastBioUnlockController) this.keyguardFastBioUnlockControllerProvider.get();
        Lazy lazy = DoubleCheck.lazy(this.pluginAODManagerLazyProvider);
        MediaDataManager mediaDataManager = (MediaDataManager) this.mediaDataManagerProvider.get();
        BootAnimationFinishedCache bootAnimationFinishedCache = (BootAnimationFinishedCache) this.bootAnimationFinishedCacheProvider.get();
        KeyguardWallpaper keyguardWallpaper = (KeyguardWallpaper) this.keyguardWallpaperProvider.get();
        KeyguardEditModeController keyguardEditModeController = (KeyguardEditModeController) this.keyguardEditModeControllerProvider.get();
        DozeParameters dozeParameters = (DozeParameters) this.dozeParametersProvider.get();
        KeyguardStatusViewAlphaChangeControllerWrapper keyguardStatusViewAlphaChangeControllerWrapper = (KeyguardStatusViewAlphaChangeControllerWrapper) this.keyguardStatusViewAlphaChangeControllerWrapperProvider.get();
        IndicatorGardenPresenter indicatorGardenPresenter = (IndicatorGardenPresenter) this.indicatorGardenPresenterProvider.get();
        MediaDataRepository mediaDataRepository = (MediaDataRepository) this.mediaDataRepositoryProvider.get();
        SecQpBlurController secQpBlurController = (SecQpBlurController) this.secQpBlurControllerProvider.get();
        ConfigurationController configurationController = (ConfigurationController) this.configurationControllerProvider.get();
        KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository = (KeyguardStatusBarNioLayoutRepository) this.keyguardStatusBarNioLayoutRepositoryProvider.get();
        JavaAdapter javaAdapter = (JavaAdapter) this.javaAdapterProvider.get();
        SelectedUserInteractor selectedUserInteractor = (SelectedUserInteractor) this.selectedUserInteractorProvider.get();
        KeyguardInteractor keyguardInteractor = (KeyguardInteractor) this.keyguardInteractorProvider.get();
        PluginFaceWidgetManager pluginFaceWidgetManager = new PluginFaceWidgetManager(context, pluginManager, keyguardFoldController, keyguardClockPositionAlgorithm, faceWidgetContainerWrapper, faceWidgetKeyguardStatusCallbackWrapper, faceWidgetKeyguardUpdateMonitorWrapper, faceWidgetDisplayLifeCycleWrapper, faceWidgetWakefulnessLifecycleWrapper, faceWidgetKnoxStateMonitorWrapper, faceWidgetLockPatternUtilsWrapper, faceWidgetWallpaperUtilsWrapper, faceWidgetPluginLockManagerWrapper, faceWidgetNotificationControllerWrapper, faceWidgetLockscreenShadeTransitionControllerWrapper, externalClockProvider, keyguardFastBioUnlockController, lazy, mediaDataManager, bootAnimationFinishedCache, keyguardWallpaper, keyguardEditModeController, dozeParameters, keyguardStatusViewAlphaChangeControllerWrapper, indicatorGardenPresenter, mediaDataRepository, this.mediaOutputControllerProvider, this.soundCraftControllerProvider, secQpBlurController, configurationController, keyguardStatusBarNioLayoutRepository, this.settingsHelperProvider, javaAdapter, selectedUserInteractor, keyguardInteractor);
        pluginFaceWidgetManager.mBatteryMeterViewControllerFactory = (BatteryMeterViewController.Factory) this.mBatteryMeterViewControllerFactoryProvider.get();
        return pluginFaceWidgetManager;
    }
}
