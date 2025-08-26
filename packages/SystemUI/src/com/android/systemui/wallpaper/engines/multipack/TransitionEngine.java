package com.android.systemui.wallpaper.engines.multipack;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.android.systemui.wallpapers.ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda1;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import com.samsung.android.wallpaper.live.sdk.utils.GraphicsUtils;

/* loaded from: classes3.dex */
public class TransitionEngine extends WallpaperEngine {
    public final String TAG;
    public final long TRANSITION_ANIMATION_DURATION;
    public Bitmap mNextThumbnail;
    public Paint mPaint;
    public Bitmap mPrevThumbnail;
    public int mRotation;
    public long mStartTime;
    public final Runnable mTransitionFinishListener;

    public TransitionEngine(WallpaperEngineCallback wallpaperEngineCallback, Bitmap bitmap, Bitmap bitmap2, Runnable runnable) {
        super(wallpaperEngineCallback);
        this.TRANSITION_ANIMATION_DURATION = 500L;
        this.TAG = "ImageWallpaper_" + getWhich() + "[Transition]";
        this.mPrevThumbnail = bitmap;
        this.mNextThumbnail = bitmap2;
        this.mTransitionFinishListener = runnable;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:57:0x014d A[Catch: all -> 0x002b, TRY_ENTER, TryCatch #4 {, blocks: (B:4:0x0007, B:6:0x000d, B:8:0x0013, B:10:0x0017, B:15:0x0022, B:20:0x002e, B:24:0x0041, B:26:0x0051, B:32:0x00fc, B:33:0x0100, B:46:0x0126, B:48:0x012c, B:53:0x0135, B:57:0x014d, B:58:0x0154, B:45:0x0121), top: B:65:0x0007 }] */
    /* JADX WARN: Type inference failed for: r14v0, types: [android.view.SurfaceHolder] */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v2, types: [android.view.SurfaceHolder] */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v9, types: [android.view.Surface] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v2, types: [int] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v6, types: [android.graphics.Canvas] */
    /* JADX WARN: Type inference failed for: r8v9 */
    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized boolean draw(SurfaceHolder surfaceHolder) {
        Canvas canvasLockHardwareCanvas;
        Exception e;
        Bitmap bitmap;
        Bitmap bitmap2 = this.mPrevThumbnail;
        if (!((bitmap2 == null || bitmap2.isRecycled() || (bitmap = this.mNextThumbnail) == null || bitmap.isRecycled()) ? false : true)) {
            Log.w(this.TAG, "draw: invalid thumbnails");
            return false;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.mStartTime;
        if (!surfaceHolder.getSurface().isValid()) {
            return false;
        }
        int i = this.mRotation;
        ?? displayRotation = DisplayUtils.getDisplayRotation(getWhich(), getAppContext());
        if (i != displayRotation) {
            Log.d(this.TAG, "draw: animation finished due to rotation. elapsed=" + jElapsedRealtime);
            release$1();
            return false;
        }
        Canvas canvas = null;
        try {
            try {
                Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
                canvasLockHardwareCanvas = surfaceHolder.getSurface().lockHardwareCanvas();
                try {
                    canvasLockHardwareCanvas.save();
                    Rect centerCropRect = GraphicsUtils.getCenterCropRect(this.mPrevThumbnail.getWidth(), this.mPrevThumbnail.getHeight(), surfaceFrame.width(), surfaceFrame.height());
                    float fWidth = surfaceFrame.width() / centerCropRect.width();
                    canvasLockHardwareCanvas.scale(fWidth, fWidth);
                    canvasLockHardwareCanvas.drawBitmap(this.mPrevThumbnail, -centerCropRect.left, -centerCropRect.top, (Paint) null);
                    canvasLockHardwareCanvas.restore();
                    this.mPaint.setAlpha(Math.min(255, (int) ((jElapsedRealtime * 255.0f) / this.TRANSITION_ANIMATION_DURATION)));
                    canvasLockHardwareCanvas.save();
                    Rect centerCropRect2 = GraphicsUtils.getCenterCropRect(this.mNextThumbnail.getWidth(), this.mNextThumbnail.getHeight(), surfaceFrame.width(), surfaceFrame.height());
                    float fWidth2 = surfaceFrame.width() / centerCropRect2.width();
                    canvasLockHardwareCanvas.scale(fWidth2, fWidth2);
                    canvasLockHardwareCanvas.drawBitmap(this.mNextThumbnail, -centerCropRect2.left, -centerCropRect2.top, this.mPaint);
                    canvasLockHardwareCanvas.restore();
                    displayRotation = canvasLockHardwareCanvas;
                    surfaceHolder = surfaceHolder.getSurface();
                } catch (Exception e2) {
                    e = e2;
                    Log.e(this.TAG, "draw: e=" + e, e);
                    if (canvasLockHardwareCanvas != null) {
                        displayRotation = canvasLockHardwareCanvas;
                        surfaceHolder = surfaceHolder.getSurface();
                        surfaceHolder.unlockCanvasAndPost(displayRotation);
                    }
                    if (jElapsedRealtime > this.TRANSITION_ANIMATION_DURATION) {
                    }
                    Log.d(this.TAG, "draw: animation finished due to time. elapsed=" + jElapsedRealtime);
                    release$1();
                    return false;
                }
            } catch (Throwable th) {
                th = th;
                canvas = displayRotation;
                if (canvas != null) {
                    surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
                }
                throw th;
            }
        } catch (Exception e3) {
            canvasLockHardwareCanvas = null;
            e = e3;
        } catch (Throwable th2) {
            th = th2;
            if (canvas != null) {
            }
            throw th;
        }
        surfaceHolder.unlockCanvasAndPost(displayRotation);
        if (jElapsedRealtime > this.TRANSITION_ANIMATION_DURATION && isVisible()) {
            return true;
        }
        Log.d(this.TAG, "draw: animation finished due to time. elapsed=" + jElapsedRealtime);
        release$1();
        return false;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(SurfaceHolder surfaceHolder) throws NoSuchMethodException, SecurityException {
        Log.d(this.TAG, "onCreate: prev=" + BitmapUtils.getBitmapSizeString(this.mPrevThumbnail) + ", next=" + BitmapUtils.getBitmapSizeString(this.mNextThumbnail));
        this.mPaint = new Paint();
        int displayRotation = DisplayUtils.getDisplayRotation(getWhich(), getAppContext());
        this.mRotation = displayRotation;
        if ((displayRotation == 1 || displayRotation == 3) && ImageWallpaper.IntegratedEngine.this.semIsFixedOrientationRequested()) {
            int iConvertDisplayRotationToAngle = DisplayUtils.convertDisplayRotationToAngle(this.mRotation);
            Bitmap bitmapCropRotateResizeBitmap = BitmapUtils.cropRotateResizeBitmap(this.mPrevThumbnail, null, iConvertDisplayRotationToAngle, 1.0f, true);
            Bitmap bitmap = this.mPrevThumbnail;
            if (bitmapCropRotateResizeBitmap != bitmap) {
                bitmap.recycle();
            }
            this.mPrevThumbnail = bitmapCropRotateResizeBitmap;
            Bitmap bitmapCropRotateResizeBitmap2 = BitmapUtils.cropRotateResizeBitmap(this.mNextThumbnail, null, iConvertDisplayRotationToAngle, 1.0f, true);
            Bitmap bitmap2 = this.mNextThumbnail;
            if (bitmapCropRotateResizeBitmap2 != bitmap2) {
                bitmap2.recycle();
            }
            this.mNextThumbnail = bitmapCropRotateResizeBitmap2;
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.mChoreographerHandler.post(new ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda1(anonymousClass2, 0));
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        Bitmap bitmap;
        Log.i(this.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("onVisibilityChanged: visible = ", z));
        Bitmap bitmap2 = this.mPrevThumbnail;
        if (bitmap2 == null || bitmap2.isRecycled() || (bitmap = this.mNextThumbnail) == null || bitmap.isRecycled()) {
            release$1();
        } else if (z) {
            this.mStartTime = SystemClock.elapsedRealtime();
            ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
            anonymousClass2.mChoreographerHandler.post(new ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda1(anonymousClass2, 1));
        }
    }

    public final void release$1() {
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.mChoreographerHandler.post(new ImageWallpaper$IntegratedEngine$2$$ExternalSyntheticLambda1(anonymousClass2, 0));
        Bitmap bitmap = this.mPrevThumbnail;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mPrevThumbnail.recycle();
            this.mPrevThumbnail = null;
        }
        Bitmap bitmap2 = this.mNextThumbnail;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mNextThumbnail.recycle();
            this.mNextThumbnail = null;
        }
        Runnable runnable = this.mTransitionFinishListener;
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
    }
}
