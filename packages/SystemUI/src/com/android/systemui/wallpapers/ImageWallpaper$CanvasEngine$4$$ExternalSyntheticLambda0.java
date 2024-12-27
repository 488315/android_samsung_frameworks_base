package com.android.systemui.wallpapers;

import android.app.WallpaperManager;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpapers.ImageWallpaper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class ImageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ImageWallpaper$CanvasEngine$4$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ImageWallpaper.CanvasEngine canvasEngine = ImageWallpaper.CanvasEngine.this;
                boolean z = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).getLidState() == 0;
                boolean z2 = LsRune.SUBSCREEN_WATCHFACE;
                if (!z2 || !z || canvasEngine.getDisplayId() != 0) {
                    ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = canvasEngine.mHelper;
                    imageWallpaperCanvasHelper.getClass();
                    boolean z3 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
                    if (z3 && imageWallpaperCanvasHelper.mLidState != imageWallpaperCanvasHelper.mWallpaperManager.getLidState()) {
                        ((WallpaperLoggerImpl) imageWallpaperCanvasHelper.mLoggerWrapper).log(imageWallpaperCanvasHelper.TAG, "onStartedWakingUp lid state different. so update " + ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mLidState) + " , " + ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mWallpaperManager.getLidState()) + " , " + imageWallpaperCanvasHelper.mIsFolded);
                        if (imageWallpaperCanvasHelper.mIsFolded) {
                            imageWallpaperCanvasHelper.setLidState(0);
                        } else {
                            imageWallpaperCanvasHelper.setLidState(1);
                        }
                        int currentWhich = canvasEngine.mHelper.getCurrentWhich();
                        if (z3 && !z2) {
                            canvasEngine.updateSurfaceSize(currentWhich);
                        }
                        canvasEngine.updateRendering(currentWhich);
                        break;
                    }
                } else {
                    Log.i(canvasEngine.TAG, "Ignore Waking up when closed in watch face mode. ");
                    break;
                }
                break;
            default:
                ImageWallpaper.CanvasEngine canvasEngine2 = ImageWallpaper.CanvasEngine.this;
                if (!(!canvasEngine2.mIsEngineAlive)) {
                    ImageWallpaper.CanvasEngine.m2376$$Nest$mupdatePluginWallpaper(canvasEngine2);
                    break;
                } else {
                    Log.w(canvasEngine2.TAG, " mPluginWallpaperConsumer, skip, engine is destroyed");
                    break;
                }
        }
    }
}
