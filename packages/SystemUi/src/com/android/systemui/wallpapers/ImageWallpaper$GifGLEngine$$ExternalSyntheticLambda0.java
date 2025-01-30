package com.android.systemui.wallpapers;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.LsRune;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.glwallpaper.ImageWallpaperGifRenderer;
import com.android.systemui.wallpapers.ImageWallpaper;
import java.io.File;
import java.io.FileInputStream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ImageWallpaper$GifGLEngine$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ImageWallpaper.GifGLEngine f$0;

    public /* synthetic */ ImageWallpaper$GifGLEngine$$ExternalSyntheticLambda0(ImageWallpaper.GifGLEngine gifGLEngine, int i) {
        this.$r8$classId = i;
        this.f$0 = gifGLEngine;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ImageWallpaperGifRenderer imageWallpaperGifRenderer = this.f$0.mRenderer;
                imageWallpaperGifRenderer.getClass();
                Log.i("ImageWallpaperGifRenderer", "pause");
                imageWallpaperGifRenderer.mIsPaused = true;
                break;
            case 1:
                ImageWallpaperGifRenderer imageWallpaperGifRenderer2 = this.f$0.mRenderer;
                imageWallpaperGifRenderer2.getClass();
                Log.i("ImageWallpaperGifRenderer", "resume");
                imageWallpaperGifRenderer2.mIsPaused = false;
                if (imageWallpaperGifRenderer2.mVisible) {
                    imageWallpaperGifRenderer2.mStartedMovieTime = SystemClock.uptimeMillis() - imageWallpaperGifRenderer2.mPausedMovieTime;
                    imageWallpaperGifRenderer2.mOnDrawHandler.sendEmptyMessageDelayed(1001, 50L);
                    break;
                }
                break;
            case 2:
                ImageWallpaper.GifGLEngine gifGLEngine = this.f$0;
                gifGLEngine.mRenderer.stop();
                gifGLEngine.mRenderer = null;
                break;
            case 3:
                ImageWallpaper.GifGLEngine gifGLEngine2 = this.f$0;
                if (gifGLEngine2.mRenderer != null) {
                    Log.i("ImageWallpaper[GifGLEngine]", " updatePlugInWallpaper " + ImageWallpaper.this.mSubWallpaperType + " , " + ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperType());
                    ImageWallpaper imageWallpaper = ImageWallpaper.this;
                    if (imageWallpaper.mSubWallpaperType == ((CoverWallpaperController) imageWallpaper.mCoverWallpaper).getWallpaperType()) {
                        ImageWallpaper imageWallpaper2 = ImageWallpaper.this;
                        if (imageWallpaper2.mSubWallpaperType == 22) {
                            Bitmap wallpaperBitmap = ((CoverWallpaperController) imageWallpaper2.mCoverWallpaper).getWallpaperBitmap(true);
                            if (wallpaperBitmap != null) {
                                gifGLEngine2.mRenderer.setThumbnail(wallpaperBitmap, gifGLEngine2.getSurfaceHolder());
                            } else {
                                Log.i("ImageWallpaper[GifGLEngine]", " bitmap is null");
                            }
                            gifGLEngine2.mRenderer.setMediaPath(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperPath());
                            gifGLEngine2.mRenderer.updateGif(gifGLEngine2.getSurfaceHolder());
                            break;
                        }
                    } else {
                        ImageWallpaper imageWallpaper3 = ImageWallpaper.this;
                        imageWallpaper3.mSubWallpaperType = ((CoverWallpaperController) imageWallpaper3.mCoverWallpaper).getWallpaperType();
                        int i = ImageWallpaper.this.mSubWallpaperType;
                        if (i != -2 && 22 != i) {
                            if (LsRune.SUBSCREEN_WATCHFACE) {
                                WallpaperUtils.clearCachedWallpaper(17);
                                WallpaperUtils.clearCachedSmartCroppedRect(17);
                            }
                            WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).forceRebindWallpaper(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getCoverWhich());
                            break;
                        }
                    }
                }
                break;
            default:
                ImageWallpaper.GifGLEngine gifGLEngine3 = this.f$0;
                if (((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).isCoverWallpaperRequired()) {
                    Bitmap wallpaperBitmap2 = ((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperBitmap(true);
                    if (wallpaperBitmap2 != null) {
                        gifGLEngine3.mRenderer.setThumbnail(wallpaperBitmap2, gifGLEngine3.getSurfaceHolder());
                    } else {
                        Log.i("ImageWallpaper[GifGLEngine]", " bitmap is null");
                    }
                    gifGLEngine3.mRenderer.setMediaPath(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getWallpaperPath());
                } else {
                    Uri semGetUri = WallpaperManager.getInstance(ImageWallpaper.this.getApplicationContext()).semGetUri(((CoverWallpaperController) ImageWallpaper.this.mCoverWallpaper).getCoverWhich());
                    ImageWallpaperGifRenderer imageWallpaperGifRenderer3 = gifGLEngine3.mRenderer;
                    imageWallpaperGifRenderer3.getClass();
                    Log.i("ImageWallpaperGifRenderer", " setMediaUri : ");
                    try {
                        imageWallpaperGifRenderer3.setInputStreamToMovie("content".equals(semGetUri.getScheme()) ? imageWallpaperGifRenderer3.mContext.getContentResolver().openInputStream(semGetUri) : new FileInputStream(new File(semGetUri.getPath())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                gifGLEngine3.mRenderer.updateGif(gifGLEngine3.getSurfaceHolder());
                break;
        }
    }
}
