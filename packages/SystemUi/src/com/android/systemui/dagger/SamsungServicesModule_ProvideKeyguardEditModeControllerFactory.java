package com.android.systemui.dagger;

import android.view.WindowManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguardimage.WallpaperImageInjectCreator;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.util.SettingsHelper;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SamsungServicesModule_ProvideKeyguardEditModeControllerFactory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider bgExecutorProvider;
    public final Provider displayLifecycleProvider;
    public final Provider executorProvider;
    public final Provider pluginWallpaperManagerProvider;
    public final Provider settingsHelperProvider;
    public final Provider updateMonitorProvider;
    public final Provider wakefulnessLifecycleProvider;
    public final Provider wallpaperImageInjectCreatorProvider;
    public final Provider windowManagerProvider;

    public SamsungServicesModule_ProvideKeyguardEditModeControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10) {
        this.executorProvider = provider;
        this.bgExecutorProvider = provider2;
        this.wallpaperImageInjectCreatorProvider = provider3;
        this.pluginWallpaperManagerProvider = provider4;
        this.wakefulnessLifecycleProvider = provider5;
        this.settingsHelperProvider = provider6;
        this.updateMonitorProvider = provider7;
        this.displayLifecycleProvider = provider8;
        this.activityStarterProvider = provider9;
        this.windowManagerProvider = provider10;
    }

    public static KeyguardEditModeControllerImpl provideKeyguardEditModeController(Executor executor, Executor executor2, WallpaperImageInjectCreator wallpaperImageInjectCreator, PluginWallpaperManager pluginWallpaperManager, WakefulnessLifecycle wakefulnessLifecycle, SettingsHelper settingsHelper, KeyguardUpdateMonitor keyguardUpdateMonitor, DisplayLifecycle displayLifecycle, ActivityStarter activityStarter, WindowManager windowManager) {
        return new KeyguardEditModeControllerImpl(false, executor, executor2, wallpaperImageInjectCreator, pluginWallpaperManager, wakefulnessLifecycle, settingsHelper, keyguardUpdateMonitor, displayLifecycle, activityStarter, windowManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideKeyguardEditModeController((Executor) this.executorProvider.get(), (Executor) this.bgExecutorProvider.get(), (WallpaperImageInjectCreator) this.wallpaperImageInjectCreatorProvider.get(), (PluginWallpaperManager) this.pluginWallpaperManagerProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (SettingsHelper) this.settingsHelperProvider.get(), (KeyguardUpdateMonitor) this.updateMonitorProvider.get(), (DisplayLifecycle) this.displayLifecycleProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (WindowManager) this.windowManagerProvider.get());
    }
}
