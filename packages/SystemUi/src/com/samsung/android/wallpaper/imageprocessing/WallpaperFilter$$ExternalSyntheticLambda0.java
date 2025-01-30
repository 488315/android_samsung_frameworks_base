package com.samsung.android.wallpaper.imageprocessing;

import android.graphics.Bitmap;
import com.samsung.android.wallpaper.imageprocessing.WallpaperFilter;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final /* synthetic */ class WallpaperFilter$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WallpaperFilter f$0;
    public final /* synthetic */ Bitmap f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ WallpaperFilter$$ExternalSyntheticLambda0(WallpaperFilter wallpaperFilter, Bitmap bitmap, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = wallpaperFilter;
        this.f$1 = bitmap;
        this.f$2 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                WallpaperFilter wallpaperFilter = this.f$0;
                Bitmap bitmap = this.f$1;
                int i = this.f$2;
                WallpaperFilter.ProcessingRange processingRange = (WallpaperFilter.ProcessingRange) obj;
                int i2 = WallpaperFilter.$r8$clinit;
                wallpaperFilter.getClass();
                wallpaperFilter.nativeStackBlur(bitmap, processingRange.start, processingRange.length, i, 1);
                break;
            case 1:
                WallpaperFilter wallpaperFilter2 = this.f$0;
                Bitmap bitmap2 = this.f$1;
                int i3 = this.f$2;
                WallpaperFilter.ProcessingRange processingRange2 = (WallpaperFilter.ProcessingRange) obj;
                int i4 = WallpaperFilter.$r8$clinit;
                wallpaperFilter2.getClass();
                wallpaperFilter2.nativeStackBlur(bitmap2, processingRange2.start, processingRange2.length, i3, 2);
                break;
            default:
                WallpaperFilter wallpaperFilter3 = this.f$0;
                Bitmap bitmap3 = this.f$1;
                int i5 = this.f$2;
                WallpaperFilter.ProcessingRange processingRange3 = (WallpaperFilter.ProcessingRange) obj;
                int i6 = WallpaperFilter.$r8$clinit;
                wallpaperFilter3.getClass();
                wallpaperFilter3.nativeSetHighLightFilter(bitmap3, processingRange3.start, processingRange3.length, i5);
                break;
        }
    }
}
