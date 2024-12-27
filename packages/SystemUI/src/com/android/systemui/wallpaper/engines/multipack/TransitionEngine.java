package com.android.systemui.wallpaper.engines.multipack;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.Choreographer;
import android.view.SurfaceHolder;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import com.samsung.android.wallpaper.live.sdk.utils.GraphicsUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TransitionEngine extends WallpaperEngine {
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
    /* JADX WARN: Type inference failed for: r13v15 */
    /* JADX WARN: Type inference failed for: r13v16 */
    /* JADX WARN: Type inference failed for: r13v5, types: [android.view.Surface] */
    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final synchronized boolean draw(SurfaceHolder surfaceHolder) {
        Bitmap bitmap;
        Bitmap bitmap2 = this.mPrevThumbnail;
        if (!((bitmap2 == null || bitmap2.isRecycled() || (bitmap = this.mNextThumbnail) == null || bitmap.isRecycled()) ? false : true)) {
            Log.w(this.TAG, "draw: invalid thumbnails");
            return false;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mStartTime;
        if (!surfaceHolder.getSurface().isValid()) {
            return false;
        }
        if (this.mRotation != DisplayUtils.getDisplayRotation(getWhich(), getAppContext())) {
            Log.d(this.TAG, "draw: animation finished due to rotation. elapsed=" + elapsedRealtime);
            release();
            return false;
        }
        Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
        Canvas lockHardwareCanvas = surfaceHolder.getSurface().lockHardwareCanvas();
        try {
            try {
                lockHardwareCanvas.save();
                Rect centerCropRect = GraphicsUtils.getCenterCropRect(this.mPrevThumbnail.getWidth(), this.mPrevThumbnail.getHeight(), surfaceFrame.width(), surfaceFrame.height());
                float width = surfaceFrame.width() / centerCropRect.width();
                lockHardwareCanvas.scale(width, width);
                lockHardwareCanvas.drawBitmap(this.mPrevThumbnail, -centerCropRect.left, -centerCropRect.top, (Paint) null);
                lockHardwareCanvas.restore();
                this.mPaint.setAlpha(Math.min(255, (int) ((elapsedRealtime * 255.0f) / this.TRANSITION_ANIMATION_DURATION)));
                lockHardwareCanvas.save();
                Rect centerCropRect2 = GraphicsUtils.getCenterCropRect(this.mNextThumbnail.getWidth(), this.mNextThumbnail.getHeight(), surfaceFrame.width(), surfaceFrame.height());
                float width2 = surfaceFrame.width() / centerCropRect2.width();
                lockHardwareCanvas.scale(width2, width2);
                lockHardwareCanvas.drawBitmap(this.mNextThumbnail, -centerCropRect2.left, -centerCropRect2.top, this.mPaint);
                lockHardwareCanvas.restore();
                surfaceHolder = surfaceHolder.getSurface();
            } catch (Exception e) {
                Log.e(this.TAG, "draw: e=" + e, e);
                surfaceHolder = surfaceHolder.getSurface();
            }
            surfaceHolder.unlockCanvasAndPost(lockHardwareCanvas);
            if (elapsedRealtime <= this.TRANSITION_ANIMATION_DURATION) {
                ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
                anonymousClass2.getClass();
                int i = ImageWallpaper.IntegratedEngine.$r8$clinit;
                if (ImageWallpaper.IntegratedEngine.this.isVisible()) {
                    return true;
                }
            }
            Log.d(this.TAG, "draw: animation finished due to time. elapsed=" + elapsedRealtime);
            release();
            return false;
        } catch (Throwable th) {
            surfaceHolder.getSurface().unlockCanvasAndPost(lockHardwareCanvas);
            throw th;
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(SurfaceHolder surfaceHolder) {
        boolean semIsFixedOrientationRequested;
        Log.d(this.TAG, "onCreate: prev=" + BitmapUtils.getBitmapSizeString(this.mPrevThumbnail) + ", next=" + BitmapUtils.getBitmapSizeString(this.mNextThumbnail));
        this.mPaint = new Paint();
        int displayRotation = DisplayUtils.getDisplayRotation(getWhich(), getAppContext());
        this.mRotation = displayRotation;
        if (displayRotation == 1 || displayRotation == 3) {
            semIsFixedOrientationRequested = ImageWallpaper.IntegratedEngine.this.semIsFixedOrientationRequested();
            if (semIsFixedOrientationRequested) {
                int convertDisplayRotationToAngle = DisplayUtils.convertDisplayRotationToAngle(this.mRotation);
                Bitmap cropRotateResizeBitmap = BitmapUtils.cropRotateResizeBitmap(this.mPrevThumbnail, null, convertDisplayRotationToAngle, 1.0f, true);
                Bitmap bitmap = this.mPrevThumbnail;
                if (cropRotateResizeBitmap != bitmap) {
                    bitmap.recycle();
                }
                this.mPrevThumbnail = cropRotateResizeBitmap;
                Bitmap cropRotateResizeBitmap2 = BitmapUtils.cropRotateResizeBitmap(this.mNextThumbnail, null, convertDisplayRotationToAngle, 1.0f, true);
                Bitmap bitmap2 = this.mNextThumbnail;
                if (cropRotateResizeBitmap2 != bitmap2) {
                    bitmap2.recycle();
                }
                this.mNextThumbnail = cropRotateResizeBitmap2;
            }
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.getClass();
        Choreographer.getInstance().removeFrameCallback(anonymousClass2.mChoreographerFrameCallback);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        Bitmap bitmap;
        Log.i(this.TAG, KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("onVisibilityChanged: visible = ", z));
        Bitmap bitmap2 = this.mPrevThumbnail;
        if (bitmap2 == null || bitmap2.isRecycled() || (bitmap = this.mNextThumbnail) == null || bitmap.isRecycled()) {
            release();
        } else if (z) {
            this.mStartTime = SystemClock.elapsedRealtime();
            ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
            anonymousClass2.getClass();
            Choreographer.getInstance().postFrameCallback(anonymousClass2.mChoreographerFrameCallback);
        }
    }

    public final void release() {
        ImageWallpaper.IntegratedEngine.AnonymousClass2 anonymousClass2 = (ImageWallpaper.IntegratedEngine.AnonymousClass2) this.mCallback;
        anonymousClass2.getClass();
        Choreographer.getInstance().removeFrameCallback(anonymousClass2.mChoreographerFrameCallback);
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
