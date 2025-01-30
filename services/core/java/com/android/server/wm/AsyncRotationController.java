package com.android.server.wm;

import android.R;
import android.os.HandlerExecutor;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class AsyncRotationController extends FadeAnimationController implements Consumer {
  public boolean mAlwaysWaitForStartTransaction;
  public Transition mFoldingTransition;
  public final ArrayList mForceTargetWindowTokens;
  public final boolean mHasScreenRotationAnimation;
  public boolean mHasUnlockTransitionAnimation;
  public boolean mHideImmediately;
  public boolean mIsStartTransactionCommitted;
  public boolean mIsSyncDrawRequested;
  public WindowToken mNavBarToken;
  public Runnable mOnShowRunnable;
  public final int mOriginalRotation;
  public SeamlessRotator mRotator;
  public final WindowManagerService mService;
  public int mSyncId;
  public final ArrayMap mTargetWindowTokens;
  public Runnable mTimeoutRunnable;
  public final int mTransitionOp;

  public static /* synthetic */ void lambda$keepAppearanceInPreviousRotation$0(
      SurfaceControl.Transaction transaction) {}

  public AsyncRotationController(DisplayContent displayContent) {
    super(displayContent);
    BLASTSyncEngine.SyncGroup syncSet;
    this.mTargetWindowTokens = new ArrayMap();
    this.mForceTargetWindowTokens = new ArrayList();
    this.mService = displayContent.mWmService;
    int rotation = displayContent.getWindowConfiguration().getRotation();
    this.mOriginalRotation = rotation;
    if (displayContent.mTransitionController.getCollectingTransitionType() == 6) {
      DisplayRotation displayRotation = displayContent.getDisplayRotation();
      WindowState topFullscreenOpaqueWindow =
          displayContent.getDisplayPolicy().getTopFullscreenOpaqueWindow();
      if (topFullscreenOpaqueWindow != null
          && topFullscreenOpaqueWindow.mAttrs.rotationAnimation == 3
          && topFullscreenOpaqueWindow.getTask() != null
          && displayRotation.canRotateSeamlessly(rotation, displayRotation.getRotation())) {
        this.mTransitionOp = 3;
      } else {
        this.mTransitionOp = 2;
      }
    } else if (displayContent.mTransitionController.isShellTransitionsEnabled()) {
      this.mTransitionOp = 1;
    } else {
      this.mTransitionOp = 0;
    }
    boolean z = displayContent.getRotationAnimation() != null || this.mTransitionOp == 2;
    this.mHasScreenRotationAnimation = z;
    if (displayContent.isKeyguardGoingAway()) {
      this.mHasUnlockTransitionAnimation = true;
    }
    if (z) {
      this.mHideImmediately = true;
    }
    displayContent.forAllWindows((Consumer) this, true);
    if (this.mTransitionOp == 0) {
      this.mIsStartTransactionCommitted = true;
      return;
    }
    if (displayContent.mTransitionController.isCollecting(displayContent)) {
      Transition collectingTransition =
          this.mDisplayContent.mTransitionController.getCollectingTransition();
      if (collectingTransition != null
          && (syncSet =
                  this.mDisplayContent.mWmService.mSyncEngine.getSyncSet(
                      collectingTransition.getSyncId()))
              != null
          && syncSet.mSyncMethod == 1) {
        this.mAlwaysWaitForStartTransaction = true;
      }
      keepAppearanceInPreviousRotation();
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:29:0x0068, code lost:

     if (r6.mDisplayContent.mTransitionController.mNavigationBarAttachedToApp == false) goto L36;
  */
  @Override // java.util.function.Consumer
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void accept(WindowState windowState) {
    SurfaceControl surfaceControl;
    if ((windowState.mHasSurface && canBeAsync(windowState.mToken))
        || (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER
            && windowState.getAttrs().type == 2604
            && (surfaceControl = windowState.mToken.mSurfaceControl) != null
            && surfaceControl.isValid())) {
      int i = this.mTransitionOp;
      if (i == 0 && windowState.mForceSeamlesslyRotate) {
        return;
      }
      int i2 = 1;
      if (windowState.mAttrs.type == 2019) {
        boolean navigationBarCanMove =
            this.mDisplayContent.getDisplayPolicy().navigationBarCanMove();
        int i3 = this.mTransitionOp;
        if (i3 == 0) {
          this.mNavBarToken = windowState.mToken;
          if (navigationBarCanMove) {
            return;
          }
          RecentsAnimationController recentsAnimationController =
              this.mService.getRecentsAnimationController();
          if (recentsAnimationController != null
              && recentsAnimationController.isNavigationBarAttachedToApp()) {
            return;
          }
        } else {
          if (!navigationBarCanMove) {
            if (i3 != 3) {}
          }
          this.mTargetWindowTokens.put(windowState.mToken, new Operation(i2));
          return;
        }
        i2 = 2;
        this.mTargetWindowTokens.put(windowState.mToken, new Operation(i2));
        return;
      }
      WindowToken windowToken = windowState.mToken;
      if (windowToken.mIsPortraitWindowToken) {
        this.mTargetWindowTokens.put(windowToken, new Operation(2));
        return;
      }
      if (i != 3
          && (!windowState.mForceSeamlesslyRotate
              || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && !windowState.isOnScreen()))) {
        i2 = 2;
      }
      this.mTargetWindowTokens.put(windowState.mToken, new Operation(i2));
    }
  }

  public static boolean canBeAsync(WindowToken windowToken) {
    int i = windowToken.windowType;
    return ((CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && i == 2632)
            || i == 2415
            || i == 2009
            || i == 2411
            || i <= 99
            || i == 2011
            || i == 2013
            || i == 2040)
        ? false
        : true;
  }

  public void keepAppearanceInPreviousRotation() {
    if (this.mIsSyncDrawRequested) {
      return;
    }
    for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
      if (!canDrawBeforeStartTransaction((Operation) this.mTargetWindowTokens.valueAt(size))) {
        WindowToken windowToken = (WindowToken) this.mTargetWindowTokens.keyAt(size);
        for (int childCount = windowToken.getChildCount() - 1; childCount >= 0; childCount += -1) {
          if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
              && ((Operation) this.mTargetWindowTokens.valueAt(size)).mAction == 1
              && ((WindowState) windowToken.getChildAt(childCount)).isVisible()) {
            ((WindowState) windowToken.getChildAt(childCount)).mHasSeamlessOperation = true;
          }
          ((WindowState) windowToken.getChildAt(childCount))
              .applyWithNextDraw(
                  new Consumer() { // from class:
                                   // com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                      AsyncRotationController.lambda$keepAppearanceInPreviousRotation$0(
                          (SurfaceControl.Transaction) obj);
                    }
                  });
          Slog.d(
              "AsyncRotation_WindowManager", "Sync draw for " + windowToken.getChildAt(childCount));
        }
      }
    }
    this.mIsSyncDrawRequested = true;
    Slog.d("AsyncRotation_WindowManager", "Requested to sync draw transaction");
  }

  public void updateTargetWindows() {
    if (this.mTransitionOp == 0 || !this.mIsStartTransactionCommitted) {
      return;
    }
    for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
      Operation operation = (Operation) this.mTargetWindowTokens.valueAt(size);
      if (!operation.mIsCompletionPending && operation.mAction != 1) {
        WindowToken windowToken = (WindowToken) this.mTargetWindowTokens.keyAt(size);
        int childCount = windowToken.getChildCount();
        int i = 0;
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
          WindowState windowState = (WindowState) windowToken.getChildAt(i2);
          if (windowState.isDrawn() || !windowState.mWinAnimator.getShown()) {
            i++;
          }
        }
        if (i == childCount) {
          this.mDisplayContent.finishAsyncRotation(windowToken);
        }
      }
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
          "AsyncRotation_WindowManager", "finishOp merge transaction " + windowToken.getTopChild());
    }
    int i = operation.mAction;
    if (i == 3) {
      Slog.d("AsyncRotation_WindowManager", "finishOp fade-in IME " + windowToken.getTopChild());
      fadeWindowToken(
          true,
          windowToken,
          64,
          new SurfaceAnimator
              .OnAnimationFinishedCallback() { // from class:
                                               // com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda1
            @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
            public final void onAnimationFinished(int i2, AnimationAdapter animationAdapter) {
              AsyncRotationController.this.lambda$finishOp$1(i2, animationAdapter);
            }
          });
    } else if (i == 2) {
      Slog.d("AsyncRotation_WindowManager", "finishOp fade-in " + windowToken.getTopChild());
      fadeWindowToken(true, windowToken, 64);
    } else if (i == 1
        && this.mRotator != null
        && (surfaceControl = operation.mLeash) != null
        && surfaceControl.isValid()) {
      Slog.d("AsyncRotation_WindowManager", "finishOp undo seamless " + windowToken.getTopChild());
      this.mRotator.setIdentityMatrix(windowToken.getSyncTransaction(), operation.mLeash);
    }
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && operation.mAction == 1) {
      for (int childCount = windowToken.getChildCount() - 1; childCount >= 0; childCount--) {
        if (((WindowState) windowToken.getChildAt(childCount)).mHasSeamlessOperation) {
          ((WindowState) windowToken.getChildAt(childCount)).mHasSeamlessOperation = false;
        }
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$finishOp$1(int i, AnimationAdapter animationAdapter) {
    this.mDisplayContent
        .getInsetsStateController()
        .getImeSourceProvider()
        .reportImeDrawnForOrganizer();
  }

  public void completeAll() {
    for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
      finishOp((WindowToken) this.mTargetWindowTokens.keyAt(size));
    }
    this.mTargetWindowTokens.clear();
    onAllCompleted();
  }

  public final void onAllCompleted() {
    Slog.d("AsyncRotation_WindowManager", "onAllCompleted");
    Runnable runnable = this.mTimeoutRunnable;
    if (runnable != null) {
      this.mService.f1749mH.removeCallbacks(runnable);
    }
    Runnable runnable2 = this.mOnShowRunnable;
    if (runnable2 != null) {
      runnable2.run();
      this.mOnShowRunnable = null;
    }
  }

  public boolean completeRotation(WindowToken windowToken) {
    Operation operation;
    if (!this.mIsStartTransactionCommitted) {
      Operation operation2 = (Operation) this.mTargetWindowTokens.get(windowToken);
      if (operation2 != null) {
        Slog.d("AsyncRotation_WindowManager", "Complete set pending " + windowToken.getTopChild());
        operation2.mIsCompletionPending = true;
      }
      return false;
    }
    if (this.mTransitionOp == 1
        && windowToken.mTransitionController.inTransition(this.mSyncId)
        && (operation = (Operation) this.mTargetWindowTokens.get(windowToken)) != null
        && operation.mAction == 2) {
      Slog.d("AsyncRotation_WindowManager", "Defer completion " + windowToken.getTopChild());
      return false;
    }
    if (!isTargetToken(windowToken)) {
      return false;
    }
    if (this.mHasScreenRotationAnimation || this.mTransitionOp != 0) {
      Slog.d("AsyncRotation_WindowManager", "Complete directly " + windowToken.getTopChild());
      finishOp(windowToken);
      if (this.mTargetWindowTokens.isEmpty()) {
        onAllCompleted();
        return true;
      }
    }
    return false;
  }

  public void start() {
    for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
      WindowToken windowToken = (WindowToken) this.mTargetWindowTokens.keyAt(size);
      Operation operation = (Operation) this.mTargetWindowTokens.valueAt(size);
      int i = operation.mAction;
      if (i == 2 || i == 3) {
        fadeWindowToken(false, windowToken, 64);
        operation.mLeash = windowToken.getAnimationLeash();
        Slog.d("AsyncRotation_WindowManager", "Start fade-out " + windowToken.getTopChild());
      } else if (i == 1) {
        operation.mLeash = windowToken.mSurfaceControl;
        Slog.d("AsyncRotation_WindowManager", "Start seamless " + windowToken.getTopChild());
      }
    }
    if (this.mHasScreenRotationAnimation) {
      scheduleTimeout();
    }
  }

  public final void scheduleTimeout() {
    if (this.mTimeoutRunnable == null) {
      this.mTimeoutRunnable =
          new Runnable() { // from class:
                           // com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              AsyncRotationController.this.lambda$scheduleTimeout$2();
            }
          };
    }
    this.mService.f1749mH.postDelayed(this.mTimeoutRunnable, 2000L);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$scheduleTimeout$2() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        StringBuilder sb = new StringBuilder();
        sb.append("Async rotation timeout: ");
        sb.append(
            !this.mIsStartTransactionCommitted
                ? " start transaction is not committed"
                : this.mTargetWindowTokens);
        Slog.i("AsyncRotation_WindowManager", sb.toString());
        this.mIsStartTransactionCommitted = true;
        this.mDisplayContent.finishAsyncRotationIfPossible();
        this.mService.mWindowPlacerLocked.performSurfacePlacement();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void hideImeImmediately() {
    WindowState windowState = this.mDisplayContent.mInputMethodWindow;
    if (windowState == null) {
      return;
    }
    WindowToken windowToken = windowState.mToken;
    if (isTargetToken(windowToken)) {
      return;
    }
    hideImmediately(windowToken, 3);
    Slog.d("AsyncRotation_WindowManager", "hideImeImmediately " + windowToken.getTopChild());
  }

  public final void hideImmediately(WindowToken windowToken, int i) {
    boolean z = this.mHideImmediately;
    this.mHideImmediately = true;
    Operation operation = new Operation(i);
    this.mTargetWindowTokens.put(windowToken, operation);
    fadeWindowToken(false, windowToken, 64);
    operation.mLeash = windowToken.getAnimationLeash();
    this.mHideImmediately = z;
  }

  public boolean isAsync(WindowState windowState) {
    WindowToken windowToken = windowState.mToken;
    return windowToken == this.mNavBarToken
        || (windowState.mForceSeamlesslyRotate && this.mTransitionOp == 0)
        || isTargetToken(windowToken);
  }

  public boolean isTargetToken(WindowToken windowToken) {
    return this.mTargetWindowTokens.containsKey(windowToken);
  }

  public boolean hasFadeOperation(WindowToken windowToken) {
    Operation operation = (Operation) this.mTargetWindowTokens.get(windowToken);
    return operation != null && operation.mAction == 2;
  }

  public boolean shouldFreezeInsetsPosition(WindowState windowState) {
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && windowState.mHasSeamlessOperation) {
      return true;
    }
    if (TransitionController.SYNC_METHOD != 1) {
      return false;
    }
    return (this.mTransitionOp == 0
            || this.mIsStartTransactionCommitted
            || !isTargetToken(windowState.mToken))
        ? false
        : true;
  }

  public SurfaceControl.Transaction getDrawTransaction(WindowToken windowToken) {
    Operation operation;
    if (this.mTransitionOp == 0
        || (operation = (Operation) this.mTargetWindowTokens.get(windowToken)) == null) {
      return null;
    }
    if (operation.mDrawTransaction == null) {
      operation.mDrawTransaction = new SurfaceControl.Transaction();
    }
    return operation.mDrawTransaction;
  }

  public void setOnShowRunnable(Runnable runnable) {
    this.mOnShowRunnable = runnable;
  }

  public void setupStartTransaction(SurfaceControl.Transaction transaction) {
    if (this.mIsStartTransactionCommitted) {
      return;
    }
    for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
      Operation operation = (Operation) this.mTargetWindowTokens.valueAt(size);
      SurfaceControl surfaceControl = operation.mLeash;
      if (surfaceControl != null && surfaceControl.isValid()) {
        if (this.mHasScreenRotationAnimation && operation.mAction == 2) {
          if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
              && this.mAlwaysWaitForStartTransaction
              && this.mHideImmediately
              && this.mTransitionOp == 2) {
            Slog.d(
                "AsyncRotation_WindowManager",
                "Setup to keep leash alpha"
                    + ((WindowToken) this.mTargetWindowTokens.keyAt(size)).getTopChild());
          } else {
            transaction.setAlpha(surfaceControl, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            Slog.d(
                "AsyncRotation_WindowManager",
                "Setup alpha0 "
                    + ((WindowToken) this.mTargetWindowTokens.keyAt(size)).getTopChild());
          }
        } else {
          if (this.mRotator == null) {
            this.mRotator =
                new SeamlessRotator(
                    this.mOriginalRotation,
                    this.mDisplayContent.getWindowConfiguration().getRotation(),
                    this.mDisplayContent.getDisplayInfo(),
                    false);
          }
          this.mRotator.applyTransform(transaction, surfaceControl);
          Slog.d(
              "AsyncRotation_WindowManager",
              "Setup unrotate "
                  + ((WindowToken) this.mTargetWindowTokens.keyAt(size)).getTopChild());
        }
      }
    }
    transaction.addTransactionCommittedListener(
        new HandlerExecutor(this.mService.f1749mH),
        new SurfaceControl
            .TransactionCommittedListener() { // from class:
                                              // com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda3
          @Override // android.view.SurfaceControl.TransactionCommittedListener
          public final void onTransactionCommitted() {
            AsyncRotationController.this.lambda$setupStartTransaction$3();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setupStartTransaction$3() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        Slog.d("AsyncRotation_WindowManager", "Start transaction is committed");
        this.mIsStartTransactionCommitted = true;
        boolean z = false;
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
          if (((Operation) this.mTargetWindowTokens.valueAt(size)).mIsCompletionPending) {
            Slog.d(
                "AsyncRotation_WindowManager",
                "Continue pending completion "
                    + ((WindowToken) this.mTargetWindowTokens.keyAt(size)).getTopChild());
            this.mDisplayContent.finishAsyncRotation(
                (WindowToken) this.mTargetWindowTokens.keyAt(size));
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
              z = true;
            }
          }
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && z) {
          this.mService.mWindowPlacerLocked.performSurfacePlacement();
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void onTransitionFinished() {
    if (this.mTransitionOp == 2) {
      return;
    }
    Slog.d("AsyncRotation_WindowManager", "onTransitionFinished " + this.mTargetWindowTokens);
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && this.mTargetWindowTokens.isEmpty()) {
      scheduleTimeout();
      return;
    }
    for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
      WindowToken windowToken = (WindowToken) this.mTargetWindowTokens.keyAt(size);
      if (windowToken.isVisible()) {
        int childCount = windowToken.getChildCount() - 1;
        while (true) {
          if (childCount < 0) {
            break;
          }
          if (((WindowState) windowToken.getChildAt(childCount)).isDrawFinishedLw()) {
            this.mDisplayContent.finishAsyncRotation(windowToken);
            break;
          }
          childCount--;
        }
      } else {
        this.mDisplayContent.finishAsyncRotation(windowToken);
      }
    }
    if (this.mTargetWindowTokens.isEmpty()) {
      return;
    }
    scheduleTimeout();
  }

  public boolean handleFinishDrawing(
      WindowState windowState, SurfaceControl.Transaction transaction) {
    if (this.mTransitionOp == 0) {
      return false;
    }
    Operation operation = (Operation) this.mTargetWindowTokens.get(windowState.mToken);
    if (operation == null) {
      if (this.mTransitionOp == 1
          && !this.mIsStartTransactionCommitted
          && canBeAsync(windowState.mToken)) {
        hideImmediately(windowState.mToken, 2);
        Slog.d(
            "AsyncRotation_WindowManager",
            "Hide on finishDrawing " + windowState.mToken.getTopChild());
      }
      return false;
    }
    Slog.d("AsyncRotation_WindowManager", "handleFinishDrawing " + windowState);
    if (transaction == null
        || !this.mIsSyncDrawRequested
        || canDrawBeforeStartTransaction(operation)) {
      this.mDisplayContent.finishAsyncRotation(windowState.mToken);
      return false;
    }
    SurfaceControl.Transaction transaction2 = operation.mDrawTransaction;
    if (transaction2 == null) {
      if (windowState.isClientLocal()) {
        SurfaceControl.Transaction transaction3 =
            (SurfaceControl.Transaction) this.mService.mTransactionFactory.get();
        operation.mDrawTransaction = transaction3;
        transaction3.merge(transaction);
      } else {
        operation.mDrawTransaction = transaction;
      }
    } else {
      transaction2.merge(transaction);
    }
    Slog.d(
        "AsyncRotation_WindowManager",
        "handleFinishDrawing, merged drawTransaction="
            + operation.mDrawTransaction.mDebugName
            + ", w="
            + windowState);
    this.mDisplayContent.finishAsyncRotation(windowState.mToken);
    return true;
  }

  public boolean shouldFinishAsyncRotationForSameChanges(Transition transition) {
    return (this.mIsStartTransactionCommitted
            || this.mOriginalRotation != this.mDisplayContent.getRotation()
            || this.mFoldingTransition == transition)
        ? false
        : true;
  }

  @Override // com.android.server.wm.FadeAnimationController
  public Animation getFadeInAnimation() {
    if (this.mHasScreenRotationAnimation) {
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
          && this.mAlwaysWaitForStartTransaction
          && this.mHideImmediately
          && this.mTransitionOp == 2) {
        return AnimationUtils.loadAnimation(
            this.mContext, R.anim.task_fragment_clear_top_close_enter);
      }
      return AnimationUtils.loadAnimation(
          this.mContext, R.anim.ft_avd_tooverflow_rectangle_2_animation);
    }
    return super.getFadeInAnimation();
  }

  @Override // com.android.server.wm.FadeAnimationController
  public Animation getFadeOutAnimation() {
    if (this.mHideImmediately) {
      float f = this.mTransitionOp == 2 ? 1.0f : DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      return new AlphaAnimation(f, f);
    }
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION) {
      return AnimationUtils.loadAnimation(
          this.mContext, R.anim.ft_avd_tooverflow_rectangle_1_pivot_animation);
    }
    return super.getFadeOutAnimation();
  }

  public boolean shouldHideImmediately(WindowToken windowToken) {
    int i;
    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
        && (((i = windowToken.windowType) == 2019
                || (i == 2024
                    && this.mDisplayContent
                        .getDisplayPolicy()
                        .mExt
                        .getTaskbarController()
                        .isTaskbarToken(windowToken)))
            && !this.mDisplayContent.getDisplayPolicy().navigationBarCanMove())) {
      return true;
    }
    if (this.mHasUnlockTransitionAnimation) {
      return windowToken != null
          && windowToken.getWindowLayerFromType()
              < this.mService.mPolicy.getWindowLayerFromTypeLw(2040);
    }
    return false;
  }

  @Override // com.android.server.wm.FadeAnimationController
  public void fadeWindowToken(boolean z, WindowToken windowToken, int i) {
    boolean z2 = this.mHideImmediately;
    try {
      this.mHideImmediately = shouldHideImmediately(windowToken) | z2;
      super.fadeWindowToken(z, windowToken, i);
    } finally {
      this.mHideImmediately = z2;
    }
  }

  public void hideImmediatelyIfNeeded(WindowState windowState, String str) {
    if (!windowState.mHasSurface
        || !canBeAsync(windowState.mToken)
        || isTargetToken(windowState.mToken)
        || this.mIsStartTransactionCommitted) {
      return;
    }
    Slog.d(
        "AsyncRotation_WindowManager",
        "hideImmediatelyIfNeeded, w=" + windowState + ", reason=" + str);
    boolean z = this.mHideImmediately;
    this.mHideImmediately = true;
    Operation operation = new Operation(2);
    this.mTargetWindowTokens.put(windowState.mToken, operation);
    fadeWindowToken(false, windowState.mToken, 64);
    operation.mLeash = windowState.mToken.getAnimationLeash();
    this.mHideImmediately = z;
  }

  public final boolean canDrawBeforeStartTransaction(Operation operation) {
    return (this.mAlwaysWaitForStartTransaction || operation.mAction == 1) ? false : true;
  }

  public void setSyncId(int i) {
    this.mSyncId = i;
  }

  public void resetSyncId() {
    this.mSyncId = -1;
  }

  public class Operation {
    public final int mAction;
    public SurfaceControl.Transaction mDrawTransaction;
    public boolean mIsCompletionPending;
    public SurfaceControl mLeash;

    public Operation(int i) {
      this.mAction = i;
    }
  }
}
