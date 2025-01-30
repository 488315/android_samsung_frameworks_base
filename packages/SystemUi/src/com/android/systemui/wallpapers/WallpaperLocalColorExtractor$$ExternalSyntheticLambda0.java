package com.android.systemui.wallpapers;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.Trace;
import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.wallpapers.ImageWallpaper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                    boolean isActive = wallpaperLocalColorExtractor.isActive();
                    ((ArrayList) wallpaperLocalColorExtractor.mPendingRegions).removeAll(list);
                    final Set set = wallpaperLocalColorExtractor.mProcessedRegions;
                    Objects.requireNonNull(set);
                    list.forEach(new Consumer() { // from class: com.android.systemui.wallpapers.WallpaperLocalColorExtractor$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            set.remove((RectF) obj);
                        }
                    });
                    if (isActive && !wallpaperLocalColorExtractor.isActive()) {
                        ImageWallpaper.CanvasEngine.this.setOffsetNotificationsEnabled(false);
                    }
                }
                return;
            case 1:
                WallpaperLocalColorExtractor wallpaperLocalColorExtractor2 = this.f$0;
                List list2 = (List) this.f$1;
                synchronized (wallpaperLocalColorExtractor2.mLock) {
                    boolean isActive2 = wallpaperLocalColorExtractor2.isActive();
                    ((ArrayList) wallpaperLocalColorExtractor2.mPendingRegions).addAll(list2);
                    if (!isActive2 && wallpaperLocalColorExtractor2.isActive()) {
                        ImageWallpaper.CanvasEngine.this.setOffsetNotificationsEnabled(true);
                    }
                    wallpaperLocalColorExtractor2.processColorsInternal();
                }
                return;
            default:
                WallpaperLocalColorExtractor wallpaperLocalColorExtractor3 = this.f$0;
                Bitmap bitmap = (Bitmap) this.f$1;
                synchronized (wallpaperLocalColorExtractor3.mLock) {
                    if (!bitmap.isRecycled() && bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
                        wallpaperLocalColorExtractor3.mBitmapWidth = bitmap.getWidth();
                        wallpaperLocalColorExtractor3.mBitmapHeight = bitmap.getHeight();
                        Trace.beginSection("WallpaperLocalColorExtractor#createMiniBitmap");
                        float min = Math.min(1.0f, 128.0f / Math.min(bitmap.getWidth(), bitmap.getHeight()));
                        Bitmap createMiniBitmap = wallpaperLocalColorExtractor3.createMiniBitmap(bitmap, (int) (bitmap.getWidth() * min), (int) (min * bitmap.getHeight()));
                        Trace.endSection();
                        wallpaperLocalColorExtractor3.mMiniBitmap = createMiniBitmap;
                        ImageWallpaper.CanvasEngine.this.onMiniBitmapUpdated();
                        List list3 = wallpaperLocalColorExtractor3.mPendingRegions;
                        Set set2 = wallpaperLocalColorExtractor3.mProcessedRegions;
                        ((ArrayList) list3).addAll(set2);
                        ((ArraySet) set2).clear();
                        wallpaperLocalColorExtractor3.processColorsInternal();
                        return;
                    }
                    Log.e("WallpaperLocalColorExtractor", "Attempt to extract colors from an invalid bitmap");
                    return;
                }
        }
    }
}
