package com.android.wm.shell.splitscreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.IActivityTaskManager;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayStatus;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.view.Choreographer;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.widget.Toast;
import android.window.DesktopExperienceFlags;
import android.window.DisplayAreaInfo;
import android.window.InputTransferToken;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.policy.FoldLockSettingsObserver;
import com.android.internal.protolog.ProtoLog;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.FrameworkStatsLog;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.R;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wmshell.WMShell;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.LaunchAdjacentController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.split.CellUtil;
import com.android.wm.shell.common.split.DividerResizeController;
import com.android.wm.shell.common.split.DividerSnapAlgorithm;
import com.android.wm.shell.common.split.DividerView;
import com.android.wm.shell.common.split.MultiSplitLayoutInfo;
import com.android.wm.shell.common.split.OffscreenTouchZone;
import com.android.wm.shell.common.split.SplitDecorManager;
import com.android.wm.shell.common.split.SplitLayout;
import com.android.wm.shell.common.split.SplitScreenUtils;
import com.android.wm.shell.common.split.SplitState;
import com.android.wm.shell.common.split.SplitWindowManager;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.split.SplitBounds;
import com.android.wm.shell.shared.split.SplitScreenConstants;
import com.android.wm.shell.splitscreen.SplitBackgroundController;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.SplitScreenTransitions;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.OneShotRemoteHandler;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.util.StageUtils;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StageCoordinator implements SplitLayout.SplitLayoutHandler, DisplayController.OnDisplaysChangedListener, Transitions.TransitionHandler, ShellTaskOrganizer.TaskListener, StageTaskListener.StageListenerCallbacks {
    public boolean mBreakOnNextWake;
    public ValueAnimator mCellDividerFadeInAnimator;
    public boolean mCellDividerVisible;
    public final StageTaskListener mCellStage;
    public boolean mChangingParallelMultiSplit;
    public final Context mContext;
    public final DesktopState mDesktopState;
    public final Optional mDesktopTasksController;
    public final DisplayController mDisplayController;
    public final int mDisplayId;
    public final DisplayImeController mDisplayImeController;
    public final DisplayInsetsController mDisplayInsetsController;
    public ValueAnimator mDividerFadeInAnimator;
    public boolean mDividerLeashHidden;
    public DividerResizeController mDividerResizeController;
    public boolean mDividerVisible;
    public boolean mExitSplitScreenOnHide;
    public final FoldLockSettingsObserver mFoldLockSettingsObserver;
    public final Runnable mHandleSplitWithAIAssistTimeoutRunnable;
    public boolean mIsDropEntering;
    public boolean mIsExiting;
    public boolean mIsMultiSplitRotating;
    public boolean mIsOpeningHomeDuringSplit;
    public boolean mIsRecentsInSplitAnimating;
    public boolean mIsRootTranslucent;
    public boolean mIsTaskOpening;
    public boolean mKeyguardActive;
    int mLastActiveStage;
    public final Configuration mLastConfiguration;
    public int mLastMainSplitDivision;
    public int mLastReportedCellStageWinConfigPosition;
    public int mLastReportedMainStageWinConfigPosition;
    public int mLastReportedSideStageWinConfigPosition;
    public SplitBounds mLastSplitStateInfo;
    public final LaunchAdjacentController mLaunchAdjacentController;
    public final SplitscreenEventLogger mLogger;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final StageTaskListener mMainStage;
    public DefaultMixedHandler mMixedHandler;
    public WindowContainerToken mMovingToFreeformTaskToken;
    public int mOrientation;
    public final AnonymousClass1 mParentContainerCallbacks;
    public final Optional mRecentTasks;
    public final RootTaskDisplayAreaOrganizer mRootTDAOrganizer;
    ActivityManager.RunningTaskInfo mRootTaskInfo;
    public SurfaceControl mRootTaskLeash;
    public final StageTaskListener mSideStage;
    public boolean mSkipEvictingMainStageChildren;
    public final SplitBackgroundController mSplitBackgroundController;
    public int mSplitDivision;
    public WMShell.AnonymousClass10 mSplitInvocationListener;
    public Executor mSplitInvocationListenerExecutor;
    public SplitLayout mSplitLayout;
    public boolean mSplitLayoutChangedForLaunchAdjacent;
    public SplitRequest mSplitRequest;
    public final SplitState mSplitState;
    public SplitScreenTransitions mSplitTransitions;
    public final Toast mSplitUnsupportedToast;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public Configuration mTmpConfigAfterFoldDismiss;
    public final TransactionPool mTransactionPool;
    public final Transitions mTransitions;
    public boolean mUpdateCoverDisplaySplitLayout;
    public final Optional mWindowDecorViewModel;
    public int mCellStageWindowConfigPosition = 0;
    public int mSideStagePosition = 1;
    public final List mListeners = new ArrayList();
    public final Set mSelectListeners = new HashSet();
    public final ArrayList mPausingTasks = new ArrayList();
    public final Rect mTempRect1 = new Rect();
    public final Rect mTempRect2 = new Rect();
    public final Rect mTempRect3 = new Rect();
    public boolean mIsFolded = false;
    public int mTopStageAfterFold = -1;
    public boolean mIsStageTasksChanged = false;
    public boolean mShouldUpdateRecents = true;
    public int mLastTransactionType = 0;
    public final Runnable mReEnableLaunchAdjacentOnRoot = new StageCoordinator$$ExternalSyntheticLambda6(0, this);
    public final Runnable mDelayedHandleLayoutSizeChange = new StageCoordinator$$ExternalSyntheticLambda6(3, this);
    public final ArrayList mLastPackageNameList = new ArrayList();
    public final ArrayList mCurrentPackageNameList = new ArrayList();
    public final List mExcludeLoggingPackages = Arrays.asList("com.sec.android.app.launcher", "com.android.systemui");
    public boolean mAppPairStarted = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$1, reason: invalid class name */
    public class AnonymousClass1 implements SplitWindowManager.ParentContainerCallbacks {
        public AnonymousClass1() {
        }

        public final void inflateOnStageRoot(OffscreenTouchZone offscreenTouchZone) {
            StageCoordinator stageCoordinator = StageCoordinator.this;
            int i = stageCoordinator.mSideStagePosition;
            SurfaceControl surfaceControl = (i == 1 ? stageCoordinator.mMainStage : stageCoordinator.mSideStage).mRootLeash;
            SurfaceControl surfaceControl2 = (i == 1 ? stageCoordinator.mSideStage : stageCoordinator.mMainStage).mRootLeash;
            Context createConfigurationContext = stageCoordinator.mContext.createConfigurationContext(stageCoordinator.mRootTaskInfo.configuration);
            Configuration configuration = stageCoordinator.mRootTaskInfo.configuration;
            if (!offscreenTouchZone.mIsTopLeft) {
                surfaceControl = surfaceControl2;
            }
            View view = new View(createConfigurationContext);
            view.setOnTouchListener(new OffscreenTouchZone.OffscreenTouchListener(offscreenTouchZone, 0));
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2022, 8, -3);
            layoutParams.token = new Binder();
            layoutParams.setTitle("OffscreenTouchZone");
            layoutParams.privateFlags |= 536870976;
            view.setLayoutParams(layoutParams);
            SurfaceControl.Builder callsite = new SurfaceControl.Builder().setContainerLayer().setName("OffscreenTouchZone".concat(offscreenTouchZone.mIsTopLeft ? "TopLeft" : "BottomRight")).setCallsite("OffscreenTouchZone::init");
            callsite.setParent(surfaceControl);
            SurfaceControl build = callsite.build();
            offscreenTouchZone.mLeash = build;
            SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(createConfigurationContext, createConfigurationContext.getDisplay(), new WindowlessWindowManager(configuration, build, (InputTransferToken) null), "SplitTouchZones");
            offscreenTouchZone.mViewHost = surfaceControlViewHost;
            surfaceControlViewHost.setView(view, layoutParams);
            final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setLayer(build, Integer.MAX_VALUE);
            transaction.show(build);
            stageCoordinator.mSyncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.common.split.OffscreenTouchZone$$ExternalSyntheticLambda0
                @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                    SurfaceControl.Transaction transaction3 = transaction;
                    transaction2.merge(transaction3);
                    transaction3.close();
                }
            });
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$2, reason: invalid class name */
    public class AnonymousClass2 extends IRemoteAnimationRunner.Stub {
        public final /* synthetic */ boolean val$isEnteringSplit;
        public final /* synthetic */ int val$position;

        public AnonymousClass2(boolean z, int i) {
            this.val$isEnteringSplit = z;
            this.val$position = i;
        }

        public final void onAnimationCancelled() {
            if (this.val$isEnteringSplit) {
                StageCoordinator.this.mMainExecutor.execute(new StageCoordinator$2$$ExternalSyntheticLambda0(this, 0));
            }
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (this.val$isEnteringSplit && StageCoordinator.this.mSideStage.getChildCount() == 0) {
                StageCoordinator.this.mMainExecutor.execute(new StageCoordinator$2$$ExternalSyntheticLambda0(this, 1));
                Log.w("StageCoordinator", SplitScreenUtils.splitFailureMessage("startShortcut", "side stage was not populated"));
                StageCoordinator stageCoordinator = StageCoordinator.this;
                stageCoordinator.mSplitUnsupportedToast.setText(R.string.dock_non_resizeble_failed_to_dock_text);
                stageCoordinator.mSplitUnsupportedToast.show();
                stageCoordinator.notifySplitAnimationFinished();
            }
            if (iRemoteAnimationFinishedCallback != null) {
                try {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                } catch (RemoteException e) {
                    Slog.e("StageCoordinator", "Error finishing legacy transition: ", e);
                }
            }
            if (CoreRune.MW_SPLIT_STACKING || this.val$isEnteringSplit || remoteAnimationTargetArr == null) {
                return;
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            StageCoordinator stageCoordinator2 = StageCoordinator.this;
            if (this.val$position == stageCoordinator2.mSideStagePosition) {
                stageCoordinator2.mSideStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
            } else {
                stageCoordinator2.mMainStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
            }
            StageCoordinator.this.mSyncQueue.queue(windowContainerTransaction);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RecentsTransitionCallback {
        public /* synthetic */ RecentsTransitionCallback(int i, StageCoordinator stageCoordinator) {
            this();
        }

        private RecentsTransitionCallback() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class StageChangeRecord {
        public boolean mContainShowFullscreenChange = false;
        public final ArrayMap mChanges = new ArrayMap();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class StageChange {
            public final IntArray mAddedTaskId = new IntArray();
            public final IntArray mRemovedTaskId = new IntArray();
            public final StageTaskListener mStageTaskListener;

            public StageChange(StageTaskListener stageTaskListener) {
                this.mStageTaskListener = stageTaskListener;
            }

            public final boolean shouldDismissStage() {
                StageTaskListener stageTaskListener;
                if (this.mAddedTaskId.size() <= 0 && this.mRemovedTaskId.size() != 0) {
                    int size = this.mRemovedTaskId.size() - 1;
                    int i = 0;
                    while (true) {
                        stageTaskListener = this.mStageTaskListener;
                        if (size < 0) {
                            break;
                        }
                        if (stageTaskListener.mChildrenTaskInfo.contains(this.mRemovedTaskId.get(size))) {
                            i++;
                        }
                        size--;
                    }
                    if (i == stageTaskListener.getChildCount()) {
                        return true;
                    }
                }
                return false;
            }
        }

        public final void addRecord(StageTaskListener stageTaskListener, boolean z, int i) {
            StageChange stageChange;
            if (this.mChanges.containsKey(stageTaskListener)) {
                stageChange = (StageChange) this.mChanges.get(stageTaskListener);
            } else {
                stageChange = new StageChange(stageTaskListener);
                this.mChanges.put(stageTaskListener, stageChange);
            }
            if (z) {
                stageChange.mAddedTaskId.add(i);
            } else {
                stageChange.mRemovedTaskId.add(i);
            }
        }
    }

    public StageCoordinator(Context context, int i, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, Transitions transitions, TransactionPool transactionPool, IconProvider iconProvider, ShellExecutor shellExecutor, Handler handler, Optional<RecentTasksController> optional, LaunchAdjacentController launchAdjacentController, Optional<WindowDecorViewModel> optional2, SplitState splitState, Optional<DesktopTasksController> optional3, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopState desktopState) {
        Context context2;
        int i2 = 0;
        new Rect();
        this.mSplitDivision = 0;
        this.mLastMainSplitDivision = 0;
        Configuration configuration = new Configuration();
        this.mLastConfiguration = configuration;
        this.mIsMultiSplitRotating = false;
        this.mChangingParallelMultiSplit = false;
        this.mUpdateCoverDisplaySplitLayout = false;
        this.mParentContainerCallbacks = new AnonymousClass1();
        boolean z = CoreRune.MW_MULTI_SPLIT_BACKGROUND;
        RecentsTransitionCallback recentsTransitionCallback = z ? new RecentsTransitionCallback(i2, this) : null;
        this.mSplitLayoutChangedForLaunchAdjacent = false;
        new ArrayList();
        new ArrayList();
        new ArrayList();
        new ArrayList();
        this.mHandleSplitWithAIAssistTimeoutRunnable = new StageCoordinator$$ExternalSyntheticLambda6(4, this);
        this.mContext = context;
        this.mDisplayId = i;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mLogger = new SplitscreenEventLogger();
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mRecentTasks = optional;
        this.mLaunchAdjacentController = launchAdjacentController;
        this.mWindowDecorViewModel = optional2;
        this.mSplitState = splitState;
        this.mDesktopTasksController = optional3;
        this.mRootTDAOrganizer = rootTaskDisplayAreaOrganizer;
        this.mDesktopState = desktopState;
        DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Objects.requireNonNull(displayManager);
        new SplitMultiDisplayHelper(displayManager);
        shellTaskOrganizer.createRootTask(i, this);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1564738155237815538L, 0, null);
        }
        RecentsTransitionCallback recentsTransitionCallback2 = recentsTransitionCallback;
        this.mMainStage = new StageTaskListener(context, shellTaskOrganizer, i, this, syncTransactionQueue, iconProvider, optional2, 0);
        this.mSideStage = new StageTaskListener(context, shellTaskOrganizer, i, this, syncTransactionQueue, iconProvider, optional2, 1);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            context2 = context;
            this.mCellStage = new StageTaskListener(context2, shellTaskOrganizer, i, this, syncTransactionQueue, iconProvider, optional2, 5);
        } else {
            context2 = context;
        }
        this.mTransitions = transitions;
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mTransactionPool = transactionPool;
        ((DeviceStateManager) context2.getSystemService(DeviceStateManager.class)).registerCallback(shellTaskOrganizer.getExecutor(), new DeviceStateManager.FoldStateListener(context2, new StageCoordinator$$ExternalSyntheticLambda14(0, this)));
        this.mSplitTransitions = new SplitScreenTransitions(transactionPool, transitions, new StageCoordinator$$ExternalSyntheticLambda6(5, this), this);
        displayController.addDisplayWindowListener(this, -1);
        transitions.addHandler(this);
        this.mSplitUnsupportedToast = Toast.makeText(context2, R.string.dock_non_resizeble_failed_to_dock_text, 0);
        FoldLockSettingsObserver foldLockSettingsObserver = new FoldLockSettingsObserver(handler, context2);
        this.mFoldLockSettingsObserver = foldLockSettingsObserver;
        foldLockSettingsObserver.register();
        SplitBackgroundController splitBackgroundController = new SplitBackgroundController(context2, this, transactionPool, shellExecutor, displayController);
        this.mSplitBackgroundController = splitBackgroundController;
        if (z) {
            transitions.mRecentTransitionCallback = recentsTransitionCallback2;
        }
        registerSplitScreenListener(splitBackgroundController);
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
            configuration.updateFrom(context.getResources().getConfiguration());
        }
    }

    public static void addActivityOptions(Bundle bundle, StageTaskListener stageTaskListener) {
        ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
        if (stageTaskListener != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1218188467398233829L, 0, String.valueOf(SplitScreen.stageTypeToString(stageTaskListener.mId)));
            }
            fromBundle.setLaunchRootTask(stageTaskListener.mRootTaskInfo.token);
        }
        fromBundle.setStartedFromWindowTypeLauncher(true);
        fromBundle.setLaunchDisplayId(0);
        fromBundle.setPendingIntentBackgroundActivityStartMode(3);
        fromBundle.setDisallowEnterPictureInPictureWhileLaunching(true);
        bundle.putAll(fromBundle.toBundle());
    }

    public static int convertCreateMode(MultiSplitLayoutInfo multiSplitLayoutInfo) {
        if (multiSplitLayoutInfo.splitDivision == 0) {
            int i = multiSplitLayoutInfo.cellStagePosition;
            if ((i & 8) != 0) {
                return 2;
            }
            return ((i & 32) == 0 && multiSplitLayoutInfo.sideStagePosition == 1) ? 2 : 4;
        }
        int i2 = multiSplitLayoutInfo.cellStagePosition;
        if ((i2 & 16) != 0) {
            return 3;
        }
        return ((i2 & 64) == 0 && multiSplitLayoutInfo.sideStagePosition == 1) ? 3 : 5;
    }

    public static ActivityManager.RecentTaskInfo getRecentTaskInfo(int i) {
        try {
            for (ActivityManager.RecentTaskInfo recentTaskInfo : ActivityTaskManager.getInstance().getRecentTasks(Integer.MAX_VALUE, 3, -2)) {
                if (recentTaskInfo.taskId == i) {
                    return recentTaskInfo;
                }
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean isVisibleTask(ActivityManager.RunningTaskInfo runningTaskInfo, Intent intent, UserHandle userHandle) {
        return (runningTaskInfo == null || intent == null || userHandle == null || intent.getComponent() == null || runningTaskInfo.baseIntent == null || !intent.getComponent().equals(runningTaskInfo.baseIntent.getComponent()) || runningTaskInfo.userId != userHandle.getIdentifier()) ? false : true;
    }

    public static int rotateMultiSplitClockwise(MultiSplitLayoutInfo multiSplitLayoutInfo) {
        int convertCreateMode = convertCreateMode(multiSplitLayoutInfo);
        if (convertCreateMode == 2) {
            multiSplitLayoutInfo.splitDivision = 1;
            int i = multiSplitLayoutInfo.cellStagePosition;
            if (i == 24) {
                multiSplitLayoutInfo.cellStagePosition = 48;
                return 3;
            }
            if (i == 72) {
                multiSplitLayoutInfo.cellStagePosition = 24;
            }
            return 3;
        }
        if (convertCreateMode == 3) {
            multiSplitLayoutInfo.sideStagePosition = SplitScreenUtils.reverseSplitPosition(multiSplitLayoutInfo.sideStagePosition);
            multiSplitLayoutInfo.splitDivision = 0;
            int i2 = multiSplitLayoutInfo.cellStagePosition;
            if (i2 == 24) {
                multiSplitLayoutInfo.cellStagePosition = 48;
                return 4;
            }
            if (i2 == 48) {
                multiSplitLayoutInfo.cellStagePosition = 96;
            }
            return 4;
        }
        if (convertCreateMode == 4) {
            multiSplitLayoutInfo.splitDivision = 1;
            int i3 = multiSplitLayoutInfo.cellStagePosition;
            if (i3 == 48) {
                multiSplitLayoutInfo.cellStagePosition = 96;
                return 5;
            }
            if (i3 == 96) {
                multiSplitLayoutInfo.cellStagePosition = 72;
            }
            return 5;
        }
        if (convertCreateMode != 5) {
            return -1;
        }
        multiSplitLayoutInfo.sideStagePosition = SplitScreenUtils.reverseSplitPosition(multiSplitLayoutInfo.sideStagePosition);
        multiSplitLayoutInfo.splitDivision = 0;
        int i4 = multiSplitLayoutInfo.cellStagePosition;
        if (i4 == 72) {
            multiSplitLayoutInfo.cellStagePosition = 24;
            return 2;
        }
        if (i4 == 96) {
            multiSplitLayoutInfo.cellStagePosition = 72;
        }
        return 2;
    }

    public final void addCellDividerBarToTransition(TransitionInfo transitionInfo, boolean z) {
        SurfaceControl cellDividerLeash = this.mSplitLayout.getCellDividerLeash();
        if (cellDividerLeash == null || !cellDividerLeash.isValid()) {
            Slog.w("StageCoordinator", "addDividerBarToTransition but leash was released or not be created");
            return;
        }
        TransitionInfo.Change change = new TransitionInfo.Change((WindowContainerToken) null, cellDividerLeash);
        Rect rect = this.mTempRect3;
        SplitLayout splitLayout = this.mSplitLayout;
        splitLayout.getClass();
        Rect rect2 = new Rect(splitLayout.mCellDividerBounds);
        Rect rect3 = splitLayout.mRootBounds;
        rect2.offset(-rect3.left, -rect3.top);
        rect.set(rect2);
        change.setParent(this.mRootTaskInfo.token);
        change.setStartAbsBounds(this.mTempRect3);
        change.setEndAbsBounds(this.mTempRect3);
        change.setMode(z ? 3 : 4);
        change.setFlags(16777216);
        change.setIsCellDivider(true);
        transitionInfo.addChange(change);
        StringBuilder sb = new StringBuilder("addCellDividerBarToTransition:[MST] leash=");
        sb.append(cellDividerLeash);
        sb.append(", Callers=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(7, "StageCoordinator", sb);
    }

    public final void addChangeTransitFlagsToStages(WindowContainerTransaction windowContainerTransaction, boolean z) {
        WindowContainerToken stageToken;
        WindowContainerToken stageToken2 = getStageToken(0);
        WindowContainerToken stageToken3 = getStageToken(1);
        if (stageToken2 != null) {
            windowContainerTransaction.addChangeTransitFlags(stageToken2, 2);
        }
        if (stageToken3 != null) {
            windowContainerTransaction.addChangeTransitFlags(stageToken3, 2);
        }
        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && z && (stageToken = getStageToken(5)) != null) {
            windowContainerTransaction.addChangeTransitFlags(stageToken, 2);
        }
    }

    public final void addDividerBarToTransition(TransitionInfo transitionInfo, boolean z) {
        SurfaceControl dividerLeash = this.mSplitLayout.getDividerLeash();
        if (dividerLeash == null || !dividerLeash.isValid()) {
            Slog.w("StageCoordinator", "addDividerBarToTransition but leash was released or not be created");
            return;
        }
        TransitionInfo.Change change = new TransitionInfo.Change((WindowContainerToken) null, dividerLeash);
        SplitLayout splitLayout = this.mSplitLayout;
        Rect rect = this.mTempRect1;
        rect.set(splitLayout.mDividerBounds);
        Rect rect2 = splitLayout.mRootBounds;
        rect.offset(-rect2.left, -rect2.top);
        change.setParent(this.mRootTaskInfo.token);
        change.setStartAbsBounds(this.mTempRect1);
        change.setEndAbsBounds(this.mTempRect1);
        change.setMode(z ? 3 : 4);
        change.setFlags(16777216);
        transitionInfo.addChange(change);
        if (CoreRune.MW_SHELL_TRANSITION_LOG) {
            StringBuilder sb = new StringBuilder("addDividerBarToTransition:[MST] leash=");
            sb.append(dividerLeash);
            sb.append(", Callers=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(7, "StageCoordinator", sb);
        }
    }

    public final void applyCellDividerVisibility(SurfaceControl.Transaction transaction) {
        final SurfaceControl cellDividerLeash = this.mSplitLayout.getCellDividerLeash();
        if (cellDividerLeash == null) {
            return;
        }
        ValueAnimator valueAnimator = this.mCellDividerFadeInAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            Slog.d("StageCoordinator", "applyCellDividerVisibility: cancel, prev animator");
            this.mCellDividerFadeInAnimator.cancel();
        }
        Slog.d("StageCoordinator", "applyCellDividerVisibility: vis=" + this.mCellDividerVisible);
        if (transaction != null) {
            updateSurfaceBounds(this.mSplitLayout, transaction, false);
            transaction.setVisibility(cellDividerLeash, this.mCellDividerVisible);
            return;
        }
        boolean z = this.mCellDividerVisible;
        TransactionPool transactionPool = this.mTransactionPool;
        if (!z) {
            SurfaceControl.Transaction acquire = transactionPool.acquire();
            acquire.hide(cellDividerLeash);
            acquire.apply();
            transactionPool.release(acquire);
            return;
        }
        final SurfaceControl.Transaction acquire2 = transactionPool.acquire();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mCellDividerFadeInAnimator = ofFloat;
        ofFloat.addUpdateListener(new StageCoordinator$$ExternalSyntheticLambda10(this, cellDividerLeash, acquire2, 1));
        this.mCellDividerFadeInAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SurfaceControl surfaceControl = cellDividerLeash;
                if (surfaceControl != null && surfaceControl.isValid()) {
                    acquire2.setAlpha(cellDividerLeash, 1.0f);
                    acquire2.apply();
                }
                StageCoordinator.this.mTransactionPool.release(acquire2);
                StageCoordinator.this.mCellDividerFadeInAnimator = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                SurfaceControl surfaceControl = cellDividerLeash;
                if (surfaceControl == null || !surfaceControl.isValid()) {
                    StageCoordinator.this.mCellDividerFadeInAnimator.cancel();
                    return;
                }
                StageCoordinator stageCoordinator = StageCoordinator.this;
                stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, acquire2, false);
                acquire2.show(cellDividerLeash);
                acquire2.setAlpha(cellDividerLeash, 0.0f);
                acquire2.apply();
            }
        });
        this.mCellDividerFadeInAnimator.start();
    }

    public final void applyCellHostResizeTransition(WindowContainerTransaction windowContainerTransaction) {
        WindowContainerToken stageToken = getStageToken(getCellHostStageType());
        if (stageToken == null) {
            Slog.w("StageCoordinator", "applyCellHostResizeTransition: cannot find cell host token");
        } else {
            windowContainerTransaction.setChangeTransitMode(stageToken, 1, "cell_start");
        }
    }

    public final void applyDividerVisibility(SurfaceControl.Transaction transaction) {
        final SurfaceControl dividerLeash = this.mSplitLayout.getDividerLeash();
        if (dividerLeash == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -2891277396196116082L, 0, null);
                return;
            }
            return;
        }
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && this.mIsMultiSplitRotating) {
            return;
        }
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mChangingParallelMultiSplit) {
            return;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && transaction != null) {
            StageTaskListener stageTaskListener = this.mCellStage;
            if (stageTaskListener.mIsActive && stageTaskListener.mToSplit) {
                transaction.setVisibility(dividerLeash, this.mDividerVisible);
                return;
            }
        }
        ValueAnimator valueAnimator = this.mDividerFadeInAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mDividerFadeInAnimator.cancel();
        }
        if (transaction != null) {
            updateSurfaceBounds(this.mSplitLayout, transaction, false);
            transaction.setVisibility(dividerLeash, this.mDividerVisible);
        } else {
            boolean z = this.mDividerVisible;
            TransactionPool transactionPool = this.mTransactionPool;
            if (z) {
                final SurfaceControl.Transaction acquire = transactionPool.acquire();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mDividerFadeInAnimator = ofFloat;
                ofFloat.addUpdateListener(new StageCoordinator$$ExternalSyntheticLambda10(this, dividerLeash, acquire, 0));
                this.mDividerFadeInAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        SurfaceControl surfaceControl = dividerLeash;
                        if (surfaceControl != null && surfaceControl.isValid()) {
                            acquire.setAlpha(dividerLeash, 1.0f);
                            acquire.apply();
                        }
                        StageCoordinator.this.mTransactionPool.release(acquire);
                        StageCoordinator.this.mDividerFadeInAnimator = null;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        SurfaceControl surfaceControl = dividerLeash;
                        if (surfaceControl == null || !surfaceControl.isValid()) {
                            StageCoordinator.this.mDividerFadeInAnimator.cancel();
                            return;
                        }
                        StageCoordinator stageCoordinator = StageCoordinator.this;
                        stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, acquire, false);
                        acquire.show(dividerLeash);
                        acquire.setAlpha(dividerLeash, 0.0f);
                        acquire.apply();
                    }
                });
                this.mDividerFadeInAnimator.start();
            } else {
                SurfaceControl.Transaction acquire2 = transactionPool.acquire();
                acquire2.hide(dividerLeash);
                acquire2.apply();
                transactionPool.release(acquire2);
            }
        }
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && isMultiSplitActive()) {
            applyCellDividerVisibility(transaction);
        }
    }

    public final void applyExitSplitScreen(final StageTaskListener stageTaskListener, WindowContainerTransaction windowContainerTransaction, int i) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -8832527832122182701L, 0, String.valueOf(SplitScreenController.exitReasonToString(i)));
        }
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (!stageTaskListener2.mIsActive || this.mIsExiting) {
            return;
        }
        this.mSplitState.mState = -1;
        clearSplitPairedInRecents(i);
        this.mShouldUpdateRecents = false;
        this.mSplitRequest = null;
        this.mTempRect1.set(this.mSplitLayout.mInvisibleBounds);
        if (stageTaskListener == null || stageTaskListener.getTopVisibleChildTaskId() == -1) {
            StageTaskListener stageTaskListener3 = this.mSideStage;
            stageTaskListener3.removeAllTasks(windowContainerTransaction, false);
            this.mMainStage.deactivate(windowContainerTransaction, false);
            windowContainerTransaction.reorder(this.mRootTaskInfo.token, false);
            setRootForceTranslucent(windowContainerTransaction, true);
            windowContainerTransaction.setBounds(stageTaskListener3.mRootTaskInfo.token, this.mTempRect1);
            onTransitionAnimationComplete();
        } else {
            this.mIsExiting = true;
            windowContainerTransaction.setBounds(stageTaskListener.mRootTaskInfo.token, (Rect) null);
            windowContainerTransaction.setAppBounds(stageTaskListener.mRootTaskInfo.token, (Rect) null);
            windowContainerTransaction.setSmallestScreenWidthDp(stageTaskListener.mRootTaskInfo.token, 0);
            windowContainerTransaction.reorder(stageTaskListener.mRootTaskInfo.token, true);
        }
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, false);
        windowContainerTransaction.setDismissSplit(true);
        SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
        syncTransactionQueue.queue(windowContainerTransaction);
        syncTransactionQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda16
            @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
            public final void runWithTransaction(SurfaceControl.Transaction transaction) {
                final StageCoordinator stageCoordinator = StageCoordinator.this;
                StageTaskListener stageTaskListener4 = stageCoordinator.mMainStage;
                SurfaceControl.Transaction windowCrop = transaction.setWindowCrop(stageTaskListener4.mRootLeash, null);
                StageTaskListener stageTaskListener5 = stageCoordinator.mSideStage;
                windowCrop.setWindowCrop(stageTaskListener5.mRootLeash, null);
                transaction.hide(stageTaskListener4.mDimLayer).hide(stageTaskListener5.mDimLayer);
                stageCoordinator.setDividerVisibility(transaction, false);
                final StageTaskListener stageTaskListener6 = stageTaskListener;
                if (stageTaskListener6 == null) {
                    SurfaceControl surfaceControl = stageTaskListener5.mRootLeash;
                    Rect rect = stageCoordinator.mTempRect1;
                    transaction.setPosition(surfaceControl, rect.left, rect.right);
                } else {
                    Runnable runnable = new Runnable() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda29
                        @Override // java.lang.Runnable
                        public final void run() {
                            StageCoordinator stageCoordinator2 = StageCoordinator.this;
                            StageTaskListener stageTaskListener7 = stageTaskListener6;
                            stageCoordinator2.getClass();
                            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                            stageCoordinator2.mIsExiting = false;
                            stageCoordinator2.mMainStage.deactivate(windowContainerTransaction2, stageTaskListener7.mId == 0);
                            StageTaskListener stageTaskListener8 = stageCoordinator2.mSideStage;
                            stageTaskListener8.removeAllTasks(windowContainerTransaction2, stageTaskListener7 == stageTaskListener8);
                            windowContainerTransaction2.reorder(stageCoordinator2.mRootTaskInfo.token, false);
                            stageCoordinator2.setRootForceTranslucent(windowContainerTransaction2, true);
                            windowContainerTransaction2.setBounds(stageTaskListener8.mRootTaskInfo.token, stageCoordinator2.mTempRect1);
                            SyncTransactionQueue syncTransactionQueue2 = stageCoordinator2.mSyncQueue;
                            syncTransactionQueue2.queue(windowContainerTransaction2);
                            syncTransactionQueue2.runInSync(new StageCoordinator$$ExternalSyntheticLambda2(stageCoordinator2, 1));
                            stageCoordinator2.onTransitionAnimationComplete();
                        }
                    };
                    SplitDecorManager splitDecorManager = stageTaskListener6.mSplitDecorManager;
                    if (splitDecorManager != null) {
                        splitDecorManager.fadeOutDecor(runnable, false);
                    } else {
                        runnable.run();
                    }
                }
            }
        });
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
            this.mTopStageAfterFold = -1;
        }
        if (stageTaskListener != null) {
            logExitToStage(i, stageTaskListener == stageTaskListener2);
        } else {
            logExit(i);
        }
        if (CoreRune.MW_SA_LOGGING) {
            this.mLastPackageNameList.clear();
        }
    }

    public final void applyParallelMultiSplitLayoutInfo(WindowContainerTransaction windowContainerTransaction, MultiSplitLayoutInfo multiSplitLayoutInfo) {
        int convertCreateMode = convertCreateMode(multiSplitLayoutInfo);
        multiSplitLayoutInfo.splitDivision = !isLandscape() ? 1 : 0;
        if (convertCreateMode == 2 || convertCreateMode == 3) {
            if (multiSplitLayoutInfo.cellStagePosition != 24) {
                swapCellAndHostStageTasks(windowContainerTransaction);
            }
            multiSplitLayoutInfo.cellStagePosition = 24;
        } else if (convertCreateMode == 4 || convertCreateMode == 5) {
            if (multiSplitLayoutInfo.cellStagePosition != 96) {
                swapCellAndHostStageTasks(windowContainerTransaction);
            }
            multiSplitLayoutInfo.cellStagePosition = 96;
        }
    }

    public final float calculateSplitRatioForParallelMultiSplit(MultiSplitLayoutInfo multiSplitLayoutInfo) {
        if (!this.mCellStage.mIsActive) {
            return 0.5f;
        }
        int cellHostStageType = getCellHostStageType();
        return multiSplitLayoutInfo.sideStagePosition == 0 ? cellHostStageType != 1 ? 0.33f : 0.66f : cellHostStageType != 1 ? 0.66f : 0.33f;
    }

    public final boolean checkNonResizableTaskAndStartTask(int i, int i2, int i3) {
        if (MultiWindowManager.getInstance().isAllTasksResizable(i, i2, i3)) {
            return false;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.startTask(i, (Bundle) null);
        windowContainerTransaction.setDisplayIdForChangeTransition(this.mDisplayId, "dismiss_recent_pair");
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        Slog.d("StageCoordinator", "include non resizable task");
        return true;
    }

    public final void clearSplitPairedInRecents(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 9:
            case 11:
            case 12:
            case 14:
            case 15:
                if (this.mShouldUpdateRecents) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 6140256617638804326L, 0, String.valueOf(SplitScreenController.exitReasonToString(i)));
                    }
                    this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda14(1, this));
                    logExit(i);
                    return;
                }
                break;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2584929832216640845L, 0, String.valueOf(!this.mShouldUpdateRecents ? "shouldn't update" : SplitScreenController.exitReasonToString(i)));
        }
    }

    public final void dismissMainOrSideWhenParallelMultiSplit(WindowContainerTransaction windowContainerTransaction, int i) {
        StageTaskListener stageTaskListener = this.mCellStage;
        if (stageTaskListener.mIsActive) {
            prepareMultiSplitDismissChangeTransition(i, windowContainerTransaction, false);
            StageTaskListener stageTaskListenerByStageType = getStageTaskListenerByStageType(i);
            StageTaskListener stageTaskListener2 = stageTaskListener.mHost;
            if (!stageTaskListenerByStageType.equals(stageTaskListener2)) {
                stageTaskListener2.reparentAllChildren(stageTaskListenerByStageType.mRootTaskInfo.token, windowContainerTransaction);
            }
            stageTaskListener.reparentAllTasks(stageTaskListener2.mRootTaskInfo.token, windowContainerTransaction, true);
            SplitLayout splitLayout = this.mSplitLayout;
            splitLayout.setDividePosition(splitLayout.mCellDividerPosition, null, false);
            prepareExitMultiSplitScreen(windowContainerTransaction, false);
        }
    }

    public final void dismissSplitKeepingLastActiveStage(int i) {
        if (!this.mMainStage.mIsActive || this.mLastActiveStage == -1) {
            return;
        }
        clearSplitPairedInRecents(i);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        prepareExitSplitScreen(this.mLastActiveStage, i, windowContainerTransaction, true);
        this.mSplitTransitions.startDismissTransition(windowContainerTransaction, this, this.mLastActiveStage, i, false);
        setSplitsVisible(false);
        this.mBreakOnNextWake = false;
        logExit(i);
    }

    public final void dismissSplitTask(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction, boolean z) {
        int i;
        int i2;
        boolean hasAppsEdgeActivityOnTop;
        boolean z2 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (!z2 || !isMultiSplitActive()) {
            if (stageTaskListener2.containsToken(windowContainerToken)) {
                hasAppsEdgeActivityOnTop = stageTaskListener.hasAppsEdgeActivityOnTop();
                i = 1;
                i2 = 0;
            } else if (stageTaskListener.containsToken(windowContainerToken)) {
                hasAppsEdgeActivityOnTop = stageTaskListener2.hasAppsEdgeActivityOnTop();
                i2 = 1;
                i = 0;
            } else {
                i = -1;
                i2 = -1;
            }
            if (i != -1 || i2 == -1) {
                Slog.w("StageCoordinator", "dismissSplitTask: failed, cannot find " + windowContainerToken);
            } else {
                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                    prepareAndStartDismissTransition(i, i2, windowContainerTransaction, z, hasAppsEdgeActivityOnTop);
                    return;
                }
                return;
            }
        }
        int cellHostStageType = getCellHostStageType();
        int cellHostStageType2 = getCellHostStageType();
        int i3 = cellHostStageType2 != 0 ? cellHostStageType2 == 1 ? 0 : -1 : 1;
        StageTaskListener stageTaskListener3 = this.mCellStage;
        StageTaskListener stageTaskListener4 = stageTaskListener3.mHost;
        if (stageTaskListener4 != stageTaskListener2) {
            stageTaskListener = stageTaskListener2;
        }
        if (stageTaskListener3.containsToken(windowContainerToken)) {
            i3 = 5;
        } else {
            if (!stageTaskListener4.containsToken(windowContainerToken)) {
                if (stageTaskListener.containsToken(windowContainerToken)) {
                    cellHostStageType = i3;
                } else {
                    cellHostStageType = -1;
                }
            }
            i3 = cellHostStageType;
        }
        i = cellHostStageType;
        i2 = i3;
        hasAppsEdgeActivityOnTop = false;
        if (i != -1) {
        }
        Slog.w("StageCoordinator", "dismissSplitTask: failed, cannot find " + windowContainerToken);
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    @NeverCompile
    public final void dump$2(PrintWriter printWriter, String str) {
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        String m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "  ");
        StringBuilder m3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "StageCoordinator mDisplayId=");
        m3.append(this.mDisplayId);
        printWriter.println(m3.toString());
        StringBuilder sb = new StringBuilder();
        sb.append(m);
        sb.append("mDividerVisible=");
        StringBuilder m4 = BackAnimationController$$ExternalSyntheticOutline0.m(sb, this.mDividerVisible, printWriter, m, "isSplitActive=");
        StageTaskListener stageTaskListener = this.mMainStage;
        StringBuilder m5 = BackAnimationController$$ExternalSyntheticOutline0.m(m4, stageTaskListener.mIsActive, printWriter, m, "isSplitVisible=");
        m5.append(isSplitScreenVisible());
        printWriter.println(m5.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(m);
        sb2.append("isLeftRightSplit=");
        SplitLayout splitLayout = this.mSplitLayout;
        sb2.append(splitLayout != null ? Boolean.valueOf(splitLayout.mIsLeftRightSplit) : "null");
        printWriter.println(sb2.toString());
        printWriter.println(m + "MainStage");
        StringBuilder sb3 = new StringBuilder();
        sb3.append(m2);
        sb3.append("stagePosition=");
        int reverseSplitPosition = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
        sb3.append(reverseSplitPosition != -1 ? reverseSplitPosition != 0 ? reverseSplitPosition != 1 ? "UNKNOWN" : "SPLIT_POSITION_BOTTOM_OR_RIGHT" : "SPLIT_POSITION_TOP_OR_LEFT" : "SPLIT_POSITION_UNDEFINED");
        printWriter.println(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(m2);
        sb4.append("isActive=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb4, stageTaskListener.mIsActive, printWriter);
        stageTaskListener.dump$2(printWriter, m2);
        printWriter.println(m + "SideStage");
        StringBuilder sb5 = new StringBuilder();
        sb5.append(m2);
        sb5.append("stagePosition=");
        int i = this.mSideStagePosition;
        CarrierTextController$$ExternalSyntheticOutline0.m(sb5, i != -1 ? i != 0 ? i != 1 ? "UNKNOWN" : "SPLIT_POSITION_BOTTOM_OR_RIGHT" : "SPLIT_POSITION_TOP_OR_LEFT" : "SPLIT_POSITION_UNDEFINED", printWriter);
        this.mSideStage.dump$2(printWriter, m2);
        SplitLayout splitLayout2 = this.mSplitLayout;
        if (splitLayout2 != null) {
            String m6 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m2, "\t");
            printWriter.println(m2 + "SplitLayout:");
            StringBuilder sb6 = new StringBuilder();
            sb6.append(m6);
            sb6.append("mAllowLeftRightSplitInPortrait=");
            StringBuilder m7 = BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb6, splitLayout2.mAllowLeftRightSplitInPortrait, printWriter, m6, "mIsLeftRightSplit="), splitLayout2.mIsLeftRightSplit, printWriter, m6, "mFreezeDividerWindow="), splitLayout2.mFreezeDividerWindow, printWriter, m6, "mDimNonImeSide="), splitLayout2.mDimNonImeSide, printWriter, m6, "mDividerPosition=");
            m7.append(splitLayout2.mDividerPosition);
            printWriter.println(m7.toString());
            printWriter.println(m6 + "bounds1=" + splitLayout2.getTopLeftBounds().toShortString());
            printWriter.println(m6 + "dividerBounds=" + splitLayout2.mDividerBounds.toShortString());
            printWriter.println(m6 + "bounds2=" + splitLayout2.getBottomRightBounds().toShortString());
            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
                StringBuilder m8 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m2, "bounds3=");
                m8.append(splitLayout2.mBounds3.toShortString());
                printWriter.println(m8.toString());
            }
            if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
                MagnificationImpl$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m2, "splitDivision="), splitLayout2.mSplitDivision, printWriter);
            }
        }
        if (!this.mPausingTasks.isEmpty()) {
            StringBuilder m9 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m2, "mPausingTasks=");
            m9.append(this.mPausingTasks);
            printWriter.println(m9.toString());
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            printWriter.println(m + "CellStage");
            printWriter.println(m2 + "stagePosition=" + WindowConfiguration.stagePositionToString(this.mCellStageWindowConfigPosition));
            this.mCellStage.dump$2(printWriter, m2);
        }
    }

    public final void exitSplitScreen(StageTaskListener stageTaskListener, int i) {
        boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0];
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (z) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 153037047750734517L, 51, Boolean.valueOf(stageTaskListener == stageTaskListener2), String.valueOf(SplitScreenController.exitReasonToString(i)), Boolean.valueOf(stageTaskListener2.mIsActive));
        }
        if (stageTaskListener2.mIsActive) {
            applyExitSplitScreen(stageTaskListener, new WindowContainerTransaction(), i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0181  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void finishEnterSplitScreen(android.view.SurfaceControl.Transaction r32, boolean r33) {
        /*
            Method dump skipped, instructions count: 401
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.finishEnterSplitScreen(android.view.SurfaceControl$Transaction, boolean):void");
    }

    public final int getActivateSplitPosition(TaskInfo taskInfo) {
        SplitRequest splitRequest = this.mSplitRequest;
        if (splitRequest == null || taskInfo == null) {
            return -1;
        }
        int i = splitRequest.mActivateTaskId;
        if (i != 0 && splitRequest.mActivateTaskId2 == taskInfo.taskId) {
            return splitRequest.mActivatePosition;
        }
        if (i == taskInfo.taskId) {
            return splitRequest.mActivatePosition;
        }
        String packageName = ComponentUtils.getPackageName(splitRequest.mStartIntent);
        String packageName2 = ComponentUtils.getPackageName(taskInfo.baseIntent);
        if (packageName != null && packageName.equals(packageName2)) {
            return this.mSplitRequest.mActivatePosition;
        }
        String packageName3 = ComponentUtils.getPackageName(this.mSplitRequest.mStartIntent2);
        if (packageName3 == null || !packageName3.equals(packageName2)) {
            return -1;
        }
        return this.mSplitRequest.mActivatePosition;
    }

    public final ArrayList getBottomStages() {
        ArrayList arrayList = new ArrayList();
        if ((getMainStageWinConfigPosition() & 64) != 0) {
            arrayList.add(this.mMainStage.mRootTaskInfo);
        }
        if ((getSideStageWinConfigPosition() & 64) != 0) {
            arrayList.add(this.mSideStage.mRootTaskInfo);
        }
        if ((this.mCellStageWindowConfigPosition & 64) != 0) {
            arrayList.add(this.mCellStage.mRootTaskInfo);
        }
        return arrayList;
    }

    public final int getCellHostStageType() {
        if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            return -1;
        }
        StageTaskListener stageTaskListener = this.mCellStage;
        if (stageTaskListener.mIsActive) {
            return getStageType(stageTaskListener.mHost);
        }
        return -1;
    }

    public final MultiSplitLayoutInfo getCurrentMultiSplitLayoutInfo() {
        return new MultiSplitLayoutInfo(this.mSideStagePosition, this.mCellStageWindowConfigPosition, getSplitDivision());
    }

    public final int getFocusedStageType() {
        if (this.mMainStage.isFocused()) {
            return 0;
        }
        if (this.mSideStage.isFocused()) {
            return 1;
        }
        return (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING && this.mCellStage.isFocused()) ? 5 : -1;
    }

    public final Rect getMainStageBounds() {
        return (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && isMultiSplitActive() && getCellHostStageType() == 0) ? this.mSplitLayout.getHostBounds() : this.mSideStagePosition == 0 ? this.mSplitLayout.getBottomRightBounds() : this.mSplitLayout.getTopLeftBounds();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0063 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x006b A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getMainStageWinConfigPosition() {
        /*
            r4 = this;
            boolean r0 = com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER
            r1 = 8
            r2 = 16
            if (r0 == 0) goto L56
            com.android.wm.shell.splitscreen.StageTaskListener r0 = r4.mCellStage
            boolean r3 = r0.mIsActive
            if (r3 == 0) goto L46
            com.android.wm.shell.splitscreen.StageTaskListener r0 = r0.mHost
            com.android.wm.shell.splitscreen.StageTaskListener r3 = r4.mMainStage
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L46
            boolean r0 = r4.isVerticalDivision()
            if (r0 == 0) goto L2d
            int r0 = r4.mCellStageWindowConfigPosition
            r0 = r0 & r2
            if (r0 == 0) goto L28
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L40
            goto L36
        L28:
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L43
            goto L39
        L2d:
            int r0 = r4.mCellStageWindowConfigPosition
            r0 = r0 & r1
            if (r0 == 0) goto L3c
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L39
        L36:
            r4 = 96
            return r4
        L39:
            r4 = 48
            return r4
        L3c:
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L43
        L40:
            r4 = 72
            return r4
        L43:
            r4 = 24
            return r4
        L46:
            boolean r0 = r4.isVerticalDivision()
            if (r0 == 0) goto L51
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L63
            goto L60
        L51:
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L6b
            goto L68
        L56:
            boolean r0 = r4.isLandscape()
            if (r0 == 0) goto L64
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L63
        L60:
            r4 = 32
            return r4
        L63:
            return r1
        L64:
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L6b
        L68:
            r4 = 64
            return r4
        L6b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.getMainStageWinConfigPosition():int");
    }

    public final Rect getSideStageBounds() {
        return (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && isMultiSplitActive() && getCellHostStageType() == 1) ? this.mSplitLayout.getHostBounds() : this.mSideStagePosition == 0 ? this.mSplitLayout.getTopLeftBounds() : this.mSplitLayout.getBottomRightBounds();
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0060 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0068 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getSideStageWinConfigPosition() {
        /*
            r4 = this;
            boolean r0 = com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER
            r1 = 8
            r2 = 16
            if (r0 == 0) goto L56
            com.android.wm.shell.splitscreen.StageTaskListener r0 = r4.mCellStage
            boolean r3 = r0.mIsActive
            if (r3 == 0) goto L46
            com.android.wm.shell.splitscreen.StageTaskListener r0 = r0.mHost
            com.android.wm.shell.splitscreen.StageTaskListener r3 = r4.mSideStage
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L46
            boolean r0 = r4.isVerticalDivision()
            if (r0 == 0) goto L2d
            int r0 = r4.mCellStageWindowConfigPosition
            r0 = r0 & r2
            if (r0 == 0) goto L28
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L39
            goto L43
        L28:
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L36
            goto L40
        L2d:
            int r0 = r4.mCellStageWindowConfigPosition
            r0 = r0 & r1
            if (r0 == 0) goto L3c
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L39
        L36:
            r4 = 48
            return r4
        L39:
            r4 = 96
            return r4
        L3c:
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L43
        L40:
            r4 = 24
            return r4
        L43:
            r4 = 72
            return r4
        L46:
            boolean r0 = r4.isVerticalDivision()
            if (r0 == 0) goto L51
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L61
            goto L60
        L51:
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L69
            goto L68
        L56:
            boolean r0 = r4.isLandscape()
            if (r0 == 0) goto L64
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L61
        L60:
            return r1
        L61:
            r4 = 32
            return r4
        L64:
            int r4 = r4.mSideStagePosition
            if (r4 != 0) goto L69
        L68:
            return r2
        L69:
            r4 = 64
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.getSideStageWinConfigPosition():int");
    }

    public final int getSplitCreateMode() {
        MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
        multiSplitLayoutInfo.sideStagePosition = this.mSideStagePosition;
        multiSplitLayoutInfo.splitDivision = getSplitDivision();
        multiSplitLayoutInfo.cellStagePosition = this.mCellStageWindowConfigPosition;
        return convertCreateMode(multiSplitLayoutInfo);
    }

    public final int getSplitDivision() {
        return CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? this.mSplitDivision : !isLandscape() ? 1 : 0;
    }

    public final int getSplitItemPosition(WindowContainerToken windowContainerToken) {
        if (windowContainerToken == null) {
            return -1;
        }
        StageTaskListener stageTaskListener = this.mMainStage;
        if (stageTaskListener.containsToken(windowContainerToken)) {
            return SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
        }
        if (this.mSideStage.containsToken(windowContainerToken)) {
            return this.mSideStagePosition;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            StageTaskListener stageTaskListener2 = this.mCellStage;
            if (stageTaskListener2.containsToken(windowContainerToken)) {
                return stageTaskListener2.mHost == stageTaskListener ? SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition) : this.mSideStagePosition;
            }
        }
        return -1;
    }

    public final int getSplitItemStage(WindowContainerToken windowContainerToken) {
        if (windowContainerToken == null) {
            return -1;
        }
        if (this.mMainStage.containsToken(windowContainerToken)) {
            return 0;
        }
        if (this.mSideStage.containsToken(windowContainerToken)) {
            return 1;
        }
        return (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mCellStage.containsToken(windowContainerToken)) ? 5 : -1;
    }

    public final int getSplitItemStagePosition(WindowContainerToken windowContainerToken) {
        if (windowContainerToken == null) {
            return 0;
        }
        if (this.mMainStage.containsToken(windowContainerToken)) {
            return getMainStageWinConfigPosition();
        }
        if (this.mSideStage.containsToken(windowContainerToken)) {
            return getSideStageWinConfigPosition();
        }
        if (this.mCellStage.containsToken(windowContainerToken)) {
            return this.mCellStageWindowConfigPosition;
        }
        return 0;
    }

    public final int getSplitPosition(int i) {
        if (this.mSideStage.getTopVisibleChildTaskId() == i) {
            return this.mSideStagePosition;
        }
        if (this.mMainStage.getTopVisibleChildTaskId() == i) {
            return SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
        }
        return -1;
    }

    public SplitScreenTransitions getSplitTransitions() {
        return this.mSplitTransitions;
    }

    public final StageTaskListener getStageAtPosition(int i) {
        if (getMainStageWinConfigPosition() == i) {
            return this.mMainStage;
        }
        if (getSideStageWinConfigPosition() == i) {
            return this.mSideStage;
        }
        if (isMultiSplitActive() && this.mCellStageWindowConfigPosition == i) {
            return this.mCellStage;
        }
        return null;
    }

    public final Rect getStageBounds(int i) {
        if (i == 0) {
            return getMainStageBounds();
        }
        if (i == 1) {
            return getSideStageBounds();
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 5) {
            return this.mSplitLayout.getBounds3();
        }
        return null;
    }

    public final int getStageOfTask(int i) {
        if (this.mMainStage.mChildrenTaskInfo.contains(i)) {
            return 0;
        }
        if (this.mSideStage.mChildrenTaskInfo.contains(i)) {
            return 1;
        }
        return (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mCellStage.mChildrenTaskInfo.contains(i)) ? 5 : -1;
    }

    public final StageTaskListener getStageTaskListenerByStageType(int i) {
        if (i == 0) {
            return this.mMainStage;
        }
        if (i == 1) {
            return this.mSideStage;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 5) {
            return this.mCellStage;
        }
        return null;
    }

    public final WindowContainerToken getStageToken(int i) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        if (i == 0) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mMainStage.mRootTaskInfo;
            if (runningTaskInfo2 != null) {
                return runningTaskInfo2.token;
            }
            return null;
        }
        if (i == 1) {
            ActivityManager.RunningTaskInfo runningTaskInfo3 = this.mSideStage.mRootTaskInfo;
            if (runningTaskInfo3 != null) {
                return runningTaskInfo3.token;
            }
            return null;
        }
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && i == 5 && (runningTaskInfo = this.mCellStage.mRootTaskInfo) != null) {
            return runningTaskInfo.token;
        }
        return null;
    }

    public final int getStageType(StageTaskListener stageTaskListener) {
        if (stageTaskListener == null) {
            return -1;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageTaskListener == this.mCellStage) {
            return 5;
        }
        return stageTaskListener == this.mMainStage ? 0 : 1;
    }

    public final int getStageWinConfigPositionByType(int i) {
        if (i == 0) {
            return getMainStageWinConfigPosition();
        }
        if (i == 1) {
            return getSideStageWinConfigPosition();
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 5) {
            return this.mCellStageWindowConfigPosition;
        }
        return 0;
    }

    public final int getTaskIdByStageType(int i) {
        if (i == 0) {
            return this.mMainStage.getTopVisibleChildTaskId();
        }
        if (i == 1) {
            return this.mSideStage.getTopVisibleChildTaskId();
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 5) {
            return this.mCellStage.getTopVisibleChildTaskId();
        }
        return -1;
    }

    public final int getTopStageBottom() {
        if ((getMainStageWinConfigPosition() & 16) != 0) {
            return getMainStageBounds().bottom;
        }
        if ((getSideStageWinConfigPosition() & 16) != 0) {
            return getSideStageBounds().bottom;
        }
        if (!isMultiSplitActive() || (this.mCellStageWindowConfigPosition & 16) == 0) {
            return 0;
        }
        return this.mSplitLayout.getBounds3().bottom;
    }

    public final void grantFocusToPosition(boolean z) {
        int i = this.mSideStagePosition;
        if (i == 1) {
            if (z) {
                i = SplitScreenUtils.reverseSplitPosition(i);
            }
        } else if (!z) {
            i = SplitScreenUtils.reverseSplitPosition(i);
        }
        IActivityTaskManager asInterface = IActivityTaskManager.Stub.asInterface(ServiceManager.getService("activity_task"));
        int i2 = -1;
        if (i != -1) {
            try {
                i2 = this.mSideStagePosition == i ? this.mSideStage.getTopVisibleChildTaskId() : this.mMainStage.getTopVisibleChildTaskId();
            } catch (RemoteException | NullPointerException e) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[4]) {
                    ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -7228576009982558496L, 0, String.valueOf(e.getMessage()));
                    return;
                }
                return;
            }
        }
        asInterface.setFocusedTask(i2);
    }

    public final void handleLayoutSizeChange(SplitLayout splitLayout, boolean z) {
        StageCoordinator stageCoordinator;
        if (this.mKeyguardActive) {
            z = false;
        }
        Runnable runnable = this.mDelayedHandleLayoutSizeChange;
        HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
        if (handlerExecutor.mHandler.hasCallbacks(runnable)) {
            handlerExecutor.removeCallbacks(this.mDelayedHandleLayoutSizeChange);
        }
        splitLayout.updateSnapAlgorithm(this.mSplitDivision);
        StageTaskListener stageTaskListener = this.mMainStage;
        boolean z2 = stageTaskListener.mIsActive;
        boolean isSplitScreenVisible = isSplitScreenVisible();
        if (z2 && !isSplitScreenVisible) {
            splitLayout.update(null, false);
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (z2 && z) {
            windowContainerTransaction.setDisplayIdForChangeTransition(0, "handle_layout_size_change");
        }
        updateStagePositionIfNeeded(windowContainerTransaction);
        int i = this.mSideStagePosition;
        StageTaskListener stageTaskListener2 = this.mSideStage;
        StageTaskListener stageTaskListener3 = i == 0 ? stageTaskListener2 : stageTaskListener;
        StageTaskListener stageTaskListener4 = i == 0 ? stageTaskListener : stageTaskListener2;
        boolean z3 = CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY;
        StageTaskListener stageTaskListener5 = this.mCellStage;
        boolean applyTaskChanges = (z3 && stageTaskListener5.mIsActive) ? splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener3.mRootTaskInfo, stageTaskListener4.mRootTaskInfo, stageTaskListener5.mRootTaskInfo) : splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener3.mRootTaskInfo, stageTaskListener4.mRootTaskInfo);
        if (Transitions.ENABLE_SHELL_TRANSITIONS && applyTaskChanges && isSplitScreenVisible) {
            this.mSplitLayout.setDividerInteractive("handleLayoutSizeChange", false, false);
            SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
            StageCoordinator$$ExternalSyntheticLambda0 stageCoordinator$$ExternalSyntheticLambda0 = new StageCoordinator$$ExternalSyntheticLambda0(0, this);
            StageCoordinator$$ExternalSyntheticLambda0 stageCoordinator$$ExternalSyntheticLambda02 = new StageCoordinator$$ExternalSyntheticLambda0(3, this);
            SplitDecorManager splitDecorManager = stageTaskListener.mSplitDecorManager;
            SplitDecorManager splitDecorManager2 = stageTaskListener2.mSplitDecorManager;
            List list = Collections.EMPTY_LIST;
            stageCoordinator = this;
            splitScreenTransitions.startResizeTransition(windowContainerTransaction, stageCoordinator, stageCoordinator$$ExternalSyntheticLambda0, stageCoordinator$$ExternalSyntheticLambda02, splitDecorManager, splitDecorManager2);
        } else {
            stageCoordinator = this;
            stageCoordinator.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
        stageCoordinator.sendOnBoundsChanged();
        if (z2) {
            TransactionPool transactionPool = stageCoordinator.mTransactionPool;
            SurfaceControl.Transaction acquire = transactionPool.acquire();
            stageCoordinator.updateSurfaceBounds(splitLayout, acquire, false);
            stageTaskListener.onResized(acquire);
            stageTaskListener2.onResized(acquire);
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                stageTaskListener5.onResized(acquire);
            }
            acquire.apply();
            transactionPool.release(acquire);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:184:0x0240, code lost:
    
        if (r2 != false) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x04a8, code lost:
    
        if (r8.getChildCount() != 1) goto L283;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x04cf, code lost:
    
        if (r1.getChildCount() == 1) goto L293;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0211, code lost:
    
        if (r3 != 1) goto L108;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0495  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x04d7  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x04ed  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0510  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x051a  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x04fa  */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v91 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v42 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v8 */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.window.WindowContainerTransaction handleRequest(android.os.IBinder r23, android.window.TransitionRequestInfo r24) {
        /*
            Method dump skipped, instructions count: 1605
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.handleRequest(android.os.IBinder, android.window.TransitionRequestInfo):android.window.WindowContainerTransaction");
    }

    public final boolean hasSameRatioInGroupedTasks(SplitBounds splitBounds, boolean z) {
        SplitBounds splitBounds2 = this.mLastSplitStateInfo;
        boolean z2 = splitBounds2.appsStackedVertically;
        if (z2 && splitBounds2.topTaskPercent != splitBounds.topTaskPercent) {
            return false;
        }
        if (!z2 && splitBounds2.leftTaskPercent != splitBounds.leftTaskPercent) {
            return false;
        }
        if (z && z2 && splitBounds2.cellTopTaskPercent != splitBounds.cellTopTaskPercent) {
            return false;
        }
        return !z2 || splitBounds2.cellLeftTaskPercent == splitBounds.cellLeftTaskPercent;
    }

    public final boolean isApplyFoldingPolicy(boolean z) {
        return ((CoreRune.MW_SPLIT_CONTINUITY_MODE && z && this.mFoldLockSettingsObserver.isStayAwakeOnFold()) || !this.mIsFolded || this.mTopStageAfterFold == -1) ? false : true;
    }

    public final boolean isInSubDisplay() {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        return runningTaskInfo != null ? runningTaskInfo.configuration.semDisplayDeviceType == 5 : MultiWindowUtils.isInSubDisplay(this.mContext);
    }

    public final boolean isLandscape() {
        return SplitLayout.isLandscape(this.mSplitLayout.mRootBounds);
    }

    public final boolean isMultiSplitActive() {
        if (CoreRune.MW_MULTI_SPLIT) {
            return this.mCellStage.mIsActive;
        }
        return false;
    }

    public final boolean isMultiSplitScreenVisible() {
        return CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isSplitScreenVisible() && this.mCellStage.mVisible;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0026, code lost:
    
        r3 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isSameIntentRequested(android.app.TaskInfo r4, android.content.Intent r5, android.os.UserHandle r6, boolean r7) {
        /*
            r3 = this;
            r0 = 0
            if (r4 == 0) goto L48
            if (r5 == 0) goto L48
            if (r6 == 0) goto L48
            android.content.ComponentName r1 = r5.getComponent()
            if (r1 == 0) goto L48
            android.content.ComponentName r1 = r4.baseActivity
            if (r1 != 0) goto L12
            goto L48
        L12:
            boolean r1 = r4.isAliasManaged
            r2 = 1
            if (r1 == 0) goto L28
            if (r7 == 0) goto L20
            com.android.wm.shell.splitscreen.StageTaskListener r3 = r3.mCellStage
            boolean r3 = r3.mVisible
            if (r3 != 0) goto L28
            goto L26
        L20:
            boolean r3 = r3.isSplitScreenVisible()
            if (r3 != 0) goto L28
        L26:
            r3 = r2
            goto L29
        L28:
            r3 = r0
        L29:
            android.content.ComponentName r5 = r5.getComponent()
            java.lang.String r5 = r5.getPackageName()
            android.content.ComponentName r7 = r4.baseActivity
            java.lang.String r7 = r7.getPackageName()
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L48
            int r4 = r4.userId
            int r5 = r6.getIdentifier()
            if (r4 != r5) goto L48
            if (r3 != 0) goto L48
            return r2
        L48:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.isSameIntentRequested(android.app.TaskInfo, android.content.Intent, android.os.UserHandle, boolean):boolean");
    }

    public final boolean isSplitScreenVisible() {
        return this.mSideStage.mVisible && this.mMainStage.mVisible;
    }

    public final boolean isVerticalDivision() {
        return getSplitDivision() == 0;
    }

    public final void logExit(int i) {
        SplitLayout splitLayout = this.mSplitLayout;
        if (splitLayout != null) {
            this.mLogger.logExit(i, -1, 0, -1, 0, splitLayout.mIsLeftRightSplit);
            return;
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "logExit: failed to logging, splitLayout is null, exitReason=", ", Callers=");
        m.append(Debug.getCallers(5));
        Slog.e("StageCoordinator", m.toString());
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        splitscreenEventLogger.mLoggerSessionId = null;
        splitscreenEventLogger.mDragEnterPosition = -1;
        splitscreenEventLogger.mEnterSessionId = null;
        splitscreenEventLogger.mLastMainStagePosition = -1;
        splitscreenEventLogger.mLastMainStageUid = -1;
        splitscreenEventLogger.mLastSideStagePosition = -1;
        splitscreenEventLogger.mLastSideStageUid = -1;
        splitscreenEventLogger.mEnterReason = 0;
    }

    public final void logExitToStage(int i, boolean z) {
        if (this.mSplitLayout != null) {
            this.mLogger.logExit(i, z ? SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition) : -1, z ? this.mMainStage.getTopChildTaskUid() : 0, z ? -1 : this.mSideStagePosition, z ? 0 : this.mSideStage.getTopChildTaskUid(), this.mSplitLayout.mIsLeftRightSplit);
            return;
        }
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("logExitToStage: failed to logging, splitLayout is null, exitReason=", i, ", toMainStage=", z, ", Callers=");
        m.append(Debug.getCallers(5));
        Slog.e("StageCoordinator", m.toString());
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        splitscreenEventLogger.mLoggerSessionId = null;
        splitscreenEventLogger.mDragEnterPosition = -1;
        splitscreenEventLogger.mEnterSessionId = null;
        splitscreenEventLogger.mLastMainStagePosition = -1;
        splitscreenEventLogger.mLastMainStageUid = -1;
        splitscreenEventLogger.mLastSideStagePosition = -1;
        splitscreenEventLogger.mLastSideStageUid = -1;
        splitscreenEventLogger.mEnterReason = 0;
    }

    public final void maximizeSplitTask(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction) {
        int i;
        int i2 = this.mMainStage.containsToken(windowContainerToken) ? 0 : this.mSideStage.containsToken(windowContainerToken) ? 1 : (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mCellStage.containsToken(windowContainerToken)) ? 5 : -1;
        if (i2 != -1) {
            i = i2;
        } else {
            if (this.mSplitTransitions.mPendingEnter == null || this.mLastTransactionType != 2) {
                Slog.w("StageCoordinator", "maximizeSplitTask: failed, cannot find " + windowContainerToken);
                return;
            }
            Slog.d("StageCoordinator", "maximizeSplitTask: during splitTransition");
            i = 0;
        }
        if (windowContainerTransaction == null) {
            windowContainerTransaction = new WindowContainerTransaction();
        }
        WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        if (runningTaskInfo != null) {
            windowContainerTransaction2.setDoNotPip(runningTaskInfo.token);
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            prepareSplitMaximizeChangeTransition(windowContainerTransaction2, i);
        }
        prepareExitSplitScreen(i, 13, windowContainerTransaction2, true);
        this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, i, 2, false);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -7527927886439366740L, 1, Long.valueOf(transitionInfo.getDebugId()));
        }
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (iBinder2 != splitScreenTransitions.mAnimatingTransition) {
            return;
        }
        OneShotRemoteHandler oneShotRemoteHandler = splitScreenTransitions.mActiveRemoteHandler;
        if (oneShotRemoteHandler != null) {
            oneShotRemoteHandler.mergeAnimation(iBinder, transitionInfo, transaction, transaction2, iBinder2, transitionFinishCallback);
            return;
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                if (change.hasFlags(33554432) && change.getMode() == 2) {
                }
            }
            Log.w("SplitScreenTransitions", "mergeAnimation: keep current transition, new=" + transitionInfo);
            return;
        }
        for (int size = splitScreenTransitions.mAnimations.size() - 1; size >= 0; size--) {
            Animator animator = (Animator) splitScreenTransitions.mAnimations.get(size);
            ShellExecutor shellExecutor = splitScreenTransitions.mTransitions.mAnimExecutor;
            Objects.requireNonNull(animator);
            shellExecutor.execute(new SplitScreenTransitions$$ExternalSyntheticLambda2(animator, 0));
        }
    }

    public final void moveSplitToFreeform(WindowContainerToken windowContainerToken, Rect rect, boolean z) {
        Rect rect2;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        int i = 5;
        windowContainerTransaction.setWindowingMode(windowContainerToken, 5);
        windowContainerTransaction.setAlwaysOnTop(windowContainerToken, true);
        if (rect != null) {
            windowContainerTransaction.setBounds(windowContainerToken, rect);
        }
        int i2 = z ? 4 : 1;
        this.mMovingToFreeformTaskToken = windowContainerToken;
        try {
            if (this.mMainStage.containsToken(windowContainerToken)) {
                i = 0;
            } else if (this.mSideStage.containsToken(windowContainerToken)) {
                i = 1;
            } else if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !this.mCellStage.containsToken(windowContainerToken)) {
                rect2 = null;
                if (rect2 != null && !rect2.isEmpty()) {
                    windowContainerTransaction.setChangeTransitStartBounds(windowContainerToken, rect2);
                }
                windowContainerTransaction.setChangeTransitMode(windowContainerToken, i2, "split_to_freeform");
                dismissSplitTask(windowContainerToken, windowContainerTransaction, z);
                this.mMovingToFreeformTaskToken = null;
            }
            rect2 = getStageBounds(i);
            if (rect2 != null) {
                windowContainerTransaction.setChangeTransitStartBounds(windowContainerToken, rect2);
            }
            windowContainerTransaction.setChangeTransitMode(windowContainerToken, i2, "split_to_freeform");
            dismissSplitTask(windowContainerToken, windowContainerTransaction, z);
            this.mMovingToFreeformTaskToken = null;
        } catch (Throwable th) {
            this.mMovingToFreeformTaskToken = null;
            throw th;
        }
    }

    public final void notifySplitAnimationFinished() {
        Executor executor;
        if (this.mSplitInvocationListener == null || (executor = this.mSplitInvocationListenerExecutor) == null) {
            return;
        }
        executor.execute(new StageCoordinator$$ExternalSyntheticLambda6(1, this));
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x001a, code lost:
    
        if (r20 == r3) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onChildTaskStatusChanged(com.android.wm.shell.splitscreen.StageTaskListener r20, int r21, boolean r22, boolean r23) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r23
            com.android.wm.shell.splitscreen.StageTaskListener r3 = r0.mSideStage
            r4 = 1
            if (r22 == 0) goto L1d
            boolean r5 = com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER
            r6 = 0
            if (r5 == 0) goto L1a
            if (r1 != r3) goto L14
        L12:
            r6 = r4
            goto L1e
        L14:
            com.android.wm.shell.splitscreen.StageTaskListener r5 = r0.mCellStage
            if (r1 != r5) goto L1e
            r6 = 5
            goto L1e
        L1a:
            if (r1 != r3) goto L1e
            goto L12
        L1d:
            r6 = -1
        L1e:
            com.android.wm.shell.splitscreen.StageTaskListener r1 = r0.mMainStage
            com.android.wm.shell.splitscreen.SplitscreenEventLogger r5 = r0.mLogger
            if (r6 != 0) goto L5b
            int r7 = r0.mSideStagePosition
            int r7 = com.android.wm.shell.common.split.SplitScreenUtils.reverseSplitPosition(r7)
            int r8 = r1.getTopChildTaskUid()
            com.android.wm.shell.common.split.SplitLayout r9 = r0.mSplitLayout
            boolean r9 = r9.mIsLeftRightSplit
            com.android.internal.logging.InstanceId r10 = r5.mLoggerSessionId
            if (r10 != 0) goto L37
            goto L90
        L37:
            int r7 = com.android.wm.shell.splitscreen.SplitscreenEventLogger.getMainStagePositionFromSplitPosition(r7, r9)
            boolean r7 = r5.updateMainStageState(r7, r8)
            if (r7 != 0) goto L42
            goto L90
        L42:
            int r13 = r5.mLastMainStagePosition
            int r14 = r5.mLastMainStageUid
            com.android.internal.logging.InstanceId r5 = r5.mLoggerSessionId
            int r18 = r5.getId()
            r12 = 0
            r15 = 0
            r8 = 388(0x184, float:5.44E-43)
            r9 = 3
            r10 = 0
            r11 = 0
            r16 = 0
            r17 = 0
            com.android.internal.util.FrameworkStatsLog.write(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            goto L90
        L5b:
            if (r6 != r4) goto L90
            int r7 = r0.mSideStagePosition
            int r8 = r3.getTopChildTaskUid()
            com.android.wm.shell.common.split.SplitLayout r9 = r0.mSplitLayout
            boolean r9 = r9.mIsLeftRightSplit
            com.android.internal.logging.InstanceId r10 = r5.mLoggerSessionId
            if (r10 != 0) goto L6c
            goto L90
        L6c:
            int r7 = com.android.wm.shell.splitscreen.SplitscreenEventLogger.getSideStagePositionFromSplitPosition(r7, r9)
            boolean r7 = r5.updateSideStageState(r7, r8)
            if (r7 != 0) goto L77
            goto L90
        L77:
            int r15 = r5.mLastSideStagePosition
            int r7 = r5.mLastSideStageUid
            com.android.internal.logging.InstanceId r5 = r5.mLoggerSessionId
            int r18 = r5.getId()
            r12 = 0
            r13 = 0
            r8 = 388(0x184, float:5.44E-43)
            r9 = 3
            r10 = 0
            r11 = 0
            r14 = 0
            r17 = 0
            r16 = r7
            com.android.internal.util.FrameworkStatsLog.write(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
        L90:
            if (r22 == 0) goto L98
            if (r2 == 0) goto L98
            r0.updateRecentTasksSplitPair()
            goto La1
        L98:
            int r1 = r1.getChildCount()
            if (r1 != 0) goto La1
            r3.getChildCount()
        La1:
            java.util.List r1 = r0.mListeners
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            int r1 = r1.size()
            int r1 = r1 - r4
        Laa:
            if (r1 < 0) goto Lbe
            java.util.List r3 = r0.mListeners
            java.util.ArrayList r3 = (java.util.ArrayList) r3
            java.lang.Object r3 = r3.get(r1)
            com.android.wm.shell.splitscreen.SplitScreen$SplitScreenListener r3 = (com.android.wm.shell.splitscreen.SplitScreen.SplitScreenListener) r3
            r4 = r21
            r3.onTaskStageChanged(r4, r6, r2)
            int r1 = r1 + (-1)
            goto Laa
        Lbe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.onChildTaskStatusChanged(com.android.wm.shell.splitscreen.StageTaskListener, int, boolean, boolean):void");
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        if (i != 0) {
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 4807260383806698559L, 1, Long.valueOf(i));
        }
        this.mDisplayController.addDisplayChangingController(new DisplayChangeController.OnDisplayChangingListener() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
            public final void onDisplayChange(int i2, int i3, int i4, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
                StageCoordinator stageCoordinator;
                int i5;
                int i6;
                int i7;
                SemWifiDisplayStatus semGetWifiDisplayStatus;
                StageCoordinator stageCoordinator2 = StageCoordinator.this;
                if (i2 != 0) {
                    stageCoordinator2.getClass();
                    return;
                }
                if (stageCoordinator2.mMainStage.mIsActive) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -6802425196666471292L, 21, Long.valueOf(i2), Long.valueOf(i3), Long.valueOf(i4), String.valueOf(displayAreaInfo != null ? displayAreaInfo.configuration : null));
                    }
                    if (i3 != i4 && (stageCoordinator2.mSplitTransitions.mPendingEnter != null || (stageCoordinator2.isSplitScreenVisible() && stageCoordinator2.mKeyguardActive))) {
                        stageCoordinator2.mDisplayController.getDisplayLayout(stageCoordinator2.mContext.getDisplayId()).rotateTo(stageCoordinator2.mContext.getResources(), i4);
                    }
                    boolean isLandscape = stageCoordinator2.isLandscape();
                    int i8 = stageCoordinator2.mLastConfiguration.semDisplayDeviceType;
                    SplitLayout splitLayout = stageCoordinator2.mSplitLayout;
                    int i9 = splitLayout.mRotation;
                    if (i3 != -1 || i4 != -1) {
                        byte b = (((i4 - i9) + 4) % 4) % 2 != 0;
                        splitLayout.mRotation = i4;
                        Rect rect = new Rect(splitLayout.mRootBounds);
                        if (b != false) {
                            Rect rect2 = splitLayout.mRootBounds;
                            rect.set(rect2.top, rect2.left, rect2.bottom, rect2.right);
                        }
                        boolean z = splitLayout.mIsLargeScreen;
                        boolean z2 = splitLayout.mRootBounds.width() >= splitLayout.mRootBounds.height();
                        boolean isInSubDisplay = MultiWindowUtils.isInSubDisplay(splitLayout.mContext);
                        int i10 = splitLayout.mSplitDivision;
                        boolean z3 = splitLayout.mAllowLeftRightSplitInPortrait;
                        boolean isLeftRightSplit = SplitScreenUtils.isLeftRightSplit(z3, z, z2, isInSubDisplay, i10);
                        splitLayout.mTempRect.set(splitLayout.mRootBounds);
                        splitLayout.mRootBounds.set(rect);
                        boolean isVerticalDivision = splitLayout.isVerticalDivision();
                        if (((CoreRune.MW_MULTI_SPLIT_FREE_POSITION && MultiWindowUtils.isInSubDisplay(splitLayout.mContext)) || (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && splitLayout.mSplitScreenFeasibleMode == 1)) && b != false) {
                            splitLayout.mSplitDivision = !SplitLayout.isLandscape(splitLayout.mRootBounds) ? 1 : 0;
                            splitLayout.mIsLeftRightSplit = splitLayout.isVerticalDivision();
                        }
                        boolean z4 = CoreRune.MW_PARALLEL_MULTI_SPLIT;
                        if (z4 && splitLayout.mParallelMultiSplit && b != false) {
                            splitLayout.mSplitDivision = !SplitLayout.isLandscape(splitLayout.mRootBounds) ? 1 : 0;
                        }
                        byte b2 = b;
                        splitLayout.mIsLeftRightSplit = SplitScreenUtils.isLeftRightSplit(z3, splitLayout.mIsLargeScreen, splitLayout.mRootBounds.width() >= splitLayout.mRootBounds.height(), MultiWindowUtils.isInSubDisplay(splitLayout.mContext), splitLayout.mSplitDivision);
                        splitLayout.updateLayouts();
                        splitLayout.initDividerPosition(splitLayout.mTempRect, isLeftRightSplit, isVerticalDivision);
                        if (z4 && splitLayout.mParallelMultiSplit && b2 != false && (stageCoordinator = splitLayout.mStageCoordinator) != null) {
                            splitLayout.setDivideRatio(stageCoordinator.calculateSplitRatioForParallelMultiSplit(stageCoordinator.getCurrentMultiSplitLayoutInfo()), true, true);
                            splitLayout.setCellDividerRatio(0.5f, splitLayout.mCellStageWindowConfigPosition, true, false);
                        }
                    }
                    if (displayAreaInfo != null) {
                        Configuration configuration = displayAreaInfo.configuration;
                        boolean z5 = CoreRune.MW_MULTI_SPLIT_FREE_POSITION;
                        if (z5 && stageCoordinator2.isSplitScreenVisible()) {
                            int i11 = stageCoordinator2.mLastConfiguration.semDisplayDeviceType;
                            int i12 = configuration.semDisplayDeviceType;
                            if (i11 != i12 && i12 == 5 && !stageCoordinator2.mFoldLockSettingsObserver.isStayAwakeOnFold() && ((semGetWifiDisplayStatus = ((DisplayManager) stageCoordinator2.mContext.getSystemService("display")).semGetWifiDisplayStatus()) == null || semGetWifiDisplayStatus.getActiveDisplayState() != 2 || semGetWifiDisplayStatus.getConnectedState() != 0)) {
                                Slog.d("StageCoordinator", "onDisplayChange : defer updateConfig in splitVisible");
                                if (z5 && (i6 = configuration.semDisplayDeviceType) == 0 && i6 != stageCoordinator2.mLastConfiguration.semDisplayDeviceType && !stageCoordinator2.isSplitScreenVisible() && (i7 = stageCoordinator2.mLastMainSplitDivision) != stageCoordinator2.mSplitDivision && stageCoordinator2.setSplitDivision(i7, false, true)) {
                                    Slog.d("StageCoordinator", "Restore main Split Division=" + stageCoordinator2.mLastMainSplitDivision);
                                    stageCoordinator2.mLastMainSplitDivision = -1;
                                }
                                stageCoordinator2.mLastConfiguration.updateFrom(configuration);
                            }
                        }
                        stageCoordinator2.mSplitLayout.updateConfiguration(displayAreaInfo.configuration);
                        if (z5) {
                            Slog.d("StageCoordinator", "Restore main Split Division=" + stageCoordinator2.mLastMainSplitDivision);
                            stageCoordinator2.mLastMainSplitDivision = -1;
                        }
                        stageCoordinator2.mLastConfiguration.updateFrom(configuration);
                    }
                    if ((!CoreRune.MW_MULTI_SPLIT || MultiWindowUtils.isInSubDisplay(stageCoordinator2.mContext) || (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && stageCoordinator2.mSplitLayout.mSplitScreenFeasibleMode == 1)) && i9 != i4 && ((i4 == 3 || i9 == 3) && !(CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator2.isMultiSplitActive()))) {
                        SplitLayout splitLayout2 = stageCoordinator2.mSplitLayout;
                        splitLayout2.setDivideRatio(1.0f - ((splitLayout2.mDividerPosition + splitLayout2.mDividerSize) / (SplitLayout.isLandscape(splitLayout2.mRootBounds) ? splitLayout2.mRootBounds.width() : splitLayout2.mRootBounds.height())), false, false);
                        stageCoordinator2.setSideStagePosition$1(windowContainerTransaction, SplitScreenUtils.reverseSplitPosition(stageCoordinator2.mSideStagePosition));
                    } else {
                        stageCoordinator2.updateWindowBounds(stageCoordinator2.mSplitLayout, windowContainerTransaction, false);
                    }
                    if (isLandscape != stageCoordinator2.isLandscape()) {
                        if (((CoreRune.MW_MULTI_SPLIT_FREE_POSITION && MultiWindowUtils.isInSubDisplay(stageCoordinator2.mContext)) || (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && stageCoordinator2.mSplitLayout.mSplitScreenFeasibleMode == 1)) && (!CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY || !stageCoordinator2.isApplyFoldingPolicy(false) || !stageCoordinator2.mFoldLockSettingsObserver.isSleepOnFold())) {
                            stageCoordinator2.setSplitDivision(!stageCoordinator2.isLandscape() ? 1 : 0, false, false);
                        }
                        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && stageCoordinator2.mSplitLayout.mParallelMultiSplit) {
                            stageCoordinator2.setSplitDivision(!stageCoordinator2.isLandscape() ? 1 : 0, false, false);
                        }
                        stageCoordinator2.updateStagePositionIfNeeded(windowContainerTransaction);
                    } else if (CoreRune.MW_SPLIT_CONTINUITY_MODE && displayAreaInfo != null && i8 != (i5 = displayAreaInfo.configuration.semDisplayDeviceType) && i5 == 5 && stageCoordinator2.mFoldLockSettingsObserver.isStayAwakeOnFold()) {
                        stageCoordinator2.updateStagePositionIfNeeded(windowContainerTransaction);
                    }
                    stageCoordinator2.sendOnBoundsChanged();
                }
            }
        });
    }

    public void onFoldedStateChanged(boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -665186314396288994L, 3, Boolean.valueOf(z));
        }
        boolean z2 = CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY;
        if (z2) {
            this.mIsFolded = z;
            this.mTopStageAfterFold = -1;
        }
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && z && !isInSubDisplay()) {
            Slog.d("StageCoordinator", "Save main Split Division=" + this.mSplitDivision);
            this.mLastMainSplitDivision = this.mSplitDivision;
        }
        if (!z) {
            this.mBreakOnNextWake = false;
            return;
        }
        if (z2) {
            recordLastActiveStage(true);
        } else {
            recordLastActiveStage(false);
        }
        this.mBreakOnNextWake = willSleepOnFold();
    }

    public final void onFreeformToSplitRequested(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, int i, boolean z2, Rect rect, boolean z3, String str) {
        Bundle resolveStartStage;
        int i2;
        Bundle resolveStartCellStage;
        int i3 = i;
        int i4 = runningTaskInfo.taskId;
        WindowContainerToken windowContainerToken = runningTaskInfo.token;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setChangeTransitMode(windowContainerToken, 1, str);
        if (rect != null) {
            windowContainerTransaction.setChangeTransitStartBounds(windowContainerToken, rect);
        }
        if (z) {
            int i5 = i3 & 64;
            int i6 = (i3 == 0 || !(i5 != 0 || (i3 & 32) != 0)) ? 1 : 0;
            if ((i3 & 16) == 0 && i5 == 0) {
                r6 = 0;
            }
            startTaskAndIntent(i4, MultiWindowUtils.getEdgeAllAppsActivityIntent(runningTaskInfo.baseIntent.getComponent(), runningTaskInfo.userId, i4), i6, r6, windowContainerTransaction);
            return;
        }
        int i7 = ((i3 != 0 || z2) && (i3 == 16 || i3 == 8)) ? 0 : 1;
        boolean z4 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        StageTaskListener stageTaskListener = this.mMainStage;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        if (!z4 || MultiWindowUtils.isInSubDisplay(this.mContext) || !isSplitScreenVisible()) {
            int i8 = i7;
            if (i3 != 0) {
                resolveStartStage = resolveStartStage(-1, i8, null, null, (i3 == 8 || i3 == 32) ? 0 : 1);
            } else {
                resolveStartStage = resolveStartStage(-1, i8, null, null, -1);
            }
            windowContainerTransaction.startTask(i4, resolveStartStage);
            if (isSplitScreenVisible()) {
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                return;
            }
            int i9 = stageTaskListener.mIsActive ? 1005 : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
            prepareEnterSplitScreen(windowContainerTransaction, null, i8, false);
            this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, i9, false, 1);
            return;
        }
        if (z2) {
            int splitDivision = getSplitDivision();
            i2 = getSideStageWinConfigPosition();
            if (splitDivision == 1) {
                if (i3 == 8) {
                    i2 |= 32;
                } else if (i3 == 32) {
                    i2 |= 8;
                }
            } else if (i3 == 16) {
                i2 |= 64;
            } else if (i3 == 64) {
                i2 |= 16;
            }
            setSideStagePosition(i7, splitDivision == 1 ? 0 : 1, windowContainerTransaction, false);
            if (z3) {
                WindowContainerToken windowContainerToken2 = stageTaskListener.getTopRunningTaskInfo().token;
                WindowContainerToken windowContainerToken3 = this.mSideStage.getTopRunningTaskInfo().token;
                windowContainerTransaction.setChangeTransitMode(windowContainerToken2, 4, "natural_swtiching");
                windowContainerTransaction.setChangeTransitMode(windowContainerToken3, 4, "natural_swtiching");
            }
            swapStageTasks(1, 5, windowContainerTransaction);
            resolveStartCellStage = resolveStartStage(1, i7, null, null, -1);
        } else {
            if (i3 == 0) {
                i3 = StageUtils.getMultiSplitLaunchPosition(this.mCellStageWindowConfigPosition, isVerticalDivision());
            }
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitLayout.mParallelMultiSplit && isMultiSplitScreenVisible()) {
                i3 = this.mCellStageWindowConfigPosition;
            }
            i2 = i3;
            resolveStartCellStage = resolveStartCellStage(-1, i3, null, null);
        }
        windowContainerTransaction.startTask(i4, resolveStartCellStage);
        if (isMultiSplitScreenVisible()) {
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            return;
        }
        this.mSplitLayout.setCellDividerRatio(0.5f, i2, true, false);
        prepareEnterMultiSplitScreen(windowContainerTransaction, i2);
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && !z3) {
            applyCellHostResizeTransition(windowContainerTransaction);
        }
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, VolteConstants.ErrorCode.CALL_SESSION_ABORT, false, 1);
    }

    public final void onLayoutPositionChanging(SplitLayout splitLayout) {
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction acquire = transactionPool.acquire();
        acquire.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        updateSurfaceBounds(splitLayout, acquire, false);
        acquire.apply();
        transactionPool.release(acquire);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0091, code lost:
    
        if (r0 != 15) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onLayoutSizeChanged(com.android.wm.shell.common.split.SplitLayout r18, android.window.WindowContainerTransaction r19) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.onLayoutSizeChanged(com.android.wm.shell.common.split.SplitLayout, android.window.WindowContainerTransaction):void");
    }

    public final void onLayoutSizeChanging(SplitLayout splitLayout, int i, int i2, boolean z) {
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction acquire = transactionPool.acquire();
        acquire.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        updateSurfaceBounds(splitLayout, acquire, z);
        Rect rect = this.mTempRect1;
        if (this.mSideStagePosition == 0) {
            rect.set(this.mSplitLayout.getBottomRightBounds());
        } else {
            rect.set(this.mSplitLayout.getTopLeftBounds());
        }
        Rect rect2 = this.mTempRect2;
        if (this.mSideStagePosition == 0) {
            rect2.set(this.mSplitLayout.getTopLeftBounds());
        } else {
            rect2.set(this.mSplitLayout.getBottomRightBounds());
        }
        this.mSplitLayout.getRootBounds();
        this.mMainStage.getClass();
        this.mSideStage.getClass();
        acquire.apply();
        transactionPool.release(acquire);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onNoLongerSupportMultiWindow(StageTaskListener stageTaskListener, ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 8876191938084552299L, 0, String.valueOf(runningTaskInfo));
        }
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (stageTaskListener2.mIsActive) {
            int i = stageTaskListener2 == stageTaskListener ? 1 : 0;
            boolean isSplitScreenVisible = isSplitScreenVisible();
            int i2 = isSplitScreenVisible ? ~i ? 1 : 0 : -1;
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                int i3 = i;
                if (!MultiWindowCoreState.MW_ENABLED) {
                    i3 = stageTaskListener2.isFocused();
                }
                prepareSplitDismissChangeTransition(windowContainerTransaction, i3, null);
            }
            prepareExitSplitScreen(i2, 1, windowContainerTransaction, true);
            clearSplitPairedInRecents(1);
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction, this, i2, 1, false);
            if (runningTaskInfo.baseActivity == null) {
                Log.w("StageCoordinator", SplitScreenUtils.splitFailureMessage("onNoLongerSupportMultiWindow", "taskInfo " + runningTaskInfo + " does not support splitscreen, or is a controlled activity type"));
            } else {
                Log.w("StageCoordinator", SplitScreenUtils.splitFailureMessage("onNoLongerSupportMultiWindow", "app package " + runningTaskInfo.baseIntent.getComponent() + " does not support splitscreen, or is a controlled activity type"));
            }
            if (isSplitScreenVisible) {
                this.mSplitUnsupportedToast.setText(R.string.dock_non_resizeble_failed_to_dock_text);
                this.mSplitUnsupportedToast.show();
                notifySplitAnimationFinished();
            }
        }
    }

    public final void onRecentsInSplitAnimationCanceled() {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -6931743103654743905L, 0, null);
        }
        this.mPausingTasks.clear();
        setSplitsVisible(false);
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            this.mIsRecentsInSplitAnimating = false;
        }
        this.mSplitBackgroundController.onRecentsInSplitAnimationFinish(false);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
    }

    public final void onRecentsInSplitAnimationFinishing(boolean z, WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -7098334258329928429L, 3, Boolean.valueOf(z));
        }
        this.mPausingTasks.clear();
        SplitBackgroundController splitBackgroundController = this.mSplitBackgroundController;
        if (!z) {
            setSplitsVisible$1(false, true);
            windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
            splitBackgroundController.onRecentsInSplitAnimationFinish(false);
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                this.mIsRecentsInSplitAnimating = false;
                return;
            }
            return;
        }
        updateSurfaceBounds(this.mSplitLayout, transaction, false);
        if (this.mSplitLayout.getDividerLeash() == null) {
            Log.d("StageCoordinator", "divider leash is null");
            this.mSplitLayout.update(null, true);
        }
        transaction.reparent(this.mSplitLayout.getDividerLeash(), this.mRootTaskLeash);
        setDividerVisibility(transaction, true);
        splitBackgroundController.onRecentsInSplitAnimationFinish(true);
    }

    @Override // com.android.wm.shell.splitscreen.StageTaskListener.StageListenerCallbacks
    public void onRootTaskAppeared() {
        boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0];
        StageTaskListener stageTaskListener = this.mMainStage;
        StageTaskListener stageTaskListener2 = this.mSideStage;
        if (z) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3196764427582911387L, 60, String.valueOf(this.mRootTaskInfo), Boolean.valueOf(stageTaskListener.mHasRootTask), Boolean.valueOf(stageTaskListener2.mHasRootTask));
        }
        boolean z2 = stageTaskListener.mHasRootTask;
        StageTaskListener stageTaskListener3 = this.mCellStage;
        boolean z3 = (z2 && stageTaskListener2.mHasRootTask && (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || stageTaskListener3.mHasRootTask)) ? false : true;
        if (this.mRootTaskInfo == null || z3) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.reparent(stageTaskListener.mRootTaskInfo.token, this.mRootTaskInfo.token, true);
        windowContainerTransaction.reparent(stageTaskListener2.mRootTaskInfo.token, this.mRootTaskInfo.token, true);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            windowContainerTransaction.reparent(stageTaskListener3.mRootTaskInfo.token, this.mRootTaskInfo.token, true);
        }
        setRootForceTranslucent(windowContainerTransaction, true);
        windowContainerTransaction.setAdjacentRootSet(new WindowContainerToken[]{stageTaskListener.mRootTaskInfo.token, stageTaskListener2.mRootTaskInfo.token});
        this.mTempRect1.set(this.mSplitLayout.mInvisibleBounds);
        windowContainerTransaction.setBounds(stageTaskListener2.mRootTaskInfo.token, this.mTempRect1);
        SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
        syncTransactionQueue.queue(windowContainerTransaction);
        syncTransactionQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda2(this, 0));
        WindowContainerToken windowContainerToken = stageTaskListener2.mRootTaskInfo.token;
        LaunchAdjacentController launchAdjacentController = this.mLaunchAdjacentController;
        launchAdjacentController.getClass();
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_TASK_ORG;
        ProtoLog.d(shellProtoLogGroup, "set new launch adjacent flag root container", new Object[0]);
        launchAdjacentController.container = windowContainerToken;
        if (launchAdjacentController.launchAdjacentEnabled) {
            ProtoLog.v(shellProtoLogGroup, "enable launch adjacent flag root container", new Object[0]);
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            windowContainerTransaction2.setLaunchAdjacentFlagRoot(windowContainerToken);
            launchAdjacentController.syncQueue.queue(windowContainerTransaction2);
        }
    }

    public final void onRootTaskVanished() {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -8718789868823431286L, 0, null);
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        LaunchAdjacentController launchAdjacentController = this.mLaunchAdjacentController;
        launchAdjacentController.getClass();
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_TASK_ORG;
        ProtoLog.d(shellProtoLogGroup, "clear launch adjacent flag root container", new Object[0]);
        WindowContainerToken windowContainerToken = launchAdjacentController.container;
        if (windowContainerToken != null) {
            ProtoLog.v(shellProtoLogGroup, "disable launch adjacent flag root container", new Object[0]);
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            windowContainerTransaction2.clearLaunchAdjacentFlagRoot(windowContainerToken);
            launchAdjacentController.syncQueue.queue(windowContainerTransaction2);
            launchAdjacentController.container = null;
        }
        applyExitSplitScreen(null, windowContainerTransaction, 6);
        this.mDisplayInsetsController.removeInsetsChangedListener(this.mDisplayId, this.mSplitLayout);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onSnappedToDismiss(int i, boolean z, boolean z2) {
        if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
            CoreSaLogger.logForAdvanced(SystemUIAnalytics.EID_OPEN_NOTIFICATION_LIST, "Move divider");
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -5678120486275894442L, 3, Boolean.valueOf(z), String.valueOf(SplitScreenController.exitReasonToString(i)));
        }
        r0 = true;
        boolean z3 = true;
        int i2 = (!z ? this.mSideStagePosition == 0 : this.mSideStagePosition == 1) ? 0 : 1;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        StageTaskListener stageTaskListener3 = i2 != 0 ? stageTaskListener2 : stageTaskListener;
        if (!CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING || !this.mDividerResizeController.mIsResizing || !isMultiSplitActive()) {
            int i3 = i2 ^ 1;
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (z2) {
                prepareSplitDismissChangeTransition(windowContainerTransaction, i2, null, false);
            }
            windowContainerTransaction.addAdditionalInfo(2);
            windowContainerTransaction.setBounds(stageTaskListener3.mRootTaskInfo.token, (Rect) null);
            windowContainerTransaction.setAppBounds(stageTaskListener3.mRootTaskInfo.token, (Rect) null);
            windowContainerTransaction.setSmallestScreenWidthDp(stageTaskListener3.mRootTaskInfo.token, 0);
            prepareExitSplitScreen(i3, 4, windowContainerTransaction, true);
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
            if (runningTaskInfo != null) {
                windowContainerTransaction.setDoNotPip(runningTaskInfo.token);
            }
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction, this, i3, 4, false);
            return;
        }
        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mRootTaskInfo;
        if (runningTaskInfo2 != null) {
            windowContainerTransaction2.setDoNotPip(runningTaskInfo2.token);
        }
        DividerResizeController dividerResizeController = this.mDividerResizeController;
        DividerView dividerView = dividerResizeController.mDividerView;
        if ((dividerView != null && dividerView.mIsCellDivider) == true) {
            int cellHostStageType = getCellHostStageType();
            SplitLayout splitLayout = this.mSplitLayout;
            int cellSide = CellUtil.getCellSide(splitLayout.mCellStageWindowConfigPosition, splitLayout.isVerticalDivision(), splitLayout.mParallelMultiSplit);
            int stageWinConfigPositionByType = ((cellSide == 1 || cellSide == 3) ? 40 : 80) & getStageWinConfigPositionByType(cellHostStageType);
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                SplitLayout splitLayout2 = this.mSplitLayout;
                if (splitLayout2.mParallelMultiSplit) {
                    int stageWinConfigPositionByType2 = getStageWinConfigPositionByType(cellHostStageType);
                    stageWinConfigPositionByType = splitLayout2.isVerticalDivision() ? (stageWinConfigPositionByType2 & 16) != 0 ? 8 : 32 : (stageWinConfigPositionByType2 & 8) != 0 ? 16 : 64;
                }
            }
            if (!z ? (stageWinConfigPositionByType & 24) == 0 : (stageWinConfigPositionByType & 96) == 0) {
                z3 = false;
            }
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(cellHostStageType, "onSnappedToDismissMultiSplit: cell divider action, hostStageType=", ", hostPos=");
            m.append(WindowConfiguration.stagePositionToString(stageWinConfigPositionByType));
            m.append(", dismissToHostStage=");
            m.append(z3);
            Slog.d("StageCoordinator", m.toString());
            reparentCellToMainOrSide(windowContainerTransaction2, this.mCellStage.mHost, z3);
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, cellHostStageType, 2, true);
            return;
        }
        int i4 = dividerResizeController.mHalfSplitStageType;
        if (i4 == 0) {
            stageTaskListener = stageTaskListener2;
        }
        int stageWinConfigPositionByType3 = getStageWinConfigPositionByType(i4);
        boolean z4 = !z ? (stageWinConfigPositionByType3 & 24) == 0 : (stageWinConfigPositionByType3 & 96) == 0;
        StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "onSnappedToDismissMultiSplit: halfStageType=", ", halfPos=");
        m2.append(WindowConfiguration.stagePositionToString(stageWinConfigPositionByType3));
        m2.append(", dismissToHalfStage=");
        m2.append(z4);
        Slog.d("StageCoordinator", m2.toString());
        if (z4) {
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitLayout.mParallelMultiSplit) {
                dismissMainOrSideWhenParallelMultiSplit(windowContainerTransaction2, i4);
            } else {
                reparentCellToMainOrSide(windowContainerTransaction2, stageTaskListener, true);
            }
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, i4, 2, true);
            return;
        }
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitLayout.mParallelMultiSplit) {
            dismissMainOrSideWhenParallelMultiSplit(windowContainerTransaction2, i4 != 0 ? 0 : 1);
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, i4, 2, true);
        } else {
            prepareExitSplitScreen(i4, 4, windowContainerTransaction2, true);
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, i4, 2, false);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        if (this.mRootTaskInfo != null || runningTaskInfo.hasParentTask()) {
            throw new IllegalArgumentException(this + "\n Unknown task appeared: " + runningTaskInfo);
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 141305615486892765L, 0, String.valueOf(runningTaskInfo));
        }
        this.mRootTaskInfo = runningTaskInfo;
        this.mRootTaskLeash = surfaceControl;
        SplitBackgroundController splitBackgroundController = this.mSplitBackgroundController;
        if (splitBackgroundController.mIsAttached) {
            Slog.e("SplitBackgroundController", "attachTo: new root coming.");
            splitBackgroundController.detach();
        }
        SurfaceControl build = new SurfaceControl.Builder(splitBackgroundController.mSurfaceSession).setName("Split Background Layer").setHidden(true).setColorLayer().setCallsite("SplitBackgroundController.onDisplayAreaAppeared").build();
        splitBackgroundController.mBackgroundColorLayer = build;
        SplitBackgroundController.SurfaceDelegate surfaceDelegate = splitBackgroundController.mSurfaceDelegate;
        surfaceDelegate.mSurfaceControl = build;
        surfaceDelegate.setCrop(splitBackgroundController.getDisplayBounds());
        TransactionPool transactionPool = splitBackgroundController.mTransactionPool;
        SurfaceControl.Transaction acquire = transactionPool.acquire();
        acquire.setLayer(splitBackgroundController.mBackgroundColorLayer, -1);
        acquire.reparent(splitBackgroundController.mBackgroundColorLayer, surfaceControl);
        acquire.apply();
        transactionPool.release(acquire);
        splitBackgroundController.mIsAttached = true;
        if (this.mSplitLayout == null) {
            SplitLayout splitLayout = new SplitLayout("StageCoordinatorSplitDivider", this.mContext, this.mRootTaskInfo.configuration, this, this.mParentContainerCallbacks, this.mDisplayController, this.mDisplayImeController, this.mTaskOrganizer, 3, this.mSplitState, this.mMainHandler, this.mDesktopState, this.mSplitDivision);
            this.mSplitLayout = splitLayout;
            this.mDisplayInsetsController.addInsetsChangedListener(this.mDisplayId, splitLayout);
            SplitLayout splitLayout2 = this.mSplitLayout;
            splitLayout2.mStageCoordinator = this;
            DividerResizeController dividerResizeController = this.mDividerResizeController;
            splitLayout2.mSplitWindowManager.mDividerResizeController = dividerResizeController;
            if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
                splitLayout2.mCellSplitWindowManager.mDividerResizeController = dividerResizeController;
            }
        }
        onRootTaskAppeared();
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        SplitLayout splitLayout;
        int i;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mRootTaskInfo;
        if (runningTaskInfo2 == null || runningTaskInfo2.taskId != runningTaskInfo.taskId) {
            throw new IllegalArgumentException(this + "\n Unknown task info changed: " + runningTaskInfo);
        }
        SplitLayout splitLayout2 = this.mSplitLayout;
        boolean z = (splitLayout2 == null || splitLayout2.getRootBounds().equals(runningTaskInfo.getConfiguration().windowConfiguration.getBounds())) ? false : true;
        boolean z2 = !this.mIsFolded ? runningTaskInfo.configuration.semDisplayDeviceType != 5 : runningTaskInfo.configuration.semDisplayDeviceType != 0;
        boolean z3 = CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY;
        if (z3 && z && z2 && isSplitScreenVisible()) {
            Slog.d("StageCoordinator", "onTaskInfoChanged ignore - device type is differents folded state.");
            return;
        }
        this.mRootTaskInfo = runningTaskInfo;
        if (z3 && isApplyFoldingPolicy(true)) {
            this.mTmpConfigAfterFoldDismiss = runningTaskInfo.configuration;
            return;
        }
        boolean z4 = CoreRune.MW_MULTI_SPLIT_FREE_POSITION;
        StageTaskListener stageTaskListener = this.mMainStage;
        if ((!z4 || (i = runningTaskInfo.configuration.semDisplayDeviceType) != 0 || i == this.mLastConfiguration.semDisplayDeviceType || !stageTaskListener.mIsActive || isSplitScreenVisible() || this.mLastMainSplitDivision == this.mSplitDivision) && (splitLayout = this.mSplitLayout) != null && splitLayout.updateConfiguration(this.mRootTaskInfo.configuration) && stageTaskListener.mIsActive) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 808996695524260386L, 1, Long.valueOf(runningTaskInfo.taskId));
            }
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                this.mSplitTransitions.cancelDividerFadeAnimation();
            }
            this.mSplitLayout.update(null, false);
            if (runningTaskInfo.hasConfigChanged) {
                onLayoutSizeChanged(this.mSplitLayout, null);
            }
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 6301854730259805020L, 0, String.valueOf(runningTaskInfo));
        }
        if (this.mRootTaskInfo == null) {
            throw new IllegalArgumentException(this + "\n Unknown task vanished: " + runningTaskInfo);
        }
        onRootTaskVanished();
        SplitLayout splitLayout = this.mSplitLayout;
        if (splitLayout != null) {
            splitLayout.release(null);
            this.mSplitLayout = null;
        }
        this.mRootTaskInfo = null;
        this.mRootTaskLeash = null;
        this.mIsRootTranslucent = false;
        this.mSplitBackgroundController.detach();
    }

    public final void onTransitionAnimationComplete() {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3546642264633455342L, 0, null);
        }
        if (!this.mMainStage.mIsActive && !this.mIsExiting) {
            this.mSplitLayout.release(null);
            if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
                this.mTopStageAfterFold = -1;
            }
        }
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && !this.mCellStage.mIsActive && !this.mIsExiting) {
            this.mSplitLayout.releaseCellDivider(null);
        }
        this.mLastTransactionType = 0;
        DividerResizeController dividerResizeController = this.mDividerResizeController;
        if (dividerResizeController != null) {
            Log.d("DividerResizeController", "onSyncAppsReady: SyncId=" + dividerResizeController.mSyncAppsId);
            dividerResizeController.stopWaitingForSyncAppsCallback("sync_apps_ready");
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            this.mIsRecentsInSplitAnimating = false;
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 5115115808305329563L, 0, null);
        }
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (splitScreenTransitions.isPendingEnter(iBinder)) {
            StageCoordinator stageCoordinator = splitScreenTransitions.mStageCoordinator;
            if (!z) {
                stageCoordinator.finishEnterSplitScreen(transaction, false);
            }
            SplitScreenTransitions.TransitionConsumedCallback transitionConsumedCallback = splitScreenTransitions.mPendingEnter.mConsumedCallback;
            if (transitionConsumedCallback != null) {
                transitionConsumedCallback.onConsumed();
            }
            splitScreenTransitions.mPendingEnter = null;
            stageCoordinator.notifySplitAnimationFinished();
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -6820546117650467136L, 0, null);
            }
        } else if (splitScreenTransitions.isPendingDismiss(iBinder)) {
            SplitScreenTransitions.TransitionConsumedCallback transitionConsumedCallback2 = splitScreenTransitions.mPendingDismiss.mConsumedCallback;
            if (transitionConsumedCallback2 != null) {
                transitionConsumedCallback2.onConsumed();
            }
            splitScreenTransitions.mPendingDismiss = null;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 5648275469297727409L, 0, null);
            }
        } else if (splitScreenTransitions.isPendingResize(iBinder)) {
            SplitScreenTransitions.TransitionConsumedCallback transitionConsumedCallback3 = splitScreenTransitions.mPendingResize.mConsumedCallback;
            if (transitionConsumedCallback3 != null) {
                transitionConsumedCallback3.onConsumed();
            }
            splitScreenTransitions.mPendingResize = null;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -555665995816094727L, 0, null);
            }
        } else {
            SplitScreenTransitions.TransitSession transitSession = splitScreenTransitions.mPendingRemotePassthrough;
            if (transitSession != null && transitSession.mTransition == iBinder) {
                SplitScreenTransitions.TransitionConsumedCallback transitionConsumedCallback4 = transitSession.mConsumedCallback;
                if (transitionConsumedCallback4 != null) {
                    transitionConsumedCallback4.onConsumed();
                }
                splitScreenTransitions.mPendingRemotePassthrough.mRemoteHandler.onTransitionConsumed(iBinder, z, transaction);
                splitScreenTransitions.mPendingRemotePassthrough = null;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3690820204750712967L, 0, null);
                }
            }
        }
        OneShotRemoteHandler oneShotRemoteHandler = splitScreenTransitions.mActiveRemoteHandler;
        if (oneShotRemoteHandler != null) {
            oneShotRemoteHandler.onTransitionConsumed(iBinder, z, transaction);
        }
    }

    public final void postDividerPanelAutoOpenIfNeeded() {
        SplitLayout splitLayout = this.mSplitLayout;
        if (splitLayout == null) {
            return;
        }
        SplitWindowManager splitWindowManager = splitLayout.mSplitWindowManager;
        if (splitWindowManager.mDividerView != null && splitWindowManager.mDividerVisible && !splitWindowManager.mIsPendingFirstAutoOpenDividerPanel && splitWindowManager.mIsFirstAutoOpenDividerPanel && splitWindowManager.mDividerPanel.isSupportPanelOpenPolicy()) {
            splitWindowManager.mIsPendingFirstAutoOpenDividerPanel = true;
            Slog.d("SplitWindowManager", "Try to run DividerPanel first auto open");
            splitWindowManager.mDividerView.postDelayed(splitWindowManager.mDividerPanelAutoOpen, 500L);
        }
    }

    public final void prepareActiveSplit(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, int i, boolean z, float f) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2300292853844685151L, 13, Long.valueOf(runningTaskInfo != null ? runningTaskInfo.taskId : -1L), Boolean.valueOf(isSplitScreenVisible()));
        }
        setSplitsVisible(false);
        if (runningTaskInfo != null) {
            setSideStagePosition$1(windowContainerTransaction, i);
            StageTaskListener stageTaskListener = this.mSideStage;
            stageTaskListener.getClass();
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 5914549992674768572L, 1, Long.valueOf(runningTaskInfo.taskId));
            }
            windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0).setBounds(runningTaskInfo.token, (Rect) null);
            windowContainerTransaction.reparent(runningTaskInfo.token, stageTaskListener.mRootTaskInfo.token, true);
        }
        this.mMainStage.activate(windowContainerTransaction, true);
        prepareSplitLayout(windowContainerTransaction, z, f);
    }

    public void prepareAndStartDismissTransition(int i, int i2, WindowContainerTransaction windowContainerTransaction, boolean z, boolean z2) {
        StageTaskListener stageTaskListenerByStageType = getStageTaskListenerByStageType(i);
        if (windowContainerTransaction == null) {
            windowContainerTransaction = new WindowContainerTransaction();
        }
        WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
        if (z2) {
            Log.d("StageCoordinator", "prepareAndStartDismissTransition. avoidReady.");
            windowContainerTransaction2.setAvoidReady();
        }
        prepareSplitDismissChangeTransition(windowContainerTransaction2, i2, null, z);
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        if (runningTaskInfo != null) {
            windowContainerTransaction2.setDoNotPip(runningTaskInfo.token);
        }
        if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !isMultiSplitActive()) {
            prepareExitSplitScreen(i, 15, windowContainerTransaction2, true);
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, i, 15, false);
        } else {
            if (i2 == 5) {
                prepareExitMultiSplitScreen(windowContainerTransaction2, false);
            } else {
                reparentCellToMainOrSide(windowContainerTransaction2, stageTaskListenerByStageType, true);
            }
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, i, 15, true);
        }
    }

    public final void prepareBringSplit(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, int i, boolean z, float f) {
        StageCoordinator stageCoordinator;
        WindowContainerTransaction windowContainerTransaction2;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 4010958692492917319L, 13, Long.valueOf(runningTaskInfo != null ? runningTaskInfo.taskId : -1L), Boolean.valueOf(isSplitScreenVisible()));
        }
        if (runningTaskInfo != null) {
            stageCoordinator = this;
            windowContainerTransaction2 = windowContainerTransaction;
            windowContainerTransaction2.startTask(runningTaskInfo.taskId, stageCoordinator.resolveStartStage(-1, i, null, windowContainerTransaction, -1));
        } else {
            stageCoordinator = this;
            windowContainerTransaction2 = windowContainerTransaction;
        }
        if (stageCoordinator.mAppPairStarted) {
            Slog.d("StageCoordinator", "When the App Pair is starting, it does not reparent on the mainStage.");
            stageCoordinator.prepareSplitLayout(windowContainerTransaction2, z, 0.0f);
            return;
        }
        if (stageCoordinator.isSplitScreenVisible()) {
            return;
        }
        boolean z2 = stageCoordinator.mSkipEvictingMainStageChildren;
        StageTaskListener stageTaskListener = stageCoordinator.mMainStage;
        if (!z2) {
            stageTaskListener.evictAllChildren(windowContainerTransaction2);
        }
        windowContainerTransaction2.reparentTasks((WindowContainerToken) null, stageTaskListener.mRootTaskInfo.token, SplitScreenConstants.CONTROLLED_WINDOWING_MODES, SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, true, true);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            StageTaskListener stageTaskListener2 = stageCoordinator.mCellStage;
            if (stageTaskListener2.hasChild()) {
                stageTaskListener2.evictAllChildren(windowContainerTransaction2);
            }
            if (stageTaskListener2.mIsActive) {
                stageCoordinator.prepareExitMultiSplitScreen(windowContainerTransaction2, false);
            }
        }
        stageCoordinator.prepareSplitLayout(windowContainerTransaction2, z, f);
    }

    public final void prepareDismissAnimation(int i, int i2, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, boolean z) {
        int i3 = 0;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1807079196441233509L, 5, Long.valueOf(transitionInfo.getDebugId()), Long.valueOf(i), String.valueOf(SplitScreenController.exitReasonToString(i2)));
        }
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (i == -1) {
            if (stageTaskListener2.getChildCount() != 0) {
                StringBuilder sb = new StringBuilder();
                while (i3 < stageTaskListener2.getChildCount()) {
                    sb.append(i3 != 0 ? ", " : "");
                    sb.append(stageTaskListener2.mChildrenTaskInfo.keyAt(i3));
                    i3++;
                }
                Log.w("StageCoordinator", "Expected onTaskVanished on " + stageTaskListener2 + " to have been called with [" + sb.toString() + "] before startAnimation().");
            }
            if (stageTaskListener.getChildCount() != 0) {
                StringBuilder sb2 = new StringBuilder();
                int i4 = 0;
                while (i4 < stageTaskListener.getChildCount()) {
                    sb2.append(i4 != 0 ? ", " : "");
                    sb2.append(stageTaskListener.mChildrenTaskInfo.keyAt(i4));
                    i4++;
                }
                Log.w("StageCoordinator", "Expected onTaskVanished on " + stageTaskListener + " to have been called with [" + sb2.toString() + "] before startAnimation().");
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (taskInfo != null && (getStageOfTask(taskInfo) != null || getSplitItemPosition(change.getLastParent()) != -1)) {
                arrayMap.put(Integer.valueOf(taskInfo.taskId), change.getLeash());
            }
        }
        switch (i2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
            case 9:
            case 11:
            case 12:
            case 14:
            case 15:
                this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda17(arrayMap, 2));
                break;
        }
        boolean z2 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        StageTaskListener stageTaskListener3 = this.mCellStage;
        if (z2 && z) {
            setCellSplitVisible(false);
            setCellDividerVisibility(transaction, false);
            transaction.setCrop(stageTaskListener3.mRootLeash, null);
            transaction2.hide(stageTaskListener3.mDimLayer);
            return;
        }
        this.mSplitRequest = null;
        setSplitsVisible(false);
        transaction.setCrop(stageTaskListener2.mRootLeash, null);
        transaction.setCrop(stageTaskListener.mRootLeash, null);
        if (i != -1) {
            transaction.hide(i == 0 ? stageTaskListener.mRootLeash : stageTaskListener2.mRootLeash);
            transaction.setPosition(i == 0 ? stageTaskListener2.mRootLeash : stageTaskListener.mRootLeash, 0.0f, 0.0f);
        } else {
            for (int size = arrayMap.keySet().size() - 1; size >= 0; size--) {
                transaction2.hide((SurfaceControl) arrayMap.valueAt(size));
            }
        }
        if (i == -1) {
            logExit(i2);
        } else {
            logExitToStage(i2, i == 0);
        }
        setDividerVisibility(transaction, false);
        transaction2.hide(stageTaskListener2.mDimLayer);
        transaction2.hide(stageTaskListener.mDimLayer);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            setCellSplitVisible(false);
            setCellDividerVisibility(transaction, false);
            transaction.setCrop(stageTaskListener3.mRootLeash, null);
            transaction2.hide(stageTaskListener3.mDimLayer);
        }
    }

    public final void prepareEnterMultiSplitScreen(WindowContainerTransaction windowContainerTransaction, int i) {
        StageTaskListener stageTaskListener = this.mCellStage;
        if (stageTaskListener.mIsActive) {
            return;
        }
        stageTaskListener.activate(null, false);
        if (i != 0) {
            setCellStageWindowConfigPosition(i, false);
        }
        this.mSplitLayout.updateCellStageWindowConfigPosition(this.mCellStageWindowConfigPosition);
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        updateStagePositionIfNeeded(windowContainerTransaction);
    }

    public final void prepareEnterSplitScreen(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, int i, boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 5089466645438215981L, 13, Long.valueOf(i), Boolean.valueOf(z));
        }
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, false);
        if (this.mMainStage.mIsActive) {
            prepareBringSplit(windowContainerTransaction, runningTaskInfo, i, z, 0.0f);
        } else {
            prepareActiveSplit(windowContainerTransaction, runningTaskInfo, i, z, 0.0f);
        }
    }

    public final void prepareExitMultiSplitScreen(WindowContainerTransaction windowContainerTransaction, boolean z) {
        StageTaskListener stageTaskListener = this.mCellStage;
        stageTaskListener.deactivate(windowContainerTransaction, z);
        stageTaskListener.mToSplit = false;
        this.mCellStageWindowConfigPosition = 0;
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
            this.mSplitLayout.mParallelMultiSplit = false;
        }
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        updateStagePositionIfNeeded(windowContainerTransaction);
    }

    public final void prepareExitSplitScreen(int i, int i2, WindowContainerTransaction windowContainerTransaction, boolean z) {
        StageTaskListener stageTaskListener = this.mMainStage;
        if (stageTaskListener.mIsActive) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 6292194992427381667L, 0, String.valueOf(SplitScreen.stageTypeToString(i)), String.valueOf(SplitScreenController.exitReasonToString(i2)));
            }
            boolean z2 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
            StageTaskListener stageTaskListener2 = this.mSideStage;
            if (z2) {
                stageTaskListener2.removeAllTasks(windowContainerTransaction, i == 1, z);
            } else {
                stageTaskListener2.removeAllTasks(windowContainerTransaction, i == 1);
            }
            if (i2 != 12) {
                if (i != 0) {
                    stageTaskListener = stageTaskListener2;
                }
                DisplayAreaInfo displayAreaInfo = this.mRootTDAOrganizer.getDisplayAreaInfo(this.mDisplayId);
                Objects.requireNonNull(displayAreaInfo);
                int i3 = displayAreaInfo.configuration.windowConfiguration.getWindowingMode() == 5 ? 1 : 0;
                for (int size = stageTaskListener.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size--) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) stageTaskListener.mChildrenTaskInfo.valueAt(size);
                    if (runningTaskInfo.pictureInPictureParams == null || !this.mIsTaskOpening) {
                        windowContainerTransaction.setWindowingMode(runningTaskInfo.token, i3);
                    }
                }
            }
            this.mMainStage.deactivate(windowContainerTransaction, i == 0);
            this.mSplitState.mState = -1;
            if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && isApplyFoldingPolicy(false)) {
                this.mTopStageAfterFold = -1;
                updateCoverDisplaySplitLayoutIfNeeded();
            }
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mCellStage.mIsActive) {
                prepareExitMultiSplitScreen(windowContainerTransaction, i == 5);
            }
        }
    }

    public final void prepareMultiSplitDismissChangeTransition(int i, WindowContainerTransaction windowContainerTransaction, boolean z) {
        String str;
        StageTaskListener stageTaskListener = this.mCellStage;
        if (stageTaskListener.mHost == null || !stageTaskListener.mIsActive) {
            Slog.w("StageCoordinator", "prepareMultiSplitDismissChangeTransition: failed, invalid cell host");
            return;
        }
        int stageType = getStageType(stageTaskListener);
        int stageType2 = getStageType(stageTaskListener.mHost);
        int i2 = stageType2 == 0 ? 1 : 0;
        WindowContainerToken stageToken = getStageToken(stageType2);
        WindowContainerToken stageToken2 = getStageToken(stageType);
        WindowContainerToken stageToken3 = getStageToken(i);
        if (stageToken == null || stageToken2 == null || stageToken3 == null) {
            Slog.w("StageCoordinator", "prepareMultiSplitDismissChangeTransition: failed, dismissStageToken=" + stageToken3 + ", cellHostStageToken=" + stageToken + ", cellStageToken=" + stageToken2);
            return;
        }
        int i3 = z ? 4 : 1;
        if (i == i2) {
            str = "half_dismiss";
            windowContainerTransaction.setChangeTransitMode(stageToken2, 1, "half_dismiss");
            windowContainerTransaction.setChangeTransitMode(stageToken, 1, "half_dismiss");
        } else if (i == stageType) {
            windowContainerTransaction.setChangeTransitMode(stageToken, i3, "cell_dismiss");
            str = "cell_dismiss";
        } else {
            windowContainerTransaction.setChangeTransitMode(stageToken2, i3, "cell_host_dismiss");
            str = "cell_host_dismiss";
        }
        boolean anyMatch = windowContainerTransaction.getChanges().values().stream().anyMatch(new StageCoordinator$$ExternalSyntheticLambda5());
        if (!anyMatch) {
            windowContainerTransaction.setChangeTransitMode(stageToken3, 2, str);
        }
        Slog.d("StageCoordinator", "prepareMultiSplitDismissChangeTransition: dismiss=" + SplitScreen.stageTypeToString(i) + ", hasMovingToFreeform=" + anyMatch);
    }

    public void prepareSplitDismissChangeTransition(WindowContainerTransaction windowContainerTransaction, int i, TransitionRequestInfo transitionRequestInfo) {
        prepareSplitDismissChangeTransition(windowContainerTransaction, i, transitionRequestInfo, false);
    }

    public final void prepareSplitLayout(WindowContainerTransaction windowContainerTransaction, boolean z, float f) {
        boolean z2 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
        if (z2 && z) {
            z = false;
        }
        this.mSplitState.mState = 10;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 4545601228927874522L, 3, Boolean.valueOf(z));
        }
        if (f != 0.0f) {
            this.mSplitLayout.setDivideRatio(f, true, true);
        } else if (z) {
            this.mSplitLayout.setDividerAtBorder(this.mSideStagePosition == 0);
        } else {
            this.mSplitLayout.resetDividerPosition();
        }
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        updateStagePositionIfNeeded(windowContainerTransaction);
        if (z) {
            windowContainerTransaction.setSmallestScreenWidthDp(this.mMainStage.mRootTaskInfo.token, 0);
            this.mTempRect1.set(this.mSplitLayout.mInvisibleBounds);
            SplitLayout splitLayout = this.mSplitLayout;
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mSideStage.mRootTaskInfo;
            Rect rect = this.mTempRect1;
            splitLayout.getClass();
            windowContainerTransaction.setBounds(runningTaskInfo.token, rect);
            windowContainerTransaction.setSmallestScreenWidthDp(runningTaskInfo.token, splitLayout.getSmallestWidthDp(rect));
        }
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        setRootForceTranslucent(windowContainerTransaction, false);
        if (z2) {
            windowContainerTransaction.setChangeTransitionRequest(1);
        }
    }

    public void prepareSplitMaximizeChangeTransition(WindowContainerTransaction windowContainerTransaction, int i) {
        if (!CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION || !isMultiSplitActive()) {
            StageTaskListener stageTaskListenerByStageType = getStageTaskListenerByStageType(i);
            WindowContainerToken stageToken = getStageToken(i == 0 ? 1 : 0);
            WindowContainerToken stageToken2 = getStageToken(i);
            if (stageToken == null || stageToken2 == null) {
                Slog.w("StageCoordinator", "prepareSplitMaximizeChangeTransition: failed, dismissStageToken=" + stageToken + ", expandStageToken=" + stageToken2);
                return;
            }
            windowContainerTransaction.setChangeTransitMode(stageToken, 2, "maximize_split");
            windowContainerTransaction.setChangeTransitMode(stageToken2, stageTaskListenerByStageType.hasAppsEdgeActivityOnTop() ? 2 : 1, "maximize_split");
            Slog.d("StageCoordinator", "prepareSplitMaximizeChangeTransition: expand=" + SplitScreen.stageTypeToString(i));
            return;
        }
        StageTaskListener stageTaskListener = this.mCellStage;
        if (stageTaskListener.mHost == null || !stageTaskListener.mIsActive) {
            Slog.w("StageCoordinator", "prepareMultiSplitMaximizeChangeTransition: failed, invalid cell host");
            return;
        }
        StageTaskListener stageTaskListenerByStageType2 = getStageTaskListenerByStageType(i);
        WindowContainerToken stageToken3 = getStageToken(i);
        if (stageToken3 == null) {
            Slog.w("StageCoordinator", "prepareMultiSplitMaximizeChangeTransition: failed, cannot find token");
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(getStageToken(0));
        arrayList.add(getStageToken(1));
        arrayList.add(getStageToken(5));
        arrayList.remove(stageToken3);
        int size = arrayList.size();
        while (r2 < size) {
            Object obj = arrayList.get(r2);
            r2++;
            WindowContainerToken windowContainerToken = (WindowContainerToken) obj;
            if (windowContainerToken != null) {
                windowContainerTransaction.setChangeTransitMode(windowContainerToken, 2, "maximize_multi_split");
            }
        }
        windowContainerTransaction.setChangeTransitMode(stageToken3, stageTaskListenerByStageType2.hasAppsEdgeActivityOnTop() ? 2 : 1, "maximize_multi_split");
        Slog.d("StageCoordinator", "prepareMultiSplitMaximizeChangeTransition: expand=" + SplitScreen.stageTypeToString(i));
    }

    public final void prepareTasksForSplitScreen(int[] iArr, WindowContainerTransaction windowContainerTransaction) {
        for (int i : iArr) {
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskOrganizer.getRunningTaskInfo(i);
            if (runningTaskInfo != null && WindowConfiguration.inMultiWindowMode(runningTaskInfo.getWindowingMode())) {
                windowContainerTransaction.setWindowingMode(runningTaskInfo.getToken(), 0).setBounds(runningTaskInfo.getToken(), (Rect) null);
            }
        }
    }

    public final void recordLastActiveStage(boolean z) {
        StageTaskListener stageTaskListener = this.mMainStage;
        if (!stageTaskListener.mIsActive || !isSplitScreenVisible()) {
            this.mLastActiveStage = -1;
        } else if (stageTaskListener.isFocused()) {
            this.mLastActiveStage = 0;
        } else if (this.mSideStage.isFocused()) {
            this.mLastActiveStage = 1;
        } else {
            boolean z2 = CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY;
            if (z2 && this.mCellStage.isFocused()) {
                this.mLastActiveStage = 5;
            } else if (z2 && isSplitScreenVisible()) {
                this.mLastActiveStage = 0;
            }
        }
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && z) {
            this.mTopStageAfterFold = this.mLastActiveStage;
        }
    }

    public final void registerSplitScreenListener(SplitScreen.SplitScreenListener splitScreenListener) {
        if (this.mListeners.contains(splitScreenListener)) {
            return;
        }
        this.mListeners.add(splitScreenListener);
        sendStatusToListener(splitScreenListener);
    }

    public final void removeDividerWhenSplitInactive() {
        if (this.mMainStage.mIsActive || !this.mDividerVisible) {
            return;
        }
        setDividerVisibility(null, false);
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            this.mSplitTransitions.cancelDividerFadeAnimation();
        }
        this.mSplitLayout.release(null);
    }

    public final void reparentCellToMainOrSide(WindowContainerTransaction windowContainerTransaction, StageTaskListener stageTaskListener, boolean z) {
        StageTaskListener stageTaskListener2 = this.mCellStage;
        boolean z2 = stageTaskListener2.mIsActive;
        if (z2) {
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitLayout.mParallelMultiSplit) {
                if (z2) {
                    StageTaskListener stageTaskListener3 = stageTaskListener2.mHost;
                    if (stageTaskListener.equals(stageTaskListener3)) {
                        stageTaskListener2.reparentAllTasks(stageTaskListener3.mRootTaskInfo.token, windowContainerTransaction, z);
                    } else {
                        stageTaskListener3.reparentAllChildren(stageTaskListener.mRootTaskInfo.token, windowContainerTransaction);
                        stageTaskListener2.reparentAllTasks(stageTaskListener3.mRootTaskInfo.token, windowContainerTransaction, true);
                        SplitLayout splitLayout = this.mSplitLayout;
                        splitLayout.setDividePosition(splitLayout.mCellDividerPosition, null, false);
                        splitLayout.update(null, false);
                    }
                    prepareExitMultiSplitScreen(windowContainerTransaction, false);
                    return;
                }
                return;
            }
            stageTaskListener2.reparentAllTasks(stageTaskListener.mRootTaskInfo.token, windowContainerTransaction, z);
            stageTaskListener2.mToSplit = true;
            if (!stageTaskListener.equals(stageTaskListener2.mHost)) {
                int i = this.mSideStagePosition;
                int reverseSplitPosition = stageTaskListener.mStageType == 1 ? SplitScreenUtils.reverseSplitPosition(i) : i;
                if (isVerticalDivision()) {
                    r3 = (this.mCellStageWindowConfigPosition & 16) != 0 ? 1 : 0;
                    if ((reverseSplitPosition == 0 && r3 == 0) || (reverseSplitPosition == 1 && r3 != 0)) {
                        i = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
                    }
                    r3 = 1;
                } else {
                    boolean z3 = (this.mCellStageWindowConfigPosition & 8) != 0;
                    if ((reverseSplitPosition == 0 && !z3) || (reverseSplitPosition == 1 && z3)) {
                        i = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
                    }
                }
                if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
                    SplitLayout splitLayout2 = this.mSplitLayout;
                    splitLayout2.mTempRect2.set(splitLayout2.mRootBounds);
                    splitLayout2.mTempRect2.inset(splitLayout2.getDisplayStableInsets(splitLayout2.mContext));
                    if (splitLayout2.mSplitDivision == 0) {
                        int i2 = splitLayout2.mCellDividerPosition;
                        Rect rect = splitLayout2.mTempRect2;
                        float height = (i2 - rect.top) / (rect.height() - splitLayout2.mDividerSize);
                        splitLayout2.mDividerPosition = splitLayout2.mTempRect2.left + ((int) (((r5.width() - splitLayout2.mDividerSize) * height) + 0.5f));
                    } else {
                        int i3 = splitLayout2.mCellDividerPosition;
                        Rect rect2 = splitLayout2.mTempRect2;
                        float width = (i3 - rect2.left) / (rect2.width() - splitLayout2.mDividerSize);
                        splitLayout2.mDividerPosition = splitLayout2.mTempRect2.top + ((int) (((r5.height() - splitLayout2.mDividerSize) * width) + 0.5f));
                    }
                }
                setSideStagePosition(i, r3, windowContainerTransaction, true);
            }
            prepareExitMultiSplitScreen(windowContainerTransaction, z);
        }
    }

    public final IBinder requestEnterSplitSelect(int i, ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect, WindowContainerTransaction windowContainerTransaction) {
        Iterator it = ((HashSet) this.mSelectListeners).iterator();
        boolean z = false;
        while (it.hasNext()) {
            SplitScreenController.ISplitScreenImpl.AnonymousClass2 anonymousClass2 = (SplitScreenController.ISplitScreenImpl.AnonymousClass2) it.next();
            anonymousClass2.getClass();
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            IInterface iInterface = SplitScreenController.ISplitScreenImpl.this.mSelectListener.mListener;
            if (iInterface == null) {
                Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
            } else {
                try {
                    atomicBoolean.set(((ISplitSelectListener$Stub$Proxy) iInterface).onRequestSplitSelect(runningTaskInfo, i, rect));
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }
            z |= atomicBoolean.get();
        }
        if (!z) {
            return null;
        }
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            return this.mTransitions.startTransition(6, windowContainerTransaction, null);
        }
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        return null;
    }

    public final Bundle resolveStartCellStage(int i, int i2, Bundle bundle, WindowContainerTransaction windowContainerTransaction) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        StageTaskListener stageTaskListener = this.mCellStage;
        if (i == -1) {
            if (i2 == 0 || !stageTaskListener.mIsActive) {
                resolveStartCellStage(5, i2, bundle, windowContainerTransaction);
                return bundle;
            }
            int i3 = getMainStageWinConfigPosition() != i2 ? getSideStageWinConfigPosition() == i2 ? 1 : this.mCellStageWindowConfigPosition == i2 ? 5 : -1 : 0;
            if (i3 != -1) {
                resolveStartCellStage(i3, i2, bundle, windowContainerTransaction);
                return bundle;
            }
            Slog.w("StageCoordinator", "No stage type nor split position specified to resolve start stage");
            return bundle;
        }
        if (i == 0) {
            addActivityOptions(bundle, this.mMainStage);
            return bundle;
        }
        if (i == 1) {
            addActivityOptions(bundle, this.mSideStage);
            return bundle;
        }
        if (i != 5) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown stage="));
        }
        if (i2 == 0) {
            i2 = StageUtils.getMultiSplitLaunchPosition(0, isVerticalDivision());
        }
        setCellStageWindowConfigPosition(i2, false);
        if (windowContainerTransaction != null) {
            this.mSplitLayout.updateCellStageWindowConfigPosition(this.mCellStageWindowConfigPosition);
            updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
            updateStagePositionIfNeeded(windowContainerTransaction);
        }
        addActivityOptions(bundle, stageTaskListener);
        return bundle;
    }

    public final Bundle resolveStartStage(int i, int i2, Bundle bundle, WindowContainerTransaction windowContainerTransaction, int i3) {
        if (i == -1) {
            if (i2 == -1) {
                Slog.w("StageCoordinator", "No stage type nor split position specified to resolve start stage");
                return bundle;
            }
            if (isSplitScreenVisible()) {
                return resolveStartStage(i2 != this.mSideStagePosition ? 0 : 1, i2, bundle, windowContainerTransaction, i3);
            }
            return resolveStartStage(1, i2, bundle, windowContainerTransaction, i3);
        }
        if (i == 0) {
            if (i2 != -1) {
                setSideStagePosition(SplitScreenUtils.reverseSplitPosition(i2), i3, windowContainerTransaction, true);
            } else {
                i2 = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            addActivityOptions(bundle, i2 == this.mSideStagePosition ? this.mSideStage : this.mMainStage);
            return bundle;
        }
        if (i != 1) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown stage="));
        }
        if (i2 != -1) {
            setSideStagePosition(i2, i3, windowContainerTransaction, true);
        } else {
            i2 = this.mSideStagePosition;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        addActivityOptions(bundle, i2 == this.mSideStagePosition ? this.mSideStage : this.mMainStage);
        return bundle;
    }

    public final boolean rotateMultiSplitWithTransition() {
        if (isSplitScreenVisible()) {
            StageTaskListener stageTaskListener = this.mMainStage;
            if (stageTaskListener.mRootTaskInfo != null) {
                StageTaskListener stageTaskListener2 = this.mSideStage;
                if (stageTaskListener2.mRootTaskInfo != null) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
                    WindowContainerToken windowContainerToken = stageTaskListener.mRootTaskInfo.token;
                    WindowContainerToken windowContainerToken2 = stageTaskListener2.mRootTaskInfo.token;
                    ActivityManager.RunningTaskInfo runningTaskInfo = this.mCellStage.mRootTaskInfo;
                    WindowContainerToken windowContainerToken3 = runningTaskInfo != null ? runningTaskInfo.token : null;
                    multiSplitLayoutInfo.sideStagePosition = this.mSideStagePosition;
                    multiSplitLayoutInfo.splitDivision = getSplitDivision();
                    multiSplitLayoutInfo.cellStagePosition = this.mCellStageWindowConfigPosition;
                    rotateMultiSplitClockwise(multiSplitLayoutInfo);
                    this.mSplitLayout.setDivideRatio(0.5f, true, true);
                    windowContainerTransaction.setChangeTransitMode(windowContainerToken, 1, "rotate_split");
                    windowContainerTransaction.setChangeTransitMode(windowContainerToken2, 1, "rotate_split");
                    if (isMultiSplitScreenVisible() && windowContainerToken3 != null) {
                        windowContainerTransaction.setChangeTransitMode(windowContainerToken3, 1, "rotate_split");
                    }
                    this.mIsMultiSplitRotating = true;
                    updateMultiSplitLayout(multiSplitLayoutInfo, true, windowContainerTransaction);
                    this.mIsMultiSplitRotating = false;
                    return true;
                }
            }
        }
        Slog.w("StageCoordinator", "rotateMultiSplitWithTransition: failed, split isn't activated");
        return false;
    }

    public final void sendOnBoundsChanged() {
        if (this.mSplitLayout == null) {
            return;
        }
        for (int size = this.mListeners.size() - 1; size >= 0; size--) {
            ((SplitScreen.SplitScreenListener) this.mListeners.get(size)).onSplitBoundsChanged(this.mSplitLayout.getRootBounds(), getMainStageBounds(), getSideStageBounds());
        }
    }

    public final void sendSplitDirectionSaLogging() {
        if (this.mDividerVisible) {
            CoreSaLogger.logForAdvanced("1025", (isVerticalDivision() ? "Vertical split" : "Horizontal split") + " + " + (this.mContext.getResources().getConfiguration().orientation == 1 ? "Vertical device" : "Horizontal device"));
        }
    }

    public final void sendStatusToListener(SplitScreen.SplitScreenListener splitScreenListener) {
        splitScreenListener.onStagePositionChanged(0, SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition));
        splitScreenListener.onStagePositionChanged(1, this.mSideStagePosition);
        splitScreenListener.onSplitVisibilityChanged(isSplitScreenVisible());
        SplitLayout splitLayout = this.mSplitLayout;
        if (splitLayout != null) {
            splitScreenListener.onSplitBoundsChanged(splitLayout.getRootBounds(), getMainStageBounds(), getSideStageBounds());
        }
        this.mSideStage.onSplitScreenListenerRegistered(splitScreenListener, 1);
        this.mMainStage.onSplitScreenListenerRegistered(splitScreenListener, 0);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            this.mCellStage.onSplitScreenListenerRegistered(splitScreenListener, 5);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void setAnimScaleSetting(float f) {
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (splitScreenTransitions.mDurationScale != f) {
            Log.d("SplitScreenTransitions", "setAnimScaleSetting: " + splitScreenTransitions.mDurationScale + "->" + f);
            splitScreenTransitions.mDurationScale = f;
        }
    }

    public final void setCellDividerVisibility(SurfaceControl.Transaction transaction, boolean z) {
        if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || z == this.mCellDividerVisible) {
            return;
        }
        this.mCellDividerVisible = z;
        applyCellDividerVisibility(transaction);
    }

    public final void setCellSplitVisible(boolean z) {
        StageTaskListener stageTaskListener = this.mCellStage;
        stageTaskListener.mVisible = z;
        stageTaskListener.mHasChildren = z;
    }

    public final void setCellStageWindowConfigPosition(int i, boolean z) {
        if (this.mCellStageWindowConfigPosition != i || z) {
            this.mCellStageWindowConfigPosition = i;
            boolean isVerticalDivision = isVerticalDivision();
            int i2 = this.mSideStagePosition;
            StageTaskListener stageTaskListener = this.mMainStage;
            StageTaskListener stageTaskListener2 = this.mSideStage;
            if (i2 != 0 ? !((!isVerticalDivision || (i & 32) == 0) && (isVerticalDivision || (i & 64) == 0)) : !((!isVerticalDivision || (i & 8) == 0) && (isVerticalDivision || (i & 16) == 0))) {
                stageTaskListener = stageTaskListener2;
            }
            this.mCellStage.mHost = stageTaskListener;
        }
    }

    public final void setDividerVisibility(SurfaceControl.Transaction transaction, boolean z) {
        if (z != this.mDividerVisible) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -4204423971804924690L, 63, Boolean.valueOf(z), Boolean.valueOf(this.mKeyguardActive), Boolean.FALSE, String.valueOf(Debug.getCaller()));
            }
            if (z && this.mKeyguardActive) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 4019041459758970273L, 0, null);
                    return;
                }
                return;
            }
            this.mDividerVisible = z;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1023677597576446492L, 3, Boolean.valueOf(z));
            }
            for (int size = ((ArrayList) this.mListeners).size() - 1; size >= 0; size--) {
                ((SplitScreen.SplitScreenListener) ((ArrayList) this.mListeners).get(size)).onSplitVisibilityChanged(this.mDividerVisible);
            }
            sendOnBoundsChanged();
            SplitWindowManager splitWindowManager = this.mSplitLayout.mSplitWindowManager;
            boolean z2 = this.mDividerVisible;
            splitWindowManager.mDividerVisible = z2;
            if (!z2) {
                DividerView dividerView = splitWindowManager.mDividerView;
                if (dividerView != null && splitWindowManager.mIsPendingFirstAutoOpenDividerPanel) {
                    dividerView.removeCallbacks(splitWindowManager.mDividerPanelAutoOpen);
                    splitWindowManager.mIsPendingFirstAutoOpenDividerPanel = false;
                    Slog.d("SplitWindowManager", "removeCallbacks() DividerPanel first auto open / mIsFirstAutoOpenDividerPanel: " + splitWindowManager.mIsFirstAutoOpenDividerPanel);
                }
                splitWindowManager.mDividerPanel.removeDividerPanel();
                AlertDialog alertDialog = splitWindowManager.mDividerPanel.mAddToAppPairDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
            if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION_SA_LOGGING) {
                sendSplitDirectionSaLogging();
            }
            applyDividerVisibility(transaction);
        }
    }

    public final void setExcludeImeInsets(boolean z) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        if (runningTaskInfo == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 803653301589720325L, 0, null);
            }
        } else {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 8184076608760067502L, 0, String.valueOf(runningTaskInfo.taskId), String.valueOf(z));
            }
            windowContainerTransaction.setExcludeImeInsets(this.mRootTaskInfo.token, z);
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
    }

    public final void setLaunchAdjacentDisabled(boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -4543435650501889644L, 3, Boolean.valueOf(z));
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setDisableLaunchAdjacent(this.mRootTaskInfo.token, z);
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
    }

    public final void setLayoutOffsetTargetForEnsureDock(int i, SplitLayout splitLayout) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME && isMultiSplitActive()) {
            splitLayout.applyLayoutOffsetTargetForMultiSplit(windowContainerTransaction, i, getBottomStages());
        } else {
            ActivityManager.RunningTaskInfo runningTaskInfo = (this.mSideStagePosition == 0 ? this.mMainStage : this.mSideStage).mRootTaskInfo;
            splitLayout.getClass();
            if (i == 0) {
                windowContainerTransaction.setBounds(runningTaskInfo.token, splitLayout.getBottomRightBounds());
                windowContainerTransaction.setScreenSizeDp(runningTaskInfo.token, 0, 0);
            } else {
                splitLayout.mTempRect.set(splitLayout.getBottomRightBounds());
                splitLayout.mTempRect.offset(0, i);
                windowContainerTransaction.setBounds(runningTaskInfo.token, splitLayout.mTempRect);
                if (runningTaskInfo.configuration.windowConfiguration.getBounds().equals(splitLayout.getBottomRightBounds())) {
                    WindowContainerToken windowContainerToken = runningTaskInfo.token;
                    Configuration configuration = runningTaskInfo.configuration;
                    windowContainerTransaction.setScreenSizeDp(windowContainerToken, configuration.screenWidthDp, configuration.screenHeightDp);
                } else {
                    splitLayout.getDisplayLayout(splitLayout.mContext).getStableBounds(splitLayout.mTempRect, false);
                    splitLayout.mTempRect.intersectUnchecked(splitLayout.getBottomRightBounds());
                    windowContainerTransaction.setScreenSizeDp(runningTaskInfo.token, (int) ((splitLayout.mTempRect.width() / splitLayout.getDisplayLayout(splitLayout.mContext).density()) + 0.5f), (int) ((splitLayout.mTempRect.height() / splitLayout.getDisplayLayout(splitLayout.mContext).density()) + 0.5f));
                }
            }
        }
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
    }

    public final void setRootForceTranslucent(WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (this.mIsRootTranslucent == z) {
            return;
        }
        this.mIsRootTranslucent = z;
        windowContainerTransaction.setForceTranslucent(this.mRootTaskInfo.token, z);
    }

    public final void setSideStagePosition(int i, int i2, WindowContainerTransaction windowContainerTransaction, boolean z) {
        boolean splitDivision = (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitLayout.mParallelMultiSplit) ? setSplitDivision(!isLandscape() ? 1 : 0, isInSubDisplay(), true) : CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? setSplitDivision(i2, isInSubDisplay(), true) : false;
        if (this.mSideStagePosition != i || (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && splitDivision)) {
            this.mSideStagePosition = i;
            for (int size = ((ArrayList) this.mListeners).size() - 1; size >= 0; size--) {
                SplitScreen.SplitScreenListener splitScreenListener = (SplitScreen.SplitScreenListener) ((ArrayList) this.mListeners).get(size);
                splitScreenListener.onStagePositionChanged(0, SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition));
                splitScreenListener.onStagePositionChanged(1, this.mSideStagePosition);
            }
            if (this.mSideStage.mVisible && z) {
                if (windowContainerTransaction == null) {
                    onLayoutSizeChanged(this.mSplitLayout, null);
                    return;
                }
                updateStagePositionIfNeeded(windowContainerTransaction);
                updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
                sendOnBoundsChanged();
            }
        }
    }

    public final void setSideStagePosition$1(WindowContainerTransaction windowContainerTransaction, int i) {
        setSideStagePosition(i, -1, windowContainerTransaction, true);
    }

    public final void setSideStagePositionByAdjacentTask(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo) {
        Intent intent = runningTaskInfo.baseIntent;
        if (intent == null || (intent.getFlags() & 4096) == 0) {
            this.mSplitLayout.mSplitWindowManager.getClass();
            return;
        }
        this.mSplitLayout.mSplitWindowManager.getClass();
        windowContainerTransaction.setChangeStagePosition(true);
        if (this.mSplitLayoutChangedForLaunchAdjacent) {
            this.mSplitLayoutChangedForLaunchAdjacent = false;
        } else if (CoreRune.MW_MULTI_SPLIT_LAUNCH_ADJACENT) {
            setSideStagePosition(1, 0, windowContainerTransaction, true);
        } else if (this.mSideStagePosition == 0) {
            setSideStagePosition$1(windowContainerTransaction, 1);
        }
    }

    public final MultiSplitLayoutInfo setSplitCreateMode(int i, boolean z) {
        if (i == 2 || i == 3 || i == 4 || i == 5) {
            MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
            multiSplitLayoutInfo.sideStagePosition = this.mSideStagePosition;
            multiSplitLayoutInfo.splitDivision = getSplitDivision();
            multiSplitLayoutInfo.cellStagePosition = this.mCellStageWindowConfigPosition;
            if (i != convertCreateMode(multiSplitLayoutInfo)) {
                while (i != rotateMultiSplitClockwise(multiSplitLayoutInfo)) {
                }
                updateMultiSplitLayout(multiSplitLayoutInfo, z, null);
                return multiSplitLayoutInfo;
            }
        }
        return null;
    }

    public final boolean setSplitDivision(int i, boolean z, boolean z2) {
        int i2;
        if (i == -1) {
            return false;
        }
        if (z) {
            i = !isLandscape() ? 1 : 0;
        } else if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            SplitLayout splitLayout = this.mSplitLayout;
            if (splitLayout.mSplitScreenFeasibleMode == 1 && i != (i2 = splitLayout.mPossibleSplitDivision)) {
                Slog.d("StageCoordinator", "split division not feasible, so change: " + i2);
                i = i2;
            }
        }
        if (this.mSplitDivision == i) {
            return false;
        }
        if (CoreRune.IS_DEBUG_LEVEL_MID) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setSplitDivision: nextSplitDivision=", "   Caller=");
            m.append(Debug.getCallers(5));
            Slog.d("StageCoordinator", m.toString());
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            this.mSplitTransitions.cancelDividerFadeAnimation();
        }
        int i3 = this.mSplitDivision;
        this.mSplitDivision = i;
        SplitLayout splitLayout2 = this.mSplitLayout;
        if (splitLayout2.mSplitDivision != i) {
            splitLayout2.mSplitDivision = i;
            splitLayout2.mIsLeftRightSplit = splitLayout2.isVerticalDivision();
            splitLayout2.updateSnapAlgorithm(i3);
        }
        if (z2) {
            this.mSplitLayout.update(null, true);
        }
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION_SA_LOGGING) {
            sendSplitDirectionSaLogging();
        }
        return true;
    }

    public void setSplitTransitions(SplitScreenTransitions splitScreenTransitions) {
        this.mSplitTransitions = splitScreenTransitions;
    }

    public final void setSplitsVisible(boolean z) {
        boolean z2 = !z;
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && z && !isSplitScreenVisible()) {
            z2 = true;
        }
        setSplitsVisible$1(z, z2);
    }

    public final void setSplitsVisible$1(boolean z, boolean z2) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -7171744665051645980L, 3, Boolean.valueOf(z));
        }
        StageTaskListener stageTaskListener = this.mSideStage;
        stageTaskListener.mVisible = z;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        stageTaskListener2.mVisible = z;
        stageTaskListener.mHasChildren = z;
        stageTaskListener2.mHasChildren = z;
        if (this.mDividerLeashHidden && z) {
            updateDividerLeashVisible(true);
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            boolean isMultiSplitActive = isMultiSplitActive();
            StageTaskListener stageTaskListener3 = this.mCellStage;
            if (isMultiSplitActive && z) {
                stageTaskListener3.mVisible = true;
                stageTaskListener3.mHasChildren = true;
            } else {
                stageTaskListener3.mVisible = false;
                stageTaskListener3.mHasChildren = false;
            }
        }
        this.mSplitBackgroundController.setSplitsVisible(z, z2);
    }

    public final boolean shouldkeyguardUnlockWithUpdateSplit(int i) {
        return this.mFoldLockSettingsObserver.isSelectiveStayAwake() && isApplyFoldingPolicy(false) && isSplitScreenVisible() && !isMultiSplitScreenVisible() && (i & 256) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x03f9  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0611 A[EDGE_INSN: B:297:0x0611->B:298:0x0611 BREAK  A[LOOP:3: B:102:0x021a->B:120:0x0601], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:361:0x06e8  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x06f7 A[EDGE_INSN: B:366:0x06f7->B:367:0x06f7 BREAK  A[LOOP:6: B:359:0x06e2->B:363:0x06f4], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0708  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x0754  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x07a3  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x07ab  */
    /* JADX WARN: Removed duplicated region for block: B:502:0x01d3  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startAnimation(android.os.IBinder r29, android.window.TransitionInfo r30, android.view.SurfaceControl.Transaction r31, android.view.SurfaceControl.Transaction r32, com.android.wm.shell.transition.Transitions.TransitionFinishCallback r33) {
        /*
            Method dump skipped, instructions count: 2346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
    }

    public final void startIntent(PendingIntent pendingIntent, Intent intent, int i, Bundle bundle, WindowContainerToken windowContainerToken, int i2, int i3, boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 5549206730724687003L, 4, String.valueOf(pendingIntent.getIntent()), Long.valueOf(i));
        }
        this.mSplitRequest = new SplitRequest(this, pendingIntent.getIntent(), i);
        boolean z2 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        if (z2 && i3 != 0) {
            startIntentToCell(pendingIntent, null, intent, null, i3, z, null);
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        Bundle resolveStartStage = resolveStartStage(-1, i, bundle, null, i2);
        if (windowContainerToken != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1580296436628468854L, 0, null);
            }
            windowContainerTransaction.reorder(windowContainerToken, false);
        }
        if (z2 && isMultiSplitActive() && !isMultiSplitScreenVisible()) {
            prepareExitMultiSplitScreen(windowContainerTransaction, false);
        }
        windowContainerTransaction.sendPendingIntent(pendingIntent, intent, resolveStartStage);
        DefaultMixedHandler defaultMixedHandler = this.mMixedHandler;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        if ((defaultMixedHandler != null && defaultMixedHandler.isIntentInPip(pendingIntent)) || i == -1) {
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            return;
        }
        if (!isSplitScreenVisible()) {
            this.mSkipEvictingMainStageChildren = true;
            setDividerVisibility(null, false);
        }
        if (CoreRune.MW_SPLIT_STACKING && isSplitScreenVisible()) {
            windowContainerTransaction.addAdditionalInfo(4);
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
        } else {
            int i4 = this.mMainStage.mIsActive ? 1005 : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
            prepareEnterSplitScreen(windowContainerTransaction, null, i, !this.mIsDropEntering);
            this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, i4, !this.mIsDropEntering, 1);
        }
    }

    public final void startIntentToCell(PendingIntent pendingIntent, Intent intent, Intent intent2, UserHandle userHandle, int i, boolean z, Bundle bundle) {
        if (userHandle == null) {
            userHandle = UserHandle.CURRENT;
        }
        UserHandle userHandle2 = userHandle;
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1107296256, null, userHandle2);
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.sendPendingIntent(pendingIntent, intent2, resolveStartCellStage(-1, i, bundle, windowContainerTransaction));
        if (CoreRune.MW_SPLIT_STACKING && isMultiSplitScreenVisible()) {
            windowContainerTransaction.addAdditionalInfo(4);
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
            return;
        }
        boolean z2 = CoreRune.MW_PARALLEL_MULTI_SPLIT;
        if (z2 && z) {
            this.mSplitLayout.mParallelMultiSplit = true;
            MultiSplitLayoutInfo currentMultiSplitLayoutInfo = getCurrentMultiSplitLayoutInfo();
            currentMultiSplitLayoutInfo.cellStagePosition = i;
            applyParallelMultiSplitLayoutInfo(windowContainerTransaction, currentMultiSplitLayoutInfo);
            this.mSplitLayout.setDivideRatio(this.mSplitLayout.calculateSplitRatioForParallelMultiSplit(currentMultiSplitLayoutInfo), true, true);
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !isMultiSplitScreenVisible()) {
            this.mSplitLayout.setCellDividerRatio(0.5f, i, true, false);
        }
        prepareEnterMultiSplitScreen(windowContainerTransaction, i);
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
            if (z2 && z) {
                windowContainerTransaction.setChangeTransitMode(this.mMainStage.mRootTaskInfo.token, 1, "enter_multi_fold_split");
                windowContainerTransaction.setChangeTransitMode(this.mSideStage.mRootTaskInfo.token, 1, "enter_multi_fold_split");
            } else {
                applyCellHostResizeTransition(windowContainerTransaction);
            }
        }
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, VolteConstants.ErrorCode.CALL_SESSION_ABORT, false, 1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:147:0x06fb, code lost:
    
        if (r1.getAnimationOptions().getType() == 1) goto L303;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x07d4, code lost:
    
        if (r2.getChangeLeash() != null) goto L334;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00b5, code lost:
    
        if (r9.getMode() == 6) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:356:0x07ed, code lost:
    
        if (r20.getChangeLeash() != null) goto L334;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x010c, code lost:
    
        if (r29.getMode() != 6) goto L56;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x03bb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:380:0x0423  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x044f  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x0473  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x0483  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x04a4 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:409:0x0498 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:413:0x0305  */
    /* JADX WARN: Removed duplicated region for block: B:417:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0298  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02b5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02e4  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0329  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startPendingAnimation(android.os.IBinder r39, android.window.TransitionInfo r40, android.view.SurfaceControl.Transaction r41, android.view.SurfaceControl.Transaction r42, com.android.wm.shell.transition.Transitions.TransitionFinishCallback r43) {
        /*
            Method dump skipped, instructions count: 3049
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.startPendingAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
    }

    public final void startSingleTask(int i, Bundle bundle, WindowContainerTransaction windowContainerTransaction, RemoteTransition remoteTransition) {
        if (this.mMainStage.mChildrenTaskInfo.contains(i) || this.mSideStage.mChildrenTaskInfo.contains(i)) {
            prepareExitSplitScreen(-1, 13, windowContainerTransaction, true);
        }
        if (this.mRecentTasks.isPresent()) {
            ((RecentTasksController) this.mRecentTasks.get()).removeSplitPair(i);
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        addActivityOptions(bundle, null);
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskOrganizer.getRunningTaskInfo(i);
        if (runningTaskInfo != null && runningTaskInfo.getWindowingMode() == 5) {
            prepareTasksForSplitScreen(new int[]{i}, windowContainerTransaction);
        }
        windowContainerTransaction.startTask(i, bundle);
        this.mSplitTransitions.startFullscreenTransition(windowContainerTransaction, remoteTransition);
    }

    public final void startSplitScreen(int i, PendingIntent pendingIntent, Intent intent, Intent intent2, Intent intent3, UserHandle userHandle, UserHandle userHandle2, UserHandle userHandle3, int i2, int i3, float f, float f2, int i4, int i5, boolean z, WindowContainerTransaction windowContainerTransaction, RemoteTransition remoteTransition) {
        Intent intent4;
        PendingIntent pendingIntent2;
        Intent intent5;
        UserHandle userHandle4;
        PendingIntent pendingIntent3;
        PendingIntent pendingIntent4;
        Intent intent6;
        UserHandle userHandle5;
        RemoteTransition remoteTransition2;
        int i6;
        boolean z2;
        int i7;
        SyncTransactionQueue syncTransactionQueue;
        Intent intent7;
        PendingIntent pendingIntent5;
        RemoteTransition remoteTransition3;
        RemoteTransition remoteTransition4;
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
            this.mTopStageAfterFold = -1;
        }
        boolean z3 = CoreRune.MW_PARALLEL_MULTI_SPLIT;
        if (z3) {
            SplitLayout splitLayout = this.mSplitLayout;
            if (splitLayout.mParallelMultiSplit) {
                splitLayout.mParallelMultiSplit = false;
            }
        }
        if (pendingIntent != null) {
            pendingIntent2 = pendingIntent;
            intent4 = intent;
        } else if (intent != null) {
            pendingIntent2 = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 167772160, null, userHandle);
            intent4 = intent;
        } else {
            intent4 = intent;
            pendingIntent2 = null;
        }
        PendingIntent pendingIntent6 = pendingIntent2;
        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.mContext, 0, intent2, 167772160, null, userHandle2);
        if (intent3 != null) {
            userHandle4 = userHandle3;
            pendingIntent3 = activityAsUser;
            intent5 = intent3;
            pendingIntent4 = PendingIntent.getActivityAsUser(this.mContext, 0, intent5, 167772160, null, userHandle4);
        } else {
            intent5 = intent3;
            userHandle4 = userHandle3;
            pendingIntent3 = activityAsUser;
            pendingIntent4 = null;
        }
        if (pendingIntent3 == null || (i == -1 && pendingIntent6 == null)) {
            Slog.w("StageCoordinator", "startSplitScreen param is wrong. taskId:" + i + ",mainIntent:" + intent4 + ",sideIntent:" + intent2);
            return;
        }
        WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction != null ? windowContainerTransaction : new WindowContainerTransaction();
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mCellStage;
        StageTaskListener stageTaskListener3 = this.mMainStage;
        PendingIntent pendingIntent7 = pendingIntent4;
        SyncTransactionQueue syncTransactionQueue2 = this.mSyncQueue;
        PendingIntent pendingIntent8 = pendingIntent3;
        int i8 = this.mDisplayId;
        if (i4 == 1 && stageTaskListener3.mIsActive) {
            if (isSplitScreenVisible() && remoteTransition != null) {
                remoteTransition = null;
            }
            this.mPausingTasks.clear();
            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
            if (!isSameIntentRequested(stageTaskListener3.getTopRunningTaskInfo(), intent4, userHandle, false)) {
                stageTaskListener3.evictAllChildren(windowContainerTransaction3);
            }
            intent6 = intent2;
            userHandle5 = userHandle2;
            if (!isSameIntentRequested(stageTaskListener.getTopRunningTaskInfo(), intent6, userHandle5, false)) {
                stageTaskListener.evictAllChildren(windowContainerTransaction3);
            }
            if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && stageTaskListener2.mIsActive && !isSameIntentRequested(stageTaskListener2.getTopRunningTaskInfo(), intent5, userHandle4, true)) {
                stageTaskListener2.evictAllChildren(windowContainerTransaction3);
            }
            if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && windowContainerTransaction3.isEmpty() && !MultiWindowUtils.isInSubDisplay(this.mContext) && isSplitScreenVisible() && getSplitDivision() != -1 && getSplitDivision() != i5) {
                addChangeTransitFlagsToStages(windowContainerTransaction3, isMultiSplitScreenVisible());
            }
            if (!windowContainerTransaction3.isEmpty()) {
                if (remoteTransition == null) {
                    windowContainerTransaction3.setDisplayIdForChangeTransition(i8, "evict_all_children");
                }
                syncTransactionQueue2.queue(windowContainerTransaction3);
            }
        } else {
            intent6 = intent2;
            userHandle5 = userHandle2;
        }
        RemoteTransition remoteTransition5 = remoteTransition;
        boolean z4 = CoreRune.MW_MULTI_SPLIT_APP_PAIR;
        if (!z4 || intent5 == null) {
            remoteTransition2 = remoteTransition5;
            if (i2 == 0 || i2 == 1) {
                i6 = i2;
            } else {
                i6 = this.mSideStagePosition;
                if (i6 == -1) {
                    i6 = 1;
                }
            }
            z2 = false;
            setSideStagePosition(i6, i5, windowContainerTransaction2, false);
            i7 = i3;
        } else {
            remoteTransition2 = remoteTransition5;
            stageTaskListener2.activate(null, false);
            MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
            multiSplitLayoutInfo.sideStagePosition = i2;
            multiSplitLayoutInfo.splitDivision = i5;
            i7 = i3;
            multiSplitLayoutInfo.cellStagePosition = i7;
            if (z3 && z) {
                applyParallelMultiSplitLayoutInfo(windowContainerTransaction2, multiSplitLayoutInfo);
                i7 = multiSplitLayoutInfo.cellStagePosition;
                this.mSplitLayout.mParallelMultiSplit = true;
            }
            updateMultiSplitLayout(multiSplitLayoutInfo, false, windowContainerTransaction2);
            z2 = false;
        }
        windowContainerTransaction2.setTransactionType(i4);
        this.mLastTransactionType = i4;
        windowContainerTransaction2.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, z2);
        setRootForceTranslucent(windowContainerTransaction2, z2);
        boolean z5 = stageTaskListener3.mIsActive;
        if (!z5) {
            stageTaskListener3.activate(windowContainerTransaction2, z2);
            this.mSplitLayout.resetDividerPosition();
        }
        this.mSplitLayout.setDivideRatio(f, true, true);
        if (!z4 || intent5 == null) {
            syncTransactionQueue = syncTransactionQueue2;
            updateWindowBounds(this.mSplitLayout, windowContainerTransaction2, false);
        } else {
            syncTransactionQueue = syncTransactionQueue2;
            this.mSplitLayout.setCellDividerRatio(f2, i7, true, false);
            updateWindowBounds(this.mSplitLayout, windowContainerTransaction2, true);
        }
        windowContainerTransaction2.reorder(this.mRootTaskInfo.token, true);
        updateStagePositionIfNeeded(windowContainerTransaction2);
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        addActivityOptions(bundle, stageTaskListener3);
        addActivityOptions(bundle2, stageTaskListener);
        if (i == -1) {
            intent7 = intent;
            windowContainerTransaction2.sendPendingIntent(pendingIntent6, intent7, bundle);
        } else {
            intent7 = intent;
            if (i4 == 2 && z5) {
                bundle.putBoolean("android.activity.splitTaskDeferResume", true);
            }
            windowContainerTransaction2.startTask(i, bundle);
        }
        windowContainerTransaction2.sendPendingIntent(pendingIntent8, intent6, bundle2);
        if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            pendingIntent5 = pendingIntent7;
        } else if (pendingIntent7 != null) {
            Bundle bundle3 = new Bundle();
            addActivityOptions(bundle3, stageTaskListener2);
            pendingIntent5 = pendingIntent7;
            windowContainerTransaction2.sendPendingIntent(pendingIntent5, intent5, bundle3);
        } else {
            pendingIntent5 = pendingIntent7;
            if (isMultiSplitActive()) {
                prepareExitMultiSplitScreen(windowContainerTransaction2, false);
            }
        }
        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction2);
            syncTransactionQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda2(this, 2));
            return;
        }
        if (i4 == 1) {
            this.mAppPairStarted = true;
            if (remoteTransition2 != null) {
                for (ActivityManager.RunningTaskInfo runningTaskInfo : MultiWindowManager.getInstance().getVisibleTasks()) {
                    if (isVisibleTask(runningTaskInfo, intent7, userHandle) || isVisibleTask(runningTaskInfo, intent6, userHandle5) || (CoreRune.MW_MULTI_SPLIT_APP_PAIR && isVisibleTask(runningTaskInfo, intent5, userHandle4))) {
                        Slog.d("StageCoordinator", "startSplitScreen: If there is already a visible task, delete the remote transition because the animation does not look normal. task=" + runningTaskInfo);
                        remoteTransition4 = null;
                        break;
                    }
                }
            }
            remoteTransition4 = remoteTransition2;
            if (remoteTransition4 == null) {
                addChangeTransitFlagsToStages(windowContainerTransaction2, CoreRune.MW_MULTI_SPLIT_APP_PAIR && intent5 != null);
                windowContainerTransaction2.setDisplayIdForChangeTransition(i8, "app_pair");
            } else {
                windowContainerTransaction2.addAdditionalInfo(1);
            }
            remoteTransition3 = remoteTransition4;
        } else {
            remoteTransition3 = remoteTransition2;
        }
        int i9 = pendingIntent5 != null ? VolteConstants.ErrorCode.CALL_SESSION_TERMINATED : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && intent6.getBooleanExtra("start_dnd_split_with_all_apps", false)) {
            i9 = VolteConstants.ErrorCode.CALL_SESSION_TIMEOUT;
        }
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction2, remoteTransition3, this, i9, false, 1);
    }

    public final void startSplitTasks(int i, int i2, int i3, boolean z, int i4, float f, float f2) {
        if (checkNonResizableTaskAndStartTask(i, i2, i3)) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        boolean z2 = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        if (z2 && isMultiSplitActive() && i3 == -1) {
            prepareExitMultiSplitScreen(windowContainerTransaction, false);
        }
        windowContainerTransaction.setTransactionType(3);
        windowContainerTransaction.setDisplayIdForChangeTransition(this.mDisplayId, "split_tasks");
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        StageTaskListener stageTaskListener = this.mMainStage;
        if (stageTaskListener.mIsActive) {
            bundle.putBoolean("android.activity.splitTaskDeferResume", true);
            bundle2.putBoolean("android.activity.splitTaskDeferResume", true);
            if (z2 && i3 != -1) {
                bundle3.putBoolean("android.activity.splitTaskDeferResume", true);
            }
        } else {
            stageTaskListener.activate(windowContainerTransaction, false);
        }
        addActivityOptions(bundle, stageTaskListener);
        addActivityOptions(bundle2, this.mSideStage);
        windowContainerTransaction.startTask(i, bundle);
        windowContainerTransaction.startTask(i2, bundle2);
        if (z2 && i3 != -1) {
            StageTaskListener stageTaskListener2 = this.mCellStage;
            stageTaskListener2.activate(null, false);
            addActivityOptions(bundle3, stageTaskListener2);
            windowContainerTransaction.startTask(i3, bundle3);
        }
        if (!z2 || i3 == -1) {
            setSideStagePosition(1, z ? 1 : 0, windowContainerTransaction, false);
        } else {
            MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
            multiSplitLayoutInfo.sideStagePosition = 1;
            multiSplitLayoutInfo.splitDivision = z ? 1 : 0;
            multiSplitLayoutInfo.cellStagePosition = i4;
            updateMultiSplitLayout(multiSplitLayoutInfo, false, windowContainerTransaction);
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            this.mSplitLayout.setDivideRatio(f, true, false);
        } else {
            this.mSplitLayout.setDivideRatio(f, false, false);
        }
        if (i3 != -1) {
            this.mSplitLayout.setCellDividerRatio(f2, i4, false, (z && (i4 & 32) != 0) || !(z || (i4 & 64) == 0));
        }
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        updateStagePositionIfNeeded(windowContainerTransaction);
        setRootForceTranslucent(windowContainerTransaction, false);
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, (!z2 || i3 == -1) ? VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI : VolteConstants.ErrorCode.CALL_SESSION_TERMINATED, false, 1);
    }

    public final void startTask(int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken, int i3, int i4) {
        boolean z;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -5985507751851902320L, 21, Long.valueOf(i), Long.valueOf(i2), Long.valueOf(i3));
        }
        this.mSplitRequest = new SplitRequest(this, i, i2);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        Bundle resolveStartStage = CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? resolveStartStage(-1, i2, bundle, null, i4) : resolveStartStage(-1, i2, bundle, null, -1);
        if (windowContainerToken != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1580296436628468854L, 0, null);
            }
            windowContainerTransaction.reorder(windowContainerToken, false);
        }
        prepareTasksForSplitScreen(new int[]{i}, windowContainerTransaction);
        windowContainerTransaction.startTask(i, resolveStartStage);
        DefaultMixedHandler defaultMixedHandler = this.mMixedHandler;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        if (defaultMixedHandler != null) {
            PipTransitionController pipTransitionController = defaultMixedHandler.mPipHandler;
            if (pipTransitionController != null) {
                int i5 = ComponentUtils.$r8$clinit;
                ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(i);
                z = pipTransitionController.isPackageActiveInPip(runningTaskInfo == null ? null : ComponentUtils.getPackageName(((TaskInfo) runningTaskInfo).baseIntent));
            } else {
                z = false;
            }
            if (z) {
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                return;
            }
        }
        if (!isSplitScreenVisible()) {
            this.mSkipEvictingMainStageChildren = true;
            setDividerVisibility(null, false);
        }
        if (CoreRune.MW_SPLIT_STACKING && isSplitScreenVisible()) {
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
        } else {
            if (i2 == -1) {
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                return;
            }
            int i6 = this.mMainStage.mIsActive ? 1005 : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
            prepareEnterSplitScreen(windowContainerTransaction, null, i2, !this.mIsDropEntering);
            this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, i6, !this.mIsDropEntering, 1);
        }
    }

    public final void startTaskAndIntent(int i, Intent intent, int i2, int i3, WindowContainerTransaction windowContainerTransaction) {
        startSplitScreen(i, null, null, intent, null, null, UserHandle.CURRENT, null, i2, 0, 0.5f, 0.0f, 2, i3, false, windowContainerTransaction, null);
    }

    public final void startTaskWithAllApps(int i, SplitScreenController.CallerInfo callerInfo, int i2) {
        ActivityManager.RecentTaskInfo recentTaskInfo = getRecentTaskInfo(i);
        if (recentTaskInfo == null) {
            Slog.e("StageCoordinator", "task not found");
            return;
        }
        if (callerInfo != null) {
            Slog.e("StageCoordinator", "startTaskWithAllApps from uid:" + callerInfo.mUid);
        }
        startTaskAndIntent(i, MultiWindowUtils.getEdgeAllAppsActivityIntent(recentTaskInfo.baseIntent.getComponent(), recentTaskInfo.userId, recentTaskInfo.taskId), ((isLandscape() && this.mSplitLayout.mRotation == 3 && !CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) ? 1 : 0) ^ 1, i2, null);
        if (CoreRune.MW_SPLIT_START_EDGE_ALL_APPS_SA_LOGGING && callerInfo != null && "com.sec.android.app.launcher".equalsIgnoreCase(this.mContext.getPackageManager().getNameForUid(callerInfo.mUid))) {
            CoreSaLogger.logForAdvanced("1000", "From recent_option");
        }
    }

    public final void startTaskWithMultiSplit(int i, int i2, Bundle bundle, int i3, int i4, boolean z) {
        StageCoordinator stageCoordinator;
        int i5;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
        if (fromBundle != null) {
            fromBundle.setLaunchedFromDnD(true);
            bundle = fromBundle.toBundle();
        }
        Bundle bundle2 = bundle;
        boolean z2 = CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET;
        StageTaskListener stageTaskListener = this.mMainStage;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        if (z2 && isSplitScreenVisible() && i4 != 0) {
            windowContainerTransaction.startTask(i, resolveStartCellStage(-1, i4, bundle2, windowContainerTransaction));
            if (CoreRune.MW_SPLIT_STACKING && isMultiSplitScreenVisible()) {
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                return;
            }
            boolean z3 = CoreRune.MW_PARALLEL_MULTI_SPLIT;
            if (z3 && z) {
                this.mSplitLayout.mParallelMultiSplit = true;
                MultiSplitLayoutInfo currentMultiSplitLayoutInfo = getCurrentMultiSplitLayoutInfo();
                currentMultiSplitLayoutInfo.cellStagePosition = i4;
                applyParallelMultiSplitLayoutInfo(windowContainerTransaction, currentMultiSplitLayoutInfo);
                this.mSplitLayout.setDivideRatio(this.mSplitLayout.calculateSplitRatioForParallelMultiSplit(currentMultiSplitLayoutInfo), true, true);
            }
            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
                this.mSplitLayout.setCellDividerRatio(0.5f, i4, true, false);
            }
            prepareEnterMultiSplitScreen(windowContainerTransaction, i4);
            if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
                if (z3 && z) {
                    windowContainerTransaction.setChangeTransitMode(stageTaskListener.mRootTaskInfo.token, 1, "enter_multi_fold_split");
                    windowContainerTransaction.setChangeTransitMode(this.mSideStage.mRootTaskInfo.token, 1, "enter_multi_fold_split");
                } else {
                    applyCellHostResizeTransition(windowContainerTransaction);
                }
            }
            i5 = VolteConstants.ErrorCode.CALL_SESSION_ABORT;
            stageCoordinator = this;
        } else {
            stageCoordinator = this;
            windowContainerTransaction.startTask(i, resolveStartStage(-1, i2, bundle2, windowContainerTransaction, i3));
            if (CoreRune.MW_SPLIT_STACKING && stageCoordinator.isSplitScreenVisible()) {
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                return;
            } else {
                i5 = stageTaskListener.mIsActive ? 1005 : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
                stageCoordinator.prepareEnterSplitScreen(windowContainerTransaction, null, i2, false);
            }
        }
        stageCoordinator.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, stageCoordinator, i5, false, 1);
    }

    public final void startTasks(int i, Bundle bundle, int i2, Bundle bundle2, int i3, Bundle bundle3, int i4, int i5, float f, int i6, float f2, RemoteTransition remoteTransition, InstanceId instanceId, int i7, SplitScreenController.CallerInfo callerInfo) {
        int i8;
        int i9;
        int i10;
        int i11 = i;
        int i12 = i4;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -6325240002302691464L, 85, Long.valueOf(i11), Long.valueOf(i2), Long.valueOf(i12), Long.valueOf(i5));
        }
        if (i2 == -1) {
            startTaskWithAllApps(i11, callerInfo, i7);
            return;
        }
        if (checkNonResizableTaskAndStartTask(i11, i2, i3)) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (i2 == -1) {
            startSingleTask(i11, bundle, windowContainerTransaction, remoteTransition);
            return;
        }
        boolean z = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        if (z && isMultiSplitActive() && i3 == -1) {
            prepareExitMultiSplitScreen(windowContainerTransaction, false);
        }
        if (!this.mMainStage.mIsActive || (i10 = this.mSideStagePosition) == -1 || i10 == i12) {
            i8 = i2;
        } else {
            i8 = i11;
            i11 = i2;
            i12 = i10;
        }
        setSideStagePosition(i12, i7, windowContainerTransaction, true);
        if (!z || i3 == -1) {
            i9 = i6;
        } else {
            i9 = i6;
            setCellStageWindowConfigPosition(i9, false);
        }
        Bundle bundle4 = bundle != null ? bundle : new Bundle();
        addActivityOptions(bundle4, this.mSideStage);
        prepareTasksForSplitScreen(new int[]{i11, i8}, windowContainerTransaction);
        windowContainerTransaction.startTask(i11, bundle4);
        startWithTask(windowContainerTransaction, i8, bundle2, i5, f, i3, bundle3, f2, i9, i7, remoteTransition, instanceId, CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY, false, bundle4);
        if (!CoreRune.MW_SPLIT_RECENT_TASKS_SA_LOGGING || remoteTransition == null) {
            return;
        }
        CoreSaLogger.logForAdvanced("1000", "From recent_task");
        if (!z || i3 == -1) {
            return;
        }
        CoreSaLogger.logForAdvanced("1021", "From recent_task");
    }

    public final void startWithTask(WindowContainerTransaction windowContainerTransaction, int i, Bundle bundle, int i2, float f, int i3, Bundle bundle2, float f2, int i4, int i5, RemoteTransition remoteTransition, InstanceId instanceId, boolean z, boolean z2, Bundle bundle3) {
        boolean z3;
        Bundle bundle4;
        StageTaskListener stageTaskListener = this.mMainStage;
        if (!stageTaskListener.mIsActive) {
            stageTaskListener.activate(windowContainerTransaction, false);
        }
        boolean z4 = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        boolean z5 = z4 && i3 != -1;
        StageTaskListener stageTaskListener2 = this.mCellStage;
        if (z5) {
            stageTaskListener2.activate(null, false);
        }
        boolean z6 = CoreRune.MW_PARALLEL_MULTI_SPLIT;
        this.mSplitLayout.setDivideRatio((z6 && this.mSplitLayout.mParallelMultiSplit) ? calculateSplitRatioForParallelMultiSplit(getCurrentMultiSplitLayoutInfo()) : f, z, z2);
        if (!z5 || (z6 && this.mSplitLayout.mParallelMultiSplit)) {
            z3 = false;
        } else {
            z3 = false;
            this.mSplitLayout.setCellDividerRatio(f2, i4, false, (i5 == 1 && (i4 & 32) != 0) || (i5 == 0 && (i4 & 64) != 0));
        }
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, z3);
        updateStagePositionIfNeeded(windowContainerTransaction);
        windowContainerTransaction.setTransactionType(5);
        this.mLastTransactionType = 5;
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, z3);
        setRootForceTranslucent(windowContainerTransaction, z3);
        Bundle bundle5 = bundle != null ? bundle : new Bundle();
        addActivityOptions(bundle5, stageTaskListener);
        windowContainerTransaction.startTask(i, bundle5);
        if (z5) {
            bundle4 = bundle2 != null ? bundle2 : new Bundle();
            addActivityOptions(bundle4, stageTaskListener2);
            windowContainerTransaction.startTask(i3, bundle4);
        } else {
            bundle4 = bundle2;
        }
        if (remoteTransition != null && ((stageTaskListener.hasChild() || this.mSideStage.hasChild()) && bundle3 != null)) {
            bundle5.putBoolean("android.activity.splitTaskDeferResume", true);
            bundle3.putBoolean("android.activity.splitTaskDeferResume", true);
            if (z5) {
                bundle4.putBoolean("android.activity.splitTaskDeferResume", true);
            }
        }
        if (this.mPausingTasks.contains(Integer.valueOf(i))) {
            this.mPausingTasks.clear();
        }
        int i6 = VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
        int i7 = (!z4 || i3 == -1) ? 1004 : VolteConstants.ErrorCode.CALL_SESSION_TERMINATED;
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (z4) {
            i6 = i7;
        }
        splitScreenTransitions.startEnterTransition(windowContainerTransaction, remoteTransition, this, i6, false, i2);
        if (instanceId != null) {
            SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
            splitscreenEventLogger.mEnterSessionId = instanceId;
            splitscreenEventLogger.mEnterReason = 3;
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final boolean supportCompatUI() {
        return false;
    }

    public final void swapCellAndHostStageTasks(WindowContainerTransaction windowContainerTransaction) {
        StageTaskListener stageTaskListenerByStageType = getStageTaskListenerByStageType(getCellHostStageType());
        StageTaskListener stageTaskListenerByStageType2 = getStageTaskListenerByStageType(5);
        if (stageTaskListenerByStageType != null && stageTaskListenerByStageType2 != null) {
            stageTaskListenerByStageType.reparentAllChildren(stageTaskListenerByStageType2.mRootTaskInfo.token, windowContainerTransaction);
            stageTaskListenerByStageType2.reparentAllChildren(stageTaskListenerByStageType.mRootTaskInfo.token, windowContainerTransaction);
            return;
        }
        Slog.w("StageCoordinator", "Cannot swapCellAndHostStageTasks, stage1=" + stageTaskListenerByStageType + ",stage2=" + stageTaskListenerByStageType2 + ",callers=" + Debug.getCallers(3));
    }

    public final void swapStageTasks(int i, int i2, WindowContainerTransaction windowContainerTransaction) {
        StageTaskListener stageTaskListenerByStageType = getStageTaskListenerByStageType(i);
        StageTaskListener stageTaskListenerByStageType2 = getStageTaskListenerByStageType(i2);
        if (stageTaskListenerByStageType != null && stageTaskListenerByStageType2 != null) {
            stageTaskListenerByStageType.reparentAllChildren(stageTaskListenerByStageType2.mRootTaskInfo.token, windowContainerTransaction);
            stageTaskListenerByStageType2.reparentAllChildren(stageTaskListenerByStageType.mRootTaskInfo.token, windowContainerTransaction);
            return;
        }
        Slog.w("StageCoordinator", "Cannot swapStageTasks, stage1=" + stageTaskListenerByStageType + ",stage2=" + stageTaskListenerByStageType2 + ",callers=" + Debug.getCallers(3));
    }

    public final void swapTasksInSplitScreenMode$1() {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mMainStage.mRootTaskInfo;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mSideStage.mRootTaskInfo;
        if (runningTaskInfo == null || runningTaskInfo2 == null) {
            Slog.e("StageCoordinator", "swapTasksInSplitScreenMode: main or side running task is empty");
            return;
        }
        setSideStagePosition(SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition), -1, windowContainerTransaction, false);
        windowContainerTransaction.setChangeTransitMode(runningTaskInfo.token, 1, "swap_split");
        windowContainerTransaction.setChangeTransitMode(runningTaskInfo2.token, 1, "swap_split");
        SplitLayout splitLayout = this.mSplitLayout;
        int i = splitLayout.mDividerPosition;
        DividerSnapAlgorithm dividerSnapAlgorithm = splitLayout.getDividerSnapAlgorithm();
        int i2 = dividerSnapAlgorithm.mFirstSplitTarget.position;
        int i3 = dividerSnapAlgorithm.mLastSplitTarget.position;
        if (i2 <= i && i3 >= i) {
            i = (i2 + i3) - i;
        }
        this.mSplitLayout.updateSnapAlgorithm(i);
        this.mSplitLayout.setDividePosition(this.mSplitLayout.getDividerSnapAlgorithm().calculateSnapTarget(i, true).position, windowContainerTransaction, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v3, types: [com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda28] */
    public final void switchSplitPosition(String str) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1399060535595712440L, 0, null);
        }
        SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        this.mTempRect1.setEmpty();
        int i = this.mSideStagePosition;
        StageTaskListener stageTaskListener = this.mMainStage;
        StageTaskListener stageTaskListener2 = this.mSideStage;
        final StageTaskListener stageTaskListener3 = i == 0 ? stageTaskListener2 : stageTaskListener;
        final StageTaskListener stageTaskListener4 = i == 0 ? stageTaskListener : stageTaskListener2;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setFocusable(this.mRootTaskInfo.token, false);
        this.mSyncQueue.queue(windowContainerTransaction);
        this.mSplitLayout.removeTouchZones();
        this.mSplitLayout.playSwapAnimation(acquire, stageTaskListener3, stageTaskListener4, new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda28
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final StageCoordinator stageCoordinator = StageCoordinator.this;
                StageTaskListener stageTaskListener5 = stageTaskListener3;
                StageTaskListener stageTaskListener6 = stageTaskListener4;
                stageCoordinator.getClass();
                final SplitDecorManager splitDecorManager = stageTaskListener5.mSplitDecorManager;
                final SplitDecorManager splitDecorManager2 = stageTaskListener6.mSplitDecorManager;
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                windowContainerTransaction2.setFocusable(stageCoordinator.mRootTaskInfo.token, true);
                stageCoordinator.setSideStagePosition$1(windowContainerTransaction2, SplitScreenUtils.reverseSplitPosition(stageCoordinator.mSideStagePosition));
                SyncTransactionQueue syncTransactionQueue = stageCoordinator.mSyncQueue;
                syncTransactionQueue.queue(windowContainerTransaction2);
                syncTransactionQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda32
                    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                    public final void runWithTransaction(SurfaceControl.Transaction transaction) {
                        StageCoordinator stageCoordinator2 = StageCoordinator.this;
                        SplitLayout splitLayout = stageCoordinator2.mSplitLayout;
                        splitLayout.mSplitState.mState = splitLayout.calculateCurrentSnapPosition();
                        stageCoordinator2.updateSurfaceBounds(stageCoordinator2.mSplitLayout, transaction, false);
                        stageCoordinator2.mSplitLayout.populateTouchZones();
                        splitDecorManager.fadeOutVeilAndCleanUp(transaction);
                        splitDecorManager2.fadeOutVeilAndCleanUp(transaction);
                    }
                });
            }
        });
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 8065000900149852489L, 0, str);
        }
        int reverseSplitPosition = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
        int topChildTaskUid = stageTaskListener.getTopChildTaskUid();
        int i2 = this.mSideStagePosition;
        int topChildTaskUid2 = stageTaskListener2.getTopChildTaskUid();
        boolean z = this.mSplitLayout.mIsLeftRightSplit;
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        if (splitscreenEventLogger.mLoggerSessionId == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 7942520113985172790L, 0, null);
            }
        } else {
            splitscreenEventLogger.updateMainStageState(SplitscreenEventLogger.getMainStagePositionFromSplitPosition(reverseSplitPosition, z), topChildTaskUid);
            splitscreenEventLogger.updateSideStageState(SplitscreenEventLogger.getSideStagePositionFromSplitPosition(i2, z), topChildTaskUid2);
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 7611404824230378646L, 341, Long.valueOf(splitscreenEventLogger.mLastMainStagePosition), Long.valueOf(splitscreenEventLogger.mLastMainStageUid), Long.valueOf(splitscreenEventLogger.mLastSideStagePosition), Long.valueOf(splitscreenEventLogger.mLastSideStageUid), Long.valueOf(splitscreenEventLogger.mLoggerSessionId.getId()));
            }
            FrameworkStatsLog.write(388, 5, 0, 0, 0.0f, splitscreenEventLogger.mLastMainStagePosition, splitscreenEventLogger.mLastMainStageUid, splitscreenEventLogger.mLastSideStagePosition, splitscreenEventLogger.mLastSideStageUid, 0, splitscreenEventLogger.mLoggerSessionId.getId());
        }
    }

    public final void updateCornerRadiusForStages(SurfaceControl.Transaction transaction) {
        Context displayContext = this.mDisplayController.getDisplayContext(0);
        if (displayContext == null) {
            return;
        }
        float roundedCornerRadius = MultiWindowUtils.getRoundedCornerRadius(displayContext);
        boolean z = transaction != null;
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction acquire = transaction != null ? transaction : transactionPool.acquire();
        boolean applyCornerRadiusToLeashIfNeeded = this.mCellStage.applyCornerRadiusToLeashIfNeeded(roundedCornerRadius, acquire, z) | this.mMainStage.applyCornerRadiusToLeashIfNeeded(roundedCornerRadius, acquire, z) | this.mSideStage.applyCornerRadiusToLeashIfNeeded(roundedCornerRadius, acquire, z);
        if (transaction == null) {
            if (applyCornerRadiusToLeashIfNeeded) {
                acquire.apply();
            }
            transactionPool.release(acquire);
        }
    }

    public final boolean updateCoverDisplaySplitLayoutIfNeeded() {
        Configuration configuration;
        SplitLayout splitLayout;
        if (!isInSubDisplay() || (configuration = this.mTmpConfigAfterFoldDismiss) == null || (splitLayout = this.mSplitLayout) == null || !splitLayout.updateConfiguration(configuration)) {
            return false;
        }
        updateSplitDivisionIfNeeded();
        return true;
    }

    public final void updateDividerLeashVisible(boolean z) {
        SurfaceControl dividerLeash = this.mSplitLayout.getDividerLeash();
        if (dividerLeash == null || !dividerLeash.isValid()) {
            Slog.w("StageCoordinator", "updateDividerLeashVisible: leash was released or not be created");
            return;
        }
        this.mDividerLeashHidden = !z;
        float f = z ? 1.0f : 0.0f;
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction acquire = transactionPool.acquire();
        acquire.setAlpha(dividerLeash, f).apply();
        transactionPool.release(acquire);
        Slog.d("StageCoordinator", "updateDividerLeashVisible: " + dividerLeash + ", show=" + z + "");
    }

    public final void updateMultiSplitLayout(MultiSplitLayoutInfo multiSplitLayoutInfo, boolean z, WindowContainerTransaction windowContainerTransaction) {
        setSideStagePosition(multiSplitLayoutInfo.sideStagePosition, multiSplitLayoutInfo.splitDivision, windowContainerTransaction, false);
        setCellStageWindowConfigPosition(multiSplitLayoutInfo.cellStagePosition, true);
        this.mSplitLayout.updateCellStageWindowConfigPosition(this.mCellStageWindowConfigPosition);
        if (this.mSideStage.mVisible && z) {
            onLayoutSizeChanged(this.mSplitLayout, windowContainerTransaction);
        }
    }

    public final void updateRecentTasksSplitPair() {
        if (this.mShouldUpdateRecents && this.mPausingTasks.isEmpty()) {
            this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda14(3, this));
        } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1621558214698676699L, 0, !this.mShouldUpdateRecents ? "shouldn't update" : "no pausing tasks");
        }
    }

    public final void updateSplitDivisionIfNeeded() {
        int i;
        if (isInSubDisplay()) {
            if ((CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && isApplyFoldingPolicy(false)) || this.mSplitDivision == (i = !isLandscape() ? 1 : 0)) {
                return;
            }
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Update split division for SubDisplay. d=", "  Call=");
            m.append(Debug.getCallers(5));
            Slog.i("StageCoordinator", m.toString());
            setSplitDivision(i, true, true);
        }
    }

    public final void updateStagePositionIfNeeded(WindowContainerTransaction windowContainerTransaction) {
        int mainStageWinConfigPosition = getMainStageWinConfigPosition();
        int sideStageWinConfigPosition = getSideStageWinConfigPosition();
        boolean z = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        boolean z2 = false;
        int i = z ? this.mCellStageWindowConfigPosition : 0;
        if (sideStageWinConfigPosition == this.mLastReportedSideStageWinConfigPosition && i == this.mLastReportedCellStageWinConfigPosition) {
            z2 = true;
        }
        if (mainStageWinConfigPosition != this.mLastReportedMainStageWinConfigPosition || (z && !z2)) {
            this.mLastReportedMainStageWinConfigPosition = mainStageWinConfigPosition;
            windowContainerTransaction.setStagePosition(this.mMainStage.mRootTaskInfo.token, mainStageWinConfigPosition);
            windowContainerTransaction.setStagePosition(this.mSideStage.mRootTaskInfo.token, sideStageWinConfigPosition);
            if (z) {
                this.mLastReportedSideStageWinConfigPosition = sideStageWinConfigPosition;
                this.mLastReportedCellStageWinConfigPosition = i;
                windowContainerTransaction.setStagePosition(this.mCellStage.mRootTaskInfo.token, i);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0431 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0485  */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x03bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSurfaceBounds(com.android.wm.shell.common.split.SplitLayout r18, android.view.SurfaceControl.Transaction r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 1188
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.updateSurfaceBounds(com.android.wm.shell.common.split.SplitLayout, android.view.SurfaceControl$Transaction, boolean):void");
    }

    public final boolean updateWindowBounds(SplitLayout splitLayout, WindowContainerTransaction windowContainerTransaction, boolean z) {
        int i = this.mSideStagePosition;
        StageTaskListener stageTaskListener = this.mMainStage;
        StageTaskListener stageTaskListener2 = this.mSideStage;
        StageTaskListener stageTaskListener3 = i == 0 ? stageTaskListener2 : stageTaskListener;
        if (i != 0) {
            stageTaskListener = stageTaskListener2;
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            StageTaskListener stageTaskListener4 = this.mCellStage;
            if (stageTaskListener4.mIsActive || z) {
                return splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener3.mRootTaskInfo, stageTaskListener.mRootTaskInfo, stageTaskListener4.mRootTaskInfo);
            }
        }
        boolean applyTaskChanges = splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener3.mRootTaskInfo, stageTaskListener.mRootTaskInfo);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -2337050910502079842L, 0, String.valueOf(splitLayout.getTopLeftBounds()), String.valueOf(splitLayout.getBottomRightBounds()));
        }
        return applyTaskChanges;
    }

    public boolean willSleepOnFold() {
        FoldLockSettingsObserver foldLockSettingsObserver = this.mFoldLockSettingsObserver;
        return foldLockSettingsObserver != null && foldLockSettingsObserver.isSleepOnFold();
    }

    public final void prepareSplitDismissChangeTransition(WindowContainerTransaction windowContainerTransaction, int i, TransitionRequestInfo transitionRequestInfo, boolean z) {
        StageTaskListener stageTaskListener;
        int i2;
        ActivityManager.RunningTaskInfo triggerTask;
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && isMultiSplitActive()) {
            prepareMultiSplitDismissChangeTransition(i, windowContainerTransaction, z);
            return;
        }
        int i3 = 0;
        if (i == 0) {
            stageTaskListener = this.mSideStage;
            i2 = 1;
        } else {
            stageTaskListener = this.mMainStage;
            i2 = 0;
        }
        WindowContainerToken stageToken = getStageToken(i);
        WindowContainerToken stageToken2 = getStageToken(i2);
        if (stageToken == null || stageToken2 == null) {
            Slog.w("StageCoordinator", "prepareSplitDismissChangeTransition: failed, dismissStageToken=" + stageToken + ", expandStageToken=" + stageToken2);
            return;
        }
        if (transitionRequestInfo != null && (triggerTask = transitionRequestInfo.getTriggerTask()) != null && transitionRequestInfo.getType() == 4) {
            windowContainerTransaction.setChangeTransitMode(triggerTask.token, 2, "split_to_close(triggerTask)");
        }
        boolean z2 = this.mMovingToFreeformTaskToken != null;
        if (!z2) {
            if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && this.mContext.getResources().getConfiguration().isNewDexMode()) {
                windowContainerTransaction.orderedSetChangeTransitMode(stageToken, 2, "split_to_close");
            } else {
                windowContainerTransaction.setChangeTransitMode(stageToken, 2, "split_to_close");
            }
        }
        if (z) {
            i3 = 4;
        } else if (z2 && stageTaskListener.hasAppsEdgeActivityOnTop()) {
            windowContainerTransaction.setDisplayIdForChangeTransition(this.mDisplayId, "split_to_freeform(hasAppsEdge)");
            windowContainerTransaction.addChangeTransitFlags(this.mMovingToFreeformTaskToken, 1);
            windowContainerTransaction.setTransactionType(6);
        } else {
            i3 = stageTaskListener.hasAppsEdgeActivityOnTop() ? 2 : 1;
        }
        if (i3 != 0) {
            if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && this.mContext.getResources().getConfiguration().isNewDexMode()) {
                windowContainerTransaction.orderedSetChangeTransitMode(stageToken2, i3, "split_to_full");
            } else {
                windowContainerTransaction.setChangeTransitMode(stageToken2, i3, "split_to_full");
            }
        }
        if (CoreRune.MW_SA_LOGGING) {
            CoreSaLogger.logForAdvanced("2090", "From split dismiss");
        }
        Slog.d("StageCoordinator", "prepareSplitDismissChangeTransition: dismiss=" + SplitScreen.stageTypeToString(i) + ", expand=" + SplitScreen.stageTypeToString(i2) + ", hasMovingToFreeform=" + z2);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SplitRequest {
        public final int mActivatePosition;
        public final int mActivateTaskId;
        public final int mActivateTaskId2;
        public final Intent mStartIntent;
        public final Intent mStartIntent2;

        public SplitRequest(StageCoordinator stageCoordinator, int i, Intent intent, int i2) {
            this.mActivateTaskId = i;
            this.mStartIntent = intent;
            this.mActivatePosition = i2;
        }

        public SplitRequest(StageCoordinator stageCoordinator, Intent intent, int i) {
            this.mStartIntent = intent;
            this.mActivatePosition = i;
        }

        public SplitRequest(StageCoordinator stageCoordinator, Intent intent, Intent intent2, int i) {
            this.mStartIntent = intent;
            this.mStartIntent2 = intent2;
            this.mActivatePosition = i;
        }

        public SplitRequest(StageCoordinator stageCoordinator, int i, int i2) {
            this.mActivateTaskId = i;
            this.mActivatePosition = i2;
        }

        public SplitRequest(StageCoordinator stageCoordinator, int i, int i2, int i3) {
            this.mActivateTaskId = i;
            this.mActivateTaskId2 = i2;
            this.mActivatePosition = i3;
        }
    }

    public final StageTaskListener getStageOfTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        StageTaskListener stageTaskListener;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        ActivityManager.RunningTaskInfo runningTaskInfo3 = stageTaskListener2.mRootTaskInfo;
        if (runningTaskInfo3 != null && runningTaskInfo.parentTaskId == runningTaskInfo3.taskId) {
            return stageTaskListener2;
        }
        StageTaskListener stageTaskListener3 = this.mSideStage;
        ActivityManager.RunningTaskInfo runningTaskInfo4 = stageTaskListener3.mRootTaskInfo;
        if (runningTaskInfo4 != null && runningTaskInfo.parentTaskId == runningTaskInfo4.taskId) {
            return stageTaskListener3;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && (runningTaskInfo2 = (stageTaskListener = this.mCellStage).mRootTaskInfo) != null && runningTaskInfo.parentTaskId == runningTaskInfo2.taskId) {
            return stageTaskListener;
        }
        return null;
    }

    public StageCoordinator(Context context, int i, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer shellTaskOrganizer, StageTaskListener stageTaskListener, StageTaskListener stageTaskListener2, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, SplitLayout splitLayout, Transitions transitions, TransactionPool transactionPool, ShellExecutor shellExecutor, Handler handler, Optional<RecentTasksController> optional, LaunchAdjacentController launchAdjacentController, Optional<WindowDecorViewModel> optional2, SplitState splitState, Optional<DesktopTasksController> optional3, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DesktopState desktopState) {
        int i2 = 0;
        new Rect();
        this.mSplitDivision = 0;
        this.mLastMainSplitDivision = 0;
        Configuration configuration = new Configuration();
        this.mLastConfiguration = configuration;
        this.mIsMultiSplitRotating = false;
        this.mChangingParallelMultiSplit = false;
        this.mUpdateCoverDisplaySplitLayout = false;
        this.mParentContainerCallbacks = new AnonymousClass1();
        if (CoreRune.MW_MULTI_SPLIT_BACKGROUND) {
            new RecentsTransitionCallback(i2, this);
        }
        this.mSplitLayoutChangedForLaunchAdjacent = false;
        new ArrayList();
        new ArrayList();
        new ArrayList();
        new ArrayList();
        this.mHandleSplitWithAIAssistTimeoutRunnable = new StageCoordinator$$ExternalSyntheticLambda6(4, this);
        this.mContext = context;
        this.mDisplayId = i;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mMainStage = stageTaskListener;
        this.mSideStage = stageTaskListener2;
        this.mTransitions = transitions;
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mTransactionPool = transactionPool;
        this.mSplitLayout = splitLayout;
        this.mSplitTransitions = new SplitScreenTransitions(transactionPool, transitions, new StageCoordinator$$ExternalSyntheticLambda6(5, this), this);
        this.mLogger = new SplitscreenEventLogger();
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mRecentTasks = optional;
        this.mLaunchAdjacentController = launchAdjacentController;
        this.mWindowDecorViewModel = optional2;
        this.mSplitState = splitState;
        this.mDesktopTasksController = optional3;
        this.mRootTDAOrganizer = rootTaskDisplayAreaOrganizer;
        this.mDesktopState = desktopState;
        displayController.addDisplayWindowListener(this, -1);
        this.mSplitLayout.mStageCoordinator = this;
        transitions.addHandler(this);
        this.mSplitUnsupportedToast = Toast.makeText(context, R.string.dock_non_resizeble_failed_to_dock_text, 0);
        FoldLockSettingsObserver foldLockSettingsObserver = new FoldLockSettingsObserver(context.getMainThreadHandler(), context);
        this.mFoldLockSettingsObserver = foldLockSettingsObserver;
        foldLockSettingsObserver.register();
        SplitBackgroundController splitBackgroundController = new SplitBackgroundController(context, this, transactionPool, shellExecutor, displayController);
        this.mSplitBackgroundController = splitBackgroundController;
        registerSplitScreenListener(splitBackgroundController);
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
            configuration.updateFrom(context.getResources().getConfiguration());
        }
    }
}
