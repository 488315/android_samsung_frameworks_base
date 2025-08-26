package com.android.systemui.wallpapers;

import android.app.WallpaperManager;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpapers.ImageWallpaper;

/* loaded from: classes3.dex */
public final /* synthetic */ class ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ImageWallpaper$CanvasEngine$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ImageWallpaper.CanvasEngine canvasEngine = (ImageWallpaper.CanvasEngine) obj;
                synchronized (canvasEngine.mLock) {
                    canvasEngine.unloadBitmapIfNotUsedInternal();
                }
                return;
            case 1:
                ImageWallpaper.CanvasEngine canvasEngine2 = ImageWallpaper.CanvasEngine.this;
                boolean z = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).getLidState() == 0;
                boolean z2 = LsRune.SUBSCREEN_WATCHFACE;
                if (z2 && z && canvasEngine2.getDisplayId() == 0) {
                    Log.i(canvasEngine2.TAG, "Ignore Waking up when closed in watch face mode. ");
                    return;
                }
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = canvasEngine2.mHelper;
                imageWallpaperCanvasHelper.getClass();
                boolean z3 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
                if (!z3 || imageWallpaperCanvasHelper.mLidState == imageWallpaperCanvasHelper.mWallpaperManager.getLidState()) {
                    return;
                }
                ((WallpaperLoggerImpl) imageWallpaperCanvasHelper.mLoggerWrapper).log(imageWallpaperCanvasHelper.TAG, "onStartedWakingUp lid state different. so update " + ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mLidState) + " , " + ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mWallpaperManager.getLidState()) + " , " + imageWallpaperCanvasHelper.mIsFolded);
                if (imageWallpaperCanvasHelper.mIsFolded) {
                    imageWallpaperCanvasHelper.setLidState(0);
                } else {
                    imageWallpaperCanvasHelper.setLidState(1);
                }
                int currentWhich = canvasEngine2.mHelper.getCurrentWhich();
                if (z3 && !z2) {
                    canvasEngine2.updateSurfaceSize(currentWhich);
                }
                canvasEngine2.updateRendering(currentWhich);
                return;
            default:
                ImageWallpaper.CanvasEngine canvasEngine3 = ImageWallpaper.CanvasEngine.this;
                if (canvasEngine3.mIsEngineAlive) {
                    ImageWallpaper.CanvasEngine.m3230$$Nest$mupdatePluginWallpaper(canvasEngine3);
                    return;
                } else {
                    Log.w(canvasEngine3.TAG, " mPluginWallpaperConsumer, skip, engine is destroyed");
                    return;
                }
        }
    }
}
