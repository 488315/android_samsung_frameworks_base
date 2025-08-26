package com.android.systemui.wallpapers;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Trace;
import android.util.ArraySet;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class WallpaperLocalColorExtractor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WallpaperLocalColorExtractor f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ WallpaperLocalColorExtractor$$ExternalSyntheticLambda0(WallpaperLocalColorExtractor wallpaperLocalColorExtractor, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = wallpaperLocalColorExtractor;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.f$0;
                List list = (List) this.f$1;
                synchronized (wallpaperLocalColorExtractor.mLock) {
                    try {
                        boolean zIsActive = wallpaperLocalColorExtractor.isActive();
                        ((ArrayList) wallpaperLocalColorExtractor.mPendingRegions).removeAll(list);
                        final Set set = wallpaperLocalColorExtractor.mProcessedRegions;
                        Objects.requireNonNull(set);
                        list.forEach(new Consumer() { // from class: com.android.systemui.wallpapers.WallpaperLocalColorExtractor$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                set.remove((RectF) obj);
                            }
                        });
                        if (zIsActive && !wallpaperLocalColorExtractor.isActive()) {
                            wallpaperLocalColorExtractor.mWallpaperLocalColorExtractorCallback.onDeactivated();
                        }
                    } finally {
                    }
                }
                return;
            case 1:
                WallpaperLocalColorExtractor wallpaperLocalColorExtractor2 = this.f$0;
                List list2 = (List) this.f$1;
                synchronized (wallpaperLocalColorExtractor2.mLock) {
                    try {
                        boolean zIsActive2 = wallpaperLocalColorExtractor2.isActive();
                        ((ArrayList) wallpaperLocalColorExtractor2.mPendingRegions).addAll(list2);
                        if (!zIsActive2 && wallpaperLocalColorExtractor2.isActive()) {
                            wallpaperLocalColorExtractor2.mWallpaperLocalColorExtractorCallback.onActivated();
                        }
                        wallpaperLocalColorExtractor2.processLocalColorsInternal();
                    } finally {
                    }
                }
                return;
            default:
                WallpaperLocalColorExtractor wallpaperLocalColorExtractor3 = this.f$0;
                Bitmap bitmap = (Bitmap) this.f$1;
                synchronized (wallpaperLocalColorExtractor3.mLock) {
                    try {
                        if (bitmap.isRecycled()) {
                            Log.i("WallpaperLocalColorExtractor", "Wallpaper has changed; deferring color extraction");
                            return;
                        }
                        if (bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
                            wallpaperLocalColorExtractor3.mBitmapWidth = bitmap.getWidth();
                            wallpaperLocalColorExtractor3.mBitmapHeight = bitmap.getHeight();
                            Trace.beginSection("WallpaperLocalColorExtractor#createMiniBitmap");
                            double dMin = Math.min(1.0d, Math.sqrt(12544.0d / (bitmap.getHeight() * bitmap.getWidth())));
                            Bitmap bitmapCreateMiniBitmap = wallpaperLocalColorExtractor3.createMiniBitmap(bitmap, Math.max(1, (int) (bitmap.getWidth() * dMin)), Math.max(1, (int) (dMin * bitmap.getHeight())));
                            Trace.endSection();
                            wallpaperLocalColorExtractor3.mMiniBitmap = bitmapCreateMiniBitmap;
                            wallpaperLocalColorExtractor3.mWallpaperLocalColorExtractorCallback.onMiniBitmapUpdated();
                            ((ArrayList) wallpaperLocalColorExtractor3.mPendingRegions).addAll(wallpaperLocalColorExtractor3.mProcessedRegions);
                            ((ArraySet) wallpaperLocalColorExtractor3.mProcessedRegions).clear();
                            wallpaperLocalColorExtractor3.processLocalColorsInternal();
                            return;
                        }
                        Log.e("WallpaperLocalColorExtractor", "Attempt to extract colors from an invalid bitmap");
                        return;
                    } finally {
                    }
                }
        }
    }
}
