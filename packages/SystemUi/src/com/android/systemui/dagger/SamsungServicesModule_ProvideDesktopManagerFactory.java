package com.android.systemui.dagger;

import android.content.Context;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.logging.IndicatorLogger;
import com.android.systemui.util.DesktopManagerImpl;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServicesModule_ProvideDesktopManagerFactory implements Provider {
    public final Provider contextProvider;
    public final Provider customDeviceControlsControllerLazyProvider;
    public final Provider desktopSystemUiBinderLazyProvider;
    public final Provider indicatorLoggerProvider;
    public final Provider keyguardSecurityModelProvider;
    public final Provider updateMonitorProvider;
    public final Provider viewControllerLazyProvider;
    public final Provider wakefulnessLifecycleProvider;

    public SamsungServicesModule_ProvideDesktopManagerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.viewControllerLazyProvider = provider2;
        this.updateMonitorProvider = provider3;
        this.keyguardSecurityModelProvider = provider4;
        this.wakefulnessLifecycleProvider = provider5;
        this.desktopSystemUiBinderLazyProvider = provider6;
        this.indicatorLoggerProvider = provider7;
        this.customDeviceControlsControllerLazyProvider = provider8;
    }

    public static DesktopManagerImpl provideDesktopManager(Context context, Lazy lazy, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, WakefulnessLifecycle wakefulnessLifecycle, Lazy lazy2, IndicatorLogger indicatorLogger, Lazy lazy3) {
        return new DesktopManagerImpl(context, lazy, keyguardUpdateMonitor, keyguardSecurityModel, wakefulnessLifecycle, lazy2, indicatorLogger, lazy3);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideDesktopManager((Context) this.contextProvider.get(), DoubleCheck.lazy(this.viewControllerLazyProvider), (KeyguardUpdateMonitor) this.updateMonitorProvider.get(), (KeyguardSecurityModel) this.keyguardSecurityModelProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), DoubleCheck.lazy(this.desktopSystemUiBinderLazyProvider), (IndicatorLogger) this.indicatorLoggerProvider.get(), DoubleCheck.lazy(this.customDeviceControlsControllerLazyProvider));
    }
}
