package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.wallpaper.PluginWallpaper;
import com.android.systemui.wallpaper.PluginWallpaperController;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;

public final class PluginImageSupplier implements ImageSource.ImageSupplier {
    public final PluginWallpaper mPluginWallpaper;
    public final WallpaperManager mWallpaperManager;
    public final int mWhich;

    public PluginImageSupplier(Context context, PluginWallpaper pluginWallpaper, int i) {
        this.mWallpaperManager = WallpaperManager.getInstance(context);
        this.mPluginWallpaper = pluginWallpaper;
        this.mWhich = i;
    }

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    public final String getFilterData() {
        return null;
    }

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    public final ImageSource.WallpaperImage getWallpaperImage() {
        PluginWallpaperController pluginWallpaperController = (PluginWallpaperController) this.mPluginWallpaper;
        int i = this.mWhich;
        Bitmap wallpaperBitmap = pluginWallpaperController.getWallpaperBitmap(i);
        pluginWallpaperController.getClass();
        int screen = PluginWallpaperController.getScreen(i);
        PluginWallpaperManager pluginWallpaperManager = pluginWallpaperController.mPluginWallpaperManager;
        return new ImageSource.WallpaperImage(wallpaperBitmap, IntelligentCropHelper.parseCropHints(pluginWallpaperManager.isFbeAvailable(screen) ? pluginWallpaperManager.getFbeWallpaperIntelligentCrop(screen) : pluginWallpaperManager.getWallpaperIntelligentCrop(screen)), wallpaperBitmap == null ? false : this.mWallpaperManager.wallpaperSupportsWcg(wallpaperBitmap));
    }
}
