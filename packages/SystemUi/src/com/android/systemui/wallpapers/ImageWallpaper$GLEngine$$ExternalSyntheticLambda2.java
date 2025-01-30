package com.android.systemui.wallpapers;

import android.graphics.Bitmap;
import android.util.Log;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperRenderer;
import com.android.systemui.wallpapers.ImageWallpaper;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GLEngine$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.GLEngine f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ ImageWallpaper$GLEngine$$ExternalSyntheticLambda2(ImageWallpaper.GLEngine gLEngine, List list, int i) {
        this.$r8$classId = i;
        this.f$0 = gLEngine;
        this.f$1 = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.GLEngine gLEngine = this.f$0;
                List list = this.f$1;
                ImageWallpaper.this.mColorAreas.removeAll(list);
                ImageWallpaper.this.mLocalColorsToAdd.removeAll(list);
                if (ImageWallpaper.this.mLocalColorsToAdd.size() + ImageWallpaper.this.mColorAreas.size() == 0) {
                    gLEngine.setOffsetNotificationsEnabled(false);
                    break;
                }
                break;
            default:
                ImageWallpaper.GLEngine gLEngine2 = this.f$0;
                List list2 = this.f$1;
                gLEngine2.getClass();
                Log.i("ImageWallpaper[GLEngine]", " addLocalColorsAreas ");
                if (ImageWallpaper.this.mLocalColorsToAdd.size() + ImageWallpaper.this.mColorAreas.size() == 0) {
                    gLEngine2.setOffsetNotificationsEnabled(true);
                }
                ImageWallpaper imageWallpaper = ImageWallpaper.this;
                Bitmap bitmap = imageWallpaper.mMiniBitmap;
                if (bitmap != null) {
                    gLEngine2.computeAndNotifyLocalColors(list2, bitmap);
                    break;
                } else {
                    imageWallpaper.mLocalColorsToAdd.addAll(list2);
                    ImageWallpaperRenderer imageWallpaperRenderer = gLEngine2.mRenderer;
                    if (imageWallpaperRenderer != null) {
                        imageWallpaperRenderer.mTexture.use(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda5(gLEngine2, 1));
                        break;
                    }
                }
                break;
        }
    }
}
