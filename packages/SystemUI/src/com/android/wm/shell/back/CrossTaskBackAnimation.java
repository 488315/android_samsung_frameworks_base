package com.android.wm.shell.back;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.RemoteException;
import android.view.Choreographer;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.MotionEvent;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.window.BackEvent;
import android.window.BackMotionEvent;
import android.window.BackProgressAnimator;
import android.window.IOnBackInvokedCallback;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.internal.dynamicanimation.animation.FloatValueHolder;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.policy.SystemBarUtils;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.wm.shell.back.CrossTaskBackAnimation;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;

/* loaded from: classes3.dex */
public class CrossTaskBackAnimation extends ShellBackAnimation {
    public final BackAnimationRunner mBackAnimationRunner;
    public final BackAnimationBackground mBackground;
    public RemoteAnimationTarget mClosingTarget;
    public final Context mContext;
    public float mCornerRadius;
    public RemoteAnimationTarget mEnteringTarget;
    public IRemoteAnimationFinishedCallback mFinishCallback;
    public float mInterWindowMargin;
    public boolean mIsRightEdge;
    public int mStatusbarHeight;
    public float mVerticalMargin;
    public final Rect mStartTaskRect = new Rect();
    public final Rect mClosingStartRect = new Rect();
    public final RectF mClosingCurrentRect = new RectF();
    public final Rect mEnteringStartRect = new Rect();
    public final RectF mEnteringCurrentRect = new RectF();
    public final PointF mInitialTouchPos = new PointF();
    public final Interpolator mPostAnimationInterpolator = Interpolators.EMPHASIZED;
    public final Interpolator mProgressInterpolator = Interpolators.BACK_GESTURE;
    public final Interpolator mVerticalMoveInterpolator = new DecelerateInterpolator();
    public final Matrix mTransformMatrix = new Matrix();
    public final float[] mTmpFloat9 = new float[9];
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
    public boolean mBackInProgress = false;
    public final PointF mTouchPos = new PointF();
    public final BackProgressAnimator mProgressAnimator = new BackProgressAnimator();
    public final FloatValueHolder mPostCommitFlingScale = new FloatValueHolder(100.0f);
    public final SpringForce mPostCommitFlingSpring = new SpringForce(100.0f).setStiffness(320.0f).setDampingRatio(1.0f);
    public final ProgressVelocityTracker mVelocityTracker = new ProgressVelocityTracker();
    public float mGestureProgress = 0.0f;

    public final class Callback extends IOnBackInvokedCallback.Default {
        public static final /* synthetic */ int $r8$clinit = 0;

        public /* synthetic */ Callback(CrossTaskBackAnimation crossTaskBackAnimation, int i) {
            this();
        }

        public final void onBackCancelled() {
            final CrossTaskBackAnimation crossTaskBackAnimation = CrossTaskBackAnimation.this;
            crossTaskBackAnimation.mProgressAnimator.onBackCancelled(new Runnable() { // from class: com.android.wm.shell.back.CrossTaskBackAnimation$Callback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CrossTaskBackAnimation crossTaskBackAnimation2 = crossTaskBackAnimation;
                    int i = CrossTaskBackAnimation.Callback.$r8$clinit;
                    crossTaskBackAnimation2.finishAnimation$1();
                }
            });
        }

        public final void onBackInvoked() {
            float f;
            CrossTaskBackAnimation.this.mProgressAnimator.reset();
            final CrossTaskBackAnimation crossTaskBackAnimation = CrossTaskBackAnimation.this;
            if (crossTaskBackAnimation.mEnteringTarget == null || crossTaskBackAnimation.mClosingTarget == null) {
                crossTaskBackAnimation.finishAnimation$1();
                return;
            }
            if (crossTaskBackAnimation.mGestureProgress < 0.1f) {
                f = -320.0f;
            } else {
                ProgressVelocityTracker progressVelocityTracker = crossTaskBackAnimation.mVelocityTracker;
                progressVelocityTracker.velocityTracker.computeCurrentVelocity(1000);
                f = -progressVelocityTracker.velocityTracker.getXVelocity();
            }
            SpringAnimation spring = new SpringAnimation(crossTaskBackAnimation.mPostCommitFlingScale, 100.0f).setStartVelocity(Math.max(-1000.0f, Math.min(0.0f, f))).setStartValue(100.0f).setMinimumVisibleChange(0.1f).setSpring(crossTaskBackAnimation.mPostCommitFlingSpring);
            spring.start();
            spring.doAnimationFrame(Choreographer.getInstance().getLastFrameTimeNanos() / 1000000);
            crossTaskBackAnimation.mEnteringCurrentRect.round(crossTaskBackAnimation.mEnteringStartRect);
            crossTaskBackAnimation.mClosingCurrentRect.round(crossTaskBackAnimation.mClosingStartRect);
            ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(500L);
            duration.setInterpolator(crossTaskBackAnimation.mPostAnimationInterpolator);
            duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.back.CrossTaskBackAnimation$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CrossTaskBackAnimation crossTaskBackAnimation2 = crossTaskBackAnimation;
                    crossTaskBackAnimation2.getClass();
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    float fMapRange = CrossTaskBackAnimation.mapRange(animatedFraction, crossTaskBackAnimation2.mEnteringStartRect.left, crossTaskBackAnimation2.mStartTaskRect.left);
                    float fMapRange2 = CrossTaskBackAnimation.mapRange(animatedFraction, crossTaskBackAnimation2.mEnteringStartRect.top, crossTaskBackAnimation2.mStartTaskRect.top);
                    crossTaskBackAnimation2.mEnteringCurrentRect.set(fMapRange, fMapRange2, CrossTaskBackAnimation.mapRange(animatedFraction, crossTaskBackAnimation2.mEnteringStartRect.width(), crossTaskBackAnimation2.mStartTaskRect.width()) + fMapRange, CrossTaskBackAnimation.mapRange(animatedFraction, crossTaskBackAnimation2.mEnteringStartRect.height(), crossTaskBackAnimation2.mStartTaskRect.height()) + fMapRange2);
                    crossTaskBackAnimation2.applyFlingScale(crossTaskBackAnimation2.mEnteringCurrentRect);
                    crossTaskBackAnimation2.applyTransform(crossTaskBackAnimation2.mEnteringTarget.leash, crossTaskBackAnimation2.mEnteringCurrentRect, crossTaskBackAnimation2.mCornerRadius);
                    Rect rect = crossTaskBackAnimation2.mStartTaskRect;
                    Rect rect2 = crossTaskBackAnimation2.mStartTaskRect;
                    float fMapRange3 = CrossTaskBackAnimation.mapRange(animatedFraction, crossTaskBackAnimation2.mClosingStartRect.left, ((rect.width() * 0.19999999f) / 2.0f) + rect.left);
                    float fMapRange4 = CrossTaskBackAnimation.mapRange(animatedFraction, crossTaskBackAnimation2.mClosingStartRect.top, ((rect2.height() * 0.19999999f) / 2.0f) + rect2.top);
                    float fMapRange5 = CrossTaskBackAnimation.mapRange(animatedFraction, crossTaskBackAnimation2.mClosingStartRect.width(), crossTaskBackAnimation2.mStartTaskRect.width() * 0.8f);
                    float fMapRange6 = CrossTaskBackAnimation.mapRange(animatedFraction, crossTaskBackAnimation2.mClosingStartRect.height(), crossTaskBackAnimation2.mStartTaskRect.height() * 0.8f);
                    SurfaceControl surfaceControl = crossTaskBackAnimation2.mClosingTarget.leash;
                    if (surfaceControl != null && surfaceControl.isValid()) {
                        crossTaskBackAnimation2.mTransaction.setLayer(crossTaskBackAnimation2.mClosingTarget.leash, 0);
                    }
                    crossTaskBackAnimation2.mClosingCurrentRect.set(fMapRange3, fMapRange4, fMapRange5 + fMapRange3, fMapRange6 + fMapRange4);
                    crossTaskBackAnimation2.applyFlingScale(crossTaskBackAnimation2.mClosingCurrentRect);
                    crossTaskBackAnimation2.applyTransform(crossTaskBackAnimation2.mClosingTarget.leash, crossTaskBackAnimation2.mClosingCurrentRect, crossTaskBackAnimation2.mCornerRadius);
                    if (animatedFraction > 0.8f) {
                        crossTaskBackAnimation2.mBackground.mCustomizer.customizeStatusBarAppearance(null);
                    }
                    crossTaskBackAnimation2.applyTransaction$1();
                }
            });
            duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.back.CrossTaskBackAnimation.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CrossTaskBackAnimation.this.mBackground.mCustomizer.customizeStatusBarAppearance(null);
                    CrossTaskBackAnimation.this.finishAnimation$1();
                }
            });
            duration.start();
        }

        public final void onBackProgressed(BackMotionEvent backMotionEvent) {
            CrossTaskBackAnimation.this.mProgressAnimator.onBackProgressed(backMotionEvent);
        }

        public final void onBackStarted(BackMotionEvent backMotionEvent) {
            CrossTaskBackAnimation.this.mProgressAnimator.removeOnBackCancelledFinishCallback();
            CrossTaskBackAnimation.this.mIsRightEdge = backMotionEvent.getSwipeEdge() == 1;
            CrossTaskBackAnimation.this.mInitialTouchPos.set(backMotionEvent.getTouchX(), backMotionEvent.getTouchY());
            final CrossTaskBackAnimation crossTaskBackAnimation = CrossTaskBackAnimation.this;
            crossTaskBackAnimation.mProgressAnimator.onBackStarted(backMotionEvent, new BackProgressAnimator.ProgressCallback() { // from class: com.android.wm.shell.back.CrossTaskBackAnimation$Callback$$ExternalSyntheticLambda0
                public final void onProgressUpdate(BackEvent backEvent) {
                    CrossTaskBackAnimation crossTaskBackAnimation2 = crossTaskBackAnimation;
                    int i = CrossTaskBackAnimation.Callback.$r8$clinit;
                    if (!crossTaskBackAnimation2.mBackInProgress) {
                        crossTaskBackAnimation2.mBackInProgress = true;
                    }
                    float progress = backEvent.getProgress();
                    crossTaskBackAnimation2.mTouchPos.set(backEvent.getTouchX(), backEvent.getTouchY());
                    float interpolation = crossTaskBackAnimation2.mProgressInterpolator.getInterpolation(progress);
                    long frameTimeMillis = backEvent.getFrameTimeMillis();
                    float f = interpolation * 100.0f;
                    ProgressVelocityTracker progressVelocityTracker = crossTaskBackAnimation2.mVelocityTracker;
                    if (progressVelocityTracker.downTime == -1) {
                        progressVelocityTracker.downTime = frameTimeMillis;
                    }
                    progressVelocityTracker.velocityTracker.addMovement(MotionEvent.obtain(progressVelocityTracker.downTime, frameTimeMillis, 2, f, 0.0f, 0));
                    if (crossTaskBackAnimation2.mEnteringTarget == null || crossTaskBackAnimation2.mClosingTarget == null) {
                        return;
                    }
                    crossTaskBackAnimation2.mGestureProgress = interpolation;
                    float touchY = backEvent.getTouchY();
                    int iWidth = crossTaskBackAnimation2.mStartTaskRect.width();
                    int iHeight = crossTaskBackAnimation2.mStartTaskRect.height();
                    float fMapRange = CrossTaskBackAnimation.mapRange(interpolation, 1.0f, 0.8f);
                    float f2 = iWidth;
                    float f3 = fMapRange * f2;
                    float f4 = iHeight;
                    float f5 = fMapRange * f4;
                    float f6 = touchY - crossTaskBackAnimation2.mInitialTouchPos.y;
                    float f7 = f4 / 2.0f;
                    float f8 = f4 - f5;
                    float fMax = (f8 * 0.5f) + (Math.max(0.0f, (f8 / 2.0f) - crossTaskBackAnimation2.mVerticalMargin) * ((DecelerateInterpolator) crossTaskBackAnimation2.mVerticalMoveInterpolator).getInterpolation(Math.min(f7, Math.abs(f6)) / f7) * (f6 < 0.0f ? -1.0f : 1.0f));
                    float fM$1 = crossTaskBackAnimation2.mIsRightEdge ? DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f3, 0.5f, f3) : f2 - (interpolation * crossTaskBackAnimation2.mVerticalMargin);
                    float f9 = fM$1 - f3;
                    float f10 = f5 + fMax;
                    crossTaskBackAnimation2.mClosingCurrentRect.set(f9, fMax, fM$1, f10);
                    RectF rectF = crossTaskBackAnimation2.mEnteringCurrentRect;
                    float f11 = crossTaskBackAnimation2.mInterWindowMargin;
                    rectF.set((f9 - f3) - f11, fMax, f9 - f11, f10);
                    crossTaskBackAnimation2.applyTransform(crossTaskBackAnimation2.mClosingTarget.leash, crossTaskBackAnimation2.mClosingCurrentRect, crossTaskBackAnimation2.mCornerRadius);
                    crossTaskBackAnimation2.applyTransform(crossTaskBackAnimation2.mEnteringTarget.leash, crossTaskBackAnimation2.mEnteringCurrentRect, crossTaskBackAnimation2.mCornerRadius);
                    crossTaskBackAnimation2.applyTransaction$1();
                    crossTaskBackAnimation2.mBackground.customizeStatusBarAppearance((int) fMax);
                }
            });
        }

        private Callback() {
        }
    }

    public final class Runner extends IRemoteAnimationRunner.Default {
        public /* synthetic */ Runner(CrossTaskBackAnimation crossTaskBackAnimation, int i) {
            this();
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            RemoteAnimationTarget remoteAnimationTarget;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -8349216159198398073L, 0, null);
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
                crossTaskBackAnimation.mStartTaskRect.set(remoteAnimationTarget.windowConfiguration.getBounds());
                crossTaskBackAnimation.mStartTaskRect.offsetTo(0, 0);
                crossTaskBackAnimation.mStartTaskRect.inset(0, 0, 0, crossTaskBackAnimation.mClosingTarget.contentInsets.bottom);
                Rect bounds = crossTaskBackAnimation.mClosingTarget.windowConfiguration.getBounds();
                SurfaceControl.Transaction transaction = crossTaskBackAnimation.mTransaction;
                int i3 = crossTaskBackAnimation.mStatusbarHeight;
                crossTaskBackAnimation.mClosingTarget.taskInfo.getDisplayId();
                crossTaskBackAnimation.mBackground.ensureBackground(bounds, 4408122, transaction, i3, null, 0.0f);
                crossTaskBackAnimation.mInterWindowMargin = crossTaskBackAnimation.mContext.getResources().getDimension(R.dimen.cross_task_back_inter_window_margin);
                crossTaskBackAnimation.mVerticalMargin = crossTaskBackAnimation.mContext.getResources().getDimension(R.dimen.cross_task_back_vertical_margin);
            } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 2843564892737714090L, 0, null);
            }
            CrossTaskBackAnimation.this.mFinishCallback = iRemoteAnimationFinishedCallback;
        }

        private Runner() {
        }
    }

    public CrossTaskBackAnimation(Context context, BackAnimationBackground backAnimationBackground, Handler handler) {
        int i = 0;
        this.mBackAnimationRunner = new BackAnimationRunner(new Callback(this, i), new Runner(this, i), context, 85, handler);
        this.mBackground = backAnimationBackground;
        this.mContext = context;
        this.mCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mStatusbarHeight = SystemBarUtils.getStatusBarHeight(context);
    }

    public static float mapRange(float f, float f2, float f3) {
        return DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f3, f2, f, f2);
    }

    public final void applyFlingScale(RectF rectF) {
        float fMin = Math.min(this.mPostCommitFlingScale.getValue() / 100.0f, 1.0f);
        if (fMin >= 1.0f) {
            return;
        }
        float f = rectF.right;
        float fHeight = (rectF.height() / 2.0f) + rectF.top;
        rectF.offset(-f, -fHeight);
        rectF.scale(fMin);
        rectF.offset(f, fHeight);
    }

    public final void applyTransaction$1() {
        this.mTransaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        this.mTransaction.apply();
    }

    public final void applyTransform(SurfaceControl surfaceControl, RectF rectF, float f) {
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        float fWidth = rectF.width() / this.mStartTaskRect.width();
        this.mTransformMatrix.reset();
        this.mTransformMatrix.setScale(fWidth, fWidth);
        this.mTransformMatrix.postTranslate(rectF.left, rectF.top);
        this.mTransaction.setMatrix(surfaceControl, this.mTransformMatrix, this.mTmpFloat9).setWindowCrop(surfaceControl, this.mStartTaskRect).setCornerRadius(surfaceControl, f);
    }

    public final void finishAnimation$1() {
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
        BackAnimationBackground backAnimationBackground = this.mBackground;
        if (backAnimationBackground != null) {
            SurfaceControl.Transaction transaction = this.mTransaction;
            SurfaceControl surfaceControl = backAnimationBackground.mBackgroundSurface;
            if (surfaceControl != null) {
                if (surfaceControl.isValid()) {
                    transaction.remove(backAnimationBackground.mBackgroundSurface);
                }
                backAnimationBackground.mBackgroundSurface = null;
                backAnimationBackground.mIsRequestingStatusBarAppearance = false;
            }
        }
        applyTransaction$1();
        this.mBackInProgress = false;
        this.mTransformMatrix.reset();
        this.mClosingCurrentRect.setEmpty();
        this.mInitialTouchPos.set(0.0f, 0.0f);
        this.mGestureProgress = 0.0f;
        ProgressVelocityTracker progressVelocityTracker = this.mVelocityTracker;
        progressVelocityTracker.velocityTracker.clear();
        progressVelocityTracker.downTime = -1L;
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.mFinishCallback;
        if (iRemoteAnimationFinishedCallback != null) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException unused) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BACK_PREVIEW_enabled[4]) {
                    ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -4706434252374524577L, 0, null);
                }
            }
            this.mFinishCallback = null;
        }
    }

    @Override // com.android.wm.shell.back.ShellBackAnimation
    public final BackAnimationRunner getRunner() {
        return this.mBackAnimationRunner;
    }

    @Override // com.android.wm.shell.back.ShellBackAnimation
    public final void onConfigurationChanged() {
        this.mCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(this.mContext);
        this.mStatusbarHeight = SystemBarUtils.getStatusBarHeight(this.mContext);
    }
}
