package com.android.systemui.dagger;

import com.android.systemui.util.DisabledDesktopManager;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class SamsungServicesModule_ProvideDesktopManagerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider desktopSystemUiBinderLazyProvider;
    public final Provider indicatorLoggerProvider;
    public final Provider secDeviceControlsControllerLazyProvider;
    public final Provider viewControllerLazyProvider;
    public final Provider wakefulnessLifecycleProvider;

    public SamsungServicesModule_ProvideDesktopManagerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.contextProvider = provider;
        this.viewControllerLazyProvider = provider2;
        this.wakefulnessLifecycleProvider = provider3;
        this.desktopSystemUiBinderLazyProvider = provider4;
        this.indicatorLoggerProvider = provider5;
        this.secDeviceControlsControllerLazyProvider = provider6;
    }

    public static DisabledDesktopManager provideDesktopManager() {
        return new DisabledDesktopManager();
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DoubleCheck.lazy(this.viewControllerLazyProvider);
        DoubleCheck.lazy(this.desktopSystemUiBinderLazyProvider);
        DoubleCheck.lazy(this.secDeviceControlsControllerLazyProvider);
        return new DisabledDesktopManager();
    }
}
