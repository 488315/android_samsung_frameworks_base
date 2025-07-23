package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceControl;
import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.OrigamiValueConverter;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.util.InterpolatorUtils;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class TaskMotionAnimator {
    public final TaskMotionAnimation mAnimation;
    public OnAnimationFinishedCallback mAnimationFinishedCallback;
    public boolean mCanceled;
    public final FreeformStashState mFreeformStashState;
    public final SurfaceControl mTaskSurface;
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public final Object mLock = new Object();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnAnimationFinishedCallback {
        void onAnimationFinished(Rect rect);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class StashRestoreAnimation extends SimpleSpringListener implements TaskMotionAnimation {
        public final RectF mAnimatedBounds;
        public final Rect mEndBounds;
        public boolean mNeedAlphaAnimation;
        public final float mScale;
        public Spring mSpringTranslateX;
        public Spring mSpringTranslateY;
        public final Rect mStartBounds;

        public StashRestoreAnimation(TaskMotionAnimValue taskMotionAnimValue) {
            SpringSystem create = SpringSystem.create();
            Rect rect = new Rect();
            this.mStartBounds = rect;
            Rect rect2 = new Rect();
            this.mEndBounds = rect2;
            RectF rectF = new RectF();
            this.mAnimatedBounds = rectF;
            this.mScale = 1.0f;
            synchronized (TaskMotionAnimator.this.mLock) {
                rect.set(taskMotionAnimValue.mStartBounds);
                rectF.set(taskMotionAnimValue.mStartBounds);
                rect2.set(taskMotionAnimValue.mEndBounds);
                Spring createSpring = create.createSpring();
                this.mSpringTranslateX = createSpring;
                createSpring.mSpringConfig = new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(200.0d), OrigamiValueConverter.frictionFromOrigamiValue(20.0d));
                Spring createSpring2 = create.createSpring();
                this.mSpringTranslateY = createSpring2;
                createSpring2.mSpringConfig = new SpringConfig(OrigamiValueConverter.tensionFromOrigamiValue(200.0d), OrigamiValueConverter.frictionFromOrigamiValue(20.0d));
                this.mSpringTranslateX.setCurrentValue(rect.left);
                this.mSpringTranslateY.setCurrentValue(rect.top);
                this.mSpringTranslateX.setVelocity(60.0d);
                this.mSpringTranslateY.setVelocity(60.0d);
                this.mSpringTranslateX.addListener(this);
                this.mSpringTranslateY.addListener(this);
                this.mScale = taskMotionAnimValue.mTargetScale;
            }
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final void cancel() {
            Spring spring;
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    TaskMotionAnimator taskMotionAnimator = TaskMotionAnimator.this;
                    if (!taskMotionAnimator.mCanceled && (spring = this.mSpringTranslateX) != null && this.mSpringTranslateY != null) {
                        taskMotionAnimator.mCanceled = true;
                        spring.setAtRest();
                        Spring spring2 = this.mSpringTranslateY;
                        if (spring2 != null) {
                            spring2.setAtRest();
                        }
                        Rect rect = this.mEndBounds;
                        RectF rectF = this.mAnimatedBounds;
                        rect.offsetTo((int) rectF.left, (int) rectF.top);
                        TaskMotionAnimator taskMotionAnimator2 = TaskMotionAnimator.this;
                        OnAnimationFinishedCallback onAnimationFinishedCallback = taskMotionAnimator2.mAnimationFinishedCallback;
                        taskMotionAnimator2.mAnimationFinishedCallback = null;
                        if (onAnimationFinishedCallback != null) {
                            onAnimationFinishedCallback.onAnimationFinished(this.mEndBounds);
                        }
                    }
                } finally {
                }
            }
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final boolean isAnimating() {
            boolean z;
            Spring spring;
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    Spring spring2 = this.mSpringTranslateX;
                    z = (spring2 == null || spring2.isAtRest() || (spring = this.mSpringTranslateY) == null || spring.isAtRest()) ? false : true;
                } finally {
                }
            }
            return z;
        }

        @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
        public final void onSpringAtRest(Spring spring) {
            SurfaceControl surfaceControl;
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    if (isAnimating()) {
                        return;
                    }
                    TaskMotionAnimator taskMotionAnimator = TaskMotionAnimator.this;
                    if (taskMotionAnimator.mFreeformStashState.mScale < 1.0f && (surfaceControl = taskMotionAnimator.mTaskSurface) != null && surfaceControl.isValid()) {
                        TaskMotionAnimator taskMotionAnimator2 = TaskMotionAnimator.this;
                        taskMotionAnimator2.mTransaction.setMatrix(taskMotionAnimator2.mTaskSurface, 1.0f, 0.0f, 0.0f, 1.0f).apply();
                        TaskMotionAnimator.this.mFreeformStashState.mScale = 1.0f;
                    }
                    TaskMotionAnimator taskMotionAnimator3 = TaskMotionAnimator.this;
                    OnAnimationFinishedCallback onAnimationFinishedCallback = taskMotionAnimator3.mAnimationFinishedCallback;
                    taskMotionAnimator3.mAnimationFinishedCallback = null;
                    Spring spring2 = this.mSpringTranslateX;
                    if (spring2 != null) {
                        spring2.setAtRest();
                        Spring spring3 = this.mSpringTranslateX;
                        spring3.mListeners.clear();
                        BaseSpringSystem baseSpringSystem = spring3.mSpringSystem;
                        ((CopyOnWriteArraySet) baseSpringSystem.mActiveSprings).remove(spring3);
                        ((HashMap) baseSpringSystem.mSpringRegistry).remove(spring3.mId);
                        this.mSpringTranslateX = null;
                    }
                    Spring spring4 = this.mSpringTranslateY;
                    if (spring4 != null) {
                        spring4.setAtRest();
                        Spring spring5 = this.mSpringTranslateY;
                        spring5.mListeners.clear();
                        BaseSpringSystem baseSpringSystem2 = spring5.mSpringSystem;
                        ((CopyOnWriteArraySet) baseSpringSystem2.mActiveSprings).remove(spring5);
                        ((HashMap) baseSpringSystem2.mSpringRegistry).remove(spring5.mId);
                        this.mSpringTranslateY = null;
                    }
                    if (onAnimationFinishedCallback != null) {
                        onAnimationFinishedCallback.onAnimationFinished(this.mEndBounds);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.facebook.rebound.SimpleSpringListener, com.facebook.rebound.SpringListener
        public final void onSpringUpdate(Spring spring) {
            float min;
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    SurfaceControl surfaceControl = TaskMotionAnimator.this.mTaskSurface;
                    if (surfaceControl != null && surfaceControl.isValid() && !TaskMotionAnimator.this.mCanceled) {
                        float f = this.mSpringTranslateX != null ? (int) r3.mCurrentState.position : this.mAnimatedBounds.left;
                        float f2 = this.mSpringTranslateY != null ? (int) r4.mCurrentState.position : this.mAnimatedBounds.top;
                        double d = f;
                        float ceil = (float) Math.ceil(d);
                        double d2 = f2;
                        float ceil2 = (float) Math.ceil(d2);
                        RectF rectF = this.mAnimatedBounds;
                        if (ceil == rectF.left && ceil2 == rectF.top) {
                            return;
                        }
                        rectF.offsetTo(f, f2);
                        float abs = Math.abs(this.mStartBounds.left - this.mEndBounds.left);
                        float abs2 = Math.abs(this.mStartBounds.left - ((int) f));
                        if (abs == 0.0f) {
                            min = 1.0f;
                        } else {
                            float f3 = this.mScale;
                            min = Math.min(((1.0f - f3) * (abs2 / abs)) + f3, 1.0f);
                        }
                        TaskMotionAnimator taskMotionAnimator = TaskMotionAnimator.this;
                        taskMotionAnimator.mTransaction.setMatrix(taskMotionAnimator.mTaskSurface, min, 0.0f, 0.0f, min);
                        TaskMotionAnimator taskMotionAnimator2 = TaskMotionAnimator.this;
                        taskMotionAnimator2.mTransaction.setPosition(taskMotionAnimator2.mTaskSurface, (float) Math.ceil(d), (float) Math.ceil(d2)).apply();
                        FreeformStashState freeformStashState = TaskMotionAnimator.this.mFreeformStashState;
                        freeformStashState.mScale = min;
                        if (this.mNeedAlphaAnimation) {
                            TaskMotionAnimator.this.mFreeformStashState.setDimOverlayAlpha((1.0f - (abs2 / abs)) * (MultiWindowUtils.isNightMode(freeformStashState.mTaskInfo) ? 0.4f : 0.2f));
                        }
                        if (Math.abs(spring.mEndValue - spring.mCurrentState.position) < 1.0d) {
                            spring.setAtRest();
                        }
                    }
                } finally {
                }
            }
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final void start() {
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    Spring spring = this.mSpringTranslateX;
                    if (spring != null) {
                        spring.setEndValue(this.mEndBounds.left);
                    }
                    Spring spring2 = this.mSpringTranslateY;
                    if (spring2 != null) {
                        spring2.setEndValue(this.mEndBounds.top);
                    }
                    this.mNeedAlphaAnimation = (MultiWindowUtils.isNightMode(TaskMotionAnimator.this.mFreeformStashState.mTaskInfo) ? 0.4f : 0.2f) == TaskMotionAnimator.this.mFreeformStashState.mCurrentAlpha;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface TaskMotionAnimation {
        void cancel();

        boolean isAnimating();

        void start();
    }

    public TaskMotionAnimator(TaskMotionAnimValue taskMotionAnimValue, OnAnimationFinishedCallback onAnimationFinishedCallback) {
        this.mAnimationFinishedCallback = onAnimationFinishedCallback;
        this.mFreeformStashState = taskMotionAnimValue.mFreeformStashState;
        this.mTaskSurface = taskMotionAnimValue.mTaskSurface;
        int i = taskMotionAnimValue.mAnimType;
        if (i == 1 || i == 0) {
            this.mAnimation = new ScaleAnimation(taskMotionAnimValue);
        } else if (i == 2) {
            this.mAnimation = new StashRestoreAnimation(taskMotionAnimValue);
        } else {
            this.mAnimation = null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScaleAnimation implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener, TaskMotionAnimation {
        public final int mAnimType;
        public final boolean mStartFromLeftStash;
        public final Rect mTaskSurfaceBounds;
        public ValueAnimator mValueAnimator;

        public ScaleAnimation(TaskMotionAnimValue taskMotionAnimValue) {
            ValueAnimator ofPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("scale", taskMotionAnimValue.mFreeformStashState.mScale, taskMotionAnimValue.mTargetScale));
            this.mValueAnimator = ofPropertyValuesHolder;
            ofPropertyValuesHolder.setDuration(300L);
            this.mValueAnimator.setInterpolator(InterpolatorUtils.SINE_OUT_60);
            this.mValueAnimator.addListener(this);
            this.mValueAnimator.addUpdateListener(this);
            this.mTaskSurfaceBounds = taskMotionAnimValue.mStartBounds;
            this.mAnimType = taskMotionAnimValue.mAnimType;
            this.mStartFromLeftStash = TaskMotionAnimator.this.mFreeformStashState.isLeftStashed();
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final void cancel() {
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    if (!TaskMotionAnimator.this.mCanceled && isAnimating()) {
                        TaskMotionAnimator.this.mCanceled = true;
                        ValueAnimator valueAnimator = this.mValueAnimator;
                        if (valueAnimator != null) {
                            valueAnimator.cancel();
                        }
                    }
                } finally {
                }
            }
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final boolean isAnimating() {
            boolean z;
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    ValueAnimator valueAnimator = this.mValueAnimator;
                    z = valueAnimator != null && valueAnimator.isRunning();
                } finally {
                }
            }
            return z;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    OnAnimationFinishedCallback onAnimationFinishedCallback = TaskMotionAnimator.this.mAnimationFinishedCallback;
                    if (onAnimationFinishedCallback != null) {
                        onAnimationFinishedCallback.onAnimationFinished(this.mTaskSurfaceBounds);
                        TaskMotionAnimator.this.mAnimationFinishedCallback = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            OnAnimationFinishedCallback onAnimationFinishedCallback;
            synchronized (TaskMotionAnimator.this.mLock) {
                this.mValueAnimator = null;
                TaskMotionAnimator taskMotionAnimator = TaskMotionAnimator.this;
                onAnimationFinishedCallback = taskMotionAnimator.mAnimationFinishedCallback;
                taskMotionAnimator.mAnimationFinishedCallback = null;
            }
            if (onAnimationFinishedCallback != null) {
                onAnimationFinishedCallback.onAnimationFinished(this.mTaskSurfaceBounds);
            }
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    SurfaceControl surfaceControl = TaskMotionAnimator.this.mTaskSurface;
                    if (surfaceControl != null && surfaceControl.isValid() && !TaskMotionAnimator.this.mCanceled) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue("scale")).floatValue();
                        TaskMotionAnimator taskMotionAnimator = TaskMotionAnimator.this;
                        taskMotionAnimator.mTransaction.setMatrix(taskMotionAnimator.mTaskSurface, floatValue, 0.0f, 0.0f, floatValue);
                        FreeformStashState freeformStashState = TaskMotionAnimator.this.mFreeformStashState;
                        freeformStashState.mScale = floatValue;
                        float f = MultiWindowUtils.isNightMode(freeformStashState.mTaskInfo) ? 0.4f : 0.2f;
                        if (this.mAnimType == 0) {
                            TaskMotionAnimator.this.mFreeformStashState.setDimOverlayAlpha(valueAnimator.getAnimatedFraction() * f);
                        } else {
                            TaskMotionAnimator.this.mFreeformStashState.setDimOverlayAlpha(f - (valueAnimator.getAnimatedFraction() * f));
                        }
                        float width = this.mStartFromLeftStash ? this.mTaskSurfaceBounds.width() - (this.mTaskSurfaceBounds.width() * TaskMotionAnimator.this.mFreeformStashState.mScale) : 0.0f;
                        TaskMotionAnimator taskMotionAnimator2 = TaskMotionAnimator.this;
                        SurfaceControl.Transaction transaction = taskMotionAnimator2.mTransaction;
                        SurfaceControl surfaceControl2 = taskMotionAnimator2.mTaskSurface;
                        Rect rect = this.mTaskSurfaceBounds;
                        transaction.setPosition(surfaceControl2, rect.left + width, rect.top);
                        TaskMotionAnimator.this.mTransaction.apply();
                    }
                } finally {
                }
            }
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final void start() {
            synchronized (TaskMotionAnimator.this.mLock) {
                try {
                    ValueAnimator valueAnimator = this.mValueAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.start();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
        }
    }
}
