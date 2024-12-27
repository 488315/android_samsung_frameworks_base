package com.android.systemui.wallpaper.engines.gif;

import android.app.WallpaperManager;
import android.content.Context;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.engines.WallpaperSource;
import com.android.systemui.wallpaper.utils.WhichChecker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GifSource implements WallpaperSource {
    public final String TAG;
    public final Context mContext;
    public final CoverWallpaper mCoverWallpaper;
    public final int mWhich;

    public GifSource(Context context, int i, CoverWallpaper coverWallpaper) {
        this.TAG = LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "ImageWallpaper_", "[GifSource]");
        this.mCoverWallpaper = coverWallpaper;
        this.mWhich = i;
        this.mContext = context;
    }

    public final String getGifPath() {
        String path;
        int i = this.mWhich;
        boolean isWatchFace = WhichChecker.isWatchFace(i);
        String str = this.TAG;
        if (isWatchFace || WhichChecker.isVirtualDisplay(i)) {
            CoverWallpaper coverWallpaper = this.mCoverWallpaper;
            if (((CoverWallpaperController) coverWallpaper).isCoverWallpaperRequired()) {
                path = ((CoverWallpaperController) coverWallpaper).getWallpaperPath();
            } else {
                try {
                    path = WallpaperManager.getInstance(this.mContext).semGetUri(i).getPath();
                } catch (Throwable th) {
                    Log.e(str, "getGifPath: " + th, th);
                }
            }
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getGifPath: ", path, str);
            return path;
        }
        path = "";
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getGifPath: ", path, str);
        return path;
    }
}
