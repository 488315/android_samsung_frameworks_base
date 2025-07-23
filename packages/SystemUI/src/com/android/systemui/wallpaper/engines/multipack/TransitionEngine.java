package com.android.systemui.wallpaper.engines.multipack;

import android.graphics.Bitmap;
import android.graphics.Paint;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* JADX WARN: Removed duplicated region for block: B:53:0x014d A[Catch: all -> 0x002b, TRY_ENTER, TryCatch #4 {, blocks: (B:4:0x0007, B:6:0x000d, B:8:0x0013, B:10:0x0017, B:14:0x0022, B:18:0x002e, B:22:0x0041, B:24:0x0051, B:32:0x00fc, B:33:0x0100, B:34:0x0126, B:36:0x012c, B:40:0x0135, B:53:0x014d, B:54:0x0154, B:48:0x0121), top: B:3:0x0007 }] */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean draw(android.view.SurfaceHolder r14) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.engines.multipack.TransitionEngine.draw(android.view.SurfaceHolder):boolean");
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
