package com.android.systemui.wallpaper.engines.image;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CoverImageSupplier implements ImageSource.ImageSupplier {
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
