package com.android.systemui.animation.back;

import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.animation.Interpolator;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import com.android.app.animation.Interpolators;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.FlingAnimation;
import com.android.internal.dynamicanimation.animation.FloatValueHolder;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FlingOnBackAnimationCallback implements OnBackAnimationCallback {
    public FlingAnimation backInvokedFlingAnim;
    public final FlingOnBackAnimationCallback$backInvokedFlingEndListener$1 backInvokedFlingEndListener;
    public final FlingOnBackAnimationCallback$backInvokedFlingUpdateListener$1 backInvokedFlingUpdateListener;
    public Long downTime;
    public BackEvent lastBackEvent;
    public final Interpolator progressInterpolator;
    public final VelocityTracker velocityTracker;

    public FlingOnBackAnimationCallback() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // android.window.OnBackAnimationCallback
    public final void onBackCancelled() {
        onBackCancelledCompat();
        reset();
    }

    public abstract void onBackCancelledCompat();

    @Override // android.window.OnBackInvokedCallback
    public final void onBackInvoked() {
        if (this.lastBackEvent == null) {
            onBackInvokedCompat();
            reset();
            return;
        }
        this.velocityTracker.computeCurrentVelocity(1000);
        FlingAnimation flingAnimation = new FlingAnimation(new FloatValueHolder());
        BackEvent backEvent = this.lastBackEvent;
        FlingAnimation maxValue = flingAnimation.setStartValue((backEvent != null ? backEvent.getProgress() : 0.0f) * 100.0f).setFriction(6.0f).setStartVelocity(this.velocityTracker.getXVelocity()).setMinValue(0.0f).setMaxValue(100.0f);
        maxValue.addUpdateListener(this.backInvokedFlingUpdateListener);
        maxValue.addEndListener(this.backInvokedFlingEndListener);
        maxValue.start();
        maxValue.doAnimationFrame(Choreographer.getInstance().getLastFrameTimeNanos() / 1000000);
        this.backInvokedFlingAnim = maxValue;
    }

    public abstract void onBackInvokedCompat();

    @Override // android.window.OnBackAnimationCallback
    public final void onBackProgressed(BackEvent backEvent) {
        float interpolation = this.progressInterpolator.getInterpolation(backEvent.getProgress());
        Long l = this.downTime;
        if (l != null) {
            this.velocityTracker.addMovement(MotionEvent.obtain(l.longValue(), backEvent.getFrameTimeMillis(), 2, interpolation * 100.0f, 0.0f, 0));
        }
        BackEvent backEvent2 = new BackEvent(backEvent.getTouchX(), backEvent.getTouchY(), interpolation, backEvent.getSwipeEdge(), backEvent.getFrameTimeMillis());
        this.lastBackEvent = backEvent2;
        onBackProgressedCompat(backEvent2);
    }

    public abstract void onBackProgressedCompat(BackEvent backEvent);

    @Override // android.window.OnBackAnimationCallback
    public final void onBackStarted(BackEvent backEvent) {
        if (this.backInvokedFlingAnim != null) {
            onBackInvokedCompat();
        }
        reset();
        this.downTime = Long.valueOf(backEvent.getFrameTimeMillis());
        onBackStartedCompat(backEvent);
    }

    public abstract void onBackStartedCompat(BackEvent backEvent);

    public final void reset() {
        this.velocityTracker.clear();
        FlingAnimation flingAnimation = this.backInvokedFlingAnim;
        if (flingAnimation != null) {
            flingAnimation.removeEndListener(this.backInvokedFlingEndListener);
        }
        FlingAnimation flingAnimation2 = this.backInvokedFlingAnim;
        if (flingAnimation2 != null) {
            flingAnimation2.removeUpdateListener(this.backInvokedFlingUpdateListener);
        }
        this.lastBackEvent = null;
        this.backInvokedFlingAnim = null;
        this.downTime = null;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.animation.back.FlingOnBackAnimationCallback$backInvokedFlingUpdateListener$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.animation.back.FlingOnBackAnimationCallback$backInvokedFlingEndListener$1] */
    public FlingOnBackAnimationCallback(Interpolator interpolator) {
        this.progressInterpolator = interpolator;
        this.velocityTracker = VelocityTracker.obtain();
        this.backInvokedFlingUpdateListener = new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.animation.back.FlingOnBackAnimationCallback$backInvokedFlingUpdateListener$1
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                FlingOnBackAnimationCallback flingOnBackAnimationCallback = FlingOnBackAnimationCallback.this;
                BackEvent backEvent = flingOnBackAnimationCallback.lastBackEvent;
                if (backEvent != null) {
                    flingOnBackAnimationCallback.onBackProgressedCompat(new BackEvent(backEvent.getTouchX(), backEvent.getTouchY(), f / 100.0f, backEvent.getSwipeEdge(), backEvent.getFrameTimeMillis()));
                }
            }
        };
        this.backInvokedFlingEndListener = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.animation.back.FlingOnBackAnimationCallback$backInvokedFlingEndListener$1
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                FlingOnBackAnimationCallback.this.onBackInvokedCompat();
                FlingOnBackAnimationCallback.this.reset();
            }
        };
    }

    public /* synthetic */ FlingOnBackAnimationCallback(Interpolator interpolator, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? Interpolators.BACK_GESTURE : interpolator);
    }
}
