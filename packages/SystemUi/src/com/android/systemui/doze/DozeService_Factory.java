package com.android.systemui.doze;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.doze.dagger.DozeComponent;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeService_Factory implements Provider {
    public final Provider dozeComponentBuilderProvider;
    public final Provider mAODAmbientWallpaperHelperProvider;
    public final Provider mDozeServiceHostProvider;
    public final Provider mFaceWidgetManagerLazyProvider;
    public final Provider mKeyguardUpdateMonitorProvider;
    public final Provider mPluginAODManagerLazyProvider;
    public final Provider mWakefulnessLifecycleProvider;
    public final Provider pluginManagerProvider;

    public DozeService_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.dozeComponentBuilderProvider = provider;
        this.pluginManagerProvider = provider2;
        this.mKeyguardUpdateMonitorProvider = provider3;
        this.mPluginAODManagerLazyProvider = provider4;
        this.mFaceWidgetManagerLazyProvider = provider5;
        this.mDozeServiceHostProvider = provider6;
        this.mAODAmbientWallpaperHelperProvider = provider7;
        this.mWakefulnessLifecycleProvider = provider8;
    }

    public static DozeService newInstance(DozeComponent.Builder builder, PluginManager pluginManager) {
        return new DozeService(builder, pluginManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DozeService dozeService = new DozeService((DozeComponent.Builder) this.dozeComponentBuilderProvider.get(), (PluginManager) this.pluginManagerProvider.get());
        dozeService.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) this.mKeyguardUpdateMonitorProvider.get();
        dozeService.mPluginAODManagerLazy = DoubleCheck.lazy(this.mPluginAODManagerLazyProvider);
        dozeService.mFaceWidgetManagerLazy = DoubleCheck.lazy(this.mFaceWidgetManagerLazyProvider);
        dozeService.mDozeServiceHost = (DozeServiceHost) this.mDozeServiceHostProvider.get();
        dozeService.mAODAmbientWallpaperHelper = (AODAmbientWallpaperHelper) this.mAODAmbientWallpaperHelperProvider.get();
        dozeService.mWakefulnessLifecycle = (WakefulnessLifecycle) this.mWakefulnessLifecycleProvider.get();
        return dozeService;
    }
}
