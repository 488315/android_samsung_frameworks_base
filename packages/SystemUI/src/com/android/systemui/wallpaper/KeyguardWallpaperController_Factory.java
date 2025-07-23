package com.android.systemui.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class KeyguardWallpaperController_Factory implements Provider {
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider eventHandlerProvider;
    public final Provider foldControllerProvider;
    public final Provider loggerProvider;
    public final Provider pluginLockUtilsProvider;
    public final Provider pluginWallpaperManagerProvider;
    public final Provider selectedUserInteractorProvider;
    public final Provider settingsHelperProvider;
    public final Provider systemWallpaperColorsProvider;
    public final Provider updateMonitorProvider;
    public final Provider wakefulnessLifecycleProvider;
    public final Provider wallpaperChangeNotifierProvider;
    public final Provider wallpaperEventNotifierProvider;
    public final Provider wallpaperManagerProvider;

    public KeyguardWallpaperController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15) {
        this.contextProvider = provider;
        this.wallpaperManagerProvider = provider2;
        this.updateMonitorProvider = provider3;
        this.pluginWallpaperManagerProvider = provider4;
        this.pluginLockUtilsProvider = provider5;
        this.settingsHelperProvider = provider6;
        this.wakefulnessLifecycleProvider = provider7;
        this.loggerProvider = provider8;
        this.wallpaperEventNotifierProvider = provider9;
        this.systemWallpaperColorsProvider = provider10;
        this.configurationControllerProvider = provider11;
        this.foldControllerProvider = provider12;
        this.eventHandlerProvider = provider13;
        this.selectedUserInteractorProvider = provider14;
        this.wallpaperChangeNotifierProvider = provider15;
    }

    public static KeyguardWallpaperController newInstance(Context context, WallpaperManager wallpaperManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PluginWallpaperManager pluginWallpaperManager, PluginLockUtils pluginLockUtils, SettingsHelper settingsHelper, WakefulnessLifecycle wakefulnessLifecycle, WallpaperLogger wallpaperLogger, WallpaperEventNotifier wallpaperEventNotifier, SystemWallpaperColors systemWallpaperColors, ConfigurationController configurationController, KeyguardFoldController keyguardFoldController, KeyguardWallpaperEventHandler keyguardWallpaperEventHandler, SelectedUserInteractor selectedUserInteractor, WallpaperChangeNotifier wallpaperChangeNotifier) {
        return new KeyguardWallpaperController(context, wallpaperManager, keyguardUpdateMonitor, pluginWallpaperManager, pluginLockUtils, settingsHelper, wakefulnessLifecycle, wallpaperLogger, wallpaperEventNotifier, systemWallpaperColors, configurationController, keyguardFoldController, keyguardWallpaperEventHandler, selectedUserInteractor, wallpaperChangeNotifier);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new KeyguardWallpaperController((Context) this.contextProvider.get(), (WallpaperManager) this.wallpaperManagerProvider.get(), (KeyguardUpdateMonitor) this.updateMonitorProvider.get(), (PluginWallpaperManager) this.pluginWallpaperManagerProvider.get(), (PluginLockUtils) this.pluginLockUtilsProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (WallpaperLogger) this.loggerProvider.get(), (WallpaperEventNotifier) this.wallpaperEventNotifierProvider.get(), (SystemWallpaperColors) this.systemWallpaperColorsProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (KeyguardFoldController) this.foldControllerProvider.get(), (KeyguardWallpaperEventHandler) this.eventHandlerProvider.get(), (SelectedUserInteractor) this.selectedUserInteractorProvider.get(), (WallpaperChangeNotifier) this.wallpaperChangeNotifierProvider.get());
    }
}
