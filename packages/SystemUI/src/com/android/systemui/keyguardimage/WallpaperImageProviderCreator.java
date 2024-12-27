package com.android.systemui.keyguardimage;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.KeyguardWallpaperController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WallpaperImageProviderCreator extends WallpaperImageCreator {
    public WallpaperImageProviderCreator(Context context) {
        super("WallpaperImageCreator", context, (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class), (PluginWallpaperManager) Dependency.sDependency.getDependencyInner(PluginWallpaperManager.class), CoverWallpaperController.sInstance, KeyguardWallpaperController.sController);
    }
}
