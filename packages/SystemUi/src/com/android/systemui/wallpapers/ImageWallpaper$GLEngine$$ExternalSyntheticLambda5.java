package com.android.systemui.wallpapers;

import android.graphics.Bitmap;
import com.android.systemui.wallpapers.ImageWallpaper;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GLEngine$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.GLEngine f$0;

    public /* synthetic */ ImageWallpaper$GLEngine$$ExternalSyntheticLambda5(ImageWallpaper.GLEngine gLEngine, int i) {
        this.$r8$classId = i;
        this.f$0 = gLEngine;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.GLEngine gLEngine = this.f$0;
                Bitmap bitmap = (Bitmap) obj;
                ImageWallpaper imageWallpaper = gLEngine.this$0;
                imageWallpaper.mLocalColorsToAdd.addAll(imageWallpaper.mColorAreas);
                if (gLEngine.this$0.mLocalColorsToAdd.size() > 0) {
                    gLEngine.updateMiniBitmapAndNotify(bitmap);
                    break;
                }
                break;
            default:
                this.f$0.updateMiniBitmapAndNotify((Bitmap) obj);
                break;
        }
    }
}
