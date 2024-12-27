package com.android.server.wm;

import android.R;
import android.os.HandlerExecutor;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.rune.CoreRune;

import java.util.function.Consumer;

public final class AsyncRotationController extends FadeAnimationController implements Consumer {
    public final boolean mHasScreenRotationAnimation;
    public boolean mHideImmediately;
    public boolean mIsStartTransactionCommitted;
    public boolean mIsStartTransactionPrepared;
    public boolean mIsSyncDrawRequested;
    public WindowToken mNavBarToken;
    public Runnable mOnShowRunnable;
    public int mOriginalRotation;
    public SeamlessRotator mRotator;
    public final WindowManagerService mService;
    public int mSyncId;
    public final ArrayMap mTargetWindowTokens;
    public AsyncRotationController$$ExternalSyntheticLambda1 mTimeoutRunnable;
    public final int mTransitionOp;

    public final class Operation {
        public final int mAction;
        public SurfaceControl.Transaction mDrawTransaction;
        public boolean mIsCompletionPending;
        public SurfaceControl mLeash;

        public Operation(int i) {
            this.mAction = i;
        }

        public final String toString() {
            return "Operation{a=" + this.mAction + " pending=" + this.mIsCompletionPending + '}';
        }
    }

    public AsyncRotationController(DisplayContent displayContent) {
        super(displayContent);
        this.mTargetWindowTokens = new ArrayMap();
        this.mService = displayContent.mWmService;
        this.mOriginalRotation = displayContent.getWindowConfiguration().getRotation();
        if (displayContent.mTransitionController.getCollectingTransitionType() == 6) {
            DisplayRotation displayRotation = displayContent.mDisplayRotation;
            WindowState windowState = displayContent.mDisplayPolicy.mTopFullscreenOpaqueWindowState;
            if (windowState != null
                    && windowState.mAttrs.rotationAnimation == 3
                    && windowState.getTask() != null) {
                int i = this.mOriginalRotation;
                int i2 = displayRotation.mRotation;
                if (displayRotation.mAllowSeamlessRotationDespiteNavBarMoving
                        || displayRotation.mDisplayPolicy.mNavigationBarCanMove
                        || (i != 2 && i2 != 2)) {
                    this.mTransitionOp = 3;
                }
            }
            this.mTransitionOp = 2;
        } else if (displayContent.mTransitionController.isShellTransitionsEnabled()) {
            this.mTransitionOp = 1;
        } else {
            this.mTransitionOp = 0;
        }
        boolean z = displayContent.mScreenRotationAnimation != null || this.mTransitionOp == 2;
        this.mHasScreenRotationAnimation = z;
        if (z) {
            this.mHideImmediately = true;
        }
        displayContent.forAllWindows((Consumer) this, true);
        if (this.mTransitionOp == 0) {
            this.mIsStartTransactionCommitted = true;
        } else if (displayContent.mTransitionController.isCollecting(displayContent)) {
            keepAppearanceInPreviousRotation();
        }
    }

    public static boolean canBeAsync(WindowToken windowToken) {
        DisplayContent displayContent;
        int i = windowToken.windowType;
        if ((CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && i == 2632)
                || windowToken.mIsPortraitWindowToken
                || i == 2415
                || i == 2009
                || i == 2411) {
            return false;
        }
        return ((CoreRune.FW_FLEXIBLE_DUAL_MODE
                                && i == 2037
                                && (displayContent = windowToken.mDisplayContent) != null
                                && displayContent.mDisplayId == 1
                                && (displayContent.mDisplayInfo.flags & 8192) != 0)
                        || i <= 99
                        || i == 2011
                        || i == 2013
                        || i == 2040)
                ? false
                : true;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SurfaceControl surfaceControl;
        ActivityRecord activityRecord;
        WindowState findMainWindow;
        WindowState windowState = (WindowState) obj;
        if ((windowState.mHasSurface && canBeAsync(windowState.mToken))
                || (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER
                        && windowState.mAttrs.type == 2604
                        && (surfaceControl = windowState.mToken.mSurfaceControl) != null
                        && surfaceControl.isValid())) {
            int i = this.mTransitionOp;
            if (i == 0 && windowState.mForceSeamlesslyRotate) {
                return;
            }
            if (windowState.mAttrs.type != 2019) {
                this.mTargetWindowTokens.put(
                        windowState.mToken,
                        new Operation((i == 3 || windowState.mForceSeamlesslyRotate) ? 1 : 2));
                return;
            }
            boolean z = this.mDisplayContent.mDisplayPolicy.mNavigationBarCanMove;
            int i2 = this.mTransitionOp;
            if (i2 == 0) {
                this.mNavBarToken = windowState.mToken;
                if (z) {
                    return;
                }
                RecentsAnimationController recentsAnimationController =
                        this.mService.mRecentsAnimationController;
                if (recentsAnimationController != null
                        && recentsAnimationController.mNavigationBarAttachedToApp) {
                    return;
                }
            } else if (i2 == 3
                    || this.mDisplayContent.mTransitionController.mNavigationBarAttachedToApp
                    || (z
                            && (!windowState.isVisible()
                                    || (activityRecord =
                                                    this.mDisplayContent.topRunningActivity(false))
                                            == null
                                    || (findMainWindow = activityRecord.findMainWindow(true))
                                            == null
                                    || (findMainWindow.mRequestedVisibleTypes
                                                    & WindowInsets.Type.navigationBars())
                                            != 0))) {
                r4 = 1;
            }
            this.mTargetWindowTokens.put(windowState.mToken, new Operation(r4));
        }
    }

    public final void finishOp(WindowToken windowToken) {
        SurfaceControl surfaceControl;
        Operation operation = (Operation) this.mTargetWindowTokens.remove(windowToken);
        if (operation == null) {
            return;
        }
        if (operation.mDrawTransaction != null) {
            windowToken.getSyncTransaction().merge(operation.mDrawTransaction);
            operation.mDrawTransaction = null;
            Slog.d(
                    "AsyncRotation_WindowManager",
                    "finishOp merge transaction " + windowToken.getTopChild());
        }
        int i = operation.mAction;
        if (i == 3) {
            Slog.d(
                    "AsyncRotation_WindowManager",
                    "finishOp fade-in IME " + windowToken.getTopChild());
            fadeWindowToken(
                    true,
                    windowToken,
                    new SurfaceAnimator
                            .OnAnimationFinishedCallback() { // from class:
                                                             // com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda0
                        @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                        public final void onAnimationFinished(
                                int i2, AnimationAdapter animationAdapter) {
                            ImeInsetsSourceProvider imeSourceProvider =
                                    AsyncRotationController.this.mDisplayContent
                                            .mInsetsStateController.getImeSourceProvider();
                            InsetsControlTarget insetsControlTarget =
                                    imeSourceProvider.mControlTarget;
                            if (insetsControlTarget != null) {
                                imeSourceProvider.reportImeDrawnForOrganizer(insetsControlTarget);
                            }
                        }
                    });
        } else if (i == 2) {
            Slog.d("AsyncRotation_WindowManager", "finishOp fade-in " + windowToken.getTopChild());
            fadeWindowToken(true, windowToken, null);
        } else if (i == 1
                && (surfaceControl = operation.mLeash) != null
                && surfaceControl.isValid()) {
            Slog.d(
                    "AsyncRotation_WindowManager",
                    "finishOp undo seamless " + windowToken.getTopChild());
            SurfaceControl.Transaction syncTransaction = windowToken.getSyncTransaction();
            SurfaceControl surfaceControl2 = operation.mLeash;
            syncTransaction.setMatrix(
                    surfaceControl2,
                    1.0f,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    1.0f);
            syncTransaction.setPosition(
                    surfaceControl2,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE);
        }
        int i2 = this.mTransitionOp;
        if (i2 == 1 || i2 == 3) {
            for (int childCount = windowToken.getChildCount() - 1; childCount >= 0; childCount--) {
                WindowState windowState = (WindowState) windowToken.getChildAt(childCount);
                InsetsSourceProvider controllableInsetProvider =
                        windowState.getControllableInsetProvider();
                if (controllableInsetProvider != null) {
                    controllableInsetProvider.updateInsetsControlPosition(windowState, false);
                }
            }
        }
    }

    @Override // com.android.server.wm.FadeAnimationController
    public final Animation getFadeInAnimation() {
        if (this.mHasScreenRotationAnimation) {
            return AnimationUtils.loadAnimation(this.mContext, R.anim.wallpaper_close_enter);
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.fade_in);
        loadAnimation.scaleCurrentDuration(
                this.mDisplayContent.mWmService.getWindowAnimationScaleLocked());
        return loadAnimation;
    }

    @Override // com.android.server.wm.FadeAnimationController
    public final Animation getFadeOutAnimation() {
        if (this.mHideImmediately) {
            float f =
                    this.mTransitionOp == 2
                            ? 1.0f
                            : FullScreenMagnificationGestureHandler.MAX_SCALE;
            return new AlphaAnimation(f, f);
        }
        if (CoreRune.FW_SHELL_TRANSITION) {
            return AnimationUtils.loadAnimation(
                    this.mContext, R.anim.ft_avd_toarrow_rectangle_5_animation);
        }
        Animation loadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.fade_out);
        loadAnimation.scaleCurrentDuration(
                this.mDisplayContent.mWmService.getWindowAnimationScaleLocked());
        return loadAnimation;
    }

    public final void hideImmediately(WindowToken windowToken, int i) {
        boolean z = this.mHideImmediately;
        this.mHideImmediately = true;
        Operation operation = new Operation(i);
        this.mTargetWindowTokens.put(windowToken, operation);
        fadeWindowToken(false, windowToken, null);
        operation.mLeash = windowToken.getAnimationLeash();
        this.mHideImmediately = z;
    }

    public final void keepAppearanceInPreviousRotation() {
        if (this.mIsSyncDrawRequested) {
            return;
        }
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            if (((Operation) this.mTargetWindowTokens.valueAt(size)).mAction == 1) {
                WindowToken windowToken = (WindowToken) this.mTargetWindowTokens.keyAt(size);
                for (int childCount = windowToken.getChildCount() - 1;
                        childCount >= 0;
                        childCount += -1) {
                    ((WindowState) windowToken.getChildAt(childCount))
                            .applyWithNextDraw(
                                    0, new AsyncRotationController$$ExternalSyntheticLambda3());
                    Slog.d(
                            "AsyncRotation_WindowManager",
                            "Sync draw for " + windowToken.getChildAt(childCount));
                }
            }
        }
        this.mIsSyncDrawRequested = true;
        Slog.d("AsyncRotation_WindowManager", "Requested to sync draw transaction");
    }

    public final void onAllCompleted() {
        Slog.d("AsyncRotation_WindowManager", "onAllCompleted");
        AsyncRotationController$$ExternalSyntheticLambda1
                asyncRotationController$$ExternalSyntheticLambda1 = this.mTimeoutRunnable;
        if (asyncRotationController$$ExternalSyntheticLambda1 != null) {
            this.mService.mH.removeCallbacks(asyncRotationController$$ExternalSyntheticLambda1);
        }
        Runnable runnable = this.mOnShowRunnable;
        if (runnable != null) {
            runnable.run();
            this.mOnShowRunnable = null;
        }
    }

    public final void onTransitionFinished() {
        int i;
        int i2 = this.mTransitionOp;
        DisplayContent displayContent = this.mDisplayContent;
        if (i2 == 2) {
            if (this.mTargetWindowTokens.isEmpty()) {
                displayContent.finishAsyncRotationIfPossible();
                return;
            }
            return;
        }
        Slog.d("AsyncRotation_WindowManager", "onTransitionFinished " + this.mTargetWindowTokens);
        for (int size = this.mTargetWindowTokens.size() + (-1); size >= 0; size--) {
            WindowToken windowToken = (WindowToken) this.mTargetWindowTokens.keyAt(size);
            if (windowToken.isVisible()) {
                for (int childCount = windowToken.getChildCount() - 1;
                        childCount >= 0;
                        childCount--) {
                    WindowState windowState = (WindowState) windowToken.getChildAt(childCount);
                    if (windowState.mHasSurface
                            && !windowState.mDestroying
                            && ((i = windowState.mWinAnimator.mDrawState) == 2
                                    || i == 3
                                    || i == 4)) {
                        displayContent.finishAsyncRotation(windowToken);
                        break;
                    }
                }
            } else {
                displayContent.finishAsyncRotation(windowToken);
            }
        }
        if (this.mTargetWindowTokens.isEmpty()) {
            return;
        }
        if (this.mTimeoutRunnable == null) {
            this.mTimeoutRunnable = new AsyncRotationController$$ExternalSyntheticLambda1(this);
        }
        this.mService.mH.postDelayed(this.mTimeoutRunnable, 2000L);
    }

    public final void setupStartTransaction(SurfaceControl.Transaction transaction) {
        if (this.mIsStartTransactionCommitted) {
            return;
        }
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            Operation operation = (Operation) this.mTargetWindowTokens.valueAt(size);
            SurfaceControl surfaceControl = operation.mLeash;
            if (surfaceControl != null && surfaceControl.isValid()) {
                if (this.mHasScreenRotationAnimation && operation.mAction == 2) {
                    transaction.setAlpha(
                            surfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE);
                    Slog.d(
                            "AsyncRotation_WindowManager",
                            "Setup alpha0 "
                                    + ((WindowToken) this.mTargetWindowTokens.keyAt(size))
                                            .getTopChild());
                } else {
                    if (this.mRotator == null) {
                        int i = this.mOriginalRotation;
                        DisplayContent displayContent = this.mDisplayContent;
                        this.mRotator =
                                new SeamlessRotator(
                                        i,
                                        displayContent.getWindowConfiguration().getRotation(),
                                        displayContent.mDisplayInfo,
                                        false);
                    }
                    SeamlessRotator seamlessRotator = this.mRotator;
                    transaction.setMatrix(
                            surfaceControl, seamlessRotator.mTransform, seamlessRotator.mFloat9);
                    Slog.d(
                            "AsyncRotation_WindowManager",
                            "Setup unrotate "
                                    + ((WindowToken) this.mTargetWindowTokens.keyAt(size))
                                            .getTopChild());
                }
            }
        }
        transaction.addTransactionCommittedListener(
                new HandlerExecutor(this.mService.mH),
                new SurfaceControl
                        .TransactionCommittedListener() { // from class:
                                                          // com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda4
                    @Override // android.view.SurfaceControl.TransactionCommittedListener
                    public final void onTransactionCommitted() {
                        AsyncRotationController asyncRotationController =
                                AsyncRotationController.this;
                        WindowManagerGlobalLock windowManagerGlobalLock =
                                asyncRotationController.mService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                Slog.d(
                                        "AsyncRotation_WindowManager",
                                        "Start transaction is committed");
                                asyncRotationController.mIsStartTransactionCommitted = true;
                                for (int size2 =
                                                asyncRotationController.mTargetWindowTokens.size()
                                                        - 1;
                                        size2 >= 0;
                                        size2--) {
                                    if (((AsyncRotationController.Operation)
                                                    asyncRotationController.mTargetWindowTokens
                                                            .valueAt(size2))
                                            .mIsCompletionPending) {
                                        Slog.d(
                                                "AsyncRotation_WindowManager",
                                                "Continue pending completion "
                                                        + ((WindowToken)
                                                                        asyncRotationController
                                                                                .mTargetWindowTokens
                                                                                .keyAt(size2))
                                                                .getTopChild());
                                        asyncRotationController.mDisplayContent.finishAsyncRotation(
                                                (WindowToken)
                                                        asyncRotationController.mTargetWindowTokens
                                                                .keyAt(size2));
                                    }
                                }
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                });
        this.mIsStartTransactionPrepared = true;
    }
}
