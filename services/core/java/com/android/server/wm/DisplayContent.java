package com.android.server.wm;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.ContextImpl;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.HardwareBuffer;
import android.hardware.display.DisplayManagerInternal;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Debug;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.WorkSource;
import android.os.IInstalld;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.DisplayUtils;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Pair;
import android.util.RotationUtils;
import android.util.Size;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.util.proto.ProtoOutputStream;
import android.view.ContentRecordingSession;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.DisplayShape;
import android.view.IDisplayWindowInsetsController;
import android.view.ISystemGestureExclusionListener;
import android.view.IWindow;
import android.view.InputDevice;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.MagnificationSpec;
import android.view.MotionEvent;
import android.view.PrivacyIndicatorBounds;
import android.view.RemoteAnimationDefinition;
import android.view.RoundedCorners;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerPolicyConstants;
import android.view.inputmethod.ImeTracker;
import android.window.IDisplayAreaOrganizer;
import android.window.ScreenCapture;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalServices;
import com.android.server.display.DisplayPowerController2;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.wm.utils.RegionUtils;
import com.android.server.wm.utils.RotationCache;
import com.android.server.wm.utils.WmDisplayCutout;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class DisplayContent extends RootDisplayArea
    implements WindowManagerPolicy.DisplayContentInfo {
  public static final InsetsState.OnTraverseCallbacks COPY_SOURCE_VISIBILITY =
      new InsetsState.OnTraverseCallbacks() { // from class: com.android.server.wm.DisplayContent.1
        public void onIdMatch(InsetsSource insetsSource, InsetsSource insetsSource2) {
          insetsSource.setVisible(insetsSource2.isVisible());
        }
      };
  boolean isDefaultDisplay;
  public SurfaceControl mA11yOverlayLayer;
  public Set mActiveSizeCompatActivities;
  public final ArrayList mAllSleepTokens;
  public final AppTransition mAppTransition;
  public final AppTransitionController mAppTransitionController;
  public volatile IApplicationPolicy mApplicationPolicy;
  public final Consumer mApplyPostLayoutPolicy;
  public final Consumer mApplySurfaceChangesTransaction;
  public AsyncRotationController mAsyncRotationController;
  public final ActivityTaskManagerService mAtmService;
  public DisplayCutout mBaseDisplayCutout;
  public int mBaseDisplayDensity;
  public int mBaseDisplayHeight;
  public float mBaseDisplayPhysicalXDpi;
  public float mBaseDisplayPhysicalYDpi;
  public int mBaseDisplayWidth;
  public RoundedCorners mBaseRoundedCorners;
  public final ArraySet mChangingContainers;
  final float mCloseToSquareMaxAspectRatio;
  public final ArraySet mClosingApps;
  public final ArrayMap mClosingChangingContainers;
  public final DisplayMetrics mCompatDisplayMetrics;
  public float mCompatibleScreenScale;
  public final Predicate mComputeImeTargetPredicate;
  public ContentRecorder mContentRecorder;
  public WindowState mCurrentFocus;
  public int mCurrentOverrideConfigurationChanges;
  public PrivacyIndicatorBounds mCurrentPrivacyIndicatorBounds;
  public String mCurrentUniqueDisplayId;
  public int mDeferUpdateImeTargetCount;
  public boolean mDeferredRemoval;
  public DesktopModeManagerInternal mDesktopModeManagerInternal;
  public final Consumer mDeviceStateConsumer;
  final DeviceStateController mDeviceStateController;
  public final Display mDisplay;
  public IntArray mDisplayAccessUIDs;
  DisplayAreaPolicy mDisplayAreaPolicy;
  public final RotationCache mDisplayCutoutCache;
  public DisplayFrames mDisplayFrames;
  public final int mDisplayId;
  public final DisplayInfo mDisplayInfo;
  public final DisplayMetrics mDisplayMetrics;
  public final DisplayPolicy mDisplayPolicy;
  public boolean mDisplayReady;
  public final DisplayRotation mDisplayRotation;
  public final DisplayRotationCompatPolicy mDisplayRotationCompatPolicy;
  public boolean mDisplayScalingDisabled;
  public final RotationCache mDisplayShapeCache;
  public final PhysicalDisplaySwitchTransitionLauncher mDisplaySwitchTransitionLauncher;
  public boolean mDontMoveToTop;
  public DisplayWindowPolicyControllerHelper mDwpcHelper;
  public boolean mFadeInOutAnimationNeeded;
  public final ToBooleanFunction mFindFocusedWindow;
  public ActivityRecord mFixedRotationLaunchingApp;
  public final FixedRotationTransitionListener mFixedRotationTransitionListener;
  public ActivityRecord mFocusedApp;
  public int mForcedHideCutout;
  public boolean mHasBlackSnapshot;
  public PowerManager.WakeLock mHoldScreenWakeLock;
  public WindowState mHoldScreenWindow;
  public boolean mIgnoreDisplayCutout;
  public InsetsControlTarget mImeControlTarget;
  public InputTarget mImeInputTarget;
  public WindowState mImeLayeringTarget;
  public ImeScreenshot mImeScreenshot;
  public Pair mImeTargetTokenListenerPair;
  public final ImeContainer mImeWindowsContainer;
  public boolean mInEnsureActivitiesVisible;
  public boolean mInTouchMode;
  public DisplayCutout mInitialDisplayCutout;
  public int mInitialDisplayDensity;
  public int mInitialDisplayHeight;
  public DisplayShape mInitialDisplayShape;
  public int mInitialDisplayWidth;
  public float mInitialPhysicalXDpi;
  public float mInitialPhysicalYDpi;
  public RoundedCorners mInitialRoundedCorners;
  SurfaceControl mInputMethodSurfaceParent;
  public WindowContainer mInputMethodSurfaceParentContainer;
  public WindowState mInputMethodWindow;
  public InputMonitor mInputMonitor;
  public SurfaceControl mInputOverlayLayer;
  public final InsetsPolicy mInsetsPolicy;
  public final InsetsStateController mInsetsStateController;
  public boolean mIsDensityForced;
  public boolean mIsInExitingRecents;
  public boolean mIsOverlappingWithCutoutAsDefault;
  public boolean mIsSizeForced;
  public boolean mLastHasContent;
  public InputTarget mLastImeInputTarget;
  public WindowContainer mLastOrientationControlSource;
  public WindowState mLastWakeLockHoldingWindow;
  public WindowState mLastWakeLockObscuringWindow;
  public boolean mLastWallpaperVisible;
  public boolean mLayoutNeeded;
  public int mLayoutSeq;
  public MagnificationSpec mMagnificationSpec;
  public int mMaxUiWidth;
  public MetricsLogger mMetricsLogger;
  public int mMinSizeOfResizeableTaskDp;
  public MultiWindowPointerEventListener mMultiWindowPointerEventListener;
  public final List mNoAnimationNotifyOnTransitionFinished;
  public final DisplayInfo mNonOverrideDisplayInfo;
  public WindowState mObscuringWindow;
  public final ActivityTaskManagerInternal.SleepTokenAcquirer mOffTokenAcquirer;
  public WindowState mOldFocus;
  public final ArraySet mOpeningApps;
  public TaskDisplayArea mOrientationRequestingTaskDisplayArea;
  public SurfaceControl mOverlayLayer;
  public final Consumer mPerformLayout;
  public final Consumer mPerformLayoutAttached;
  public Point mPhysicalDisplaySize;
  public final PinnedTaskController mPinnedTaskController;
  public final PointerEventDispatcher mPointerEventDispatcher;
  public PopOverController mPopOverController;
  public final RotationCache mPrivacyIndicatorBoundsCache;
  public final DisplayMetrics mRealDisplayMetrics;
  public final RemoteDisplayChangeController mRemoteDisplayChangeController;
  public RemoteInsetsControlTarget mRemoteInsetsControlTarget;
  public final IBinder.DeathRecipient mRemoteInsetsDeath;
  public boolean mRemoved;
  public boolean mRemoving;
  public Set mRestrictedKeepClearAreas;
  public RootWindowContainer mRootWindowContainer;
  public final DisplayRotationReversionController mRotationReversionController;
  public final RotationCache mRoundedCornerCache;
  public boolean mSandboxDisplayApis;
  public final Consumer mScheduleToastTimeout;
  public ScreenRotationAnimation mScreenRotationAnimation;
  public final SurfaceSession mSession;
  public final SparseArray mShellRoots;
  public boolean mSkipAppTransitionAnimation;
  public boolean mSleeping;
  public final Region mSystemGestureExclusion;
  public int mSystemGestureExclusionLimit;
  public final RemoteCallbackList mSystemGestureExclusionListeners;
  public final Region mSystemGestureExclusionUnrestricted;
  public boolean mSystemGestureExclusionWasRestricted;
  public final Rect mSystemGestureFrameLeft;
  public final Rect mSystemGestureFrameRight;
  final TaskTapPointerEventListener mTapDetector;
  public final ArraySet mTapExcludeProvidingWindows;
  public final ArrayList mTapExcludedWindows;
  public final Configuration mTempConfig;
  public final Region mTmpAlwaysOnTopFreeformRegion;
  public final ApplySurfaceChangesTransactionState mTmpApplySurfaceChangesTransactionState;
  public final Configuration mTmpConfiguration;
  public final DisplayMetrics mTmpDisplayMetrics;
  public Point mTmpDisplaySize;
  public WindowState mTmpHoldScreenWindow;
  public boolean mTmpInitial;
  public final Rect mTmpPrevBounds;
  public final Rect mTmpRect;
  public final Rect mTmpRect2;
  public final Region mTmpRegion;
  public final TaskForResizePointSearchResult mTmpTaskForResizePointSearchResult;
  public final LinkedList mTmpUpdateAllDrawn;
  public WindowState mTmpWindow;
  public final HashMap mTokenMap;
  public Region mTouchExcludeRegion;
  public final ArrayList mTransientLaunchOverlayTokens;
  public UdcCutoutPolicy mUdcCutoutPolicy;
  public final UnknownAppVisibilityController mUnknownAppVisibilityController;
  public Set mUnrestrictedKeepClearAreas;
  public Runnable mUpdateAboveInsetsStateRunnable;
  public boolean mUpdateImeRequestedWhileDeferred;
  public boolean mUpdateImeTarget;
  public final Consumer mUpdateWindowsForAnimator;
  public boolean mWaitingForConfig;
  public WallpaperController mWallpaperController;
  public boolean mWallpaperMayChange;
  public final ArrayList mWinAddedSinceNullFocus;
  public final ArrayList mWinRemovedSinceNullFocus;
  public final float mWindowCornerRadius;
  public SurfaceControl mWindowingLayer;
  public int pendingLayoutChanges;

  public class DisplayAnimationPair {
    public int mEnter;
    public int mExit;
  }

  public static boolean alwaysCreateRootTask(int i, int i2) {
    return (i2 == 1 || i2 == 3) && (i == 1 || i == 5 || i == 2 || i == 6);
  }

  public static /* synthetic */ boolean lambda$getTopRootTask$19(Task task) {
    return true;
  }

  public static /* synthetic */ boolean lambda$removeImeSurfaceByTarget$31(
      WindowState windowState, Object obj) {
    return obj == windowState;
  }

  @Override // com.android.server.wm.WindowContainer
  public DisplayContent asDisplayContent() {
    return this;
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
  public boolean fillsParent() {
    return true;
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
  public long getProtoFieldId() {
    return 1146756268035L;
  }

  @Override // com.android.server.wm.WindowContainer
  public int getRelativeDisplayRotation() {
    return 0;
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean isVisible() {
    return true;
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.ConfigurationContainer
  public boolean providesMaxBounds() {
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mRemoteInsetsControlTarget = null;
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$1(WindowState windowState) {
    WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
    ActivityRecord activityRecord = windowState.mActivityRecord;
    if (windowStateAnimator.mDrawState == 3) {
      if ((activityRecord == null || activityRecord.canShowWindows())
          && windowState.performShowLocked()) {
        this.pendingLayoutChanges |= 8;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$2(WindowState windowState) {
    int i = this.mTmpWindow.mOwnerUid;
    WindowManagerService.HandlerC2998H handlerC2998H = this.mWmService.f1749mH;
    if (windowState.mAttrs.type == 2005
        && windowState.mOwnerUid == i
        && !handlerC2998H.hasMessages(52, windowState)) {
      handlerC2998H.sendMessageDelayed(
          handlerC2998H.obtainMessage(52, windowState), windowState.mAttrs.hideTimeoutMilliseconds);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$new$3(WindowState windowState) {
    WindowState windowState2;
    WindowState windowState3;
    ActivityRecord activityRecord = this.mFocusedApp;
    if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_FOCUS,
          -1142279614,
          52,
          (String) null,
          new Object[] {
            String.valueOf(windowState),
            Long.valueOf(windowState.mAttrs.flags),
            Boolean.valueOf(windowState.canReceiveKeys()),
            String.valueOf(windowState.canReceiveKeysReason(false))
          });
    }
    if (CoreRune.SYSFW_APP_SPEG && (this.mDisplayInfo.flags & 32768) != 0) {
      Slog.i("SPEG", "Do not update focused window");
      return false;
    }
    if (!windowState.canReceiveKeys()) {
      return false;
    }
    if (windowState.mIsImWindow
        && windowState.isChildWindow()
        && ((windowState3 = this.mImeLayeringTarget) == null
            || !windowState3.isRequestedVisible(WindowInsets.Type.ime()))) {
      return false;
    }
    if (windowState.mAttrs.type == 2012
        && (windowState2 = this.mImeLayeringTarget) != null
        && !windowState2.isRequestedVisible(WindowInsets.Type.ime())
        && !this.mImeLayeringTarget.isVisibleRequested()) {
      return false;
    }
    ActivityRecord activityRecord2 = windowState.mActivityRecord;
    if (activityRecord == null) {
      if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
            -87705714,
            0,
            (String) null,
            new Object[] {String.valueOf(windowState)});
      }
      this.mTmpWindow = windowState;
      return true;
    }
    if (!activityRecord.windowsAreFocusable()) {
      if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
            1430336882,
            0,
            (String) null,
            new Object[] {String.valueOf(windowState)});
      }
      this.mTmpWindow = windowState;
      return true;
    }
    if (activityRecord2 != null
        && activityRecord2.getLastParentBeforePip() != null
        && activityRecord2 != this.mFocusedApp) {
      return false;
    }
    if (activityRecord2 != null && windowState.mAttrs.type != 3) {
      if (activityRecord.compareTo((WindowContainer) activityRecord2) > 0) {
        if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
          ProtoLogImpl.v(
              ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
              -809771899,
              0,
              (String) null,
              new Object[] {String.valueOf(activityRecord)});
        }
        this.mTmpWindow = null;
        return true;
      }
      TaskFragment taskFragment = activityRecord2.getTaskFragment();
      if (taskFragment != null
          && taskFragment.isEmbedded()
          && activityRecord2.getTask() == activityRecord.getTask()
          && activityRecord2.getTaskFragment() != activityRecord.getTaskFragment()) {
        return false;
      }
    }
    Task task = activityRecord2 != null ? activityRecord2.getTask() : null;
    if (task != null
        && task.inFreeformWindowingMode()
        && ((task.isAlwaysOnTopFreeform()
                || task.isDexMode()
                || (CoreRune.MT_NEW_DEX_TASK_ORDERING && task.isNewDexMode()))
            && activityRecord2.compareTo((WindowContainer) activityRecord) > 0)) {
      if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
            1644213761,
            0,
            (String) null,
            new Object[] {String.valueOf(activityRecord2)});
      }
      this.mTmpWindow = null;
      return false;
    }
    if (task != null && task.inFreeformWindowingMode() && task.isFreeformStashed()) {
      return false;
    }
    if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
          -415865166,
          0,
          (String) null,
          new Object[] {String.valueOf(windowState)});
    }
    this.mTmpWindow = windowState;
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$4(WindowState windowState) {
    if (windowState.mLayoutAttached) {
      return;
    }
    if (windowState.isGoneForLayout() && windowState.mHaveFrame && !windowState.mLayoutNeeded) {
      return;
    }
    if (this.mTmpInitial) {
      windowState.resetContentChanged();
    }
    windowState.mSurfacePlacementNeeded = true;
    windowState.mLayoutNeeded = false;
    boolean isLaidOut = true ^ windowState.isLaidOut();
    getDisplayPolicy().layoutWindowLw(windowState, null, this.mDisplayFrames);
    windowState.mLayoutSeq = this.mLayoutSeq;
    if (isLaidOut) {
      if (!windowState.getFrame().isEmpty()) {
        windowState.updateLastFrames();
      }
      windowState.onResizeHandled();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$5(WindowState windowState) {
    if (windowState.mLayoutAttached) {
      if ((windowState.mViewVisibility == 8 || !windowState.mRelayoutCalled)
          && windowState.mHaveFrame
          && !windowState.mLayoutNeeded) {
        return;
      }
      if (this.mTmpInitial) {
        windowState.resetContentChanged();
      }
      windowState.mSurfacePlacementNeeded = true;
      windowState.mLayoutNeeded = false;
      getDisplayPolicy()
          .layoutWindowLw(windowState, windowState.getParentWindow(), this.mDisplayFrames);
      windowState.mLayoutSeq = this.mLayoutSeq;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$new$6(WindowState windowState) {
    return windowState.canBeImeTarget();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$7(WindowState windowState) {
    getDisplayPolicy()
        .applyPostLayoutPolicyLw(
            windowState,
            windowState.mAttrs,
            windowState.getParentWindow(),
            this.mImeLayeringTarget);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$8(WindowState windowState) {
    WindowManagerService windowManagerService = this.mWmService;
    WindowSurfacePlacer windowSurfacePlacer = windowManagerService.mWindowPlacerLocked;
    boolean z = windowState.mObscured;
    boolean z2 = this.mTmpApplySurfaceChangesTransactionState.obscured;
    boolean z3 = z != z2;
    RootWindowContainer rootWindowContainer = windowManagerService.mRoot;
    windowState.mObscured = z2;
    if (!z2) {
      boolean isDisplayed = windowState.isDisplayed();
      if (isDisplayed && windowState.isObscuringDisplay()) {
        this.mObscuringWindow = windowState;
        this.mTmpApplySurfaceChangesTransactionState.obscured = true;
      }
      ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState =
          this.mTmpApplySurfaceChangesTransactionState;
      boolean handleNotObscuredLocked =
          rootWindowContainer.handleNotObscuredLocked(
              windowState,
              applySurfaceChangesTransactionState.obscured,
              applySurfaceChangesTransactionState.syswin);
      if (!this.mTmpApplySurfaceChangesTransactionState.displayHasContent
          && !getDisplayPolicy().isWindowExcludedFromContent(windowState)) {
        ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState2 =
            this.mTmpApplySurfaceChangesTransactionState;
        applySurfaceChangesTransactionState2.displayHasContent =
            handleNotObscuredLocked | applySurfaceChangesTransactionState2.displayHasContent;
      }
      if (windowState.mHasSurface && isDisplayed) {
        if ((windowState.mAttrs.flags & 128) != 0) {
          this.mTmpHoldScreenWindow = windowState;
          if (CoreRune.SYSFW_APP_SPEG && (this.mDisplayInfo.flags & 32768) != 0) {
            Slog.i("SPEG", "Ignore FLAG_KEEP_SCREEN_ON for " + windowState.mAttrs.packageName);
            this.mTmpHoldScreenWindow = null;
            WindowManager.LayoutParams layoutParams = windowState.mAttrs;
            layoutParams.flags = layoutParams.flags & (-129);
          }
        } else if (windowState == this.mLastWakeLockHoldingWindow
            && ProtoLogCache.WM_DEBUG_KEEP_SCREEN_ON_enabled) {
          ProtoLogImpl.d(
              ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON,
              191486492,
              0,
              (String) null,
              new Object[] {String.valueOf(windowState), String.valueOf(Debug.getCallers(10))});
        }
        int i = windowState.mAttrs.type;
        if (i == 2008 || i == 2010 || (i == 2040 && this.mWmService.mPolicy.isKeyguardShowing())) {
          this.mTmpApplySurfaceChangesTransactionState.syswin = true;
        }
        ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState3 =
            this.mTmpApplySurfaceChangesTransactionState;
        if (applySurfaceChangesTransactionState3.preferredRefreshRate
            == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          float f = windowState.mAttrs.preferredRefreshRate;
          if (f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
            applySurfaceChangesTransactionState3.preferredRefreshRate = f;
          }
        }
        applySurfaceChangesTransactionState3.preferMinimalPostProcessing =
            applySurfaceChangesTransactionState3.preferMinimalPostProcessing
                | windowState.mAttrs.preferMinimalPostProcessing;
        applySurfaceChangesTransactionState3.disableHdrConversion |= !r7.isHdrConversionEnabled();
        int preferredModeId =
            getDisplayPolicy().getRefreshRatePolicy().getPreferredModeId(windowState);
        if (windowState.getWindowingMode() != 2) {
          ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState4 =
              this.mTmpApplySurfaceChangesTransactionState;
          if (applySurfaceChangesTransactionState4.preferredModeId == 0 && preferredModeId != 0) {
            applySurfaceChangesTransactionState4.preferredModeId = preferredModeId;
            if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
              getDisplayPolicy()
                  .getRefreshRatePolicy()
                  .updateLog(windowState, preferredModeId, -1.0f, 0);
            }
          }
        }
        float preferredMinRefreshRate =
            getDisplayPolicy().getRefreshRatePolicy().getPreferredMinRefreshRate(windowState);
        ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState5 =
            this.mTmpApplySurfaceChangesTransactionState;
        if (applySurfaceChangesTransactionState5.preferredMinRefreshRate
                == DisplayPowerController2.RATE_FROM_DOZE_TO_ON
            && preferredMinRefreshRate != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          applySurfaceChangesTransactionState5.preferredMinRefreshRate = preferredMinRefreshRate;
          if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
            getDisplayPolicy()
                .getRefreshRatePolicy()
                .updateLog(windowState, -1, preferredMinRefreshRate, 1);
          }
        }
        float preferredMaxRefreshRate =
            getDisplayPolicy().getRefreshRatePolicy().getPreferredMaxRefreshRate(windowState);
        ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState6 =
            this.mTmpApplySurfaceChangesTransactionState;
        if (applySurfaceChangesTransactionState6.preferredMaxRefreshRate
                == DisplayPowerController2.RATE_FROM_DOZE_TO_ON
            && preferredMaxRefreshRate != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
          applySurfaceChangesTransactionState6.preferredMaxRefreshRate = preferredMaxRefreshRate;
          if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
            getDisplayPolicy()
                .getRefreshRatePolicy()
                .updateLog(windowState, -1, preferredMaxRefreshRate, 2);
          }
        }
      }
    }
    if (z3 && windowState.isVisible() && this.mWallpaperController.isWallpaperTarget(windowState)) {
      this.mWallpaperController.updateWallpaperVisibility();
    }
    windowState.handleWindowMovedIfNeeded();
    WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
    windowState.resetContentChanged();
    if (windowState.mHasSurface) {
      boolean commitFinishDrawingLocked = windowStateAnimator.commitFinishDrawingLocked();
      if (this.isDefaultDisplay && commitFinishDrawingLocked) {
        if (windowState.hasWallpaper()) {
          if (ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
            ProtoLogImpl.v(
                ProtoLogGroup.WM_DEBUG_WALLPAPER,
                422634333,
                0,
                (String) null,
                new Object[] {String.valueOf(windowState)});
          }
          this.mWallpaperMayChange = true;
          this.pendingLayoutChanges |= 4;
        }
        if ((windowState.mAttrs.flags & 128) != 0) {
          this.pendingLayoutChanges |= 1;
        }
      }
    }
    ActivityRecord activityRecord = windowState.mActivityRecord;
    if (activityRecord != null && activityRecord.isVisibleRequested()) {
      activityRecord.updateLetterboxSurface(windowState);
      if (activityRecord.updateDrawnWindowStates(windowState)
          && !this.mTmpUpdateAllDrawn.contains(activityRecord)) {
        this.mTmpUpdateAllDrawn.add(activityRecord);
      }
    }
    windowState.updateResizingWindowIfNeeded();
    if (activityRecord != null || windowState.mIsImWindow) {
      windowState.updateLetterboxDirectionIfNeeded();
    }
  }

  public DisplayContent(
      Display display,
      RootWindowContainer rootWindowContainer,
      DeviceStateController deviceStateController) {
    super(rootWindowContainer.mWindowManager, "DisplayContent", 0);
    DisplayContent defaultDisplay;
    CoverPolicy coverPolicy;
    this.mTmpAlwaysOnTopFreeformRegion = new Region();
    this.mTmpPrevBounds = new Rect();
    this.mUpdateAboveInsetsStateRunnable =
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            DisplayContent.this.updateAboveInsetsState();
          }
        };
    this.mMinSizeOfResizeableTaskDp = -1;
    this.mImeWindowsContainer = new ImeContainer(this.mWmService);
    this.mMaxUiWidth = 0;
    this.mSkipAppTransitionAnimation = false;
    this.mOpeningApps = new ArraySet();
    this.mClosingApps = new ArraySet();
    this.mChangingContainers = new ArraySet();
    this.mClosingChangingContainers = new ArrayMap();
    this.mNoAnimationNotifyOnTransitionFinished = new ArrayList();
    this.mTokenMap = new HashMap();
    this.mInitialDisplayWidth = 0;
    this.mInitialDisplayHeight = 0;
    this.mInitialPhysicalXDpi = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    this.mInitialPhysicalYDpi = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    this.mInitialDisplayDensity = 0;
    this.mDisplayCutoutCache =
        new RotationCache(
            new RotationCache
                .RotationDependentComputation() { // from class:
                                                  // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda7
              @Override // com.android.server.wm.utils.RotationCache.RotationDependentComputation
              public final Object compute(Object obj, int i) {
                WmDisplayCutout calculateDisplayCutoutForRotationUncached;
                calculateDisplayCutoutForRotationUncached =
                    DisplayContent.this.calculateDisplayCutoutForRotationUncached(
                        (DisplayCutout) obj, i);
                return calculateDisplayCutoutForRotationUncached;
              }
            });
    this.mRoundedCornerCache =
        new RotationCache(
            new RotationCache
                .RotationDependentComputation() { // from class:
                                                  // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda9
              @Override // com.android.server.wm.utils.RotationCache.RotationDependentComputation
              public final Object compute(Object obj, int i) {
                RoundedCorners calculateRoundedCornersForRotationUncached;
                calculateRoundedCornersForRotationUncached =
                    DisplayContent.this.calculateRoundedCornersForRotationUncached(
                        (RoundedCorners) obj, i);
                return calculateRoundedCornersForRotationUncached;
              }
            });
    this.mCurrentPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
    this.mPrivacyIndicatorBoundsCache =
        new RotationCache(
            new RotationCache
                .RotationDependentComputation() { // from class:
                                                  // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda10
              @Override // com.android.server.wm.utils.RotationCache.RotationDependentComputation
              public final Object compute(Object obj, int i) {
                PrivacyIndicatorBounds calculatePrivacyIndicatorBoundsForRotationUncached;
                calculatePrivacyIndicatorBoundsForRotationUncached =
                    DisplayContent.this.calculatePrivacyIndicatorBoundsForRotationUncached(
                        (PrivacyIndicatorBounds) obj, i);
                return calculatePrivacyIndicatorBoundsForRotationUncached;
              }
            });
    this.mDisplayShapeCache =
        new RotationCache(
            new RotationCache
                .RotationDependentComputation() { // from class:
                                                  // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda11
              @Override // com.android.server.wm.utils.RotationCache.RotationDependentComputation
              public final Object compute(Object obj, int i) {
                DisplayShape calculateDisplayShapeForRotationUncached;
                calculateDisplayShapeForRotationUncached =
                    DisplayContent.this.calculateDisplayShapeForRotationUncached(
                        (DisplayShape) obj, i);
                return calculateDisplayShapeForRotationUncached;
              }
            });
    this.mBaseDisplayWidth = 0;
    this.mBaseDisplayHeight = 0;
    this.mIsSizeForced = false;
    this.mSandboxDisplayApis = true;
    this.mBaseDisplayDensity = 0;
    this.mIsDensityForced = false;
    this.mBaseDisplayPhysicalXDpi = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    this.mBaseDisplayPhysicalYDpi = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    DisplayInfo displayInfo = new DisplayInfo();
    this.mDisplayInfo = displayInfo;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    this.mDisplayMetrics = displayMetrics;
    this.mSystemGestureExclusionListeners = new RemoteCallbackList();
    this.mSystemGestureExclusion = new Region();
    this.mSystemGestureExclusionWasRestricted = false;
    this.mSystemGestureExclusionUnrestricted = new Region();
    this.mSystemGestureFrameLeft = new Rect();
    this.mSystemGestureFrameRight = new Rect();
    this.mRestrictedKeepClearAreas = new ArraySet();
    this.mUnrestrictedKeepClearAreas = new ArraySet();
    this.mRealDisplayMetrics = new DisplayMetrics();
    this.mTmpDisplayMetrics = new DisplayMetrics();
    this.mCompatDisplayMetrics = new DisplayMetrics();
    this.mLastWallpaperVisible = false;
    this.mTouchExcludeRegion = new Region();
    this.mTmpRect = new Rect();
    this.mTmpRect2 = new Rect();
    this.mTmpRegion = new Region();
    this.mTmpConfiguration = new Configuration();
    this.mTapExcludedWindows = new ArrayList();
    this.mTapExcludeProvidingWindows = new ArraySet();
    this.mTmpUpdateAllDrawn = new LinkedList();
    this.mTmpTaskForResizePointSearchResult = new TaskForResizePointSearchResult();
    this.mTmpApplySurfaceChangesTransactionState = new ApplySurfaceChangesTransactionState();
    this.mDisplayReady = false;
    this.mWallpaperMayChange = false;
    this.mSession = new SurfaceSession();
    this.mCurrentFocus = null;
    this.mOldFocus = null;
    this.mFocusedApp = null;
    this.mOrientationRequestingTaskDisplayArea = null;
    FixedRotationTransitionListener fixedRotationTransitionListener =
        new FixedRotationTransitionListener();
    this.mFixedRotationTransitionListener = fixedRotationTransitionListener;
    this.mWinAddedSinceNullFocus = new ArrayList();
    this.mWinRemovedSinceNullFocus = new ArrayList();
    this.mLayoutSeq = 0;
    this.mShellRoots = new SparseArray();
    this.mRemoteInsetsControlTarget = null;
    this.mRemoteInsetsDeath =
        new IBinder
            .DeathRecipient() { // from class:
                                // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda12
          @Override // android.os.IBinder.DeathRecipient
          public final void binderDied() {
            DisplayContent.this.lambda$new$0();
          }
        };
    this.mDisplayAccessUIDs = new IntArray();
    this.mAllSleepTokens = new ArrayList();
    this.mActiveSizeCompatActivities = new ArraySet();
    this.mTmpDisplaySize = new Point();
    this.mTempConfig = new Configuration();
    this.mInEnsureActivitiesVisible = false;
    this.mFadeInOutAnimationNeeded = false;
    this.mForcedHideCutout = -1;
    this.mUpdateWindowsForAnimator =
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda13
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$new$1((WindowState) obj);
          }
        };
    this.mScheduleToastTimeout =
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda14
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$new$2((WindowState) obj);
          }
        };
    this.mFindFocusedWindow =
        new ToBooleanFunction() { // from class:
                                  // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda15
          public final boolean apply(Object obj) {
            boolean lambda$new$3;
            lambda$new$3 = DisplayContent.this.lambda$new$3((WindowState) obj);
            return lambda$new$3;
          }
        };
    this.mPerformLayout =
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda16
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$new$4((WindowState) obj);
          }
        };
    this.mPerformLayoutAttached =
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda3
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$new$5((WindowState) obj);
          }
        };
    this.mComputeImeTargetPredicate =
        new Predicate() { // from class:
                          // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda4
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$new$6;
            lambda$new$6 = DisplayContent.this.lambda$new$6((WindowState) obj);
            return lambda$new$6;
          }
        };
    this.mApplyPostLayoutPolicy =
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda5
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$new$7((WindowState) obj);
          }
        };
    this.mApplySurfaceChangesTransaction =
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda6
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$new$8((WindowState) obj);
          }
        };
    this.mApplicationPolicy = null;
    this.mNonOverrideDisplayInfo = new DisplayInfo();
    this.mTransientLaunchOverlayTokens = new ArrayList();
    if (this.mWmService.mRoot.getDisplayContent(display.getDisplayId()) != null) {
      throw new IllegalArgumentException(
          "Display with ID="
              + display.getDisplayId()
              + " already exists="
              + this.mWmService.mRoot.getDisplayContent(display.getDisplayId())
              + " new="
              + display);
    }
    this.mRootWindowContainer = rootWindowContainer;
    this.mAtmService = this.mWmService.mAtmService;
    this.mDisplay = display;
    int displayId = display.getDisplayId();
    this.mDisplayId = displayId;
    this.mCurrentUniqueDisplayId = display.getUniqueId();
    this.mOffTokenAcquirer = this.mRootWindowContainer.mDisplayOffTokenAcquirer;
    WallpaperController wallpaperController = new WallpaperController(this.mWmService, this);
    this.mWallpaperController = wallpaperController;
    wallpaperController.resetLargestDisplay(display);
    display.getDisplayInfo(displayInfo);
    display.getMetrics(displayMetrics);
    this.mSystemGestureExclusionLimit =
        (this.mWmService.mConstants.mSystemGestureExclusionLimitDp * displayMetrics.densityDpi)
            / 160;
    this.isDefaultDisplay = displayId == 0;
    InsetsStateController insetsStateController = new InsetsStateController(this);
    this.mInsetsStateController = insetsStateController;
    if (this.isDefaultDisplay
        && !TextUtils.isEmpty(
            this.mWmService.mContext.getString(R.string.duration_hours_relative))) {
      this.mUdcCutoutPolicy =
          new UdcCutoutPolicy(
              this,
              new RotationCache(
                  new RotationCache
                      .RotationDependentComputation() { // from class:
                                                        // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda7
                    @Override // com.android.server.wm.utils.RotationCache.RotationDependentComputation
                    public final Object compute(Object obj, int i) {
                      WmDisplayCutout calculateDisplayCutoutForRotationUncached;
                      calculateDisplayCutoutForRotationUncached =
                          DisplayContent.this.calculateDisplayCutoutForRotationUncached(
                              (DisplayCutout) obj, i);
                      return calculateDisplayCutoutForRotationUncached;
                    }
                  }));
    }
    initializeDisplayBaseInfo();
    this.mDisplayFrames =
        new DisplayFrames(
            insetsStateController.getRawInsetsState(),
            displayInfo,
            calculateDisplayCutoutForRotation(displayInfo.rotation),
            calculateRoundedCornersForRotation(displayInfo.rotation),
            calculatePrivacyIndicatorBoundsForRotation(displayInfo.rotation),
            calculateDisplayShapeForRotation(displayInfo.rotation),
            this.mUdcCutoutPolicy);
    if (displayId == 2) {
      this.mHoldScreenWakeLock =
          this.mWmService.mPowerManager.newWakeLock(
              536870922, "WindowManager(DexScreen)", displayId);
    } else {
      this.mHoldScreenWakeLock =
          this.mWmService.mPowerManager.newWakeLock(
              536870922, "WindowManager/displayId:" + displayId, displayId);
    }
    this.mHoldScreenWakeLock.setReferenceCounted(false);
    WindowManagerService windowManagerService = this.mWmService;
    AppTransition appTransition =
        new AppTransition(windowManagerService.mContext, windowManagerService, this);
    this.mAppTransition = appTransition;
    appTransition.registerListenerLocked(this.mWmService.mActivityManagerAppTransitionNotifier);
    appTransition.registerListenerLocked(fixedRotationTransitionListener);
    this.mAppTransitionController = new AppTransitionController(this.mWmService, this);
    this.mTransitionController.registerLegacyListener(fixedRotationTransitionListener);
    this.mUnknownAppVisibilityController =
        new UnknownAppVisibilityController(this.mWmService, this);
    this.mDisplaySwitchTransitionLauncher =
        new PhysicalDisplaySwitchTransitionLauncher(this, this.mTransitionController);
    this.mRemoteDisplayChangeController = new RemoteDisplayChangeController(this);
    this.mPointerEventDispatcher =
        new PointerEventDispatcher(
            this.mWmService.mInputManager.monitorInput(
                "PointerEventDispatcher" + displayId, displayId));
    TaskTapPointerEventListener taskTapPointerEventListener =
        new TaskTapPointerEventListener(this.mWmService, this);
    this.mTapDetector = taskTapPointerEventListener;
    registerPointerEventListener(taskTapPointerEventListener);
    registerPointerEventListener(this.mWmService.mMousePositionTracker);
    if (this.mWmService.mAtmService.getRecentTasks() != null) {
      registerPointerEventListener(this.mWmService.mAtmService.getRecentTasks().getInputListener());
    }
    this.mDeviceStateController = deviceStateController;
    DisplayPolicy displayPolicy = new DisplayPolicy(this.mWmService, this);
    this.mDisplayPolicy = displayPolicy;
    this.mDisplayRotation =
        new DisplayRotation(
            this.mWmService,
            this,
            displayInfo.address,
            deviceStateController,
            rootWindowContainer.getDisplayRotationCoordinator());
    Consumer consumer =
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda8
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$new$9((DeviceStateController.DeviceState) obj);
          }
        };
    this.mDeviceStateConsumer = consumer;
    deviceStateController.registerDeviceStateCallback(
        consumer, new HandlerExecutor(this.mWmService.f1749mH));
    this.mCloseToSquareMaxAspectRatio =
        this.mWmService.mContext.getResources().getFloat(R.dimen.circular_display_mask_thickness);
    if (this.isDefaultDisplay) {
      this.mWmService.mPolicy.setDefaultDisplay(this);
    } else if (displayId == 2) {
      this.mWmService.mPolicy.setDexDisplay(this);
    } else if (displayId == 4) {
      this.mWmService.mPolicy.setCoverViewDisplay(this);
    }
    if (CoreRune.BAIDU_CARLIFE && isCarLifeDisplay()) {
      this.mWmService.mExt.mPolicyExt.setCarLifeDisplay(this);
    }
    if (this.mWmService.mDisplayReady) {
      displayPolicy.onConfigurationChanged();
    }
    if (this.mWmService.mSystemReady) {
      displayPolicy.systemReady();
    }
    this.mWindowCornerRadius = displayPolicy.getWindowCornerRadius();
    this.mPinnedTaskController = new PinnedTaskController(this.mWmService, this);
    SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
    configureSurfaces(pendingTransaction);
    pendingTransaction.apply();
    onDisplayChanged(this);
    updateDisplayAreaOrganizers();
    this.mDisplayRotationCompatPolicy =
        this.mWmService.mLetterboxConfiguration.isCameraCompatTreatmentEnabled(false)
            ? new DisplayRotationCompatPolicy(this)
            : null;
    this.mRotationReversionController = new DisplayRotationReversionController(this);
    this.mInputMonitor = new InputMonitor(this.mWmService, this);
    this.mInsetsPolicy = new InsetsPolicy(insetsStateController, this);
    this.mMinSizeOfResizeableTaskDp = getMinimalTaskSizeDp();
    if (CoreRune.FW_FLIP_RESTORE_DEFAULT_DISPLAY_DENSITY_IN_BOOT
        && !CoreRune.SAFE_DEBUG
        && displayId == 1) {
      this.mWmService.mDisplayWindowSettings.clearForcedDensity(this);
    }
    setWindowingMode(1);
    this.mWmService.mDisplayWindowSettings.applySettingsToDisplayLocked(this);
    if (this.isDefaultDisplay) {
      registerPointerEventListener(
          new SystemPerformancePointerEventListener(this.mWmService.mContext));
    }
    boolean z =
        this.mWmService
            .mContext
            .getResources()
            .getBoolean(R.bool.config_cecTvWakeOnOneTouchPlayDisabled_default);
    this.mInTouchMode = z;
    this.mWmService.mInputManager.setInTouchMode(
        z, WindowManagerService.MY_PID, WindowManagerService.MY_UID, true, displayId);
    if (displayId == 4
        && (defaultDisplay = this.mRootWindowContainer.getDefaultDisplay()) != null
        && (coverPolicy = defaultDisplay.mDisplayPolicy.mExt.mCoverPolicy) != null) {
      coverPolicy.addViewCoverDisplay(this);
    }
    if (this.isDefaultDisplay) {
      MultiWindowPointerEventListener multiWindowPointerEventListener =
          new MultiWindowPointerEventListener(this.mWmService, this);
      this.mMultiWindowPointerEventListener = multiWindowPointerEventListener;
      registerPointerEventListener(multiWindowPointerEventListener);
    }
    this.mPopOverController = new PopOverController(this);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$9(DeviceStateController.DeviceState deviceState) {
    this.mDisplaySwitchTransitionLauncher.foldStateChanged(deviceState);
    this.mDisplayRotation.foldStateChanged(deviceState);
  }

  public IApplicationPolicy getApplicationPolicy() {
    IApplicationPolicy iApplicationPolicy = this.mApplicationPolicy;
    if (iApplicationPolicy == null) {
      synchronized (this) {
        iApplicationPolicy = this.mApplicationPolicy;
        if (iApplicationPolicy == null) {
          iApplicationPolicy =
              IApplicationPolicy.Stub.asInterface(ServiceManager.getService("application_policy"));
          this.mApplicationPolicy = iApplicationPolicy;
        }
      }
    }
    return iApplicationPolicy;
  }

  public final void checkFocusMonitoringPolicy(ActivityRecord activityRecord, String str) {
    if (activityRecord == null) {
      return;
    }
    String str2 = activityRecord.packageName;
    IApplicationPolicy applicationPolicy = getApplicationPolicy();
    if (applicationPolicy != null) {
      try {
        int i = activityRecord.mUserId;
        if (applicationPolicy.isApplicationFocusMonitoredAsUser(str2, i)) {
          sendApplicationFocusMonitoringIntent(
              activityRecord.mActivityComponent.flattenToString(), str, i, isDexMode());
        }
      } catch (RemoteException unused) {
      }
    }
  }

  public final void sendApplicationFocusMonitoringIntent(
      String str, String str2, int i, boolean z) {
    final Intent intent =
        new Intent("com.samsung.android.knox.intent.action.APPLICATION_FOCUS_CHANGE");
    intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_FOCUS_COMPONENT_NAME", str);
    intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_FOCUS_STATUS", str2);
    intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", i);
    intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_FOCUS_DEX_MODE", z);
    this.mWmService.f1749mH.post(
        new Runnable() { // from class: com.android.server.wm.DisplayContent.2
          @Override // java.lang.Runnable
          public void run() {
            DisplayContent.this.mWmService.mContext.sendBroadcastAsUser(
                intent, UserHandle.ALL, "com.samsung.android.knox.permission.KNOX_APP_MGMT");
          }
        });
  }

  public final void beginHoldScreenUpdate() {
    this.mTmpHoldScreenWindow = null;
    this.mObscuringWindow = null;
  }

  public final void finishHoldScreenUpdate() {
    WindowState windowState = this.mTmpHoldScreenWindow;
    boolean z = windowState != null;
    if (z && windowState != this.mHoldScreenWindow) {
      PowerManager.WakeLock wakeLock = this.mHoldScreenWakeLock;
      Session session = this.mTmpHoldScreenWindow.mSession;
      wakeLock.setWorkSource(new WorkSource(session.mUid, session.mPackageName));
    }
    this.mHoldScreenWindow = this.mTmpHoldScreenWindow;
    this.mTmpHoldScreenWindow = null;
    if (z != this.mHoldScreenWakeLock.isHeld()) {
      if (z) {
        if (ProtoLogCache.WM_DEBUG_KEEP_SCREEN_ON_enabled) {
          ProtoLogImpl.d(
              ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON,
              -384639879,
              0,
              (String) null,
              new Object[] {String.valueOf(this.mHoldScreenWindow)});
        }
        this.mLastWakeLockHoldingWindow = this.mHoldScreenWindow;
        this.mLastWakeLockObscuringWindow = null;
        this.mHoldScreenWakeLock.acquire();
        return;
      }
      if (ProtoLogCache.WM_DEBUG_KEEP_SCREEN_ON_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON,
            782864973,
            0,
            (String) null,
            new Object[] {String.valueOf(this.mObscuringWindow)});
      }
      this.mLastWakeLockHoldingWindow = null;
      this.mLastWakeLockObscuringWindow = this.mObscuringWindow;
      this.mHoldScreenWakeLock.release();
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public void migrateToNewSurfaceControl(SurfaceControl.Transaction transaction) {
    transaction.remove(this.mSurfaceControl);
    this.mLastSurfacePosition.set(0, 0);
    this.mLastDeltaRotation = 0;
    configureSurfaces(transaction);
    for (int i = 0; i < this.mChildren.size(); i++) {
      SurfaceControl surfaceControl = ((DisplayArea) this.mChildren.get(i)).getSurfaceControl();
      if (surfaceControl != null) {
        transaction.reparent(surfaceControl, this.mSurfaceControl);
      }
    }
    scheduleAnimation();
  }

  public final void configureSurfaces(SurfaceControl.Transaction transaction) {
    SurfaceControl.Builder callsite =
        this.mWmService
            .makeSurfaceBuilder(this.mSession)
            .setOpaque(true)
            .setContainerLayer()
            .setCallsite("DisplayContent");
    this.mSurfaceControl = callsite.setName(getName()).setContainerLayer().build();
    if (this.mDisplayAreaPolicy == null) {
      this.mDisplayAreaPolicy =
          this.mWmService
              .getDisplayAreaPolicyProvider()
              .instantiate(this.mWmService, this, this, this.mImeWindowsContainer);
    }
    List displayAreas = this.mDisplayAreaPolicy.getDisplayAreas(4);
    DisplayArea displayArea = displayAreas.size() == 1 ? (DisplayArea) displayAreas.get(0) : null;
    if (displayArea != null && displayArea.getParent() == this) {
      SurfaceControl surfaceControl = displayArea.mSurfaceControl;
      this.mWindowingLayer = surfaceControl;
      transaction.reparent(surfaceControl, this.mSurfaceControl);
    } else {
      this.mWindowingLayer = this.mSurfaceControl;
      SurfaceControl build = callsite.setName("RootWrapper").build();
      this.mSurfaceControl = build;
      transaction.reparent(this.mWindowingLayer, build).show(this.mWindowingLayer);
    }
    SurfaceControl surfaceControl2 = this.mOverlayLayer;
    if (surfaceControl2 == null) {
      this.mOverlayLayer =
          callsite.setName("Display Overlays").setParent(this.mSurfaceControl).build();
    } else {
      transaction.reparent(surfaceControl2, this.mSurfaceControl);
    }
    SurfaceControl surfaceControl3 = this.mInputOverlayLayer;
    if (surfaceControl3 == null) {
      this.mInputOverlayLayer =
          callsite.setName("Input Overlays").setParent(this.mSurfaceControl).build();
    } else {
      transaction.reparent(surfaceControl3, this.mSurfaceControl);
    }
    SurfaceControl surfaceControl4 = this.mA11yOverlayLayer;
    if (surfaceControl4 == null) {
      this.mA11yOverlayLayer =
          callsite.setName("Accessibility Overlays").setParent(this.mSurfaceControl).build();
    } else {
      transaction.reparent(surfaceControl4, this.mSurfaceControl);
    }
    transaction
        .setLayer(this.mSurfaceControl, 0)
        .setLayerStack(this.mSurfaceControl, this.mDisplayId)
        .show(this.mSurfaceControl)
        .setLayer(this.mOverlayLayer, Integer.MAX_VALUE)
        .show(this.mOverlayLayer)
        .setLayer(this.mInputOverlayLayer, 2147483646)
        .show(this.mInputOverlayLayer)
        .setLayer(this.mA11yOverlayLayer, 2147483645)
        .show(this.mA11yOverlayLayer);
    this.mWmService.mInputManager.configureGestureMonitorSurfaces(
        this.mDisplayId, this.mSurfaceControl);
  }

  public DisplayRotationReversionController getRotationReversionController() {
    return this.mRotationReversionController;
  }

  public boolean isReady() {
    return this.mWmService.mDisplayReady && this.mDisplayReady;
  }

  public boolean setInTouchMode(boolean z) {
    if (this.mInTouchMode == z) {
      return false;
    }
    this.mInTouchMode = z;
    return true;
  }

  public boolean isInTouchMode() {
    return this.mInTouchMode;
  }

  public int getDisplayId() {
    return this.mDisplayId;
  }

  public float getWindowCornerRadius() {
    return this.mWindowCornerRadius;
  }

  public WindowToken getWindowToken(IBinder iBinder) {
    return (WindowToken) this.mTokenMap.get(iBinder);
  }

  public ActivityRecord getActivityRecord(IBinder iBinder) {
    WindowToken windowToken = getWindowToken(iBinder);
    if (windowToken == null) {
      return null;
    }
    return windowToken.asActivityRecord();
  }

  public void addWindowToken(IBinder iBinder, WindowToken windowToken) {
    DisplayContent windowTokenDisplay = this.mWmService.mRoot.getWindowTokenDisplay(windowToken);
    if (windowTokenDisplay != null) {
      throw new IllegalArgumentException(
          "Can't map token="
              + windowToken
              + " to display="
              + getName()
              + " already mapped to display="
              + windowTokenDisplay
              + " tokens="
              + windowTokenDisplay.mTokenMap);
    }
    if (iBinder == null) {
      throw new IllegalArgumentException(
          "Can't map token=" + windowToken + " to display=" + getName() + " binder is null");
    }
    if (windowToken == null) {
      throw new IllegalArgumentException(
          "Can't map null token to display=" + getName() + " binder=" + iBinder);
    }
    this.mTokenMap.put(iBinder, windowToken);
    if (windowToken.asActivityRecord() == null) {
      windowToken.mDisplayContent = this;
      findAreaForToken(windowToken).asTokens().addChild(windowToken);
    }
  }

  public WindowToken removeWindowToken(IBinder iBinder, boolean z) {
    WindowToken windowToken = (WindowToken) this.mTokenMap.remove(iBinder);
    if (windowToken != null && windowToken.asActivityRecord() == null) {
      windowToken.setExiting(z);
    }
    return windowToken;
  }

  public SurfaceControl addShellRoot(IWindow iWindow, int i) {
    ShellRoot shellRoot = (ShellRoot) this.mShellRoots.get(i);
    if (shellRoot != null) {
      if (shellRoot.getClient() == iWindow) {
        return shellRoot.getSurfaceControl();
      }
      shellRoot.clear();
      this.mShellRoots.remove(i);
    }
    ShellRoot shellRoot2 = new ShellRoot(iWindow, this, i);
    SurfaceControl surfaceControl = shellRoot2.getSurfaceControl();
    if (surfaceControl == null) {
      shellRoot2.clear();
      return null;
    }
    this.mShellRoots.put(i, shellRoot2);
    return new SurfaceControl(surfaceControl, "DisplayContent.addShellRoot");
  }

  public void removeShellRoot(int i) {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        ShellRoot shellRoot = (ShellRoot) this.mShellRoots.get(i);
        if (shellRoot == null) {
          WindowManagerService.resetPriorityAfterLockedSection();
          return;
        }
        shellRoot.clear();
        this.mShellRoots.remove(i);
        WindowManagerService.resetPriorityAfterLockedSection();
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
  }

  public void setRemoteInsetsController(
      IDisplayWindowInsetsController iDisplayWindowInsetsController) {
    RemoteInsetsControlTarget remoteInsetsControlTarget = this.mRemoteInsetsControlTarget;
    if (remoteInsetsControlTarget != null) {
      remoteInsetsControlTarget
          .mRemoteInsetsController
          .asBinder()
          .unlinkToDeath(this.mRemoteInsetsDeath, 0);
      this.mRemoteInsetsControlTarget = null;
    }
    if (iDisplayWindowInsetsController != null) {
      try {
        iDisplayWindowInsetsController.asBinder().linkToDeath(this.mRemoteInsetsDeath, 0);
        this.mRemoteInsetsControlTarget =
            new RemoteInsetsControlTarget(iDisplayWindowInsetsController);
      } catch (RemoteException unused) {
      }
    }
  }

  public void reParentWindowToken(WindowToken windowToken) {
    DisplayContent displayContent = windowToken.getDisplayContent();
    if (displayContent == this) {
      return;
    }
    if (displayContent != null
        && displayContent.mTokenMap.remove(windowToken.token) != null
        && windowToken.asActivityRecord() == null) {
      windowToken.getParent().removeChild(windowToken);
    }
    addWindowToken(windowToken.token, windowToken);
    if (this.mWmService.mAccessibilityController.hasCallbacks()) {
      this.mWmService.mAccessibilityController.onSomeWindowResizedOrMoved(
          displayContent != null ? displayContent.getDisplayId() : -1, getDisplayId());
    }
  }

  public void removeAppToken(IBinder iBinder) {
    WindowToken removeWindowToken = removeWindowToken(iBinder, true);
    if (removeWindowToken == null) {
      Slog.w(
          StartingSurfaceController.TAG,
          "removeAppToken: Attempted to remove non-existing token: " + iBinder);
      return;
    }
    ActivityRecord asActivityRecord = removeWindowToken.asActivityRecord();
    if (asActivityRecord == null) {
      Slog.w(
          StartingSurfaceController.TAG,
          "Attempted to remove non-App token: " + iBinder + " token=" + removeWindowToken);
      return;
    }
    asActivityRecord.onRemovedFromDisplay();
    if (asActivityRecord == this.mFixedRotationLaunchingApp) {
      asActivityRecord.finishFixedRotationTransform();
      setFixedRotationLaunchingAppUnchecked(null);
    }
  }

  @Override // com.android.server.policy.WindowManagerPolicy.DisplayContentInfo
  public Display getDisplay() {
    return this.mDisplay;
  }

  public DisplayInfo getDisplayInfo() {
    return this.mDisplayInfo;
  }

  public DisplayMetrics getDisplayMetrics() {
    return this.mDisplayMetrics;
  }

  public DisplayPolicy getDisplayPolicy() {
    return this.mDisplayPolicy;
  }

  @Override // com.android.server.policy.WindowManagerPolicy.DisplayContentInfo
  public DisplayRotation getDisplayRotation() {
    return this.mDisplayRotation;
  }

  public InsetsStateController getInsetsStateController() {
    return this.mInsetsStateController;
  }

  public InsetsPolicy getInsetsPolicy() {
    return this.mInsetsPolicy;
  }

  public int getRotation() {
    return this.mDisplayRotation.getRotation();
  }

  public int getLastOrientation() {
    return this.mDisplayRotation.getLastOrientation();
  }

  public void registerRemoteAnimations(RemoteAnimationDefinition remoteAnimationDefinition) {
    this.mAppTransitionController.registerRemoteAnimations(remoteAnimationDefinition);
  }

  public void reconfigureDisplayLocked() {
    if (isReady()) {
      configureDisplayPolicy();
      setLayoutNeeded();
      boolean updateOrientation = updateOrientation();
      Configuration configuration = getConfiguration();
      this.mTmpConfiguration.setTo(configuration);
      computeScreenConfiguration(this.mTmpConfiguration);
      int diff = configuration.diff(this.mTmpConfiguration);
      if (updateOrientation | (diff != 0)) {
        this.mWaitingForConfig = true;
        if (this.mTransitionController.isShellTransitionsEnabled()) {
          TransitionRequestInfo.DisplayChange displayChange =
              this.mTransitionController.isCollecting()
                  ? null
                  : new TransitionRequestInfo.DisplayChange(this.mDisplayId);
          if (displayChange != null) {
            displayChange.setStartAbsBounds(configuration.windowConfiguration.getBounds());
            displayChange.setEndAbsBounds(this.mTmpConfiguration.windowConfiguration.getBounds());
          }
          requestChangeTransitionIfNeeded(diff, displayChange);
        } else if (this.mLastHasContent) {
          this.mWmService.startFreezingDisplay(0, 0, this);
        }
        sendNewConfiguration();
      }
      this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
    }
  }

  public void sendNewConfiguration() {
    if (!isReady()
        || this.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange()
        || updateDisplayOverrideConfigurationLocked()) {
      return;
    }
    clearFixedRotationLaunchingApp();
    if (this.mWaitingForConfig) {
      this.mWaitingForConfig = false;
      this.mWmService.mLastFinishedFreezeSource = "config-unchanged";
      setLayoutNeeded();
      this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
    }
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
  public boolean onDescendantOrientationChanged(WindowContainer windowContainer) {
    Configuration updateOrientation = updateOrientation(windowContainer, false);
    boolean handlesOrientationChangeFromDescendant =
        handlesOrientationChangeFromDescendant(
            windowContainer != null ? windowContainer.getOverrideOrientation() : -2);
    if (updateOrientation == null) {
      return handlesOrientationChangeFromDescendant;
    }
    if (handlesOrientationChangeFromDescendant && (windowContainer instanceof ActivityRecord)) {
      ActivityRecord activityRecord = (ActivityRecord) windowContainer;
      boolean updateDisplayOverrideConfigurationLocked =
          updateDisplayOverrideConfigurationLocked(updateOrientation, activityRecord, false, null);
      activityRecord.frozenBeforeDestroy = true;
      if (!updateDisplayOverrideConfigurationLocked) {
        this.mRootWindowContainer.resumeFocusedTasksTopActivities();
      }
    } else {
      updateDisplayOverrideConfigurationLocked(updateOrientation, null, false, null);
    }
    return handlesOrientationChangeFromDescendant;
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
  public boolean handlesOrientationChangeFromDescendant(int i) {
    return (shouldIgnoreOrientationRequest(i) || getDisplayRotation().isFixedToUserRotation())
        ? false
        : true;
  }

  public boolean updateOrientation() {
    return updateOrientation(false);
  }

  public Configuration updateOrientation(WindowContainer windowContainer, boolean z) {
    ActivityRecord asActivityRecord;
    if (!this.mDisplayReady) {
      return null;
    }
    if (this.mWmService.mDragDropController.isDragInProgressByRecents()) {
      Slog.d(StartingSurfaceController.TAG, "Ignore updateOrientation during dragging by Recents.");
      return null;
    }
    if (!updateOrientation(z)) {
      return null;
    }
    if (windowContainer != null
        && !this.mWmService.mRoot.mOrientationChangeComplete
        && (asActivityRecord = windowContainer.asActivityRecord()) != null
        && asActivityRecord.mayFreezeScreenLocked()) {
      asActivityRecord.startFreezingScreen();
    }
    Configuration configuration = new Configuration();
    computeScreenConfiguration(configuration);
    return configuration;
  }

  public final int getMinimalTaskSizeDp() {
    Resources resources =
        this.mAtmService.mContext.createConfigurationContext(getConfiguration()).getResources();
    TypedValue typedValue = new TypedValue();
    resources.getValue(R.dimen.dialog_corner_radius, typedValue, true);
    int i = typedValue.data;
    int i2 = (i >> 0) & 15;
    if (typedValue.type != 5 || i2 != 1) {
      throw new IllegalArgumentException(
          "Resource ID #0x"
              + Integer.toHexString(R.dimen.dialog_corner_radius)
              + " is not in valid type or unit");
    }
    return (int) TypedValue.complexToFloat(i);
  }

  public final boolean updateOrientation(boolean z) {
    WindowContainer windowContainer = this.mLastOrientationSource;
    int orientation = getOrientation();
    WindowContainer lastOrientationSource = getLastOrientationSource();
    if (lastOrientationSource != windowContainer
        && this.mRotationReversionController.isRotationReversionEnabled()) {
      this.mRotationReversionController.updateForNoSensorOverride();
    }
    ActivityRecord asActivityRecord =
        lastOrientationSource != null ? lastOrientationSource.asActivityRecord() : null;
    if (asActivityRecord != null) {
      Task task = asActivityRecord.getTask();
      if (task != null && orientation != task.mLastReportedRequestedOrientation) {
        task.mLastReportedRequestedOrientation = orientation;
        this.mAtmService
            .getTaskChangeNotificationController()
            .notifyTaskRequestedOrientationChanged(task.mTaskId, orientation);
      }
      ActivityRecord activityRecord =
          !asActivityRecord.isVisibleRequested() ? topRunningActivity() : asActivityRecord;
      if (activityRecord != null
          && handleTopActivityLaunchingInDifferentOrientation(
              activityRecord, asActivityRecord, true)) {
        return false;
      }
    }
    return this.mDisplayRotation.updateOrientation(orientation, z);
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
    return !this.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange();
  }

  public int rotationForActivityInDifferentOrientation(ActivityRecord activityRecord) {
    int adjustedOrientation;
    ActivityRecord activity;
    if (this.mTransitionController.useShellTransitionsRotation()) {
      return -1;
    }
    int overrideOrientation = activityRecord.getOverrideOrientation();
    if (!WindowManagerService.ENABLE_FIXED_ROTATION_TRANSFORM
        || shouldIgnoreOrientationRequest(overrideOrientation)
        || (this.isDefaultDisplay && isDexMode())) {
      return -1;
    }
    if (overrideOrientation == 3
        && (activity =
                getActivity(
                    new Predicate() { // from class:
                                      // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda29
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean canDefineOrientationForActivitiesAbove;
                        canDefineOrientationForActivitiesAbove =
                            ((ActivityRecord) obj).canDefineOrientationForActivitiesAbove();
                        return canDefineOrientationForActivitiesAbove;
                      }
                    },
                    activityRecord,
                    false,
                    true))
            != null) {
      activityRecord = activity;
    }
    if ((CoreRune.FW_ORIENTATION_CONTROL
            && activityRecord.mCompatRecord.mIsIgnoreOrientationRequest)
        || (!activityRecord.inMultiWindowMode()
            && activityRecord.getRequestedConfigurationOrientation(true)
                != getConfiguration().orientation)) {
      if (CoreRune.BAIDU_CARLIFE && isCarLifeDisplay()) {
        return -1;
      }
      if (CoreRune.FW_ORIENTATION_CONTROL
          && (adjustedOrientation =
                  this.mAtmService.mExt.mOrientationController.getAdjustedOrientation(
                      activityRecord, 0, true))
              != -2) {
        int rotation = getRotation();
        int rotationForOrientation =
            this.mDisplayRotation.rotationForOrientation(adjustedOrientation, rotation);
        if (rotationForOrientation == rotation) {
          return -1;
        }
        Task task = activityRecord.getTask();
        if (task != null && task.mOrientationControlEnabledAsAspectRatio) {
          int i = this.mDisplayRotation.isAnyPortrait(rotationForOrientation) ? 1 : 2;
          if (i
              != this.mAtmService.mExt.mOrientationController.getPreferredConfigurationOrientation(
                  activityRecord, i)) {
            return -1;
          }
        }
        return rotationForOrientation;
      }
      int rotation2 = getRotation();
      int rotationForOrientation2 =
          this.mDisplayRotation.rotationForOrientation(
              activityRecord.getRequestedOrientation(), rotation2);
      if (rotationForOrientation2 == rotation2) {
        return -1;
      }
      return rotationForOrientation2;
    }
    return -1;
  }

  public boolean handleTopActivityLaunchingInDifferentOrientation(
      ActivityRecord activityRecord, boolean z) {
    return handleTopActivityLaunchingInDifferentOrientation(activityRecord, activityRecord, z);
  }

  /* JADX WARN: Code restructure failed: missing block: B:24:0x0069, code lost:

     if (r5.getTask().mInResumeTopActivity == false) goto L36;
  */
  /* JADX WARN: Code restructure failed: missing block: B:55:0x0058, code lost:

     if (r4.mOpeningApps.contains(r5) != false) goto L32;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean handleTopActivityLaunchingInDifferentOrientation(
      ActivityRecord activityRecord, ActivityRecord activityRecord2, boolean z) {
    ActivityRecord activityRecord3;
    int rotationForActivityInDifferentOrientation;
    if (!WindowManagerService.ENABLE_FIXED_ROTATION_TRANSFORM
        || activityRecord.isFinishingFixedRotationTransform()) {
      return false;
    }
    if (activityRecord.hasFixedRotationTransform()) {
      return true;
    }
    if ((!activityRecord.occludesParent() || activityRecord.isReportedDrawn())
        && !(activityRecord.mPopOverState.isActivated()
            && (activityRecord3 = this.mFixedRotationLaunchingApp) != null
            && activityRecord3.getTask() == activityRecord.getTask())) {
      return false;
    }
    if (z) {
      if (this.mTransitionController.isShellTransitionsEnabled()) {
        if (!this.mTransitionController.isCollecting(activityRecord)) {
          return false;
        }
      } else {
        if (this.mAppTransition.isTransitionSet()) {}
        return false;
      }
      if (activityRecord.isState(ActivityRecord.State.RESUMED)) {}
    } else if (activityRecord != topRunningActivity()) {
      return false;
    }
    if ((this.mLastWallpaperVisible
            && activityRecord.windowsCanBeWallpaperTarget()
            && this.mFixedRotationTransitionListener.mAnimatingRecents == null
            && !this.mTransitionController.isTransientLaunch(activityRecord)
            && ((!CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH
                    || !this.mTransitionController.isLateTransientLaunch(activityRecord))
                && !this.mWmService.mAtmService.mKeyguardController.isKeyguardGoingAway(
                    this.mDisplayId)))
        || (rotationForActivityInDifferentOrientation =
                rotationForActivityInDifferentOrientation(activityRecord2))
            == -1
        || handleFixedRotationForMultiWindow(activityRecord)
        || !activityRecord.getDisplayArea().matchParentBounds()) {
      return false;
    }
    setFixedRotationLaunchingApp(activityRecord, rotationForActivityInDifferentOrientation);
    return true;
  }

  public boolean mayImeShowOnLaunchingActivity(ActivityRecord activityRecord) {
    int i;
    StartingData startingData;
    WindowState findMainWindow = activityRecord.findMainWindow(false);
    if (findMainWindow == null || (i = findMainWindow.mAttrs.softInputMode & 15) == 2 || i == 3) {
      return false;
    }
    if (activityRecord.getWindow(
            new Predicate() { // from class:
                              // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda68
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$mayImeShowOnLaunchingActivity$11;
                lambda$mayImeShowOnLaunchingActivity$11 =
                    DisplayContent.lambda$mayImeShowOnLaunchingActivity$11((WindowState) obj);
                return lambda$mayImeShowOnLaunchingActivity$11;
              }
            })
        != null) {
      return activityRecord.mLastImeShown
          || ((startingData = activityRecord.mStartingData) != null
              && startingData.hasImeSurface());
    }
    return false;
  }

  public static /* synthetic */ boolean lambda$mayImeShowOnLaunchingActivity$11(
      WindowState windowState) {
    return WindowManager.LayoutParams.mayUseInputMethod(windowState.mAttrs.flags);
  }

  public boolean hasTopFixedRotationLaunchingApp() {
    ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
    return (activityRecord == null
            || activityRecord == this.mFixedRotationTransitionListener.mAnimatingRecents)
        ? false
        : true;
  }

  public boolean isFixedRotationLaunchingApp(ActivityRecord activityRecord) {
    return this.mFixedRotationLaunchingApp == activityRecord;
  }

  public AsyncRotationController getAsyncRotationController() {
    return this.mAsyncRotationController;
  }

  public void setFixedRotationLaunchingAppUnchecked(ActivityRecord activityRecord) {
    setFixedRotationLaunchingAppUnchecked(activityRecord, -1);
  }

  public void setFixedRotationLaunchingAppUnchecked(ActivityRecord activityRecord, int i) {
    ActivityRecord activityRecord2 = this.mFixedRotationLaunchingApp;
    if (activityRecord2 == null && activityRecord != null) {
      this.mWmService.mDisplayNotificationController.dispatchFixedRotationStarted(this, i);
      startAsyncRotation(
          activityRecord == this.mFixedRotationTransitionListener.mAnimatingRecents
              || this.mTransitionController.isTransientLaunch(activityRecord));
    } else if (activityRecord2 != null && activityRecord == null) {
      this.mWmService.mDisplayNotificationController.dispatchFixedRotationFinished(this);
      if (!this.mTransitionController.isCollecting(this)) {
        finishAsyncRotationIfPossible();
      }
    }
    Slog.d(
        StartingSurfaceController.TAG,
        "setFixedRotationLaunchingAppUnchecked, rotation="
            + i
            + ", r="
            + activityRecord
            + ", caller="
            + Debug.getCallers(5));
    this.mFixedRotationLaunchingApp = activityRecord;
  }

  public void setFixedRotationLaunchingApp(ActivityRecord activityRecord, int i) {
    ActivityRecord activityRecord2 = this.mFixedRotationLaunchingApp;
    if (activityRecord2 == activityRecord
        && activityRecord.getWindowConfiguration().getRotation() == i) {
      return;
    }
    if (activityRecord2 != null
        && activityRecord2.getWindowConfiguration().getRotation() == i
        && activityRecord2.isInTransition()) {
      activityRecord.linkFixedRotationTransform(activityRecord2);
      if (activityRecord != this.mFixedRotationTransitionListener.mAnimatingRecents) {
        setFixedRotationLaunchingAppUnchecked(activityRecord, i);
        return;
      }
      return;
    }
    if (!activityRecord.hasFixedRotationTransform()) {
      startFixedRotationTransform(activityRecord, i);
    }
    setFixedRotationLaunchingAppUnchecked(activityRecord, i);
    if (activityRecord2 != null) {
      activityRecord2.finishFixedRotationTransform();
    }
  }

  public void continueUpdateOrientationForDiffOrienLaunchingApp() {
    if (this.mFixedRotationLaunchingApp == null
        || this.mPinnedTaskController.shouldDeferOrientationChange()) {
      return;
    }
    if (this.mDisplayRotation.updateOrientation(getOrientation(), false)) {
      sendNewConfiguration();
    } else {
      if (this.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange()) {
        return;
      }
      clearFixedRotationLaunchingApp();
    }
  }

  public final void clearFixedRotationLaunchingApp() {
    ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
    if (activityRecord == null) {
      return;
    }
    activityRecord.finishFixedRotationTransform();
    setFixedRotationLaunchingAppUnchecked(null);
  }

  public void startFixedRotationTransform(WindowToken windowToken, int i) {
    this.mTmpConfiguration.unset();
    DisplayInfo computeScreenConfiguration =
        computeScreenConfiguration(
            this.mTmpConfiguration, i, windowToken.isConfigurationNeededInUdcCutout());
    windowToken.applyFixedRotationTransform(
        computeScreenConfiguration,
        new DisplayFrames(
            new InsetsState(),
            computeScreenConfiguration,
            calculateDisplayCutoutForRotation(i, windowToken.isConfigurationNeededInUdcCutout()),
            calculateRoundedCornersForRotation(i),
            calculatePrivacyIndicatorBoundsForRotation(i),
            calculateDisplayShapeForRotation(i)),
        this.mTmpConfiguration);
  }

  public void rotateInDifferentOrientationIfNeeded(ActivityRecord activityRecord) {
    int rotationForActivityInDifferentOrientation =
        rotationForActivityInDifferentOrientation(activityRecord);
    if (rotationForActivityInDifferentOrientation != -1) {
      startFixedRotationTransform(activityRecord, rotationForActivityInDifferentOrientation);
    }
  }

  public boolean isRotationChanging() {
    return this.mDisplayRotation.getRotation() != getWindowConfiguration().getRotation();
  }

  public final void startAsyncRotationIfNeeded() {
    if (isRotationChanging()) {
      startAsyncRotation(false);
    }
  }

  public final boolean startAsyncRotation(boolean z) {
    if (z) {
      this.mWmService.f1749mH.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda43
            @Override // java.lang.Runnable
            public final void run() {
              DisplayContent.this.lambda$startAsyncRotation$12();
            }
          },
          250L);
      return false;
    }
    if (this.mAsyncRotationController != null) {
      return false;
    }
    AsyncRotationController asyncRotationController = new AsyncRotationController(this);
    this.mAsyncRotationController = asyncRotationController;
    asyncRotationController.start();
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startAsyncRotation$12() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        if (this.mFixedRotationLaunchingApp != null && startAsyncRotation(false)) {
          getPendingTransaction().apply();
        }
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public void finishAsyncRotationIfPossible() {
    AsyncRotationController asyncRotationController = this.mAsyncRotationController;
    if (asyncRotationController == null || this.mDisplayRotation.hasSeamlessRotatingWindow()) {
      return;
    }
    asyncRotationController.completeAll();
    this.mAsyncRotationController = null;
  }

  public void finishAsyncRotation(WindowToken windowToken) {
    AsyncRotationController asyncRotationController = this.mAsyncRotationController;
    if (asyncRotationController == null || !asyncRotationController.completeRotation(windowToken)) {
      return;
    }
    this.mAsyncRotationController = null;
  }

  public boolean shouldSyncRotationChange(WindowState windowState) {
    AsyncRotationController asyncRotationController = this.mAsyncRotationController;
    return asyncRotationController == null || !asyncRotationController.isAsync(windowState);
  }

  public void notifyInsetsChanged(Consumer consumer) {
    InsetsState fixedRotationTransformInsetsState;
    ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
    if (activityRecord != null
        && (fixedRotationTransformInsetsState =
                activityRecord.getFixedRotationTransformInsetsState())
            != null) {
      InsetsState.traverse(
          fixedRotationTransformInsetsState,
          this.mInsetsStateController.getRawInsetsState(),
          COPY_SOURCE_VISIBILITY);
    }
    forAllWindows(consumer, true);
    RemoteInsetsControlTarget remoteInsetsControlTarget = this.mRemoteInsetsControlTarget;
    if (remoteInsetsControlTarget != null) {
      remoteInsetsControlTarget.notifyInsetsChanged();
    }
    if (this.mWmService.mAccessibilityController.hasCallbacks()) {
      InsetsControlTarget insetsControlTarget = this.mImeControlTarget;
      this.mWmService.mAccessibilityController.updateImeVisibilityIfNeeded(
          this.mDisplayId,
          insetsControlTarget != null
              && insetsControlTarget.isRequestedVisible(WindowInsets.Type.ime()));
    }
  }

  public boolean updateRotationUnchecked() {
    return this.mDisplayRotation.updateRotationUnchecked(false);
  }

  public boolean canShowTasksInHostDeviceRecents() {
    DisplayWindowPolicyControllerHelper displayWindowPolicyControllerHelper = this.mDwpcHelper;
    if (displayWindowPolicyControllerHelper == null) {
      return true;
    }
    return displayWindowPolicyControllerHelper.canShowTasksInHostDeviceRecents();
  }

  /* renamed from: applyRotation, reason: merged with bridge method [inline-methods] */
  public final void lambda$applyRotationAndFinishFixedRotation$49(final int i, final int i2) {
    this.mDisplayRotation.applyCurrentRotation(i2);
    final boolean z = false;
    boolean z2 = this.mTransitionController.getTransitionPlayer() != null;
    if (this.mDisplayRotation.isRotatingSeamlessly() && !z2) {
      z = true;
    }
    final SurfaceControl.Transaction syncTransaction =
        z2 ? getSyncTransaction() : getPendingTransaction();
    ScreenRotationAnimation rotationAnimation = z ? null : getRotationAnimation();
    updateDisplayAndOrientation(null);
    if (rotationAnimation != null && rotationAnimation.hasScreenshot()) {
      rotationAnimation.setRotation(syncTransaction, i2);
    }
    if (!z2) {
      forAllWindows(
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda61
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayContent.lambda$applyRotation$13(syncTransaction, i, i2, z, (WindowState) obj);
            }
          },
          true);
      this.mPinnedTaskController.startSeamlessRotationIfNeeded(syncTransaction, i, i2);
      if (!this.mDisplayRotation.hasSeamlessRotatingWindow()) {
        this.mDisplayRotation.cancelSeamlessRotation();
      }
    }
    if (z2) {
      getPendingTransaction().setFixedTransformHint(this.mSurfaceControl, i2);
      syncTransaction.unsetFixedTransformHint(this.mSurfaceControl);
    }
    scheduleAnimation();
    if (!z2) {
      forAllWindows(
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda62
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayContent.this.lambda$applyRotation$14((WindowState) obj);
            }
          },
          true);
    }
    this.mWmService.mRotationWatcherController.dispatchDisplayRotationChange(this.mDisplayId, i2);
  }

  public static /* synthetic */ void lambda$applyRotation$13(
      SurfaceControl.Transaction transaction, int i, int i2, boolean z, WindowState windowState) {
    windowState.seamlesslyRotateIfAllowed(transaction, i, i2, z);
    if (z || !windowState.mHasSurface) {
      return;
    }
    if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_ORIENTATION,
          2083556954,
          0,
          (String) null,
          new Object[] {String.valueOf(windowState)});
    }
    windowState.setOrientationChanging(true);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$applyRotation$14(WindowState windowState) {
    WindowToken windowToken = windowState.mToken;
    if (windowToken.mIsPortraitWindowToken) {
      windowToken.finishFixedRotationTransform(null);
      startFixedRotationTransform(windowState.mToken, 0);
    }
  }

  public void configureDisplayPolicy() {
    this.mRootWindowContainer.updateDisplayImePolicyCache();
    this.mDisplayPolicy.updateConfigurationAndScreenSizeDependentBehaviors();
    this.mDisplayRotation.configure(this.mBaseDisplayWidth, this.mBaseDisplayHeight);
  }

  public final DisplayInfo updateDisplayAndOrientation(Configuration configuration) {
    String str;
    int rotation = getRotation();
    boolean z = true;
    if (rotation != 1 && rotation != 3) {
      z = false;
    }
    boolean z2 = z;
    int i = z2 ? this.mBaseDisplayHeight : this.mBaseDisplayWidth;
    int i2 = z2 ? this.mBaseDisplayWidth : this.mBaseDisplayHeight;
    DisplayCutout calculateDisplayCutoutForRotation = calculateDisplayCutoutForRotation(rotation);
    RoundedCorners calculateRoundedCornersForRotation =
        calculateRoundedCornersForRotation(rotation);
    DisplayShape calculateDisplayShapeForRotation = calculateDisplayShapeForRotation(rotation);
    Rect rect = this.mDisplayPolicy.getDecorInsetsInfo(rotation, i, i2).mNonDecorFrame;
    DisplayInfo displayInfo = this.mDisplayInfo;
    displayInfo.rotation = rotation;
    displayInfo.logicalWidth = i;
    displayInfo.logicalHeight = i2;
    displayInfo.logicalDensityDpi = this.mBaseDisplayDensity;
    float f = this.mBaseDisplayPhysicalXDpi;
    displayInfo.physicalXDpi = f;
    float f2 = this.mBaseDisplayPhysicalYDpi;
    displayInfo.physicalYDpi = f2;
    DisplayInfo displayInfo2 = this.mNonOverrideDisplayInfo;
    float f3 = i / (z2 ? displayInfo2.logicalHeight : displayInfo2.logicalWidth);
    float f4 = i2 / (z2 ? displayInfo2.logicalWidth : displayInfo2.logicalHeight);
    float f5 = displayInfo2.physicalXDpi * f3;
    float f6 = displayInfo2.physicalYDpi * f4;
    if (f != f5 || f2 != f6) {
      StringBuilder sb = new StringBuilder();
      sb.append("updateDisplayAndOrientation: Change ");
      sb.append(this.mDisplayInfo.physicalXDpi);
      sb.append("x");
      sb.append(this.mDisplayInfo.physicalYDpi);
      sb.append("dpi to ");
      sb.append(f5);
      sb.append("x");
      sb.append(f6);
      sb.append("dpi, BaseDpi=");
      sb.append(displayInfo2.physicalXDpi);
      sb.append("x");
      sb.append(displayInfo2.physicalYDpi);
      sb.append(", DisplayRatio=");
      sb.append(f3);
      sb.append("x");
      sb.append(f4);
      if (f3 == f3 && f4 == f4) {
        str = "";
      } else {
        str = ", adjustedRatio=" + f3 + "x" + f4;
      }
      sb.append(str);
      Slog.v(StartingSurfaceController.TAG, sb.toString());
      DisplayInfo displayInfo3 = this.mDisplayInfo;
      displayInfo3.physicalXDpi = f5;
      displayInfo3.physicalYDpi = f6;
    }
    this.mDisplayInfo.appWidth = rect.width();
    this.mDisplayInfo.appHeight = rect.height();
    if (this.isDefaultDisplay) {
      this.mDisplayInfo.getLogicalMetrics(
          this.mRealDisplayMetrics,
          CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO,
          (Configuration) null);
    }
    DisplayInfo displayInfo4 = this.mDisplayInfo;
    if (calculateDisplayCutoutForRotation.isEmpty()) {
      calculateDisplayCutoutForRotation = null;
    }
    displayInfo4.displayCutout = calculateDisplayCutoutForRotation;
    DisplayInfo displayInfo5 = this.mDisplayInfo;
    displayInfo5.roundedCorners = calculateRoundedCornersForRotation;
    displayInfo5.displayShape = calculateDisplayShapeForRotation;
    displayInfo5.getAppMetrics(this.mDisplayMetrics);
    if (this.mDisplayScalingDisabled) {
      this.mDisplayInfo.flags |= 1073741824;
    } else {
      this.mDisplayInfo.flags &= -1073741825;
    }
    computeSizeRanges(this.mDisplayInfo, z2, i, i2, this.mDisplayMetrics.density, configuration);
    this.mWmService.mDisplayManagerInternal.setDisplayInfoOverrideFromWindowManager(
        this.mDisplayId, this.mDisplayInfo);
    if (this.isDefaultDisplay) {
      this.mCompatibleScreenScale =
          CompatibilityInfo.computeCompatibleScaling(
              this.mDisplayMetrics, this.mCompatDisplayMetrics);
    }
    onDisplayInfoChanged();
    return this.mDisplayInfo;
  }

  public DisplayCutout calculateDisplayCutoutForRotation(int i) {
    return calculateDisplayCutoutForRotation(i, false);
  }

  public DisplayCutout calculateDisplayCutoutForRotation(int i, boolean z) {
    if (z) {
      return this.mUdcCutoutPolicy.calculateDisplayCutoutForRotation(i);
    }
    return ((WmDisplayCutout) this.mDisplayCutoutCache.getOrCompute(this.mBaseDisplayCutout, i))
        .getDisplayCutout();
  }

  public static WmDisplayCutout calculateDisplayCutoutForRotationAndDisplaySizeUncached(
      DisplayCutout displayCutout, int i, int i2, int i3) {
    if (displayCutout == null || displayCutout == DisplayCutout.NO_CUTOUT) {
      return WmDisplayCutout.NO_CUTOUT;
    }
    if (i2 == i3) {
      Slog.w(StartingSurfaceController.TAG, "Ignore cutout because display size is square: " + i2);
      return WmDisplayCutout.NO_CUTOUT;
    }
    if (i == 0) {
      return WmDisplayCutout.computeSafeInsets(displayCutout, i2, i3);
    }
    DisplayCutout rotated = displayCutout.getRotated(i2, i3, 0, i);
    boolean z = i == 1 || i == 3;
    int i4 = z ? i3 : i2;
    if (!z) {
      i2 = i3;
    }
    return new WmDisplayCutout(rotated, new Size(i4, i2));
  }

  public final WmDisplayCutout calculateDisplayCutoutForRotationUncached(
      DisplayCutout displayCutout, int i) {
    return calculateDisplayCutoutForRotationAndDisplaySizeUncached(
        displayCutout, i, this.mBaseDisplayWidth, this.mBaseDisplayHeight);
  }

  public RoundedCorners calculateRoundedCornersForRotation(int i) {
    return (RoundedCorners)
        this.mRoundedCornerCache.getOrCompute(
            this.mIsSizeForced ? this.mBaseRoundedCorners : this.mInitialRoundedCorners, i);
  }

  public final RoundedCorners calculateRoundedCornersForRotationUncached(
      RoundedCorners roundedCorners, int i) {
    if (roundedCorners == null || roundedCorners == RoundedCorners.NO_ROUNDED_CORNERS) {
      return RoundedCorners.NO_ROUNDED_CORNERS;
    }
    if (i == 0) {
      return roundedCorners;
    }
    boolean z = this.mIsSizeForced;
    return roundedCorners.rotate(
        i,
        z ? this.mBaseDisplayWidth : this.mInitialDisplayWidth,
        z ? this.mBaseDisplayHeight : this.mInitialDisplayHeight);
  }

  public PrivacyIndicatorBounds calculatePrivacyIndicatorBoundsForRotation(int i) {
    return (PrivacyIndicatorBounds)
        this.mPrivacyIndicatorBoundsCache.getOrCompute(this.mCurrentPrivacyIndicatorBounds, i);
  }

  public final PrivacyIndicatorBounds calculatePrivacyIndicatorBoundsForRotationUncached(
      PrivacyIndicatorBounds privacyIndicatorBounds, int i) {
    if (privacyIndicatorBounds == null) {
      return new PrivacyIndicatorBounds(new Rect[4], i);
    }
    return privacyIndicatorBounds.rotate(i);
  }

  public DisplayShape calculateDisplayShapeForRotation(int i) {
    return (DisplayShape) this.mDisplayShapeCache.getOrCompute(this.mInitialDisplayShape, i);
  }

  public final DisplayShape calculateDisplayShapeForRotationUncached(
      DisplayShape displayShape, int i) {
    if (displayShape == null) {
      return DisplayShape.NONE;
    }
    return i == 0 ? displayShape : displayShape.setRotation(i);
  }

  public DisplayInfo computeScreenConfiguration(Configuration configuration, int i, boolean z) {
    boolean z2 = i == 1 || i == 3;
    int i2 = z2 ? this.mBaseDisplayHeight : this.mBaseDisplayWidth;
    int i3 = z2 ? this.mBaseDisplayWidth : this.mBaseDisplayHeight;
    configuration.windowConfiguration.setMaxBounds(0, 0, i2, i3);
    WindowConfiguration windowConfiguration = configuration.windowConfiguration;
    windowConfiguration.setBounds(windowConfiguration.getMaxBounds());
    computeScreenAppConfiguration(configuration, i2, i3, i);
    DisplayInfo displayInfo = new DisplayInfo(this.mDisplayInfo);
    displayInfo.rotation = i;
    displayInfo.logicalWidth = i2;
    displayInfo.logicalHeight = i3;
    Rect appBounds = configuration.windowConfiguration.getAppBounds();
    displayInfo.appWidth = appBounds.width();
    displayInfo.appHeight = appBounds.height();
    DisplayCutout calculateDisplayCutoutForRotation = calculateDisplayCutoutForRotation(i, z);
    if (calculateDisplayCutoutForRotation.isEmpty()) {
      calculateDisplayCutoutForRotation = null;
    }
    displayInfo.displayCutout = calculateDisplayCutoutForRotation;
    computeSizeRanges(displayInfo, z2, i2, i3, this.mDisplayMetrics.density, configuration);
    return displayInfo;
  }

  public final void computeScreenAppConfiguration(
      Configuration configuration, int i, int i2, int i3) {
    configuration.windowConfiguration.setAppBounds(
        this.mDisplayPolicy.getDecorInsetsInfo(i3, i, i2).mNonDecorFrame);
    configuration.windowConfiguration.setRotation(i3);
    boolean z = true;
    configuration.orientation = i <= i2 ? 1 : 2;
    float f = this.mDisplayMetrics.density;
    configuration.screenWidthDp = (int) ((r0.mConfigFrame.width() / f) + 0.5f);
    int height = (int) ((r0.mConfigFrame.height() / f) + 0.5f);
    configuration.screenHeightDp = height;
    float f2 = configuration.screenWidthDp;
    float f3 = this.mCompatibleScreenScale;
    configuration.compatScreenWidthDp = (int) (f2 / f3);
    configuration.compatScreenHeightDp = (int) (height / f3);
    configuration.screenLayout =
        WindowContainer.computeScreenLayout(
            Configuration.resetScreenLayout(configuration.screenLayout),
            configuration.screenWidthDp,
            configuration.screenHeightDp);
    if (i3 != 1 && i3 != 3) {
      z = false;
    }
    configuration.compatSmallestScreenWidthDp = computeCompatSmallestWidth(z, i, i2);
    configuration.windowConfiguration.setDisplayRotation(i3);
  }

  /* JADX WARN: Removed duplicated region for block: B:41:0x00e9  */
  /* JADX WARN: Removed duplicated region for block: B:43:0x00ec A[SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void computeScreenConfiguration(Configuration configuration) {
    DisplayInfo updateDisplayAndOrientation = updateDisplayAndOrientation(configuration);
    int i = updateDisplayAndOrientation.logicalWidth;
    int i2 = updateDisplayAndOrientation.logicalHeight;
    this.mTmpRect.set(0, 0, i, i2);
    configuration.windowConfiguration.setBounds(this.mTmpRect);
    configuration.windowConfiguration.setMaxBounds(this.mTmpRect);
    configuration.windowConfiguration.setWindowingMode(getWindowingMode());
    configuration.windowConfiguration.setDisplayWindowingMode(getWindowingMode());
    computeScreenAppConfiguration(configuration, i, i2, updateDisplayAndOrientation.rotation);
    configuration.screenLayout =
        (configuration.screenLayout & (-769))
            | ((updateDisplayAndOrientation.flags & 16) != 0 ? 512 : 256);
    configuration.densityDpi = updateDisplayAndOrientation.logicalDensityDpi;
    configuration.colorMode =
        ((updateDisplayAndOrientation.isWideColorGamut()
                    && this.mWmService.hasWideColorGamutSupport())
                ? 2
                : 1)
            | ((updateDisplayAndOrientation.isHdr() && this.mWmService.hasHdrSupport()) ? 8 : 4);
    configuration.touchscreen = 1;
    configuration.keyboard = 1;
    configuration.navigation = 1;
    InputDevice[] inputDevices = this.mWmService.mInputManager.getInputDevices();
    int length = inputDevices != null ? inputDevices.length : 0;
    int i3 = 0;
    int i4 = 0;
    for (int i5 = 0; i5 < length; i5++) {
      InputDevice inputDevice = inputDevices[i5];
      if (!inputDevice.isVirtual()
          && (this.mWmService.mInputManager.canDispatchToDisplay(
                  inputDevice.getId(), this.mDisplayId)
              || shouldApplyInputDeviceToDisplay(inputDevice.getId()))) {
        int sources = inputDevice.getSources();
        int i6 = inputDevice.isExternal() ? 2 : 1;
        if (!this.mWmService.mIsTouchDevice) {
          configuration.touchscreen = 1;
        } else if ((sources & 4098) == 4098) {
          configuration.touchscreen = 3;
        }
        if ((sources & 65540) == 65540) {
          configuration.navigation = 3;
        } else {
          if ((sources & FrameworkStatsLog.HEARING_AID_INFO_REPORTED) == 513
              && configuration.navigation == 1) {
            configuration.navigation = 2;
          }
          if (inputDevice.getKeyboardType() != 2) {
            configuration.keyboard = 2;
            i4 |= i6;
          }
        }
        i3 |= i6;
        if (inputDevice.getKeyboardType() != 2) {}
      }
    }
    if (configuration.navigation == 1 && this.mWmService.mHasPermanentDpad) {
      configuration.navigation = 2;
      i3 |= 1;
    }
    boolean z = configuration.keyboard != 1;
    WindowManagerService windowManagerService = this.mWmService;
    if (z != windowManagerService.mHardKeyboardAvailable) {
      windowManagerService.mHardKeyboardAvailable = z;
      windowManagerService.f1749mH.removeMessages(22);
      this.mWmService.f1749mH.sendEmptyMessage(22);
    }
    this.mDisplayPolicy.updateConfigurationAndScreenSizeDependentBehaviors();
    configuration.keyboardHidden = 1;
    configuration.hardKeyboardHidden = 1;
    configuration.navigationHidden = 1;
    this.mWmService.mPolicy.adjustConfigurationLw(configuration, i4, i3);
    this.mAtmService.mDexController.adjustDexConfigurationLocked(configuration, this);
    configuration.windowConfiguration.setDexTaskDockingState(0);
    if (CoreRune.MT_NEW_DEX_CONFIG_MANAGEMENT) {
      this.mAtmService.mNewDexController.adjustDesktopModeConfiguration(configuration, this);
    }
  }

  public final int computeCompatSmallestWidth(boolean z, int i, int i2) {
    this.mTmpDisplayMetrics.setTo(this.mDisplayMetrics);
    DisplayMetrics displayMetrics = this.mTmpDisplayMetrics;
    if (!z) {
      i2 = i;
      i = i2;
    }
    return reduceCompatConfigWidthSize(
        reduceCompatConfigWidthSize(
            reduceCompatConfigWidthSize(
                reduceCompatConfigWidthSize(0, 0, displayMetrics, i2, i), 1, displayMetrics, i, i2),
            2,
            displayMetrics,
            i2,
            i),
        3,
        displayMetrics,
        i,
        i2);
  }

  public final int reduceCompatConfigWidthSize(
      int i, int i2, DisplayMetrics displayMetrics, int i3, int i4) {
    Rect rect = this.mDisplayPolicy.getDecorInsetsInfo(i2, i3, i4).mNonDecorFrame;
    displayMetrics.noncompatWidthPixels = rect.width();
    displayMetrics.noncompatHeightPixels = rect.height();
    int computeCompatibleScaling =
        (int)
            (((displayMetrics.noncompatWidthPixels
                        / CompatibilityInfo.computeCompatibleScaling(
                            displayMetrics, (DisplayMetrics) null))
                    / displayMetrics.density)
                + 0.5f);
    return (i == 0 || computeCompatibleScaling < i) ? computeCompatibleScaling : i;
  }

  public final void computeSizeRanges(
      DisplayInfo displayInfo, boolean z, int i, int i2, float f, Configuration configuration) {
    if (z) {
      i2 = i;
      i = i2;
    }
    displayInfo.smallestNominalAppWidth = 1073741824;
    displayInfo.smallestNominalAppHeight = 1073741824;
    displayInfo.largestNominalAppWidth = 0;
    displayInfo.largestNominalAppHeight = 0;
    adjustDisplaySizeRanges(displayInfo, 0, i, i2);
    adjustDisplaySizeRanges(displayInfo, 1, i2, i);
    adjustDisplaySizeRanges(displayInfo, 2, i, i2);
    adjustDisplaySizeRanges(displayInfo, 3, i2, i);
    if (configuration == null) {
      return;
    }
    configuration.smallestScreenWidthDp = (int) ((displayInfo.smallestNominalAppWidth / f) + 0.5f);
  }

  public final void adjustDisplaySizeRanges(DisplayInfo displayInfo, int i, int i2, int i3) {
    DisplayPolicy.DecorInsets.Info decorInsetsInfo =
        this.mDisplayPolicy.getDecorInsetsInfo(i, i2, i3);
    int width = decorInsetsInfo.mConfigFrame.width();
    int height = decorInsetsInfo.mConfigFrame.height();
    if (width < displayInfo.smallestNominalAppWidth) {
      displayInfo.smallestNominalAppWidth = width;
    }
    if (width > displayInfo.largestNominalAppWidth) {
      displayInfo.largestNominalAppWidth = width;
    }
    if (height < displayInfo.smallestNominalAppHeight) {
      displayInfo.smallestNominalAppHeight = height;
    }
    if (height > displayInfo.largestNominalAppHeight) {
      displayInfo.largestNominalAppHeight = height;
    }
  }

  public int getPreferredOptionsPanelGravity() {
    int rotation = getRotation();
    if (this.mInitialDisplayWidth < this.mInitialDisplayHeight) {
      if (rotation != 1) {
        return rotation != 3 ? 81 : 8388691;
      }
      return 85;
    }
    if (rotation == 1) {
      return 81;
    }
    if (rotation != 2) {
      return rotation != 3 ? 85 : 81;
    }
    return 8388691;
  }

  public PinnedTaskController getPinnedTaskController() {
    return this.mPinnedTaskController;
  }

  public boolean hasAccess(int i) {
    return this.mDisplay.hasAccess(i);
  }

  public boolean isPrivate() {
    return (this.mDisplay.getFlags() & 4) != 0;
  }

  public boolean isTrusted() {
    return this.mDisplay.isTrusted();
  }

  public Task getRootTask(final int i, final int i2) {
    return (Task)
        getItemFromTaskDisplayAreas(
            new Function() { // from class:
                             // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda47
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                Task lambda$getRootTask$15;
                lambda$getRootTask$15 =
                    DisplayContent.lambda$getRootTask$15(i, i2, (TaskDisplayArea) obj);
                return lambda$getRootTask$15;
              }
            });
  }

  public static /* synthetic */ Task lambda$getRootTask$15(
      int i, int i2, TaskDisplayArea taskDisplayArea) {
    return taskDisplayArea.getRootTask(i, i2);
  }

  public static /* synthetic */ boolean lambda$getRootTask$16(int i, Task task) {
    return task.getRootTaskId() == i;
  }

  public Task getRootTask(final int i) {
    return getRootTask(
        new Predicate() { // from class:
                          // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda26
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$getRootTask$16;
            lambda$getRootTask$16 = DisplayContent.lambda$getRootTask$16(i, (Task) obj);
            return lambda$getRootTask$16;
          }
        });
  }

  public int getRootTaskCount() {
    final int[] iArr = new int[1];
    forAllRootTasks(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda39
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.lambda$getRootTaskCount$17(iArr, (Task) obj);
          }
        });
    return iArr[0];
  }

  public static /* synthetic */ void lambda$getRootTaskCount$17(int[] iArr, Task task) {
    iArr[0] = iArr[0] + 1;
  }

  public List getRootTasks(final int i, final int i2) {
    final ArrayList arrayList = new ArrayList();
    final boolean z = i == 0;
    forAllRootTasks(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda32
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.lambda$getRootTasks$18(z, i2, arrayList, i, (Task) obj);
          }
        });
    return arrayList;
  }

  public static /* synthetic */ void lambda$getRootTasks$18(
      boolean z, int i, List list, int i2, Task task) {
    if (z) {
      if (task.getActivityType() == i) {
        list.add(task);
      }
    } else if (task.isCompatible(i2, i)) {
      list.add(task);
    }
  }

  public Task getTopRootTask() {
    return getRootTask(
        new Predicate() { // from class:
                          // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda23
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$getTopRootTask$19;
            lambda$getTopRootTask$19 = DisplayContent.lambda$getTopRootTask$19((Task) obj);
            return lambda$getTopRootTask$19;
          }
        });
  }

  public int getCurrentOverrideConfigurationChanges() {
    return this.mCurrentOverrideConfigurationChanges;
  }

  public int getInitialDisplayDensity() {
    int i;
    int i2 = this.mInitialDisplayDensity;
    int i3 = this.mMaxUiWidth;
    return (i3 <= 0 || (i = this.mInitialDisplayWidth) <= i3) ? i2 : (int) ((i2 * i3) / i);
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
            // com.android.server.wm.ConfigurationContainer
  public void onConfigurationChanged(Configuration configuration) {
    if (this.isDefaultDisplay) {
      BoundsCompatUtils.get().onConfigurationChanged(this, configuration);
      this.mAtmService.mExt.mCustomAspectRatioController.onConfigurationChanged(this);
      this.mAtmService.mExt.mDisplayCutoutController.onConfigurationChanged(this, configuration);
      if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT) {
        updateIsOverlappingWithCutoutAsDefault();
      }
      if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
        this.mAtmService.mMultiTaskingController.handleMultiSplitAppMinSize(this);
      }
    } else if (isMultiTaskingDisplay()) {
      Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
      if (!requestedOverrideConfiguration.getLocales().equals(configuration.getLocales())) {
        requestedOverrideConfiguration.setLocales(configuration.getLocales());
      }
      int i = requestedOverrideConfiguration.uiMode;
      int i2 = i & 48;
      int i3 = configuration.uiMode;
      if (i2 != (i3 & 48)) {
        requestedOverrideConfiguration.uiMode = (i & (-49)) | (i3 & 48);
      }
    }
    int i4 = getConfiguration().orientation;
    super.onConfigurationChanged(configuration);
    DisplayPolicy displayPolicy = this.mDisplayPolicy;
    if (displayPolicy != null) {
      displayPolicy.onConfigurationChanged();
      this.mPinnedTaskController.onPostDisplayConfigurationChanged();
      this.mAtmService.mFreeformController.onConfigurationChanged(this);
      MultiWindowPointerEventListener multiWindowPointerEventListener =
          this.mMultiWindowPointerEventListener;
      if (multiWindowPointerEventListener != null) {
        multiWindowPointerEventListener.onConfigurationChanged();
      }
      if (CoreRune.FW_BLUR_WALLPAPER_LETTERBOX) {
        BlurWallpaperLetterbox.onConfigurationChanged(this);
      }
    }
    updateImeParent();
    ContentRecorder contentRecorder = this.mContentRecorder;
    if (contentRecorder != null) {
      contentRecorder.onConfigurationChanged(i4);
    }
    if (i4 != getConfiguration().orientation) {
      getMetricsLogger()
          .write(
              new LogMaker(1659)
                  .setSubtype(getConfiguration().orientation)
                  .addTaggedData(1660, Integer.valueOf(getDisplayId())));
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean isVisibleRequested() {
    return (!isVisible() || this.mRemoved || this.mRemoving) ? false : true;
  }

  @Override // com.android.server.wm.WindowContainer
  public void onAppTransitionDone() {
    super.onAppTransitionDone();
    this.mWmService.mWindowsChanged = true;
    ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
    if (activityRecord == null
        || activityRecord.isVisibleRequested()
        || this.mFixedRotationLaunchingApp.isVisible()
        || this.mDisplayRotation.isRotatingSeamlessly()) {
      return;
    }
    clearFixedRotationLaunchingApp();
  }

  @Override // com.android.server.wm.ConfigurationContainer
  public void setDisplayWindowingMode(int i) {
    setWindowingMode(i);
  }

  public boolean forAllImeWindows(ToBooleanFunction toBooleanFunction, boolean z) {
    return this.mImeWindowsContainer.forAllWindowForce(toBooleanFunction, z);
  }

  @Override // com.android.server.wm.WindowContainer
  public int getOrientation() {
    int orientation;
    int orientationForDexStandalone;
    if (CoreRune.FW_ORIENTATION_CONTROL) {
      this.mLastOrientationControlSource = null;
    }
    if (CoreRune.BAIDU_CARLIFE && isCarLifeDisplay()) {
      DisplayInfo displayInfo = this.mDisplayInfo;
      return displayInfo.logicalWidth > displayInfo.logicalHeight ? 0 : 1;
    }
    if (this.isDefaultDisplay
        && isDexMode()
        && (orientationForDexStandalone = getOrientationForDexStandalone()) != -2) {
      return orientationForDexStandalone;
    }
    WindowManagerService windowManagerService = this.mWmService;
    if (windowManagerService.mDisplayFrozen && windowManagerService.mPolicy.isKeyguardLocked()) {
      if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_ORIENTATION,
            -1710206702,
            5,
            (String) null,
            new Object[] {Long.valueOf(this.mDisplayId), Long.valueOf(getLastOrientation())});
      }
      return getLastOrientation();
    }
    DisplayRotationCompatPolicy displayRotationCompatPolicy = this.mDisplayRotationCompatPolicy;
    if (displayRotationCompatPolicy != null
        && (orientation = displayRotationCompatPolicy.getOrientation()) != -1) {
      this.mLastOrientationSource = null;
      return orientation;
    }
    int orientation2 = super.getOrientation();
    if (handlesOrientationChangeFromDescendant(orientation2)) {
      if (orientation2 != -2) {
        return orientation2;
      }
      if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_ORIENTATION,
            -1480772131,
            5,
            (String) null,
            new Object[] {-1L, Long.valueOf(this.mDisplayId)});
      }
      return -1;
    }
    ActivityRecord activityRecord = topRunningActivity(true);
    if (activityRecord != null
        && activityRecord.mLetterboxUiController.shouldUseDisplayLandscapeNaturalOrientation()) {
      if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_ORIENTATION,
            1015746067,
            21,
            (String) null,
            new Object[] {
              Long.valueOf(this.mDisplayId),
              Long.valueOf(orientation2),
              0L,
              String.valueOf(activityRecord)
            });
      }
      return 0;
    }
    this.mLastOrientationSource = null;
    if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_ORIENTATION,
          1877863087,
          21,
          (String) null,
          new Object[] {Long.valueOf(this.mDisplayId), Long.valueOf(orientation2), -1L});
    }
    return -1;
  }

  public void updateDisplayInfo() {
    updateBaseDisplayMetricsIfNeeded();
    this.mDisplay.getDisplayInfo(this.mDisplayInfo);
    this.mDisplay.getMetrics(this.mDisplayMetrics);
    onDisplayInfoChanged();
    onDisplayChanged(this);
  }

  public void updatePrivacyIndicatorBounds(Rect[] rectArr) {
    PrivacyIndicatorBounds privacyIndicatorBounds = this.mCurrentPrivacyIndicatorBounds;
    PrivacyIndicatorBounds updateStaticBounds = privacyIndicatorBounds.updateStaticBounds(rectArr);
    this.mCurrentPrivacyIndicatorBounds = updateStaticBounds;
    if (Objects.equals(privacyIndicatorBounds, updateStaticBounds)) {
      return;
    }
    updateDisplayFrames(true);
  }

  public void onDisplayInfoChanged() {
    updateDisplayFrames(false);
    this.mMinSizeOfResizeableTaskDp = getMinimalTaskSizeDp();
    InputMonitor inputMonitor = this.mInputMonitor;
    DisplayInfo displayInfo = this.mDisplayInfo;
    inputMonitor.layoutInputConsumers(displayInfo.logicalWidth, displayInfo.logicalHeight);
    this.mDisplayPolicy.onDisplayInfoChanged(this.mDisplayInfo);
  }

  public final void updateDisplayFrames(boolean z) {
    DisplayFrames displayFrames = this.mDisplayFrames;
    DisplayInfo displayInfo = this.mDisplayInfo;
    if (updateDisplayFrames(
        displayFrames, displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight)) {
      this.mInsetsStateController.onDisplayFramesUpdated(z);
    }
  }

  public boolean updateDisplayFrames(DisplayFrames displayFrames, int i, int i2, int i3) {
    return displayFrames.update(
        i,
        i2,
        i3,
        calculateDisplayCutoutForRotation(i),
        calculateRoundedCornersForRotation(i),
        calculatePrivacyIndicatorBoundsForRotation(i),
        calculateDisplayShapeForRotation(i));
  }

  @Override // com.android.server.wm.WindowContainer
  public void onDisplayChanged(DisplayContent displayContent) {
    super.onDisplayChanged(displayContent);
    updateSystemGestureExclusionLimit();
    updateKeepClearAreas();
  }

  public void updateSystemGestureExclusionLimit() {
    this.mSystemGestureExclusionLimit =
        (this.mWmService.mConstants.mSystemGestureExclusionLimitDp
                * this.mDisplayMetrics.densityDpi)
            / 160;
    updateSystemGestureExclusion();
  }

  public void initializeDisplayBaseInfo() {
    DisplayManagerInternal displayManagerInternal = this.mWmService.mDisplayManagerInternal;
    if (displayManagerInternal != null) {
      DisplayInfo displayInfo = displayManagerInternal.getDisplayInfo(this.mDisplayId);
      if (displayInfo != null) {
        this.mDisplayInfo.copyFrom(displayInfo);
      }
      this.mDwpcHelper = new DisplayWindowPolicyControllerHelper(this);
    }
    this.mNonOverrideDisplayInfo.copyFrom(this.mDisplayInfo);
    DisplayInfo displayInfo2 = this.mDisplayInfo;
    updateBaseDisplayMetrics(
        displayInfo2.logicalWidth,
        displayInfo2.logicalHeight,
        displayInfo2.logicalDensityDpi,
        displayInfo2.physicalXDpi,
        displayInfo2.physicalYDpi);
    DisplayInfo displayInfo3 = this.mDisplayInfo;
    this.mInitialDisplayWidth = displayInfo3.logicalWidth;
    this.mInitialDisplayHeight = displayInfo3.logicalHeight;
    this.mInitialDisplayDensity = displayInfo3.logicalDensityDpi;
    this.mInitialPhysicalXDpi = displayInfo3.physicalXDpi;
    this.mInitialPhysicalYDpi = displayInfo3.physicalYDpi;
    this.mInitialDisplayCutout = displayInfo3.displayCutout;
    this.mInitialRoundedCorners = displayInfo3.roundedCorners;
    this.mCurrentPrivacyIndicatorBounds =
        new PrivacyIndicatorBounds(new Rect[4], this.mDisplayInfo.rotation);
    DisplayInfo displayInfo4 = this.mDisplayInfo;
    this.mInitialDisplayShape = displayInfo4.displayShape;
    Display.Mode maximumResolutionDisplayMode =
        DisplayUtils.getMaximumResolutionDisplayMode(displayInfo4.supportedModes);
    this.mPhysicalDisplaySize =
        new Point(
            maximumResolutionDisplayMode == null
                ? this.mInitialDisplayWidth
                : maximumResolutionDisplayMode.getPhysicalWidth(),
            maximumResolutionDisplayMode == null
                ? this.mInitialDisplayHeight
                : maximumResolutionDisplayMode.getPhysicalHeight());
  }

  public final void updateBaseDisplayMetricsIfNeeded() {
    int i;
    RoundedCorners roundedCorners;
    String str;
    DisplayCutout displayCutout;
    DisplayShape displayShape;
    float f;
    this.mWmService.mDisplayManagerInternal.getNonOverrideDisplayInfo(
        this.mDisplayId, this.mDisplayInfo);
    int rotation = getRotation();
    this.mNonOverrideDisplayInfo.copyFrom(this.mDisplayInfo);
    DisplayInfo displayInfo = this.mDisplayInfo;
    int i2 = displayInfo.rotation;
    boolean z = i2 == 1 || i2 == 3;
    int i3 = z ? displayInfo.logicalHeight : displayInfo.logicalWidth;
    int i4 = z ? displayInfo.logicalWidth : displayInfo.logicalHeight;
    int i5 = displayInfo.logicalDensityDpi;
    float f2 = displayInfo.physicalXDpi;
    float f3 = displayInfo.physicalYDpi;
    DisplayCutout displayCutout2 =
        this.mIgnoreDisplayCutout ? DisplayCutout.NO_CUTOUT : displayInfo.displayCutout;
    String str2 = displayInfo.uniqueId;
    RoundedCorners roundedCorners2 = displayInfo.roundedCorners;
    DisplayShape displayShape2 = displayInfo.displayShape;
    boolean z2 =
        (this.mInitialDisplayWidth == i3
                && this.mInitialDisplayHeight == i4
                && this.mInitialDisplayDensity == i5
                && this.mInitialPhysicalXDpi == f2
                && this.mInitialPhysicalYDpi == f3
                && Objects.equals(this.mInitialDisplayCutout, displayCutout2)
                && Objects.equals(this.mInitialRoundedCorners, roundedCorners2)
                && Objects.equals(this.mInitialDisplayShape, displayShape2))
            ? false
            : true;
    boolean z3 = !str2.equals(this.mCurrentUniqueDisplayId);
    if (z2 || z3) {
      if (z3) {
        this.mWmService.mDisplayWindowSettings.applySettingsToDisplayLocked(this, false);
        roundedCorners = roundedCorners2;
        str = str2;
        i = rotation;
        displayCutout = displayCutout2;
        this.mDisplaySwitchTransitionLauncher.requestDisplaySwitchTransitionIfNeeded(
            this.mDisplayId, this.mInitialDisplayWidth, this.mInitialDisplayHeight, i3, i4);
        this.mDisplayRotation.physicalDisplayChanged();
        this.mDisplayPolicy.physicalDisplayChanged();
      } else {
        i = rotation;
        roundedCorners = roundedCorners2;
        str = str2;
        displayCutout = displayCutout2;
      }
      boolean z4 = this.mIsSizeForced;
      int i6 = z4 ? this.mBaseDisplayWidth : i3;
      int i7 = z4 ? this.mBaseDisplayHeight : i4;
      int i8 = this.mIsDensityForced ? this.mBaseDisplayDensity : i5;
      float f4 = z4 ? this.mBaseDisplayPhysicalXDpi : f2;
      if (z4) {
        f = this.mBaseDisplayPhysicalYDpi;
        displayShape = displayShape2;
      } else {
        displayShape = displayShape2;
        f = f3;
      }
      DisplayShape displayShape3 = displayShape;
      int i9 = i6;
      String str3 = str;
      int i10 = i7;
      RoundedCorners roundedCorners3 = roundedCorners;
      updateBaseDisplayMetrics(i9, i10, i8, f4, f);
      configureDisplayPolicy();
      if (z3) {
        this.mWmService.mDisplayWindowSettings.applyRotationSettingsToDisplayLocked(this);
      }
      this.mInitialDisplayWidth = i3;
      this.mInitialDisplayHeight = i4;
      this.mInitialDisplayDensity = i5;
      this.mInitialPhysicalXDpi = f2;
      this.mInitialPhysicalYDpi = f3;
      this.mInitialDisplayCutout = displayCutout;
      this.mInitialRoundedCorners = roundedCorners3;
      this.mInitialDisplayShape = displayShape3;
      this.mCurrentUniqueDisplayId = str3;
      reconfigureDisplayLocked();
      if (z3) {
        this.mDisplayPolicy.physicalDisplayUpdated();
        this.mDisplaySwitchTransitionLauncher.onDisplayUpdated(
            i, getRotation(), getDisplayAreaInfo());
      }
    }
  }

  public void setMaxUiWidth(int i) {
    this.mMaxUiWidth = i;
    updateBaseDisplayMetrics(
        this.mBaseDisplayWidth,
        this.mBaseDisplayHeight,
        this.mBaseDisplayDensity,
        this.mBaseDisplayPhysicalXDpi,
        this.mBaseDisplayPhysicalYDpi);
  }

  public void updateBaseDisplayMetrics(int i, int i2, int i3, float f, float f2) {
    int i4;
    this.mBaseDisplayWidth = i;
    this.mBaseDisplayHeight = i2;
    this.mBaseDisplayDensity = i3;
    this.mBaseDisplayPhysicalXDpi = f;
    this.mBaseDisplayPhysicalYDpi = f2;
    if (this.mIsSizeForced) {
      this.mBaseDisplayCutout = loadDisplayCutout(i, i2);
      this.mBaseRoundedCorners = loadRoundedCorners(i, i2);
    }
    int i5 = this.mMaxUiWidth;
    if (i5 > 0 && (i4 = this.mBaseDisplayWidth) > i5) {
      float f3 = i5 / i4;
      this.mBaseDisplayHeight = (int) (this.mBaseDisplayHeight * f3);
      this.mBaseDisplayWidth = i5;
      this.mBaseDisplayPhysicalXDpi *= f3;
      this.mBaseDisplayPhysicalYDpi *= f3;
      if (!this.mIsDensityForced) {
        this.mBaseDisplayDensity = (int) (this.mBaseDisplayDensity * f3);
      }
    }
    int i6 = this.mForcedHideCutout;
    if (i6 == 1 || i6 == 2) {
      this.mBaseDisplayCutout = null;
    } else {
      updateBaseDisplayCutout(this.mBaseDisplayWidth, this.mBaseDisplayHeight);
    }
    if (!this.mDisplayReady || this.mDisplayPolicy.shouldKeepCurrentDecorInsets()) {
      return;
    }
    this.mDisplayPolicy.mDecorInsets.invalidate();
  }

  public void setForcedDensity(int i, int i2) {
    this.mIsDensityForced = i != getInitialDisplayDensity();
    boolean z = i2 == -2;
    if (this.mWmService.mCurrentUserId == i2 || z) {
      this.mBaseDisplayDensity = i;
      reconfigureDisplayLocked();
    }
    if (z) {
      return;
    }
    if (i == getInitialDisplayDensity()) {
      i = 0;
    }
    this.mWmService.mDisplayWindowSettings.setForcedDensity(getDisplayInfo(), i, i2);
  }

  public void setForcedScalingMode(int i) {
    if (i != 1) {
      i = 0;
    }
    this.mDisplayScalingDisabled = i != 0;
    StringBuilder sb = new StringBuilder();
    sb.append("Using display scaling mode: ");
    sb.append(this.mDisplayScalingDisabled ? "off" : "auto");
    Slog.i(StartingSurfaceController.TAG, sb.toString());
    reconfigureDisplayLocked();
    this.mWmService.mDisplayWindowSettings.setForcedScalingMode(this, i);
  }

  public void setForcedSize(int i, int i2) {
    setForcedSize(
        i,
        i2,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
        DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
  }

  public void setForcedSize(int i, int i2, float f, float f2) {
    setForcedSize(i, i2, f, f2, true, false, false);
  }

  public void setForcedSize(int i, int i2, float f, float f2, boolean z, boolean z2, boolean z3) {
    int i3;
    int i4;
    int i5 = i;
    int i6 = this.mMaxUiWidth;
    if (i6 <= 0 || i5 <= i6) {
      i3 = i2;
    } else {
      i3 = (int) (i2 * (i6 / i5));
      i5 = i6;
    }
    int i7 = 0;
    boolean z4 =
        (this.mInitialDisplayWidth == i5 && this.mInitialDisplayHeight == i3) ? false : true;
    this.mIsSizeForced = z4;
    if (z4) {
      Point validForcedSize = getValidForcedSize(i5, i3);
      int i8 = validForcedSize.x;
      i3 = validForcedSize.y;
      i4 = i8;
    } else {
      i4 = i5;
    }
    int i9 = i3;
    if (z3) {
      this.mAtmService.mExt.mAvoidCompatDisplayInsets = true;
    }
    final IBinder displayToken =
        (CoreRune.FW_VRR_RESOLUTION_POLICY_FOR_SHELL_TRANSITION && this.isDefaultDisplay)
            ? SurfaceControl.getDisplayToken(this.mDisplayInfo.address)
            : null;
    if (CoreRune.FW_VRR_RESOLUTION_POLICY_FOR_SHELL_TRANSITION
        && displayToken != null
        && (i4 != this.mBaseDisplayWidth || i9 != this.mBaseDisplayHeight)) {
      ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get())
          .startChangeResolution(displayToken, true)
          .apply();
    }
    Slog.i(StartingSurfaceController.TAG, "Using new display size: " + i4 + "x" + i9);
    updateBaseDisplayMetrics(
        i4,
        i9,
        this.mBaseDisplayDensity,
        f != DisplayPowerController2.RATE_FROM_DOZE_TO_ON ? f : this.mBaseDisplayPhysicalXDpi,
        f2 != DisplayPowerController2.RATE_FROM_DOZE_TO_ON ? f2 : this.mBaseDisplayPhysicalYDpi);
    reconfigureDisplayLocked();
    if (z3) {
      this.mAtmService.mExt.mAvoidCompatDisplayInsets = false;
    }
    if (CoreRune.FW_VRR_RESOLUTION_POLICY_FOR_SHELL_TRANSITION && displayToken != null) {
      SurfaceControl.TransactionCommittedListener transactionCommittedListener =
          new SurfaceControl
              .TransactionCommittedListener() { // from class:
                                                // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda19
            @Override // android.view.SurfaceControl.TransactionCommittedListener
            public final void onTransactionCommitted() {
              DisplayContent.this.lambda$setForcedSize$20(displayToken);
            }
          };
      if (inTransition() && getSyncTransaction() == this.mSyncTransaction) {
        getSyncTransaction()
            .addTransactionCommittedListener(
                new HandlerExecutor(this.mWmService.f1749mH), transactionCommittedListener);
      } else {
        transactionCommittedListener.onTransactionCommitted();
      }
    }
    if (this.mIsSizeForced || z2) {
      i7 = i4;
    } else {
      i9 = 0;
    }
    if (z) {
      this.mWmService.mDisplayWindowSettings.setForcedSize(this, i7, i9);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setForcedSize$20(IBinder iBinder) {
    ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get())
        .startChangeResolution(iBinder, false)
        .apply();
  }

  public Point getValidForcedSize(int i, int i2) {
    int max = Math.max(this.mInitialDisplayWidth, this.mInitialDisplayHeight) * 3;
    return new Point(Math.min(Math.max(i, 200), max), Math.min(Math.max(i2, 200), max));
  }

  public DisplayCutout loadDisplayCutout(int i, int i2) {
    DisplayPolicy displayPolicy = this.mDisplayPolicy;
    if (displayPolicy == null || this.mInitialDisplayCutout == null) {
      return null;
    }
    Resources resources = displayPolicy.getSystemUiContext().getResources();
    String str = this.mDisplayInfo.uniqueId;
    Point point = this.mPhysicalDisplaySize;
    return DisplayCutout.fromResourcesRectApproximation(resources, str, point.x, point.y, i, i2);
  }

  public RoundedCorners loadRoundedCorners(int i, int i2) {
    DisplayPolicy displayPolicy = this.mDisplayPolicy;
    if (displayPolicy == null || this.mInitialRoundedCorners == null) {
      return null;
    }
    Resources resources = displayPolicy.getSystemUiContext().getResources();
    String str = this.mDisplayInfo.uniqueId;
    Point point = this.mPhysicalDisplaySize;
    return RoundedCorners.fromResources(resources, str, point.x, point.y, i, i2);
  }

  @Override // com.android.server.wm.DisplayArea
  public void getStableRect(Rect rect) {
    InsetsState rawInsetsState =
        this.mDisplayContent.getInsetsStateController().getRawInsetsState();
    rect.set(rawInsetsState.getDisplayFrame());
    rect.inset(rawInsetsState.calculateInsets(rect, WindowInsets.Type.systemBars(), true));
  }

  public TaskDisplayArea getDefaultTaskDisplayArea() {
    return this.mDisplayAreaPolicy.getDefaultTaskDisplayArea();
  }

  public void updateDisplayAreaOrganizers() {
    if (isTrusted()) {
      forAllDisplayAreas(
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayContent.this.lambda$updateDisplayAreaOrganizers$21((DisplayArea) obj);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateDisplayAreaOrganizers$21(DisplayArea displayArea) {
    IDisplayAreaOrganizer organizerByFeature;
    if (displayArea.isOrganized()
        || (organizerByFeature =
                this.mAtmService.mWindowOrganizerController.mDisplayAreaOrganizerController
                    .getOrganizerByFeature(displayArea.mFeatureId))
            == null) {
      return;
    }
    displayArea.setOrganizer(organizerByFeature);
  }

  public Task findTaskForResizePoint(final int i, final int i2) {
    final int dipToPixel =
        WindowManagerService.dipToPixel(
            WindowState.RESIZE_HANDLE_WIDTH_IN_DP, this.mDisplayMetrics);
    return (Task)
        getItemFromTaskDisplayAreas(
            new Function() { // from class:
                             // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda64
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                Task lambda$findTaskForResizePoint$22;
                lambda$findTaskForResizePoint$22 =
                    DisplayContent.this.lambda$findTaskForResizePoint$22(
                        i, i2, dipToPixel, (TaskDisplayArea) obj);
                return lambda$findTaskForResizePoint$22;
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ Task lambda$findTaskForResizePoint$22(
      int i, int i2, int i3, TaskDisplayArea taskDisplayArea) {
    return this.mTmpTaskForResizePointSearchResult.process(taskDisplayArea, i, i2, i3);
  }

  public void updateTouchExcludeRegion() {
    ActivityRecord activityRecord = this.mFocusedApp;
    final Task task = activityRecord != null ? activityRecord.getTask() : null;
    if (task == null) {
      this.mTouchExcludeRegion.setEmpty();
    } else {
      Region region = this.mTouchExcludeRegion;
      DisplayInfo displayInfo = this.mDisplayInfo;
      region.set(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
      final int dipToPixel =
          WindowManagerService.dipToPixel(
              WindowState.RESIZE_HANDLE_WIDTH_IN_DP, this.mDisplayMetrics);
      this.mTmpRect.setEmpty();
      this.mTmpRect2.setEmpty();
      this.mTmpAlwaysOnTopFreeformRegion.setEmpty();
      forAllTasks(
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda50
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayContent.this.lambda$updateTouchExcludeRegion$23(task, dipToPixel, (Task) obj);
            }
          });
      if (!this.mTmpRect2.isEmpty()) {
        this.mTouchExcludeRegion.op(this.mTmpRect2, Region.Op.UNION);
        if (!task.isAlwaysOnTopFreeform()) {
          this.mTouchExcludeRegion.op(this.mTmpAlwaysOnTopFreeformRegion, Region.Op.DIFFERENCE);
        }
      }
    }
    WindowState windowState = this.mInputMethodWindow;
    if (windowState != null && windowState.isVisible()) {
      this.mInputMethodWindow.getTouchableRegion(this.mTmpRegion);
      this.mTouchExcludeRegion.op(this.mTmpRegion, Region.Op.UNION);
    }
    for (int size = this.mTapExcludedWindows.size() - 1; size >= 0; size--) {
      WindowState windowState2 = (WindowState) this.mTapExcludedWindows.get(size);
      if (windowState2.isVisible()) {
        windowState2.getTouchableRegion(this.mTmpRegion);
        this.mTouchExcludeRegion.op(this.mTmpRegion, Region.Op.UNION);
      }
    }
    forAllWindows(
        new ToBooleanFunction() { // from class:
                                  // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda51
          public final boolean apply(Object obj) {
            boolean lambda$updateTouchExcludeRegion$24;
            lambda$updateTouchExcludeRegion$24 =
                DisplayContent.this.lambda$updateTouchExcludeRegion$24((WindowState) obj);
            return lambda$updateTouchExcludeRegion$24;
          }
        },
        true);
    amendWindowTapExcludeRegion(this.mTouchExcludeRegion);
    this.mTapDetector.setTouchExcludeRegion(this.mTouchExcludeRegion);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$updateTouchExcludeRegion$24(WindowState windowState) {
    int i;
    boolean z = true;
    if (windowState.mActivityRecord == null && ((i = windowState.mAttrs.type) < 1 || i > 99)) {
      z = false;
      if (windowState.isVisible() && (windowState.mAttrs.flags & 16) == 0) {
        WindowState windowState2 = this.mInputMethodWindow;
        if ((windowState2 != null && windowState2 == windowState)
            || this.mTapExcludedWindows.contains(windowState)) {
          return false;
        }
        windowState.getTouchableRegion(this.mTmpRegion);
        this.mTouchExcludeRegion.op(this.mTmpRegion, Region.Op.UNION);
      }
    }
    return z;
  }

  /* renamed from: processTaskForTouchExcludeRegion, reason: merged with bridge method [inline-methods] */
  public final void lambda$updateTouchExcludeRegion$23(Task task, Task task2, int i) {
    WindowState findMainWindow;
    ActivityRecord topVisibleActivity = task.getTopVisibleActivity();
    if (topVisibleActivity == null || !topVisibleActivity.hasContentToDisplay()) {
      return;
    }
    if (!inFreeformWindowingMode()
        || ((findMainWindow = topVisibleActivity.findMainWindow()) != null
            && findMainWindow.isVisibleByPolicy())) {
      if (task.isActivityTypeHome() && task.isVisible() && task.isResizeable()) {
        task.getDisplayArea().getBounds(this.mTmpRect);
      } else {
        task.getDimBounds(this.mTmpRect);
      }
      if (task == task2) {
        this.mTmpRect2.set(this.mTmpRect);
      }
      boolean inFreeformWindowingMode = task.inFreeformWindowingMode();
      if (task != task2 || inFreeformWindowingMode) {
        if (inFreeformWindowingMode) {
          int i2 = -i;
          this.mTmpRect.inset(i2, i2);
          this.mTmpRect.inset(
              getInsetsStateController()
                  .getRawInsetsState()
                  .calculateInsets(
                      this.mTmpRect,
                      WindowInsets.Type.systemBars() | WindowInsets.Type.ime(),
                      false));
          if (task.isAlwaysOnTopFreeform()) {
            this.mTmpAlwaysOnTopFreeformRegion.op(this.mTmpRect, Region.Op.UNION);
          }
        }
        this.mTouchExcludeRegion.op(this.mTmpRect, Region.Op.DIFFERENCE);
      }
    }
  }

  public final void amendWindowTapExcludeRegion(Region region) {
    Region obtain = Region.obtain();
    for (int size = this.mTapExcludeProvidingWindows.size() - 1; size >= 0; size--) {
      ((WindowState) this.mTapExcludeProvidingWindows.valueAt(size)).getTapExcludeRegion(obtain);
      region.op(obtain, Region.Op.UNION);
    }
    obtain.recycle();
  }

  @Override // com.android.server.wm.WindowContainer
  public void switchUser(int i) {
    super.switchUser(i);
    this.mWmService.mWindowsChanged = true;
    this.mDisplayPolicy.switchUser();
  }

  public final boolean shouldDeferRemoval() {
    return isAnimating(3) || this.mTransitionController.isTransitionOnDisplay(this);
  }

  @Override // com.android.server.wm.WindowContainer
  public void removeIfPossible() {
    if (shouldDeferRemoval()) {
      this.mDeferredRemoval = true;
    } else {
      removeImmediately();
    }
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
  public void removeImmediately() {
    DisplayContent defaultDisplay;
    CoverPolicy coverPolicy;
    this.mDeferredRemoval = false;
    try {
      this.mOpeningApps.clear();
      this.mClosingApps.clear();
      this.mChangingContainers.clear();
      this.mUnknownAppVisibilityController.clear();
      this.mAppTransition.removeAppTransitionTimeoutCallbacks();
      this.mTransitionController.unregisterLegacyListener(this.mFixedRotationTransitionListener);
      handleAnimatingStoppedAndTransition();
      this.mWmService.stopFreezingDisplayLocked();
      this.mDeviceStateController.unregisterDeviceStateCallback(this.mDeviceStateConsumer);
      super.removeImmediately();
      this.mPointerEventDispatcher.dispose();
      setRotationAnimation(null);
      setRemoteInsetsController(null);
      this.mOverlayLayer.release();
      this.mInputOverlayLayer.release();
      this.mA11yOverlayLayer.release();
      this.mWindowingLayer.release();
      this.mInputMonitor.onDisplayRemoved();
      this.mWmService.mDisplayNotificationController.dispatchDisplayRemoved(this);
      this.mDisplayRotation.onDisplayRemoved();
      this.mWmService.mAccessibilityController.onDisplayRemoved(this.mDisplayId);
      this.mRootWindowContainer
          .mTaskSupervisor
          .getKeyguardController()
          .onDisplayRemoved(this.mDisplayId);
      this.mWallpaperController.resetLargestDisplay(this.mDisplay);
      this.mAtmService.mFreeformController.onDisplayRemovedLocked(this.mDisplayId);
      if (this.mDisplayId == 4
          && (defaultDisplay = this.mRootWindowContainer.getDefaultDisplay()) != null
          && (coverPolicy = defaultDisplay.mDisplayPolicy.mExt.mCoverPolicy) != null) {
        coverPolicy.removeViewCoverDisplay(this.mDisplayId);
      }
      MultiWindowPointerEventListener multiWindowPointerEventListener =
          this.mMultiWindowPointerEventListener;
      if (multiWindowPointerEventListener != null) {
        unregisterPointerEventListener(multiWindowPointerEventListener);
      }
      this.mDisplayReady = false;
      getPendingTransaction().apply();
      this.mWmService.mWindowPlacerLocked.requestTraversal();
      DisplayRotationCompatPolicy displayRotationCompatPolicy = this.mDisplayRotationCompatPolicy;
      if (displayRotationCompatPolicy != null) {
        displayRotationCompatPolicy.dispose();
      }
    } catch (Throwable th) {
      this.mDisplayReady = false;
      throw th;
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean handleCompleteDeferredRemoval() {
    boolean z = super.handleCompleteDeferredRemoval() || shouldDeferRemoval();
    if (z || !this.mDeferredRemoval) {
      return z;
    }
    removeImmediately();
    return false;
  }

  public void adjustForImeIfNeeded() {
    WindowState windowState = this.mInputMethodWindow;
    this.mPinnedTaskController.setAdjustedForIme(
        windowState != null && windowState.isVisible() && windowState.isDisplayed(),
        getInputMethodWindowVisibleHeight());
  }

  public int getInputMethodWindowVisibleHeight() {
    InsetsState rawInsetsState = getInsetsStateController().getRawInsetsState();
    InsetsSource peekSource = rawInsetsState.peekSource(InsetsSource.ID_IME);
    if (peekSource == null || !peekSource.isVisible()) {
      return 0;
    }
    Rect visibleFrame =
        peekSource.getVisibleFrame() != null ? peekSource.getVisibleFrame() : peekSource.getFrame();
    Rect rect = this.mTmpRect;
    rect.set(rawInsetsState.getDisplayFrame());
    rect.inset(
        rawInsetsState.calculateInsets(
            rect, WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(), false));
    return rect.bottom - visibleFrame.top;
  }

  public void rotateBounds(int i, int i2, Rect rect) {
    getBounds(this.mTmpRect, i);
    RotationUtils.rotateBounds(rect, this.mTmpRect, i, i2);
  }

  public void setRotationAnimation(ScreenRotationAnimation screenRotationAnimation) {
    ScreenRotationAnimation screenRotationAnimation2 = this.mScreenRotationAnimation;
    this.mScreenRotationAnimation = screenRotationAnimation;
    if (screenRotationAnimation2 != null) {
      screenRotationAnimation2.kill();
    }
    if (screenRotationAnimation == null || !screenRotationAnimation.hasScreenshot()) {
      return;
    }
    startAsyncRotationIfNeeded();
  }

  public ScreenRotationAnimation getRotationAnimation() {
    return this.mScreenRotationAnimation;
  }

  public void requestChangeTransitionIfNeeded(
      int i, TransitionRequestInfo.DisplayChange displayChange) {
    DisplayAnimationPair selectDisplayChangeAnimation;
    int i2;
    AsyncRotationController asyncRotationController;
    if (this.mLastHasContent) {
      TransitionController transitionController = this.mTransitionController;
      if (transitionController.isCollecting()) {
        if (displayChange != null) {
          throw new IllegalArgumentException("Provided displayChange for non-new transition");
        }
        if (!transitionController.isCollecting(this)) {
          transitionController.collect(this);
          startAsyncRotationIfNeeded();
          if (this.mFixedRotationLaunchingApp != null) {
            setSeamlessTransitionForFixedRotation(transitionController.getCollectingTransition());
            return;
          }
          return;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
            && (asyncRotationController = this.mAsyncRotationController) != null
            && asyncRotationController.shouldFinishAsyncRotationForSameChanges(
                transitionController.getCollectingTransition())) {
          Slog.d(
              StartingSurfaceController.TAG,
              "Call finishAsyncRotationIfPossible, if final changes are the same as the start"
                  + " changes in transition");
          finishAsyncRotationIfPossible();
          this.mDisplaySwitchTransitionLauncher.unsetFoldChanging(
              "finishAsyncRotationForSameChanges");
          return;
        }
        return;
      }
      Transition requestTransitionIfNeeded =
          transitionController.requestTransitionIfNeeded(6, 0, this, this, null, displayChange);
      if (requestTransitionIfNeeded != null) {
        this.mAtmService.startLaunchPowerMode(2);
        if (this.mFixedRotationLaunchingApp != null) {
          setSeamlessTransitionForFixedRotation(requestTransitionIfNeeded);
        } else if (isRotationChanging()) {
          if (displayChange != null
              && this.mDisplayRotation.shouldRotateSeamlessly(
                  displayChange.getStartRotation(), displayChange.getEndRotation(), false)) {
            requestTransitionIfNeeded.onSeamlessRotating(this);
          }
          this.mWmService.mLatencyTracker.onActionStart(6);
          transitionController.mTransitionMetricsReporter.associate(
              requestTransitionIfNeeded.getToken(),
              new LongConsumer() { // from class:
                                   // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda1
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                  DisplayContent.this.lambda$requestChangeTransitionIfNeeded$25(j);
                }
              });
          startAsyncRotation(false);
        } else {
          if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BLACK_SNAPSHOT && this.mHasBlackSnapshot) {
            requestTransitionIfNeeded.setHasBlackSnapshot();
          }
          if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_DISPLAY_CHANGE
              && displayChange != null
              && ((i2 =
                          (selectDisplayChangeAnimation =
                                  selectDisplayChangeAnimation(displayChange))
                              .mEnter)
                      != 0
                  || selectDisplayChangeAnimation.mExit != 0)) {
            transitionController.setOverrideAnimation(
                TransitionInfo.AnimationOptions.makeCustomDisplayChangeAnimOptions(
                    i2, selectDisplayChangeAnimation.mExit),
                null,
                null);
          }
        }
        requestTransitionIfNeeded.setKnownConfigChanges(this, i);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$requestChangeTransitionIfNeeded$25(long j) {
    this.mWmService.mLatencyTracker.onActionEnd(6);
  }

  public final void setSeamlessTransitionForFixedRotation(Transition transition) {
    transition.setSeamlessRotation(this);
    AsyncRotationController asyncRotationController = this.mAsyncRotationController;
    if (asyncRotationController != null) {
      asyncRotationController.keepAppearanceInPreviousRotation();
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean inTransition() {
    return this.mScreenRotationAnimation != null || super.inTransition();
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
            // com.android.server.wm.ConfigurationContainer
  public void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
    if (i != 2 || isVisible()) {
      long start = protoOutputStream.start(j);
      super.dumpDebug(protoOutputStream, 1146756268053L, i);
      protoOutputStream.write(1120986464258L, this.mDisplayId);
      protoOutputStream.write(1120986464265L, this.mBaseDisplayDensity);
      this.mDisplayInfo.dumpDebug(protoOutputStream, 1146756268042L);
      this.mDisplayRotation.dumpDebug(protoOutputStream, 1146756268065L);
      ScreenRotationAnimation rotationAnimation = getRotationAnimation();
      if (rotationAnimation != null) {
        rotationAnimation.dumpDebug(protoOutputStream, 1146756268044L);
      }
      this.mDisplayFrames.dumpDebug(protoOutputStream, 1146756268045L);
      protoOutputStream.write(1120986464295L, this.mMinSizeOfResizeableTaskDp);
      if (this.mTransitionController.isShellTransitionsEnabled()) {
        this.mTransitionController.dumpDebugLegacy(protoOutputStream, 1146756268048L);
      } else {
        this.mAppTransition.dumpDebug(protoOutputStream, 1146756268048L);
      }
      ActivityRecord activityRecord = this.mFocusedApp;
      if (activityRecord != null) {
        activityRecord.writeNameToProto(protoOutputStream, 1138166333455L);
      }
      for (int size = this.mOpeningApps.size() - 1; size >= 0; size--) {
        ((ActivityRecord) this.mOpeningApps.valueAt(size))
            .writeIdentifierToProto(protoOutputStream, 2246267895825L);
      }
      for (int size2 = this.mClosingApps.size() - 1; size2 >= 0; size2--) {
        ((ActivityRecord) this.mClosingApps.valueAt(size2))
            .writeIdentifierToProto(protoOutputStream, 2246267895826L);
      }
      Task focusedRootTask = getFocusedRootTask();
      if (focusedRootTask != null) {
        protoOutputStream.write(1120986464279L, focusedRootTask.getRootTaskId());
        ActivityRecord focusedActivity = focusedRootTask.getDisplayArea().getFocusedActivity();
        if (focusedActivity != null) {
          focusedActivity.writeIdentifierToProto(protoOutputStream, 1146756268056L);
        }
      } else {
        protoOutputStream.write(1120986464279L, -1);
      }
      protoOutputStream.write(1133871366170L, isReady());
      protoOutputStream.write(1133871366180L, isSleeping());
      for (int i2 = 0; i2 < this.mAllSleepTokens.size(); i2++) {
        ((RootWindowContainer.SleepToken) this.mAllSleepTokens.get(i2))
            .writeTagToProto(protoOutputStream, 2237677961253L);
      }
      WindowState windowState = this.mImeLayeringTarget;
      if (windowState != null) {
        windowState.dumpDebug(protoOutputStream, 1146756268059L, i);
      }
      InputTarget inputTarget = this.mImeInputTarget;
      if (inputTarget != null) {
        inputTarget.dumpProto(protoOutputStream, 1146756268060L, i);
      }
      InsetsControlTarget insetsControlTarget = this.mImeControlTarget;
      if (insetsControlTarget != null && insetsControlTarget.getWindow() != null) {
        this.mImeControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268061L, i);
      }
      WindowState windowState2 = this.mCurrentFocus;
      if (windowState2 != null) {
        windowState2.dumpDebug(protoOutputStream, 1146756268062L, i);
      }
      InsetsStateController insetsStateController = this.mInsetsStateController;
      if (insetsStateController != null) {
        insetsStateController.dumpDebug(protoOutputStream, i);
      }
      protoOutputStream.write(1120986464290L, getImePolicy());
      Iterator it = getKeepClearAreas().iterator();
      while (it.hasNext()) {
        ((Rect) it.next()).dumpDebug(protoOutputStream, 2246267895846L);
      }
      protoOutputStream.end(start);
    }
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
  public void dump(final PrintWriter printWriter, final String str, final boolean z) {
    Task rootCellStageTask;
    printWriter.print(str);
    StringBuilder sb = new StringBuilder();
    sb.append("Display: mDisplayId=");
    sb.append(this.mDisplayId);
    sb.append(isOrganized() ? " (organized)" : "");
    printWriter.println(sb.toString());
    String str2 = "  " + str;
    printWriter.print(str2);
    printWriter.print("init=");
    printWriter.print(this.mInitialDisplayWidth);
    printWriter.print("x");
    printWriter.print(this.mInitialDisplayHeight);
    printWriter.print(" ");
    printWriter.print(this.mInitialDisplayDensity);
    printWriter.print("dpi");
    printWriter.print(" mMinSizeOfResizeableTaskDp=");
    printWriter.print(this.mMinSizeOfResizeableTaskDp);
    if (this.mInitialDisplayWidth != this.mBaseDisplayWidth
        || this.mInitialDisplayHeight != this.mBaseDisplayHeight
        || this.mInitialDisplayDensity != this.mBaseDisplayDensity) {
      printWriter.print(" base=");
      printWriter.print(this.mBaseDisplayWidth);
      printWriter.print("x");
      printWriter.print(this.mBaseDisplayHeight);
      printWriter.print(" ");
      printWriter.print(this.mBaseDisplayDensity);
      printWriter.print("dpi");
    }
    if (this.mDisplayScalingDisabled) {
      printWriter.println(" noscale");
    }
    printWriter.print(" cur=");
    printWriter.print(this.mDisplayInfo.logicalWidth);
    printWriter.print("x");
    printWriter.print(this.mDisplayInfo.logicalHeight);
    printWriter.print(" app=");
    printWriter.print(this.mDisplayInfo.appWidth);
    printWriter.print("x");
    printWriter.print(this.mDisplayInfo.appHeight);
    printWriter.print(" rng=");
    printWriter.print(this.mDisplayInfo.smallestNominalAppWidth);
    printWriter.print("x");
    printWriter.print(this.mDisplayInfo.smallestNominalAppHeight);
    printWriter.print(PackageManagerShellCommandDataLoader.STDIN_PATH);
    printWriter.print(this.mDisplayInfo.largestNominalAppWidth);
    printWriter.print("x");
    printWriter.println(this.mDisplayInfo.largestNominalAppHeight);
    printWriter.print(
        str2 + "deferred=" + this.mDeferredRemoval + " mLayoutNeeded=" + this.mLayoutNeeded);
    StringBuilder sb2 = new StringBuilder();
    sb2.append(" mTouchExcludeRegion=");
    sb2.append(this.mTouchExcludeRegion);
    printWriter.println(sb2.toString());
    printWriter.println();
    printWriter.print(str2 + "initCutout=");
    printWriter.println(this.mInitialDisplayCutout);
    printWriter.print(str2 + "baseCutout=");
    printWriter.println(this.mBaseDisplayCutout);
    UdcCutoutPolicy udcCutoutPolicy = this.mUdcCutoutPolicy;
    if (udcCutoutPolicy != null) {
      udcCutoutPolicy.dump(printWriter, str2);
    }
    if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT && this.mIsOverlappingWithCutoutAsDefault) {
      printWriter.println();
      printWriter.println(str2 + "mIsOverlappingWithCutoutAsDefault=true");
    }
    printWriter.println();
    super.dump(printWriter, str, z);
    printWriter.print(str);
    printWriter.print("mLayoutSeq=");
    printWriter.println(this.mLayoutSeq);
    printWriter.print("  mCurrentFocus=");
    printWriter.println(this.mCurrentFocus);
    printWriter.print("  mFocusedApp=");
    printWriter.println(this.mFocusedApp);
    if (this.mFixedRotationLaunchingApp != null) {
      printWriter.println("  mFixedRotationLaunchingApp=" + this.mFixedRotationLaunchingApp);
    }
    printWriter.println();
    printWriter.print(str + "mHoldScreenWindow=");
    printWriter.print(this.mHoldScreenWindow);
    printWriter.println();
    printWriter.print(str + "mObscuringWindow=");
    printWriter.print(this.mObscuringWindow);
    printWriter.println();
    printWriter.print(str + "mLastWakeLockHoldingWindow=");
    printWriter.print(this.mLastWakeLockHoldingWindow);
    printWriter.println();
    printWriter.print(str + "mLastWakeLockObscuringWindow=");
    printWriter.println(this.mLastWakeLockObscuringWindow);
    printWriter.println();
    this.mWallpaperController.dump(printWriter, "  ");
    if (this.mSystemGestureExclusionListeners.getRegisteredCallbackCount() > 0) {
      printWriter.println();
      printWriter.print("  mSystemGestureExclusion=");
      printWriter.println(this.mSystemGestureExclusion);
    }
    Set keepClearAreas = getKeepClearAreas();
    if (!keepClearAreas.isEmpty()) {
      printWriter.println();
      printWriter.print("  keepClearAreas=");
      printWriter.println(keepClearAreas);
    }
    printWriter.println();
    printWriter.println(str + "Display areas in top down Z order:");
    dumpChildDisplayArea(printWriter, str2, z);
    printWriter.println();
    printWriter.println(str + "Task display areas in top down Z order:");
    forAllTaskDisplayAreas(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda31
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.lambda$dump$26(printWriter, str, z, (TaskDisplayArea) obj);
          }
        });
    printWriter.println();
    ScreenRotationAnimation rotationAnimation = getRotationAnimation();
    if (rotationAnimation != null) {
      printWriter.println("  mScreenRotationAnimation:");
      rotationAnimation.printTo(str2, printWriter);
    } else if (z) {
      printWriter.println("  no ScreenRotationAnimation ");
    }
    printWriter.println();
    Task rootHomeTask = getDefaultTaskDisplayArea().getRootHomeTask();
    if (rootHomeTask != null) {
      printWriter.println(str + "rootHomeTask=" + rootHomeTask.getName());
    }
    Task rootPinnedTask = getDefaultTaskDisplayArea().getRootPinnedTask();
    if (rootPinnedTask != null) {
      printWriter.println(str + "rootPinnedTask=" + rootPinnedTask.getName());
    }
    Task rootTask = getDefaultTaskDisplayArea().getRootTask(0, 3);
    if (rootTask != null) {
      printWriter.println(str + "rootRecentsTask=" + rootTask.getName());
    }
    Task rootTask2 = getRootTask(0, 5);
    if (rootTask2 != null) {
      printWriter.println(str + "rootDreamTask=" + rootTask2.getName());
    }
    printWriter.println();
    this.mPinnedTaskController.dump(str, printWriter);
    Task rootMainStageTask = getDefaultTaskDisplayArea().getRootMainStageTask();
    if (rootMainStageTask != null) {
      printWriter.println(str + "rootMainStageTask=" + rootMainStageTask.getName());
    }
    Task rootSideStageTask = getDefaultTaskDisplayArea().getRootSideStageTask();
    if (rootSideStageTask != null) {
      printWriter.println(str + "rootSideStageTask=" + rootSideStageTask.getName());
    }
    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER
        && (rootCellStageTask = getDefaultTaskDisplayArea().getRootCellStageTask()) != null) {
      printWriter.println(str + "rootCellStageTask=" + rootCellStageTask.getName());
    }
    printWriter.println();
    this.mDisplayFrames.dump(str, printWriter);
    printWriter.println();
    this.mDisplayPolicy.dump(str, printWriter);
    printWriter.println();
    this.mDisplayRotation.dump(str, printWriter);
    printWriter.println();
    this.mInputMonitor.dump(printWriter, "  ");
    printWriter.println();
    this.mInsetsStateController.dump(str, printWriter);
    this.mInsetsPolicy.dump(str, printWriter);
    this.mDwpcHelper.dump(str, printWriter);
    printWriter.println();
    if (isRemoteAppDisplay()) {
      printWriter.println();
      printWriter.println(str + "isRemoteAppDisplay=true");
    }
    if (CoreRune.MT_DEX_SIZE_COMPAT_MODE) {
      DexSizeCompatController.getInstance().dump(this, printWriter, "  ");
    }
  }

  public static /* synthetic */ void lambda$dump$26(
      PrintWriter printWriter, String str, boolean z, TaskDisplayArea taskDisplayArea) {
    taskDisplayArea.dump(printWriter, str + "  ", z);
  }

  @Override // com.android.server.wm.DisplayArea
  public String toString() {
    return "Display{#"
        + this.mDisplayId
        + " state="
        + Display.stateToString(this.mDisplayInfo.state)
        + " size="
        + this.mDisplayInfo.logicalWidth
        + "x"
        + this.mDisplayInfo.logicalHeight
        + " "
        + Surface.rotationToString(this.mDisplayInfo.rotation)
        + "}";
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.ConfigurationContainer
  public String getName() {
    return "Display " + this.mDisplayId + " name=\"" + this.mDisplayInfo.name + "\"";
  }

  public WindowState getTouchableWinAtPointLocked(float f, float f2) {
    final int i = (int) f;
    final int i2 = (int) f2;
    return getWindow(
        new Predicate() { // from class:
                          // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda37
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$getTouchableWinAtPointLocked$27;
            lambda$getTouchableWinAtPointLocked$27 =
                DisplayContent.this.lambda$getTouchableWinAtPointLocked$27(
                    i, i2, (WindowState) obj);
            return lambda$getTouchableWinAtPointLocked$27;
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$getTouchableWinAtPointLocked$27(
      int i, int i2, WindowState windowState) {
    int i3 = windowState.mAttrs.flags;
    if (!windowState.isVisible() || (i3 & 16) != 0) {
      return false;
    }
    windowState.getVisibleBounds(this.mTmpRect);
    if (!this.mTmpRect.contains(i, i2)) {
      return false;
    }
    windowState.getTouchableRegion(this.mTmpRegion);
    return this.mTmpRegion.contains(i, i2) || (i3 & 40) == 0;
  }

  public boolean canAddToastWindowForUid(final int i) {
    return getWindow(
                new Predicate() { // from class:
                                  // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda45
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean lambda$canAddToastWindowForUid$28;
                    lambda$canAddToastWindowForUid$28 =
                        DisplayContent.lambda$canAddToastWindowForUid$28(i, (WindowState) obj);
                    return lambda$canAddToastWindowForUid$28;
                  }
                })
            != null
        || getWindow(
                new Predicate() { // from class:
                                  // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda46
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    boolean lambda$canAddToastWindowForUid$29;
                    lambda$canAddToastWindowForUid$29 =
                        DisplayContent.lambda$canAddToastWindowForUid$29(i, (WindowState) obj);
                    return lambda$canAddToastWindowForUid$29;
                  }
                })
            == null;
  }

  public static /* synthetic */ boolean lambda$canAddToastWindowForUid$28(
      int i, WindowState windowState) {
    return windowState.mOwnerUid == i && windowState.isFocused();
  }

  public static /* synthetic */ boolean lambda$canAddToastWindowForUid$29(
      int i, WindowState windowState) {
    return windowState.mAttrs.type == 2005
        && windowState.mOwnerUid == i
        && !windowState.mPermanentlyHidden
        && !windowState.mWindowRemovalAllowed;
  }

  public void scheduleToastWindowsTimeoutIfNeededLocked(
      WindowState windowState, WindowState windowState2) {
    if (windowState != null) {
      if (windowState2 == null || windowState2.mOwnerUid != windowState.mOwnerUid) {
        this.mTmpWindow = windowState;
        forAllWindows(this.mScheduleToastTimeout, false);
      }
    }
  }

  public boolean canStealTopFocus() {
    return (this.mDisplayInfo.flags & IInstalld.FLAG_USE_QUOTA) == 0;
  }

  public WindowState findFocusedWindowIfNeeded(int i) {
    if (hasOwnFocus() || i == -1) {
      return findFocusedWindow();
    }
    return null;
  }

  public WindowState findFocusedWindow() {
    this.mTmpWindow = null;
    forAllWindows(this.mFindFocusedWindow, true);
    WindowState windowState = this.mTmpWindow;
    if (windowState != null) {
      return windowState;
    }
    if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
          620519522,
          1,
          (String) null,
          new Object[] {Long.valueOf(getDisplayId())});
    }
    return null;
  }

  public boolean updateFocusedWindowLocked(int i, boolean z, int i2) {
    boolean z2;
    WindowManager.LayoutParams layoutParams;
    String str;
    WindowManager.LayoutParams layoutParams2;
    String str2;
    ActivityRecord activityRecord;
    WindowState windowState = this.mCurrentFocus;
    if (windowState != null
        && this.mTransitionController.shouldKeepFocus(windowState)
        && (activityRecord = this.mFocusedApp) != null
        && this.mCurrentFocus.isDescendantOf(activityRecord)
        && this.mCurrentFocus.isVisible()
        && this.mCurrentFocus.isFocusable()) {
      if (ProtoLogCache.WM_DEBUG_FOCUS_enabled) {
        ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_FOCUS, -464564167, 0, (String) null, (Object[]) null);
      }
      return false;
    }
    WindowState findFocusedWindowIfNeeded = findFocusedWindowIfNeeded(i2);
    if (this.mCurrentFocus == findFocusedWindowIfNeeded) {
      return false;
    }
    if (this.mInputMethodWindow != null) {
      z2 = this.mImeLayeringTarget != computeImeTarget(true);
      if (i != 1 && i != 3) {
        assignWindowLayers(false);
      }
      if (z2) {
        this.mWmService.mWindowsChanged = true;
        setLayoutNeeded();
        findFocusedWindowIfNeeded = findFocusedWindowIfNeeded(i2);
      }
    } else {
      z2 = false;
    }
    if (ProtoLogCache.WM_FORCE_DEBUG_FOCUS_LIGHT_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_FORCE_DEBUG_FOCUS_LIGHT,
          1885212618,
          16,
          "Changing focus from %s to %s displayId=%d Callers=%s",
          new Object[] {
            String.valueOf(this.mCurrentFocus),
            String.valueOf(findFocusedWindowIfNeeded),
            Long.valueOf(getDisplayId()),
            String.valueOf(Debug.getCallers(4))
          });
    }
    WindowState windowState2 = this.mCurrentFocus;
    this.mCurrentFocus = findFocusedWindowIfNeeded;
    if (findFocusedWindowIfNeeded != null
        && (layoutParams2 = findFocusedWindowIfNeeded.mAttrs) != null
        && (str2 = layoutParams2.packageName) != null) {
      try {
        this.mAtmService.mAmInternal.onPackageResumedFG(
            (List) null,
            str2,
            (String) null,
            false,
            (Intent) null,
            findFocusedWindowIfNeeded.mShowUserId);
      } catch (Exception e) {
        Slog.e("MARs", "Error occurred in updateFocusedWindowLocked()" + e);
      }
    } else if (windowState2 != null
        && (layoutParams = windowState2.mAttrs) != null
        && (str = layoutParams.packageName) != null) {
      try {
        this.mAtmService.mAmInternal.onPackageResumedFG(
            (List) null, str, (String) null, false, (Intent) null, windowState2.mShowUserId);
      } catch (Exception e2) {
        Slog.e("MARs", "Error occurred in updateFocusedWindowLocked()" + e2);
      }
    }
    this.mOldFocus = windowState2;
    if (findFocusedWindowIfNeeded != null) {
      this.mWinAddedSinceNullFocus.clear();
      this.mWinRemovedSinceNullFocus.clear();
      if (findFocusedWindowIfNeeded.canReceiveKeys()) {
        findFocusedWindowIfNeeded.mToken.paused = false;
      }
    }
    getDisplayPolicy().focusChangedLw(windowState2, findFocusedWindowIfNeeded);
    this.mAtmService.mBackNavigationController.onFocusChanged(findFocusedWindowIfNeeded);
    if (z2 && windowState2 != this.mInputMethodWindow) {
      if (i == 2) {
        performLayout(true, z);
      } else if (i == 3) {
        assignWindowLayers(false);
      }
    }
    if ((this.mDisplayId == 2
            || (CoreRune.MT_NEW_DEX_TASK_ORDERING && !this.isDefaultDisplay && isNewDexMode()))
        && this.mInputMethodWindow == null
        && findFocusedWindowIfNeeded != null
        && windowState2 != null
        && findFocusedWindowIfNeeded.getTask() != null
        && !findFocusedWindowIfNeeded.getTask().equals(windowState2.getTask())) {
      assignWindowLayers(false);
    }
    if (i != 1) {
      getInputMonitor().setInputFocusLw(findFocusedWindowIfNeeded, z);
    }
    adjustForImeIfNeeded();
    updateKeepClearAreas();
    scheduleToastWindowsTimeoutIfNeededLocked(windowState2, findFocusedWindowIfNeeded);
    if (i == 2) {
      this.pendingLayoutChanges |= 8;
    }
    if (this.mWmService.mAccessibilityController.hasCallbacks()) {
      this.mWmService.f1749mH.sendMessage(
          PooledLambda.obtainMessage(
              new Consumer() { // from class:
                               // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda30
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  DisplayContent.this.updateAccessibilityOnWindowFocusChanged(
                      (AccessibilityController) obj);
                }
              },
              this.mWmService.mAccessibilityController));
    }
    return true;
  }

  public void updateAccessibilityOnWindowFocusChanged(
      AccessibilityController accessibilityController) {
    accessibilityController.onWindowFocusChangedNot(getDisplayId());
  }

  public boolean setFocusedApp(ActivityRecord activityRecord) {
    if (activityRecord != null) {
      DisplayContent displayContent = activityRecord.getDisplayContent();
      if (displayContent != this) {
        StringBuilder sb = new StringBuilder();
        sb.append(activityRecord);
        sb.append(" is not on ");
        sb.append(getName());
        sb.append(" but ");
        sb.append(displayContent != null ? displayContent.getName() : "none");
        throw new IllegalStateException(sb.toString());
      }
      if (CoreRune.SYSFW_APP_SPEG && (this.mDisplayInfo.flags & 32768) != 0) {
        Slog.i("SPEG", "Do not set focus");
        return false;
      }
      onLastFocusedTaskDisplayAreaChanged(activityRecord.getDisplayArea());
    }
    ActivityRecord activityRecord2 = this.mFocusedApp;
    if (activityRecord2 == activityRecord) {
      return false;
    }
    checkFocusMonitoringPolicy(activityRecord2, "lost");
    if (ProtoLogCache.WM_DEBUG_FOCUS_LIGHT_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
          -639217716,
          4,
          (String) null,
          new Object[] {
            String.valueOf(activityRecord),
            Long.valueOf(getDisplayId()),
            String.valueOf(Debug.getCallers(4))
          });
    }
    ActivityRecord activityRecord3 = this.mFocusedApp;
    Task task = activityRecord3 != null ? activityRecord3.getTask() : null;
    Task task2 = activityRecord != null ? activityRecord.getTask() : null;
    this.mFocusedApp = activityRecord;
    if (task != task2) {
      if (task != null) {
        task.onAppFocusChanged(false);
      }
      if (task2 != null) {
        task2.onAppFocusChanged(true);
      }
    }
    getInputMonitor().setFocusedAppLw(activityRecord);
    updateTouchExcludeRegion();
    this.mWmService.mExt.mPolicyExt.updateTopActivity(
        convertActivityResultToComponentName(activityRecord));
    checkFocusMonitoringPolicy(this.mFocusedApp, "gained");
    return true;
  }

  public void onRunningActivityChanged() {
    this.mDwpcHelper.onRunningActivityChanged();
  }

  public void onLastFocusedTaskDisplayAreaChanged(TaskDisplayArea taskDisplayArea) {
    this.mOrientationRequestingTaskDisplayArea = taskDisplayArea;
  }

  public TaskDisplayArea getOrientationRequestingTaskDisplayArea() {
    return this.mOrientationRequestingTaskDisplayArea;
  }

  public void assignWindowLayers(boolean z) {
    Trace.traceBegin(32L, "assignWindowLayers");
    assignChildLayers(getSyncTransaction());
    if (z) {
      setLayoutNeeded();
    }
    scheduleAnimation();
    Trace.traceEnd(32L);
  }

  public boolean destroyLeakedSurfaces() {
    this.mTmpWindow = null;
    final SurfaceControl.Transaction transaction =
        (SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get();
    forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda60
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$destroyLeakedSurfaces$30(transaction, (WindowState) obj);
          }
        },
        false);
    transaction.apply();
    return this.mTmpWindow != null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$destroyLeakedSurfaces$30(
      SurfaceControl.Transaction transaction, WindowState windowState) {
    WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
    if (windowStateAnimator.mSurfaceController == null) {
      return;
    }
    if (!this.mWmService.mSessions.contains(windowStateAnimator.mSession)) {
      Slog.w(
          StartingSurfaceController.TAG,
          "LEAKED SURFACE (session doesn't exist): "
              + windowState
              + " surface="
              + windowStateAnimator.mSurfaceController
              + " token="
              + windowState.mToken
              + " pid="
              + windowState.mSession.mPid
              + " uid="
              + windowState.mSession.mUid);
      windowStateAnimator.destroySurface(transaction);
      this.mWmService.mForceRemoves.add(windowState);
      this.mTmpWindow = windowState;
      return;
    }
    ActivityRecord activityRecord = windowState.mActivityRecord;
    if (activityRecord == null || activityRecord.isClientVisible()) {
      return;
    }
    Slog.w(
        StartingSurfaceController.TAG,
        "LEAKED SURFACE (app token hidden): "
            + windowState
            + " surface="
            + windowStateAnimator.mSurfaceController
            + " token="
            + windowState.mActivityRecord);
    if (ProtoLogCache.WM_SHOW_TRANSACTIONS_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_SHOW_TRANSACTIONS,
          -1938839202,
          0,
          (String) null,
          new Object[] {String.valueOf(windowState)});
    }
    windowStateAnimator.destroySurface(transaction);
    this.mTmpWindow = windowState;
  }

  public boolean hasAlertWindowSurfaces() {
    for (int size = this.mWmService.mSessions.size() - 1; size >= 0; size--) {
      if (((Session) this.mWmService.mSessions.valueAt(size)).hasAlertWindowSurfaces(this)) {
        return true;
      }
    }
    return false;
  }

  public void setInputMethodWindowLocked(WindowState windowState) {
    this.mInputMethodWindow = windowState;
    if (windowState != null) {
      this.mAtmService.onImeWindowSetOnDisplayArea(
          windowState.mSession.mPid, this.mImeWindowsContainer);
    }
    this.mInsetsStateController
        .getImeSourceProvider()
        .setWindowContainer(windowState, this.mDisplayPolicy.getImeSourceFrameProvider(), null);
    computeImeTarget(true);
    updateImeControlTarget();
  }

  public WindowState computeImeTarget(boolean z) {
    WindowState windowState = this.mInputMethodWindow;
    if (windowState == null) {
      if (z) {
        setImeLayeringTargetInner(null);
      }
      return null;
    }
    windowState.updateLetterboxDirectionIfNeeded();
    WindowState windowState2 = this.mImeLayeringTarget;
    if (!canUpdateImeTarget()) {
      this.mUpdateImeRequestedWhileDeferred = true;
      return windowState2;
    }
    this.mUpdateImeTarget = z;
    WindowState window = getWindow(this.mComputeImeTargetPredicate);
    if (windowState2 != null
        && windowState2.isPopOver()
        && !windowState2.mRemoved
        && windowState2.isDisplayed()
        && windowState2.mAnimatingExit
        && !windowState2.inFreeformWindowingMode()) {
      return windowState2;
    }
    if (window == null) {
      if (z) {
        setImeLayeringTargetInner(null);
      }
      return null;
    }
    if (z) {
      setImeLayeringTargetInner(window);
    }
    return window;
  }

  public void computeImeTargetIfNeeded(ActivityRecord activityRecord) {
    WindowState windowState = this.mImeLayeringTarget;
    if (windowState == null || windowState.mActivityRecord != activityRecord) {
      return;
    }
    computeImeTarget(true);
  }

  public final boolean isImeControlledByApp() {
    InputTarget inputTarget = this.mImeInputTarget;
    return inputTarget != null && inputTarget.shouldControlIme();
  }

  public boolean shouldImeAttachedToApp() {
    WindowState windowState;
    WindowState windowState2;
    StartingData startingData;
    if (this.mImeWindowsContainer.isOrganized()) {
      return false;
    }
    if ((this.mMagnificationSpec == null)
        && isImeControlledByApp()
        && (windowState = this.mImeLayeringTarget) != null
        && windowState.mActivityRecord != null
        && windowState.getWindowingMode() == 1
        && this.mImeLayeringTarget.matchesDisplayAreaBounds()) {
      return !CoreRune.MW_EMBED_ACTIVITY
          || (startingData = (windowState2 = this.mImeLayeringTarget).mStartingData) == null
          || startingData.mAssociatedTask == null
          || !windowState2.mActivityRecord.isSplitEmbedded();
    }
    return false;
  }

  public boolean isImeAttachedToApp() {
    SurfaceControl surfaceControl;
    return shouldImeAttachedToApp()
        && (surfaceControl = this.mInputMethodSurfaceParent) != null
        && surfaceControl.isSameSurface(
            this.mImeLayeringTarget.mActivityRecord.getSurfaceControl());
  }

  public InsetsControlTarget getImeHostOrFallback(WindowState windowState) {
    return (windowState == null || windowState.getDisplayContent().getImePolicy() != 0)
        ? getImeFallback()
        : windowState;
  }

  public InsetsControlTarget getImeFallback() {
    DisplayContent defaultDisplayContentLocked = this.mWmService.getDefaultDisplayContentLocked();
    WindowState statusBar = defaultDisplayContentLocked.getDisplayPolicy().getStatusBar();
    return statusBar != null ? statusBar : defaultDisplayContentLocked.mRemoteInsetsControlTarget;
  }

  public InsetsControlTarget getImeTarget(int i) {
    if (i == 0) {
      return this.mImeLayeringTarget;
    }
    if (i != 2) {
      return null;
    }
    return this.mImeControlTarget;
  }

  public InputTarget getImeInputTarget() {
    return this.mImeInputTarget;
  }

  public int getImePolicy() {
    if (!isTrusted()) {
      return 1;
    }
    int imePolicyLocked = this.mWmService.mDisplayWindowSettings.getImePolicyLocked(this);
    if (imePolicyLocked == 1 && forceDesktopMode()) {
      return 0;
    }
    return imePolicyLocked;
  }

  public boolean forceDesktopMode() {
    return (isRemoteAppDisplay()
            || isAppCastingDisplay()
            || this.mWmService.mExt.mExtraDisplayPolicy.isDisplayControlledByPolicy(this.mDisplayId)
            || !this.mWmService.mForceDesktopModeOnExternalDisplays
            || this.isDefaultDisplay
            || isPrivate())
        ? false
        : true;
  }

  public void onShowImeRequested() {
    ActivityRecord activityRecord;
    WindowState windowState = this.mInputMethodWindow;
    if (windowState == null || (activityRecord = this.mFixedRotationLaunchingApp) == null) {
      return;
    }
    windowState.mToken.linkFixedRotationTransform(activityRecord);
    AsyncRotationController asyncRotationController = this.mAsyncRotationController;
    if (asyncRotationController != null) {
      asyncRotationController.hideImeImmediately();
    }
  }

  public void setImeLayeringTarget(WindowState windowState) {
    this.mImeLayeringTarget = windowState;
  }

  public final void setImeLayeringTargetInner(WindowState windowState) {
    RootDisplayArea rootDisplayArea;
    WindowState windowState2 = this.mImeLayeringTarget;
    if (windowState == windowState2 && this.mLastImeInputTarget == this.mImeInputTarget) {
      return;
    }
    InputTarget inputTarget = this.mImeInputTarget;
    this.mLastImeInputTarget = inputTarget;
    if (windowState2 != null && windowState2 == inputTarget) {
      boolean z =
          windowState2.mAnimatingExit
              && windowState2.mAttrs.type != 1
              && windowState2.isSelfAnimating(0, 16);
      if ((this.mImeLayeringTarget.inTransitionSelfOrParent() && !inTransition()) || z) {
        showImeScreenshot();
      }
    }
    if (ProtoLogCache.WM_DEBUG_IME_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_IME,
          2119122320,
          0,
          (String) null,
          new Object[] {String.valueOf(windowState)});
    }
    boolean z2 = windowState != this.mImeLayeringTarget;
    this.mImeLayeringTarget = windowState;
    if (windowState != null
        && !this.mImeWindowsContainer.isOrganized()
        && (rootDisplayArea = windowState.getRootDisplayArea()) != null
        && rootDisplayArea != this.mImeWindowsContainer.getRootDisplayArea()
        && rootDisplayArea.placeImeContainer(this.mImeWindowsContainer)) {
      WindowState windowState3 = this.mInputMethodWindow;
      if (windowState3 != null) {
        windowState3.hide(false, false);
      }
      z2 = true;
    }
    assignWindowLayers(true);
    InsetsStateController insetsStateController = this.mInsetsStateController;
    insetsStateController.updateAboveInsetsState(
        insetsStateController
            .getRawInsetsState()
            .isSourceOrDefaultVisible(InsetsSource.ID_IME, WindowInsets.Type.ime()));
    updateImeControlTarget(z2);
  }

  public void setImeInputTarget(InputTarget inputTarget) {
    final WindowState windowState;
    Pair pair = this.mImeTargetTokenListenerPair;
    if (pair != null) {
      WindowToken windowToken = (WindowToken) this.mTokenMap.get(pair.first);
      if (windowToken != null) {
        windowToken.unregisterWindowContainerListener(
            (WindowContainerListener) this.mImeTargetTokenListenerPair.second);
      }
      this.mImeTargetTokenListenerPair = null;
    }
    this.mImeInputTarget = inputTarget;
    if (inputTarget != null && (windowState = inputTarget.getWindowState()) != null) {
      Pair pair2 =
          new Pair(
              windowState.mToken.token,
              new WindowContainerListener() { // from class: com.android.server.wm.DisplayContent.3
                @Override // com.android.server.wm.WindowContainerListener
                public void onVisibleRequestedChanged(boolean z) {
                  WindowManagerService windowManagerService = DisplayContent.this.mWmService;
                  IBinder asBinder = windowState.mClient.asBinder();
                  ActivityRecord activityRecord = windowState.mActivityRecord;
                  windowManagerService.dispatchImeInputTargetVisibilityChanged(
                      asBinder, z, activityRecord != null && activityRecord.finishing);
                }
              });
      this.mImeTargetTokenListenerPair = pair2;
      windowState.mToken.registerWindowContainerListener((WindowContainerListener) pair2.second);
      this.mWmService.dispatchImeInputTargetVisibilityChanged(
          windowState.mClient.asBinder(), windowState.isVisible(), false);
    }
    if (refreshImeSecureFlag(getPendingTransaction())) {
      this.mWmService.requestTraversal();
    }
  }

  public boolean refreshImeSecureFlag(SurfaceControl.Transaction transaction) {
    InputTarget inputTarget = this.mImeInputTarget;
    return this.mImeWindowsContainer.setCanScreenshot(
        transaction, inputTarget == null || inputTarget.canScreenshotIme());
  }

  public void setImeControlTarget(InsetsControlTarget insetsControlTarget) {
    this.mImeControlTarget = insetsControlTarget;
  }

  public final class ImeScreenshot {
    public SurfaceControl mImeSurface;
    public Point mImeSurfacePosition;
    public WindowState mImeTarget;
    public SurfaceControl.Builder mSurfaceBuilder;

    public ImeScreenshot(SurfaceControl.Builder builder, WindowState windowState) {
      this.mSurfaceBuilder = builder;
      this.mImeTarget = windowState;
    }

    public WindowState getImeTarget() {
      return this.mImeTarget;
    }

    public SurfaceControl getImeScreenshotSurface() {
      return this.mImeSurface;
    }

    public final SurfaceControl createImeSurface(
        ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer,
        SurfaceControl.Transaction transaction) {
      SurfaceControl surfaceControl;
      HardwareBuffer hardwareBuffer = screenshotHardwareBuffer.getHardwareBuffer();
      if (ProtoLogCache.WM_DEBUG_IME_enabled) {
        ProtoLogImpl.i(
            ProtoLogGroup.WM_DEBUG_IME,
            -1777010776,
            0,
            (String) null,
            new Object[] {
              String.valueOf(this.mImeTarget),
              String.valueOf(hardwareBuffer.getWidth()),
              String.valueOf(hardwareBuffer.getHeight())
            });
      }
      WindowState windowState = this.mImeTarget.getDisplayContent().mInputMethodWindow;
      WindowState windowState2 = this.mImeTarget;
      ActivityRecord activityRecord = windowState2.mActivityRecord;
      if (windowState2.mAttrs.type == 1) {
        surfaceControl = activityRecord.getSurfaceControl();
      } else {
        surfaceControl = windowState2.getSurfaceControl();
      }
      if (!this.mImeTarget.getDisplayContent().shouldImeAttachedToApp()) {
        return null;
      }
      SurfaceControl build =
          this.mSurfaceBuilder
              .setName("IME-snapshot-surface")
              .setBLASTLayer()
              .setFormat(hardwareBuffer.getFormat())
              .setParent(surfaceControl)
              .setCallsite("DisplayContent.attachAndShowImeScreenshotOnTarget")
              .build();
      InputMonitor.setTrustedOverlayInputInfo(
          build, transaction, windowState.getDisplayId(), "IME-snapshot-surface");
      transaction.setBuffer(build, hardwareBuffer);
      transaction.setColorSpace(
          activityRecord.mSurfaceControl, ColorSpace.get(ColorSpace.Named.SRGB));
      transaction.setLayer(build, 1);
      Point point = new Point(windowState.getFrame().left, windowState.getFrame().top);
      if (surfaceControl == activityRecord.getSurfaceControl()) {
        if (activityRecord.mCompatRecord.isCompatModeEnabled()) {
          Rect bounds = activityRecord.getTask().getBounds();
          Rect bounds2 = activityRecord.getBounds();
          point.offset(bounds.left - bounds2.left, bounds.top - bounds2.top);
        }
        transaction.setPosition(build, point.x, point.y);
      } else {
        point.offset(-this.mImeTarget.getFrame().left, -this.mImeTarget.getFrame().top);
        Rect rect = this.mImeTarget.mAttrs.surfaceInsets;
        point.offset(rect.left, rect.top);
        transaction.setPosition(build, point.x, point.y);
      }
      this.mImeSurfacePosition = point;
      if (ProtoLogCache.WM_DEBUG_IME_enabled) {
        ProtoLogImpl.i(
            ProtoLogGroup.WM_DEBUG_IME,
            -1814361639,
            5,
            (String) null,
            new Object[] {Long.valueOf(point.x), Long.valueOf(point.y)});
      }
      return build;
    }

    public final void removeImeSurface(SurfaceControl.Transaction transaction) {
      if (this.mImeSurface != null) {
        if (ProtoLogCache.WM_DEBUG_IME_enabled) {
          ProtoLogImpl.i(
              ProtoLogGroup.WM_DEBUG_IME,
              -2111539867,
              0,
              (String) null,
              new Object[] {String.valueOf(Debug.getCallers(6))});
        }
        transaction.remove(this.mImeSurface);
        this.mImeSurface = null;
      }
      if (ImeTracker.DEBUG_IME_VISIBILITY) {
        EventLog.writeEvent(32005, this.mImeTarget.toString());
      }
    }

    public void attachAndShow(SurfaceControl.Transaction transaction) {
      DisplayContent displayContent = this.mImeTarget.getDisplayContent();
      Task task = this.mImeTarget.getTask();
      SurfaceControl surfaceControl = this.mImeSurface;
      boolean z =
          (surfaceControl != null
                  && surfaceControl.getWidth()
                      == displayContent.mInputMethodWindow.getFrame().width()
                  && this.mImeSurface.getHeight()
                      == displayContent.mInputMethodWindow.getFrame().height())
              ? false
              : true;
      if (task != null && !task.isActivityTypeHomeOrRecents()) {
        ScreenCapture.ScreenshotHardwareBuffer snapshotImeFromAttachedTask =
            z
                ? displayContent.mWmService.mTaskSnapshotController.snapshotImeFromAttachedTask(
                    task)
                : null;
        if (snapshotImeFromAttachedTask != null) {
          removeImeSurface(transaction);
          this.mImeSurface = createImeSurface(snapshotImeFromAttachedTask, transaction);
        }
      }
      SurfaceControl surfaceControl2 = this.mImeSurface;
      boolean z2 = surfaceControl2 != null && surfaceControl2.isValid();
      if (!z2 || !displayContent.getInsetsStateController().getImeSourceProvider().isImeShowing()) {
        if (z2) {
          return;
        }
        removeImeSurface(transaction);
      } else {
        if (ProtoLogCache.WM_DEBUG_IME_enabled) {
          ProtoLogImpl.i(
              ProtoLogGroup.WM_DEBUG_IME,
              -57750640,
              0,
              (String) null,
              new Object[] {String.valueOf(this.mImeTarget), String.valueOf(Debug.getCallers(6))});
        }
        transaction.show(this.mImeSurface);
        if (ImeTracker.DEBUG_IME_VISIBILITY) {
          EventLog.writeEvent(
              32004,
              this.mImeTarget.toString(),
              Integer.valueOf(displayContent.mInputMethodWindow.mTransitFlags),
              this.mImeSurfacePosition.toString());
        }
      }
    }

    public void detach(SurfaceControl.Transaction transaction) {
      removeImeSurface(transaction);
    }

    public String toString() {
      StringBuilder sb = new StringBuilder(64);
      sb.append("ImeScreenshot{");
      sb.append(Integer.toHexString(System.identityHashCode(this)));
      sb.append(" imeTarget=" + this.mImeTarget);
      sb.append(" surface=" + this.mImeSurface);
      sb.append('}');
      return sb.toString();
    }
  }

  public final void attachImeScreenshotOnTargetIfNeeded() {
    WindowState windowState;
    if (shouldImeAttachedToApp()
        && this.mWmService.mPolicy.isScreenOn()
        && (windowState = this.mInputMethodWindow) != null
        && windowState.isVisible()) {
      attachImeScreenshotOnTarget(this.mImeLayeringTarget);
    }
  }

  public final void attachImeScreenshotOnTarget(WindowState windowState) {
    attachImeScreenshotOnTarget(windowState, false);
  }

  public final void attachImeScreenshotOnTarget(WindowState windowState, boolean z) {
    SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
    removeImeSurfaceImmediately();
    ImeScreenshot imeScreenshot =
        new ImeScreenshot(
            (SurfaceControl.Builder) this.mWmService.mSurfaceControlFactory.apply(null),
            windowState);
    this.mImeScreenshot = imeScreenshot;
    imeScreenshot.attachAndShow(pendingTransaction);
    if (this.mInputMethodWindow == null || !z || this.mImeScreenshot.mImeSurface == null) {
      return;
    }
    this.mInputMethodWindow.hide(false, false);
  }

  public void showImeScreenshot() {
    attachImeScreenshotOnTargetIfNeeded();
  }

  public void showImeScreenshot(WindowState windowState) {
    attachImeScreenshotOnTarget(windowState, true);
  }

  public void removeImeSurfaceByTarget(WindowContainer windowContainer) {
    if (this.mImeScreenshot == null || windowContainer == null) {
      return;
    }
    if (windowContainer.asWindowState() == null
        || windowContainer.asWindowState().mAttrs.type != 3) {
      final WindowState imeTarget = this.mImeScreenshot.getImeTarget();
      if (windowContainer == imeTarget
          || windowContainer.getWindow(
                  new Predicate() { // from class:
                                    // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda28
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                      boolean lambda$removeImeSurfaceByTarget$31;
                      lambda$removeImeSurfaceByTarget$31 =
                          DisplayContent.lambda$removeImeSurfaceByTarget$31(WindowState.this, obj);
                      return lambda$removeImeSurfaceByTarget$31;
                    }
                  })
              != null) {
        removeImeSurfaceImmediately();
      }
    }
  }

  public void removeImeSurfaceImmediately() {
    ImeScreenshot imeScreenshot = this.mImeScreenshot;
    if (imeScreenshot != null) {
      imeScreenshot.detach(getSyncTransaction());
      this.mImeScreenshot = null;
    }
  }

  public void updateImeInputAndControlTarget(InputTarget inputTarget) {
    SurfaceControl surfaceControl;
    if (this.mImeInputTarget != inputTarget) {
      boolean z = false;
      if (ProtoLogCache.WM_DEBUG_IME_enabled) {
        ProtoLogImpl.i(
            ProtoLogGroup.WM_DEBUG_IME,
            -322743468,
            0,
            (String) null,
            new Object[] {String.valueOf(inputTarget)});
      }
      setImeInputTarget(inputTarget);
      InsetsStateController insetsStateController = this.mInsetsStateController;
      insetsStateController.updateAboveInsetsState(
          insetsStateController
              .getRawInsetsState()
              .isSourceOrDefaultVisible(InsetsSource.ID_IME, WindowInsets.Type.ime()));
      if (this.mImeControlTarget == this.mRemoteInsetsControlTarget
          && (surfaceControl = this.mInputMethodSurfaceParent) != null
          && !surfaceControl.isSameSurface(this.mImeWindowsContainer.getParent().mSurfaceControl)) {
        z = true;
      }
      updateImeControlTarget(z);
    }
  }

  public boolean onImeInsetsClientVisibilityUpdate() {
    final boolean[] zArr = new boolean[1];
    InputTarget inputTarget = this.mImeInputTarget;
    ActivityRecord activityRecord = inputTarget != null ? inputTarget.getActivityRecord() : null;
    if ((this.mImeInputTarget != this.mLastImeInputTarget)
        || (activityRecord != null
            && activityRecord.isVisibleRequested()
            && activityRecord.mImeInsetsFrozenUntilStartInput)) {
      forAllActivities(
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda59
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayContent.lambda$onImeInsetsClientVisibilityUpdate$32(
                  zArr, (ActivityRecord) obj);
            }
          });
    }
    return zArr[0];
  }

  public static /* synthetic */ void lambda$onImeInsetsClientVisibilityUpdate$32(
      boolean[] zArr, ActivityRecord activityRecord) {
    if (activityRecord.mImeInsetsFrozenUntilStartInput && activityRecord.isVisibleRequested()) {
      activityRecord.mImeInsetsFrozenUntilStartInput = false;
      zArr[0] = true;
    }
  }

  public void updateImeControlTarget() {
    updateImeControlTarget(false);
  }

  public void updateImeControlTarget(boolean z) {
    InsetsControlTarget insetsControlTarget = this.mImeControlTarget;
    InsetsControlTarget computeImeControlTarget = computeImeControlTarget();
    this.mImeControlTarget = computeImeControlTarget;
    this.mInsetsStateController.onImeControlTargetChanged(computeImeControlTarget);
    if ((insetsControlTarget != this.mImeControlTarget) || z) {
      SurfaceControl surfaceControl = this.mInputMethodSurfaceParent;
      updateImeParent();
      if (surfaceControl != null
          && surfaceControl == this.mInputMethodSurfaceParent
          && shouldBeRelativeLayer(this.mImeLayeringTarget)) {
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX
            && this.mTransitionController.inPlayingTransition(this)) {
          this.mTransitionController.mStateValidators.add(
              new Runnable() { // from class:
                               // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                  DisplayContent.this.lambda$updateImeControlTarget$33();
                }
              });
        } else {
          assignRelativeLayerForIme(getSyncTransaction(), true);
          scheduleAnimation();
        }
      }
    }
    WindowState asWindowOrNull = InsetsControlTarget.asWindowOrNull(this.mImeControlTarget);
    final IBinder asBinder = asWindowOrNull != null ? asWindowOrNull.mClient.asBinder() : null;
    this.mWmService.f1749mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda18
          @Override // java.lang.Runnable
          public final void run() {
            DisplayContent.lambda$updateImeControlTarget$34(asBinder);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updateImeControlTarget$33() {
    assignRelativeLayerForIme(getSyncTransaction(), true);
    scheduleAnimation();
  }

  public static /* synthetic */ void lambda$updateImeControlTarget$34(IBinder iBinder) {
    InputMethodManagerInternal.get().reportImeControl(iBinder);
  }

  public void updateImeParent() {
    ActivityRecord activityRecord;
    WindowContainer windowContainer;
    if (this.mImeWindowsContainer.isOrganized()) {
      this.mInputMethodSurfaceParent = null;
      this.mInputMethodSurfaceParentContainer = null;
      return;
    }
    SurfaceControl computeImeParent = computeImeParent();
    if (computeImeParent == null
        && this.isDefaultDisplay
        && this.mImeWindowsContainer.getParent() != null
        && this.mInputMethodSurfaceParent != null
        && (windowContainer = this.mInputMethodSurfaceParentContainer) != null
        && windowContainer.getDisplayContent() != null) {
      if (this.mInputMethodSurfaceParentContainer.getDisplayContent() != this) {
        computeImeParent = this.mImeWindowsContainer.getParent().getSurfaceControl();
        Slog.w(
            StartingSurfaceController.TAG,
            "updateImeParent: reset surface parent(d#"
                + this.mDisplayId
                + ") surface is moved to other display, r= "
                + this.mInputMethodSurfaceParentContainer);
      } else if (this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked()) {
        computeImeParent = this.mImeWindowsContainer.getParent().getSurfaceControl();
        Slog.w(
            StartingSurfaceController.TAG,
            "updateImeParent: reset surface parent(d#"
                + this.mDisplayId
                + ") by DeX IME policy, r= "
                + this.mInputMethodSurfaceParentContainer);
      }
    }
    if (computeImeParent == null || computeImeParent == this.mInputMethodSurfaceParent) {
      return;
    }
    this.mInputMethodSurfaceParent = computeImeParent;
    WindowState windowState = this.mImeLayeringTarget;
    SurfaceControl surfaceControl =
        (windowState == null || (activityRecord = windowState.mActivityRecord) == null)
            ? null
            : activityRecord.getSurfaceControl();
    if (surfaceControl != null && this.mInputMethodSurfaceParent.isSameSurface(surfaceControl)) {
      this.mInputMethodSurfaceParentContainer = this.mImeLayeringTarget.mActivityRecord;
    } else {
      this.mInputMethodSurfaceParentContainer = null;
    }
    getSyncTransaction().reparent(this.mImeWindowsContainer.mSurfaceControl, computeImeParent);
    if (ImeTracker.DEBUG_IME_VISIBILITY) {
      EventLog.writeEvent(32003, computeImeParent.toString());
    }
    assignRelativeLayerForIme(getSyncTransaction(), true);
    scheduleAnimation();
    this.mWmService.f1749mH.post(
        new Runnable() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda25
          @Override // java.lang.Runnable
          public final void run() {
            DisplayContent.lambda$updateImeParent$35();
          }
        });
  }

  public static /* synthetic */ void lambda$updateImeParent$35() {
    InputMethodManagerInternal.get().onImeParentChanged();
  }

  public InsetsControlTarget computeImeControlTarget() {
    if (shouldUseRemoteInsetsControlTarget()) {
      return this.mRemoteInsetsControlTarget;
    }
    InputTarget inputTarget = this.mImeInputTarget;
    if (inputTarget == null) {
      return null;
    }
    WindowState windowState = inputTarget.getWindowState();
    return ((isImeControlledByApp() || this.mRemoteInsetsControlTarget == null)
            && getImeHostOrFallback(windowState) != this.mRemoteInsetsControlTarget)
        ? windowState
        : this.mRemoteInsetsControlTarget;
  }

  public SurfaceControl computeImeParent() {
    if (!ImeTargetVisibilityPolicy.canComputeImeParent(
        this.mImeLayeringTarget, this.mImeInputTarget)) {
      return null;
    }
    if (shouldImeAttachedToApp()) {
      return this.mImeLayeringTarget.mActivityRecord.getSurfaceControl();
    }
    if (this.mImeWindowsContainer.getParent() != null) {
      return this.mImeWindowsContainer.getParent().getSurfaceControl();
    }
    return null;
  }

  public void setLayoutNeeded() {
    WindowSurfacePlacer windowSurfacePlacer = this.mWmService.mWindowPlacerLocked;
    if (windowSurfacePlacer.mPrintLayoutCaller) {
      windowSurfacePlacer.mPrintLayoutCaller = false;
      Slog.w(
          StartingSurfaceController.TAG,
          "setLayoutNeeded: d" + this.mDisplayId + ", callers=" + Debug.getCallers(3));
    }
    this.mLayoutNeeded = true;
  }

  public final void clearLayoutNeeded() {
    this.mLayoutNeeded = false;
  }

  public boolean isLayoutNeeded() {
    return this.mLayoutNeeded;
  }

  public void dumpTokens(PrintWriter printWriter, boolean z) {
    if (this.mTokenMap.isEmpty()) {
      return;
    }
    printWriter.println("  Display #" + this.mDisplayId);
    printWriter.println("    mInTouchMode=" + this.mInTouchMode);
    for (WindowToken windowToken : this.mTokenMap.values()) {
      printWriter.print("  ");
      printWriter.print(windowToken);
      if (z) {
        printWriter.println(':');
        windowToken.dump(printWriter, "    ", z);
      } else {
        printWriter.println();
      }
    }
    if (!this.mOpeningApps.isEmpty()
        || !this.mClosingApps.isEmpty()
        || !this.mChangingContainers.isEmpty()) {
      printWriter.println();
      if (this.mOpeningApps.size() > 0) {
        printWriter.print("  mOpeningApps=");
        printWriter.println(this.mOpeningApps);
      }
      if (this.mClosingApps.size() > 0) {
        printWriter.print("  mClosingApps=");
        printWriter.println(this.mClosingApps);
      }
      if (this.mChangingContainers.size() > 0) {
        printWriter.print("  mChangingApps=");
        printWriter.println(this.mChangingContainers);
      }
    }
    this.mUnknownAppVisibilityController.dump(printWriter, "  ");
  }

  public void dumpWindowAnimators(final PrintWriter printWriter, final String str) {
    final int[] iArr = new int[1];
    forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda70
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.lambda$dumpWindowAnimators$36(printWriter, str, iArr, (WindowState) obj);
          }
        },
        false);
  }

  public static /* synthetic */ void lambda$dumpWindowAnimators$36(
      PrintWriter printWriter, String str, int[] iArr, WindowState windowState) {
    printWriter.println(str + "Window #" + iArr[0] + ": " + windowState.mWinAnimator);
    iArr[0] = iArr[0] + 1;
  }

  public boolean shouldWaitForSystemDecorWindowsOnBoot() {
    if (!this.isDefaultDisplay && !supportsSystemDecorations()) {
      return false;
    }
    final SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
    sparseBooleanArray.put(2040, true);
    if (getWindow(
            new Predicate() { // from class:
                              // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda52
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$shouldWaitForSystemDecorWindowsOnBoot$38;
                lambda$shouldWaitForSystemDecorWindowsOnBoot$38 =
                    DisplayContent.this.lambda$shouldWaitForSystemDecorWindowsOnBoot$38(
                        sparseBooleanArray, (WindowState) obj);
                return lambda$shouldWaitForSystemDecorWindowsOnBoot$38;
              }
            })
        != null) {
      return true;
    }
    boolean z =
        this.mWmService.mContext.getResources().getBoolean(R.bool.config_enableFusedLocationOverlay)
            && this.mWmService
                .mContext
                .getResources()
                .getBoolean(R.bool.config_cecSystemAudioModeMutingDisabled_allowed);
    boolean z2 = sparseBooleanArray.get(2021);
    boolean z3 = sparseBooleanArray.get(1);
    boolean z4 = sparseBooleanArray.get(2013);
    boolean z5 = sparseBooleanArray.get(2040);
    if (ProtoLogCache.WM_DEBUG_SCREEN_ON_enabled) {
      WindowManagerService windowManagerService = this.mWmService;
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_SCREEN_ON,
          -635082269,
          16383,
          (String) null,
          new Object[] {
            Boolean.valueOf(windowManagerService.mSystemBooted),
            Boolean.valueOf(windowManagerService.mShowingBootMessages),
            Boolean.valueOf(z2),
            Boolean.valueOf(z3),
            Boolean.valueOf(z4),
            Boolean.valueOf(z),
            Boolean.valueOf(z5)
          });
    }
    boolean z6 = this.mWmService.mSystemBooted;
    if (z6 || z2) {
      return z6 && (!(z3 || z5) || (z && !z4));
    }
    return true;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$shouldWaitForSystemDecorWindowsOnBoot$38(
      SparseBooleanArray sparseBooleanArray, WindowState windowState) {
    boolean z = windowState.isVisible() && !windowState.mObscured;
    boolean isDrawn = windowState.isDrawn();
    if (z && !isDrawn) {
      if (ProtoLogCache.WM_DEBUG_BOOT_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_BOOT,
            -381475323,
            1,
            (String) null,
            new Object[] {Long.valueOf(windowState.mAttrs.type)});
      }
      return true;
    }
    if (isDrawn) {
      int i = windowState.mAttrs.type;
      if (i == 1 || i == 2013 || i == 2021) {
        sparseBooleanArray.put(i, true);
      } else if (i == 2040) {
        sparseBooleanArray.put(2040, this.mWmService.mPolicy.isKeyguardDrawnLw());
      }
    }
    return false;
  }

  public void updateWindowsForAnimator() {
    forAllWindows(this.mUpdateWindowsForAnimator, true);
    AsyncRotationController asyncRotationController = this.mAsyncRotationController;
    if (asyncRotationController != null) {
      asyncRotationController.updateTargetWindows();
    }
  }

  public boolean isInputMethodClientFocus(int i, int i2) {
    WindowState computeImeTarget = computeImeTarget(false);
    if (computeImeTarget == null) {
      return false;
    }
    Session session = computeImeTarget.mSession;
    return session.mUid == i && session.mPid == i2;
  }

  public static /* synthetic */ boolean lambda$hasSecureWindowOnScreen$39(WindowState windowState) {
    return windowState.isOnScreen() && windowState.isSecureLocked();
  }

  public boolean hasSecureWindowOnScreen() {
    return getWindow(
            new Predicate() { // from class:
                              // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda48
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$hasSecureWindowOnScreen$39;
                lambda$hasSecureWindowOnScreen$39 =
                    DisplayContent.lambda$hasSecureWindowOnScreen$39((WindowState) obj);
                return lambda$hasSecureWindowOnScreen$39;
              }
            })
        != null;
  }

  public void onWindowFreezeTimeout() {
    Slog.w(StartingSurfaceController.TAG, "Window freeze timeout expired.");
    this.mWmService.mWindowsFreezingScreen = 2;
    forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda65
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$onWindowFreezeTimeout$40((WindowState) obj);
          }
        },
        true);
    this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onWindowFreezeTimeout$40(WindowState windowState) {
    if (windowState.getOrientationChanging()) {
      windowState.orientationChangeTimedOut();
      windowState.mLastFreezeDuration =
          (int) (SystemClock.elapsedRealtime() - this.mWmService.mDisplayFreezeTime);
      Slog.w(StartingSurfaceController.TAG, "Force clearing orientation change: " + windowState);
    }
  }

  public void onWindowAnimationFinished(WindowContainer windowContainer, int i) {
    if (this.mImeScreenshot != null && ProtoLogCache.WM_DEBUG_IME_enabled) {
      ProtoLogImpl.i(
          ProtoLogGroup.WM_DEBUG_IME,
          -658964693,
          0,
          (String) null,
          new Object[] {
            String.valueOf(windowContainer),
            String.valueOf(SurfaceAnimator.animationTypeToString(i)),
            String.valueOf(this.mImeScreenshot),
            String.valueOf(this.mImeScreenshot.getImeTarget())
          });
    }
    if ((i & 25) != 0) {
      removeImeSurfaceByTarget(windowContainer);
    }
  }

  public void applySurfaceChangesTransaction() {
    WindowSurfacePlacer windowSurfacePlacer = this.mWmService.mWindowPlacerLocked;
    beginHoldScreenUpdate();
    this.mTmpUpdateAllDrawn.clear();
    if ((this.pendingLayoutChanges & 4) != 0) {
      this.mWallpaperController.adjustWallpaperWindows();
    }
    if ((this.pendingLayoutChanges & 2) != 0 && updateOrientation()) {
      setLayoutNeeded();
      sendNewConfiguration();
    }
    if ((this.pendingLayoutChanges & 1) != 0) {
      setLayoutNeeded();
    }
    performLayout(true, false);
    this.pendingLayoutChanges = 0;
    Trace.traceBegin(32L, "applyPostLayoutPolicy");
    try {
      this.mDisplayPolicy.beginPostLayoutPolicyLw();
      forAllWindows(this.mApplyPostLayoutPolicy, true);
      this.mDisplayPolicy.finishPostLayoutPolicyLw();
      Trace.traceEnd(32L);
      this.mInsetsStateController.onPostLayout();
      this.mTmpApplySurfaceChangesTransactionState.reset();
      if (CoreRune.FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST) {
        getDisplayPolicy().getRefreshRatePolicy().resetRestrictHighRefreshRate();
      }
      if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED) {
        CustomLetterboxConfiguration.adjustLetterboxes(this);
      }
      Trace.traceBegin(32L, "applyWindowSurfaceChanges");
      try {
        forAllWindows(this.mApplySurfaceChangesTransaction, true);
        Trace.traceEnd(32L);
        prepareSurfaces();
        this.mInsetsStateController.getImeSourceProvider().checkShowImePostLayout();
        if (CoreRune.FW_VRR_HIGH_REFRESH_RATE_BLOCK_LIST) {
          getDisplayPolicy().getRefreshRatePolicy().updateRestrictHighRefreshRate();
        }
        this.mLastHasContent = this.mTmpApplySurfaceChangesTransactionState.displayHasContent;
        if (!inTransition() && !this.mDisplayRotation.isRotatingSeamlessly()) {
          DisplayManagerInternal displayManagerInternal = this.mWmService.mDisplayManagerInternal;
          int i = this.mDisplayId;
          boolean z = this.mLastHasContent;
          ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState =
              this.mTmpApplySurfaceChangesTransactionState;
          displayManagerInternal.setDisplayProperties(
              i,
              z,
              applySurfaceChangesTransactionState.preferredRefreshRate,
              applySurfaceChangesTransactionState.preferredModeId,
              applySurfaceChangesTransactionState.preferredMinRefreshRate,
              applySurfaceChangesTransactionState.preferredMaxRefreshRate,
              applySurfaceChangesTransactionState.preferMinimalPostProcessing,
              applySurfaceChangesTransactionState.disableHdrConversion,
              true);
        }
        updateRecording();
        boolean isWallpaperVisible = this.mWallpaperController.isWallpaperVisible();
        if (isWallpaperVisible != this.mLastWallpaperVisible) {
          this.mLastWallpaperVisible = isWallpaperVisible;
          this.mWmService.mWallpaperVisibilityListeners.notifyWallpaperVisibilityChanged(this);
          if (this.isDefaultDisplay) {
            this.mWmService.mAtmService.mMultiTaskingController.notifyWallpaperVisibilityChanged(
                this);
          }
        }
        while (!this.mTmpUpdateAllDrawn.isEmpty()) {
          ((ActivityRecord) this.mTmpUpdateAllDrawn.removeLast()).updateAllDrawn();
        }
        finishHoldScreenUpdate();
      } finally {
      }
    } finally {
    }
  }

  public final void getBounds(Rect rect, int i) {
    getBounds(rect);
    int deltaRotation = RotationUtils.deltaRotation(this.mDisplayInfo.rotation, i);
    if (deltaRotation == 1 || deltaRotation == 3) {
      rect.set(0, 0, rect.height(), rect.width());
    }
  }

  public int getNaturalOrientation() {
    return this.mBaseDisplayWidth < this.mBaseDisplayHeight ? 1 : 2;
  }

  public void performLayout(boolean z, boolean z2) {
    Trace.traceBegin(32L, "performLayout");
    try {
      performLayoutNoTrace(z, z2);
    } finally {
      Trace.traceEnd(32L);
    }
  }

  public final void performLayoutNoTrace(boolean z, boolean z2) {
    if (isLayoutNeeded()) {
      clearLayoutNeeded();
      int i = this.mLayoutSeq + 1;
      if (i < 0) {
        i = 0;
      }
      this.mLayoutSeq = i;
      this.mTmpInitial = z;
      forAllWindows(this.mPerformLayout, true);
      forAllWindows(this.mPerformLayoutAttached, true);
      this.mInputMonitor.setUpdateInputWindowsNeededLw();
      if (z2) {
        this.mInputMonitor.updateInputWindowsLw(false);
      }
    }
  }

  public Bitmap screenshotDisplayLocked(boolean z) {
    if (!this.mWmService.mPolicy.isScreenOn()) {
      Slog.i(StartingSurfaceController.TAG, "Attempted to take screenshot while display was off.");
      return null;
    }
    ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener =
        ScreenCapture.createSyncCaptureListener();
    getBounds(this.mTmpRect);
    this.mTmpRect.offsetTo(0, 0);
    ScreenCapture.captureLayers(
        new ScreenCapture.LayerCaptureArgs.Builder(getSurfaceControl())
            .setExcludeLayers(
                (CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG && z) ? getSystemWindowLayersLocked() : null)
            .setSourceCrop(this.mTmpRect)
            .build(),
        createSyncCaptureListener);
    ScreenCapture.ScreenshotHardwareBuffer buffer = createSyncCaptureListener.getBuffer();
    Bitmap asBitmap = buffer != null ? buffer.asBitmap() : null;
    if (asBitmap == null) {
      Slog.w(StartingSurfaceController.TAG, "Failed to take screenshot");
    }
    return asBitmap;
  }

  public SurfaceControl[] getSystemWindowLayersLocked() {
    if (!CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG) {
      return null;
    }
    final ArrayList arrayList = new ArrayList();
    forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda55
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$getSystemWindowLayersLocked$41(arrayList, (WindowState) obj);
          }
        },
        true);
    if (arrayList.size() <= 0) {
      return null;
    }
    SurfaceControl[] surfaceControlArr = new SurfaceControl[arrayList.size()];
    arrayList.toArray(surfaceControlArr);
    return surfaceControlArr;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$getSystemWindowLayersLocked$41(
      ArrayList arrayList, WindowState windowState) {
    int i;
    if (!canBeScreenshotTarget(windowState)
        || !windowState.isVisible()
        || windowState.mIsWallpaper
        || (i = windowState.mAttrs.type) < 2000
        || i > 2999) {
      return;
    }
    arrayList.add(windowState.mSurfaceControl);
  }

  public ScreenCapture.LayerCaptureArgs getLayerCaptureArgs(boolean z) {
    SurfaceControl[] surfaceControlArr = null;
    if (!this.mWmService.mPolicy.isScreenOn()) {
      Slog.i(StartingSurfaceController.TAG, "Attempted to take screenshot while display was off.");
      return null;
    }
    if (CoreRune.FW_SUPPORT_SEARCLE_HOME_LONG && z) {
      surfaceControlArr = getSystemWindowLayersLocked();
    }
    getBounds(this.mTmpRect);
    this.mTmpRect.offsetTo(0, 0);
    return new ScreenCapture.LayerCaptureArgs.Builder(getSurfaceControl())
        .setExcludeLayers(surfaceControlArr)
        .setSourceCrop(this.mTmpRect)
        .build();
  }

  public final boolean canBeScreenshotTarget(WindowState windowState) {
    return (windowState.mAttrs.privateFlags & 1048576) == 0 && !windowState.hasRelativeLayer();
  }

  @Override // com.android.server.wm.WindowContainer
  public void onDescendantOverrideConfigurationChanged() {
    setLayoutNeeded();
    this.mWmService.requestTraversal();
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean okToDisplay() {
    return okToDisplay(false, false);
  }

  public boolean okToDisplay(boolean z, boolean z2) {
    if (this.mDisplayId != 0) {
      return this.mDisplayInfo.state == 2;
    }
    WindowManagerService windowManagerService = this.mWmService;
    return (!windowManagerService.mDisplayFrozen || z)
        && windowManagerService.mDisplayEnabled
        && (z2 || windowManagerService.mPolicy.isScreenOn());
  }

  @Override // com.android.server.wm.WindowContainer
  public boolean okToAnimate(boolean z, boolean z2) {
    if ((this.mDisplayId == 2 && this.mAtmService.mDexController.getDexModeLocked() != 2)
        || !okToDisplay(z, z2)) {
      return false;
    }
    if (this.mDisplayId != 0 || this.mWmService.mPolicy.okToAnimate(z2)) {
      return z || this.mDisplayPolicy.isScreenOnFully();
    }
    return false;
  }

  public final class TaskForResizePointSearchResult implements Predicate {
    public int delta;
    public Rect mTmpRect = new Rect();
    public Task taskForResize;

    /* renamed from: x */
    public int f1739x;

    /* renamed from: y */
    public int f1740y;

    public Task process(WindowContainer windowContainer, int i, int i2, int i3) {
      this.taskForResize = null;
      this.f1739x = i;
      this.f1740y = i2;
      this.delta = i3;
      this.mTmpRect.setEmpty();
      windowContainer.forAllTasks(this);
      return this.taskForResize;
    }

    @Override // java.util.function.Predicate
    public boolean test(Task task) {
      ActivityRecord topVisibleActivity;
      if (task.inPinnedWindowingMode()) {
        return false;
      }
      if (!task.getRootTask().getWindowConfiguration().canResizeTask()
          || task.getWindowingMode() == 1
          || task.isOrganized()) {
        return true;
      }
      if (task.inFreeformWindowingMode()
          && (topVisibleActivity = task.getTopVisibleActivity()) != null
          && topVisibleActivity.getTopFullscreenOpaqueWindow() == null) {
        return false;
      }
      task.getDimBounds(this.mTmpRect);
      Rect rect = this.mTmpRect;
      int i = this.delta;
      rect.inset(-i, -i);
      if (!this.mTmpRect.contains(this.f1739x, this.f1740y)) {
        return false;
      }
      Rect rect2 = this.mTmpRect;
      int i2 = this.delta;
      rect2.inset(i2, i2);
      if (!this.mTmpRect.contains(this.f1739x, this.f1740y)) {
        this.taskForResize = task;
      }
      return true;
    }
  }

  public final class ApplySurfaceChangesTransactionState {
    public boolean disableHdrConversion;
    public boolean displayHasContent;
    public boolean obscured;
    public boolean preferMinimalPostProcessing;
    public float preferredMaxRefreshRate;
    public float preferredMinRefreshRate;
    public int preferredModeId;
    public float preferredRefreshRate;
    public boolean syswin;

    public ApplySurfaceChangesTransactionState() {}

    public void reset() {
      this.displayHasContent = false;
      this.obscured = false;
      this.syswin = false;
      this.preferMinimalPostProcessing = false;
      this.preferredRefreshRate = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      this.preferredModeId = 0;
      this.preferredMinRefreshRate = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      this.preferredMaxRefreshRate = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      this.disableHdrConversion = false;
    }
  }

  public class ImeContainer extends DisplayArea.Tokens {
    public boolean mNeedsLayer;

    public ImeContainer(WindowManagerService windowManagerService) {
      super(windowManagerService, DisplayArea.Type.ABOVE_TASKS, "ImeContainer", 8);
      this.mNeedsLayer = false;
    }

    public void setNeedsLayer() {
      this.mNeedsLayer = true;
    }

    @Override // com.android.server.wm.DisplayArea.Tokens, com.android.server.wm.DisplayArea,
              // com.android.server.wm.WindowContainer
    public int getOrientation(int i) {
      if (shouldIgnoreOrientationRequest(i)) {
        return -2;
      }
      return i;
    }

    @Override // com.android.server.wm.WindowContainer
    public void updateAboveInsetsState(
        InsetsState insetsState, SparseArray sparseArray, ArraySet arraySet) {
      if (skipImeWindowsDuringTraversal(this.mDisplayContent)) {
        return;
      }
      super.updateAboveInsetsState(insetsState, sparseArray, arraySet);
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean forAllWindows(ToBooleanFunction toBooleanFunction, boolean z) {
      if (skipImeWindowsDuringTraversal(this.mDisplayContent)) {
        return false;
      }
      return super.forAllWindows(toBooleanFunction, z);
    }

    public static boolean skipImeWindowsDuringTraversal(DisplayContent displayContent) {
      return (displayContent.mImeLayeringTarget == null || displayContent.mWmService.mDisplayFrozen)
          ? false
          : true;
    }

    public boolean forAllWindowForce(ToBooleanFunction toBooleanFunction, boolean z) {
      return super.forAllWindows(toBooleanFunction, z);
    }

    @Override // com.android.server.wm.WindowContainer
    public void assignLayer(SurfaceControl.Transaction transaction, int i) {
      if (this.mNeedsLayer) {
        super.assignLayer(transaction, i);
        this.mNeedsLayer = false;
      }
    }

    @Override // com.android.server.wm.WindowContainer
    public void assignRelativeLayer(
        SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i, boolean z) {
      if (this.mNeedsLayer) {
        super.assignRelativeLayer(transaction, surfaceControl, i, z);
        this.mNeedsLayer = false;
      }
    }

    @Override // com.android.server.wm.DisplayArea
    public void setOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer, boolean z) {
      super.setOrganizer(iDisplayAreaOrganizer, z);
      this.mDisplayContent.updateImeParent();
      if (iDisplayAreaOrganizer != null) {
        SurfaceControl parentSurfaceControl = getParentSurfaceControl();
        SurfaceControl surfaceControl = this.mSurfaceControl;
        if (surfaceControl != null && parentSurfaceControl != null) {
          if (ProtoLogCache.WM_DEBUG_IME_enabled) {
            ProtoLogImpl.i(
                ProtoLogGroup.WM_DEBUG_IME,
                1175495463,
                0,
                (String) null,
                new Object[] {String.valueOf(parentSurfaceControl)});
          }
          getPendingTransaction().reparent(this.mSurfaceControl, parentSurfaceControl);
          return;
        }
        if (ProtoLogCache.WM_DEBUG_IME_enabled) {
          ProtoLogImpl.e(
              ProtoLogGroup.WM_DEBUG_IME,
              -81121442,
              0,
              (String) null,
              new Object[] {String.valueOf(surfaceControl), String.valueOf(parentSurfaceControl)});
        }
      }
    }
  }

  @Override // com.android.server.wm.WindowContainer
  public SurfaceSession getSession() {
    return this.mSession;
  }

  @Override // com.android.server.wm.WindowContainer
  public SurfaceControl.Builder makeChildSurface(WindowContainer windowContainer) {
    SurfaceControl.Builder containerLayer =
        this.mWmService
            .makeSurfaceBuilder(
                windowContainer != null ? windowContainer.getSession() : getSession())
            .setContainerLayer();
    return windowContainer == null
        ? containerLayer
        : containerLayer.setName(windowContainer.getName()).setParent(this.mSurfaceControl);
  }

  public SurfaceControl.Builder makeOverlay() {
    return this.mWmService.makeSurfaceBuilder(this.mSession).setParent(getOverlayLayer());
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
            // com.android.server.wm.SurfaceAnimator.Animatable
  public SurfaceControl.Builder makeAnimationLeash() {
    return this.mWmService
        .makeSurfaceBuilder(this.mSession)
        .setParent(this.mSurfaceControl)
        .setContainerLayer();
  }

  public void reparentToOverlay(
      SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
    transaction.reparent(surfaceControl, getOverlayLayer());
  }

  public void applyMagnificationSpec(MagnificationSpec magnificationSpec) {
    if (magnificationSpec.scale != 1.0d) {
      this.mMagnificationSpec = magnificationSpec;
    } else {
      this.mMagnificationSpec = null;
    }
    updateImeParent();
    if (magnificationSpec.scale != 1.0d) {
      applyMagnificationSpec(getPendingTransaction(), magnificationSpec);
    } else {
      clearMagnificationSpec(getPendingTransaction());
    }
    getPendingTransaction().apply();
  }

  public void reapplyMagnificationSpec() {
    if (this.mMagnificationSpec != null) {
      applyMagnificationSpec(getPendingTransaction(), this.mMagnificationSpec);
    }
  }

  @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
  public void onParentChanged(
      ConfigurationContainer configurationContainer,
      ConfigurationContainer configurationContainer2) {
    if (isReady()) {
      return;
    }
    this.mDisplayReady = true;
    DisplayManagerInternal displayManagerInternal = this.mWmService.mDisplayManagerInternal;
    if (displayManagerInternal != null) {
      displayManagerInternal.setDisplayInfoOverrideFromWindowManager(
          this.mDisplayId, getDisplayInfo());
      configureDisplayPolicy();
    }
    reconfigureDisplayLocked();
    onRequestedOverrideConfigurationChanged(getRequestedOverrideConfiguration());
    this.mWmService.mDisplayNotificationController.dispatchDisplayAdded(this);
    this.mWmService.mWindowContextListenerController.registerWindowContainerListener(
        getDisplayUiContext().getWindowContextToken(), this, 1000, -1, null);
  }

  @Override // com.android.server.wm.WindowContainer
  public void assignChildLayers(SurfaceControl.Transaction transaction) {
    assignRelativeLayerForIme(transaction, false);
    super.assignChildLayers(transaction);
  }

  public final void assignRelativeLayerForIme(SurfaceControl.Transaction transaction, boolean z) {
    ActivityRecord activityRecord;
    if (this.mImeWindowsContainer.isOrganized()) {
      return;
    }
    this.mImeWindowsContainer.setNeedsLayer();
    WindowState windowState = this.mImeLayeringTarget;
    if (this.isDefaultDisplay
        && this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked()) {
      if (!getDisplayPolicy().hasNavigationBar()
          && assignImeLayer(transaction, getSurfaceControl(), 1)) {
        return;
      }
    } else if (windowState != null
        && ((activityRecord = windowState.mActivityRecord) == null
            || !activityRecord.hasStartingWindow())) {
      InsetsControlTarget insetsControlTarget = this.mImeControlTarget;
      if ((windowState.getSurfaceControl() == null
              || windowState.mToken
                  != ((insetsControlTarget == null || insetsControlTarget.getWindow() == null)
                      ? null
                      : this.mImeControlTarget.getWindow().mToken)
              || windowState.inMultiWindowMode())
          ? false
          : true) {
        this.mImeWindowsContainer.assignRelativeLayer(
            transaction, windowState.getSurfaceControl(), 1, z);
        return;
      }
    }
    if (shouldBeRelativeLayer(windowState)) {
      this.mImeWindowsContainer.assignRelativeLayer(
          transaction, windowState.getSurfaceControl(), 1, z);
      return;
    }
    SurfaceControl surfaceControl = this.mInputMethodSurfaceParent;
    if (surfaceControl != null) {
      this.mImeWindowsContainer.assignRelativeLayer(transaction, surfaceControl, 1, z);
    }
  }

  public void assignRelativeLayerForImeTargetChild(
      SurfaceControl.Transaction transaction, WindowContainer windowContainer) {
    windowContainer.assignRelativeLayer(
        transaction, this.mImeWindowsContainer.getSurfaceControl(), 1);
  }

  public boolean assignImeLayer(
      SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i) {
    if (this.mImeWindowsContainer.getSurfaceControl() == null) {
      return false;
    }
    this.mImeWindowsContainer.setNeedsLayer();
    this.mImeWindowsContainer.assignRelativeLayer(transaction, surfaceControl, i);
    return true;
  }

  @Override // com.android.server.wm.DisplayArea.Dimmable, com.android.server.wm.WindowContainer
  public void prepareSurfaces() {
    Trace.traceBegin(32L, "prepareSurfaces");
    try {
      SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
      super.prepareSurfaces();
      SurfaceControl.mergeToGlobalTransaction(pendingTransaction);
    } finally {
      Trace.traceEnd(32L);
    }
  }

  public void deferUpdateImeTarget() {
    int i = this.mDeferUpdateImeTargetCount;
    if (i == 0) {
      this.mUpdateImeRequestedWhileDeferred = false;
    }
    this.mDeferUpdateImeTargetCount = i + 1;
  }

  public void continueUpdateImeTarget() {
    int i = this.mDeferUpdateImeTargetCount;
    if (i == 0) {
      return;
    }
    int i2 = i - 1;
    this.mDeferUpdateImeTargetCount = i2;
    if (i2 == 0 && this.mUpdateImeRequestedWhileDeferred) {
      computeImeTarget(true);
    }
  }

  public final boolean canUpdateImeTarget() {
    return this.mDeferUpdateImeTargetCount == 0;
  }

  public InputMonitor getInputMonitor() {
    return this.mInputMonitor;
  }

  public boolean getLastHasContent() {
    return this.mLastHasContent;
  }

  public void setLastHasContent() {
    this.mLastHasContent = true;
  }

  public void registerPointerEventListener(
      WindowManagerPolicyConstants.PointerEventListener pointerEventListener) {
    this.mPointerEventDispatcher.registerInputEventListener(pointerEventListener);
  }

  public void unregisterPointerEventListener(
      WindowManagerPolicyConstants.PointerEventListener pointerEventListener) {
    this.mPointerEventDispatcher.unregisterInputEventListener(pointerEventListener);
  }

  public void transferAppTransitionFrom(DisplayContent displayContent) {
    if (this.mAppTransition.transferFrom(displayContent.mAppTransition) && okToAnimate()) {
      this.mSkipAppTransitionAnimation = false;
    }
  }

  public void prepareAppTransition(int i) {
    prepareAppTransition(i, 0);
  }

  public void prepareAppTransition(int i, int i2) {
    if (this.mAppTransition.prepareAppTransition(i, i2) && okToAnimate() && i != 0) {
      this.mSkipAppTransitionAnimation = false;
    }
  }

  public void requestTransitionAndLegacyPrepare(int i, int i2) {
    requestTransitionAndLegacyPrepare(i, i2, null);
  }

  public void requestTransitionAndLegacyPrepare(int i, int i2, WindowContainer windowContainer) {
    prepareAppTransition(i, i2);
    this.mTransitionController.requestTransitionIfNeeded(i, i2, windowContainer, this);
  }

  public void executeAppTransition() {
    this.mTransitionController.setReady(this);
    if (this.mAppTransition.isTransitionSet()) {
      if (ProtoLogCache.WM_FORCE_DEBUG_APP_TRANSITIONS_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_FORCE_DEBUG_APP_TRANSITIONS,
            1235864419,
            4,
            "Execute app transition: %s, displayId: %d Callers=%s",
            new Object[] {
              String.valueOf(this.mAppTransition),
              Long.valueOf(this.mDisplayId),
              String.valueOf(Debug.getCallers(5))
            });
      }
      this.mAppTransition.setReady();
      this.mWmService.mWindowPlacerLocked.requestTraversal();
    }
  }

  public void handleAnimatingStoppedAndTransition() {
    this.mAppTransition.setIdle();
    for (int size = this.mNoAnimationNotifyOnTransitionFinished.size() - 1; size >= 0; size--) {
      this.mAppTransition.notifyAppTransitionFinishedLocked(
          (IBinder) this.mNoAnimationNotifyOnTransitionFinished.get(size));
    }
    this.mNoAnimationNotifyOnTransitionFinished.clear();
    this.mWallpaperController.hideDeferredWallpapersIfNeededLegacy();
    onAppTransitionDone();
    if (ProtoLogCache.WM_DEBUG_WALLPAPER_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_WALLPAPER, -182877285, 0, (String) null, (Object[]) null);
    }
    computeImeTarget(true);
    this.mWallpaperMayChange = true;
    this.mWmService.mFocusMayChange = true;
    this.pendingLayoutChanges |= 1;
  }

  public boolean isNextTransitionForward() {
    if (!this.mTransitionController.isShellTransitionsEnabled()) {
      return this.mAppTransition.containsTransitRequest(1)
          || this.mAppTransition.containsTransitRequest(3);
    }
    int collectingTransitionType = this.mTransitionController.getCollectingTransitionType();
    return collectingTransitionType == 1 || collectingTransitionType == 3;
  }

  public boolean supportsSystemDecorations() {
    return (this.mWmService.mDisplayWindowSettings.shouldShowSystemDecorsLocked(this)
            || (this.mDisplay.getFlags() & 64) != 0
            || forceDesktopMode())
        && this.mDisplayId != this.mWmService.mVr2dDisplayId
        && isTrusted();
  }

  public SurfaceControl getWindowingLayer() {
    return this.mWindowingLayer;
  }

  public DisplayArea.Tokens getImeContainer() {
    return this.mImeWindowsContainer;
  }

  public SurfaceControl getOverlayLayer() {
    return this.mOverlayLayer;
  }

  public SurfaceControl getInputOverlayLayer() {
    return this.mInputOverlayLayer;
  }

  public SurfaceControl getA11yOverlayLayer() {
    return this.mA11yOverlayLayer;
  }

  public SurfaceControl[] findRoundedCornerOverlays() {
    ArrayList arrayList = new ArrayList();
    for (WindowToken windowToken : this.mTokenMap.values()) {
      if (windowToken.mRoundedCornerOverlay && windowToken.isVisible()) {
        arrayList.add(windowToken.mSurfaceControl);
      }
    }
    return (SurfaceControl[]) arrayList.toArray(new SurfaceControl[0]);
  }

  public boolean updateSystemGestureExclusion() {
    if (this.mSystemGestureExclusionListeners.getRegisteredCallbackCount() == 0) {
      return false;
    }
    Region obtain = Region.obtain();
    this.mSystemGestureExclusionWasRestricted =
        calculateSystemGestureExclusion(obtain, this.mSystemGestureExclusionUnrestricted);
    try {
      if (this.mSystemGestureExclusion.equals(obtain)) {
        return false;
      }
      this.mSystemGestureExclusion.set(obtain);
      Region region =
          this.mSystemGestureExclusionWasRestricted
              ? this.mSystemGestureExclusionUnrestricted
              : null;
      for (int beginBroadcast = this.mSystemGestureExclusionListeners.beginBroadcast() - 1;
          beginBroadcast >= 0;
          beginBroadcast--) {
        try {
          this.mSystemGestureExclusionListeners
              .getBroadcastItem(beginBroadcast)
              .onSystemGestureExclusionChanged(this.mDisplayId, obtain, region);
        } catch (RemoteException e) {
          Slog.e(
              StartingSurfaceController.TAG, "Failed to notify SystemGestureExclusionListener", e);
        }
      }
      this.mSystemGestureExclusionListeners.finishBroadcast();
      return true;
    } finally {
      obtain.recycle();
    }
  }

  public boolean calculateSystemGestureExclusion(final Region region, final Region region2) {
    region.setEmpty();
    if (region2 != null) {
      region2.setEmpty();
    }
    final Region obtain = Region.obtain();
    DisplayFrames displayFrames = this.mDisplayFrames;
    obtain.set(0, 0, displayFrames.mWidth, displayFrames.mHeight);
    InsetsState rawInsetsState = this.mInsetsStateController.getRawInsetsState();
    Rect displayFrame = rawInsetsState.getDisplayFrame();
    Insets calculateInsets =
        rawInsetsState.calculateInsets(displayFrame, WindowInsets.Type.systemGestures(), false);
    Rect rect = this.mSystemGestureFrameLeft;
    int i = displayFrame.left;
    rect.set(i, displayFrame.top, calculateInsets.left + i, displayFrame.bottom);
    Rect rect2 = this.mSystemGestureFrameRight;
    int i2 = displayFrame.right;
    rect2.set(i2 - calculateInsets.right, displayFrame.top, i2, displayFrame.bottom);
    final Region obtain2 = Region.obtain();
    final Region obtain3 = Region.obtain();
    int i3 = this.mSystemGestureExclusionLimit;
    final int[] iArr = {i3, i3};
    final RecentsAnimationController recentsAnimationController =
        this.mWmService.getRecentsAnimationController();
    forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda33
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$calculateSystemGestureExclusion$42(
                recentsAnimationController,
                obtain,
                obtain2,
                obtain3,
                iArr,
                region,
                region2,
                (WindowState) obj);
          }
        },
        true);
    obtain3.recycle();
    obtain2.recycle();
    obtain.recycle();
    int i4 = iArr[0];
    int i5 = this.mSystemGestureExclusionLimit;
    return i4 < i5 || iArr[1] < i5;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$calculateSystemGestureExclusion$42(
      RecentsAnimationController recentsAnimationController,
      Region region,
      Region region2,
      Region region3,
      int[] iArr,
      Region region4,
      Region region5,
      WindowState windowState) {
    boolean z =
        recentsAnimationController != null
            && recentsAnimationController.shouldApplyInputConsumer(windowState.getActivityRecord());
    if (windowState.canReceiveTouchInput()
        && windowState.isVisible()
        && (windowState.getAttrs().flags & 16) == 0
        && !region.isEmpty()
        && !z) {
      windowState.getEffectiveTouchableRegion(region2);
      region2.op(region, Region.Op.INTERSECT);
      if (windowState.isImplicitlyExcludingAllSystemGestures()) {
        region3.set(region2);
      } else {
        RegionUtils.rectListToRegion(windowState.getSystemGestureExclusion(), region3);
        region3.scale(windowState.mGlobalScale);
        Rect rect = windowState.getWindowFrames().mFrame;
        region3.translate(rect.left, rect.top);
        region3.op(region2, Region.Op.INTERSECT);
      }
      if (needsGestureExclusionRestrictions(windowState, false)) {
        iArr[0] =
            addToGlobalAndConsumeLimit(
                region3, region4, this.mSystemGestureFrameLeft, iArr[0], windowState, 0);
        iArr[1] =
            addToGlobalAndConsumeLimit(
                region3, region4, this.mSystemGestureFrameRight, iArr[1], windowState, 1);
        Region obtain = Region.obtain(region3);
        obtain.op(this.mSystemGestureFrameLeft, Region.Op.DIFFERENCE);
        obtain.op(this.mSystemGestureFrameRight, Region.Op.DIFFERENCE);
        region4.op(obtain, Region.Op.UNION);
        obtain.recycle();
      } else {
        if (needsGestureExclusionRestrictions(windowState, true)) {
          addToGlobalAndConsumeLimit(
              region3, region4, this.mSystemGestureFrameLeft, Integer.MAX_VALUE, windowState, 0);
          addToGlobalAndConsumeLimit(
              region3, region4, this.mSystemGestureFrameRight, Integer.MAX_VALUE, windowState, 1);
        }
        region4.op(region3, Region.Op.UNION);
      }
      if (region5 != null) {
        region5.op(region3, Region.Op.UNION);
      }
      region.op(region2, Region.Op.DIFFERENCE);
    }
  }

  public static boolean needsGestureExclusionRestrictions(WindowState windowState, boolean z) {
    WindowManager.LayoutParams layoutParams = windowState.mAttrs;
    int i = layoutParams.type;
    return (((!windowState.isRequestedVisible(WindowInsets.Type.navigationBars())
                    && windowState.mAttrs.insetsFlags.behavior == 2)
                && !z)
            || i == 2011
            || i == 2040
            || windowState.getActivityType() == 2
            || (layoutParams.privateFlags & 32) != 0)
        ? false
        : true;
  }

  public static boolean logsGestureExclusionRestrictions(WindowState windowState) {
    WindowManager.LayoutParams attrs;
    int i;
    return windowState.mWmService.mConstants.mSystemGestureExclusionLogDebounceTimeoutMillis > 0
        && (i = (attrs = windowState.getAttrs()).type) != 2013
        && i != 3
        && i != 2019
        && (attrs.flags & 16) == 0
        && needsGestureExclusionRestrictions(windowState, true)
        && windowState.getDisplayContent().mDisplayPolicy.hasSideGestures();
  }

  public static int addToGlobalAndConsumeLimit(
      Region region, final Region region2, Rect rect, int i, WindowState windowState, int i2) {
    Region obtain = Region.obtain(region);
    obtain.op(rect, Region.Op.INTERSECT);
    final int[] iArr = {i};
    final int[] iArr2 = {0};
    RegionUtils.forEachRectReverse(
        obtain,
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda66
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.lambda$addToGlobalAndConsumeLimit$43(iArr, iArr2, region2, (Rect) obj);
          }
        });
    windowState.setLastExclusionHeights(i2, iArr2[0], i - iArr[0]);
    obtain.recycle();
    return iArr[0];
  }

  public static /* synthetic */ void lambda$addToGlobalAndConsumeLimit$43(
      int[] iArr, int[] iArr2, Region region, Rect rect) {
    if (iArr[0] <= 0) {
      return;
    }
    int height = rect.height();
    iArr2[0] = iArr2[0] + height;
    int i = iArr[0];
    if (height > i) {
      rect.top = rect.bottom - i;
    }
    iArr[0] = i - height;
    region.op(rect, Region.Op.UNION);
  }

  public void registerSystemGestureExclusionListener(
      ISystemGestureExclusionListener iSystemGestureExclusionListener) {
    this.mSystemGestureExclusionListeners.register(iSystemGestureExclusionListener);
    if (this.mSystemGestureExclusionListeners.getRegisteredCallbackCount() == 1
        ? updateSystemGestureExclusion()
        : false) {
      return;
    }
    try {
      iSystemGestureExclusionListener.onSystemGestureExclusionChanged(
          this.mDisplayId,
          this.mSystemGestureExclusion,
          this.mSystemGestureExclusionWasRestricted
              ? this.mSystemGestureExclusionUnrestricted
              : null);
    } catch (RemoteException e) {
      Slog.e(
          StartingSurfaceController.TAG,
          "Failed to notify SystemGestureExclusionListener during register",
          e);
    }
  }

  public void unregisterSystemGestureExclusionListener(
      ISystemGestureExclusionListener iSystemGestureExclusionListener) {
    this.mSystemGestureExclusionListeners.unregister(iSystemGestureExclusionListener);
  }

  public void updateKeepClearAreas() {
    ArraySet arraySet = new ArraySet();
    ArraySet arraySet2 = new ArraySet();
    getKeepClearAreas(arraySet, arraySet2);
    if (this.mRestrictedKeepClearAreas.equals(arraySet)
        && this.mUnrestrictedKeepClearAreas.equals(arraySet2)) {
      return;
    }
    this.mRestrictedKeepClearAreas = arraySet;
    this.mUnrestrictedKeepClearAreas = arraySet2;
    this.mWmService.mDisplayNotificationController.dispatchKeepClearAreasChanged(
        this, arraySet, arraySet2);
  }

  public void getKeepClearAreas(final Set set, final Set set2) {
    final Matrix matrix = new Matrix();
    final float[] fArr = new float[9];
    final RecentsAnimationController recentsAnimationController =
        this.mWmService.getRecentsAnimationController();
    forAllWindows(
        new ToBooleanFunction() { // from class:
                                  // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda40
          public final boolean apply(Object obj) {
            boolean lambda$getKeepClearAreas$45;
            lambda$getKeepClearAreas$45 =
                DisplayContent.lambda$getKeepClearAreas$45(
                    RecentsAnimationController.this, set, set2, matrix, fArr, (WindowState) obj);
            return lambda$getKeepClearAreas$45;
          }
        },
        true);
  }

  public static /* synthetic */ boolean lambda$getKeepClearAreas$45(
      RecentsAnimationController recentsAnimationController,
      Set set,
      final Set set2,
      Matrix matrix,
      float[] fArr,
      WindowState windowState) {
    if (recentsAnimationController != null
        && recentsAnimationController.shouldApplyInputConsumer(windowState.getActivityRecord())) {
      return false;
    }
    if (windowState.isVisible() && !windowState.inPinnedWindowingMode()) {
      windowState.getKeepClearAreas(set, set2, matrix, fArr);
      if (windowState.mIsImWindow) {
        Region obtain = Region.obtain();
        windowState.getEffectiveTouchableRegion(obtain);
        RegionUtils.forEachRect(
            obtain,
            new Consumer() { // from class:
                             // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda63
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                set2.add((Rect) obj);
              }
            });
      }
    }
    return windowState.getWindowType() == 1 && windowState.getWindowingMode() == 1;
  }

  public Set getKeepClearAreas() {
    ArraySet arraySet = new ArraySet();
    getKeepClearAreas(arraySet, arraySet);
    return arraySet;
  }

  public MetricsLogger getMetricsLogger() {
    if (this.mMetricsLogger == null) {
      this.mMetricsLogger = new MetricsLogger();
    }
    return this.mMetricsLogger;
  }

  public void onDisplayChanged() {
    int i = this.mDisplayInfo.state;
    updateDisplayInfo();
    int displayId = this.mDisplay.getDisplayId();
    int i2 = this.mDisplayInfo.state;
    if (displayId != 0) {
      if (displayId == 2) {
        if (i2 == 1) {
          this.mAtmService.mDexController.deactivateDexDisplayLocked(this);
        } else if (i2 == 2) {
          this.mAtmService.mDexController.activateDexDisplayLocked(this);
        }
      } else if (displayId != 4) {
        if (i2 == 1) {
          this.mOffTokenAcquirer.acquire(this.mDisplayId);
        } else if (i2 == 2) {
          this.mOffTokenAcquirer.release(this.mDisplayId);
        }
      }
      if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
        ProtoLogImpl.v(
            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
            -1883484959,
            5,
            "Content Recording: Display %d state is now (%d), so update recording?",
            new Object[] {Long.valueOf(this.mDisplayId), Long.valueOf(i2)});
      }
      if (i != i2) {
        updateRecording();
      }
    }
    if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
      int i3 = getConfiguration().semDisplayDeviceType;
      if (i3 == 0) {
        this.mWmService.mAtmService.mMultiWindowFoldController.updateMainDisplayBounds(
            this.mBaseDisplayWidth, this.mBaseDisplayHeight);
      } else if (i3 == 5) {
        this.mWmService.mAtmService.mMultiWindowFoldController.updateCoverDisplayBounds(
            this.mBaseDisplayWidth, this.mBaseDisplayHeight);
      }
    }
    this.mWallpaperController.resetLargestDisplay(this.mDisplay);
    if (Display.isSuspendedState(i) && !Display.isSuspendedState(i2) && i2 != 0) {
      this.mWmService.mWindowContextListenerController.dispatchPendingConfigurationIfNeeded(
          this.mDisplayId);
    }
    this.mWmService.requestTraversal();
  }

  public Task getFocusedRootTask() {
    return (Task)
        getItemFromTaskDisplayAreas(
            new Function() { // from class:
                             // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda56
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                return ((TaskDisplayArea) obj).getFocusedRootTask();
              }
            });
  }

  public void removeRootTasksInWindowingModes(final int... iArr) {
    if (iArr == null || iArr.length == 0) {
      return;
    }
    final ArrayList arrayList = new ArrayList();
    forAllRootTasks(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda57
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.lambda$removeRootTasksInWindowingModes$46(iArr, arrayList, (Task) obj);
          }
        });
    for (int size = arrayList.size() - 1; size >= 0; size--) {
      this.mRootWindowContainer.mTaskSupervisor.removeRootTask((Task) arrayList.get(size));
    }
  }

  public static /* synthetic */ void lambda$removeRootTasksInWindowingModes$46(
      int[] iArr, ArrayList arrayList, Task task) {
    for (int i : iArr) {
      if (!task.mCreatedByOrganizer
          && task.getWindowingMode() == i
          && task.isActivityTypeStandardOrUndefined()) {
        arrayList.add(task);
      }
    }
  }

  public void removeRootTasksWithActivityTypes(final int... iArr) {
    if (iArr == null || iArr.length == 0) {
      return;
    }
    final ArrayList arrayList = new ArrayList();
    forAllRootTasks(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda36
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.lambda$removeRootTasksWithActivityTypes$47(iArr, arrayList, (Task) obj);
          }
        });
    for (int size = arrayList.size() - 1; size >= 0; size--) {
      this.mRootWindowContainer.mTaskSupervisor.removeRootTask((Task) arrayList.get(size));
    }
  }

  public static /* synthetic */ void lambda$removeRootTasksWithActivityTypes$47(
      int[] iArr, ArrayList arrayList, Task task) {
    for (int i : iArr) {
      if (task.mCreatedByOrganizer) {
        for (int childCount = task.getChildCount() - 1; childCount >= 0; childCount--) {
          Task task2 = (Task) task.getChildAt(childCount);
          if (task2.getActivityType() == i) {
            arrayList.add(task2);
          }
        }
      } else if (task.getActivityType() == i) {
        arrayList.add(task);
      }
    }
  }

  public ActivityRecord topRunningActivity() {
    return topRunningActivity(false);
  }

  public ActivityRecord topRunningActivity(final boolean z) {
    return (ActivityRecord)
        getItemFromTaskDisplayAreas(
            new Function() { // from class:
                             // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda58
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                ActivityRecord lambda$topRunningActivity$48;
                lambda$topRunningActivity$48 =
                    DisplayContent.lambda$topRunningActivity$48(z, (TaskDisplayArea) obj);
                return lambda$topRunningActivity$48;
              }
            });
  }

  public static /* synthetic */ ActivityRecord lambda$topRunningActivity$48(
      boolean z, TaskDisplayArea taskDisplayArea) {
    return taskDisplayArea.topRunningActivity(z);
  }

  public boolean updateDisplayOverrideConfigurationLocked() {
    RecentsAnimationController recentsAnimationController =
        this.mWmService.getRecentsAnimationController();
    if (recentsAnimationController != null) {
      recentsAnimationController.cancelAnimationForDisplayChange();
    }
    Configuration configuration = new Configuration();
    computeScreenConfiguration(configuration);
    if (this.mDisplayId == 2) {
      this.mDisplayPolicy.updateDisplayOverrideConfiguration();
      computeScreenConfiguration(configuration);
    }
    this.mAtmService.f1734mH.sendMessage(
        PooledLambda.obtainMessage(
            new ActivityTaskManagerService$$ExternalSyntheticLambda9(),
            this.mAtmService.mAmInternal,
            Integer.valueOf(this.mDisplayId)));
    if (isDexMode()) {
      float f = configuration.fontScale;
      Settings.System.clearConfiguration(configuration);
      configuration.fontScale = f;
    } else {
      Settings.System.clearConfiguration(configuration);
    }
    updateDisplayOverrideConfigurationLocked(
        configuration, null, false, this.mAtmService.mTmpUpdateConfigurationResult);
    return this.mAtmService.mTmpUpdateConfigurationResult.changes != 0;
  }

  public boolean updateDisplayOverrideConfigurationLocked(
      Configuration configuration,
      ActivityRecord activityRecord,
      boolean z,
      ActivityTaskManagerService.UpdateConfigurationResult updateConfigurationResult) {
    this.mAtmService.deferWindowLayout();
    int i = 0;
    if (configuration != null) {
      try {
        if (this.mDisplayId == 0) {
          i = this.mAtmService.updateGlobalConfigurationLocked(configuration, false, false, -10000);
        } else {
          i = performDisplayOverrideConfigUpdate(configuration);
        }
      } catch (Throwable th) {
        this.mAtmService.continueWindowLayout();
        throw th;
      }
    }
    boolean ensureConfigAndVisibilityAfterUpdate =
        !z ? this.mAtmService.ensureConfigAndVisibilityAfterUpdate(activityRecord, i) : true;
    this.mAtmService.continueWindowLayout();
    if (updateConfigurationResult != null) {
      updateConfigurationResult.changes = i;
      updateConfigurationResult.activityRelaunched = !ensureConfigAndVisibilityAfterUpdate;
    }
    return ensureConfigAndVisibilityAfterUpdate;
  }

  public int performDisplayOverrideConfigUpdate(Configuration configuration) {
    Message obtainMessage;
    if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && this.isDefaultDisplay) {
      int i = this.mTempConfig.semDisplayDeviceType;
      int i2 = configuration.semDisplayDeviceType;
      if (i != i2) {
        this.mAtmService.mMultiWindowFoldController.onDisplayDeviceTypeChanged(i2);
      }
    }
    this.mTempConfig.setTo(getRequestedOverrideConfiguration());
    boolean checkScreenDpSizeChanges =
        this.mAtmService.mExt.checkScreenDpSizeChanges(this.mTempConfig, configuration);
    float width =
        configuration.windowConfiguration.getBounds().width()
            / this.mTempConfig.windowConfiguration.getBounds().width();
    float height =
        configuration.windowConfiguration.getBounds().height()
            / this.mTempConfig.windowConfiguration.getBounds().height();
    this.mTmpPrevBounds.set(this.mTempConfig.windowConfiguration.getBounds());
    if (isMultiTaskingDisplay()) {
      configuration.setLocales(this.mRootWindowContainer.getConfiguration().getLocales());
    }
    int updateFrom = this.mTempConfig.updateFrom(configuration);
    if (updateFrom != 0) {
      Slog.i(
          StartingSurfaceController.TAG,
          "[d"
              + this.mDisplayId
              + "] Override config changes="
              + Integer.toHexString(updateFrom)
              + " "
              + this.mTempConfig
              + ", callers="
              + Debug.getCallers(5));
      if (isReady() && this.mTransitionController.isShellTransitionsEnabled()) {
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_DISPLAY_CHANGE
            && !this.mTransitionController.isCollecting()) {
          TransitionRequestInfo.DisplayChange createDisplayChangeIfNeeded =
              createDisplayChangeIfNeeded(getRequestedOverrideConfiguration(), configuration);
          requestChangeTransitionIfNeeded(updateFrom, createDisplayChangeIfNeeded);
          boolean z =
              (createDisplayChangeIfNeeded == null && (Integer.MIN_VALUE & updateFrom) == 0)
                  ? false
                  : true;
          DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(2);
          if (z
              && this.mTransitionController.isCollecting()
              && this.isDefaultDisplay
              && displayContent != null
              && this.mAtmService.mDexController.getDexModeLocked() == 2) {
            this.mTransitionController.collect(displayContent);
            this.mTransitionController.collectForDisplayAreaChange(displayContent);
          }
        } else {
          requestChangeTransitionIfNeeded(updateFrom, null);
        }
      }
      if (CoreRune.MT_SUPPORT_SIZE_COMPAT) {
        SizeCompatPolicyManager.get().performDisplayOverrideConfigUpdate(this, this.mTempConfig);
      }
      onRequestedOverrideConfigurationChanged(this.mTempConfig);
      boolean z2 = (updateFrom & IInstalld.FLAG_USE_QUOTA) != 0;
      boolean z3 = (updateFrom & 512) != 0;
      if (DesktopModeFeature.SUPPORT_STANDALONE
          && this.mDisplayId == 0
          && getDesktopModeManagerInternal() != null
          && (z2 || z3)) {
        getDesktopModeManagerInternal().isConfigurationChangedFromDeX(getConfiguration());
      }
      if (z2 && this.mDisplayId == 0) {
        this.mAtmService.mAppWarnings.onDensityChanged();
        if (!checkScreenDpSizeChanges) {
          obtainMessage =
              PooledLambda.obtainMessage(
                  new QuadConsumer() { // from class:
                                       // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda34
                    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                      ((ActivityManagerInternal) obj)
                          .killAllBackgroundProcessesExcept(
                              ((Integer) obj2).intValue(),
                              ((Integer) obj3).intValue(),
                              (Bundle) obj4);
                    }
                  },
                  this.mAtmService.mAmInternal,
                  24,
                  6,
                  (Object) null);
        } else {
          obtainMessage =
              PooledLambda.obtainMessage(
                  new TriConsumer() { // from class:
                                      // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda35
                    public final void accept(Object obj, Object obj2, Object obj3) {
                      ((ActivityManagerInternal) obj)
                          .killAllBackgroundProcessesExcept(
                              ((Integer) obj2).intValue(), ((Integer) obj3).intValue());
                    }
                  },
                  this.mAtmService.mAmInternal,
                  24,
                  6);
        }
        this.mAtmService.f1734mH.sendMessage(obtainMessage);
      }
      this.mAtmService.mMultiTaskingController.performDisplayOverrideConfigUpdate(
          updateFrom,
          this.mDisplayId,
          width,
          height,
          this.mTmpPrevBounds,
          this.mTempConfig.windowConfiguration.getBounds());
      if (DesktopModeFeature.SUPPORT_STANDALONE
          && this.mDisplayId == 0
          && getDesktopModeManagerInternal() != null) {
        getDesktopModeManagerInternal().onConfigurationChanged(getConfiguration());
      }
      this.mWmService.mDisplayNotificationController.dispatchDisplayChanged(
          this, getConfiguration());
    }
    return updateFrom;
  }

  public final DesktopModeManagerInternal getDesktopModeManagerInternal() {
    if (this.mDesktopModeManagerInternal == null) {
      this.mDesktopModeManagerInternal =
          (DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class);
    }
    return this.mDesktopModeManagerInternal;
  }

  @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
            // com.android.server.wm.ConfigurationContainer
  public void onRequestedOverrideConfigurationChanged(Configuration configuration) {
    UdcCutoutPolicy udcCutoutPolicy = this.mUdcCutoutPolicy;
    if (udcCutoutPolicy != null && udcCutoutPolicy.hasUdcCutout()) {
      this.mUdcCutoutPolicy.onRequestedOverrideConfigurationChanged(configuration);
    }
    Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
    int rotation = requestedOverrideConfiguration.windowConfiguration.getRotation();
    int rotation2 = configuration.windowConfiguration.getRotation();
    if (rotation != -1 && rotation2 != -1 && rotation != rotation2) {
      applyRotationAndFinishFixedRotation(rotation, rotation2);
    }
    this.mCurrentOverrideConfigurationChanges = requestedOverrideConfiguration.diff(configuration);
    super.onRequestedOverrideConfigurationChanged(configuration);
    this.mCurrentOverrideConfigurationChanges = 0;
    if (this.mWaitingForConfig) {
      this.mWaitingForConfig = false;
      this.mWmService.mLastFinishedFreezeSource = "new-config";
    }
    this.mAtmService.addWindowLayoutReasons(1);
  }

  @Override // com.android.server.wm.WindowContainer
  public void onResize() {
    super.onResize();
    if (this.mWmService.mAccessibilityController.hasCallbacks()) {
      this.mWmService.mAccessibilityController.onDisplaySizeChanged(this);
    }
  }

  public final void applyRotationAndFinishFixedRotation(final int i, final int i2) {
    ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
    if (activityRecord == null) {
      lambda$applyRotationAndFinishFixedRotation$49(i, i2);
    } else {
      activityRecord.finishFixedRotationTransform(
          new Runnable() { // from class:
                           // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda44
            @Override // java.lang.Runnable
            public final void run() {
              DisplayContent.this.lambda$applyRotationAndFinishFixedRotation$49(i, i2);
            }
          });
      setFixedRotationLaunchingAppUnchecked(null);
    }
  }

  public void handleActivitySizeCompatModeIfNeeded(ActivityRecord activityRecord) {
    Task organizedTask = activityRecord.getOrganizedTask();
    if (organizedTask == null) {
      this.mActiveSizeCompatActivities.remove(activityRecord);
      return;
    }
    if (CoreRune.FW_FIXED_ASPECT_RATIO_MODE
        && this.isDefaultDisplay
        && activityRecord.isState(ActivityRecord.State.RESUMED)
        && activityRecord.mCompatRecord.isFixedAspectRatioModeEnabled()) {
      if (this.mActiveSizeCompatActivities.add(activityRecord)) {
        organizedTask.onSizeCompatActivityChanged();
        return;
      }
      return;
    }
    if (CoreRune.FW_ORIENTATION_CONTROL_WITH_ROTATION_COMPAT
        && this.isDefaultDisplay
        && activityRecord.isState(ActivityRecord.State.RESUMED)) {
      BoundsCompatRecord boundsCompatRecord = activityRecord.mCompatRecord;
      if (boundsCompatRecord.mCanRotationCompatMode
          && boundsCompatRecord.getController() == this.mAtmService.mExt.mOrientationController
          && activityRecord.getCompatDisplayInsets() != null
          && activityRecord.getCompatDisplayInsets().mCanRotationCompatMode) {
        if (this.mActiveSizeCompatActivities.remove(activityRecord)) {
          organizedTask.onSizeCompatActivityChanged();
          return;
        }
        return;
      }
    }
    if (activityRecord.isState(ActivityRecord.State.RESUMED) && activityRecord.inSizeCompatMode()) {
      if (this.mActiveSizeCompatActivities.add(activityRecord)) {
        organizedTask.onSizeCompatActivityChanged();
      }
    } else if (this.mActiveSizeCompatActivities.remove(activityRecord)) {
      organizedTask.onSizeCompatActivityChanged();
    }
  }

  public boolean isUidPresent(int i) {
    Predicate obtainPredicate =
        PooledLambda.obtainPredicate(
            new DisplayContent$$ExternalSyntheticLambda24(),
            PooledLambda.__(ActivityRecord.class),
            Integer.valueOf(i));
    boolean z = this.mDisplayContent.getActivity(obtainPredicate) != null;
    obtainPredicate.recycle();
    return z;
  }

  public boolean isRemoved() {
    return this.mRemoved;
  }

  public boolean isRemoving() {
    return this.mRemoving;
  }

  public void remove() {
    this.mRemoving = true;
    this.mRootWindowContainer.mTaskSupervisor.beginDeferResume();
    try {
      Task task =
          (Task)
              reduceOnAllTaskDisplayAreas(
                  new BiFunction() { // from class:
                                     // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda20
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                      Task lambda$remove$50;
                      lambda$remove$50 =
                          DisplayContent.lambda$remove$50((TaskDisplayArea) obj, (Task) obj2);
                      return lambda$remove$50;
                    }
                  },
                  null,
                  false);
      this.mRootWindowContainer.mTaskSupervisor.endDeferResume();
      this.mRemoved = true;
      ContentRecorder contentRecorder = this.mContentRecorder;
      if (contentRecorder != null) {
        contentRecorder.stopRecording();
      }
      if (task != null) {
        task.resumeNextFocusAfterReparent();
      }
      releaseSelfIfNeeded();
      this.mDisplayPolicy.release();
      if (this.mAllSleepTokens.isEmpty()) {
        return;
      }
      this.mAllSleepTokens.forEach(
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              DisplayContent.this.lambda$remove$51((RootWindowContainer.SleepToken) obj);
            }
          });
      this.mAllSleepTokens.clear();
      if (ProtoLogCache.WM_DEBUG_STATES_enabled) {
        ProtoLogImpl.d(
            ProtoLogGroup.WM_DEBUG_STATES, -1716263676, 0, (String) null, (Object[]) null);
      }
      this.mAtmService.updateSleepIfNeededLocked();
    } catch (Throwable th) {
      this.mRootWindowContainer.mTaskSupervisor.endDeferResume();
      throw th;
    }
  }

  public static /* synthetic */ Task lambda$remove$50(TaskDisplayArea taskDisplayArea, Task task) {
    Task remove = taskDisplayArea.remove();
    return remove != null ? remove : task;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$remove$51(RootWindowContainer.SleepToken sleepToken) {
    this.mRootWindowContainer.mSleepTokens.remove(sleepToken.mHashKey);
  }

  public void releaseSelfIfNeeded() {
    if (this.mRemoved) {
      if (getDisplayId() == 4) {
        removeIfPossible();
      }
      if (!forAllRootTasks(
              new Predicate() { // from class:
                                // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda53
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$releaseSelfIfNeeded$52;
                  lambda$releaseSelfIfNeeded$52 =
                      DisplayContent.lambda$releaseSelfIfNeeded$52((Task) obj);
                  return lambda$releaseSelfIfNeeded$52;
                }
              })
          && getRootTaskCount() > 0) {
        forAllRootTasks(
            new Consumer() { // from class:
                             // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda54
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                ((Task) obj).removeIfPossible("releaseSelfIfNeeded");
              }
            });
      } else if (getTopRootTask() == null) {
        removeIfPossible();
      }
    }
  }

  public static /* synthetic */ boolean lambda$releaseSelfIfNeeded$52(Task task) {
    return !task.isActivityTypeHome() || task.hasChild();
  }

  public IntArray getPresentUIDs() {
    this.mDisplayAccessUIDs.clear();
    this.mDisplayContent.forAllActivities(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda22
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$getPresentUIDs$54((ActivityRecord) obj);
          }
        });
    return this.mDisplayAccessUIDs;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$getPresentUIDs$54(ActivityRecord activityRecord) {
    this.mDisplayAccessUIDs.add(activityRecord.getUid());
  }

  public boolean shouldDestroyContentOnRemove() {
    return this.mDisplay.getRemoveMode() == 1;
  }

  public boolean shouldSleep() {
    return (getRootTaskCount() == 0 || !this.mAllSleepTokens.isEmpty())
        && this.mAtmService.mRunningVoice == null;
  }

  public void ensureActivitiesVisible(
      final ActivityRecord activityRecord, final int i, final boolean z, final boolean z2) {
    if (this.mInEnsureActivitiesVisible) {
      return;
    }
    this.mAtmService.mTaskSupervisor.beginActivityVisibilityUpdate(this);
    try {
      this.mInEnsureActivitiesVisible = true;
      forAllRootTasks(
          new Consumer() { // from class:
                           // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda42
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              ((Task) obj).ensureActivitiesVisible(ActivityRecord.this, i, z, z2);
            }
          });
      if (this.mTransitionController.useShellTransitionsRotation()
          && this.mTransitionController.isCollecting()
          && this.mWallpaperController.getWallpaperTarget() != null) {
        this.mWallpaperController.adjustWallpaperWindows();
        if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED) {
          CustomLetterboxConfiguration.adjustLetterboxes(this);
        }
        if (CoreRune.FW_SUPPORT_OCCLUDES_PARENT_CHANGE_CALLBACK && this.isDefaultDisplay) {
          this.mWmService.mExt.updateOccludeTargetIfNeeded(this);
        }
      }
    } finally {
      this.mAtmService.mTaskSupervisor.endActivityVisibilityUpdate();
      this.mInEnsureActivitiesVisible = false;
    }
  }

  public boolean isSleeping() {
    return this.mSleeping;
  }

  public void setIsSleeping(boolean z) {
    this.mSleeping = z;
  }

  public void notifyKeyguardFlagsChanged() {
    if (isKeyguardLocked()) {
      boolean isTransitionSet = this.mAppTransition.isTransitionSet();
      if (!isTransitionSet) {
        prepareAppTransition(0);
      }
      this.mRootWindowContainer.ensureActivitiesVisible(null, 0, false);
      if (isTransitionSet) {
        return;
      }
      executeAppTransition();
    }
  }

  public boolean canShowWithInsecureKeyguard() {
    return (this.mDisplay.getFlags() & 32) != 0;
  }

  public boolean isKeyguardLocked() {
    return this.mRootWindowContainer
        .mTaskSupervisor
        .getKeyguardController()
        .isKeyguardLocked(this.mDisplayId);
  }

  public boolean isKeyguardGoingAway() {
    return this.mRootWindowContainer
        .mTaskSupervisor
        .getKeyguardController()
        .isKeyguardGoingAway(this.mDisplayId);
  }

  public boolean isKeyguardAlwaysUnlocked() {
    return (this.mDisplayInfo.flags & 512) != 0;
  }

  public boolean isAodShowing() {
    return this.mRootWindowContainer
        .mTaskSupervisor
        .getKeyguardController()
        .isAodShowing(this.mDisplayId);
  }

  public boolean hasOwnFocus() {
    return this.mWmService.mPerDisplayFocusEnabled
        || (this.mDisplayInfo.flags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
  }

  public boolean isKeyguardOccluded() {
    return this.mRootWindowContainer
        .mTaskSupervisor
        .getKeyguardController()
        .isDisplayOccluded(this.mDisplayId);
  }

  public Task getTaskOccludingKeyguard() {
    KeyguardController keyguardController =
        this.mRootWindowContainer.mTaskSupervisor.getKeyguardController();
    if (keyguardController.getTopOccludingActivity(this.mDisplayId) != null) {
      return keyguardController.getTopOccludingActivity(this.mDisplayId).getRootTask();
    }
    if (keyguardController.getDismissKeyguardActivity(this.mDisplayId) != null) {
      return keyguardController.getDismissKeyguardActivity(this.mDisplayId).getRootTask();
    }
    return null;
  }

  public static /* synthetic */ void lambda$removeAllTasks$56(Task task) {
    task.getRootTask().removeChild(task, "removeAllTasks");
  }

  public void removeAllTasks() {
    forAllTasks(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda41
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.lambda$removeAllTasks$56((Task) obj);
          }
        });
  }

  public Context getDisplayUiContext() {
    return this.mDisplayPolicy.getSystemUiContext();
  }

  @Override // com.android.server.wm.DisplayArea
  public boolean setIgnoreOrientationRequest(boolean z) {
    if (this.mSetIgnoreOrientationRequest == z) {
      return false;
    }
    boolean ignoreOrientationRequest = super.setIgnoreOrientationRequest(z);
    this.mWmService.mDisplayWindowSettings.setIgnoreOrientationRequest(
        this, this.mSetIgnoreOrientationRequest);
    return ignoreOrientationRequest;
  }

  public void onIsIgnoreOrientationRequestDisabledChanged() {
    ActivityRecord activityRecord = this.mFocusedApp;
    if (activityRecord != null) {
      onLastFocusedTaskDisplayAreaChanged(activityRecord.getDisplayArea());
    }
    if (this.mSetIgnoreOrientationRequest) {
      updateOrientation();
    }
  }

  public WindowState findScrollCaptureTargetWindow(WindowState windowState, int i) {
    return getWindow(
        new Predicate(windowState, i) { // from class: com.android.server.wm.DisplayContent.4
          public boolean behindTopWindow;
          public final /* synthetic */ WindowState val$searchBehind;
          public final /* synthetic */ int val$taskId;

          {
            this.val$searchBehind = windowState;
            this.val$taskId = i;
            this.behindTopWindow = windowState == null;
          }

          @Override // java.util.function.Predicate
          public boolean test(WindowState windowState2) {
            if (!this.behindTopWindow) {
              if (windowState2 == this.val$searchBehind) {
                this.behindTopWindow = true;
              }
              return false;
            }
            if (this.val$taskId == -1) {
              if (!windowState2.canReceiveKeys()) {
                return false;
              }
            } else {
              Task task = windowState2.getTask();
              if (task == null || !task.isTaskId(this.val$taskId)) {
                return false;
              }
            }
            return !windowState2.isSecureLocked();
          }
        });
  }

  public void setSandboxDisplayApis(boolean z) {
    this.mSandboxDisplayApis = z;
  }

  public boolean sandboxDisplayApis() {
    return this.mSandboxDisplayApis;
  }

  public final ContentRecorder getContentRecorder() {
    if (this.mContentRecorder == null) {
      this.mContentRecorder = new ContentRecorder(this);
    }
    return this.mContentRecorder;
  }

  public void pauseRecording() {
    ContentRecorder contentRecorder = this.mContentRecorder;
    if (contentRecorder != null) {
      contentRecorder.pauseRecording();
    }
  }

  public void setContentRecordingSession(ContentRecordingSession contentRecordingSession) {
    getContentRecorder().setContentRecordingSession(contentRecordingSession);
  }

  public boolean setDisplayMirroring() {
    int displayIdToMirror =
        this.mWmService.mDisplayManagerInternal.getDisplayIdToMirror(this.mDisplayId);
    if (displayIdToMirror == -1) {
      return false;
    }
    int i = this.mDisplayId;
    if (displayIdToMirror == i) {
      if (i != 0 && ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
            1145016093,
            1,
            "Content Recording: Attempting to mirror self on %d",
            new Object[] {Long.valueOf(displayIdToMirror)});
      }
      return false;
    }
    DisplayContent displayContentOrCreate =
        this.mRootWindowContainer.getDisplayContentOrCreate(displayIdToMirror);
    if (displayContentOrCreate == null && this.mDisplayId == 0) {
      if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
            -2072029833,
            1,
            "Content Recording: Found no matching mirror display for id=%d for DEFAULT_DISPLAY."
                + " Nothing to mirror.",
            new Object[] {Long.valueOf(displayIdToMirror)});
      }
      return false;
    }
    if (displayContentOrCreate == null) {
      displayContentOrCreate = this.mRootWindowContainer.getDefaultDisplay();
      if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
        ProtoLogImpl.w(
            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
            801521566,
            5,
            "Content Recording: Attempting to mirror %d from %d but no DisplayContent associated."
                + " Changing to mirror default display.",
            new Object[] {Long.valueOf(displayIdToMirror), Long.valueOf(this.mDisplayId)});
      }
    }
    setContentRecordingSession(
        ContentRecordingSession.createDisplaySession(displayContentOrCreate.getDisplayId())
            .setVirtualDisplayId(this.mDisplayId));
    if (ProtoLogCache.WM_DEBUG_CONTENT_RECORDING_enabled) {
      ProtoLogImpl.v(
          ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
          -1885450608,
          5,
          "Content Recording: Successfully created a ContentRecordingSession for displayId=%d to"
              + " mirror content from displayId=%d",
          new Object[] {Long.valueOf(this.mDisplayId), Long.valueOf(displayIdToMirror)});
    }
    return true;
  }

  public void updateRecording() {
    ContentRecorder contentRecorder = this.mContentRecorder;
    if ((contentRecorder == null || !contentRecorder.isContentRecordingSessionSet())
        && !setDisplayMirroring()) {
      return;
    }
    this.mContentRecorder.updateRecording();
  }

  public void updateMirroredSurfaceFromDisplayManager() {
    ContentRecorder contentRecorder = this.mContentRecorder;
    if (contentRecorder != null) {
      contentRecorder.updateMirroredSurfaceFromDisplayManager();
    }
  }

  public class FixedRotationTransitionListener extends WindowManagerInternal.AppTransitionListener {
    public ActivityRecord mAnimatingRecents;
    public boolean mRecentsWillBeTop;

    public FixedRotationTransitionListener() {}

    public void onStartRecentsAnimation(ActivityRecord activityRecord) {
      ActivityRecord activityRecord2;
      Slog.d(StartingSurfaceController.TAG, "onStartRecentsAnimation, r=" + activityRecord);
      this.mAnimatingRecents = activityRecord;
      if (!activityRecord.isVisible()
          || (activityRecord2 = DisplayContent.this.mFocusedApp) == null
          || activityRecord2.occludesParent()) {
        DisplayContent.this.rotateInDifferentOrientationIfNeeded(activityRecord);
        if (activityRecord.hasFixedRotationTransform()) {
          DisplayContent.this.setFixedRotationLaunchingApp(
              activityRecord, activityRecord.getWindowConfiguration().getRotation());
        }
      }
    }

    public void onFinishRecentsAnimation() {
      ActivityRecord activityRecord = this.mAnimatingRecents;
      boolean z = this.mRecentsWillBeTop;
      this.mAnimatingRecents = null;
      this.mRecentsWillBeTop = false;
      Slog.d(
          StartingSurfaceController.TAG,
          "onFinishRecentsAnimation, animatingRecents=" + activityRecord);
      if (z) {
        return;
      }
      if (activityRecord != null
          && activityRecord == DisplayContent.this.mFixedRotationLaunchingApp
          && activityRecord.isVisible()
          && activityRecord != DisplayContent.this.topRunningActivity()) {
        DisplayContent.this.setFixedRotationLaunchingAppUnchecked(null);
      } else {
        DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
      }
    }

    public void notifyRecentsWillBeTop() {
      this.mRecentsWillBeTop = true;
    }

    public boolean shouldDeferRotation() {
      ActivityRecord activityRecord = null;
      if (DisplayContent.this.mTransitionController.isShellTransitionsEnabled()) {
        ActivityRecord activityRecord2 = DisplayContent.this.mFixedRotationLaunchingApp;
        if (activityRecord2 != null
            && DisplayContent.this.mTransitionController.isTransientLaunch(activityRecord2)) {
          activityRecord = activityRecord2;
        }
      } else if (this.mAnimatingRecents != null
          && !DisplayContent.this.hasTopFixedRotationLaunchingApp()) {
        activityRecord = this.mAnimatingRecents;
      }
      if (activityRecord == null
          || activityRecord.getRequestedConfigurationOrientation(true) == 0) {
        return false;
      }
      return DisplayContent.this.mWmService.mPolicy.okToAnimate(false);
    }

    @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
    public void onAppTransitionFinishedLocked(IBinder iBinder) {
      ActivityRecord activityRecord;
      final ActivityRecord activityRecord2 = DisplayContent.this.getActivityRecord(iBinder);
      if (activityRecord2 == null || activityRecord2 == (activityRecord = this.mAnimatingRecents)) {
        return;
      }
      if (activityRecord == null || !this.mRecentsWillBeTop) {
        if (DisplayContent.this.mFixedRotationLaunchingApp == null) {
          activityRecord2.finishFixedRotationTransform();
          return;
        }
        if (DisplayContent.this.mFixedRotationLaunchingApp.hasFixedRotationTransform(
            activityRecord2)) {
          if (DisplayContent.this.mFixedRotationLaunchingApp
              .hasAnimatingFixedRotationTransition()) {
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
              DisplayContent.this.mTransitionController.mStateValidators.add(
                  new Runnable() { // from class:
                                   // com.android.server.wm.DisplayContent$FixedRotationTransitionListener$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                      DisplayContent.FixedRotationTransitionListener.this
                          .lambda$onAppTransitionFinishedLocked$0(activityRecord2);
                    }
                  });
              return;
            }
            return;
          }
        } else {
          Task task = activityRecord2.getTask();
          if (task == null
              || task != DisplayContent.this.mFixedRotationLaunchingApp.getTask()
              || task.getActivity(
                      new Predicate() { // from class:
                                        // com.android.server.wm.DisplayContent$FixedRotationTransitionListener$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          return ((ActivityRecord) obj).isInTransition();
                        }
                      })
                  != null) {
            return;
          }
        }
        DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAppTransitionFinishedLocked$0(
        ActivityRecord activityRecord) {
      if (DisplayContent.this.isFixedRotationLaunchingApp(activityRecord)) {
        Slog.e(
            StartingSurfaceController.TAG,
            "Need to clear fixed rotation transform, r=" + activityRecord);
        DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
      }
    }

    @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
    public void onAppTransitionCancelledLocked(boolean z) {
      if (DisplayContent.this.mTransitionController.isShellTransitionsEnabled()) {
        return;
      }
      DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
    }

    @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
    public void onAppTransitionTimeoutLocked() {
      DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
    }
  }

  public class RemoteInsetsControlTarget implements InsetsControlTarget {
    public final boolean mCanShowTransient;
    public final IDisplayWindowInsetsController mRemoteInsetsController;
    public int mRequestedVisibleTypes = WindowInsets.Type.defaultVisible();

    public RemoteInsetsControlTarget(
        IDisplayWindowInsetsController iDisplayWindowInsetsController) {
      this.mRemoteInsetsController = iDisplayWindowInsetsController;
      this.mCanShowTransient =
          DisplayContent.this
              .mWmService
              .mContext
              .getResources()
              .getBoolean(R.bool.config_navBarTapThrough);
    }

    public void topFocusedWindowChanged(ComponentName componentName, int i) {
      try {
        this.mRemoteInsetsController.topFocusedWindowChanged(componentName, i);
      } catch (RemoteException e) {
        Slog.w(
            StartingSurfaceController.TAG,
            "Failed to deliver package in top focused window change",
            e);
      }
    }

    public void notifyInsetsChanged() {
      try {
        this.mRemoteInsetsController.insetsChanged(
            DisplayContent.this.getInsetsStateController().getRawInsetsState());
      } catch (RemoteException e) {
        Slog.w(StartingSurfaceController.TAG, "Failed to deliver inset state change", e);
      }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public void notifyInsetsControlChanged() {
      InsetsStateController insetsStateController = DisplayContent.this.getInsetsStateController();
      try {
        this.mRemoteInsetsController.insetsControlChanged(
            insetsStateController.getRawInsetsState(),
            insetsStateController.getControlsForDispatch(this));
      } catch (RemoteException e) {
        Slog.w(StartingSurfaceController.TAG, "Failed to deliver inset control state change", e);
      }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public void showInsets(int i, boolean z, ImeTracker.Token token) {
      try {
        ImeTracker.forLogging().onProgress(token, 23);
        this.mRemoteInsetsController.showInsets(i, z, token);
      } catch (RemoteException e) {
        Slog.w(StartingSurfaceController.TAG, "Failed to deliver showInsets", e);
        ImeTracker.forLogging().onFailed(token, 23);
      }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public void hideInsets(int i, boolean z, ImeTracker.Token token) {
      try {
        ImeTracker.forLogging().onProgress(token, 24);
        this.mRemoteInsetsController.hideInsets(i, z, token);
      } catch (RemoteException e) {
        Slog.w(StartingSurfaceController.TAG, "Failed to deliver hideInsets", e);
        ImeTracker.forLogging().onFailed(token, 24);
      }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public boolean canShowTransient() {
      return this.mCanShowTransient;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public boolean isRequestedVisible(int i) {
      return ((WindowInsets.Type.ime() & i) != 0
              && DisplayContent.this
                  .getInsetsStateController()
                  .getImeSourceProvider()
                  .isImeShowing())
          || (this.mRequestedVisibleTypes & i) != 0;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public int getRequestedVisibleTypes() {
      return this.mRequestedVisibleTypes;
    }

    public void setRequestedVisibleTypes(int i) {
      if (this.mRequestedVisibleTypes != i) {
        this.mRequestedVisibleTypes = i;
      }
    }

    public void clearImeRequestedVisibleTypes(String str) {
      if ((this.mRequestedVisibleTypes & WindowInsets.Type.ime()) == 0) {
        return;
      }
      int i = this.mRequestedVisibleTypes & (~WindowInsets.Type.ime());
      Slog.d(
          StartingSurfaceController.TAG,
          "clearImeRequestedVisibleTypes: vis=0x" + Integer.toHexString(i) + ", reason=" + str);
      setRequestedVisibleTypes(i);
      DisplayContent.this.getInsetsStateController().onInsetsModified(this);
    }
  }

  public MagnificationSpec getMagnificationSpec() {
    return this.mMagnificationSpec;
  }

  public DisplayArea findAreaForWindowType(int i, Bundle bundle, boolean z, boolean z2) {
    if (i >= 1 && i <= 99) {
      return this.mDisplayAreaPolicy.getTaskDisplayArea(bundle);
    }
    if (i == 2011 || i == 2012) {
      return getImeContainer();
    }
    return this.mDisplayAreaPolicy.findAreaForWindowType(i, bundle, z, z2);
  }

  public DisplayArea findAreaForToken(WindowToken windowToken) {
    if (CoreRune.FW_CUSTOM_LETTERBOX_ENHANCED_AS_CAPTURED_BLUR
        && CustomLetterboxConfiguration.isEnhancedControllerToken(windowToken.token)) {
      return findAreaForWindowType(
          2013,
          windowToken.mOptions,
          windowToken.mOwnerCanManageAppTokens,
          windowToken.mRoundedCornerOverlay);
    }
    return findAreaForWindowType(
        windowToken.getWindowType(),
        windowToken.mOptions,
        windowToken.mOwnerCanManageAppTokens,
        windowToken.mRoundedCornerOverlay);
  }

  public List getVisibleWindowInfoList() {
    final ArrayList arrayList = new ArrayList();
    forAllWindows(
        new Consumer() { // from class:
                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda27
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            DisplayContent.this.lambda$getVisibleWindowInfoList$57(arrayList, (WindowState) obj);
          }
        },
        true);
    return arrayList;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$getVisibleWindowInfoList$57(
      ArrayList arrayList, WindowState windowState) {
    if (windowState.isVisible()) {
      SemWindowManager.VisibleWindowInfo visibleWindowInfo =
          new SemWindowManager.VisibleWindowInfo();
      visibleWindowInfo.packageName = windowState.getOwningPackage();
      visibleWindowInfo.name = windowState.mAttrs.getTitle().toString();
      visibleWindowInfo.type = windowState.mAttrs.type;
      visibleWindowInfo.focused = windowState.equals(this.mCurrentFocus);
      visibleWindowInfo.lastFocused = windowState.equals(this.mOldFocus);
      arrayList.add(visibleWindowInfo);
    }
  }

  public static /* synthetic */ boolean lambda$getSecureWindowOnScreen$58(WindowState windowState) {
    return windowState.isOnScreen() && windowState.isSecureLocked();
  }

  public WindowState getSecureWindowOnScreen() {
    return getWindow(
        new Predicate() { // from class:
                          // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda69
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$getSecureWindowOnScreen$58;
            lambda$getSecureWindowOnScreen$58 =
                DisplayContent.lambda$getSecureWindowOnScreen$58((WindowState) obj);
            return lambda$getSecureWindowOnScreen$58;
          }
        });
  }

  public boolean isAppCastingDisplay() {
    return (this.mDisplay.getFlags() & 16384) != 0;
  }

  public boolean isCarLifeDisplay() {
    return (this.mDisplay.getFlags() & 1048576) != 0;
  }

  public boolean isMultiTaskingDisplay() {
    return isRemoteAppDisplay() || this.mDisplayId == 2 || isAppCastingDisplay();
  }

  public final boolean shouldUseRemoteInsetsControlTarget() {
    if (this.mInputMethodWindow == null
        || this.mRemoteInsetsControlTarget == null
        || this.mImeInputTarget != null
        || isImeControlledByApp()) {
      return false;
    }
    if (this.mAtmService.mDexController.getDexModeLocked() == 2) {
      if (this.mDisplayId == 2) {
        return true;
      }
      if (this.isDefaultDisplay
          && this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked()) {
        return true;
      }
    }
    return this.isDefaultDisplay
        && getParent().getTopChild().asDisplayContent().isAppCastingDisplay();
  }

  public boolean isInputMethodTargetTaskAndShowing(Task task) {
    InputTarget inputTarget;
    WindowState windowState = this.mInputMethodWindow;
    return windowState != null
        && windowState.isVisible()
        && (inputTarget = this.mImeInputTarget) != null
        && inputTarget.getWindowState() != null
        && this.mImeInputTarget.getWindowState().getTask() == task
        && this.mInsetsStateController.getImeSourceProvider().isImeShowing();
  }

  public WindowState getWindowEavesdropDragEvent() {
    return getWindow(
        new Predicate() { // from class:
                          // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda49
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            return ((WindowState) obj).isEavesdropDragEvent();
          }
        });
  }

  public final boolean shouldApplyInputDeviceToDisplay(int i) {
    if (isAppCastingDisplay()) {
      return this.mWmService.mInputManager.canDispatchToDisplay(i, 0);
    }
    int i2 = this.mDisplayId;
    if ((i2 == 0 || i2 == 2) && this.mAtmService.mDexController.getDexModeLocked() == 2) {
      return true;
    }
    if (isRemoteAppDisplay()) {
      return this.mWmService.mInputManager.canDispatchToDisplay(i, 0);
    }
    return false;
  }

  public boolean isRemoteAppDisplay() {
    return (this.mDisplay.getFlags() & 33554432) != 0;
  }

  public final ComponentName convertActivityResultToComponentName(ActivityRecord activityRecord) {
    String activityRecord2;
    String str;
    if (activityRecord == null
        || (activityRecord2 = activityRecord.toString()) == null
        || activityRecord2.length() <= 26) {
      return null;
    }
    String[] split = activityRecord2.split(" ");
    try {
      int length = split.length - 1;
      while (true) {
        if (length < 0) {
          break;
        }
        if (split[length].indexOf(47) != -1) {
          split = split[length].split("/");
          break;
        }
        length--;
      }
      if (split.length != 2) {
        return null;
      }
      int lastIndexOf = split[1].lastIndexOf("}");
      if (lastIndexOf != -1) {
        split[1] = split[1].substring(0, lastIndexOf);
      }
      String str2 = split[0];
      if (split[1].indexOf(".") == 0) {
        str = str2 + split[1];
      } else {
        str = split[1];
      }
      return new ComponentName(str2, str);
    } catch (NullPointerException e) {
      Slog.e(StartingSurfaceController.TAG, "package and class name's parsing error. " + e);
      return null;
    }
  }

  public void setForcedSizeDensity(int i, int i2, int i3, boolean z, boolean z2, int i4) {
    this.mIsDensityForced = i3 != this.mInitialDisplayDensity;
    this.mBaseDisplayDensity = i3;
    Slog.i(
        StartingSurfaceController.TAG,
        "Using new display size & density : "
            + i
            + "x"
            + i2
            + " "
            + i3
            + "dp saveSize="
            + z
            + " saveDensity="
            + z2
            + " forcedHideCutout="
            + i4);
    try {
      this.mWmService.mAtmService.deferWindowLayout();
      if (CoreRune.FW_BLACK_SNAPSHOT_TRANSITION) {
        requestBlackSnapshotIfNeeded(i, i2);
      }
      this.mForcedHideCutout = i4;
      setForcedSize(
          i,
          i2,
          DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
          DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
          z,
          true,
          true);
      if (z2) {
        this.mWmService.mDisplayWindowSettings.setForcedDensity(getDisplayInfo(), i3, 0);
      }
    } finally {
      if (CoreRune.FW_BLACK_SNAPSHOT_TRANSITION) {
        clearBlackSnapshot();
      }
      this.mWmService.mAtmService.continueWindowLayout();
    }
  }

  public void updateBaseDisplayCutout(int i, int i2) {
    UdcCutoutPolicy udcCutoutPolicy = this.mUdcCutoutPolicy;
    if (udcCutoutPolicy != null) {
      udcCutoutPolicy.updateUdcCutout(this.mNonOverrideDisplayInfo, i, i2);
    }
    if (this.mNonOverrideDisplayInfo.displayCutout == null) {
      this.mBaseDisplayCutout = null;
      return;
    }
    ContextImpl systemUiContext = ActivityThread.currentActivityThread().getSystemUiContext();
    this.mBaseDisplayCutout =
        DisplayCutout.fromResourcesRectApproximation(
            systemUiContext.getResources(),
            this.mNonOverrideDisplayInfo.uniqueId,
            i,
            i2,
            i,
            i2,
            getProportionalDensity(
                this.mNonOverrideDisplayInfo.getNaturalWidth(),
                i,
                this.mNonOverrideDisplayInfo.logicalDensityDpi),
            false);
  }

  public static int getProportionalDensity(int i, int i2, int i3) {
    if (i <= 0) {
      return DisplayMetrics.DENSITY_DEVICE_STABLE;
    }
    return (i2 * i3) / i;
  }

  public final void updateIsOverlappingWithCutoutAsDefault() {
    DisplayCutout displayCutout = this.mBaseDisplayCutout;
    boolean z = false;
    if (displayCutout == null || this.mDisplayPolicy == null) {
      this.mIsOverlappingWithCutoutAsDefault = false;
      return;
    }
    int max =
        Math.max(
            Math.max(displayCutout.getSafeInsetLeft(), displayCutout.getSafeInsetRight()),
            Math.max(displayCutout.getSafeInsetTop(), displayCutout.getSafeInsetBottom()));
    int dimensionPixelSize =
        this.mDisplayPolicy
            .getCurrentUserResources()
            .getDimensionPixelSize(R.dimen.timepicker_center_dot_radius);
    if (max > 0 && max <= dimensionPixelSize) {
      z = true;
    }
    this.mIsOverlappingWithCutoutAsDefault = z;
    if (CoreRune.MD_DEX_NOT_SUPPORT_CUTOUT && isDexMode()) {
      this.mIsOverlappingWithCutoutAsDefault = true;
    }
    Slog.i(
        StartingSurfaceController.TAG,
        "updateIsOverlappingWithCutoutAsDefault: largeCutoutSize="
            + max
            + ", minimumSizeForOverlappingWithCutoutAsDefault="
            + dimensionPixelSize
            + ", isDexMode="
            + isDexMode());
  }

  public final int getOrientationForDexStandalone() {
    WindowManagerService windowManagerService = this.mWmService;
    if ((windowManagerService.mDisplayFrozen && windowManagerService.mPolicy.isKeyguardLocked())
        || !this.mAtmService.mDexController.isDexStandaloneRotationEnabledLocked()) {
      return 11;
    }
    super.getOrientation();
    WindowContainer lastOrientationSource = getLastOrientationSource();
    ActivityRecord asActivityRecord =
        lastOrientationSource != null ? lastOrientationSource.asActivityRecord() : null;
    return (asActivityRecord == null
            || !asActivityRecord.isActivityTypeStandardOrUndefined()
            || asActivityRecord.inMultiWindowMode())
        ? 11
        : 2;
  }

  public TransitionRequestInfo.DisplayChange createDisplayChangeIfNeeded(
      Configuration configuration, Configuration configuration2) {
    if (!CoreRune.FW_UI_MODE_ANIMATION
        || configuration.isNightModeActive() == configuration2.isNightModeActive()) {
      return null;
    }
    TransitionRequestInfo.DisplayChange displayChange =
        new TransitionRequestInfo.DisplayChange(
            this.mDisplayId,
            this.mDisplayRotation.getRotation(),
            this.mDisplayRotation.getRotation());
    displayChange.setUiModeChanged(true);
    return displayChange;
  }

  public DisplayAnimationPair selectDisplayChangeAnimation(
      TransitionRequestInfo.DisplayChange displayChange) {
    DisplayAnimationPair displayAnimationPair = new DisplayAnimationPair();
    if (CoreRune.FW_UI_MODE_ANIMATION && displayChange.isUiModeChanged()) {
      displayAnimationPair.mEnter = R.anim.wallpaper_intra_close_enter;
      displayAnimationPair.mExit = R.anim.wallpaper_intra_close_exit;
    }
    return displayAnimationPair;
  }

  public void requestBlackSnapshotIfNeeded(int i, int i2) {
    boolean z = false;
    if (i > 0
        && i2 > 0
        && Math.abs((i / i2) - (this.mBaseDisplayWidth / this.mBaseDisplayHeight)) > 0.001f) {
      z = true;
    }
    this.mHasBlackSnapshot = z;
  }

  public void clearBlackSnapshot() {
    this.mHasBlackSnapshot = false;
  }

  public final boolean handleFixedRotationForMultiWindow(ActivityRecord activityRecord) {
    if (!hasVisibleFreeformTask()) {
      return false;
    }
    if (this.mIsInExitingRecents && hasFreeformForceHideTransientOverlay()) {
      Slog.d(
          StartingSurfaceController.TAG,
          "handleFixedRotationForMultiWindow: allow fixed-rotation, reason=recents_overlay");
      return false;
    }
    setFadeInOutAnimationNeeded(true, "handle_fixed_rot");
    TaskSnapshotController taskSnapshotController = this.mWmService.mTaskSnapshotController;
    Task taskBehindHome = getTaskBehindHome(activityRecord);
    if (taskBehindHome != null
        && taskSnapshotController.isInSkipClosingAppSnapshotTasks(taskBehindHome)) {
      ArraySet arraySet = new ArraySet();
      arraySet.add(taskBehindHome);
      taskSnapshotController.snapshotTasks(arraySet);
      taskSnapshotController.addSkipClosingAppSnapshotTasks(arraySet);
    }
    return true;
  }

  public void onExitingRecentsChanged(boolean z) {
    if (this.mIsInExitingRecents != z) {
      this.mIsInExitingRecents = z;
    }
  }

  public final boolean hasFreeformForceHideTransientOverlay() {
    if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY) {
      return false;
    }
    Iterator it = this.mTransientLaunchOverlayTokens.iterator();
    while (it.hasNext()) {
      if (((TransientLaunchOverlayToken) it.next())
              .getWindow(
                  new Predicate() { // from class:
                                    // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda67
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                      boolean lambda$hasFreeformForceHideTransientOverlay$59;
                      lambda$hasFreeformForceHideTransientOverlay$59 =
                          DisplayContent.lambda$hasFreeformForceHideTransientOverlay$59(
                              (WindowState) obj);
                      return lambda$hasFreeformForceHideTransientOverlay$59;
                    }
                  })
          != null) {
        return true;
      }
    }
    return false;
  }

  public static /* synthetic */ boolean lambda$hasFreeformForceHideTransientOverlay$59(
      WindowState windowState) {
    return windowState.isVisible() && (windowState.mAttrs.samsungFlags & 67108864) != 0;
  }

  public static /* synthetic */ boolean lambda$hasVisibleFreeformTask$60(Task task) {
    return task.inFreeformWindowingMode() && task.shouldBeVisible(null);
  }

  public boolean hasVisibleFreeformTask() {
    return getRootTask(
            new Predicate() { // from class:
                              // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda38
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$hasVisibleFreeformTask$60;
                lambda$hasVisibleFreeformTask$60 =
                    DisplayContent.lambda$hasVisibleFreeformTask$60((Task) obj);
                return lambda$hasVisibleFreeformTask$60;
              }
            })
        != null;
  }

  public final Task getTaskBehindHome(ActivityRecord activityRecord) {
    Task task = activityRecord.getTask();
    if (task == null || task.mCreatedByOrganizer || !task.isActivityTypeHomeOrRecents()) {
      return null;
    }
    return getDefaultTaskDisplayArea().getTaskBelow(task);
  }

  public void setFadeInOutAnimationNeeded(boolean z, String str) {
    if (this.mFadeInOutAnimationNeeded != z) {
      this.mFadeInOutAnimationNeeded = z;
      Slog.d(
          StartingSurfaceController.TAG, "setFadeInOutAnimationNeeded: " + z + ", reason=" + str);
    }
  }

  public boolean isFadeInOutAnimationNeeded() {
    return this.mFadeInOutAnimationNeeded;
  }

  public final class TaskIdFromPointSearchResult {
    public Task mTask;

    public void reset() {
      this.mTask = null;
    }
  }

  public boolean isValidCornerGesture(MotionEvent motionEvent) {
    return this.mMultiWindowPointerEventListener.isValidCornerGesture(motionEvent);
  }

  public void getBaseDisplayRect(Rect rect) {
    DisplayInfo displayInfo = this.mDisplayInfo;
    rect.set(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
  }

  public void getStableRect(Rect rect, boolean z) {
    InsetsState rawInsetsState =
        this.mDisplayContent.getInsetsStateController().getRawInsetsState();
    rect.set(rawInsetsState.getDisplayFrame());
    rect.inset(rawInsetsState.calculateInsets(rect, WindowInsets.Type.systemBars(), z));
  }

  public final boolean shouldBeRelativeLayer(WindowState windowState) {
    if (windowState != null && windowState.getSurfaceControl() != null && this.isDefaultDisplay) {
      ActivityRecord activityRecord = windowState.mActivityRecord;
      if (activityRecord == null || !activityRecord.hasStartingWindow()) {
        InsetsControlTarget insetsControlTarget = this.mImeControlTarget;
        if ((windowState.getSurfaceControl() == null
                || windowState.mToken
                    != ((insetsControlTarget == null || insetsControlTarget.getWindow() == null)
                        ? null
                        : this.mImeControlTarget.getWindow().mToken)
                || windowState.inMultiWindowMode())
            ? false
            : true) {
          return true;
        }
      }
      DisplayContent topFocusedDisplayContent =
          this.mRootWindowContainer.getTopFocusedDisplayContent();
      if (topFocusedDisplayContent != null
          && topFocusedDisplayContent.isAppCastingDisplay()
          && topFocusedDisplayContent.mImeInputTarget != null) {
        return true;
      }
    }
    return false;
  }

  public void updateAboveInsetsState() {
    WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
    WindowManagerService.boostPriorityForLockedSection();
    synchronized (windowManagerGlobalLock) {
      try {
        this.mInsetsStateController.updateAboveInsetsState(false);
        Slog.d(StartingSurfaceController.TAG, "updateAboveInsetsState");
      } catch (Throwable th) {
        WindowManagerService.resetPriorityAfterLockedSection();
        throw th;
      }
    }
    WindowManagerService.resetPriorityAfterLockedSection();
  }

  public boolean isInSidesGestureArea(int i, int i2) {
    return this.mSystemGestureFrameLeft.contains(i, i2)
        || this.mSystemGestureFrameRight.contains(i, i2);
  }

  public void addTransientLaunchOverlayToken(
      TransientLaunchOverlayToken transientLaunchOverlayToken) {
    this.mTransientLaunchOverlayTokens.add(transientLaunchOverlayToken);
  }

  public void removeTransientLaunchOverlayToken(
      TransientLaunchOverlayToken transientLaunchOverlayToken) {
    this.mTransientLaunchOverlayTokens.remove(transientLaunchOverlayToken);
  }
}
