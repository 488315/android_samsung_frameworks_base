package com.android.p038wm.shell.back;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.FloatProperty;
import android.view.Choreographer;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.window.BackEvent;
import android.window.BackMotionEvent;
import android.window.BackNavigationInfo;
import android.window.BackProgressAnimator;
import android.window.IOnBackInvokedCallback;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.policy.TransitionAnimation;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CustomizeActivityAnimation {
    public static final C37861 ENTER_PROGRESS_PROP = new FloatProperty("enter") { // from class: com.android.wm.shell.back.CustomizeActivityAnimation.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((CustomizeActivityAnimation) obj).mLatestProgress * 1000.0f);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            CustomizeActivityAnimation customizeActivityAnimation = (CustomizeActivityAnimation) obj;
            float f2 = f / 1000.0f;
            customizeActivityAnimation.mLatestProgress = f2;
            customizeActivityAnimation.applyTransformTransaction(f2);
        }
    };
    public final BackAnimationRunner mBackAnimationRunner;
    public final BackAnimationBackground mBackground;
    public final Choreographer mChoreographer;
    public Animation mCloseAnimation;
    public RemoteAnimationTarget mClosingTarget;
    public final float mCornerRadius;
    public final CustomAnimationLoader mCustomAnimationLoader;
    public final DecelerateInterpolator mDecelerateInterpolator;
    public Animation mEnterAnimation;
    public RemoteAnimationTarget mEnteringTarget;
    public IRemoteAnimationFinishedCallback mFinishCallback;
    public float mLatestProgress;
    public int mNextBackgroundColor;
    public final BackProgressAnimator mProgressAnimator;
    public final SpringAnimation mProgressSpring;
    public final float[] mTmpFloat9;
    public final SurfaceControl.Transaction mTransaction;
    public final Transformation mTransformation;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AnimationLoadResult {
        public int mBackgroundColor;
        public Animation mCloseAnimation;
        public Animation mEnterAnimation;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Callback extends IOnBackInvokedCallback.Default {
        public /* synthetic */ Callback(CustomizeActivityAnimation customizeActivityAnimation, int i) {
            this();
        }

        public final void onBackCancelled() {
            final CustomizeActivityAnimation customizeActivityAnimation = CustomizeActivityAnimation.this;
            customizeActivityAnimation.mProgressAnimator.onBackCancelled(new Runnable() { // from class: com.android.wm.shell.back.CustomizeActivityAnimation$Callback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CustomizeActivityAnimation.this.finishAnimation();
                }
            });
        }

        public final void onBackInvoked() {
            CustomizeActivityAnimation.this.mProgressAnimator.reset();
            final CustomizeActivityAnimation customizeActivityAnimation = CustomizeActivityAnimation.this;
            if (customizeActivityAnimation.mEnteringTarget == null || customizeActivityAnimation.mClosingTarget == null || customizeActivityAnimation.mCloseAnimation == null || customizeActivityAnimation.mEnterAnimation == null) {
                customizeActivityAnimation.finishAnimation();
                return;
            }
            customizeActivityAnimation.mProgressSpring.cancel();
            ValueAnimator duration = ValueAnimator.ofFloat(customizeActivityAnimation.mLatestProgress, 1.0f).setDuration(250L);
            duration.setInterpolator(customizeActivityAnimation.mDecelerateInterpolator);
            duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.back.CustomizeActivityAnimation$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    CustomizeActivityAnimation customizeActivityAnimation2 = CustomizeActivityAnimation.this;
                    customizeActivityAnimation2.getClass();
                    customizeActivityAnimation2.applyTransformTransaction(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            duration.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.back.CustomizeActivityAnimation.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    CustomizeActivityAnimation.this.finishAnimation();
                }
            });
            duration.start();
        }

        public final void onBackProgressed(BackMotionEvent backMotionEvent) {
            CustomizeActivityAnimation.this.mProgressAnimator.onBackProgressed(backMotionEvent);
        }

        public final void onBackStarted(BackMotionEvent backMotionEvent) {
            final CustomizeActivityAnimation customizeActivityAnimation = CustomizeActivityAnimation.this;
            customizeActivityAnimation.mProgressAnimator.onBackStarted(backMotionEvent, new BackProgressAnimator.ProgressCallback() { // from class: com.android.wm.shell.back.CustomizeActivityAnimation$Callback$$ExternalSyntheticLambda0
                public final void onProgressUpdate(BackEvent backEvent) {
                    CustomizeActivityAnimation customizeActivityAnimation2 = CustomizeActivityAnimation.this;
                    if (customizeActivityAnimation2.mEnteringTarget == null || customizeActivityAnimation2.mClosingTarget == null || customizeActivityAnimation2.mCloseAnimation == null || customizeActivityAnimation2.mEnterAnimation == null) {
                        return;
                    }
                    float progress = backEvent.getProgress();
                    customizeActivityAnimation2.mProgressSpring.animateToFinalPosition((progress > 0.1f ? (((progress - 0.1f) * 0.5f) / 0.9f) + 0.5f : (((progress - 0.0f) * 0.5f) / 1.0f) + 0.0f) * 1000.0f);
                }
            });
        }

        private Callback() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CustomAnimationLoader {
        public final TransitionAnimation mTransitionAnimation;

        public CustomAnimationLoader(Context context) {
            this.mTransitionAnimation = new TransitionAnimation(context, false, "CustomizeBackAnimation");
        }

        public final Animation loadAnimation(BackNavigationInfo.CustomAnimationInfo customAnimationInfo, boolean z) {
            Animation animation;
            TransitionAnimation transitionAnimation = this.mTransitionAnimation;
            if ((z && customAnimationInfo.getCustomEnterAnim() != 0) || (!z && customAnimationInfo.getCustomExitAnim() != 0)) {
                animation = transitionAnimation.loadAppTransitionAnimation(customAnimationInfo.getPackageName(), z ? customAnimationInfo.getCustomEnterAnim() : customAnimationInfo.getCustomExitAnim());
            } else if (customAnimationInfo.getWindowAnimations() != 0) {
                animation = transitionAnimation.loadAnimationAttr(customAnimationInfo.getPackageName(), customAnimationInfo.getWindowAnimations(), z ? 6 : 7, false);
            } else {
                animation = null;
            }
            if (animation == null && z) {
                animation = transitionAnimation.loadDefaultAnimationAttr(6, false);
            }
            if (animation != null) {
                if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -550774733, 0, "custom animation loaded %s", String.valueOf(animation));
                }
            } else if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 1636471665, 0, "No custom animation loaded", null);
            }
            return animation;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Runner extends IRemoteAnimationRunner.Default {
        public /* synthetic */ Runner(CustomizeActivityAnimation customizeActivityAnimation, int i) {
            this();
        }

        public final void onAnimationCancelled() {
            CustomizeActivityAnimation.this.finishAnimation();
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            RemoteAnimationTarget remoteAnimationTarget;
            Animation animation;
            if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 216824038, 0, "Start back to customize animation.", null);
            }
            for (RemoteAnimationTarget remoteAnimationTarget2 : remoteAnimationTargetArr) {
                int i2 = remoteAnimationTarget2.mode;
                if (i2 == 1) {
                    CustomizeActivityAnimation.this.mClosingTarget = remoteAnimationTarget2;
                }
                if (i2 == 0) {
                    CustomizeActivityAnimation.this.mEnteringTarget = remoteAnimationTarget2;
                }
            }
            CustomizeActivityAnimation customizeActivityAnimation = CustomizeActivityAnimation.this;
            if ((customizeActivityAnimation.mCloseAnimation == null || customizeActivityAnimation.mEnterAnimation == null) && ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, -1198426044, 0, "No animation loaded, should choose cross-activity animation?", null);
            }
            CustomizeActivityAnimation customizeActivityAnimation2 = CustomizeActivityAnimation.this;
            if (customizeActivityAnimation2.mEnteringTarget != null && (remoteAnimationTarget = customizeActivityAnimation2.mClosingTarget) != null && (animation = customizeActivityAnimation2.mCloseAnimation) != null && customizeActivityAnimation2.mEnterAnimation != null) {
                Rect rect = remoteAnimationTarget.localBounds;
                int width = rect.width();
                int height = rect.height();
                animation.initialize(width, height, width, height);
                Animation animation2 = customizeActivityAnimation2.mEnterAnimation;
                Rect rect2 = customizeActivityAnimation2.mEnteringTarget.localBounds;
                int width2 = rect2.width();
                int height2 = rect2.height();
                animation2.initialize(width2, height2, width2, height2);
                ActivityManager.RunningTaskInfo runningTaskInfo = customizeActivityAnimation2.mEnteringTarget.taskInfo;
                if (runningTaskInfo != null && runningTaskInfo.taskDescription != null) {
                    Rect bounds = customizeActivityAnimation2.mClosingTarget.windowConfiguration.getBounds();
                    int i3 = customizeActivityAnimation2.mNextBackgroundColor;
                    if (i3 == 0) {
                        i3 = customizeActivityAnimation2.mEnteringTarget.taskInfo.taskDescription.getBackgroundColor();
                    }
                    customizeActivityAnimation2.mBackground.ensureBackground(bounds, i3, customizeActivityAnimation2.mTransaction);
                }
            } else if (ShellProtoLogCache.WM_SHELL_BACK_PREVIEW_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, 744990182, 0, "Entering target or closing target is null.", null);
            }
            CustomizeActivityAnimation.this.mFinishCallback = iRemoteAnimationFinishedCallback;
        }

        private Runner() {
        }
    }

    public CustomizeActivityAnimation(Context context, BackAnimationBackground backAnimationBackground) {
        this(context, backAnimationBackground, new SurfaceControl.Transaction(), null);
    }

    public final void applyTransform(SurfaceControl surfaceControl, float f, Animation animation) {
        Transformation transformation = this.mTransformation;
        transformation.clear();
        animation.getTransformationAt(f, transformation);
        Matrix matrix = transformation.getMatrix();
        float[] fArr = this.mTmpFloat9;
        SurfaceControl.Transaction transaction = this.mTransaction;
        transaction.setMatrix(surfaceControl, matrix, fArr);
        transaction.setAlpha(surfaceControl, Math.max(transformation.getAlpha(), 0.005f));
        transaction.setCornerRadius(surfaceControl, this.mCornerRadius);
    }

    public final void applyTransformTransaction(float f) {
        RemoteAnimationTarget remoteAnimationTarget = this.mClosingTarget;
        if (remoteAnimationTarget == null || this.mEnteringTarget == null) {
            return;
        }
        applyTransform(remoteAnimationTarget.leash, f, this.mCloseAnimation);
        applyTransform(this.mEnteringTarget.leash, f, this.mEnterAnimation);
        long vsyncId = this.mChoreographer.getVsyncId();
        SurfaceControl.Transaction transaction = this.mTransaction;
        transaction.setFrameTimelineVsync(vsyncId);
        transaction.apply();
    }

    public final void finishAnimation() {
        Animation animation = this.mCloseAnimation;
        if (animation != null) {
            animation.reset();
            this.mCloseAnimation = null;
        }
        Animation animation2 = this.mEnterAnimation;
        if (animation2 != null) {
            animation2.reset();
            this.mEnterAnimation = null;
        }
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
        transaction.setFrameTimelineVsync(this.mChoreographer.getVsyncId());
        transaction.apply();
        this.mTransformation.clear();
        this.mLatestProgress = 0.0f;
        this.mNextBackgroundColor = 0;
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.mFinishCallback;
        if (iRemoteAnimationFinishedCallback != null) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            this.mFinishCallback = null;
        }
        SpringAnimation springAnimation = this.mProgressSpring;
        springAnimation.animateToFinalPosition(0.0f);
        springAnimation.skipToEnd();
    }

    public CustomizeActivityAnimation(Context context, BackAnimationBackground backAnimationBackground, SurfaceControl.Transaction transaction, Choreographer choreographer) {
        this.mProgressAnimator = new BackProgressAnimator();
        this.mLatestProgress = 0.0f;
        this.mTmpFloat9 = new float[9];
        this.mDecelerateInterpolator = new DecelerateInterpolator();
        this.mTransformation = new Transformation();
        this.mCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mBackground = backAnimationBackground;
        int i = 0;
        this.mBackAnimationRunner = new BackAnimationRunner(new Callback(this, i), new Runner(this, i));
        this.mCustomAnimationLoader = new CustomAnimationLoader(context);
        SpringAnimation springAnimation = new SpringAnimation(this, ENTER_PROGRESS_PROP);
        this.mProgressSpring = springAnimation;
        springAnimation.setSpring(new SpringForce().setStiffness(1500.0f).setDampingRatio(1.0f));
        this.mTransaction = transaction == null ? new SurfaceControl.Transaction() : transaction;
        this.mChoreographer = choreographer == null ? Choreographer.getInstance() : choreographer;
    }
}
