package com.android.server.wm;

import android.R;
import android.app.StatusBarManager;
import android.app.WindowConfiguration;
import android.content.res.CompatibilityInfo;
import android.graphics.Rect;
import android.os.IInstalld;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.InsetsAnimationControlCallbacks;
import android.view.InsetsAnimationControlImpl;
import android.view.InsetsAnimationControlRunner;
import android.view.InsetsController;
import android.view.InsetsFrameProvider;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowManager;
import android.view.inputmethod.ImeTracker;
import com.android.server.DisplayThread;
import com.android.server.display.DisplayPowerController2;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class InsetsPolicy {
  public static final int CONTROLLABLE_TYPES =
      (WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars())
          | WindowInsets.Type.ime();
  public boolean mAnimatingShown;
  public final DisplayContent mDisplayContent;
  public WindowState mFocusedWin;
  public int mForcedShowingTypes;
  public final boolean mHideNavBarForKeyboard;
  public boolean mLastTransientRequestedByPolicyControl;
  public final DisplayPolicy mPolicy;
  public int mShowingTransientTypes;
  public final InsetsStateController mStateController;
  public final InsetsControlTarget mDummyControlTarget =
      new InsetsControlTarget() { // from class: com.android.server.wm.InsetsPolicy.1
        @Override // com.android.server.wm.InsetsControlTarget
        public void notifyInsetsControlChanged() {
          SurfaceControl leash;
          InsetsSourceControl[] controlsForDispatch =
              InsetsPolicy.this.mStateController.getControlsForDispatch(this);
          if (controlsForDispatch == null) {
            return;
          }
          boolean z = false;
          for (InsetsSourceControl insetsSourceControl : controlsForDispatch) {
            if (!InsetsPolicy.this.isTransient(insetsSourceControl.getType())
                && (leash = insetsSourceControl.getLeash()) != null) {
              InsetsPolicy.this
                  .mDisplayContent
                  .getPendingTransaction()
                  .setAlpha(
                      leash,
                      (insetsSourceControl.getType() & WindowInsets.Type.defaultVisible()) != 0
                          ? 1.0f
                          : DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
              z = true;
            }
          }
          if (z) {
            InsetsPolicy.this.mDisplayContent.scheduleAnimation();
          }
        }
      };
  public final BarWindow mStatusBar = new BarWindow(1);
  public final BarWindow mNavBar = new BarWindow(2);
  public final float[] mTmpFloat9 = new float[9];
  public final PolicyControlTarget mPolicyControlTarget = new PolicyControlTarget();

  public InsetsPolicy(InsetsStateController insetsStateController, DisplayContent displayContent) {
    this.mStateController = insetsStateController;
    this.mDisplayContent = displayContent;
    DisplayPolicy displayPolicy = displayContent.getDisplayPolicy();
    this.mPolicy = displayPolicy;
    this.mHideNavBarForKeyboard =
        displayPolicy.getContext().getResources().getBoolean(R.bool.config_evenDimmerEnabled);
  }

  public void updateBarControlTarget(WindowState windowState) {
    InsetsControlTarget statusControlTarget;
    boolean z = windowState != null && windowState.isDexMode();
    if ((windowState == this.mPolicy.getNotificationShade() && !z)
        || (!PolicyControl.shouldApplyImmersiveStatus(windowState)
            && !PolicyControl.shouldApplyImmersiveNavigation(windowState))) {
      if (this.mFocusedWin != windowState) {
        abortTransient();
      } else if (needAbortTransientByPolicyControl(windowState)) {
        abortTransient();
      }
    }
    this.mFocusedWin = windowState;
    InsetsControlTarget statusControlTarget2 = getStatusControlTarget(windowState, false);
    InsetsControlTarget navControlTarget = getNavControlTarget(windowState, false);
    WindowState notificationShade = this.mPolicy.getNotificationShade();
    WindowState topFullscreenOpaqueWindow = this.mPolicy.getTopFullscreenOpaqueWindow();
    InsetsStateController insetsStateController = this.mStateController;
    InsetsControlTarget insetsControlTarget = null;
    if (statusControlTarget2 == this.mDummyControlTarget) {
      statusControlTarget = getStatusControlTarget(windowState, true);
    } else {
      statusControlTarget =
          statusControlTarget2 == notificationShade
              ? getStatusControlTarget(topFullscreenOpaqueWindow, true)
              : null;
    }
    if (navControlTarget == this.mDummyControlTarget) {
      insetsControlTarget = getNavControlTarget(windowState, true);
    } else if (navControlTarget == notificationShade) {
      insetsControlTarget = getNavControlTarget(topFullscreenOpaqueWindow, true);
    }
    insetsStateController.onBarControlTargetChanged(
        statusControlTarget2, statusControlTarget, navControlTarget, insetsControlTarget);
    this.mStatusBar.updateVisibility(statusControlTarget2, WindowInsets.Type.statusBars());
    this.mNavBar.updateVisibility(navControlTarget, WindowInsets.Type.navigationBars());
  }

  public boolean hasHiddenSources(int i) {
    InsetsState rawInsetsState = this.mStateController.getRawInsetsState();
    if ((WindowInsets.Type.navigationBars() & i) != 0) {
      rawInsetsState =
          this.mDisplayContent
              .getDisplayPolicy()
              .mExt
              .getTaskbarController()
              .adjustInsetsForTaskbar(rawInsetsState);
    }
    for (int sourceSize = rawInsetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
      InsetsSource sourceAt = rawInsetsState.sourceAt(sourceSize);
      if ((sourceAt.getType() & i) != 0
          && !sourceAt.getFrame().isEmpty()
          && !sourceAt.isVisible()) {
        return true;
      }
    }
    return false;
  }

  public void showTransient(int i, boolean z) {
    int i2 = this.mShowingTransientTypes;
    InsetsState rawInsetsState = this.mStateController.getRawInsetsState();
    if ((WindowInsets.Type.navigationBars() & i) != 0) {
      rawInsetsState =
          this.mDisplayContent
              .getDisplayPolicy()
              .mExt
              .getTaskbarController()
              .adjustInsetsForTaskbar(rawInsetsState);
    }
    for (int sourceSize = rawInsetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
      InsetsSource sourceAt = rawInsetsState.sourceAt(sourceSize);
      if (!sourceAt.isVisible()) {
        int type = sourceAt.getType();
        if ((sourceAt.getType() & i) != 0) {
          i2 |= type;
        }
      }
    }
    if (this.mShowingTransientTypes != i2) {
      this.mShowingTransientTypes = i2;
      StatusBarManagerInternal statusBarManagerInternal =
          this.mPolicy.getStatusBarManagerInternal();
      if (statusBarManagerInternal != null) {
        statusBarManagerInternal.showTransientToType(
            this.mDisplayContent.getDisplayId(),
            i2,
            z,
            StatusBarManager.getNaturalBarTypeByDisplayId(
                this.mPolicy.getContext(), this.mDisplayContent.getDisplayId()));
        if (CoreRune.CARLIFE_NAVBAR && this.mDisplayContent.isCarLifeDisplay()) {
          statusBarManagerInternal.showTransientToType(
              this.mDisplayContent.getDisplayId(), i2, z, 2);
        }
      }
      updateBarControlTarget(this.mFocusedWin);
      dispatchTransientSystemBarsVisibilityChanged(
          this.mFocusedWin,
          (i2 & (WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars())) != 0,
          z);
      this.mDisplayContent
          .mWmService
          .mAnimator
          .getChoreographer()
          .postFrameCallback(
              new Choreographer
                  .FrameCallback() { // from class:
                                     // com.android.server.wm.InsetsPolicy$$ExternalSyntheticLambda0
                @Override // android.view.Choreographer.FrameCallback
                public final void doFrame(long j) {
                  InsetsPolicy.this.lambda$showTransient$0(j);
                }
              });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$showTransient$0(long j) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mDisplayContent.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        startAnimation(true, null);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void hideTransient() {
    if (this.mShowingTransientTypes == 0) {
      return;
    }
    dispatchTransientSystemBarsVisibilityChanged(this.mFocusedWin, false, false);
    startAnimation(
        false,
        new Runnable() { // from class: com.android.server.wm.InsetsPolicy$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            InsetsPolicy.this.lambda$hideTransient$1();
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$hideTransient$1() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mDisplayContent.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        SparseArray sourceProviders = this.mStateController.getSourceProviders();
        for (int size = sourceProviders.size() - 1; size >= 0; size--) {
          InsetsSourceProvider insetsSourceProvider =
              (InsetsSourceProvider) sourceProviders.valueAt(size);
          if (isTransient(insetsSourceProvider.getSource().getType())) {
            insetsSourceProvider.setClientVisible(false);
          }
        }
        this.mShowingTransientTypes = 0;
        updateBarControlTarget(this.mFocusedWin);
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean isTransient(int i) {
    return (this.mShowingTransientTypes & i) != 0;
  }

  public InsetsState adjustInsetsForWindow(
      WindowState windowState, InsetsState insetsState, boolean z) {
    InsetsState adjustVisibilityForTransientTypes =
        !z ? adjustVisibilityForTransientTypes(insetsState) : insetsState;
    InsetsState adjustVisibilityForIme =
        adjustVisibilityForIme(
            windowState,
            adjustVisibilityForTransientTypes,
            adjustVisibilityForTransientTypes == insetsState);
    return adjustInsetsForRoundedCorners(
        windowState.mToken, adjustVisibilityForIme, adjustVisibilityForIme == insetsState);
  }

  public InsetsState adjustInsetsForWindow(WindowState windowState, InsetsState insetsState) {
    return adjustInsetsForWindow(windowState, insetsState, false);
  }

  public void getInsetsForWindowMetrics(WindowToken windowToken, InsetsState insetsState) {
    InsetsState rawInsetsState;
    if (windowToken != null && windowToken.isFixedRotationTransforming()) {
      rawInsetsState = windowToken.getFixedRotationTransformInsetsState();
    } else {
      rawInsetsState = this.mStateController.getRawInsetsState();
    }
    insetsState.set(rawInsetsState, true);
    if (windowToken != null && windowToken.isLayoutNeededInUdcCutout()) {
      this.mDisplayContent.mUdcCutoutPolicy.adjustInsetsForUdc(windowToken, insetsState);
    }
    for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
      InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
      if (isTransient(sourceAt.getType())) {
        sourceAt.setVisible(false);
      }
    }
    adjustInsetsForRoundedCorners(windowToken, insetsState, false);
    if (windowToken == null || !windowToken.hasSizeCompatBounds()) {
      return;
    }
    insetsState.scale(1.0f / windowToken.getCompatScale());
  }

  public InsetsState enforceInsetsPolicyForTarget(
      WindowManager.LayoutParams layoutParams, int i, boolean z, InsetsState insetsState) {
    InsetsState insetsState2;
    DisplayContent displayContent;
    boolean z2 = false;
    if (layoutParams.type == 2011) {
      insetsState2 = new InsetsState(insetsState);
      insetsState2.removeSource(InsetsSource.ID_IME);
    } else {
      InsetsFrameProvider[] insetsFrameProviderArr = layoutParams.providedInsets;
      if (insetsFrameProviderArr != null) {
        InsetsState insetsState3 = insetsState;
        for (InsetsFrameProvider insetsFrameProvider : insetsFrameProviderArr) {
          if ((insetsFrameProvider.getType() & WindowInsets.Type.systemBars()) != 0) {
            if (insetsState3 == insetsState) {
              insetsState3 = new InsetsState(insetsState3);
            }
            insetsState3.removeSource(insetsFrameProvider.getId());
          }
        }
        insetsState2 = insetsState3;
      } else {
        insetsState2 = insetsState;
      }
    }
    int i2 = layoutParams.type;
    boolean z3 = i2 == 1 || i2 == 2 || i2 == 4;
    if (!CoreRune.MW_CAPTION_SHELL_BUG_FIX
        || (displayContent = this.mDisplayContent) == null
        || !displayContent.isNewDexMode()
        || !z3
        || (i != 1 && i != 6)) {
      z2 = true;
    }
    if ((!layoutParams.isFullscreen() || layoutParams.getFitInsetsTypes() != 0) && z2) {
      if (insetsState2 == insetsState) {
        insetsState2 = new InsetsState(insetsState);
      }
      for (int sourceSize = insetsState2.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
        if (insetsState2.sourceAt(sourceSize).getType() == WindowInsets.Type.captionBar()) {
          insetsState2.removeSourceAt(sourceSize);
        }
      }
    }
    SparseArray sourceProviders = this.mStateController.getSourceProviders();
    int i3 = layoutParams.type;
    for (int size = sourceProviders.size() - 1; size >= 0; size--) {
      InsetsSourceProvider insetsSourceProvider =
          (InsetsSourceProvider) sourceProviders.valueAt(size);
      if (insetsSourceProvider.overridesFrame(i3)) {
        if (insetsState2 == insetsState) {
          insetsState2 = new InsetsState(insetsState2);
        }
        InsetsSource insetsSource = new InsetsSource(insetsSourceProvider.getSource());
        insetsSource.setFrame(insetsSourceProvider.getOverriddenFrame(i3));
        insetsState2.addSource(insetsSource);
      }
    }
    if (!WindowConfiguration.isFloating(i) && (i != 6 || !z)) {
      return insetsState2;
    }
    int captionBar = WindowInsets.Type.captionBar();
    if (i != 2) {
      captionBar |= WindowInsets.Type.ime();
    }
    InsetsState insetsState4 = new InsetsState();
    insetsState4.set(insetsState2, captionBar);
    return insetsState4;
  }

  public final InsetsState adjustVisibilityForTransientTypes(InsetsState insetsState) {
    InsetsState insetsState2 = insetsState;
    for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
      InsetsSource sourceAt = insetsState2.sourceAt(sourceSize);
      if (isTransient(sourceAt.getType()) && sourceAt.isVisible()) {
        if (insetsState2 == insetsState) {
          insetsState2 = new InsetsState(insetsState);
        }
        InsetsSource insetsSource = new InsetsSource(sourceAt);
        insetsSource.setVisible(false);
        insetsState2.addSource(insetsSource);
      }
    }
    return insetsState2;
  }

  public final InsetsState adjustVisibilityForIme(
      WindowState windowState, InsetsState insetsState, boolean z) {
    InsetsSource peekSource;
    boolean z2 = true;
    if (windowState.mIsImWindow) {
      boolean z3 = !this.mHideNavBarForKeyboard;
      InsetsState insetsState2 = insetsState;
      for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
        InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
        if (sourceAt.getType() == WindowInsets.Type.navigationBars()
            && windowState.isDexMode()
            && windowState.mWmService.mAtmService.mDexController.isDexForceImmersiveModeEnabled()) {
          sourceAt.setFrame(0, 0, 0, 0);
        }
        if (sourceAt.getType() == WindowInsets.Type.navigationBars()
            && sourceAt.isVisible() != z3) {
          if (insetsState2 == insetsState && z) {
            insetsState2 = new InsetsState(insetsState);
          }
          InsetsSource insetsSource = new InsetsSource(sourceAt);
          insetsSource.setVisible(z3);
          insetsState2.addSource(insetsSource);
        }
      }
      return insetsState2;
    }
    ActivityRecord activityRecord = windowState.mActivityRecord;
    if (activityRecord != null
        && activityRecord.mImeInsetsFrozenUntilStartInput
        && (peekSource = insetsState.peekSource(InsetsSource.ID_IME)) != null) {
      if (windowState.inFreeformWindowingMode()) {
        if (!windowState.mActivityRecord.mLastImeShown
            || !windowState.isRequestedVisible(WindowInsets.Type.ime())) {
          z2 = false;
        }
      } else {
        z2 = windowState.isRequestedVisible(WindowInsets.Type.ime());
      }
      if (z) {
        insetsState = new InsetsState(insetsState);
      }
      InsetsSource insetsSource2 = new InsetsSource(peekSource);
      insetsSource2.setVisible(z2);
      insetsState.addSource(insetsSource2);
    }
    return insetsState;
  }

  public final InsetsState adjustInsetsForRoundedCorners(
      WindowToken windowToken, InsetsState insetsState, boolean z) {
    if (windowToken != null) {
      ActivityRecord asActivityRecord = windowToken.asActivityRecord();
      Task task = asActivityRecord != null ? asActivityRecord.getTask() : null;
      if (task != null && !task.getWindowConfiguration().tasksAreFloating()) {
        if (z) {
          insetsState = new InsetsState(insetsState);
        }
        insetsState.setRoundedCornerFrame(task.getBounds());
      }
    }
    return insetsState;
  }

  public void onInsetsModified(InsetsControlTarget insetsControlTarget) {
    this.mStateController.onInsetsModified(insetsControlTarget);
    checkAbortTransient(insetsControlTarget);
    updateBarControlTarget(this.mFocusedWin);
  }

  public final void checkAbortTransient(InsetsControlTarget insetsControlTarget) {
    if (this.mShowingTransientTypes == 0) {
      return;
    }
    int requestedVisibleTypes =
        (insetsControlTarget.getRequestedVisibleTypes()
                & this.mStateController.getFakeControllingTypes(insetsControlTarget))
            | (this.mStateController.getImeSourceProvider().isClientVisible()
                ? WindowInsets.Type.navigationBars()
                : 0);
    this.mShowingTransientTypes &= ~requestedVisibleTypes;
    if (requestedVisibleTypes != 0) {
      this.mDisplayContent.setLayoutNeeded();
      this.mDisplayContent.mWmService.requestTraversal();
      StatusBarManagerInternal statusBarManagerInternal =
          this.mPolicy.getStatusBarManagerInternal();
      if (statusBarManagerInternal != null) {
        statusBarManagerInternal.abortTransient(
            this.mDisplayContent.getDisplayId(), requestedVisibleTypes);
        statusBarManagerInternal.abortTransientToType(
            this.mDisplayContent.getDisplayId(),
            requestedVisibleTypes,
            StatusBarManager.getNaturalBarTypeByDisplayId(
                this.mPolicy.getContext(), this.mDisplayContent.getDisplayId()));
        if (CoreRune.CARLIFE_NAVBAR && this.mDisplayContent.isCarLifeDisplay()) {
          statusBarManagerInternal.abortTransientToType(
              this.mDisplayContent.getDisplayId(), requestedVisibleTypes, 2);
        }
      }
    }
  }

  public void abortTransient() {
    if (this.mShowingTransientTypes == 0) {
      return;
    }
    StatusBarManagerInternal statusBarManagerInternal = this.mPolicy.getStatusBarManagerInternal();
    if (statusBarManagerInternal != null) {
      statusBarManagerInternal.abortTransientToType(
          this.mDisplayContent.getDisplayId(),
          this.mShowingTransientTypes,
          StatusBarManager.getNaturalBarTypeByDisplayId(
              this.mPolicy.getContext(), this.mDisplayContent.getDisplayId()));
      if (CoreRune.CARLIFE_NAVBAR && this.mDisplayContent.isCarLifeDisplay()) {
        statusBarManagerInternal.abortTransientToType(
            this.mDisplayContent.getDisplayId(), this.mShowingTransientTypes, 2);
      }
    }
    this.mShowingTransientTypes = 0;
    this.mDisplayContent.setLayoutNeeded();
    this.mDisplayContent.mWmService.requestTraversal();
    dispatchTransientSystemBarsVisibilityChanged(this.mFocusedWin, false, false);
  }

  public final InsetsControlTarget getStatusControlTarget(WindowState windowState, boolean z) {
    if (!z && isTransient(WindowInsets.Type.statusBars())) {
      return this.mDummyControlTarget;
    }
    if (PolicyControl.shouldApplyImmersiveStatus(windowState)) {
      return this.mPolicyControlTarget;
    }
    WindowState notificationShade = this.mPolicy.getNotificationShade();
    if (windowState == notificationShade) {
      return windowState;
    }
    if (remoteInsetsControllerControlsSystemBars(windowState)) {
      ActivityRecord activityRecord = windowState.mActivityRecord;
      this.mDisplayContent.mRemoteInsetsControlTarget.topFocusedWindowChanged(
          activityRecord != null ? activityRecord.mActivityComponent : null,
          windowState.getRequestedVisibleTypes());
      return this.mDisplayContent.mRemoteInsetsControlTarget;
    }
    if (areTypesForciblyShowing(WindowInsets.Type.statusBars())) {
      return null;
    }
    if (forceShowsStatusBarTransiently() && !z) {
      return this.mDummyControlTarget;
    }
    if (!canBeTopFullscreenOpaqueWindow(windowState)
        && this.mPolicy.topAppHidesSystemBar(WindowInsets.Type.statusBars())
        && (notificationShade == null || !notificationShade.canReceiveKeys())) {
      return this.mPolicy.getTopFullscreenOpaqueWindow();
    }
    if (windowState == null || !windowState.inFreeformWindowingMode()) {
      return (windowState == null || !windowState.isPopOver())
          ? windowState
          : this.mPolicy.getTopFullscreenOpaqueWindow();
    }
    return this.mPolicy.getTopFullscreenOpaqueWindow();
  }

  public static boolean canBeTopFullscreenOpaqueWindow(WindowState windowState) {
    int i;
    return (!(windowState != null && (i = windowState.mAttrs.type) >= 1 && i <= 99)
            || !windowState.mAttrs.isFullscreen()
            || windowState.isFullyTransparent()
            || windowState.inMultiWindowMode()
            || windowState.isPopOver())
        ? false
        : true;
  }

  public WindowState getStatusControlWindow(WindowState windowState) {
    InsetsControlTarget statusControlTarget = getStatusControlTarget(windowState, false);
    if (statusControlTarget != null) {
      return statusControlTarget.getWindow();
    }
    return null;
  }

  public WindowState getNavControlWindow(WindowState windowState) {
    InsetsControlTarget navControlTarget = getNavControlTarget(windowState, false);
    if (navControlTarget != null) {
      return navControlTarget.getWindow();
    }
    return null;
  }

  public static boolean canBeDexNavigationBarControl(WindowState windowState) {
    if (windowState == null
        || !windowState.isDexMode()
        || !windowState.inFullscreenWindowingMode()
        || windowState.isFullyTransparent()) {
      return false;
    }
    WindowManager.LayoutParams layoutParams = windowState.mAttrs;
    int i = layoutParams.type;
    if (i == 2009) {
      return true;
    }
    return i == 2008 && layoutParams.isFullscreen();
  }

  public final InsetsControlTarget getNavControlTarget(WindowState windowState, boolean z) {
    InsetsSourceProvider controllableInsetProvider;
    WindowState windowState2 = this.mDisplayContent.mInputMethodWindow;
    if (windowState2 != null
        && windowState2.isVisible()
        && !this.mHideNavBarForKeyboard
        && !this.mDisplayContent.isDexMode()) {
      return null;
    }
    if (!z && isTransient(WindowInsets.Type.navigationBars())) {
      return this.mDummyControlTarget;
    }
    if (this.mDisplayContent.getDisplayId() == 2 && isKeyguardPresentation(windowState)) {
      return null;
    }
    if (PolicyControl.shouldApplyImmersiveNavigation(windowState)) {
      return this.mPolicyControlTarget;
    }
    if (windowState == this.mPolicy.getNotificationShade()) {
      return windowState;
    }
    if (windowState != null
        && (controllableInsetProvider = windowState.getControllableInsetProvider()) != null
        && controllableInsetProvider.getSource().getType() == WindowInsets.Type.navigationBars()) {
      return windowState;
    }
    if (remoteInsetsControllerControlsSystemBars(windowState)) {
      ActivityRecord activityRecord = windowState.mActivityRecord;
      this.mDisplayContent.mRemoteInsetsControlTarget.topFocusedWindowChanged(
          activityRecord != null ? activityRecord.mActivityComponent : null,
          windowState.getRequestedVisibleTypes());
      return this.mDisplayContent.mRemoteInsetsControlTarget;
    }
    if (areTypesForciblyShowing(WindowInsets.Type.navigationBars())) {
      return null;
    }
    boolean z2 = true;
    if (!forceShowsNavigationBarInFreeformMode()) {
      DisplayContent displayContent = this.mDisplayContent;
      if (!displayContent.isDefaultDisplay
          || displayContent.mAtmService.mDexController.getDexModeLocked() != 2
          || !this.mDisplayContent.mAtmService.mDexController.forceShowSystemBars()) {
        z2 = false;
      }
    }
    if (z2) {
      return null;
    }
    if (forceShowsNavigationBarTransiently() && !z) {
      return this.mDummyControlTarget;
    }
    WindowState notificationShade = this.mPolicy.getNotificationShade();
    return (canBeTopFullscreenOpaqueWindow(windowState)
            || !this.mPolicy.topAppHidesSystemBar(WindowInsets.Type.navigationBars())
            || (notificationShade != null && notificationShade.canReceiveKeys())
            || canBeDexNavigationBarControl(windowState))
        ? windowState
        : this.mPolicy.getTopFullscreenOpaqueWindow();
  }

  public boolean areTypesForciblyShowing(int i) {
    return (this.mForcedShowingTypes & i) == i;
  }

  public void updateSystemBars(WindowState windowState, boolean z, boolean z2) {
    int statusBars;
    if (z) {
      statusBars = WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars();
    } else {
      statusBars = forceShowingNavigationBars(windowState) ? WindowInsets.Type.navigationBars() : 0;
    }
    this.mForcedShowingTypes = statusBars;
    if (z
        && ((!PolicyControl.shouldApplyImmersiveNavigation(this.mFocusedWin)
                && isTransient(WindowInsets.Type.navigationBars()))
            || (!PolicyControl.shouldApplyImmersiveStatus(this.mFocusedWin)
                && isTransient(WindowInsets.Type.statusBars())))) {
      abortTransient();
    }
    this.mStateController.setForcedConsumingTypes(
        (remoteInsetsControllerControlsSystemBars(windowState)
                ? WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars()
                : 0)
            | this.mForcedShowingTypes);
    updateBarControlTarget(windowState);
  }

  public final boolean forceShowingNavigationBars(WindowState windowState) {
    return this.mPolicy.isForceShowNavigationBarEnabled()
        && windowState != null
        && windowState.getActivityType() == 1;
  }

  public boolean remoteInsetsControllerControlsSystemBars(WindowState windowState) {
    DisplayContent displayContent;
    return windowState != null
        && this.mPolicy.isRemoteInsetsControllerControllingSystemBars()
        && (displayContent = this.mDisplayContent) != null
        && displayContent.mRemoteInsetsControlTarget != null
        && windowState.getAttrs().type >= 1
        && windowState.getAttrs().type <= 99;
  }

  public final boolean forceShowsStatusBarTransiently() {
    WindowState statusBar = this.mPolicy.getStatusBar();
    return (statusBar == null
            || (statusBar.mAttrs.privateFlags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES)
                == 0)
        ? false
        : true;
  }

  public final boolean forceShowsNavigationBarTransiently() {
    WindowState notificationShade = this.mPolicy.getNotificationShade();
    return (notificationShade == null || (notificationShade.mAttrs.privateFlags & 8388608) == 0)
        ? false
        : true;
  }

  public void startAnimation(boolean z, Runnable runnable) {
    SparseArray sparseArray = new SparseArray();
    InsetsSourceControl[] controlsForDispatch =
        this.mStateController.getControlsForDispatch(this.mDummyControlTarget);
    if (controlsForDispatch == null) {
      if (runnable != null) {
        DisplayThread.getHandler().post(runnable);
        return;
      }
      return;
    }
    int i = 0;
    for (InsetsSourceControl insetsSourceControl : controlsForDispatch) {
      if (isTransient(insetsSourceControl.getType()) && insetsSourceControl.getLeash() != null) {
        i |= insetsSourceControl.getType();
        sparseArray.put(insetsSourceControl.getId(), new InsetsSourceControl(insetsSourceControl));
      }
    }
    controlAnimationUnchecked(i, sparseArray, z, runnable);
  }

  public final void controlAnimationUnchecked(
      int i, SparseArray sparseArray, boolean z, Runnable runnable) {
    new InsetsPolicyAnimationControlListener(z, runnable, i)
        .mControlCallbacks.controlAnimationUnchecked(i, sparseArray, z);
  }

  public final void dispatchTransientSystemBarsVisibilityChanged(
      WindowState windowState, boolean z, boolean z2) {
    Task task;
    if (windowState == null || (task = windowState.getTask()) == null) {
      return;
    }
    int i = task.mTaskId;
    if (i != -1) {
      this.mDisplayContent.mWmService.mTaskSystemBarsListenerController
          .dispatchTransientSystemBarVisibilityChanged(i, z, z2);
    }
  }

  public void dump(String str, PrintWriter printWriter) {
    printWriter.println(str + "InsetsPolicy");
    String str2 = str + "  ";
    printWriter.println(
        str2 + "status: " + StatusBarManager.windowStateToString(this.mStatusBar.mState));
    printWriter.println(str2 + "nav: " + StatusBarManager.windowStateToString(this.mNavBar.mState));
    if (this.mShowingTransientTypes != 0) {
      printWriter.println(
          str2
              + "mShowingTransientTypes="
              + WindowInsets.Type.toString(this.mShowingTransientTypes));
    }
    if (this.mForcedShowingTypes != 0) {
      printWriter.println(
          str2 + "mForcedShowingTypes=" + WindowInsets.Type.toString(this.mForcedShowingTypes));
    }
  }

  public class BarWindow {
    public final int mId;
    public int mState = 0;

    public BarWindow(int i) {
      this.mId = i;
    }

    public final void updateVisibility(InsetsControlTarget insetsControlTarget, int i) {
      setVisible(insetsControlTarget == null || insetsControlTarget.isRequestedVisible(i));
    }

    public final void setVisible(boolean z) {
      int i = z ? 0 : 2;
      if (this.mState != i) {
        this.mState = i;
        StatusBarManagerInternal statusBarManagerInternal =
            InsetsPolicy.this.mPolicy.getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
          statusBarManagerInternal.setWindowState(
              InsetsPolicy.this.mDisplayContent.getDisplayId(), this.mId, i);
        }
      }
    }
  }

  public class InsetsPolicyAnimationControlListener
      extends InsetsController.InternalAnimationControlListener {
    public InsetsPolicyAnimationControlCallbacks mControlCallbacks;
    public Runnable mFinishCallback;

    public InsetsPolicyAnimationControlListener(boolean z, Runnable runnable, int i) {
      super(
          z,
          false,
          i,
          2,
          false,
          0,
          (WindowInsetsAnimationControlListener) null,
          (ImeTracker.InputMethodJankContext) null);
      this.mFinishCallback = runnable;
      this.mControlCallbacks = new InsetsPolicyAnimationControlCallbacks(this);
    }

    public void onAnimationFinish() {
      super.onAnimationFinish();
      if (this.mFinishCallback != null) {
        DisplayThread.getHandler().post(this.mFinishCallback);
      }
    }

    public class InsetsPolicyAnimationControlCallbacks implements InsetsAnimationControlCallbacks {
      public InsetsAnimationControlImpl mAnimationControl = null;
      public InsetsPolicyAnimationControlListener mListener;

      public void notifyFinished(
          InsetsAnimationControlRunner insetsAnimationControlRunner, boolean z) {}

      public void reportPerceptible(int i, boolean z) {}

      public void startAnimation(
          InsetsAnimationControlRunner insetsAnimationControlRunner,
          WindowInsetsAnimationControlListener windowInsetsAnimationControlListener,
          int i,
          WindowInsetsAnimation windowInsetsAnimation,
          WindowInsetsAnimation.Bounds bounds) {}

      public InsetsPolicyAnimationControlCallbacks(
          InsetsPolicyAnimationControlListener insetsPolicyAnimationControlListener) {
        this.mListener = insetsPolicyAnimationControlListener;
      }

      public final void controlAnimationUnchecked(final int i, SparseArray sparseArray, boolean z) {
        if (i == 0) {
          return;
        }
        InsetsPolicy.this.mAnimatingShown = z;
        InsetsState insetsState = InsetsPolicy.this.mFocusedWin.getInsetsState();
        InsetsController.InternalAnimationControlListener internalAnimationControlListener =
            this.mListener;
        this.mAnimationControl =
            new InsetsAnimationControlImpl(
                sparseArray,
                (Rect) null,
                insetsState,
                internalAnimationControlListener,
                i,
                this,
                internalAnimationControlListener.getDurationMs(),
                InsetsPolicyAnimationControlListener.this.getInsetsInterpolator(),
                !z ? 1 : 0,
                !z ? 1 : 0,
                (CompatibilityInfo.Translator) null,
                (ImeTracker.Token) null);
        SurfaceAnimationThread.getHandler()
            .post(
                new Runnable() { // from class:
                                 // com.android.server.wm.InsetsPolicy$InsetsPolicyAnimationControlListener$InsetsPolicyAnimationControlCallbacks$$ExternalSyntheticLambda0
                  @Override // java.lang.Runnable
                  public final void run() {
                    InsetsPolicy.InsetsPolicyAnimationControlListener
                        .InsetsPolicyAnimationControlCallbacks.this
                        .lambda$controlAnimationUnchecked$0(i);
                  }
                });
      }

      /* JADX INFO: Access modifiers changed from: private */
      public /* synthetic */ void lambda$controlAnimationUnchecked$0(int i) {
        this.mListener.onReady(this.mAnimationControl, i);
      }

      public void scheduleApplyChangeInsets(
          InsetsAnimationControlRunner insetsAnimationControlRunner) {
        if (this.mAnimationControl.applyChangeInsets((InsetsState) null)) {
          this.mAnimationControl.finish(InsetsPolicy.this.mAnimatingShown);
        }
      }

      public void applySurfaceParams(
          SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        for (int length = surfaceParamsArr.length - 1; length >= 0; length--) {
          SyncRtSurfaceTransactionApplier.applyParams(
              transaction, surfaceParamsArr[length], InsetsPolicy.this.mTmpFloat9);
        }
        transaction.apply();
        transaction.close();
      }

      public void releaseSurfaceControlFromRt(SurfaceControl surfaceControl) {
        surfaceControl.release();
      }
    }
  }

  public final boolean needAbortTransientByPolicyControl(WindowState windowState) {
    if (this.mShowingTransientTypes > 0
        || !this.mLastTransientRequestedByPolicyControl
        || windowState == null
        || PolicyControl.shouldApplyImmersiveStatus(windowState)
        || PolicyControl.shouldApplyImmersiveNavigation(windowState)) {
      return false;
    }
    return !windowState.canShowTransient()
        || windowState.isRequestedVisible(WindowInsets.Type.systemBars());
  }

  public final class PolicyControlTarget implements InsetsControlTarget {
    @Override // com.android.server.wm.InsetsControlTarget
    public boolean canShowTransient() {
      return true;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public int getRequestedVisibleTypes() {
      return 0;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public boolean isRequestedVisible(int i) {
      return false;
    }

    public PolicyControlTarget() {}

    @Override // com.android.server.wm.InsetsControlTarget
    public void notifyInsetsControlChanged() {
      final InsetsSourceControl[] controlsForDispatch =
          InsetsPolicy.this.mStateController.getControlsForDispatch(this);
      if (controlsForDispatch == null) {
        return;
      }
      SparseArray sparseArray = new SparseArray();
      final int i = 0;
      for (InsetsSourceControl insetsSourceControl : controlsForDispatch) {
        SurfaceControl leash = insetsSourceControl.getLeash();
        if (leash != null && leash.isValid()) {
          if (canStartHideAnimation(insetsSourceControl)) {
            i |= insetsSourceControl.getType();
            sparseArray.put(
                insetsSourceControl.getType(), new InsetsSourceControl(insetsSourceControl));
          } else {
            SurfaceControl.Transaction surfaceTransaction =
                getSurfaceTransaction(insetsSourceControl);
            surfaceTransaction.setAlpha(leash, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            surfaceTransaction.hide(leash);
          }
        }
      }
      if (i != 0) {
        Slog.d(
            StartingSurfaceController.TAG,
            "notifyInsetsControlChanged: hide anim, typesReady=" + i);
        InsetsPolicy.this.controlAnimationUnchecked(
            i,
            sparseArray,
            false,
            new Runnable() { // from class:
                             // com.android.server.wm.InsetsPolicy$PolicyControlTarget$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                InsetsPolicy.PolicyControlTarget.this.lambda$notifyInsetsControlChanged$0(
                    controlsForDispatch, i);
              }
            });
      }
      InsetsPolicy.this.mDisplayContent.scheduleAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyInsetsControlChanged$0(
        InsetsSourceControl[] insetsSourceControlArr, int i) {
      InsetsSourceProvider insetsSourceProvider;
      SurfaceControl leash;
      WindowManagerGlobalLock windowManagerGlobalLock =
          InsetsPolicy.this.mDisplayContent.mWmService.mGlobalLock;
      WindowManagerService.boostPriorityForLockedSection();
      synchronized (windowManagerGlobalLock) {
        try {
          for (InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
            if ((insetsSourceControl.getType() & i) != 0
                && (insetsSourceProvider =
                        (InsetsSourceProvider)
                            InsetsPolicy.this
                                .mStateController
                                .getSourceProviders()
                                .get(insetsSourceControl.getId()))
                    != null
                && !insetsSourceProvider.getSource().isVisible()
                && (leash = insetsSourceControl.getLeash()) != null
                && leash.isValid()) {
              SurfaceControl.Transaction surfaceTransaction =
                  getSurfaceTransaction(insetsSourceControl);
              surfaceTransaction.setAlpha(leash, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
              surfaceTransaction.hide(leash);
            }
          }
        } catch (Throwable th) {
          WindowManagerService.resetPriorityAfterLockedSection();
          throw th;
        }
      }
      WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final SurfaceControl.Transaction getSurfaceTransaction(
        InsetsSourceControl insetsSourceControl) {
      WindowContainer windowContainer;
      InsetsSourceProvider insetsSourceProvider =
          (InsetsSourceProvider)
              InsetsPolicy.this
                  .mStateController
                  .getSourceProviders()
                  .get(insetsSourceControl.getId());
      if (insetsSourceProvider != null
          && (windowContainer = insetsSourceProvider.mWindowContainer) != null) {
        return windowContainer.getSyncTransaction();
      }
      return InsetsPolicy.this.mDisplayContent.getSyncTransaction();
    }

    public final boolean canStartHideAnimation(InsetsSourceControl insetsSourceControl) {
      int type = insetsSourceControl.getType();
      if (InsetsPolicy.this.hasHiddenSources(type)) {
        return false;
      }
      if (type == WindowInsets.Type.navigationBars()) {
        return InsetsPolicy.this
            .mDisplayContent
            .getDisplayPolicy()
            .isInImmersiveSplitMode(InsetsPolicy.this.mFocusedWin);
      }
      if (type == WindowInsets.Type.statusBars()) {
        return InsetsPolicy.this
            .mDisplayContent
            .getDisplayPolicy()
            .isInImmersiveSplitMode(InsetsPolicy.this.mFocusedWin, true);
      }
      return false;
    }
  }

  public static boolean isKeyguardPresentation(WindowState windowState) {
    if (windowState != null) {
      WindowManager.LayoutParams layoutParams = windowState.mAttrs;
      if (layoutParams.type == 2009 && (layoutParams.insetsFlags.behavior & 2) != 0) {
        return true;
      }
    }
    return false;
  }

  public final boolean forceShowsNavigationBarInFreeformMode() {
    WindowState windowState = this.mFocusedWin;
    return windowState != null && windowState.inFreeformWindowingMode();
  }
}
