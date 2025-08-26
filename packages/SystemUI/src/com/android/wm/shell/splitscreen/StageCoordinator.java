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
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceState;
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
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.util.SparseIntArray;
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
import com.android.internal.policy.AttributeCache;
import com.android.internal.policy.FoldLockSettingsObserver;
import com.android.internal.protolog.ProtoLog;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.FrameworkStatsLog;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
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
import com.android.wm.shell.common.split.ResizingEffectPolicy;
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
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shared.split.SplitBounds;
import com.android.wm.shell.shared.split.SplitScreenConstants;
import com.android.wm.shell.splitscreen.SplitBackgroundController;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.SplitScreenTransitions;
import com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitSession;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.OneShotRemoteHandler;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.util.StageUtils;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

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
    public int mDeviceState;
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

    /* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$1, reason: invalid class name */
    public class AnonymousClass1 implements SplitWindowManager.ParentContainerCallbacks {
        public AnonymousClass1() {
        }

        public final void inflateOnStageRoot(OffscreenTouchZone offscreenTouchZone) {
            StageCoordinator stageCoordinator = StageCoordinator.this;
            int i = stageCoordinator.mSideStagePosition;
            SurfaceControl surfaceControl = (i == 1 ? stageCoordinator.mMainStage : stageCoordinator.mSideStage).mRootLeash;
            SurfaceControl surfaceControl2 = (i == 1 ? stageCoordinator.mSideStage : stageCoordinator.mMainStage).mRootLeash;
            Context contextCreateConfigurationContext = stageCoordinator.mContext.createConfigurationContext(stageCoordinator.mRootTaskInfo.configuration);
            Configuration configuration = stageCoordinator.mRootTaskInfo.configuration;
            if (!offscreenTouchZone.mIsTopLeft) {
                surfaceControl = surfaceControl2;
            }
            View view = new View(contextCreateConfigurationContext);
            view.setOnTouchListener(new OffscreenTouchZone.OffscreenTouchListener(offscreenTouchZone, 0));
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2022, 8, -3);
            layoutParams.token = new Binder();
            layoutParams.setTitle("OffscreenTouchZone");
            layoutParams.privateFlags |= 536870976;
            view.setLayoutParams(layoutParams);
            SurfaceControl.Builder callsite = new SurfaceControl.Builder().setContainerLayer().setName("OffscreenTouchZone".concat(offscreenTouchZone.mIsTopLeft ? "TopLeft" : "BottomRight")).setCallsite("OffscreenTouchZone::init");
            callsite.setParent(surfaceControl);
            SurfaceControl surfaceControlBuild = callsite.build();
            offscreenTouchZone.mLeash = surfaceControlBuild;
            SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(contextCreateConfigurationContext, contextCreateConfigurationContext.getDisplay(), new WindowlessWindowManager(configuration, surfaceControlBuild, (InputTransferToken) null), "SplitTouchZones");
            offscreenTouchZone.mViewHost = surfaceControlViewHost;
            surfaceControlViewHost.setView(view, layoutParams);
            final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setLayer(surfaceControlBuild, Integer.MAX_VALUE);
            transaction.show(surfaceControlBuild);
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

    public class DeviceMultiFoldStateListener implements DeviceStateManager.DeviceStateCallback {
        public /* synthetic */ DeviceMultiFoldStateListener(int i, StageCoordinator stageCoordinator) {
            this();
        }

        public final void onDeviceStateChanged(DeviceState deviceState) {
            int identifier = deviceState.getIdentifier();
            if (StageCoordinator.this.mDeviceState != identifier) {
                KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("onDeviceStateChanged, oldState="), StageCoordinator.this.mDeviceState, ", newState=", identifier, "StageCoordinator");
                StageCoordinator.this.mDeviceState = identifier;
            }
        }

        private DeviceMultiFoldStateListener() {
        }
    }

    public class RecentsTransitionCallback {
        public /* synthetic */ RecentsTransitionCallback(int i, StageCoordinator stageCoordinator) {
            this();
        }

        private RecentsTransitionCallback() {
        }
    }

    public class StageChangeRecord {
        public boolean mContainShowFullscreenChange = false;
        public final ArrayMap mChanges = new ArrayMap();

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
        this.mDeviceState = -1;
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
        DeviceStateManager deviceStateManager = (DeviceStateManager) context2.getSystemService(DeviceStateManager.class);
        deviceStateManager.registerCallback(shellTaskOrganizer.getExecutor(), new DeviceStateManager.FoldStateListener(context2, new StageCoordinator$$ExternalSyntheticLambda14(0, this)));
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
        if (CoreRune.MW_MULTI_FOLD_SPLIT_FOLDING_POLICY) {
            deviceStateManager.registerCallback(shellTaskOrganizer.getExecutor(), new DeviceMultiFoldStateListener(i2, this));
        }
    }

    public static void addActivityOptions(Bundle bundle, StageTaskListener stageTaskListener) {
        ActivityOptions activityOptionsFromBundle = ActivityOptions.fromBundle(bundle);
        if (stageTaskListener != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1218188467398233829L, 0, String.valueOf(SplitScreen.stageTypeToString(stageTaskListener.mId)));
            }
            activityOptionsFromBundle.setLaunchRootTask(stageTaskListener.mRootTaskInfo.token);
        }
        activityOptionsFromBundle.setStartedFromWindowTypeLauncher(true);
        activityOptionsFromBundle.setLaunchDisplayId(0);
        activityOptionsFromBundle.setPendingIntentBackgroundActivityStartMode(3);
        activityOptionsFromBundle.setDisallowEnterPictureInPictureWhileLaunching(true);
        bundle.putAll(activityOptionsFromBundle.toBundle());
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
        int iConvertCreateMode = convertCreateMode(multiSplitLayoutInfo);
        if (iConvertCreateMode == 2) {
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
        if (iConvertCreateMode == 3) {
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
        if (iConvertCreateMode == 4) {
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
        if (iConvertCreateMode != 5) {
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
            SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
            transactionAcquire.hide(cellDividerLeash);
            transactionAcquire.apply();
            transactionPool.release(transactionAcquire);
            return;
        }
        final SurfaceControl.Transaction transactionAcquire2 = transactionPool.acquire();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mCellDividerFadeInAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new StageCoordinator$$ExternalSyntheticLambda10(this, cellDividerLeash, transactionAcquire2, 1));
        this.mCellDividerFadeInAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SurfaceControl surfaceControl = cellDividerLeash;
                if (surfaceControl != null && surfaceControl.isValid()) {
                    transactionAcquire2.setAlpha(cellDividerLeash, 1.0f);
                    transactionAcquire2.apply();
                }
                StageCoordinator.this.mTransactionPool.release(transactionAcquire2);
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
                stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, transactionAcquire2, false);
                transactionAcquire2.show(cellDividerLeash);
                transactionAcquire2.setAlpha(cellDividerLeash, 0.0f);
                transactionAcquire2.apply();
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
        if (CoreRune.MW_MULTI_FOLD_SPLIT_FOLDING_POLICY && transaction != null && isDeviceHalfClosed()) {
            transaction.setVisibility(dividerLeash, this.mDividerVisible);
            return;
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
                final SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mDividerFadeInAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new StageCoordinator$$ExternalSyntheticLambda10(this, dividerLeash, transactionAcquire, 0));
                this.mDividerFadeInAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        SurfaceControl surfaceControl = dividerLeash;
                        if (surfaceControl != null && surfaceControl.isValid()) {
                            transactionAcquire.setAlpha(dividerLeash, 1.0f);
                            transactionAcquire.apply();
                        }
                        StageCoordinator.this.mTransactionPool.release(transactionAcquire);
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
                        stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, transactionAcquire, false);
                        transactionAcquire.show(dividerLeash);
                        transactionAcquire.setAlpha(dividerLeash, 0.0f);
                        transactionAcquire.apply();
                    }
                });
                this.mDividerFadeInAnimator.start();
            } else {
                SurfaceControl.Transaction transactionAcquire2 = transactionPool.acquire();
                transactionAcquire2.hide(dividerLeash);
                transactionAcquire2.apply();
                transactionPool.release(transactionAcquire2);
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
                final StageCoordinator stageCoordinator = this.f$0;
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
                    Runnable runnable = new Runnable() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda24
                        @Override // java.lang.Runnable
                        public final void run() {
                            StageCoordinator stageCoordinator2 = stageCoordinator;
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
        int iConvertCreateMode = convertCreateMode(multiSplitLayoutInfo);
        multiSplitLayoutInfo.splitDivision = !isLandscape() ? 1 : 0;
        if (iConvertCreateMode == 2 || iConvertCreateMode == 3) {
            if (multiSplitLayoutInfo.cellStagePosition != 24) {
                swapCellAndHostStageTasks(windowContainerTransaction);
            }
            multiSplitLayoutInfo.cellStagePosition = 24;
        } else if (iConvertCreateMode == 4 || iConvertCreateMode == 5) {
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

    public final void closeHandleMenuIfNeeded() {
        DesktopModeWindowDecoration focusedDecor;
        StageTaskListener stageTaskListener = this.mMainStage;
        if (!stageTaskListener.isFocused()) {
            stageTaskListener = this.mSideStage;
        }
        if (!stageTaskListener.mWindowDecorViewModel.isPresent() || (focusedDecor = ((DesktopModeWindowDecorViewModel) stageTaskListener.mWindowDecorViewModel.get()).getFocusedDecor()) == null) {
            return;
        }
        focusedDecor.closeHandleMenu();
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

    /* JADX WARN: Removed duplicated region for block: B:36:0x0069 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dismissSplitTask(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction, boolean z) {
        int i;
        int i2;
        boolean zHasAppsEdgeActivityOnTop;
        boolean z2 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (!z2 || !isMultiSplitActive()) {
            if (stageTaskListener2.containsToken(windowContainerToken)) {
                zHasAppsEdgeActivityOnTop = stageTaskListener.hasAppsEdgeActivityOnTop();
                i = 1;
                i2 = 0;
            } else if (stageTaskListener.containsToken(windowContainerToken)) {
                zHasAppsEdgeActivityOnTop = stageTaskListener2.hasAppsEdgeActivityOnTop();
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
                    prepareAndStartDismissTransition(i, i2, windowContainerTransaction, z, zHasAppsEdgeActivityOnTop);
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
        if (!stageTaskListener3.containsToken(windowContainerToken)) {
            if (stageTaskListener4.containsToken(windowContainerToken)) {
                i3 = cellHostStageType;
            } else if (stageTaskListener.containsToken(windowContainerToken)) {
                cellHostStageType = i3;
            } else {
                cellHostStageType = -1;
                i3 = cellHostStageType;
            }
            if (i != -1) {
            }
            Slog.w("StageCoordinator", "dismissSplitTask: failed, cannot find " + windowContainerToken);
        }
        i3 = 5;
        i = cellHostStageType;
        i2 = i3;
        zHasAppsEdgeActivityOnTop = false;
        if (i != -1) {
        }
        Slog.w("StageCoordinator", "dismissSplitTask: failed, cannot find " + windowContainerToken);
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    @NeverCompile
    public final void dump$2(PrintWriter printWriter, String str) {
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        String strM2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, "  ");
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "StageCoordinator mDisplayId=");
        sbM.append(this.mDisplayId);
        printWriter.println(sbM.toString());
        StringBuilder sb = new StringBuilder();
        sb.append(strM);
        sb.append("mDividerVisible=");
        StringBuilder sbM2 = BackAnimationController$$ExternalSyntheticOutline0.m(sb, this.mDividerVisible, printWriter, strM, "isSplitActive=");
        StageTaskListener stageTaskListener = this.mMainStage;
        StringBuilder sbM3 = BackAnimationController$$ExternalSyntheticOutline0.m(sbM2, stageTaskListener.mIsActive, printWriter, strM, "isSplitVisible=");
        sbM3.append(isSplitScreenVisible());
        printWriter.println(sbM3.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(strM);
        sb2.append("isLeftRightSplit=");
        SplitLayout splitLayout = this.mSplitLayout;
        sb2.append(splitLayout != null ? Boolean.valueOf(splitLayout.mIsLeftRightSplit) : "null");
        printWriter.println(sb2.toString());
        printWriter.println(strM + "MainStage");
        StringBuilder sb3 = new StringBuilder();
        sb3.append(strM2);
        sb3.append("stagePosition=");
        int iReverseSplitPosition = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
        sb3.append(iReverseSplitPosition != -1 ? iReverseSplitPosition != 0 ? iReverseSplitPosition != 1 ? "UNKNOWN" : "SPLIT_POSITION_BOTTOM_OR_RIGHT" : "SPLIT_POSITION_TOP_OR_LEFT" : "SPLIT_POSITION_UNDEFINED");
        printWriter.println(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(strM2);
        sb4.append("isActive=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb4, stageTaskListener.mIsActive, printWriter);
        stageTaskListener.dump$2(printWriter, strM2);
        printWriter.println(strM + "SideStage");
        StringBuilder sb5 = new StringBuilder();
        sb5.append(strM2);
        sb5.append("stagePosition=");
        int i = this.mSideStagePosition;
        CarrierTextController$$ExternalSyntheticOutline0.m(sb5, i != -1 ? i != 0 ? i != 1 ? "UNKNOWN" : "SPLIT_POSITION_BOTTOM_OR_RIGHT" : "SPLIT_POSITION_TOP_OR_LEFT" : "SPLIT_POSITION_UNDEFINED", printWriter);
        this.mSideStage.dump$2(printWriter, strM2);
        SplitLayout splitLayout2 = this.mSplitLayout;
        if (splitLayout2 != null) {
            String strM3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM2, "\t");
            printWriter.println(strM2 + "SplitLayout:");
            StringBuilder sb6 = new StringBuilder();
            sb6.append(strM3);
            sb6.append("mAllowLeftRightSplitInPortrait=");
            StringBuilder sbM4 = BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb6, splitLayout2.mAllowLeftRightSplitInPortrait, printWriter, strM3, "mIsLeftRightSplit="), splitLayout2.mIsLeftRightSplit, printWriter, strM3, "mFreezeDividerWindow="), splitLayout2.mFreezeDividerWindow, printWriter, strM3, "mDimNonImeSide="), splitLayout2.mDimNonImeSide, printWriter, strM3, "mDividerPosition=");
            sbM4.append(splitLayout2.mDividerPosition);
            printWriter.println(sbM4.toString());
            printWriter.println(strM3 + "bounds1=" + splitLayout2.getTopLeftBounds().toShortString());
            printWriter.println(strM3 + "dividerBounds=" + splitLayout2.mDividerBounds.toShortString());
            printWriter.println(strM3 + "bounds2=" + splitLayout2.getBottomRightBounds().toShortString());
            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
                StringBuilder sbM5 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strM2, "bounds3=");
                sbM5.append(splitLayout2.mBounds3.toShortString());
                printWriter.println(sbM5.toString());
            }
            if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
                MagnificationImpl$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strM2, "splitDivision="), splitLayout2.mSplitDivision, printWriter);
            }
        }
        if (!this.mPausingTasks.isEmpty()) {
            StringBuilder sbM6 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strM2, "mPausingTasks=");
            sbM6.append(this.mPausingTasks);
            printWriter.println(sbM6.toString());
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            printWriter.println(strM + "CellStage");
            printWriter.println(strM2 + "stagePosition=" + WindowConfiguration.stagePositionToString(this.mCellStageWindowConfigPosition));
            this.mCellStage.dump$2(printWriter, strM2);
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

    /* JADX WARN: Removed duplicated region for block: B:38:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0181  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void finishEnterSplitScreen(SurfaceControl.Transaction transaction, boolean z) {
        int i;
        int i2;
        long j;
        long id;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -3613640443892843881L, 0, null);
        }
        SplitLayout splitLayout = this.mSplitLayout;
        splitLayout.mSplitState.mState = splitLayout.calculateCurrentSnapPosition();
        this.mSplitLayout.update(null, true);
        StageTaskListener stageTaskListener = this.mMainStage;
        stageTaskListener.mSplitDecorManager.inflate(this.mContext, stageTaskListener.mRootLeash);
        StageTaskListener stageTaskListener2 = this.mSideStage;
        stageTaskListener2.mSplitDecorManager.inflate(this.mContext, stageTaskListener2.mRootLeash);
        setDividerVisibility(transaction, true);
        transaction.reparent(this.mSplitLayout.getDividerLeash(), this.mRootTaskLeash);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && z) {
            SplitLayout splitLayout2 = this.mSplitLayout;
            splitLayout2.releaseCellDivider(transaction);
            if (!splitLayout2.mCellInitialized) {
                splitLayout2.mCellInitialized = true;
                splitLayout2.mCellSplitWindowManager.init(splitLayout2, splitLayout2.mInsetsState, false, splitLayout2.mDesktopState);
                splitLayout2.mCellSnapAlgorithm = splitLayout2.createCellSnapAlgorithm();
            }
            StageTaskListener stageTaskListener3 = this.mCellStage;
            stageTaskListener3.mSplitDecorManager.inflate(this.mContext, stageTaskListener3.mRootLeash);
            setCellDividerVisibility(transaction, true);
            transaction.reparent(this.mSplitLayout.getCellDividerLeash(), this.mRootTaskLeash);
        }
        updateSurfaceBounds(this.mSplitLayout, transaction, false);
        transaction.show(this.mRootTaskLeash);
        setSplitsVisible(true);
        this.mIsDropEntering = false;
        this.mSkipEvictingMainStageChildren = false;
        this.mSplitRequest = null;
        updateRecentTasksSplitPair();
        float dividerPositionAsFraction = this.mSplitLayout.getDividerPositionAsFraction();
        int iReverseSplitPosition = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
        int topChildTaskUid = stageTaskListener.getTopChildTaskUid();
        int i3 = this.mSideStagePosition;
        int topChildTaskUid2 = stageTaskListener2.getTopChildTaskUid();
        boolean z2 = this.mSplitLayout.mIsLeftRightSplit;
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        if (splitscreenEventLogger.mLoggerSessionId != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -188572275768151446L, 0, null);
                return;
            }
            return;
        }
        splitscreenEventLogger.mLoggerSessionId = splitscreenEventLogger.mIdSequence.newInstanceId();
        int i4 = splitscreenEventLogger.mEnterReason;
        if (i4 != 1) {
            i = 3;
            i2 = 2;
            if (i4 == 2) {
                int i5 = splitscreenEventLogger.mDragEnterPosition;
                if (z2) {
                    if (i5 != 0) {
                        i = 4;
                    }
                } else if (i5 != 0) {
                    i = 5;
                }
            } else if (i4 != 3) {
                i2 = 0;
            } else {
                i = 6;
            }
            splitscreenEventLogger.updateMainStageState(SplitscreenEventLogger.getMainStagePositionFromSplitPosition(iReverseSplitPosition, z2), topChildTaskUid);
            splitscreenEventLogger.updateSideStageState(SplitscreenEventLogger.getSideStagePositionFromSplitPosition(i3, z2), topChildTaskUid2);
            if (Float.compare(splitscreenEventLogger.mLastSplitRatio, dividerPositionAsFraction) != 0) {
                splitscreenEventLogger.mLastSplitRatio = dividerPositionAsFraction;
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                long j2 = i2;
                double d = dividerPositionAsFraction;
                long j3 = splitscreenEventLogger.mLastMainStagePosition;
                long j4 = splitscreenEventLogger.mLastMainStageUid;
                long j5 = splitscreenEventLogger.mLastSideStagePosition;
                long j6 = splitscreenEventLogger.mLastSideStageUid;
                InstanceId instanceId = splitscreenEventLogger.mEnterSessionId;
                if (instanceId != null) {
                    j = j2;
                    id = instanceId.getId();
                } else {
                    j = j2;
                    id = 0;
                }
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1995399227079995721L, 95577, Long.valueOf(j), Double.valueOf(d), Long.valueOf(j3), Long.valueOf(j4), Long.valueOf(j5), Long.valueOf(j6), Boolean.valueOf(z2), Long.valueOf(id), Long.valueOf(splitscreenEventLogger.mLoggerSessionId.getId()));
            }
            int i6 = splitscreenEventLogger.mLastMainStagePosition;
            int i7 = splitscreenEventLogger.mLastMainStageUid;
            int i8 = splitscreenEventLogger.mLastSideStagePosition;
            int i9 = splitscreenEventLogger.mLastSideStageUid;
            InstanceId instanceId2 = splitscreenEventLogger.mEnterSessionId;
            FrameworkStatsLog.write(388, 1, i2, 0, dividerPositionAsFraction, i6, i7, i8, i9, instanceId2 == null ? instanceId2.getId() : 0, splitscreenEventLogger.mLoggerSessionId.getId());
        }
        i = 7;
        i2 = i;
        splitscreenEventLogger.updateMainStageState(SplitscreenEventLogger.getMainStagePositionFromSplitPosition(iReverseSplitPosition, z2), topChildTaskUid);
        splitscreenEventLogger.updateSideStageState(SplitscreenEventLogger.getSideStagePositionFromSplitPosition(i3, z2), topChildTaskUid2);
        if (Float.compare(splitscreenEventLogger.mLastSplitRatio, dividerPositionAsFraction) != 0) {
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
        }
        int i62 = splitscreenEventLogger.mLastMainStagePosition;
        int i72 = splitscreenEventLogger.mLastMainStageUid;
        int i82 = splitscreenEventLogger.mLastSideStagePosition;
        int i92 = splitscreenEventLogger.mLastSideStageUid;
        InstanceId instanceId22 = splitscreenEventLogger.mEnterSessionId;
        FrameworkStatsLog.write(388, 1, i2, 0, dividerPositionAsFraction, i62, i72, i82, i92, instanceId22 == null ? instanceId22.getId() : 0, splitscreenEventLogger.mLoggerSessionId.getId());
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
        boolean z = CoreRune.MW_PARALLEL_MULTI_SPLIT;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mCellStage;
        StageTaskListener stageTaskListener3 = this.mMainStage;
        if (z && this.mSplitLayout.mParallelMultiSplit) {
            if ((getMainStageWinConfigPosition() & 64) != 0) {
                arrayList.add(stageTaskListener3.mRootTaskInfo);
                if (getCellHostStageType() == 0) {
                    arrayList.add(stageTaskListener2.mRootTaskInfo);
                } else {
                    arrayList.add(stageTaskListener.mRootTaskInfo);
                }
            }
            if ((getSideStageWinConfigPosition() & 64) != 0) {
                arrayList.add(stageTaskListener.mRootTaskInfo);
                if (getCellHostStageType() == 1) {
                    arrayList.add(stageTaskListener2.mRootTaskInfo);
                    return arrayList;
                }
                arrayList.add(stageTaskListener3.mRootTaskInfo);
                return arrayList;
            }
        } else {
            if ((getMainStageWinConfigPosition() & 64) != 0) {
                arrayList.add(stageTaskListener3.mRootTaskInfo);
            }
            if ((getSideStageWinConfigPosition() & 64) != 0) {
                arrayList.add(stageTaskListener.mRootTaskInfo);
            }
            if ((this.mCellStageWindowConfigPosition & 64) != 0) {
                arrayList.add(stageTaskListener2.mRootTaskInfo);
            }
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

    /* JADX WARN: Removed duplicated region for block: B:46:0x0063 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x006b A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getMainStageWinConfigPosition() {
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            StageTaskListener stageTaskListener = this.mCellStage;
            if (stageTaskListener.mIsActive) {
                if (this.mMainStage.equals(stageTaskListener.mHost)) {
                    return isVerticalDivision() ? (this.mCellStageWindowConfigPosition & 16) != 0 ? this.mSideStagePosition == 0 ? 96 : 72 : this.mSideStagePosition == 0 ? 48 : 24 : (this.mCellStageWindowConfigPosition & 8) != 0 ? this.mSideStagePosition == 0 ? 96 : 48 : this.mSideStagePosition == 0 ? 72 : 24;
                }
            }
            return isVerticalDivision() ? this.mSideStagePosition == 0 ? 32 : 8 : this.mSideStagePosition == 0 ? 64 : 16;
        }
        if (isLandscape()) {
            if (this.mSideStagePosition == 0) {
                return 32;
            }
        } else if (this.mSideStagePosition == 0) {
            return 64;
        }
    }

    public final Rect getSideStageBounds() {
        return (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && isMultiSplitActive() && getCellHostStageType() == 1) ? this.mSplitLayout.getHostBounds() : this.mSideStagePosition == 0 ? this.mSplitLayout.getTopLeftBounds() : this.mSplitLayout.getBottomRightBounds();
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0060 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0068 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getSideStageWinConfigPosition() {
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            StageTaskListener stageTaskListener = this.mCellStage;
            if (stageTaskListener.mIsActive) {
                if (this.mSideStage.equals(stageTaskListener.mHost)) {
                    return isVerticalDivision() ? (this.mCellStageWindowConfigPosition & 16) != 0 ? this.mSideStagePosition == 0 ? 72 : 96 : this.mSideStagePosition == 0 ? 24 : 48 : (this.mCellStageWindowConfigPosition & 8) != 0 ? this.mSideStagePosition == 0 ? 48 : 96 : this.mSideStagePosition == 0 ? 24 : 72;
                }
            }
            return isVerticalDivision() ? this.mSideStagePosition == 0 ? 8 : 32 : this.mSideStagePosition == 0 ? 16 : 64;
        }
        if (isLandscape()) {
            if (this.mSideStagePosition != 0) {
                return 32;
            }
        } else if (this.mSideStagePosition != 0) {
            return 64;
        }
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
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitLayout.mParallelMultiSplit) {
            if ((getMainStageWinConfigPosition() & 16) != 0) {
                return getCellHostStageType() == 0 ? this.mSplitLayout.getBounds3().bottom : getMainStageBounds().bottom;
            }
            if ((getSideStageWinConfigPosition() & 16) != 0) {
                return getCellHostStageType() == 1 ? this.mSplitLayout.getBounds3().bottom : getSideStageBounds().bottom;
            }
            return 0;
        }
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
        int iReverseSplitPosition = this.mSideStagePosition;
        if (iReverseSplitPosition == 1) {
            if (z) {
                iReverseSplitPosition = SplitScreenUtils.reverseSplitPosition(iReverseSplitPosition);
            }
        } else if (!z) {
            iReverseSplitPosition = SplitScreenUtils.reverseSplitPosition(iReverseSplitPosition);
        }
        IActivityTaskManager iActivityTaskManagerAsInterface = IActivityTaskManager.Stub.asInterface(ServiceManager.getService("activity_task"));
        int topVisibleChildTaskId = -1;
        if (iReverseSplitPosition != -1) {
            try {
                topVisibleChildTaskId = this.mSideStagePosition == iReverseSplitPosition ? this.mSideStage.getTopVisibleChildTaskId() : this.mMainStage.getTopVisibleChildTaskId();
            } catch (RemoteException | NullPointerException e) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[4]) {
                    ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -7228576009982558496L, 0, String.valueOf(e.getMessage()));
                    return;
                }
                return;
            }
        }
        iActivityTaskManagerAsInterface.setFocusedTask(topVisibleChildTaskId);
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
        boolean zIsSplitScreenVisible = isSplitScreenVisible();
        if (z2 && !zIsSplitScreenVisible) {
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
        boolean zApplyTaskChanges = (z3 && stageTaskListener5.mIsActive) ? splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener3.mRootTaskInfo, stageTaskListener4.mRootTaskInfo, stageTaskListener5.mRootTaskInfo) : splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener3.mRootTaskInfo, stageTaskListener4.mRootTaskInfo);
        if (Transitions.ENABLE_SHELL_TRANSITIONS && zApplyTaskChanges && zIsSplitScreenVisible) {
            this.mSplitLayout.setDividerInteractive("handleLayoutSizeChange", false, false);
            SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
            StageCoordinator$$ExternalSyntheticLambda0 stageCoordinator$$ExternalSyntheticLambda0 = new StageCoordinator$$ExternalSyntheticLambda0(0, this);
            StageCoordinator$$ExternalSyntheticLambda0 stageCoordinator$$ExternalSyntheticLambda02 = new StageCoordinator$$ExternalSyntheticLambda0(4, this);
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
            SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
            stageCoordinator.updateSurfaceBounds(splitLayout, transactionAcquire, false);
            stageTaskListener.onResized(transactionAcquire);
            stageTaskListener2.onResized(transactionAcquire);
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                stageTaskListener5.onResized(transactionAcquire);
            }
            transactionAcquire.apply();
            transactionPool.release(transactionAcquire);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02a8  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x038a  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x03a1  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x049e  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x04e1 A[PHI: r5
      0x04e1: PHI (r5v16 boolean) = (r5v14 boolean), (r5v11 boolean), (r5v17 boolean) binds: [B:298:0x04df, B:292:0x04ca, B:286:0x04b8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:300:0x04e3  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x04e7  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0503  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x050a  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x052a  */
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
    */
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        boolean z;
        StageTaskListener stageTaskListener;
        int i;
        TransitionRequestInfo transitionRequestInfo2;
        int i2;
        int activityType;
        boolean z2;
        boolean z3;
        boolean z4;
        AttributeCache.Entry entry;
        boolean z5;
        Intent intent;
        AlertDialog alertDialog;
        int i3;
        IBinder iBinder2;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        boolean z6;
        int stageOfTask;
        IBinder iBinder3;
        boolean z7;
        StageTaskListener stageTaskListener2;
        int stageType;
        int size;
        ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
        StageTaskListener stageTaskListener3 = this.mMainStage;
        if (triggerTask != null) {
            int i4 = triggerTask.displayId;
            int i5 = this.mDisplayId;
            if (i4 == i5) {
                int type = transitionRequestInfo.getType();
                boolean zIsOpeningType = TransitionUtil.isOpeningType(type);
                ?? r15 = triggerTask.getWindowingMode() == 1;
                ?? r1 = this.mDesktopTasksController.isPresent() && ((DesktopTasksController) this.mDesktopTasksController.get()).isDesktopModeShowing(i5);
                ?? r5 = zIsOpeningType && ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode && triggerTask.getWindowingMode() == 5;
                StageTaskListener stageOfTask2 = getStageOfTask(triggerTask);
                boolean z8 = CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY;
                ?? r9 = z8 && triggerTask.getWindowingMode() == 5;
                this.mIsTaskOpening = zIsOpeningType;
                if (r1 == false && r5 == false) {
                    if ((zIsOpeningType && r15 != false) || (!stageTaskListener3.mIsActive && zIsOpeningType && r9 != false)) {
                        this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda17(triggerTask, 0));
                        logExit(13);
                    }
                    this.mIsOpeningHomeDuringSplit = false;
                    Runnable runnable = this.mHandleSplitWithAIAssistTimeoutRunnable;
                    HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
                    if (handlerExecutor.mHandler.hasCallbacks(runnable)) {
                        handlerExecutor.removeCallbacks(this.mHandleSplitWithAIAssistTimeoutRunnable);
                    }
                    boolean z9 = stageTaskListener3.mIsActive;
                    ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
                    if (z9) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -3120441554095620640L, 1, Long.valueOf(transitionRequestInfo.getDebugId()));
                        }
                        boolean z10 = ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1];
                        StageTaskListener stageTaskListener4 = this.mSideStage;
                        if (z10) {
                            i = type;
                            stageTaskListener = stageOfTask2;
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 9176187796528510634L, 81, Long.valueOf(triggerTask.taskId), String.valueOf(Transitions.transitTypeToString(type)), Long.valueOf(stageTaskListener3.getChildCount()), Long.valueOf(stageTaskListener4.getChildCount()));
                        } else {
                            stageTaskListener = stageOfTask2;
                            i = type;
                        }
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        StageTaskListener stageTaskListener5 = this.mCellStage;
                        if (stageTaskListener != null) {
                            boolean zIsClosingType = TransitionUtil.isClosingType(i);
                            StageTaskListener stageTaskListener6 = stageTaskListener;
                            StageTaskListener.RunningTaskInfoList runningTaskInfoList = stageTaskListener6.mRunningTaskInfoList;
                            if (zIsClosingType) {
                                if (stageTaskListener6.getChildCount() != 1) {
                                    if (CoreRune.MW_SPLIT_STACKING) {
                                        if (runningTaskInfoList == null) {
                                            size = stageTaskListener6.mChildrenTaskInfo.mTaskIds.size();
                                        } else if (runningTaskInfoList.mClosingTaskIds.isEmpty()) {
                                            size = runningTaskInfoList.mTaskIds.size();
                                        } else {
                                            ArrayList arrayList = runningTaskInfoList.mTaskIds;
                                            int size2 = arrayList.size();
                                            int i6 = 0;
                                            int i7 = 0;
                                            while (i7 < size2) {
                                                Object obj = arrayList.get(i7);
                                                i7++;
                                                if (!runningTaskInfoList.mClosingTaskIds.contains((Integer) obj)) {
                                                    i6++;
                                                }
                                            }
                                            size = i6;
                                        }
                                        if (size != 1) {
                                        }
                                    }
                                }
                                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                                }
                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                                    iBinder3 = iBinder;
                                    if (getStageType(stageTaskListener6) != 0) {
                                    }
                                    prepareExitSplitScreen(i, 2, windowContainerTransaction, true);
                                    if (CoreRune.MW_MULTI_SPLIT_BACKGROUND) {
                                    }
                                    this.mSplitTransitions.setDismissTransition(iBinder3, i, 2, false);
                                    iBinder2 = iBinder3;
                                    i3 = 13;
                                    if (!CoreRune.MW_SPLIT_STACKING) {
                                    }
                                }
                            } else {
                                if (triggerTask.isAiKeyRemoveAppTask) {
                                    if (stageTaskListener6.getChildCount() == 0) {
                                        z6 = true;
                                        if (!z6) {
                                            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                                                transitionRequestInfo2 = transitionRequestInfo;
                                            } else {
                                                transitionRequestInfo2 = transitionRequestInfo;
                                                prepareSplitDismissChangeTransition(windowContainerTransaction, getStageType(stageTaskListener6), transitionRequestInfo2);
                                            }
                                            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !stageTaskListener5.mIsActive) {
                                                iBinder3 = iBinder;
                                                int i8 = getStageType(stageTaskListener6) != 0 ? 1 : 0;
                                                prepareExitSplitScreen(i8, 2, windowContainerTransaction, true);
                                                if (CoreRune.MW_MULTI_SPLIT_BACKGROUND) {
                                                    SplitBackgroundController splitBackgroundController = this.mSplitBackgroundController;
                                                    splitBackgroundController.getClass();
                                                    if (splitBackgroundController.mOneshotStartAlpha != 0.6f) {
                                                        splitBackgroundController.mOneshotStartAlpha = 0.6f;
                                                    }
                                                }
                                                this.mSplitTransitions.setDismissTransition(iBinder3, i8, 2, false);
                                            } else {
                                                int stageType2 = getStageType(stageTaskListener6);
                                                if (stageType2 == 0) {
                                                    stageTaskListener2 = stageTaskListener3;
                                                    z7 = true;
                                                } else {
                                                    z7 = true;
                                                    stageTaskListener2 = stageType2 == 1 ? stageTaskListener4 : null;
                                                }
                                                if (stageTaskListener2 != null) {
                                                    stageType = getStageType(stageTaskListener2);
                                                    reparentCellToMainOrSide(windowContainerTransaction, stageTaskListener2, z7);
                                                } else {
                                                    stageType = getStageType(stageTaskListener5);
                                                    prepareExitMultiSplitScreen(windowContainerTransaction, false);
                                                }
                                                iBinder3 = iBinder;
                                                this.mSplitTransitions.setDismissTransition(iBinder3, stageType, 2, z7);
                                            }
                                            iBinder2 = iBinder3;
                                            i3 = 13;
                                        }
                                        if (!CoreRune.MW_SPLIT_STACKING && TransitionUtil.isClosingType(i)) {
                                            int i9 = triggerTask.taskId;
                                            if (runningTaskInfoList != null && !runningTaskInfoList.mClosingTaskIds.contains(Integer.valueOf(i9))) {
                                                runningTaskInfoList.mClosingTaskIds.add(Integer.valueOf(i9));
                                                Slog.d("StageTaskListener", "addToClosingTaskIds: #" + i9 + ", " + StageTaskListener.this);
                                            }
                                        } else if (r15 != false && isSplitScreenVisible()) {
                                            stageOfTask = getStageOfTask(triggerTask.taskId);
                                            if (!triggerTask.supportsMultiWindow || stageOfTask != -1) {
                                                prepareExitSplitScreen(stageOfTask, i3, windowContainerTransaction, true);
                                                this.mSplitTransitions.setDismissTransition(iBinder2, stageOfTask, i3, false);
                                            }
                                        }
                                    } else {
                                        for (int size3 = stageTaskListener6.mChildrenTaskInfo.mTaskIds.size() - 1; size3 >= 0; size3--) {
                                            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) stageTaskListener6.mChildrenTaskInfo.valueAt(size3);
                                            if (runningTaskInfo2 == null || !runningTaskInfo2.isAiKeyRemoveAppTask) {
                                                z6 = false;
                                                break;
                                            }
                                        }
                                        z6 = true;
                                        if (!z6) {
                                        }
                                        if (!CoreRune.MW_SPLIT_STACKING) {
                                            if (r15 != false) {
                                                stageOfTask = getStageOfTask(triggerTask.taskId);
                                                if (!triggerTask.supportsMultiWindow) {
                                                    prepareExitSplitScreen(stageOfTask, i3, windowContainerTransaction, true);
                                                    this.mSplitTransitions.setDismissTransition(iBinder2, stageOfTask, i3, false);
                                                }
                                            }
                                        }
                                    }
                                }
                                transitionRequestInfo2 = transitionRequestInfo;
                                if (isSplitScreenVisible() || !zIsOpeningType) {
                                    i3 = 13;
                                    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isSplitScreenVisible() && zIsOpeningType && !isMultiSplitActive() && getStageType(stageTaskListener6) == 5) {
                                        int i10 = this.mCellStageWindowConfigPosition;
                                        this.mSplitLayout.setCellDividerRatio(0.5f, i10, true, false);
                                        prepareEnterMultiSplitScreen(windowContainerTransaction, i10);
                                        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
                                            applyCellHostResizeTransition(windowContainerTransaction);
                                        }
                                        iBinder2 = iBinder;
                                        this.mSplitTransitions.setEnterTransition(iBinder2, transitionRequestInfo2.getRemoteTransition(), VolteConstants.ErrorCode.CALL_SESSION_ABORT, false, 1);
                                    } else {
                                        iBinder2 = iBinder;
                                    }
                                } else {
                                    setSideStagePositionByAdjacentTask(windowContainerTransaction, triggerTask);
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                                        runningTaskInfo = null;
                                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2994550463633307045L, 0, null);
                                    } else {
                                        runningTaskInfo = null;
                                    }
                                    prepareEnterSplitScreen(windowContainerTransaction, runningTaskInfo, -1, !this.mIsDropEntering);
                                    iBinder2 = iBinder;
                                    i3 = 13;
                                    this.mSplitTransitions.setEnterTransition(iBinder2, transitionRequestInfo2.getRemoteTransition(), VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, !this.mIsDropEntering, 1);
                                }
                                if (!CoreRune.MW_SPLIT_STACKING) {
                                }
                            }
                        } else {
                            transitionRequestInfo2 = transitionRequestInfo;
                            if (zIsOpeningType && r15 == true) {
                                this.mSplitLayout.mSplitWindowManager.sendSplitStateChangedInfo(true);
                                this.mLastSplitStateInfo = null;
                                if (z8 && isApplyFoldingPolicy(false)) {
                                    int activityType2 = triggerTask.getActivityType();
                                    if (activityType2 == 2 || activityType2 == 3) {
                                        z5 = true;
                                    } else {
                                        z5 = true;
                                        prepareExitSplitScreen(-1, 3, windowContainerTransaction, true);
                                    }
                                    setSplitsVisible(false);
                                    WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                    windowContainerTransaction2.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, z5);
                                    shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
                                }
                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                boolean z11 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                                if (z11) {
                                    ActivityInfo activityInfo = triggerTask.topActivityInfo;
                                    if (((activityInfo == null || triggerTask.numActivities != 1 || (entry = AttributeCache.instance().get(activityInfo.packageName, activityInfo.getThemeResource(), com.android.internal.R.styleable.Window)) == null) ? true : !ActivityInfo.isTranslucentOrFloating(entry.array)) && !this.mIsRecentsInSplitAnimating) {
                                        char c = (stageTaskListener3.hasAppsEdgeActivityOnTop() || stageTaskListener3.getChildCount() == 0) ? (char) 0 : (stageTaskListener4.hasAppsEdgeActivityOnTop() || stageTaskListener4.getChildCount() == 0) ? (char) 1 : (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageTaskListener5.mIsActive && (stageTaskListener5.hasAppsEdgeActivityOnTop() || stageTaskListener5.getChildCount() == 0)) ? (char) 5 : (char) 65535;
                                        if (c != 0 && c != 1) {
                                            if (c == 5) {
                                                prepareExitMultiSplitScreen(windowContainerTransaction3, false);
                                                setCellSplitVisible(false);
                                            }
                                            z4 = true;
                                        } else if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageTaskListener5.mIsActive) {
                                            z4 = true;
                                            reparentCellToMainOrSide(windowContainerTransaction3, c == 0 ? stageTaskListener3 : stageTaskListener4, true);
                                            setCellSplitVisible(false);
                                        } else {
                                            z4 = true;
                                            i2 = 2;
                                            prepareExitSplitScreen(c == 0 ? 1 : 0, 2, windowContainerTransaction3, true);
                                            setSplitsVisible$1(false, true);
                                            windowContainerTransaction.merge(windowContainerTransaction3, z4);
                                        }
                                        i2 = 2;
                                        windowContainerTransaction.merge(windowContainerTransaction3, z4);
                                    }
                                    activityType = triggerTask.getActivityType();
                                    if (activityType != i2) {
                                    }
                                    if (!isSplitScreenVisible()) {
                                        if (this.mSplitTransitions.mPendingEnter != null) {
                                        }
                                        WindowContainerTransaction windowContainerTransaction4 = new WindowContainerTransaction();
                                        windowContainerTransaction4.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
                                        shellTaskOrganizer.applyTransaction(windowContainerTransaction4);
                                        removeDividerWhenSplitInactive();
                                        if (z11) {
                                            if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
                                            }
                                            return windowContainerTransaction3;
                                        }
                                    }
                                } else {
                                    i2 = 2;
                                    activityType = triggerTask.getActivityType();
                                    if (activityType != i2 || activityType == 3) {
                                        if (!isSplitScreenVisible() || this.mSplitTransitions.mPendingEnter != null) {
                                            if (this.mSplitTransitions.mPendingEnter != null) {
                                                this.mIsOpeningHomeDuringSplit = true;
                                            }
                                            WindowContainerTransaction windowContainerTransaction42 = new WindowContainerTransaction();
                                            windowContainerTransaction42.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
                                            shellTaskOrganizer.applyTransaction(windowContainerTransaction42);
                                        }
                                        removeDividerWhenSplitInactive();
                                        if (z11 && !windowContainerTransaction3.isEmpty()) {
                                            if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
                                                windowContainerTransaction3.setDisplayIdForChangeTransition(i5, "evict_all_children");
                                            }
                                            return windowContainerTransaction3;
                                        }
                                    } else {
                                        if (stageTaskListener3.mChildrenTaskInfo.contains(triggerTask.taskId)) {
                                            z2 = true;
                                            if (stageTaskListener3.getChildCount() == 1) {
                                                z3 = z2;
                                                if (z3) {
                                                    prepareExitSplitScreen(-1, 13, windowContainerTransaction, z2);
                                                }
                                                removeDividerWhenSplitInactive();
                                                this.mSplitState.mState = 10;
                                            }
                                        } else {
                                            z2 = true;
                                        }
                                        if (!stageTaskListener4.mChildrenTaskInfo.contains(triggerTask.taskId) || stageTaskListener4.getChildCount() != z2) {
                                            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                                                if (stageTaskListener5.mChildrenTaskInfo.contains(triggerTask.taskId)) {
                                                    z2 = true;
                                                    if (stageTaskListener5.getChildCount() == 1) {
                                                    }
                                                    if (z3) {
                                                    }
                                                    removeDividerWhenSplitInactive();
                                                    this.mSplitState.mState = 10;
                                                } else {
                                                    z2 = true;
                                                }
                                                z3 = false;
                                                if (z3) {
                                                }
                                                removeDividerWhenSplitInactive();
                                                this.mSplitState.mState = 10;
                                            }
                                        }
                                    }
                                }
                            } else if (i == 8 && triggerTask.topActivity != null && isSplitScreenVisible()) {
                                int i11 = !triggerTask.topActivity.equals(stageTaskListener3.mRootTaskInfo.topActivity) ? 1 : 0;
                                prepareExitSplitScreen(i11, 8, windowContainerTransaction, true);
                                this.mSplitTransitions.setDismissTransition(iBinder, i11, 8, false);
                            }
                        }
                        if (windowContainerTransaction.isEmpty()) {
                            if (isSplitScreenVisible()) {
                                boolean z12 = (stageTaskListener3.getChildCount() == 0 || stageTaskListener4.getChildCount() == 0) ? false : true;
                                if (zIsOpeningType && (alertDialog = this.mSplitLayout.mSplitWindowManager.mDividerPanel.mAddToAppPairDialog) != null) {
                                    alertDialog.dismiss();
                                }
                                ActivityManager.RunningTaskInfo triggerTask2 = transitionRequestInfo2.getTriggerTask();
                                if (!((triggerTask2 != null && (intent = triggerTask2.baseIntent) != null && (intent.getFlags() & 4096) != 0) && z12)) {
                                }
                            }
                        }
                        return windowContainerTransaction;
                    }
                    if (stageOfTask2 != null) {
                        if (!zIsOpeningType) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -8356695535571945587L, 1, Long.valueOf(transitionRequestInfo.getDebugId()));
                            }
                            WindowContainerTransaction windowContainerTransaction5 = new WindowContainerTransaction();
                            this.mSplitTransitions.setEnterTransition(iBinder, transitionRequestInfo.getRemoteTransition(), 1005, false, 1);
                            return windowContainerTransaction5;
                        }
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                            z = true;
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -8859917926934956411L, 1, Long.valueOf(transitionRequestInfo.getDebugId()));
                        } else {
                            z = true;
                        }
                        WindowContainerTransaction windowContainerTransaction6 = new WindowContainerTransaction();
                        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && getStageType(getStageOfTask(triggerTask)) == 5) {
                            prepareExitMultiSplitScreen(windowContainerTransaction6, z);
                            shellTaskOrganizer.applyTransaction(windowContainerTransaction6);
                            return null;
                        }
                        setSideStagePositionByAdjacentTask(windowContainerTransaction6, triggerTask);
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2994550463633307045L, 0, null);
                        }
                        prepareEnterSplitScreen(windowContainerTransaction6, null, -1, !this.mIsDropEntering);
                        this.mSplitTransitions.setEnterTransition(iBinder, transitionRequestInfo.getRemoteTransition(), VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, !this.mIsDropEntering, 1);
                        return windowContainerTransaction6;
                    }
                    return null;
                }
            }
        } else if (stageTaskListener3.mIsActive) {
            if (this.mKeyguardActive && (transitionRequestInfo.getFlags() & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
                Slog.d("StageCoordinator", "handleRequest: split active, but AOD appearing transition");
                return null;
            }
            if (transitionRequestInfo.getDisplayChange() == null || transitionRequestInfo.getDisplayChange().getDisconnectReparentDisplay() == -1) {
                if (CoreRune.MW_MULTI_FOLD_SPLIT_FOLDING_POLICY && isDeviceHalfClosed()) {
                    Slog.d("StageCoordinator", "handleRequest: split active, but device half closed");
                    return null;
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 914707202565812593L, 1, Long.valueOf(transitionRequestInfo.getDebugId()));
                }
                TransitionRequestInfo.DisplayChange displayChange = transitionRequestInfo.getDisplayChange();
                if (transitionRequestInfo.getType() == 6 && displayChange != null && displayChange.getStartRotation() != displayChange.getEndRotation()) {
                    this.mSplitLayout.mFreezeDividerWindow = true;
                }
                if (transitionRequestInfo.getRemoteTransition() != null) {
                    SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
                    RemoteTransition remoteTransition = transitionRequestInfo.getRemoteTransition();
                    splitScreenTransitions.getClass();
                    splitScreenTransitions.mPendingRemotePassthrough = splitScreenTransitions.new TransitSession(iBinder, null, null, remoteTransition, 1018);
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1486730673797123039L, 0, null);
                    }
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3031695091370835604L, 1, 1018L, String.valueOf(remoteTransition));
                    }
                }
                return new WindowContainerTransaction();
            }
        }
        return null;
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

    public final boolean isDeviceHalfClosed() {
        return this.mDeviceState == 6;
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

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0026, code lost:
    
        r3 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isSameIntentRequested(TaskInfo taskInfo, Intent intent, UserHandle userHandle, boolean z) {
        boolean z2;
        if (taskInfo != null && intent != null && userHandle != null && intent.getComponent() != null && taskInfo.baseActivity != null) {
            if (taskInfo.isAliasManaged) {
                z2 = z ? false : false;
                if (!intent.getComponent().getPackageName().equals(taskInfo.baseActivity.getPackageName())) {
                }
            } else {
                if (!intent.getComponent().getPackageName().equals(taskInfo.baseActivity.getPackageName()) && taskInfo.userId == userHandle.getIdentifier() && !z2) {
                    return true;
                }
            }
        }
        return false;
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
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "logExit: failed to logging, splitLayout is null, exitReason=", ", Callers=");
        sbM.append(Debug.getCallers(5));
        Slog.e("StageCoordinator", sbM.toString());
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
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("logExitToStage: failed to logging, splitLayout is null, exitReason=", i, ", toMainStage=", z, ", Callers=");
        sbM.append(Debug.getCallers(5));
        Slog.e("StageCoordinator", sbM.toString());
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
                if (!change.hasFlags(64) || change.getMode() != 2) {
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
        Rect stageBounds;
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
            } else {
                if (!this.mSideStage.containsToken(windowContainerToken)) {
                    if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !this.mCellStage.containsToken(windowContainerToken)) {
                        stageBounds = null;
                    }
                    if (stageBounds != null && !stageBounds.isEmpty()) {
                        windowContainerTransaction.setChangeTransitStartBounds(windowContainerToken, stageBounds);
                    }
                    windowContainerTransaction.setChangeTransitMode(windowContainerToken, i2, "split_to_freeform");
                    dismissSplitTask(windowContainerToken, windowContainerTransaction, z);
                    this.mMovingToFreeformTaskToken = null;
                }
                i = 1;
            }
            stageBounds = getStageBounds(i);
            if (stageBounds != null) {
                windowContainerTransaction.setChangeTransitStartBounds(windowContainerToken, stageBounds);
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onChildTaskStatusChanged(StageTaskListener stageTaskListener, int i, boolean z, boolean z2) {
        int i2;
        StageTaskListener stageTaskListener2 = this.mSideStage;
        if (z) {
            i2 = 0;
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                if (stageTaskListener == stageTaskListener2) {
                    i2 = 1;
                } else if (stageTaskListener == this.mCellStage) {
                    i2 = 5;
                }
            } else if (stageTaskListener == stageTaskListener2) {
            }
        } else {
            i2 = -1;
        }
        StageTaskListener stageTaskListener3 = this.mMainStage;
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        if (i2 == 0) {
            int iReverseSplitPosition = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
            int topChildTaskUid = stageTaskListener3.getTopChildTaskUid();
            boolean z3 = this.mSplitLayout.mIsLeftRightSplit;
            if (splitscreenEventLogger.mLoggerSessionId != null && splitscreenEventLogger.updateMainStageState(SplitscreenEventLogger.getMainStagePositionFromSplitPosition(iReverseSplitPosition, z3), topChildTaskUid)) {
                FrameworkStatsLog.write(388, 3, 0, 0, 0.0f, splitscreenEventLogger.mLastMainStagePosition, splitscreenEventLogger.mLastMainStageUid, 0, 0, 0, splitscreenEventLogger.mLoggerSessionId.getId());
            }
        } else if (i2 == 1) {
            int i3 = this.mSideStagePosition;
            int topChildTaskUid2 = stageTaskListener2.getTopChildTaskUid();
            boolean z4 = this.mSplitLayout.mIsLeftRightSplit;
            if (splitscreenEventLogger.mLoggerSessionId != null && splitscreenEventLogger.updateSideStageState(SplitscreenEventLogger.getSideStagePositionFromSplitPosition(i3, z4), topChildTaskUid2)) {
                FrameworkStatsLog.write(388, 3, 0, 0, 0.0f, 0, 0, splitscreenEventLogger.mLastSideStagePosition, splitscreenEventLogger.mLastSideStageUid, 0, splitscreenEventLogger.mLoggerSessionId.getId());
            }
        }
        if (z && z2) {
            updateRecentTasksSplitPair();
        } else if (stageTaskListener3.getChildCount() == 0) {
            stageTaskListener2.getChildCount();
        }
        for (int size = ((ArrayList) this.mListeners).size() - 1; size >= 0; size--) {
            ((SplitScreen.SplitScreenListener) ((ArrayList) this.mListeners).get(size)).onTaskStageChanged(i, i2, z2);
        }
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
            /* JADX WARN: Removed duplicated region for block: B:100:0x01e0  */
            @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onDisplayChange(int i2, int i3, int i4, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) throws Resources.NotFoundException {
                boolean z;
                StageCoordinator stageCoordinator;
                int i5;
                int i6;
                int i7;
                SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus;
                StageCoordinator stageCoordinator2 = this.f$0;
                if (i2 != 0) {
                    stageCoordinator2.getClass();
                    return;
                }
                if (stageCoordinator2.mMainStage.mIsActive) {
                    boolean z2 = CoreRune.MW_MULTI_FOLD_SPLIT_FOLDING_POLICY;
                    if (z2 && displayAreaInfo == null && stageCoordinator2.isDeviceHalfClosed()) {
                        Slog.d("StageCoordinator", "onDisplayChange: skip by non display info and device half closed");
                        return;
                    }
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -6802425196666471292L, 21, Long.valueOf(i2), Long.valueOf(i3), Long.valueOf(i4), String.valueOf(displayAreaInfo != null ? displayAreaInfo.configuration : null));
                    }
                    if (i3 != i4 && (stageCoordinator2.mSplitTransitions.mPendingEnter != null || (stageCoordinator2.isSplitScreenVisible() && stageCoordinator2.mKeyguardActive))) {
                        stageCoordinator2.mDisplayController.getDisplayLayout(stageCoordinator2.mContext.getDisplayId()).rotateTo(stageCoordinator2.mContext.getResources(), i4);
                    }
                    boolean zIsLandscape = stageCoordinator2.isLandscape();
                    int i8 = stageCoordinator2.mLastConfiguration.semDisplayDeviceType;
                    int i9 = stageCoordinator2.mSplitLayout.mRotation;
                    if (i3 == -1 && i4 == -1) {
                        z = z2;
                    } else if (z2 && stageCoordinator2.isDeviceHalfClosed() && displayAreaInfo != null && i3 == i4) {
                        Slog.d("StageCoordinator", "onDisplayChange: skip rotate by no rotate changed");
                        z = z2;
                    } else {
                        SplitLayout splitLayout = stageCoordinator2.mSplitLayout;
                        boolean z3 = (((i4 - splitLayout.mRotation) + 4) % 4) % 2 != 0;
                        splitLayout.mRotation = i4;
                        Rect rect = new Rect(splitLayout.mRootBounds);
                        if (z3) {
                            Rect rect2 = splitLayout.mRootBounds;
                            rect.set(rect2.top, rect2.left, rect2.bottom, rect2.right);
                        }
                        boolean z4 = splitLayout.mIsLargeScreen;
                        boolean z5 = splitLayout.mRootBounds.width() >= splitLayout.mRootBounds.height();
                        boolean zIsInSubDisplay = MultiWindowUtils.isInSubDisplay(splitLayout.mContext);
                        int i10 = splitLayout.mSplitDivision;
                        z = z2;
                        boolean z6 = splitLayout.mAllowLeftRightSplitInPortrait;
                        boolean zIsLeftRightSplit = SplitScreenUtils.isLeftRightSplit(z6, z4, z5, zIsInSubDisplay, i10);
                        splitLayout.mTempRect.set(splitLayout.mRootBounds);
                        splitLayout.mRootBounds.set(rect);
                        boolean zIsVerticalDivision = splitLayout.isVerticalDivision();
                        if (((CoreRune.MW_MULTI_SPLIT_FREE_POSITION && MultiWindowUtils.isInSubDisplay(splitLayout.mContext)) || (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && splitLayout.mSplitScreenFeasibleMode == 1)) && z3 && (!z || !splitLayout.mStageCoordinator.isDeviceHalfClosed())) {
                            splitLayout.mSplitDivision = !SplitLayout.isLandscape(splitLayout.mRootBounds) ? 1 : 0;
                            splitLayout.mIsLeftRightSplit = splitLayout.isVerticalDivision();
                        }
                        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && splitLayout.mParallelMultiSplit && z3) {
                            splitLayout.mSplitDivision = !SplitLayout.isLandscape(splitLayout.mRootBounds) ? 1 : 0;
                        }
                        boolean z7 = z3;
                        splitLayout.mIsLeftRightSplit = SplitScreenUtils.isLeftRightSplit(z6, splitLayout.mIsLargeScreen, splitLayout.mRootBounds.width() >= splitLayout.mRootBounds.height(), MultiWindowUtils.isInSubDisplay(splitLayout.mContext), splitLayout.mSplitDivision);
                        splitLayout.updateLayouts();
                        splitLayout.initDividerPosition(splitLayout.mTempRect, zIsLeftRightSplit, zIsVerticalDivision);
                        if (!CoreRune.MW_PARALLEL_MULTI_SPLIT_SUPPORT_RESIZE && splitLayout.mParallelMultiSplit && z7 && (stageCoordinator = splitLayout.mStageCoordinator) != null) {
                            splitLayout.setDivideRatio(stageCoordinator.calculateSplitRatioForParallelMultiSplit(stageCoordinator.getCurrentMultiSplitLayoutInfo()), true, true);
                            splitLayout.setCellDividerRatio(0.5f, splitLayout.mCellStageWindowConfigPosition, true, false);
                        }
                    }
                    if (displayAreaInfo != null) {
                        Configuration configuration = displayAreaInfo.configuration;
                        boolean z8 = CoreRune.MW_MULTI_SPLIT_FREE_POSITION;
                        if (z8 && stageCoordinator2.isSplitScreenVisible()) {
                            int i11 = stageCoordinator2.mLastConfiguration.semDisplayDeviceType;
                            int i12 = configuration.semDisplayDeviceType;
                            if (i11 != i12 && i12 == 5 && !stageCoordinator2.mFoldLockSettingsObserver.isStayAwakeOnFold() && ((semWifiDisplayStatusSemGetWifiDisplayStatus = ((DisplayManager) stageCoordinator2.mContext.getSystemService("display")).semGetWifiDisplayStatus()) == null || semWifiDisplayStatusSemGetWifiDisplayStatus.getActiveDisplayState() != 2 || semWifiDisplayStatusSemGetWifiDisplayStatus.getConnectedState() != 0)) {
                                Slog.d("StageCoordinator", "onDisplayChange : defer updateConfig in splitVisible");
                            }
                            if (z8) {
                                Slog.d("StageCoordinator", "Restore main Split Division=" + stageCoordinator2.mLastMainSplitDivision);
                                stageCoordinator2.mLastMainSplitDivision = -1;
                            }
                            stageCoordinator2.mLastConfiguration.updateFrom(configuration);
                        } else {
                            stageCoordinator2.mSplitLayout.updateConfiguration(displayAreaInfo.configuration);
                            if (z8 && (i6 = configuration.semDisplayDeviceType) == 0 && i6 != stageCoordinator2.mLastConfiguration.semDisplayDeviceType && !stageCoordinator2.isSplitScreenVisible() && (i7 = stageCoordinator2.mLastMainSplitDivision) != stageCoordinator2.mSplitDivision && stageCoordinator2.setSplitDivision(i7, false, true)) {
                                Slog.d("StageCoordinator", "Restore main Split Division=" + stageCoordinator2.mLastMainSplitDivision);
                                stageCoordinator2.mLastMainSplitDivision = -1;
                            }
                            stageCoordinator2.mLastConfiguration.updateFrom(configuration);
                        }
                    }
                    if ((!CoreRune.MW_MULTI_SPLIT || MultiWindowUtils.isInSubDisplay(stageCoordinator2.mContext) || (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && stageCoordinator2.mSplitLayout.mSplitScreenFeasibleMode == 1)) && i9 != i4 && ((i4 == 3 || i9 == 3) && !(CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator2.isMultiSplitActive()))) {
                        SplitLayout splitLayout2 = stageCoordinator2.mSplitLayout;
                        splitLayout2.setDivideRatio(1.0f - ((splitLayout2.mDividerPosition + splitLayout2.mDividerSize) / (SplitLayout.isLandscape(splitLayout2.mRootBounds) ? splitLayout2.mRootBounds.width() : splitLayout2.mRootBounds.height())), false, false);
                        stageCoordinator2.setSideStagePosition$1(windowContainerTransaction, SplitScreenUtils.reverseSplitPosition(stageCoordinator2.mSideStagePosition));
                    } else {
                        stageCoordinator2.updateWindowBounds(stageCoordinator2.mSplitLayout, windowContainerTransaction, false);
                    }
                    if (zIsLandscape != stageCoordinator2.isLandscape()) {
                        if (((CoreRune.MW_MULTI_SPLIT_FREE_POSITION && MultiWindowUtils.isInSubDisplay(stageCoordinator2.mContext)) || (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && stageCoordinator2.mSplitLayout.mSplitScreenFeasibleMode == 1)) && (!CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY || !stageCoordinator2.isApplyFoldingPolicy(false) || !stageCoordinator2.mFoldLockSettingsObserver.isSleepOnFold())) {
                            if (z && stageCoordinator2.isDeviceHalfClosed()) {
                                Slog.d("StageCoordinator", "onDisplayChange: keep division by half closed");
                            } else {
                                stageCoordinator2.setSplitDivision(!stageCoordinator2.isLandscape() ? 1 : 0, false, false);
                            }
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
        Bundle bundleResolveStartStage;
        int multiSplitLaunchPosition;
        Bundle bundleResolveStartCellStage;
        int i2 = runningTaskInfo.taskId;
        WindowContainerToken windowContainerToken = runningTaskInfo.token;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setChangeTransitMode(windowContainerToken, 1, str);
        if (rect != null) {
            windowContainerTransaction.setChangeTransitStartBounds(windowContainerToken, rect);
        }
        if (z) {
            int i3 = i & 64;
            int i4 = (i == 0 || !(i3 != 0 || (i & 32) != 0)) ? 1 : 0;
            if ((i & 16) == 0 && i3 == 0) {
                i = 0;
            }
            startTaskAndIntent(i2, MultiWindowUtils.getEdgeAllAppsActivityIntent(runningTaskInfo.baseIntent.getComponent(), runningTaskInfo.userId, i2), i4, i, windowContainerTransaction);
            return;
        }
        int i5 = ((i != 0 || z2) && (i == 16 || i == 8)) ? 0 : 1;
        boolean z4 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        StageTaskListener stageTaskListener = this.mMainStage;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        if (!z4 || MultiWindowUtils.isInSubDisplay(this.mContext) || !isSplitScreenVisible()) {
            int i6 = i5;
            if (i != 0) {
                bundleResolveStartStage = resolveStartStage(-1, i6, null, null, (i == 8 || i == 32) ? 0 : 1);
            } else {
                bundleResolveStartStage = resolveStartStage(-1, i6, null, null, -1);
            }
            windowContainerTransaction.startTask(i2, bundleResolveStartStage);
            if (isSplitScreenVisible()) {
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                return;
            }
            int i7 = stageTaskListener.mIsActive ? 1005 : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
            prepareEnterSplitScreen(windowContainerTransaction, null, i6, false);
            this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, i7, false, 1);
            return;
        }
        if (z2) {
            int splitDivision = getSplitDivision();
            multiSplitLaunchPosition = getSideStageWinConfigPosition();
            if (splitDivision == 1) {
                if (i == 8) {
                    multiSplitLaunchPosition |= 32;
                } else if (i == 32) {
                    multiSplitLaunchPosition |= 8;
                }
            } else if (i == 16) {
                multiSplitLaunchPosition |= 64;
            } else if (i == 64) {
                multiSplitLaunchPosition |= 16;
            }
            setSideStagePosition(i5, splitDivision == 1 ? 0 : 1, windowContainerTransaction, false);
            if (z3) {
                WindowContainerToken windowContainerToken2 = stageTaskListener.getTopRunningTaskInfo().token;
                WindowContainerToken windowContainerToken3 = this.mSideStage.getTopRunningTaskInfo().token;
                windowContainerTransaction.setChangeTransitMode(windowContainerToken2, 4, "natural_swtiching");
                windowContainerTransaction.setChangeTransitMode(windowContainerToken3, 4, "natural_swtiching");
            }
            swapStageTasks(1, 5, windowContainerTransaction);
            bundleResolveStartCellStage = resolveStartStage(1, i5, null, null, -1);
            this.mSplitLayout.update(null, true);
        } else {
            multiSplitLaunchPosition = (CoreRune.MW_PARALLEL_MULTI_SPLIT && i == 0 && this.mSplitLayout.mParallelMultiSplit && isMultiSplitScreenVisible()) ? this.mCellStageWindowConfigPosition : i != 0 ? i : StageUtils.getMultiSplitLaunchPosition(this.mCellStageWindowConfigPosition, isVerticalDivision());
            bundleResolveStartCellStage = resolveStartCellStage(-1, multiSplitLaunchPosition, null, null);
        }
        windowContainerTransaction.startTask(i2, bundleResolveStartCellStage);
        if (isMultiSplitScreenVisible()) {
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            return;
        }
        this.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
        prepareEnterMultiSplitScreen(windowContainerTransaction, multiSplitLaunchPosition);
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && !z3) {
            applyCellHostResizeTransition(windowContainerTransaction);
        }
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, VolteConstants.ErrorCode.CALL_SESSION_ABORT, false, 1);
    }

    public final void onLayoutPositionChanging(SplitLayout splitLayout) {
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
        transactionAcquire.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        updateSurfaceBounds(splitLayout, transactionAcquire, false);
        transactionAcquire.apply();
        transactionPool.release(transactionAcquire);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLayoutSizeChanged(SplitLayout splitLayout, WindowContainerTransaction windowContainerTransaction) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 4982149234074334951L, 0, null);
        }
        WindowContainerTransaction windowContainerTransaction2 = (!CoreRune.MW_SPLIT_SHELL_TRANSITION || windowContainerTransaction == null) ? new WindowContainerTransaction() : windowContainerTransaction;
        updateStagePositionIfNeeded(windowContainerTransaction2);
        boolean zUpdateWindowBounds = updateWindowBounds(splitLayout, windowContainerTransaction2, false);
        boolean z = CoreRune.MW_CAPTION_HANDLE;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (z && this.mIsStageTasksChanged) {
            Slog.d("StageCoordinator", "StageTasksChanged, so need SplitDecor Update is required.");
            this.mIsStageTasksChanged = false;
        } else if (!zUpdateWindowBounds) {
            TransactionPool transactionPool = this.mTransactionPool;
            SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
            stageTaskListener2.onResized(transactionAcquire);
            stageTaskListener.onResized(transactionAcquire);
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                this.mCellStage.onResized(transactionAcquire);
            }
            transactionPool.release(transactionAcquire);
            return;
        }
        new ArrayList();
        SplitDecorManager splitDecorManager = stageTaskListener2.mSplitDecorManager;
        SplitDecorManager splitDecorManager2 = stageTaskListener.mSplitDecorManager;
        sendOnBoundsChanged();
        this.mSplitLayout.setDividerInteractive("onSplitResizeStart", false, false);
        this.mSplitTransitions.startResizeTransition(windowContainerTransaction2, this, new StageCoordinator$$ExternalSyntheticLambda0(1, this), new StageCoordinator$$ExternalSyntheticLambda0(2, this), splitDecorManager, splitDecorManager2);
        int iCalculateCurrentSnapPosition = splitLayout.calculateCurrentSnapPosition();
        if (iCalculateCurrentSnapPosition == 3) {
            grantFocusToPosition(true);
        } else if (iCalculateCurrentSnapPosition == 4 || iCalculateCurrentSnapPosition == 14) {
            grantFocusToPosition(false);
        } else if (iCalculateCurrentSnapPosition == 15) {
        }
        float dividerPositionAsFraction = this.mSplitLayout.getDividerPositionAsFraction();
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        if (splitscreenEventLogger.mLoggerSessionId == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -4109533377578191407L, 0, null);
                return;
            }
            return;
        }
        if (dividerPositionAsFraction <= 0.0f || dividerPositionAsFraction >= 1.0f) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1401793980569207330L, 0, null);
            }
        } else if (Float.compare(splitscreenEventLogger.mLastSplitRatio, dividerPositionAsFraction) == 0) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -4749391843597404276L, 0, null);
            }
        } else {
            splitscreenEventLogger.mLastSplitRatio = dividerPositionAsFraction;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -3839017821800023881L, 6, Double.valueOf(dividerPositionAsFraction), Long.valueOf(splitscreenEventLogger.mLoggerSessionId.getId()));
            }
            FrameworkStatsLog.write(388, 4, 0, 0, splitscreenEventLogger.mLastSplitRatio, splitscreenEventLogger.mLastMainStagePosition, splitscreenEventLogger.mLastMainStageUid, splitscreenEventLogger.mLastSideStagePosition, splitscreenEventLogger.mLastSideStageUid, 0, splitscreenEventLogger.mLoggerSessionId.getId());
        }
    }

    public final void onLayoutSizeChanging(SplitLayout splitLayout, int i, int i2, boolean z) {
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
        transactionAcquire.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        updateSurfaceBounds(splitLayout, transactionAcquire, z);
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
        transactionAcquire.apply();
        transactionPool.release(transactionAcquire);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onNoLongerSupportMultiWindow(StageTaskListener stageTaskListener, ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 8876191938084552299L, 0, String.valueOf(runningTaskInfo));
        }
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (stageTaskListener2.mIsActive) {
            int i = stageTaskListener2 == stageTaskListener ? 1 : 0;
            boolean zIsSplitScreenVisible = isSplitScreenVisible();
            int i2 = zIsSplitScreenVisible ? ~i ? 1 : 0 : -1;
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                int iIsFocused = i;
                if (!MultiWindowCoreState.MW_ENABLED) {
                    iIsFocused = stageTaskListener2.isFocused();
                }
                prepareSplitDismissChangeTransition(windowContainerTransaction, iIsFocused, null);
            }
            prepareExitSplitScreen(i2, 1, windowContainerTransaction, true);
            clearSplitPairedInRecents(1);
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction, this, i2, 1, false);
            if (runningTaskInfo.baseActivity == null) {
                Log.w("StageCoordinator", SplitScreenUtils.splitFailureMessage("onNoLongerSupportMultiWindow", "taskInfo " + runningTaskInfo + " does not support splitscreen, or is a controlled activity type"));
            } else {
                Log.w("StageCoordinator", SplitScreenUtils.splitFailureMessage("onNoLongerSupportMultiWindow", "app package " + runningTaskInfo.baseIntent.getComponent() + " does not support splitscreen, or is a controlled activity type"));
            }
            if (zIsSplitScreenVisible) {
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
        z = true;
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
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(cellHostStageType, "onSnappedToDismissMultiSplit: cell divider action, hostStageType=", ", hostPos=");
            sbM.append(WindowConfiguration.stagePositionToString(stageWinConfigPositionByType));
            sbM.append(", dismissToHostStage=");
            sbM.append(z3);
            Slog.d("StageCoordinator", sbM.toString());
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
        StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "onSnappedToDismissMultiSplit: halfStageType=", ", halfPos=");
        sbM2.append(WindowConfiguration.stagePositionToString(stageWinConfigPositionByType3));
        sbM2.append(", dismissToHalfStage=");
        sbM2.append(z4);
        Slog.d("StageCoordinator", sbM2.toString());
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
        SurfaceControl surfaceControlBuild = new SurfaceControl.Builder(splitBackgroundController.mSurfaceSession).setName("Split Background Layer").setHidden(true).setColorLayer().setCallsite("SplitBackgroundController.onDisplayAreaAppeared").build();
        splitBackgroundController.mBackgroundColorLayer = surfaceControlBuild;
        SplitBackgroundController.SurfaceDelegate surfaceDelegate = splitBackgroundController.mSurfaceDelegate;
        surfaceDelegate.mSurfaceControl = surfaceControlBuild;
        surfaceDelegate.setCrop(splitBackgroundController.getDisplayBounds());
        TransactionPool transactionPool = splitBackgroundController.mTransactionPool;
        SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
        transactionAcquire.setLayer(splitBackgroundController.mBackgroundColorLayer, -1);
        transactionAcquire.reparent(splitBackgroundController.mBackgroundColorLayer, surfaceControl);
        transactionAcquire.apply();
        transactionPool.release(transactionAcquire);
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
        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
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
        boolean zAnyMatch = windowContainerTransaction.getChanges().values().stream().anyMatch(new StageCoordinator$$ExternalSyntheticLambda5());
        if (!zAnyMatch) {
            windowContainerTransaction.setChangeTransitMode(stageToken3, 2, str);
        }
        Slog.d("StageCoordinator", "prepareMultiSplitDismissChangeTransition: dismiss=" + SplitScreen.stageTypeToString(i) + ", hasMovingToFreeform=" + zAnyMatch);
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
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
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
                int iReverseSplitPosition = this.mSideStagePosition;
                int iReverseSplitPosition2 = stageTaskListener.mStageType == 1 ? SplitScreenUtils.reverseSplitPosition(iReverseSplitPosition) : iReverseSplitPosition;
                if (isVerticalDivision()) {
                    i = (this.mCellStageWindowConfigPosition & 16) != 0 ? 1 : 0;
                    if ((iReverseSplitPosition2 == 0 && i == 0) || (iReverseSplitPosition2 == 1 && i != 0)) {
                        iReverseSplitPosition = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
                    }
                    i = 1;
                } else {
                    boolean z3 = (this.mCellStageWindowConfigPosition & 8) != 0;
                    if ((iReverseSplitPosition2 == 0 && !z3) || (iReverseSplitPosition2 == 1 && z3)) {
                        iReverseSplitPosition = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
                    }
                }
                if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
                    SplitLayout splitLayout2 = this.mSplitLayout;
                    splitLayout2.mTempRect2.set(splitLayout2.mRootBounds);
                    splitLayout2.mTempRect2.inset(splitLayout2.getDisplayStableInsets(splitLayout2.mContext));
                    if (splitLayout2.mSplitDivision == 0) {
                        int i = splitLayout2.mCellDividerPosition;
                        Rect rect = splitLayout2.mTempRect2;
                        float fHeight = (i - rect.top) / (rect.height() - splitLayout2.mDividerSize);
                        splitLayout2.mDividerPosition = splitLayout2.mTempRect2.left + ((int) (((r5.width() - splitLayout2.mDividerSize) * fHeight) + 0.5f));
                    } else {
                        int i2 = splitLayout2.mCellDividerPosition;
                        Rect rect2 = splitLayout2.mTempRect2;
                        float fWidth = (i2 - rect2.left) / (rect2.width() - splitLayout2.mDividerSize);
                        splitLayout2.mDividerPosition = splitLayout2.mTempRect2.top + ((int) (((r5.height() - splitLayout2.mDividerSize) * fWidth) + 0.5f));
                    }
                }
                setSideStagePosition(iReverseSplitPosition, i, windowContainerTransaction, true);
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

    public final void sendPairLoggingLocked() {
        CoreSaLogger.logForAdvanced("1004", this.mCurrentPackageNameList.toString());
        if (this.mCurrentPackageNameList.size() == 3) {
            CoreSaLogger.logForAdvanced("1045", (CoreRune.MW_PARALLEL_MULTI_SPLIT_SA_LOGGING && this.mSplitLayout.mParallelMultiSplit) ? "Parallel" : "Legacy");
        } else {
            CoreSaLogger.logForAdvanced("1044");
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
            boolean zIsVerticalDivision = isVerticalDivision();
            int i2 = this.mSideStagePosition;
            StageTaskListener stageTaskListener = this.mMainStage;
            StageTaskListener stageTaskListener2 = this.mSideStage;
            if (i2 != 0 ? !((!zIsVerticalDivision || (i & 32) == 0) && (zIsVerticalDivision || (i & 64) == 0)) : !((!zIsVerticalDivision || (i & 8) == 0) && (zIsVerticalDivision || (i & 16) == 0))) {
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
        boolean splitDivision = (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitLayout.mParallelMultiSplit) ? setSplitDivision(!isLandscape() ? 1 : 0, isInSubDisplay(), z) : CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? setSplitDivision(i2, isInSubDisplay(), z) : false;
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
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setSplitDivision: nextSplitDivision=", "   Caller=");
            sbM.append(Debug.getCallers(5));
            Slog.d("StageCoordinator", sbM.toString());
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
            boolean zIsMultiSplitActive = isMultiSplitActive();
            StageTaskListener stageTaskListener3 = this.mCellStage;
            if (zIsMultiSplitActive && z) {
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

    /* JADX WARN: Removed duplicated region for block: B:188:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x03f2  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x03f9  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01c1  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        boolean z2;
        StageTaskListener stageTaskListener;
        ShellTaskOrganizer shellTaskOrganizer;
        ShellTaskOrganizer shellTaskOrganizer2;
        int i;
        int i2;
        TransitionInfo transitionInfo2;
        TransitionInfo.Change change;
        boolean z3;
        int i3;
        SparseIntArray sparseIntArray;
        SparseIntArray sparseIntArray2;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        SplitLayout splitLayout;
        boolean z4;
        SurfaceControl.Transaction transaction3;
        SurfaceControl.Transaction transaction4 = transaction;
        if (this.mSplitTransitions.getPendingTransition(iBinder) != null) {
            if (this.mMixedHandler == null || !TransitionUtil.hasDisplayChange(transitionInfo)) {
                SplitScreenTransitions.TransitSession transitSession = this.mSplitTransitions.mPendingRemotePassthrough;
                if (transitSession != null && transitSession.mTransition == iBinder) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 9106024789016981533L, 1, Long.valueOf(transitionInfo.getDebugId()));
                    }
                    this.mSplitTransitions.mPendingRemotePassthrough.mRemoteHandler.startAnimation(iBinder, transitionInfo, transaction4, transaction2, transitionFinishCallback);
                    notifySplitAnimationFinished();
                    return true;
                }
            } else {
                if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
                    this.mMixedHandler.getClass();
                    TransitionInfo transitionInfoSubCopy = DefaultMixedHandler.subCopy(transitionInfo, 6, false);
                    for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
                        TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
                        TransitionInfo.Change change3 = change2;
                        while (change3 != null) {
                            if (change3.getTaskInfo() == null) {
                                if (change3.getParent() == null) {
                                    break;
                                }
                                change3 = transitionInfo.getChange(change3.getParent());
                            }
                        }
                        transitionInfoSubCopy.addChange(change2);
                    }
                    if (!transitionInfoSubCopy.getChanges().isEmpty()) {
                        if (this.mSplitTransitions.isPendingResize(iBinder)) {
                            this.mSplitLayout.update(transaction4, true);
                        }
                        if (this.mMixedHandler.animatePendingSplitWithDisplayChange(iBinder, transitionInfo, transaction4, transaction2, transitionFinishCallback)) {
                            notifySplitAnimationFinished();
                            return true;
                        }
                    }
                    transaction3 = transaction2;
                    if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
                        updateCornerRadiusForStages(transaction3);
                    }
                    return startPendingAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
                }
                transaction4 = transaction;
                if (this.mMixedHandler.animatePendingSplitWithDisplayChange(iBinder, transitionInfo, transaction4, transaction2, transitionFinishCallback)) {
                    if (this.mSplitTransitions.isPendingResize(iBinder)) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -6116775544800529753L, 1, Long.valueOf(transitionInfo.getDebugId()));
                        }
                        this.mSplitLayout.update(transaction4, true);
                        transaction4.apply();
                    }
                    notifySplitAnimationFinished();
                    return true;
                }
            }
            transaction3 = transaction2;
            if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
            }
            return startPendingAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
        }
        TransitionInfo transitionInfo3 = transitionInfo;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        boolean z5 = stageTaskListener2.mIsActive;
        StageTaskListener stageTaskListener3 = this.mSideStage;
        if (!z5) {
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                if (isSplitScreenVisible()) {
                    setSplitsVisible(false);
                }
                for (int i4 = 0; i4 < transitionInfo3.getChanges().size(); i4++) {
                    TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i4);
                    WindowContainerToken lastParent = change4.getLastParent();
                    boolean z6 = stageTaskListener2.containsToken(lastParent) || stageTaskListener3.containsToken(lastParent);
                    if (TransitionUtil.isClosingType(change4.getMode()) && z6) {
                        Log.d("StageCoordinator", "startAnimation. disappear at stage position. " + change4);
                        change4.setEndAbsBounds(change4.getStartAbsBounds());
                    }
                }
            }
            return false;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 5609231403121587914L, 1, Long.valueOf(transitionInfo3.getDebugId()));
        }
        this.mSplitLayout.mFreezeDividerWindow = false;
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && TransitionUtil.hasDisplayChange(transitionInfo3)) {
            onLayoutSizeChanged(this.mSplitLayout, null);
            if (this.mSplitTransitions.mPendingResize != null) {
                z = true;
            }
        } else {
            z = false;
        }
        StageChangeRecord stageChangeRecord = new StageChangeRecord();
        if (TransitionUtil.isOpeningType(transitionInfo3.getType())) {
            for (int i5 = 0; i5 < transitionInfo3.getChanges().size(); i5++) {
                TransitionInfo.Change change5 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i5);
                ActivityManager.RunningTaskInfo taskInfo = change5.getTaskInfo();
                if (taskInfo != null && TransitionUtil.isOpeningType(change5.getMode()) && taskInfo.getWindowingMode() == 1) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        } else {
            z2 = false;
        }
        int type = transitionInfo3.getType();
        SparseIntArray sparseIntArray3 = new SparseIntArray();
        sparseIntArray3.put(0, -1);
        sparseIntArray3.put(1, -1);
        int i6 = -1;
        int i7 = 0;
        boolean zIsSplitScreenVisible = false;
        TransitionInfo.Change change6 = null;
        while (true) {
            int size = transitionInfo3.getChanges().size();
            stageTaskListener = this.mCellStage;
            boolean z7 = z;
            shellTaskOrganizer = this.mTaskOrganizer;
            if (i7 >= size) {
                break;
            }
            TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i7);
            int i8 = i7;
            if (change7.getMode() == 6 && (change7.getFlags() & 32) != 0) {
                this.mSplitLayout.update(transaction4, false);
            }
            if (this.mMixedHandler.mPipHandler.isEnteringPip$1(change7, type) && getSplitItemStage(change7.getLastParent()) != -1) {
                change6 = change7;
            }
            ActivityManager.RunningTaskInfo taskInfo2 = change7.getTaskInfo();
            if (taskInfo2 == null) {
                z3 = z2;
            } else {
                z3 = z2;
                if (!taskInfo2.token.equals(this.mRootTaskInfo.token)) {
                    StageTaskListener stageOfTask = getStageOfTask(taskInfo2);
                    if (CoreRune.MW_SPLIT_SHELL_TRANSITION && z7) {
                        Rect rect = new Rect();
                        boolean z8 = stageOfTask == null;
                        WindowContainerToken windowContainerToken = z8 ? taskInfo2.token : stageOfTask.mRootTaskInfo.token;
                        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isMultiSplitScreenVisible()) {
                            i3 = type;
                            int cellHostStageType = getCellHostStageType();
                            int stagePosition = taskInfo2.getConfiguration().windowConfiguration.getStagePosition();
                            sparseIntArray = sparseIntArray3;
                            boolean z9 = (getMainStageWinConfigPosition() == stagePosition ? 0 : getSideStageWinConfigPosition() == stagePosition ? 1 : this.mCellStageWindowConfigPosition == stagePosition ? 5 : -1) == cellHostStageType;
                            splitLayout = this.mSplitLayout;
                            if (windowContainerToken.equals(splitLayout.mWinToken1)) {
                                if (windowContainerToken.equals(splitLayout.mWinToken2)) {
                                    if (z9) {
                                        splitLayout.mTempRect.set(splitLayout.getRefHostBounds());
                                    } else {
                                        Rect rect2 = splitLayout.mTempRect;
                                        rect2.set(splitLayout.getBottomRightBounds());
                                        Rect rect3 = splitLayout.mRootBounds;
                                        rect2.offset(-rect3.left, -rect3.top);
                                    }
                                } else if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && windowContainerToken.equals(splitLayout.mWinToken3)) {
                                    Rect rect4 = splitLayout.mTempRect;
                                    rect4.set(splitLayout.mBounds3);
                                    Rect rect5 = splitLayout.mRootBounds;
                                    rect4.offset(-rect5.left, -rect5.top);
                                } else {
                                    z4 = false;
                                    if (z4) {
                                        rect.set(splitLayout.mTempRect);
                                    }
                                    if (z4) {
                                        if (z8) {
                                            change7.setEndAbsBounds(rect);
                                            change7.setEndRelOffset(rect.left, rect.top);
                                            change7.getTaskInfo().positionInParent.x = rect.left;
                                            change7.getTaskInfo().positionInParent.y = rect.top;
                                            SurfaceControl leash = change7.getLeash();
                                            int iWidth = change7.getEndAbsBounds().width();
                                            int iHeight = change7.getEndAbsBounds().height();
                                            int i9 = change7.getEndRelOffset().x;
                                            int i10 = change7.getEndRelOffset().y;
                                            transaction4.setWindowCrop(leash, iWidth, iHeight);
                                            transaction2.setWindowCrop(leash, iWidth, iHeight).setPosition(leash, i9, i10);
                                        } else {
                                            change7.setEndAbsBounds(rect);
                                        }
                                    }
                                }
                            } else if (z9) {
                                splitLayout.mTempRect.set(splitLayout.getRefHostBounds());
                            } else {
                                splitLayout.copyTopLeftRefBounds(splitLayout.mTempRect);
                            }
                            z4 = true;
                            if (z4) {
                            }
                            if (z4) {
                            }
                        } else {
                            i3 = type;
                            sparseIntArray = sparseIntArray3;
                        }
                        splitLayout = this.mSplitLayout;
                        if (windowContainerToken.equals(splitLayout.mWinToken1)) {
                        }
                        z4 = true;
                        if (z4) {
                        }
                        if (z4) {
                        }
                    } else {
                        i3 = type;
                        sparseIntArray = sparseIntArray3;
                    }
                    if (stageOfTask == null) {
                        SplitLayout splitLayout2 = this.mSplitLayout;
                        if (splitLayout2.mImePositionProcessor.mYOffsetForIme != 0) {
                            ActivityManager.RunningTaskInfo runningTaskInfo2 = stageTaskListener2.mRootTaskInfo;
                            if (runningTaskInfo2 == null || runningTaskInfo2.taskId != taskInfo2.taskId) {
                                ActivityManager.RunningTaskInfo runningTaskInfo3 = stageTaskListener3.mRootTaskInfo;
                                if (runningTaskInfo3 != null && runningTaskInfo3.taskId == taskInfo2.taskId) {
                                    stageTaskListener = stageTaskListener3;
                                } else if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || (runningTaskInfo = stageTaskListener.mRootTaskInfo) == null || runningTaskInfo.taskId != taskInfo2.taskId) {
                                    stageTaskListener = null;
                                }
                            } else {
                                stageTaskListener = stageTaskListener2;
                            }
                            if (stageTaskListener != null) {
                                if (CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME) {
                                    int splitItemStagePosition = ((StageCoordinator) splitLayout2.mSplitLayoutHandler).getSplitItemStagePosition(taskInfo2.token);
                                    int i11 = splitItemStagePosition & 16;
                                    if (i11 == 0 || !isSplitScreenVisible()) {
                                        updateWindowBounds(this.mSplitLayout, new WindowContainerTransaction(), true);
                                    } else {
                                        Rect rect6 = new Rect();
                                        SplitLayout splitLayout3 = this.mSplitLayout;
                                        if (i11 == 0) {
                                            splitLayout3.getClass();
                                        } else {
                                            if ((splitItemStagePosition & 40) == 0) {
                                                splitLayout3.copyTopLeftRefBounds(rect6);
                                            } else if (splitItemStagePosition == splitLayout3.mCellStageWindowConfigPosition) {
                                                rect6.set(splitLayout3.mBounds3);
                                                Rect rect7 = splitLayout3.mRootBounds;
                                                rect6.offset(-rect7.left, -rect7.top);
                                            } else {
                                                rect6.set(splitLayout3.getRefHostBounds());
                                            }
                                            rect6.offset(-rect6.left, -rect6.top);
                                            rect6.bottom = Math.max(rect6.height() + splitLayout3.mImePositionProcessor.mYOffsetForIme, 0);
                                        }
                                        transaction4.setCrop(change7.getLeash(), rect6);
                                        transaction2.setCrop(change7.getLeash(), rect6);
                                    }
                                } else if (stageTaskListener == (this.mSideStagePosition == 0 ? stageTaskListener3 : stageTaskListener2)) {
                                    Rect rect8 = new Rect();
                                    SplitLayout splitLayout4 = this.mSplitLayout;
                                    splitLayout4.copyTopLeftRefBounds(rect8);
                                    rect8.offset(-rect8.left, -rect8.top);
                                    rect8.bottom = Math.max(rect8.height() + splitLayout4.mImePositionProcessor.mYOffsetForIme, 0);
                                    transaction4.setCrop(change7.getLeash(), rect8);
                                    transaction2.setCrop(change7.getLeash(), rect8);
                                }
                            }
                        }
                    }
                    if (stageOfTask != null) {
                        int i12 = taskInfo2.taskId;
                        if (TransitionUtil.isOpeningType(change7.getMode())) {
                            if (!stageOfTask.mChildrenTaskInfo.contains(i12)) {
                                Log.w("StageCoordinator", "Expected onTaskAppeared on " + stageOfTask + " to have been called with " + i12 + " before startAnimation().");
                                stageChangeRecord.addRecord(stageOfTask, true, i12);
                            }
                        } else if ((change7.getMode() == 2 || (CoreRune.MW_SPLIT_STACKING && change7.getMode() == 4 && !z3 && (transitionInfo.getFlags() & 2048) == 0 && (transitionInfo.getFlags() & NetworkAnalyticsConstants.DataPoints.FLAG_UID) == 0)) && stageOfTask.mChildrenTaskInfo.contains(i12)) {
                            stageChangeRecord.addRecord(stageOfTask, false, i12);
                            Log.w("StageCoordinator", "Expected onTaskVanished on " + stageOfTask + " to have been called with " + i12 + " before startAnimation().");
                        }
                        int stageOfTask2 = getStageOfTask(i12);
                        if (stageOfTask2 != -1) {
                            if (TransitionUtil.isClosingType(change7.getMode())) {
                                i6 = i12;
                            }
                            if ((change7.getMode() == 4 || change7.getMode() == 3) && (stageOfTask2 == 0 || stageOfTask2 == 1)) {
                                int stageOfTask3 = getStageOfTask(i12);
                                int mode = change7.getMode();
                                sparseIntArray2 = sparseIntArray;
                                sparseIntArray2.put(stageOfTask3, mode);
                            }
                        }
                    } else if (change7.getParent() == null && !TransitionUtil.isClosingType(change7.getMode()) && taskInfo2.getWindowingMode() == 1) {
                        stageChangeRecord.mContainShowFullscreenChange = true;
                    }
                    sparseIntArray2 = sparseIntArray;
                } else if (TransitionUtil.isOpeningType(change7.getMode())) {
                    setSplitsVisible(true);
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, false);
                    shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                } else if (TransitionUtil.isClosingType(change7.getMode())) {
                    if (change7.getMode() == 4 && !z3 && taskInfo2.isSleeping) {
                        Slog.d("StageCoordinator", "In the process of sleeping, the visible of split should be maintained." + transitionInfo3);
                    } else {
                        if (CoreRune.MW_PIP_SHELL_TRANSITION && change6 != null) {
                            zIsSplitScreenVisible = isSplitScreenVisible();
                        }
                        setSplitsVisible(false);
                    }
                    if (TransitionUtil.isOpeningType(transitionInfo3.getType())) {
                        int i13 = 0;
                        while (true) {
                            if (i13 >= transitionInfo3.getChanges().size()) {
                                break;
                            }
                            TransitionInfo.Change change8 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i13);
                            ActivityManager.RunningTaskInfo taskInfo3 = change8.getTaskInfo();
                            if (taskInfo3 != null && TransitionUtil.isOpeningType(change8.getMode()) && taskInfo3.getWindowingMode() == 1 && taskInfo3.getActivityType() == 2) {
                                updateDividerLeashVisible(false);
                                break;
                            }
                            i13++;
                        }
                    }
                    WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                    windowContainerTransaction2.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
                    shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
                    i3 = type;
                    sparseIntArray2 = sparseIntArray3;
                }
                i7 = i8 + 1;
                transitionInfo3 = transitionInfo;
                sparseIntArray3 = sparseIntArray2;
                z = z7;
                z2 = z3;
                type = i3;
            }
            i3 = type;
            sparseIntArray = sparseIntArray3;
            sparseIntArray2 = sparseIntArray;
            i7 = i8 + 1;
            transitionInfo3 = transitionInfo;
            sparseIntArray3 = sparseIntArray2;
            z = z7;
            z2 = z3;
            type = i3;
        }
        SparseIntArray sparseIntArray4 = sparseIntArray3;
        TransitionInfo.Change change9 = change6;
        if (change9 != null && (!CoreRune.MW_PIP_SHELL_TRANSITION || !isSplitScreenVisible())) {
            int i14 = stageTaskListener2.mRootTaskInfo.taskId;
            int i15 = stageTaskListener3.mRootTaskInfo.taskId;
            int splitItemStage = getSplitItemStage(change9.getLastParent());
            if (splitItemStage == 0) {
                transitionInfo2 = transitionInfo;
                i15 = i14;
                i2 = 1;
            } else {
                i2 = 1;
                transitionInfo2 = transitionInfo;
                if (splitItemStage != 1) {
                    i15 = -1;
                }
            }
            int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo2, i2);
            while (true) {
                if (iM2 < 0) {
                    change = null;
                    break;
                }
                TransitionInfo.Change change10 = (TransitionInfo.Change) transitionInfo2.getChanges().get(iM2);
                if (change10 != change9 && TransitionUtil.isOpeningMode(change10.getMode()) && change10.getTaskInfo() != null && change10.getTaskInfo() != null && change10.getTaskInfo().parentTaskId == i15) {
                    change = change10;
                    break;
                }
                iM2--;
            }
            final int i16 = i6;
            if (change == null || i16 != -1) {
                this.mRecentTasks.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda33
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((RecentTasksController) obj).removeSplitPair(i16);
                    }
                });
                logExit(13);
            } else {
                this.mSplitTransitions.setEnterTransition(iBinder, null, 1005, false, 1);
            }
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                this.mMixedHandler.animatePendingEnterPipFromSplit(iBinder, transitionInfo2, transaction4, transaction2, transitionFinishCallback, change != null, zIsSplitScreenVisible);
            } else {
                this.mMixedHandler.animatePendingEnterPipFromSplit(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback, change != null, false);
            }
            notifySplitAnimationFinished();
            return true;
        }
        int i17 = -1;
        if (this.mKeyguardActive && sparseIntArray4.size() > 0) {
            int iValueAt = sparseIntArray4.valueAt(0);
            int i18 = 0;
            while (true) {
                if (i18 >= sparseIntArray4.size()) {
                    break;
                }
                if (sparseIntArray4.valueAt(i18) != iValueAt) {
                    dismissSplitKeepingLastActiveStage(8);
                    break;
                }
                i18++;
            }
        }
        ArraySet arraySet = new ArraySet();
        for (int size2 = stageChangeRecord.mChanges.size() - 1; size2 >= 0; size2--) {
            StageChangeRecord.StageChange stageChange = (StageChangeRecord.StageChange) stageChangeRecord.mChanges.valueAt(size2);
            if (stageChange.shouldDismissStage()) {
                arraySet.add(stageChange.mStageTaskListener);
            }
        }
        boolean z10 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isMultiSplitScreenVisible() && stageTaskListener.getChildCount() == 0;
        if (stageTaskListener2.getChildCount() == 0 || stageTaskListener3.getChildCount() == 0 || z10 || arraySet.size() == 1) {
            Log.e("StageCoordinator", "Somehow removed the last task in a stage outside of a proper transition.");
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                Log.d("StageCoordinator", "Dismiss Split Debugging mMainStage.getChildCount()=" + stageTaskListener2.getChildCount() + " mSideStage.getChildCount()=" + stageTaskListener3.getChildCount() + " dismissStages.size()=" + arraySet.size() + " isCellStageEmpty=" + z10 + " isSplitActive=" + stageTaskListener2.mIsActive + " isSplitScreenVisible=" + isSplitScreenVisible());
            }
            if (this.mAppPairStarted) {
                Log.d("StageCoordinator", "In the process of starting the App Pair, skip the process");
                return false;
            }
            SplitScreenTransitions.EnterSession enterSession = this.mSplitTransitions.mPendingEnter;
            if (enterSession != null && enterSession.mExtraTransitType == 1106 && (TransitionUtil.isClosingType(transitionInfo.getType()) || (TransitionUtil.hasDisplayChange(transitionInfo) && stageTaskListener2.getChildCount() == 0 && stageTaskListener3.getChildCount() == 0))) {
                Slog.d("StageCoordinator", "waiting split transition change by ai key");
                return false;
            }
            if (CoreRune.MW_SPLIT_STACKING) {
                for (int i19 = 0; i19 < stageChangeRecord.mChanges.size(); i19++) {
                    StageChangeRecord.StageChange stageChange2 = (StageChangeRecord.StageChange) stageChangeRecord.mChanges.valueAt(i19);
                    if (!stageChange2.shouldDismissStage() && stageChange2.mStageTaskListener.getChildCount() == 0) {
                        break;
                    }
                }
            }
            clearSplitPairedInRecents(2);
            boolean z11 = z10;
            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
            int i20 = ((arraySet.size() == 1 && getStageType((StageTaskListener) arraySet.valueAt(0)) == 0) || stageTaskListener2.getChildCount() == 0) ? 1 : 0;
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isMultiSplitScreenVisible()) {
                int i21 = i20 ^ 1;
                if ((arraySet.size() == 1 && getStageType((StageTaskListener) arraySet.valueAt(0)) == 5) || z11) {
                    i21 = 5;
                }
                int cellHostStageType2 = getCellHostStageType();
                int cellHostStageType3 = getCellHostStageType();
                int i22 = cellHostStageType3 == 0 ? 1 : cellHostStageType3 == 1 ? 0 : -1;
                StageTaskListener stageTaskListener4 = stageTaskListener.mHost;
                if (stageTaskListener4 == stageTaskListener2) {
                    stageTaskListener2 = stageTaskListener3;
                }
                prepareMultiSplitDismissChangeTransition(i21, windowContainerTransaction3, false);
                if (i21 == 5) {
                    prepareExitMultiSplitScreen(windowContainerTransaction3, false);
                } else {
                    if (i21 != cellHostStageType2) {
                        if (i21 == i22) {
                            reparentCellToMainOrSide(windowContainerTransaction3, stageTaskListener2, true);
                            i = i22;
                        }
                        this.mSplitTransitions.startDismissTransition(windowContainerTransaction3, this, i, 10, true);
                        updateRecentTasksSplitPair();
                        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mCellDividerVisible) {
                            this.mSplitLayout.releaseCellDivider(null);
                            return false;
                        }
                        return false;
                    }
                    reparentCellToMainOrSide(windowContainerTransaction3, stageTaskListener4, true);
                }
                i = cellHostStageType2;
                this.mSplitTransitions.startDismissTransition(windowContainerTransaction3, this, i, 10, true);
                updateRecentTasksSplitPair();
                if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER) {
                    this.mSplitLayout.releaseCellDivider(null);
                    return false;
                }
                return false;
            }
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION && isSplitScreenVisible() && arraySet.size() == 1) {
                StageTaskListener stageTaskListenerByStageType = getStageTaskListenerByStageType(i20);
                if (stageTaskListenerByStageType != null && stageTaskListenerByStageType.hasAppsEdgeActivityOnTop()) {
                    windowContainerTransaction3.setTransactionType(7);
                }
                prepareSplitDismissChangeTransition(windowContainerTransaction3, getStageType((StageTaskListener) arraySet.valueAt(0)), null);
            }
            if (!stageChangeRecord.mContainShowFullscreenChange && isSplitScreenVisible()) {
                i17 = i20;
            }
            prepareExitSplitScreen(i17, 2, windowContainerTransaction3, true);
            shellTaskOrganizer2 = shellTaskOrganizer;
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction3, this, i20, 2, false);
            if (this.mDividerVisible) {
                this.mSplitTransitions.cancelDividerFadeAnimation();
                this.mSplitLayout.release(null);
            }
        } else {
            shellTaskOrganizer2 = shellTaskOrganizer;
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && (transitionInfo.getFlags() & 256) != 0 && (transitionInfo.getFlags() & 131072) != 0 && isSplitScreenVisible()) {
            setSplitsVisible(false);
            WindowContainerTransaction windowContainerTransaction4 = new WindowContainerTransaction();
            windowContainerTransaction4.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
            shellTaskOrganizer2.applyTransaction(windowContainerTransaction4);
        }
        notifySplitAnimationFinished();
        return false;
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
        Bundle bundleResolveStartStage = resolveStartStage(-1, i, bundle, null, i2);
        if (windowContainerToken != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1580296436628468854L, 0, null);
            }
            windowContainerTransaction.reorder(windowContainerToken, false);
        }
        if (z2 && isMultiSplitActive() && !isMultiSplitScreenVisible()) {
            prepareExitMultiSplitScreen(windowContainerTransaction, false);
        }
        windowContainerTransaction.sendPendingIntent(pendingIntent, intent, bundleResolveStartStage);
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
        if (z2) {
            this.mSplitLayout.mParallelMultiSplit = z;
            if (z) {
                MultiSplitLayoutInfo currentMultiSplitLayoutInfo = getCurrentMultiSplitLayoutInfo();
                currentMultiSplitLayoutInfo.cellStagePosition = i;
                applyParallelMultiSplitLayoutInfo(windowContainerTransaction, currentMultiSplitLayoutInfo);
                this.mSplitLayout.setDivideRatio(this.mSplitLayout.calculateSplitRatioForParallelMultiSplit(currentMultiSplitLayoutInfo), true, true);
            }
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

    /* JADX WARN: Code restructure failed: missing block: B:300:0x070d, code lost:
    
        if (r1.getAnimationOptions().getType() == 1) goto L307;
     */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02c7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x02e0  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0317  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0389  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x03ac  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x03cd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0482  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x048d  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x07e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startPendingAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo transitionInfo2;
        StageTaskListener stageTaskListener;
        IBinder iBinder2;
        WindowContainerToken windowContainerToken;
        StageTaskListener stageTaskListener2;
        StageTaskListener stageTaskListener3;
        SurfaceControl.Transaction transaction3;
        SurfaceControl.Transaction transaction4;
        int i;
        Transitions.TransitionFinishCallback transitionFinishCallback2;
        WindowContainerToken windowContainerToken2;
        WindowContainerToken windowContainerToken3;
        Transitions transitions;
        TransitionInfo.Change change;
        WindowContainerToken windowContainerToken4;
        TransitionInfo transitionInfo3;
        SurfaceControl.Transaction transaction5;
        boolean z;
        TransitionInfo.Change change2;
        int i2;
        WindowContainerToken windowContainerToken5;
        boolean z2;
        WindowContainerToken windowContainerToken6;
        WindowContainerToken windowContainerToken7;
        boolean zEquals;
        boolean zEquals2;
        boolean z3;
        SplitScreenTransitions splitScreenTransitions;
        int i3;
        SplitScreenTransitions splitScreenTransitions2;
        int i4;
        boolean z4;
        TransitionInfo.Change change3;
        TransitionInfo.Change change4;
        boolean z5;
        AlertDialog alertDialog;
        boolean z6;
        boolean z7;
        StageTaskListener stageTaskListener4;
        StageTaskListener stageTaskListener5;
        StageTaskListener stageTaskListener6;
        int i5;
        final boolean z8;
        int i6;
        boolean z9;
        final StageTaskListener stageTaskListener7;
        final WindowContainerTransaction windowContainerTransaction;
        final TransitionInfo.Change change5;
        TransitionInfo.Change change6;
        final boolean z10;
        final TransitionInfo.Change change7;
        boolean z11;
        int i7;
        int type;
        SurfaceControl.Transaction transaction6;
        SplitScreenTransitions.EnterSession enterSession;
        StageTaskListener stageTaskListener8;
        TransitionInfo.Change change8;
        final StageCoordinator stageCoordinator = this;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -2032741871919746197L, 1, Long.valueOf(transitionInfo.getDebugId()));
        }
        boolean zIsPendingEnter = stageCoordinator.mSplitTransitions.isPendingEnter(iBinder);
        StageTaskListener stageTaskListener9 = stageCoordinator.mCellStage;
        StageTaskListener stageTaskListener10 = stageCoordinator.mSideStage;
        StageTaskListener stageTaskListener11 = stageCoordinator.mMainStage;
        if (zIsPendingEnter) {
            SplitScreenTransitions.EnterSession enterSession2 = stageCoordinator.mSplitTransitions.mPendingEnter;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -6329736440246291648L, 0, String.valueOf(enterSession2));
            }
            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            int i8 = 0;
            boolean z12 = false;
            TransitionInfo.Change change9 = null;
            final TransitionInfo.Change change10 = null;
            TransitionInfo.Change change11 = null;
            StageTaskListener stageOfTask = null;
            StageTaskListener stageTaskListener12 = null;
            while (i8 < transitionInfo.getChanges().size()) {
                TransitionInfo.Change change12 = (TransitionInfo.Change) transitionInfo.getChanges().get(i8);
                ActivityManager.RunningTaskInfo taskInfo = change12.getTaskInfo();
                if (taskInfo == null || !taskInfo.hasParentTask()) {
                    enterSession = enterSession2;
                } else {
                    enterSession = enterSession2;
                    if (stageCoordinator.mPausingTasks.contains(Integer.valueOf(taskInfo.taskId))) {
                        z12 = true;
                    } else {
                        StageTaskListener stageOfTask2 = stageCoordinator.getStageOfTask(taskInfo);
                        int stageType = stageCoordinator.getStageType(stageOfTask2);
                        if (change11 == null && stageType == 0) {
                            if (TransitionUtil.isOpeningType(change12.getMode())) {
                                change8 = change12;
                            } else {
                                stageTaskListener8 = stageOfTask2;
                                change8 = change12;
                                if (change12.getMode() == 6) {
                                }
                            }
                            stageOfTask = stageCoordinator.getStageOfTask(taskInfo);
                            change11 = change8;
                        } else {
                            stageTaskListener8 = stageOfTask2;
                            change8 = change12;
                        }
                        if (change9 == null && stageType == 1 && (TransitionUtil.isOpeningType(change8.getMode()) || change8.getMode() == 6 || !(!CoreRune.MW_SPLIT_SHELL_TRANSITION || TransitionUtil.isClosingType(change8.getMode()) || change8.getChangeLeash() == null))) {
                            stageTaskListener12 = stageTaskListener8;
                            change9 = change8;
                        } else {
                            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageType == 5) {
                                if (TransitionUtil.isOpeningType(change8.getMode()) || change8.getMode() == 6) {
                                    change10 = change8;
                                }
                            }
                            if (stageType != -1) {
                                if (change8.getMode() == 4) {
                                    windowContainerTransaction2.reparent(taskInfo.token, (WindowContainerToken) null, false);
                                }
                            }
                        }
                    }
                    i8++;
                    enterSession2 = enterSession;
                }
                i8++;
                enterSession2 = enterSession;
            }
            final SplitScreenTransitions.EnterSession enterSession3 = enterSession2;
            SplitScreenTransitions.EnterSession enterSession4 = stageCoordinator.mSplitTransitions.mPendingEnter;
            boolean z13 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
            boolean z14 = z13 && enterSession4.mExtraTransitType == 1101 && change10 != null;
            int i9 = enterSession4.mExtraTransitType;
            boolean z15 = stageCoordinator.mAppPairStarted;
            if (z15) {
                stageCoordinator.mAppPairStarted = false;
                Log.d("StageCoordinator", "isSkipDismissPair: mAppPairStarted=true");
            } else {
                if (stageCoordinator.mKeyguardActive) {
                    if (i9 == 1004) {
                        Log.d("StageCoordinator", "isSkipDismissPair: type=TRANSIT_SPLIT_SCREEN_PAIR_OPEN mKeyguardShowing=true");
                    } else if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && i9 == 1102) {
                        Log.d("StageCoordinator", "isSkipDismissPair: type=TRANSIT_MULTI_SPLIT_SCREEN_PAIR_OPEN mKeyguardShowing=true");
                    }
                }
                if (z14) {
                    z6 = z14;
                } else {
                    z6 = z14;
                    if (i9 == 1005 || (enterSession4 != null && i9 == 1106)) {
                        if (change11 == null && change9 == null) {
                            Log.w("StageCoordinator", SplitScreenUtils.splitFailureMessage("startPendingEnterAnimation", "Launched a task in split, but didn't receive any task in transition."));
                            enterSession4.mCanceled = true;
                            enterSession4.mFinishedCallback = null;
                            windowContainerToken = null;
                            stageTaskListener = stageTaskListener10;
                            iBinder2 = iBinder;
                            z7 = true;
                            transitionInfo2 = transitionInfo;
                            stageTaskListener2 = stageTaskListener11;
                            stageTaskListener3 = stageTaskListener9;
                            transaction3 = transaction2;
                            transaction6 = transaction;
                            stageCoordinator.setLaunchAdjacentDisabled(z7);
                            Runnable runnable = stageCoordinator.mReEnableLaunchAdjacentOnRoot;
                            Handler handler = stageCoordinator.mMainHandler;
                            handler.removeCallbacks(runnable);
                            handler.postDelayed(stageCoordinator.mReEnableLaunchAdjacentOnRoot, 1000L);
                            transitionFinishCallback2 = transitionFinishCallback;
                            transaction4 = transaction6;
                        } else {
                            if (enterSession4 != null && i9 == 1106 && change9 == null) {
                                ((HandlerExecutor) stageCoordinator.mMainExecutor).executeDelayed(stageCoordinator.mHandleSplitWithAIAssistTimeoutRunnable, 1500L);
                            }
                            if (change11 != null) {
                                stageTaskListener4 = stageOfTask;
                                boolean z16 = stageTaskListener4.mChildrenTaskInfo.contains(change11.getTaskInfo().taskId) ? false : true;
                                if (change9 == null) {
                                    stageTaskListener5 = stageTaskListener4;
                                    stageTaskListener6 = stageTaskListener12;
                                    if (!stageTaskListener6.mChildrenTaskInfo.contains(change9.getTaskInfo().taskId)) {
                                        i5 = i9;
                                        z8 = true;
                                    }
                                    if (!z13 || change10 == null) {
                                        i6 = i5;
                                    } else {
                                        i6 = i5;
                                        if (!stageTaskListener9.mChildrenTaskInfo.contains(change10.getTaskInfo().taskId)) {
                                            z9 = z15;
                                            stageTaskListener7 = stageTaskListener6;
                                            windowContainerTransaction = windowContainerTransaction2;
                                            change5 = change9;
                                            change6 = change11;
                                            z10 = true;
                                        }
                                        final boolean z17 = z9;
                                        change7 = change6;
                                        if (z16) {
                                            StringBuilder sb = new StringBuilder("Expected onTaskAppeared on ");
                                            sb.append(stageTaskListener11);
                                            sb.append(" to have been called with ");
                                            z11 = z16;
                                            sb.append(change7.getTaskInfo().taskId);
                                            sb.append(" before startAnimation().");
                                            Log.w("StageCoordinator", sb.toString());
                                        } else {
                                            z11 = z16;
                                        }
                                        if (z8) {
                                            Log.w("StageCoordinator", "Expected onTaskAppeared on " + stageTaskListener10 + " to have been called with " + change5.getTaskInfo().taskId + " before startAnimation().");
                                        }
                                        if (z10) {
                                            Log.w("StageCoordinator", "Expected onTaskAppeared on " + stageTaskListener9 + " to have been called with " + change10.getTaskInfo().taskId + " before startAnimation().");
                                        }
                                        stageTaskListener = stageTaskListener10;
                                        stageTaskListener2 = stageTaskListener11;
                                        final StageTaskListener stageTaskListener13 = stageTaskListener5;
                                        i7 = i6;
                                        final boolean z18 = z6;
                                        final boolean z19 = z11;
                                        stageCoordinator = this;
                                        enterSession3.mFinishedCallback = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda38
                                            @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                                            public final void onFinished(WindowContainerTransaction windowContainerTransaction3, SurfaceControl.Transaction transaction7) {
                                                final TransitionInfo.Change change13 = change7;
                                                final TransitionInfo.Change change14 = change5;
                                                TransitionInfo.Change change15 = change10;
                                                WindowContainerTransaction windowContainerTransaction4 = windowContainerTransaction;
                                                StageCoordinator stageCoordinator2 = this.f$0;
                                                stageCoordinator2.getClass();
                                                SplitScreenTransitions.EnterSession enterSession5 = enterSession3;
                                                if (!enterSession5.mResizeAnim) {
                                                    stageCoordinator2.notifySplitAnimationFinished();
                                                }
                                                boolean z20 = z18;
                                                boolean z21 = z17;
                                                if (change13 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z20) && !z21)) {
                                                    boolean z22 = z19;
                                                    StageTaskListener stageTaskListener14 = stageTaskListener13;
                                                    if (z22) {
                                                        stageTaskListener14.evictInvisibleChildren(windowContainerTransaction3);
                                                    } else {
                                                        stageTaskListener14.evictOtherChildren(windowContainerTransaction3, change13.getTaskInfo().taskId);
                                                    }
                                                }
                                                if (change14 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z20) && !z21)) {
                                                    boolean z23 = z8;
                                                    StageTaskListener stageTaskListener15 = stageTaskListener7;
                                                    if (z23) {
                                                        stageTaskListener15.evictInvisibleChildren(windowContainerTransaction3);
                                                    } else {
                                                        stageTaskListener15.evictOtherChildren(windowContainerTransaction3, change14.getTaskInfo().taskId);
                                                    }
                                                }
                                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && change15 != null && (!CoreRune.MW_MULTI_SPLIT_APP_PAIR || !z21)) {
                                                    StageTaskListener stageTaskListener16 = stageCoordinator2.mCellStage;
                                                    if (z10) {
                                                        stageTaskListener16.evictInvisibleChildren(windowContainerTransaction3);
                                                    } else {
                                                        stageTaskListener16.evictOtherChildren(windowContainerTransaction3, change15.getTaskInfo().taskId);
                                                    }
                                                }
                                                if (!windowContainerTransaction4.isEmpty()) {
                                                    windowContainerTransaction3.merge(windowContainerTransaction4, true);
                                                }
                                                if (enterSession5.mResizeAnim) {
                                                    stageCoordinator2.mSplitLayout.flingDividerToCenter(new StageCoordinator$$ExternalSyntheticLambda6(2, stageCoordinator2));
                                                }
                                                if (stageCoordinator2.mIsOpeningHomeDuringSplit) {
                                                    Slog.d("StageCoordinator", "skip to send reparentLeafTaskIfRelaunch");
                                                    stageCoordinator2.mIsOpeningHomeDuringSplit = false;
                                                } else {
                                                    windowContainerTransaction3.setReparentLeafTaskIfRelaunch(stageCoordinator2.mRootTaskInfo.token, false);
                                                }
                                                stageCoordinator2.mWindowDecorViewModel.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda42
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        TransitionInfo.Change change16 = change13;
                                                        TransitionInfo.Change change17 = change14;
                                                        WindowDecorViewModel windowDecorViewModel = (WindowDecorViewModel) obj;
                                                        if (change16 != null) {
                                                            windowDecorViewModel.onTaskInfoChanged(change16.getTaskInfo());
                                                        }
                                                        if (change17 != null) {
                                                            windowDecorViewModel.onTaskInfoChanged(change17.getTaskInfo());
                                                        }
                                                    }
                                                });
                                                stageCoordinator2.mPausingTasks.clear();
                                            }
                                        };
                                        type = transitionInfo.getType();
                                        int i10 = enterSession4.mExtraTransitType;
                                        if (type == 6 && !stageTaskListener2.mIsActive && i10 == 1005) {
                                            if (change7 == null && change5 == null) {
                                                stageCoordinator.requestEnterSplitSelect(SplitScreenUtils.reverseSplitPosition(stageCoordinator.mSideStagePosition), change7.getTaskInfo(), change7.getStartAbsBounds(), new WindowContainerTransaction());
                                            } else {
                                                if (change5 != null || change7 != null) {
                                                    throw new IllegalStateException("Attempting to restore to split but reparenting change not found");
                                                }
                                                stageCoordinator.requestEnterSplitSelect(stageCoordinator.mSideStagePosition, change5.getTaskInfo(), change5.getStartAbsBounds(), new WindowContainerTransaction());
                                            }
                                        }
                                        if (z13 && z18) {
                                            SplitLayout splitLayout = stageCoordinator.mSplitLayout;
                                            transaction3 = transaction2;
                                            splitLayout.releaseCellDivider(transaction3);
                                            if (!splitLayout.mCellInitialized) {
                                                splitLayout.mCellInitialized = true;
                                                splitLayout.mCellSplitWindowManager.init(splitLayout, splitLayout.mInsetsState, false, splitLayout.mDesktopState);
                                                splitLayout.mCellSnapAlgorithm = splitLayout.createCellSnapAlgorithm();
                                            }
                                            stageTaskListener3 = stageTaskListener9;
                                            stageTaskListener3.mSplitDecorManager.inflate(stageCoordinator.mContext, stageTaskListener3.mRootLeash);
                                            z7 = true;
                                            stageCoordinator.setCellDividerVisibility(transaction3, true);
                                            transaction3.reparent(stageCoordinator.mSplitLayout.getCellDividerLeash(), stageCoordinator.mRootTaskLeash);
                                            stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, transaction3, false);
                                            transaction3.show(stageCoordinator.mRootTaskLeash);
                                            stageCoordinator.setCellSplitVisible(true);
                                            stageCoordinator.mIsDropEntering = false;
                                            stageCoordinator.updateRecentTasksSplitPair();
                                            transitionInfo2 = transitionInfo;
                                            stageCoordinator.addCellDividerBarToTransition(transitionInfo2, true);
                                            iBinder2 = iBinder;
                                            transaction6 = transaction;
                                            windowContainerToken = null;
                                        } else {
                                            transitionInfo2 = transitionInfo;
                                            transaction3 = transaction2;
                                            stageTaskListener3 = stageTaskListener9;
                                            if (z13 || stageCoordinator.isMultiSplitActive() || !stageCoordinator.mCellDividerVisible) {
                                                transaction6 = transaction;
                                                windowContainerToken = null;
                                            } else {
                                                stageCoordinator.setCellSplitVisible(false);
                                                transaction6 = transaction;
                                                stageCoordinator.setCellDividerVisibility(transaction6, false);
                                                windowContainerToken = null;
                                                transaction6.setCrop(stageTaskListener3.mRootLeash, null);
                                                transaction6.hide(stageTaskListener3.mDimLayer);
                                            }
                                            iBinder2 = iBinder;
                                            if (!stageCoordinator.mSplitTransitions.isPendingEnter(iBinder2) && TransitionUtil.isOpeningType(transitionInfo2.getType()) && transitionInfo2.findChange(new SplitScreenTransitions$$ExternalSyntheticLambda12()) != null) {
                                                SplitBackgroundController splitBackgroundController = stageCoordinator.mSplitBackgroundController;
                                                if (splitBackgroundController.mOneshotStartAlpha != 0.0f) {
                                                    splitBackgroundController.mOneshotStartAlpha = 0.0f;
                                                }
                                            }
                                            boolean z20 = !z13 && i7 == 1102;
                                            stageCoordinator.finishEnterSplitScreen(transaction3, z20);
                                            if (stageCoordinator.mDividerVisible || (CoreRune.MW_SPLIT_SHELL_TRANSITION && i10 == 1103)) {
                                                z7 = true;
                                                stageCoordinator.addDividerBarToTransition(transitionInfo2, true);
                                                int i11 = stageCoordinator.mSplitState.mState;
                                            } else {
                                                Log.d("StageCoordinator", "startPendingEnterAnimation: skip addDividerBarToTransition, divider is already visible.");
                                                z7 = true;
                                            }
                                            if (z13 && z20) {
                                                stageCoordinator.addCellDividerBarToTransition(transitionInfo2, z7);
                                            }
                                        }
                                        stageCoordinator.setLaunchAdjacentDisabled(z7);
                                        Runnable runnable2 = stageCoordinator.mReEnableLaunchAdjacentOnRoot;
                                        Handler handler2 = stageCoordinator.mMainHandler;
                                        handler2.removeCallbacks(runnable2);
                                        handler2.postDelayed(stageCoordinator.mReEnableLaunchAdjacentOnRoot, 1000L);
                                        transitionFinishCallback2 = transitionFinishCallback;
                                        transaction4 = transaction6;
                                    }
                                    z9 = z15;
                                    stageTaskListener7 = stageTaskListener6;
                                    windowContainerTransaction = windowContainerTransaction2;
                                    change5 = change9;
                                    change6 = change11;
                                    z10 = false;
                                    final boolean z172 = z9;
                                    change7 = change6;
                                    if (z16) {
                                    }
                                    if (z8) {
                                    }
                                    if (z10) {
                                    }
                                    stageTaskListener = stageTaskListener10;
                                    stageTaskListener2 = stageTaskListener11;
                                    final StageTaskListener stageTaskListener132 = stageTaskListener5;
                                    i7 = i6;
                                    final boolean z182 = z6;
                                    final boolean z192 = z11;
                                    stageCoordinator = this;
                                    enterSession3.mFinishedCallback = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda38
                                        @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                                        public final void onFinished(WindowContainerTransaction windowContainerTransaction3, SurfaceControl.Transaction transaction7) {
                                            final TransitionInfo.Change change13 = change7;
                                            final TransitionInfo.Change change14 = change5;
                                            TransitionInfo.Change change15 = change10;
                                            WindowContainerTransaction windowContainerTransaction4 = windowContainerTransaction;
                                            StageCoordinator stageCoordinator2 = this.f$0;
                                            stageCoordinator2.getClass();
                                            SplitScreenTransitions.EnterSession enterSession5 = enterSession3;
                                            if (!enterSession5.mResizeAnim) {
                                                stageCoordinator2.notifySplitAnimationFinished();
                                            }
                                            boolean z202 = z182;
                                            boolean z21 = z172;
                                            if (change13 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z202) && !z21)) {
                                                boolean z22 = z192;
                                                StageTaskListener stageTaskListener14 = stageTaskListener132;
                                                if (z22) {
                                                    stageTaskListener14.evictInvisibleChildren(windowContainerTransaction3);
                                                } else {
                                                    stageTaskListener14.evictOtherChildren(windowContainerTransaction3, change13.getTaskInfo().taskId);
                                                }
                                            }
                                            if (change14 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z202) && !z21)) {
                                                boolean z23 = z8;
                                                StageTaskListener stageTaskListener15 = stageTaskListener7;
                                                if (z23) {
                                                    stageTaskListener15.evictInvisibleChildren(windowContainerTransaction3);
                                                } else {
                                                    stageTaskListener15.evictOtherChildren(windowContainerTransaction3, change14.getTaskInfo().taskId);
                                                }
                                            }
                                            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && change15 != null && (!CoreRune.MW_MULTI_SPLIT_APP_PAIR || !z21)) {
                                                StageTaskListener stageTaskListener16 = stageCoordinator2.mCellStage;
                                                if (z10) {
                                                    stageTaskListener16.evictInvisibleChildren(windowContainerTransaction3);
                                                } else {
                                                    stageTaskListener16.evictOtherChildren(windowContainerTransaction3, change15.getTaskInfo().taskId);
                                                }
                                            }
                                            if (!windowContainerTransaction4.isEmpty()) {
                                                windowContainerTransaction3.merge(windowContainerTransaction4, true);
                                            }
                                            if (enterSession5.mResizeAnim) {
                                                stageCoordinator2.mSplitLayout.flingDividerToCenter(new StageCoordinator$$ExternalSyntheticLambda6(2, stageCoordinator2));
                                            }
                                            if (stageCoordinator2.mIsOpeningHomeDuringSplit) {
                                                Slog.d("StageCoordinator", "skip to send reparentLeafTaskIfRelaunch");
                                                stageCoordinator2.mIsOpeningHomeDuringSplit = false;
                                            } else {
                                                windowContainerTransaction3.setReparentLeafTaskIfRelaunch(stageCoordinator2.mRootTaskInfo.token, false);
                                            }
                                            stageCoordinator2.mWindowDecorViewModel.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda42
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    TransitionInfo.Change change16 = change13;
                                                    TransitionInfo.Change change17 = change14;
                                                    WindowDecorViewModel windowDecorViewModel = (WindowDecorViewModel) obj;
                                                    if (change16 != null) {
                                                        windowDecorViewModel.onTaskInfoChanged(change16.getTaskInfo());
                                                    }
                                                    if (change17 != null) {
                                                        windowDecorViewModel.onTaskInfoChanged(change17.getTaskInfo());
                                                    }
                                                }
                                            });
                                            stageCoordinator2.mPausingTasks.clear();
                                        }
                                    };
                                    type = transitionInfo.getType();
                                    int i102 = enterSession4.mExtraTransitType;
                                    if (type == 6) {
                                        if (change7 == null) {
                                            if (change5 != null) {
                                            }
                                            throw new IllegalStateException("Attempting to restore to split but reparenting change not found");
                                        }
                                    }
                                    if (z13) {
                                        transitionInfo2 = transitionInfo;
                                        transaction3 = transaction2;
                                        stageTaskListener3 = stageTaskListener9;
                                        if (z13) {
                                            transaction6 = transaction;
                                            windowContainerToken = null;
                                            iBinder2 = iBinder;
                                            if (!stageCoordinator.mSplitTransitions.isPendingEnter(iBinder2)) {
                                                if (z13) {
                                                    stageCoordinator.finishEnterSplitScreen(transaction3, z20);
                                                    if (stageCoordinator.mDividerVisible) {
                                                    }
                                                    z7 = true;
                                                    stageCoordinator.addDividerBarToTransition(transitionInfo2, true);
                                                    int i112 = stageCoordinator.mSplitState.mState;
                                                    if (z13) {
                                                        stageCoordinator.addCellDividerBarToTransition(transitionInfo2, z7);
                                                    }
                                                    stageCoordinator.setLaunchAdjacentDisabled(z7);
                                                    Runnable runnable22 = stageCoordinator.mReEnableLaunchAdjacentOnRoot;
                                                    Handler handler22 = stageCoordinator.mMainHandler;
                                                    handler22.removeCallbacks(runnable22);
                                                    handler22.postDelayed(stageCoordinator.mReEnableLaunchAdjacentOnRoot, 1000L);
                                                    transitionFinishCallback2 = transitionFinishCallback;
                                                    transaction4 = transaction6;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    stageTaskListener5 = stageTaskListener4;
                                    stageTaskListener6 = stageTaskListener12;
                                }
                                i5 = i9;
                                z8 = false;
                                if (z13) {
                                    i6 = i5;
                                    z9 = z15;
                                    stageTaskListener7 = stageTaskListener6;
                                    windowContainerTransaction = windowContainerTransaction2;
                                    change5 = change9;
                                    change6 = change11;
                                    z10 = false;
                                    final boolean z1722 = z9;
                                    change7 = change6;
                                    if (z16) {
                                    }
                                    if (z8) {
                                    }
                                    if (z10) {
                                    }
                                    stageTaskListener = stageTaskListener10;
                                    stageTaskListener2 = stageTaskListener11;
                                    final StageTaskListener stageTaskListener1322 = stageTaskListener5;
                                    i7 = i6;
                                    final boolean z1822 = z6;
                                    final boolean z1922 = z11;
                                    stageCoordinator = this;
                                    enterSession3.mFinishedCallback = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda38
                                        @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                                        public final void onFinished(WindowContainerTransaction windowContainerTransaction3, SurfaceControl.Transaction transaction7) {
                                            final TransitionInfo.Change change13 = change7;
                                            final TransitionInfo.Change change14 = change5;
                                            TransitionInfo.Change change15 = change10;
                                            WindowContainerTransaction windowContainerTransaction4 = windowContainerTransaction;
                                            StageCoordinator stageCoordinator2 = this.f$0;
                                            stageCoordinator2.getClass();
                                            SplitScreenTransitions.EnterSession enterSession5 = enterSession3;
                                            if (!enterSession5.mResizeAnim) {
                                                stageCoordinator2.notifySplitAnimationFinished();
                                            }
                                            boolean z202 = z1822;
                                            boolean z21 = z1722;
                                            if (change13 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z202) && !z21)) {
                                                boolean z22 = z1922;
                                                StageTaskListener stageTaskListener14 = stageTaskListener1322;
                                                if (z22) {
                                                    stageTaskListener14.evictInvisibleChildren(windowContainerTransaction3);
                                                } else {
                                                    stageTaskListener14.evictOtherChildren(windowContainerTransaction3, change13.getTaskInfo().taskId);
                                                }
                                            }
                                            if (change14 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z202) && !z21)) {
                                                boolean z23 = z8;
                                                StageTaskListener stageTaskListener15 = stageTaskListener7;
                                                if (z23) {
                                                    stageTaskListener15.evictInvisibleChildren(windowContainerTransaction3);
                                                } else {
                                                    stageTaskListener15.evictOtherChildren(windowContainerTransaction3, change14.getTaskInfo().taskId);
                                                }
                                            }
                                            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && change15 != null && (!CoreRune.MW_MULTI_SPLIT_APP_PAIR || !z21)) {
                                                StageTaskListener stageTaskListener16 = stageCoordinator2.mCellStage;
                                                if (z10) {
                                                    stageTaskListener16.evictInvisibleChildren(windowContainerTransaction3);
                                                } else {
                                                    stageTaskListener16.evictOtherChildren(windowContainerTransaction3, change15.getTaskInfo().taskId);
                                                }
                                            }
                                            if (!windowContainerTransaction4.isEmpty()) {
                                                windowContainerTransaction3.merge(windowContainerTransaction4, true);
                                            }
                                            if (enterSession5.mResizeAnim) {
                                                stageCoordinator2.mSplitLayout.flingDividerToCenter(new StageCoordinator$$ExternalSyntheticLambda6(2, stageCoordinator2));
                                            }
                                            if (stageCoordinator2.mIsOpeningHomeDuringSplit) {
                                                Slog.d("StageCoordinator", "skip to send reparentLeafTaskIfRelaunch");
                                                stageCoordinator2.mIsOpeningHomeDuringSplit = false;
                                            } else {
                                                windowContainerTransaction3.setReparentLeafTaskIfRelaunch(stageCoordinator2.mRootTaskInfo.token, false);
                                            }
                                            stageCoordinator2.mWindowDecorViewModel.ifPresent(new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda42
                                                @Override // java.util.function.Consumer
                                                public final void accept(Object obj) {
                                                    TransitionInfo.Change change16 = change13;
                                                    TransitionInfo.Change change17 = change14;
                                                    WindowDecorViewModel windowDecorViewModel = (WindowDecorViewModel) obj;
                                                    if (change16 != null) {
                                                        windowDecorViewModel.onTaskInfoChanged(change16.getTaskInfo());
                                                    }
                                                    if (change17 != null) {
                                                        windowDecorViewModel.onTaskInfoChanged(change17.getTaskInfo());
                                                    }
                                                }
                                            });
                                            stageCoordinator2.mPausingTasks.clear();
                                        }
                                    };
                                    type = transitionInfo.getType();
                                    int i1022 = enterSession4.mExtraTransitType;
                                    if (type == 6) {
                                    }
                                    if (z13) {
                                    }
                                }
                            } else {
                                stageTaskListener4 = stageOfTask;
                            }
                            if (change9 == null) {
                            }
                            i5 = i9;
                            z8 = false;
                            if (z13) {
                            }
                        }
                    } else if (change11 == null || change9 == null) {
                        StringBuilder sb2 = new StringBuilder("  info:");
                        sb2.append(" mainChild=" + change11);
                        sb2.append(" sideChild=" + change9);
                        final int i12 = change11 != null ? 0 : change9 != null ? 1 : -1;
                        SplitScreenTransitions.TransitionFinishedCallback transitionFinishedCallback = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda37
                            @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                            public final void onFinished(WindowContainerTransaction windowContainerTransaction3, SurfaceControl.Transaction transaction7) {
                                StageCoordinator stageCoordinator2 = stageCoordinator;
                                stageCoordinator2.getClass();
                                boolean z21 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                                int i13 = i12;
                                if (z21) {
                                    windowContainerTransaction3.setDisplayIdForChangeTransition(0, "enter_split_failed");
                                    stageCoordinator2.prepareExitSplitScreen(i13, 0, windowContainerTransaction3, false);
                                } else {
                                    stageCoordinator2.prepareExitSplitScreen(i13, 0, windowContainerTransaction3, true);
                                }
                                stageCoordinator2.logExit(0);
                            }
                        };
                        enterSession4.mCanceled = true;
                        enterSession4.mFinishedCallback = transitionFinishedCallback;
                        Log.w("StageCoordinator", SplitScreenUtils.splitFailureMessage("startPendingEnterAnimation", "launched 2 tasks in split, but didn't receive 2 tasks in transition. Possibly one of them failed to launch (foundPausingTask=" + z12 + ")" + ((Object) sb2)));
                        if (stageCoordinator.mRecentTasks.isPresent() && change11 != null) {
                            ((RecentTasksController) stageCoordinator.mRecentTasks.get()).removeSplitPair(change11.getTaskInfo().taskId);
                        }
                        if (stageCoordinator.mRecentTasks.isPresent() && change9 != null) {
                            ((RecentTasksController) stageCoordinator.mRecentTasks.get()).removeSplitPair(change9.getTaskInfo().taskId);
                        }
                        OneShotRemoteHandler oneShotRemoteHandler = enterSession4.mRemoteHandler;
                        if (oneShotRemoteHandler != null) {
                            oneShotRemoteHandler.onTransitionConsumed(iBinder, false, transaction2);
                        }
                        stageCoordinator.mSplitUnsupportedToast.setText(R.string.dock_non_resizeble_failed_to_dock_text);
                        stageCoordinator.mSplitUnsupportedToast.show();
                        stageCoordinator.notifySplitAnimationFinished();
                        transitionInfo2 = transitionInfo;
                        stageTaskListener = stageTaskListener10;
                        iBinder2 = iBinder;
                        z7 = true;
                        windowContainerToken = null;
                        stageTaskListener2 = stageTaskListener11;
                        stageTaskListener3 = stageTaskListener9;
                        transaction3 = transaction2;
                        transaction6 = transaction;
                        stageCoordinator.setLaunchAdjacentDisabled(z7);
                        Runnable runnable222 = stageCoordinator.mReEnableLaunchAdjacentOnRoot;
                        Handler handler222 = stageCoordinator.mMainHandler;
                        handler222.removeCallbacks(runnable222);
                        handler222.postDelayed(stageCoordinator.mReEnableLaunchAdjacentOnRoot, 1000L);
                        transitionFinishCallback2 = transitionFinishCallback;
                        transaction4 = transaction6;
                    }
                }
                if (change11 != null) {
                }
                if (change9 == null) {
                }
                i5 = i9;
                z8 = false;
                if (z13) {
                }
            }
            if (change11 == null && change9 == null) {
                StageCoordinator$$ExternalSyntheticLambda0 stageCoordinator$$ExternalSyntheticLambda0 = new StageCoordinator$$ExternalSyntheticLambda0(6, stageCoordinator);
                enterSession4.mCanceled = true;
                enterSession4.mFinishedCallback = stageCoordinator$$ExternalSyntheticLambda0;
                transitionInfo2 = transitionInfo;
                stageTaskListener = stageTaskListener10;
                iBinder2 = iBinder;
                z7 = true;
                windowContainerToken = null;
                stageTaskListener2 = stageTaskListener11;
                stageTaskListener3 = stageTaskListener9;
                transaction3 = transaction2;
                transaction6 = transaction;
                stageCoordinator.setLaunchAdjacentDisabled(z7);
                Runnable runnable2222 = stageCoordinator.mReEnableLaunchAdjacentOnRoot;
                Handler handler2222 = stageCoordinator.mMainHandler;
                handler2222.removeCallbacks(runnable2222);
                handler2222.postDelayed(stageCoordinator.mReEnableLaunchAdjacentOnRoot, 1000L);
                transitionFinishCallback2 = transitionFinishCallback;
                transaction4 = transaction6;
            }
            z6 = z14;
            if (change11 != null) {
            }
            if (change9 == null) {
            }
            i5 = i9;
            z8 = false;
            if (z13) {
            }
        } else {
            transitionInfo2 = transitionInfo;
            stageTaskListener = stageTaskListener10;
            iBinder2 = iBinder;
            windowContainerToken = null;
            stageTaskListener2 = stageTaskListener11;
            stageTaskListener3 = stageTaskListener9;
            transaction3 = transaction2;
            if (stageCoordinator.mSplitTransitions.isPendingDismiss(iBinder2)) {
                final SplitScreenTransitions.DismissSession dismissSession = stageCoordinator.mSplitTransitions.mPendingDismiss;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1384474473890067295L, 1, Long.valueOf(transitionInfo2.getDebugId()), String.valueOf(dismissSession));
                }
                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                    transaction4 = transaction;
                    stageCoordinator.prepareDismissAnimation(dismissSession.mDismissTop, dismissSession.mReason, transitionInfo2, transaction4, transaction3, dismissSession.mIsMultiSplitDismissed);
                    stageCoordinator = this;
                    transitionInfo2 = transitionInfo;
                    transaction3 = transaction2;
                } else {
                    stageCoordinator = this;
                    transitionInfo2 = transitionInfo;
                    transaction4 = transaction;
                    transaction3 = transaction2;
                    stageCoordinator.prepareDismissAnimation(dismissSession.mDismissTop, dismissSession.mReason, transitionInfo2, transaction4, transaction3, false);
                }
                if (dismissSession.mDismissTop == -1) {
                    stageCoordinator.setDividerVisibility(transaction4, false);
                    stageCoordinator.mSplitLayout.release(transaction4);
                    stageCoordinator.mSplitTransitions.mPendingDismiss = null;
                    return false;
                }
                dismissSession.mFinishedCallback = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda39
                    @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                    public final void onFinished(WindowContainerTransaction windowContainerTransaction3, SurfaceControl.Transaction transaction7) {
                        StageCoordinator stageCoordinator2 = this.f$0;
                        stageCoordinator2.getClass();
                        boolean z21 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
                        SplitScreenTransitions.DismissSession dismissSession2 = dismissSession;
                        if (!z21 || !dismissSession2.mIsMultiSplitDismissed) {
                            stageCoordinator2.mMainStage.mSplitDecorManager.release(transaction7);
                            stageCoordinator2.mSideStage.mSplitDecorManager.release(transaction7);
                        }
                        if (z21) {
                            stageCoordinator2.mCellStage.mSplitDecorManager.release(transaction7);
                        }
                        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && dismissSession2.mIsMultiSplitDismissed) {
                            stageCoordinator2.updateRecentTasksSplitPair();
                        }
                        windowContainerTransaction3.setReparentLeafTaskIfRelaunch(stageCoordinator2.mRootTaskInfo.token, false);
                    }
                };
            } else {
                transaction4 = transaction;
                if (stageCoordinator.mSplitTransitions.isPendingResize(iBinder2)) {
                    if (!CoreRune.MW_SPLIT_SHELL_TRANSITION || !transitionInfo2.hasChangeTransition()) {
                        HashMap map = new HashMap();
                        map.put(stageTaskListener2.mRootTaskInfo.getToken(), stageTaskListener2.mSplitDecorManager);
                        map.put(stageTaskListener.mRootTaskInfo.getToken(), stageTaskListener.mSplitDecorManager);
                        SplitScreenTransitions splitScreenTransitions3 = stageCoordinator.mSplitTransitions;
                        splitScreenTransitions3.getClass();
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                            i = 1;
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -8615838818752709148L, 1, Long.valueOf(transitionInfo2.getDebugId()));
                        } else {
                            i = 1;
                        }
                        splitScreenTransitions3.mAnimatingTransition = iBinder2;
                        splitScreenTransitions3.mFinishTransaction = transaction3;
                        splitScreenTransitions3.mFinishCallback = transitionFinishCallback;
                        Set setKeySet = map.keySet();
                        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo2, i); iM >= 0; iM--) {
                            TransitionInfo.Change change13 = (TransitionInfo.Change) transitionInfo2.getChanges().get(iM);
                            if (setKeySet.contains(change13.getContainer())) {
                                SurfaceControl leash = change13.getLeash();
                                transaction4.setPosition(leash, change13.getEndAbsBounds().left, change13.getEndAbsBounds().top);
                                transaction4.setWindowCrop(leash, change13.getEndAbsBounds().width(), change13.getEndAbsBounds().height());
                                SplitDecorManager splitDecorManager = (SplitDecorManager) map.get(change13.getContainer());
                                ValueAnimator valueAnimator = new ValueAnimator();
                                splitScreenTransitions3.mAnimations.add(valueAnimator);
                                SurfaceControl snapshot = change13.getSnapshot();
                                splitDecorManager.getClass();
                                if (snapshot != null && snapshot.isValid() && !splitDecorManager.mShown && splitDecorManager.mIsCurrentlyChanging && !splitDecorManager.mOldMainBounds.equals(splitDecorManager.mInstantaneousBounds)) {
                                    ValueAnimator valueAnimator2 = splitDecorManager.mScreenshotAnimator;
                                    if (valueAnimator2 == null || !valueAnimator2.isRunning()) {
                                        SurfaceControl surfaceControl = splitDecorManager.mScreenshot;
                                        if (surfaceControl != null) {
                                            transaction4.remove(surfaceControl);
                                        }
                                    } else {
                                        splitDecorManager.mScreenshotAnimator.cancel();
                                    }
                                    splitDecorManager.mScreenshot = snapshot;
                                    transaction4.reparent(snapshot, splitDecorManager.mHostLeash);
                                    transaction4.setLayer(snapshot, 2147483646);
                                }
                                splitDecorManager.onResized(transaction4, new SplitScreenTransitions$$ExternalSyntheticLambda9(splitScreenTransitions3, valueAnimator));
                            }
                        }
                        transaction4.apply();
                        splitScreenTransitions3.onFinish(null);
                        return true;
                    }
                    stageCoordinator.addDividerBarToTransition(transitionInfo2, true);
                    if (stageCoordinator.isMultiSplitScreenVisible()) {
                        stageCoordinator.addCellDividerBarToTransition(transitionInfo2, true);
                    }
                }
            }
            transitionFinishCallback2 = transitionFinishCallback;
        }
        StageTaskListener stageTaskListener14 = stageTaskListener;
        WindowContainerToken windowContainerToken8 = stageTaskListener2.mRootTaskInfo.token;
        WindowContainerToken windowContainerToken9 = stageTaskListener14.mRootTaskInfo.token;
        WindowContainerToken windowContainerToken10 = CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION ? stageTaskListener3.mRootTaskInfo.token : windowContainerToken;
        if (stageCoordinator.isSplitScreenVisible() && (alertDialog = stageCoordinator.mSplitLayout.mSplitWindowManager.mDividerPanel.mAddToAppPairDialog) != null) {
            alertDialog.dismiss();
        }
        SplitScreenTransitions splitScreenTransitions4 = stageCoordinator.mSplitTransitions;
        WindowContainerToken windowContainerToken11 = stageCoordinator.mRootTaskInfo.token;
        splitScreenTransitions4.getClass();
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            windowContainerToken2 = windowContainerToken8;
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 8470564445513330979L, 1, Long.valueOf(transitionInfo2.getDebugId()));
        } else {
            windowContainerToken2 = windowContainerToken8;
        }
        splitScreenTransitions4.mAnimatingTransition = iBinder2;
        splitScreenTransitions4.mFinishTransaction = transaction3;
        splitScreenTransitions4.mFinishCallback = transitionFinishCallback2;
        SplitScreenTransitions.TransitSession pendingTransition = splitScreenTransitions4.getPendingTransition(iBinder2);
        if (pendingTransition != null) {
            if (!pendingTransition.mCanceled) {
                boolean z21 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                OneShotRemoteHandler oneShotRemoteHandler2 = pendingTransition.mRemoteHandler;
                if (z21 && oneShotRemoteHandler2 == null && splitScreenTransitions4.isPendingEnter(iBinder2)) {
                    Iterator it = transitionInfo2.getChanges().iterator();
                    if (it.hasNext()) {
                        TransitionInfo.Change change14 = (TransitionInfo.Change) it.next();
                        z5 = change14.getAnimationOptions() != null ? true : true;
                    }
                }
                if (oneShotRemoteHandler2 != null) {
                    oneShotRemoteHandler2.startAnimation(iBinder2, transitionInfo2, transaction4, transaction2, splitScreenTransitions4.mRemoteFinishCB);
                    splitScreenTransitions4.mActiveRemoteHandler = oneShotRemoteHandler2;
                    return true;
                }
            }
            transaction4.apply();
            splitScreenTransitions4.onFinish(null);
            return z5;
        }
        IBinder iBinder3 = iBinder2;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            windowContainerToken3 = windowContainerToken10;
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1762009573334400102L, 1, Long.valueOf(transitionInfo2.getDebugId()));
        } else {
            windowContainerToken3 = windowContainerToken10;
        }
        boolean zIsPendingEnter2 = splitScreenTransitions4.isPendingEnter(iBinder3);
        int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo2, 1);
        while (true) {
            transitions = splitScreenTransitions4.mTransitions;
            if (iM2 < 0) {
                break;
            }
            TransitionInfo.Change change15 = (TransitionInfo.Change) transitionInfo2.getChanges().get(iM2);
            SurfaceControl leash2 = change15.getLeash();
            int mode = ((TransitionInfo.Change) transitionInfo2.getChanges().get(iM2)).getMode();
            int iRootIndexFor = TransitionUtil.rootIndexFor(change15, transitionInfo2);
            if (mode != 6 || change15.getParent() == null) {
                change = change15;
                windowContainerToken4 = windowContainerToken3;
            } else {
                TransitionInfo.Change change16 = transitionInfo2.getChange(change15.getParent());
                if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                    change4 = change16;
                    if (change15.hasFlags(512) && !change15.hasFlags(1024) && change4 != null && change4.getSnapshot() != null) {
                        Log.d("SplitScreenTransitions", "Except TaskFragment changes, it's parent has snapshot. it replaces task fragment changes.");
                        transaction4.setWindowCrop(change15.getLeash(), null);
                        transitionInfo3 = transitionInfo2;
                        transaction5 = transaction4;
                        splitScreenTransitions = splitScreenTransitions4;
                        windowContainerToken5 = windowContainerToken11;
                        windowContainerToken4 = windowContainerToken3;
                        windowContainerToken6 = windowContainerToken2;
                        iM2--;
                        transitionInfo2 = transitionInfo3;
                        transaction4 = transaction5;
                        splitScreenTransitions4 = splitScreenTransitions;
                        windowContainerToken11 = windowContainerToken5;
                        windowContainerToken3 = windowContainerToken4;
                        windowContainerToken2 = windowContainerToken6;
                    }
                } else {
                    change4 = change16;
                }
                transaction4.show(change4.getLeash());
                windowContainerToken4 = windowContainerToken3;
                transaction4.setAlpha(change4.getLeash(), 1.0f);
                boolean z22 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                if ((!z22 || change15.getChangeLeash() == null) && (!change15.hasFlags(64) || change15.hasFlags(512) || change4.getChangeLeash() == null)) {
                    if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && transitionInfo2.isSeparatedFromCustomDisplayChange()) {
                        change = change15;
                    } else {
                        change = change15;
                        transaction4.reparent(change4.getLeash(), transitionInfo2.getRoot(iRootIndexFor).getLeash());
                        transaction4.setLayer(change4.getLeash(), transitionInfo2.getChanges().size() - iM2);
                    }
                    if (z22 && zIsPendingEnter2 && change.getTaskInfo() != null && windowContainerToken11.equals(change4.getContainer())) {
                        Log.d("SplitScreenTransitions", "prevent child to reparent to topRoot during split enter");
                    } else {
                        splitScreenTransitions4.mFinishTransaction.reparent(leash2, change4.getLeash());
                        splitScreenTransitions4.mFinishTransaction.setPosition(leash2, change.getEndRelOffset().x, change.getEndRelOffset().y);
                    }
                }
                iM2--;
                transitionInfo2 = transitionInfo3;
                transaction4 = transaction5;
                splitScreenTransitions4 = splitScreenTransitions;
                windowContainerToken11 = windowContainerToken5;
                windowContainerToken3 = windowContainerToken4;
                windowContainerToken2 = windowContainerToken6;
            }
            boolean z23 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
            if (!z23 || change.getChangeLeash() == null) {
                transitionInfo3 = transitionInfo2;
                transaction5 = transaction4;
                z = z23;
                change2 = change;
                i2 = 512;
            } else {
                boolean z24 = TransitionUtil.isClosingType(change.getMode()) && change.getChangeTransitMode() == 2;
                StringBuilder sb3 = new StringBuilder("buildChangeTransition: leash=");
                sb3.append(change.getLeash());
                sb3.append(", snapshot=");
                sb3.append(change.getSnapshot());
                sb3.append(z24 ? ", shouldBeHidden=true" : "");
                Log.d("SplitScreenTransitions", sb3.toString());
                z = z23;
                change2 = change;
                i2 = 512;
                transitions.mChangeTransitProvider.buildChangeTransitionAnimators(splitScreenTransitions4.mAnimations, change2, new SplitScreenTransitions$$ExternalSyntheticLambda0(splitScreenTransitions4, 3), transaction4, transitionInfo);
                transaction5 = transaction4;
                transitionInfo3 = transitionInfo;
                if (z24) {
                    splitScreenTransitions4.mFinishTransaction.hide(change2.getLeash());
                }
            }
            if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION && change2.hasFlags(i2) && !change2.hasFlags(1024) && change2.getParent() != null && (change3 = transitionInfo3.getChange(change2.getParent())) != null && change3.getSnapshot() != null) {
                if (mode == 2) {
                    transaction5.hide(change2.getLeash());
                } else if (TransitionUtil.isOpeningType(transitionInfo3.getType())) {
                    transaction5.setAlpha(change2.getLeash(), 1.0f);
                }
                splitScreenTransitions = splitScreenTransitions4;
                windowContainerToken5 = windowContainerToken11;
                windowContainerToken6 = windowContainerToken2;
                iM2--;
                transitionInfo2 = transitionInfo3;
                transaction4 = transaction5;
                splitScreenTransitions4 = splitScreenTransitions;
                windowContainerToken11 = windowContainerToken5;
                windowContainerToken3 = windowContainerToken4;
                windowContainerToken2 = windowContainerToken6;
            }
            boolean isCellDivider = change2.getIsCellDivider();
            boolean zEquals3 = windowContainerToken11.equals(change2.getContainer());
            windowContainerToken5 = windowContainerToken11;
            WindowContainerToken windowContainerToken12 = windowContainerToken2;
            boolean zEquals4 = windowContainerToken12.equals(change2.getContainer());
            boolean zEquals5 = windowContainerToken9.equals(change2.getContainer());
            boolean z25 = change2.getFlags() == 16777216;
            boolean zEquals6 = windowContainerToken12.equals(change2.getParent());
            boolean z26 = z25;
            boolean zEquals7 = windowContainerToken9.equals(change2.getParent());
            boolean z27 = CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION;
            if (!z27 || windowContainerToken4 == null) {
                z2 = zEquals7;
                windowContainerToken6 = windowContainerToken12;
                windowContainerToken7 = windowContainerToken4;
                zEquals = false;
                zEquals2 = false;
            } else {
                z2 = zEquals7;
                windowContainerToken6 = windowContainerToken12;
                windowContainerToken7 = windowContainerToken4;
                zEquals2 = windowContainerToken7.equals(change2.getContainer());
                zEquals = windowContainerToken7.equals(change2.getParent());
            }
            if (zIsPendingEnter2 && (zEquals6 || z2 || zEquals)) {
                z3 = zEquals3;
                splitScreenTransitions4.mFinishTransaction.setPosition(leash2, change2.getEndRelOffset().x, change2.getEndRelOffset().y);
                splitScreenTransitions4.mFinishTransaction.setCrop(leash2, null);
            } else {
                z3 = zEquals3;
                if (z3) {
                    transaction5.setAlpha(leash2, 1.0f);
                    transaction5.show(leash2);
                } else if ((zIsPendingEnter2 && zEquals4) || zEquals5 || zEquals2) {
                    transaction5.setPosition(leash2, change2.getEndAbsBounds().left, change2.getEndAbsBounds().top);
                    transaction5.setWindowCrop(leash2, change2.getEndAbsBounds().width(), change2.getEndAbsBounds().height());
                    if (z27 && zEquals2) {
                        transaction5.setAlpha(leash2, 1.0f);
                    }
                } else if (z26) {
                    transaction5.setPosition(leash2, change2.getEndAbsBounds().left, change2.getEndAbsBounds().top);
                    transaction5.setLayer(leash2, Integer.MAX_VALUE);
                    transaction5.show(leash2);
                }
            }
            if (z3 || zEquals4 || zEquals5 || ((z27 && zEquals2) || ((change2.getTaskInfo() == null && !z26) || ((CoreRune.MW_SHELL_CHANGE_TRANSITION && change2.getChangeLeash() != null) || (zIsPendingEnter2 && splitScreenTransitions4.mPendingEnter.mResizeAnim))))) {
                splitScreenTransitions = splitScreenTransitions4;
                windowContainerToken4 = windowContainerToken7;
                iM2--;
                transitionInfo2 = transitionInfo3;
                transaction4 = transaction5;
                splitScreenTransitions4 = splitScreenTransitions;
                windowContainerToken11 = windowContainerToken5;
                windowContainerToken3 = windowContainerToken4;
                windowContainerToken2 = windowContainerToken6;
            } else {
                if (splitScreenTransitions4.isPendingDismiss(iBinder3)) {
                    i3 = 4;
                    if (splitScreenTransitions4.mPendingDismiss.mReason == 4) {
                        splitScreenTransitions = splitScreenTransitions4;
                    }
                    windowContainerToken4 = windowContainerToken7;
                    iM2--;
                    transitionInfo2 = transitionInfo3;
                    transaction4 = transaction5;
                    splitScreenTransitions4 = splitScreenTransitions;
                    windowContainerToken11 = windowContainerToken5;
                    windowContainerToken3 = windowContainerToken4;
                    windowContainerToken2 = windowContainerToken6;
                } else {
                    i3 = 4;
                }
                boolean zIsOpeningType = TransitionUtil.isOpeningType(transitionInfo3.getType());
                boolean zIsHomeOrRecents = TransitionUtil.isHomeOrRecents(change2);
                if (z && zIsHomeOrRecents && zIsPendingEnter2 && zIsOpeningType && (mode == 2 || mode == i3)) {
                    SplitScreenTransitions.EnterSession enterSession5 = splitScreenTransitions4.mPendingEnter;
                    boolean z28 = enterSession5 != null && enterSession5.mExtraTransitType == 1103;
                    splitScreenTransitions = splitScreenTransitions4;
                    splitScreenTransitions.startCustomFadeAnimation(leash2, false, false, false, !z28, false);
                    windowContainerToken4 = windowContainerToken7;
                    iM2--;
                    transitionInfo2 = transitionInfo3;
                    transaction4 = transaction5;
                    splitScreenTransitions4 = splitScreenTransitions;
                    windowContainerToken11 = windowContainerToken5;
                    windowContainerToken3 = windowContainerToken4;
                    windowContainerToken2 = windowContainerToken6;
                } else {
                    SplitScreenTransitions splitScreenTransitions5 = splitScreenTransitions4;
                    if (z && !zIsHomeOrRecents && zIsPendingEnter2) {
                        SplitScreenTransitions.EnterSession enterSession6 = splitScreenTransitions5.mPendingEnter;
                        if (enterSession6 == null || enterSession6.mExtraTransitType != 1103) {
                            splitScreenTransitions2 = splitScreenTransitions5;
                            windowContainerToken4 = windowContainerToken7;
                            i4 = 1;
                        } else if (z26) {
                            splitScreenTransitions = splitScreenTransitions5;
                            splitScreenTransitions.startCustomFadeAnimation(leash2, true, true, true, false, isCellDivider);
                            windowContainerToken4 = windowContainerToken7;
                            iM2--;
                            transitionInfo2 = transitionInfo3;
                            transaction4 = transaction5;
                            splitScreenTransitions4 = splitScreenTransitions;
                            windowContainerToken11 = windowContainerToken5;
                            windowContainerToken3 = windowContainerToken4;
                            windowContainerToken2 = windowContainerToken6;
                        } else {
                            transaction5.setAlpha(leash2, 0.0f);
                            if (MultiWindowUtils.isAppsEdgeActivity(change2.getTaskInfo().realActivity)) {
                                windowContainerToken4 = windowContainerToken7;
                                splitScreenTransitions5.startCustomFadeAnimation(leash2, true, false, true, false, false);
                                z4 = true;
                            } else {
                                windowContainerToken4 = windowContainerToken7;
                                z4 = true;
                                splitScreenTransitions5.buildSurfaceAnimation(change2, leash2, R.anim.split_open_with_allapps_enter);
                            }
                            splitScreenTransitions = splitScreenTransitions5;
                            iM2--;
                            transitionInfo2 = transitionInfo3;
                            transaction4 = transaction5;
                            splitScreenTransitions4 = splitScreenTransitions;
                            windowContainerToken11 = windowContainerToken5;
                            windowContainerToken3 = windowContainerToken4;
                            windowContainerToken2 = windowContainerToken6;
                        }
                    } else {
                        splitScreenTransitions2 = splitScreenTransitions5;
                        windowContainerToken4 = windowContainerToken7;
                        i4 = 1;
                    }
                    if (z && zIsOpeningType && ((mode == i4 || mode == 3) && !transitionInfo3.isSeparatedFromCustomDisplayChange())) {
                        transaction5.setAlpha(leash2, 0.0f);
                        splitScreenTransitions2.buildSurfaceAnimation(change2, leash2, R.anim.split_pair_enter);
                        splitScreenTransitions = splitScreenTransitions2;
                    } else {
                        if (z && transitionInfo3.getType() == 6 && z26 && mode == 3) {
                            splitScreenTransitions = splitScreenTransitions2;
                            splitScreenTransitions.startCustomFadeAnimation(leash2, true, true, false, false, isCellDivider);
                        } else {
                            TransitionInfo.Change change17 = change2;
                            splitScreenTransitions = splitScreenTransitions2;
                            if (!zIsOpeningType && (mode == 2 || mode == 4)) {
                                splitScreenTransitions.startFadeAnimation(leash2);
                            }
                            if (mode == 6 && change17.getSnapshot() != null) {
                                transaction5.reparent(change17.getSnapshot(), transitionInfo3.getRoot(iRootIndexFor).getLeash());
                                transaction5.setLayer(change17.getSnapshot(), transitionInfo3.getChanges().size() + 1);
                                transaction5.setPosition(change17.getSnapshot(), change17.getStartAbsBounds().left, change17.getStartAbsBounds().top);
                                transaction5.show(change17.getSnapshot());
                                splitScreenTransitions.startFadeAnimation(change17.getSnapshot());
                            }
                        }
                        iM2--;
                        transitionInfo2 = transitionInfo3;
                        transaction4 = transaction5;
                        splitScreenTransitions4 = splitScreenTransitions;
                        windowContainerToken11 = windowContainerToken5;
                        windowContainerToken3 = windowContainerToken4;
                        windowContainerToken2 = windowContainerToken6;
                    }
                    iM2--;
                    transitionInfo2 = transitionInfo3;
                    transaction4 = transaction5;
                    splitScreenTransitions4 = splitScreenTransitions;
                    windowContainerToken11 = windowContainerToken5;
                    windowContainerToken3 = windowContainerToken4;
                    windowContainerToken2 = windowContainerToken6;
                }
            }
        }
        SplitScreenTransitions splitScreenTransitions6 = splitScreenTransitions4;
        transaction.apply();
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            Log.d("SplitScreenTransitions", "startAllAnimators: num_anim=" + splitScreenTransitions6.mAnimations.size());
            ArrayList arrayList = splitScreenTransitions6.mAnimations;
            int size = arrayList.size();
            int i13 = 0;
            while (i13 < size) {
                Object obj = arrayList.get(i13);
                i13++;
                transitions.mAnimExecutor.execute(new SplitScreenTransitions$$ExternalSyntheticLambda2((Animator) obj, 1));
            }
        }
        splitScreenTransitions6.onFinish(null);
        return true;
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

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r5v42 android.app.PendingIntent, still in use, count: 2, list:
          (r5v42 android.app.PendingIntent) from 0x006e: IF  (r5v42 android.app.PendingIntent) == (null android.app.PendingIntent)  -> B:23:0x0070 A[HIDDEN] (LINE:111)
          (r5v42 android.app.PendingIntent) from 0x0079: PHI (r5v5 android.app.PendingIntent) = (r5v4 android.app.PendingIntent), (r5v42 android.app.PendingIntent) binds: [B:24:0x0077, B:22:0x006e] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:114)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:62)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:45)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:67)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:35)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:34)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    /* JADX WARN: Removed duplicated region for block: B:135:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x027f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startSplitScreen(int r22, android.app.PendingIntent r23, android.content.Intent r24, android.content.Intent r25, android.content.Intent r26, android.os.UserHandle r27, android.os.UserHandle r28, android.os.UserHandle r29, int r30, int r31, float r32, float r33, int r34, int r35, boolean r36, android.window.WindowContainerTransaction r37, android.window.RemoteTransition r38) {
        /*
            Method dump skipped, instructions count: 745
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.StageCoordinator.startSplitScreen(int, android.app.PendingIntent, android.content.Intent, android.content.Intent, android.content.Intent, android.os.UserHandle, android.os.UserHandle, android.os.UserHandle, int, int, float, float, int, int, boolean, android.window.WindowContainerTransaction, android.window.RemoteTransition):void");
    }

    public final void startSplitTasks(int i, int i2, int i3, boolean z, int i4, float f, float f2, boolean z2) {
        int i5;
        if (checkNonResizableTaskAndStartTask(i, i2, i3)) {
            return;
        }
        boolean z3 = CoreRune.MW_PARALLEL_MULTI_SPLIT;
        if (z3) {
            this.mSplitLayout.mParallelMultiSplit = z2;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        boolean z4 = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        if (z4 && isMultiSplitActive() && i3 == -1) {
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
            if (z4 && i3 != -1) {
                bundle3.putBoolean("android.activity.splitTaskDeferResume", true);
            }
        } else {
            stageTaskListener.activate(windowContainerTransaction, false);
        }
        addActivityOptions(bundle, stageTaskListener);
        addActivityOptions(bundle2, this.mSideStage);
        windowContainerTransaction.startTask(i, bundle);
        windowContainerTransaction.startTask(i2, bundle2);
        if (z4 && i3 != -1) {
            StageTaskListener stageTaskListener2 = this.mCellStage;
            stageTaskListener2.activate(null, false);
            addActivityOptions(bundle3, stageTaskListener2);
            windowContainerTransaction.startTask(i3, bundle3);
        }
        if (!z4 || i3 == -1) {
            i5 = i4;
            setSideStagePosition(1, z ? 1 : 0, windowContainerTransaction, false);
        } else {
            MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
            multiSplitLayoutInfo.sideStagePosition = 1;
            multiSplitLayoutInfo.splitDivision = z ? 1 : 0;
            i5 = i4;
            multiSplitLayoutInfo.cellStagePosition = i5;
            if (z3 && z2) {
                applyParallelMultiSplitLayoutInfo(windowContainerTransaction, multiSplitLayoutInfo);
                i5 = multiSplitLayoutInfo.cellStagePosition;
            }
            updateMultiSplitLayout(multiSplitLayoutInfo, false, windowContainerTransaction);
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            this.mSplitLayout.setDivideRatio(f, true, false);
        } else {
            this.mSplitLayout.setDivideRatio(f, false, false);
        }
        if (i3 != -1) {
            boolean z5 = (z && (i5 & 32) != 0) || !(z || (i5 & 64) == 0);
            if (z3 && z2) {
                z5 = (z && (i5 & 64) != 0) || !(z || (i5 & 32) == 0);
            }
            this.mSplitLayout.setCellDividerRatio(f2, i5, false, z5);
        }
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        updateStagePositionIfNeeded(windowContainerTransaction);
        setRootForceTranslucent(windowContainerTransaction, false);
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, (!z4 || i3 == -1) ? VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI : VolteConstants.ErrorCode.CALL_SESSION_TERMINATED, false, 1);
    }

    public final void startTask(int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken, int i3, int i4) {
        boolean zIsPackageActiveInPip;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -5985507751851902320L, 21, Long.valueOf(i), Long.valueOf(i2), Long.valueOf(i3));
        }
        this.mSplitRequest = new SplitRequest(this, i, i2);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        Bundle bundleResolveStartStage = CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? resolveStartStage(-1, i2, bundle, null, i4) : resolveStartStage(-1, i2, bundle, null, -1);
        if (windowContainerToken != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1580296436628468854L, 0, null);
            }
            windowContainerTransaction.reorder(windowContainerToken, false);
        }
        prepareTasksForSplitScreen(new int[]{i}, windowContainerTransaction);
        windowContainerTransaction.startTask(i, bundleResolveStartStage);
        DefaultMixedHandler defaultMixedHandler = this.mMixedHandler;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        if (defaultMixedHandler != null) {
            PipTransitionController pipTransitionController = defaultMixedHandler.mPipHandler;
            if (pipTransitionController != null) {
                int i5 = ComponentUtils.$r8$clinit;
                ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(i);
                zIsPackageActiveInPip = pipTransitionController.isPackageActiveInPip(runningTaskInfo == null ? null : ComponentUtils.getPackageName(((TaskInfo) runningTaskInfo).baseIntent));
            } else {
                zIsPackageActiveInPip = false;
            }
            if (zIsPackageActiveInPip) {
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
        ActivityOptions activityOptionsFromBundle = ActivityOptions.fromBundle(bundle);
        if (activityOptionsFromBundle != null) {
            activityOptionsFromBundle.setLaunchedFromDnD(true);
            bundle = activityOptionsFromBundle.toBundle();
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
            if (z3) {
                this.mSplitLayout.mParallelMultiSplit = z;
                if (z) {
                    MultiSplitLayoutInfo currentMultiSplitLayoutInfo = getCurrentMultiSplitLayoutInfo();
                    currentMultiSplitLayoutInfo.cellStagePosition = i4;
                    applyParallelMultiSplitLayoutInfo(windowContainerTransaction, currentMultiSplitLayoutInfo);
                    this.mSplitLayout.setDivideRatio(this.mSplitLayout.calculateSplitRatioForParallelMultiSplit(currentMultiSplitLayoutInfo), true, true);
                }
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

    public final void startTasks(int i, Bundle bundle, int i2, Bundle bundle2, int i3, Bundle bundle3, int i4, int i5, float f, int i6, float f2, RemoteTransition remoteTransition, InstanceId instanceId, int i7, boolean z, SplitScreenController.CallerInfo callerInfo) {
        int i8;
        boolean z2;
        int i9;
        int i10;
        int i11;
        int i12 = i;
        int i13 = i4;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            i8 = i5;
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -6325240002302691464L, 85, Long.valueOf(i12), Long.valueOf(i2), Long.valueOf(i13), Long.valueOf(i8));
        } else {
            i8 = i5;
        }
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
            z2 = z;
            this.mSplitLayout.mParallelMultiSplit = z2;
        } else {
            z2 = z;
        }
        if (i2 == -1) {
            startTaskWithAllApps(i12, callerInfo, i7);
            return;
        }
        if (checkNonResizableTaskAndStartTask(i12, i2, i3)) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (i2 == -1) {
            startSingleTask(i12, bundle, windowContainerTransaction, remoteTransition);
            return;
        }
        boolean z3 = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        if (z3 && isMultiSplitActive() && i3 == -1) {
            prepareExitMultiSplitScreen(windowContainerTransaction, false);
        }
        if (!this.mMainStage.mIsActive || (i11 = this.mSideStagePosition) == -1 || i11 == i13) {
            i9 = i2;
        } else {
            i9 = i12;
            i12 = i2;
            i13 = i11;
        }
        setSideStagePosition(i13, i7, windowContainerTransaction, true);
        if (!z3 || i3 == -1) {
            i10 = i6;
        } else {
            i10 = i6;
            setCellStageWindowConfigPosition(i10, false);
        }
        Bundle bundle4 = bundle != null ? bundle : new Bundle();
        addActivityOptions(bundle4, this.mSideStage);
        prepareTasksForSplitScreen(new int[]{i12, i9}, windowContainerTransaction);
        windowContainerTransaction.startTask(i12, bundle4);
        startWithTask(windowContainerTransaction, i9, bundle2, i8, f, i3, bundle3, f2, i10, i7, z2, remoteTransition, instanceId, CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY, false, bundle4);
        if (!CoreRune.MW_SPLIT_RECENT_TASKS_SA_LOGGING || remoteTransition == null) {
            return;
        }
        CoreSaLogger.logForAdvanced("1000", "From recent_task");
        if (!z3 || i3 == -1) {
            return;
        }
        CoreSaLogger.logForAdvanced("1021", "From recent_task");
    }

    public final void startWithTask(WindowContainerTransaction windowContainerTransaction, int i, Bundle bundle, int i2, float f, int i3, Bundle bundle2, float f2, int i4, int i5, boolean z, RemoteTransition remoteTransition, InstanceId instanceId, boolean z2, boolean z3, Bundle bundle3) {
        boolean z4;
        Bundle bundle4;
        StageTaskListener stageTaskListener = this.mMainStage;
        if (!stageTaskListener.mIsActive) {
            stageTaskListener.activate(windowContainerTransaction, false);
        }
        boolean z5 = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        boolean z6 = z5 && i3 != -1;
        StageTaskListener stageTaskListener2 = this.mCellStage;
        if (z6) {
            stageTaskListener2.activate(null, false);
        }
        boolean z7 = CoreRune.MW_PARALLEL_MULTI_SPLIT;
        if (z7) {
            this.mSplitLayout.mParallelMultiSplit = z;
        }
        this.mSplitLayout.setDivideRatio(f, z2, z3);
        if (z6) {
            boolean z8 = (i5 == 1 && (i4 & 32) != 0) || (i5 == 0 && (i4 & 64) != 0);
            if (z7 && z) {
                z8 = (i5 == 1 && (i4 & 64) != 0) || (i5 == 0 && (i4 & 32) != 0);
            }
            z4 = false;
            this.mSplitLayout.setCellDividerRatio(f2, i4, false, z8);
        } else {
            z4 = false;
        }
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, z4);
        updateStagePositionIfNeeded(windowContainerTransaction);
        windowContainerTransaction.setTransactionType(5);
        this.mLastTransactionType = 5;
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, z4);
        setRootForceTranslucent(windowContainerTransaction, z4);
        Bundle bundle5 = bundle != null ? bundle : new Bundle();
        addActivityOptions(bundle5, stageTaskListener);
        windowContainerTransaction.startTask(i, bundle5);
        if (z6) {
            bundle4 = bundle2 != null ? bundle2 : new Bundle();
            addActivityOptions(bundle4, stageTaskListener2);
            windowContainerTransaction.startTask(i3, bundle4);
        } else {
            bundle4 = bundle2;
        }
        if (remoteTransition != null && ((stageTaskListener.hasChild() || this.mSideStage.hasChild()) && bundle3 != null)) {
            bundle5.putBoolean("android.activity.splitTaskDeferResume", true);
            bundle3.putBoolean("android.activity.splitTaskDeferResume", true);
            if (z6) {
                bundle4.putBoolean("android.activity.splitTaskDeferResume", true);
            }
        }
        if (this.mPausingTasks.contains(Integer.valueOf(i))) {
            this.mPausingTasks.clear();
        }
        int i6 = VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
        int i7 = (!z5 || i3 == -1) ? 1004 : VolteConstants.ErrorCode.CALL_SESSION_TERMINATED;
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (z5) {
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
    /* JADX WARN: Type inference failed for: r9v3, types: [com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda31] */
    public final void switchSplitPosition(String str) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1399060535595712440L, 0, null);
        }
        SurfaceControl.Transaction transactionAcquire = this.mTransactionPool.acquire();
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
        this.mSplitLayout.playSwapAnimation(transactionAcquire, stageTaskListener3, stageTaskListener4, new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda31
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final StageCoordinator stageCoordinator = this.f$0;
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
                        StageCoordinator stageCoordinator2 = stageCoordinator;
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
        int iReverseSplitPosition = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
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
            splitscreenEventLogger.updateMainStageState(SplitscreenEventLogger.getMainStagePositionFromSplitPosition(iReverseSplitPosition, z), topChildTaskUid);
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
        SurfaceControl.Transaction transactionAcquire = transaction != null ? transaction : transactionPool.acquire();
        boolean zApplyCornerRadiusToLeashIfNeeded = this.mCellStage.applyCornerRadiusToLeashIfNeeded(roundedCornerRadius, transactionAcquire, z) | this.mMainStage.applyCornerRadiusToLeashIfNeeded(roundedCornerRadius, transactionAcquire, z) | this.mSideStage.applyCornerRadiusToLeashIfNeeded(roundedCornerRadius, transactionAcquire, z);
        if (transaction == null) {
            if (zApplyCornerRadiusToLeashIfNeeded) {
                transactionAcquire.apply();
            }
            transactionPool.release(transactionAcquire);
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
        SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
        transactionAcquire.setAlpha(dividerLeash, f).apply();
        transactionPool.release(transactionAcquire);
        Slog.d("StageCoordinator", "updateDividerLeashVisible: " + dividerLeash + ", show=" + z + "");
    }

    public final void updateMultiSplitLayout(MultiSplitLayoutInfo multiSplitLayoutInfo, boolean z, WindowContainerTransaction windowContainerTransaction) {
        setSideStagePosition(multiSplitLayoutInfo.sideStagePosition, multiSplitLayoutInfo.splitDivision, windowContainerTransaction, false);
        setCellStageWindowConfigPosition(multiSplitLayoutInfo.cellStagePosition, true);
        this.mSplitLayout.updateCellStageWindowConfigPosition(this.mCellStageWindowConfigPosition);
        this.mSplitLayout.update(null, true);
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
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Update split division for SubDisplay. d=", "  Call=");
            sbM.append(Debug.getCallers(5));
            Slog.i("StageCoordinator", sbM.toString());
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

    /* JADX WARN: Removed duplicated region for block: B:105:0x0451  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0453  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0460  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x04d5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x037f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSurfaceBounds(SplitLayout splitLayout, SurfaceControl.Transaction transaction, boolean z) {
        Rect topLeftRefBounds;
        Rect refHostBounds;
        SurfaceControl cellDividerLeash;
        int i = this.mSideStagePosition;
        StageTaskListener stageTaskListener = this.mMainStage;
        StageTaskListener stageTaskListener2 = this.mSideStage;
        StageTaskListener stageTaskListener3 = i == 0 ? stageTaskListener2 : stageTaskListener;
        if (i != 0) {
            stageTaskListener = stageTaskListener2;
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            StageTaskListener stageTaskListener4 = this.mCellStage;
            if (stageTaskListener4.mIsActive) {
                SplitLayout splitLayout2 = splitLayout != null ? splitLayout : this.mSplitLayout;
                SurfaceControl surfaceControl = stageTaskListener3.mRootLeash;
                SurfaceControl surfaceControl2 = stageTaskListener.mRootLeash;
                SurfaceControl surfaceControl3 = stageTaskListener3.mDimLayer;
                SurfaceControl surfaceControl4 = stageTaskListener.mDimLayer;
                SurfaceControl surfaceControl5 = stageTaskListener4.mRootLeash;
                SurfaceControl dividerLeash = splitLayout2.getDividerLeash();
                if (dividerLeash != null) {
                    Rect rect = splitLayout2.mTempRect;
                    Rect rect2 = new Rect(splitLayout2.mDividerBounds);
                    Rect rect3 = splitLayout2.mRootBounds;
                    rect2.offset(-rect3.left, -rect3.top);
                    rect.set(rect2);
                    Rect rect4 = splitLayout2.mTempRect;
                    transaction.setPosition(dividerLeash, rect4.left, rect4.top);
                    transaction.setLayer(dividerLeash, Integer.MAX_VALUE);
                }
                if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && splitLayout2.mStageCoordinator.isMultiSplitActive() && (cellDividerLeash = splitLayout2.getCellDividerLeash()) != null) {
                    Rect rect5 = splitLayout2.mTempRect;
                    Rect rect6 = new Rect(splitLayout2.mCellDividerBounds);
                    Rect rect7 = splitLayout2.mRootBounds;
                    rect6.offset(-rect7.left, -rect7.top);
                    rect5.set(rect6);
                    Rect rect8 = splitLayout2.mTempRect;
                    transaction.setPosition(cellDividerLeash, rect8.left, rect8.top);
                    transaction.setLayer(cellDividerLeash, Integer.MAX_VALUE);
                }
                if (CellUtil.isCellInLeftOrTopBounds(splitLayout2.mCellStageWindowConfigPosition, splitLayout2.isVerticalDivision())) {
                    topLeftRefBounds = splitLayout2.getRefHostBounds();
                    refHostBounds = splitLayout2.getBottomRightRefBounds();
                } else {
                    topLeftRefBounds = splitLayout2.getTopLeftRefBounds();
                    refHostBounds = splitLayout2.getRefHostBounds();
                }
                Rect bounds3 = splitLayout2.getBounds3();
                Rect rect9 = splitLayout2.mRootBounds;
                bounds3.offset(-rect9.left, -rect9.top);
                transaction.setPosition(surfaceControl, topLeftRefBounds.left, topLeftRefBounds.top).setWindowCrop(surfaceControl, topLeftRefBounds.width(), topLeftRefBounds.height());
                transaction.setPosition(surfaceControl2, refHostBounds.left, refHostBounds.top).setWindowCrop(surfaceControl2, refHostBounds.width(), refHostBounds.height());
                transaction.setPosition(surfaceControl5, bounds3.left, bounds3.top).setWindowCrop(surfaceControl5, bounds3.width(), bounds3.height());
                if (surfaceControl3 != null && surfaceControl3.isValid()) {
                    transaction.setAlpha(surfaceControl3, 0.0f).setVisibility(surfaceControl3, false);
                }
                if (surfaceControl4 != null && surfaceControl4.isValid()) {
                    transaction.setAlpha(surfaceControl4, 0.0f).setVisibility(surfaceControl4, false);
                }
                if (CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME && splitLayout2.mStageCoordinator.isMultiSplitActive()) {
                    SurfaceControl cellDividerLeash2 = splitLayout2.getCellDividerLeash();
                    SplitLayout.ImePositionProcessor imePositionProcessor = splitLayout2.mImePositionProcessor;
                    if (imePositionProcessor.mYOffsetForIme != 0) {
                        SplitLayout splitLayout3 = SplitLayout.this;
                        if (dividerLeash != null) {
                            splitLayout3.mTempRect.set(splitLayout3.mDividerBounds);
                            splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                            Rect rect10 = splitLayout3.mTempRect;
                            transaction.setPosition(dividerLeash, rect10.left, rect10.top);
                        }
                        if (cellDividerLeash2 != null) {
                            splitLayout3.mTempRect.set(splitLayout3.mCellDividerBounds);
                            splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                            Rect rect11 = splitLayout3.mTempRect;
                            transaction.setPosition(cellDividerLeash2, rect11.left, rect11.top);
                        }
                        if (splitLayout3.isVerticalDivision()) {
                            int i2 = splitLayout3.mCellStageWindowConfigPosition;
                            if ((i2 & 8) != 0) {
                                if ((i2 & 64) != 0) {
                                    transaction.setWindowCrop(surfaceControl, splitLayout3.mHostBounds.width(), splitLayout3.mHostBounds.height() + imePositionProcessor.mYOffsetForIme);
                                    splitLayout3.mTempRect.set(splitLayout3.mBounds3);
                                    splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                    Rect rect12 = splitLayout3.mTempRect;
                                    transaction.setPosition(surfaceControl5, rect12.left, rect12.top);
                                } else {
                                    transaction.setWindowCrop(surfaceControl5, splitLayout3.mBounds3.width(), splitLayout3.mBounds3.height() + imePositionProcessor.mYOffsetForIme);
                                    splitLayout3.mTempRect.set(splitLayout3.mHostBounds);
                                    splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                    Rect rect13 = splitLayout3.mTempRect;
                                    transaction.setPosition(surfaceControl, rect13.left, rect13.top);
                                }
                            } else if ((i2 & 32) != 0) {
                                if ((i2 & 64) != 0) {
                                    transaction.setWindowCrop(surfaceControl2, splitLayout3.mHostBounds.width(), splitLayout3.mHostBounds.height() + imePositionProcessor.mYOffsetForIme);
                                    splitLayout3.mTempRect.set(splitLayout3.mBounds3);
                                    splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                    Rect rect14 = splitLayout3.mTempRect;
                                    transaction.setPosition(surfaceControl5, rect14.left, rect14.top);
                                } else {
                                    transaction.setWindowCrop(surfaceControl5, splitLayout3.mBounds3.width(), splitLayout3.mBounds3.height() + imePositionProcessor.mYOffsetForIme);
                                    splitLayout3.mTempRect.set(splitLayout3.mHostBounds);
                                    splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                    Rect rect15 = splitLayout3.mTempRect;
                                    transaction.setPosition(surfaceControl2, rect15.left, rect15.top);
                                }
                            }
                        } else if (CoreRune.MW_PARALLEL_MULTI_SPLIT && splitLayout3.mParallelMultiSplit) {
                            int i3 = splitLayout3.mCellStageWindowConfigPosition;
                            if ((i3 & 16) != 0) {
                                transaction.setWindowCrop(surfaceControl5, splitLayout3.mBounds3.width(), splitLayout3.mBounds3.height() + imePositionProcessor.mYOffsetForIme);
                                splitLayout3.mTempRect.set(splitLayout3.mHostBounds);
                                splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                Rect rect16 = splitLayout3.mTempRect;
                                transaction.setPosition(surfaceControl, rect16.left, rect16.top);
                                splitLayout3.mTempRect.set(splitLayout3.getBottomRightBounds());
                                splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                Rect rect17 = splitLayout3.mTempRect;
                                transaction.setPosition(surfaceControl2, rect17.left, rect17.top);
                            } else if ((i3 & 64) != 0) {
                                transaction.setWindowCrop(surfaceControl, splitLayout3.getTopLeftBounds().width(), splitLayout3.getTopLeftBounds().height() + imePositionProcessor.mYOffsetForIme);
                                splitLayout3.mTempRect.set(splitLayout3.mHostBounds);
                                splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                Rect rect18 = splitLayout3.mTempRect;
                                transaction.setPosition(surfaceControl2, rect18.left, rect18.top);
                                splitLayout3.mTempRect.set(splitLayout3.mBounds3);
                                splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                Rect rect19 = splitLayout3.mTempRect;
                                transaction.setPosition(surfaceControl5, rect19.left, rect19.top);
                            }
                        } else {
                            int i4 = splitLayout3.mCellStageWindowConfigPosition;
                            if ((i4 & 16) != 0) {
                                transaction.setWindowCrop(surfaceControl, splitLayout3.mHostBounds.width(), splitLayout3.mHostBounds.height() + imePositionProcessor.mYOffsetForIme);
                                transaction.setWindowCrop(surfaceControl5, splitLayout3.mBounds3.width(), splitLayout3.mBounds3.height() + imePositionProcessor.mYOffsetForIme);
                                splitLayout3.mTempRect.set(splitLayout3.getBottomRightBounds());
                                splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                Rect rect20 = splitLayout3.mTempRect;
                                transaction.setPosition(surfaceControl2, rect20.left, rect20.top);
                            } else if ((i4 & 64) != 0) {
                                transaction.setWindowCrop(surfaceControl, splitLayout3.getTopLeftBounds().width(), splitLayout3.getTopLeftBounds().height() + imePositionProcessor.mYOffsetForIme);
                                splitLayout3.mTempRect.set(splitLayout3.mHostBounds);
                                splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                Rect rect21 = splitLayout3.mTempRect;
                                transaction.setPosition(surfaceControl2, rect21.left, rect21.top);
                                splitLayout3.mTempRect.set(splitLayout3.mBounds3);
                                splitLayout3.mTempRect.offset(0, imePositionProcessor.mYOffsetForIme);
                                Rect rect22 = splitLayout3.mTempRect;
                                transaction.setPosition(surfaceControl5, rect22.left, rect22.top);
                            }
                        }
                    }
                } else {
                    splitLayout2.mImePositionProcessor.adjustSurfaceLayoutForIme(transaction, dividerLeash, surfaceControl, surfaceControl2, surfaceControl3, surfaceControl4);
                }
            } else {
                SplitLayout splitLayout4 = splitLayout != null ? splitLayout : this.mSplitLayout;
                SurfaceControl surfaceControl6 = stageTaskListener3.mRootLeash;
                SurfaceControl surfaceControl7 = stageTaskListener.mRootLeash;
                SurfaceControl surfaceControl8 = stageTaskListener3.mDimLayer;
                SurfaceControl surfaceControl9 = stageTaskListener.mDimLayer;
                SurfaceControl dividerLeash2 = splitLayout4.getDividerLeash();
                if (dividerLeash2 != null) {
                    Rect rect23 = splitLayout4.mTempRect;
                    rect23.set(splitLayout4.mDividerBounds);
                    Rect rect24 = splitLayout4.mRootBounds;
                    rect23.offset(-rect24.left, -rect24.top);
                    Rect rect25 = splitLayout4.mTempRect;
                    transaction.setPosition(dividerLeash2, rect25.left, rect25.top);
                    transaction.setLayer(dividerLeash2, Integer.MAX_VALUE);
                }
                if (surfaceControl8 != null) {
                    transaction.setLayer(surfaceControl8, 2147483646);
                }
                if (surfaceControl9 != null) {
                    transaction.setLayer(surfaceControl9, 2147483646);
                }
                splitLayout4.copyTopLeftRefBounds(splitLayout4.mTempRect);
                Rect rect26 = splitLayout4.mTempRect;
                transaction.setPosition(surfaceControl6, rect26.left, rect26.top).setWindowCrop(surfaceControl6, splitLayout4.mTempRect.width(), splitLayout4.mTempRect.height());
                Rect rect27 = splitLayout4.mTempRect;
                rect27.set(splitLayout4.getBottomRightBounds());
                Rect rect28 = splitLayout4.mRootBounds;
                rect27.offset(-rect28.left, -rect28.top);
                Rect rect29 = splitLayout4.mTempRect;
                transaction.setPosition(surfaceControl7, rect29.left, rect29.top).setWindowCrop(surfaceControl7, splitLayout4.mTempRect.width(), splitLayout4.mTempRect.height());
                SurfaceControl surfaceControl10 = surfaceControl7;
                SurfaceControl surfaceControl11 = surfaceControl6;
                if (!splitLayout4.mImePositionProcessor.adjustSurfaceLayoutForIme(transaction, dividerLeash2, surfaceControl11, surfaceControl10, surfaceControl8, surfaceControl9)) {
                    ResizingEffectPolicy resizingEffectPolicy = splitLayout4.mSurfaceEffectPolicy;
                    int i5 = resizingEffectPolicy.mDimmingSide;
                    if (i5 == 1 || i5 == 2) {
                        transaction.setAlpha(surfaceControl8, resizingEffectPolicy.mDimValue).setVisibility(surfaceControl8, resizingEffectPolicy.mDimValue <= 0.001f);
                        transaction.setAlpha(surfaceControl9, 0.0f).setVisibility(surfaceControl9, false);
                        if (z) {
                            SplitLayout splitLayout5 = resizingEffectPolicy.mSplitLayout;
                            int i6 = resizingEffectPolicy.mParallaxType;
                            if (i6 == 1) {
                                int i7 = resizingEffectPolicy.mDimmingSide;
                                if (i7 == 1 || i7 == 2) {
                                    resizingEffectPolicy.mTempRect.set(splitLayout5.getTopLeftBounds());
                                    resizingEffectPolicy.mTempRect2.set(splitLayout5.getBottomRightBounds());
                                    if (i6 != 0 && surfaceControl11 != null && surfaceControl10 != null) {
                                        int i8 = resizingEffectPolicy.mTempRect.left;
                                        Point point = resizingEffectPolicy.mRetreatingSideParallax;
                                        transaction.setPosition(surfaceControl11, i8 + point.x, r2.top + point.y);
                                        Rect rect30 = resizingEffectPolicy.mTempRect;
                                        Point point2 = resizingEffectPolicy.mRetreatingSideParallax;
                                        rect30.offsetTo(-point2.x, -point2.y);
                                        transaction.setWindowCrop(surfaceControl11, resizingEffectPolicy.mTempRect);
                                        int i9 = resizingEffectPolicy.mTempRect2.left;
                                        Point point3 = resizingEffectPolicy.mAdvancingSideParallax;
                                        transaction.setPosition(surfaceControl10, i9 + point3.x, r2.top + point3.y);
                                        Rect rect31 = resizingEffectPolicy.mTempRect2;
                                        Point point4 = resizingEffectPolicy.mAdvancingSideParallax;
                                        rect31.offsetTo(-point4.x, -point4.y);
                                        transaction.setWindowCrop(surfaceControl10, resizingEffectPolicy.mTempRect2);
                                    }
                                } else {
                                    if (i7 == 3 || i7 == 4) {
                                        resizingEffectPolicy.mTempRect.set(splitLayout5.getBottomRightBounds());
                                        resizingEffectPolicy.mTempRect2.set(splitLayout5.getTopLeftBounds());
                                        surfaceControl10 = surfaceControl11;
                                        surfaceControl11 = surfaceControl10;
                                        if (i6 != 0) {
                                            int i82 = resizingEffectPolicy.mTempRect.left;
                                            Point point5 = resizingEffectPolicy.mRetreatingSideParallax;
                                            transaction.setPosition(surfaceControl11, i82 + point5.x, r2.top + point5.y);
                                            Rect rect302 = resizingEffectPolicy.mTempRect;
                                            Point point22 = resizingEffectPolicy.mRetreatingSideParallax;
                                            rect302.offsetTo(-point22.x, -point22.y);
                                            transaction.setWindowCrop(surfaceControl11, resizingEffectPolicy.mTempRect);
                                            int i92 = resizingEffectPolicy.mTempRect2.left;
                                            Point point32 = resizingEffectPolicy.mAdvancingSideParallax;
                                            transaction.setPosition(surfaceControl10, i92 + point32.x, r2.top + point32.y);
                                            Rect rect312 = resizingEffectPolicy.mTempRect2;
                                            Point point42 = resizingEffectPolicy.mAdvancingSideParallax;
                                            rect312.offsetTo(-point42.x, -point42.y);
                                            transaction.setWindowCrop(surfaceControl10, resizingEffectPolicy.mTempRect2);
                                        }
                                    }
                                    surfaceControl11 = null;
                                    surfaceControl10 = null;
                                    if (i6 != 0) {
                                    }
                                }
                            } else if (i6 == 2 || i6 == 3) {
                                int i10 = resizingEffectPolicy.mShrinkSide;
                                if (i10 == 1 || i10 == 2) {
                                    resizingEffectPolicy.mTempRect.set(splitLayout5.getTopLeftBounds());
                                    resizingEffectPolicy.mTempRect2.set(splitLayout5.getBottomRightBounds());
                                    if (i6 != 0) {
                                    }
                                } else {
                                    if (i10 == 3 || i10 == 4) {
                                        resizingEffectPolicy.mTempRect.set(splitLayout5.getBottomRightBounds());
                                        resizingEffectPolicy.mTempRect2.set(splitLayout5.getTopLeftBounds());
                                        surfaceControl10 = surfaceControl11;
                                        surfaceControl11 = surfaceControl10;
                                        if (i6 != 0) {
                                        }
                                    }
                                    surfaceControl11 = null;
                                    surfaceControl10 = null;
                                    if (i6 != 0) {
                                    }
                                }
                            } else {
                                surfaceControl11 = null;
                                surfaceControl10 = null;
                                if (i6 != 0) {
                                }
                            }
                        }
                    } else if (i5 == 3 || i5 == 4) {
                        surfaceControl9 = surfaceControl8;
                        surfaceControl8 = surfaceControl9;
                        transaction.setAlpha(surfaceControl8, resizingEffectPolicy.mDimValue).setVisibility(surfaceControl8, resizingEffectPolicy.mDimValue <= 0.001f);
                        transaction.setAlpha(surfaceControl9, 0.0f).setVisibility(surfaceControl9, false);
                        if (z) {
                        }
                    } else {
                        transaction.setAlpha(surfaceControl8, 0.0f).hide(surfaceControl8);
                        transaction.setAlpha(surfaceControl9, 0.0f).hide(surfaceControl9);
                        if (z) {
                        }
                    }
                }
            }
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -8927322811896087248L, 0, String.valueOf(splitLayout.getTopLeftBounds()), String.valueOf(splitLayout.getBottomRightBounds()));
        }
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
        boolean zApplyTaskChanges = splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener3.mRootTaskInfo, stageTaskListener.mRootTaskInfo);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -2337050910502079842L, 0, String.valueOf(splitLayout.getTopLeftBounds()), String.valueOf(splitLayout.getBottomRightBounds()));
        }
        return zApplyTaskChanges;
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
        this.mDeviceState = -1;
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
