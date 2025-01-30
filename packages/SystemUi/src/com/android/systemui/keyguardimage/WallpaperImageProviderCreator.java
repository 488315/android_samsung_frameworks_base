package com.android.systemui.keyguardimage;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.KeyguardWallpaperController;
import com.android.systemui.wallpaper.WallpaperChangeObserver;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WallpaperImageProviderCreator extends WallpaperImageCreator {
    public WallpaperImageProviderCreator(Context context) {
        super("WallpaperImageCreator", context, (SettingsHelper) Dependency.get(SettingsHelper.class), (PluginWallpaperManager) Dependency.get(PluginWallpaperManager.class), CoverWallpaperController.sInstance, KeyguardWallpaperController.sController, new WallpaperChangeObserver());
    }
}
