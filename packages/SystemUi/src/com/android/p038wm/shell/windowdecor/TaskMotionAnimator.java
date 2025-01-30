package com.android.p038wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Slog;
import android.view.SurfaceControl;
import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.util.InterpolatorUtils;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TaskMotionAnimator {
    public final TaskMotionAnimation mAnimation;
    public OnAnimationFinishedCallback mAnimationFinishedCallback;
    public boolean mCanceled;
    public final FreeformStashState mFreeformStashState;
    public final SurfaceControl mTaskSurface;
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public final Object mLock = new Object();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnAnimationFinishedCallback {
        void onAnimationFinished(Rect rect);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StashRestoreAnimation extends SimpleSpringListener implements TaskMotionAnimation {
        public Spring mSpringTranslateX;
        public Spring mSpringTranslateY;
        public final SpringSystem mSpringSystem = SpringSystem.create();
        public final Rect mStartBounds = new Rect();
        public final Rect mEndBounds = new Rect();
        public final RectF mAnimatedBounds = new RectF();
        public float mScale = 1.0f;

        public StashRestoreAnimation() {
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final void cancel(boolean z) {
            Spring spring;
            synchronized (TaskMotionAnimator.this.mLock) {
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
                    if (z) {
                        TaskMotionAnimator.this.mAnimationFinishedCallback = null;
                    }
                    TaskMotionAnimator taskMotionAnimator2 = TaskMotionAnimator.this;
                    OnAnimationFinishedCallback onAnimationFinishedCallback = taskMotionAnimator2.mAnimationFinishedCallback;
                    taskMotionAnimator2.mAnimationFinishedCallback = null;
                    if (onAnimationFinishedCallback != null) {
                        onAnimationFinishedCallback.onAnimationFinished(this.mEndBounds);
                    }
                    if (TaskMotionController.DEBUG) {
                        Slog.d("TaskMotionAnimator", "StashRestoreAnimation[cancel]");
                    }
                }
            }
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final void getDragBounds(Rect rect) {
            synchronized (TaskMotionAnimator.this.mLock) {
                RectF rectF = this.mAnimatedBounds;
                rect.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            }
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final boolean isAnimating() {
            boolean z;
            Spring spring;
            synchronized (TaskMotionAnimator.this.mLock) {
                Spring spring2 = this.mSpringTranslateX;
                z = (spring2 == null || spring2.isAtRest() || (spring = this.mSpringTranslateY) == null || spring.isAtRest()) ? false : true;
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
                        if (TaskMotionController.DEBUG) {
                            Slog.w("TaskMotionAnimator", "StashRestoreAnimation: scale " + TaskMotionAnimator.this.mFreeformStashState.mScale + " to 1.0");
                        }
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
                    if (TaskMotionController.DEBUG) {
                        Slog.d("TaskMotionAnimator", "StashRestoreAnimation[finish]: EndBounds=" + this.mEndBounds);
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
                    TaskMotionAnimator.this.mFreeformStashState.setDimOverlayAlpha((1.0f - (abs2 / abs)) * (MultiWindowUtils.isNightMode(freeformStashState.mTaskInfo) ? 0.4f : 0.2f));
                    if (Math.abs(spring.mEndValue - spring.mCurrentState.position) < 1.0d) {
                        spring.setAtRest();
                    }
                }
            }
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final void start() {
            synchronized (TaskMotionAnimator.this.mLock) {
                Spring spring = this.mSpringTranslateX;
                if (spring != null) {
                    spring.setEndValue(this.mEndBounds.left);
                }
                Spring spring2 = this.mSpringTranslateY;
                if (spring2 != null) {
                    spring2.setEndValue(this.mEndBounds.top);
                }
            }
            if (TaskMotionController.DEBUG) {
                Slog.d("TaskMotionAnimator", "StashRestoreAnimation[start]");
            }
        }
    }

    public TaskMotionAnimator(int i, FreeformStashState freeformStashState, SurfaceControl surfaceControl, OnAnimationFinishedCallback onAnimationFinishedCallback) {
        this.mAnimationFinishedCallback = onAnimationFinishedCallback;
        this.mFreeformStashState = freeformStashState;
        this.mTaskSurface = surfaceControl;
        if (i == 0 || i == 1) {
            this.mAnimation = new ScaleAnimation();
        } else if (i == 4) {
            this.mAnimation = new StashRestoreAnimation();
        } else {
            this.mAnimation = null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScaleAnimation implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener, TaskMotionAnimation {
        public int mAnimType;
        public Rect mTaskSurfaceBounds;
        public ValueAnimator mValueAnimator = null;
        public final Rect mTaskBounds = new Rect();
        public boolean mStartFromLeftStash = false;

        public ScaleAnimation() {
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final void cancel(boolean z) {
            synchronized (TaskMotionAnimator.this.mLock) {
                if (!TaskMotionAnimator.this.mCanceled && isAnimating()) {
                    TaskMotionAnimator taskMotionAnimator = TaskMotionAnimator.this;
                    taskMotionAnimator.mCanceled = true;
                    if (z) {
                        taskMotionAnimator.mAnimationFinishedCallback = null;
                    }
                    ValueAnimator valueAnimator = this.mValueAnimator;
                    if (valueAnimator != null) {
                        valueAnimator.cancel();
                    }
                    if (TaskMotionController.DEBUG) {
                        Slog.d("TaskMotionAnimator", "ScaleAnimation[cancel] : this=" + this);
                    }
                }
            }
        }

        public final void initialize(int i, float f, float f2, Rect rect, Rect rect2, boolean z) {
            ValueAnimator ofPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("scale", f, f2));
            this.mValueAnimator = ofPropertyValuesHolder;
            ofPropertyValuesHolder.setDuration(300L);
            this.mValueAnimator.setInterpolator(InterpolatorUtils.SINE_OUT_60);
            this.mValueAnimator.addListener(this);
            this.mValueAnimator.addUpdateListener(this);
            this.mTaskBounds.set(rect);
            this.mTaskSurfaceBounds = rect2;
            this.mAnimType = i;
            this.mStartFromLeftStash = z;
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final boolean isAnimating() {
            boolean z;
            synchronized (TaskMotionAnimator.this.mLock) {
                ValueAnimator valueAnimator = this.mValueAnimator;
                z = valueAnimator != null && valueAnimator.isRunning();
            }
            return z;
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            synchronized (TaskMotionAnimator.this.mLock) {
                OnAnimationFinishedCallback onAnimationFinishedCallback = TaskMotionAnimator.this.mAnimationFinishedCallback;
                if (onAnimationFinishedCallback != null) {
                    onAnimationFinishedCallback.onAnimationFinished(this.mTaskSurfaceBounds);
                    TaskMotionAnimator.this.mAnimationFinishedCallback = null;
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
            if (TaskMotionController.DEBUG) {
                Slog.d("TaskMotionAnimator", "ScaleAnimation[End] this=" + this);
            }
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            synchronized (TaskMotionAnimator.this.mLock) {
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
                    float width = this.mStartFromLeftStash ? this.mTaskBounds.width() - (this.mTaskBounds.width() * TaskMotionAnimator.this.mFreeformStashState.mScale) : 0.0f;
                    TaskMotionAnimator taskMotionAnimator2 = TaskMotionAnimator.this;
                    SurfaceControl.Transaction transaction = taskMotionAnimator2.mTransaction;
                    SurfaceControl surfaceControl2 = taskMotionAnimator2.mTaskSurface;
                    Rect rect = this.mTaskSurfaceBounds;
                    transaction.setPosition(surfaceControl2, rect.left + width, rect.top);
                    TaskMotionAnimator.this.mTransaction.apply();
                }
            }
        }

        @Override // com.android.wm.shell.windowdecor.TaskMotionAnimator.TaskMotionAnimation
        public final void start() {
            synchronized (TaskMotionAnimator.this.mLock) {
                ValueAnimator valueAnimator = this.mValueAnimator;
                if (valueAnimator != null) {
                    valueAnimator.start();
                }
            }
            if (TaskMotionController.DEBUG) {
                Slog.d("TaskMotionAnimator", "ScaleAnimation[start] : this=" + this);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TaskMotionAnimation {
        void cancel(boolean z);

        boolean isAnimating();

        void start();

        default void getDragBounds(Rect rect) {
        }
    }
}
