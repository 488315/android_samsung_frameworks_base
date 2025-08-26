package com.android.systemui.doze;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.doze.dagger.DozeComponent;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public final class DozeService_Factory implements Provider {
    public final Provider bgExecutorProvider;
    public final Provider dozeComponentBuilderProvider;
    public final Provider dozeLogProvider;
    public final Provider mAODAmbientWallpaperHelperProvider;
    public final Provider mDozeServiceHostProvider;
    public final Provider mFaceWidgetManagerLazyProvider;
    public final Provider mKeyguardUpdateMonitorProvider;
    public final Provider mPluginAODManagerLazyProvider;
    public final Provider mWakefulnessLifecycleProvider;
    public final Provider pluginManagerProvider;

    public DozeService_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.dozeComponentBuilderProvider = provider;
        this.pluginManagerProvider = provider2;
        this.dozeLogProvider = provider3;
        this.bgExecutorProvider = provider4;
        this.mKeyguardUpdateMonitorProvider = provider5;
        this.mPluginAODManagerLazyProvider = provider6;
        this.mFaceWidgetManagerLazyProvider = provider7;
        this.mDozeServiceHostProvider = provider8;
        this.mAODAmbientWallpaperHelperProvider = provider9;
        this.mWakefulnessLifecycleProvider = provider10;
    }

    public static DozeService newInstance(DaggerReferenceGlobalRootComponent.DozeComponentFactory dozeComponentFactory, PluginManager pluginManager, DozeLog dozeLog, Executor executor) {
        return new DozeService(dozeComponentFactory, pluginManager, dozeLog, executor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DozeService dozeService = new DozeService((DozeComponent.Builder) this.dozeComponentBuilderProvider.get(), (PluginManager) this.pluginManagerProvider.get(), (DozeLog) this.dozeLogProvider.get(), (Executor) this.bgExecutorProvider.get());
        dozeService.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) this.mKeyguardUpdateMonitorProvider.get();
        dozeService.mPluginAODManagerLazy = DoubleCheck.lazy(this.mPluginAODManagerLazyProvider);
        dozeService.mFaceWidgetManagerLazy = DoubleCheck.lazy(this.mFaceWidgetManagerLazyProvider);
        dozeService.mDozeServiceHost = (DozeServiceHost) this.mDozeServiceHostProvider.get();
        dozeService.mAODAmbientWallpaperHelper = (AODAmbientWallpaperHelper) this.mAODAmbientWallpaperHelperProvider.get();
        dozeService.mWakefulnessLifecycle = (WakefulnessLifecycle) this.mWakefulnessLifecycleProvider.get();
        return dozeService;
    }
}
