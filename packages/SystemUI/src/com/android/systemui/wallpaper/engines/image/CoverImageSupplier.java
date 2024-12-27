package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CoverImageSupplier implements ImageSource.ImageSupplier {
    public final CoverWallpaper mCoverWallpaper;
    public final WallpaperManager mWallpaperManager;

    public CoverImageSupplier(Context context, CoverWallpaper coverWallpaper) {
        this.mWallpaperManager = WallpaperManager.getInstance(context);
        this.mCoverWallpaper = coverWallpaper;
    }

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    public final String getFilterData() {
        return null;
    }

    @Override // com.android.systemui.wallpaper.engines.image.ImageSource.ImageSupplier
    public final ImageSource.WallpaperImage getWallpaperImage() {
        CoverWallpaper coverWallpaper = this.mCoverWallpaper;
        Bitmap wallpaperBitmap = ((CoverWallpaperController) coverWallpaper).getWallpaperBitmap();
        return new ImageSource.WallpaperImage(wallpaperBitmap, IntelligentCropHelper.parseCropHints(((CoverWallpaperController) coverWallpaper).getWallpaperIntelligentCrop()), wallpaperBitmap == null ? false : this.mWallpaperManager.wallpaperSupportsWcg(wallpaperBitmap));
    }
}
