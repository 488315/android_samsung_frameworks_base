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
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MotionEngine extends WallpaperEngine {
    public final Context mContext;
    public MotionWallpaper mMotionWallpaper;
    public SurfaceHolder mSurfaceHolder;
    public boolean mVisible;

    public MotionEngine(Context context, WallpaperEngineCallback wallpaperEngineCallback) {
        super(wallpaperEngineCallback);
        getWhich();
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
                    motionEngine.mMotionWallpaper.draw(lockHardwareCanvas);
                    motionEngine.mSurfaceHolder.unlockCanvasAndPost(lockHardwareCanvas);
                }
            }
        });
        setFixedOrientation(true, surfaceHolder.getSurface().isValid());
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
        this.mMotionWallpaper.measure(View.MeasureSpec.makeMeasureSpec(surfaceFrame.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(surfaceFrame.height(), 1073741824));
        this.mMotionWallpaper.layout(0, 0, surfaceFrame.width(), surfaceFrame.height());
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
