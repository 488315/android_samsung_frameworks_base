package com.android.systemui.keyguardimage;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.KeyguardWallpaperController;

/* loaded from: classes2.dex */
public class WallpaperImageProviderCreator extends WallpaperImageCreator {
    public WallpaperImageProviderCreator(Context context) {
        super("WallpaperImageCreator", context, (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class), (PluginWallpaperManager) Dependency.sDependency.getDependencyInner(PluginWallpaperManager.class), CoverWallpaperController.sInstance, KeyguardWallpaperController.sController);
    }
}
