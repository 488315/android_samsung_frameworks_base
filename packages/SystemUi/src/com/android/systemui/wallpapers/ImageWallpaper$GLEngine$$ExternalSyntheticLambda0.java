package com.android.systemui.wallpapers;

import android.app.WallpaperManager;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.glwallpaper.EglHelper;
import com.android.systemui.wallpapers.ImageWallpaper;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.GLEngine f$0;

    public /* synthetic */ ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(ImageWallpaper.GLEngine gLEngine, int i) {
        this.$r8$classId = i;
        this.f$0 = gLEngine;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.GLEngine gLEngine = this.f$0;
                gLEngine.updateWallpaperOffset(gLEngine.mRotation);
                break;
            case 1:
                ImageWallpaper.GLEngine gLEngine2 = this.f$0;
                gLEngine2.getClass();
                gLEngine2.computeAndNotifyLocalColors(new ArrayList(ImageWallpaper.this.mColorAreas), ImageWallpaper.this.mMiniBitmap);
                break;
            case 2:
                this.f$0.drawFrame();
                break;
            case 3:
                this.f$0.drawFrame();
                break;
            case 4:
                this.f$0.finishRendering();
                break;
            case 5:
                ImageWallpaper.GLEngine gLEngine3 = this.f$0;
                if (((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType() == 21) {
                    if (!gLEngine3.updateSurfaceSizeIfNeed() && gLEngine3.mSurfaceCreated) {
                        gLEngine3.finishRendering();
                        gLEngine3.drawFrame();
                        break;
                    }
                } else {
                    WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).forceRebindWallpaper(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getCoverWhich());
                    break;
                }
                break;
            case 6:
                ImageWallpaper.GLEngine gLEngine4 = this.f$0;
                EglHelper eglHelper = gLEngine4.mEglHelper;
                if (eglHelper != null) {
                    eglHelper.destroyEglSurface();
                    gLEngine4.mEglHelper.destroyEglContext();
                }
                gLEngine4.drawFrame();
                break;
            case 7:
                ImageWallpaper.GLEngine gLEngine5 = this.f$0;
                gLEngine5.finishRendering();
                gLEngine5.updateRendering();
                break;
            case 8:
                ImageWallpaper.GLEngine.m2736$r8$lambda$gvSO08LSV1OEOlvWqQf1uysZ84(this.f$0);
                break;
            default:
                ImageWallpaper.GLEngine.$r8$lambda$InHMzcxP9yIB3NrHGHisYRbdVpE(this.f$0);
                break;
        }
    }
}
