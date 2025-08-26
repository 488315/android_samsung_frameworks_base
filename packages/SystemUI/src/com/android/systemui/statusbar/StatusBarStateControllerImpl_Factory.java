package com.android.systemui.statusbar;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.kotlin.JavaAdapter;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class StatusBarStateControllerImpl_Factory implements Provider {
    public final Provider alternateBouncerInteractorLazyProvider;
    public final Provider deviceUnlockedInteractorLazyProvider;
    public final Provider javaAdapterProvider;
    public final Provider keyguardClockInteractorLazyProvider;
    public final Provider keyguardInteractorProvider;
    public final Provider keyguardTransitionInteractorProvider;
    public final Provider mLooperSlowLogControllerProvider;
    public final Provider mSettingHelperProvider;
    public final Provider sceneBackInteractorLazyProvider;
    public final Provider sceneContainerOcclusionInteractorProvider;
    public final Provider sceneInteractorLazyProvider;
    public final Provider shadeInteractorLazyProvider;
    public final Provider uiEventLoggerProvider;

    public StatusBarStateControllerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        this.uiEventLoggerProvider = provider;
        this.javaAdapterProvider = provider2;
        this.keyguardInteractorProvider = provider3;
        this.keyguardTransitionInteractorProvider = provider4;
        this.shadeInteractorLazyProvider = provider5;
        this.deviceUnlockedInteractorLazyProvider = provider6;
        this.sceneInteractorLazyProvider = provider7;
        this.sceneContainerOcclusionInteractorProvider = provider8;
        this.keyguardClockInteractorLazyProvider = provider9;
        this.sceneBackInteractorLazyProvider = provider10;
        this.alternateBouncerInteractorLazyProvider = provider11;
        this.mLooperSlowLogControllerProvider = provider12;
        this.mSettingHelperProvider = provider13;
    }

    public static StatusBarStateControllerImpl newInstance(UiEventLogger uiEventLogger, JavaAdapter javaAdapter, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7, Lazy lazy8, Lazy lazy9) {
        return new StatusBarStateControllerImpl(uiEventLogger, javaAdapter, lazy, lazy2, lazy3, lazy4, lazy5, lazy6, lazy7, lazy8, lazy9);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        StatusBarStateControllerImpl statusBarStateControllerImpl = new StatusBarStateControllerImpl((UiEventLogger) this.uiEventLoggerProvider.get(), (JavaAdapter) this.javaAdapterProvider.get(), DoubleCheck.lazy(this.keyguardInteractorProvider), DoubleCheck.lazy(this.keyguardTransitionInteractorProvider), DoubleCheck.lazy(this.shadeInteractorLazyProvider), DoubleCheck.lazy(this.deviceUnlockedInteractorLazyProvider), DoubleCheck.lazy(this.sceneInteractorLazyProvider), DoubleCheck.lazy(this.sceneContainerOcclusionInteractorProvider), DoubleCheck.lazy(this.keyguardClockInteractorLazyProvider), DoubleCheck.lazy(this.sceneBackInteractorLazyProvider), DoubleCheck.lazy(this.alternateBouncerInteractorLazyProvider));
        statusBarStateControllerImpl.mLooperSlowLogController = (LooperSlowLogController) this.mLooperSlowLogControllerProvider.get();
        statusBarStateControllerImpl.mSettingHelper = (SettingsHelper) this.mSettingHelperProvider.get();
        return statusBarStateControllerImpl;
    }
}
