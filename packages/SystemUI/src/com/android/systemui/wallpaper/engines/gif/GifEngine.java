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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            public final void run() {
                Bitmap decodeBitmap;
                GifEngine gifEngine = GifEngine.this;
                GifSource gifSource2 = gifEngine.mSource;
                gifSource2.getClass();
                File file = new File(gifSource2.mGifPath);
                if (file.exists()) {
                    try {
                        decodeBitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(file));
                    } catch (IOException e) {
                        Log.e(gifSource2.TAG, "decodeFirstFrameBitmap:  e = " + e, e);
                    }
                    gifEngine.mInitialBitmap = decodeBitmap;
                }
                decodeBitmap = null;
                gifEngine.mInitialBitmap = decodeBitmap;
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
    public final boolean draw(SurfaceHolder surfaceHolder) {
        int width;
        int height;
        String str = this.TAG;
        if (this.mGif == null || this.mDestroyed) {
            return false;
        }
        Rect gifSize = getGifSize();
        if (gifSize == null || !gifSize.isValid()) {
            width = getSurfaceHolder().getSurfaceFrame().width();
            height = getSurfaceHolder().getSurfaceFrame().height();
        } else {
            width = gifSize.width();
            height = gifSize.height();
        }
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.mGif.draw(new Canvas(createBitmap));
        Canvas canvas = null;
        try {
            try {
                Canvas lockHardwareCanvas = surfaceHolder.getSurface().lockHardwareCanvas();
                try {
                    lockHardwareCanvas.drawBitmap(createBitmap, getCenterCropMatrix(), null);
                    try {
                        createBitmap.recycle();
                        surfaceHolder.getSurface().unlockCanvasAndPost(lockHardwareCanvas);
                        return true;
                    } catch (Exception e) {
                        WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("draw: e = ", e, str, e);
                        return true;
                    }
                } catch (Exception e2) {
                    e = e2;
                    canvas = lockHardwareCanvas;
                    Log.e(str, "draw: e = " + e, e);
                    try {
                        createBitmap.recycle();
                        if (canvas == null) {
                            return false;
                        }
                        surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
                        return false;
                    } catch (Exception e3) {
                        WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("draw: e = ", e3, str, e3);
                        return false;
                    }
                } catch (Throwable th) {
                    th = th;
                    canvas = lockHardwareCanvas;
                    try {
                        createBitmap.recycle();
                        if (canvas != null) {
                            surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
                        }
                    } catch (Exception e4) {
                        WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("draw: e = ", e4, str, e4);
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
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
        float max = Math.max(surfaceFrame.width() / gifSize.width(), surfaceFrame.height() / gifSize.height());
        matrix.setScale(max, max);
        matrix.postTranslate(SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(gifSize.width(), max, surfaceFrame.width(), 2.0f), SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(gifSize.height(), max, surfaceFrame.height(), 2.0f));
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

    public final void pauseAndSeekToFirstFrame() {
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
                    Canvas lockHardwareCanvas = surfaceHolder.getSurface().lockHardwareCanvas();
                    try {
                        lockHardwareCanvas.drawBitmap(this.mInitialBitmap, getCenterCropMatrix(), null);
                        surfaceHolder.getSurface().unlockCanvasAndPost(lockHardwareCanvas);
                    } catch (Exception e) {
                        e = e;
                        canvas = lockHardwareCanvas;
                        Log.e(str2, "drawFirstFrame: " + e, e);
                        if (canvas != null) {
                            surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
                        }
                    } catch (Throwable th) {
                        th = th;
                        canvas = lockHardwareCanvas;
                        if (canvas != null) {
                            try {
                                surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
                            } catch (Exception e2) {
                                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFirstFrame: ", e2, str2, e2);
                            }
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e4) {
            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("drawFirstFrame: ", e4, str2, e4);
        }
    }

    public final synchronized void updatePlayerState() {
        try {
            if (this.mGif != null && !this.mDestroyed) {
                boolean isVisible = isVisible();
                boolean z = this.mPlayerPausedForcefully;
                Log.i(this.TAG, "updatePlayerState: isVisible = " + isVisible + ", mDisplayState = " + this.mDisplayState + ", isPausedForcefully = " + z);
                if (!isVisible) {
                    pauseAndSeekToFirstFrame();
                } else if (z) {
                    pause();
                } else {
                    DisplayState displayState = this.mDisplayState;
                    if (displayState != DisplayState.AOD_WITH_WALLPAPER && displayState != DisplayState.AOD_WITHOUT_WALLPAPER) {
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
                    pause();
                }
                return;
            }
            Log.i(this.TAG, "updatePlayerState: mDestroyed = " + this.mDestroyed);
        } finally {
        }
    }
}
