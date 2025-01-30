package com.android.systemui.wallpapers;

import android.app.WallpaperManager;
import android.util.Log;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperGifRenderer;
import com.android.systemui.wallpapers.ImageWallpaper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GifGLEngine$2$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.GifGLEngine.C37262 f$0;

    public /* synthetic */ ImageWallpaper$GifGLEngine$2$$ExternalSyntheticLambda0(ImageWallpaper.GifGLEngine.C37262 c37262, int i) {
        this.$r8$classId = i;
        this.f$0 = c37262;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.GifGLEngine gifGLEngine = ImageWallpaper.GifGLEngine.this;
                boolean z = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).getLidState() == 0;
                ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("onStartedWakingUp : isSubDisplay = ", z, "ImageWallpaper[GifGLEngine]");
                ImageWallpaperGifRenderer imageWallpaperGifRenderer = gifGLEngine.mRenderer;
                if (imageWallpaperGifRenderer != null && z) {
                    imageWallpaperGifRenderer.updateGif(gifGLEngine.getSurfaceHolder());
                    if (gifGLEngine.isVisible()) {
                        Log.i("ImageWallpaper[GifGLEngine]", "onStartedWakingUp : wake up in visible state");
                        gifGLEngine.mRenderer.start();
                        break;
                    }
                }
                break;
            default:
                ImageWallpaper.GifGLEngine.C37262 c37262 = this.f$0;
                c37262.getClass();
                Log.i("ImageWallpaper[GifGLEngine]", "onFinishedGoingToSleep");
                ImageWallpaperGifRenderer imageWallpaperGifRenderer2 = ImageWallpaper.GifGLEngine.this.mRenderer;
                if (imageWallpaperGifRenderer2 != null) {
                    imageWallpaperGifRenderer2.stop();
                    break;
                }
                break;
        }
    }
}
