package com.android.systemui.wallpapers;

import android.graphics.Bitmap;
import android.util.ArraySet;
import java.util.ArrayList;

public final /* synthetic */ class WallpaperLocalColorExtractor$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ WallpaperLocalColorExtractor f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ WallpaperLocalColorExtractor$$ExternalSyntheticLambda5(WallpaperLocalColorExtractor wallpaperLocalColorExtractor, int i) {
        this.f$0 = wallpaperLocalColorExtractor;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.f$0;
        int i = this.f$1;
        synchronized (wallpaperLocalColorExtractor.mLock) {
            try {
                if (wallpaperLocalColorExtractor.mPages == i) {
                    return;
                }
                wallpaperLocalColorExtractor.mPages = i;
                Bitmap bitmap = wallpaperLocalColorExtractor.mMiniBitmap;
                if (bitmap != null && !bitmap.isRecycled()) {
                    ((ArrayList) wallpaperLocalColorExtractor.mPendingRegions).addAll(wallpaperLocalColorExtractor.mProcessedRegions);
                    ((ArraySet) wallpaperLocalColorExtractor.mProcessedRegions).clear();
                    wallpaperLocalColorExtractor.processLocalColorsInternal();
                }
            } finally {
            }
        }
    }
}
