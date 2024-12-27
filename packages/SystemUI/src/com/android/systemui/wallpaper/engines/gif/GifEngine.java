package com.android.systemui.wallpaper.engines.gif;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Choreographer;
import android.view.SurfaceHolder;
import androidx.compose.ui.graphics.colorspace.Rgb$Companion$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GifEngine extends WallpaperEngine {
    public final String TAG;
    public AnimatedImageDrawable mGif;
    public final String mGifPath;
    public boolean mIsWatchFaceGoingToSleep;
    public boolean mIsWatchFacePauseByCommand;
    public PowerManager mPm;

    public GifEngine(GifSource gifSource, WallpaperEngineCallback wallpaperEngineCallback) {
        super(wallpaperEngineCallback);
        this.mIsWatchFaceGoingToSleep = false;
        this.mIsWatchFacePauseByCommand = false;
        this.TAG = "ImageWallpaper_" + getWhich() + "[Gif]";
        this.mGifPath = gifSource.getGifPath();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final boolean draw(SurfaceHolder surfaceHolder) {
        float f;
        float f2;
        if (this.mGif == null) {
            return false;
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(surfaceHolder.getSurfaceFrame().width(), surfaceHolder.getSurfaceFrame().height(), Bitmap.Config.ARGB_8888);
            this.mGif.draw(new Canvas(createBitmap));
            Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
            Matrix matrix = new Matrix();
            int width = createBitmap.getWidth();
            int height = createBitmap.getHeight();
            int width2 = surfaceFrame.width();
            int height2 = surfaceFrame.height();
            if (width * height2 > width2 * height) {
                f = height2;
                f2 = height;
            } else {
                f = width2;
                f2 = width;
            }
            float f3 = (f / f2) * 1.0f;
            float m = Rgb$Companion$$ExternalSyntheticOutline0.m(width, f3, width2, 0.5f);
            float m2 = Rgb$Companion$$ExternalSyntheticOutline0.m(height, f3, height2, 0.5f);
            matrix.setScale(f3, f3);
            matrix.postTranslate(Math.round(m), Math.round(m2));
            Canvas lockHardwareCanvas = surfaceHolder.getSurface().lockHardwareCanvas();
            Paint paint = new Paint();
            paint.setAlpha(255);
            try {
                lockHardwareCanvas.drawBitmap(createBitmap, matrix, paint);
                surfaceHolder.getSurface().unlockCanvasAndPost(lockHardwareCanvas);
                return true;
            } catch (Throwable th) {
                surfaceHolder.getSurface().unlockCanvasAndPost(lockHardwareCanvas);
                throw th;
            }
        } catch (Exception e) {
            Log.e(this.TAG, "draw:" + e, e);
            return false;
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) {
        boolean isInteractive = this.mPm.isInteractive();
        Log.d(this.TAG, "onCommand: " + str + ", isInteractive = " + isInteractive);
        if (!WhichChecker.isWatchFace(getWhich())) {
            WhichChecker.isVirtualDisplay(getWhich());
            return;
        }
        str.getClass();
        switch (str) {
            case "android.wallpaper.wakingup":
                this.mIsWatchFaceGoingToSleep = false;
                if (!this.mIsWatchFacePauseByCommand) {
                    play();
                    break;
                }
                break;
            case "samsung.android.wallpaper.resume":
                this.mIsWatchFacePauseByCommand = false;
                if (!this.mIsWatchFaceGoingToSleep && isInteractive) {
                    play();
                    break;
                }
                break;
            case "samsung.android.wallpaper.pause":
                this.mIsWatchFacePauseByCommand = true;
                if (!this.mIsWatchFaceGoingToSleep) {
                    stop();
                    break;
                }
                break;
            case "android.wallpaper.goingtosleep":
                this.mIsWatchFaceGoingToSleep = true;
                stop();
                break;
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(SurfaceHolder surfaceHolder) {
        String str = this.TAG;
        Log.d(str, "onCreate");
        this.mPm = (PowerManager) getAppContext().getSystemService("power");
        try {
            this.mGif = (AnimatedImageDrawable) AnimatedImageDrawable.createFromPath(this.mGifPath);
        } catch (Exception e) {
            Log.e(str, "setMediaPath: " + e, e);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
        Log.d(this.TAG, "onDestroy");
        stop();
        this.mGif = null;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.getClass();
        int i = ImageWallpaper.IntegratedEngine.$r8$clinit;
        DisplayState displayState = ImageWallpaper.IntegratedEngine.this.getDisplayState();
        boolean isInteractive = this.mPm.isInteractive();
        Log.d(this.TAG, "onVisibilityChanged: visible = " + z + ", displayState = " + displayState + ", isInteractive = " + isInteractive);
        if (!WhichChecker.isWatchFace(getWhich())) {
            if (WhichChecker.isVirtualDisplay(getWhich())) {
                if (z) {
                    play();
                    return;
                } else {
                    stop();
                    return;
                }
            }
            return;
        }
        if (!z) {
            stop();
        } else if ((displayState != DisplayState.AOD_WITH_WALLPAPER || isInteractive) && !this.mIsWatchFacePauseByCommand) {
            play();
        }
    }

    public final void play() {
        if (this.mGif == null) {
            return;
        }
        Log.d(this.TAG, "play");
        this.mGif.start();
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.getClass();
        Choreographer.getInstance().postFrameCallback(anonymousClass2.mChoreographerFrameCallback);
    }

    public final void stop() {
        if (this.mGif == null) {
            return;
        }
        Log.d(this.TAG, "stop");
        this.mGif.stop();
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.getClass();
        Choreographer.getInstance().removeFrameCallback(anonymousClass2.mChoreographerFrameCallback);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onStartedWakingUp() {
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSwitchDisplayChanged(boolean z) {
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }
}
