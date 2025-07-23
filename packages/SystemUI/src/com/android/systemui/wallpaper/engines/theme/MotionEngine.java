package com.android.systemui.wallpaper.engines.theme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.theme.MotionWallpaper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.samsung.android.wallpaper.Rune;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MotionEngine extends WallpaperEngine {
    public final String TAG;
    public final Context mContext;
    public MotionWallpaper mMotionWallpaper;
    public MotionSource mSource;
    public SurfaceHolder mSurfaceHolder;
    public boolean mVisible;

    public MotionEngine(Context context, WallpaperEngineCallback wallpaperEngineCallback) {
        super(wallpaperEngineCallback);
        this.TAG = "ImageWallpaper_" + getWhich() + "[Motion]";
        this.mContext = context;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        this.mMotionWallpaper = new MotionWallpaper(this.mContext, getWhich(), (Consumer<Integer>) new Consumer() { // from class: com.android.systemui.wallpaper.engines.theme.MotionEngine$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MotionEngine motionEngine = MotionEngine.this;
                ((Integer) obj).intValue();
                if (motionEngine.mVisible) {
                    Canvas lockHardwareCanvas = motionEngine.mSurfaceHolder.lockHardwareCanvas();
                    if (lockHardwareCanvas == null) {
                        Log.e(motionEngine.TAG, "onDrawFrame: canvas is null");
                    } else {
                        motionEngine.mMotionWallpaper.draw(lockHardwareCanvas);
                        motionEngine.mSurfaceHolder.unlockCanvasAndPost(lockHardwareCanvas);
                    }
                }
            }
        });
        this.mSource = new MotionSource(getWhich(), this.mMotionWallpaper);
        boolean isValid = surfaceHolder.getSurface().isValid();
        MotionSource motionSource = this.mSource;
        motionSource.getClass();
        boolean z = Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE;
        boolean isFlagEnabled = z ? WhichChecker.isFlagEnabled(motionSource.mWhich, 16) : true;
        Log.i(motionSource.TAG, "isFixedOrientation: , isFold=" + z + ", isFixedOrientation=" + isFlagEnabled);
        ImageWallpaper.IntegratedEngine.this.semSetFixedOrientation(isFlagEnabled, isValid);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
        this.mMotionWallpaper.measure(View.MeasureSpec.makeMeasureSpec(surfaceFrame.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(surfaceFrame.height(), 1073741824));
        this.mMotionWallpaper.layout(0, 0, surfaceFrame.width(), surfaceFrame.height());
        this.mMotionWallpaper.init();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        this.mVisible = z;
        if (z) {
            MotionWallpaper motionWallpaper = this.mMotionWallpaper;
            if (!motionWallpaper.mIsSensorRegistered) {
                Log.d("MotionWallpaper", "registerSensor");
                motionWallpaper.mSensorManager.registerListener(motionWallpaper, motionWallpaper.mInterruptedGyro, 1);
                motionWallpaper.mIsSensorRegistered = true;
            }
            motionWallpaper.init();
            return;
        }
        MotionWallpaper motionWallpaper2 = this.mMotionWallpaper;
        Log.d("MotionWallpaper", (motionWallpaper2.mIsPreview ? "(Preview)" : "").concat("onPause()"));
        if (motionWallpaper2.mIsSensorRegistered) {
            Log.d("MotionWallpaper", "unregisterSensor");
            motionWallpaper2.mSensorManager.unregisterListener(motionWallpaper2);
            motionWallpaper2.mIsSensorRegistered = false;
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }
}
