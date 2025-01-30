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
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.window.BackEvent;
import android.window.BackMotionEvent;
import android.window.BackProgressAnimator;
import android.window.IOnBackInvokedCallback;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CrossTaskBackAnimation {
    public final BackAnimationRunner mBackAnimationRunner;
    public final BackAnimationBackground mBackground;
    public RemoteAnimationTarget mClosingTarget;
    public final float mCornerRadius;
    public RemoteAnimationTarget mEnteringTarget;
    public IRemoteAnimationFinishedCallback mFinishCallback;
    public final Rect mStartTaskRect = new Rect();
    public final RectF mClosingCurrentRect = new RectF();
    public final Rect mEnteringStartRect = new Rect();
    public final RectF mEnteringCurrentRect = new RectF();
    public final PointF mInitialTouchPos = new PointF();
    public final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    public final Matrix mTransformMatrix = new Matrix();
    public final float[] mTmpFloat9 = new float[9];
    public final float[] mTmpTranslate = {0.0f, 0.0f, 0.0f};
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public boolean mBackInProgress = false;
    public float mProgress = 0.0f;
    public final PointF mTouchPos = new PointF();
    public final BackProgressAnimator mProgressAnimator = new BackProgressAnimator();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Callback extends IOnBackInvokedCallback.Default {
        public /* synthetic */ Callback(CrossTaskBackAnimation crossTaskBackAnimation, int i) {
            this();
        }

        public final void onBackCancelled() {
            final CrossTaskBackAnimation crossTaskBackAnimation = CrossTaskBackAnimation.this;
            crossTaskBackAnimation.mProgressAnimator.onBackCancelled(new Runnable() { // from class: com.android.wm.shell.back.CrossTaskBackAnimation$Callback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CrossTaskBackAnimation.this.finishAnimation();
                }
            });
        }

        public final void onBackInvoked() {
            CrossTaskBackAnimation.this.mProgressAnimator.reset();
            final CrossTaskBackAnimation crossTaskBackAnimation = CrossTaskBackAnimation.this;
            if (crossTaskBackAnimation.mEnteringTarget == null || crossTaskBackAnimation.mClosingTarget == null) {
                crossTaskBackAnimation.finishAnimation();
                return;
            }
            crossTaskBackAnimation.mEnteringCurrentRect.round(crossTaskBackAnimation.mEnteringStartRect);
            ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(300L);
            duration.setInterpolator(crossTaskBackAnimation.mInterpolator);
            duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.back.CrossTaskBackAnimation$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CrossTaskBackAnimation crossTaskBackAnimation2 = CrossTaskBackAnimation.this;
                    crossTaskBackAnimation2.getClass();
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    Rect rect = crossTaskBackAnimation2.mEnteringStartRect;
                    float f = rect.left;
                    Rect rect2 = crossTaskBackAnimation2.mStartTaskRect;
                    float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(rect2.left, f, animatedFraction, f);
                    float f2 = rect.top;
                    float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(rect2.top, f2, animatedFraction, f2);
                    float width = rect.width();
                    float m20m3 = DependencyGraph$$ExternalSyntheticOutline0.m20m(rect2.width(), width, animatedFraction, width);
                    float height = rect.height();
                    float m20m4 = DependencyGraph$$ExternalSyntheticOutline0.m20m(rect2.height(), height, animatedFraction, height);
                    RectF rectF = crossTaskBackAnimation2.mEnteringCurrentRect;
                    rectF.set(m20m, m20m2, m20m3 + m20m, m20m4 + m20m2);
                    crossTaskBackAnimation2.applyTransform(crossTaskBackAnimation2.mEnteringTarget.leash, rectF, crossTaskBackAnimation2.mCornerRadius);
                    SurfaceControl.Transaction transaction = crossTaskBackAnimation2.mTransaction;
                    transaction.setLayer(crossTaskBackAnimation2.mClosingTarget.leash, 0);
                    transaction.setAlpha(crossTaskBackAnimation2.mClosingTarget.leash, (animatedFraction * (-1.0f)) + 1.0f);
                    transaction.apply();
                }
            });
            duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.back.CrossTaskBackAnimation.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CrossTaskBackAnimation.this.finishAnimation();
                }
            });
            duration.start();
        }

        public final void onBackProgressed(BackMotionEvent backMotionEvent) {
            CrossTaskBackAnimation.this.mProgressAnimator.onBackProgressed(backMotionEvent);
        }

        public final void onBackStarted(BackMotionEvent backMotionEvent) {
            final CrossTaskBackAnimation crossTaskBackAnimation = CrossTaskBackAnimation.this;
            crossTaskBackAnimation.mProgressAnimator.onBackStarted(backMotionEvent, new BackProgressAnimator.ProgressCallback() { // from class: com.android.wm.shell.back.CrossTaskBackAnimation$Callback$$ExternalSyntheticLambda0
                public final void onProgressUpdate(BackEvent backEvent) {
                    CrossTaskBackAnimation crossTaskBackAnimation2 = CrossTaskBackAnimation.this;
                    boolean z = crossTaskBackAnimation2.mBackInProgress;
                    PointF pointF = crossTaskBackAnimation2.mInitialTouchPos;
                    if (!z) {
                        pointF.set(backEvent.getTouchX(), backEvent.getTouchY());
                        backEvent.getSwipeEdge();
                        crossTaskBackAnimation2.mBackInProgress = true;
                    }
                    crossTaskBackAnimation2.mProgress = backEvent.getProgress();
                    crossTaskBackAnimation2.mTouchPos.set(backEvent.getTouchX(), backEvent.getTouchY());
                    float f = 1.0f - crossTaskBackAnimation2.mProgress;
                    float f2 = 1.0f - ((f * f) * f);
                    if (crossTaskBackAnimation2.mEnteringTarget == null || crossTaskBackAnimation2.mClosingTarget == null) {
                        return;
                    }
                    float touchX = backEvent.getTouchX();
                    float touchY = backEvent.getTouchY();
                    Math.abs(touchX - pointF.x);
                    Rect rect = crossTaskBackAnimation2.mStartTaskRect;
                    float f3 = ((-0.9f) * f2) + 1.0f;
                    float width = rect.width();
                    float f4 = (((-0.14999998f) * f2) + 1.0f) * width;
                    float f5 = (((-0.25f) * f2) + 1.0f) * width;
                    float height = rect.height();
                    float f6 = height / width;
                    float f7 = f6 * f4;
                    float f8 = f6 * f5;
                    float sin = ((float) Math.sin(((touchY - pointF.y) / height) * 3.141592653589793d * 0.5d)) * 160.0f;
                    float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(height, f8, 0.5f, sin);
                    float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(height, f7, 0.5f, sin);
                    float f9 = width - (f2 * 35.0f);
                    float f10 = f9 - f5;
                    RectF rectF = crossTaskBackAnimation2.mClosingCurrentRect;
                    rectF.set(f10, m20m, f9, f8 + m20m);
                    RectF rectF2 = crossTaskBackAnimation2.mEnteringCurrentRect;
                    rectF2.set((f10 - f4) - 35.0f, m20m2, f10 - 35.0f, f7 + m20m2);
                    SurfaceControl surfaceControl = crossTaskBackAnimation2.mClosingTarget.leash;
                    float f11 = crossTaskBackAnimation2.mCornerRadius;
                    crossTaskBackAnimation2.applyTransform(surfaceControl, rectF, f11);
                    SurfaceControl surfaceControl2 = crossTaskBackAnimation2.mClosingTarget.leash;
                    SurfaceControl.Transaction transaction = crossTaskBackAnimation2.mTransaction;
                    if (surfaceControl2 != null) {
                        float[] fArr = crossTaskBackAnimation2.mTmpFloat9;
                        fArr[0] = f3;
                        fArr[1] = 0.0f;
                        fArr[2] = 0.0f;
                        fArr[3] = 0.0f;
                        fArr[4] = f3;
                        fArr[5] = 0.0f;
                        fArr[6] = 0.0f;
                        fArr[7] = 0.0f;
                        fArr[8] = f3;
                        transaction.setColorTransform(surfaceControl2, fArr, crossTaskBackAnimation2.mTmpTranslate);
                    }
                    crossTaskBackAnimation2.applyTransform(crossTaskBackAnimation2.mEnteringTarget.leash, rectF2, f11);
                    transaction.apply();
                    crossTaskBackAnimation2.mBackground.onBackProgressed(f2);
                }
            });
        }

        private Callback() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Runner extends IRemoteAnimationRunner.Default {
        public /* synthetic */ Runner(CrossTaskBackAnimation crossTaskBackAnimation, int i) {
            this();
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            RemoteAnimationTarget remoteAnimationTarget;
            if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 1867524246, 0, "Start back to task animation.", null);
            }
            for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr) {
                int i2 = remoteAnimationTarget2.mode;
                if (i2 == 1) {
                    CrossTaskBackAnimation.this.mClosingTarget = remoteAnimationTarget2;
                }
                if (i2 == 0) {
                    CrossTaskBackAnimation.this.mEnteringTarget = remoteAnimationTarget2;
                }
            }
            CrossTaskBackAnimation crossTaskBackAnimation = CrossTaskBackAnimation.this;
            if (crossTaskBackAnimation.mEnteringTarget != null && (remoteAnimationTarget = crossTaskBackAnimation.mClosingTarget) != null) {
                Rect bounds = remoteAnimationTarget.windowConfiguration.getBounds();
                Rect rect = crossTaskBackAnimation.mStartTaskRect;
                rect.set(bounds);
                rect.offsetTo(0, 0);
                crossTaskBackAnimation.mBackground.ensureBackground(crossTaskBackAnimation.mClosingTarget.windowConfiguration.getBounds(), 4408122, crossTaskBackAnimation.mTransaction);
            } else if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1749500832, 0, "Entering target or closing target is null.", null);
            }
            CrossTaskBackAnimation.this.mFinishCallback = iRemoteAnimationFinishedCallback;
        }

        private Runner() {
        }
    }

    public CrossTaskBackAnimation(Context context, BackAnimationBackground backAnimationBackground) {
        int i = 0;
        this.mCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mBackAnimationRunner = new BackAnimationRunner(new Callback(this, i), new Runner(this, i));
        this.mBackground = backAnimationBackground;
    }

    public final void applyTransform(SurfaceControl surfaceControl, RectF rectF, float f) {
        if (surfaceControl == null) {
            return;
        }
        float width = rectF.width();
        Rect rect = this.mStartTaskRect;
        float width2 = width / rect.width();
        Matrix matrix = this.mTransformMatrix;
        matrix.reset();
        matrix.setScale(width2, width2);
        matrix.postTranslate(rectF.left, rectF.top);
        this.mTransaction.setMatrix(surfaceControl, matrix, this.mTmpFloat9).setWindowCrop(surfaceControl, rect).setCornerRadius(surfaceControl, f);
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
        this.mClosingCurrentRect.setEmpty();
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
    }
}
