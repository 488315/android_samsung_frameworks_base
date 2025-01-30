package com.android.systemui.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.pluginlock.PluginLockUtils;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardWallpaperController_Factory implements Provider {
    public final Provider configurationControllerProvider;
    public final Provider contextProvider;
    public final Provider dozeParametersProvider;
    public final Provider eventHandlerProvider;
    public final Provider foldControllerProvider;
    public final Provider loggerProvider;
    public final Provider observerProvider;
    public final Provider pluginLockUtilsProvider;
    public final Provider pluginWallpaperManagerProvider;
    public final Provider settingsHelperProvider;
    public final Provider systemWallpaperColorsProvider;
    public final Provider updateMonitorProvider;
    public final Provider wakefulnessLifecycleProvider;
    public final Provider wallpaperChangeNotifierProvider;
    public final Provider wallpaperEventNotifierProvider;
    public final Provider wallpaperManagerProvider;

    public KeyguardWallpaperController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
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
        this.dozeParametersProvider = provider11;
        this.configurationControllerProvider = provider12;
        this.foldControllerProvider = provider13;
        this.observerProvider = provider14;
        this.eventHandlerProvider = provider15;
        this.wallpaperChangeNotifierProvider = provider16;
    }

    public static KeyguardWallpaperController newInstance(Context context, WallpaperManager wallpaperManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PluginWallpaperManager pluginWallpaperManager, PluginLockUtils pluginLockUtils, SettingsHelper settingsHelper, WakefulnessLifecycle wakefulnessLifecycle, WallpaperLogger wallpaperLogger, WallpaperEventNotifier wallpaperEventNotifier, SystemWallpaperColors systemWallpaperColors, DozeParameters dozeParameters, ConfigurationController configurationController, KeyguardFoldController keyguardFoldController, WallpaperChangeObserver wallpaperChangeObserver, KeyguardWallpaperEventHandler keyguardWallpaperEventHandler, WallpaperChangeNotifier wallpaperChangeNotifier) {
        return new KeyguardWallpaperController(context, wallpaperManager, keyguardUpdateMonitor, pluginWallpaperManager, pluginLockUtils, settingsHelper, wakefulnessLifecycle, wallpaperLogger, wallpaperEventNotifier, systemWallpaperColors, dozeParameters, configurationController, keyguardFoldController, wallpaperChangeObserver, keyguardWallpaperEventHandler, wallpaperChangeNotifier);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((Context) this.contextProvider.get(), (WallpaperManager) this.wallpaperManagerProvider.get(), (KeyguardUpdateMonitor) this.updateMonitorProvider.get(), (PluginWallpaperManager) this.pluginWallpaperManagerProvider.get(), (PluginLockUtils) this.pluginLockUtilsProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (WallpaperLogger) this.loggerProvider.get(), (WallpaperEventNotifier) this.wallpaperEventNotifierProvider.get(), (SystemWallpaperColors) this.systemWallpaperColorsProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (KeyguardFoldController) this.foldControllerProvider.get(), (WallpaperChangeObserver) this.observerProvider.get(), (KeyguardWallpaperEventHandler) this.eventHandlerProvider.get(), (WallpaperChangeNotifier) this.wallpaperChangeNotifierProvider.get());
    }
}
