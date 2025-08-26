package com.android.wm.shell.back;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.MathUtils;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.window.BackMotionEvent;
import android.window.BackNavigationInfo;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.back.CrossActivityBackAnimation;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class CustomCrossActivityBackAnimation extends CrossActivityBackAnimation {
    public Animation closeAnimation;
    public final CustomAnimationLoader customAnimationLoader;
    public Animation enterAnimation;
    public final Transformation transformation;

    public final class AnimationLoadResult {
        public int backgroundColor;
        public Animation closeAnimation;
        public Animation enterAnimation;
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CustomCrossActivityBackAnimation(Context context, BackAnimationBackground backAnimationBackground, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, SurfaceControl.Transaction transaction, CustomAnimationLoader customAnimationLoader, Handler handler) {
        super(context, backAnimationBackground, rootTaskDisplayAreaOrganizer, transaction, handler);
        this.customAnimationLoader = customAnimationLoader;
        this.transformation = new Transformation();
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final void finishAnimation() {
        Animation animation = this.closeAnimation;
        if (animation != null) {
            animation.reset();
        }
        this.closeAnimation = null;
        Animation animation2 = this.enterAnimation;
        if (animation2 != null) {
            animation2.reset();
        }
        this.enterAnimation = null;
        this.transformation.clear();
        super.finishAnimation();
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final boolean getAllowEnteringYShift() {
        return false;
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final long getPostCommitAnimationDuration() {
        Animation animation = this.closeAnimation;
        animation.getClass();
        long duration = animation.getDuration();
        Animation animation2 = this.enterAnimation;
        animation2.getClass();
        return Math.min(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, Math.max(duration, animation2.getDuration()));
    }

    public final float getPostCommitProgress(Animation animation, float f) {
        if (animation.getDuration() == 0) {
            return 1.0f;
        }
        return Math.min(1.0f, (getPostCommitAnimationDuration() / Math.min(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, animation.getDuration())) * f);
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final Transformation getPreCommitEnteringBaseTransformation(float f) {
        this.transformation.clear();
        Animation animation = this.enterAnimation;
        animation.getClass();
        animation.getTransformationAt(f * 0.2f, this.transformation);
        return this.transformation;
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final void onPostCommitProgress(float f) {
        super.onPostCommitProgress(f);
        if (this.closingTarget == null || this.enteringTarget == null) {
            return;
        }
        Animation animation = this.closeAnimation;
        animation.getClass();
        float postCommitProgress = getPostCommitProgress(animation, f);
        RemoteAnimationTarget remoteAnimationTarget = this.closingTarget;
        remoteAnimationTarget.getClass();
        SurfaceControl surfaceControl = remoteAnimationTarget.leash;
        RectF rectF = this.currentClosingRect;
        Animation animation2 = this.closeAnimation;
        animation2.getClass();
        CrossActivityBackAnimation.FlingMode flingMode = CrossActivityBackAnimation.FlingMode.FLING_SHRINK;
        this.transformation.clear();
        animation2.getTransformationAt(postCommitProgress, this.transformation);
        applyTransform(surfaceControl, rectF, this.transformation.getAlpha(), this.transformation, flingMode);
        float f2 = this.gestureProgress * 0.2f;
        Animation animation3 = this.enterAnimation;
        animation3.getClass();
        float fLerp = MathUtils.lerp(f2, 1.0f, getPostCommitProgress(animation3, f));
        RemoteAnimationTarget remoteAnimationTarget2 = this.enteringTarget;
        remoteAnimationTarget2.getClass();
        SurfaceControl surfaceControl2 = remoteAnimationTarget2.leash;
        RectF rectF2 = this.currentEnteringRect;
        Animation animation4 = this.enterAnimation;
        animation4.getClass();
        CrossActivityBackAnimation.FlingMode flingMode2 = CrossActivityBackAnimation.FlingMode.NO_FLING;
        this.transformation.clear();
        animation4.getTransformationAt(fLerp, this.transformation);
        applyTransform(surfaceControl2, rectF2, this.transformation.getAlpha(), this.transformation, flingMode2);
        applyTransaction();
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation, com.android.wm.shell.back.ShellBackAnimation
    public final boolean prepareNextAnimation(BackNavigationInfo.CustomAnimationInfo customAnimationInfo, int i) {
        Animation animationLoadAnimation;
        this.letterboxColor = i;
        if (customAnimationInfo == null) {
            return false;
        }
        CustomAnimationLoader customAnimationLoader = this.customAnimationLoader;
        customAnimationLoader.getClass();
        AnimationLoadResult animationLoadResult = null;
        if (customAnimationInfo.getPackageName().length() != 0 && (animationLoadAnimation = customAnimationLoader.loadAnimation(customAnimationInfo, false)) != null) {
            Animation animationLoadAnimation2 = customAnimationLoader.loadAnimation(customAnimationInfo, true);
            animationLoadResult = new AnimationLoadResult();
            animationLoadResult.closeAnimation = animationLoadAnimation;
            animationLoadResult.enterAnimation = animationLoadAnimation2;
            animationLoadResult.backgroundColor = customAnimationInfo.getCustomBackground();
        }
        if (animationLoadResult == null) {
            return false;
        }
        this.closeAnimation = animationLoadResult.closeAnimation;
        this.enterAnimation = animationLoadResult.enterAnimation;
        this.customizedBackgroundColor = animationLoadResult.backgroundColor;
        return true;
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final void preparePreCommitClosingRectMovement(int i) {
        this.startClosingRect.set(this.backAnimRect);
        this.targetClosingRect.set(this.startClosingRect);
        CrossActivityBackAnimationKt.scaleCentered$default(this.targetClosingRect, 0.9f);
        float f = this.displayBoundsMargin;
        this.targetClosingRect.offset(i != 1 ? (this.startClosingRect.right - this.targetClosingRect.right) - f : (-this.targetClosingRect.left) + f, 0.0f);
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final void preparePreCommitEnteringRectMovement() {
        this.startEnteringRect.set(this.startClosingRect);
        this.targetEnteringRect.set(this.startClosingRect);
    }

    @Override // com.android.wm.shell.back.CrossActivityBackAnimation
    public final void startBackAnimation(BackMotionEvent backMotionEvent) {
        RemoteAnimationTarget remoteAnimationTarget;
        super.startBackAnimation(backMotionEvent);
        Animation animation = this.closeAnimation;
        if (animation == null || this.enterAnimation == null || (remoteAnimationTarget = this.closingTarget) == null || this.enteringTarget == null) {
            ProtoLog.d(ShellProtoLogGroup.WM_SHELL_BACK_PREVIEW, "Enter animation or close animation is null.", new Object[0]);
            return;
        }
        remoteAnimationTarget.getClass();
        Rect rect = remoteAnimationTarget.localBounds;
        int iWidth = rect.width();
        int iHeight = rect.height();
        animation.initialize(iWidth, iHeight, iWidth, iHeight);
        Animation animation2 = this.enterAnimation;
        animation2.getClass();
        RemoteAnimationTarget remoteAnimationTarget2 = this.enteringTarget;
        remoteAnimationTarget2.getClass();
        Rect rect2 = remoteAnimationTarget2.localBounds;
        int iWidth2 = rect2.width();
        int iHeight2 = rect2.height();
        animation2.initialize(iWidth2, iHeight2, iWidth2, iHeight2);
    }

    public CustomCrossActivityBackAnimation(Context context, BackAnimationBackground backAnimationBackground, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, Handler handler) {
        this(context, backAnimationBackground, rootTaskDisplayAreaOrganizer, new SurfaceControl.Transaction(), new CustomAnimationLoader(new TransitionAnimation(context, false, "CustomCrossActivityBackAnimation")), handler);
    }
}
