package com.android.systemui.wallpaper.engines;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.animation.PathInterpolator;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0;
import com.android.systemui.util.DeviceType;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.nexus.video.BuildConfig;
import com.samsung.android.wallpaper.live.sdk.data.DisplayState;

/* loaded from: classes3.dex */
public class WallpaperAnimator {
    public float mCurrentScale;
    public final boolean mIsAnimationEnabled;
    public boolean mKeyguardState;
    public long mLastDrawingTime;
    public final SurfaceControl mSurfaceControl;
    public final SurfaceHolder mSurfaceHolder;
    public ValueAnimator mValueAnimator;
    public final AnonymousClass1 mAnimatorControlHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.wallpaper.engines.WallpaperAnimator.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what == 1000) {
                WallpaperAnimator.this.playDownScale();
            }
        }
    };
    public final String TAG = "ImageWallpaper[WallpaperAnimator]";

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.wallpaper.engines.WallpaperAnimator$1] */
    public WallpaperAnimator(SurfaceHolder surfaceHolder, SurfaceControl surfaceControl, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mIsAnimationEnabled = true;
        this.mSurfaceHolder = surfaceHolder;
        this.mSurfaceControl = surfaceControl;
        this.mKeyguardState = keyguardUpdateMonitor.isKeyguardVisible();
        if (DeviceType.isFactoryBinary()) {
            Log.i("ImageWallpaper[WallpaperAnimator]", "This is factory binary");
            this.mIsAnimationEnabled = false;
        }
    }

    public final void cancelReservedDownScaleAnimation() {
        AnonymousClass1 anonymousClass1 = this.mAnimatorControlHandler;
        if (anonymousClass1.hasMessages(1000)) {
            anonymousClass1.removeMessages(1000);
        }
    }

    public final void changeToUpScaleImmediately() {
        Log.d(this.TAG, "changeToUpScaleImmediately");
        release();
        onTransitionScaleChanged(1.025f);
    }

    public final boolean isPlaying() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        return valueAnimator != null && valueAnimator.isRunning();
    }

    public final void onDisplayStateChanged(DisplayState displayState, int i, boolean z, boolean z2, boolean z3) {
        StringBuilder sb = new StringBuilder("engineDisplayStateChanged: state = ");
        sb.append(displayState);
        sb.append(", which = ");
        sb.append(i);
        sb.append(", visible = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, z, ", isFullAodShown = ", z2, ", isMultipack = ");
        sb.append(z3);
        sb.append(", mKeyguardState = ");
        sb.append(this.mKeyguardState);
        Log.d(this.TAG, sb.toString());
        DisplayState displayState2 = DisplayState.ON;
        if (displayState != displayState2) {
            this.mKeyguardState = true;
        }
        if (WhichChecker.isSystemAndLock(i)) {
            if (z2) {
                if (displayState == displayState2) {
                    reserveDownScaleAnimation();
                    return;
                } else {
                    playUpScale();
                    return;
                }
            }
            if (displayState == displayState2 || z) {
                return;
            }
            changeToUpScaleImmediately();
            return;
        }
        if (z3) {
            if (z2) {
                if (displayState == displayState2) {
                    reserveDownScaleAnimation();
                    return;
                } else {
                    playUpScale();
                    return;
                }
            }
            if (displayState != displayState2 || z) {
                return;
            }
            changeToUpScaleImmediately();
            return;
        }
        if (z2) {
            if (displayState == displayState2) {
                reserveDownScaleAnimation();
                return;
            } else {
                playUpScale();
                return;
            }
        }
        if (displayState == displayState2 || z) {
            return;
        }
        changeToUpScaleImmediately();
    }

    public final void onEngineVisibilityChanged(int i, boolean z, boolean z2, boolean z3) {
        StringBuilder sbM = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("onEngineVisibilityChanged: visible = ", i, ", which = ", z, ", isFullAodShown = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z2, ", isMultipack = ", z3, ", mKeyguardState = ");
        sbM.append(this.mKeyguardState);
        String string = sbM.toString();
        String str = this.TAG;
        Log.d(str, string);
        if (WhichChecker.isSystemAndLock(i)) {
            if (!this.mKeyguardState) {
                if (isPlaying()) {
                    return;
                }
                Log.d(str, "changeToDownScaleImmediately");
                release();
                onTransitionScaleChanged(1.0f);
                return;
            }
            if (z2) {
                if (z) {
                    return;
                }
                changeToUpScaleImmediately();
                return;
            } else if (z) {
                playDownScale();
                return;
            } else {
                changeToUpScaleImmediately();
                return;
            }
        }
        if (!z3) {
            if (z2) {
                return;
            }
            if (z) {
                playDownScale();
                return;
            } else {
                changeToUpScaleImmediately();
                return;
            }
        }
        if (z2) {
            if (z) {
                return;
            }
            changeToUpScaleImmediately();
        } else if (z) {
            playDownScale();
        } else {
            changeToUpScaleImmediately();
        }
    }

    public final void onTransitionScaleChanged(float f) {
        if (this.mIsAnimationEnabled) {
            SurfaceHolder surfaceHolder = this.mSurfaceHolder;
            String str = this.TAG;
            if (surfaceHolder == null || !surfaceHolder.getSurface().isValid()) {
                Log.w(str, "onTransitionScaleChanged: mSurfaceHolder = " + this.mSurfaceHolder);
                return;
            }
            try {
                Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
                float f2 = 1.0f - f;
                scaleSurface(new Rect((int) ((surfaceFrame.width() * f2) + surfaceFrame.left), (int) ((surfaceFrame.height() * f2) + surfaceFrame.top), (int) (surfaceFrame.right - (surfaceFrame.width() * f2)), (int) (surfaceFrame.bottom - (surfaceFrame.height() * f2))));
                this.mCurrentScale = f;
            } catch (Exception e) {
                WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("onTransitionScaleChanged: e = ", e, str, e);
            }
        }
    }

    public final void playDownScale() {
        Log.d(this.TAG, "playDownScale");
        cancelReservedDownScaleAnimation();
        startAnimation(isPlaying() ? this.mCurrentScale : 1.025f, 1.0f);
    }

    public final void playUpScale() {
        Log.d(this.TAG, "playUpScale");
        cancelReservedDownScaleAnimation();
        startAnimation(isPlaying() ? this.mCurrentScale : 1.0f, 1.025f);
    }

    public final void release() {
        Log.i(this.TAG, BuildConfig.BUILD_TYPE);
        cancelReservedDownScaleAnimation();
        if (isPlaying()) {
            this.mValueAnimator.removeAllUpdateListeners();
            this.mValueAnimator.removeAllListeners();
            this.mValueAnimator.end();
            this.mValueAnimator = null;
        }
    }

    public final void reserveDownScaleAnimation() {
        Log.d(this.TAG, "reserveDownScaleAnimation: delay = 240");
        cancelReservedDownScaleAnimation();
        sendEmptyMessageDelayed(1000, 240L);
    }

    public final void scaleSurface(Rect rect) {
        boolean zIsEmpty = rect.isEmpty();
        String str = this.TAG;
        if (zIsEmpty) {
            Log.w(str, "scaleSurface: visibleRect = " + rect);
            return;
        }
        Rect surfaceFrame = this.mSurfaceHolder.getSurfaceFrame();
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl == null || !surfaceControl.isValid() || surfaceFrame.isEmpty()) {
            Log.w(str, "scaleSurface: mSurfaceHolder = " + this.mSurfaceControl + ", frame = " + surfaceFrame);
            return;
        }
        this.mSurfaceHolder.mSurfaceLock.lock();
        try {
            new SurfaceControl.Transaction().setGeometry(this.mSurfaceControl, surfaceFrame, rect, 0).apply();
        } catch (Exception e) {
            Log.e(str, "scaleSurface: e = " + e, e);
        } finally {
            this.mSurfaceHolder.mSurfaceLock.unlock();
        }
    }

    public final void startAnimation(float f, float f2) {
        Log.d(this.TAG, "startAnimation: " + f + " -> " + f2);
        release();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        this.mValueAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(600L);
        this.mValueAnimator.setInterpolator(new PathInterpolator(0.17f, 0.17f, 0.4f, 1.0f));
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.wallpaper.engines.WallpaperAnimator.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                WallpaperAnimator.this.mLastDrawingTime = SystemClock.elapsedRealtime();
            }
        });
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.wallpaper.engines.WallpaperAnimator$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                WallpaperAnimator wallpaperAnimator = this.f$0;
                wallpaperAnimator.getClass();
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                if (jElapsedRealtime - wallpaperAnimator.mLastDrawingTime < 12) {
                    return;
                }
                wallpaperAnimator.mLastDrawingTime = jElapsedRealtime;
                wallpaperAnimator.onTransitionScaleChanged(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.mValueAnimator.start();
    }
}
