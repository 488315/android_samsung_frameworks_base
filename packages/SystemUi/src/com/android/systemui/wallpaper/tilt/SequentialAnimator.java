package com.android.systemui.wallpaper.tilt;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SequentialAnimator {
    public Animator.AnimatorListener mAnimatorListener;
    public ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;
    public final Choreographer mChoreographer;
    public final ChoreographerFrameCallbackC36901 mFrameCallback = new Choreographer.FrameCallback() { // from class: com.android.systemui.wallpaper.tilt.SequentialAnimator.1
        @Override // android.view.Choreographer.FrameCallback
        public final void doFrame(long j) {
            SequentialAnimator sequentialAnimator = SequentialAnimator.this;
            if (sequentialAnimator.mIsRunning) {
                System.currentTimeMillis();
                float f = sequentialAnimator.mCurrentFraction;
                if (1.0f - f < 0.1f) {
                    Log.i("SequentialAnimator", "handleMessage: fraction cut : " + f);
                    sequentialAnimator.mCurrentFraction = 1.0f;
                    f = 1.0f;
                }
                if (sequentialAnimator.mAnimationState == 0) {
                    sequentialAnimator.notifyListener(f, 0);
                    sequentialAnimator.mAnimationState = 3;
                }
                if (3 == sequentialAnimator.mAnimationState) {
                    if (sequentialAnimator.mCurrentFraction < 1.0f) {
                        sequentialAnimator.notifyListener(f, 3);
                    } else {
                        sequentialAnimator.mCurrentFraction = 1.0f;
                        sequentialAnimator.notifyListener(f, 3);
                        sequentialAnimator.mAnimationState = 1;
                        sequentialAnimator.notifyListener(f, 1);
                        sequentialAnimator.mIsRunning = false;
                    }
                    sequentialAnimator.mFrameCount++;
                }
                SequentialAnimator sequentialAnimator2 = SequentialAnimator.this;
                float f2 = sequentialAnimator2.mCurrentFraction + sequentialAnimator2.mFractionUnit;
                sequentialAnimator2.mCurrentFraction = f2;
                if (!sequentialAnimator2.mIsRunning || f2 >= 1.0f) {
                    return;
                }
                sequentialAnimator2.mChoreographer.postFrameCallback(this);
            }
        }
    };
    public final ValueAnimator mDummyAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
    public boolean mIsRunning = false;
    public float mFractionUnit = 31.0f;
    public int mAnimationState = -1;
    public float mCurrentFraction = 0.0f;
    public long mDuration = 500;
    public long mFrameCount = 0;
    public final ArrayList mAnimationValues = new ArrayList();

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.wallpaper.tilt.SequentialAnimator$1] */
    public SequentialAnimator() {
        this.mChoreographer = null;
        if (Looper.myLooper() == null) {
            Log.d("SequentialAnimator", "SequentialAnimator: prepare looper for Choreographer");
            Looper.prepare();
            Looper.loop();
        }
        this.mChoreographer = Choreographer.getInstance();
    }

    public final void cancel() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            this.mChoreographer.removeFrameCallback(this.mFrameCallback);
            this.mAnimationState = 2;
            notifyListener(this.mCurrentFraction, 2);
            this.mAnimationState = 1;
            notifyListener(this.mCurrentFraction, 1);
        }
    }

    public final void notifyListener(float f, int i) {
        ValueAnimator valueAnimator = this.mDummyAnimator;
        valueAnimator.setCurrentFraction(f);
        long nanoTime = System.nanoTime();
        if (i == 3) {
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener = this.mAnimatorUpdateListener;
            float animatedFraction = valueAnimator.getAnimatedFraction();
            ArrayList arrayList = this.mAnimationValues;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                AnimatedValue animatedValue = (AnimatedValue) arrayList.get(i2);
                animatedValue.currentValue = (animatedValue.delta * animatedFraction) + animatedValue.startValue;
            }
            if (animatorUpdateListener != null) {
                animatorUpdateListener.onAnimationUpdate(valueAnimator);
            }
        } else {
            Animator.AnimatorListener animatorListener = this.mAnimatorListener;
            Log.i("SequentialAnimator", "notifyListener: #" + this.mFrameCount + " state:" + i + " fraction" + this.mCurrentFraction + " / " + f + " unit:" + this.mFractionUnit);
            if (animatorListener != null) {
                if (i == 0) {
                    animatorListener.onAnimationStart(valueAnimator);
                } else if (i == 1) {
                    animatorListener.onAnimationEnd(valueAnimator);
                } else if (i == 2) {
                    animatorListener.onAnimationCancel(valueAnimator);
                }
            }
        }
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime);
        if (millis > 64) {
            Log.w("SequentialAnimator", "notifyListener : took :" + millis + "ms");
        }
    }

    public final void setDuration(long j) {
        if (j < 0) {
            Log.w("SequentialAnimator", "setStartDelay: under 0 : " + j);
            j = 0L;
        }
        this.mDuration = j;
        this.mDummyAnimator.setDuration(j);
    }

    public final synchronized void start() {
        if (this.mAnimatorListener == null && this.mAnimatorUpdateListener == null) {
            Log.w("SequentialAnimator", "start: no listeners");
            return;
        }
        if (this.mIsRunning) {
            Log.w("SequentialAnimator", "start: skipped, already Running");
            return;
        }
        this.mFractionUnit = 1.0f / (this.mDuration / 16.0f);
        this.mIsRunning = true;
        this.mCurrentFraction = 0.0f;
        this.mFrameCount = 0L;
        Log.i("SequentialAnimator", "start: duration: " + this.mDuration + " startDelay: 0 mFractionUnit:" + this.mFractionUnit + " frameDelay:16");
        Animator.AnimatorListener animatorListener = this.mAnimatorListener;
        if (animatorListener != null && (animatorListener instanceof AnimationListenerAdapterProfiler)) {
            AnimationListenerAdapterProfiler animationListenerAdapterProfiler = (AnimationListenerAdapterProfiler) animatorListener;
            animationListenerAdapterProfiler.startAnimationProfile(animationListenerAdapterProfiler.mProfileTag);
        }
        this.mAnimationState = 0;
        if (this.mDuration > 0) {
            System.currentTimeMillis();
            this.mChoreographer.postFrameCallback(this.mFrameCallback);
        } else {
            this.mChoreographer.removeFrameCallback(this.mFrameCallback);
            notifyListener(this.mCurrentFraction, 0);
            notifyListener(this.mCurrentFraction, 3);
            this.mCurrentFraction = 1.0f;
            notifyListener(1.0f, 3);
            this.mIsRunning = false;
            notifyListener(this.mCurrentFraction, 1);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimatedValue {
        public float currentValue;
        public float startValue = 0.0f;
        public float targetValue = 0.0f;
        public float delta = 0.0f;

        public AnimatedValue(float f) {
            this.currentValue = f;
        }

        public final void setTarget(float f) {
            this.targetValue = f;
            float f2 = this.currentValue;
            this.startValue = f2;
            this.delta = f - f2;
        }

        public AnimatedValue(float f, String str) {
            this.currentValue = f;
        }
    }
}
