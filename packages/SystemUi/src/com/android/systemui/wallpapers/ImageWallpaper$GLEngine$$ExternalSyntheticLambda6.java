package com.android.systemui.wallpapers;

import android.view.SurfaceHolder;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperGifRenderer;
import com.android.systemui.wallpapers.ImageWallpaper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GLEngine$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.BaseEngine f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ ImageWallpaper$GLEngine$$ExternalSyntheticLambda6(ImageWallpaper.BaseEngine baseEngine, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = baseEngine;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaper.GLEngine.$r8$lambda$MTjZ9OcqEdrPyZzafR7xU6HwKHk((ImageWallpaper.GLEngine) this.f$0, this.f$1);
                break;
            case 1:
                ImageWallpaper.CanvasEngine canvasEngine = (ImageWallpaper.CanvasEngine) this.f$0;
                boolean z = this.f$1;
                int i = ImageWallpaper.CanvasEngine.$r8$clinit;
                canvasEngine.updateOnSwitchDisplayChanged(z);
                break;
            case 2:
                ImageWallpaper.GifGLEngine gifGLEngine = (ImageWallpaper.GifGLEngine) this.f$0;
                boolean z2 = this.f$1;
                int i2 = ImageWallpaper.GifGLEngine.MIN_SURFACE_WIDTH;
                gifGLEngine.getClass();
                StringBuilder sb = new StringBuilder("onVisibilityChanged : visible = ");
                sb.append(z2);
                sb.append(", AOD state = ");
                TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, gifGLEngine.mAodState, "ImageWallpaper[GifGLEngine]");
                ImageWallpaperGifRenderer imageWallpaperGifRenderer = gifGLEngine.mRenderer;
                if (imageWallpaperGifRenderer != null) {
                    boolean isInteractive = ImageWallpaper.this.mPm.isInteractive();
                    int i3 = gifGLEngine.mAodState;
                    SurfaceHolder surfaceHolder = gifGLEngine.getSurfaceHolder();
                    TooltipPopup$$ExternalSyntheticOutline0.m13m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m(" onVisibilityChanged: ", z2, ", isInteractive : ", isInteractive, ", aodState : "), i3, "ImageWallpaperGifRenderer");
                    imageWallpaperGifRenderer.mSurfaceHolder = surfaceHolder;
                    imageWallpaperGifRenderer.mVisible = z2;
                    if (!z2) {
                        imageWallpaperGifRenderer.stop();
                        break;
                    } else if (i3 != 2 || isInteractive) {
                        imageWallpaperGifRenderer.start();
                        break;
                    }
                }
                break;
            default:
                ImageWallpaper.VideoGLEngine videoGLEngine = (ImageWallpaper.VideoGLEngine) this.f$0;
                boolean z3 = this.f$1;
                int i4 = ImageWallpaper.VideoGLEngine.MIN_SURFACE_WIDTH;
                videoGLEngine.getClass();
                StringBuilder sb2 = new StringBuilder("onVisibilityChanged : visible = ");
                sb2.append(z3);
                sb2.append(", isInteractive = ");
                sb2.append(ImageWallpaper.this.mPm.isInteractive());
                sb2.append(", AOD state = ");
                sb2.append(videoGLEngine.mAodState);
                sb2.append(" , mIsPauseByCommand = ");
                NotificationListener$$ExternalSyntheticOutline0.m123m(sb2, videoGLEngine.mIsPauseByCommand, "ImageWallpaper[VideoGLEngine]");
                if (!z3) {
                    videoGLEngine.mRenderer.stop();
                    ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$VideoGLEngine$$ExternalSyntheticLambda0(videoGLEngine, 3));
                    break;
                } else {
                    if (!((videoGLEngine.mAodState == 2) & (true ^ ImageWallpaper.this.mPm.isInteractive())) && !videoGLEngine.mIsPauseByCommand) {
                        videoGLEngine.mRenderer.start();
                        ImageWallpaper.this.mWorker.getThreadHandler().post(new ImageWallpaper$VideoGLEngine$$ExternalSyntheticLambda0(videoGLEngine, 2));
                        break;
                    }
                }
                break;
        }
    }
}
