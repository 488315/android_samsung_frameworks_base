package com.android.systemui.wallpaper.engines.gif;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import androidx.appcompat.animation.SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.android.systemui.wallpapers.ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda1;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import java.io.File;
import java.io.IOException;

/* loaded from: classes3.dex */
public class GifEngine extends WallpaperEngine {
    public final String TAG;
    public boolean mDestroyed;
    public DisplayState mDisplayState;
    public AnimatedImageDrawable mGif;
    public Bitmap mInitialBitmap;
    public boolean mPlayerPausedForcefully;
    public final GifSource mSource;
    public int mState;

    public GifEngine(GifSource gifSource, WallpaperEngineCallback wallpaperEngineCallback) {
        super(wallpaperEngineCallback);
        this.mState = 0;
        this.mDestroyed = false;
        this.mPlayerPausedForcefully = false;
        this.mDisplayState = DisplayState.NONE;
        String str = "ImageWallpaper_" + getWhich() + "[Gif]";
        this.TAG = str;
        WallpaperEngineCallback wallpaperEngineCallback2 = this.mCallback;
        WallpaperLogger wallpaperLogger = ImageWallpaper.this.mLogger;
        this.mSource = gifSource;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.wallpaper.engines.gif.GifEngine$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                Bitmap bitmapDecodeBitmap;
                GifEngine gifEngine = this.f$0;
                GifSource gifSource2 = gifEngine.mSource;
                gifSource2.getClass();
                File file = new File(gifSource2.mGifPath);
                if (file.exists()) {
                    try {
                        bitmapDecodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(file));
                    } catch (IOException e) {
                        Log.e(gifSource2.TAG, "decodeFirstFrameBitmap:  e = " + e, e);
                    }
                } else {
                    bitmapDecodeBitmap = null;
                }
                gifEngine.mInitialBitmap = bitmapDecodeBitmap;
            }
        };
        Handler threadHandler = ImageWallpaper.this.mWorker.getThreadHandler();
        if (threadHandler == null) {
            Log.e(str, "runAsWorkerThread: workerHandler is null");
        } else {
            threadHandler.post(runnable);
        }
        String str2 = gifSource.mGifPath;
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.getClass();
        int i = ImageWallpaper.IntegratedEngine.$r8$clinit;
        this.mDisplayState = ImageWallpaper.IntegratedEngine.this.getDisplayState();
        WallpaperLoggerImpl wallpaperLoggerImpl = (WallpaperLoggerImpl) wallpaperLogger;
        wallpaperLoggerImpl.log(str, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("init: path = ", str2));
        try {
            this.mGif = (AnimatedImageDrawable) AnimatedImageDrawable.createFromPath(str2);
        } catch (Exception e) {
            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("init: e = ", e, str, e);
            wallpaperLoggerImpl.log(str, "init failed");
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final boolean draw(SurfaceHolder surfaceHolder) throws Throwable {
        int iWidth;
        int iHeight;
        Canvas canvasLockHardwareCanvas;
        String str = this.TAG;
        if (this.mGif == null || this.mDestroyed) {
            return false;
        }
        Rect gifSize = getGifSize();
        if (gifSize == null || !gifSize.isValid()) {
            iWidth = getSurfaceHolder().getSurfaceFrame().width();
            iHeight = getSurfaceHolder().getSurfaceFrame().height();
        } else {
            iWidth = gifSize.width();
            iHeight = gifSize.height();
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iWidth, iHeight, Bitmap.Config.ARGB_8888);
        this.mGif.draw(new Canvas(bitmapCreateBitmap));
        Canvas canvas = null;
        try {
            try {
                canvasLockHardwareCanvas = surfaceHolder.getSurface().lockHardwareCanvas();
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            canvasLockHardwareCanvas.drawBitmap(bitmapCreateBitmap, getCenterCropMatrix(), null);
            try {
                bitmapCreateBitmap.recycle();
                surfaceHolder.getSurface().unlockCanvasAndPost(canvasLockHardwareCanvas);
                return true;
            } catch (Exception e2) {
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("draw: e = ", e2, str, e2);
                return true;
            }
        } catch (Exception e3) {
            e = e3;
            canvas = canvasLockHardwareCanvas;
            Log.e(str, "draw: e = " + e, e);
            try {
                bitmapCreateBitmap.recycle();
                if (canvas == null) {
                    return false;
                }
                surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
                return false;
            } catch (Exception e4) {
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("draw: e = ", e4, str, e4);
                return false;
            }
        } catch (Throwable th2) {
            th = th2;
            canvas = canvasLockHardwareCanvas;
            try {
                bitmapCreateBitmap.recycle();
                if (canvas != null) {
                    surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
                }
            } catch (Exception e5) {
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("draw: e = ", e5, str, e5);
            }
            throw th;
        }
    }

    public final Matrix getCenterCropMatrix() {
        Matrix matrix = new Matrix();
        Rect gifSize = getGifSize();
        String str = this.TAG;
        if (gifSize == null || !gifSize.isValid()) {
            Log.w(str, "getCenterCropMatrix: gifSize = " + gifSize);
            return matrix;
        }
        Rect surfaceFrame = getSurfaceHolder().getSurfaceFrame();
        if (surfaceFrame == null || surfaceFrame.isEmpty()) {
            Log.w(str, "getCenterCropMatrix: frame is empty");
            return matrix;
        }
        float fMax = Math.max(surfaceFrame.width() / gifSize.width(), surfaceFrame.height() / gifSize.height());
        matrix.setScale(fMax, fMax);
        matrix.postTranslate(SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(gifSize.width(), fMax, surfaceFrame.width(), 2.0f), SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(gifSize.height(), fMax, surfaceFrame.height(), 2.0f));
        return matrix;
    }

    public final Rect getGifSize() {
        if (this.mGif != null) {
            return new Rect(0, 0, this.mGif.getIntrinsicWidth(), this.mGif.getIntrinsicHeight());
        }
        Log.e(this.TAG, "getGifSize: gif is null");
        return null;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        Log.i(this.TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onCommand: action = ", str));
        str.getClass();
        switch (str) {
            case "android.wallpaper.wakingup":
            case "samsung.android.wallpaper.resume":
                this.mPlayerPausedForcefully = false;
                break;
            case "samsung.android.wallpaper.pause":
                this.mPlayerPausedForcefully = true;
                break;
        }
        updatePlayerState();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(SurfaceHolder surfaceHolder) {
        Log.i(this.TAG, "onCreate");
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
        Log.i(this.TAG, "onDestroy");
        this.mDestroyed = true;
        pause();
        this.mGif = null;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDisplayStateChanged(DisplayState displayState, DisplayState displayState2) {
        Log.i(this.TAG, "onDisplayStateChanged: " + displayState2 + " -> " + displayState);
        this.mDisplayState = displayState;
        updatePlayerState();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        Log.i(this.TAG, "onVisibilityChanged: visible = " + z);
        updatePlayerState();
    }

    public final void pause() {
        Log.i(this.TAG, "pause: curState=" + this.mState);
        int i = this.mState;
        if (i == 1 || i == 3) {
            return;
        }
        this.mState = 1;
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.mChoreographerHandler.post(new ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda1(anonymousClass2, 0));
        AnimatedImageDrawable animatedImageDrawable = this.mGif;
        if (animatedImageDrawable != null) {
            animatedImageDrawable.stop();
        }
    }

    public final void pauseAndSeekToFirstFrame() throws Throwable {
        Canvas canvasLockHardwareCanvas;
        String str = "pauseAndSeekToFirstFrame: curState=" + this.mState;
        String str2 = this.TAG;
        Log.i(str2, str);
        if (this.mState == 3) {
            return;
        }
        pause();
        this.mState = 3;
        if (this.mInitialBitmap == null || this.mDestroyed) {
            return;
        }
        SurfaceHolder surfaceHolder = getSurfaceHolder();
        Canvas canvas = null;
        try {
            try {
                try {
                    canvasLockHardwareCanvas = surfaceHolder.getSurface().lockHardwareCanvas();
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFirstFrame: ", e2, str2, e2);
        }
        try {
            canvasLockHardwareCanvas.drawBitmap(this.mInitialBitmap, getCenterCropMatrix(), null);
            surfaceHolder.getSurface().unlockCanvasAndPost(canvasLockHardwareCanvas);
        } catch (Exception e3) {
            e = e3;
            canvas = canvasLockHardwareCanvas;
            Log.e(str2, "drawFirstFrame: " + e, e);
            if (canvas != null) {
                surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
            }
        } catch (Throwable th2) {
            th = th2;
            canvas = canvasLockHardwareCanvas;
            if (canvas != null) {
                try {
                    surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
                } catch (Exception e4) {
                    WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFirstFrame: ", e4, str2, e4);
                }
            }
            throw th;
        }
    }

    public final synchronized void updatePlayerState() {
        DisplayState displayState;
        try {
            if (this.mGif != null && !this.mDestroyed) {
                boolean zIsVisible = isVisible();
                boolean z = this.mPlayerPausedForcefully;
                Log.i(this.TAG, "updatePlayerState: isVisible = " + zIsVisible + ", mDisplayState = " + this.mDisplayState + ", isPausedForcefully = " + z);
                if (!zIsVisible) {
                    pauseAndSeekToFirstFrame();
                } else if (z || (displayState = this.mDisplayState) == DisplayState.AOD_WITH_WALLPAPER || displayState == DisplayState.AOD_WITHOUT_WALLPAPER) {
                    pause();
                } else {
                    Log.i(this.TAG, "play: curState=" + this.mState);
                    if (this.mState != 2) {
                        this.mState = 2;
                        AnimatedImageDrawable animatedImageDrawable = this.mGif;
                        if (animatedImageDrawable != null) {
                            animatedImageDrawable.start();
                        }
                        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
                        anonymousClass2.mChoreographerHandler.post(new ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda1(anonymousClass2, 1));
                    }
                }
                return;
            }
            Log.i(this.TAG, "updatePlayerState: mDestroyed = " + this.mDestroyed);
        } finally {
        }
    }
}
