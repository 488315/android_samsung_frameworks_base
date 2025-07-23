package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.util.Preconditions;
import com.android.systemui.R;
import com.android.wm.shell.animation.FloatProperties;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_WIDTH$1;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_X$1;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.pip.PipAppOpsListener;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip2.animation.PipResizeAnimator;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipMotionHelper implements PipAppOpsListener.Callback, FloatingContentCoordinator.FloatingContent, PipTransitionState.PipTransitionStateChangedListener {
    public final Context mContext;
    public PhysicsAnimator.FlingConfig mFlingConfigX;
    public PhysicsAnimator.FlingConfig mFlingConfigY;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public AnonymousClass1 mMagnetizedPip;
    public final PhonePipMenuController mMenuController;
    public final PipBoundsState mPipBoundsState;
    public PipPerfHintController.PipHighPerfSession mPipHighPerfSession;
    public final PipPerfHintController mPipPerfHintController;
    public final PipScheduler mPipScheduler;
    public final PipTransitionState mPipTransitionState;
    public final PipUiEventLogger mPipUiEventLogger;
    public PipMenuView$$ExternalSyntheticLambda0 mPostPipTransitionCallback;
    public final PipSnapAlgorithm mSnapAlgorithm;
    public PhysicsAnimator.FlingConfig mStashConfigX;
    public PhysicsAnimator mTemporaryBoundsPhysicsAnimator;
    public final Rect mFloatingAllowedArea = new Rect();
    public final PhysicsAnimator.SpringConfig mSpringConfig = new PhysicsAnimator.SpringConfig(300.0f, 0.75f);
    public final PhysicsAnimator.SpringConfig mAnimateToDismissSpringConfig = new PhysicsAnimator.SpringConfig(1500.0f, 1.0f);
    public final PhysicsAnimator.SpringConfig mCatchUpSpringConfig = new PhysicsAnimator.SpringConfig(5000.0f, 1.0f);
    public final PhysicsAnimator.SpringConfig mConflictResolutionSpringConfig = new PhysicsAnimator.SpringConfig(200.0f, 1.0f);
    public boolean mSpringingToTouch = false;
    public boolean mDismissalPending = false;
    public boolean mWaitingForFlingTransition = false;
    public boolean mWaitingToPlayBoundsChangeTransition = false;
    public final PipMotionHelper$$ExternalSyntheticLambda0 mResizePipUpdateListener = new PhysicsAnimator.UpdateListener() { // from class: com.android.wm.shell.pip2.phone.PipMotionHelper$$ExternalSyntheticLambda0
        @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.UpdateListener
        public final void onAnimationUpdateForProperty(Object obj) {
            PipMotionHelper pipMotionHelper = PipMotionHelper.this;
            PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
            if (pipBoundsState.mMotionBoundsState.isInMotion()) {
                pipMotionHelper.mPipScheduler.scheduleUserResizePip(pipBoundsState.mMotionBoundsState.mBoundsInMotion, 0.0f);
            }
        }
    };

    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.wm.shell.pip2.phone.PipMotionHelper$$ExternalSyntheticLambda0] */
    public PipMotionHelper(Context context, PipBoundsState pipBoundsState, PhonePipMenuController phonePipMenuController, PipSnapAlgorithm pipSnapAlgorithm, FloatingContentCoordinator floatingContentCoordinator, PipScheduler pipScheduler, Optional<PipPerfHintController> optional, PipTransitionState pipTransitionState, PipUiEventLogger pipUiEventLogger) {
        this.mContext = context;
        this.mPipBoundsState = pipBoundsState;
        this.mPipScheduler = pipScheduler;
        this.mMenuController = phonePipMenuController;
        this.mSnapAlgorithm = pipSnapAlgorithm;
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        this.mPipPerfHintController = optional.orElse(null);
        this.mPipTransitionState = pipTransitionState;
        pipTransitionState.addPipTransitionStateChangedListener(this);
        this.mPipUiEventLogger = pipUiEventLogger;
    }

    public final void animateToOffset(int i, Rect rect) {
        if (i == 0) {
            return;
        }
        cancelPhysicsAnimation();
        Rect rect2 = new Rect(rect);
        rect2.offset(0, i);
        setAnimatingToBounds(rect2);
        Bundle bundle = new Bundle();
        bundle.putBoolean("animating_bounds_change", true);
        bundle.putInt("animating_bounds_change_duration", 300);
        this.mPipTransitionState.setState(4, bundle);
    }

    public final void cancelPhysicsAnimation() {
        this.mTemporaryBoundsPhysicsAnimator.cancel();
        this.mPipBoundsState.mMotionBoundsState.mAnimatingToBounds.setEmpty();
        this.mSpringingToTouch = false;
    }

    @Override // com.android.wm.shell.common.pip.PipAppOpsListener.Callback
    public final void dismissPip() {
        dismissPip(true);
    }

    @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
    public final Rect getAllowedFloatingBoundsRegion() {
        return this.mFloatingAllowedArea;
    }

    @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
    public final Rect getFloatingBoundsOnScreen() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        return !pipBoundsState.mMotionBoundsState.mAnimatingToBounds.isEmpty() ? pipBoundsState.mMotionBoundsState.mAnimatingToBounds : pipBoundsState.getBounds();
    }

    @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
    public final void moveToBounds(Rect rect) {
        if (!this.mTemporaryBoundsPhysicsAnimator.isRunning()) {
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            pipBoundsState.mMotionBoundsState.setBoundsInMotion(pipBoundsState.getBounds());
        }
        PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
        FloatProperties$Companion$RECT_X$1 floatProperties$Companion$RECT_X$1 = FloatProperties.RECT_X;
        float f = rect.left;
        PhysicsAnimator.SpringConfig springConfig = this.mConflictResolutionSpringConfig;
        physicsAnimator.spring(floatProperties$Companion$RECT_X$1, f, 0.0f, springConfig);
        physicsAnimator.spring(FloatProperties.RECT_Y, rect.top, 0.0f, springConfig);
        startBoundsAnimator(rect.left, rect.top, null);
    }

    public final void movetoTarget(float f, float f2, PipTouchHandler$$ExternalSyntheticLambda3 pipTouchHandler$$ExternalSyntheticLambda3, boolean z) {
        this.mSpringingToTouch = false;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (f == 0.0f) {
            f = pipBoundsState.mMotionBoundsState.mBoundsInMotion.centerX() < pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().centerX() ? -0.001f : 0.001f;
        }
        float f3 = f;
        PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
        FloatProperties$Companion$RECT_WIDTH$1 floatProperties$Companion$RECT_WIDTH$1 = FloatProperties.RECT_WIDTH;
        float width = pipBoundsState.getBounds().width();
        PhysicsAnimator.SpringConfig springConfig = this.mSpringConfig;
        physicsAnimator.spring(floatProperties$Companion$RECT_WIDTH$1, width, 0.0f, springConfig);
        physicsAnimator.spring(FloatProperties.RECT_HEIGHT, pipBoundsState.getBounds().height(), 0.0f, springConfig);
        physicsAnimator.flingThenSpring(FloatProperties.RECT_X, f3, z ? this.mStashConfigX : this.mFlingConfigX, this.mSpringConfig, true);
        physicsAnimator.flingThenSpring(FloatProperties.RECT_Y, f2, this.mFlingConfigY, this.mSpringConfig, false);
        PipDisplayLayoutState pipDisplayLayoutState = pipBoundsState.mPipDisplayLayoutState;
        Rect rect = pipDisplayLayoutState.getDisplayLayout().mStableInsets;
        float width2 = z ? (pipBoundsState.mStashOffset - pipBoundsState.getBounds().width()) + rect.left : pipBoundsState.mMovementBounds.left;
        float f4 = z ? (pipDisplayLayoutState.getDisplayBounds().right - pipBoundsState.mStashOffset) - rect.right : pipBoundsState.mMovementBounds.right;
        if (f3 >= 0.0f) {
            width2 = f4;
        }
        startBoundsAnimator(width2, PhysicsAnimator.estimateFlingEndValue(pipBoundsState.mMotionBoundsState.mBoundsInMotion.top, f2, this.mFlingConfigY), pipTouchHandler$$ExternalSyntheticLambda3);
    }

    @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
    public final void onPipTransitionStateChanged(int i, int i2, Bundle bundle) {
        PipScheduler pipScheduler = this.mPipScheduler;
        final PipTransitionState pipTransitionState = this.mPipTransitionState;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (i2 == 4) {
            this.mWaitingForFlingTransition = bundle.getBoolean("fling_bounds_change");
            boolean z = bundle.getBoolean("animating_bounds_change");
            this.mWaitingToPlayBoundsChangeTransition = z;
            if (this.mWaitingForFlingTransition || z) {
                Rect bounds = pipBoundsState.getBounds();
                PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState.mMotionBoundsState;
                if (!bounds.equals(motionBoundsState.mBoundsInMotion)) {
                    pipScheduler.scheduleAnimateResizePip(bundle.getInt("animating_bounds_change_duration", 0), this.mWaitingToPlayBoundsChangeTransition, motionBoundsState.mAnimatingToBounds);
                    return;
                }
                settlePipBoundsAfterPhysicsAnimation(false);
                PipPerfHintController.PipHighPerfSession pipHighPerfSession = this.mPipHighPerfSession;
                if (pipHighPerfSession != null) {
                    pipHighPerfSession.close();
                    this.mPipHighPerfSession = null;
                }
                pipTransitionState.getClass();
                pipTransitionState.mMainHandler.post(new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipTransitionState$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipTransitionState.this.setState(6, null);
                    }
                });
                return;
            }
            return;
        }
        if (i2 != 5) {
            if (i2 != 6) {
                if (i2 != 7) {
                    return;
                }
                cancelPhysicsAnimation();
                settlePipBoundsAfterPhysicsAnimation(false);
                return;
            }
            if (pipBoundsState.getBounds().right > pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().width() || pipBoundsState.getBounds().left < 0) {
                int i3 = pipBoundsState.getBounds().left;
                PipUiEventLogger pipUiEventLogger = this.mPipUiEventLogger;
                if (i3 < 0 && pipBoundsState.mStashedState != 1) {
                    pipBoundsState.setStashed(1, false);
                    pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_LEFT);
                } else if (pipBoundsState.getBounds().left >= 0 && pipBoundsState.mStashedState != 2) {
                    pipBoundsState.setStashed(2, false);
                    pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_RIGHT);
                }
                this.mMenuController.hideMenu();
                return;
            }
            return;
        }
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) bundle.getParcelable("pip_start_tx", SurfaceControl.Transaction.class);
        SurfaceControl.Transaction transaction2 = (SurfaceControl.Transaction) bundle.getParcelable("pip_finish_tx", SurfaceControl.Transaction.class);
        final Rect rect = (Rect) bundle.getParcelable("pip_dest_bounds", Rect.class);
        int i4 = bundle.getInt("animating_bounds_change_duration", 0);
        if (!this.mWaitingForFlingTransition) {
            if (this.mWaitingToPlayBoundsChangeTransition) {
                this.mWaitingToPlayBoundsChangeTransition = false;
                SurfaceControl surfaceControl = pipTransitionState.mPinnedTaskLeash;
                Preconditions.checkState(surfaceControl != null, "No leash cached by mPipTransitionState=" + pipTransitionState);
                PipResizeAnimator pipResizeAnimator = new PipResizeAnimator(this.mContext, surfaceControl, transaction, transaction2, rect, pipBoundsState.getBounds(), rect, i4, 0.0f);
                pipResizeAnimator.mAnimationEndCallback = new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipMotionHelper$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipMotionHelper pipMotionHelper = PipMotionHelper.this;
                        Rect rect2 = rect;
                        pipMotionHelper.mPipBoundsState.mMotionBoundsState.setBoundsInMotion(rect2);
                        pipMotionHelper.settlePipBoundsAfterPhysicsAnimation(false);
                        PipPerfHintController.PipHighPerfSession pipHighPerfSession2 = pipMotionHelper.mPipHighPerfSession;
                        if (pipHighPerfSession2 != null) {
                            pipHighPerfSession2.close();
                            pipMotionHelper.mPipHighPerfSession = null;
                        }
                        pipMotionHelper.mPipScheduler.scheduleFinishResizePip(rect2);
                    }
                };
                pipResizeAnimator.start();
                return;
            }
            return;
        }
        this.mWaitingForFlingTransition = false;
        SurfaceControl surfaceControl2 = pipTransitionState.mPinnedTaskLeash;
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius);
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius);
        transaction.merge(transaction2);
        transaction.setPosition(surfaceControl2, rect.left, rect.top).setCornerRadius(surfaceControl2, dimensionPixelSize).setShadowRadius(surfaceControl2, dimensionPixelSize2);
        transaction.apply();
        settlePipBoundsAfterPhysicsAnimation(false);
        PipPerfHintController.PipHighPerfSession pipHighPerfSession2 = this.mPipHighPerfSession;
        if (pipHighPerfSession2 != null) {
            pipHighPerfSession2.close();
            this.mPipHighPerfSession = null;
        }
        pipScheduler.scheduleFinishResizePip(rect);
    }

    public final void resizeAndAnimatePipUnchecked(Rect rect) {
        if (this.mPipBoundsState.mMotionBoundsState.isInMotion()) {
            return;
        }
        setAnimatingToBounds(rect);
        Bundle bundle = new Bundle();
        bundle.putBoolean("animating_bounds_change", true);
        bundle.putInt("animating_bounds_change_duration", IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
        this.mPipTransitionState.setState(4, bundle);
    }

    public final void setAnimatingToBounds(Rect rect) {
        this.mPipBoundsState.mMotionBoundsState.mAnimatingToBounds.set(rect);
        this.mFloatingContentCoordinator.onContentMoved(this);
    }

    public final void settlePipBoundsAfterPhysicsAnimation(boolean z) {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (!z && pipBoundsState.mMotionBoundsState.isInMotion()) {
            pipBoundsState.mMotionBoundsState.mBoundsInMotion.setEmpty();
        }
        pipBoundsState.mMotionBoundsState.mAnimatingToBounds.setEmpty();
        this.mSpringingToTouch = false;
        this.mDismissalPending = false;
    }

    public final void startBoundsAnimator(float f, float f2, PipTouchHandler$$ExternalSyntheticLambda3 pipTouchHandler$$ExternalSyntheticLambda3) {
        if (!this.mSpringingToTouch) {
            cancelPhysicsAnimation();
        }
        int i = (int) f;
        int i2 = (int) f2;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        setAnimatingToBounds(new Rect(i, i2, pipBoundsState.getBounds().width() + i, pipBoundsState.getBounds().height() + i2));
        if (!this.mTemporaryBoundsPhysicsAnimator.isRunning()) {
            PipPerfHintController pipPerfHintController = this.mPipPerfHintController;
            if (pipPerfHintController != null) {
                this.mPipHighPerfSession = pipPerfHintController.startSession(new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipMotionHelper$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PipMotionHelper.this.getClass();
                    }
                }, "startBoundsAnimator");
            }
            PipMotionHelper$$ExternalSyntheticLambda0 pipMotionHelper$$ExternalSyntheticLambda0 = this.mResizePipUpdateListener;
            if (pipTouchHandler$$ExternalSyntheticLambda3 != null) {
                PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
                physicsAnimator.updateListeners.add(pipMotionHelper$$ExternalSyntheticLambda0);
                physicsAnimator.withEndActions(new PipMotionHelper$$ExternalSyntheticLambda2(this, 0), pipTouchHandler$$ExternalSyntheticLambda3);
            } else {
                PhysicsAnimator physicsAnimator2 = this.mTemporaryBoundsPhysicsAnimator;
                physicsAnimator2.updateListeners.add(pipMotionHelper$$ExternalSyntheticLambda0);
                physicsAnimator2.withEndActions(new PipMotionHelper$$ExternalSyntheticLambda2(this, 0));
            }
        }
        this.mTemporaryBoundsPhysicsAnimator.start();
    }

    public final void dismissPip(final boolean z) {
        cancelPhysicsAnimation();
        this.mMenuController.hideMenu(2);
        final PipScheduler pipScheduler = this.mPipScheduler;
        pipScheduler.getClass();
        pipScheduler.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipScheduler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WindowContainerTransaction windowContainerTransaction;
                PipScheduler pipScheduler2 = PipScheduler.this;
                boolean z2 = z;
                if (pipScheduler2.mPipTransitionState.isInPip()) {
                    PipTransition pipTransition = pipScheduler2.mPipTransitionController;
                    PipTransitionState pipTransitionState = pipTransition.mPipTransitionState;
                    WindowContainerToken pipTaskToken = pipTransitionState.getPipTaskToken();
                    if (pipTaskToken == null) {
                        windowContainerTransaction = null;
                    } else {
                        windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.setBounds(pipTaskToken, (Rect) null);
                        windowContainerTransaction.setWindowingMode(pipTaskToken, 0);
                        windowContainerTransaction.reorder(pipTaskToken, false);
                    }
                    if (windowContainerTransaction == null) {
                        return;
                    }
                    pipTransitionState.setState(7, null);
                    pipTransition.mPendingRemoveWithFadeout = z2;
                    pipTransition.mTransitions.startTransition(1003, windowContainerTransaction, pipTransition);
                }
            }
        });
    }
}
