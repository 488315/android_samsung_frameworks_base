package com.android.systemui.pluginlock;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;

public final class PluginWallpaperManagerImpl_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider delegateAppProvider;
    private final javax.inject.Provider helperProvider;
    private final javax.inject.Provider mediatorProvider;
    private final javax.inject.Provider monitorProvider;
    private final javax.inject.Provider utilsProvider;

    public PluginWallpaperManagerImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        this.mediatorProvider = provider;
        this.delegateAppProvider = provider2;
        this.helperProvider = provider3;
        this.utilsProvider = provider4;
        this.contextProvider = provider5;
        this.monitorProvider = provider6;
    }

    public static PluginWallpaperManagerImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6) {
        return new PluginWallpaperManagerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static PluginWallpaperManagerImpl newInstance(PluginLockMediator pluginLockMediator, PluginLockDelegateApp pluginLockDelegateApp, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, Context context, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new PluginWallpaperManagerImpl(pluginLockMediator, pluginLockDelegateApp, settingsHelper, pluginLockUtils, context, keyguardUpdateMonitor);
    }

    @Override // javax.inject.Provider
    public PluginWallpaperManagerImpl get() {
        return newInstance((PluginLockMediator) this.mediatorProvider.get(), (PluginLockDelegateApp) this.delegateAppProvider.get(), (SettingsHelper) this.helperProvider.get(), (PluginLockUtils) this.utilsProvider.get(), (Context) this.contextProvider.get(), (KeyguardUpdateMonitor) this.monitorProvider.get());
    }
}
