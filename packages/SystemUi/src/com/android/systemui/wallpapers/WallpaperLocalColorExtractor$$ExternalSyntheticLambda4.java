package com.android.systemui.wallpapers;

import android.graphics.Bitmap;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WallpaperLocalColorExtractor$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ WallpaperLocalColorExtractor f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ WallpaperLocalColorExtractor$$ExternalSyntheticLambda4(WallpaperLocalColorExtractor wallpaperLocalColorExtractor, int i) {
        this.f$0 = wallpaperLocalColorExtractor;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.f$0;
        int i = this.f$1;
        synchronized (wallpaperLocalColorExtractor.mLock) {
            if (wallpaperLocalColorExtractor.mPages == i) {
                return;
            }
            wallpaperLocalColorExtractor.mPages = i;
            Bitmap bitmap = wallpaperLocalColorExtractor.mMiniBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                List list = wallpaperLocalColorExtractor.mPendingRegions;
                Set set = wallpaperLocalColorExtractor.mProcessedRegions;
                ((ArrayList) list).addAll(set);
                ((ArraySet) set).clear();
                wallpaperLocalColorExtractor.processColorsInternal();
            }
        }
    }
}
