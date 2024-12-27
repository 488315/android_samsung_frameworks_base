package com.android.server.wm;

import android.R;
import android.app.ActivityThread;
import android.app.ContextImpl;
import android.app.StatusBarManager;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BLASTBufferQueue;
import android.graphics.ColorSpace;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.hardware.HardwareBuffer;
import android.hardware.SensorManager;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.camera2.CameraManager;
import android.hardware.display.VirtualDisplay;
import android.metrics.LogMaker;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
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
import android.view.IAppTransitionAnimationSpecsFuture;
import android.view.IDisplayWindowInsetsController;
import android.view.IPinnedTaskListener;
import android.view.IWindow;
import android.view.InsetsFlags;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.MagnificationSpec;
import android.view.PrivacyIndicatorBounds;
import android.view.RoundedCorners;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.ViewDebug;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerPolicyConstants;
import android.view.inputmethod.Flags;
import android.view.inputmethod.ImeTracker;
import android.window.IDisplayAreaOrganizer;
import android.window.ScreenCapture;
import android.window.SystemPerformanceHinter;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowTokenClientController;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.ForceShowNavBarSettingsObserver;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.ToBooleanFunction;
import com.android.internal.util.function.TriFunction;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerConstants$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.BroadcastStats$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.media.MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.PhoneWindowManagerExt;
import com.android.server.wm.utils.RotationCache;
import com.android.server.wm.utils.WmDisplayCutout;

import com.samsung.android.knox.application.IApplicationPolicy;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayContent extends RootDisplayArea {
    public static final AnonymousClass1 COPY_SOURCE_VISIBILITY = new AnonymousClass1();
    public final boolean isDefaultDisplay;
    public SurfaceControl mA11yOverlayLayer;
    public final Set mActiveSizeCompatActivities;
    public final ArrayList mAllSleepTokens;
    public final AppCompatCameraPolicy mAppCompatCameraPolicy;
    public final AppTransition mAppTransition;
    public final AppTransitionController mAppTransitionController;
    public volatile IApplicationPolicy mApplicationPolicy;
    public final DisplayContent$$ExternalSyntheticLambda1 mApplyPostLayoutPolicy;
    public final DisplayContent$$ExternalSyntheticLambda1 mApplySurfaceChangesTransaction;
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
    public final DisplayContent$$ExternalSyntheticLambda5 mComputeImeTargetPredicate;
    public ContentRecorder mContentRecorder;
    public WindowState mCurrentFocus;
    public int mCurrentOverrideConfigurationChanges;
    public PrivacyIndicatorBounds mCurrentPrivacyIndicatorBounds;
    public String mCurrentUniqueDisplayId;
    public final RemoteCallbackList mDecorViewGestureListener;
    public int mDeferUpdateImeTargetCount;
    public boolean mDeferredRemoval;
    public final DisplayContent$$ExternalSyntheticLambda1 mDeviceStateConsumer;
    final DeviceStateController mDeviceStateController;
    public final Display mDisplay;
    public final IntArray mDisplayAccessUIDs;
    final DisplayAreaPolicy mDisplayAreaPolicy;
    public final RotationCache mDisplayCutoutCache;
    public final DisplayFrames mDisplayFrames;
    public final int mDisplayId;
    public final DisplayInfo mDisplayInfo;
    public final DisplayMetrics mDisplayMetrics;
    public final DisplayPolicy mDisplayPolicy;
    public boolean mDisplayReady;
    public final DisplayRotation mDisplayRotation;
    public boolean mDisplayScalingDisabled;
    public final RotationCache mDisplayShapeCache;
    public final PhysicalDisplaySwitchTransitionLauncher mDisplaySwitchTransitionLauncher;
    public final DisplayUpdater mDisplayUpdater;
    public boolean mDontMoveToTop;
    public final DisplayWindowPolicyControllerHelper mDwpcHelper;
    public boolean mFadeInOutAnimationNeeded;
    public final DisplayContent$$ExternalSyntheticLambda48 mFindFocusedWindow;
    public ActivityRecord mFixedRotationLaunchingApp;
    public final FixedRotationTransitionListener mFixedRotationTransitionListener;
    public ActivityRecord mFocusedApp;
    public boolean mForceMakeConfigChange;
    public int mForcedHideCutout;
    public SystemPerformanceHinter.HighPerfSession mHighFrameRateSession;
    public final PowerManager.WakeLock mHoldScreenWakeLock;
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
    public ActivityRecord mInputMethodSurfaceParentContainer;
    public WindowState mInputMethodWindow;
    public final InputMonitor mInputMonitor;
    public SurfaceControl mInputOverlayLayer;
    public final InsetsPolicy mInsetsPolicy;
    public final InsetsStateController mInsetsStateController;
    public boolean mIsDensityForced;
    public boolean mIsInExitingRecents;
    public boolean mIsOverlappingWithCutoutAsDefault;
    public boolean mIsSizeForced;
    public boolean mLastContainsRunningSurfaceAnimator;
    public DisplayInfo mLastDisplayInfoOverride;
    public boolean mLastHasContent;
    public InputTarget mLastImeInputTarget;
    public WindowState mLastWakeLockHoldingWindow;
    public WindowState mLastWakeLockObscuringWindow;
    public boolean mLastWallpaperVisible;
    public boolean mLayoutNeeded;
    public int mLayoutSeq;
    public MagnificationSpec mMagnificationSpec;
    public int mMaxUiWidth;
    public MetricsLogger mMetricsLogger;
    public int mMinSizeOfResizeableTaskDp;
    public MultiTaskingAppCompatConfiguration.BlackLetterboxConfig
            mMultiTaskingAppCompatConfiguration;
    public final MultiWindowPointerEventListener mMultiWindowPointerEventListener;
    public boolean mNeedImmediateDisplayUpdate;
    public final List mNoAnimationNotifyOnTransitionFinished;
    public final DisplayInfo mNonOverrideDisplayInfo;
    public WindowState mObscuringWindow;
    public final ActivityTaskManagerService.SleepTokenAcquirerImpl mOffTokenAcquirer;
    public WindowState mOldFocus;
    public final ArraySet mOpeningApps;
    public TaskDisplayArea mOrientationRequestingTaskDisplayArea;
    public SurfaceControl mOverlayLayer;
    public final DisplayContent$$ExternalSyntheticLambda1 mPerformLayout;
    public final DisplayContent$$ExternalSyntheticLambda1 mPerformLayoutAttached;
    public final Point mPhysicalDisplaySize;
    public final PinnedTaskController mPinnedTaskController;
    public final PointerEventDispatcher mPointerEventDispatcher;
    public final PopOverController mPopOverController;
    public final RotationCache mPrivacyIndicatorBoundsCache;
    public final DisplayMetrics mRealDisplayMetrics;
    public final RemoteDisplayChangeController mRemoteDisplayChangeController;
    public RemoteInsetsControlTarget mRemoteInsetsControlTarget;
    public final DisplayContent$$ExternalSyntheticLambda45 mRemoteInsetsDeath;
    public boolean mRemoved;
    public boolean mRemoving;
    public Set mRestrictedKeepClearAreas;
    public final RootWindowContainer mRootWindowContainer;
    public final DisplayRotationReversionController mRotationReversionController;
    public final RotationCache mRoundedCornerCache;
    public boolean mSandboxDisplayApis;
    public final DisplayContent$$ExternalSyntheticLambda1 mScheduleToastTimeout;
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
    public final Configuration mTempConfig;
    public final ApplySurfaceChangesTransactionState mTmpApplySurfaceChangesTransactionState;
    public final Configuration mTmpConfiguration;
    public final DisplayMetrics mTmpDisplayMetrics;
    public WindowState mTmpHoldScreenWindow;
    public boolean mTmpInitial;
    public final Rect mTmpRect;
    public final Region mTmpRegion;
    public final LinkedList mTmpUpdateAllDrawn;
    public WindowState mTmpWindow;
    public final HashMap mTokenMap;
    public final ArrayList mTransientLaunchOverlayTokens;
    public SystemPerformanceHinter.HighPerfSession mTransitionPrefSession;
    public final UdcCutoutPolicy mUdcCutoutPolicy;
    public final UnknownAppVisibilityController mUnknownAppVisibilityController;
    public Set mUnrestrictedKeepClearAreas;
    public boolean mUpdateImeRequestedWhileDeferred;
    public final DisplayContent$$ExternalSyntheticLambda1 mUpdateWindowsForAnimator;
    public final boolean mVisibleBackgroundUserEnabled;
    public boolean mWaitingForConfig;
    public final WallpaperController mWallpaperController;
    public boolean mWallpaperMayChange;
    public final ArrayList mWinAddedSinceNullFocus;
    public final ArrayList mWinRemovedSinceNullFocus;
    public final float mWindowCornerRadius;
    public int pendingLayoutChanges;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.DisplayContent$1, reason: invalid class name */
    public final class AnonymousClass1 implements InsetsState.OnTraverseCallbacks {
        public final void onIdMatch(InsetsSource insetsSource, InsetsSource insetsSource2) {
            insetsSource.setVisible(insetsSource2.isVisible());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FixedRotationTransitionListener
            extends WindowManagerInternal.AppTransitionListener {
        public ActivityRecord mAnimatingRecents;
        public boolean mRecentsWillBeTop;

        public FixedRotationTransitionListener() {}

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionCancelledLocked(boolean z) {
            DisplayContent displayContent = DisplayContent.this;
            if (displayContent.mTransitionController.isShellTransitionsEnabled()) {
                return;
            }
            displayContent.continueUpdateOrientationForDiffOrienLaunchingApp();
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionFinishedLocked(IBinder iBinder) {
            Task task;
            ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
            if (forTokenLocked == null || forTokenLocked == this.mAnimatingRecents) {
                return;
            }
            int displayId = forTokenLocked.getDisplayId();
            DisplayContent displayContent = DisplayContent.this;
            if (displayId != displayContent.mDisplayId) {
                return;
            }
            if (this.mAnimatingRecents == null || !this.mRecentsWillBeTop) {
                ActivityRecord activityRecord = displayContent.mFixedRotationLaunchingApp;
                if (activityRecord == null) {
                    if (!displayContent.mWmService.mFlags.mRespectNonTopVisibleFixedOrientation) {
                        forTokenLocked.finishFixedRotationTransform(null);
                        return;
                    } else {
                        if (forTokenLocked.mVisible) {
                            return;
                        }
                        forTokenLocked.finishFixedRotationTransform(null);
                        return;
                    }
                }
                WindowToken.FixedRotationTransformState fixedRotationTransformState =
                        activityRecord.mFixedRotationTransformState;
                if (fixedRotationTransformState == null
                        || !(activityRecord == forTokenLocked
                                || fixedRotationTransformState
                                        == forTokenLocked.mFixedRotationTransformState)) {
                    Task task2 = forTokenLocked.task;
                    if ((task2 != activityRecord.task
                                    && (!displayContent
                                                    .mWmService
                                                    .mFlags
                                                    .mRespectNonTopVisibleFixedOrientation
                                            || forTokenLocked.occludesParent(true)))
                            || task2.getActivity(new DisplayContent$$ExternalSyntheticLambda7(7))
                                    != null) {
                        return;
                    }
                    if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX
                            && !displayContent
                                    .mWmService
                                    .mFlags
                                    .mRespectNonTopVisibleFixedOrientation
                            && task2.getActivity(
                                            new DisplayContent$$ExternalSyntheticLambda5(2, this))
                                    != null) {
                        return;
                    }
                } else {
                    loop0:
                    for (int size = fixedRotationTransformState.mAssociatedTokens.size() - 1;
                            size >= 0;
                            size--) {
                        ActivityRecord asActivityRecord =
                                ((WindowToken)
                                                activityRecord.mFixedRotationTransformState
                                                        .mAssociatedTokens.get(size))
                                        .asActivityRecord();
                        if (CoreRune.MW_PIP_SHELL_TRANSITION
                                && asActivityRecord != null
                                && (task = asActivityRecord.task) != null
                                && task.inPinnedWindowingMode()) {
                            TransitionController transitionController =
                                    activityRecord.mTransitionController;
                            for (int size2 = transitionController.mPlayingTransitions.size() - 1;
                                    size2 >= 0;
                                    size2--) {
                                Transition transition =
                                        (Transition)
                                                transitionController.mPlayingTransitions.get(size2);
                                if (transition.mType == 10
                                        && transition.isInTransition(asActivityRecord)) {
                                    break loop0;
                                }
                            }
                        }
                        if (asActivityRecord != null
                                && asActivityRecord.inTransitionSelfOrParent()
                                && !asActivityRecord.mDisplayContent.inTransition()) {
                            if (!CoreRune.MW_PIP_SHELL_TRANSITION
                                    || activityRecord.mTransitionController.inCollectingTransition(
                                            asActivityRecord)) {
                                return;
                            }
                            TransitionController transitionController2 =
                                    activityRecord.mTransitionController;
                            for (int size3 = transitionController2.mPlayingTransitions.size() - 1;
                                    size3 >= 0;
                                    size3--) {
                                Transition transition2 =
                                        (Transition)
                                                transitionController2.mPlayingTransitions.get(
                                                        size3);
                                TransitionInfo transitionInfo = transition2.mLogger.mInfo;
                                if (transitionInfo != null
                                        && transitionInfo.getChanges().isEmpty()
                                        && transition2.mType >= 1000) {
                                    Slog.d(
                                            "TransitionController",
                                            "continue inPlayingTransition checkCustomTransition"
                                                + " playing="
                                                    + transition2);
                                } else if (transition2.isInTransition(asActivityRecord)) {
                                    return;
                                }
                            }
                            Slog.d(
                                    "WindowManager",
                                    "continue customTransition isInTransition r="
                                            + asActivityRecord);
                        }
                    }
                }
                displayContent.continueUpdateOrientationForDiffOrienLaunchingApp();
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public final void onAppTransitionTimeoutLocked() {
            DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ImeContainer extends DisplayArea.Tokens {
        public boolean mNeedsLayer;

        @Override // com.android.server.wm.WindowContainer
        public final void assignLayer(SurfaceControl.Transaction transaction, int i) {
            if (this.mNeedsLayer) {
                super.assignLayer(transaction, i);
                this.mNeedsLayer = false;
            }
        }

        @Override // com.android.server.wm.WindowContainer
        public final void assignRelativeLayer(
                SurfaceControl.Transaction transaction,
                SurfaceControl surfaceControl,
                int i,
                boolean z) {
            if (this.mNeedsLayer) {
                super.assignRelativeLayer(transaction, surfaceControl, i, z);
                this.mNeedsLayer = false;
            }
        }

        public final boolean forAllWindowForce(ToBooleanFunction toBooleanFunction, boolean z) {
            return super.forAllWindows(toBooleanFunction, z);
        }

        @Override // com.android.server.wm.WindowContainer
        public final boolean forAllWindows(ToBooleanFunction toBooleanFunction, boolean z) {
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent.mImeLayeringTarget == null
                    || displayContent.mWmService.mDisplayFrozen) {
                return super.forAllWindows(toBooleanFunction, z);
            }
            return false;
        }

        @Override // com.android.server.wm.DisplayArea.Tokens, com.android.server.wm.DisplayArea,
                  // com.android.server.wm.WindowContainer
        public final int getOrientation(int i) {
            if (shouldIgnoreOrientationRequest(i)) {
                return -2;
            }
            return i;
        }

        @Override // com.android.server.wm.DisplayArea
        public final void setOrganizer(IDisplayAreaOrganizer iDisplayAreaOrganizer, boolean z) {
            super.setOrganizer(iDisplayAreaOrganizer, z);
            this.mDisplayContent.updateImeParent();
            if (iDisplayAreaOrganizer != null) {
                SurfaceControl parentSurfaceControl = getParentSurfaceControl();
                SurfaceControl surfaceControl = this.mSurfaceControl;
                boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled;
                if (surfaceControl != null && parentSurfaceControl != null) {
                    if (zArr[2]) {
                        ProtoLogImpl_54989576.i(
                                ProtoLogGroup.WM_DEBUG_IME,
                                -1556099709547629010L,
                                0,
                                null,
                                String.valueOf(parentSurfaceControl));
                    }
                    getPendingTransaction().reparent(this.mSurfaceControl, parentSurfaceControl);
                    return;
                }
                if (zArr[4]) {
                    ProtoLogImpl_54989576.e(
                            ProtoLogGroup.WM_DEBUG_IME,
                            1119786654111970652L,
                            0,
                            null,
                            String.valueOf(surfaceControl),
                            String.valueOf(parentSurfaceControl));
                }
            }
        }

        @Override // com.android.server.wm.WindowContainer
        public final void updateAboveInsetsState(
                InsetsState insetsState, SparseArray sparseArray, ArraySet arraySet) {
            DisplayContent displayContent = this.mDisplayContent;
            if (displayContent.mImeLayeringTarget == null
                    || displayContent.mWmService.mDisplayFrozen) {
                super.updateAboveInsetsState(insetsState, sparseArray, arraySet);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ImeScreenshot {
        public SurfaceControl mImeSurface;
        public Point mImeSurfacePosition;
        public WindowState mImeTarget;

        public SurfaceControl getImeScreenshotSurface() {
            return this.mImeSurface;
        }

        public final void removeImeSurface(SurfaceControl.Transaction transaction) {
            if (this.mImeSurface != null) {
                if (ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_DEBUG_IME,
                            2005731931732324688L,
                            0,
                            null,
                            String.valueOf(Debug.getCallers(6)));
                }
                transaction.remove(this.mImeSurface);
                this.mImeSurface = null;
            }
            if (ImeTracker.DEBUG_IME_VISIBILITY) {
                EventLog.writeEvent(32005, this.mImeTarget.toString());
            }
        }

        public final String toString() {
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(64, "ImeScreenshot{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" imeTarget=" + this.mImeTarget);
            m.append(" surface=" + this.mImeSurface);
            m.append('}');
            return m.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteInsetsControlTarget implements InsetsControlTarget {
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
                            .getBoolean(R.bool.config_setColorTransformAccelerated);
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final boolean canShowTransient() {
            return this.mCanShowTransient;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final int getRequestedVisibleTypes() {
            return this.mRequestedVisibleTypes;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final void hideInsets(int i, boolean z, ImeTracker.Token token) {
            try {
                ImeTracker.forLogging().onProgress(token, 24);
                this.mRemoteInsetsController.hideInsets(i, true, token);
            } catch (RemoteException e) {
                Slog.w("WindowManager", "Failed to deliver hideInsets", e);
                ImeTracker.forLogging().onFailed(token, 24);
            }
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final boolean isRequestedVisible(int i) {
            return Flags.refactorInsetsController()
                    ? (this.mRequestedVisibleTypes & i) != 0
                    : ((WindowInsets.Type.ime() & i) != 0
                                    && DisplayContent.this.mInsetsStateController
                                            .getImeSourceProvider()
                                            .mImeShowing)
                            || (this.mRequestedVisibleTypes & i) != 0;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final void notifyInsetsControlChanged(int i) {
            InsetsStateController insetsStateController =
                    DisplayContent.this.mInsetsStateController;
            try {
                this.mRemoteInsetsController.insetsControlChanged(
                        insetsStateController.mState,
                        insetsStateController.getControlsForDispatch(this));
            } catch (RemoteException e) {
                Slog.w("WindowManager", "Failed to deliver inset control state change", e);
            }
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final void setImeInputTargetRequestedVisibility(boolean z) {
            if (Flags.refactorInsetsController()) {
                try {
                    this.mRemoteInsetsController.setImeInputTargetRequestedVisibility(z);
                } catch (RemoteException e) {
                    Slog.w(
                            "WindowManager",
                            "Failed to deliver setImeInputTargetRequestedVisibility",
                            e);
                }
            }
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public final void showInsets(int i, boolean z, ImeTracker.Token token) {
            try {
                ImeTracker.forLogging().onProgress(token, 23);
                this.mRemoteInsetsController.showInsets(i, z, token);
            } catch (RemoteException e) {
                Slog.w("WindowManager", "Failed to deliver showInsets", e);
                ImeTracker.forLogging().onFailed(token, 23);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskFromPointSearchResult {
        public Task mTask;
    }

    public static WmDisplayCutout $r8$lambda$OlRwH3_Eqb403xPL7MPG5vhH0aE(
            DisplayContent displayContent, DisplayCutout displayCutout, int i) {
        DisplayCutout displayCutout2;
        int i2 = displayContent.mBaseDisplayWidth;
        int i3 = displayContent.mBaseDisplayHeight;
        if (displayCutout == null || displayCutout == (displayCutout2 = DisplayCutout.NO_CUTOUT)) {
            return WmDisplayCutout.NO_CUTOUT;
        }
        if (i2 == i3) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i2, "Ignore cutout because display size is square: ", "WindowManager");
            return WmDisplayCutout.NO_CUTOUT;
        }
        if (i == 0) {
            WmDisplayCutout wmDisplayCutout = WmDisplayCutout.NO_CUTOUT;
            return displayCutout == displayCutout2
                    ? WmDisplayCutout.NO_CUTOUT
                    : new WmDisplayCutout(
                            displayCutout.replaceSafeInsets(
                                    DisplayCutout.computeSafeInsets(i2, i3, displayCutout)),
                            new Size(i2, i3));
        }
        DisplayCutout rotated = displayCutout.getRotated(i2, i3, 0, i);
        boolean z = i == 1 || i == 3;
        int i4 = z ? i3 : i2;
        if (!z) {
            i2 = i3;
        }
        return new WmDisplayCutout(rotated, new Size(i4, i2));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0747  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x06bf  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x06f0  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0705  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x071a  */
    /* JADX WARN: Type inference failed for: r10v4, types: [com.android.server.wm.DisplayContent$$ExternalSyntheticLambda45] */
    /* JADX WARN: Type inference failed for: r1v79 */
    /* JADX WARN: Type inference failed for: r1v80, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v94 */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.server.wm.DisplayContent$$ExternalSyntheticLambda48] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DisplayContent(
            android.view.Display r28,
            com.android.server.wm.RootWindowContainer r29,
            com.android.server.wm.DeviceStateController r30) {
        /*
            Method dump skipped, instructions count: 1958
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayContent.<init>(android.view.Display,"
                    + " com.android.server.wm.RootWindowContainer,"
                    + " com.android.server.wm.DeviceStateController):void");
    }

    public static int addToGlobalAndConsumeLimit(
            Region region, Region region2, Rect rect, int i, WindowState windowState, int i2) {
        Region obtain = Region.obtain(region);
        obtain.op(rect, Region.Op.INTERSECT);
        int[] iArr = {i};
        int[] iArr2 = {0};
        DisplayContent$$ExternalSyntheticLambda54 displayContent$$ExternalSyntheticLambda54 =
                new DisplayContent$$ExternalSyntheticLambda54(iArr, iArr2, region2);
        RegionIterator regionIterator = new RegionIterator(obtain);
        ArrayList arrayList = new ArrayList();
        Rect rect2 = new Rect();
        while (regionIterator.next(rect2)) {
            arrayList.add(new Rect(rect2));
        }
        Collections.reverse(arrayList);
        arrayList.forEach(displayContent$$ExternalSyntheticLambda54);
        int i3 = i - iArr[0];
        int i4 = iArr2[0];
        if (windowState.mLastGrantedExclusionHeight[i2] != i3
                || windowState.mLastRequestedExclusionHeight[i2] != i4) {
            if (windowState.mLastShownChangedReported) {
                windowState.logExclusionRestrictions(i2);
            }
            windowState.mLastGrantedExclusionHeight[i2] = i3;
            windowState.mLastRequestedExclusionHeight[i2] = i4;
        }
        obtain.recycle();
        return iArr[0];
    }

    public static boolean alwaysCreateRootTask(int i, int i2) {
        return (i2 == 1 || i2 == 3) && (i == 1 || i == 5 || i == 2 || i == 6);
    }

    public static boolean needsGestureExclusionRestrictions(WindowState windowState, boolean z) {
        if (windowState.getTask() != null && windowState.getTask().isFreeformStashed()) {
            return false;
        }
        WindowManager.LayoutParams layoutParams = windowState.mAttrs;
        int i = layoutParams.type;
        return ((!windowState.isRequestedVisible(WindowInsets.Type.navigationBars(), false)
                                && windowState.mAttrs.insetsFlags.behavior == 2
                                && !z)
                        || i == 2011
                        || i == 2040
                        || windowState.getActivityType() == 2
                        || (layoutParams.privateFlags & 32) != 0)
                ? false
                : true;
    }

    public final SurfaceControl addShellRoot(IWindow iWindow, int i) {
        ShellRoot shellRoot = (ShellRoot) this.mShellRoots.get(i);
        if (shellRoot != null) {
            if (shellRoot.mClient == iWindow) {
                return shellRoot.mSurfaceControl;
            }
            shellRoot.clear();
            this.mShellRoots.remove(i);
        }
        ShellRoot shellRoot2 = new ShellRoot(iWindow, this, i);
        SurfaceControl surfaceControl = shellRoot2.mSurfaceControl;
        if (surfaceControl == null) {
            shellRoot2.clear();
            return null;
        }
        this.mShellRoots.put(i, shellRoot2);
        return new SurfaceControl(surfaceControl, "DisplayContent.addShellRoot");
    }

    public final void addWindowToken(IBinder iBinder, WindowToken windowToken) {
        RootWindowContainer rootWindowContainer = this.mWmService.mRoot;
        DisplayContent displayContent = null;
        if (windowToken != null) {
            int size = rootWindowContainer.mChildren.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                DisplayContent displayContent2 =
                        (DisplayContent) rootWindowContainer.mChildren.get(size);
                if (displayContent2.getWindowToken(windowToken.token) == windowToken) {
                    displayContent = displayContent2;
                    break;
                }
                size--;
            }
        } else {
            rootWindowContainer.getClass();
        }
        if (displayContent != null) {
            throw new IllegalArgumentException(
                    "Can't map token="
                            + windowToken
                            + " to display="
                            + getName()
                            + " already mapped to display="
                            + displayContent
                            + " tokens="
                            + displayContent.mTokenMap);
        }
        if (iBinder == null) {
            throw new IllegalArgumentException(
                    "Can't map token="
                            + windowToken
                            + " to display="
                            + getName()
                            + " binder is null");
        }
        if (windowToken == null) {
            throw new IllegalArgumentException(
                    "Can't map null token to display=" + getName() + " binder=" + iBinder);
        }
        this.mTokenMap.put(iBinder, windowToken);
        if (windowToken.asActivityRecord() == null) {
            windowToken.mDisplayContent = this;
            DisplayArea.Tokens asTokens =
                    findAreaForWindowType(
                                    windowToken.windowType,
                                    windowToken.mOptions,
                                    windowToken.mOwnerCanManageAppTokens,
                                    windowToken.mRoundedCornerOverlay)
                            .asTokens();
            asTokens.addChild(windowToken, asTokens.mWindowComparator);
        }
    }

    public final void adjustDisplaySizeRanges(
            DisplayInfo displayInfo, int i, int i2, int i3, boolean z) {
        int width;
        int height;
        DisplayPolicy.DecorInsets.Info decorInsetsInfo =
                this.mDisplayPolicy.getDecorInsetsInfo(i, i2, i3);
        if (z) {
            width = decorInsetsInfo.mOverrideConfigFrame.width();
            height = decorInsetsInfo.mOverrideConfigFrame.height();
        } else {
            width = decorInsetsInfo.mConfigFrame.width();
            height = decorInsetsInfo.mConfigFrame.height();
        }
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

    public final void adjustForImeIfNeeded() {
        WindowState windowState = this.mInputMethodWindow;
        boolean z = windowState != null && windowState.isVisible() && windowState.isDisplayed();
        int inputMethodWindowVisibleHeight = getInputMethodWindowVisibleHeight();
        PinnedTaskController pinnedTaskController = this.mPinnedTaskController;
        pinnedTaskController.getClass();
        boolean z2 = z && inputMethodWindowVisibleHeight > 0;
        int i = z2 ? inputMethodWindowVisibleHeight : 0;
        if (z2 == pinnedTaskController.mIsImeShowing && i == pinnedTaskController.mImeHeight) {
            return;
        }
        pinnedTaskController.mIsImeShowing = z2;
        pinnedTaskController.mImeHeight = i;
        IPinnedTaskListener iPinnedTaskListener = pinnedTaskController.mPinnedTaskListener;
        if (iPinnedTaskListener != null) {
            try {
                iPinnedTaskListener.onImeVisibilityChanged(z2, i);
            } catch (RemoteException e) {
                Slog.e("WindowManager", "Error delivering bounds changed event.", e);
            }
        }
        pinnedTaskController.notifyMovementBoundsChanged(true);
    }

    public final void applyFixedRotationForNonTopVisibleActivityIfNeeded(
            int i, ActivityRecord activityRecord) {
        WindowState topVisibleWallpaper;
        int requestedOrientation = activityRecord.getRequestedOrientation();
        if (requestedOrientation == i
                || activityRecord.inMultiWindowMode()
                || activityRecord.getRequestedConfigurationOrientation() == 0) {
            return;
        }
        DisplayRotation displayRotation = this.mDisplayRotation;
        int i2 = displayRotation.mRotation;
        int displayRotation2 =
                activityRecord.mVisible
                        ? activityRecord.getWindowConfiguration().getDisplayRotation()
                        : displayRotation.rotationForOrientation(requestedOrientation, i2);
        if (displayRotation2 == i2) {
            return;
        }
        startFixedRotationTransform(activityRecord, displayRotation2);
        WallpaperController wallpaperController = this.mWallpaperController;
        WindowState windowState = wallpaperController.mWallpaperTarget;
        if (windowState == null
                || windowState.mActivityRecord != activityRecord
                || (topVisibleWallpaper = wallpaperController.getTopVisibleWallpaper()) == null) {
            return;
        }
        topVisibleWallpaper.mToken.linkFixedRotationTransform(activityRecord);
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01a4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void applyRotation(int r18, int r19) {
        /*
            Method dump skipped, instructions count: 540
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.wm.DisplayContent.applyRotation(int,"
                    + " int):void");
    }

    @Override // com.android.server.wm.WindowContainer
    public final DisplayContent asDisplayContent() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void assignChildLayers(SurfaceControl.Transaction transaction) {
        assignRelativeLayerForIme(transaction, false);
        super.assignChildLayers(transaction);
    }

    public final void assignRelativeLayerForIme(SurfaceControl.Transaction transaction, boolean z) {
        ActivityRecord activityRecord;
        if (this.mImeWindowsContainer.isOrganized()) {
            return;
        }
        this.mImeWindowsContainer.mNeedsLayer = true;
        WindowState windowState = this.mImeLayeringTarget;
        if (this.isDefaultDisplay
                && this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked()) {
            if (!this.mDisplayPolicy.mHasNavigationBar) {
                SurfaceControl surfaceControl = getSurfaceControl();
                if (this.mImeWindowsContainer.getSurfaceControl() != null) {
                    ImeContainer imeContainer = this.mImeWindowsContainer;
                    imeContainer.mNeedsLayer = true;
                    imeContainer.assignRelativeLayer(transaction, surfaceControl, 1);
                    return;
                }
            }
        } else if (windowState != null
                && ((activityRecord = windowState.mActivityRecord) == null
                        || !activityRecord.hasStartingWindow())) {
            InsetsControlTarget insetsControlTarget = this.mImeControlTarget;
            WindowToken windowToken =
                    (insetsControlTarget == null || insetsControlTarget.getWindow() == null)
                            ? null
                            : this.mImeControlTarget.getWindow().mToken;
            if (windowState.mSurfaceControl != null
                    && windowState.mToken == windowToken
                    && !windowState.inMultiWindowMode()) {
                this.mImeWindowsContainer.assignRelativeLayer(
                        transaction, windowState.mSurfaceControl, 1, z);
                return;
            }
        }
        SurfaceControl surfaceControl2 = this.mInputMethodSurfaceParent;
        if (surfaceControl2 != null) {
            this.mImeWindowsContainer.assignRelativeLayer(transaction, surfaceControl2, 1, z);
        }
    }

    public final void assignWindowLayers(boolean z) {
        Trace.traceBegin(32L, "assignWindowLayers");
        assignChildLayers(getSyncTransaction());
        if (z) {
            setLayoutNeeded();
        }
        scheduleAnimation();
        Trace.traceEnd(32L);
    }

    public final void attachImeScreenshotOnTarget(WindowState windowState, boolean z) {
        SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
        removeImeSurfaceImmediately();
        ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer = null;
        SurfaceControl.Builder builder =
                (SurfaceControl.Builder) this.mWmService.mSurfaceControlFactory.apply(null);
        ImeScreenshot imeScreenshot = new ImeScreenshot();
        imeScreenshot.mImeTarget = windowState;
        this.mImeScreenshot = imeScreenshot;
        DisplayContent displayContent = windowState.getDisplayContent();
        Task task = windowState.getTask();
        SurfaceControl surfaceControl = imeScreenshot.mImeSurface;
        boolean z2 =
                (surfaceControl != null
                                && surfaceControl.getWidth()
                                        == displayContent.mInputMethodWindow.mWindowFrames.mFrame
                                                .width()
                                && imeScreenshot.mImeSurface.getHeight()
                                        == displayContent.mInputMethodWindow.mWindowFrames.mFrame
                                                .height())
                        ? false
                        : true;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled;
        if (task != null && (z || !task.isActivityTypeHomeOrRecents())) {
            if (z2) {
                TaskSnapshotController taskSnapshotController =
                        displayContent.mWmService.mTaskSnapshotController;
                if (taskSnapshotController.checkIfReadyToSnapshot(task) != null) {
                    int i = taskSnapshotController.mPersistInfoProvider.mUse16BitFormat ? 4 : 1;
                    if (task.mSurfaceControl == null) {
                        Slog.w(
                                "WindowManager",
                                "Failed to take screenshot. No surface control for " + task);
                    } else {
                        WindowState windowState2 = task.getDisplayContent().mInputMethodWindow;
                        if (windowState2 != null && windowState2.isVisible()) {
                            Rect rect = windowState2.mWindowFrames.mParentFrame;
                            rect.offsetTo(0, 0);
                            screenshotHardwareBuffer =
                                    ScreenCapture.captureLayersExcluding(
                                            windowState2.mSurfaceControl,
                                            rect,
                                            1.0f,
                                            i,
                                            (SurfaceControl[]) null,
                                            true);
                        }
                    }
                }
            }
            if (screenshotHardwareBuffer != null) {
                imeScreenshot.removeImeSurface(pendingTransaction);
                HardwareBuffer hardwareBuffer = screenshotHardwareBuffer.getHardwareBuffer();
                if (zArr[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_DEBUG_IME,
                            4835192778854186097L,
                            0,
                            null,
                            String.valueOf(windowState),
                            String.valueOf(hardwareBuffer.getWidth()),
                            String.valueOf(hardwareBuffer.getHeight()));
                }
                WindowState windowState3 = windowState.getDisplayContent().mInputMethodWindow;
                ActivityRecord activityRecord = windowState.mActivityRecord;
                SurfaceControl surfaceControl2 =
                        windowState.mAttrs.type == 1
                                ? activityRecord.mSurfaceControl
                                : windowState.mSurfaceControl;
                SurfaceControl build =
                        builder.setName("IME-snapshot-surface")
                                .setBLASTLayer()
                                .setFormat(hardwareBuffer.getFormat())
                                .setParent(surfaceControl2)
                                .setCallsite("DisplayContent.attachAndShowImeScreenshotOnTarget")
                                .build();
                InputMonitor.setTrustedOverlayInputInfo(
                        build,
                        pendingTransaction,
                        windowState3.getDisplayId(),
                        "IME-snapshot-surface");
                pendingTransaction.setBuffer(build, hardwareBuffer);
                pendingTransaction.setColorSpace(
                        activityRecord.mSurfaceControl, ColorSpace.get(ColorSpace.Named.SRGB));
                pendingTransaction.setLayer(build, 1);
                Rect rect2 = windowState3.mWindowFrames.mFrame;
                Point point = new Point(rect2.left, rect2.top);
                if (surfaceControl2 == activityRecord.mSurfaceControl) {
                    Task task2 = activityRecord.task;
                    if (task2 != null && task2.inMultiWindowMode()) {
                        point.offset(
                                -activityRecord.getBounds().left, -activityRecord.getBounds().top);
                    }
                    pendingTransaction.setPosition(build, point.x, point.y);
                } else {
                    Rect rect3 = windowState.mWindowFrames.mFrame;
                    point.offset(-rect3.left, -rect3.top);
                    Rect rect4 = windowState.mAttrs.surfaceInsets;
                    point.offset(rect4.left, rect4.top);
                    pendingTransaction.setPosition(build, point.x, point.y);
                }
                imeScreenshot.mImeSurfacePosition = point;
                if (zArr[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_DEBUG_IME,
                            2408509162360028352L,
                            5,
                            null,
                            Long.valueOf(point.x),
                            Long.valueOf(point.y));
                }
                imeScreenshot.mImeSurface = build;
                if (displayContent.mTransitionController.inTransition()) {
                    displayContent.mTransitionController.mStateValidators.add(
                            new DisplayContent$$ExternalSyntheticLambda25(2, displayContent));
                }
            }
        }
        SurfaceControl surfaceControl3 = imeScreenshot.mImeSurface;
        boolean z3 = surfaceControl3 != null && surfaceControl3.isValid();
        if (z3 && displayContent.mInsetsStateController.getImeSourceProvider().mImeShowing) {
            if (zArr[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_DEBUG_IME,
                        -6495118720675662641L,
                        0,
                        null,
                        String.valueOf(windowState),
                        String.valueOf(Debug.getCallers(6)));
            }
            pendingTransaction.show(imeScreenshot.mImeSurface);
            if (ImeTracker.DEBUG_IME_VISIBILITY) {
                EventLog.writeEvent(
                        32004,
                        windowState.toString(),
                        Integer.valueOf(displayContent.mInputMethodWindow.mTransitFlags),
                        imeScreenshot.mImeSurfacePosition.toString());
            }
        } else if (!z3) {
            imeScreenshot.removeImeSurface(pendingTransaction);
        }
        WindowState windowState4 = this.mInputMethodWindow;
        if (windowState4 == null || !z || this.mImeScreenshot.mImeSurface == null) {
            return;
        }
        windowState4.hide(false, false);
    }

    public final DisplayCutout calculateDisplayCutoutForRotation(int i, boolean z) {
        if (!z) {
            return ((WmDisplayCutout)
                            this.mDisplayCutoutCache.getOrCompute(i, this.mBaseDisplayCutout))
                    .mInner;
        }
        UdcCutoutPolicy udcCutoutPolicy = this.mUdcCutoutPolicy;
        return ((WmDisplayCutout)
                        udcCutoutPolicy.mDisplayCutoutCache.getOrCompute(
                                i, udcCutoutPolicy.mUdcCutout))
                .mInner;
    }

    public final RoundedCorners calculateRoundedCornersForRotation(int i) {
        return (RoundedCorners)
                this.mRoundedCornerCache.getOrCompute(
                        i,
                        (this.mIsSizeForced || this.mIsDensityForced)
                                ? this.mBaseRoundedCorners
                                : this.mInitialRoundedCorners);
    }

    public boolean calculateSystemGestureExclusion(final Region region, final Region region2) {
        region.setEmpty();
        if (region2 != null) {
            region2.setEmpty();
        }
        final Region obtain = Region.obtain();
        DisplayFrames displayFrames = this.mDisplayFrames;
        obtain.set(0, 0, displayFrames.mWidth, displayFrames.mHeight);
        InsetsState insetsState = this.mInsetsStateController.mState;
        Rect displayFrame = insetsState.getDisplayFrame();
        Insets calculateInsets =
                insetsState.calculateInsets(
                        displayFrame, WindowInsets.Type.systemGestures(), false);
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
                this.mWmService.mRecentsAnimationController;
        forAllWindows(
                new Consumer() { // from class:
                                 // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda15
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i4;
                        ActivityRecord activityRecord;
                        DisplayContent displayContent = DisplayContent.this;
                        RecentsAnimationController recentsAnimationController2 =
                                recentsAnimationController;
                        Region region3 = obtain;
                        Region region4 = obtain2;
                        Region region5 = obtain3;
                        int[] iArr2 = iArr;
                        Region region6 = region;
                        Region region7 = region2;
                        WindowState windowState = (WindowState) obj;
                        displayContent.getClass();
                        boolean z =
                                recentsAnimationController2 != null
                                        && recentsAnimationController2.shouldApplyInputConsumer(
                                                windowState.mActivityRecord);
                        if (windowState.canReceiveTouchInput() && windowState.isVisible()) {
                            WindowManager.LayoutParams layoutParams = windowState.mAttrs;
                            if ((layoutParams.flags & 16) != 0
                                    || (i4 = layoutParams.type) == 2601
                                    || i4 == 2600
                                    || region3.isEmpty()
                                    || z) {
                                return;
                            }
                            windowState.getEffectiveTouchableRegion(region4);
                            region4.op(region3, Region.Op.INTERSECT);
                            if ((windowState.getTask() == null
                                            || !windowState.getTask().isFreeformStashed())
                                    && (windowState.mAttrs.insetsFlags.behavior != 2
                                            || windowState.isRequestedVisible(
                                                    WindowInsets.Type.navigationBars(), false)
                                            || !windowState
                                                    .mWmService
                                                    .mConstants
                                                    .mSystemGestureExcludedByPreQStickyImmersive
                                            || (activityRecord = windowState.mActivityRecord)
                                                    == null
                                            || activityRecord.mTargetSdk >= 29)) {
                                List list = windowState.mExclusionRects;
                                region5.setEmpty();
                                ArrayList arrayList = (ArrayList) list;
                                int size = arrayList.size();
                                for (int i5 = 0; i5 < size; i5++) {
                                    region5.union((Rect) arrayList.get(i5));
                                }
                                region5.scale(windowState.mGlobalScale);
                                Rect rect3 = windowState.mWindowFrames.mFrame;
                                region5.translate(rect3.left, rect3.top);
                                region5.op(region4, Region.Op.INTERSECT);
                            } else {
                                region5.set(region4);
                            }
                            if (DisplayContent.needsGestureExclusionRestrictions(
                                    windowState, false)) {
                                iArr2[0] =
                                        DisplayContent.addToGlobalAndConsumeLimit(
                                                region5,
                                                region6,
                                                displayContent.mSystemGestureFrameLeft,
                                                iArr2[0],
                                                windowState,
                                                0);
                                iArr2[1] =
                                        DisplayContent.addToGlobalAndConsumeLimit(
                                                region5,
                                                region6,
                                                displayContent.mSystemGestureFrameRight,
                                                iArr2[1],
                                                windowState,
                                                1);
                                Region obtain4 = Region.obtain(region5);
                                Rect rect4 = displayContent.mSystemGestureFrameLeft;
                                Region.Op op = Region.Op.DIFFERENCE;
                                obtain4.op(rect4, op);
                                obtain4.op(displayContent.mSystemGestureFrameRight, op);
                                region6.op(obtain4, Region.Op.UNION);
                                obtain4.recycle();
                            } else {
                                if (DisplayContent.needsGestureExclusionRestrictions(
                                        windowState, true)) {
                                    DisplayContent.addToGlobalAndConsumeLimit(
                                            region5,
                                            region6,
                                            displayContent.mSystemGestureFrameLeft,
                                            Integer.MAX_VALUE,
                                            windowState,
                                            0);
                                    DisplayContent.addToGlobalAndConsumeLimit(
                                            region5,
                                            region6,
                                            displayContent.mSystemGestureFrameRight,
                                            Integer.MAX_VALUE,
                                            windowState,
                                            1);
                                }
                                region6.op(region5, Region.Op.UNION);
                            }
                            if (region7 != null) {
                                region7.op(region5, Region.Op.UNION);
                            }
                            region3.op(region4, Region.Op.DIFFERENCE);
                        }
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

    public final boolean canAddToastWindowForUid(int i) {
        return getWindow(new DisplayContent$$ExternalSyntheticLambda14(i, 1)) != null
                || getWindow(new DisplayContent$$ExternalSyntheticLambda14(i, 2)) == null;
    }

    public final void checkFocusMonitoringPolicy(ActivityRecord activityRecord, String str) {
        if (activityRecord == null) {
            return;
        }
        String str2 = activityRecord.packageName;
        IApplicationPolicy iApplicationPolicy = this.mApplicationPolicy;
        if (iApplicationPolicy == null) {
            synchronized (this) {
                try {
                    iApplicationPolicy = this.mApplicationPolicy;
                    if (iApplicationPolicy == null) {
                        iApplicationPolicy =
                                IApplicationPolicy.Stub.asInterface(
                                        ServiceManager.getService("application_policy"));
                        this.mApplicationPolicy = iApplicationPolicy;
                    }
                } finally {
                }
            }
        }
        if (iApplicationPolicy != null) {
            try {
                int i = activityRecord.mUserId;
                if (iApplicationPolicy.isApplicationFocusMonitoredAsUser(str2, i)) {
                    sendApplicationFocusMonitoringIntent(
                            i,
                            activityRecord.mActivityComponent.flattenToString(),
                            str,
                            isDexMode());
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public final void clearFixedRotationLaunchingApp() {
        ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
        if (activityRecord == null) {
            return;
        }
        activityRecord.finishFixedRotationTransform(null);
        setFixedRotationLaunchingAppUnchecked(-1, null);
    }

    public final void collectDisplayChange(Transition transition) {
        if (this.mLastHasContent) {
            if (!transition.isCollecting()) {
                throw new IllegalArgumentException(
                        "Can only collect display change if transition is collecting");
            }
            if (transition.mParticipants.contains(this)) {
                if (this.mAsyncRotationController == null || isRotationChanging()) {
                    return;
                }
                Slog.i("WindowManager", "Finish AsyncRotation for previous intermediate change");
                finishAsyncRotationIfPossible();
                return;
            }
            transition.collect(this, false);
            if (isRotationChanging()) {
                startAsyncRotation(false);
            }
            if (this.mFixedRotationLaunchingApp != null) {
                Transition.ChangeInfo changeInfo =
                        (Transition.ChangeInfo) transition.mChanges.get(this);
                if (changeInfo != null) {
                    changeInfo.mFlags |= 1;
                    transition.onSeamlessRotating(getDisplayContent());
                }
                AsyncRotationController asyncRotationController = this.mAsyncRotationController;
                if (asyncRotationController != null) {
                    asyncRotationController.keepAppearanceInPreviousRotation();
                }
            }
        }
    }

    public final void collectFixedRotationLaunchingAppIfNeeded() {
        if (this.mTransitionController.getCollectingTransitionType() == 10
                && hasTopFixedRotationLaunchingApp()
                && this.mFixedRotationLaunchingApp.hasFixedRotationTransform()
                && this.mFixedRotationLaunchingApp.inFullscreenWindowingMode()) {
            Slog.d(
                    "WindowManager",
                    "collectFixedRotationLaunchingAppIfNeeded: "
                            + this.mFixedRotationLaunchingApp
                            + ", reason:enter_pip");
            this.mTransitionController.collect(this.mFixedRotationLaunchingApp);
        }
    }

    public InsetsControlTarget computeImeControlTarget() {
        RemoteInsetsControlTarget remoteInsetsControlTarget;
        InputTarget inputTarget;
        if (this.mInputMethodWindow != null
                && this.mRemoteInsetsControlTarget != null
                && (inputTarget = this.mImeInputTarget) == null
                && ((inputTarget == null || !inputTarget.shouldControlIme())
                        && ((this.mAtmService.mDexController.getDexModeLocked() == 2
                                        && (this.mDisplayId == 2
                                                || (this.isDefaultDisplay
                                                        && this.mAtmService.mDexController
                                                                .shouldShowDexImeInDefaultDisplayLocked())))
                                || (this.isDefaultDisplay
                                        && getParent()
                                                .getTopChild()
                                                .asDisplayContent()
                                                .isAppCastingDisplay())))) {
            return this.mRemoteInsetsControlTarget;
        }
        InputTarget inputTarget2 = this.mImeInputTarget;
        if (inputTarget2 != null) {
            WindowState windowState = inputTarget2.getWindowState();
            InputTarget inputTarget3 = this.mImeInputTarget;
            return (((inputTarget3 == null || !inputTarget3.shouldControlIme())
                                    && this.mRemoteInsetsControlTarget != null)
                            || getImeHostOrFallback(windowState) == this.mRemoteInsetsControlTarget)
                    ? this.mRemoteInsetsControlTarget
                    : windowState;
        }
        if (Flags.refactorInsetsController()
                && this.isDefaultDisplay
                && (remoteInsetsControlTarget = this.mRemoteInsetsControlTarget) != null) {
            return remoteInsetsControlTarget;
        }
        return null;
    }

    public SurfaceControl computeImeParent() {
        WindowState windowState;
        WindowState windowState2 = this.mImeLayeringTarget;
        InputTarget inputTarget = this.mImeInputTarget;
        boolean z = false;
        if (windowState2 != null) {
            if (inputTarget != null
                    && (windowState = inputTarget.getWindowState()) != null
                    && windowState2.isAttached()
                    && windowState.isAttached()) {
                ActivityRecord activityRecord = inputTarget.getActivityRecord();
                ActivityRecord activityRecord2 = windowState2.mActivityRecord;
                if (activityRecord != null
                        && activityRecord2 != null
                        && activityRecord != activityRecord2
                        && activityRecord.task == activityRecord2.task
                        && activityRecord.isEmbedded()
                        && activityRecord2.isEmbedded()
                        && windowState2.compareTo((WindowContainer) windowState) > 0) {
                    z = true;
                }
            }
            boolean z2 =
                    WindowManager.LayoutParams.mayUseInputMethod(windowState2.mAttrs.flags)
                            || windowState2.mAttrs.type == 3;
            boolean z3 =
                    inputTarget == null
                            || windowState2.mActivityRecord != inputTarget.getActivityRecord();
            if (windowState2.inFreeformWindowingMode()) {
                z3 = false;
            }
            if (z2 && z3) {
                z = true;
            }
            z = !z;
        }
        if (!z) {
            return null;
        }
        if (shouldImeAttachedToApp()) {
            return this.mImeLayeringTarget.mActivityRecord.mSurfaceControl;
        }
        if (this.mImeWindowsContainer.getParent() != null) {
            return this.mImeWindowsContainer.getParent().getSurfaceControl();
        }
        return null;
    }

    public final WindowState computeImeTarget(boolean z) {
        WindowState windowState = this.mInputMethodWindow;
        if (windowState == null) {
            if (z) {
                setImeLayeringTargetInner(null);
            }
            return null;
        }
        windowState.updateLetterboxDirectionIfNeeded();
        WindowState windowState2 = this.mImeLayeringTarget;
        if (this.mDeferUpdateImeTargetCount != 0) {
            this.mUpdateImeRequestedWhileDeferred = true;
            return windowState2;
        }
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

    public final void computeScreenAppConfiguration(
            Configuration configuration, int i, int i2, int i3) {
        configuration.windowConfiguration.setAppBounds(
                this.mDisplayPolicy.getDecorInsetsInfo(i3, i, i2).mNonDecorFrame);
        configuration.windowConfiguration.setRotation(i3);
        float f = this.mDisplayMetrics.density;
        configuration.screenWidthDp = (int) ((r0.mConfigFrame.width() / f) + 0.5f);
        int height = (int) ((r0.mConfigFrame.height() / f) + 0.5f);
        configuration.screenHeightDp = height;
        int i4 = configuration.screenWidthDp;
        float f2 = this.mCompatibleScreenScale;
        configuration.compatScreenWidthDp = (int) (i4 / f2);
        configuration.compatScreenHeightDp = (int) (height / f2);
        boolean z = true;
        configuration.orientation = i4 <= height ? 1 : 2;
        configuration.screenLayout =
                WindowContainer.computeScreenLayout(
                        Configuration.resetScreenLayout(configuration.screenLayout),
                        configuration.screenWidthDp,
                        configuration.screenHeightDp);
        if (i3 != 1 && i3 != 3) {
            z = false;
        }
        this.mTmpDisplayMetrics.setTo(this.mDisplayMetrics);
        DisplayMetrics displayMetrics = this.mTmpDisplayMetrics;
        if (!z) {
            i2 = i;
            i = i2;
        }
        configuration.compatSmallestScreenWidthDp =
                reduceCompatConfigWidthSize(
                        reduceCompatConfigWidthSize(
                                reduceCompatConfigWidthSize(
                                        reduceCompatConfigWidthSize(0, 0, displayMetrics, i2, i),
                                        1,
                                        displayMetrics,
                                        i,
                                        i2),
                                2,
                                displayMetrics,
                                i2,
                                i),
                        3,
                        displayMetrics,
                        i,
                        i2);
        configuration.windowConfiguration.setDisplayRotation(i3);
    }

    public final DisplayInfo computeScreenConfiguration(
            Configuration configuration, int i, boolean z) {
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
        computeSizeRanges(
                displayInfo, z2, i2, i3, this.mDisplayMetrics.density, configuration, false);
        return displayInfo;
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x01b8, code lost:

       if ((r3 == 1 ? r4 == 0 : r3 == 2 && r4 == 1) != false) goto L112;
    */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0295, code lost:

       if (r6 == null) goto L188;
    */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0195, code lost:

       if ((r3 == 1 ? r4 == 0 : r3 == 2 && r4 == 1) != false) goto L97;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:155:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x012e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01a3  */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v16 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void computeScreenConfiguration(android.content.res.Configuration r17) {
        /*
            Method dump skipped, instructions count: 832
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayContent.computeScreenConfiguration(android.content.res.Configuration):void");
    }

    public final void computeSizeRanges(
            DisplayInfo displayInfo,
            boolean z,
            int i,
            int i2,
            float f,
            Configuration configuration,
            boolean z2) {
        if (z) {
            i2 = i;
            i = i2;
        }
        displayInfo.smallestNominalAppWidth = 1073741824;
        displayInfo.smallestNominalAppHeight = 1073741824;
        displayInfo.largestNominalAppWidth = 0;
        displayInfo.largestNominalAppHeight = 0;
        adjustDisplaySizeRanges(displayInfo, 0, i, i2, z2);
        adjustDisplaySizeRanges(displayInfo, 1, i2, i, z2);
        adjustDisplaySizeRanges(displayInfo, 2, i, i2, z2);
        adjustDisplaySizeRanges(displayInfo, 3, i2, i, z2);
        if (configuration == null) {
            return;
        }
        configuration.smallestScreenWidthDp =
                (int) ((displayInfo.smallestNominalAppWidth / f) + 0.5f);
    }

    public final void configureDisplayPolicy() {
        this.mRootWindowContainer.updateDisplayImePolicyCache();
        this.mDisplayPolicy.updateConfigurationAndScreenSizeDependentBehaviors();
        DisplayRotation displayRotation = this.mDisplayRotation;
        int i = this.mBaseDisplayWidth;
        int i2 = this.mBaseDisplayHeight;
        Resources resources = displayRotation.mContext.getResources();
        if (i > i2) {
            displayRotation.mLandscapeRotation = 0;
            displayRotation.mSeascapeRotation = 2;
            if (resources.getBoolean(R.bool.config_shortPressEarlyOnStemPrimary)) {
                displayRotation.mPortraitRotation = 1;
                displayRotation.mUpsideDownRotation = 3;
            } else {
                displayRotation.mPortraitRotation = 3;
                displayRotation.mUpsideDownRotation = 1;
            }
        } else {
            displayRotation.mPortraitRotation = 0;
            displayRotation.mUpsideDownRotation = 2;
            if (resources.getBoolean(R.bool.config_shortPressEarlyOnStemPrimary)) {
                displayRotation.mLandscapeRotation = 3;
                displayRotation.mSeascapeRotation = 1;
            } else {
                displayRotation.mLandscapeRotation = 1;
                displayRotation.mSeascapeRotation = 3;
            }
        }
        if ("portrait".equals(SystemProperties.get("persist.demo.hdmirotation"))) {
            displayRotation.mDemoHdmiRotation = displayRotation.mPortraitRotation;
        } else {
            displayRotation.mDemoHdmiRotation = displayRotation.mLandscapeRotation;
        }
        displayRotation.mDemoHdmiRotationLock =
                SystemProperties.getBoolean("persist.demo.hdmirotationlock", false);
        if ("portrait".equals(SystemProperties.get("persist.demo.remoterotation"))) {
            displayRotation.mDemoRotation = displayRotation.mPortraitRotation;
        } else {
            displayRotation.mDemoRotation = displayRotation.mLandscapeRotation;
        }
        displayRotation.mDemoRotationLock =
                SystemProperties.getBoolean("persist.demo.rotationlock", false);
        boolean hasSystemFeature =
                displayRotation
                        .mContext
                        .getPackageManager()
                        .hasSystemFeature("android.hardware.type.automotive");
        boolean hasSystemFeature2 =
                displayRotation
                        .mContext
                        .getPackageManager()
                        .hasSystemFeature("android.software.leanback");
        DisplayContent displayContent = displayRotation.mDisplayContent;
        displayRotation.mDefaultFixedToUserRotation =
                (hasSystemFeature
                                || hasSystemFeature2
                                || displayRotation.mService.mIsPc
                                || displayContent.forceDesktopMode()
                                || (displayContent.mDisplayInfo.flags
                                                & EndpointMonitorConst
                                                        .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION)
                                        == 0
                                || (displayContent.mDisplayId == 2))
                        && !"true".equals(SystemProperties.get("config.override_forced_orient"));
    }

    public final void configureSurfaces(SurfaceControl.Transaction transaction) {
        SurfaceControl.Builder callsite =
                this.mWmService
                        .makeSurfaceBuilder(this.mSession)
                        .setOpaque(true)
                        .setContainerLayer()
                        .setCallsite("DisplayContent");
        this.mSurfaceControl = callsite.setName(getName()).setContainerLayer().build();
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            SurfaceControl surfaceControl = ((DisplayArea) getChildAt(childCount)).mSurfaceControl;
            if (surfaceControl != null) {
                transaction.reparent(surfaceControl, this.mSurfaceControl);
            }
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
                    callsite.setName("Accessibility Overlays")
                            .setParent(this.mSurfaceControl)
                            .build();
        } else {
            transaction.reparent(surfaceControl4, this.mSurfaceControl);
        }
        transaction
                .setLayerStack(this.mSurfaceControl, this.mDisplayId)
                .show(this.mSurfaceControl)
                .setLayer(this.mOverlayLayer, Integer.MAX_VALUE)
                .show(this.mOverlayLayer)
                .setLayer(this.mInputOverlayLayer, 2147483646)
                .show(this.mInputOverlayLayer)
                .setLayer(this.mA11yOverlayLayer, 2147483645)
                .show(this.mA11yOverlayLayer);
    }

    public final void continueUpdateImeTarget() {
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

    public final void continueUpdateOrientationForDiffOrienLaunchingApp() {
        boolean z;
        ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
        if (activityRecord == null || this.mPinnedTaskController.mDeferOrientationChanging) {
            return;
        }
        DragState dragState = this.mWmService.mDragDropController.mDragState;
        if (dragState == null
                || !dragState.mDragInProgressByRecents
                || activityRecord.isActivityTypeHomeOrRecents()) {
            z = false;
        } else {
            Slog.d(
                    "WindowManager",
                    "continueUpdateOrientationForDiffOrienLaunchingApp: forceUpdate,"
                        + " reason=drag_recents, r="
                            + this.mFixedRotationLaunchingApp);
            z = true;
        }
        if (this.mDisplayRotation.updateOrientation(getOrientation(), z)) {
            if (this.mTransitionController.isCollecting(this)) {
                return;
            }
            sendNewConfiguration();
        } else {
            if (this.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange()) {
                return;
            }
            clearFixedRotationLaunchingApp();
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final void dump(final PrintWriter printWriter, final String str, final boolean z) {
        Task task;
        DisplayCutout displayCutout;
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        printWriter, str, "Display: mDisplayId=");
        m.append(this.mDisplayId);
        m.append(isOrganized() ? " (organized)" : "");
        printWriter.println(m.toString());
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
                str2
                        + "deferred="
                        + this.mDeferredRemoval
                        + " mLayoutNeeded="
                        + this.mLayoutNeeded);
        printWriter.println();
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("initCutout=");
        printWriter.print(sb.toString());
        printWriter.println(this.mInitialDisplayCutout);
        printWriter.print(str2 + "baseCutout=");
        printWriter.println(this.mBaseDisplayCutout);
        UdcCutoutPolicy udcCutoutPolicy = this.mUdcCutoutPolicy;
        if (udcCutoutPolicy != null
                && (displayCutout = udcCutoutPolicy.mUdcCutout) != null
                && !displayCutout.isEmpty()) {
            printWriter.print(str2 + "udcCutout=");
            printWriter.println(udcCutoutPolicy.mUdcCutout);
            Configuration configuration = udcCutoutPolicy.mUdcConfiguration;
            if (configuration != null && !configuration.equals(Configuration.EMPTY)) {
                printWriter.print(str2 + "udcConfig=");
                printWriter.print(udcCutoutPolicy.mUdcConfiguration);
            }
            printWriter.println();
        }
        printWriter.println();
        printWriter.print(str2 + "initRoundedCorners=");
        printWriter.println(this.mInitialRoundedCorners);
        printWriter.print(str2 + "baseRoundedCorners=");
        printWriter.println(this.mBaseRoundedCorners);
        if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT
                && this.mIsOverlappingWithCutoutAsDefault) {
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
        AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        if (asyncRotationController != null) {
            printWriter.println(str + "AsyncRotationController");
            String str3 = str + "  ";
            StringBuilder m2 =
                    MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                    MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                    .m(
                                                            Preconditions$$ExternalSyntheticOutline0
                                                                    .m(str3, "mTransitionOp="),
                                                            asyncRotationController.mTransitionOp,
                                                            printWriter,
                                                            str3,
                                                            "mIsStartTransactionCommitted="),
                                            asyncRotationController.mIsStartTransactionCommitted,
                                            printWriter,
                                            str3,
                                            "mIsSyncDrawRequested="),
                                    asyncRotationController.mIsSyncDrawRequested,
                                    printWriter,
                                    str3,
                                    "mOriginalRotation="),
                            asyncRotationController.mOriginalRotation,
                            printWriter,
                            str3,
                            "mTargetWindowTokens=");
            m2.append(asyncRotationController.mTargetWindowTokens);
            printWriter.println(m2.toString());
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
        this.mWallpaperController.dump(printWriter);
        if (this.mSystemGestureExclusionListeners.getRegisteredCallbackCount() > 0) {
            printWriter.println();
            printWriter.print("  mSystemGestureExclusion=");
            printWriter.println(this.mSystemGestureExclusion);
        }
        ArraySet arraySet = new ArraySet();
        forAllWindows(
                (ToBooleanFunction)
                        new DisplayContent$$ExternalSyntheticLambda2(
                                this.mWmService.mRecentsAnimationController,
                                arraySet,
                                arraySet,
                                new Matrix(),
                                new float[9]),
                true);
        if (!arraySet.isEmpty()) {
            printWriter.println();
            printWriter.print("  keepClearAreas=");
            printWriter.println(arraySet);
        }
        printWriter.println();
        printWriter.println(str + "Display areas in top down Z order:");
        dumpChildDisplayArea(printWriter, str2, z);
        printWriter.println();
        printWriter.println(str + "Task display areas in top down Z order:");
        forAllTaskDisplayAreas(
                new Consumer() { // from class:
                                 // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PrintWriter printWriter2 = printWriter;
                        String str4 = str;
                        ((TaskDisplayArea) obj).dump(printWriter2, str4 + "  ", z);
                    }
                });
        printWriter.println();
        ScreenRotationAnimation screenRotationAnimation = this.mScreenRotationAnimation;
        if (screenRotationAnimation != null) {
            ProcessList$$ExternalSyntheticOutline0.m(
                    printWriter, "  mScreenRotationAnimation:", str2, "mSurface=");
            printWriter.print(screenRotationAnimation.mScreenshotLayer);
            printWriter.print(str2);
            printWriter.print("mEnteringBlackFrame=");
            printWriter.println(screenRotationAnimation.mEnteringBlackFrame);
            BlackFrame blackFrame = screenRotationAnimation.mEnteringBlackFrame;
            if (blackFrame != null) {
                String str4 = str2 + "  ";
                printWriter.print(str4);
                printWriter.print("Outer: ");
                blackFrame.mOuterRect.printShortString(printWriter);
                printWriter.print(" / Inner: ");
                blackFrame.mInnerRect.printShortString(printWriter);
                printWriter.println();
                int i = 0;
                while (true) {
                    BlackFrame.BlackSurface[] blackSurfaceArr = blackFrame.mBlackSurfaces;
                    if (i >= blackSurfaceArr.length) {
                        break;
                    }
                    BlackFrame.BlackSurface blackSurface = blackSurfaceArr[i];
                    printWriter.print(str4);
                    printWriter.print("#");
                    printWriter.print(i);
                    printWriter.print(": ");
                    printWriter.print(blackSurface.surface);
                    printWriter.print(" left=");
                    printWriter.print(blackSurface.left);
                    printWriter.print(" top=");
                    printWriter.println(blackSurface.top);
                    i++;
                }
            }
            printWriter.print(str2);
            printWriter.print("mCurRotation=");
            printWriter.print(screenRotationAnimation.mCurRotation);
            printWriter.print(" mOriginalRotation=");
            BroadcastStats$$ExternalSyntheticOutline0.m(
                    screenRotationAnimation.mOriginalRotation,
                    printWriter,
                    str2,
                    "mOriginalWidth=");
            printWriter.print(screenRotationAnimation.mOriginalWidth);
            printWriter.print(" mOriginalHeight=");
            BroadcastStats$$ExternalSyntheticOutline0.m(
                    screenRotationAnimation.mOriginalHeight, printWriter, str2, "mStarted=");
            printWriter.print(screenRotationAnimation.mStarted);
            printWriter.print(" mAnimRunning=");
            printWriter.print(false);
            printWriter.print(" mFinishAnimReady=");
            printWriter.print(screenRotationAnimation.mFinishAnimReady);
            printWriter.print(" mFinishAnimStartTime=");
            ActivityManagerConstants$$ExternalSyntheticOutline0.m(
                    screenRotationAnimation.mFinishAnimStartTime,
                    printWriter,
                    str2,
                    "mRotateExitAnimation=");
            printWriter.print(screenRotationAnimation.mRotateExitAnimation);
            printWriter.print(" ");
            screenRotationAnimation.mRotateExitTransformation.printShortString(printWriter);
            printWriter.println();
            printWriter.print(str2);
            printWriter.print("mRotateEnterAnimation=");
            printWriter.print(screenRotationAnimation.mRotateEnterAnimation);
            printWriter.print(" ");
            screenRotationAnimation.mRotateEnterTransformation.printShortString(printWriter);
            printWriter.println();
            printWriter.print(str2);
            printWriter.print("mSnapshotInitialMatrix=");
            screenRotationAnimation.mSnapshotInitialMatrix.dump(printWriter);
            printWriter.println();
        } else if (z) {
            printWriter.println("  no ScreenRotationAnimation ");
        }
        printWriter.println();
        Task task2 = getDefaultTaskDisplayArea().mRootHomeTask;
        if (task2 != null) {
            StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(str, "rootHomeTask=");
            m3.append(task2.getName());
            printWriter.println(m3.toString());
        }
        Task task3 = getDefaultTaskDisplayArea().mRootPinnedTask;
        if (task3 != null) {
            StringBuilder m4 = Preconditions$$ExternalSyntheticOutline0.m(str, "rootPinnedTask=");
            m4.append(task3.getName());
            printWriter.println(m4.toString());
        }
        Task rootTask = getDefaultTaskDisplayArea().getRootTask(0, 3);
        if (rootTask != null) {
            StringBuilder m5 = Preconditions$$ExternalSyntheticOutline0.m(str, "rootRecentsTask=");
            m5.append(rootTask.getName());
            printWriter.println(m5.toString());
        }
        Task rootTask2 = getRootTask(0, 5);
        if (rootTask2 != null) {
            StringBuilder m6 = Preconditions$$ExternalSyntheticOutline0.m(str, "rootDreamTask=");
            m6.append(rootTask2.getName());
            printWriter.println(m6.toString());
        }
        printWriter.println();
        PinnedTaskController pinnedTaskController = this.mPinnedTaskController;
        pinnedTaskController.getClass();
        printWriter.println(str + "PinnedTaskController");
        if (pinnedTaskController.mDeferOrientationChanging) {
            printWriter.println(str + "  mDeferOrientationChanging=true");
        }
        if (pinnedTaskController.mFreezingTaskConfig) {
            printWriter.println(str + "  mFreezingTaskConfig=true");
        }
        if (pinnedTaskController.mDestRotatedBounds != null) {
            StringBuilder m7 = Preconditions$$ExternalSyntheticOutline0.m(str, "  mPendingBounds=");
            m7.append(pinnedTaskController.mDestRotatedBounds);
            printWriter.println(m7.toString());
        }
        if (pinnedTaskController.mPipTransaction != null) {
            StringBuilder m8 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "  mPipTransaction=");
            m8.append(pinnedTaskController.mPipTransaction);
            printWriter.println(m8.toString());
        }
        StringBuilder m9 =
                MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                Preconditions$$ExternalSyntheticOutline0.m(str, "  mIsImeShowing="),
                                pinnedTaskController.mIsImeShowing,
                                printWriter,
                                str,
                                "  mImeHeight="),
                        pinnedTaskController.mImeHeight,
                        printWriter,
                        str,
                        "  mMinAspectRatio=");
        m9.append(pinnedTaskController.mMinAspectRatio);
        printWriter.println(m9.toString());
        printWriter.println(str + "  mMaxAspectRatio=" + pinnedTaskController.mMaxAspectRatio);
        Task task4 = getDefaultTaskDisplayArea().mRootMainStageTask;
        if (task4 != null) {
            StringBuilder m10 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "rootMainStageTask=");
            m10.append(task4.getName());
            printWriter.println(m10.toString());
        }
        Task task5 = getDefaultTaskDisplayArea().mRootSideStageTask;
        if (task5 != null) {
            StringBuilder m11 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "rootSideStageTask=");
            m11.append(task5.getName());
            printWriter.println(m11.toString());
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER
                && (task = getDefaultTaskDisplayArea().mRootCellStageTask) != null) {
            StringBuilder m12 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, "rootCellStageTask=");
            m12.append(task.getName());
            printWriter.println(m12.toString());
        }
        printWriter.println();
        DisplayFrames displayFrames = this.mDisplayFrames;
        displayFrames.getClass();
        printWriter.println(
                str
                        + "DisplayFrames w="
                        + displayFrames.mWidth
                        + " h="
                        + displayFrames.mHeight
                        + " r="
                        + displayFrames.mRotation);
        printWriter.println();
        DisplayPolicy displayPolicy = this.mDisplayPolicy;
        displayPolicy.getClass();
        printWriter.print(str);
        printWriter.println("DisplayPolicy");
        String str5 = str + "  ";
        String str6 = str5 + "  ";
        printWriter.print(str5);
        printWriter.print("mCarDockEnablesAccelerometer=");
        printWriter.print(displayPolicy.mCarDockEnablesAccelerometer);
        printWriter.print(" mDeskDockEnablesAccelerometer=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, str5, "mDockMode=", displayPolicy.mDeskDockEnablesAccelerometer);
        printWriter.print(Intent.dockStateToString(displayPolicy.mDockMode));
        printWriter.print(" mLidState=");
        int i2 = displayPolicy.mLidState;
        ProcessList$$ExternalSyntheticOutline0.m(
                printWriter,
                i2 != -1
                        ? i2 != 0 ? i2 != 1 ? Integer.toString(i2) : "LID_OPEN" : "LID_CLOSED"
                        : "LID_ABSENT",
                str5,
                "mAwake=");
        printWriter.print(displayPolicy.mAwake);
        printWriter.print(" mScreenOnEarly=");
        printWriter.print(displayPolicy.mScreenOnEarly);
        printWriter.print(" mScreenOnFully=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, str5, "mKeyguardDrawComplete=", displayPolicy.mScreenOnFully);
        printWriter.print(displayPolicy.mKeyguardDrawComplete);
        printWriter.print(" mWindowManagerDrawComplete=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, str5, "mHdmiPlugged=", displayPolicy.mWindowManagerDrawComplete);
        printWriter.println(displayPolicy.mHdmiPlugged);
        if (displayPolicy.mLastDisableFlags != 0) {
            printWriter.print(str5);
            printWriter.print("mLastDisableFlags=0x");
            printWriter.println(Integer.toHexString(displayPolicy.mLastDisableFlags));
        }
        if (displayPolicy.mLastAppearance != 0) {
            printWriter.print(str5);
            printWriter.print("mLastAppearance=");
            printWriter.println(
                    ViewDebug.flagsToString(
                            InsetsFlags.class, "appearance", displayPolicy.mLastAppearance));
        }
        if (displayPolicy.mLastBehavior != 0) {
            printWriter.print(str5);
            printWriter.print("mLastBehavior=");
            printWriter.println(
                    ViewDebug.flagsToString(
                            InsetsFlags.class, "behavior", displayPolicy.mLastBehavior));
        }
        printWriter.print(str5);
        printWriter.print("mShowingDream=");
        printWriter.print(displayPolicy.mShowingDream);
        printWriter.print(" mDreamingLockscreen=");
        printWriter.println(displayPolicy.mDreamingLockscreen);
        if (displayPolicy.mStatusBar != null) {
            printWriter.print(str5);
            printWriter.print("mStatusBar=");
            printWriter.println(displayPolicy.mStatusBar);
        }
        if (displayPolicy.mNotificationShade != null) {
            printWriter.print(str5);
            printWriter.print("mExpandedPanel=");
            printWriter.println(displayPolicy.mNotificationShade);
        }
        printWriter.print(str5);
        printWriter.print("isKeyguardShowing=");
        printWriter.println(
                ((PhoneWindowManager) displayPolicy.mService.mPolicy).isKeyguardShowing());
        if (displayPolicy.mNavigationBar != null) {
            printWriter.print(str5);
            printWriter.print("mNavigationBar=");
            printWriter.println(displayPolicy.mNavigationBar);
            printWriter.print(str5);
            printWriter.print("mNavBarOpacityMode=");
            BroadcastStats$$ExternalSyntheticOutline0.m(
                    displayPolicy.mNavBarOpacityMode, printWriter, str5, "mNavigationBarCanMove=");
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                    printWriter,
                    str5,
                    "mNavigationBarPosition=",
                    displayPolicy.mNavigationBarCanMove);
            printWriter.println(displayPolicy.mNavigationBarPosition);
        }
        WindowState windowState = displayPolicy.mStatusBar;
        WindowState windowState2 = displayPolicy.mDefaultStatusBar;
        if (windowState != windowState2 && windowState2 != null) {
            printWriter.print(str5);
            printWriter.print("mDefaultStatusBar=");
            printWriter.println(displayPolicy.mDefaultStatusBar);
        }
        WindowState windowState3 = displayPolicy.mNotificationShade;
        WindowState windowState4 = displayPolicy.mDefaultNotificationShade;
        if (windowState3 != windowState4 && windowState4 != null) {
            printWriter.print(str5);
            printWriter.print("mDefaultNotificationShade=");
            printWriter.println(displayPolicy.mDefaultNotificationShade);
        }
        WindowState windowState5 = displayPolicy.mNavigationBar;
        WindowState windowState6 = displayPolicy.mDefaultNavigationBar;
        if (windowState5 != windowState6 && windowState6 != null) {
            printWriter.print(str5);
            printWriter.print("mDefaultNavigationBar=");
            printWriter.println(displayPolicy.mDefaultNavigationBar);
        }
        if (displayPolicy.shouldKeepSystemUiControllingWindow()) {
            printWriter.print(str5);
            printWriter.print("shouldKeepSystemUiControllingWindow=true");
        }
        if (displayPolicy.mLeftGestureHost != null) {
            printWriter.print(str5);
            printWriter.print("mLeftGestureHost=");
            printWriter.println(displayPolicy.mLeftGestureHost);
        }
        if (displayPolicy.mTopGestureHost != null) {
            printWriter.print(str5);
            printWriter.print("mTopGestureHost=");
            printWriter.println(displayPolicy.mTopGestureHost);
        }
        if (displayPolicy.mRightGestureHost != null) {
            printWriter.print(str5);
            printWriter.print("mRightGestureHost=");
            printWriter.println(displayPolicy.mRightGestureHost);
        }
        if (displayPolicy.mBottomGestureHost != null) {
            printWriter.print(str5);
            printWriter.print("mBottomGestureHost=");
            printWriter.println(displayPolicy.mBottomGestureHost);
        }
        if (displayPolicy.mFocusedWindow != null) {
            printWriter.print(str5);
            printWriter.print("mFocusedWindow=");
            printWriter.println(displayPolicy.mFocusedWindow);
        }
        if (displayPolicy.mTopFullscreenOpaqueWindowState != null) {
            printWriter.print(str5);
            printWriter.print("mTopFullscreenOpaqueWindowState=");
            printWriter.println(displayPolicy.mTopFullscreenOpaqueWindowState);
        }
        if (!displayPolicy.mSystemBarColorApps.isEmpty()) {
            printWriter.print(str5);
            printWriter.print("mSystemBarColorApps=");
            printWriter.println(displayPolicy.mSystemBarColorApps);
        }
        if (!displayPolicy.mRelaunchingSystemBarColorApps.isEmpty()) {
            printWriter.print(str5);
            printWriter.print("mRelaunchingSystemBarColorApps=");
            printWriter.println(displayPolicy.mRelaunchingSystemBarColorApps);
        }
        if (displayPolicy.mNavBarColorWindowCandidate != null) {
            printWriter.print(str5);
            printWriter.print("mNavBarColorWindowCandidate=");
            printWriter.println(displayPolicy.mNavBarColorWindowCandidate);
        }
        if (displayPolicy.mNavBarBackgroundWindowCandidate != null) {
            printWriter.print(str5);
            printWriter.print("mNavBarBackgroundWindowCandidate=");
            printWriter.println(displayPolicy.mNavBarBackgroundWindowCandidate);
        }
        if (displayPolicy.mLastStatusBarAppearanceRegions != null) {
            printWriter.print(str5);
            printWriter.println("mLastStatusBarAppearanceRegions=");
            for (int length = displayPolicy.mLastStatusBarAppearanceRegions.length - 1;
                    length >= 0;
                    length--) {
                printWriter.print(str6);
                printWriter.println(displayPolicy.mLastStatusBarAppearanceRegions[length]);
            }
        }
        if (displayPolicy.mLastLetterboxDetails != null) {
            printWriter.print(str5);
            printWriter.println("mLastLetterboxDetails=");
            for (int length2 = displayPolicy.mLastLetterboxDetails.length - 1;
                    length2 >= 0;
                    length2--) {
                printWriter.print(str6);
                printWriter.println(displayPolicy.mLastLetterboxDetails[length2]);
            }
        }
        if (!displayPolicy.mStatusBarBackgroundWindows.isEmpty()) {
            printWriter.print(str5);
            printWriter.println("mStatusBarBackgroundWindows=");
            for (int size = displayPolicy.mStatusBarBackgroundWindows.size() - 1;
                    size >= 0;
                    size--) {
                Object obj = (WindowState) displayPolicy.mStatusBarBackgroundWindows.get(size);
                printWriter.print(str6);
                printWriter.println(obj);
            }
        }
        printWriter.print(str5);
        printWriter.print("mTopIsFullscreen=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter, str5, "mImeInsetsConsumed=", displayPolicy.mTopIsFullscreen);
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter,
                str5,
                "mForceShowNavigationBarEnabled=",
                displayPolicy.mImeInsetsConsumed);
        printWriter.print(displayPolicy.mForceShowNavigationBarEnabled);
        printWriter.print(" mAllowLockscreenWhenOn=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                printWriter,
                str5,
                "mRemoteInsetsControllerControlsSystemBars=",
                displayPolicy.mAllowLockscreenWhenOn);
        printWriter.println(displayPolicy.mRemoteInsetsControllerControlsSystemBars);
        printWriter.print(str5);
        printWriter.println("mDecorInsetsInfo:");
        displayPolicy.mDecorInsets.dump(str6, printWriter);
        if (displayPolicy.mCachedDecorInsets != null) {
            printWriter.print(str5);
            printWriter.println("mCachedDecorInsets:");
            displayPolicy.mCachedDecorInsets.mDecorInsets.dump(str6, printWriter);
        }
        if (!ViewRootImpl.CLIENT_TRANSIENT) {
            SystemGesturesPointerEventListener systemGesturesPointerEventListener =
                    displayPolicy.mSystemGestures;
            systemGesturesPointerEventListener.getClass();
            String str7 = str5 + "  ";
            printWriter.println(str5 + "SystemGestures:");
            printWriter.print(str7);
            printWriter.print("mDisplayCutoutTouchableRegionSize=");
            BroadcastStats$$ExternalSyntheticOutline0.m(
                    systemGesturesPointerEventListener.mDisplayCutoutTouchableRegionSize,
                    printWriter,
                    str7,
                    "mSwipeStartThreshold=");
            printWriter.println(systemGesturesPointerEventListener.mSwipeStartThreshold);
            printWriter.print(str7);
            printWriter.print("mSwipeDistanceThreshold=");
            printWriter.println(systemGesturesPointerEventListener.mSwipeDistanceThreshold);
        }
        Context context = displayPolicy.mUiContext;
        if (context != null && context.getResources() != null) {
            printWriter.print(str5);
            printWriter.println("UiContextResourcesConfig=");
            printWriter.print(str5);
            printWriter.println(displayPolicy.mUiContext.getResources().getConfiguration());
            printWriter.print(str5);
            printWriter.println("UiContextResourcesMetrics=");
            printWriter.print(str5);
            printWriter.println(displayPolicy.mUiContext.getResources().getDisplayMetrics());
        }
        printWriter.println();
        DisplayPolicyExt displayPolicyExt = displayPolicy.mExt;
        displayPolicyExt.getClass();
        printWriter.print(str5);
        printWriter.println("DisplayPolicyExt");
        String str8 = str5 + "  ";
        if (displayPolicyExt.mPayHandlerWin != null) {
            printWriter.print(str8);
            printWriter.print("mPayHandlerWin=");
            printWriter.print(displayPolicyExt.mPayHandlerWin);
            printWriter.print(" mLastPayHandlerVisible=");
            printWriter.print(displayPolicyExt.mLastPayHandlerVisible);
            printWriter.print(" mLastPayHandlerFrame=");
            printWriter.println(displayPolicyExt.mLastPayHandlerFrame);
            printWriter.print("mNavigationMode=");
            printWriter.println(displayPolicyExt.mNavigationMode);
        }
        OneHandOpPolicy oneHandOpPolicy = displayPolicyExt.mOneHandOpPolicy;
        if (oneHandOpPolicy != null) {
            printWriter.print(str8);
            printWriter.print("mIsOneHandOpEnabled=");
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                    printWriter, str8, "mHasOneHandOpSpec=", oneHandOpPolicy.mIsOneHandOpEnabled);
            AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                    printWriter, str8, "mReasonToStart=", oneHandOpPolicy.mHasOneHandOpSpec);
            printWriter.println(
                    OneHandOpPolicy.startReasonToString(oneHandOpPolicy.mReasonToStart));
            printWriter.print(str8);
            printWriter.print("mOneHandOpController=");
            printWriter.println((Object) null);
            printWriter.print(str8);
            printWriter.print("mOneHandOpHandler=");
            printWriter.println((Object) null);
        }
        CoverPolicy coverPolicy = displayPolicyExt.mCoverPolicy;
        if (coverPolicy != null) {
            printWriter.print(str8);
            printWriter.println("CoverPolicy");
            String str9 = str8 + "  ";
            if (coverPolicy.mCoverWindow != null) {
                printWriter.print(str9);
                printWriter.print("mCoverWindow=");
                printWriter.println(coverPolicy.mCoverWindow);
            }
            if (coverPolicy.mHideSViewCoverWindow != null) {
                printWriter.print(str9);
                printWriter.print("mHideSViewCoverWindow=");
                printWriter.println(coverPolicy.mHideSViewCoverWindow);
            }
            printWriter.print(str9);
            printWriter.print("mLastCoverAppCovered=");
            printWriter.println(coverPolicy.mLastCoverAppCovered);
        }
        if (displayPolicyExt.mFakeFocusedWindow != null) {
            printWriter.print(str8);
            printWriter.print("mFakeFocusedWindow=");
            printWriter.println(displayPolicyExt.mFakeFocusedWindow);
        }
        if (CoreRune.FW_VRR_POLICY) {
            printWriter.println();
            RefreshRatePolicy refreshRatePolicy = displayPolicy.mRefreshRatePolicy;
            refreshRatePolicy.getClass();
            printWriter.print(str5);
            printWriter.println("RefreshRatePolicy");
            final String str10 = str5 + "  ";
            printWriter.print(str10);
            printWriter.print("mLowRefreshRateMode=");
            printWriter.println(refreshRatePolicy.mLowRefreshRateMode);
            RefreshRatePolicy.PackageRefreshRate packageRefreshRate =
                    refreshRatePolicy.mNonHighRefreshRatePackages;
            packageRefreshRate.getClass();
            printWriter.print(str10);
            printWriter.println("mNonHighRefreshRatePackages:");
            packageRefreshRate.mPackages.forEach(
                    new BiConsumer() { // from class:
                                       // com.android.server.wm.RefreshRatePolicy$PackageRefreshRate$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj2, Object obj3) {
                            PrintWriter printWriter2 = printWriter;
                            printWriter2.print(str10 + "  ");
                            printWriter2.println(
                                    ((String) obj2)
                                            + ":"
                                            + ((SurfaceControl.RefreshRateRange) obj3).toString());
                        }
                    });
            if (CoreRune.FW_VRR_SYSTEM_HISTORY) {
                RefreshRatePolicyLogger refreshRatePolicyLogger =
                        refreshRatePolicy.mRefreshRatePolicyLogger;
                refreshRatePolicyLogger.getClass();
                printWriter.print(str10);
                printWriter.println("RefreshRatePolicy History");
                refreshRatePolicyLogger.mRefreshRateHistories.forEach(
                        new Consumer() { // from class:
                                         // com.android.server.wm.RefreshRatePolicyLogger$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj2) {
                                PrintWriter printWriter2 = printWriter;
                                RefreshRatePolicyLogger.RefreshRateHistory refreshRateHistory =
                                        (RefreshRatePolicyLogger.RefreshRateHistory) obj2;
                                ProxyManager$$ExternalSyntheticOutline0.m(
                                        printWriter2,
                                        refreshRateHistory.mTag,
                                        " >>",
                                        new StringBuilder("<< PreferredModeHistory_"));
                                refreshRateHistory.mHistory.dump(printWriter2);
                            }
                        });
            }
        }
        printWriter.println();
        this.mDisplayRotation.dump(str, printWriter);
        printWriter.println();
        this.mInputMonitor.dump(printWriter);
        printWriter.println();
        InsetsStateController insetsStateController = this.mInsetsStateController;
        insetsStateController.getClass();
        printWriter.println(str + "WindowInsetsStateController");
        String str11 = str + "  ";
        insetsStateController.mState.dump(str11, printWriter);
        printWriter.println(str11 + "Control map:");
        for (int size2 = insetsStateController.mControlTargetProvidersMap.size() + (-1);
                size2 >= 0;
                size2--) {
            InsetsControlTarget insetsControlTarget =
                    (InsetsControlTarget)
                            insetsStateController.mControlTargetProvidersMap.keyAt(size2);
            printWriter.print(str11 + "  ");
            printWriter.print(insetsControlTarget);
            printWriter.println(":");
            ArrayList arrayList =
                    (ArrayList) insetsStateController.mControlTargetProvidersMap.valueAt(size2);
            for (int size3 = arrayList.size() - 1; size3 >= 0; size3--) {
                InsetsSourceProvider insetsSourceProvider =
                        (InsetsSourceProvider) arrayList.get(size3);
                if (insetsSourceProvider != null) {
                    printWriter.print(str11 + "    ");
                    if (insetsControlTarget == insetsSourceProvider.mFakeControlTarget) {
                        printWriter.print("(fake) ");
                    }
                    printWriter.println(insetsSourceProvider.getControl(insetsControlTarget));
                }
            }
        }
        if (insetsStateController.mControlTargetProvidersMap.isEmpty()) {
            printWriter.print(str11 + "  none");
        }
        printWriter.println(str11 + "InsetsSourceProviders:");
        for (int size4 = insetsStateController.mProviders.size() - 1; size4 >= 0; size4 += -1) {
            ((InsetsSourceProvider) insetsStateController.mProviders.valueAt(size4))
                    .dump(printWriter, str11 + "  ");
        }
        if (insetsStateController.mForcedConsumingTypes != 0) {
            StringBuilder m13 =
                    Preconditions$$ExternalSyntheticOutline0.m(str11, "mForcedConsumingTypes=");
            m13.append(WindowInsets.Type.toString(insetsStateController.mForcedConsumingTypes));
            printWriter.println(m13.toString());
        }
        InsetsPolicy insetsPolicy = this.mInsetsPolicy;
        insetsPolicy.getClass();
        printWriter.println(str + "InsetsPolicy");
        String str12 = str + "  ";
        StringBuilder m14 = Preconditions$$ExternalSyntheticOutline0.m(str12, "status: ");
        m14.append(StatusBarManager.windowStateToString(insetsPolicy.mStatusBar.mState));
        printWriter.println(m14.toString());
        printWriter.println(
                str12
                        + "nav: "
                        + StatusBarManager.windowStateToString(insetsPolicy.mNavBar.mState));
        if (insetsPolicy.mShowingTransientTypes != 0) {
            StringBuilder m15 =
                    Preconditions$$ExternalSyntheticOutline0.m(str12, "mShowingTransientTypes=");
            m15.append(WindowInsets.Type.toString(insetsPolicy.mShowingTransientTypes));
            printWriter.println(m15.toString());
        }
        if (insetsPolicy.mForcedShowingTypes != 0) {
            StringBuilder m16 =
                    Preconditions$$ExternalSyntheticOutline0.m(str12, "mForcedShowingTypes=");
            m16.append(WindowInsets.Type.toString(insetsPolicy.mForcedShowingTypes));
            printWriter.println(m16.toString());
        }
        DisplayWindowPolicyControllerHelper displayWindowPolicyControllerHelper = this.mDwpcHelper;
        if (displayWindowPolicyControllerHelper.mDisplayWindowPolicyController != null) {
            printWriter.println();
            displayWindowPolicyControllerHelper.mDisplayWindowPolicyController.dump(
                    str, printWriter);
        }
        printWriter.println();
        if (isRemoteAppDisplay()) {
            printWriter.println();
            printWriter.println(str + "isRemoteAppDisplay=true");
        }
        if (CoreRune.MT_DEX_SIZE_COMPAT_MODE) {
            DexSizeCompatController dexSizeCompatController =
                    DexSizeCompatController.LazyHolder.sInstance;
            dexSizeCompatController.getClass();
            TaskDisplayArea defaultTaskDisplayArea = getDefaultTaskDisplayArea();
            if (defaultTaskDisplayArea != null) {
                SizeCompatPolicyManager sizeCompatPolicyManager =
                        SizeCompatPolicyManager.LazyHolder.sManager;
                if (((Integer)
                                        sizeCompatPolicyManager.mDisplayIdsForActiveMode.get(
                                                defaultTaskDisplayArea.mDisplayContent.mDisplayId,
                                                0))
                                .intValue()
                        == 1) {
                    printWriter.println("  DEX SIZE COMPAT CONTROLLER");
                    printWriter.print("    ");
                    printWriter.print("DisplayId=" + this.mDisplayId);
                    printWriter.print(", DefaultScale=" + dexSizeCompatController.mDefaultScale);
                    printWriter.print(
                            ", AspectRatioScale=" + dexSizeCompatController.mAspectRatioScale);
                    if (sizeCompatPolicyManager.mLaunchPolicy == 2) {
                        printWriter.print(", ResizableAllowed=true");
                    }
                    printWriter.println();
                    printWriter.println();
                }
            }
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
              // com.android.server.wm.ConfigurationContainer
    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        int i2;
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268053L, i);
        protoOutputStream.write(1120986464258L, this.mDisplayId);
        protoOutputStream.write(1120986464265L, this.mBaseDisplayDensity);
        this.mDisplayInfo.dumpDebug(protoOutputStream, 1146756268042L);
        DisplayRotation displayRotation = this.mDisplayRotation;
        displayRotation.getClass();
        long start2 = protoOutputStream.start(1146756268065L);
        protoOutputStream.write(1120986464257L, displayRotation.mRotation);
        protoOutputStream.write(1133871366146L, displayRotation.isRotationFrozen());
        protoOutputStream.write(1120986464259L, displayRotation.mUserRotation);
        protoOutputStream.write(1120986464260L, displayRotation.mFixedToUserRotation);
        protoOutputStream.write(1120986464261L, displayRotation.mLastOrientation);
        protoOutputStream.write(1133871366150L, displayRotation.isFixedToUserRotation());
        protoOutputStream.end(start2);
        ScreenRotationAnimation screenRotationAnimation = this.mScreenRotationAnimation;
        if (screenRotationAnimation != null) {
            long start3 = protoOutputStream.start(1146756268044L);
            protoOutputStream.write(1133871366145L, screenRotationAnimation.mStarted);
            protoOutputStream.write(1133871366146L, false);
            protoOutputStream.end(start3);
        }
        this.mDisplayFrames.getClass();
        protoOutputStream.end(protoOutputStream.start(1146756268045L));
        protoOutputStream.write(1120986464295L, this.mMinSizeOfResizeableTaskDp);
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            TransitionController transitionController = this.mTransitionController;
            transitionController.getClass();
            long start4 = protoOutputStream.start(1146756268048L);
            if (transitionController.mPlayingTransitions.isEmpty()) {
                Transition transition = transitionController.mCollectingTransition;
                i2 =
                        ((transition == null
                                                || !transition.isCollecting()
                                                || transition.mSyncId < 0)
                                        && !(transitionController.mSyncEngine.mPendingSyncSets
                                                        .isEmpty()
                                                ^ true))
                                ? 0
                                : 1;
            } else {
                i2 = 2;
            }
            protoOutputStream.write(1159641169921L, i2);
            protoOutputStream.end(start4);
        } else {
            AppTransition appTransition = this.mAppTransition;
            appTransition.getClass();
            long start5 = protoOutputStream.start(1146756268048L);
            protoOutputStream.write(1159641169921L, appTransition.mAppTransitionState);
            protoOutputStream.write(1159641169922L, appTransition.mLastUsedAppTransition);
            protoOutputStream.end(start5);
        }
        ActivityRecord activityRecord = this.mFocusedApp;
        if (activityRecord != null) {
            protoOutputStream.write(1138166333455L, activityRecord.shortComponentName);
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
            protoOutputStream.write(1120986464279L, focusedRootTask.getRootTask().mTaskId);
            ActivityRecord focusedActivity = focusedRootTask.getDisplayArea().getFocusedActivity();
            if (focusedActivity != null) {
                focusedActivity.writeIdentifierToProto(protoOutputStream, 1146756268056L);
            }
        } else {
            protoOutputStream.write(1120986464279L, -1);
        }
        protoOutputStream.write(1133871366170L, isReady());
        protoOutputStream.write(1133871366180L, this.mSleeping);
        for (int i3 = 0; i3 < this.mAllSleepTokens.size(); i3++) {
            protoOutputStream.write(
                    2237677961253L,
                    ((RootWindowContainer.SleepToken) this.mAllSleepTokens.get(i3)).mTag);
        }
        WindowState windowState = this.mImeLayeringTarget;
        if (windowState != null) {
            windowState.dumpDebug(protoOutputStream, 1146756268059L, i);
        }
        InputTarget inputTarget = this.mImeInputTarget;
        if (inputTarget != null) {
            inputTarget.dumpProto(i, protoOutputStream);
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
            for (int size3 = insetsStateController.mProviders.size() - 1; size3 >= 0; size3--) {
                InsetsSourceProvider insetsSourceProvider =
                        (InsetsSourceProvider) insetsStateController.mProviders.valueAt(size3);
                insetsSourceProvider.dumpDebug(
                        protoOutputStream,
                        insetsSourceProvider.mSource.getType() == WindowInsets.Type.ime()
                                ? 1146756268063L
                                : 2246267895843L,
                        i);
            }
        }
        protoOutputStream.write(1120986464290L, getImePolicy());
        ArraySet arraySet = new ArraySet();
        forAllWindows(
                (ToBooleanFunction)
                        new DisplayContent$$ExternalSyntheticLambda2(
                                this.mWmService.mRecentsAnimationController,
                                arraySet,
                                arraySet,
                                new Matrix(),
                                new float[9]),
                true);
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            ((Rect) it.next()).dumpDebug(protoOutputStream, 2246267895846L);
        }
        protoOutputStream.end(start);
    }

    public final void enableHighFrameRate(boolean z) {
        if (com.android.window.flags.Flags.explicitRefreshRateHints()) {
            if (z) {
                if (this.mHighFrameRateSession == null) {
                    this.mHighFrameRateSession =
                            this.mWmService.mSystemPerformanceHinter.createSession(
                                    2, this.mDisplayId, "WindowAnimation");
                }
                this.mHighFrameRateSession.start();
            } else {
                SystemPerformanceHinter.HighPerfSession highPerfSession =
                        this.mHighFrameRateSession;
                if (highPerfSession != null) {
                    highPerfSession.close();
                }
            }
        }
    }

    public final void enableHighPerfTransition(boolean z) {
        if (this.mWmService.mSupportsHighPerfTransitions) {
            if (!com.android.window.flags.Flags.explicitRefreshRateHints()) {
                if (z) {
                    getPendingTransaction().setEarlyWakeupStart();
                    return;
                } else {
                    getPendingTransaction().setEarlyWakeupEnd();
                    return;
                }
            }
            if (z) {
                if (this.mTransitionPrefSession == null) {
                    this.mTransitionPrefSession =
                            this.mWmService.mSystemPerformanceHinter.createSession(
                                    3, this.mDisplayId, "Transition");
                }
                this.mTransitionPrefSession.start();
            } else {
                SystemPerformanceHinter.HighPerfSession highPerfSession =
                        this.mTransitionPrefSession;
                if (highPerfSession != null) {
                    highPerfSession.close();
                }
            }
        }
    }

    public final void ensureActivitiesVisible(
            final boolean z, final ActivityRecord activityRecord) {
        if (this.mInEnsureActivitiesVisible) {
            return;
        }
        this.mAtmService.mTaskSupervisor.beginActivityVisibilityUpdate(this);
        try {
            this.mInEnsureActivitiesVisible = true;
            forAllRootTasks(
                    new Consumer() { // from class:
                                     // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda28
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Task task = (Task) obj;
                            task.ensureActivitiesVisible(z, activityRecord);
                        }
                    });
            if (this.mTransitionController.useShellTransitionsRotation()
                    && this.mTransitionController.isCollecting()) {
                WallpaperController wallpaperController = this.mWallpaperController;
                if (wallpaperController.mWallpaperTarget != null) {
                    wallpaperController.adjustWallpaperWindows();
                }
            }
        } finally {
            this.mAtmService.mTaskSupervisor.endActivityVisibilityUpdate();
            this.mInEnsureActivitiesVisible = false;
        }
    }

    public final void executeAppTransition() {
        this.mTransitionController.setReady(this, true);
        if (this.mAppTransition.isTransitionSet()) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_APP_TRANSITIONS_enabled[3]) {
                ProtoLogImpl_54989576.w(
                        ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS,
                        7019634211809476510L,
                        4,
                        null,
                        String.valueOf(this.mAppTransition),
                        Long.valueOf(this.mDisplayId),
                        String.valueOf(Debug.getCallers(5)));
            }
            AppTransition appTransition = this.mAppTransition;
            appTransition.mAppTransitionState = 1;
            appTransition.updateBooster();
            IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture =
                    appTransition.mNextAppTransitionAnimationsSpecsFuture;
            if (iAppTransitionAnimationSpecsFuture != null) {
                appTransition.mNextAppTransitionAnimationsSpecsPending = true;
                appTransition.mNextAppTransitionAnimationsSpecsFuture = null;
                appTransition.mDefaultExecutor.execute(
                        new AppTransition$$ExternalSyntheticLambda0(
                                appTransition, iAppTransitionAnimationSpecsFuture));
            }
            this.mWmService.mWindowPlacerLocked.requestTraversal();
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final boolean fillsParent() {
        return true;
    }

    public final DisplayArea findAreaForWindowType(int i, Bundle bundle, boolean z, boolean z2) {
        return (i < 1 || i > 99)
                ? (i == 2011 || i == 2012)
                        ? this.mImeWindowsContainer
                        : ((RootDisplayArea)
                                        ((DisplayAreaPolicyBuilder.Result) this.mDisplayAreaPolicy)
                                                .mSelectRootForWindowFunc.apply(
                                                        Integer.valueOf(i), bundle))
                                .findAreaForWindowTypeInLayer(i, z, z2)
                : (TaskDisplayArea)
                        ((DisplayAreaPolicyBuilder.Result) this.mDisplayAreaPolicy)
                                .mSelectTaskDisplayAreaFunc.apply(bundle);
    }

    public final WindowState findFocusedWindow() {
        this.mTmpWindow = null;
        forAllWindows((ToBooleanFunction) this.mFindFocusedWindow, true);
        WindowState windowState = this.mTmpWindow;
        if (windowState != null) {
            return windowState;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_LIGHT_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
                    -1123818872155982592L,
                    1,
                    null,
                    Long.valueOf(this.mDisplayId));
        }
        return null;
    }

    public final SurfaceControl[] findRoundedCornerOverlays() {
        ArrayList arrayList = new ArrayList();
        for (WindowToken windowToken : this.mTokenMap.values()) {
            if (windowToken.mRoundedCornerOverlay && windowToken.isVisible()) {
                arrayList.add(windowToken.mSurfaceControl);
            }
        }
        return (SurfaceControl[]) arrayList.toArray(new SurfaceControl[0]);
    }

    public final void finishAsyncRotation(WindowToken windowToken) {
        AsyncRotationController.Operation operation;
        AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        if (asyncRotationController != null) {
            if (!asyncRotationController.mIsStartTransactionCommitted) {
                AsyncRotationController.Operation operation2 =
                        (AsyncRotationController.Operation)
                                asyncRotationController.mTargetWindowTokens.get(windowToken);
                if (operation2 != null) {
                    Slog.d(
                            "AsyncRotation_WindowManager",
                            "Complete set pending " + windowToken.getTopChild());
                    operation2.mIsCompletionPending = true;
                    return;
                }
                return;
            }
            if (asyncRotationController.mTransitionOp == 1
                    && windowToken.mTransitionController.inTransition(
                            asyncRotationController.mSyncId)
                    && (operation =
                                    (AsyncRotationController.Operation)
                                            asyncRotationController.mTargetWindowTokens.get(
                                                    windowToken))
                            != null
                    && operation.mAction == 2) {
                Slog.d(
                        "AsyncRotation_WindowManager",
                        "Defer completion " + windowToken.getTopChild());
            } else if (asyncRotationController.mTargetWindowTokens.containsKey(windowToken)) {
                if (asyncRotationController.mHasScreenRotationAnimation
                        || asyncRotationController.mTransitionOp != 0) {
                    Slog.d(
                            "AsyncRotation_WindowManager",
                            "Complete directly " + windowToken.getTopChild());
                    asyncRotationController.finishOp(windowToken);
                    if (asyncRotationController.mTargetWindowTokens.isEmpty()) {
                        asyncRotationController.onAllCompleted();
                        this.mAsyncRotationController = null;
                    }
                }
            }
        }
    }

    public final void finishAsyncRotationIfPossible() {
        AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        if (asyncRotationController == null || this.mDisplayRotation.mSeamlessRotationCount > 0) {
            return;
        }
        for (int size = asyncRotationController.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            asyncRotationController.finishOp(
                    (WindowToken) asyncRotationController.mTargetWindowTokens.keyAt(size));
        }
        asyncRotationController.mTargetWindowTokens.clear();
        asyncRotationController.onAllCompleted();
        this.mAsyncRotationController = null;
    }

    public final boolean forceDesktopMode() {
        return (isRemoteAppDisplay()
                        || isAppCastingDisplay()
                        || this.mWmService.mExt.mExtraDisplayPolicy.isDisplayControlledByPolicy(
                                this.mDisplayId)
                        || !this.mWmService.mForceDesktopModeOnExternalDisplays
                        || this.isDefaultDisplay
                        || isPrivate())
                ? false
                : true;
    }

    public AsyncRotationController getAsyncRotationController() {
        return this.mAsyncRotationController;
    }

    public final TaskDisplayArea getDefaultTaskDisplayArea() {
        return ((DisplayAreaPolicyBuilder.Result) this.mDisplayAreaPolicy).mDefaultTaskDisplayArea;
    }

    public final Task getFocusedRootTask() {
        return (Task) getItemFromTaskDisplayAreas(new DisplayContent$$ExternalSyntheticLambda21());
    }

    public final InsetsControlTarget getImeFallback() {
        DisplayContent defaultDisplayContentLocked =
                this.mWmService.getDefaultDisplayContentLocked();
        WindowState windowState = defaultDisplayContentLocked.mDisplayPolicy.mStatusBar;
        return windowState != null
                ? windowState
                : defaultDisplayContentLocked.mRemoteInsetsControlTarget;
    }

    public final InsetsControlTarget getImeHostOrFallback(WindowState windowState) {
        return (windowState == null || windowState.getDisplayContent().getImePolicy() != 0)
                ? Flags.refactorInsetsController()
                        ? this.mWmService.getDefaultDisplayContentLocked()
                                .mRemoteInsetsControlTarget
                        : getImeFallback()
                : windowState;
    }

    public final int getImePolicy() {
        int intValue;
        if (!this.mDisplay.isTrusted()) {
            return 1;
        }
        DisplayWindowSettings displayWindowSettings = this.mWmService.mDisplayWindowSettings;
        displayWindowSettings.getClass();
        if (this.mDisplayId != 0) {
            DisplayWindowSettings$SettingsProvider$SettingsEntry settings =
                    displayWindowSettings.mSettingsProvider.getSettings(this.mDisplayInfo);
            if ((settings.mImePolicy != null || this.mDisplayId != 2) && !isRemoteAppDisplay()) {
                Integer num = settings.mImePolicy;
                intValue = num != null ? num.intValue() : 1;
                if (intValue == 1 || !forceDesktopMode()) {
                    return intValue;
                }
                return 0;
            }
        }
        intValue = 0;
        if (intValue == 1) {}
        return intValue;
    }

    public final InsetsControlTarget getImeTarget(int i) {
        if (i == 0) {
            return this.mImeLayeringTarget;
        }
        if (i != 2) {
            return null;
        }
        return this.mImeControlTarget;
    }

    public final int getInitialDisplayDensity() {
        int i;
        int i2 = this.mInitialDisplayDensity;
        int i3 = this.mMaxUiWidth;
        return (i3 <= 0 || (i = this.mInitialDisplayWidth) <= i3) ? i2 : (int) ((i2 * i3) / i);
    }

    public final int getInputMethodWindowVisibleHeight() {
        InsetsState insetsState = this.mInsetsStateController.mState;
        InsetsSource peekSource = insetsState.peekSource(InsetsSource.ID_IME);
        if (peekSource == null || !peekSource.isVisible()) {
            return 0;
        }
        Rect visibleFrame =
                peekSource.getVisibleFrame() != null
                        ? peekSource.getVisibleFrame()
                        : peekSource.getFrame();
        Rect rect = this.mTmpRect;
        rect.set(insetsState.getDisplayFrame());
        rect.inset(
                insetsState.calculateInsets(
                        rect,
                        WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(),
                        false));
        return rect.bottom - visibleFrame.top;
    }

    public final ScreenCapture.LayerCaptureArgs getLayerCaptureArgs(Set set) {
        if (!((PhoneWindowManager) this.mWmService.mPolicy).mDefaultDisplayPolicy.mScreenOnEarly) {
            Slog.i("WindowManager", "Attempted to take screenshot while display was off.");
            return null;
        }
        getBounds(this.mTmpRect);
        this.mTmpRect.offsetTo(0, 0);
        ScreenCapture.LayerCaptureArgs.Builder sourceCrop =
                new ScreenCapture.LayerCaptureArgs.Builder(getSurfaceControl())
                        .setSourceCrop(this.mTmpRect);
        if (!set.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            forAllWindows(
                    (Consumer) new DisplayContent$$ExternalSyntheticLambda11(0, set, arrayList),
                    true);
            if (!arrayList.isEmpty()) {
                sourceCrop.setExcludeLayers(
                        (SurfaceControl[]) arrayList.toArray(new SurfaceControl[0]));
            }
        }
        return sourceCrop.build();
    }

    public final int getMinimalTaskSizeDp() {
        Resources resources = this.mDisplayPolicy.mUiContext.getResources();
        TypedValue typedValue = new TypedValue();
        resources.getValue(R.dimen.floating_toolbar_vertical_margin, typedValue, true);
        int i = typedValue.data;
        int i2 = i & 15;
        if (typedValue.type == 5 && i2 == 1) {
            return (int) TypedValue.complexToFloat(i);
        }
        throw new IllegalArgumentException(
                "Resource ID #0x"
                        + Integer.toHexString(R.dimen.floating_toolbar_vertical_margin)
                        + " is not in valid type or unit");
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.ConfigurationContainer
    public final String getName() {
        StringBuilder sb = new StringBuilder("Display ");
        sb.append(this.mDisplayId);
        sb.append(" name=\"");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mDisplayInfo.name, "\"");
    }

    public final OneHandOpPolicy getOneHandOpPolicy() {
        return this.mDisplayPolicy.mExt.mOneHandOpPolicy;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0061 A[RETURN] */
    @Override // com.android.server.wm.WindowContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getOrientation() {
        /*
            Method dump skipped, instructions count: 598
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.wm.DisplayContent.getOrientation():int");
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final long getProtoFieldId() {
        return 1146756268035L;
    }

    @Override // com.android.server.wm.WindowContainer
    public final int getRelativeDisplayRotation() {
        return 0;
    }

    public final Task getRootTask(final int i, final int i2) {
        return (Task)
                getItemFromTaskDisplayAreas(
                        new Function() { // from class:
                                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda33
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ((TaskDisplayArea) obj).getRootTask(i, i2);
                            }
                        });
    }

    @Override // com.android.server.wm.WindowContainer
    public final SurfaceSession getSession() {
        return this.mSession;
    }

    @Override // com.android.server.wm.DisplayArea
    public final void getStableRect(Rect rect) {
        InsetsState insetsState = this.mDisplayContent.mInsetsStateController.mState;
        rect.set(insetsState.getDisplayFrame());
        rect.inset(insetsState.calculateInsets(rect, WindowInsets.Type.systemBars(), true));
    }

    public final Point getValidForcedSize(int i, int i2) {
        int max = Math.max(this.mInitialDisplayWidth, this.mInitialDisplayHeight) * 3;
        return new Point(Math.min(Math.max(i, 200), max), Math.min(Math.max(i2, 200), max));
    }

    public final WindowToken getWindowToken(IBinder iBinder) {
        return (WindowToken) this.mTokenMap.get(iBinder);
    }

    public final SurfaceControl getWindowingLayer() {
        RootDisplayArea rootDisplayArea =
                ((DisplayAreaPolicyBuilder.Result) this.mDisplayAreaPolicy).mRoot;
        if (rootDisplayArea.mFeatures.isEmpty()) {
            throw new IllegalStateException("There must be at least one feature.");
        }
        DisplayAreaPolicyBuilder.Feature feature =
                (DisplayAreaPolicyBuilder.Feature) rootDisplayArea.mFeatures.get(0);
        if (DisplayAreaPolicyBuilder.canBeWindowingLayer(feature.mId)) {
            List list = (List) rootDisplayArea.mFeatureToDisplayAreas.get(feature);
            if (list.size() == 1) {
                return ((DisplayArea) list.get(0)).mSurfaceControl;
            }
        }
        throw new IllegalStateException(
                "There must be exactly one DisplayArea at top for the"
                    + " FEATURE_WINDOWED_MAGNIFICATION or FEATURE_WINDOWING_LAYER");
    }

    public final void handleActivitySizeCompatModeIfNeeded(ActivityRecord activityRecord) {
        Task task = activityRecord.task;
        Task organizedTask = task != null ? task.getOrganizedTask() : null;
        if (organizedTask == null) {
            ((ArraySet) this.mActiveSizeCompatActivities).remove(activityRecord);
            return;
        }
        if (activityRecord.isState(ActivityRecord.State.RESUMED)
                && activityRecord.inSizeCompatMode()) {
            if (((ArraySet) this.mActiveSizeCompatActivities).add(activityRecord)) {
                organizedTask.dispatchTaskInfoChangedIfNeeded(true);
            }
        } else if (((ArraySet) this.mActiveSizeCompatActivities).remove(activityRecord)) {
            organizedTask.dispatchTaskInfoChangedIfNeeded(true);
        }
    }

    public final void handleAnimatingStoppedAndTransition() {
        AppTransition appTransition = this.mAppTransition;
        appTransition.mAppTransitionState = 0;
        appTransition.updateBooster();
        for (int size = ((ArrayList) this.mNoAnimationNotifyOnTransitionFinished).size() - 1;
                size >= 0;
                size--) {
            this.mAppTransition.notifyAppTransitionFinishedLocked(
                    (IBinder) ((ArrayList) this.mNoAnimationNotifyOnTransitionFinished).get(size));
        }
        ((ArrayList) this.mNoAnimationNotifyOnTransitionFinished).clear();
        WallpaperController wallpaperController = this.mWallpaperController;
        for (int size2 = wallpaperController.mWallpaperTokens.size() - 1; size2 >= 0; size2--) {
            WallpaperWindowToken wallpaperWindowToken =
                    (WallpaperWindowToken) wallpaperController.mWallpaperTokens.get(size2);
            if (!wallpaperWindowToken.isVisibleRequested()) {
                wallpaperWindowToken.commitVisibility(false);
            }
        }
        onAppTransitionDone();
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_WALLPAPER_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_WALLPAPER, -3219913508985161450L, 0, null, null);
        }
        computeImeTarget(true);
        this.mWallpaperMayChange = true;
        this.mWmService.mFocusMayChange = true;
        this.pendingLayoutChanges |= 1;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean handleCompleteDeferredRemoval() {
        boolean z = super.handleCompleteDeferredRemoval() || shouldDeferRemoval();
        if (z || !this.mDeferredRemoval) {
            return z;
        }
        removeImmediately();
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a4, code lost:

       if (r5.mTransitionController.hasTransientLaunch(r5) == false) goto L59;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0076, code lost:

       if (r5.mOpeningApps.contains(r6) != false) goto L44;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleTopActivityLaunchingInDifferentOrientation(
            com.android.server.wm.ActivityRecord r6,
            com.android.server.wm.ActivityRecord r7,
            boolean r8) {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayContent.handleTopActivityLaunchingInDifferentOrientation(com.android.server.wm.ActivityRecord,"
                    + " com.android.server.wm.ActivityRecord, boolean):boolean");
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final boolean handlesOrientationChangeFromDescendant(int i) {
        return (shouldIgnoreOrientationRequest(i) || this.mDisplayRotation.isFixedToUserRotation())
                ? false
                : true;
    }

    public final boolean hasAccess(int i) {
        if (!this.mDisplay.hasAccess(i)) {
            return false;
        }
        if (!this.mVisibleBackgroundUserEnabled || isPrivate()) {
            return true;
        }
        int userId = UserHandle.getUserId(i);
        return userId == 0 || this.mWmService.mUmInternal.isUserVisible(userId, this.mDisplayId);
    }

    public final boolean hasAlertWindowSurfaces() {
        for (int size = this.mWmService.mSessions.size() - 1; size >= 0; size--) {
            Session session = (Session) this.mWmService.mSessions.valueAt(size);
            for (int size2 = session.mAlertWindowSurfaces.size() - 1; size2 >= 0; size2--) {
                if (((WindowSurfaceController) session.mAlertWindowSurfaces.valueAt(size2))
                                .mAnimator.mWin.getDisplayContent()
                        == this) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean hasOneHandOpSpec() {
        OneHandOpPolicy oneHandOpPolicy = this.mDisplayPolicy.mExt.mOneHandOpPolicy;
        if (oneHandOpPolicy != null) {
            return oneHandOpPolicy.mHasOneHandOpSpec;
        }
        return false;
    }

    public final boolean hasOwnFocus() {
        return this.mWmService.mPerDisplayFocusEnabled || (this.mDisplayInfo.flags & 2048) != 0;
    }

    public final boolean hasTopFixedRotationLaunchingApp() {
        ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
        return (activityRecord == null
                        || activityRecord
                                == this.mFixedRotationTransitionListener.mAnimatingRecents)
                ? false
                : true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean inTransition() {
        return this.mScreenRotationAnimation != null || super.inTransition();
    }

    public final boolean isAodShowing() {
        boolean z =
                this.mRootWindowContainer.mTaskSupervisor.mKeyguardController.getDisplayState(
                                this.mDisplayId)
                        .mAodShowing;
        return (this.mDisplayId == 0 && z) ? !isKeyguardGoingAway() : z;
    }

    public final boolean isAppCastingDisplay() {
        return (this.mDisplay.getFlags() & 33554432) != 0;
    }

    public final boolean isCarLifeDisplay() {
        return (this.mDisplay.getFlags() & 1048576) != 0;
    }

    public final boolean isFixedRotationLaunchingApp(ActivityRecord activityRecord) {
        return this.mFixedRotationLaunchingApp == activityRecord;
    }

    public final boolean isFlexPanelRunning() {
        ActivityRecord activityRecord;
        if (!this.isDefaultDisplay || MultiWindowUtils.isInSubDisplay(this.mWmService.mContext)) {
            return false;
        }
        Task task = getDefaultTaskDisplayArea().mRootSideStageTask;
        Task topMostTask = task != null ? task.getTopMostTask() : null;
        return (topMostTask == null
                        || (activityRecord = topMostTask.topRunningActivity(false)) == null
                        || !activityRecord.mIsFlexPanel)
                ? false
                : true;
    }

    public final boolean isHomeSupported() {
        boolean booleanValue;
        DisplayWindowSettings displayWindowSettings = this.mWmService.mDisplayWindowSettings;
        displayWindowSettings.getClass();
        if (this.mDisplayId == 0) {
            booleanValue = true;
        } else {
            Boolean bool =
                    displayWindowSettings.mSettingsProvider.getSettings(this.mDisplayInfo)
                            .mIsHomeSupported;
            booleanValue =
                    bool != null
                            ? bool.booleanValue()
                            : displayWindowSettings.shouldShowSystemDecorsLocked(this);
        }
        return (booleanValue && this.mDisplay.isTrusted()) || supportsSystemDecorations();
    }

    public final boolean isKeyguardGoingAway() {
        return this.mRootWindowContainer.mTaskSupervisor.mKeyguardController.isKeyguardGoingAway(
                this.mDisplayId);
    }

    public final boolean isKeyguardLocked() {
        return this.mRootWindowContainer.mTaskSupervisor.mKeyguardController.isKeyguardLocked(
                this.mDisplayId);
    }

    public final boolean isMultiTaskingDisplay() {
        return this.mDisplayId == 2 || isRemoteAppDisplay() || isAppCastingDisplay();
    }

    public final boolean isNextTransitionForward() {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return this.mAppTransition.containsTransitRequest(1)
                    || this.mAppTransition.containsTransitRequest(3);
        }
        int collectingTransitionType = this.mTransitionController.getCollectingTransitionType();
        return collectingTransitionType == 1 || collectingTransitionType == 3;
    }

    public final boolean isPrivate() {
        return (this.mDisplay.getFlags() & 4) != 0;
    }

    public final boolean isReady() {
        return this.mWmService.mDisplayReady && this.mDisplayReady;
    }

    public final boolean isRemoteAppDisplay() {
        return (this.mDisplay.getFlags() & 2097152) != 0;
    }

    public final boolean isRotationChanging() {
        return this.mDisplayRotation.mRotation != getWindowConfiguration().getRotation();
    }

    public final boolean isSizeRatioChanging() {
        return Math.abs(
                        (((float) Math.min(this.mBaseDisplayWidth, this.mBaseDisplayHeight))
                                        / ((float)
                                                Math.max(
                                                        this.mBaseDisplayWidth,
                                                        this.mBaseDisplayHeight)))
                                - (((float)
                                                Math.min(
                                                        getWindowConfiguration()
                                                                .getBounds()
                                                                .width(),
                                                        getWindowConfiguration()
                                                                .getBounds()
                                                                .height()))
                                        / ((float)
                                                Math.max(
                                                        getWindowConfiguration()
                                                                .getBounds()
                                                                .width(),
                                                        getWindowConfiguration()
                                                                .getBounds()
                                                                .height()))))
                > 0.001f;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isSyncFinished(BLASTSyncEngine.SyncGroup syncGroup) {
        return !this.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isVisible() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isVisibleRequested() {
        return (this.mRemoved || this.mRemoving) ? false : true;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
              // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl.Builder makeAnimationLeash() {
        return this.mWmService
                .makeSurfaceBuilder(this.mSession)
                .setParent(this.mSurfaceControl)
                .setContainerLayer();
    }

    @Override // com.android.server.wm.WindowContainer
    public final SurfaceControl.Builder makeChildSurface(WindowContainer windowContainer) {
        SurfaceControl.Builder containerLayer =
                this.mWmService
                        .makeSurfaceBuilder(
                                windowContainer != null
                                        ? windowContainer.getSession()
                                        : this.mSession)
                        .setContainerLayer();
        return windowContainer == null
                ? containerLayer
                : containerLayer.setName(windowContainer.getName()).setParent(this.mSurfaceControl);
    }

    public final SurfaceControl.Builder makeOverlay() {
        return this.mWmService.makeSurfaceBuilder(this.mSession).setParent(this.mOverlayLayer);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void migrateToNewSurfaceControl(SurfaceControl.Transaction transaction) {
        transaction.remove(this.mSurfaceControl);
        this.mLastSurfacePosition.set(0, 0);
        this.mLastDeltaRotation = 0;
        configureSurfaces(transaction);
        scheduleAnimation();
    }

    public final void notifyKeyguardFlagsChanged() {
        if (isKeyguardLocked()) {
            boolean isTransitionSet = this.mAppTransition.isTransitionSet();
            if (!isTransitionSet) {
                prepareAppTransition(0, 0);
            }
            this.mRootWindowContainer.ensureActivitiesVisible();
            if (isTransitionSet) {
                return;
            }
            executeAppTransition();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean okToAnimate(boolean z, boolean z2) {
        if ((this.mDisplayId == 2 && this.mAtmService.mDexController.getDexModeLocked() != 2)
                || !okToDisplay(z, z2)) {
            return false;
        }
        if (this.mDisplayId != 0
                || ((PhoneWindowManager) this.mWmService.mPolicy).okToAnimate(z2)) {
            return z || this.mDisplayPolicy.mScreenOnFully;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean okToDisplay() {
        return okToDisplay(false, false);
    }

    public final boolean okToDisplay(boolean z, boolean z2) {
        if (this.mDisplayId != 0) {
            return this.mDisplayInfo.state == 2;
        }
        WindowManagerService windowManagerService = this.mWmService;
        if ((!windowManagerService.mDisplayFrozen || z) && windowManagerService.mDisplayEnabled) {
            return z2
                    || ((PhoneWindowManager) windowManagerService.mPolicy)
                            .mDefaultDisplayPolicy
                            .mScreenOnEarly;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onAppTransitionDone() {
        super.onAppTransitionDone();
        this.mWmService.mWindowsChanged = true;
        ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
        if (activityRecord == null
                || activityRecord.isVisibleRequested()
                || this.mFixedRotationLaunchingApp.mVisible
                || this.mDisplayRotation.mRotatingSeamlessly) {
            return;
        }
        clearFixedRotationLaunchingApp();
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
              // com.android.server.wm.ConfigurationContainer
    public final void onConfigurationChanged(Configuration configuration) {
        MultiTaskingAppCompatConfiguration.BlackLetterboxConfig blackLetterboxConfig;
        ActivityRecord topMostActivity;
        DisplayInfo displayInfo;
        DisplayCutout displayCutout;
        if (this.isDefaultDisplay) {
            DisplayCutoutController displayCutoutController =
                    this.mAtmService.mExt.mDisplayCutoutController;
            displayCutoutController.getClass();
            DisplayCutout displayCutout2 = this.mBaseDisplayCutout;
            DisplayPolicy displayPolicy = this.mDisplayPolicy;
            if (displayCutout2 == null
                    || displayPolicy == null
                    || (displayCutout = (displayInfo = this.mDisplayInfo).displayCutout) == null) {
                displayCutoutController.mCutoutInset = null;
                displayCutoutController.mNonDecorInsetsWithoutCutout.setEmpty();
            } else {
                int i = displayInfo.rotation;
                displayCutoutController.mCutoutInset = displayCutout.getSafeInsets();
                if (displayPolicy.mHasNavigationBar) {
                    int navigationBarPosition = displayPolicy.navigationBarPosition(i);
                    if (navigationBarPosition == 4) {
                        if (displayCutoutController.mNonDecorInsetsWithoutCutout.bottom > 0) {
                            displayCutoutController.mCutoutInset.bottom = 0;
                        }
                    } else if (navigationBarPosition == 2) {
                        if (displayCutoutController.mNonDecorInsetsWithoutCutout.right > 0) {
                            displayCutoutController.mCutoutInset.right = 0;
                        }
                    } else if (navigationBarPosition == 1
                            && displayCutoutController.mNonDecorInsetsWithoutCutout.left > 0) {
                        displayCutoutController.mCutoutInset.left = 0;
                    }
                }
            }
            if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT) {
                DisplayCutout displayCutout3 = this.mBaseDisplayCutout;
                if (displayCutout3 == null || this.mDisplayPolicy == null) {
                    this.mIsOverlappingWithCutoutAsDefault = false;
                } else {
                    int max =
                            Math.max(
                                    Math.max(
                                            displayCutout3.getSafeInsetLeft(),
                                            displayCutout3.getSafeInsetRight()),
                                    Math.max(
                                            displayCutout3.getSafeInsetTop(),
                                            displayCutout3.getSafeInsetBottom()));
                    int dimensionPixelSize =
                            this.mDisplayPolicy
                                    .getCurrentUserResources()
                                    .getDimensionPixelSize(17105826);
                    this.mIsOverlappingWithCutoutAsDefault = max > 0 && max <= dimensionPixelSize;
                    if (CoreRune.MD_DEX_NOT_SUPPORT_CUTOUT && isDexMode()) {
                        this.mIsOverlappingWithCutoutAsDefault = true;
                    }
                    StringBuilder m =
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    max,
                                    dimensionPixelSize,
                                    "updateIsOverlappingWithCutoutAsDefault: largeCutoutSize=",
                                    ", minimumSizeForOverlappingWithCutoutAsDefault=",
                                    ", isDexMode=");
                    m.append(isDexMode());
                    Slog.i("WindowManager", m.toString());
                }
            }
            if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
                MultiTaskingController multiTaskingController =
                        this.mAtmService.mMultiTaskingController;
                ActivityTaskManagerService activityTaskManagerService = multiTaskingController.mAtm;
                int updateFrom =
                        multiTaskingController.mLastConfig.updateFrom(
                                activityTaskManagerService.getConfiguration());
                int i2 = this.mDisplayInfo.rotation;
                boolean z = multiTaskingController.mLastRotation != i2;
                if ((updateFrom & 5120) != 0 || z) {
                    TaskDisplayArea defaultTaskDisplayArea = getDefaultTaskDisplayArea();
                    multiTaskingController.updateMultiSplitAppMinimumSizeLocked();
                    if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
                        activityTaskManagerService.mWindowManager.getClass();
                    }
                    if (multiTaskingController.mSplitFeasibleMode != 2
                            && defaultTaskDisplayArea.isMultiSplitActive()) {
                        Task focusedRootTask = defaultTaskDisplayArea.getFocusedRootTask();
                        if (focusedRootTask != null) {
                            ActivityRecord topMostActivity2 = focusedRootTask.getTopMostActivity();
                            if (topMostActivity2 != null
                                    && topMostActivity2.inSplitScreenWindowingMode()) {
                                multiTaskingController.exitMultiWindow(topMostActivity2.token);
                            }
                        } else {
                            Task task = defaultTaskDisplayArea.mRootSideStageTask;
                            if (task != null
                                    && (topMostActivity = task.getTopMostActivity()) != null) {
                                multiTaskingController.exitMultiWindow(topMostActivity.token);
                            }
                        }
                    }
                }
                if (z) {
                    multiTaskingController.mLastRotation = i2;
                }
            }
        } else if (isMultiTaskingDisplay()) {
            Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
            if (!requestedOverrideConfiguration.getLocales().equals(configuration.getLocales())) {
                requestedOverrideConfiguration.setLocales(configuration.getLocales());
            }
            int i3 = requestedOverrideConfiguration.uiMode;
            int i4 = i3 & 48;
            int i5 = configuration.uiMode & 48;
            if (i4 != i5) {
                requestedOverrideConfiguration.uiMode = (i3 & (-49)) | i5;
            }
        }
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION
                && (blackLetterboxConfig = this.mMultiTaskingAppCompatConfiguration) != null) {
            blackLetterboxConfig.onConfigurationChanged(configuration);
        }
        int i6 = getConfiguration().orientation;
        int windowingMode = getWindowingMode();
        super.onConfigurationChanged(configuration);
        DisplayPolicy displayPolicy2 = this.mDisplayPolicy;
        if (displayPolicy2 != null) {
            displayPolicy2.onConfigurationChanged();
            PinnedTaskController pinnedTaskController = this.mPinnedTaskController;
            Resources resources = pinnedTaskController.mService.mContext.getResources();
            pinnedTaskController.mMinAspectRatio =
                    resources.getFloat(R.dimen.config_viewMinFlingVelocity);
            pinnedTaskController.mMaxAspectRatio =
                    resources.getFloat(R.dimen.config_viewMaxRotaryEncoderFlingVelocity);
            pinnedTaskController.mFreezingTaskConfig = false;
            this.mMinSizeOfResizeableTaskDp = getMinimalTaskSizeDp();
            MultiWindowPointerEventListener multiWindowPointerEventListener =
                    this.mMultiWindowPointerEventListener;
            if (multiWindowPointerEventListener != null) {
                multiWindowPointerEventListener.loadDimens();
                MultiWindowEdgeDetector multiWindowEdgeDetector =
                        multiWindowPointerEventListener.mMultiWindowEdgeDetector;
                if (multiWindowEdgeDetector != null) {
                    multiWindowEdgeDetector.onConfigurationChanged();
                }
            }
            FreeformController freeformController = this.mAtmService.mFreeformController;
            freeformController.getClass();
            freeformController.mFreeformCornerRadius.put(
                    this.mDisplayId,
                    Integer.valueOf(
                            this.mDisplayPolicy
                                    .getContext()
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen.indeterminate_progress_alpha_30)));
        }
        updateImeParent();
        ContentRecorder contentRecorder = this.mContentRecorder;
        if (contentRecorder != null) {
            contentRecorder.onConfigurationChanged(i6, windowingMode);
        }
        if (i6 != getConfiguration().orientation) {
            if (this.mMetricsLogger == null) {
                this.mMetricsLogger = new MetricsLogger();
            }
            this.mMetricsLogger.write(
                    new LogMaker(1659)
                            .setSubtype(getConfiguration().orientation)
                            .addTaggedData(1660, Integer.valueOf(this.mDisplayId)));
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final boolean onDescendantOrientationChanged(WindowContainer windowContainer) {
        boolean z = false;
        Configuration updateOrientation = updateOrientation(windowContainer, false);
        boolean handlesOrientationChangeFromDescendant =
                handlesOrientationChangeFromDescendant(
                        windowContainer != null ? windowContainer.getOverrideOrientation() : -2);
        if (updateOrientation == null) {
            return handlesOrientationChangeFromDescendant;
        }
        if (handlesOrientationChangeFromDescendant && (windowContainer instanceof ActivityRecord)) {
            ActivityRecord activityRecord = (ActivityRecord) windowContainer;
            if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE
                    && activityRecord.mIsFlexPanel
                    && this.mAtmService.mTaskSupervisor.mDeferResumeCount != 0) {
                z = true;
            }
            if (!updateDisplayOverrideConfigurationLocked(updateOrientation, activityRecord, z)) {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities();
            }
        } else {
            updateDisplayOverrideConfigurationLocked(updateOrientation, null, false);
        }
        return handlesOrientationChangeFromDescendant;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onDescendantOverrideConfigurationChanged() {
        setLayoutNeeded();
        this.mWmService.requestTraversal();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onDisplayChanged(DisplayContent displayContent) {
        super.onDisplayChanged(displayContent);
        this.mSystemGestureExclusionLimit =
                (this.mWmService.mConstants.mSystemGestureExclusionLimitDp
                                * this.mDisplayMetrics.densityDpi)
                        / 160;
        updateSystemGestureExclusion();
    }

    public final void onDisplayInfoChanged() {
        updateDisplayFrames(false);
        InputMonitor inputMonitor = this.mInputMonitor;
        DisplayInfo displayInfo = this.mDisplayInfo;
        inputMonitor.layoutInputConsumers(
                displayInfo.logicalWidth, displayInfo.logicalHeight, false);
        DisplayPolicy displayPolicy = this.mDisplayPolicy;
        DisplayInfo displayInfo2 = this.mDisplayInfo;
        displayPolicy.getClass();
        if (ViewRootImpl.CLIENT_TRANSIENT) {
            return;
        }
        int i = displayInfo2.displayId;
        if (i == 2) {
            displayPolicy.mUiContext =
                    displayPolicy.mService.mAtmService.mSystemThread.getSystemUiContext(i);
            displayPolicy.updateCurrentUserResources();
        }
        SystemGesturesPointerEventListener systemGesturesPointerEventListener =
                displayPolicy.mSystemGestures;
        systemGesturesPointerEventListener.getClass();
        systemGesturesPointerEventListener.screenWidth = displayInfo2.logicalWidth;
        systemGesturesPointerEventListener.screenHeight = displayInfo2.logicalHeight;
        systemGesturesPointerEventListener.onConfigurationChanged();
        if (CoreRune.FW_VRR_FOR_SUB_DISPLAY && displayInfo2.displayId == 0) {
            RefreshRatePolicy refreshRatePolicy = displayPolicy.mRefreshRatePolicy;
            refreshRatePolicy.getClass();
            refreshRatePolicy.mLowRefreshRateMode =
                    refreshRatePolicy.findLowRefreshRateMode(
                            displayInfo2, displayInfo2.getDefaultMode());
        }
    }

    public final void onDisplayInfoUpdated(DisplayInfo displayInfo) {
        DisplayShape displayShape;
        float f;
        int i;
        RoundedCorners roundedCorners;
        String str;
        DisplayCutout displayCutout;
        DisplayCutout displayCutout2;
        DisplayShape displayShape2;
        float f2;
        int i2;
        DisplayInfo displayInfo2 = this.mDisplayInfo;
        int i3 = displayInfo2.state;
        displayInfo2.copyFrom(displayInfo);
        this.mNonOverrideDisplayInfo.copyFrom(this.mDisplayInfo);
        int i4 = this.mDisplayRotation.mRotation;
        DisplayInfo displayInfo3 = this.mDisplayInfo;
        int i5 = displayInfo3.rotation;
        boolean z = i5 == 1 || i5 == 3;
        int i6 = z ? displayInfo3.logicalHeight : displayInfo3.logicalWidth;
        int i7 = z ? displayInfo3.logicalWidth : displayInfo3.logicalHeight;
        int i8 = displayInfo3.logicalDensityDpi;
        float f3 = displayInfo3.physicalXDpi;
        float f4 = displayInfo3.physicalYDpi;
        DisplayCutout displayCutout3 =
                this.mIgnoreDisplayCutout ? DisplayCutout.NO_CUTOUT : displayInfo3.displayCutout;
        String str2 = displayInfo3.uniqueId;
        RoundedCorners roundedCorners2 = displayInfo3.roundedCorners;
        DisplayShape displayShape3 = displayInfo3.displayShape;
        boolean z2 =
                (this.mInitialDisplayWidth == i6
                                && this.mInitialDisplayHeight == i7
                                && this.mInitialDisplayDensity == i8
                                && this.mInitialPhysicalXDpi == f3
                                && this.mInitialPhysicalYDpi == f4
                                && Objects.equals(this.mInitialDisplayCutout, displayCutout3)
                                && Objects.equals(this.mInitialRoundedCorners, roundedCorners2)
                                && Objects.equals(this.mInitialDisplayShape, displayShape3))
                        ? false
                        : true;
        boolean z3 = !str2.equals(this.mCurrentUniqueDisplayId);
        if (z2 || z3) {
            if (z3) {
                this.mWmService.mDisplayWindowSettings.applySettingsToDisplayLocked(this, false);
                displayShape = displayShape3;
                f = f4;
                i = i3;
                roundedCorners = roundedCorners2;
                str = str2;
                displayCutout = displayCutout3;
                this.mDisplayUpdater.onDisplayContentDisplayPropertiesPreChanged(
                        this.mDisplayId,
                        this.mInitialDisplayWidth,
                        this.mInitialDisplayHeight,
                        i6,
                        i7);
                DisplayRotation.FoldController foldController =
                        this.mDisplayRotation.mFoldController;
                if (foldController != null && foldController.mPauseAutorotationDuringUnfolding) {
                    DisplayRotation displayRotation = DisplayRotation.this;
                    foldController.mLastDisplaySwitchTime = displayRotation.uptimeMillis();
                    DeviceStateController.DeviceState deviceState = foldController.mDeviceState;
                    if (deviceState == DeviceStateController.DeviceState.OPEN
                            || deviceState == DeviceStateController.DeviceState.HALF_FOLDED) {
                        foldController.mShouldDisableRotationSensor = true;
                        displayRotation.updateOrientationListenerLw();
                    }
                    foldController.updateSensorRotationBlockIfNeeded();
                    displayRotation
                            .getHandler()
                            .postDelayed(
                                    new DisplayRotation$$ExternalSyntheticLambda0(
                                            2, foldController),
                                    foldController.mDisplaySwitchRotationBlockTimeMs);
                }
                DisplayPolicy displayPolicy = this.mDisplayPolicy;
                if (DisplayPolicy.USE_CACHED_INSETS_FOR_DISPLAY_SWITCH) {
                    displayPolicy.updateCachedDecorInsets();
                } else {
                    displayPolicy.getClass();
                }
            } else {
                displayShape = displayShape3;
                f = f4;
                i = i3;
                roundedCorners = roundedCorners2;
                str = str2;
                displayCutout = displayCutout3;
            }
            boolean z4 = this.mIsSizeForced;
            int i9 = z4 ? this.mBaseDisplayWidth : i6;
            int i10 = z4 ? this.mBaseDisplayHeight : i7;
            int i11 = this.mIsDensityForced ? this.mBaseDisplayDensity : i8;
            float f5 = z4 ? this.mBaseDisplayPhysicalXDpi : f3;
            if (z4) {
                f2 = this.mBaseDisplayPhysicalYDpi;
                displayShape2 = displayShape;
                displayCutout2 = displayCutout;
            } else {
                displayCutout2 = displayCutout;
                displayShape2 = displayShape;
                f2 = f;
            }
            String str3 = str;
            float f6 = f;
            DisplayCutout displayCutout4 = displayCutout2;
            updateBaseDisplayMetrics(i9, i10, i11, f5, f2);
            configureDisplayPolicy();
            if (z3) {
                this.mWmService.mDisplayWindowSettings.applyRotationSettingsToDisplayLocked(this);
            }
            this.mInitialDisplayWidth = i6;
            this.mInitialDisplayHeight = i7;
            this.mInitialDisplayDensity = i8;
            this.mInitialPhysicalXDpi = f3;
            this.mInitialPhysicalYDpi = f6;
            this.mInitialDisplayCutout = displayCutout4;
            this.mInitialRoundedCorners = roundedCorners;
            this.mInitialDisplayShape = displayShape2;
            this.mCurrentUniqueDisplayId = str3;
            reconfigureDisplayLocked();
            if (z3) {
                DisplayPolicy displayPolicy2 = this.mDisplayPolicy;
                if (displayPolicy2.mCachedDecorInsets != null) {
                    DisplayContent displayContent = displayPolicy2.mDisplayContent;
                    if (displayContent.mTransitionController.isCollecting()) {
                        displayPolicy2.mCachedDecorInsets.mPreserveId =
                                displayContent.mTransitionController.getCollectingTransitionId();
                    } else {
                        displayPolicy2.mCachedDecorInsets = null;
                    }
                }
                this.mDisplayUpdater.onDisplayContentDisplayPropertiesPostChanged(
                        i4, this.mDisplayRotation.mRotation, getDisplayAreaInfo());
            }
        } else {
            i = i3;
        }
        DisplayInfo displayInfo4 = this.mDisplayInfo;
        DisplayInfo displayInfo5 = this.mLastDisplayInfoOverride;
        displayInfo4.copyFrom(displayInfo);
        if (displayInfo5 != null) {
            displayInfo4.appWidth = displayInfo5.appWidth;
            displayInfo4.appHeight = displayInfo5.appHeight;
            displayInfo4.smallestNominalAppWidth = displayInfo5.smallestNominalAppWidth;
            displayInfo4.smallestNominalAppHeight = displayInfo5.smallestNominalAppHeight;
            displayInfo4.largestNominalAppWidth = displayInfo5.largestNominalAppWidth;
            displayInfo4.largestNominalAppHeight = displayInfo5.largestNominalAppHeight;
            displayInfo4.logicalWidth = displayInfo5.logicalWidth;
            displayInfo4.logicalHeight = displayInfo5.logicalHeight;
            displayInfo4.physicalXDpi = displayInfo5.physicalXDpi;
            displayInfo4.physicalYDpi = displayInfo5.physicalYDpi;
            displayInfo4.rotation = displayInfo5.rotation;
            displayInfo4.displayCutout = displayInfo5.displayCutout;
            displayInfo4.logicalDensityDpi = displayInfo5.logicalDensityDpi;
            displayInfo4.roundedCorners = displayInfo5.roundedCorners;
            displayInfo4.displayShape = displayInfo5.displayShape;
        }
        this.mDisplayInfo.getAppMetrics(
                this.mDisplayMetrics, this.mDisplay.getDisplayAdjustments());
        onDisplayInfoChanged();
        onDisplayChanged(this);
        int displayId = this.mDisplay.getDisplayId();
        int i12 = this.mDisplayInfo.state;
        if (displayId != 0) {
            if (!this.mWmService.mExt.mExtraDisplayPolicy.isDisplayControlledByPolicy(displayId)) {
                if (displayId == 2) {
                    if (i12 == 1) {
                        this.mAtmService.mDexController.deactivateDexDisplayLocked(this);
                    } else if (i12 == 2) {
                        this.mAtmService.mDexController.activateDexDisplayLocked(this);
                    }
                } else if (i12 == 1) {
                    this.mOffTokenAcquirer.acquire(this.mDisplayId, false);
                } else if (i12 == 2) {
                    this.mOffTokenAcquirer.release(this.mDisplayId);
                }
            }
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled[1]) {
                i2 = i;
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                        -8165317816061445169L,
                        21,
                        "Content Recording: Display %d state was (%d), is now (%d), so update"
                            + " recording?",
                        Long.valueOf(this.mDisplayId),
                        Long.valueOf(i2),
                        Long.valueOf(i12));
            } else {
                i2 = i;
            }
            if (i2 != i12) {
                updateRecording();
            }
        } else {
            i2 = i;
        }
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
            int i13 = getConfiguration().semDisplayDeviceType;
            if (i13 == 0) {
                this.mWmService.mAtmService.mMultiWindowFoldController.updateMainDisplayBounds(
                        this.mBaseDisplayWidth, this.mBaseDisplayHeight);
            } else if (i13 == 5) {
                this.mWmService.mAtmService.mMultiWindowFoldController.updateCoverDisplayBounds(
                        this.mBaseDisplayWidth, this.mBaseDisplayHeight);
            }
        }
        WallpaperController wallpaperController = this.mWallpaperController;
        Display display = this.mDisplay;
        wallpaperController.getClass();
        if (display != null && display.getType() == 1) {
            wallpaperController.mLargestDisplaySize = null;
        }
        if (Display.isSuspendedState(i2) && !Display.isSuspendedState(i12) && i12 != 0) {
            WindowContextListenerController windowContextListenerController =
                    this.mWmService.mWindowContextListenerController;
            int i14 = this.mDisplayId;
            for (int size = windowContextListenerController.mListeners.size() - 1;
                    size >= 0;
                    size--) {
                WindowContextListenerController.WindowContextListenerImpl
                        windowContextListenerImpl =
                                (WindowContextListenerController.WindowContextListenerImpl)
                                        windowContextListenerController.mListeners.valueAt(size);
                if (windowContextListenerImpl.getWindowContainer().getDisplayContent().mDisplayId
                                == i14
                        && windowContextListenerImpl.mHasPendingConfiguration) {
                    windowContextListenerImpl.dispatchWindowContextInfoChange();
                }
            }
        }
        this.mWmService.requestTraversal();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void onParentChanged(
            ConfigurationContainer configurationContainer,
            ConfigurationContainer configurationContainer2) {
        if (isReady()) {
            return;
        }
        this.mDisplayReady = true;
        if (this.mWmService.mDisplayManagerInternal != null) {
            setDisplayInfoOverride();
            configureDisplayPolicy();
        }
        if (!this.isDefaultDisplay) {
            this.mDisplayRotation.updateRotationUnchecked(true);
        }
        reconfigureDisplayLocked();
        onRequestedOverrideConfigurationChanged(getRequestedOverrideConfiguration());
        DisplayWindowListenerController displayWindowListenerController =
                this.mWmService.mDisplayNotificationController;
        int beginBroadcast = displayWindowListenerController.mDisplayListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                displayWindowListenerController
                        .mDisplayListeners
                        .getBroadcastItem(i)
                        .onDisplayAdded(this.mDisplayId);
            } catch (RemoteException unused) {
            }
        }
        displayWindowListenerController.mDisplayListeners.finishBroadcast();
        this.mAtmService.getProcessController(
                this.mDisplayPolicy.mUiContext.getIApplicationThread());
        WindowTokenClientController.getInstance()
                .attachToDisplayContent(
                        this.mDisplayPolicy.mUiContext.getWindowContextToken(), this.mDisplayId);
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer,
              // com.android.server.wm.ConfigurationContainer
    public final void onRequestedOverrideConfigurationChanged(Configuration configuration) {
        UdcCutoutPolicy udcCutoutPolicy = this.mUdcCutoutPolicy;
        if (udcCutoutPolicy != null && udcCutoutPolicy.mUdcCutout != null) {
            udcCutoutPolicy.getClass();
            int rotation = configuration.windowConfiguration.getRotation();
            if (rotation != -1) {
                Configuration configuration2 = udcCutoutPolicy.mUdcConfiguration;
                if (configuration2 == null) {
                    udcCutoutPolicy.mUdcConfiguration = new Configuration();
                } else {
                    configuration2.unset();
                }
                udcCutoutPolicy.mDisplayContent.computeScreenConfiguration(
                        udcCutoutPolicy.mUdcConfiguration, rotation, true);
            }
        }
        Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
        int rotation2 = requestedOverrideConfiguration.windowConfiguration.getRotation();
        int rotation3 = configuration.windowConfiguration.getRotation();
        if (rotation2 != -1 && rotation3 != -1 && rotation2 != rotation3) {
            ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
            if (activityRecord == null) {
                applyRotation(rotation2, rotation3);
            } else {
                activityRecord.finishFixedRotationTransform(
                        new DisplayContent$$ExternalSyntheticLambda6(this, rotation2, rotation3));
                setFixedRotationLaunchingAppUnchecked(-1, null);
            }
        }
        this.mCurrentOverrideConfigurationChanges =
                requestedOverrideConfiguration.diff(configuration);
        super.onRequestedOverrideConfigurationChanged(configuration);
        this.mCurrentOverrideConfigurationChanges = 0;
        if (this.mWaitingForConfig) {
            this.mWaitingForConfig = false;
            this.mWmService.mLastFinishedFreezeSource = "new-config";
        }
        this.mAtmService.mLayoutReasons |= 1;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onResize() {
        super.onResize();
        if (this.mWmService.mAccessibilityController.hasCallbacks()) {
            AccessibilityController accessibilityController =
                    this.mWmService.mAccessibilityController;
            if (accessibilityController.mAccessibilityTracing.isTracingEnabled(3072L)) {
                accessibilityController.mAccessibilityTracing.logTrace(
                        "AccessibilityController.onRotationChanged",
                        3072L,
                        "displayContent={" + this + "}");
            }
            AccessibilityController.DisplayMagnifier displayMagnifier =
                    (AccessibilityController.DisplayMagnifier)
                            accessibilityController.mDisplayMagnifiers.get(this.mDisplayId);
            if (displayMagnifier != null) {
                if (displayMagnifier.mAccessibilityTracing.isTracingEnabled(2048L)) {
                    displayMagnifier.mAccessibilityTracing.logTrace(
                            "WindowManager.onDisplaySizeChanged",
                            2048L,
                            "displayContent={" + this + "}");
                }
                displayMagnifier.recomputeBounds();
                if (!com.android.window.flags.Flags.alwaysDrawMagnificationFullscreenBorder()) {
                    AccessibilityController.DisplayMagnifier.MagnifiedViewport magnifiedViewport =
                            displayMagnifier.mMagnifiedViewport;
                    if (AccessibilityController.DisplayMagnifier.this
                            .isFullscreenMagnificationActivated()) {
                        magnifiedViewport.setMagnifiedRegionBorderShown(false, false);
                        AccessibilityController.DisplayMagnifier.this.mHandler.sendMessageDelayed(
                                AccessibilityController.DisplayMagnifier.this.mHandler
                                        .obtainMessage(5),
                                (long)
                                        (AccessibilityController.DisplayMagnifier.this.mService
                                                        .getWindowAnimationScaleLocked()
                                                * r1.mLongAnimationDuration));
                    }
                    AccessibilityController.DisplayMagnifier.MagnifiedViewport.ViewportWindow
                            viewportWindow = magnifiedViewport.mWindow;
                    WindowManagerGlobalLock windowManagerGlobalLock =
                            AccessibilityController.DisplayMagnifier.this.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            AccessibilityController.DisplayMagnifier displayMagnifier2 =
                                    AccessibilityController.DisplayMagnifier.this;
                            displayMagnifier2.getDisplaySizeLocked(displayMagnifier2.mScreenSize);
                            BLASTBufferQueue bLASTBufferQueue = viewportWindow.mBlastBufferQueue;
                            SurfaceControl surfaceControl = viewportWindow.mSurfaceControl;
                            Point point = AccessibilityController.DisplayMagnifier.this.mScreenSize;
                            bLASTBufferQueue.update(surfaceControl, point.x, point.y, 1);
                            viewportWindow.invalidate(viewportWindow.mDirtyRect);
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
                displayMagnifier.mHandler.sendEmptyMessage(4);
            }
        }
    }

    public final void onRunningActivityChanged() {
        DisplayWindowPolicyControllerHelper displayWindowPolicyControllerHelper = this.mDwpcHelper;
        if (displayWindowPolicyControllerHelper.mDisplayWindowPolicyController == null) {
            return;
        }
        DisplayContent displayContent = displayWindowPolicyControllerHelper.mDisplayContent;
        ActivityRecord topActivity = displayContent.getTopActivity(false, true);
        if (topActivity != displayWindowPolicyControllerHelper.mTopRunningActivity) {
            displayWindowPolicyControllerHelper.mTopRunningActivity = topActivity;
            if (topActivity == null) {
                displayWindowPolicyControllerHelper.mDisplayWindowPolicyController
                        .onTopActivityChanged((ComponentName) null, -1, -10000);
            } else {
                displayWindowPolicyControllerHelper.mDisplayWindowPolicyController
                        .onTopActivityChanged(
                                topActivity.info.getComponentName(),
                                topActivity.info.applicationInfo.uid,
                                topActivity.mUserId);
            }
        }
        final boolean[] zArr = {false};
        final ArraySet arraySet = new ArraySet();
        displayContent.forAllActivities(
                new Consumer() { // from class:
                                 // com.android.server.wm.DisplayWindowPolicyControllerHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean[] zArr2 = zArr;
                        ArraySet arraySet2 = arraySet;
                        ActivityRecord activityRecord = (ActivityRecord) obj;
                        if (activityRecord.finishing) {
                            return;
                        }
                        zArr2[0] =
                                arraySet2.add(Integer.valueOf(activityRecord.getUid())) | zArr2[0];
                    }
                });
        if (zArr[0] || displayWindowPolicyControllerHelper.mRunningUid.size() != arraySet.size()) {
            displayWindowPolicyControllerHelper.mRunningUid = arraySet;
            displayWindowPolicyControllerHelper.mDisplayWindowPolicyController.onRunningAppsChanged(
                    arraySet);
        }
    }

    public final void onShowImeRequested() {
        ActivityRecord activityRecord;
        WindowState windowState;
        WindowState windowState2 = this.mInputMethodWindow;
        if (windowState2 == null || (activityRecord = this.mFixedRotationLaunchingApp) == null) {
            return;
        }
        windowState2.mToken.linkFixedRotationTransform(activityRecord);
        AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        if (asyncRotationController == null
                || (windowState = asyncRotationController.mDisplayContent.mInputMethodWindow)
                        == null) {
            return;
        }
        WindowToken windowToken = windowState.mToken;
        if (asyncRotationController.mTargetWindowTokens.containsKey(windowToken)) {
            return;
        }
        asyncRotationController.hideImmediately(windowToken, 3);
        Slog.d(
                "AsyncRotation_WindowManager",
                "hideImeImmediately "
                        + windowToken.getTopChild()
                        + ", caller="
                        + Debug.getCallers(6));
    }

    public void pauseRecording() {
        ContentRecorder contentRecorder = this.mContentRecorder;
        if (contentRecorder != null) {
            contentRecorder.pauseRecording();
        }
    }

    public final int performDisplayOverrideConfigUpdate(Configuration configuration) {
        int i;
        ActivityTaskManagerService activityTaskManagerService;
        int i2;
        int i3;
        int i4;
        int i5;
        Context context = null;
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && this.isDefaultDisplay) {
            int i6 = this.mTempConfig.semDisplayDeviceType;
            int i7 = configuration.semDisplayDeviceType;
            if (i6 != i7) {
                MultiWindowFoldController multiWindowFoldController =
                        this.mAtmService.mMultiWindowFoldController;
                multiWindowFoldController.getClass();
                boolean z = i7 == 0;
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "onDisplayDeviceTypeChanged opened : ", "MultiWindowFoldController", z);
                if (z) {
                    multiWindowFoldController.setFoldingState(0, "displayDevice(" + i7 + ")");
                } else {
                    ActivityTaskManagerService activityTaskManagerService2 =
                            multiWindowFoldController.mAtm;
                    if (activityTaskManagerService2
                            .mRootWindowContainer
                            .mDefaultDisplay
                            .getDefaultTaskDisplayArea()
                            .isSplitScreenModeActivated()) {
                        Task task =
                                activityTaskManagerService2.mRootWindowContainer.mDefaultDisplay
                                        .getDefaultTaskDisplayArea()
                                        .mRootMainStageTask;
                        if ((task != null ? task.getTopMostTask() : null) == null) {
                            multiWindowFoldController.setFoldingState(0, "reset");
                        } else {
                            multiWindowFoldController.setFoldingState(1, "apply_folding_policy");
                        }
                    }
                }
            }
        }
        this.mTempConfig.setTo(getRequestedOverrideConfiguration());
        ActivityTaskManagerServiceExt activityTaskManagerServiceExt = this.mAtmService.mExt;
        Configuration configuration2 = this.mTempConfig;
        activityTaskManagerServiceExt.getClass();
        int i8 = configuration.screenWidthDp;
        boolean z2 =
                ((i8 == 0 || configuration2.screenWidthDp == i8)
                                && ((i = configuration.screenHeightDp) == 0
                                        || configuration2.screenHeightDp == i))
                        ? false
                        : true;
        final float width =
                configuration.windowConfiguration.getBounds().width()
                        / this.mTempConfig.windowConfiguration.getBounds().width();
        final float height =
                configuration.windowConfiguration.getBounds().height()
                        / this.mTempConfig.windowConfiguration.getBounds().height();
        this.mTmpPrevBounds.set(this.mTempConfig.windowConfiguration.getBounds());
        if (isMultiTaskingDisplay()) {
            configuration.setLocales(this.mRootWindowContainer.getConfiguration().getLocales());
        }
        final int updateFrom = this.mTempConfig.updateFrom(configuration);
        if (updateFrom == 0 && !this.mForceMakeConfigChange) {
            return updateFrom;
        }
        Slog.i(
                "WindowManager",
                "[d"
                        + this.mDisplayId
                        + "] Override config changes="
                        + Integer.toHexString(updateFrom)
                        + " "
                        + this.mTempConfig
                        + ", callers="
                        + Debug.getCallers(5));
        if (isReady()
                && this.mTransitionController.isShellTransitionsEnabled()
                && this.mLastHasContent) {
            Transition transition = this.mTransitionController.mCollectingTransition;
            if (transition != null) {
                collectDisplayChange(transition);
            } else {
                requestChangeTransition(updateFrom, null);
                if (CoreRune.FW_SHELL_TRANSITION_DISPLAY_CHANGE) {
                    Configuration requestedOverrideConfiguration =
                            getRequestedOverrideConfiguration();
                    if (!CoreRune.FW_UI_MODE_ANIMATION
                            || requestedOverrideConfiguration.isNightModeActive()
                                    == configuration.isNightModeActive()) {
                        i4 = 0;
                        i5 = 0;
                    } else {
                        i4 = R.anim.translucent_exit;
                        i5 = R.anim.voice_activity_close_enter;
                    }
                    if (i4 != 0 || i5 != 0) {
                        TransitionInfo.AnimationOptions makeCustomDisplayChangeAnimOptions =
                                TransitionInfo.AnimationOptions.makeCustomDisplayChangeAnimOptions(
                                        i4, i5);
                        Transition transition2 = this.mTransitionController.mCollectingTransition;
                        if (transition2 != null) {
                            transition2.setOverrideAnimation(
                                    makeCustomDisplayChangeAnimOptions, null, null);
                        }
                    }
                }
                boolean z3 =
                        (getRequestedOverrideConfiguration().isNightModeActive()
                                                == configuration.isNightModeActive()
                                        && (Integer.MIN_VALUE & updateFrom) == 0)
                                ? false
                                : true;
                DisplayContent displayContent = this.mRootWindowContainer.getDisplayContent(2);
                if (z3
                        && this.mTransitionController.isCollecting()
                        && this.isDefaultDisplay
                        && displayContent != null
                        && this.mAtmService.mDexController.getDexModeLocked() == 2) {
                    this.mTransitionController.collect(displayContent);
                    this.mTransitionController.collectForDisplayAreaChange(displayContent);
                }
            }
        }
        if (CoreRune.MT_SIZE_COMPAT_POLICY) {
            final SizeCompatPolicyManager sizeCompatPolicyManager =
                    SizeCompatPolicyManager.LazyHolder.sManager;
            final Configuration configuration3 = this.mTempConfig;
            if (sizeCompatPolicyManager.mCompatPolicyCount > 0) {
                Rect bounds = configuration3.windowConfiguration.getBounds();
                Rect bounds2 = getBounds();
                if (bounds.width() == bounds2.height() && bounds.height() == bounds2.width()) {
                    forAllTasks(
                            new Consumer() { // from class:
                                             // com.android.server.wm.SizeCompatPolicyManager$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    SizeCompatPolicyManager sizeCompatPolicyManager2 =
                                            SizeCompatPolicyManager.this;
                                    DisplayContent displayContent2 = this;
                                    Configuration configuration4 = configuration3;
                                    sizeCompatPolicyManager2.getClass();
                                    DexSizeCompatController.DexSizeCompatPolicy compatPolicy =
                                            SizeCompatPolicyManager.getCompatPolicy(
                                                    (Task) obj, false);
                                    if (compatPolicy == null) {
                                        return;
                                    }
                                    compatPolicy.mUserOrientation = 0;
                                    Task task2 = compatPolicy.mTask;
                                    Configuration requestedOverrideConfiguration2 =
                                            task2.getRequestedOverrideConfiguration();
                                    if (task2.inFreeformWindowingMode()) {
                                        final Rect bounds3 = displayContent2.getBounds();
                                        final Rect bounds4 =
                                                configuration4.windowConfiguration.getBounds();
                                        compatPolicy.setFreeformConfiguration(
                                                requestedOverrideConfiguration2,
                                                bounds4,
                                                new BiConsumer() { // from class:
                                                                   // com.android.server.wm.SizeCompatMultiTaskingPolicy$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.BiConsumer
                                                    public final void accept(
                                                            Object obj2, Object obj3) {
                                                        int i9;
                                                        int i10;
                                                        Rect rect = bounds3;
                                                        Rect rect2 = bounds4;
                                                        Rect rect3 = (Rect) obj2;
                                                        Rect rect4 = (Rect) obj3;
                                                        rect4.offsetTo(rect3.left, rect3.top);
                                                        int width2 = rect.width();
                                                        int width3 = rect2.width();
                                                        int width4 = rect4.width();
                                                        if (width4 >= width3) {
                                                            i9 =
                                                                    rect2.left
                                                                            + ((width3 - width4)
                                                                                    >> 1);
                                                        } else {
                                                            int i11 = rect4.right;
                                                            int i12 = rect2.left;
                                                            if (i11 < i12) {
                                                                i9 = i12;
                                                            } else {
                                                                int i13 = rect4.left;
                                                                int i14 = rect2.right;
                                                                if (i13 > i14) {
                                                                    i9 = i14 - width4;
                                                                } else {
                                                                    i9 =
                                                                            (int)
                                                                                    (i13
                                                                                            * (width3
                                                                                                    / width2));
                                                                }
                                                            }
                                                        }
                                                        int height2 = rect.height();
                                                        int height3 = rect2.height();
                                                        int height4 = rect4.height();
                                                        if (height4 >= height3) {
                                                            i10 =
                                                                    rect2.top
                                                                            + ((height3 - height4)
                                                                                    >> 1);
                                                        } else {
                                                            int i15 = rect4.bottom;
                                                            int i16 = rect2.top;
                                                            if (i15 < i16) {
                                                                i10 = i16;
                                                            } else {
                                                                int i17 = rect4.top;
                                                                int i18 = rect2.bottom;
                                                                if (i17 > i18) {
                                                                    i10 = i18 - height4;
                                                                } else {
                                                                    i10 =
                                                                            (int)
                                                                                    (i17
                                                                                            * (height3
                                                                                                    / height2));
                                                                }
                                                            }
                                                        }
                                                        if (i9 < rect2.left
                                                                || i10 < rect2.top
                                                                || i9 + width4 > rect2.right
                                                                || i10 + height4 > rect2.bottom) {
                                                            i9 = (width3 - width4) >> 1;
                                                            i10 = (height3 - height4) >> 1;
                                                        }
                                                        rect4.offsetTo(i9, i10);
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        }
        onRequestedOverrideConfigurationChanged(this.mTempConfig);
        int i9 = updateFrom & 4096;
        if (i9 != 0 && this.mDisplayId == 0) {
            AppWarnings.UiHandler uiHandler = this.mAtmService.mAppWarnings.mUiHandler;
            uiHandler.removeMessages(2);
            uiHandler.sendEmptyMessage(2);
            this.mAtmService.mH.sendMessage(
                    z2
                            ? PooledLambda.obtainMessage(
                                    new DisplayContent$$ExternalSyntheticLambda10(),
                                    this.mAtmService.mAmInternal,
                                    24,
                                    6)
                            : PooledLambda.obtainMessage(
                                    new DisplayContent$$ExternalSyntheticLambda9(),
                                    this.mAtmService.mAmInternal,
                                    24,
                                    6,
                                    (Object) null));
        }
        MultiTaskingController multiTaskingController = this.mAtmService.mMultiTaskingController;
        int i10 = this.mDisplayId;
        final Rect rect = this.mTmpPrevBounds;
        final Rect bounds3 = this.mTempConfig.windowConfiguration.getBounds();
        multiTaskingController.getClass();
        Slog.i(
                "MultiTaskingController",
                "onConfigurationChangedLocked: display#"
                        + i10
                        + ", configChanges=0x"
                        + Integer.toHexString(updateFrom)
                        + ", scaleW="
                        + width
                        + ", scaleH="
                        + height
                        + ", prevScreenBounds="
                        + rect
                        + ", nextScreenBounds="
                        + bounds3);
        ActivityTaskManagerService activityTaskManagerService3 = multiTaskingController.mAtm;
        DexController dexController = activityTaskManagerService3.mDexController;
        if ((updateFrom & 7296) == 0) {
            dexController.getClass();
        } else {
            if (i10 == 2) {
                VirtualDisplay virtualDisplay = dexController.mDexDisplay;
                if (virtualDisplay == null) {
                    Slog.w(
                            "DexController",
                            "performDisplayOverrideConfigUpdate: mDexDisplay is null");
                } else {
                    Display display = virtualDisplay.getDisplay();
                    if (display.getDisplayId() == 2) {
                        int displayId = display.getDisplayId();
                        Context createDisplayContext =
                                dexController.mAtm.mContext.createDisplayContext(display);
                        if (createDisplayContext != null) {
                            dexController.mDisplayContexts.put(displayId, createDisplayContext);
                        }
                    }
                    dexController
                            .mDexDisplay
                            .getDisplay()
                            .getRealSize(dexController.mDexDisplaySize);
                }
            }
            DexCompatController dexCompatController = dexController.mAtm.mDexCompatController;
            ActivityTaskManagerService activityTaskManagerService4 = dexCompatController.mAtm;
            if (activityTaskManagerService4.mRootWindowContainer.getDisplayContent(i10) == null) {
                Slog.w("DexCompatController", "loadResources: failed, cannot find display!");
            } else {
                DexController dexController2 = activityTaskManagerService4.mDexController;
                if (i10 != 0) {
                    context = (Context) dexController2.mDisplayContexts.get(i10, null);
                } else {
                    dexController2.getClass();
                }
                if (context == null) {
                    context = activityTaskManagerService4.mContext;
                }
                Resources resources = context.getResources();
                dexCompatController.mDecorCaptionHeightInFullscreen.put(
                        i10, Integer.valueOf(resources.getDimensionPixelSize(17105845)));
                dexCompatController.mDecorCaptionHeightInFreeform.put(
                        i10, Integer.valueOf(resources.getDimensionPixelSize(17105844)));
            }
        }
        final FreeformController freeformController =
                activityTaskManagerService3.mFreeformController;
        final DisplayContent displayContent2 =
                freeformController.mAtm.mRootWindowContainer.getDisplayContent(i10);
        if (displayContent2 == null) {
            activityTaskManagerService = activityTaskManagerService3;
            i2 = i10;
            i3 = updateFrom;
        } else {
            final boolean z4 = i9 != 0;
            activityTaskManagerService = activityTaskManagerService3;
            i2 = i10;
            i3 = updateFrom;
            final boolean z5 =
                    (updateFrom & 128) == 0
                            && (134217728 & updateFrom) == 0
                            && (updateFrom & 7168) != 0;
            displayContent2.forAllTasks(
                    new Consumer() { // from class:
                        // com.android.server.wm.FreeformController$$ExternalSyntheticLambda3
                        /* JADX WARN: Code restructure failed: missing block: B:25:0x006e, code lost:

                           if (com.android.server.wm.SizeCompatPolicyManager.getCompatPolicy(r10, false) != null) goto L33;
                        */
                        @Override // java.util.function.Consumer
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void accept(java.lang.Object r10) {
                            /*
                                Method dump skipped, instructions count: 223
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.server.wm.FreeformController$$ExternalSyntheticLambda3.accept(java.lang.Object):void");
                        }
                    });
        }
        activityTaskManagerService.mDexController.updateDexModeIfNeededLocked();
        if (i2 == 0) {
            multiTaskingController.mSwipeGestureThreshold =
                    multiTaskingController
                            .mWm
                            .getDefaultDisplayContentLocked()
                            .mDisplayPolicy
                            .getCurrentUserResources()
                            .getDimensionPixelSize(17106300);
        }
        DisplayWindowListenerController displayWindowListenerController =
                this.mWmService.mDisplayNotificationController;
        Configuration configuration4 = getConfiguration();
        displayWindowListenerController.getClass();
        boolean z6 = false;
        for (int i11 = 0; i11 < getParent().getChildCount(); i11++) {
            if (getParent().getChildAt(i11) == this) {
                z6 = true;
            }
        }
        if (!z6) {
            return i3;
        }
        int beginBroadcast = displayWindowListenerController.mDisplayListeners.beginBroadcast();
        for (int i12 = 0; i12 < beginBroadcast; i12++) {
            try {
                displayWindowListenerController
                        .mDisplayListeners
                        .getBroadcastItem(i12)
                        .onDisplayConfigurationChanged(this.mDisplayId, configuration4);
            } catch (RemoteException unused) {
            }
        }
        displayWindowListenerController.mDisplayListeners.finishBroadcast();
        return i3;
    }

    public final void performLayout(boolean z) {
        Trace.traceBegin(32L, "performLayout");
        try {
            if (this.mLayoutNeeded) {
                this.mLayoutNeeded = false;
                int i = this.mLayoutSeq + 1;
                if (i < 0) {
                    i = 0;
                }
                this.mLayoutSeq = i;
                this.mTmpInitial = true;
                forAllWindows((Consumer) this.mPerformLayout, true);
                forAllWindows((Consumer) this.mPerformLayoutAttached, true);
                InputMonitor inputMonitor = this.mInputMonitor;
                inputMonitor.mUpdateInputWindowsNeeded = true;
                if (z) {
                    inputMonitor.updateInputWindowsLw(false);
                }
            }
        } finally {
            Trace.traceEnd(32L);
        }
    }

    public final void prepareAppTransition(int i, int i2) {
        boolean prepare;
        AppTransition appTransition = this.mAppTransition;
        if (appTransition.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            prepare = false;
        } else {
            appTransition.mNextAppTransitionRequests.add(Integer.valueOf(i));
            appTransition.mNextAppTransitionFlags = i2 | appTransition.mNextAppTransitionFlags;
            appTransition.updateBooster();
            appTransition.mHandler.removeCallbacks(
                    appTransition.mHandleAppTransitionTimeoutRunnable);
            appTransition.mHandler.postDelayed(
                    appTransition.mHandleAppTransitionTimeoutRunnable, 5000L);
            prepare = appTransition.prepare();
        }
        if (prepare && okToAnimate() && i != 0) {
            this.mSkipAppTransitionAnimation = false;
        }
    }

    @Override // com.android.server.wm.DisplayArea.Dimmable, com.android.server.wm.WindowContainer
    public final void prepareSurfaces() {
        Trace.traceBegin(32L, "prepareSurfaces");
        try {
            super.prepareSurfaces();
        } finally {
            Trace.traceEnd(32L);
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.ConfigurationContainer
    public final boolean providesMaxBounds() {
        return true;
    }

    public final void reParentWindowToken(WindowToken windowToken) {
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
            int i = displayContent != null ? displayContent.mDisplayId : -1;
            AccessibilityController accessibilityController =
                    this.mWmService.mAccessibilityController;
            int[] iArr = {i, this.mDisplayId};
            accessibilityController.getClass();
            accessibilityController.onSomeWindowResizedOrMovedWithCallingUid(
                    Binder.getCallingUid(), iArr);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0058, code lost:

       if (r5 == 524288) goto L32;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reconfigureDisplayLocked() {
        /*
            r9 = this;
            boolean r0 = r9.isReady()
            if (r0 != 0) goto L7
            return
        L7:
            r9.configureDisplayPolicy()
            r9.setLayoutNeeded()
            r0 = 0
            boolean r1 = r9.updateOrientation(r0)
            android.content.res.Configuration r2 = r9.getConfiguration()
            android.content.res.Configuration r3 = r9.mTmpConfiguration
            r3.setTo(r2)
            android.content.res.Configuration r3 = r9.mTmpConfiguration
            r9.computeScreenConfiguration(r3)
            android.content.res.Configuration r3 = r9.mTmpConfiguration
            int r3 = r2.diff(r3)
            r4 = 1
            if (r3 == 0) goto L2b
            r5 = r4
            goto L2c
        L2b:
            r5 = r0
        L2c:
            r1 = r1 | r5
            if (r1 == 0) goto Lab
            r9.mWaitingForConfig = r4
            boolean r1 = r9.mLastHasContent
            if (r1 == 0) goto L9e
            com.android.server.wm.TransitionController r1 = r9.mTransitionController
            boolean r1 = r1.isShellTransitionsEnabled()
            if (r1 == 0) goto L9e
            boolean r1 = com.samsung.android.rune.CoreRune.MW_SPLIT_FLEX_PANEL_MODE
            if (r1 == 0) goto L5b
            android.content.res.Configuration r1 = r9.mTmpConfiguration
            int r4 = r2.diff(r1)
            android.app.WindowConfiguration r5 = r2.windowConfiguration
            android.app.WindowConfiguration r1 = r1.windowConfiguration
            long r5 = r5.diff(r1, r0)
            r1 = 536870912(0x20000000, float:1.0842022E-19)
            if (r4 != r1) goto L5b
            r7 = 524288(0x80000, double:2.590327E-318)
            int r1 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r1 != 0) goto L5b
            goto La8
        L5b:
            android.app.WindowConfiguration r1 = r2.windowConfiguration
            android.graphics.Rect r1 = r1.getBounds()
            android.content.res.Configuration r2 = r9.mTmpConfiguration
            android.app.WindowConfiguration r2 = r2.windowConfiguration
            android.graphics.Rect r2 = r2.getBounds()
            com.android.server.wm.TransitionController r4 = r9.mTransitionController
            boolean r4 = r4.isCollecting()
            if (r4 != 0) goto L82
            android.window.TransitionRequestInfo$DisplayChange r4 = new android.window.TransitionRequestInfo$DisplayChange
            int r5 = r9.mDisplayId
            r4.<init>(r5)
            r4.setStartAbsBounds(r1)
            r4.setEndAbsBounds(r2)
            r9.requestChangeTransition(r3, r4)
            goto La8
        L82:
            com.android.server.wm.TransitionController r4 = r9.mTransitionController
            com.android.server.wm.Transition r4 = r4.mCollectingTransition
            android.util.ArrayMap r5 = r4.mChanges
            java.lang.Object r5 = r5.get(r9)
            com.android.server.wm.Transition$ChangeInfo r5 = (com.android.server.wm.Transition.ChangeInfo) r5
            if (r5 == 0) goto L92
            r5.mKnownConfigChanges = r3
        L92:
            com.android.server.wm.TransitionController r3 = r9.mTransitionController
            r3.getClass()
            com.android.server.wm.TransitionController.setDisplaySyncMethod(r1, r2, r9)
            r9.collectDisplayChange(r4)
            goto La8
        L9e:
            boolean r1 = r9.mLastHasContent
            if (r1 == 0) goto La8
            com.android.server.wm.WindowManagerService r1 = r9.mWmService
            r2 = -1
            r1.startFreezingDisplay(r0, r0, r2, r9)
        La8:
            r9.sendNewConfiguration()
        Lab:
            com.android.server.wm.WindowManagerService r9 = r9.mWmService
            com.android.server.wm.WindowSurfacePlacer r9 = r9.mWindowPlacerLocked
            r9.performSurfacePlacement(r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DisplayContent.reconfigureDisplayLocked():void");
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

    public final void registerPointerEventListener(
            WindowManagerPolicyConstants.PointerEventListener pointerEventListener) {
        PointerEventDispatcher pointerEventDispatcher = this.mPointerEventDispatcher;
        synchronized (pointerEventDispatcher.mListeners) {
            try {
                if (pointerEventDispatcher.mListeners.contains(pointerEventListener)) {
                    throw new IllegalStateException(
                            "registerInputEventListener: trying to register"
                                    + pointerEventListener
                                    + " twice.");
                }
                pointerEventDispatcher.mListeners.add(pointerEventListener);
                pointerEventDispatcher.mListenersArray = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void releaseSelfIfNeeded() {
        if (this.mRemoved) {
            if (this.mDisplayId == 4) {
                removeIfPossible();
            }
            if (!forAllRootTasks(new DisplayContent$$ExternalSyntheticLambda7(2))) {
                int[] iArr = new int[1];
                forAllRootTasks(new DisplayContent$$ExternalSyntheticLambda20(0, iArr));
                if (iArr[0] > 0) {
                    forAllRootTasks(new DisplayContent$$ExternalSyntheticLambda18(0));
                    return;
                }
            }
            if (getRootTask(WindowContainer.alwaysTruePredicate()) == null) {
                removeIfPossible();
            }
        }
    }

    public final void remove() {
        this.mRemoving = true;
        this.mRootWindowContainer.mTaskSupervisor.beginDeferResume();
        try {
            Task task =
                    (Task)
                            reduceOnAllTaskDisplayAreas(
                                    new DisplayContent$$ExternalSyntheticLambda3(), null, false);
            this.mRootWindowContainer.mTaskSupervisor.endDeferResume();
            this.mRemoved = true;
            ContentRecorder contentRecorder = this.mContentRecorder;
            if (contentRecorder != null) {
                if (CoreRune.FW_SCREENSHOT_FOR_HDR) {
                    contentRecorder.invalidateForRecording(false);
                }
                contentRecorder.unregisterListener();
                if (contentRecorder.mRecordedSurface != null) {
                    DisplayContent displayContent = contentRecorder.mDisplayContent;
                    ((SurfaceControl.Transaction)
                                    displayContent.mWmService.mTransactionFactory.get())
                            .remove(contentRecorder.mRecordedSurface)
                            .apply();
                    contentRecorder.mRecordedSurface = null;
                    contentRecorder.mContentRecordingSession = null;
                    WindowManagerService windowManagerService = displayContent.mWmService;
                    windowManagerService.mContentRecordingController
                            .setContentRecordingSessionLocked(null, windowManagerService);
                }
            }
            if (task != null) {
                task.resumeNextFocusAfterReparent();
            }
            releaseSelfIfNeeded();
            DisplayPolicy displayPolicy = this.mDisplayPolicy;
            DisplayContent displayContent2 = displayPolicy.mDisplayContent;
            displayContent2.mTransitionController.mLegacyListeners.remove(
                    displayPolicy.mAppTransitionListener);
            GestureNavigationSettingsObserver gestureNavigationSettingsObserver =
                    displayPolicy.mGestureNavigationSettingsObserver;
            Objects.requireNonNull(gestureNavigationSettingsObserver);
            DisplayPolicy$$ExternalSyntheticLambda3 displayPolicy$$ExternalSyntheticLambda3 =
                    new DisplayPolicy$$ExternalSyntheticLambda3(
                            gestureNavigationSettingsObserver, 1);
            DisplayPolicy.PolicyHandler policyHandler = displayPolicy.mHandler;
            policyHandler.post(displayPolicy$$ExternalSyntheticLambda3);
            ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver =
                    displayPolicy.mForceShowNavBarSettingsObserver;
            Objects.requireNonNull(forceShowNavBarSettingsObserver);
            policyHandler.post(
                    new DisplayPolicy$$ExternalSyntheticLambda5(
                            forceShowNavBarSettingsObserver, 1));
            if (!ViewRootImpl.CLIENT_TRANSIENT && !ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
                ImmersiveModeConfirmation.H h = displayPolicy.mImmersiveModeConfirmation.mHandler;
                h.removeMessages(1);
                h.removeMessages(2);
            }
            displayPolicy.mIsKnoxZtStarted = false;
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    new StringBuilder(
                            "release() >> KnoxZT mIsKnoxZtStarted is false for Display Id : "),
                    displayContent2.mDisplayId,
                    "WindowManager");
            if (displayPolicy.mService.mPointerLocationEnabled) {
                displayPolicy.setPointerLocationEnabled(false);
            }
            if (this.mAllSleepTokens.isEmpty()) {
                return;
            }
            this.mAllSleepTokens.forEach(new DisplayContent$$ExternalSyntheticLambda1(11, this));
            this.mAllSleepTokens.clear();
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[0]) {
                ProtoLogImpl_54989576.d(
                        ProtoLogGroup.WM_DEBUG_STATES, 3125477013281649777L, 0, null, null);
            }
            this.mAtmService.updateSleepIfNeededLocked();
        } catch (Throwable th) {
            this.mRootWindowContainer.mTaskSupervisor.endDeferResume();
            throw th;
        }
    }

    public void removeAllTasks() {
        forAllTasks(new DisplayContent$$ExternalSyntheticLambda18(1));
    }

    @Override // com.android.server.wm.WindowContainer
    public final void removeIfPossible() {
        if (shouldDeferRemoval()) {
            this.mDeferredRemoval = true;
        } else {
            removeImmediately();
        }
    }

    public final void removeImeSurfaceByTarget(WindowContainer windowContainer) {
        if (this.mImeScreenshot == null || windowContainer == null) {
            return;
        }
        if (windowContainer.asWindowState() == null
                || windowContainer.asWindowState().mAttrs.type != 3) {
            WindowState windowState = this.mImeScreenshot.mImeTarget;
            if (windowContainer == windowState
                    || windowContainer.getWindow(
                                    new DisplayContent$$ExternalSyntheticLambda5(0, windowState))
                            != null) {
                removeImeSurfaceImmediately();
            }
        }
    }

    public final void removeImeSurfaceImmediately() {
        ImeScreenshot imeScreenshot = this.mImeScreenshot;
        if (imeScreenshot != null) {
            imeScreenshot.removeImeSurface(getSyncTransaction());
            this.mImeScreenshot = null;
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final void removeImmediately() {
        DisplayContent displayContent;
        CoverPolicy coverPolicy;
        SensorManager sensorManager;
        DisplayRotation.FoldController.AnonymousClass2 anonymousClass2;
        this.mDeferredRemoval = false;
        try {
            this.mOpeningApps.clear();
            this.mClosingApps.clear();
            this.mChangingContainers.clear();
            this.mUnknownAppVisibilityController.mUnknownApps.clear();
            AppTransition appTransition = this.mAppTransition;
            appTransition.mHandler.removeCallbacks(
                    appTransition.mHandleAppTransitionTimeoutRunnable);
            this.mTransitionController.mLegacyListeners.remove(
                    this.mFixedRotationTransitionListener);
            handleAnimatingStoppedAndTransition();
            this.mWmService.stopFreezingDisplayLocked();
            DeviceStateController deviceStateController = this.mDeviceStateController;
            DisplayContent$$ExternalSyntheticLambda1 displayContent$$ExternalSyntheticLambda1 =
                    this.mDeviceStateConsumer;
            WindowManagerGlobalLock windowManagerGlobalLock = deviceStateController.mWmLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    deviceStateController.mDeviceStateCallbacks.remove(
                            displayContent$$ExternalSyntheticLambda1);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            super.removeImmediately();
            this.mPointerEventDispatcher.dispose();
            setRotationAnimation(null);
            setRemoteInsetsController(null);
            this.mOverlayLayer.release();
            this.mInputOverlayLayer.release();
            this.mA11yOverlayLayer.release();
            final InputMonitor inputMonitor = this.mInputMonitor;
            inputMonitor.mHandler.removeCallbacks(inputMonitor.mUpdateInputWindows);
            ((SurfaceControl.Transaction) inputMonitor.mService.mTransactionFactory.get())
                    .addWindowInfosReportedListener(
                            new Runnable() { // from class:
                                             // com.android.server.wm.InputMonitor$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    InputMonitor inputMonitor2 = InputMonitor.this;
                                    inputMonitor2.mService.mInputManager.onDisplayRemoved(
                                            inputMonitor2.mDisplayId);
                                }
                            })
                    .apply();
            inputMonitor.mDisplayRemoved = true;
            DisplayWindowListenerController displayWindowListenerController =
                    this.mWmService.mDisplayNotificationController;
            int beginBroadcast = displayWindowListenerController.mDisplayListeners.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    displayWindowListenerController
                            .mDisplayListeners
                            .getBroadcastItem(i)
                            .onDisplayRemoved(this.mDisplayId);
                } catch (RemoteException unused) {
                }
            }
            displayWindowListenerController.mDisplayListeners.finishBroadcast();
            DisplayRotation displayRotation = this.mDisplayRotation;
            if (DisplayRotationCoordinator.isSecondaryInternalDisplay(
                    displayRotation.mDisplayContent)) {
                displayRotation.mDisplayRotationCoordinator.mDefaultDisplayRotationChangedCallback =
                        null;
            }
            DisplayRotation.FoldController foldController = displayRotation.mFoldController;
            if (foldController != null
                    && (sensorManager = foldController.mSensorManager) != null
                    && (anonymousClass2 = foldController.mHingeAngleSensorEventListener) != null) {
                sensorManager.unregisterListener(anonymousClass2);
            }
            AccessibilityController accessibilityController =
                    this.mWmService.mAccessibilityController;
            int i2 = this.mDisplayId;
            accessibilityController.mIsImeVisibleArray.delete(i2);
            accessibilityController.mFocusedWindow.remove(i2);
            KeyguardController keyguardController =
                    this.mRootWindowContainer.mTaskSupervisor.mKeyguardController;
            int i3 = this.mDisplayId;
            KeyguardController.KeyguardDisplayState keyguardDisplayState =
                    (KeyguardController.KeyguardDisplayState)
                            keyguardController.mDisplayStates.get(i3);
            if (keyguardDisplayState != null) {
                keyguardDisplayState.mTopOccludesActivity = null;
                keyguardDisplayState.mDismissingKeyguardActivity = null;
                keyguardDisplayState.mTopTurnScreenOnActivity = null;
                keyguardDisplayState.mSleepTokenAcquirer.release(keyguardDisplayState.mDisplayId);
                keyguardController.mDisplayStates.remove(i3);
            }
            WallpaperController wallpaperController = this.mWallpaperController;
            Display display = this.mDisplay;
            wallpaperController.getClass();
            if (display != null && display.getType() == 1) {
                wallpaperController.mLargestDisplaySize = null;
            }
            this.mWmService.mDisplayWindowSettings.onDisplayRemoved(this);
            this.mAtmService.mFreeformController.mFreeformCornerRadius.remove(this.mDisplayId);
            int i4 = this.mDisplayId;
            if (i4 == 4
                    && (displayContent = this.mRootWindowContainer.mDefaultDisplay) != null
                    && (coverPolicy = displayContent.mDisplayPolicy.mExt.mCoverPolicy) != null
                    && i4 == 4) {
                coverPolicy.mViewCoverDisplay = null;
            }
            MultiWindowPointerEventListener multiWindowPointerEventListener =
                    this.mMultiWindowPointerEventListener;
            if (multiWindowPointerEventListener != null) {
                unregisterPointerEventListener(multiWindowPointerEventListener);
            }
            this.mDisplayReady = false;
            getPendingTransaction().apply();
            this.mWmService.mWindowPlacerLocked.requestTraversal();
            AppCompatCameraPolicy appCompatCameraPolicy = this.mAppCompatCameraPolicy;
            DisplayRotationCompatPolicy displayRotationCompatPolicy =
                    appCompatCameraPolicy.mDisplayRotationCompatPolicy;
            if (displayRotationCompatPolicy != null) {
                displayRotationCompatPolicy.mCameraStateMonitor.mCameraStateListeners.remove(
                        displayRotationCompatPolicy);
                displayRotationCompatPolicy.mActivityRefresher.mEvaluators.remove(
                        displayRotationCompatPolicy);
                displayRotationCompatPolicy.mIsRunning = false;
            }
            CameraCompatFreeformPolicy cameraCompatFreeformPolicy =
                    appCompatCameraPolicy.mCameraCompatFreeformPolicy;
            if (cameraCompatFreeformPolicy != null) {
                cameraCompatFreeformPolicy.mCameraStateMonitor.mCameraStateListeners.remove(
                        cameraCompatFreeformPolicy);
                cameraCompatFreeformPolicy.mActivityRefresher.mEvaluators.remove(
                        cameraCompatFreeformPolicy);
                cameraCompatFreeformPolicy.mIsRunning = false;
            }
            CameraStateMonitor cameraStateMonitor = appCompatCameraPolicy.mCameraStateMonitor;
            if (cameraStateMonitor != null) {
                CameraManager cameraManager = cameraStateMonitor.mCameraManager;
                if (cameraManager != null) {
                    cameraManager.unregisterAvailabilityCallback(
                            cameraStateMonitor.mAvailabilityCallback);
                }
                cameraStateMonitor.mIsRunning = false;
            }
        } catch (Throwable th2) {
            this.mDisplayReady = false;
            throw th2;
        }
    }

    public final WindowToken removeWindowToken(IBinder iBinder, boolean z) {
        WindowToken windowToken = (WindowToken) this.mTokenMap.remove(iBinder);
        if (windowToken != null && windowToken.asActivityRecord() == null) {
            windowToken.setExiting(z);
        }
        return windowToken;
    }

    public final void requestChangeTransition(
            int i, TransitionRequestInfo.DisplayChange displayChange) {
        TransitionController transitionController = this.mTransitionController;
        Transition createTransition = transitionController.createTransition(6, 0);
        transitionController.requestStartTransition(createTransition, null, null, displayChange);
        if (displayChange != null) {
            Rect startAbsBounds = displayChange.getStartAbsBounds();
            Rect endAbsBounds = displayChange.getEndAbsBounds();
            if (startAbsBounds != null && endAbsBounds != null) {
                TransitionController.setDisplaySyncMethod(startAbsBounds, endAbsBounds, this);
            }
        }
        createTransition.collect(this, false);
        this.mAtmService.startPowerMode(2);
        final AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        if (asyncRotationController != null && asyncRotationController.mRotator != null) {
            DisplayContent displayContent = asyncRotationController.mDisplayContent;
            int rotation = displayContent.getWindowConfiguration().getRotation();
            if (asyncRotationController.mOriginalRotation != rotation) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        rotation, "Update original rotation ", "AsyncRotation_WindowManager");
                asyncRotationController.mOriginalRotation = rotation;
                displayContent.forAllWindows(
                        new Consumer() { // from class:
                                         // com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AsyncRotationController asyncRotationController2 =
                                        AsyncRotationController.this;
                                WindowState windowState = (WindowState) obj;
                                asyncRotationController2.getClass();
                                if (windowState.mForceSeamlesslyRotate
                                        && windowState.mHasSurface
                                        && !asyncRotationController2.mTargetWindowTokens
                                                .containsKey(windowState.mToken)) {
                                    WindowToken windowToken = windowState.mToken;
                                    if (windowToken.mIsPortraitWindowToken) {
                                        return;
                                    }
                                    AsyncRotationController.Operation operation =
                                            new AsyncRotationController.Operation(1);
                                    operation.mLeash = windowToken.mSurfaceControl;
                                    asyncRotationController2.mTargetWindowTokens.put(
                                            windowToken, operation);
                                }
                            }
                        },
                        true);
                asyncRotationController.mRotator = null;
                asyncRotationController.mIsStartTransactionCommitted = false;
                asyncRotationController.mIsSyncDrawRequested = false;
                asyncRotationController.keepAppearanceInPreviousRotation();
            }
        }
        if (this.mFixedRotationLaunchingApp != null) {
            Transition.ChangeInfo changeInfo =
                    (Transition.ChangeInfo) createTransition.mChanges.get(this);
            if (changeInfo != null) {
                changeInfo.mFlags |= 1;
                createTransition.onSeamlessRotating(getDisplayContent());
            }
            AsyncRotationController asyncRotationController2 = this.mAsyncRotationController;
            if (asyncRotationController2 != null) {
                asyncRotationController2.keepAppearanceInPreviousRotation();
            }
        } else if (isRotationChanging()) {
            if (displayChange != null
                    && this.mDisplayRotation.shouldRotateSeamlessly(
                            displayChange.getStartRotation(),
                            displayChange.getEndRotation(),
                            false)) {
                createTransition.onSeamlessRotating(this);
                if (CoreRune.FW_FLEXIBLE_DUAL_MODE) {
                    this.mWmService.mExt.getClass();
                    throw null;
                }
            }
            this.mWmService.mLatencyTracker.onActionStart(6);
            TransitionController.TransitionMetricsReporter transitionMetricsReporter =
                    transitionController.mTransitionMetricsReporter;
            Transition.Token token = createTransition.mToken;
            LongConsumer longConsumer =
                    new LongConsumer() { // from class:
                                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda23
                        @Override // java.util.function.LongConsumer
                        public final void accept(long j) {
                            DisplayContent.this.mWmService.mLatencyTracker.onActionEnd(6);
                        }
                    };
            synchronized (transitionMetricsReporter.mMetricConsumers) {
                transitionMetricsReporter.mMetricConsumers.put(token, longConsumer);
            }
            startAsyncRotation(false);
        }
        Transition.ChangeInfo changeInfo2 =
                (Transition.ChangeInfo) createTransition.mChanges.get(this);
        if (changeInfo2 != null) {
            changeInfo2.mKnownConfigChanges = i;
        }
    }

    public final void requestDisplayUpdate(Runnable runnable) {
        this.mAtmService.deferWindowLayout();
        try {
            this.mDisplayUpdater.updateDisplayInfo(runnable);
        } finally {
            this.mAtmService.continueWindowLayout();
        }
    }

    public final void rotateBounds(int i, Rect rect, int i2) {
        Rect rect2 = this.mTmpRect;
        getBounds(rect2);
        int deltaRotation = RotationUtils.deltaRotation(this.mDisplayInfo.rotation, i);
        if (deltaRotation == 1 || deltaRotation == 3) {
            rect2.set(0, 0, rect2.height(), rect2.width());
        }
        RotationUtils.rotateBounds(rect, this.mTmpRect, i, i2);
    }

    public final int rotationForActivityInDifferentOrientation(ActivityRecord activityRecord) {
        DisplayRotation displayRotation;
        int i;
        int rotationForOrientation;
        ActivityRecord activity;
        if (this.mTransitionController.useShellTransitionsRotation()) {
            return -1;
        }
        int overrideOrientation = activityRecord.getOverrideOrientation();
        if (!WindowManagerService.ENABLE_FIXED_ROTATION_TRANSFORM
                || shouldIgnoreOrientationRequest(overrideOrientation)
                || (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY
                        && this.mAtmService.mMultiTaskingAppCompatController.mOrientationPolicy
                                .shouldIgnoreOrientationRequest(
                                        overrideOrientation, activityRecord))) {
            return -1;
        }
        if (this.isDefaultDisplay && isDexMode()) {
            return -1;
        }
        if (overrideOrientation == 3
                && (activity =
                                getActivity(
                                        new DisplayContent$$ExternalSyntheticLambda7(4),
                                        activityRecord,
                                        false,
                                        true))
                        != null) {
            activityRecord = activity;
        }
        if (!activityRecord.inMultiWindowMode()
                && activityRecord.getRequestedConfigurationOrientation(true)
                        != getConfiguration().orientation) {
            if ((CoreRune.BAIDU_CARLIFE && isCarLifeDisplay())
                    || (rotationForOrientation =
                                    displayRotation.rotationForOrientation(
                                            activityRecord.getRequestedOrientation(),
                                            (i =
                                                    (displayRotation = this.mDisplayRotation)
                                                            .mRotation)))
                            == i) {
                return -1;
            }
            return rotationForOrientation;
        }
        return -1;
    }

    public final void sendApplicationFocusMonitoringIntent(
            int i, String str, String str2, boolean z) {
        final Intent intent =
                new Intent("com.samsung.android.knox.intent.action.APPLICATION_FOCUS_CHANGE");
        intent.putExtra(
                "com.samsung.android.knox.intent.extra.APPLICATION_FOCUS_COMPONENT_NAME", str);
        intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_FOCUS_STATUS", str2);
        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", i);
        intent.putExtra("com.samsung.android.knox.intent.extra.APPLICATION_FOCUS_DEX_MODE", z);
        this.mWmService.mH.post(
                new Runnable() { // from class: com.android.server.wm.DisplayContent.4
                    @Override // java.lang.Runnable
                    public final void run() {
                        DisplayContent.this.mWmService.mContext.sendBroadcastAsUser(
                                intent,
                                UserHandle.ALL,
                                "com.samsung.android.knox.permission.KNOX_APP_MGMT");
                    }
                });
    }

    public final boolean sendNewConfiguration() {
        if (!isReady() || this.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange()) {
            return false;
        }
        Transition.ReadyCondition readyCondition =
                this.mTransitionController.isCollecting()
                        ? new Transition.ReadyCondition("displayConfig", this)
                        : null;
        if (readyCondition != null) {
            this.mTransitionController.waitFor(readyCondition);
        } else if (this.mTransitionController.isShellTransitionsEnabled() && this.mLastHasContent) {
            Slog.e("WindowManager", "Display reconfigured outside of a transition: " + this);
        }
        boolean updateDisplayOverrideConfigurationLocked =
                updateDisplayOverrideConfigurationLocked();
        if (readyCondition != null) {
            readyCondition.meet();
        }
        if (updateDisplayOverrideConfigurationLocked) {
            return true;
        }
        clearFixedRotationLaunchingApp();
        if (this.mWaitingForConfig) {
            this.mWaitingForConfig = false;
            this.mWmService.mLastFinishedFreezeSource = "config-unchanged";
            setLayoutNeeded();
            this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
        }
        return false;
    }

    public final void setContentRecordingSession(ContentRecordingSession contentRecordingSession) {
        if (this.mContentRecorder == null) {
            ContentRecorder.RemoteMediaProjectionManagerWrapper
                    remoteMediaProjectionManagerWrapper =
                            new ContentRecorder.RemoteMediaProjectionManagerWrapper(
                                    this.mDisplayId);
            if (new DisplayManagerFlags().mConnectedDisplayManagementFlagState.isEnabled()
                    && !new DisplayManagerFlags().mPixelAnisotropyCorrectionEnabled.isEnabled()) {
                int i = this.mDisplayInfo.type;
            }
            this.mContentRecorder =
                    new ContentRecorder(this, remoteMediaProjectionManagerWrapper, false);
        }
        this.mContentRecorder.mContentRecordingSession = contentRecordingSession;
    }

    public final void setDisplayInfoOverride() {
        this.mWmService.mDisplayManagerInternal.setDisplayInfoOverrideFromWindowManager(
                this.mDisplayId, this.mDisplayInfo);
        if (this.mLastDisplayInfoOverride == null) {
            this.mLastDisplayInfoOverride = new DisplayInfo();
        }
        this.mLastDisplayInfoOverride.copyFrom(this.mDisplayInfo);
    }

    public final void setFadeInOutAnimationNeeded(String str, boolean z) {
        if (this.mFadeInOutAnimationNeeded != z) {
            this.mFadeInOutAnimationNeeded = z;
            Slog.d("WindowManager", "setFadeInOutAnimationNeeded: " + z + ", reason=" + str);
        }
    }

    public final void setFixedRotationLaunchingApp(int i, ActivityRecord activityRecord) {
        ActivityRecord activityRecord2 = this.mFixedRotationLaunchingApp;
        if (activityRecord2 == activityRecord
                && activityRecord.getWindowConfiguration().getRotation() == i) {
            return;
        }
        if (activityRecord2 != null
                && activityRecord2.getWindowConfiguration().getRotation() == i
                && activityRecord2.inTransitionSelfOrParent()) {
            activityRecord.linkFixedRotationTransform(activityRecord2);
            if (activityRecord != this.mFixedRotationTransitionListener.mAnimatingRecents) {
                setFixedRotationLaunchingAppUnchecked(i, activityRecord);
                return;
            }
            return;
        }
        if (!activityRecord.hasFixedRotationTransform()) {
            startFixedRotationTransform(activityRecord, i);
        }
        setFixedRotationLaunchingAppUnchecked(i, activityRecord);
        if (activityRecord2 != null) {
            activityRecord2.finishFixedRotationTransform(null);
        }
    }

    public final void setFixedRotationLaunchingAppUnchecked(int i, ActivityRecord activityRecord) {
        WindowState windowState;
        ActivityRecord activityRecord2 = this.mFixedRotationLaunchingApp;
        if (activityRecord2 == null && activityRecord != null) {
            DisplayWindowListenerController displayWindowListenerController =
                    this.mWmService.mDisplayNotificationController;
            int beginBroadcast = displayWindowListenerController.mDisplayListeners.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    displayWindowListenerController
                            .mDisplayListeners
                            .getBroadcastItem(i2)
                            .onFixedRotationStarted(this.mDisplayId, i);
                } catch (RemoteException unused) {
                }
            }
            displayWindowListenerController.mDisplayListeners.finishBroadcast();
            startAsyncRotation(
                    activityRecord == this.mFixedRotationTransitionListener.mAnimatingRecents
                            || this.mTransitionController.isTransientLaunch(activityRecord));
        } else if (activityRecord2 != null && activityRecord == null) {
            DisplayWindowListenerController displayWindowListenerController2 =
                    this.mWmService.mDisplayNotificationController;
            int beginBroadcast2 =
                    displayWindowListenerController2.mDisplayListeners.beginBroadcast();
            for (int i3 = 0; i3 < beginBroadcast2; i3++) {
                try {
                    displayWindowListenerController2
                            .mDisplayListeners
                            .getBroadcastItem(i3)
                            .onFixedRotationFinished(this.mDisplayId);
                } catch (RemoteException unused2) {
                }
            }
            displayWindowListenerController2.mDisplayListeners.finishBroadcast();
            if (!this.mTransitionController.hasCollectingRotationChange(
                    this.mDisplayRotation.mRotation, this)) {
                finishAsyncRotationIfPossible();
            }
        }
        StringBuilder sb = new StringBuilder("setFixedRotationLaunchingAppUnchecked, rotation=");
        sb.append(i);
        sb.append(", r=");
        sb.append(activityRecord);
        sb.append(", caller=");
        ActivityManagerService$$ExternalSyntheticOutline0.m(5, sb, "WindowManager");
        this.mFixedRotationLaunchingApp = activityRecord;
        if (isKeyguardGoingAway()
                && (windowState = this.mInputMethodWindow) != null
                && windowState.mHasSurface) {
            onShowImeRequested();
        }
    }

    public final boolean setFocusedApp(ActivityRecord activityRecord) {
        String activityRecord2;
        String str;
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
                Slog.d("SPEG", "Do not set focus");
                return false;
            }
            this.mOrientationRequestingTaskDisplayArea = activityRecord.getDisplayArea();
        }
        ActivityRecord activityRecord3 = this.mFocusedApp;
        if (activityRecord3 == activityRecord) {
            return false;
        }
        checkFocusMonitoringPolicy(activityRecord3, "lost");
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_FOCUS_LIGHT_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT,
                    7634130879993688940L,
                    4,
                    null,
                    String.valueOf(activityRecord),
                    Long.valueOf(this.mDisplayId),
                    String.valueOf(Debug.getCallers(4)));
        }
        ActivityRecord activityRecord4 = this.mFocusedApp;
        ComponentName componentName = null;
        Task task = activityRecord4 != null ? activityRecord4.task : null;
        Task task2 = activityRecord != null ? activityRecord.task : null;
        this.mFocusedApp = activityRecord;
        if (task != task2) {
            if (task != null) {
                task.onAppFocusChanged(false);
            }
            if (task2 != null) {
                task2.onAppFocusChanged(true);
            }
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        inputMonitor.mService.mInputManager.setFocusedApplication(
                inputMonitor.mDisplayId,
                activityRecord != null ? activityRecord.getInputApplicationHandle(true) : null);
        PhoneWindowManagerExt phoneWindowManagerExt = this.mWmService.mExt.mPolicyExt;
        if (activityRecord != null
                && (activityRecord2 = activityRecord.toString()) != null
                && activityRecord2.length() > 26) {
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
                if (split.length == 2) {
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
                    componentName = new ComponentName(str2, str);
                }
            } catch (NullPointerException e) {
                Slog.e("WindowManager", "package and class name's parsing error. " + e);
            }
        }
        phoneWindowManagerExt.mTopActivity = componentName;
        checkFocusMonitoringPolicy(this.mFocusedApp, "gained");
        return true;
    }

    public final void setForcedDensity(int i, int i2) {
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
        this.mWmService.mDisplayWindowSettings.setForcedDensity(this.mDisplayInfo, i, i2);
    }

    public final void setForcedScalingMode(int i) {
        if (i != 1) {
            i = 0;
        }
        boolean z = i != 0;
        this.mDisplayScalingDisabled = z;
        Slog.i("WindowManager", "Using display scaling mode: ".concat(z ? "off" : "auto"));
        reconfigureDisplayLocked();
        DisplayWindowSettings displayWindowSettings = this.mWmService.mDisplayWindowSettings;
        displayWindowSettings.getClass();
        if (this.isDefaultDisplay) {
            Settings.Global.putInt(
                    displayWindowSettings.mService.mContext.getContentResolver(),
                    "display_scaling_force",
                    i);
        }
        DisplayInfo displayInfo = this.mDisplayInfo;
        DisplayWindowSettingsProvider displayWindowSettingsProvider =
                displayWindowSettings.mSettingsProvider;
        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings =
                displayWindowSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mForcedScalingMode = Integer.valueOf(i);
        displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
    }

    public final void setForcedSize(
            int i, int i2, float f, float f2, boolean z, boolean z2, boolean z3) {
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
                (this.mInitialDisplayWidth == i5 && this.mInitialDisplayHeight == i3)
                        ? false
                        : true;
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
            this.mAtmService
                            .mMultiTaskingAppCompatController
                            .mSizeCompatModePolicy
                            .mAvoidAppCompatDisplayInsets =
                    true;
        }
        boolean z5 = CoreRune.FW_VRR_RESOLUTION_POLICY_FOR_SHELL_TRANSITION;
        final IBinder displayToken =
                (z5 && this.isDefaultDisplay)
                        ? SurfaceControl.getDisplayToken(this.mDisplayInfo.address)
                        : null;
        if (z5
                && displayToken != null
                && (i4 != this.mBaseDisplayWidth || i9 != this.mBaseDisplayHeight)) {
            ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get())
                    .startChangeResolution(displayToken, true)
                    .apply();
        }
        Slog.i("WindowManager", "Using new display size: " + i4 + "x" + i9);
        updateBaseDisplayMetrics(
                i4,
                i9,
                this.mBaseDisplayDensity,
                f != FullScreenMagnificationGestureHandler.MAX_SCALE
                        ? f
                        : this.mBaseDisplayPhysicalXDpi,
                f2 != FullScreenMagnificationGestureHandler.MAX_SCALE
                        ? f2
                        : this.mBaseDisplayPhysicalYDpi);
        reconfigureDisplayLocked();
        if (z3) {
            this.mAtmService
                            .mMultiTaskingAppCompatController
                            .mSizeCompatModePolicy
                            .mAvoidAppCompatDisplayInsets =
                    false;
        }
        if (z5 && displayToken != null) {
            SurfaceControl.TransactionCommittedListener transactionCommittedListener =
                    new SurfaceControl
                            .TransactionCommittedListener() { // from class:
                                                              // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda35
                        @Override // android.view.SurfaceControl.TransactionCommittedListener
                        public final void onTransactionCommitted() {
                            DisplayContent displayContent = DisplayContent.this;
                            ((SurfaceControl.Transaction)
                                            displayContent.mWmService.mTransactionFactory.get())
                                    .startChangeResolution(displayToken, false)
                                    .apply();
                        }
                    };
            if (inTransition() && getSyncTransaction() == this.mSyncTransaction) {
                getSyncTransaction()
                        .addTransactionCommittedListener(
                                new HandlerExecutor(this.mWmService.mH),
                                transactionCommittedListener);
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

    public final void setForcedSizeDensity(int i, int i2, int i3, boolean z, int i4, boolean z2) {
        this.mIsDensityForced = i3 != this.mInitialDisplayDensity;
        this.mBaseDisplayDensity = i3;
        StringBuilder m =
                ArrayUtils$$ExternalSyntheticOutline0.m(
                        i, i2, "Using new display size & density : ", "x", " ");
        m.append(i3);
        m.append("dp saveSize=");
        m.append(z);
        m.append(" saveDensity=");
        m.append(z2);
        m.append(" forcedHideCutout=");
        m.append(i4);
        Slog.i("WindowManager", m.toString());
        try {
            this.mWmService.mAtmService.deferWindowLayout();
            this.mForcedHideCutout = i4;
            setForcedSize(
                    i,
                    i2,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    z,
                    true,
                    true);
            this.mNeedImmediateDisplayUpdate = true;
            if (z2) {
                this.mWmService.mDisplayWindowSettings.setForcedDensity(this.mDisplayInfo, i3, 0);
            }
        } finally {
            this.mWmService.mAtmService.continueWindowLayout();
        }
    }

    @Override // com.android.server.wm.DisplayArea
    public final boolean setIgnoreOrientationRequest(boolean z) {
        if (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY
                && this.mSetIgnoreOrientationRequestOverride
                && this.mSetIgnoreOrientationRequest == z) {
            this.mWmService.mDisplayWindowSettings.setNullableIgnoreOrientationRequest(
                    this, Boolean.valueOf(z));
            this.mSetIgnoreOrientationRequestOverride = false;
        }
        if (this.mSetIgnoreOrientationRequest == z) {
            return false;
        }
        boolean ignoreOrientationRequest = super.setIgnoreOrientationRequest(z);
        this.mWmService.mDisplayWindowSettings.setNullableIgnoreOrientationRequest(
                this, Boolean.valueOf(this.mSetIgnoreOrientationRequest));
        return ignoreOrientationRequest;
    }

    public final void setIgnoreOrientationRequestOverrideIfNeeded() {
        this.mAtmService.mMultiTaskingAppCompatController.mOrientationPolicy.getClass();
        boolean z =
                !this.isDefaultDisplay
                        ? false
                        : CoreRune.MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS;
        this.mSetIgnoreOrientationRequestOverride = z;
        super.setIgnoreOrientationRequest(z);
    }

    public void setImeControlTarget(InsetsControlTarget insetsControlTarget) {
        this.mImeControlTarget = insetsControlTarget;
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
                            new WindowContainerListener() { // from class:
                                                            // com.android.server.wm.DisplayContent.2
                                @Override // com.android.server.wm.WindowContainerListener
                                public final void onVisibleRequestedChanged(boolean z) {
                                    WindowManagerService windowManagerService =
                                            DisplayContent.this.mWmService;
                                    WindowState windowState2 = windowState;
                                    IBinder asBinder = windowState2.mClient.asBinder();
                                    ActivityRecord activityRecord = windowState2.mActivityRecord;
                                    windowManagerService.dispatchImeInputTargetVisibilityChanged(
                                            asBinder,
                                            z,
                                            activityRecord != null && activityRecord.finishing);
                                }
                            });
            this.mImeTargetTokenListenerPair = pair2;
            windowState.mToken.registerWindowContainerListener(
                    (WindowContainerListener) pair2.second);
            this.mWmService.dispatchImeInputTargetVisibilityChanged(
                    windowState.mClient.asBinder(), windowState.isVisible(), false);
        }
        SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
        InputTarget inputTarget2 = this.mImeInputTarget;
        if (this.mImeWindowsContainer.setCanScreenshot(
                pendingTransaction, inputTarget2 == null || inputTarget2.canScreenshotIme())) {
            this.mWmService.requestTraversal();
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
            if (this.mImeLayeringTarget.inTransitionSelfOrParent() || z) {
                showImeScreenshot();
            }
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_DEBUG_IME,
                    4464269036743635127L,
                    0,
                    null,
                    String.valueOf(windowState));
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
                insetsStateController.mState.isSourceOrDefaultVisible(
                        InsetsSource.ID_IME, WindowInsets.Type.ime()));
        updateImeControlTarget(z2);
    }

    public final void setInputMethodWindowLocked(WindowState windowState) {
        this.mInputMethodWindow = windowState;
        ImeInsetsSourceProvider imeSourceProvider =
                this.mInsetsStateController.getImeSourceProvider();
        final DisplayPolicy displayPolicy = this.mDisplayPolicy;
        displayPolicy.getClass();
        imeSourceProvider.setWindowContainer(
                windowState,
                new TriFunction() { // from class:
                                    // com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda15
                    public final Object apply(Object obj, Object obj2, Object obj3) {
                        Rect rect = (Rect) obj3;
                        DisplayPolicy.this.getClass();
                        WindowState asWindowState = ((WindowContainer) obj2).asWindowState();
                        if (asWindowState == null) {
                            throw new IllegalArgumentException(
                                    "IME insets must be provided by a window.");
                        }
                        rect.inset(asWindowState.mGivenContentInsets);
                        return 0;
                    }
                },
                null);
        computeImeTarget(true);
        updateImeControlTarget(false);
    }

    public void setLastHasContent() {
        this.mLastHasContent = true;
    }

    public final void setLayoutNeeded() {
        WindowSurfacePlacer windowSurfacePlacer = this.mWmService.mWindowPlacerLocked;
        if (windowSurfacePlacer.mPrintLayoutCaller) {
            windowSurfacePlacer.mPrintLayoutCaller = false;
            Slog.w(
                    "WindowManager",
                    "setLayoutNeeded: d" + this.mDisplayId + ", callers=" + Debug.getCallers(3));
        }
        this.mLayoutNeeded = true;
    }

    public final void setRemoteInsetsController(
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

    public final void setRotationAnimation(ScreenRotationAnimation screenRotationAnimation) {
        ScreenRotationAnimation screenRotationAnimation2 = this.mScreenRotationAnimation;
        this.mScreenRotationAnimation = screenRotationAnimation;
        if (screenRotationAnimation2 != null) {
            screenRotationAnimation2.kill();
        }
        if (screenRotationAnimation == null
                || screenRotationAnimation.mScreenshotLayer == null
                || !isRotationChanging()) {
            return;
        }
        startAsyncRotation(false);
    }

    public final boolean shouldDeferRemoval() {
        if (isAnimating(3)) {
            return true;
        }
        TransitionController transitionController = this.mTransitionController;
        Transition transition = transitionController.mCollectingTransition;
        if (transition != null && transition.mTargetDisplays.contains(this)) {
            return true;
        }
        for (int size = transitionController.mWaitingTransitions.size() - 1; size >= 0; size--) {
            if (((Transition) transitionController.mWaitingTransitions.get(size))
                    .mTargetDisplays.contains(this)) {
                return true;
            }
        }
        for (int size2 = transitionController.mPlayingTransitions.size() - 1; size2 >= 0; size2--) {
            if (((Transition) transitionController.mPlayingTransitions.get(size2))
                    .mTargetDisplays.contains(this)) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldDestroyContentOnRemove() {
        return this.mDisplay.getRemoveMode() == 1;
    }

    public final boolean shouldImeAttachedToApp() {
        InputTarget inputTarget;
        WindowState windowState;
        boolean equals;
        WindowState windowState2;
        StartingData startingData;
        if (this.mImeWindowsContainer.isOrganized()) {
            return false;
        }
        MagnificationSpec magnificationSpec = this.mMagnificationSpec;
        if ((magnificationSpec != null && magnificationSpec.scale >= 1.0f)
                || (inputTarget = this.mImeInputTarget) == null
                || !inputTarget.shouldControlIme()
                || (windowState = this.mImeLayeringTarget) == null
                || windowState.mActivityRecord == null
                || windowState.getWindowingMode() != 1
                || !this.mImeLayeringTarget
                        .getBounds()
                        .equals(this.mImeWindowsContainer.getBounds())) {
            return false;
        }
        WindowState windowState3 = this.mImeLayeringTarget;
        Rect fixedRotationTransformDisplayBounds =
                windowState3.mToken.getFixedRotationTransformDisplayBounds();
        if (fixedRotationTransformDisplayBounds != null) {
            equals = fixedRotationTransformDisplayBounds.equals(windowState3.getBounds());
        } else {
            DisplayArea displayArea = windowState3.getDisplayArea();
            equals =
                    displayArea == null
                            ? windowState3
                                    .getDisplayContent()
                                    .getBounds()
                                    .equals(windowState3.getBounds())
                            : displayArea.getBounds().equals(windowState3.getBounds());
        }
        if (equals) {
            return !CoreRune.MW_EMBED_ACTIVITY
                    || (startingData = (windowState2 = this.mImeLayeringTarget).mStartingData)
                            == null
                    || startingData.mAssociatedTask == null
                    || !windowState2.mActivityRecord.isSplitEmbedded();
        }
        return false;
    }

    public final boolean shouldSleep() {
        int[] iArr = new int[1];
        forAllRootTasks(new DisplayContent$$ExternalSyntheticLambda20(0, iArr));
        return (iArr[0] == 0 || !this.mAllSleepTokens.isEmpty())
                && this.mAtmService.mRunningVoice == null;
    }

    public final boolean shouldSyncRotationChange(WindowState windowState) {
        WindowToken windowToken;
        AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        return asyncRotationController == null
                || !((windowToken = windowState.mToken) == asyncRotationController.mNavBarToken
                        || ((windowState.mForceSeamlesslyRotate
                                        && asyncRotationController.mTransitionOp == 0)
                                || asyncRotationController.mTargetWindowTokens.containsKey(
                                        windowToken)));
    }

    public final boolean shouldWaitForSystemDecorWindowsOnBoot() {
        if (!this.isDefaultDisplay && !supportsSystemDecorations()) {
            return false;
        }
        final SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        sparseBooleanArray.put(2040, true);
        if (getWindow(
                        new Predicate() { // from class:
                                          // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda34
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean z;
                                DisplayContent displayContent = DisplayContent.this;
                                SparseBooleanArray sparseBooleanArray2 = sparseBooleanArray;
                                WindowState windowState = (WindowState) obj;
                                displayContent.getClass();
                                boolean z2 = windowState.isVisible() && !windowState.mObscured;
                                boolean isDrawn = windowState.isDrawn();
                                if (z2 && !isDrawn) {
                                    if (ProtoLogImpl_54989576.Cache.WM_DEBUG_BOOT_enabled[0]) {
                                        ProtoLogImpl_54989576.d(
                                                ProtoLogGroup.WM_DEBUG_BOOT,
                                                2432701541536053712L,
                                                1,
                                                null,
                                                Long.valueOf(windowState.mAttrs.type));
                                    }
                                    return true;
                                }
                                if (!isDrawn) {
                                    return false;
                                }
                                int i = windowState.mAttrs.type;
                                if (i == 1 || i == 2013 || i == 2021) {
                                    sparseBooleanArray2.put(i, true);
                                    return false;
                                }
                                if (i != 2040) {
                                    return false;
                                }
                                PhoneWindowManager phoneWindowManager =
                                        (PhoneWindowManager) displayContent.mWmService.mPolicy;
                                synchronized (phoneWindowManager.mLock) {
                                    z = phoneWindowManager.mKeyguardDrawnOnce;
                                }
                                sparseBooleanArray2.put(2040, z);
                                return false;
                            }
                        })
                != null) {
            return true;
        }
        boolean z =
                this.mWmService
                                .mContext
                                .getResources()
                                .getBoolean(R.bool.config_enhanced_iwlan_handover_check)
                        && this.mWmService
                                .mContext
                                .getResources()
                                .getBoolean(
                                        R.bool.config_debugEnableAutomaticSystemServerHeapDumps);
        boolean z2 = sparseBooleanArray.get(2021);
        boolean z3 = sparseBooleanArray.get(1);
        boolean z4 = sparseBooleanArray.get(2013);
        boolean z5 = sparseBooleanArray.get(2040);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_SCREEN_ON_enabled[2]) {
            WindowManagerService windowManagerService = this.mWmService;
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_DEBUG_SCREEN_ON,
                    5683557566110711213L,
                    16383,
                    null,
                    Boolean.valueOf(windowManagerService.mSystemBooted),
                    Boolean.valueOf(windowManagerService.mShowingBootMessages),
                    Boolean.valueOf(z2),
                    Boolean.valueOf(z3),
                    Boolean.valueOf(z4),
                    Boolean.valueOf(z),
                    Boolean.valueOf(z5));
        }
        boolean z6 = this.mWmService.mSystemBooted;
        if (z6 || z2) {
            return z6 && (!(z3 || z5) || (z && !z4));
        }
        return true;
    }

    public final void showImeScreenshot() {
        WindowState windowState;
        if (shouldImeAttachedToApp()
                && ((PhoneWindowManager) this.mWmService.mPolicy)
                        .mDefaultDisplayPolicy
                        .mScreenOnEarly
                && (windowState = this.mInputMethodWindow) != null
                && windowState.isVisible()) {
            attachImeScreenshotOnTarget(this.mImeLayeringTarget, false);
        }
    }

    public void showImeScreenshot(WindowState windowState) {
        attachImeScreenshotOnTarget(windowState, true);
    }

    public final boolean startAsyncRotation(boolean z) {
        if (z) {
            this.mWmService.mH.postDelayed(
                    new DisplayContent$$ExternalSyntheticLambda25(0, this), 250L);
            return false;
        }
        if (this.mAsyncRotationController != null) {
            return false;
        }
        AsyncRotationController asyncRotationController = new AsyncRotationController(this);
        this.mAsyncRotationController = asyncRotationController;
        for (int size = asyncRotationController.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            WindowToken windowToken =
                    (WindowToken) asyncRotationController.mTargetWindowTokens.keyAt(size);
            AsyncRotationController.Operation operation =
                    (AsyncRotationController.Operation)
                            asyncRotationController.mTargetWindowTokens.valueAt(size);
            int i = operation.mAction;
            if (i == 2 || i == 3) {
                asyncRotationController.fadeWindowToken(false, windowToken, null);
                operation.mLeash = windowToken.getAnimationLeash();
                Slog.d(
                        "AsyncRotation_WindowManager",
                        "Start fade-out " + windowToken.getTopChild());
            } else if (i == 1) {
                operation.mLeash = windowToken.mSurfaceControl;
                Slog.d(
                        "AsyncRotation_WindowManager",
                        "Start seamless " + windowToken.getTopChild());
            }
        }
        if (asyncRotationController.mHasScreenRotationAnimation) {
            if (asyncRotationController.mTimeoutRunnable == null) {
                asyncRotationController.mTimeoutRunnable =
                        new AsyncRotationController$$ExternalSyntheticLambda1(
                                asyncRotationController);
            }
            asyncRotationController.mService.mH.postDelayed(
                    asyncRotationController.mTimeoutRunnable, 2000L);
        }
        return true;
    }

    public final void startFixedRotationTransform(WindowToken windowToken, int i) {
        this.mTmpConfiguration.unset();
        DisplayInfo computeScreenConfiguration =
                computeScreenConfiguration(
                        this.mTmpConfiguration, i, windowToken.isConfigurationNeededInUdcCutout());
        windowToken.applyFixedRotationTransform(
                computeScreenConfiguration,
                new DisplayFrames(
                        new InsetsState(),
                        computeScreenConfiguration,
                        calculateDisplayCutoutForRotation(
                                i, windowToken.isConfigurationNeededInUdcCutout()),
                        calculateRoundedCornersForRotation(i),
                        (PrivacyIndicatorBounds)
                                this.mPrivacyIndicatorBoundsCache.getOrCompute(
                                        i, this.mCurrentPrivacyIndicatorBounds),
                        (DisplayShape)
                                this.mDisplayShapeCache.getOrCompute(i, this.mInitialDisplayShape),
                        null),
                this.mTmpConfiguration);
    }

    public final boolean supportsSystemDecorations() {
        boolean forceDesktopMode = forceDesktopMode();
        if (com.android.window.flags.Flags.rearDisplayDisableForceDesktopSystemDecorations()) {
            forceDesktopMode = forceDesktopMode && (this.mDisplay.getFlags() & 8192) == 0;
        }
        return (this.mWmService.mDisplayWindowSettings.shouldShowSystemDecorsLocked(this)
                        || (this.mDisplay.getFlags() & 64) != 0
                        || forceDesktopMode)
                && this.mDisplayId != this.mWmService.mVr2dDisplayId
                && this.mDisplay.isTrusted();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void switchUser(int i) {
        super.switchUser(i);
        this.mWmService.mWindowsChanged = true;
        DisplayPolicy displayPolicy = this.mDisplayPolicy;
        displayPolicy.updateCurrentUserResources();
        displayPolicy.updateForceShowNavBarSettings();
    }

    @Override // com.android.server.wm.DisplayArea
    public final String toString() {
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

    public final ActivityRecord topRunningActivity(final boolean z) {
        return (ActivityRecord)
                getItemFromTaskDisplayAreas(
                        new Function() { // from class:
                                         // com.android.server.wm.DisplayContent$$ExternalSyntheticLambda13
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ((TaskDisplayArea) obj).topRunningActivity(z);
                            }
                        });
    }

    public final void unregisterPointerEventListener(
            WindowManagerPolicyConstants.PointerEventListener pointerEventListener) {
        PointerEventDispatcher pointerEventDispatcher = this.mPointerEventDispatcher;
        synchronized (pointerEventDispatcher.mListeners) {
            try {
                if (!pointerEventDispatcher.mListeners.contains(pointerEventListener)) {
                    throw new IllegalStateException(
                            "registerInputEventListener: "
                                    + pointerEventListener
                                    + " not registered.");
                }
                pointerEventDispatcher.mListeners.remove(pointerEventListener);
                pointerEventDispatcher.mListenersArray = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateBaseDisplayCutout(int i, int i2) {
        UdcCutoutPolicy udcCutoutPolicy = this.mUdcCutoutPolicy;
        if (udcCutoutPolicy != null) {
            DisplayInfo displayInfo = this.mNonOverrideDisplayInfo;
            int naturalWidth = displayInfo.getNaturalWidth();
            int i3 =
                    naturalWidth <= 0
                            ? DisplayMetrics.DENSITY_DEVICE_STABLE
                            : (displayInfo.logicalDensityDpi * i) / naturalWidth;
            String string = udcCutoutPolicy.mContext.getString(R.string.enablePin);
            DisplayCutout fromResourcesRectApproximation =
                    TextUtils.isEmpty(string)
                            ? null
                            : DisplayCutout.fromResourcesRectApproximation(
                                    udcCutoutPolicy.mContext.getResources(),
                                    displayInfo.uniqueId,
                                    i,
                                    i2,
                                    i,
                                    i2,
                                    i3,
                                    string);
            udcCutoutPolicy.mUdcCutout = fromResourcesRectApproximation;
            if (fromResourcesRectApproximation != null) {
                Slog.v(
                        "WindowManager",
                        "UdcCutoutPolicy: updateUdcCutout=" + udcCutoutPolicy.mUdcCutout);
            } else if (CoreRune.isSamsungLogEnabled()) {
                Slog.v(
                        "WindowManager",
                        "UdcCutoutPolicy: updateUdcCutout=null, isPrimaryDisplay=true");
            }
        }
        if (this.mNonOverrideDisplayInfo.displayCutout == null) {
            this.mBaseDisplayCutout = null;
            return;
        }
        ContextImpl systemUiContext = ActivityThread.currentActivityThread().getSystemUiContext();
        int naturalWidth2 = this.mNonOverrideDisplayInfo.getNaturalWidth();
        this.mBaseDisplayCutout =
                DisplayCutout.fromResourcesRectApproximation(
                        systemUiContext.getResources(),
                        this.mNonOverrideDisplayInfo.uniqueId,
                        i,
                        i2,
                        i,
                        i2,
                        naturalWidth2 <= 0
                                ? DisplayMetrics.DENSITY_DEVICE_STABLE
                                : (this.mNonOverrideDisplayInfo.logicalDensityDpi * i)
                                        / naturalWidth2,
                        false);
    }

    public final void updateBaseDisplayMetrics(int i, int i2, int i3, float f, float f2) {
        DisplayCutout displayCutout;
        RoundedCorners roundedCorners;
        int i4;
        int i5;
        this.mBaseDisplayWidth = i;
        this.mBaseDisplayHeight = i2;
        this.mBaseDisplayDensity = i3;
        this.mBaseDisplayPhysicalXDpi = f;
        this.mBaseDisplayPhysicalYDpi = f2;
        if (this.mIsSizeForced || this.mIsDensityForced) {
            DisplayPolicy displayPolicy = this.mDisplayPolicy;
            if (displayPolicy == null || this.mInitialDisplayCutout == null) {
                displayCutout = null;
            } else {
                Resources resources = displayPolicy.mUiContext.getResources();
                String str = this.mDisplayInfo.uniqueId;
                Point point = this.mPhysicalDisplaySize;
                displayCutout =
                        DisplayCutout.fromResourcesRectApproximation(
                                resources, str, point.x, point.y, i, i2);
            }
            this.mBaseDisplayCutout = displayCutout;
            DisplayPolicy displayPolicy2 = this.mDisplayPolicy;
            if (displayPolicy2 == null || this.mInitialRoundedCorners == null) {
                roundedCorners = null;
            } else if (i3 > 0) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                displayMetrics.setTo(
                        this.mDisplayPolicy.mUiContext.getResources().getDisplayMetrics());
                displayMetrics.density = i3 / 160.0f;
                Resources resources2 = this.mDisplayPolicy.mUiContext.getResources();
                String str2 = this.mDisplayInfo.uniqueId;
                Point point2 = this.mPhysicalDisplaySize;
                roundedCorners =
                        RoundedCorners.fromCustomResources(
                                resources2, str2, point2.x, point2.y, i, i2, displayMetrics);
            } else {
                Resources resources3 = displayPolicy2.mUiContext.getResources();
                String str3 = this.mDisplayInfo.uniqueId;
                Point point3 = this.mPhysicalDisplaySize;
                roundedCorners =
                        RoundedCorners.fromResources(resources3, str3, point3.x, point3.y, i, i2);
            }
            this.mBaseRoundedCorners = roundedCorners;
        }
        int i6 = this.mMaxUiWidth;
        if (i6 > 0 && (i5 = this.mBaseDisplayWidth) > i6) {
            float f3 = i6 / i5;
            this.mBaseDisplayHeight = (int) (this.mBaseDisplayHeight * f3);
            this.mBaseDisplayWidth = i6;
            this.mBaseDisplayPhysicalXDpi *= f3;
            this.mBaseDisplayPhysicalYDpi *= f3;
            if (!this.mIsDensityForced) {
                this.mBaseDisplayDensity = (int) (this.mBaseDisplayDensity * f3);
            }
        }
        int i7 = this.mForcedHideCutout;
        if (i7 == 1 || i7 == 2) {
            this.mBaseDisplayCutout = null;
        } else {
            updateBaseDisplayCutout(this.mBaseDisplayWidth, this.mBaseDisplayHeight);
        }
        if (this.mDisplayReady) {
            DisplayPolicy.DecorInsets.Cache cache = this.mDisplayPolicy.mCachedDecorInsets;
            if (cache != null
                    && cache.mActive
                    && ((i4 = cache.mPreserveId) == -1
                            || cache.mDecorInsets.mDisplayContent.mTransitionController
                                    .inTransition(i4))) {
                return;
            }
            this.mDisplayPolicy.mDecorInsets.invalidate();
        }
    }

    public final DisplayInfo updateDisplayAndOrientation(Configuration configuration) {
        String str;
        int i = this.mDisplayRotation.mRotation;
        boolean z = true;
        if (i != 1 && i != 3) {
            z = false;
        }
        int i2 = z ? this.mBaseDisplayHeight : this.mBaseDisplayWidth;
        int i3 = z ? this.mBaseDisplayWidth : this.mBaseDisplayHeight;
        DisplayCutout calculateDisplayCutoutForRotation =
                calculateDisplayCutoutForRotation(i, false);
        RoundedCorners calculateRoundedCornersForRotation = calculateRoundedCornersForRotation(i);
        DisplayShape displayShape =
                (DisplayShape) this.mDisplayShapeCache.getOrCompute(i, this.mInitialDisplayShape);
        Rect rect = this.mDisplayPolicy.getDecorInsetsInfo(i, i2, i3).mNonDecorFrame;
        DisplayInfo displayInfo = this.mDisplayInfo;
        displayInfo.rotation = i;
        displayInfo.logicalWidth = i2;
        displayInfo.logicalHeight = i3;
        displayInfo.logicalDensityDpi = this.mBaseDisplayDensity;
        float f = this.mBaseDisplayPhysicalXDpi;
        displayInfo.physicalXDpi = f;
        float f2 = this.mBaseDisplayPhysicalYDpi;
        displayInfo.physicalYDpi = f2;
        DisplayInfo displayInfo2 = this.mNonOverrideDisplayInfo;
        float f3 = i2 / (z ? displayInfo2.logicalHeight : displayInfo2.logicalWidth);
        float f4 = i3 / (z ? displayInfo2.logicalWidth : displayInfo2.logicalHeight);
        float f5 = displayInfo2.physicalXDpi * f3;
        float f6 = displayInfo2.physicalYDpi * f4;
        if (f != f5 || f2 != f6) {
            StringBuilder sb = new StringBuilder("updateDisplayAndOrientation: Change ");
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
            Slog.v("WindowManager", sb.toString());
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
        displayInfo5.displayShape = displayShape;
        displayInfo5.getAppMetrics(this.mDisplayMetrics);
        if (this.mDisplayScalingDisabled) {
            this.mDisplayInfo.flags |= 1073741824;
        } else {
            this.mDisplayInfo.flags &= -1073741825;
        }
        computeSizeRanges(
                this.mDisplayInfo, z, i2, i3, this.mDisplayMetrics.density, configuration, false);
        setDisplayInfoOverride();
        if (this.isDefaultDisplay) {
            this.mCompatibleScreenScale =
                    CompatibilityInfo.computeCompatibleScaling(
                            this.mDisplayMetrics, this.mCompatDisplayMetrics);
        }
        onDisplayInfoChanged();
        return this.mDisplayInfo;
    }

    public void updateDisplayAreaOrganizers() {
        if (this.mDisplay.isTrusted()) {
            forAllDisplayAreas(new DisplayContent$$ExternalSyntheticLambda1(5, this));
        }
    }

    public final void updateDisplayFrames(boolean z) {
        DisplayFrames displayFrames = this.mDisplayFrames;
        DisplayInfo displayInfo = this.mDisplayInfo;
        int i = displayInfo.rotation;
        if (displayFrames.update(
                i,
                displayInfo.logicalWidth,
                displayInfo.logicalHeight,
                calculateDisplayCutoutForRotation(i, false),
                calculateRoundedCornersForRotation(i),
                (PrivacyIndicatorBounds)
                        this.mPrivacyIndicatorBoundsCache.getOrCompute(
                                i, this.mCurrentPrivacyIndicatorBounds),
                (DisplayShape)
                        this.mDisplayShapeCache.getOrCompute(i, this.mInitialDisplayShape))) {
            final InsetsStateController insetsStateController = this.mInsetsStateController;
            insetsStateController.getClass();
            final ArrayList arrayList = new ArrayList();
            insetsStateController.mDisplayContent.forAllWindows(
                    new Consumer() { // from class:
                                     // com.android.server.wm.InsetsStateController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            InsetsStateController insetsStateController2 =
                                    InsetsStateController.this;
                            ArrayList arrayList2 = arrayList;
                            WindowState windowState = (WindowState) obj;
                            insetsStateController2.getClass();
                            windowState.mAboveInsetsState.set(
                                    insetsStateController2.mState,
                                    WindowInsets.Type.displayCutout());
                            arrayList2.add(windowState);
                        }
                    },
                    true);
            if (z) {
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    insetsStateController.mDispatchInsetsChanged.accept(
                            (WindowState) arrayList.get(size));
                }
            }
        }
    }

    public final boolean updateDisplayOverrideConfigurationLocked() {
        RecentsAnimationController recentsAnimationController =
                this.mWmService.mRecentsAnimationController;
        if (recentsAnimationController != null) {
            recentsAnimationController.cancelAnimation(
                    recentsAnimationController.mWillFinishToHome ? 1 : 2,
                    "cancelAnimationForDisplayChange",
                    true);
        }
        Configuration configuration = new Configuration();
        computeScreenConfiguration(configuration);
        if (this.mDisplayId == 2) {
            DisplayPolicy displayPolicy = this.mDisplayPolicy;
            displayPolicy.getClass();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Configuration configuration2 = new Configuration();
            DisplayContent displayContent = displayPolicy.mDisplayContent;
            displayContent.computeScreenConfiguration(configuration2);
            displayContent.mDisplay.getMetrics(displayMetrics);
            displayPolicy
                    .mUiContext
                    .getResources()
                    .updateConfiguration(configuration2, displayMetrics);
            displayPolicy.onConfigurationChanged();
            displayPolicy.mSystemGestures.onConfigurationChanged();
            computeScreenConfiguration(configuration);
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        activityTaskManagerService.mH.sendMessage(
                PooledLambda.obtainMessage(
                        new ActivityTaskManagerService$$ExternalSyntheticLambda0(1),
                        activityTaskManagerService.mAmInternal,
                        Integer.valueOf(this.mDisplayId)));
        if (isDexMode()) {
            float f = configuration.fontScale;
            Settings.System.clearConfiguration(configuration);
            configuration.fontScale = f;
        } else {
            Settings.System.clearConfiguration(configuration);
        }
        updateDisplayOverrideConfigurationLocked(configuration, null, false);
        return this.mAtmService.mTmpUpdateConfigurationResult.changes != 0;
    }

    public final boolean updateDisplayOverrideConfigurationLocked(
            Configuration configuration, ActivityRecord activityRecord, boolean z) {
        int updateGlobalConfigurationLocked;
        this.mAtmService.deferWindowLayout();
        if (configuration != null) {
            try {
                updateGlobalConfigurationLocked =
                        this.mDisplayId == 0
                                ? this.mAtmService.updateGlobalConfigurationLocked(
                                        configuration, false, false, -10000)
                                : performDisplayOverrideConfigUpdate(configuration);
                ActivityTaskManagerService.UpdateConfigurationResult updateConfigurationResult =
                        this.mAtmService.mTmpUpdateConfigurationResult;
                updateConfigurationResult.changes = updateGlobalConfigurationLocked;
                updateConfigurationResult.mIsUpdating = true;
            } catch (Throwable th) {
                ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
                activityTaskManagerService.mTmpUpdateConfigurationResult.mIsUpdating = false;
                activityTaskManagerService.continueWindowLayout();
                throw th;
            }
        } else {
            updateGlobalConfigurationLocked = 0;
        }
        boolean ensureConfigAndVisibilityAfterUpdate =
                z
                        ? true
                        : this.mAtmService.ensureConfigAndVisibilityAfterUpdate(
                                updateGlobalConfigurationLocked, activityRecord);
        ActivityTaskManagerService activityTaskManagerService2 = this.mAtmService;
        activityTaskManagerService2.mTmpUpdateConfigurationResult.mIsUpdating = false;
        activityTaskManagerService2.continueWindowLayout();
        this.mAtmService.mTmpUpdateConfigurationResult.getClass();
        return ensureConfigAndVisibilityAfterUpdate;
    }

    public final void updateImeControlTarget(boolean z) {
        WindowState windowState;
        ActivityRecord activityRecord;
        InsetsControlTarget insetsControlTarget = this.mImeControlTarget;
        InsetsControlTarget computeImeControlTarget = computeImeControlTarget();
        this.mImeControlTarget = computeImeControlTarget;
        InsetsStateController insetsStateController = this.mInsetsStateController;
        if (computeImeControlTarget == null) {
            computeImeControlTarget = insetsStateController.mEmptyImeControlTarget;
        }
        insetsStateController.onControlTargetChanged(
                computeImeControlTarget, insetsStateController.getImeSourceProvider(), false);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_IME,
                    -6684172224226118673L,
                    0,
                    null,
                    String.valueOf(
                            computeImeControlTarget != null
                                    ? computeImeControlTarget.getWindow()
                                    : "null"));
        }
        insetsStateController.notifyPendingInsetsControlChanged();
        if (insetsControlTarget != this.mImeControlTarget || z) {
            SurfaceControl surfaceControl = this.mInputMethodSurfaceParent;
            updateImeParent();
            if (surfaceControl != null
                    && surfaceControl == this.mInputMethodSurfaceParent
                    && (windowState = this.mImeLayeringTarget) != null
                    && windowState.mSurfaceControl != null
                    && this.isDefaultDisplay
                    && ((activityRecord = windowState.mActivityRecord) == null
                            || !activityRecord.hasStartingWindow())) {
                InsetsControlTarget insetsControlTarget2 = this.mImeControlTarget;
                WindowToken windowToken =
                        (insetsControlTarget2 == null || insetsControlTarget2.getWindow() == null)
                                ? null
                                : this.mImeControlTarget.getWindow().mToken;
                if (windowState.mSurfaceControl != null
                        && windowState.mToken == windowToken
                        && !windowState.inMultiWindowMode()) {
                    assignRelativeLayerForIme(getSyncTransaction(), true);
                    scheduleAnimation();
                }
            }
        }
        InsetsControlTarget insetsControlTarget3 = this.mImeControlTarget;
        WindowState window = insetsControlTarget3 != null ? insetsControlTarget3.getWindow() : null;
        this.mWmService.mH.post(
                new DisplayContent$$ExternalSyntheticLambda25(
                        3, window != null ? window.mClient.asBinder() : null));
    }

    public final void updateImeInputAndControlTarget(InputTarget inputTarget) {
        SurfaceControl surfaceControl;
        if (this.mImeInputTarget != inputTarget) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_IME_enabled[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_DEBUG_IME,
                        -4354595179162289537L,
                        0,
                        null,
                        String.valueOf(inputTarget));
            }
            setImeInputTarget(inputTarget);
            InsetsStateController insetsStateController = this.mInsetsStateController;
            insetsStateController.updateAboveInsetsState(
                    insetsStateController.mState.isSourceOrDefaultVisible(
                            InsetsSource.ID_IME, WindowInsets.Type.ime()));
            updateImeControlTarget(
                    (this.mImeControlTarget != this.mRemoteInsetsControlTarget
                                    || (surfaceControl = this.mInputMethodSurfaceParent) == null
                                    || surfaceControl.isSameSurface(
                                            this.mImeWindowsContainer.getParent().mSurfaceControl))
                            ? false
                            : true);
            if (Flags.refactorInsetsController()) {
                ImeInsetsSourceProvider imeSourceProvider =
                        this.mInsetsStateController.getImeSourceProvider();
                if (!Flags.refactorInsetsController() || inputTarget == null) {
                    return;
                }
                WindowState windowState = inputTarget.getWindowState();
                InsetsControlTarget insetsControlTarget = imeSourceProvider.mControlTarget;
                if (inputTarget == insetsControlTarget
                        || windowState == null
                        || insetsControlTarget == null) {
                    return;
                }
                insetsControlTarget.setImeInputTargetRequestedVisibility(
                        (windowState.mRequestedVisibleTypes & WindowInsets.Type.ime()) != 0);
            }
        }
    }

    public final void updateImeParent() {
        SurfaceControl lastRelativeLayer;
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
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
                && (activityRecord2 = this.mInputMethodSurfaceParentContainer) != null
                && activityRecord2.getDisplayContent() != null) {
            if (this.mInputMethodSurfaceParentContainer.getDisplayContent() != this) {
                computeImeParent = this.mImeWindowsContainer.getParent().getSurfaceControl();
                Slog.w(
                        "WindowManager",
                        "updateImeParent: reset surface parent(d#"
                                + this.mDisplayId
                                + ") surface is moved to other display, r= "
                                + this.mInputMethodSurfaceParentContainer);
            } else if (this.mAtmService.mDexController.shouldShowDexImeInDefaultDisplayLocked()) {
                computeImeParent = this.mImeWindowsContainer.getParent().getSurfaceControl();
                Slog.w(
                        "WindowManager",
                        "updateImeParent: reset surface parent(d#"
                                + this.mDisplayId
                                + ") by DeX IME policy, r= "
                                + this.mInputMethodSurfaceParentContainer);
            }
        }
        if (computeImeParent == null || computeImeParent == this.mInputMethodSurfaceParent) {
            InsetsControlTarget insetsControlTarget = this.mImeControlTarget;
            if (insetsControlTarget == null
                    || insetsControlTarget != this.mImeLayeringTarget
                    || (lastRelativeLayer = this.mImeWindowsContainer.getLastRelativeLayer())
                            == this.mImeLayeringTarget.mSurfaceControl) {
                return;
            }
            assignRelativeLayerForIme(getSyncTransaction(), false);
            if (lastRelativeLayer != this.mImeWindowsContainer.getLastRelativeLayer()) {
                scheduleAnimation();
                return;
            }
            return;
        }
        this.mInputMethodSurfaceParent = computeImeParent;
        WindowState windowState = this.mImeLayeringTarget;
        SurfaceControl surfaceControl =
                (windowState == null || (activityRecord = windowState.mActivityRecord) == null)
                        ? null
                        : activityRecord.mSurfaceControl;
        if (surfaceControl == null || !computeImeParent.isSameSurface(surfaceControl)) {
            this.mInputMethodSurfaceParentContainer = null;
        } else {
            this.mInputMethodSurfaceParentContainer = this.mImeLayeringTarget.mActivityRecord;
        }
        getSyncTransaction().reparent(this.mImeWindowsContainer.mSurfaceControl, computeImeParent);
        if (ImeTracker.DEBUG_IME_VISIBILITY) {
            EventLog.writeEvent(32003, computeImeParent.toString());
        }
        assignRelativeLayerForIme(getSyncTransaction(), true);
        scheduleAnimation();
        this.mWmService.mH.post(new DisplayContent$$ExternalSyntheticLambda25(1, this));
    }

    public final void updateKeepClearAreas() {
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        forAllWindows(
                (ToBooleanFunction)
                        new DisplayContent$$ExternalSyntheticLambda2(
                                this.mWmService.mRecentsAnimationController,
                                arraySet,
                                arraySet2,
                                new Matrix(),
                                new float[9]),
                true);
        if (((ArraySet) this.mRestrictedKeepClearAreas).equals(arraySet)
                && ((ArraySet) this.mUnrestrictedKeepClearAreas).equals(arraySet2)) {
            return;
        }
        this.mRestrictedKeepClearAreas = arraySet;
        this.mUnrestrictedKeepClearAreas = arraySet2;
        DisplayWindowListenerController displayWindowListenerController =
                this.mWmService.mDisplayNotificationController;
        int beginBroadcast = displayWindowListenerController.mDisplayListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                displayWindowListenerController
                        .mDisplayListeners
                        .getBroadcastItem(i)
                        .onKeepClearAreasChanged(
                                this.mDisplayId, new ArrayList(arraySet), new ArrayList(arraySet2));
            } catch (RemoteException unused) {
            }
        }
        displayWindowListenerController.mDisplayListeners.finishBroadcast();
    }

    public final void updateMirroredSurfaceFromDisplayManager() {
        WindowContainer windowContainer;
        ContentRecorder contentRecorder = this.mContentRecorder;
        if (contentRecorder == null
                || !contentRecorder.isCurrentlyRecording()
                || (windowContainer = contentRecorder.mRecordedWindowContainer) == null) {
            return;
        }
        Rect bounds = windowContainer.getBounds();
        Point fetchSurfaceSizeIfPresent = contentRecorder.fetchSurfaceSizeIfPresent();
        if (fetchSurfaceSizeIfPresent != null) {
            Slog.d(
                    "WindowManager",
                    "updateMirroredSurfaceFromDisplayManager: surfaceSize="
                            + fetchSurfaceSizeIfPresent
                            + ", recordedContentBounds="
                            + bounds
                            + ", "
                            + contentRecorder.mDisplayContent);
            contentRecorder.updateMirroredSurface(
                    contentRecorder.mRecordedWindowContainer.getSyncTransaction(),
                    bounds,
                    fetchSurfaceSizeIfPresent);
        }
    }

    public final Configuration updateOrientation(WindowContainer windowContainer, boolean z) {
        ActivityRecord asActivityRecord;
        if (!this.mDisplayReady) {
            return null;
        }
        DragState dragState = this.mWmService.mDragDropController.mDragState;
        if (dragState != null && dragState.mDragInProgressByRecents) {
            Slog.d("WindowManager", "Ignore updateOrientation during dragging by Recents.");
            return null;
        }
        if (!updateOrientation(z)) {
            if (!CoreRune.MW_SPLIT_FLEX_PANEL_MODE
                    || !z
                    || windowContainer == null
                    || windowContainer.asActivityRecord() == null
                    || !windowContainer.asActivityRecord().mIsFlexPanel) {
                return null;
            }
            Configuration configuration = new Configuration();
            computeScreenConfiguration(configuration);
            return configuration;
        }
        if (windowContainer != null
                && !this.mWmService.mRoot.mOrientationChangeComplete
                && (asActivityRecord = windowContainer.asActivityRecord()) != null) {
            WindowProcessController windowProcessController = asActivityRecord.app;
            if (asActivityRecord.hasProcess()
                    && !windowProcessController.mCrashing
                    && !windowProcessController.mNotResponding) {
                asActivityRecord.startFreezingScreen(-1);
            }
        }
        Configuration configuration2 = new Configuration();
        computeScreenConfiguration(configuration2);
        return configuration2;
    }

    public final boolean updateOrientation(boolean z) {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
        WindowContainer windowContainer = this.mLastOrientationSource;
        int orientation = getOrientation();
        WindowContainer lastOrientationSource = getLastOrientationSource();
        if (lastOrientationSource != windowContainer) {
            DisplayContent displayContent = this.mRotationReversionController.mDisplayContent;
            if (displayContent.mAppCompatCameraPolicy.mDisplayRotationCompatPolicy != null
                    || displayContent.mDisplayRotation.mFoldController != null
                    || displayContent.getIgnoreOrientationRequest()) {
                DisplayRotationReversionController displayRotationReversionController =
                        this.mRotationReversionController;
                boolean z2 = displayRotationReversionController.mSlots[0];
                boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled;
                DisplayContent displayContent2 = displayRotationReversionController.mDisplayContent;
                if (z2) {
                    Task task =
                            displayContent2.getTask(
                                    new DisplayRotationReversionController$$ExternalSyntheticLambda0());
                    if (task == null
                            || (activityRecord = task.topRunningActivity(false)) == null
                            || activityRecord.getOrientation() != 5) {
                        if (zArr[1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_ORIENTATION,
                                    -2060428960792625366L,
                                    0,
                                    null,
                                    null);
                        }
                        displayRotationReversionController.revertOverride(0);
                    }
                } else {
                    Task task2 =
                            displayContent2.getTask(
                                    new DisplayRotationReversionController$$ExternalSyntheticLambda0());
                    if (task2 != null
                            && (activityRecord2 = task2.topRunningActivity(false)) != null
                            && activityRecord2.getOrientation() == 5) {
                        if (zArr[1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_ORIENTATION,
                                    -6949326633913532620L,
                                    0,
                                    null,
                                    null);
                        }
                        displayRotationReversionController.beforeOverrideApplied(0);
                    }
                }
            }
        }
        ActivityRecord asActivityRecord =
                lastOrientationSource != null ? lastOrientationSource.asActivityRecord() : null;
        if (asActivityRecord != null) {
            Task task3 = asActivityRecord.task;
            if (task3 != null && orientation != task3.mLastReportedRequestedOrientation) {
                task3.mLastReportedRequestedOrientation = orientation;
                TaskChangeNotificationController taskChangeNotificationController =
                        this.mAtmService.mTaskChangeNotificationController;
                Message obtainMessage =
                        taskChangeNotificationController.mHandler.obtainMessage(
                                25, task3.mTaskId, orientation);
                taskChangeNotificationController.forAllLocalListeners(
                        taskChangeNotificationController.mNotifyTaskRequestedOrientationChanged,
                        obtainMessage);
                obtainMessage.sendToTarget();
            }
            ActivityRecord activityRecord3 =
                    !asActivityRecord.isVisibleRequested()
                            ? topRunningActivity(false)
                            : asActivityRecord;
            if (activityRecord3 != null
                    && handleTopActivityLaunchingInDifferentOrientation(
                            activityRecord3, asActivityRecord, true)) {
                return false;
            }
        }
        return this.mDisplayRotation.updateOrientation(orientation, z);
    }

    public final void updateRecording() {
        ContentRecorder contentRecorder = this.mContentRecorder;
        if (contentRecorder == null || contentRecorder.mContentRecordingSession == null) {
            int displayIdToMirror =
                    this.mWmService.mDisplayManagerInternal.getDisplayIdToMirror(this.mDisplayId);
            if (displayIdToMirror == -1) {
                return;
            }
            int i = this.mDisplayId;
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_CONTENT_RECORDING_enabled;
            if (displayIdToMirror == i) {
                if (i == 0 || !zArr[3]) {
                    return;
                }
                ProtoLogImpl_54989576.w(
                        ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                        4162342172327950908L,
                        1,
                        "Content Recording: Attempting to mirror self on %d",
                        Long.valueOf(displayIdToMirror));
                return;
            }
            DisplayContent displayContentOrCreate =
                    this.mRootWindowContainer.getDisplayContentOrCreate(displayIdToMirror);
            if (displayContentOrCreate == null && this.mDisplayId == 0) {
                if (zArr[3]) {
                    ProtoLogImpl_54989576.w(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            5489691866309868814L,
                            1,
                            "Content Recording: Found no matching mirror display for id=%d for"
                                + " DEFAULT_DISPLAY. Nothing to mirror.",
                            Long.valueOf(displayIdToMirror));
                    return;
                }
                return;
            }
            if (displayContentOrCreate == null) {
                displayContentOrCreate = this.mRootWindowContainer.mDefaultDisplay;
                if (zArr[3]) {
                    ProtoLogImpl_54989576.w(
                            ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                            -39794010824230928L,
                            5,
                            "Content Recording: Attempting to mirror %d from %d but no"
                                + " DisplayContent associated. Changing to mirror default display.",
                            Long.valueOf(displayIdToMirror),
                            Long.valueOf(this.mDisplayId));
                }
            }
            setContentRecordingSession(
                    ContentRecordingSession.createDisplaySession(displayContentOrCreate.mDisplayId)
                            .setVirtualDisplayId(this.mDisplayId));
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING,
                        6545352723229848841L,
                        5,
                        "Content Recording: Successfully created a ContentRecordingSession for"
                            + " displayId=%d to mirror content from displayId=%d",
                        Long.valueOf(this.mDisplayId),
                        Long.valueOf(displayIdToMirror));
            }
        }
        this.mContentRecorder.updateRecording();
    }

    public final boolean updateSystemGestureExclusion() {
        if (this.mSystemGestureExclusionListeners.getRegisteredCallbackCount() == 0) {
            return false;
        }
        Region obtain = Region.obtain();
        this.mSystemGestureExclusionWasRestricted =
                calculateSystemGestureExclusion(obtain, this.mSystemGestureExclusionUnrestricted);
        try {
            if (this.mSystemGestureExclusion.equals(obtain)) {
                obtain.recycle();
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
                    Slog.e("WindowManager", "Failed to notify SystemGestureExclusionListener", e);
                }
            }
            this.mSystemGestureExclusionListeners.finishBroadcast();
            obtain.recycle();
            return true;
        } catch (Throwable th) {
            obtain.recycle();
            throw th;
        }
    }

    public final void updateWindowsForAnimator() {
        forAllWindows((Consumer) this.mUpdateWindowsForAnimator, true);
        AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        if (asyncRotationController == null || asyncRotationController.mTransitionOp == 0) {
            return;
        }
        boolean z = asyncRotationController.mIsStartTransactionCommitted;
        DisplayContent displayContent = asyncRotationController.mDisplayContent;
        if (!z) {
            if ((asyncRotationController.mTimeoutRunnable != null
                            && asyncRotationController.mIsStartTransactionPrepared)
                    || displayContent.hasTopFixedRotationLaunchingApp()
                    || displayContent.isRotationChanging()
                    || displayContent.inTransition()) {
                return;
            }
            Slog.d("AsyncRotation_WindowManager", "Cancel for no change");
            displayContent.finishAsyncRotationIfPossible();
            return;
        }
        for (int size = asyncRotationController.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            AsyncRotationController.Operation operation =
                    (AsyncRotationController.Operation)
                            asyncRotationController.mTargetWindowTokens.valueAt(size);
            if (!operation.mIsCompletionPending && operation.mAction != 1) {
                WindowToken windowToken =
                        (WindowToken) asyncRotationController.mTargetWindowTokens.keyAt(size);
                int childCount = windowToken.getChildCount();
                int i = 0;
                for (int i2 = childCount - 1; i2 >= 0; i2--) {
                    WindowState windowState = (WindowState) windowToken.getChildAt(i2);
                    if (windowState.isDrawn() || !windowState.mWinAnimator.getShown()) {
                        i++;
                    }
                }
                if (i == childCount) {
                    displayContent.finishAsyncRotation(windowToken);
                }
            }
        }
    }
}
