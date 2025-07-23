package com.android.systemui.pluginlock;

import android.content.Context;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PluginLockManagerImpl_Factory implements Provider {
    private final Provider contextProvider;
    private final Provider delegateAppProvider;
    private final Provider delegateSysUiProvider;
    private final Provider desktopManagerProvider;
    private final Provider foldControllerProvider;
    private final Provider helperProvider;
    private final Provider mediatorProvider;
    private final Provider pluginWallpaperManagerProvider;
    private final Provider policyProvider;
    private final Provider utilsProvider;

    public PluginLockManagerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.mediatorProvider = provider;
        this.policyProvider = provider2;
        this.delegateAppProvider = provider3;
        this.delegateSysUiProvider = provider4;
        this.helperProvider = provider5;
        this.utilsProvider = provider6;
        this.pluginWallpaperManagerProvider = provider7;
        this.foldControllerProvider = provider8;
        this.desktopManagerProvider = provider9;
        this.contextProvider = provider10;
    }

    public static PluginLockManagerImpl_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10) {
        return new PluginLockManagerImpl_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9), Providers.asDaggerProvider(provider10));
    }

    public static PluginLockManagerImpl newInstance(PluginLockMediator pluginLockMediator, PluginLockInstancePolicy pluginLockInstancePolicy, PluginLockDelegateApp pluginLockDelegateApp, PluginLockDelegateSysUi pluginLockDelegateSysUi, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, PluginWallpaperManager pluginWallpaperManager, KeyguardFoldController keyguardFoldController, DesktopManager desktopManager, Context context) {
        return new PluginLockManagerImpl(pluginLockMediator, pluginLockInstancePolicy, pluginLockDelegateApp, pluginLockDelegateSysUi, settingsHelper, pluginLockUtils, pluginWallpaperManager, keyguardFoldController, desktopManager, context);
    }

    public static PluginLockManagerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        return new PluginLockManagerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    @Override // javax.inject.Provider
    public PluginLockManagerImpl get() {
        return newInstance((PluginLockMediator) this.mediatorProvider.get(), (PluginLockInstancePolicy) this.policyProvider.get(), (PluginLockDelegateApp) this.delegateAppProvider.get(), (PluginLockDelegateSysUi) this.delegateSysUiProvider.get(), (SettingsHelper) this.helperProvider.get(), (PluginLockUtils) this.utilsProvider.get(), (PluginWallpaperManager) this.pluginWallpaperManagerProvider.get(), (KeyguardFoldController) this.foldControllerProvider.get(), (DesktopManager) this.desktopManagerProvider.get(), (Context) this.contextProvider.get());
    }
}
