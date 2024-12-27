package com.android.systemui.animation;

import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActivityTransitionAnimator$AnimationDelegate$startAnimation$controller$1 implements ActivityTransitionAnimator.Controller {
    public final /* synthetic */ ActivityTransitionAnimator.Controller $$delegate_0;
    public final /* synthetic */ ActivityTransitionAnimator.Controller $delegate;
    public final /* synthetic */ IRemoteAnimationFinishedCallback $iCallback;
    public final /* synthetic */ boolean $isExpandingFullyAbove;
    public final /* synthetic */ RemoteAnimationTarget $navigationBar;
    public final /* synthetic */ RemoteAnimationTarget $window;
    public final /* synthetic */ Rect $windowBounds;
    public final /* synthetic */ ActivityTransitionAnimator.AnimationDelegate this$0;

    public ActivityTransitionAnimator$AnimationDelegate$startAnimation$controller$1(ActivityTransitionAnimator.Controller controller, ActivityTransitionAnimator.AnimationDelegate animationDelegate, boolean z, Rect rect, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, RemoteAnimationTarget remoteAnimationTarget, RemoteAnimationTarget remoteAnimationTarget2) {
        this.$delegate = controller;
        this.this$0 = animationDelegate;
        this.$isExpandingFullyAbove = z;
        this.$windowBounds = rect;
        this.$iCallback = iRemoteAnimationFinishedCallback;
        this.$window = remoteAnimationTarget;
        this.$navigationBar = remoteAnimationTarget2;
        this.$$delegate_0 = controller;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final TransitionAnimator.State createAnimatorState() {
        float f;
        if (this.$$delegate_0.isLaunching()) {
            return this.$delegate.createAnimatorState();
        }
        ActivityTransitionAnimator.AnimationDelegate animationDelegate = this.this$0;
        if (this.$isExpandingFullyAbove) {
            f = ScreenDecorationsUtils.getWindowCornerRadius(animationDelegate.context);
        } else {
            animationDelegate.getClass();
            f = 0.0f;
        }
        float f2 = f;
        Rect rect = this.$windowBounds;
        return new TransitionAnimator.State(rect.top, rect.bottom, rect.left, rect.right, f2, f2);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final View getOpeningWindowSyncView() {
        return this.$$delegate_0.getOpeningWindowSyncView();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final ViewGroup getTransitionContainer() {
        return this.$$delegate_0.getTransitionContainer();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final ActivityTransitionAnimator.TransitionCookie getTransitionCookie() {
        return this.$$delegate_0.getTransitionCookie();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final boolean isBelowAnimatingWindow() {
        return this.$$delegate_0.isBelowAnimatingWindow();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final boolean isDialogLaunch() {
        return this.$$delegate_0.isDialogLaunch();
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final boolean isLaunching() {
        return this.$$delegate_0.isLaunching();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onIntentStarted(boolean z) {
        this.$$delegate_0.onIntentStarted(z);
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onTransitionAnimationCancelled(Boolean bool) {
        this.$$delegate_0.onTransitionAnimationCancelled(bool);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationEnd(boolean z) {
        ActivityTransitionAnimator.Listener listener = this.this$0.listener;
        if (listener != null) {
            listener.onTransitionAnimationEnd();
        }
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.$iCallback;
        if (iRemoteAnimationFinishedCallback != null) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        boolean z2 = ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION;
        ActivityTransitionAnimator.Controller controller = this.$delegate;
        if (z2) {
            Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationEnd(isExpandingFullyAbove=" + z + ") [controller=" + controller + "]");
        }
        controller.onTransitionAnimationEnd(z);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationProgress(TransitionAnimator.State state, float f, float f2) {
        float f3;
        RemoteAnimationTarget remoteAnimationTarget = this.$window;
        ActivityTransitionAnimator.AnimationDelegate animationDelegate = this.this$0;
        if (animationDelegate.transactionApplierView.getViewRootImpl() != null && remoteAnimationTarget.leash.isValid()) {
            Rect rect = remoteAnimationTarget.screenSpaceBounds;
            int i = rect.left;
            int i2 = rect.right;
            float f4 = (i + i2) / 2.0f;
            int i3 = rect.top;
            float f5 = (i3 + r14) / 2.0f;
            float f6 = rect.bottom - i3;
            float max = Math.max(state.getWidth() / (i2 - i), state.getHeight() / f6);
            animationDelegate.matrix.reset();
            animationDelegate.matrix.setScale(max, max, f4, f5);
            animationDelegate.matrix.postTranslate(((state.getWidth() / 2.0f) + state.left) - f4, (((f6 * max) - f6) / 2.0f) + (state.top - rect.top));
            float f7 = state.left - rect.left;
            float f8 = state.top - rect.top;
            animationDelegate.windowCropF.set(f7, f8, state.getWidth() + f7, state.getHeight() + f8);
            animationDelegate.matrix.invert(animationDelegate.invertMatrix);
            animationDelegate.invertMatrix.mapRect(animationDelegate.windowCropF);
            animationDelegate.windowCrop.set(MathKt__MathJVMKt.roundToInt(animationDelegate.windowCropF.left), MathKt__MathJVMKt.roundToInt(animationDelegate.windowCropF.top), MathKt__MathJVMKt.roundToInt(animationDelegate.windowCropF.right), MathKt__MathJVMKt.roundToInt(animationDelegate.windowCropF.bottom));
            ActivityTransitionAnimator.Controller controller = animationDelegate.controller;
            long j = controller.isLaunching() ? ActivityTransitionAnimator.TIMINGS.contentAfterFadeInDelay : ActivityTransitionAnimator.TIMINGS.contentBeforeFadeOutDelay;
            long j2 = controller.isLaunching() ? ActivityTransitionAnimator.TIMINGS.contentAfterFadeInDuration : ActivityTransitionAnimator.TIMINGS.contentBeforeFadeOutDuration;
            TransitionAnimator.Companion companion = TransitionAnimator.Companion;
            TransitionAnimator.Timings timings = ActivityTransitionAnimator.TIMINGS;
            companion.getClass();
            float progress = TransitionAnimator.Companion.getProgress(timings, f2, j, j2);
            if (!controller.isBelowAnimatingWindow()) {
                f3 = 1.0f;
            } else if (controller.isLaunching()) {
                ActivityTransitionAnimator.Companion.getClass();
                f3 = ActivityTransitionAnimator.INTERPOLATORS.contentAfterFadeInInterpolator.getInterpolation(progress);
            } else {
                ActivityTransitionAnimator.Companion.getClass();
                f3 = 1 - ActivityTransitionAnimator.INTERPOLATORS.contentBeforeFadeOutInterpolator.getInterpolation(progress);
            }
            animationDelegate.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(f3).withMatrix(animationDelegate.matrix).withWindowCrop(animationDelegate.windowCrop).withCornerRadius(Math.max(state.topCornerRadius, state.bottomCornerRadius) / max).withVisibility(true).build()});
        }
        RemoteAnimationTarget remoteAnimationTarget2 = this.$navigationBar;
        if (remoteAnimationTarget2 != null && animationDelegate.transactionApplierView.getViewRootImpl() != null && remoteAnimationTarget2.leash.isValid()) {
            TransitionAnimator.Companion companion2 = TransitionAnimator.Companion;
            TransitionAnimator.Timings timings2 = ActivityTransitionAnimator.TIMINGS;
            long j3 = ActivityTransitionAnimator.ANIMATION_DELAY_NAV_FADE_IN;
            companion2.getClass();
            float progress2 = TransitionAnimator.Companion.getProgress(timings2, f2, j3, 133L);
            SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget2.leash);
            if (progress2 > 0.0f) {
                animationDelegate.matrix.reset();
                animationDelegate.matrix.setTranslate(0.0f, state.top - remoteAnimationTarget2.sourceContainerBounds.top);
                animationDelegate.windowCrop.set(state.left, 0, state.right, state.getHeight());
                builder.withAlpha(((PathInterpolator) ActivityTransitionAnimator.NAV_FADE_IN_INTERPOLATOR).getInterpolation(progress2)).withMatrix(animationDelegate.matrix).withWindowCrop(animationDelegate.windowCrop).withVisibility(true);
            } else {
                builder.withAlpha(1.0f - ActivityTransitionAnimator.NAV_FADE_OUT_INTERPOLATOR.getInterpolation(TransitionAnimator.Companion.getProgress(timings2, f2, 0L, 133L)));
            }
            animationDelegate.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{builder.build()});
        }
        ActivityTransitionAnimator.Listener listener = animationDelegate.listener;
        if (listener != null) {
            listener.onTransitionAnimationProgress(f2);
        }
        this.$delegate.onTransitionAnimationProgress(state, f, f2);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void onTransitionAnimationStart(boolean z) {
        ActivityTransitionAnimator.Listener listener = this.this$0.listener;
        if (listener != null) {
            listener.onTransitionAnimationStart();
        }
        boolean z2 = ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION;
        ActivityTransitionAnimator.Controller controller = this.$delegate;
        if (z2) {
            Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationStart(isExpandingFullyAbove=" + z + ") [controller=" + controller + "]");
        }
        controller.onTransitionAnimationStart(z);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void setTransitionContainer(ViewGroup viewGroup) {
        this.$$delegate_0.setTransitionContainer(viewGroup);
    }
}
