package com.android.systemui.wallpaper.engines.theme;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.Choreographer;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.theme.LockscreenCallback;
import com.android.systemui.wallpaper.theme.builder.ComplexAnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameAnimationView;

public final class AnimatedEngine extends WallpaperEngine {
    public final String TAG;
    public Choreographer mChoreographer;
    public ComplexAnimationBuilder mComplexAnimationBuilder;
    public final Context mContext;
    public final Object mLock;
    public FrameLayout mRootView;
    public boolean mShowing;
    public AnimatedSource mSource;
    public SurfaceHolder mSurfaceHolder;

    public AnimatedEngine(Context context, WallpaperEngineCallback wallpaperEngineCallback) {
        super(wallpaperEngineCallback);
        this.mShowing = false;
        this.mLock = new Object();
        this.TAG = "ImageWallpaper_" + getWhich() + "[Animated]";
        this.mContext = context;
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onCreate(SurfaceHolder surfaceHolder) {
        Log.i(this.TAG, "onCreate");
        setFixedOrientation(true, getSurfaceHolder().getSurface().isValid());
        this.mRootView = new FrameLayout(this.mContext);
        this.mChoreographer = Choreographer.getInstance();
        this.mSurfaceHolder = surfaceHolder;
        this.mSource = new AnimatedSource(this.mContext, getWhich(), this.mRootView);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            this.mComplexAnimationBuilder = this.mSource.getComplexAnimation();
        } catch (Throwable th) {
            Log.e(this.TAG, "failed to get apk resource : e = " + th, th);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
        this.mRootView.measure(View.MeasureSpec.makeMeasureSpec(surfaceFrame.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(surfaceFrame.height(), 1073741824));
        this.mRootView.layout(0, 0, surfaceFrame.width(), surfaceFrame.height());
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        synchronized (this.mLock) {
            this.mShowing = z;
        }
        if (!z) {
            ComplexAnimationBuilder complexAnimationBuilder = this.mComplexAnimationBuilder;
            if (complexAnimationBuilder != null) {
                FrameAnimationView frameAnimationView = complexAnimationBuilder.mFestivalSpriteView;
                if (frameAnimationView != null) {
                    frameAnimationView.screenTurnedOff();
                }
                LockscreenCallback lockscreenCallback = complexAnimationBuilder.mLockscreenCallback;
                if (lockscreenCallback != null) {
                    lockscreenCallback.screenTurnedOff();
                }
                complexAnimationBuilder.mAnimatorSet.cancel();
            }
            this.mChoreographer.removeFrameCallback(new AnimatedEngine$$ExternalSyntheticLambda0(this));
            return;
        }
        ComplexAnimationBuilder complexAnimationBuilder2 = this.mComplexAnimationBuilder;
        if (complexAnimationBuilder2 != null) {
            FrameAnimationView frameAnimationView2 = complexAnimationBuilder2.mFestivalSpriteView;
            if (frameAnimationView2 != null) {
                frameAnimationView2.screenTurnedOn();
            }
            LockscreenCallback lockscreenCallback2 = complexAnimationBuilder2.mLockscreenCallback;
            if (lockscreenCallback2 != null) {
                lockscreenCallback2.screenTurnedOn();
            }
            try {
                complexAnimationBuilder2.mAnimatorSet.start();
            } catch (UnsupportedOperationException unused) {
                Log.e("ComplexAnimationBuilder", "UnsupportedOperationException occurred!");
                try {
                    complexAnimationBuilder2.mAnimatorSet.start();
                } catch (UnsupportedOperationException unused2) {
                    Log.e("ComplexAnimationBuilder", "UnsupportedOperationException occurred again!");
                }
            }
        }
        this.mChoreographer.postFrameCallback(new AnimatedEngine$$ExternalSyntheticLambda0(this));
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }
}
