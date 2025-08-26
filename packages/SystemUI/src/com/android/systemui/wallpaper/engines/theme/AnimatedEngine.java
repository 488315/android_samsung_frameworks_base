package com.android.systemui.wallpaper.engines.theme;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.Choreographer;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import com.android.systemui.wallpaper.engines.WallpaperEngine;
import com.android.systemui.wallpaper.engines.WallpaperEngineCallback;
import com.android.systemui.wallpaper.theme.LockscreenCallback;
import com.android.systemui.wallpaper.theme.builder.ComplexAnimationBuilder;
import com.android.systemui.wallpaper.theme.view.FrameAnimationView;
import com.android.systemui.wallpapers.ImageWallpaper;

/* loaded from: classes3.dex */
public class AnimatedEngine extends WallpaperEngine {
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
        this.mRootView = new FrameLayout(this.mContext);
        this.mChoreographer = Choreographer.getInstance();
        this.mSurfaceHolder = surfaceHolder;
        this.mSource = new AnimatedSource(this.mContext, getWhich(), this.mRootView);
        boolean zIsValid = getSurfaceHolder().getSurface().isValid();
        ImageWallpaper.IntegratedEngine.this.semSetFixedOrientation(this.mSource.isFixedOrientation(), zIsValid);
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            this.mComplexAnimationBuilder = this.mSource.createComplexAnimation(0, 0);
        } catch (Throwable th) {
            Log.e(this.TAG, "failed to get apk resource : e = " + th, th);
        }
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        ComplexAnimationBuilder complexAnimationBuilder;
        ComplexAnimationBuilder complexAnimationBuilder2 = this.mComplexAnimationBuilder;
        if (complexAnimationBuilder2 != null) {
            FrameAnimationView frameAnimationView = complexAnimationBuilder2.mFestivalSpriteView;
            if (frameAnimationView != null) {
                frameAnimationView.screenTurnedOff();
            }
            LockscreenCallback lockscreenCallback = complexAnimationBuilder2.mLockscreenCallback;
            if (lockscreenCallback != null) {
                lockscreenCallback.screenTurnedOff();
            }
            complexAnimationBuilder2.mAnimatorSet.cancel();
        }
        try {
            this.mComplexAnimationBuilder = this.mSource.createComplexAnimation(0, 0);
        } catch (Throwable th) {
            Log.e(this.TAG, RowInflaterTask$$ExternalSyntheticOutline0.m("failed to get apk resource : e = ", th), th);
        }
        this.mSurfaceHolder = surfaceHolder;
        Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
        this.mRootView.measure(View.MeasureSpec.makeMeasureSpec(surfaceFrame.width(), 1073741824), View.MeasureSpec.makeMeasureSpec(surfaceFrame.height(), 1073741824));
        this.mRootView.layout(0, 0, surfaceFrame.width(), surfaceFrame.height());
        if (!this.mShowing || (complexAnimationBuilder = this.mComplexAnimationBuilder) == null) {
            return;
        }
        complexAnimationBuilder.playAnimation();
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onVisibilityChanged(boolean z) {
        synchronized (this.mLock) {
            this.mShowing = z;
        }
        if (z) {
            ComplexAnimationBuilder complexAnimationBuilder = this.mComplexAnimationBuilder;
            if (complexAnimationBuilder != null) {
                complexAnimationBuilder.playAnimation();
            }
            this.mChoreographer.postFrameCallback(new AnimatedEngine$$ExternalSyntheticLambda0(this));
            return;
        }
        ComplexAnimationBuilder complexAnimationBuilder2 = this.mComplexAnimationBuilder;
        if (complexAnimationBuilder2 != null) {
            FrameAnimationView frameAnimationView = complexAnimationBuilder2.mFestivalSpriteView;
            if (frameAnimationView != null) {
                frameAnimationView.screenTurnedOff();
            }
            LockscreenCallback lockscreenCallback = complexAnimationBuilder2.mLockscreenCallback;
            if (lockscreenCallback != null) {
                lockscreenCallback.screenTurnedOff();
            }
            complexAnimationBuilder2.mAnimatorSet.cancel();
        }
        this.mChoreographer.removeFrameCallback(new AnimatedEngine$$ExternalSyntheticLambda0(this));
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onDestroy() {
    }

    @Override // com.android.systemui.wallpaper.engines.WallpaperEngine
    public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }
}
