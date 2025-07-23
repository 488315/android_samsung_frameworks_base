package com.android.systemui.pluginlock;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginWallpaperManagerImpl_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider delegateAppProvider;
    private final Provider helperProvider;
    private final Provider mediatorProvider;
    private final Provider monitorProvider;
    private final Provider utilsProvider;

    public PluginWallpaperManagerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.mediatorProvider = provider;
        this.delegateAppProvider = provider2;
        this.helperProvider = provider3;
        this.utilsProvider = provider4;
        this.contextProvider = provider5;
        this.monitorProvider = provider6;
    }

    public static PluginWallpaperManagerImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new PluginWallpaperManagerImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6));
    }

    public static PluginWallpaperManagerImpl newInstance(PluginLockMediator pluginLockMediator, PluginLockDelegateApp pluginLockDelegateApp, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, Context context, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new PluginWallpaperManagerImpl(pluginLockMediator, pluginLockDelegateApp, settingsHelper, pluginLockUtils, context, keyguardUpdateMonitor);
    }

    public static PluginWallpaperManagerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new PluginWallpaperManagerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    @Override // javax.inject.Provider
    public PluginWallpaperManagerImpl get() {
        return newInstance((PluginLockMediator) this.mediatorProvider.get(), (PluginLockDelegateApp) this.delegateAppProvider.get(), (SettingsHelper) this.helperProvider.get(), (PluginLockUtils) this.utilsProvider.get(), (Context) this.contextProvider.get(), (KeyguardUpdateMonitor) this.monitorProvider.get());
    }
}
