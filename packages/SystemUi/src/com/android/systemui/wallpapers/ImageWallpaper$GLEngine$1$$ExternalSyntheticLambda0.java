package com.android.systemui.wallpapers;

import android.app.WallpaperManager;
import android.os.Trace;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.subscreen.SubHomeActivity;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.canvaswallpaper.ImageWallpaperCanvasHelper;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperRenderer;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.nexus.video.VideoLayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GLEngine$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ImageWallpaper$GLEngine$1$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.GLEngine gLEngine = ImageWallpaper.GLEngine.this;
                boolean z2 = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).getLidState() == 0;
                boolean z3 = LsRune.SUBSCREEN_WATCHFACE;
                if (z3 && z2 && gLEngine.getDisplayId() == 0) {
                    Log.i("ImageWallpaper[GLEngine]", "Ignore Waking up when closed in watch face mode. ");
                    return;
                }
                ImageWallpaperRenderer imageWallpaperRenderer = gLEngine.mRenderer;
                if (imageWallpaperRenderer != null) {
                    boolean z4 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
                    if (z4) {
                        int i = imageWallpaperRenderer.mLidState;
                        WallpaperManager wallpaperManager = imageWallpaperRenderer.mWallpaperManager;
                        if (i != wallpaperManager.getLidState()) {
                            ((WallpaperLoggerImpl) imageWallpaperRenderer.mLoggerWrapper).log("ImageWallpaperRenderer", "onStartedWakingUp lid state different. so update " + ImageWallpaperRenderer.showLidState(imageWallpaperRenderer.mLidState) + " , " + ImageWallpaperRenderer.showLidState(wallpaperManager.getLidState()) + " , " + imageWallpaperRenderer.mIsFolded);
                            if (imageWallpaperRenderer.mIsFolded) {
                                imageWallpaperRenderer.setLidState(0);
                            } else {
                                imageWallpaperRenderer.setLidState(1);
                            }
                            z = true;
                        }
                    }
                    if (z) {
                        if (z4 && !z3) {
                            gLEngine.updateSurfaceSize();
                        }
                        gLEngine.updateWallpaperOffset(gLEngine.mRotation);
                        gLEngine.updateRendering();
                        return;
                    }
                    return;
                }
                return;
            case 1:
                ImageWallpaper.CanvasEngine canvasEngine = (ImageWallpaper.CanvasEngine) this.f$0;
                synchronized (canvasEngine.mLock) {
                    Trace.beginSection("ImageWallpaper.CanvasEngine#unloadBitmap");
                    canvasEngine.getSurfaceHolder().getSurface().hwuiDestroy();
                    canvasEngine.mWallpaperManager.forgetLoadedWallpaper();
                    Trace.endSection();
                }
                return;
            case 2:
                ImageWallpaper.CanvasEngine canvasEngine2 = ImageWallpaper.CanvasEngine.this;
                boolean z5 = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).getLidState() == 0;
                boolean z6 = LsRune.SUBSCREEN_WATCHFACE;
                if (z6 && z5 && canvasEngine2.getDisplayId() == 0) {
                    Log.i(canvasEngine2.TAG, "Ignore Waking up when closed in watch face mode. ");
                    return;
                }
                ImageWallpaperCanvasHelper imageWallpaperCanvasHelper = canvasEngine2.mHelper;
                imageWallpaperCanvasHelper.getClass();
                boolean z7 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
                if (z7) {
                    int i2 = imageWallpaperCanvasHelper.mLidState;
                    WallpaperManager wallpaperManager2 = imageWallpaperCanvasHelper.mWallpaperManager;
                    if (i2 != wallpaperManager2.getLidState()) {
                        ((WallpaperLoggerImpl) imageWallpaperCanvasHelper.mLoggerWrapper).log(imageWallpaperCanvasHelper.TAG, "onStartedWakingUp lid state different. so update " + ImageWallpaperCanvasHelper.convertLidStateToString(imageWallpaperCanvasHelper.mLidState) + " , " + ImageWallpaperCanvasHelper.convertLidStateToString(wallpaperManager2.getLidState()) + " , " + imageWallpaperCanvasHelper.mIsFolded);
                        if (imageWallpaperCanvasHelper.mIsFolded) {
                            imageWallpaperCanvasHelper.setLidState(0);
                        } else {
                            imageWallpaperCanvasHelper.setLidState(1);
                        }
                        z = true;
                    }
                }
                if (z) {
                    int currentWhich = canvasEngine2.mHelper.getCurrentWhich();
                    if (z7 && !z6) {
                        canvasEngine2.updateSurfaceSize(currentWhich);
                    }
                    canvasEngine2.updateRendering(currentWhich);
                    return;
                }
                return;
            case 3:
                ImageWallpaper.GLEngine gLEngine2 = ImageWallpaper.GLEngine.this;
                gLEngine2.updateWallpaperOffset(gLEngine2.mRotation);
                return;
            default:
                ImageWallpaper.VideoGLEngine.C37282 c37282 = (ImageWallpaper.VideoGLEngine.C37282) this.f$0;
                c37282.getClass();
                SubHomeActivity subHomeActivity = ((SubScreenManager) Dependency.get(SubScreenManager.class)).mActivity;
                if (subHomeActivity != null && subHomeActivity.semIsResumed()) {
                    z = true;
                }
                StringBuilder sb = new StringBuilder("onStartedWakingUp : isSubDisplay = ");
                sb.append(WallpaperUtils.isSubDisplay());
                sb.append(" isResumed = ");
                sb.append(z);
                sb.append(" , mIsPauseByCommand = ");
                ImageWallpaper.VideoGLEngine videoGLEngine = ImageWallpaper.VideoGLEngine.this;
                NotificationListener$$ExternalSyntheticOutline0.m123m(sb, videoGLEngine.mIsPauseByCommand, "ImageWallpaper[VideoGLEngine]");
                if (videoGLEngine.mRenderer == null || !WallpaperUtils.isSubDisplay() || !z || videoGLEngine.mIsPauseByCommand) {
                    return;
                }
                VideoLayer videoLayer = videoGLEngine.mRenderer.mVideoLayer;
                if (videoLayer != null) {
                    videoLayer.setHsvValue(0.5f);
                }
                videoGLEngine.mRenderer.start();
                return;
        }
    }
}
