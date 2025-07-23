package com.android.systemui.wallpaper.engines.gif;

import android.app.WallpaperManager;
import android.content.Context;
import android.util.Log;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.utils.WhichChecker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class GifSource {
    public final String TAG;
    public final String mGifPath;

    public GifSource(Context context, int i, CoverWallpaper coverWallpaper) {
        this.mGifPath = "";
        this.TAG = ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[GifSource]");
        if (WhichChecker.isWatchFace(i) || WhichChecker.isVirtualDisplay(i)) {
            CoverWallpaperController coverWallpaperController = (CoverWallpaperController) coverWallpaper;
            if (coverWallpaperController.isCoverWallpaperRequired()) {
                this.mGifPath = coverWallpaperController.getWallpaperPath();
                return;
            }
        }
        try {
            this.mGifPath = WallpaperManager.getInstance(context).semGetUri(i).getPath();
        } catch (Exception e) {
            Log.e(this.TAG, "getGifPath: " + e, e);
        }
    }
}
