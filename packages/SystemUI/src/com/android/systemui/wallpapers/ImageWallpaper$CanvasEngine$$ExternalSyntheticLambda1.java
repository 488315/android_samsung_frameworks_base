package com.android.systemui.wallpapers;

import android.graphics.Bitmap;
import com.android.systemui.wallpapers.ImageWallpaper;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.CanvasEngine f$0;

    public /* synthetic */ ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda1(ImageWallpaper.CanvasEngine canvasEngine, int i) {
        this.$r8$classId = i;
        this.f$0 = canvasEngine;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ImageWallpaper.CanvasEngine canvasEngine = this.f$0;
        Bitmap bitmap = (Bitmap) obj;
        switch (i) {
            case 0:
                WallpaperLocalColorExtractor wallpaperLocalColorExtractor = canvasEngine.mWallpaperLocalColorExtractor;
                wallpaperLocalColorExtractor.mLongExecutor.execute(new WallpaperLocalColorExtractor$$ExternalSyntheticLambda1(wallpaperLocalColorExtractor, bitmap, 2));
                break;
            case 1:
                ImageWallpaper imageWallpaper = canvasEngine.this$0;
                imageWallpaper.mLocalColorsToAdd.addAll(imageWallpaper.mColorAreas);
                if (canvasEngine.this$0.mLocalColorsToAdd.size() > 0) {
                    canvasEngine.updateMiniBitmapAndNotify(bitmap);
                    break;
                }
                break;
            default:
                canvasEngine.updateMiniBitmapAndNotify(bitmap);
                break;
        }
    }
}
