package com.android.systemui.animation;

import android.content.ComponentName;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.animation.PathInterpolator;
import android.window.WindowAnimationState;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ActivityTransitionAnimator$AnimationDelegate$startAnimation$controller$1 implements ActivityTransitionAnimator.Controller {
    public final /* synthetic */ ActivityTransitionAnimator.Controller $$delegate_0;
    public final /* synthetic */ ActivityTransitionAnimator.Controller $delegate;
    public final /* synthetic */ IRemoteAnimationFinishedCallback $iCallback;
    public final /* synthetic */ boolean $isExpandingFullyAbove;
    public final /* synthetic */ RemoteAnimationTarget $navigationBar;
    public final /* synthetic */ SurfaceControl.Transaction $startTransaction;
    public final /* synthetic */ boolean $useSpring;
    public final /* synthetic */ RemoteAnimationTarget $window;
    public final /* synthetic */ Rect $windowBounds;
    public final /* synthetic */ WindowAnimationState $windowState;
    public final /* synthetic */ ActivityTransitionAnimator.AnimationDelegate this$0;

    public ActivityTransitionAnimator$AnimationDelegate$startAnimation$controller$1(ActivityTransitionAnimator.Controller controller, ActivityTransitionAnimator.AnimationDelegate animationDelegate, boolean z, WindowAnimationState windowAnimationState, Rect rect, SurfaceControl.Transaction transaction, RemoteAnimationTarget remoteAnimationTarget, boolean z2, ViewRootImpl viewRootImpl, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, RemoteAnimationTarget remoteAnimationTarget2) {
        this.$delegate = controller;
        this.this$0 = animationDelegate;
        this.$isExpandingFullyAbove = z;
        this.$windowState = windowAnimationState;
        this.$windowBounds = rect;
        this.$startTransaction = transaction;
        this.$window = remoteAnimationTarget;
        this.$useSpring = z2;
        this.$iCallback = iRemoteAnimationFinishedCallback;
        this.$navigationBar = remoteAnimationTarget2;
        this.$$delegate_0 = controller;
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final TransitionAnimator.State createAnimatorState() {
        float f;
        if (this.$$delegate_0.isLaunching()) {
            return this.$delegate.createAnimatorState();
        }
        TransitionAnimator.Companion.getClass();
        WindowAnimationState windowAnimationState = this.$windowState;
        RectF rectF = windowAnimationState != null ? windowAnimationState.bounds : null;
        int i = rectF != null ? (int) rectF.left : this.$windowBounds.left;
        int i2 = rectF != null ? (int) rectF.top : this.$windowBounds.top;
        int i3 = rectF != null ? (int) rectF.right : this.$windowBounds.right;
        int i4 = rectF != null ? (int) rectF.bottom : this.$windowBounds.bottom;
        Rect rect = this.$windowBounds;
        float max = Math.max((i3 - i) / (rect.right - rect.left), (i4 - i2) / (rect.bottom - rect.top));
        WindowAnimationState windowAnimationState2 = this.$windowState;
        Float valueOf = windowAnimationState2 != null ? Float.valueOf(windowAnimationState2.topLeftRadius) : null;
        if (valueOf != null) {
            f = valueOf.floatValue() * max;
        } else {
            ActivityTransitionAnimator.AnimationDelegate animationDelegate = this.this$0;
            if (this.$isExpandingFullyAbove) {
                f = ScreenDecorationsUtils.getWindowCornerRadius(animationDelegate.context);
            } else {
                animationDelegate.getClass();
                f = 0.0f;
            }
        }
        float f2 = f;
        return new TransitionAnimator.State(i2, i4, i, i3, f2, f2);
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final ComponentName getComponent() {
        return this.$$delegate_0.getComponent();
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

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final WindowAnimationState getWindowAnimatorState() {
        return this.$$delegate_0.getWindowAnimatorState();
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
    public final void onDispose() {
        this.$$delegate_0.onDispose();
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onIntentStarted(boolean z) {
        this.$$delegate_0.onIntentStarted(z);
    }

    @Override // com.android.systemui.animation.ActivityTransitionAnimator.Controller
    public final void onTransitionAnimationCancelled() {
        this.$$delegate_0.onTransitionAnimationCancelled();
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
        float f3 = f2;
        this.this$0.applyStateToWindow(this.$window, state, f3, this.$useSpring, null);
        RemoteAnimationTarget remoteAnimationTarget = this.$navigationBar;
        ActivityTransitionAnimator.AnimationDelegate animationDelegate = this.this$0;
        if (remoteAnimationTarget != null && animationDelegate.transactionApplierView.getViewRootImpl() != null && remoteAnimationTarget.leash.isValid()) {
            TransitionAnimator.Companion companion = TransitionAnimator.Companion;
            TransitionAnimator.Timings timings = ActivityTransitionAnimator.TIMINGS;
            long j = ActivityTransitionAnimator.ANIMATION_DELAY_NAV_FADE_IN;
            companion.getClass();
            float progress = TransitionAnimator.Companion.getProgress(timings, f3, j, 133L);
            f3 = f3;
            SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash);
            if (progress > 0.0f) {
                animationDelegate.matrix.reset();
                animationDelegate.matrix.setTranslate(0.0f, state.top - remoteAnimationTarget.sourceContainerBounds.top);
                animationDelegate.windowCrop.set(state.left, 0, state.right, state.getHeight());
                builder.withAlpha(((PathInterpolator) ActivityTransitionAnimator.NAV_FADE_IN_INTERPOLATOR).getInterpolation(progress)).withMatrix(animationDelegate.matrix).withWindowCrop(animationDelegate.windowCrop).withVisibility(true);
            } else {
                builder.withAlpha(1.0f - ActivityTransitionAnimator.NAV_FADE_OUT_INTERPOLATOR.getInterpolation(TransitionAnimator.Companion.getProgressInternal(timings.totalDuration, f3, 0L, 133L)));
            }
            animationDelegate.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{builder.build()});
        }
        ActivityTransitionAnimator.Listener listener = animationDelegate.listener;
        if (listener != null) {
            listener.onTransitionAnimationProgress(f3);
        }
        this.$delegate.onTransitionAnimationProgress(state, f, f3);
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
        if (this.$startTransaction != null) {
            this.this$0.applyStateToWindow(this.$window, createAnimatorState(), 0.0f, this.$useSpring, this.$startTransaction);
        }
        controller.onTransitionAnimationStart(z);
    }

    @Override // com.android.systemui.animation.TransitionAnimator.Controller
    public final void setTransitionContainer(ViewGroup viewGroup) {
        this.$$delegate_0.setTransitionContainer(viewGroup);
    }
}
