package com.android.systemui.pluginlock;

import android.content.Context;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.SettingsHelper;
import dagger.internal.Provider;

public final class PluginLockManagerImpl_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider delegateAppProvider;
    private final javax.inject.Provider delegateSysUiProvider;
    private final javax.inject.Provider desktopManagerProvider;
    private final javax.inject.Provider foldControllerProvider;
    private final javax.inject.Provider helperProvider;
    private final javax.inject.Provider mediatorProvider;
    private final javax.inject.Provider pluginWallpaperManagerProvider;
    private final javax.inject.Provider policyProvider;
    private final javax.inject.Provider utilsProvider;

    public PluginLockManagerImpl_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10) {
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
        return new PluginLockManagerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static PluginLockManagerImpl newInstance(PluginLockMediator pluginLockMediator, PluginLockInstancePolicy pluginLockInstancePolicy, PluginLockDelegateApp pluginLockDelegateApp, PluginLockDelegateSysUi pluginLockDelegateSysUi, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, PluginWallpaperManager pluginWallpaperManager, KeyguardFoldController keyguardFoldController, DesktopManager desktopManager, Context context) {
        return new PluginLockManagerImpl(pluginLockMediator, pluginLockInstancePolicy, pluginLockDelegateApp, pluginLockDelegateSysUi, settingsHelper, pluginLockUtils, pluginWallpaperManager, keyguardFoldController, desktopManager, context);
    }

    @Override // javax.inject.Provider
    public PluginLockManagerImpl get() {
        return newInstance((PluginLockMediator) this.mediatorProvider.get(), (PluginLockInstancePolicy) this.policyProvider.get(), (PluginLockDelegateApp) this.delegateAppProvider.get(), (PluginLockDelegateSysUi) this.delegateSysUiProvider.get(), (SettingsHelper) this.helperProvider.get(), (PluginLockUtils) this.utilsProvider.get(), (PluginWallpaperManager) this.pluginWallpaperManagerProvider.get(), (KeyguardFoldController) this.foldControllerProvider.get(), (DesktopManager) this.desktopManagerProvider.get(), (Context) this.contextProvider.get());
    }
}
