package com.android.systemui.wallpapers;

import android.graphics.Bitmap;
import com.android.systemui.wallpapers.ImageWallpaper;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.CanvasEngine f$0;

    public /* synthetic */ ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda0(ImageWallpaper.CanvasEngine canvasEngine, int i) {
        this.$r8$classId = i;
        this.f$0 = canvasEngine;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                WallpaperLocalColorExtractor wallpaperLocalColorExtractor = this.f$0.mWallpaperLocalColorExtractor;
                wallpaperLocalColorExtractor.getClass();
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda0(wallpaperLocalColorExtractor, (Bitmap) obj, 2));
                break;
            case 1:
                this.f$0.updateMiniBitmapAndNotify((Bitmap) obj);
                break;
            default:
                ImageWallpaper.CanvasEngine canvasEngine = this.f$0;
                Bitmap bitmap = (Bitmap) obj;
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                imageWallpaper.mLocalColorsToAdd.addAll(imageWallpaper.mColorAreas);
                if (ImageWallpaper.this.mLocalColorsToAdd.size() > 0) {
                    canvasEngine.updateMiniBitmapAndNotify(bitmap);
                    break;
                }
                break;
        }
    }
}
