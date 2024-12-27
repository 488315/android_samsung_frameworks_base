package com.android.systemui.wallpapers;

import com.android.systemui.wallpapers.ImageWallpaper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.CanvasEngine f$0;

    public /* synthetic */ ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2(ImageWallpaper.CanvasEngine canvasEngine, int i) {
        this.$r8$classId = i;
        this.f$0 = canvasEngine;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ImageWallpaper.CanvasEngine canvasEngine = this.f$0;
        switch (i) {
            case 0:
                synchronized (canvasEngine.mLock) {
                    canvasEngine.unloadBitmapIfNotUsedInternal();
                }
                return;
            default:
                synchronized (canvasEngine.mLock) {
                    canvasEngine.mSurfaceCreated = false;
                    canvasEngine.mSurfaceHolder = null;
                }
                return;
        }
    }
}
