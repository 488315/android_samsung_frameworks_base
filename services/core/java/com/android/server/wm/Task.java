package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityController;
import android.app.IApplicationThread;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.GraphicBuffer;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.voice.IVoiceInteractionSession;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.InsetsState;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.view.RemoteAnimationTarget;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.WindowManager;
import android.window.ITaskFragmentOrganizer;
import android.window.ITaskOrganizer;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.StartingWindowInfo;
import android.window.TaskSnapshot;
import android.window.WindowContainerToken;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService;
import com.android.server.am.AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0;
import com.android.server.am.AppStateBroadcaster;
import com.android.server.am.AppTimeTracker;
import com.android.server.am.Pageboost;
import com.android.server.uri.NeededUriGrants;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityStarter;
import com.android.server.wm.DexController;
import com.android.server.wm.DexSizeCompatController;
import com.android.server.wm.Dimmer;
import com.android.server.wm.LaunchParamsController;
import com.android.server.wm.LaunchParamsPersister;
import com.android.server.wm.LaunchParamsPersister.LaunchParamsWriteQueueItem;
import com.android.server.wm.MultiTaskingAppCompatController;
import com.android.server.wm.RecentsAnimationController;
import com.android.server.wm.SizeCompatPolicyManager;
import com.android.server.wm.SurfaceAnimator;
import com.android.server.wm.TaskDisplayArea;
import com.android.server.wm.TaskFragmentOrganizerController;
import com.android.server.wm.TaskOrganizerController;
import com.android.server.wm.Transition;
import com.android.server.wm.TransitionController;
import com.android.window.flags.Flags;
import com.samsung.android.core.CompatUtils;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.core.SizeCompatInfo;
import com.samsung.android.knox.mtd.KMTDManager;
import com.samsung.android.rune.CoreRune;
import com.sec.tmodiagnostics.DeviceReportingSecurityChecker;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class Task extends TaskFragment {
    public static final ResetTargetTaskHelper sResetTargetTaskHelper;
    public static final Rect sTmpBounds = new Rect();
    public static Exception sTmpException;
    public String affinity;
    public Intent affinityIntent;
    public boolean autoRemoveRecents;
    public int effectiveUid;
    public boolean inRecents;
    public Intent intent;
    public boolean isAvailable;
    public boolean isPersistable;
    public long lastActiveTime;
    public CharSequence lastDescription;
    public long lastGainFocusTime;
    public int mAffiliatedTaskId;
    public boolean mAlignActivityLocaleWithTask;
    public final AnimatingActivityRegistry mAnimatingActivityRegistry;
    public boolean mBoostRootTaskLayerForFreeform;
    public String mCallingFeatureId;
    public String mCallingPackage;
    public int mCallingUid;
    public boolean mCanAffectSystemUiFlags;
    public ActivityRecord mChildPipActivity;
    public boolean mConfigWillChange;
    public int mCurrentUser;
    public int mCutoutPolicy;
    public DecorSurfaceContainer mDecorSurfaceContainer;
    public boolean mDedicatedTask;
    public boolean mDeferTaskAppear;
    public Point mDexCompatCustomSize;
    public int mDexCompatUiMode;
    public int mDexLaunchPolicy;
    public DexController.DexMetaDataInfo mDexMetaDataInfo;
    public boolean mDragResizing;
    public final FindRootHelper mFindRootHelper;
    public boolean mForceShowForAllUsers;
    public int mFreeformStashMode;
    public float mFreeformStashScale;
    public final ActivityTaskHandler mHandler;
    public boolean mHasBeenVisible;
    public final boolean mHasTopFullscreenWindow;
    public boolean mHasWindowFocus;
    public boolean mHiddenWhileActivatingDrag;
    public String mHostProcessName;
    public boolean mIgnoreDevSettingForNonResizable;
    public boolean mInRemoveTask;
    public boolean mInResumeTopActivity;
    public boolean mIsAliasManaged;
    public boolean mIsAnimatingByRecentsAndDragSourceTask;
    public boolean mIsAvoidTrimDexPendingActivityTask;
    public boolean mIsCaptionHandlerHidden;
    public boolean mIsChangingPipToSplit;
    public boolean mIsDexCompatEnabled;
    public boolean mIsDragSourceTask;
    public boolean mIsEffectivelySystemApp;
    public boolean mIsMinimized;
    public boolean mIsPipReparetingToLastParent;
    public boolean mIsWaitingRemoveEmbedActivityTask;
    public boolean mKeepScreenOn;
    public boolean mKillProcessesOnDestroyed;
    public float mLastDensityDpi;
    public boolean mLastDispatchedWindowFocusInTask;
    public Rect mLastFreeformBoundsBeforeDragMoving;
    public int mLastMinimizedDisplayType;
    public int mLastMinimizedRotation;
    public Rect mLastNonFullscreenBounds;
    public SurfaceControl mLastRecentsAnimationOverlay;
    public PictureInPictureSurfaceTransaction mLastRecentsAnimationTransaction;
    public int mLastReportedRequestedOrientation;
    public boolean mLastSurfaceShowing;
    public final ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData mLastTaskSnapshotData;
    public long mLastTimeMoved;
    public final IBinder mLaunchCookie;
    public boolean mLaunchTaskOnHome;
    public int mLayerRank;
    public int mLockTaskAuth;
    public int mLockTaskUid;
    public final Point mMaxDimensions;
    public int mMaxHeight;
    public int mMaxWidth;
    public Rect mMetaKeyBounds;
    public final Point mMinDimensions;
    public WindowContainerToken mMultiWindowRestoreParent;
    public int mMultiWindowRestoreWindowingMode;
    public boolean mNeedToSendFreeformLogging;
    public boolean mNeverRelinquishIdentity;
    public Task mNextAffiliate;
    public int mNextAffiliateTaskId;
    public int mOffsetXForInsets;
    public int mOffsetYForInsets;
    public String mOldHostProcessName;
    public ActivityRecord mPendingConvertFromTranslucentActivity;
    public boolean mPendingEnsureVisibleForPopOver;
    public Task mPrevAffiliate;
    public int mPrevAffiliateTaskId;
    public int mPrevDisplayId;
    public String mReason;
    public boolean mRemoveByDrag;
    public final boolean mRemoveWithTaskOrganizer;
    public boolean mRemoving;
    public boolean mReparentLeafTaskIfRelaunch;
    public String mRequiredDisplayCategory;
    public int mResizeMode;
    public int mRespectOrientationRequestOverride;
    public boolean mReuseTask;
    public WindowProcessController mRootProcess;
    public int mRotation;
    public StartingData mSharedStartingData;
    public DexSizeCompatController.DexSizeCompatPolicy mSizeCompatPolicy;
    public boolean mSkipLayoutTask;
    public boolean mSkipSavingLaunchingState;
    public final Rect mSnappingGuideBounds;
    public boolean mSupportsPictureInPicture;
    public boolean mTakeInitBounds;
    public boolean mTaskAppearedSent;
    public ActivityManager.TaskDescription mTaskDescription;
    public String mTaskFragmentHostProcessName;
    public int mTaskFragmentHostUid;
    public final int mTaskId;
    public ITaskOrganizer mTaskOrganizer;
    public int mTaskViewTaskOrganizerTaskId;
    public final Rect mTmpRect;
    public ActivityRecord mTranslucentActivityWaiting;
    public final ArrayList mUndrawnActivitiesBelowTopTranslucent;
    public int mUserId;
    public boolean mUserSetupComplete;
    public String mWindowLayoutAffinity;
    public int maxRecents;
    public ComponentName origActivity;
    public ComponentName realActivity;
    public boolean realActivitySuspended;
    public String rootAffinity;
    public boolean rootWasReset;
    public String stringName;
    public IVoiceInteractor voiceInteractor;
    public IVoiceInteractionSession voiceSession;

    public final class ActivityTaskHandler extends Handler {
        public ActivityTaskHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 101) {
                return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock = Task.this.mAtmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    Task.this.notifyActivityDrawnLocked(null);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public final class Builder {
        public ActivityInfo mActivityInfo;
        public ActivityOptions mActivityOptions;
        public int mActivityType;
        public String mAffinity;
        public Intent mAffinityIntent;
        public final ActivityTaskManagerService mAtmService;
        public boolean mAutoRemoveRecents;
        public String mCallingFeatureId;
        public String mCallingPackage;
        public int mCallingUid;
        public boolean mCreatedByOrganizer;
        public boolean mDeferTaskAppear;
        public int mEffectiveUid;
        public boolean mHasBeenVisible;
        public Intent mIntent;
        public String mLastDescription;
        public ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData mLastSnapshotData;
        public ActivityManager.TaskDescription mLastTaskDescription;
        public long mLastTimeMoved;
        public IBinder mLaunchCookie;
        public int mLaunchFlags;
        public boolean mNeverRelinquishIdentity;
        public boolean mOnTop;
        public ComponentName mOrigActivity;
        public WindowContainer mParent;
        public ComponentName mRealActivity;
        public boolean mRealActivitySuspended;
        public boolean mRemoveWithTaskOrganizer;
        public int mResizeMode;
        public String mRootAffinity;
        public boolean mRootWasReset;
        public Task mSourceTask;
        public int mStageType;
        public boolean mSupportsPictureInPicture;
        public int mTaskAffiliation;
        public int mTaskId;
        public int mUserId;
        public boolean mUserSetupComplete;
        public IVoiceInteractor mVoiceInteractor;
        public IVoiceInteractionSession mVoiceSession;
        public int mPrevAffiliateTaskId = -1;
        public int mNextAffiliateTaskId = -1;
        public int mMinWidth = -1;
        public int mMinHeight = -1;
        public int mWindowingMode = 0;

        public Builder(ActivityTaskManagerService activityTaskManagerService) {
            this.mAtmService = activityTaskManagerService;
        }

        public final Task build() {
            ActivityOptions activityOptions;
            Task task;
            int i;
            Task rootTask;
            WindowContainer windowContainer = this.mParent;
            if (windowContainer != null && (windowContainer instanceof TaskDisplayArea)) {
                TaskDisplayArea taskDisplayArea = (TaskDisplayArea) windowContainer;
                if (this.mActivityType == 0 && !this.mCreatedByOrganizer) {
                    this.mActivityType = 1;
                }
                if (!DisplayContent.alwaysCreateRootTask(taskDisplayArea.getWindowingMode(), this.mActivityType) && (i = this.mActivityType) != 0 && (rootTask = taskDisplayArea.getRootTask(0, i)) != null) {
                    throw new IllegalArgumentException("Root task=" + rootTask + " of activityType=" + this.mActivityType + " already on display=" + taskDisplayArea + ". Can't have multiple.");
                }
                int i2 = this.mWindowingMode;
                ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
                if (!TaskDisplayArea.isWindowingModeSupported(i2, activityTaskManagerService.mSupportsMultiWindow, activityTaskManagerService.mSupportsFreeformWindowManagement, activityTaskManagerService.mSupportsPictureInPicture)) {
                    if (!this.mCreatedByOrganizer || this.mWindowingMode != 6 || !WindowConfiguration.isSplitScreenWindowingMode(this.mStageType) || !activityTaskManagerService.mMultiWindowEnableController.mDeviceSupportsMultiWindow) {
                        throw new IllegalArgumentException("Can't create root task for unsupported windowingMode=" + this.mWindowingMode);
                    }
                    Slog.d("ActivityTaskManager", "validateRootTask: force support to create root stage task!");
                }
                int i3 = this.mWindowingMode;
                if (i3 == 2 && this.mActivityType != 1) {
                    throw new IllegalArgumentException("Root task with pinned windowing mode cannot with non-standard activity type.");
                }
                if (i3 == 2 && (task = taskDisplayArea.mRootPinnedTask) != null) {
                    task.dismissPip();
                }
                Intent intent = this.mIntent;
                if (intent != null) {
                    this.mLaunchFlags = intent.getFlags() | this.mLaunchFlags;
                }
                Task launchRootTask = this.mCreatedByOrganizer ? null : taskDisplayArea.getLaunchRootTask(this.mWindowingMode, this.mActivityType, this.mActivityOptions, this.mSourceTask, this.mLaunchFlags, null);
                if (launchRootTask != null) {
                    this.mWindowingMode = 0;
                    this.mParent = launchRootTask;
                }
                this.mTaskId = taskDisplayArea.getNextRootTaskId();
            }
            if (this.mActivityInfo == null) {
                ActivityInfo activityInfo = new ActivityInfo();
                this.mActivityInfo = activityInfo;
                activityInfo.applicationInfo = new ApplicationInfo();
            }
            this.mUserId = UserHandle.getUserId(this.mActivityInfo.applicationInfo.uid);
            this.mTaskAffiliation = this.mTaskId;
            this.mLastTimeMoved = System.currentTimeMillis();
            this.mNeverRelinquishIdentity = true;
            ActivityInfo activityInfo2 = this.mActivityInfo;
            this.mCallingUid = activityInfo2.applicationInfo.uid;
            this.mCallingPackage = activityInfo2.packageName;
            this.mResizeMode = activityInfo2.resizeMode;
            this.mSupportsPictureInPicture = activityInfo2.supportsPictureInPicture();
            if (!this.mRemoveWithTaskOrganizer && (activityOptions = this.mActivityOptions) != null) {
                this.mRemoveWithTaskOrganizer = activityOptions.getRemoveWithTaskOranizer();
            }
            Task buildInner = buildInner();
            buildInner.mHasBeenVisible = this.mHasBeenVisible;
            int i4 = this.mActivityType;
            if (i4 != 0) {
                buildInner.setActivityType(i4);
            }
            WindowContainer windowContainer2 = this.mParent;
            if (windowContainer2 != null) {
                if (windowContainer2 instanceof Task) {
                    Task task2 = (Task) windowContainer2;
                    int i5 = this.mOnTop ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                    boolean z = (this.mActivityInfo.flags & 1024) != 0;
                    task2.addChild(buildInner, (Comparator) null);
                    task2.positionChildAt(i5, buildInner, z);
                } else {
                    windowContainer2.addChild(buildInner, this.mOnTop ? Integer.MAX_VALUE : Integer.MIN_VALUE);
                }
            }
            int i6 = this.mStageType;
            if (i6 != 0) {
                buildInner.setStageType(i6);
            }
            int i7 = this.mWindowingMode;
            if (i7 != 0) {
                buildInner.setWindowingMode(i7, true);
            }
            return buildInner;
        }

        public Task buildInner() {
            return new Task(this.mAtmService, this.mTaskId, this.mIntent, this.mAffinityIntent, this.mAffinity, this.mRootAffinity, this.mRealActivity, this.mOrigActivity, this.mRootWasReset, this.mAutoRemoveRecents, this.mUserId, this.mEffectiveUid, this.mLastDescription, this.mLastTimeMoved, this.mNeverRelinquishIdentity, this.mLastTaskDescription, this.mLastSnapshotData, this.mTaskAffiliation, this.mPrevAffiliateTaskId, this.mNextAffiliateTaskId, this.mCallingUid, this.mCallingPackage, this.mCallingFeatureId, this.mResizeMode, this.mSupportsPictureInPicture, this.mRealActivitySuspended, this.mUserSetupComplete, this.mMinWidth, this.mMinHeight, this.mActivityInfo, this.mVoiceSession, this.mVoiceInteractor, this.mCreatedByOrganizer, this.mLaunchCookie, this.mDeferTaskAppear, this.mRemoveWithTaskOrganizer);
        }
    }

    class DecorSurfaceContainer {
        final SurfaceControl mContainerSurface;
        final SurfaceControl mDecorSurface;
        public boolean mIsBoosted;
        public boolean mIsBoostedRequested;
        TaskFragment mOwnerTaskFragment;
        public final List mPendingClientTransactions = new ArrayList();

        /* renamed from: -$$Nest$massignLayer, reason: not valid java name */
        public static void m1068$$Nest$massignLayer(DecorSurfaceContainer decorSurfaceContainer, SurfaceControl.Transaction transaction, int i) {
            transaction.setLayer(decorSurfaceContainer.mContainerSurface, i);
            transaction.setVisibility(decorSurfaceContainer.mContainerSurface, decorSurfaceContainer.mOwnerTaskFragment.isVisible() || decorSurfaceContainer.mIsBoosted);
        }

        public DecorSurfaceContainer(TaskFragment taskFragment, boolean z) {
            this.mOwnerTaskFragment = taskFragment;
            SurfaceControl build = Task.this.makeSurface().setContainerLayer().setParent(Task.this.mSurfaceControl).setName(Task.this.mSurfaceControl + " - decor surface container").setContainerLayer().setHidden(false).setCallsite("Task.DecorSurfaceContainer").build();
            this.mContainerSurface = build;
            this.mDecorSurface = Task.this.makeSurface().setParent(build).setName(Task.this.mSurfaceControl + " - decor surface").setHidden(!z).setCallsite("Task.DecorSurfaceContainer").build();
        }
    }

    public final class FindRootHelper implements Predicate {
        public boolean mIgnoreRelinquishIdentity;
        public ActivityRecord mRoot;
        public boolean mSetToBottomIfNone;

        public FindRootHelper() {
        }

        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            ActivityRecord activityRecord = (ActivityRecord) obj;
            if (this.mRoot == null && this.mSetToBottomIfNone) {
                this.mRoot = activityRecord;
            }
            if (activityRecord.finishing) {
                return false;
            }
            ActivityRecord activityRecord2 = this.mRoot;
            if (activityRecord2 == null || activityRecord2.finishing) {
                this.mRoot = activityRecord;
            }
            ActivityRecord activityRecord3 = this.mRoot;
            int i = activityRecord3 == activityRecord ? Task.this.effectiveUid : activityRecord.info.applicationInfo.uid;
            if (!this.mIgnoreRelinquishIdentity) {
                ActivityInfo activityInfo = activityRecord3.info;
                if ((activityInfo.flags & 4096) != 0) {
                    ApplicationInfo applicationInfo = activityInfo.applicationInfo;
                    if (applicationInfo.uid == 1000 || applicationInfo.isSystemApp() || this.mRoot.info.applicationInfo.uid == i) {
                        this.mRoot = activityRecord;
                        return false;
                    }
                }
            }
            return true;
        }
    }

    static {
        ResetTargetTaskHelper resetTargetTaskHelper = new ResetTargetTaskHelper();
        resetTargetTaskHelper.mResultActivities = new ArrayList();
        resetTargetTaskHelper.mAllActivities = new ArrayList();
        resetTargetTaskHelper.mPendingReparentActivities = new ArrayList();
        sResetTargetTaskHelper = resetTargetTaskHelper;
    }

    public Task(ActivityTaskManagerService activityTaskManagerService, int i, Intent intent, Intent intent2, String str, String str2, ComponentName componentName, ComponentName componentName2, boolean z, boolean z2, int i2, int i3, String str3, long j, boolean z3, ActivityManager.TaskDescription taskDescription, ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData persistedTaskSnapshotData, int i4, int i5, int i6, int i7, String str4, String str5, int i8, boolean z4, boolean z5, boolean z6, int i9, int i10, ActivityInfo activityInfo, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, boolean z7, IBinder iBinder, boolean z8, boolean z9) {
        super(activityTaskManagerService, null, z7, false);
        this.mRemoveByDrag = false;
        this.mLastMinimizedDisplayType = -1;
        this.mLastMinimizedRotation = -1;
        this.mTranslucentActivityWaiting = null;
        this.mUndrawnActivitiesBelowTopTranslucent = new ArrayList();
        this.mPendingConvertFromTranslucentActivity = null;
        this.mInResumeTopActivity = false;
        this.mLockTaskAuth = 1;
        this.mLockTaskUid = -1;
        this.isPersistable = false;
        this.mNeverRelinquishIdentity = true;
        this.mReuseTask = false;
        this.mPrevAffiliateTaskId = -1;
        this.mNextAffiliateTaskId = -1;
        this.mLastNonFullscreenBounds = null;
        this.mLayerRank = -1;
        this.mRespectOrientationRequestOverride = -1;
        this.mPrevDisplayId = -1;
        this.mMultiWindowRestoreWindowingMode = -1;
        this.mLastReportedRequestedOrientation = -1;
        this.mTmpRect = new Rect();
        this.mCanAffectSystemUiFlags = true;
        this.mSkipLayoutTask = false;
        this.mIsDragSourceTask = false;
        this.mHiddenWhileActivatingDrag = false;
        this.mIsAnimatingByRecentsAndDragSourceTask = false;
        AnimatingActivityRegistry animatingActivityRegistry = new AnimatingActivityRegistry();
        animatingActivityRegistry.mAnimatingActivities = new ArraySet();
        animatingActivityRegistry.mFinishedTokens = new ArrayMap();
        animatingActivityRegistry.mTmpRunnableList = new ArrayList();
        this.mAnimatingActivityRegistry = animatingActivityRegistry;
        this.mFindRootHelper = new FindRootHelper();
        this.mAlignActivityLocaleWithTask = false;
        this.mHostProcessName = null;
        this.mOldHostProcessName = null;
        this.mDedicatedTask = false;
        this.mIsChangingPipToSplit = false;
        this.mKeepScreenOn = false;
        this.mMinDimensions = new Point(-1, -1);
        this.mMaxDimensions = new Point(-1, -1);
        this.mMaxWidth = -1;
        this.mMaxHeight = -1;
        this.mLastDensityDpi = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mBoostRootTaskLayerForFreeform = false;
        this.mLastFreeformBoundsBeforeDragMoving = null;
        this.mIsMinimized = false;
        this.mIsWaitingRemoveEmbedActivityTask = false;
        this.mTakeInitBounds = false;
        this.mHasTopFullscreenWindow = true;
        this.mFreeformStashMode = 0;
        this.mMetaKeyBounds = null;
        this.mDexCompatUiMode = 0;
        this.mSnappingGuideBounds = new Rect();
        this.mTaskId = i;
        this.mUserId = i2;
        this.mSupportsPictureInPicture = z4;
        this.mTaskDescription = taskDescription != null ? taskDescription : new ActivityManager.TaskDescription();
        this.mLastTaskSnapshotData = persistedTaskSnapshotData != null ? persistedTaskSnapshotData : new ActivityManager.RecentTaskInfo.PersistedTaskSnapshotData();
        this.mIsDexCompatEnabled = getMergedOverrideConfiguration().dexCompatEnabled == 2;
        this.affinityIntent = intent2;
        this.affinity = str;
        this.rootAffinity = str2;
        this.voiceSession = iVoiceInteractionSession;
        this.voiceInteractor = iVoiceInteractor;
        this.realActivity = componentName;
        this.realActivitySuspended = z5;
        this.origActivity = componentName2;
        this.rootWasReset = z;
        this.isAvailable = true;
        this.autoRemoveRecents = z2;
        this.mUserSetupComplete = z6;
        this.effectiveUid = i3;
        this.lastActiveTime = SystemClock.elapsedRealtime();
        this.lastDescription = str3;
        this.mLastTimeMoved = j;
        this.mNeverRelinquishIdentity = z3;
        this.mAffiliatedTaskId = i4;
        this.mPrevAffiliateTaskId = i5;
        this.mNextAffiliateTaskId = i6;
        this.mCallingUid = i7;
        this.mCallingPackage = str4;
        this.mCallingFeatureId = str5;
        this.mResizeMode = i8;
        if (activityInfo != null) {
            setIntent(intent, activityInfo);
            ActivityInfo.WindowLayout windowLayout = activityInfo.windowLayout;
            if (windowLayout != null) {
                this.mMinWidth = windowLayout.minWidth;
                this.mMinHeight = windowLayout.minHeight;
            } else {
                this.mMinWidth = -1;
                this.mMinHeight = -1;
            }
        } else {
            this.intent = intent;
            this.mMinWidth = i9;
            this.mMinHeight = i10;
        }
        this.mAtmService.mMwSupportPolicyController.updateSupportPolicyLocked(activityInfo, this);
        PackagesChange.updateAllValueToTask(this);
        MultiTaskingAppCompatController multiTaskingAppCompatController = this.mAtmService.mMultiTaskingAppCompatController;
        multiTaskingAppCompatController.getClass();
        ComponentName componentName3 = this.realActivity;
        multiTaskingAppCompatController.onOverridesChanged(this.mUserId, this, componentName3 != null ? componentName3.getPackageName() : null, false);
        TaskChangeNotificationController taskChangeNotificationController = this.mAtmService.mTaskChangeNotificationController;
        Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(8, i, 0, this.realActivity);
        taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskCreated, obtainMessage);
        obtainMessage.sendToTarget();
        this.mHandler = new ActivityTaskHandler(this.mTaskSupervisor.mLooper);
        this.mCurrentUser = this.mAtmService.mAmInternal.getCurrentUserId();
        this.mLaunchCookie = iBinder;
        this.mDeferTaskAppear = z8;
        this.mRemoveWithTaskOrganizer = z9;
        EventLog.writeEvent(31001, i);
    }

    public static void enableEnterPipOnTaskSwitch(ActivityOptions activityOptions, ActivityRecord activityRecord, ActivityRecord activityRecord2, Task task) {
        if (activityRecord == null) {
            return;
        }
        if ((activityOptions == null || !activityOptions.disallowEnterPictureInPictureWhileLaunching()) && !activityRecord.inPinnedWindowingMode()) {
            Task rootTask = task != null ? task.getRootTask() : activityRecord2 != null ? activityRecord2.getRootTask() : null;
            if (rootTask == null) {
                Slog.e("ActivityTaskManager", "No root task for enter pip, both to front task and activity are null?");
                return;
            }
            if (activityRecord2 != null) {
                if (activityRecord2.getWindowingMode() != activityRecord.getWindowingMode() && !activityRecord2.isEmbedded() && (!activityRecord2.inFullscreenWindowingMode() || !activityRecord.inSplitScreenWindowingMode())) {
                    return;
                }
                if (activityRecord2.inFreeformWindowingMode() && activityRecord.inFreeformWindowingMode()) {
                    return;
                }
                if (activityRecord2.inSplitScreenWindowingMode() && activityRecord.inSplitScreenWindowingMode() && activityRecord2.getStagePosition() != activityRecord.getStagePosition()) {
                    return;
                }
            }
            boolean z = false;
            boolean z2 = (activityOptions != null && activityOptions.getTransientLaunch()) || rootTask.mTransitionController.isTransientHide(rootTask);
            if (!rootTask.isActivityTypeAssistant() && !z2) {
                z = true;
            }
            activityRecord.supportsEnterPipOnTaskSwitch = z;
        }
    }

    public static ActivityRecord findEnterPipOnTaskSwitchCandidate(Task task) {
        if (task == null) {
            return null;
        }
        ActivityRecord[] activityRecordArr = new ActivityRecord[1];
        task.forAllLeafTaskFragments(new Task$$ExternalSyntheticLambda19(0, activityRecordArr));
        return activityRecordArr[0];
    }

    public static Task fromWindowContainerToken(WindowContainerToken windowContainerToken) {
        if (windowContainerToken == null) {
            return null;
        }
        return WindowContainer.fromBinder(windowContainerToken.asBinder()).asTask();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x05c0, code lost:
    
        if (r22 != false) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x05c2, code lost:
    
        r6 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x05d0, code lost:
    
        if (r19 > 0) goto L280;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x05d2, code lost:
    
        if (r10 == null) goto L267;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x05d4, code lost:
    
        r1 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x05d7, code lost:
    
        if (r1 == null) goto L276;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x05e5, code lost:
    
        r79 = r4;
        r7 = r30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x05eb, code lost:
    
        r2 = android.app.AppGlobals.getPackageManager().getApplicationInfo(r1.getComponent().getPackageName(), 8704, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x05ef, code lost:
    
        if (r2 == null) goto L277;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x05f1, code lost:
    
        r5 = r2.uid;
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x05f9, code lost:
    
        r2 = new java.lang.StringBuilder("Updating task #");
        r4 = r78;
        r2.append(r4);
        r2.append(" for ");
        r2.append(r1);
        r2.append(": effectiveUid=");
        com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0.m(r2, r5, r79);
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x0621, code lost:
    
        if (r44 < r0) goto L282;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0625, code lost:
    
        if (r29 == r0) goto L284;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x0627, code lost:
    
        r1 = r39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x062a, code lost:
    
        if (r1 == 2) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x062c, code lost:
    
        r2 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x062d, code lost:
    
        r1 = r40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x063d, code lost:
    
        r3 = new com.android.server.wm.Task.Builder(r81.mService);
        r3.mTaskId = r4;
        r3.mIntent = r10;
        r3.mAffinityIntent = r11;
        r3.mAffinity = r20;
        r3.mRootAffinity = r6;
        r3.mRealActivity = r23;
        r3.mOrigActivity = r25;
        r3.mRootWasReset = r27;
        r3.mAutoRemoveRecents = r28;
        r3.mUserId = r7;
        r3.mEffectiveUid = r5;
        r3.mLastDescription = r32;
        r3.mLastTimeMoved = r76;
        r3.mNeverRelinquishIdentity = r33;
        r3.mLastTaskDescription = r14;
        r3.mLastSnapshotData = r15;
        r3.mTaskAffiliation = r34;
        r3.mPrevAffiliateTaskId = r35;
        r3.mNextAffiliateTaskId = r36;
        r3.mCallingUid = r37;
        r3.mCallingPackage = r18;
        r3.mCallingFeatureId = r38;
        r3.mResizeMode = r2;
        r3.mSupportsPictureInPicture = r1;
        r3.mRealActivitySuspended = r24;
        r3.mUserSetupComplete = r31;
        r3.mMinWidth = r42;
        r3.mMinHeight = r43;
        r1 = r3.buildInner();
        r6 = r41;
        r1.mLastNonFullscreenBounds = r6;
        r1.setBounds(r6);
        r1.mWindowLayoutAffinity = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x06b1, code lost:
    
        if (com.samsung.android.rune.CoreRune.FW_DEDICATED_MEMORY != false) goto L295;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x06b3, code lost:
    
        r1.mHostProcessName = r45;
        r1.mDedicatedTask = r46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x06bf, code lost:
    
        if (r12.size() > 0) goto L298;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x06c1, code lost:
    
        r81.mRootWindowContainer.getDisplayContent(0).getDefaultTaskDisplayArea().addChild(r1, Integer.MIN_VALUE);
        r2 = r12.size() - r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x06d8, code lost:
    
        r1.addChild((com.android.server.wm.ActivityRecord) r12.get(r2), Integer.MAX_VALUE);
        r2 = r2 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x06e7, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x063b, code lost:
    
        r2 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x0630, code lost:
    
        r1 = r39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x0633, code lost:
    
        r1 = r39;
        r2 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0637, code lost:
    
        if (r1 == 3) goto L291;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x0639, code lost:
    
        r1 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x05f8, code lost:
    
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x05f4, code lost:
    
        r79 = r4;
        r7 = r30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x05d6, code lost:
    
        r1 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x061a, code lost:
    
        r7 = r30;
        r4 = r78;
        r5 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x05c5, code lost:
    
        r6 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x05cd, code lost:
    
        if ("@".equals(r6) == false) goto L264;
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x05cf, code lost:
    
        r6 = r2;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:216:0x06b3  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x06c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.wm.Task restoreFromXml(com.android.modules.utils.TypedXmlPullParser r80, com.android.server.wm.ActivityTaskSupervisor r81) {
        /*
            Method dump skipped, instructions count: 1972
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.restoreFromXml(com.android.modules.utils.TypedXmlPullParser, com.android.server.wm.ActivityTaskSupervisor):com.android.server.wm.Task");
    }

    public static void trimIneffectiveInfo(Task task, TaskInfo taskInfo) {
        ActivityRecord activity = task.getActivity(new Task$$ExternalSyntheticLambda0(0), false);
        int uid = activity != null ? activity.getUid() : task.effectiveUid;
        ActivityInfo activityInfo = taskInfo.topActivityInfo;
        if (activityInfo != null && task.effectiveUid != activityInfo.applicationInfo.uid) {
            ActivityInfo activityInfo2 = new ActivityInfo(taskInfo.topActivityInfo);
            taskInfo.topActivityInfo = activityInfo2;
            activityInfo2.applicationInfo = new ApplicationInfo(taskInfo.topActivityInfo.applicationInfo);
            taskInfo.topActivity = new ComponentName("", "");
            ActivityInfo activityInfo3 = taskInfo.topActivityInfo;
            activityInfo3.packageName = "";
            activityInfo3.taskAffinity = "";
            activityInfo3.processName = "";
            activityInfo3.name = "";
            activityInfo3.parentActivityName = "";
            activityInfo3.targetActivity = "";
            activityInfo3.splitName = "";
            ApplicationInfo applicationInfo = activityInfo3.applicationInfo;
            applicationInfo.className = "";
            applicationInfo.credentialProtectedDataDir = "";
            applicationInfo.dataDir = "";
            applicationInfo.deviceProtectedDataDir = "";
            applicationInfo.manageSpaceActivityName = "";
            applicationInfo.nativeLibraryDir = "";
            applicationInfo.nativeLibraryRootDir = "";
            applicationInfo.processName = "";
            applicationInfo.publicSourceDir = "";
            applicationInfo.scanPublicSourceDir = "";
            applicationInfo.scanSourceDir = "";
            applicationInfo.sourceDir = "";
            applicationInfo.taskAffinity = "";
            applicationInfo.name = "";
            applicationInfo.packageName = "";
        }
        if (task.effectiveUid != uid) {
            taskInfo.baseActivity = new ComponentName("", "");
        }
    }

    public final boolean abortPipEnter(ActivityRecord activityRecord) {
        if (!inPinnedWindowingMode() || activityRecord.inPinnedWindowingMode() || !canMoveTaskToBack(this)) {
            return false;
        }
        Transition transition = new Transition(4, 0, this.mTransitionController, this.mWmService.mSyncEngine);
        this.mTransitionController.moveToCollecting(transition);
        this.mTransitionController.requestStartTransition(transition, this, null, null);
        Task task = activityRecord.mLastParentBeforePip;
        if (task != null && task.isAttached()) {
            activityRecord.reparent(task, task.getChildCount(), "movePinnedActivityToOriginalTask");
        }
        if (isAttached()) {
            setWindowingMode(0);
            moveTaskToBackInner(this, null, null, false, -1, -1);
        }
        if (!activityRecord.isAttached()) {
            return true;
        }
        activityRecord.setWindowingMode(0);
        activityRecord.mWaitForEnteringPinnedMode = false;
        if (!CoreRune.MW_PIP_SHELL_TRANSITION) {
            return true;
        }
        activityRecord.setEnteringPipFromSplit("abort_pip_enter", false);
        activityRecord.setHiddenWhileEnteringPinnedMode("abort_pip_enter", false);
        return true;
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final void addChild(WindowContainer windowContainer, int i) {
        super.addChild(windowContainer, getAdjustedChildPosition(i, windowContainer));
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ADD_REMOVE_enabled[1]) {
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -1028890010429408946L, 0, null, String.valueOf(this));
        }
        if (this.mTaskOrganizer != null && this.mCreatedByOrganizer && windowContainer.asTask() != null) {
            getDisplayArea().addRootTaskReferenceIfNeeded((Task) windowContainer);
        }
        this.mRootWindowContainer.updateUIDsPresentOnDisplay();
        TaskFragment asTaskFragment = windowContainer.asTaskFragment();
        if (asTaskFragment == null || asTaskFragment.asTask() != null) {
            return;
        }
        String str = asTaskFragment.mTaskFragmentOrganizerProcessName;
        if (str != null && this.mTaskFragmentHostProcessName == null) {
            this.mTaskFragmentHostUid = asTaskFragment.mTaskFragmentOrganizerUid;
            this.mTaskFragmentHostProcessName = str;
        }
        int i2 = this.mMinWidth;
        int i3 = this.mMinHeight;
        if (asTaskFragment.asTask() != null) {
            throw new UnsupportedOperationException("This method must not be used to Task. The  minimum dimension of Task should be passed from Task constructor.");
        }
        asTaskFragment.mMinWidth = i2;
        asTaskFragment.mMinHeight = i3;
        ActivityRecord topMostActivity = getTopMostActivity();
        if ((CoreRune.MW_EMBED_ACTIVITY && CoreRune.MT_APP_COMPAT_CONFIGURATION && MultiTaskingAppCompatConfiguration.isPresetLetterboxed(topMostActivity)) || topMostActivity == null) {
            return;
        }
        topMostActivity.associateStartingWindowWithTaskIfNeeded();
    }

    public final void addChild(WindowContainer windowContainer, boolean z, boolean z2) {
        Task asTask = windowContainer.asTask();
        if (asTask != null) {
            try {
                asTask.mForceShowForAllUsers = z2;
            } catch (Throwable th) {
                if (asTask != null) {
                    asTask.mForceShowForAllUsers = false;
                }
                throw th;
            }
        }
        int i = z ? Integer.MAX_VALUE : 0;
        addChild(windowContainer, (Comparator) null);
        positionChildAt(i, windowContainer, z);
        if (asTask != null) {
            asTask.mForceShowForAllUsers = false;
        }
    }

    public final void adjustAspectRatioIfNeeded(Rect rect) {
        if (rect.isEmpty() || !ActivityInfo.isPreserveOrientationMode(this.mResizeMode) || isDexCompatEnabled()) {
            return;
        }
        boolean z = rect.width() > rect.height();
        int i = this.mResizeMode;
        if (i == 5 && !z) {
            if (rect.width() / rect.height() < 1.2f) {
                int round = rect.top + Math.round(rect.width() / 1.2f);
                Slog.i("ActivityTaskManager", "adjustAspectRatioIfNeeded: task=" + this + " resizeMode=" + this.mResizeMode + " bounds=" + rect + " adjusted(bottom)=" + round + " caller=" + Debug.getCallers(4));
                rect.bottom = round;
                return;
            }
            return;
        }
        if (i == 6 && z && rect.height() / rect.width() < 1.2f) {
            int round2 = rect.left + Math.round(rect.height() / 1.2f);
            Slog.i("ActivityTaskManager", "adjustAspectRatioIfNeeded: task=" + this + " resizeMode=" + this.mResizeMode + " bounds=" + rect + " adjusted(right)=" + round2 + " caller=" + Debug.getCallers(4));
            rect.right = round2;
        }
    }

    public final Task adjustFocusToNextFocusableTask(String str, boolean z, boolean z2) {
        Task nextFocusableRootTask;
        Task nextFocusableTask = getNextFocusableTask(z);
        ActivityRecord activityRecord = null;
        if (nextFocusableTask == null) {
            RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
            boolean z3 = !z;
            rootWindowContainer.getClass();
            TaskDisplayArea displayArea = getDisplayArea();
            if (displayArea == null) {
                displayArea = rootWindowContainer.getDisplayContent(this.mPrevDisplayId).getDefaultTaskDisplayArea();
            }
            Task nextFocusableRootTask2 = displayArea.getNextFocusableRootTask(this, z3);
            if (nextFocusableRootTask2 != null) {
                nextFocusableTask = nextFocusableRootTask2;
            } else {
                if (!displayArea.mDisplayContent.isHomeSupported()) {
                    for (int childCount = rootWindowContainer.getChildCount() - 1; childCount >= 0; childCount--) {
                        DisplayContent displayContent = (DisplayContent) rootWindowContainer.getChildAt(childCount);
                        if (displayContent != displayArea.mDisplayContent && (nextFocusableRootTask = displayContent.getDefaultTaskDisplayArea().getNextFocusableRootTask(this, z3)) != null) {
                            nextFocusableTask = nextFocusableRootTask;
                            break;
                        }
                    }
                }
                nextFocusableTask = null;
            }
        }
        if (nextFocusableTask == null) {
            TaskDisplayArea displayArea2 = getDisplayArea();
            if (displayArea2 != null) {
                displayArea2.mPreferredTopFocusableRootTask = null;
            }
            return null;
        }
        Task rootTask = nextFocusableTask.getRootTask();
        if (!z2) {
            WindowContainer parent = nextFocusableTask.getParent();
            do {
                WindowContainer windowContainer = nextFocusableTask;
                nextFocusableTask = parent;
                nextFocusableTask.positionChildAt(Integer.MAX_VALUE, windowContainer, false);
                parent = nextFocusableTask.getParent();
                if (nextFocusableTask.asTask() == null) {
                    break;
                }
            } while (parent != null);
            return rootTask;
        }
        String concat = str.concat(" adjustFocusToNextFocusableTask");
        ActivityRecord activityRecord2 = nextFocusableTask.topRunningActivity(false);
        if (!nextFocusableTask.isActivityTypeHome() || (activityRecord2 != null && activityRecord2.isVisibleRequested())) {
            nextFocusableTask.moveToFront(concat, null);
            if (rootTask.getTopResumedActivity() != null) {
                this.mTaskSupervisor.updateTopResumedActivityIfNeeded(str);
            }
            return rootTask;
        }
        TaskDisplayArea displayArea3 = nextFocusableTask.getDisplayArea();
        int i = displayArea3.mRootWindowContainer.mCurrentUser;
        Task task = displayArea3.mRootHomeTask;
        if (task != null) {
            PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new TaskDisplayArea$$ExternalSyntheticLambda2(), PooledLambda.__(ActivityRecord.class), Integer.valueOf(i));
            activityRecord = task.getActivity(obtainPredicate);
            obtainPredicate.recycle();
        }
        if (activityRecord == null) {
            displayArea3.moveHomeRootTaskToFront(concat);
        } else {
            activityRecord.moveFocusableActivityToTop(concat);
        }
        return rootTask;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0046, code lost:
    
        if (inSplitScreenWindowingMode() != false) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void adjustForMinimalTaskDimensions(android.graphics.Rect r7, android.graphics.Rect r8, android.content.res.Configuration r9) {
        /*
            Method dump skipped, instructions count: 213
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.adjustForMinimalTaskDimensions(android.graphics.Rect, android.graphics.Rect, android.content.res.Configuration):void");
    }

    /* JADX WARN: Type inference failed for: r10v1, types: [com.android.server.wm.Task$$ExternalSyntheticLambda2] */
    @Override // com.android.server.wm.WindowContainer
    public final void applyAnimationUnchecked(WindowManager.LayoutParams layoutParams, boolean z, int i, boolean z2, final ArrayList arrayList) {
        final RecentsAnimationController recentsAnimationController = this.mWmService.mRecentsAnimationController;
        if (recentsAnimationController == null) {
            super.applyAnimationUnchecked(layoutParams, z, i, z2, arrayList);
            return;
        }
        if (!z || isActivityTypeHomeOrRecents()) {
            return;
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -3401780415681318335L, 0, null, String.valueOf(recentsAnimationController), String.valueOf(this), String.valueOf(AppTransition.appTransitionOldToString(i)));
        }
        final int size = arrayList != null ? arrayList.size() : 0;
        final ?? r10 = new SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda2
            @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
            public final void onAnimationFinished(int i2, AnimationAdapter animationAdapter) {
                ArrayList arrayList2 = arrayList;
                for (int i3 = 0; i3 < size; i3++) {
                    ((WindowContainer) arrayList2.get(i3)).onAnimationFinished(i2, animationAdapter);
                }
            }
        };
        if (recentsAnimationController.mRunner != null) {
            recentsAnimationController.mIsAddingTaskToTargets = true;
            recentsAnimationController.mNavBarAttachedApp = getTopVisibleActivity(true, false);
            if (recentsAnimationController.isAnimatingTask(this)) {
                return;
            }
            WindowConfiguration windowConfiguration = getWindowConfiguration();
            if (isAlwaysOnTop() || windowConfiguration.tasksAreFloating()) {
                return;
            }
            final SparseBooleanArray recentTaskIds = recentsAnimationController.mService.mAtmService.mRecentTasks.getRecentTaskIds();
            forAllLeafTasks(new Consumer() { // from class: com.android.server.wm.RecentsAnimationController$$ExternalSyntheticLambda4
                public final /* synthetic */ int f$3 = 0;

                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    RecentsAnimationController recentsAnimationController2 = RecentsAnimationController.this;
                    SparseBooleanArray sparseBooleanArray = recentTaskIds;
                    SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback = r10;
                    int i2 = this.f$3;
                    Task task = (Task) obj;
                    recentsAnimationController2.getClass();
                    if (task.shouldBeVisible(null)) {
                        int i3 = task.mTaskId;
                        RecentsAnimationController.TaskAnimationAdapter addAnimation = recentsAnimationController2.addAnimation(task, !sparseBooleanArray.get(i3), true, onAnimationFinishedCallback);
                        recentsAnimationController2.mPendingNewTaskTargets.add(i3);
                        RemoteAnimationTarget createRemoteAnimationTarget = addAnimation.createRemoteAnimationTarget(i3, i2);
                        if (createRemoteAnimationTarget != null) {
                            recentsAnimationController2.mPendingTaskAppears.add(createRemoteAnimationTarget);
                            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
                                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, -1336603089105439710L, 0, null, String.valueOf(createRemoteAnimationTarget));
                            }
                        }
                    }
                }
            }, false);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final Task asTask() {
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00bd  */
    @Override // com.android.server.wm.WindowContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void assignChildLayers(android.view.SurfaceControl.Transaction r12) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.assignChildLayers(android.view.SurfaceControl$Transaction):void");
    }

    public final boolean canMinimize() {
        return (this.mIsMinimized || !supportsMinimizeState() || getTopNonFinishingActivity(true, true) == null || getRootTask() == null || getDisplayArea() == null || getDisplayArea().isUnderHomeTask(this)) ? false : true;
    }

    public final boolean canMoveTaskToBack(Task task) {
        boolean z;
        if (task != this && !task.isDescendantOf(this)) {
            return false;
        }
        LockTaskController lockTaskController = this.mAtmService.mLockTaskController;
        if (lockTaskController.mLockTaskModeTasks.indexOf(task) == 0) {
            lockTaskController.showLockTaskToast();
            return false;
        }
        if (this.mAtmService.mController != null && isTopRootTaskInDisplayArea()) {
            ActivityRecord activityRecord = topRunningActivity(task.mTaskId, null);
            if (activityRecord == null) {
                activityRecord = topRunningActivity(-1, null);
            }
            if (activityRecord != null) {
                try {
                    z = this.mAtmService.mController.activityResuming(activityRecord.packageName);
                } catch (RemoteException unused) {
                    this.mAtmService.mController = null;
                    Watchdog.getInstance().setActivityController(null);
                    z = true;
                }
                if (!z) {
                    return false;
                }
            }
        }
        return true;
    }

    public final void checkReadyForSleep() {
        if (shouldSleepActivities() && goToSleepIfPossible(false) && getDisplayContent() != null && getDisplayContent().isOnTop()) {
            this.mTaskSupervisor.checkReadyForSleepLocked(true);
        }
    }

    public final void clearRootProcess() {
        WindowProcessController windowProcessController = this.mRootProcess;
        if (windowProcessController != null) {
            windowProcessController.mRecentTasks.remove(this);
            windowProcessController.mHasRecentTasks = !windowProcessController.mRecentTasks.isEmpty();
            this.mRootProcess = null;
        }
    }

    public final ActivityRecord clearTopActivities(ActivityRecord activityRecord, int i, final int[] iArr) {
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new Task$$ExternalSyntheticLambda7(3), PooledLambda.__(ActivityRecord.class), activityRecord.mActivityComponent, Integer.valueOf(activityRecord.mUserId));
        ActivityRecord activity = getActivity(obtainPredicate);
        obtainPredicate.recycle();
        if (activity == null) {
            return null;
        }
        moveTaskFragmentsToBottomIfNeeded(activity, iArr);
        PooledPredicate obtainPredicate2 = PooledLambda.obtainPredicate(new BiPredicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda31
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                int[] iArr2 = iArr;
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                ActivityRecord activityRecord3 = (ActivityRecord) obj2;
                if (activityRecord2 == activityRecord3) {
                    return true;
                }
                if (!activityRecord2.finishing && !activityRecord2.mTaskOverlay) {
                    ActivityOptions activityOptions = activityRecord2.mPendingOptions;
                    if (activityOptions != null) {
                        activityRecord2.mPendingOptions = null;
                        activityRecord2.mPendingRemoteAnimation = null;
                        activityRecord2.mPendingRemoteTransition = null;
                        activityRecord3.updateOptionsLocked(activityOptions);
                    }
                    iArr2[0] = iArr2[0] + 1;
                    activityRecord2.finishIfPossible("clear-task-stack", false);
                }
                return false;
            }
        }, PooledLambda.__(ActivityRecord.class), activity);
        forAllActivities((Predicate) obtainPredicate2);
        obtainPredicate2.recycle();
        if (activity.launchMode == 0 && (536870912 & i) == 0) {
            KMTDManager kMTDManager = ActivityStarter.mtdManager;
            if (((524288 & i) == 0 || (134217728 & i) != 0) && !activity.finishing) {
                activity.finishIfPossible("clear-task-top", false);
            }
        }
        return activity;
    }

    public final boolean cropWindowsToRootTaskBounds() {
        if (isActivityTypeHomeOrRecents()) {
            Task rootTask = getRootTask();
            if (rootTask.mCreatedByOrganizer) {
                rootTask = rootTask.getTopMostTask();
            }
            if (this == rootTask || isDescendantOf(rootTask)) {
                return false;
            }
        }
        return isResizeable(true);
    }

    public final void dismissPip() {
        if (!isActivityTypeStandardOrUndefined()) {
            throw new IllegalArgumentException("You can't move tasks from non-standard root tasks.");
        }
        if (getWindowingMode() != 2) {
            throw new IllegalArgumentException("Can't exit pinned mode if it's not pinned already.");
        }
        Task bottomMostTask = getBottomMostTask();
        setWindowingMode(0);
        if (isAttached()) {
            getDisplayArea().positionChildAt(Integer.MAX_VALUE, this, false);
        }
        this.mTaskSupervisor.scheduleUpdatePictureInPictureModeIfNeeded(bottomMostTask, this);
    }

    public final void dispatchTaskInfoChangedIfNeeded(boolean z) {
        if (isOrganized()) {
            TaskOrganizerController taskOrganizerController = this.mAtmService.mTaskOrganizerController;
            taskOrganizerController.getClass();
            if (this.mTaskAppearedSent) {
                TaskOrganizerController.TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue = ((TaskOrganizerController.TaskOrganizerState) taskOrganizerController.mTaskOrganizerStates.get(this.mTaskOrganizer.asBinder())).mPendingEventsQueue;
                if (taskOrganizerPendingEventsQueue == null) {
                    Slog.i("TaskOrganizerController", "cannot send onTaskInfoChanged because pending events queue is not present for this organizer");
                    return;
                }
                if (z && taskOrganizerPendingEventsQueue.mPendingTaskEvents.size() == 0) {
                    taskOrganizerPendingEventsQueue.dispatchTaskInfoChanged(this, true);
                    return;
                }
                TaskOrganizerController.PendingTaskEvent pendingLifecycleTaskEvent = taskOrganizerPendingEventsQueue.getPendingLifecycleTaskEvent(this);
                if (pendingLifecycleTaskEvent == null) {
                    pendingLifecycleTaskEvent = new TaskOrganizerController.PendingTaskEvent(2, this);
                } else if (pendingLifecycleTaskEvent.mEventType != 2) {
                    return;
                } else {
                    taskOrganizerPendingEventsQueue.mPendingTaskEvents.remove(pendingLifecycleTaskEvent);
                }
                pendingLifecycleTaskEvent.mForce |= z;
                taskOrganizerPendingEventsQueue.mPendingTaskEvents.add(pendingLifecycleTaskEvent);
            }
        }
    }

    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("userId=");
        printWriter.print(this.mUserId);
        printWriter.print(" effectiveUid=");
        UserHandle.formatUid(printWriter, this.effectiveUid);
        printWriter.print(" mCallingUid=");
        UserHandle.formatUid(printWriter, this.mCallingUid);
        printWriter.print(" mUserSetupComplete=");
        printWriter.print(this.mUserSetupComplete);
        printWriter.print(" mCallingPackage=");
        printWriter.print(this.mCallingPackage);
        printWriter.print(" mCallingFeatureId=");
        printWriter.println(this.mCallingFeatureId);
        if (this.affinity != null || this.rootAffinity != null) {
            printWriter.print(str);
            printWriter.print("affinity=");
            printWriter.print(this.affinity);
            String str2 = this.affinity;
            if (str2 == null || !str2.equals(this.rootAffinity)) {
                printWriter.print(" root=");
                printWriter.println(this.rootAffinity);
            } else {
                printWriter.println();
            }
        }
        if (this.mWindowLayoutAffinity != null) {
            printWriter.print(str);
            printWriter.print("windowLayoutAffinity=");
            printWriter.println(this.mWindowLayoutAffinity);
        }
        if (this.voiceSession != null || this.voiceInteractor != null) {
            printWriter.print(str);
            printWriter.print("VOICE: session=0x");
            printWriter.print(Integer.toHexString(System.identityHashCode(this.voiceSession)));
            printWriter.print(" interactor=0x");
            printWriter.println(Integer.toHexString(System.identityHashCode(this.voiceInteractor)));
        }
        if (this.intent != null) {
            StringBuilder sb = new StringBuilder(128);
            sb.append(str);
            sb.append("intent={");
            this.intent.toShortString(sb, false, true, false, false);
            sb.append('}');
            printWriter.println(sb.toString());
        }
        if (this.affinityIntent != null) {
            StringBuilder sb2 = new StringBuilder(128);
            sb2.append(str);
            sb2.append("affinityIntent={");
            this.affinityIntent.toShortString(sb2, false, true, false, false);
            sb2.append('}');
            printWriter.println(sb2.toString());
        }
        if (this.origActivity != null) {
            printWriter.print(str);
            printWriter.print("origActivity=");
            printWriter.println(this.origActivity.flattenToShortString());
        }
        if (this.realActivity != null) {
            printWriter.print(str);
            printWriter.print("mActivityComponent=");
            printWriter.println(this.realActivity.flattenToShortString());
        }
        if (this.autoRemoveRecents || this.isPersistable || !isActivityTypeStandard()) {
            printWriter.print(str);
            printWriter.print("autoRemoveRecents=");
            printWriter.print(this.autoRemoveRecents);
            printWriter.print(" isPersistable=");
            printWriter.print(this.isPersistable);
            printWriter.print(" activityType=");
            printWriter.println(getActivityType());
        }
        if (this.rootWasReset || this.mNeverRelinquishIdentity || this.mReuseTask || this.mLockTaskAuth != 1) {
            printWriter.print(str);
            printWriter.print("rootWasReset=");
            printWriter.print(this.rootWasReset);
            printWriter.print(" mNeverRelinquishIdentity=");
            printWriter.print(this.mNeverRelinquishIdentity);
            printWriter.print(" mReuseTask=");
            printWriter.print(this.mReuseTask);
            printWriter.print(" mLockTaskAuth=");
            printWriter.println(lockTaskAuthToString());
        }
        if (this.mAffiliatedTaskId != this.mTaskId || this.mPrevAffiliateTaskId != -1 || this.mPrevAffiliate != null || this.mNextAffiliateTaskId != -1 || this.mNextAffiliate != null) {
            printWriter.print(str);
            printWriter.print("affiliation=");
            printWriter.print(this.mAffiliatedTaskId);
            printWriter.print(" prevAffiliation=");
            printWriter.print(this.mPrevAffiliateTaskId);
            printWriter.print(" (");
            Task task = this.mPrevAffiliate;
            if (task == null) {
                printWriter.print("null");
            } else {
                printWriter.print(Integer.toHexString(System.identityHashCode(task)));
            }
            printWriter.print(") nextAffiliation=");
            printWriter.print(this.mNextAffiliateTaskId);
            printWriter.print(" (");
            Task task2 = this.mNextAffiliate;
            if (task2 == null) {
                printWriter.print("null");
            } else {
                printWriter.print(Integer.toHexString(System.identityHashCode(task2)));
            }
            printWriter.println(")");
        }
        printWriter.print(str);
        printWriter.print("Activities=");
        printWriter.println(this.mChildren);
        if (!this.inRecents || !this.isAvailable) {
            printWriter.print(str);
            printWriter.print(" inRecents=");
            printWriter.print(this.inRecents);
            printWriter.print(" isAvailable=");
            printWriter.println(this.isAvailable);
        }
        if (this.lastDescription != null) {
            printWriter.print(str);
            printWriter.print("lastDescription=");
            printWriter.println(this.lastDescription);
        }
        if (this.mRootProcess != null) {
            printWriter.print(str);
            printWriter.print("mRootProcess=");
            printWriter.println(this.mRootProcess);
        }
        if (this.mSharedStartingData != null) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "mSharedStartingData=");
            m.append(this.mSharedStartingData);
            printWriter.println(m.toString());
        }
        if (this.mKillProcessesOnDestroyed) {
            printWriter.println(str + "mKillProcessesOnDestroyed=true");
        }
        StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, str, "taskId=");
        m2.append(this.mTaskId);
        printWriter.print(m2.toString());
        printWriter.println(" rootTaskId=" + getRootTask().mTaskId);
        printWriter.print(str);
        StringBuilder sb3 = new StringBuilder("hasChildPipActivity=");
        sb3.append(this.mChildPipActivity != null);
        printWriter.println(sb3.toString());
        printWriter.print(str);
        printWriter.print("mHasBeenVisible=");
        AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(printWriter, str, "mResizeMode=", this.mHasBeenVisible);
        printWriter.print(ActivityInfo.resizeModeToString(this.mResizeMode));
        printWriter.print(" mSupportsPictureInPicture=");
        printWriter.print(this.mSupportsPictureInPicture);
        printWriter.print(" isResizeable=");
        printWriter.println(isResizeable(true));
        printWriter.print(str);
        printWriter.print("lastActiveTime=");
        printWriter.print(this.lastActiveTime);
        printWriter.println(" (inactive for " + ((SystemClock.elapsedRealtime() - this.lastActiveTime) / 1000) + "s)");
        StringBuilder m3 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, str, "lastGainFocusTime=");
        m3.append(this.lastGainFocusTime);
        printWriter.println(m3.toString());
        printWriter.print(str);
        printWriter.println("dexBoundsPolicy:");
        DexController dexController = this.mAtmService.mDexController;
        DexController.DexMetaDataInfo dexMetaDataInfo = this.mDexMetaDataInfo;
        int displayId = getDisplayId();
        getWindowingMode();
        Point dexMetadataLaunchSizeLocked = dexController.getDexMetadataLaunchSizeLocked(dexMetaDataInfo, displayId);
        if (dexMetadataLaunchSizeLocked != null) {
            printWriter.print(str);
            printWriter.print(" MetaDataLaunchSize=");
            printWriter.println(dexMetadataLaunchSizeLocked);
        }
        if (this.mDexLaunchPolicy != 0) {
            printWriter.print(str);
            printWriter.println("dexCompatMode: ");
            printWriter.print(str);
            printWriter.print(" mDexLaunchPolicy=");
            printWriter.print("0x" + Integer.toHexString(this.mDexLaunchPolicy));
            printWriter.print(" mDexCompatCustomSize=");
            printWriter.print(this.mDexCompatCustomSize);
            printWriter.print(" mIsDexCompatEnabled=");
            printWriter.print(this.mIsDexCompatEnabled);
            printWriter.print(" mDexCompatUiMode=");
            printWriter.println(this.mDexCompatUiMode);
        }
        if (this.mMetaKeyBounds != null) {
            printWriter.print(str);
            printWriter.print("mMetaKeyBounds=");
            printWriter.println(this.mMetaKeyBounds);
        }
        if (CoreRune.FW_DEDICATED_MEMORY) {
            if (this.mDedicatedTask) {
                printWriter.print(str);
                printWriter.print("dedicated=true");
            }
            if (this.mHostProcessName != null) {
                if (this.mDedicatedTask) {
                    printWriter.print(" ");
                } else {
                    printWriter.print(str);
                }
                printWriter.print("hostProcess=");
                printWriter.print(this.mHostProcessName);
            }
            if (this.mDedicatedTask || this.mHostProcessName != null) {
                printWriter.println();
            }
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final void dump(PrintWriter printWriter, String str, boolean z) {
        super.dump(printWriter, str, z);
        AnimatingActivityRegistry animatingActivityRegistry = this.mAnimatingActivityRegistry;
        if (animatingActivityRegistry.mAnimatingActivities.isEmpty() && animatingActivityRegistry.mFinishedTokens.isEmpty()) {
            return;
        }
        printWriter.print(str);
        printWriter.println("AnimatingApps:");
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mAnimatingActivities=");
        printWriter.println(animatingActivityRegistry.mAnimatingActivities);
        printWriter.print(str2);
        printWriter.print("mFinishedTokens=");
        printWriter.println(animatingActivityRegistry.mFinishedTokens);
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j, int i) {
        if (i != 2 || isVisible()) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464258L, this.mTaskId);
            protoOutputStream.write(1120986464272L, getRootTask().mTaskId);
            if (getTopResumedActivity() != null) {
                getTopResumedActivity().writeIdentifierToProto(protoOutputStream, 1146756268044L);
            }
            ComponentName componentName = this.realActivity;
            if (componentName != null) {
                protoOutputStream.write(1138166333453L, componentName.flattenToShortString());
            }
            ComponentName componentName2 = this.origActivity;
            if (componentName2 != null) {
                protoOutputStream.write(1138166333454L, componentName2.flattenToShortString());
            }
            protoOutputStream.write(1120986464274L, this.mResizeMode);
            protoOutputStream.write(1133871366148L, matchParentBounds());
            super.getBounds().dumpDebug(protoOutputStream, 1146756268037L);
            Rect rect = this.mLastNonFullscreenBounds;
            if (rect != null) {
                rect.dumpDebug(protoOutputStream, 1146756268054L);
            }
            SurfaceControl surfaceControl = this.mSurfaceControl;
            if (surfaceControl != null) {
                protoOutputStream.write(1120986464264L, surfaceControl.getWidth());
                protoOutputStream.write(1120986464265L, this.mSurfaceControl.getHeight());
            }
            protoOutputStream.write(1133871366172L, this.mCreatedByOrganizer);
            protoOutputStream.write(1138166333469L, this.affinity);
            protoOutputStream.write(1133871366174L, this.mChildPipActivity != null);
            super.dumpDebug(protoOutputStream, 1146756268063L, i);
            protoOutputStream.end(start);
        }
    }

    @Override // com.android.server.wm.TaskFragment
    public final void dumpInner(final PrintWriter printWriter, String str, String str2, boolean z) {
        super.dumpInner(printWriter, str, str2, z);
        if (this.mCreatedByOrganizer) {
            printWriter.println(str + "  mCreatedByOrganizer=true");
            if (this.mOffsetXForInsets != 0 || this.mOffsetYForInsets != 0) {
                StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "  mOffsetXForInsets=");
                m.append(this.mOffsetXForInsets);
                m.append(" mOffsetYForInsets=");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(m, this.mOffsetYForInsets, printWriter);
            }
        }
        if (isAlwaysOnTopFreeform()) {
            printWriter.println(str + "  isAlwaysOnTopFreeform=true");
        }
        if (this.mBoostRootTaskLayerForFreeform) {
            printWriter.println(str + "  mBoostRootTaskLayerForFreeform=true");
        }
        if (CoreRune.MW_CAPTION_SHELL && this.mIsCaptionHandlerHidden) {
            printWriter.println(str + "  mIsCaptionHandlerHidden=true");
        }
        boolean z2 = CoreRune.MW_CAPTION_SHELL_FULL_SCREEN;
        if (CoreRune.MT_SIZE_COMPAT_POLICY) {
            String str3 = str + "  ";
            SizeCompatPolicyManager.LazyHolder.sManager.getClass();
            DexSizeCompatController.DexSizeCompatPolicy compatPolicy = SizeCompatPolicyManager.getCompatPolicy(this, true);
            if (compatPolicy != null) {
                printWriter.print(str3);
                printWriter.print("SizeCompatPolicy: ");
                printWriter.print("Mode=" + SizeCompatInfo.sizeCompatModeToString(1));
                if (!compatPolicy.isEnabled()) {
                    printWriter.print(", Enabled=false");
                }
                printWriter.print(", Size=" + compatPolicy.mWidth + "x" + compatPolicy.mHeight);
                printWriter.println();
                StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, str3 + "  ", "UserScale=");
                m2.append(DexSizeCompatController.DexSizeCompatPolicy.isRotatable(compatPolicy.getTopOrientationInTask()) ? DexSizeCompatController.LazyHolder.sInstance.mDefaultScale : compatPolicy.mUserScale);
                printWriter.print(m2.toString());
                printWriter.print(", UserOrientation=" + CompatUtils.orientationToString(compatPolicy.mUserOrientation));
                printWriter.println();
            }
        }
        if (CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON && this.mKeepScreenOn) {
            printWriter.println(str + "  mKeepScreenOn=true");
        }
        if (isFreeformStashed()) {
            StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(str, "  mFreeformStashScale=");
            m3.append(this.mFreeformStashScale);
            printWriter.print(m3.toString());
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("  mFreeformStashMode="), this.mFreeformStashMode, printWriter);
        }
        if (supportsMinimizeState()) {
            StringBuilder m4 = Preconditions$$ExternalSyntheticOutline0.m(str, "  isMinimized=");
            m4.append(isMinimized());
            printWriter.println(m4.toString());
        }
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  ");
        Iterator it = ((ArrayList) PackagesChange.sAllPackagesChangeAsTask).iterator();
        while (it.hasNext()) {
            ((DisplayCutoutController) it.next()).getClass();
            if (this.mCutoutPolicy != 0) {
                printWriter.print(m$1);
                printWriter.print("mCutoutPolicy=");
                int i = this.mCutoutPolicy;
                printWriter.println(i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "HideCameraCutout" : "OverlapWithTheCameraCutout" : "AppDefault");
            }
        }
        MultiTaskingAppCompatController multiTaskingAppCompatController = this.mAtmService.mMultiTaskingAppCompatController;
        final String m$12 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  ");
        synchronized (multiTaskingAppCompatController) {
            try {
                List list = multiTaskingAppCompatController.mOverridesObservers;
                if (list != null) {
                    ((ArrayList) list).forEach(new Consumer() { // from class: com.android.server.wm.MultiTaskingAppCompatController$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((MultiTaskingAppCompatController.OverridesObserver) obj).onDumpInTask(Task.this, printWriter, m$12);
                        }
                    });
                }
            } finally {
            }
        }
        if (this.mIsDragSourceTask) {
            printWriter.println(str + "  mIsDragSourceTask=true");
        }
        if (this.mHiddenWhileActivatingDrag) {
            printWriter.println(str + "  mHiddenWhileActivatingDrag=true");
        }
        if (this.mIsAnimatingByRecentsAndDragSourceTask) {
            printWriter.println(str + "  mIsAnimatingByRecentsAndDragSourceTask=true");
        }
        StringBuilder m5 = Preconditions$$ExternalSyntheticOutline0.m(str, "  mDexDocking=");
        m5.append(WindowConfiguration.dexTaskDockingStateToString(getDexTaskDockingState()));
        printWriter.println(m5.toString());
        if (this.mLastNonFullscreenBounds != null) {
            printWriter.print(str);
            printWriter.print("  mLastNonFullscreenBounds=");
            printWriter.println(this.mLastNonFullscreenBounds);
        }
        if (isLeafTask()) {
            StringBuilder m6 = Preconditions$$ExternalSyntheticOutline0.m(str, "  isSleeping=");
            m6.append(shouldSleepActivities());
            printWriter.println(m6.toString());
            ActivityTaskSupervisor.printThisActivity(printWriter, getTopPausingActivity(), str2, -1, false, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  topPausingActivity="), null);
            ActivityTaskSupervisor.printThisActivity(printWriter, getTopResumedActivity(), str2, -1, false, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  topResumedActivity="), null);
            if (this.mMinWidth == -1 && this.mMinHeight == -1) {
                return;
            }
            printWriter.print(str);
            printWriter.print("  mMinWidth=");
            printWriter.print(this.mMinWidth);
            printWriter.print(" mMinHeight=");
            printWriter.println(this.mMinHeight);
        }
    }

    public final void ensureActivitiesVisible(boolean z, ActivityRecord activityRecord) {
        this.mTaskSupervisor.beginActivityVisibilityUpdate(getDisplayContent());
        try {
            forAllLeafTasks(new Task$$ExternalSyntheticLambda3(z, activityRecord), true);
            if (this.mTranslucentActivityWaiting != null && this.mUndrawnActivitiesBelowTopTranslucent.isEmpty()) {
                notifyActivityDrawnLocked(null);
            }
        } finally {
            this.mTaskSupervisor.endActivityVisibilityUpdate();
        }
    }

    @Override // com.android.server.wm.TaskFragment
    public final void executeAppTransition(ActivityOptions activityOptions) {
        this.mDisplayContent.executeAppTransition();
        ActivityOptions.abort(activityOptions);
    }

    public final void fillTaskInfo(TaskInfo taskInfo, boolean z) {
        fillTaskInfo(taskInfo, z, getDisplayArea());
    }

    /* JADX WARN: Removed duplicated region for block: B:268:0x0516  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0520  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0560  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x056f  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0577  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0595  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0574  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void fillTaskInfo(android.app.TaskInfo r17, boolean r18, com.android.server.wm.TaskDisplayArea r19) {
        /*
            Method dump skipped, instructions count: 1458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.fillTaskInfo(android.app.TaskInfo, boolean, com.android.server.wm.TaskDisplayArea):void");
    }

    @Override // com.android.server.wm.WindowContainer
    public final void forAllLeafTasks(Consumer consumer, boolean z) {
        int size = this.mChildren.size();
        boolean z2 = true;
        if (z) {
            for (int i = size - 1; i >= 0; i--) {
                Task asTask = ((WindowContainer) this.mChildren.get(i)).asTask();
                if (asTask != null) {
                    asTask.forAllLeafTasks(consumer, z);
                    z2 = false;
                }
            }
        } else {
            for (int i2 = 0; i2 < size; i2++) {
                Task asTask2 = ((WindowContainer) this.mChildren.get(i2)).asTask();
                if (asTask2 != null) {
                    asTask2.forAllLeafTasks(consumer, z);
                    z2 = false;
                }
            }
        }
        if (z2) {
            consumer.accept(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean forAllLeafTasks(Predicate predicate) {
        boolean z = true;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            Task asTask = ((WindowContainer) this.mChildren.get(size)).asTask();
            if (asTask != null) {
                if (asTask.forAllLeafTasks(predicate)) {
                    return true;
                }
                z = false;
            }
        }
        if (z) {
            return predicate.test(this);
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void forAllRootTasks(Consumer consumer, boolean z) {
        if (isRootTask()) {
            consumer.accept(this);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean forAllRootTasks(Predicate predicate, boolean z) {
        if (isRootTask()) {
            return predicate.test(this);
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void forAllTasks(Consumer consumer, boolean z) {
        super.forAllTasks(consumer, z);
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean forAllTasks(Predicate predicate) {
        if (super.forAllTasks(predicate)) {
            return true;
        }
        return predicate.test(this);
    }

    public final Task getAdjacentTask() {
        TaskFragment taskFragment = this.mAdjacentTaskFragment;
        if (taskFragment != null && taskFragment.asTask() != null) {
            return taskFragment.asTask();
        }
        WindowContainer parent = getParent();
        if (parent == null || parent.asTask() == null) {
            return null;
        }
        return parent.asTask().getAdjacentTask();
    }

    public final int getAdjustedChildPosition(int i, WindowContainer windowContainer) {
        int i2;
        boolean showToCurrentUser = windowContainer.showToCurrentUser();
        int size = this.mChildren.size();
        int i3 = 0;
        if (showToCurrentUser) {
            while (i3 < size && !((WindowContainer) this.mChildren.get(i3)).showToCurrentUser()) {
                i3++;
            }
        }
        if (size <= 0) {
            i2 = i3;
        } else if (showToCurrentUser) {
            i2 = size - 1;
        } else {
            i2 = size - 1;
            while (i2 > 0 && ((WindowContainer) this.mChildren.get(i2)).showToCurrentUser()) {
                i2--;
            }
        }
        boolean useAlwaysOnTopFreeform = FreeformController.useAlwaysOnTopFreeform(getWindowingMode(), getDisplayContent());
        if (!windowContainer.isAlwaysOnTop() && !useAlwaysOnTopFreeform) {
            while (i2 > i3 && ((WindowContainer) this.mChildren.get(i2)).isAlwaysOnTop()) {
                i2--;
            }
        }
        if (i == Integer.MIN_VALUE && i3 == 0) {
            return Integer.MIN_VALUE;
        }
        if (i == Integer.MAX_VALUE && i2 >= size - 1) {
            return Integer.MAX_VALUE;
        }
        if (!hasChild(windowContainer)) {
            i2++;
        }
        return Math.min(Math.max(i, i3), i2);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void getAnimationFrames(Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        if (getAdjacentTask() != null) {
            super.getAnimationFrames(rect, rect2, rect3, rect4);
            return;
        }
        WindowState topVisibleAppMainWindow = getTopVisibleAppMainWindow(CoreRune.MW_FREEFORM_SHELL_TRANSITION && inFreeformWindowingMode());
        if (topVisibleAppMainWindow != null) {
            topVisibleAppMainWindow.getAnimationFrames(rect, rect2, rect3, rect4);
        } else {
            super.getAnimationFrames(rect, rect2, rect3, rect4);
        }
    }

    public final Intent getBaseIntent() {
        Intent intent = this.intent;
        if (intent != null) {
            return intent;
        }
        Intent intent2 = this.affinityIntent;
        if (intent2 != null) {
            return intent2;
        }
        Task topMostTask = getTopMostTask();
        if (topMostTask == this || topMostTask == null) {
            return null;
        }
        return topMostTask.getBaseIntent();
    }

    public final String getBasePackageName() {
        ComponentName component;
        Intent baseIntent = getBaseIntent();
        return (baseIntent == null || (component = baseIntent.getComponent()) == null) ? "" : component.getPackageName();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final void getBounds(Rect rect) {
        rect.set(getBounds());
    }

    public final Task getCreatedByOrganizerTask() {
        Task asTask;
        if (this.mCreatedByOrganizer) {
            return this;
        }
        WindowContainer parent = getParent();
        if (parent == null || (asTask = parent.asTask()) == null) {
            return null;
        }
        return asTask.getCreatedByOrganizerTask();
    }

    public final int getDexTaskDockingState() {
        return getConfiguration().windowConfiguration.getDexTaskDockingState();
    }

    @Override // com.android.server.wm.TaskFragment
    public final void getDimBounds(Rect rect) {
        int i = 0;
        if (isRootTask()) {
            getBounds(rect);
            return;
        }
        Task rootTask = getRootTask();
        if (inFreeformWindowingMode()) {
            boolean[] zArr = {false};
            forAllActivities(new Task$$ExternalSyntheticLambda10(i, rect, zArr));
            if (zArr[0]) {
                return;
            }
        }
        if (matchParentBounds()) {
            rect.set(getBounds());
            return;
        }
        if (inSplitScreenWindowingMode() && rootTask.inFullscreenWindowingMode() && getParent() != null) {
            getParent().getBounds(this.mTmpRect);
        } else {
            rootTask.getBounds(this.mTmpRect);
        }
        this.mTmpRect.intersect(getBounds());
        rect.set(this.mTmpRect);
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final Dimmer getDimmer() {
        if (inMultiWindowMode()) {
            return this.mDimmer;
        }
        if (isRootTask() && !isTranslucentAndVisible()) {
            Flags.getDimmerOnClosing();
            if (!isTranslucentForTransition()) {
                return this.mDimmer;
            }
        }
        return super.getDimmer();
    }

    public final Rect getDisplayCutoutInsets() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null || displayContent.mDisplayInfo.displayCutout == null) {
            return null;
        }
        WindowState topVisibleAppMainWindow = getTopVisibleAppMainWindow(false);
        int i = topVisibleAppMainWindow != null ? topVisibleAppMainWindow.mAttrs.layoutInDisplayCutoutMode : 0;
        if (i == 3 || i == 1) {
            return null;
        }
        return this.mDisplayContent.mDisplayInfo.displayCutout.getSafeInsets();
    }

    public final ArrayList getDumpActivitiesLocked(int i, String str) {
        ArrayList arrayList = new ArrayList();
        if ("all".equals(str)) {
            forAllActivities(new Task$$ExternalSyntheticLambda8(2, arrayList));
        } else if ("top".equals(str)) {
            ActivityRecord topMostActivity = getTopMostActivity();
            if (topMostActivity != null) {
                arrayList.add(topMostActivity);
            }
        } else {
            ActivityManagerService.ItemMatcher itemMatcher = new ActivityManagerService.ItemMatcher();
            itemMatcher.build(str);
            forAllActivities(new Task$$ExternalSyntheticLambda10(2, itemMatcher, arrayList));
        }
        if (i != -1) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (((ActivityRecord) arrayList.get(size)).mUserId != i) {
                    arrayList.remove(size);
                }
            }
        }
        return arrayList;
    }

    public final Point getFreeformContainerPosition() {
        Rect rect;
        PointF pointF = this.mAtmService.mFreeformController.mFreeformContainerPoint;
        int i = (int) pointF.x;
        int i2 = (int) pointF.y;
        if ((i == -1 || i2 == -1) && (rect = this.mLastNonFullscreenBounds) != null) {
            i = (rect.width() / 2) + rect.left;
            Rect rect2 = this.mLastNonFullscreenBounds;
            i2 = rect2.top + (rect2.height() / 2);
        }
        return new Point(i, i2);
    }

    @Override // com.android.server.wm.WindowContainer
    public final SurfaceControl getFreezeSnapshotTarget() {
        RemoteAnimationAdapter adapter;
        if (!this.mDisplayContent.mAppTransition.containsTransitRequest(6)) {
            return null;
        }
        ArraySet arraySet = new ArraySet();
        arraySet.add(Integer.valueOf(getActivityType()));
        AppTransitionController appTransitionController = this.mDisplayContent.mAppTransitionController;
        appTransitionController.getClass();
        RemoteAnimationDefinition remoteAnimationDefinition = getRemoteAnimationDefinition();
        if (remoteAnimationDefinition == null || (adapter = remoteAnimationDefinition.getAdapter(27, arraySet)) == null) {
            RemoteAnimationDefinition remoteAnimationDefinition2 = appTransitionController.mRemoteAnimationDefinition;
            adapter = remoteAnimationDefinition2 != null ? remoteAnimationDefinition2.getAdapter(27, arraySet) : null;
        }
        if (adapter == null || adapter.getChangeNeedsSnapshot()) {
            return this.mSurfaceControl;
        }
        return null;
    }

    public Point getLastSurfaceSize() {
        return this.mLastSurfaceSize;
    }

    public final Rect getLaunchBounds() {
        Task rootTask = getRootTask();
        if (rootTask == null) {
            return null;
        }
        int windowingMode = getWindowingMode();
        if (!isActivityTypeStandardOrUndefined() || windowingMode == 1) {
            if (isResizeable(true)) {
                return rootTask.getRequestedOverrideBounds();
            }
            return null;
        }
        if (!getWindowConfiguration().persistTaskBounds()) {
            return rootTask.getRequestedOverrideBounds();
        }
        if (!isDexCompatEnabled() || !inFreeformWindowingMode()) {
            return this.mLastNonFullscreenBounds;
        }
        LaunchParamsController.LaunchParams launchParams = new LaunchParamsController.LaunchParams();
        this.mTaskSupervisor.mLaunchParamsController.calculate(this, null, getTopNonFinishingActivity(true, true), null, null, null, 3, launchParams, null);
        Rect requestedOverrideBounds = getRequestedOverrideBounds();
        Rect rect = new Rect(launchParams.mBounds);
        if (!requestedOverrideBounds.isEmpty() && !rect.isEmpty()) {
            rect.offsetTo(requestedOverrideBounds.left, requestedOverrideBounds.top);
        }
        return rect;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final String getName() {
        return "Task=" + this.mTaskId;
    }

    public final Task getNextFocusableTask(final boolean z) {
        WindowContainer parent = getParent();
        if (parent == null) {
            return null;
        }
        Task task = parent.getTask(new Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                Task task2 = Task.this;
                boolean z2 = z;
                task2.getClass();
                if (z2 || obj != task2) {
                    Task task3 = (Task) obj;
                    if (task3.isFocusableAndVisible() && !task3.isFreeformForceHidden() && (!task3.isAlwaysOnTopFreeform() || task2.getWindowingMode() == 5)) {
                        return true;
                    }
                }
                return false;
            }
        });
        return (task != null || parent.asTask() == null) ? task : parent.asTask().getNextFocusableTask(z);
    }

    public final Task getOrganizedTask() {
        Task asTask;
        if (isOrganized()) {
            return this;
        }
        WindowContainer parent = getParent();
        if (parent == null || (asTask = parent.asTask()) == null) {
            return null;
        }
        return asTask.getOrganizedTask();
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final long getProtoFieldId() {
        return 1146756268037L;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void getRelativePosition(Point point) {
        DisplayInfo displayInfo;
        DisplayCutout displayCutout;
        super.getRelativePosition(point);
        if (isFreeformStashed()) {
            DisplayContent displayContent = this.mDisplayContent;
            int safeInsetRight = (displayContent == null || (displayInfo = displayContent.mDisplayInfo) == null || (displayCutout = displayInfo.displayCutout) == null) ? 0 : isLeftStash() ? -displayCutout.getSafeInsetLeft() : displayCutout.getSafeInsetRight();
            float f = point.x;
            Rect bounds = getBounds();
            point.x = (int) (((inFreeformWindowingMode() && isLeftStash()) ? bounds.width() - (bounds.width() * this.mFreeformStashScale) : FullScreenMagnificationGestureHandler.MAX_SCALE) + safeInsetRight + f);
        }
    }

    public final ActivityRecord getRootActivity(boolean z, boolean z2) {
        FindRootHelper findRootHelper = this.mFindRootHelper;
        findRootHelper.mIgnoreRelinquishIdentity = z;
        findRootHelper.mSetToBottomIfNone = z2;
        Task.this.forAllActivities((Predicate) findRootHelper, false);
        ActivityRecord activityRecord = findRootHelper.mRoot;
        findRootHelper.mRoot = null;
        return activityRecord;
    }

    @Override // com.android.server.wm.WindowContainer
    public final Task getRootTask(Predicate predicate, boolean z) {
        if (isRootTask() && predicate.test(this)) {
            return this;
        }
        return null;
    }

    public final Bitmap getSnapshotAsBitmapLocked() {
        TaskSnapshotController taskSnapshotController = this.mWmService.mTaskSnapshotController;
        TaskSnapshot snapshot = taskSnapshotController.snapshot(this, taskSnapshotController.mHighResSnapshotScale);
        if (snapshot == null) {
            Slog.w("WindowManager", "snapshotTask() returns null! task=" + this);
            return null;
        }
        GraphicBuffer snapshot2 = snapshot.getSnapshot();
        if (snapshot2 == null) {
            return null;
        }
        return Bitmap.wrapHardwareBuffer(HardwareBuffer.createFromGraphicBuffer(snapshot2), null);
    }

    public final StartingWindowInfo getStartingWindowInfo(ActivityRecord activityRecord) {
        ActivityInfo activityInfo;
        WindowState window;
        WindowState window2;
        StartingWindowInfo startingWindowInfo = new StartingWindowInfo();
        ActivityManager.RunningTaskInfo taskInfo = getTaskInfo();
        startingWindowInfo.taskInfo = taskInfo;
        ActivityInfo activityInfo2 = taskInfo.topActivityInfo;
        if (activityInfo2 == null || (activityInfo = activityRecord.info) == activityInfo2) {
            activityInfo = null;
        }
        startingWindowInfo.targetActivityInfo = activityInfo;
        startingWindowInfo.isKeyguardOccluded = this.mAtmService.mKeyguardController.isKeyguardOccluded(taskInfo.displayId);
        StartingData startingData = activityRecord.mStartingData;
        int i = startingData != null ? startingData.mTypeParams : 272;
        startingWindowInfo.startingWindowTypeParameter = i;
        if ((i & 16) != 0 && (window2 = getWindow(new Task$$ExternalSyntheticLambda0(10))) != null) {
            startingWindowInfo.mainWindowLayoutParams = window2.mAttrs;
            startingWindowInfo.requestedVisibleTypes = window2.mRequestedVisibleTypes;
        }
        Rect fixedRotationTransformDisplayBounds = activityRecord.getFixedRotationTransformDisplayBounds();
        Rect rect = startingWindowInfo.taskBounds;
        if (fixedRotationTransformDisplayBounds == null) {
            fixedRotationTransformDisplayBounds = startingWindowInfo.taskInfo.configuration.windowConfiguration.getBounds();
        }
        rect.set(fixedRotationTransformDisplayBounds);
        startingWindowInfo.taskInfo.configuration.setTo(activityRecord.getConfiguration());
        if (!Flags.drawSnapshotAspectRatioMatch() && (window = getWindow(new Task$$ExternalSyntheticLambda0(7))) != null) {
            startingWindowInfo.topOpaqueWindowInsetsState = window.getInsetsStateWithVisibilityOverride();
            startingWindowInfo.topOpaqueWindowLayoutParams = window.mAttrs;
        }
        return startingWindowInfo;
    }

    public final Rect getStashedBounds() {
        if (!isFreeformStashed()) {
            return null;
        }
        Rect rect = new Rect();
        Point point = new Point();
        getBounds(rect);
        getRelativePosition(point);
        if (isLeftStash()) {
            int i = point.x;
            int i2 = point.y;
            rect.set(i, i2, rect.right, ((int) (rect.height() * this.mFreeformStashScale)) + i2);
        } else {
            int i3 = point.x;
            rect.set(i3, point.y, ((int) (rect.width() * this.mFreeformStashScale)) + i3, point.y + ((int) (rect.height() * this.mFreeformStashScale)));
        }
        return rect;
    }

    @Override // com.android.server.wm.WindowContainer
    public final Task getTask(Predicate predicate, boolean z) {
        Task task = super.getTask(predicate, z);
        if (task != null) {
            return task;
        }
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    public final ActivityManager.RunningTaskInfo getTaskInfo() {
        ActivityManager.RunningTaskInfo runningTaskInfo = new ActivityManager.RunningTaskInfo();
        fillTaskInfo(runningTaskInfo, true);
        return runningTaskInfo;
    }

    public final Task getTopLeafTask() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            Task asTask = ((WindowContainer) this.mChildren.get(size)).asTask();
            if (asTask != null) {
                return asTask.getTopLeafTask();
            }
        }
        return this;
    }

    @Override // com.android.server.wm.TaskFragment
    public final ActivityRecord getTopPausingActivity() {
        if (!isLeafTask()) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                ActivityRecord topPausingActivity = ((WindowContainer) this.mChildren.get(size)).asTask().getTopPausingActivity();
                if (topPausingActivity != null) {
                    return topPausingActivity;
                }
            }
        }
        ActivityRecord activityRecord = this.mPausingActivity;
        ActivityRecord activityRecord2 = null;
        for (int size2 = this.mChildren.size() - 1; size2 >= 0; size2--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size2);
            if (windowContainer.asTaskFragment() != null) {
                activityRecord2 = windowContainer.asTaskFragment().getTopPausingActivity();
            } else if (activityRecord != null && windowContainer.asActivityRecord() == activityRecord) {
                activityRecord2 = activityRecord;
            }
            if (activityRecord2 != null) {
                return activityRecord2;
            }
        }
        return null;
    }

    @Override // com.android.server.wm.TaskFragment
    public final ActivityRecord getTopResumedActivity() {
        if (!isLeafTask()) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                ActivityRecord topResumedActivity = ((WindowContainer) this.mChildren.get(size)).asTask().getTopResumedActivity();
                if (topResumedActivity != null) {
                    return topResumedActivity;
                }
            }
        }
        ActivityRecord activityRecord = this.mResumedActivity;
        ActivityRecord activityRecord2 = null;
        for (int size2 = this.mChildren.size() - 1; size2 >= 0; size2--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size2);
            if (windowContainer.asTaskFragment() != null) {
                activityRecord2 = windowContainer.asTaskFragment().getTopResumedActivity();
            } else if (activityRecord != null && windowContainer.asActivityRecord() == activityRecord) {
                activityRecord2 = activityRecord;
            }
            if (activityRecord2 != null) {
                return activityRecord2;
            }
        }
        return null;
    }

    public final ActivityRecord getTopVisibleActivity(final boolean z, final boolean z2) {
        return getActivity(new Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                ActivityRecord activityRecord = (ActivityRecord) obj;
                return !activityRecord.mIsExiting && activityRecord.mClientVisible && (activityRecord.isVisibleRequested() || (CoreRune.MW_FREEFORM_SHELL_TRANSITION && z2 && activityRecord.mVisible)) && (z || !activityRecord.mPopOverState.mIsActivated);
            }
        });
    }

    public final WindowState getTopVisibleAppMainWindow(boolean z) {
        ActivityRecord topVisibleActivity = getTopVisibleActivity(true, CoreRune.MW_FREEFORM_SHELL_TRANSITION && z);
        if (topVisibleActivity != null) {
            return topVisibleActivity.findMainWindow(true);
        }
        return null;
    }

    public final boolean goToSleepIfPossible(boolean z) {
        int[] iArr = {0};
        forAllLeafTasks(new Task$$ExternalSyntheticLambda3(new Task$$ExternalSyntheticLambda3(iArr, 1, z)), true);
        return iArr[0] == 0;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean handlesOrientationChangeFromDescendant(int i) {
        if (!super.handlesOrientationChangeFromDescendant(i)) {
            return false;
        }
        if (!isLeafTask()) {
            return true;
        }
        int windowingMode = getWindowingMode();
        int activityType = getActivityType();
        if (windowingMode != 1 && activityType != 2 && activityType != 3 && activityType != 4) {
            return false;
        }
        TaskDisplayArea displayArea = getDisplayArea();
        return displayArea.mDisplayContent.mOrientationRequestingTaskDisplayArea == displayArea && !displayArea.shouldIgnoreOrientationRequest(i);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final boolean isAlwaysOnTop() {
        return !isForceHidden() && super.isAlwaysOnTop();
    }

    public final boolean isAlwaysOnTopWhenVisible() {
        return super.isAlwaysOnTop();
    }

    public final boolean isAnimatingByRecents() {
        return isAnimating(4, 8) || this.mTransitionController.isTransientHide(this);
    }

    public final boolean isCaptionHandlerHidden() {
        WindowState window;
        if (inFreeformWindowingMode() || !isLeafTask()) {
            return false;
        }
        return ((CoreRune.MW_CAPTION_SHELL_DEX && isDexMode()) || (window = getWindow(new Task$$ExternalSyntheticLambda0(4))) == null || (window.mAttrs.samsungFlags & 16777216) == 0) ? false : true;
    }

    public final boolean isChangeTransitionBlockedByCommonPolicy() {
        return this.mWmService.mDisableTransitionAnimation || this.mDisplayContent == null || isActivityTypeHomeOrRecents() || !((!inPinnedWindowingMode() || ((CoreRune.MW_NATURAL_SWITCHING_PIP && this.mAtmService.mNaturalSwitchingController.mNaturalSwitchingPipTask == this) || this.mIsChangingPipToSplit)) && isAttached() && isLeafTask() && isVisible() && this.mSurfaceControl != null);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final boolean isCompatible(int i, int i2) {
        if (i2 == 0) {
            i2 = 1;
        }
        return super.isCompatible(i, i2);
    }

    public final boolean isDexCompatEnabled() {
        return this.mIsDexCompatEnabled && isDexMode();
    }

    public final boolean isDexTaskDocked() {
        int dexTaskDockingState = getConfiguration().windowConfiguration.getDexTaskDockingState();
        return (dexTaskDockingState == -1 || dexTaskDockingState == 0) ? false : true;
    }

    public final boolean isFocusedRootTaskOnDisplay() {
        DisplayContent displayContent = this.mDisplayContent;
        return displayContent != null && this == displayContent.getFocusedRootTask();
    }

    public final boolean isFreeformForceHidden() {
        return this.mAtmService.mFreeformController.mForceHiddenFreeformTasks.contains(this);
    }

    public final boolean isFreeformPinned() {
        return (CoreRune.MT_NEW_DEX_TASK_PINNING && isNewDexMode()) ? getConfiguration().windowConfiguration.isAlwaysOnTop() : getConfiguration().windowConfiguration.getFreeformTaskPinningState() == 2;
    }

    public final boolean isFreeformStashed() {
        float f = this.mFreeformStashScale;
        return (f > FullScreenMagnificationGestureHandler.MAX_SCALE && f < 1.0f) || this.mFreeformStashMode == 2;
    }

    public final boolean isFullscreenRootForStageTask() {
        TaskDisplayArea taskDisplayArea = getTaskDisplayArea();
        if (taskDisplayArea == null) {
            return false;
        }
        Task task = taskDisplayArea.mRootMainStageTask;
        Task task2 = taskDisplayArea.mRootSideStageTask;
        return this.mCreatedByOrganizer && isRootTask() && inFullscreenWindowingMode() && task != null && task.getParent() == this && task2 != null && task2.getParent() == this;
    }

    public boolean isInChangeTransition() {
        if (!this.mSurfaceFreezer.hasLeash()) {
            int i = this.mTransit;
            ArrayList arrayList = AppTransition.sFlagToString;
            if (i != 27 && i != 30) {
                return false;
            }
        }
        return true;
    }

    public final boolean isKeepScreenOn() {
        DisplayContent displayContent;
        boolean z = false;
        WindowState topVisibleAppMainWindow = getTopVisibleAppMainWindow(false);
        if (topVisibleAppMainWindow == null) {
            return false;
        }
        boolean z2 = (topVisibleAppMainWindow.mAttrs.flags & 128) != 0;
        if (z2) {
            return z2;
        }
        if (!inFullscreenWindowingMode() || (displayContent = this.mDisplayContent) == null) {
            return inSplitScreenWindowingMode() ? topVisibleAppMainWindow.canShowTransient() : z2;
        }
        WindowState windowState = displayContent.mDisplayPolicy.mStatusBar;
        if (windowState != null && windowState.isVisible()) {
            z = true;
        }
        return !z;
    }

    public final boolean isLeafTask() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (((WindowContainer) this.mChildren.get(size)).asTask() != null) {
                return false;
            }
        }
        return true;
    }

    public final boolean isLeftStash() {
        Rect bounds = getBounds();
        if (getDisplayContent() == null) {
            return false;
        }
        getDisplayContent().getStableRect(this.mTmpRect);
        return bounds.left < this.mTmpRect.left;
    }

    public final boolean isMinimized() {
        return this.mIsMinimized && supportsMinimizeState();
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final boolean isOrganized() {
        return this.mTaskOrganizer != null;
    }

    public final boolean isResizeable(boolean z) {
        if (isDexCompatEnabled()) {
            z = false;
        }
        if (this.mResizeMode == 10) {
            return false;
        }
        return (this.mAtmService.mForceResizableActivities && getActivityType() == 1) || ActivityInfo.isResizeableMode(this.mResizeMode) || (this.mSupportsPictureInPicture && z);
    }

    public final boolean isRootTask() {
        return getRootTask() == this;
    }

    public final boolean isSplitAdjustedMinimalBounds() {
        return inSplitScreenWindowingMode() && isLeafTask() && getParent() != null && (getParent().getBounds().width() < getBounds().width() || getParent().getBounds().height() < getBounds().height());
    }

    public final boolean isStageRootTask() {
        TaskDisplayArea taskDisplayArea = getTaskDisplayArea();
        return taskDisplayArea != null && inSplitScreenWindowingMode() && taskDisplayArea.getTopRootTaskInStageType(getStageType()) == this;
    }

    public final boolean isTopRootTaskInDisplayArea() {
        TaskDisplayArea displayArea = getDisplayArea();
        return displayArea != null && this == displayArea.getTopRootTask();
    }

    public final boolean isUnderHomeRootTask() {
        TaskDisplayArea displayArea = getDisplayArea();
        return displayArea != null && displayArea.isUnderHomeTask(getRootTask());
    }

    public final String lockTaskAuthToString() {
        int i = this.mLockTaskAuth;
        if (i == 0) {
            return "LOCK_TASK_AUTH_DONT_LOCK";
        }
        if (i == 1) {
            return "LOCK_TASK_AUTH_PINNABLE";
        }
        if (i == 2) {
            return "LOCK_TASK_AUTH_LAUNCHABLE";
        }
        if (i == 3) {
            return "LOCK_TASK_AUTH_ALLOWLISTED";
        }
        if (i == 4) {
            return "LOCK_TASK_AUTH_LAUNCHABLE_PRIV";
        }
        return "unknown=" + this.mLockTaskAuth;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl.Builder makeAnimationLeash() {
        return super.makeAnimationLeash().setMetadata(3, this.mTaskId);
    }

    public final void maybeApplyLastRecentsAnimationTransaction() {
        if (this.mLastRecentsAnimationTransaction != null) {
            SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
            SurfaceControl surfaceControl = this.mLastRecentsAnimationOverlay;
            if (surfaceControl != null) {
                pendingTransaction.reparent(surfaceControl, this.mSurfaceControl);
            }
            PictureInPictureSurfaceTransaction.apply(this.mLastRecentsAnimationTransaction, this.mSurfaceControl, pendingTransaction);
            pendingTransaction.show(this.mSurfaceControl);
            this.mLastRecentsAnimationTransaction = null;
            this.mLastRecentsAnimationOverlay = null;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void migrateToNewSurfaceControl(SurfaceControl.Transaction transaction) {
        super.migrateToNewSurfaceControl(transaction);
        Point point = this.mLastSurfaceSize;
        point.x = 0;
        point.y = 0;
        updateSurfaceSize(transaction);
    }

    public void moveTaskFragmentsToBottomIfNeeded(ActivityRecord activityRecord, int[] iArr) {
        int indexOf = this.mChildren.indexOf(activityRecord);
        if (indexOf < 0) {
            return;
        }
        ArrayList arrayList = null;
        for (int size = this.mChildren.size() - 1; size > indexOf; size--) {
            TaskFragment asTaskFragment = ((WindowContainer) this.mChildren.get(size)).asTaskFragment();
            if (asTaskFragment != null && asTaskFragment.mMoveToBottomIfClearWhenLaunch) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(asTaskFragment);
            }
        }
        if (arrayList == null) {
            return;
        }
        int size2 = arrayList.size();
        for (int i = 0; i < size2; i++) {
            TaskFragment taskFragment = (TaskFragment) arrayList.get(i);
            this.mTransitionController.collect(taskFragment);
            positionChildAt(Integer.MIN_VALUE, taskFragment, false);
        }
        iArr[0] = iArr[0] + size2;
    }

    public final boolean moveTaskToBack(Task task, Bundle bundle) {
        return moveTaskToBack(task, bundle, false, false, -1, -1);
    }

    public final boolean moveTaskToBack(final Task task, final Bundle bundle, final boolean z, boolean z2, final int i, final int i2) {
        TaskDisplayArea displayArea;
        if (!canMoveTaskToBack(task)) {
            return false;
        }
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING && isFreeformPinned() && !z && (displayArea = getDisplayArea()) != null) {
            displayArea.stopFreeformTaskPinning(this);
        }
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            if (z) {
                int i3 = !z2 ? 0 : 4;
                Transition transition = this.mTransitionController.mCollectingTransition;
                if (transition != null && transition.mType == i3 && isAttached() && canMoveTaskToBack(task)) {
                    moveTaskToBackInner(task, transition, bundle, z, i, i2);
                    this.mTransitionController.collect(task);
                    return true;
                }
            } else {
                Transition transition2 = this.mTransitionController.mCollectingTransition;
                if (transition2 != null && transition2.mType == 1) {
                    transition2.collect(task, false);
                    moveTaskToBackInner(task, transition2, null, false, -1, -1);
                    return true;
                }
            }
            final Transition transition3 = new Transition(4, 0, this.mTransitionController, this.mWmService.mSyncEngine);
            if (z2 && z) {
                transition3.addFlag(524288);
            }
            this.mTransitionController.startCollectOrQueue(transition3, new TransitionController.OnStartCollect() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda12
                @Override // com.android.server.wm.TransitionController.OnStartCollect
                public final void onCollectStarted(boolean z3) {
                    boolean z4;
                    Task taskBelow;
                    Bundle bundle2 = bundle;
                    Task task2 = Task.this;
                    task2.getClass();
                    Task task3 = task;
                    boolean isAttached = task3.isAttached();
                    Transition transition4 = transition3;
                    if (!isAttached || (z3 && !task2.canMoveTaskToBack(task3))) {
                        Slog.e("ActivityTaskManager", "Failed to move task to back after saying we could: " + task3.mTaskId);
                        transition4.abort();
                        return;
                    }
                    Task task4 = null;
                    task2.mTransitionController.requestStartTransition(transition4, task3, null, null);
                    boolean z5 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                    if (z5 && task2.inFullscreenWindowingMode() && (taskBelow = task2.getTaskDisplayArea().getTaskBelow(task2)) != null && taskBelow.getRootTask().isFullscreenRootForStageTask()) {
                        task4 = taskBelow.getRootTask();
                    }
                    Task task5 = task4;
                    if (task2.mIsDragSourceTask) {
                        task2.mTransitionController.collect(task3);
                        task2.mTransitionController.setHideWhileTwoHandDragging(task3);
                        z4 = true;
                    } else {
                        task2.mTransitionController.collect(task3);
                        z4 = false;
                    }
                    boolean z6 = z4;
                    task2.moveTaskToBackInner(task3, transition4, bundle2, z, i, i2);
                    if (!z5 || z6 || task5 == null) {
                        return;
                    }
                    task2.mTransitionController.collect(task5);
                }
            });
        } else {
            if (!inPinnedWindowingMode()) {
                this.mDisplayContent.prepareAppTransition(4, 0);
            }
            moveTaskToBackInner(task, null, bundle, z, i, i2);
        }
        return true;
    }

    public final void moveTaskToBackInner(Task task, Transition transition, Bundle bundle, boolean z, int i, int i2) {
        boolean z2;
        ActivityRecord activityRecord;
        Transition.ReadyCondition readyCondition = new Transition.ReadyCondition("moved-to-back", task);
        if (transition != null) {
            this.mAtmService.deferWindowLayout();
            transition.mReadyTracker.add(readyCondition);
        }
        try {
            if (CoreRune.MW_SA_LOGGING && task.inSplitScreenWindowingMode()) {
                CoreSaLogger.logForAdvanced("1005", "Tap 'Back' button");
            }
            if (FreeformController.useAlwaysOnTopFreeform(getWindowingMode(), this.mDisplayContent)) {
                setBoostTaskLayerForFreeform(true, false);
                super.setAlwaysOnTop(false);
                z2 = isFocusedRootTaskOnDisplay();
            } else if (inFreeformWindowingMode() && this.mDisplayContent.isDesktopModeEnabled()) {
                z2 = isFocusedRootTaskOnDisplay();
                if (z2 && z) {
                    setBoostTaskLayerForFreeform(true, false);
                }
            } else {
                z2 = false;
            }
            if (z) {
                setMinimized(i, i2);
            }
            if (isDexTaskDocked()) {
                this.mAtmService.mDexDockingController.clearAllTasks("moveTaskToBack");
            }
            moveToBack("moveTaskToBackInner", task);
            if (bundle != null && getDisplayContent().mDisplayContent.mAppTransition.getKeyguardTransition() > 0 && getDisplayContent().mDisplayContent.mAppTransition.getFirstAppTransition() > 0 && (activityRecord = topRunningActivityLocked()) != null) {
                activityRecord.updateOptionsLocked(ActivityOptions.fromBundle(bundle));
                activityRecord.applyOptionsAnimation$1();
            }
            if (inPinnedWindowingMode()) {
                this.mTaskSupervisor.removeRootTask(this);
                if (this.mTransitionController.isShellTransitionsEnabled()) {
                    this.mAtmService.continueWindowLayout();
                }
                if (transition != null) {
                    readyCondition.meet();
                    return;
                }
                return;
            }
            this.mRootWindowContainer.ensureVisibilityAndConfig(null, this.mDisplayContent, false);
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                this.mAtmService.continueWindowLayout();
            }
            if (transition != null) {
                readyCondition.meet();
            }
            ActivityRecord activityRecord2 = getDisplayArea().topRunningActivity(false);
            Task rootTask = activityRecord2 != null ? activityRecord2.getRootTask() : null;
            if (rootTask == null || rootTask == this || !activityRecord2.isState(ActivityRecord.State.RESUMED)) {
                this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                return;
            }
            if (z2) {
                this.mAtmService.setLastResumedActivityUncheckLocked(activityRecord2, "moveTaskToBackLocked");
            }
            this.mDisplayContent.executeAppTransition();
            this.mDisplayContent.setFocusedApp(activityRecord2);
        } catch (Throwable th) {
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                this.mAtmService.continueWindowLayout();
            }
            if (transition != null) {
                readyCondition.meet();
            }
            throw th;
        }
    }

    public final void moveTaskToFront(Task task, boolean z, ActivityOptions activityOptions, AppTimeTracker appTimeTracker, boolean z2, String str) {
        String str2 = task.affinity;
        try {
            if (!Pageboost.isPageboostMinimized() && str2 != null) {
                Slog.i("Pageboost", "moveTaskToFront : ".concat(str2));
                Pageboost.sendMessage(10, 0, 0, -1, 0, str2);
            }
        } catch (Exception unused) {
            Slog.e("Pageboost", "failed to moveTaskToFront by exception");
        }
        ActivityRecord findEnterPipOnTaskSwitchCandidate = findEnterPipOnTaskSwitchCandidate(getDisplayArea().getRootTask(1, 0));
        ActivityRecord.State state = ActivityRecord.State.RESUMED;
        if (task != this && !task.isDescendantOf(this)) {
            if (z) {
                ActivityOptions.abort(activityOptions);
                return;
            }
            if (activityOptions != null) {
                ActivityRecord activityRecord = topRunningActivity(false);
                if (activityRecord == null || activityRecord.isState(state)) {
                    ActivityOptions.abort(activityOptions);
                } else {
                    activityRecord.updateOptionsLocked(activityOptions);
                }
            }
            this.mDisplayContent.prepareAppTransition(3, 0);
            return;
        }
        if (appTimeTracker != null) {
            task.forAllActivities(new Task$$ExternalSyntheticLambda8(0, appTimeTracker));
        }
        try {
            if (FreeformController.useAlwaysOnTopFreeform(getWindowingMode(), this.mDisplayContent) && !isAlwaysOnTopFreeform()) {
                super.setAlwaysOnTop(true);
            }
            this.mAtmService.mFreeformController.releaseForceHideTaskLocked(this);
            if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY && getDisplayId() == 0 && inFreeformWindowingMode() && isMinimized()) {
                this.mAtmService.mFreeformController.updateFreeformBoundsForDisplayDeviceTypeChanged(task);
            }
            setUnMinimizedWhenRestored();
            DisplayContent displayContent = this.mDisplayContent;
            int i = displayContent.mDeferUpdateImeTargetCount;
            if (i == 0) {
                displayContent.mUpdateImeRequestedWhileDeferred = false;
            }
            displayContent.mDeferUpdateImeTargetCount = i + 1;
            ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity(true, true);
            if (topNonFinishingActivity != null && topNonFinishingActivity.showToCurrentUser()) {
                topNonFinishingActivity.moveFocusableActivityToTop(str);
                if (z) {
                    this.mDisplayContent.prepareAppTransition(0, 0);
                    this.mTaskSupervisor.mNoAnimActivities.add(topNonFinishingActivity);
                    this.mTransitionController.collect(topNonFinishingActivity);
                    this.mTransitionController.setNoAnimation(topNonFinishingActivity);
                    ActivityOptions.abort(activityOptions);
                } else {
                    if (activityOptions != null) {
                        ActivityRecord activityRecord2 = topRunningActivity(false);
                        if (activityRecord2 == null || activityRecord2.isState(state)) {
                            ActivityOptions.abort(activityOptions);
                        } else {
                            activityRecord2.updateOptionsLocked(activityOptions);
                        }
                    }
                    this.mDisplayContent.prepareAppTransition(3, 0);
                }
                if (findEnterPipOnTaskSwitchCandidate != null && findEnterPipOnTaskSwitchCandidate.task != topNonFinishingActivity.task) {
                    enableEnterPipOnTaskSwitch(activityOptions, findEnterPipOnTaskSwitchCandidate, topNonFinishingActivity, task);
                }
                if (!z2) {
                    this.mRootWindowContainer.resumeFocusedTasksTopActivities();
                }
                this.mDisplayContent.continueUpdateImeTarget();
                return;
            }
            positionChildAtTop(task);
            if (topNonFinishingActivity != null) {
                this.mTaskSupervisor.mRecentTasks.add(topNonFinishingActivity.task);
            }
            ActivityOptions.abort(activityOptions);
            this.mDisplayContent.continueUpdateImeTarget();
        } catch (Throwable th) {
            this.mDisplayContent.continueUpdateImeTarget();
            throw th;
        }
    }

    public final void moveToBack(String str, Task task) {
        if (isAttached()) {
            TaskDisplayArea displayArea = getDisplayArea();
            if (!this.mCreatedByOrganizer) {
                WindowContainer parent = getParent();
                Task asTask = parent != null ? parent.asTask() : null;
                if (asTask != null) {
                    asTask.moveToBack(str, this);
                } else {
                    Task focusedRootTask = displayArea.getFocusedRootTask();
                    displayArea.positionChildAt(Integer.MIN_VALUE, this, false);
                    displayArea.updateLastFocusedRootTask(str, focusedRootTask);
                }
                if (task == null || task == this) {
                    return;
                }
                positionChildAtBottom(task, getDisplayArea().getNextFocusableRootTask(task.getRootTask(), true) == null);
                return;
            }
            if (task == null || task == this) {
                return;
            }
            Task createdByOrganizerTask = task.getCreatedByOrganizerTask();
            if (!CoreRune.MW_SPLIT_SHELL_TRANSITION || this.mTransitionController.getCollectingTransitionType() != 4 || createdByOrganizerTask == null || !createdByOrganizerTask.inSplitScreenWindowingMode() || createdByOrganizerTask != task.getParent() || !isFullscreenRootForStageTask() || !task.inSplitScreenWindowingMode()) {
                displayArea.positionTaskBehindHome(task);
                return;
            }
            if (createdByOrganizerTask.mChildren.size() != 1 && createdByOrganizerTask.mChildren.indexOf(task) > 0) {
                ChangeTransitionController changeTransitionController = this.mAtmService.mChangeTransitController;
                changeTransitionController.getClass();
                if (!task.isChangeTransitionBlockedByCommonPolicy()) {
                    Slog.d("ChangeTransitionController", "handlePositionTaskBehindHome: #" + task.mTaskId);
                    changeTransitionController.requestChangeTransition(task, 6, task.getWindowingMode(), new Rect(task.getBounds()), "position_behind_home", 0);
                }
                displayArea.positionTaskBehindHome(task);
            }
        }
    }

    public void moveToFront(String str, Task task) {
        ActivityRecord activityRecord;
        if (CoreRune.SYSFW_APP_SPEG && !Build.IS_USER && (activityRecord = topRunningActivity(false)) != null) {
            String str2 = activityRecord.packageName;
            DisplayManager displayManager = (DisplayManager) this.mAtmService.mContext.getSystemService(DisplayManager.class);
            if (displayManager == null) {
                Slog.e("SPEG", "Can't get DisplayManager");
            } else {
                int hiddenDisplayId = displayManager.getHiddenDisplayId(str2);
                int displayId = getDisplayId();
                if (hiddenDisplayId != -1 && hiddenDisplayId != displayId) {
                    StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(hiddenDisplayId, "There is speg display ", " for ", str2, ", but ");
                    m.append(activityRecord.mActivityComponent);
                    m.append(" moveToFront on other display ");
                    m.append(displayId);
                    Slog.e("SPEG", m.toString());
                    Slog.e("SPEG", "Backtrace: " + Log.getStackTraceString(new Throwable()));
                }
            }
        }
        if (getTaskDisplayArea() != null && isActivityTypeHome()) {
            TransitionController transitionController = this.mAtmService.mWindowOrganizerController.mTransitionController;
            Task focusedRootTask = getTaskDisplayArea().getFocusedRootTask();
            if (transitionController.mCollectingTransition != null && focusedRootTask != null && focusedRootTask.mIsDragSourceTask) {
                transitionController.collect(focusedRootTask);
                transitionController.setHideWhileTwoHandDragging(focusedRootTask);
            }
        }
        if (FreeformController.useAlwaysOnTopFreeform(getWindowingMode(), this.mDisplayContent) && !isAlwaysOnTopFreeform()) {
            super.setAlwaysOnTop(true);
        }
        if (CoreRune.MW_SA_LOGGING && inFreeformWindowingMode() && ((!isMinimized() && !isVisible()) || isDesktopModeEnabled())) {
            this.mNeedToSendFreeformLogging = true;
        }
        if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY && getDisplayId() == 0 && inFreeformWindowingMode() && isMinimized()) {
            this.mAtmService.mFreeformController.updateFreeformBoundsForDisplayDeviceTypeChanged(task != null ? task : getTopMostTask());
        }
        setUnMinimizedWhenRestored();
        if (isAttached()) {
            Transition transition = this.mTransitionController.mCollectingTransition;
            if (transition != null) {
                transition.recordDisplay(getDisplayContent());
            }
            TaskDisplayArea displayArea = getDisplayArea();
            if (isDexTaskDocked()) {
                final DexDockingController dexDockingController = this.mAtmService.mDexDockingController;
                dexDockingController.getClass();
                Slog.d("DexDockingController", "moveTaskToFrontIfNeeded=" + this);
                if (!dexDockingController.mSkipMoveToFrontList.contains(this)) {
                    dexDockingController.mSkipMoveToFrontList.add(this);
                    final int i = getDexTaskDockingState() == 1 ? 2 : 1;
                    dexDockingController.mAtm.mRootWindowContainer.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexDockingController$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            DexDockingController dexDockingController2 = DexDockingController.this;
                            int i2 = i;
                            Task task2 = (Task) obj;
                            dexDockingController2.getClass();
                            if (task2.getDexTaskDockingState() != i2 || task2.getRootTask() == null || dexDockingController2.mSkipMoveToFrontList.contains(task2)) {
                                return;
                            }
                            task2.getRootTask().moveToFront("DexDocking", task2);
                        }
                    });
                    dexDockingController.mSkipMoveToFrontList.remove(this);
                }
            }
            if (!isActivityTypeHome() && returnsToHomeRootTask()) {
                displayArea.moveHomeRootTaskToFront(str + " returnToHome");
                if (inFreeformWindowingMode() && getDisplayId() == 0) {
                    Task task2 = displayArea.mRootHomeTask;
                    Task topLeafTask = task2 != null ? task2.getTopLeafTask() : null;
                    if (topLeafTask != null) {
                        this.mAtmService.mTaskChangeNotificationController.notifyTaskMovedToFront(topLeafTask.getTaskInfo());
                    }
                }
            }
            Task focusedRootTask2 = isRootTask() ? displayArea.getFocusedRootTask() : null;
            if (task == null) {
                task = this;
            }
            this.mAtmService.mFreeformController.releaseForceHideTaskLocked(this);
            task.setUnMinimizedWhenRestored();
            task.getParent().positionChildAt(Integer.MAX_VALUE, task, true);
            displayArea.updateLastFocusedRootTask(str, focusedRootTask2);
        }
    }

    public final boolean navigateUpTo(ActivityRecord activityRecord, Intent intent, String str, NeededUriGrants neededUriGrants, int i, Intent intent2, NeededUriGrants neededUriGrants2) {
        boolean z;
        ActivityRecord activityRecord2;
        boolean z2;
        ActivityRecord activity;
        if (!activityRecord.attachedToProcess()) {
            return false;
        }
        Task task = activityRecord.task;
        if (!activityRecord.isDescendantOf(this)) {
            return false;
        }
        final ActivityRecord activityBelow = task.getActivityBelow(activityRecord);
        ComponentName component = intent.getComponent();
        if (task.getBottomMostActivity() == activityRecord || component == null || (activity = task.getActivity(new Task$$ExternalSyntheticLambda19(2, component), activityRecord, false, true)) == null) {
            z = false;
        } else {
            activityBelow = activity;
            z = true;
        }
        IActivityController iActivityController = this.mAtmService.mController;
        if (iActivityController != null && (activityRecord2 = topRunningActivity(-1, activityRecord.token)) != null) {
            try {
                z2 = iActivityController.activityResuming(activityRecord2.packageName);
            } catch (RemoteException unused) {
                this.mAtmService.mController = null;
                Watchdog.getInstance().setActivityController(null);
                z2 = true;
            }
            if (!z2) {
                return false;
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        final int[] iArr = {i};
        final Intent[] intentArr = {intent2};
        final NeededUriGrants[] neededUriGrantsArr = {neededUriGrants2};
        task.forAllActivities(new Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                ActivityRecord activityRecord3 = ActivityRecord.this;
                int[] iArr2 = iArr;
                Intent[] intentArr2 = intentArr;
                NeededUriGrants[] neededUriGrantsArr2 = neededUriGrantsArr;
                ActivityRecord activityRecord4 = (ActivityRecord) obj;
                if (activityRecord4 == activityRecord3) {
                    return true;
                }
                activityRecord4.finishIfPossible(iArr2[0], intentArr2[0], neededUriGrantsArr2[0], "navigate-up", true);
                iArr2[0] = 0;
                intentArr2[0] = null;
                return false;
            }
        }, activityRecord, true, true);
        int i2 = iArr[0];
        Intent intent3 = intentArr[0];
        if (activityBelow != null && z) {
            int i3 = activityRecord.info.applicationInfo.uid;
            ActivityStarter obtainStarter = this.mAtmService.mActivityStartController.obtainStarter(intent, "navigateUpTo");
            ActivityStarter.Request request = obtainStarter.mRequest;
            request.resolvedType = str;
            request.userId = activityRecord.mUserId;
            request.caller = activityRecord.app.mThread;
            request.resultTo = activityBelow.token;
            request.intentGrants = neededUriGrants;
            request.callingPid = -1;
            request.callingUid = i3;
            request.callingPackage = activityRecord.packageName;
            request.callingFeatureId = activityBelow.launchedFromFeatureId;
            request.realCallingPid = -1;
            request.realCallingUid = i3;
            request.componentSpecified = true;
            int execute = obtainStarter.execute();
            z = ActivityManager.isStartResultSuccessful(execute);
            if (execute == 0) {
                activityBelow.finishIfPossible(i2, intent3, neededUriGrants2, "navigate-top", true);
            }
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        return z;
    }

    public final void notifyActivityDrawnLocked(ActivityRecord activityRecord) {
        if (activityRecord == null || (this.mUndrawnActivitiesBelowTopTranslucent.remove(activityRecord) && this.mUndrawnActivitiesBelowTopTranslucent.isEmpty())) {
            ActivityRecord activityRecord2 = this.mTranslucentActivityWaiting;
            this.mTranslucentActivityWaiting = null;
            this.mUndrawnActivitiesBelowTopTranslucent.clear();
            this.mHandler.removeMessages(101);
            if (activityRecord2 != null) {
                boolean z = true;
                WindowState findMainWindow = activityRecord2.findMainWindow(true);
                if (findMainWindow != null) {
                    PixelFormat.formatHasAlpha(findMainWindow.mAttrs.format);
                    findMainWindow.mWinAnimator.setOpaqueLocked(false);
                }
                if (activityRecord2.attachedToProcess()) {
                    try {
                        IApplicationThread iApplicationThread = activityRecord2.app.mThread;
                        IBinder iBinder = activityRecord2.token;
                        if (activityRecord == null) {
                            z = false;
                        }
                        iApplicationThread.scheduleTranslucentConversionComplete(iBinder, z);
                    } catch (RemoteException unused) {
                    }
                }
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
        super.onAnimationFinished(i, animationAdapter);
        if (this.mHiddenWhileActivatingDrag) {
            updateSurfaceVisibilityForDragAndDrop();
        } else if (inSplitScreenWindowingMode()) {
            forAllLeafTasks(new Task$$ExternalSyntheticLambda6(0), true);
        }
    }

    public final void onAppFocusChanged(boolean z) {
        dispatchTaskInfoChangedIfNeeded(false);
        Task asTask = getParent().asTask();
        if (asTask != null) {
            asTask.dispatchTaskInfoChangedIfNeeded(false);
        }
        TaskChangeNotificationController taskChangeNotificationController = this.mAtmService.mTaskChangeNotificationController;
        Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(24, this.mTaskId, z ? 1 : 0);
        taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskFocusChanged, obtainMessage);
        obtainMessage.sendToTarget();
        if (this.mHasWindowFocus != z) {
            this.mHasWindowFocus = z;
            updateWindowFocusInTask();
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final void onChildPositionChanged(WindowContainer windowContainer) {
        dispatchTaskInfoChangedIfNeeded(false);
        if (this.mChildren.contains(windowContainer)) {
            if (windowContainer.asTask() != null) {
                RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
                if (!rootWindowContainer.mTaskLayersChanged) {
                    rootWindowContainer.mTaskLayersChanged = true;
                    rootWindowContainer.mService.mH.post(rootWindowContainer.mRankTaskLayersRunnable);
                }
            }
            if (windowContainer.asActivityRecord() != null) {
                sendTaskFragmentParentInfoChangedIfNeeded();
            }
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final boolean onChildVisibleRequestedChanged(WindowContainer windowContainer) {
        if (!super.onChildVisibleRequestedChanged(windowContainer)) {
            return false;
        }
        sendTaskFragmentParentInfoChangedIfNeeded();
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0072, code lost:
    
        if ((r0 == null ? false : r0.mTask.inFreeformWindowingMode()) != false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ca, code lost:
    
        if (r4 == false) goto L58;
     */
    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onConfigurationChanged(android.content.res.Configuration r9) {
        /*
            r8 = this;
            com.android.server.wm.DisplayContent r0 = r8.mDisplayContent
            if (r0 == 0) goto L15
            com.android.server.wm.PinnedTaskController r0 = r0.mPinnedTaskController
            boolean r1 = r0.mFreezingTaskConfig
            if (r1 == 0) goto L15
            com.android.server.wm.DisplayContent r0 = r0.mDisplayContent
            com.android.server.wm.TaskDisplayArea r0 = r0.getDefaultTaskDisplayArea()
            com.android.server.wm.Task r0 = r0.mRootPinnedTask
            if (r8 != r0) goto L15
            return
        L15:
            boolean r0 = r8.isRootTask()
            if (r0 != 0) goto L1f
            r8.onConfigurationChangedInner(r9)
            return
        L1f:
            int r0 = r8.getWindowingMode()
            boolean r1 = r8.isAlwaysOnTop()
            android.app.WindowConfiguration r2 = r8.getWindowConfiguration()
            int r2 = r2.getRotation()
            android.graphics.Rect r3 = r8.mTmpRect
            r8.getBounds(r3)
            r8.onConfigurationChangedInner(r9)
            com.android.server.wm.TaskDisplayArea r9 = r8.getDisplayArea()
            if (r9 != 0) goto L3e
            return
        L3e:
            int r4 = r8.getWindowingMode()
            r5 = 2147483647(0x7fffffff, float:NaN)
            r6 = 0
            if (r0 == r4) goto L5b
            r9.removeRootTaskReferenceIfNeeded(r8)
            r9.addRootTaskReferenceIfNeeded(r8)
            com.android.server.wm.Task r0 = r9.mRootPinnedTask
            if (r8 != r0) goto L5b
            com.android.server.wm.Task r0 = r9.getTopRootTask()
            if (r0 == r8) goto L5b
            r9.positionChildAt(r5, r8, r6)
        L5b:
            boolean r0 = com.samsung.android.rune.CoreRune.MT_SIZE_COMPAT_POLICY
            if (r0 == 0) goto L76
            com.android.server.wm.SizeCompatPolicyManager r0 = com.android.server.wm.SizeCompatPolicyManager.LazyHolder.sManager
            r0.getClass()
            com.android.server.wm.DexSizeCompatController$DexSizeCompatPolicy r0 = com.android.server.wm.SizeCompatPolicyManager.getCompatPolicy(r8, r6)
            if (r0 != 0) goto L6c
            r0 = r6
            goto L72
        L6c:
            com.android.server.wm.Task r0 = r0.mTask
            boolean r0 = r0.inFreeformWindowingMode()
        L72:
            if (r0 == 0) goto L76
            goto Lf0
        L76:
            boolean r0 = r8.isOrganized()
            if (r0 == 0) goto L82
            boolean r0 = r8.inFreeformWindowingMode()
            if (r0 == 0) goto Lf0
        L82:
            android.graphics.Rect r0 = r8.getRequestedOverrideBounds()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto Lf0
            com.android.server.wm.DisplayContent r0 = r8.mDisplayContent
            if (r0 == 0) goto Lf0
            android.app.WindowConfiguration r0 = r8.getWindowConfiguration()
            int r0 = r0.getRotation()
            r4 = 1
            if (r2 == r0) goto L9d
            r7 = r4
            goto L9e
        L9d:
            r7 = r6
        L9e:
            if (r7 == 0) goto Lf0
            boolean r7 = com.samsung.android.rune.CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY
            if (r7 == 0) goto Lcc
            boolean r7 = r8.inFreeformWindowingMode()
            if (r7 == 0) goto Lc3
            com.android.server.wm.DisplayContent r7 = r8.getDisplayContent()
            if (r7 == 0) goto Lc3
            com.android.server.wm.DisplayContent r7 = r8.getDisplayContent()
            boolean r7 = r7.isDefaultDisplay
            if (r7 != 0) goto Lb9
            goto Lc3
        Lb9:
            com.android.server.wm.DisplayContent r7 = r8.getDisplayContent()
            boolean r7 = r7.isDexMode()
            if (r7 == 0) goto Lc5
        Lc3:
            r4 = r6
            goto Lca
        Lc5:
            com.android.server.wm.WindowManagerService r7 = r8.mWmService
            r7.getClass()
        Lca:
            if (r4 != 0) goto Lea
        Lcc:
            com.android.server.wm.DisplayContent r4 = r8.getDisplayContent()
            boolean r4 = r4.isDexMode()
            if (r4 == 0) goto Lde
            com.android.server.wm.DisplayContent r4 = r8.getDisplayContent()
            boolean r4 = r4.isDefaultDisplay
            if (r4 != 0) goto Lea
        Lde:
            boolean r4 = r8.isFreeformStashed()
            if (r4 == 0) goto Le5
            goto Lea
        Le5:
            com.android.server.wm.DisplayContent r4 = r8.mDisplayContent
            r4.rotateBounds(r2, r3, r0)
        Lea:
            r8.setBounds(r3)
            r8.saveFreeformBoundsIfNeeded()
        Lf0:
            boolean r0 = r8.isAlwaysOnTop()
            if (r1 == r0) goto Lf9
            r9.positionChildAt(r5, r8, r6)
        Lf9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.onConfigurationChanged(android.content.res.Configuration):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:180:0x02ed, code lost:
    
        if (r0.height() == r2.height()) goto L171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x02fa, code lost:
    
        if ((r11 == 5) != (r1 == 5)) goto L170;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onConfigurationChangedInner(android.content.res.Configuration r16) {
        /*
            Method dump skipped, instructions count: 947
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.onConfigurationChangedInner(android.content.res.Configuration):void");
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean onDescendantOrientationChanged(WindowContainer windowContainer) {
        if (super.onDescendantOrientationChanged(windowContainer)) {
            return true;
        }
        if (getParent() == null) {
            return false;
        }
        onConfigurationChanged(getParent().getConfiguration());
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void onDisplayChanged(DisplayContent displayContent) {
        super.onDisplayChanged(displayContent);
        if (isLeafTask()) {
            int i = displayContent != null ? displayContent.mDisplayId : -1;
            TaskChangeNotificationController taskChangeNotificationController = this.mWmService.mAtmService.mTaskChangeNotificationController;
            Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(21, this.mTaskId, i);
            taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskDisplayChanged, obtainMessage);
            obtainMessage.sendToTarget();
        }
        if (isRootTask()) {
            updateSurfaceSize(getSyncTransaction());
            updateSurfacePositionNonOrganized();
            scheduleAnimation();
        }
        sendTaskFragmentParentInfoChangedIfNeeded();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x006c, code lost:
    
        if (((r5 == null || (r5 = r5.mDisplayWindowPolicyController) == null) ? true : r5.canShowTasksInHostDeviceRecents()) == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x007c, code lost:
    
        if (r6 != false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007a, code lost:
    
        if (r9.mTaskSupervisor.mRecentTasks.okToRemove(r9) == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d4, code lost:
    
        if (inSplitScreenWindowingMode() == false) goto L71;
     */
    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onParentChanged(com.android.server.wm.ConfigurationContainer r10, com.android.server.wm.ConfigurationContainer r11) {
        /*
            Method dump skipped, instructions count: 649
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.onParentChanged(com.android.server.wm.ConfigurationContainer, com.android.server.wm.ConfigurationContainer):void");
    }

    public final boolean onlyHasTaskOverlayActivities(boolean z) {
        int i = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            ActivityRecord asActivityRecord = getChildAt(childCount).asActivityRecord();
            if (asActivityRecord == null) {
                return false;
            }
            if (z || !asActivityRecord.finishing) {
                if (!asActivityRecord.mTaskOverlay) {
                    return false;
                }
                i++;
            }
        }
        return i > 0;
    }

    public final boolean pauseActivityIfNeeded(final ActivityRecord activityRecord, final String str) {
        if (!isLeafTask()) {
            return false;
        }
        final int[] iArr = {0};
        if (!isLeafTaskFragment()) {
            ActivityRecord activityRecord2 = topRunningActivity(false);
            if (this.mResumedActivity != null && ((activityRecord2 == null || activityRecord2.getTaskFragment() != this || !canBeResumed(activityRecord)) && startPausing$1(activityRecord, str, this.mTaskSupervisor.mUserLeaving, false))) {
                iArr[0] = iArr[0] + 1;
            }
        }
        forAllLeafTaskFragments(new Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda34
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Task task = Task.this;
                ActivityRecord activityRecord3 = activityRecord;
                String str2 = str;
                int[] iArr2 = iArr;
                TaskFragment taskFragment = (TaskFragment) obj;
                task.getClass();
                if (taskFragment.mResumedActivity == null || taskFragment.canBeResumed(activityRecord3) || !taskFragment.startPausing$1(activityRecord3, str2, taskFragment.mTaskSupervisor.mUserLeaving, false)) {
                    return;
                }
                iArr2[0] = iArr2[0] + 1;
            }
        }, true);
        return iArr[0] > 0;
    }

    public final void performClearTaskForReuse(boolean z) {
        this.mReuseTask = true;
        this.mTaskSupervisor.beginDeferResume();
        try {
            removeActivities("clear-task-all", z);
        } finally {
            this.mTaskSupervisor.endDeferResume();
            this.mReuseTask = false;
        }
    }

    public final ActivityRecord performClearTop(ActivityRecord activityRecord, int i, int[] iArr) {
        this.mReuseTask = true;
        this.mTaskSupervisor.beginDeferResume();
        try {
            return clearTopActivities(activityRecord, i, iArr);
        } finally {
            this.mTaskSupervisor.endDeferResume();
            this.mReuseTask = false;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void positionChildAt(int i, WindowContainer windowContainer, boolean z) {
        boolean z2 = i >= this.mChildren.size() - 1;
        int adjustedChildPosition = getAdjustedChildPosition(i, windowContainer);
        super.positionChildAt(adjustedChildPosition, windowContainer, z);
        Task asTask = windowContainer.asTask();
        if (asTask != null) {
            asTask.updateTaskMovement(adjustedChildPosition, z2, adjustedChildPosition == Integer.MIN_VALUE);
        }
    }

    public void positionChildAtBottom(Task task, boolean z) {
        if (task == null) {
            return;
        }
        positionChildAt(Integer.MIN_VALUE, task, z);
    }

    public final void positionChildAtTop(Task task) {
        if (task == null) {
            return;
        }
        if (task == this) {
            moveToFront("positionChildAtTop", null);
        } else {
            positionChildAt(Integer.MAX_VALUE, task, true);
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final void prepareSurfaces() {
        boolean isKeepScreenOn;
        boolean isCaptionHandlerHidden;
        SurfaceControl surfaceControl;
        Dimmer.DimState dimState = this.mDimmer.mDimState;
        if (dimState != null) {
            dimState.mLastDimmingWindow = null;
        }
        super.prepareSurfaces();
        Dimmer.DimState dimState2 = this.mDimmer.mDimState;
        Rect rect = dimState2 != null ? dimState2.mDimBounds : null;
        if (rect != null) {
            getDimBounds(rect);
            if (inFreeformWindowingMode()) {
                getBounds(this.mTmpRect);
                int i = rect.left;
                Rect rect2 = this.mTmpRect;
                rect.offsetTo(i - rect2.left, rect.top - rect2.top);
            } else {
                rect.offsetTo(0, 0);
            }
        }
        SurfaceControl.Transaction syncTransaction = getSyncTransaction();
        if (rect != null && this.mDimmer.updateDims(syncTransaction)) {
            scheduleAnimation();
        }
        if (this.mTransitionController.isCollecting() && this.mCreatedByOrganizer) {
            return;
        }
        boolean z = CoreRune.MW_SPLIT_SHELL_TRANSITION;
        boolean z2 = true;
        if (z && z && isFullscreenRootForStageTask()) {
            if (this.mTransitionController.isCollecting()) {
                return;
            }
            TransitionController transitionController = this.mTransitionController;
            for (int size = transitionController.mPlayingTransitions.size() - 1; size >= 0; size--) {
                if (((Transition) transitionController.mPlayingTransitions.get(size)).mTargets.stream().anyMatch(new Predicate() { // from class: com.android.server.wm.TransitionController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return Objects.equals(((Transition.ChangeInfo) obj).mContainer, WindowContainer.this);
                    }
                })) {
                    return;
                }
            }
        }
        boolean isVisible = isVisible();
        if (!isVisible && !isAnimating(7)) {
            z2 = false;
        }
        if (this.mSurfaceControl != null && z2 != this.mLastSurfaceShowing) {
            if (!inTransition() && this.mHiddenWhileActivatingDrag && z2) {
                syncTransaction.setVisibility(this.mSurfaceControl, false);
                return;
            }
            syncTransaction.setVisibility(this.mSurfaceControl, z2);
        }
        TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
        if (trustedOverlayHost != null && (surfaceControl = trustedOverlayHost.mSurfaceControl) != null) {
            syncTransaction.setVisibility(surfaceControl, isVisible);
        }
        this.mLastSurfaceShowing = z2;
        if (CoreRune.MW_CAPTION_SHELL && this.mIsCaptionHandlerHidden != (isCaptionHandlerHidden = isCaptionHandlerHidden())) {
            this.mIsCaptionHandlerHidden = isCaptionHandlerHidden;
            dispatchTaskInfoChangedIfNeeded(false);
        }
        if (!CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON || (isKeepScreenOn = isKeepScreenOn()) == this.mKeepScreenOn) {
            return;
        }
        this.mKeepScreenOn = isKeepScreenOn;
        dispatchTaskInfoChangedIfNeeded(false);
    }

    public final void removeActivities(final String str, final boolean z) {
        Task task;
        ActivityRecord activityRecord = this.mChildPipActivity;
        if (activityRecord != null && (task = activityRecord.task) != null) {
            this.mTaskSupervisor.removeRootTask(task);
        }
        if (getRootTask() == null) {
            forAllActivities(new Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda36
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Task task2 = Task.this;
                    boolean z2 = z;
                    String str2 = str;
                    ActivityRecord activityRecord2 = (ActivityRecord) obj;
                    task2.getClass();
                    if (activityRecord2.finishing) {
                        return;
                    }
                    if (z2 && activityRecord2.mTaskOverlay) {
                        return;
                    }
                    if (activityRecord2.inHistory) {
                        activityRecord2.inHistory = false;
                        if (activityRecord2.task != null && !activityRecord2.finishing) {
                            activityRecord2.task = null;
                        }
                        activityRecord2.abortAndClearOptionsAnimation();
                    }
                    task2.removeChild(activityRecord2, str2);
                }
            });
            return;
        }
        ArrayList arrayList = new ArrayList();
        forAllActivities(new Task$$ExternalSyntheticLambda3(arrayList, 3, z));
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ActivityRecord activityRecord2 = (ActivityRecord) arrayList.get(size);
            if (activityRecord2.isState(ActivityRecord.State.RESUMED) || (activityRecord2.mVisible && !this.mDisplayContent.mAppTransition.containsTransitRequest(2))) {
                activityRecord2.finishIfPossible(str, false);
            } else {
                activityRecord2.destroyIfPossible(str);
            }
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final void removeChild(WindowContainer windowContainer) {
        removeChild(windowContainer, "removeChild");
    }

    public final void removeChild(WindowContainer windowContainer, String str) {
        if (this.mCreatedByOrganizer && windowContainer.asTask() != null) {
            getDisplayArea().removeRootTaskReferenceIfNeeded((Task) windowContainer);
        }
        if (!this.mChildren.contains(windowContainer)) {
            Slog.e("ActivityTaskManager", "removeChild: r=" + windowContainer + " not found in t=" + this);
            return;
        }
        removeChild(windowContainer, false);
        if (inPinnedWindowingMode()) {
            this.mAtmService.mTaskChangeNotificationController.notifyTaskStackChanged();
        }
        DecorSurfaceContainer decorSurfaceContainer = this.mDecorSurfaceContainer;
        if (decorSurfaceContainer != null && windowContainer == decorSurfaceContainer.mOwnerTaskFragment && decorSurfaceContainer != null) {
            Task.this.getSyncTransaction().remove(decorSurfaceContainer.mDecorSurface).remove(decorSurfaceContainer.mContainerSurface);
            this.mDecorSurfaceContainer = null;
            sendTaskFragmentParentInfoChangedIfNeeded();
        }
        if (hasChild()) {
            updateEffectiveIntent();
            if (onlyHasTaskOverlayActivities(true)) {
                this.mTaskSupervisor.removeTask(this, false, false, str, false, 1000, -1);
                return;
            }
            return;
        }
        if (this.mReuseTask || !shouldRemoveSelfOnLastChildRemoval()) {
            return;
        }
        String str2 = str + ", last child = " + windowContainer + " in " + this;
        if (isDexTaskDocked()) {
            this.mAtmService.mDexDockingController.clearAllTasks("remove task");
        }
        removeIfPossible(str2);
        DexController dexController = this.mAtmService.mDexController;
        if (dexController.getDexModeLocked() == 2) {
            DexController.PendingActivityInfo pendingActivityInfo = dexController.mPendingActivityInfo;
            if (pendingActivityInfo.removeWaitingStoppedTask("taskRemoved", this) && pendingActivityInfo.mWaitingStoppedTasks.isEmpty() && pendingActivityInfo.mOrganizedTaskFragments.size() <= 0) {
                dexController.scheduleReparentToDisplayAndStartPendingActivity(true);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeIfPossible() {
        removeIfPossible("removeTaskIfPossible");
    }

    public final void removeIfPossible(String str) {
        this.mAtmService.mLockTaskController.clearLockedTask(this);
        if (!hasChild() ? false : isExitAnimationRunningSelfOrChild()) {
            return;
        }
        boolean isLeafTask = isLeafTask();
        removeImmediately(str);
        if (isLeafTask) {
            TaskChangeNotificationController taskChangeNotificationController = this.mAtmService.mTaskChangeNotificationController;
            Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(9, this.mTaskId, 0);
            taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskRemoved, obtainMessage);
            obtainMessage.sendToTarget();
            TaskDisplayArea displayArea = getDisplayArea();
            if (displayArea != null) {
                if (displayArea.mLastLeafTaskToFrontId == this.mTaskId) {
                    displayArea.mLastLeafTaskToFrontId = -1;
                }
            }
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final void removeImmediately() {
        removeImmediately("removeTask");
    }

    @Override // com.android.server.wm.TaskFragment
    public final void removeImmediately(String str) {
        Rect rect;
        TaskDisplayArea displayArea;
        Task task;
        if (this.mRemoving) {
            return;
        }
        this.mRemoving = true;
        EventLog.writeEvent(31003, Integer.valueOf(this.mTaskId), Integer.valueOf(getRootTask().mTaskId), Integer.valueOf(getDisplayId()), str);
        ActivityRecord activityRecord = this.mChildPipActivity;
        if (activityRecord != null && (task = activityRecord.task) != null) {
            this.mTaskSupervisor.removeRootTask(task);
        }
        ActivityRecord activityRecord2 = this.mChildPipActivity;
        if (activityRecord2 != null) {
            Task task2 = activityRecord2.mLastParentBeforePip;
            if (task2 != null) {
                task2.mChildPipActivity = null;
                activityRecord2.mLastParentBeforePip = null;
            }
            activityRecord2.mLaunchIntoPipHostActivity = null;
            activityRecord2.mLastTaskFragmentOrganizerBeforePip = null;
            activityRecord2.mLastEmbeddedParentTfTokenBeforePip = null;
        }
        setTaskOrganizer(null);
        DecorSurfaceContainer decorSurfaceContainer = this.mDecorSurfaceContainer;
        if (decorSurfaceContainer != null) {
            Task.this.getSyncTransaction().remove(decorSurfaceContainer.mDecorSurface).remove(decorSurfaceContainer.mContainerSurface);
        }
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING && isFreeformPinned() && (displayArea = getDisplayArea()) != null) {
            displayArea.stopFreeformTaskPinning(this);
        }
        if (this.mRemoveByDrag) {
            Rect rect2 = this.mLastNonFullscreenBounds;
            if (rect2 != null && (rect = this.mLastFreeformBoundsBeforeDragMoving) != null && !rect2.equals(rect)) {
                this.mLastNonFullscreenBounds.set(this.mLastFreeformBoundsBeforeDragMoving);
            }
            this.mRemoveByDrag = false;
        }
        saveFreeformBoundsIfNeeded();
        if (CoreRune.MT_SIZE_COMPAT_POLICY) {
            SizeCompatPolicyManager.LazyHolder.sManager.setCompatPolicy(this, null);
        }
        super.removeImmediately();
        this.mRemoving = false;
    }

    public final void reparent(int i, Task task, String str, boolean z) {
        int i2 = this.mTaskId;
        int i3 = getRootTask().mTaskId;
        int displayId = getDisplayId();
        EventLog.writeEvent(31003, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(displayId), ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("reParentTask:", str));
        reparent(task, i);
        task.positionChildAt(i, this, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01af A[Catch: all -> 0x0168, TRY_LEAVE, TryCatch #0 {all -> 0x0168, blocks: (B:19:0x0152, B:22:0x015a, B:24:0x0160, B:28:0x0171, B:30:0x0177, B:38:0x018a, B:41:0x0194, B:43:0x01a0, B:45:0x01a8, B:47:0x01af, B:57:0x016b), top: B:18:0x0152 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reparent(com.android.server.wm.Task r18, boolean r19, int r20, boolean r21, boolean r22, java.lang.String r23) {
        /*
            Method dump skipped, instructions count: 506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.reparent(com.android.server.wm.Task, boolean, int, boolean, boolean, java.lang.String):void");
    }

    public final void reparent(TaskDisplayArea taskDisplayArea, boolean z) {
        if (taskDisplayArea == null) {
            throw new IllegalArgumentException("Task can't reparent to null " + this);
        }
        if (getParent() == taskDisplayArea) {
            throw new IllegalArgumentException("Task=" + this + " already child of " + taskDisplayArea);
        }
        if (this.mTaskSupervisor.canPlaceEntityOnDisplay(taskDisplayArea.mDisplayContent.mDisplayId, -1, -1, this, null)) {
            reparent(taskDisplayArea, z ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            if (isLeafTask()) {
                taskDisplayArea.onLeafTaskMoved(this, z, !z);
                return;
            }
            return;
        }
        Slog.w("ActivityTaskManager", "Task=" + this + " can't reparent to " + taskDisplayArea);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void reparentSurfaceControl(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (isOrganized() && isAlwaysOnTop() && !inFreeformWindowingMode()) {
            return;
        }
        super.reparentSurfaceControl(transaction, surfaceControl);
    }

    public final void resetSurfaceControlTransforms() {
        getSyncTransaction().setMatrix(this.mSurfaceControl, Matrix.IDENTITY_MATRIX, new float[9]).setWindowCrop(this.mSurfaceControl, null).setShadowRadius(this.mSurfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE).setCornerRadius(this.mSurfaceControl, FullScreenMagnificationGestureHandler.MAX_SCALE);
    }

    public final ActivityRecord resetTaskIfNeeded(ActivityRecord activityRecord, ActivityRecord activityRecord2) {
        ActivityRecord topNonFinishingActivity;
        boolean z = (activityRecord2.info.flags & 4) != 0;
        Task task = activityRecord.task;
        this.mReuseTask = true;
        try {
            ActivityOptions process = sResetTargetTaskHelper.process(task, z);
            this.mReuseTask = false;
            if (this.mChildren.contains(task) && (topNonFinishingActivity = task.getTopNonFinishingActivity(true, true)) != null) {
                activityRecord = topNonFinishingActivity;
            }
            if (process != null) {
                activityRecord.updateOptionsLocked(process);
            }
            return activityRecord;
        } catch (Throwable th) {
            this.mReuseTask = false;
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0068, code lost:
    
        if (r4 != false) goto L41;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void resize(int r9, android.graphics.Rect r10) {
        /*
            r8 = this;
            java.lang.String r0 = "resizeTask_"
            com.android.server.wm.ActivityTaskManagerService r1 = r8.mAtmService
            r1.deferWindowLayout()
            r1 = 2
            r9 = r9 & r1
            r2 = 0
            r3 = 1
            if (r9 == 0) goto L10
            r9 = r3
            goto L11
        L10:
            r9 = r2
        L11:
            com.android.server.wm.WindowContainer r4 = r8.getParent()     // Catch: java.lang.Throwable -> L27
            if (r4 != 0) goto L30
            r8.setBounds(r10)     // Catch: java.lang.Throwable -> L27
            boolean r9 = r8.inFreeformWindowingMode()     // Catch: java.lang.Throwable -> L27
            if (r9 != 0) goto L2a
            com.android.server.wm.ActivityTaskSupervisor r9 = r8.mTaskSupervisor     // Catch: java.lang.Throwable -> L27
            r10 = 0
            r9.restoreRecentTaskLocked(r10, r8, r2)     // Catch: java.lang.Throwable -> L27
            goto L2a
        L27:
            r9 = move-exception
            goto Lbc
        L2a:
            com.android.server.wm.ActivityTaskManagerService r8 = r8.mAtmService
            r8.continueWindowLayout()
            return
        L30:
            if (r10 == 0) goto L73
            boolean r4 = r8.inFreeformWindowingMode()     // Catch: java.lang.Throwable -> L27
            if (r4 != 0) goto L39
            goto L73
        L39:
            int r4 = r10.width()     // Catch: java.lang.Throwable -> L27
            int r5 = r10.height()     // Catch: java.lang.Throwable -> L27
            if (r4 <= r5) goto L45
            r4 = r3
            goto L46
        L45:
            r4 = r2
        L46:
            android.graphics.Rect r5 = r8.getRequestedOverrideBounds()     // Catch: java.lang.Throwable -> L27
            int r6 = r8.mResizeMode     // Catch: java.lang.Throwable -> L27
            r7 = 7
            if (r6 != r7) goto L65
            boolean r6 = r5.isEmpty()     // Catch: java.lang.Throwable -> L27
            if (r6 != 0) goto L73
            int r6 = r5.width()     // Catch: java.lang.Throwable -> L27
            int r5 = r5.height()     // Catch: java.lang.Throwable -> L27
            if (r6 <= r5) goto L61
            r5 = r3
            goto L62
        L61:
            r5 = r2
        L62:
            if (r4 != r5) goto L70
            goto L73
        L65:
            r5 = 6
            if (r6 != r5) goto L6a
            if (r4 != 0) goto L70
        L6a:
            r5 = 5
            if (r6 != r5) goto L73
            if (r4 == 0) goto L70
            goto L73
        L70:
            r8.adjustAspectRatioIfNeeded(r10)     // Catch: java.lang.Throwable -> L27
        L73:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L27
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L27
            int r0 = r8.mTaskId     // Catch: java.lang.Throwable -> L27
            r4.append(r0)     // Catch: java.lang.Throwable -> L27
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L27
            r4 = 32
            android.os.Trace.traceBegin(r4, r0)     // Catch: java.lang.Throwable -> L27
            int r10 = r8.setBounds(r10)     // Catch: java.lang.Throwable -> L27
            if (r9 == 0) goto L95
            r9 = r10 & 2
            if (r9 == r1) goto L95
            r8.onResize()     // Catch: java.lang.Throwable -> L27
            r10 = r10 | 2
        L95:
            if (r10 == 0) goto Lad
            com.android.server.wm.ActivityRecord r9 = r8.topRunningActivityLocked()     // Catch: java.lang.Throwable -> L27
            if (r9 == 0) goto Lad
            boolean r10 = r9.ensureActivityConfiguration(r2)     // Catch: java.lang.Throwable -> L27
            com.android.server.wm.RootWindowContainer r0 = r8.mRootWindowContainer     // Catch: java.lang.Throwable -> L27
            r0.ensureActivitiesVisible(r3, r9)     // Catch: java.lang.Throwable -> L27
            if (r10 != 0) goto Lad
            com.android.server.wm.RootWindowContainer r9 = r8.mRootWindowContainer     // Catch: java.lang.Throwable -> L27
            r9.resumeFocusedTasksTopActivities()     // Catch: java.lang.Throwable -> L27
        Lad:
            com.android.server.wm.DisplayContent r9 = r8.getDisplayContent()     // Catch: java.lang.Throwable -> L27
            r8.saveLaunchingStateIfNeeded(r9)     // Catch: java.lang.Throwable -> L27
            r8.saveFreeformBoundsIfNeeded()     // Catch: java.lang.Throwable -> L27
            android.os.Trace.traceEnd(r4)     // Catch: java.lang.Throwable -> L27
            goto L2a
        Lbc:
            com.android.server.wm.ActivityTaskManagerService r8 = r8.mAtmService
            r8.continueWindowLayout()
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.resize(int, android.graphics.Rect):void");
    }

    public final void resumeNextFocusAfterReparent() {
        adjustFocusToNextFocusableTask("reparent", true, true);
        this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        this.mRootWindowContainer.ensureActivitiesVisible();
    }

    public final boolean resumeTopActivityInnerLocked(final ActivityRecord activityRecord, final ActivityOptions activityOptions, final boolean z) {
        Handler handler;
        Task adjustFocusToNextFocusableTask;
        if (!this.mAtmService.mAmInternal.isBooting() && !this.mAtmService.mAmInternal.isBooted()) {
            return false;
        }
        ActivityRecord activityRecord2 = topRunningActivity(!isFreeformStashed());
        if (activityRecord2 == null) {
            if (!isActivityTypeHome() && (adjustFocusToNextFocusableTask = adjustFocusToNextFocusableTask("noMoreActivities", false, true)) != null) {
                return this.mRootWindowContainer.resumeFocusedTasksTopActivities(adjustFocusToNextFocusableTask, activityRecord, null, false);
            }
            ActivityOptions.abort(activityOptions);
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_STATES_enabled[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_STATES, 4037728373502324767L, 0, null, "noMoreActivities");
            }
            return this.mRootWindowContainer.resumeHomeActivity(activityRecord, "noMoreActivities", getDisplayArea());
        }
        final TaskFragment taskFragment = activityRecord2.getTaskFragment();
        final boolean[] zArr = {taskFragment.resumeTopActivity(activityRecord, activityOptions, z)};
        forAllLeafTaskFragments(new Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda50
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskFragment taskFragment2 = TaskFragment.this;
                boolean[] zArr2 = zArr;
                ActivityRecord activityRecord3 = activityRecord;
                ActivityOptions activityOptions2 = activityOptions;
                boolean z2 = z;
                TaskFragment taskFragment3 = (TaskFragment) obj;
                if (taskFragment2 != taskFragment3 && taskFragment3.canBeResumed(null)) {
                    zArr2[0] = taskFragment3.resumeTopActivity(activityRecord3, activityOptions2, z2) | zArr2[0];
                }
            }
        }, true);
        if (CoreRune.MNO_TMO_DEVICE_REPORTING && DeviceReportingSecurityChecker.getStatus() && (handler = AppStateBroadcaster.mObjHandler) != null) {
            final String str = activityRecord2.packageName;
            handler.post(new Runnable() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    String str2 = str;
                    boolean z2 = AppStateBroadcaster.DEBUG;
                    if (z2) {
                        AppStateBroadcaster.logOriginFunction("sendApplicationFocusGain(" + str2 + ")");
                    }
                    if (!AppStateBroadcaster.mIsBroadcastEnabled || TextUtils.isEmpty(str2)) {
                        return;
                    }
                    String str3 = AppStateBroadcaster.mLastFocusAppName;
                    if (str3 == null || !str2.equals(str3)) {
                        if (!TextUtils.isEmpty(AppStateBroadcaster.mLastFocusAppName)) {
                            AppStateBroadcaster.broadcastAppState(AppStateBroadcaster.mLastFocusAppName, "FOCUS_LOSS");
                            if (z2) {
                                BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("sendApplicationFocusGain sent APP_STATE_FOCUS_LOSS for "), AppStateBroadcaster.mLastFocusAppName, "AppStateBroadcaster");
                            }
                        }
                        AppStateBroadcaster.broadcastAppState(str2, "FOCUS_GAIN");
                        if (z2) {
                            BootReceiver$$ExternalSyntheticOutline0.m(DumpUtils$$ExternalSyntheticOutline0.m("sendApplicationFocusGain sent APP_STATE_FOCUS_GAIN for ", str2, ", old focus package was "), AppStateBroadcaster.mLastFocusAppName, "AppStateBroadcaster");
                        }
                        AppStateBroadcaster.mLastFocusAppName = str2;
                    }
                }
            });
        }
        return zArr[0];
    }

    public final boolean resumeTopActivityUncheckedLocked(ActivityRecord activityRecord, ActivityOptions activityOptions, boolean z) {
        boolean z2;
        if (this.mInResumeTopActivity) {
            return false;
        }
        try {
            this.mInResumeTopActivity = true;
            if (isLeafTask()) {
                z2 = isFocusableAndVisible() ? resumeTopActivityInnerLocked(activityRecord, activityOptions, z) : (isFreeformStashed() && canBeResumed(null)) ? resumeTopActivityInnerLocked(activityRecord, activityOptions, z) : false;
            } else {
                int size = this.mChildren.size() - 1;
                boolean z3 = false;
                while (size >= 0) {
                    int i = size - 1;
                    Task task = (Task) getChildAt(size);
                    if (task.isTopActivityFocusable()) {
                        if (task.getVisibility(null) == 0) {
                            z3 |= task.resumeTopActivityUncheckedLocked(activityRecord, activityOptions, z);
                            if (i >= this.mChildren.size()) {
                                size = this.mChildren.size() - 1;
                            }
                        } else if (task.topRunningActivity(false) != null) {
                            break;
                        }
                    }
                    size = i;
                }
                z2 = z3;
            }
            ActivityRecord activityRecord2 = topRunningActivity(true);
            if (activityRecord2 == null || !activityRecord2.canTurnScreenOn()) {
                checkReadyForSleep();
            }
            if (CoreRune.FW_SA_LOGGING_FOR_HALF_OPEN_MODE) {
                this.mWmService.mExt.mService.mExt.getClass();
                throw null;
            }
            this.mInResumeTopActivity = false;
            return z2;
        } catch (Throwable th) {
            this.mInResumeTopActivity = false;
            throw th;
        }
    }

    public final boolean returnsToHomeRootTask() {
        if (inMultiWindowMode()) {
            if (this.mLaunchTaskOnHome) {
                this.mLaunchTaskOnHome = false;
            }
            return false;
        }
        if (hasChild()) {
            Intent intent = this.intent;
            if (intent == null) {
                Task bottomMostTask = getBottomMostTask();
                return bottomMostTask != this && bottomMostTask.returnsToHomeRootTask();
            }
            if ((intent.getFlags() & 268451840) != 268451840) {
                return false;
            }
            Task task = getDisplayArea() != null ? getDisplayArea().mRootHomeTask : null;
            return task == null || !this.mAtmService.mLockTaskController.isLockTaskModeViolation(task, false);
        }
        return false;
    }

    public final Task reuseOrCreateTask(ActivityInfo activityInfo, Intent intent, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor, boolean z, ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityOptions activityOptions) {
        int nextTaskIdForUser;
        Task build;
        ActivityInfo.WindowLayout windowLayout;
        if ((this.mCreatedByOrganizer || !isLeafTask()) ? false : DisplayContent.alwaysCreateRootTask(getWindowingMode(), getActivityType())) {
            this.voiceSession = iVoiceInteractionSession;
            this.voiceInteractor = iVoiceInteractor;
            setIntent(activityRecord, intent, activityInfo);
            if (activityInfo == null || (windowLayout = activityInfo.windowLayout) == null) {
                this.mMinWidth = -1;
                this.mMinHeight = -1;
            } else {
                this.mMinWidth = windowLayout.minWidth;
                this.mMinHeight = windowLayout.minHeight;
            }
            TaskChangeNotificationController taskChangeNotificationController = this.mAtmService.mTaskChangeNotificationController;
            Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(8, this.mTaskId, 0, this.realActivity);
            taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskCreated, obtainMessage);
            obtainMessage.sendToTarget();
            build = this;
        } else {
            if (activityRecord != null) {
                nextTaskIdForUser = this.mTaskSupervisor.getNextTaskIdForUser(activityRecord.mUserId);
            } else {
                ActivityTaskSupervisor activityTaskSupervisor = this.mTaskSupervisor;
                nextTaskIdForUser = activityTaskSupervisor.getNextTaskIdForUser(activityTaskSupervisor.mRootWindowContainer.mCurrentUser);
            }
            getActivityType();
            Builder builder = new Builder(this.mAtmService);
            builder.mTaskId = nextTaskIdForUser;
            builder.mActivityInfo = activityInfo;
            builder.mActivityOptions = activityOptions;
            builder.mIntent = intent;
            builder.mVoiceSession = iVoiceInteractionSession;
            builder.mVoiceInteractor = iVoiceInteractor;
            builder.mOnTop = z;
            builder.mParent = this;
            build = builder.build();
        }
        updateMinMaxSizeIfNeeded();
        if (activityInfo != null) {
            build.mDexMetaDataInfo = DexController.parseDexMetadata(activityInfo);
        }
        if (isDexMode()) {
            build.updateDexCompatMode(activityInfo, activityOptions, true);
        }
        int displayId = getDisplayId();
        boolean isKeyguardOrAodShowing = this.mAtmService.mTaskSupervisor.mKeyguardController.isKeyguardOrAodShowing(displayId != -1 ? displayId : 0);
        if (!this.mTaskSupervisor.mLaunchParamsController.layoutTask(build, activityInfo.windowLayout, activityRecord, activityRecord2, activityOptions, -1) && !getRequestedOverrideBounds().isEmpty() && build.isResizeable(true) && !isKeyguardOrAodShowing) {
            build.setBounds(getRequestedOverrideBounds());
        }
        return build;
    }

    public final void saveFreeformBoundsIfNeeded() {
        boolean z;
        int i;
        DisplayContent displayContent = getDisplayContent();
        if (displayContent != null && isLeafTask() && this.mHasBeenVisible && displayContent.isDefaultDisplay && !isDexMode() && inFreeformWindowingMode() && !isFreeformStashed()) {
            Slog.d("ActivityTaskManager", "saveFreeformBoundsIfNeeded : task #" + this.mTaskId + " from : " + Debug.getCallers(5));
            LaunchParamsPersister launchParamsPersister = this.mTaskSupervisor.mLaunchParamsPersister;
            launchParamsPersister.getClass();
            ComponentName componentName = this.realActivity;
            int i2 = this.mUserId;
            ArrayMap arrayMap = (ArrayMap) launchParamsPersister.mLaunchParamsMap.get(i2);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                launchParamsPersister.mLaunchParamsMap.put(i2, arrayMap);
            }
            LaunchParamsPersister.PersistableLaunchParams persistableLaunchParams = (LaunchParamsPersister.PersistableLaunchParams) arrayMap.computeIfAbsent(componentName, new LaunchParamsPersister$$ExternalSyntheticLambda1(launchParamsPersister, 1));
            boolean z2 = true;
            if (!CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY || persistableLaunchParams.mDisplayDeviceType == (i = displayContent.getConfiguration().semDisplayDeviceType)) {
                z = false;
            } else {
                persistableLaunchParams.mDisplayDeviceType = i;
                DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("saveDisplayDeviceType "), persistableLaunchParams.mDisplayDeviceType, "LaunchParamsPersister");
                z = true;
            }
            FreeformPersistBoundsParams freeformPersistBoundsParams = persistableLaunchParams.mFreeformPersistBoundsParams;
            Rect rect = freeformPersistBoundsParams.mFreeformBounds;
            Rect rect2 = this.mLastNonFullscreenBounds;
            if (rect2 != null && !rect.equals(rect2)) {
                rect.set(this.mLastNonFullscreenBounds);
                Slog.d("LaunchParamsPersister", "saveFreeformBounds " + rect);
                z = true;
            }
            Rect rect3 = freeformPersistBoundsParams.mDisplayBounds;
            Rect rect4 = new Rect();
            DisplayInfo displayInfo = displayContent.mDisplayInfo;
            rect4.set(0, 0, displayInfo.logicalWidth, displayInfo.logicalHeight);
            if (!rect3.equals(rect4)) {
                rect3.set(rect4);
                Slog.d("LaunchParamsPersister", "saveDisplayBounds " + rect3);
                z = true;
            }
            int i3 = freeformPersistBoundsParams.mRotation;
            int i4 = this.mRotation;
            if (i3 != i4) {
                freeformPersistBoundsParams.mRotation = i4;
                Slog.d("LaunchParamsPersister", "saveRotation " + Surface.rotationToString(freeformPersistBoundsParams.mRotation));
            } else {
                z2 = z;
            }
            if (z2) {
                if (persistableLaunchParams.mDisplayUniqueId == null) {
                    persistableLaunchParams.mDisplayUniqueId = "";
                }
                persistableLaunchParams.mTimestamp = System.currentTimeMillis();
            }
            launchParamsPersister.addComponentNameToLaunchParamAffinityMapIfNotNull(componentName, persistableLaunchParams.mWindowLayoutAffinity);
            if (z2) {
                launchParamsPersister.mPersisterQueue.updateLastOrAddItem(launchParamsPersister.new LaunchParamsWriteQueueItem(i2, componentName, persistableLaunchParams));
            }
        }
    }

    public final void saveLaunchingStateIfNeeded(DisplayContent displayContent) {
        if (isLeafTask()) {
            if (this.mHasBeenVisible || ((inFreeformWindowingMode() && isAttached() && shouldBeVisible(null)) || (isDexMode() && getDisplayId() == 2 && isActivityTypeHome()))) {
                int windowingMode = getWindowingMode();
                if ((windowingMode == 1 || windowingMode == 5) && getTaskDisplayArea() != null && getTaskDisplayArea().getWindowingMode() == 5 && !this.mSkipSavingLaunchingState) {
                    this.mTaskSupervisor.mLaunchParamsPersister.saveTask(this, displayContent);
                }
            }
        }
    }

    public final void sendFreeformLogging() {
        final int[] iArr = {1};
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mNeedToSendFreeformLogging = false;
                if (this.realActivity != null && inFreeformWindowingMode()) {
                    String packageName = this.realActivity.getPackageName();
                    if (!isDesktopModeEnabled()) {
                        this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea().forAllLeafTasks(new Task$$ExternalSyntheticLambda10(1, this, iArr), true);
                    } else if (getTaskDisplayArea() != null) {
                        final ArrayList arrayList = new ArrayList();
                        getTaskDisplayArea().forAllLeafTasks(new Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda26
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                Task task = Task.this;
                                ArrayList arrayList2 = arrayList;
                                int[] iArr2 = iArr;
                                Task task2 = (Task) obj;
                                task.getClass();
                                if (task2 == task || task2.realActivity == null || !task2.inFreeformWindowingMode() || task2.isMinimized() || !task2.isVisible()) {
                                    return;
                                }
                                arrayList2.add(task2.realActivity.getPackageName());
                                iArr2[0] = iArr2[0] + 1;
                            }
                        }, true);
                        DexController dexController = this.mAtmService.mDexController;
                        int i = dexController.mDisplayFreeformMaxCount;
                        int i2 = iArr[0];
                        if (i < i2) {
                            dexController.mDisplayFreeformMaxCount = i2;
                            CoreSaLogger.logForDexMW("2504", arrayList.toString(), iArr[0]);
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    CoreSaLogger.logForAdvanced("2000", packageName, iArr[0]);
                    return;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final void sendTaskAppeared() {
        TaskOrganizerController.TaskOrganizerState taskOrganizerState;
        ITaskOrganizer iTaskOrganizer = this.mTaskOrganizer;
        if (iTaskOrganizer == null || (taskOrganizerState = (TaskOrganizerController.TaskOrganizerState) this.mAtmService.mTaskOrganizerController.mTaskOrganizerStates.get(iTaskOrganizer.asBinder())) == null || this.mTaskAppearedSent) {
            return;
        }
        if (!taskOrganizerState.mOrganizedTasks.contains(this)) {
            taskOrganizerState.mOrganizedTasks.add(this);
        }
        if (this.mTaskOrganizer == null || this.mDeferTaskAppear) {
            return;
        }
        if (!this.mCreatedByOrganizer && (this.mSurfaceControl == null || !this.mHasBeenVisible)) {
            return;
        }
        this.mTaskAppearedSent = true;
        TaskOrganizerController.TaskOrganizerPendingEventsQueue taskOrganizerPendingEventsQueue = taskOrganizerState.mPendingEventsQueue;
        if (TaskOrganizerController.TaskOrganizerPendingEventsQueue.m1070$$Nest$mgetPendingTaskEvent(taskOrganizerPendingEventsQueue, this, 0) == null) {
            taskOrganizerPendingEventsQueue.mPendingTaskEvents.add(new TaskOrganizerController.PendingTaskEvent(0, this));
        }
    }

    public final void sendTaskFragmentParentInfoChangedIfNeeded() {
        TaskFragment taskFragment;
        TaskFragmentOrganizerController.PendingTaskFragmentEvent pendingTaskFragmentEvent;
        if (isLeafTask() && (taskFragment = getTaskFragment(new Task$$ExternalSyntheticLambda0(9))) != null) {
            Task asTask = taskFragment.getParent().asTask();
            ITaskFragmentOrganizer iTaskFragmentOrganizer = taskFragment.mTaskFragmentOrganizer;
            if (iTaskFragmentOrganizer == null || asTask == null) {
                return;
            }
            TaskFragmentOrganizerController taskFragmentOrganizerController = taskFragment.mTaskFragmentOrganizerController;
            taskFragmentOrganizerController.validateAndGetState(iTaskFragmentOrganizer);
            List list = (List) taskFragmentOrganizerController.mPendingTaskFragmentEvents.get(iTaskFragmentOrganizer.asBinder());
            int size = list.size() - 1;
            while (true) {
                if (size < 0) {
                    pendingTaskFragmentEvent = null;
                    break;
                }
                pendingTaskFragmentEvent = (TaskFragmentOrganizerController.PendingTaskFragmentEvent) list.get(size);
                if (asTask == pendingTaskFragmentEvent.mTask && pendingTaskFragmentEvent.mEventType == 3) {
                    break;
                } else {
                    size--;
                }
            }
            if (pendingTaskFragmentEvent == null) {
                taskFragmentOrganizerController.addPendingEvent(new TaskFragmentOrganizerController.PendingTaskFragmentEvent(3, iTaskFragmentOrganizer, null, null, null, null, null, null, asTask, 0));
            }
            taskFragmentOrganizerController.mAtmService.mWindowManager.mWindowPlacerLocked.requestTraversal();
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final void setAlwaysOnTop(boolean z) {
        if (super.isAlwaysOnTop() == z) {
            return;
        }
        super.setAlwaysOnTop(z);
        if (isForceHidden()) {
            return;
        }
        getDisplayArea().positionChildAt(Integer.MAX_VALUE, this, false);
    }

    public final void setBoostTaskLayerForFreeform(boolean z, boolean z2) {
        if (this.mBoostRootTaskLayerForFreeform != z) {
            this.mBoostRootTaskLayerForFreeform = z;
            if (z2 && getTaskDisplayArea() != null) {
                getTaskDisplayArea().assignChildLayers();
            }
            Slog.d("ActivityTaskManager", "setBoostTaskLayerForFreeform: " + this + ", boost=" + z);
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final int setBounds(Rect rect) {
        if (!isRootTask()) {
            int bounds = super.setBounds(rect);
            updateSurfacePositionNonOrganized();
            return bounds;
        }
        if (ConfigurationContainer.equivalentBounds(getRequestedOverrideBounds(), rect)) {
            return 0;
        }
        if (!inMultiWindowMode()) {
            rect = null;
        }
        return setBoundsUnchecked(rect);
    }

    public final int setBoundsUnchecked(Rect rect) {
        DisplayContent displayContent = getRootTask() != null ? getRootTask().getDisplayContent() : null;
        int i = displayContent != null ? displayContent.mDisplayInfo.rotation : 0;
        int bounds = super.setBounds(rect);
        this.mRotation = i;
        updateSurfaceSize(getSyncTransaction());
        updateSurfacePositionNonOrganized();
        scheduleAnimation();
        return bounds;
    }

    public final void setDeferTaskAppear(boolean z) {
        boolean z2 = this.mDeferTaskAppear;
        this.mDeferTaskAppear = z;
        if (!z2 || z) {
            return;
        }
        sendTaskAppeared();
    }

    public final void setDragResizing(boolean z) {
        if (this.mDragResizing != z) {
            if (z && !getRootTask().getWindowConfiguration().canResizeTask()) {
                VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("Drag resize isn't allowed for root task id="), getRootTask().mTaskId, "ActivityTaskManager");
            } else {
                this.mDragResizing = z;
                resetDragResizingChangeReported();
            }
        }
    }

    @Override // com.android.server.wm.TaskFragment
    public final boolean setForceHidden(int i, boolean z) {
        boolean isForceHidden = isForceHidden();
        isVisible();
        if (!super.setForceHidden(i, z)) {
            return false;
        }
        boolean isForceHidden2 = isForceHidden();
        if (isForceHidden == isForceHidden2) {
            return true;
        }
        if (isForceHidden2) {
            moveToBack("setForceHidden", null);
            return true;
        }
        if (!isAlwaysOnTop()) {
            return true;
        }
        moveToFront("setForceHidden", null);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.wm.Task] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.wm.WindowContainer] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.wm.WindowContainer] */
    public final void setHasBeenVisible() {
        Task asTask;
        this.mHasBeenVisible = true;
        if (!this.mDeferTaskAppear) {
            sendTaskAppeared();
        }
        while (true) {
            this = this.getParent();
            if (this == 0 || (asTask = this.asTask()) == null) {
                return;
            } else {
                asTask.setHasBeenVisible();
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void setInitialSurfaceControlProperties(SurfaceControl.Builder builder) {
        builder.setEffectLayer().setMetadata(3, this.mTaskId);
        super.setInitialSurfaceControlProperties(builder);
    }

    public final void setIntent(Intent intent, ActivityInfo activityInfo) {
        if (isLeafTask()) {
            this.mNeverRelinquishIdentity = (activityInfo.flags & 4096) == 0;
            String str = activityInfo.taskAffinity;
            this.affinity = str;
            if (this.intent == null) {
                this.rootAffinity = str;
                this.mRequiredDisplayCategory = activityInfo.requiredDisplayCategory;
            }
            ApplicationInfo applicationInfo = activityInfo.applicationInfo;
            this.effectiveUid = applicationInfo.uid;
            this.mIsEffectivelySystemApp = applicationInfo.isSystemApp();
            String str2 = activityInfo.targetActivity;
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_TASKS_enabled;
            if (str2 == null) {
                if (intent != null && (intent.getSelector() != null || intent.getSourceBounds() != null)) {
                    Intent intent2 = new Intent(intent);
                    intent2.setSelector(null);
                    intent2.setSourceBounds(null);
                    intent = intent2;
                }
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_TASKS, -8609432747982701423L, 0, null, String.valueOf(this), String.valueOf(intent));
                }
                this.intent = intent;
                this.realActivity = intent != null ? intent.getComponent() : null;
                this.origActivity = null;
            } else {
                ComponentName componentName = new ComponentName(activityInfo.packageName, activityInfo.targetActivity);
                if (intent != null) {
                    Intent intent3 = new Intent(intent);
                    intent3.setSelector(null);
                    intent3.setSourceBounds(null);
                    if (zArr[1]) {
                        ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_TASKS, -9155008290180285590L, 0, null, String.valueOf(this), String.valueOf(intent3));
                    }
                    this.intent = intent3;
                    this.realActivity = componentName;
                    this.origActivity = intent.getComponent();
                } else {
                    this.intent = null;
                    this.realActivity = componentName;
                    this.origActivity = new ComponentName(activityInfo.packageName, activityInfo.name);
                }
            }
            ActivityInfo.WindowLayout windowLayout = activityInfo.windowLayout;
            this.mWindowLayoutAffinity = windowLayout == null ? null : windowLayout.windowLayoutAffinity;
            Intent intent4 = this.intent;
            int flags = intent4 == null ? 0 : intent4.getFlags();
            if ((2097152 & flags) != 0) {
                this.rootWasReset = true;
            }
            this.mUserId = UserHandle.getUserId(activityInfo.applicationInfo.uid);
            this.mUserSetupComplete = Settings.Secure.getIntForUser(this.mAtmService.mContext.getContentResolver(), "user_setup_complete", 0, this.mUserId) != 0;
            if ((activityInfo.flags & 8192) != 0) {
                this.autoRemoveRecents = true;
            } else if ((flags & 532480) != 524288) {
                this.autoRemoveRecents = false;
            } else if (activityInfo.documentLaunchMode != 0) {
                this.autoRemoveRecents = false;
            } else {
                this.autoRemoveRecents = true;
            }
            this.mAtmService.mMwSupportPolicyController.updateSupportPolicyLocked(activityInfo, this);
            int i = this.mResizeMode;
            int i2 = activityInfo.resizeMode;
            if (i != i2) {
                this.mResizeMode = i2;
                updateTaskDescription$1();
            }
            this.mSupportsPictureInPicture = activityInfo.supportsPictureInPicture();
            this.stringName = null;
            if (this.inRecents) {
                this.mTaskSupervisor.mRecentTasks.remove(this);
                this.mTaskSupervisor.mRecentTasks.add(this);
            }
            ActivityInfo.WindowLayout windowLayout2 = activityInfo.windowLayout;
            if (windowLayout2 != null) {
                Point point = this.mMinDimensions;
                point.x = windowLayout2.minWidth;
                point.y = windowLayout2.minHeight;
            }
            if (activityInfo.metaData != null) {
                float initialDisplayDensity = this.mAtmService.mWindowManager.getInitialDisplayDensity(0) * 0.00625f;
                int i3 = activityInfo.metaData.getInt("com.samsung.android.sdk.multiwindow.maxWidth", -1);
                int i4 = activityInfo.metaData.getInt("com.samsung.android.sdk.multiwindow.maxHeight", -1);
                if (initialDisplayDensity > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    if (i3 != -1) {
                        this.mMaxDimensions.x = (int) ((i3 * initialDisplayDensity) + 0.5f);
                    }
                    if (i4 != -1) {
                        this.mMaxDimensions.y = (int) ((i4 * initialDisplayDensity) + 0.5f);
                    }
                }
            }
            PackagesChange.updateAllValueToTask(this);
            MultiTaskingAppCompatController multiTaskingAppCompatController = this.mAtmService.mMultiTaskingAppCompatController;
            multiTaskingAppCompatController.getClass();
            ComponentName componentName2 = this.realActivity;
            multiTaskingAppCompatController.onOverridesChanged(this.mUserId, this, componentName2 != null ? componentName2.getPackageName() : null, false);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
    
        if (r1 != r0.applicationInfo.uid) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setIntent(com.android.server.wm.ActivityRecord r4, android.content.Intent r5, android.content.pm.ActivityInfo r6) {
        /*
            r3 = this;
            android.content.Intent r0 = r3.intent
            if (r0 != 0) goto L5
            goto L1f
        L5:
            boolean r0 = r3.mNeverRelinquishIdentity
            if (r0 != 0) goto L38
            if (r6 == 0) goto Ld
            r0 = r6
            goto Lf
        Ld:
            android.content.pm.ActivityInfo r0 = r4.info
        Lf:
            int r1 = r3.effectiveUid
            r2 = 1000(0x3e8, float:1.401E-42)
            if (r1 == r2) goto L1f
            boolean r2 = r3.mIsEffectivelySystemApp
            if (r2 != 0) goto L1f
            android.content.pm.ApplicationInfo r0 = r0.applicationInfo
            int r0 = r0.uid
            if (r1 != r0) goto L38
        L1f:
            int r0 = r4.launchedFromUid
            r3.mCallingUid = r0
            java.lang.String r0 = r4.launchedFromPackage
            r3.mCallingPackage = r0
            java.lang.String r0 = r4.launchedFromFeatureId
            r3.mCallingFeatureId = r0
            if (r5 == 0) goto L2e
            goto L30
        L2e:
            android.content.Intent r5 = r4.intent
        L30:
            if (r6 == 0) goto L33
            goto L35
        L33:
            android.content.pm.ActivityInfo r6 = r4.info
        L35:
            r3.setIntent(r5, r6)
        L38:
            r3.setLockTaskAuth(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.setIntent(com.android.server.wm.ActivityRecord, android.content.Intent, android.content.pm.ActivityInfo):void");
    }

    public final void setLastNonFullscreenBounds(Rect rect) {
        if (this.mLastNonFullscreenBounds == null) {
            this.mLastNonFullscreenBounds = new Rect(rect);
            return;
        }
        if (this.mLastFreeformBoundsBeforeDragMoving == null) {
            this.mLastFreeformBoundsBeforeDragMoving = new Rect();
        }
        this.mLastFreeformBoundsBeforeDragMoving.set(this.mLastNonFullscreenBounds);
        this.mLastNonFullscreenBounds.set(rect);
    }

    public final void setLockTaskAuth(ActivityRecord activityRecord) {
        this.mLockTaskAuth = this.mAtmService.mLockTaskController.getLockTaskAuth(this, activityRecord);
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_LOCKTASK_enabled[0]) {
            ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_LOCKTASK, 38991867929900764L, 0, null, String.valueOf(this), String.valueOf(lockTaskAuthToString()));
        }
    }

    public final void setMainWindowSizeChangeTransaction(SurfaceControl.Transaction transaction) {
        ActivityRecord topNonFinishingActivity = getTopNonFinishingActivity(true, true);
        Task task = topNonFinishingActivity != null ? topNonFinishingActivity.task : null;
        if (task == null) {
            return;
        }
        if (task != this) {
            task.setMainWindowSizeChangeTransaction(transaction);
            return;
        }
        WindowState topVisibleAppMainWindow = getTopVisibleAppMainWindow(false);
        if (topVisibleAppMainWindow == null) {
            transaction.apply();
        } else {
            topVisibleAppMainWindow.mIsSurfacePositionPaused = true;
            topVisibleAppMainWindow.applyWithNextDraw(1, new Task$$ExternalSyntheticLambda10(3, topVisibleAppMainWindow, transaction));
        }
    }

    public final void setMainWindowSizeChangeTransaction$1(SurfaceControl.Transaction transaction) {
        Slog.d("ActivityTaskManager", "setMainWindowSizeChangeTransaction: t=" + transaction + ", Callers=" + Debug.getCallers(3));
        forAllWindows((Consumer) new Task$$ExternalSyntheticLambda6(3), true);
        setMainWindowSizeChangeTransaction(transaction);
        forAllWindows((Consumer) new Task$$ExternalSyntheticLambda6(4), true);
    }

    public final void setMinimized(int i, int i2) {
        boolean z;
        if (this.mIsMinimized) {
            return;
        }
        this.mIsMinimized = true;
        if (i == -1 || i2 == -1) {
            Point freeformContainerPosition = getFreeformContainerPosition();
            int i3 = freeformContainerPosition.x;
            z = false;
            i2 = freeformContainerPosition.y;
            i = i3;
        } else {
            z = true;
        }
        this.mAtmService.mFreeformController.notifyFreeformMinimizeStateChanged(i, i2, this, z);
        if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
            if (!shouldBeVisible(null)) {
                WindowManagerService windowManagerService = this.mWmService;
                if (!windowManagerService.mDisplayFrozen && windowManagerService.getTransitionAnimationScaleLocked() > FullScreenMagnificationGestureHandler.MAX_SCALE && !this.mTransitionController.inFinishingTransition(this)) {
                    updateMinimizeChangeInfo(1, i, i2);
                    DisplayContent displayContent = getDisplayContent();
                    if (CoreRune.MD_DEX_SHELL_TRANSITION && displayContent != null && displayContent.mDisplayId == 2 && displayContent.mSleeping && displayContent.mTransitionController.isCollecting()) {
                        displayContent.mTransitionController.mCollectingTransition.setReady(displayContent, true);
                    }
                }
            }
            this.mAtmService.mFreeformController.continueMinimizeStateChangedCallbacks();
            this.mAtmService.mFreeformController.notifyFreeformMinimizeAnimationEnd(this);
        }
        if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY && getDisplayId() == 0) {
            if (this.mLastMinimizedDisplayType == -1) {
                this.mLastMinimizedDisplayType = this.mAtmService.getGlobalConfiguration().semDisplayDeviceType;
            }
            this.mLastMinimizedRotation = getWindowConfiguration().getRotation();
        }
    }

    public final void setNextAffiliate(Task task) {
        this.mNextAffiliate = task;
        this.mNextAffiliateTaskId = task == null ? -1 : task.mTaskId;
    }

    public final void setPrevAffiliate(Task task) {
        this.mPrevAffiliate = task;
        this.mPrevAffiliateTaskId = task == null ? -1 : task.mTaskId;
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final void setSurfaceControl(SurfaceControl surfaceControl) {
        super.setSurfaceControl(surfaceControl);
        sendTaskAppeared();
        if (!inFreeformWindowingMode() || this.mSurfaceControl == null) {
            return;
        }
        ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get()).setTrustedOverlay(this.mSurfaceControl, true).apply();
    }

    public boolean setTaskOrganizer(ITaskOrganizer iTaskOrganizer) {
        return setTaskOrganizer(iTaskOrganizer, false);
    }

    public boolean setTaskOrganizer(ITaskOrganizer iTaskOrganizer, boolean z) {
        TaskDisplayArea.LaunchRootTaskDef launchRootTaskDef;
        TaskOrganizerController.TaskOrganizerState taskOrganizerState;
        ITaskOrganizer iTaskOrganizer2 = this.mTaskOrganizer;
        if (iTaskOrganizer2 == iTaskOrganizer) {
            return false;
        }
        this.mTaskOrganizer = iTaskOrganizer;
        if (iTaskOrganizer2 != null && (taskOrganizerState = (TaskOrganizerController.TaskOrganizerState) this.mAtmService.mTaskOrganizerController.mTaskOrganizerStates.get(iTaskOrganizer2.asBinder())) != null && taskOrganizerState.removeTask(this, this.mRemoveWithTaskOrganizer)) {
            TaskOrganizerController.onTaskVanishedInternal(taskOrganizerState, this);
        }
        if (this.mTaskOrganizer != null) {
            if (z) {
                return true;
            }
            sendTaskAppeared();
            return true;
        }
        TaskDisplayArea displayArea = getDisplayArea();
        if (displayArea != null && (launchRootTaskDef = displayArea.getLaunchRootTaskDef(this)) != null) {
            displayArea.mLaunchRootTasks.remove(launchRootTaskDef);
        }
        setForceHidden(2, false);
        if (!this.mCreatedByOrganizer) {
            return true;
        }
        removeImmediately("setTaskOrganizer");
        return true;
    }

    public final void setUnMinimizedWhenRestored() {
        if (isDexMode() && supportsMinimizeState()) {
            ((ArrayList) this.mAtmService.mDexController.mMinimizedToggleTasks).clear();
        }
        if (this.mIsMinimized) {
            this.mIsMinimized = false;
            if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                updateMinimizeChangeInfo(2, -1, -1);
            }
            this.mAtmService.mFreeformController.notifyFreeformMinimizeStateChanged(-1, -1, this, true);
            if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY && getDisplayId() == 0) {
                this.mLastMinimizedDisplayType = -1;
                this.mLastMinimizedRotation = -1;
            }
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final void setWindowingMode(int i) {
        if (isRootTask()) {
            setWindowingMode(i, false);
        } else {
            this.mMultiWindowRestoreWindowingMode = -1;
            super.setWindowingMode(i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x012e A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:133:0x0088, B:37:0x0093, B:39:0x009b, B:42:0x00a8, B:43:0x00b2, B:45:0x00b8, B:48:0x00bf, B:50:0x00c3, B:52:0x00c9, B:54:0x00d1, B:56:0x00e5, B:58:0x00eb, B:60:0x00f6, B:63:0x00fd, B:65:0x0103, B:67:0x0109, B:69:0x010f, B:72:0x0119, B:74:0x011f, B:76:0x0123, B:78:0x012e, B:79:0x0135, B:82:0x013b, B:83:0x0116, B:86:0x0144, B:87:0x0147, B:89:0x014d, B:90:0x0158, B:92:0x015e, B:94:0x0164, B:97:0x0170, B:100:0x0182, B:107:0x019a, B:109:0x01a2, B:111:0x01ab, B:113:0x01b1, B:115:0x01b5, B:117:0x01c6, B:118:0x01cd, B:131:0x0155), top: B:132:0x0088 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setWindowingMode(int r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 499
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.setWindowingMode(int, boolean):void");
    }

    @Override // com.android.server.wm.TaskFragment
    public final boolean shouldSleepActivities() {
        DisplayContent displayContent = this.mDisplayContent;
        if ((displayContent != null ? displayContent.isKeyguardGoingAway() : this.mRootWindowContainer.mDefaultDisplay.isKeyguardGoingAway()) && isFocusedRootTaskOnDisplay() && displayContent != null && displayContent.isDefaultDisplay) {
            return false;
        }
        return displayContent != null ? displayContent.mSleeping : this.mAtmService.mSleeping;
    }

    public final boolean shouldUpRecreateTaskLocked(ActivityRecord activityRecord, String str) {
        String computeTaskAffinity = ActivityRecord.computeTaskAffinity(activityRecord.getUid(), str);
        String str2 = activityRecord.task.affinity;
        if (str2 == null || !str2.equals(computeTaskAffinity)) {
            return true;
        }
        Task task = activityRecord.task;
        if (activityRecord.isRootOfTask() && task.getBaseIntent() != null && task.getBaseIntent().isDocument()) {
            TaskDisplayArea displayArea = getDisplayArea();
            if (displayArea != null) {
                final boolean[] zArr = new boolean[1];
                Task rootTask = displayArea.getRootTask(new Predicate() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda39
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        Task task2 = Task.this;
                        boolean[] zArr2 = zArr;
                        Task task3 = (Task) obj;
                        task2.getClass();
                        if (zArr2[0]) {
                            return true;
                        }
                        if (task3 != task2) {
                            return false;
                        }
                        zArr2[0] = true;
                        return false;
                    }
                });
                if (rootTask != null && rootTask.isActivityTypeStandard()) {
                    Task taskBelow = getTaskBelow(task);
                    if (taskBelow == null) {
                        Slog.w("ActivityTaskManager", "shouldUpRecreateTask: task not in history for " + activityRecord);
                        return false;
                    }
                    if (!task.affinity.equals(taskBelow.affinity)) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean showSurfaceOnCreation() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean showToCurrentUser() {
        ActivityRecord topNonFinishingActivity;
        if (this.mForceShowForAllUsers) {
            return true;
        }
        if (this.mChildren.isEmpty() || (topNonFinishingActivity = getTopNonFinishingActivity(true, true)) == null || !topNonFinishingActivity.mShowForAllUsers) {
            return this.mWmService.mUmInternal.isUserVisible(getTopMostTask().mUserId);
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x0102, code lost:
    
        if (topRunningNonDelayedActivityLocked(null) == r19) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x010e, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x010c, code lost:
    
        if (r23.getAnimationType() == 5) goto L72;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startActivityLocked(com.android.server.wm.ActivityRecord r19, com.android.server.wm.Task r20, boolean r21, boolean r22, android.app.ActivityOptions r23, com.android.server.wm.ActivityRecord r24) {
        /*
            Method dump skipped, instructions count: 387
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.startActivityLocked(com.android.server.wm.ActivityRecord, com.android.server.wm.Task, boolean, boolean, android.app.ActivityOptions, com.android.server.wm.ActivityRecord):void");
    }

    public final void startFreeformPinning() {
        DisplayContent displayContent;
        TaskDisplayArea displayArea = getDisplayArea();
        if (displayArea == null) {
            return;
        }
        FreeformTaskPinningController freeformTaskPinningController = displayArea.mFreeformTaskPinningController;
        freeformTaskPinningController.getClass();
        boolean z = CoreRune.MT_NEW_DEX_TASK_PINNING;
        TaskDisplayArea taskDisplayArea = freeformTaskPinningController.mTaskDisplayArea;
        if ((((z && taskDisplayArea.isNewDexMode()) || freeformTaskPinningController.mPinnedTask == null) ? false : true) || (z && getConfiguration().windowConfiguration.isAlwaysOnTop())) {
            android.util.secutil.Slog.d("FreeformTaskPinningController", "Failed to start freeform task pinning, already pinned");
            return;
        }
        if (!inFreeformWindowingMode()) {
            android.util.secutil.Slog.d("FreeformTaskPinningController", "Failed to start freeform task pinning, task isn't in freeform.");
            return;
        }
        if (!taskDisplayArea.isDesktopModeEnabled()) {
            android.util.secutil.Slog.d("FreeformTaskPinningController", "Failed to start freeform task pinning, it's not in dex mode.");
            return;
        }
        setFreeformTaskPinning(2);
        if (z && !taskDisplayArea.isNewDexMode()) {
            taskDisplayArea.setFreeformTaskPinning(3);
        }
        if (!isForceHidden()) {
            taskDisplayArea.positionChildAt(Integer.MAX_VALUE, this, false);
            if (z && (displayContent = taskDisplayArea.getDisplayContent()) != null) {
                displayContent.assignWindowLayers(false);
            }
        }
        taskDisplayArea.ensureActivitiesVisible(true, null);
        freeformTaskPinningController.mPinnedTask = this;
    }

    public final boolean supportsMinimizeState() {
        if (isActivityTypeHome()) {
            return false;
        }
        if (!isDexMode()) {
            return inFreeformWindowingMode();
        }
        if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && isNewDexMode() && isFullscreenRootForStageTask()) {
            return false;
        }
        return inFullscreenWindowingMode() || inFreeformWindowingMode();
    }

    @Override // com.android.server.wm.WindowContainer
    public final void switchUser(int i) {
        if (this.mCurrentUser == i) {
            return;
        }
        this.mCurrentUser = i;
        super.switchUser(i);
        if (isRootTask() || !showToCurrentUser()) {
            return;
        }
        getParent().positionChildAt(Integer.MAX_VALUE, this, false);
    }

    @Override // com.android.server.wm.TaskFragment
    public final String toFullString() {
        StringBuilder sb = new StringBuilder(192);
        sb.append(this);
        sb.setLength(sb.length() - 1);
        sb.append(" U=");
        sb.append(this.mUserId);
        Task rootTask = getRootTask();
        if (rootTask != this) {
            sb.append(" rootTaskId=");
            sb.append(rootTask.mTaskId);
        }
        sb.append(" visible=");
        sb.append(shouldBeVisible(null));
        sb.append(" visibleRequested=");
        sb.append(isVisibleRequested());
        sb.append(" mode=");
        sb.append(WindowConfiguration.windowingModeToString(getWindowingMode()));
        if (getWindowConfiguration().getStage() != 0) {
            sb.append(" stage=" + WindowConfiguration.stageConfigToString(getWindowConfiguration().getStage()));
        }
        sb.append(" translucent=");
        sb.append(isTranslucent(null));
        sb.append(" sz=");
        sb.append(getChildCount());
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.server.wm.TaskFragment
    public final String toString() {
        String str = this.stringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "Task{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(" #");
        m.append(this.mTaskId);
        m.append(" type=" + WindowConfiguration.activityTypeToString(getActivityType()));
        if (this.affinity != null) {
            m.append(" A=");
            m.append(this.affinity);
        } else {
            Intent intent = this.intent;
            if (intent == null || intent.getComponent() == null) {
                Intent intent2 = this.affinityIntent;
                if (intent2 != null && intent2.getComponent() != null) {
                    m.append(" aI=");
                    m.append(this.affinityIntent.getComponent().flattenToShortString());
                }
            } else {
                m.append(" I=");
                m.append(this.intent.getComponent().flattenToShortString());
            }
        }
        m.append('}');
        String sb = m.toString();
        this.stringName = sb;
        return sb;
    }

    public final ActivityRecord topRunningActivity(int i, IBinder iBinder) {
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new Task$$ExternalSyntheticLambda7(1), PooledLambda.__(ActivityRecord.class), Integer.valueOf(i), iBinder);
        ActivityRecord activity = getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    public final ActivityRecord topRunningActivityLocked() {
        if (getParent() == null) {
            return null;
        }
        return getActivity(new ActivityStarter$$ExternalSyntheticLambda0(1));
    }

    public final ActivityRecord topRunningNonDelayedActivityLocked(ActivityRecord activityRecord) {
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new Task$$ExternalSyntheticLambda29(1), PooledLambda.__(ActivityRecord.class), activityRecord);
        ActivityRecord activity = getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDexCompatMode(android.content.pm.ActivityInfo r9, android.app.ActivityOptions r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 279
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.Task.updateDexCompatMode(android.content.pm.ActivityInfo, android.app.ActivityOptions, boolean):void");
    }

    public void updateEffectiveIntent() {
        ActivityRecord rootActivity = getRootActivity(false, true);
        if (rootActivity != null) {
            setIntent(rootActivity, null, null);
            updateTaskDescription$1();
        }
    }

    public final void updateMinMaxSizeIfNeeded() {
        boolean z = !this.mMinDimensions.equals(-1, -1);
        boolean z2 = !this.mMaxDimensions.equals(-1, -1);
        if (!z && !z2) {
            this.mMinHeight = -1;
            this.mMinWidth = -1;
            this.mMaxHeight = -1;
            this.mMaxWidth = -1;
            return;
        }
        DisplayContent displayContent = getDisplayContent();
        float f = (displayContent == null || displayContent.isDefaultDisplay) ? this.mAtmService.getGlobalConfiguration().densityDpi : displayContent.getConfiguration().densityDpi;
        ComponentName componentName = this.realActivity;
        if (componentName != null && "android.server.wm.app".equals(componentName.getPackageName())) {
            f = DisplayMetrics.DENSITY_DEVICE_STABLE;
        }
        if (this.mLastDensityDpi == f) {
            Slog.i("ActivityTaskManager", "updateMinMaxSizeIfNeeded: new density equals last density. mLastDensityDpi: " + this.mLastDensityDpi);
            return;
        }
        this.mLastDensityDpi = f;
        float initialDisplayDensity = this.mAtmService.mWindowManager.getInitialDisplayDensity(0);
        if (initialDisplayDensity <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            Slog.w("ActivityTaskManager", "updateMinMaxSizeIfNeeded: packageBaseDensity=" + initialDisplayDensity + ", Error");
            return;
        }
        float f2 = f / initialDisplayDensity;
        if (z) {
            Point point = this.mMinDimensions;
            int i = point.x;
            this.mMinWidth = i < 0 ? -1 : (int) ((i * f2) + 0.5f);
            int i2 = point.y;
            this.mMinHeight = i2 < 0 ? -1 : (int) ((i2 * f2) + 0.5f);
        } else {
            this.mMinHeight = -1;
            this.mMinWidth = -1;
        }
        if (!z2) {
            this.mMaxHeight = -1;
            this.mMaxWidth = -1;
            return;
        }
        Point point2 = this.mMaxDimensions;
        int i3 = point2.x;
        this.mMaxWidth = i3 < 0 ? -1 : (int) ((i3 * f2) + 0.5f);
        int i4 = point2.y;
        this.mMaxHeight = i4 >= 0 ? (int) ((i4 * f2) + 0.5f) : -1;
    }

    public final void updateMinimizeChangeInfo(int i, int i2, int i3) {
        Transition transition = this.mTransitionController.mCollectingTransition;
        if (transition == null) {
            return;
        }
        if (i2 == -1 || i3 == -1) {
            Point freeformContainerPosition = getFreeformContainerPosition();
            int i4 = freeformContainerPosition.x;
            i3 = freeformContainerPosition.y;
            i2 = i4;
        }
        transition.collect(this, false);
        Transition.ChangeInfo changeInfo = (Transition.ChangeInfo) transition.mChanges.get(this);
        if (changeInfo == null) {
            Slog.w("ActivityTaskManager", "ChangeInfo is null");
            return;
        }
        changeInfo.mMinimizePoint.set(i2, i3);
        changeInfo.mMinimizeAnimState = i;
        this.mAtmService.mFreeformController.releaseForceHideTaskLocked(this);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void updateOverlayInsetsState(WindowState windowState) {
        super.updateOverlayInsetsState(windowState);
        if (windowState == getTopVisibleAppMainWindow(false) && this.mOverlayHost != null) {
            InsetsState insetsState = windowState.getInsetsState(true);
            getBounds(this.mTmpRect);
            TrustedOverlayHost trustedOverlayHost = this.mOverlayHost;
            Rect rect = this.mTmpRect;
            for (int size = trustedOverlayHost.mOverlays.size() - 1; size >= 0; size--) {
                try {
                    ((SurfaceControlViewHost.SurfacePackage) trustedOverlayHost.mOverlays.get(size)).getRemoteInterface().onInsetsChanged(insetsState, rect);
                } catch (Exception unused) {
                }
            }
        }
    }

    public final void updateOverrideConfigurationFromLaunchBounds() {
        Task rootTask = getRootTask();
        Rect launchBounds = (rootTask == this || !rootTask.isOrganized()) ? getLaunchBounds() : null;
        setBounds(launchBounds);
        if (launchBounds == null || launchBounds.isEmpty()) {
            return;
        }
        launchBounds.set(getRequestedOverrideBounds());
    }

    public final void updateSurfaceSize(SurfaceControl.Transaction transaction) {
        int i;
        int i2;
        if (this.mSurfaceControl == null || isOrganized()) {
            return;
        }
        if (isRootTask()) {
            Rect bounds = getBounds();
            i = bounds.width();
            i2 = bounds.height();
        } else {
            i = 0;
            i2 = 0;
        }
        Point point = this.mLastSurfaceSize;
        if (i == point.x && i2 == point.y) {
            return;
        }
        transaction.setWindowCrop(this.mSurfaceControl, i, i2);
        this.mLastSurfaceSize.set(i, i2);
    }

    public final void updateSurfaceVisibilityForDragAndDrop() {
        boolean z;
        if (this.mSurfaceControl == null || (!this.mHiddenWhileActivatingDrag) == this.mLastSurfaceShowing) {
            return;
        }
        getSyncTransaction().setVisibility(this.mSurfaceControl, z);
        this.mLastSurfaceShowing = z;
    }

    public final void updateTaskDescription$1() {
        Task asTask;
        ActivityRecord rootActivity = getRootActivity(false, true);
        if (rootActivity == null) {
            return;
        }
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription();
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new Task$$ExternalSyntheticLambda7(0), PooledLambda.__(ActivityRecord.class), rootActivity, taskDescription);
        forAllActivities((Predicate) obtainPredicate);
        obtainPredicate.recycle();
        taskDescription.setResizeMode(this.mResizeMode);
        taskDescription.setMinWidth(this.mMinWidth);
        taskDescription.setMinHeight(this.mMinHeight);
        this.mTaskDescription = taskDescription;
        TaskChangeNotificationController taskChangeNotificationController = this.mAtmService.mTaskChangeNotificationController;
        Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(11, getTaskInfo());
        taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskDescriptionChanged, obtainMessage);
        obtainMessage.sendToTarget();
        WindowContainer parent = getParent();
        if (parent != null && (asTask = parent.asTask()) != null) {
            asTask.updateTaskDescription$1();
        }
        dispatchTaskInfoChangedIfNeeded(false);
    }

    public final void updateTaskMovement(int i, boolean z, boolean z2) {
        EventLog.writeEvent(31002, Integer.valueOf(this.mTaskId), Integer.valueOf(getRootTask().mTaskId), Integer.valueOf(getDisplayId()), Integer.valueOf(z ? 1 : 0), Integer.valueOf(i));
        TaskDisplayArea displayArea = getDisplayArea();
        if (displayArea != null && isLeafTask()) {
            displayArea.onLeafTaskMoved(this, z, z2);
        }
        if (this.isPersistable) {
            this.mLastTimeMoved = System.currentTimeMillis();
        }
        if (z && this.inRecents) {
            this.mTaskSupervisor.mRecentTasks.add(this);
        }
    }

    public final boolean updateTaskOrganizerState(boolean z) {
        ITaskOrganizer iTaskOrganizer;
        Task asTask;
        if (this.mSurfaceControl == null) {
            return false;
        }
        if (!isRootTask() && !this.mCreatedByOrganizer && ((asTask = getParent().asTask()) == null || !asTask.mCreatedByOrganizer)) {
            return setTaskOrganizer(null);
        }
        ITaskOrganizer taskOrganizer = this.mWmService.mAtmService.mTaskOrganizerController.getTaskOrganizer();
        if (!this.mCreatedByOrganizer || (iTaskOrganizer = this.mTaskOrganizer) == null || taskOrganizer == null || iTaskOrganizer == taskOrganizer) {
            return setTaskOrganizer(taskOrganizer, z);
        }
        return false;
    }

    public final void updateWindowFocusInTask() {
        final boolean z = this.mHasWindowFocus && (inMultiWindowMode() || isDexMode()) && getActivity(new Task$$ExternalSyntheticLambda0(5)) != null;
        if (this.mLastDispatchedWindowFocusInTask != z) {
            this.mLastDispatchedWindowFocusInTask = z;
            forAllWindows(new Consumer() { // from class: com.android.server.wm.Task$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    boolean z2 = z;
                    WindowState windowState = (WindowState) obj;
                    if (windowState.isPopOver()) {
                        return;
                    }
                    Slog.v("WindowManager", "notifyWindowFocusInTaskChanged: " + z2 + ", win=" + windowState);
                    try {
                        windowState.mClient.windowFocusInTaskChanged(z2);
                    } catch (RemoteException e) {
                        Slog.w("WindowManager", "Failed to notifyWindowFocusInTaskChanged, win=" + windowState, e);
                    }
                }
            }, true);
        }
    }

    @Override // com.android.server.wm.TaskFragment, com.android.server.wm.WindowContainer
    public final void writeIdentifierToProto(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, this.mUserId);
        Intent intent = this.intent;
        protoOutputStream.write(1138166333443L, (intent == null || intent.getComponent() == null) ? "Task" : this.intent.getComponent().flattenToShortString());
        protoOutputStream.end(start);
    }
}
