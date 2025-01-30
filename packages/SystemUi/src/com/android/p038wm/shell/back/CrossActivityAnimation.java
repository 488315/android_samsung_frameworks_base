package com.android.p038wm.shell.back;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.RemoteException;
import android.util.FloatProperty;
import android.util.TypedValue;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.window.BackEvent;
import android.window.BackMotionEvent;
import android.window.BackProgressAnimator;
import android.window.IOnBackInvokedCallback;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CrossActivityAnimation {
    public final BackAnimationRunner mBackAnimationRunner;
    public final BackAnimationBackground mBackground;
    public RemoteAnimationTarget mClosingTarget;
    public final float mCornerRadius;
    public final SpringAnimation mEnteringProgressSpring;
    public RemoteAnimationTarget mEnteringTarget;
    public IRemoteAnimationFinishedCallback mFinishCallback;
    public final SpringAnimation mLeavingProgressSpring;
    public final float mWindowXShift;
    public static final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    public static final C37821 ENTER_PROGRESS_PROP = new FloatProperty("enter-alpha") { // from class: com.android.wm.shell.back.CrossActivityAnimation.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((CrossActivityAnimation) obj).mEnteringProgress * 100.0f);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            float f2;
            CrossActivityAnimation crossActivityAnimation = (CrossActivityAnimation) obj;
            float f3 = f / 100.0f;
            crossActivityAnimation.mEnteringProgress = f3;
            RemoteAnimationTarget remoteAnimationTarget = crossActivityAnimation.mEnteringTarget;
            if (remoteAnimationTarget == null || remoteAnimationTarget.leash == null) {
                return;
            }
            if (f3 < 0.22f) {
                f2 = 0.0f;
            } else if (f3 >= 1.0f) {
                f2 = 1.0f;
            } else {
                float f4 = (f3 - 0.22f) / 0.78f;
                f2 = (3.0f - (f4 * 2.0f)) * f4 * f4;
            }
            crossActivityAnimation.transformWithProgress(f3, Math.max(f2, 0.01f), crossActivityAnimation.mEnteringTarget.leash, crossActivityAnimation.mEnteringRect, -crossActivityAnimation.mWindowXShift, 0.0f);
        }
    };
    public static final C37832 LEAVE_PROGRESS_PROP = new FloatProperty("leave-alpha") { // from class: com.android.wm.shell.back.CrossActivityAnimation.2
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((CrossActivityAnimation) obj).mLeavingProgress * 100.0f);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            CrossActivityAnimation crossActivityAnimation = (CrossActivityAnimation) obj;
            float f2 = f / 100.0f;
            crossActivityAnimation.mLeavingProgress = f2;
            RemoteAnimationTarget remoteAnimationTarget = crossActivityAnimation.mClosingTarget;
            if (remoteAnimationTarget == null || remoteAnimationTarget.leash == null) {
                return;
            }
            float f3 = 0.0f;
            if (f2 >= 0.0f) {
                if (f2 >= 0.22f) {
                    f3 = 1.0f;
                } else {
                    float f4 = (f2 - 0.0f) / 0.22f;
                    f3 = (3.0f - (f4 * 2.0f)) * f4 * f4;
                }
            }
            crossActivityAnimation.transformWithProgress(f2, Math.max(1.0f - f3, 0.01f), crossActivityAnimation.mClosingTarget.leash, crossActivityAnimation.mClosingRect, 0.0f, crossActivityAnimation.mWindowXShift);
        }
    };
    public final Rect mStartTaskRect = new Rect();
    public final RectF mClosingRect = new RectF();
    public final Rect mEnteringStartRect = new Rect();
    public final RectF mEnteringRect = new RectF();
    public float mEnteringProgress = 0.0f;
    public float mLeavingProgress = 0.0f;
    public final PointF mInitialTouchPos = new PointF();
    public final Matrix mTransformMatrix = new Matrix();
    public final float[] mTmpFloat9 = new float[9];
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public boolean mBackInProgress = false;
    public final PointF mTouchPos = new PointF();
    public final BackProgressAnimator mProgressAnimator = new BackProgressAnimator();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Callback extends IOnBackInvokedCallback.Default {
        public /* synthetic */ Callback(CrossActivityAnimation crossActivityAnimation, int i) {
            this();
        }

        public final void onBackCancelled() {
            final CrossActivityAnimation crossActivityAnimation = CrossActivityAnimation.this;
            crossActivityAnimation.mProgressAnimator.onBackCancelled(new Runnable() { // from class: com.android.wm.shell.back.CrossActivityAnimation$Callback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CrossActivityAnimation crossActivityAnimation2 = CrossActivityAnimation.this;
                    Interpolator interpolator = CrossActivityAnimation.INTERPOLATOR;
                    crossActivityAnimation2.finishAnimation();
                }
            });
        }

        public final void onBackInvoked() {
            CrossActivityAnimation.this.mProgressAnimator.reset();
            final CrossActivityAnimation crossActivityAnimation = CrossActivityAnimation.this;
            if (crossActivityAnimation.mEnteringTarget == null || crossActivityAnimation.mClosingTarget == null) {
                crossActivityAnimation.finishAnimation();
                return;
            }
            crossActivityAnimation.mLeavingProgressSpring.cancel();
            crossActivityAnimation.mEnteringProgressSpring.cancel();
            crossActivityAnimation.mEnteringRect.round(crossActivityAnimation.mEnteringStartRect);
            crossActivityAnimation.mTransaction.hide(crossActivityAnimation.mClosingTarget.leash);
            ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(350L);
            duration.setInterpolator(new DecelerateInterpolator());
            duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.back.CrossActivityAnimation$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CrossActivityAnimation crossActivityAnimation2 = CrossActivityAnimation.this;
                    crossActivityAnimation2.getClass();
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    Rect rect = crossActivityAnimation2.mEnteringStartRect;
                    float f = rect.left;
                    Rect rect2 = crossActivityAnimation2.mStartTaskRect;
                    float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(rect2.left, f, animatedFraction, f);
                    float f2 = rect.top;
                    float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(rect2.top, f2, animatedFraction, f2);
                    float width = rect.width();
                    float m20m3 = DependencyGraph$$ExternalSyntheticOutline0.m20m(rect2.width(), width, animatedFraction, width);
                    float height = rect.height();
                    float m20m4 = DependencyGraph$$ExternalSyntheticOutline0.m20m(rect2.height(), height, animatedFraction, height);
                    float f3 = crossActivityAnimation2.mEnteringProgress;
                    float m20m5 = DependencyGraph$$ExternalSyntheticOutline0.m20m(1.0f, f3, animatedFraction, f3);
                    RectF rectF = crossActivityAnimation2.mEnteringRect;
                    rectF.set(m20m, m20m2, m20m3 + m20m, m20m4 + m20m2);
                    crossActivityAnimation2.applyTransform(crossActivityAnimation2.mEnteringTarget.leash, rectF, m20m5);
                    crossActivityAnimation2.mTransaction.apply();
                }
            });
            duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.back.CrossActivityAnimation.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CrossActivityAnimation crossActivityAnimation2 = CrossActivityAnimation.this;
                    Interpolator interpolator = CrossActivityAnimation.INTERPOLATOR;
                    crossActivityAnimation2.finishAnimation();
                }
            });
            duration.start();
        }

        public final void onBackProgressed(BackMotionEvent backMotionEvent) {
            CrossActivityAnimation.this.mProgressAnimator.onBackProgressed(backMotionEvent);
        }

        public final void onBackStarted(BackMotionEvent backMotionEvent) {
            final CrossActivityAnimation crossActivityAnimation = CrossActivityAnimation.this;
            crossActivityAnimation.mProgressAnimator.onBackStarted(backMotionEvent, new BackProgressAnimator.ProgressCallback() { // from class: com.android.wm.shell.back.CrossActivityAnimation$Callback$$ExternalSyntheticLambda0
                public final void onProgressUpdate(BackEvent backEvent) {
                    CrossActivityAnimation crossActivityAnimation2 = CrossActivityAnimation.this;
                    if (!crossActivityAnimation2.mBackInProgress) {
                        crossActivityAnimation2.mInitialTouchPos.set(backEvent.getTouchX(), backEvent.getTouchY());
                        crossActivityAnimation2.mBackInProgress = true;
                    }
                    crossActivityAnimation2.mTouchPos.set(backEvent.getTouchX(), backEvent.getTouchY());
                    float progress = backEvent.getProgress();
                    float f = (progress > 0.1f ? (((progress - 0.1f) * 0.5f) / 0.9f) + 0.5f : 0.0f + (((progress - 0.0f) * 0.5f) / 1.0f)) * 100.0f;
                    crossActivityAnimation2.mLeavingProgressSpring.animateToFinalPosition(f);
                    crossActivityAnimation2.mEnteringProgressSpring.animateToFinalPosition(f);
                    crossActivityAnimation2.mBackground.onBackProgressed(progress);
                }
            });
        }

        private Callback() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Runner extends IRemoteAnimationRunner.Default {
        public /* synthetic */ Runner(CrossActivityAnimation crossActivityAnimation, int i) {
            this();
        }

        public final void onAnimationCancelled() {
            CrossActivityAnimation crossActivityAnimation = CrossActivityAnimation.this;
            Interpolator interpolator = CrossActivityAnimation.INTERPOLATOR;
            crossActivityAnimation.finishAnimation();
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 189441961, 0, "Start back to activity animation.", null);
            }
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                int i2 = remoteAnimationTarget.mode;
                if (i2 == 1) {
                    CrossActivityAnimation.this.mClosingTarget = remoteAnimationTarget;
                }
                if (i2 == 0) {
                    CrossActivityAnimation.this.mEnteringTarget = remoteAnimationTarget;
                }
            }
            CrossActivityAnimation crossActivityAnimation = CrossActivityAnimation.this;
            if (crossActivityAnimation.mEnteringTarget != null && crossActivityAnimation.mClosingTarget != null) {
                SurfaceControl.Transaction transaction = crossActivityAnimation.mTransaction;
                transaction.setAnimationTransaction();
                Rect bounds = crossActivityAnimation.mClosingTarget.windowConfiguration.getBounds();
                Rect rect = crossActivityAnimation.mStartTaskRect;
                rect.set(bounds);
                rect.offsetTo(0, 0);
                crossActivityAnimation.mBackground.ensureBackground(crossActivityAnimation.mClosingTarget.windowConfiguration.getBounds(), crossActivityAnimation.mEnteringTarget.taskInfo.taskDescription.getBackgroundColor(), transaction);
            } else if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1659561821, 0, "Entering target or closing target is null.", null);
            }
            CrossActivityAnimation.this.mFinishCallback = iRemoteAnimationFinishedCallback;
        }

        private Runner() {
        }
    }

    public CrossActivityAnimation(Context context, BackAnimationBackground backAnimationBackground) {
        int i = 0;
        this.mCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mBackAnimationRunner = new BackAnimationRunner(new Callback(this, i), new Runner(this, i));
        this.mBackground = backAnimationBackground;
        SpringAnimation springAnimation = new SpringAnimation(this, ENTER_PROGRESS_PROP);
        this.mEnteringProgressSpring = springAnimation;
        springAnimation.setSpring(new SpringForce().setStiffness(1500.0f).setDampingRatio(1.0f));
        SpringAnimation springAnimation2 = new SpringAnimation(this, LEAVE_PROGRESS_PROP);
        this.mLeavingProgressSpring = springAnimation2;
        springAnimation2.setSpring(new SpringForce().setStiffness(1500.0f).setDampingRatio(1.0f));
        this.mWindowXShift = TypedValue.applyDimension(1, 96.0f, context.getResources().getDisplayMetrics());
    }

    public final void applyTransform(SurfaceControl surfaceControl, RectF rectF, float f) {
        float width = rectF.width();
        Rect rect = this.mStartTaskRect;
        float width2 = width / rect.width();
        Matrix matrix = this.mTransformMatrix;
        matrix.reset();
        matrix.setScale(width2, width2);
        matrix.postTranslate(rectF.left, rectF.top);
        this.mTransaction.setAlpha(surfaceControl, f).setMatrix(surfaceControl, matrix, this.mTmpFloat9).setWindowCrop(surfaceControl, rect).setCornerRadius(surfaceControl, this.mCornerRadius);
    }

    public final void finishAnimation() {
        RemoteAnimationTarget remoteAnimationTarget = this.mEnteringTarget;
        if (remoteAnimationTarget != null) {
            remoteAnimationTarget.leash.release();
            this.mEnteringTarget = null;
        }
        RemoteAnimationTarget remoteAnimationTarget2 = this.mClosingTarget;
        if (remoteAnimationTarget2 != null) {
            remoteAnimationTarget2.leash.release();
            this.mClosingTarget = null;
        }
        SurfaceControl.Transaction transaction = this.mTransaction;
        BackAnimationBackground backAnimationBackground = this.mBackground;
        if (backAnimationBackground != null) {
            backAnimationBackground.removeBackground(transaction);
        }
        transaction.apply();
        this.mBackInProgress = false;
        this.mTransformMatrix.reset();
        this.mInitialTouchPos.set(0.0f, 0.0f);
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.mFinishCallback;
        if (iRemoteAnimationFinishedCallback != null) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.mFinishCallback = null;
        }
        SpringAnimation springAnimation = this.mEnteringProgressSpring;
        springAnimation.animateToFinalPosition(0.0f);
        springAnimation.skipToEnd();
        SpringAnimation springAnimation2 = this.mLeavingProgressSpring;
        springAnimation2.animateToFinalPosition(0.0f);
        springAnimation2.skipToEnd();
    }

    public final void transformWithProgress(float f, float f2, SurfaceControl surfaceControl, RectF rectF, float f3, float f4) {
        float f5 = this.mTouchPos.y;
        Rect rect = this.mStartTaskRect;
        int width = rect.width();
        int height = rect.height();
        float interpolation = ((DecelerateInterpolator) INTERPOLATOR).getInterpolation(f);
        float f6 = width;
        float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(1.0f, interpolation, 0.100000024f, 0.9f) * f6;
        float f7 = height;
        float f8 = (f7 / f6) * m20m;
        float f9 = ((f4 - f3) * interpolation) + f3 + ((f6 - m20m) / 2.0f) + rect.left;
        float f10 = this.mInitialTouchPos.y;
        float f11 = (f7 - f8) * 0.5f;
        rectF.set(f9, f11, m20m + f9, f8 + f11);
        applyTransform(surfaceControl, rectF, Math.max(f2, 0.01f));
        this.mTransaction.apply();
    }
}
