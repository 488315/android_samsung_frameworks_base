package com.android.systemui.wallpapers;

import com.android.systemui.wallpapers.ImageWallpaper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GLEngine$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.GLEngine.C37222 f$0;

    public /* synthetic */ ImageWallpaper$GLEngine$2$$ExternalSyntheticLambda0(ImageWallpaper.GLEngine.C37222 c37222, int i) {
        this.$r8$classId = i;
        this.f$0 = c37222;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.GLEngine gLEngine = this.f$0.this$1;
                gLEngine.updateWallpaperOffset(gLEngine.mRotation);
                break;
            default:
                ImageWallpaper.GLEngine gLEngine2 = this.f$0.this$1;
                gLEngine2.updateWallpaperOffset(gLEngine2.mRotation);
                break;
        }
    }
}
