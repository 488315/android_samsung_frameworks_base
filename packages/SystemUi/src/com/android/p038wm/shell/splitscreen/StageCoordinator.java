package com.android.p038wm.shell.splitscreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ShortcutInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.view.Choreographer;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.InsetsState;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Toast;
import android.window.DisplayAreaInfo;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.policy.AttributeCache;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.IconProvider;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DisplayChangeController;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.DisplayImeController;
import com.android.p038wm.shell.common.DisplayInsetsController;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ScreenshotUtils;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.common.TransactionPool;
import com.android.p038wm.shell.common.split.CellUtil;
import com.android.p038wm.shell.common.split.DividerResizeController;
import com.android.p038wm.shell.common.split.DividerView;
import com.android.p038wm.shell.common.split.MultiSplitLayoutInfo;
import com.android.p038wm.shell.common.split.SplitDecorManager;
import com.android.p038wm.shell.common.split.SplitLayout;
import com.android.p038wm.shell.common.split.SplitScreenConstants;
import com.android.p038wm.shell.common.split.SplitScreenUtils;
import com.android.p038wm.shell.common.split.SplitWindowManager;
import com.android.p038wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.p038wm.shell.pip.PipTaskOrganizer;
import com.android.p038wm.shell.pip.PipTransitionController;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.recents.RecentTasksController;
import com.android.p038wm.shell.splitscreen.SplitBackgroundController;
import com.android.p038wm.shell.splitscreen.SplitScreen;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.splitscreen.SplitScreenTransitions;
import com.android.p038wm.shell.splitscreen.StageTaskListener;
import com.android.p038wm.shell.transition.DefaultMixedHandler;
import com.android.p038wm.shell.transition.LegacyTransitions$ILegacyTransition;
import com.android.p038wm.shell.transition.OneShotRemoteHandler;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.util.SplitBounds;
import com.android.p038wm.shell.util.StageUtils;
import com.android.p038wm.shell.util.TransitionUtil;
import com.android.server.LocalServices;
import com.android.systemui.R;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.core.RunestoneLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StageCoordinator implements SplitLayout.SplitLayoutHandler, DisplayController.OnDisplaysChangedListener, Transitions.TransitionHandler, ShellTaskOrganizer.TaskListener {
    public boolean mAppPairStarted;
    public ValueAnimator mCellDividerFadeInAnimator;
    public boolean mCellDividerVisible;
    public final CellStage mCellStage;
    public final StageListenerImpl mCellStageListener;
    public int mCellStageWindowConfigPosition;
    public final Context mContext;
    public final ArrayList mCurrentPackageNameList;
    public final StageCoordinator$$ExternalSyntheticLambda3 mDelayedHandleLayoutSizeChange;
    public final DisplayController mDisplayController;
    public final int mDisplayId;
    public final DisplayImeController mDisplayImeController;
    public final DisplayInsetsController mDisplayInsetsController;
    public ValueAnimator mDividerFadeInAnimator;
    public boolean mDividerLeashHidden;
    public DividerResizeController mDividerResizeController;
    public boolean mDividerVisible;
    public final List mExcludeLoggingPackages;
    public boolean mExitSplitScreenOnHide;
    public boolean mIsDividerRemoteAnimating;
    public boolean mIsDropEntering;
    public boolean mIsExiting;
    public boolean mIsFlexPanelMode;
    public boolean mIsFolded;
    public boolean mIsOpeningHomeDuringSplit;
    public boolean mIsRecentsInSplitAnimating;
    public boolean mIsRootTranslucent;
    public boolean mIsVideoControls;
    public boolean mKeyguardShowing;
    public final Configuration mLastConfiguration;
    public int mLastMainSplitDivision;
    public final ArrayList mLastPackageNameList;
    public int mLastReportedCellStageWinConfigPosition;
    public int mLastReportedMainStageWinConfigPosition;
    public int mLastReportedSideStageWinConfigPosition;
    public SplitBounds mLastSplitStateInfo;
    public int mLastTransactionType;
    public final List mListeners;
    public final SplitscreenEventLogger mLogger;
    public final ShellExecutor mMainExecutor;
    public final MainStage mMainStage;
    public final StageListenerImpl mMainStageListener;
    public DefaultMixedHandler mMixedHandler;
    public WindowContainerToken mMovingToFreeformTaskToken;
    public int mOrientation;
    public final C41151 mParentContainerCallbacks;
    public final ArrayList mPausingTasks;
    public final Optional mRecentTasks;
    ActivityManager.RunningTaskInfo mRootTaskInfo;
    public SurfaceControl mRootTaskLeash;
    public long mSeqForAsyncTransaction;
    public final StageCoordinator$$ExternalSyntheticLambda5 mSharedPrefListener;
    public boolean mShouldUpdateRecents;
    public boolean mShowDecorImmediately;
    public final SideStage mSideStage;
    public final StageListenerImpl mSideStageListener;
    public int mSideStagePosition;
    public boolean mSkipFlexPanelUpdate;
    public final SplitBackgroundController mSplitBackgroundController;
    public int mSplitDivision;
    public SplitLayout mSplitLayout;
    public boolean mSplitLayoutChangedForLaunchAdjacent;
    public SplitRequest mSplitRequest;
    public final SplitScreenTransitions mSplitTransitions;
    public final Toast mSplitUnsupportedToast;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Rect mTempRect;
    public final Rect mTempRect1;
    public final Rect mTempRect2;
    public final Rect mTempRect3;
    public Configuration mTmpConfigAfterFoldDismiss;
    public int mTopStageAfterFoldDismiss;
    public final TransactionPool mTransactionPool;
    public boolean mWillBeVideoControls;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$1 */
    public final class C41151 implements SplitWindowManager.ParentContainerCallbacks {
        public C41151() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$2 */
    public final class C41162 extends IRemoteAnimationRunner.Stub {
        public final /* synthetic */ boolean val$isEnteringSplit;
        public final /* synthetic */ int val$position;

        public C41162(boolean z, int i) {
            this.val$isEnteringSplit = z;
            this.val$position = i;
        }

        public final void onAnimationCancelled() {
            if (this.val$isEnteringSplit) {
                ((HandlerExecutor) StageCoordinator.this.mMainExecutor).execute(new StageCoordinator$2$$ExternalSyntheticLambda0(this, 1));
            }
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (this.val$isEnteringSplit && StageCoordinator.this.mSideStage.getChildCount() == 0) {
                ((HandlerExecutor) StageCoordinator.this.mMainExecutor).execute(new StageCoordinator$2$$ExternalSyntheticLambda0(this, 0));
                StageCoordinator.this.mSplitUnsupportedToast.show();
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
            StageCoordinator stageCoordinator = StageCoordinator.this;
            if (this.val$position == stageCoordinator.mSideStagePosition) {
                stageCoordinator.mSideStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
            } else {
                stageCoordinator.mMainStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
            }
            StageCoordinator.this.mSyncQueue.queue(windowContainerTransaction);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.splitscreen.StageCoordinator$3 */
    public final class C41173 implements LegacyTransitions$ILegacyTransition {
        public final /* synthetic */ boolean val$isEnteringSplit;
        public final /* synthetic */ int val$position;

        public C41173(boolean z, int i) {
            this.val$isEnteringSplit = z;
            this.val$position = i;
        }

        @Override // com.android.p038wm.shell.transition.LegacyTransitions$ILegacyTransition
        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, SurfaceControl.Transaction transaction) {
            StageCoordinator stageCoordinator = StageCoordinator.this;
            boolean z = this.val$isEnteringSplit;
            if (z && stageCoordinator.mSideStage.getChildCount() == 0) {
                ((HandlerExecutor) stageCoordinator.mMainExecutor).execute(new StageCoordinator$$ExternalSyntheticLambda6(this, 1));
                stageCoordinator.mSplitUnsupportedToast.show();
            }
            if (remoteAnimationTargetArr != null) {
                for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                    if (remoteAnimationTarget.mode == 0) {
                        transaction.show(remoteAnimationTarget.leash);
                    }
                }
            }
            transaction.apply();
            if (iRemoteAnimationFinishedCallback != null) {
                try {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                } catch (RemoteException e) {
                    Slog.e("StageCoordinator", "Error finishing legacy transition: ", e);
                }
            }
            if (z || remoteAnimationTargetArr == null) {
                return;
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (this.val$position == stageCoordinator.mSideStagePosition) {
                stageCoordinator.mSideStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
            } else {
                stageCoordinator.mMainStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
            }
            stageCoordinator.mSyncQueue.queue(windowContainerTransaction);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecentsTransitionCallback {
        public /* synthetic */ RecentsTransitionCallback(StageCoordinator stageCoordinator, int i) {
            this();
        }

        private RecentsTransitionCallback() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StageChangeRecord {
        public boolean mContainShowFullscreenChange = false;
        public final ArrayMap mChanges = new ArrayMap();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class StageChange {
            public final IntArray mAddedTaskId = new IntArray();
            public final IntArray mRemovedTaskId = new IntArray();
            public final StageTaskListener mStageTaskListener;

            public StageChange(StageTaskListener stageTaskListener) {
                this.mStageTaskListener = stageTaskListener;
            }

            public final boolean shouldDismissStage() {
                StageTaskListener stageTaskListener;
                if (this.mAddedTaskId.size() > 0) {
                    return false;
                }
                IntArray intArray = this.mRemovedTaskId;
                if (intArray.size() == 0) {
                    return false;
                }
                int size = intArray.size() - 1;
                int i = 0;
                while (true) {
                    stageTaskListener = this.mStageTaskListener;
                    if (size < 0) {
                        break;
                    }
                    if (stageTaskListener.containsTask(intArray.get(size))) {
                        i++;
                    }
                    size--;
                }
                return i == stageTaskListener.getChildCount();
            }
        }

        public final void addRecord(StageTaskListener stageTaskListener, boolean z, int i) {
            StageChange stageChange;
            ArrayMap arrayMap = this.mChanges;
            if (arrayMap.containsKey(stageTaskListener)) {
                stageChange = (StageChange) arrayMap.get(stageTaskListener);
            } else {
                stageChange = new StageChange(stageTaskListener);
                arrayMap.put(stageTaskListener, stageChange);
            }
            if (z) {
                stageChange.mAddedTaskId.add(i);
            } else {
                stageChange.mRemovedTaskId.add(i);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StageListenerImpl implements StageTaskListener.StageListenerCallbacks {
        public boolean mHasRootTask = false;
        public boolean mVisible = false;
        public boolean mHasChildren = false;

        public StageListenerImpl() {
        }

        public final void dump(PrintWriter printWriter, String str) {
            StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, "mHasRootTask=");
            m2m.append(this.mHasRootTask);
            printWriter.println(m2m.toString());
            printWriter.println(str + "mVisible=" + this.mVisible);
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("mHasChildren=");
            KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(sb, this.mHasChildren, printWriter);
        }

        /* JADX WARN: Code restructure failed: missing block: B:48:0x001c, code lost:
        
            if (r19 == r6) goto L12;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onChildTaskStatusChanged(int i, boolean z, boolean z2) {
            int i2;
            StageCoordinator stageCoordinator = StageCoordinator.this;
            boolean z3 = false;
            if (z) {
                stageCoordinator.getClass();
                boolean z4 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
                StageListenerImpl stageListenerImpl = stageCoordinator.mSideStageListener;
                if (z4) {
                    if (this != stageListenerImpl) {
                        if (this == stageCoordinator.mCellStageListener) {
                            i2 = 2;
                        }
                        i2 = 0;
                    }
                    i2 = 1;
                }
            } else {
                i2 = -1;
            }
            SplitscreenEventLogger splitscreenEventLogger = stageCoordinator.mLogger;
            SideStage sideStage = stageCoordinator.mSideStage;
            if (i2 == 0) {
                int mainStagePosition = stageCoordinator.getMainStagePosition();
                int topChildTaskUid = stageCoordinator.mMainStage.getTopChildTaskUid();
                boolean isLandscape = stageCoordinator.mSplitLayout.isLandscape();
                if (splitscreenEventLogger.mLoggerSessionId != null && splitscreenEventLogger.updateMainStageState(SplitscreenEventLogger.getMainStagePositionFromSplitPosition(mainStagePosition, isLandscape), topChildTaskUid)) {
                    FrameworkStatsLog.write(388, 3, 0, 0, 0.0f, splitscreenEventLogger.mLastMainStagePosition, splitscreenEventLogger.mLastMainStageUid, 0, 0, 0, splitscreenEventLogger.mLoggerSessionId.getId());
                }
            } else {
                int i3 = stageCoordinator.mSideStagePosition;
                int topChildTaskUid2 = sideStage.getTopChildTaskUid();
                boolean isLandscape2 = stageCoordinator.mSplitLayout.isLandscape();
                if (splitscreenEventLogger.mLoggerSessionId != null && splitscreenEventLogger.updateSideStageState(SplitscreenEventLogger.getSideStagePositionFromSplitPosition(i3, isLandscape2), topChildTaskUid2)) {
                    FrameworkStatsLog.write(388, 3, 0, 0, 0.0f, 0, 0, splitscreenEventLogger.mLastSideStagePosition, splitscreenEventLogger.mLastSideStageUid, 0, splitscreenEventLogger.mLoggerSessionId.getId());
                }
            }
            if (z && z2) {
                stageCoordinator.updateRecentTasksSplitPair();
            }
            if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && i2 == 1 && z2 && stageCoordinator.isSplitScreenVisible() && !stageCoordinator.isMultiSplitActive()) {
                if (sideStage.getTopRunningTaskInfo() != null && StageCoordinator.isVideoControlsTaskInfo(sideStage.getTopRunningTaskInfo())) {
                    z3 = true;
                }
                stageCoordinator.mWillBeVideoControls = z3;
                if (z3 != stageCoordinator.mIsVideoControls) {
                    stageCoordinator.updateVideoControlsState(z3, null, true);
                }
            }
            ArrayList arrayList = (ArrayList) stageCoordinator.mListeners;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((SplitScreen.SplitScreenListener) arrayList.get(size)).onTaskStageChanged(i, i2, z2);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onNoLongerSupportMultiWindow() {
            StageCoordinator stageCoordinator = StageCoordinator.this;
            MainStage mainStage = stageCoordinator.mMainStage;
            if (mainStage.mIsActive) {
                int i = stageCoordinator.mMainStageListener == this ? 1 : 0;
                StageTaskListener stageTaskListener = mainStage;
                if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                    if (i == 0) {
                        stageTaskListener = stageCoordinator.mSideStage;
                    }
                    stageCoordinator.exitSplitScreen(stageTaskListener, 1);
                    stageCoordinator.mSplitUnsupportedToast.show();
                    return;
                }
                int i2 = ~i ? 1 : 0;
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                    int i3 = i;
                    if (!MultiWindowCoreState.MW_ENABLED) {
                        i3 = stageCoordinator.mMainStage.isFocused();
                    }
                    stageCoordinator.prepareSplitDismissChangeTransition(windowContainerTransaction, i3, null, false);
                }
                stageCoordinator.prepareExitSplitScreen(i2, windowContainerTransaction, true);
                stageCoordinator.mSplitTransitions.startDismissTransition(windowContainerTransaction, stageCoordinator, i2, 1);
            }
        }

        public final void postDividerPanelAutoOpenIfNeeded() {
            SplitLayout splitLayout = StageCoordinator.this.mSplitLayout;
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
    }

    /* renamed from: -$$Nest$monRemoteAnimationFinishedOrCancelled, reason: not valid java name */
    public static void m2757$$Nest$monRemoteAnimationFinishedOrCancelled(StageCoordinator stageCoordinator, WindowContainerTransaction windowContainerTransaction) {
        stageCoordinator.mIsDividerRemoteAnimating = false;
        stageCoordinator.mShouldUpdateRecents = true;
        stageCoordinator.clearRequestIfPresented();
        if (stageCoordinator.mMainStage.getChildCount() == 0 || stageCoordinator.mSideStage.getChildCount() == 0) {
            ((HandlerExecutor) stageCoordinator.mMainExecutor).execute(new StageCoordinator$$ExternalSyntheticLambda3(stageCoordinator, 7));
            stageCoordinator.mSplitUnsupportedToast.show();
        } else {
            SyncTransactionQueue syncTransactionQueue = stageCoordinator.mSyncQueue;
            syncTransactionQueue.queue(windowContainerTransaction);
            syncTransactionQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda0(stageCoordinator, 4));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17, types: [android.content.SharedPreferences$OnSharedPreferenceChangeListener, com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda5] */
    public StageCoordinator(Context context, int i, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, Transitions transitions, TransactionPool transactionPool, IconProvider iconProvider, ShellExecutor shellExecutor, Optional<RecentTasksController> optional) {
        SurfaceSession surfaceSession = new SurfaceSession();
        StageListenerImpl stageListenerImpl = new StageListenerImpl();
        this.mMainStageListener = stageListenerImpl;
        StageListenerImpl stageListenerImpl2 = new StageListenerImpl();
        this.mSideStageListener = stageListenerImpl2;
        StageListenerImpl stageListenerImpl3 = new StageListenerImpl();
        this.mCellStageListener = stageListenerImpl3;
        int i2 = 0;
        this.mCellStageWindowConfigPosition = 0;
        this.mSideStagePosition = 1;
        ArrayList arrayList = new ArrayList();
        this.mListeners = arrayList;
        this.mPausingTasks = new ArrayList();
        this.mTempRect1 = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRect3 = new Rect();
        this.mTopStageAfterFoldDismiss = -1;
        this.mIsFolded = false;
        this.mIsFlexPanelMode = false;
        this.mSkipFlexPanelUpdate = false;
        this.mIsVideoControls = false;
        this.mWillBeVideoControls = false;
        this.mDelayedHandleLayoutSizeChange = new StageCoordinator$$ExternalSyntheticLambda3(this, i2);
        this.mLastPackageNameList = new ArrayList();
        this.mCurrentPackageNameList = new ArrayList();
        this.mExcludeLoggingPackages = Arrays.asList("com.sec.android.app.launcher", "com.android.systemui");
        this.mSeqForAsyncTransaction = -1L;
        this.mLastTransactionType = 0;
        this.mTempRect = new Rect();
        Configuration configuration = new Configuration();
        this.mLastConfiguration = configuration;
        this.mParentContainerCallbacks = new C41151();
        RecentsTransitionCallback recentsTransitionCallback = CoreRune.MW_MULTI_SPLIT_BACKGROUND ? new RecentsTransitionCallback(this, i2) : null;
        this.mSplitDivision = 0;
        this.mLastMainSplitDivision = 0;
        this.mSplitLayoutChangedForLaunchAdjacent = false;
        this.mAppPairStarted = false;
        new ArrayList();
        new ArrayList();
        new ArrayList();
        new ArrayList();
        this.mContext = context;
        this.mDisplayId = i;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mLogger = new SplitscreenEventLogger();
        this.mMainExecutor = shellExecutor;
        this.mRecentTasks = optional;
        shellTaskOrganizer.createRootTask(i, this);
        this.mMainStage = new MainStage(context, shellTaskOrganizer, i, stageListenerImpl, syncTransactionQueue, surfaceSession, iconProvider);
        this.mSideStage = new SideStage(context, shellTaskOrganizer, i, stageListenerImpl2, syncTransactionQueue, surfaceSession, iconProvider);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            this.mCellStage = new CellStage(context, shellTaskOrganizer, i, stageListenerImpl3, syncTransactionQueue, surfaceSession, iconProvider);
        }
        configuration.updateFrom(context.getResources().getConfiguration());
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mTransactionPool = transactionPool;
        ((DeviceStateManager) context.getSystemService(DeviceStateManager.class)).registerCallback(shellTaskOrganizer.getExecutor(), new DeviceStateManager.FoldStateListener(context, new StageCoordinator$$ExternalSyntheticLambda4(this, 0)));
        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
            ?? r0 = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda5
                @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
                public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
                    StageCoordinator stageCoordinator = StageCoordinator.this;
                    stageCoordinator.getClass();
                    if ("video_controls_mode".equals(str)) {
                        Log.d("StageCoordinator", "onSharedPreferenceChanged, key = " + str);
                        stageCoordinator.setDividerSizeIfNeeded(true);
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        stageCoordinator.updateWindowBounds(stageCoordinator.mSplitLayout, windowContainerTransaction, false);
                        stageCoordinator.mSyncQueue.queue(windowContainerTransaction);
                    }
                }
            };
            this.mSharedPrefListener = r0;
            context.getSharedPreferences("video_controls_pref", 0).registerOnSharedPreferenceChangeListener(r0);
        }
        this.mSplitTransitions = new SplitScreenTransitions(transactionPool, transitions, new StageCoordinator$$ExternalSyntheticLambda3(this, 2), this);
        displayController.addDisplayWindowListener(this);
        transitions.addHandler(this);
        this.mSplitUnsupportedToast = Toast.makeText(context, R.string.dock_non_resizeble_failed_to_dock_text, 0);
        this.mShouldUpdateRecents = Transitions.ENABLE_SHELL_TRANSITIONS;
        SplitBackgroundController splitBackgroundController = new SplitBackgroundController(context, this, transactionPool, shellExecutor, displayController);
        this.mSplitBackgroundController = splitBackgroundController;
        if (CoreRune.MW_MULTI_SPLIT_BACKGROUND) {
            transitions.mRecentTransitionCallback = recentsTransitionCallback;
            if (CoreRune.SAFE_DEBUG) {
                Log.d("ShellTransitions", "registerRecentTransitionCallback: " + recentsTransitionCallback);
            }
        }
        if (arrayList.contains(splitBackgroundController)) {
            return;
        }
        arrayList.add(splitBackgroundController);
        sendStatusToListener(splitBackgroundController);
    }

    public static void addActivityOptions(Bundle bundle, StageTaskListener stageTaskListener) {
        if (stageTaskListener != null) {
            bundle.putParcelable("android.activity.launchRootTaskToken", stageTaskListener.mRootTaskInfo.token);
        }
        bundle.putBoolean("android:activity.startedFromWindowTypeLauncher", true);
        bundle.putInt("android.activity.launchDisplayId", 0);
        bundle.putBoolean("android.pendingIntent.backgroundActivityAllowed", true);
        bundle.putBoolean("android.pendingIntent.backgroundActivityAllowedByPermission", true);
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

    public static boolean isSameIntentRequested(TaskInfo taskInfo, Intent intent, UserHandle userHandle) {
        return (taskInfo == null || intent == null || userHandle == null || intent.getComponent() == null || taskInfo.baseActivity == null || !intent.getComponent().getPackageName().equals(taskInfo.baseActivity.getPackageName()) || taskInfo.userId != userHandle.getIdentifier()) ? false : true;
    }

    public static boolean isVideoControlsTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ComponentName componentName;
        if (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null) {
            return false;
        }
        return MultiWindowUtils.isVideoControlsActivity(componentName.getClassName());
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
            } else if (i == 72) {
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
            } else if (i2 == 48) {
                multiSplitLayoutInfo.cellStagePosition = 96;
            }
            return 4;
        }
        if (convertCreateMode == 4) {
            multiSplitLayoutInfo.splitDivision = 1;
            int i3 = multiSplitLayoutInfo.cellStagePosition;
            if (i3 == 48) {
                multiSplitLayoutInfo.cellStagePosition = 96;
            } else if (i3 == 96) {
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
        } else if (i4 == 96) {
            multiSplitLayoutInfo.cellStagePosition = 72;
        }
        return 2;
    }

    public static void sendMessageProxyService(StageLaunchOptions stageLaunchOptions, int i) {
        SplitScreenProxyService splitScreenProxyService = (SplitScreenProxyService) LocalServices.getService(SplitScreenProxyService.class);
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("stage_position", stageLaunchOptions.mSideStagePosition);
        bundle.putInt("split_create_mode", stageLaunchOptions.mSplitCreateMode);
        bundle.putFloat("stage_ratio", stageLaunchOptions.mStageRatio);
        bundle.putFloat("cell_ratio", stageLaunchOptions.mCellRatio);
        bundle.putInt("launch_task_id", stageLaunchOptions.mLaunchTaskId);
        bundle.putParcelable("main_stage_intent", stageLaunchOptions.mMainStageIntent);
        bundle.putParcelable("side_stage_intent", stageLaunchOptions.mSideStageIntent);
        bundle.putParcelable("main_stage_user_handle", stageLaunchOptions.mMainStageUserHandle);
        bundle.putParcelable("side_stage_user_handle", stageLaunchOptions.mSideStageUserHandle);
        bundle.putInt("left_top_task_id", stageLaunchOptions.mLeftTopTaskId);
        bundle.putInt("right_bottom_task_id", stageLaunchOptions.mRightBottomTaskId);
        bundle.putInt("cell_task_id", stageLaunchOptions.mCellTaskId);
        bundle.putInt("tap_task_id", stageLaunchOptions.mTapTaskId);
        bundle.putParcelable("tap_intent", stageLaunchOptions.mTapIntent);
        bundle.putParcelable("tap_user_handle", stageLaunchOptions.mTapUserHandle);
        bundle.putParcelable("cell_stage_intent", stageLaunchOptions.mCellStageIntent);
        bundle.putParcelable("cell_stage_user_handle", stageLaunchOptions.mCellStageUserHandle);
        bundle.putBoolean("grouped_recent_vertically", stageLaunchOptions.mAppsStackedVertically);
        bundle.putParcelable("change_app_intent", stageLaunchOptions.mChangeAppIntent);
        bundle.putParcelable("change_app_user_handle", stageLaunchOptions.mChangeAppUserHandle);
        bundle.putInt("change_app_stage_type", stageLaunchOptions.mChangeAppStageType);
        bundle.putInt("cell_stage_position", stageLaunchOptions.mCellStageWindowConfigPosition);
        bundle.putString("launch_from", stageLaunchOptions.mLaunchFrom);
        bundle.putInt("split_division", stageLaunchOptions.mSplitDivision);
        bundle.putParcelable("pending_intent", stageLaunchOptions.mPendingIntent);
        bundle.putParcelable("remote_transition", stageLaunchOptions.mRemoteTransition);
        message.setData(bundle);
        message.what = i;
        splitScreenProxyService.getClass();
        try {
            splitScreenProxyService.mMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static boolean shouldBreakPairedTaskInRecents(int i) {
        return i == 1 || i == 2 || i == 3 || i == 4 || i == 8 || i == 9 || i == 11 || i == 13;
    }

    public final void addCellDividerBarToTransition(TransitionInfo transitionInfo, boolean z) {
        SurfaceControl cellDividerLeash = this.mSplitLayout.getCellDividerLeash();
        if (cellDividerLeash == null || !cellDividerLeash.isValid()) {
            Slog.w("StageCoordinator", "addDividerBarToTransition but leash was released or not be created");
            return;
        }
        TransitionInfo.Change change = new TransitionInfo.Change((WindowContainerToken) null, cellDividerLeash);
        Rect refCellDividerBounds = this.mSplitLayout.getRefCellDividerBounds();
        Rect rect = this.mTempRect3;
        rect.set(refCellDividerBounds);
        change.setParent(this.mRootTaskInfo.token);
        change.setStartAbsBounds(rect);
        change.setEndAbsBounds(rect);
        change.setMode(z ? 3 : 4);
        change.setFlags(QuickStepContract.SYSUI_STATE_BACK_DISABLED);
        transitionInfo.addChange(change);
        StringBuilder sb = new StringBuilder("addCellDividerBarToTransition:[MST] leash=");
        sb.append(cellDividerLeash);
        sb.append(", Callers=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(7, sb, "StageCoordinator");
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
        splitLayout.getRefDividerBounds(rect);
        change.setParent(this.mRootTaskInfo.token);
        change.setStartAbsBounds(rect);
        change.setEndAbsBounds(rect);
        change.setMode(z ? 3 : 4);
        change.setFlags(QuickStepContract.SYSUI_STATE_BACK_DISABLED);
        transitionInfo.addChange(change);
        if (CoreRune.MW_SHELL_TRANSITION_LOG) {
            StringBuilder sb = new StringBuilder("addDividerBarToTransition:[MST] leash=");
            sb.append(dividerLeash);
            sb.append(", Callers=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(7, sb, "StageCoordinator");
        }
    }

    public final void applyCellDividerVisibility(SurfaceControl.Transaction transaction) {
        final SurfaceControl cellDividerLeash = this.mSplitLayout.getCellDividerLeash();
        if (cellDividerLeash == null) {
            return;
        }
        if (this.mIsDividerRemoteAnimating) {
            if (CoreRune.SAFE_DEBUG) {
                Slog.d("StageCoordinator", "applyCellDividerVisibility: skip, divider is remote animating!");
                return;
            }
            return;
        }
        ValueAnimator valueAnimator = this.mCellDividerFadeInAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            Slog.d("StageCoordinator", "applyCellDividerVisibility: cancel, prev animator");
            this.mCellDividerFadeInAnimator.cancel();
        }
        Slog.d("StageCoordinator", "applyCellDividerVisibility: vis=" + this.mCellDividerVisible);
        Rect refCellDividerBounds = this.mSplitLayout.getRefCellDividerBounds();
        this.mTempRect3.set(refCellDividerBounds);
        if (transaction != null) {
            transaction.setVisibility(cellDividerLeash, this.mCellDividerVisible);
            transaction.setLayer(cellDividerLeash, Integer.MAX_VALUE);
            transaction.setPosition(cellDividerLeash, r2.left, r2.top);
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
        this.mCellDividerFadeInAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
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
                acquire2.show(cellDividerLeash);
                acquire2.setAlpha(cellDividerLeash, 0.0f);
                acquire2.setLayer(cellDividerLeash, Integer.MAX_VALUE);
                acquire2.setPosition(cellDividerLeash, StageCoordinator.this.mSplitLayout.getRefCellDividerBounds().left, StageCoordinator.this.mSplitLayout.getRefCellDividerBounds().top);
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
            if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -188242801, 0, "   Skip animating divider bar due to divider leash not ready.", null);
                return;
            }
            return;
        }
        if (this.mIsDividerRemoteAnimating) {
            if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1118138034, 0, "   Skip animating divider bar due to it's remote animating.", null);
                return;
            }
            return;
        }
        ValueAnimator valueAnimator = this.mDividerFadeInAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mDividerFadeInAnimator.cancel();
        }
        this.mSplitLayout.getRefDividerBounds(this.mTempRect1);
        if (transaction != null) {
            transaction.setVisibility(dividerLeash, this.mDividerVisible);
            transaction.setLayer(dividerLeash, Integer.MAX_VALUE);
            transaction.setPosition(dividerLeash, r3.left, r3.top);
        } else {
            boolean z = this.mDividerVisible;
            TransactionPool transactionPool = this.mTransactionPool;
            if (z) {
                final SurfaceControl.Transaction acquire = transactionPool.acquire();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.mDividerFadeInAnimator = ofFloat;
                ofFloat.addUpdateListener(new StageCoordinator$$ExternalSyntheticLambda10(this, dividerLeash, acquire, 0));
                this.mDividerFadeInAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.7
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
                        stageCoordinator.mSplitLayout.getRefDividerBounds(stageCoordinator.mTempRect1);
                        acquire.show(dividerLeash);
                        acquire.setAlpha(dividerLeash, 0.0f);
                        acquire.setLayer(dividerLeash, Integer.MAX_VALUE);
                        SurfaceControl.Transaction transaction2 = acquire;
                        SurfaceControl surfaceControl2 = dividerLeash;
                        SplitLayout splitLayout = StageCoordinator.this.mSplitLayout;
                        SplitLayout.ImePositionProcessor imePositionProcessor = splitLayout.mImePositionProcessor;
                        Rect rect = SplitLayout.this.mDividerBounds;
                        Rect rect2 = splitLayout.mTempRect;
                        rect2.set(rect);
                        int i = imePositionProcessor.mYOffsetForIme;
                        if (i != 0) {
                            rect2.offset(0, i);
                        }
                        float f = rect2.left;
                        SplitLayout splitLayout2 = StageCoordinator.this.mSplitLayout;
                        SplitLayout.ImePositionProcessor imePositionProcessor2 = splitLayout2.mImePositionProcessor;
                        Rect rect3 = SplitLayout.this.mDividerBounds;
                        Rect rect4 = splitLayout2.mTempRect;
                        rect4.set(rect3);
                        int i2 = imePositionProcessor2.mYOffsetForIme;
                        if (i2 != 0) {
                            rect4.offset(0, i2);
                        }
                        transaction2.setPosition(surfaceControl2, f, rect4.top);
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

    public final void applyExitSplitScreen(StageTaskListener stageTaskListener, WindowContainerTransaction windowContainerTransaction, int i) {
        MainStage mainStage = this.mMainStage;
        if (!mainStage.mIsActive || this.mIsExiting) {
            return;
        }
        onSplitScreenExit();
        clearSplitPairedInRecents(i);
        this.mShouldUpdateRecents = false;
        this.mIsDividerRemoteAnimating = false;
        this.mSplitRequest = null;
        Rect rect = this.mSplitLayout.mInvisibleBounds;
        Rect rect2 = this.mTempRect1;
        rect2.set(rect);
        int i2 = 1;
        if (stageTaskListener == null || stageTaskListener.getTopVisibleChildTaskId() == -1) {
            SideStage sideStage = this.mSideStage;
            sideStage.removeAllTasks(windowContainerTransaction, false, true);
            mainStage.deactivate(windowContainerTransaction, false);
            windowContainerTransaction.reorder(this.mRootTaskInfo.token, false);
            setRootForceTranslucent(windowContainerTransaction, true);
            windowContainerTransaction.setBounds(sideStage.mRootTaskInfo.token, rect2);
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
        syncTransactionQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda2(this, stageTaskListener, i2));
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
            this.mTopStageAfterFoldDismiss = -1;
        }
        Slog.i("StageCoordinator", "applyExitSplitScreen, reason = " + SplitScreenController.exitReasonToString(i));
        if (stageTaskListener != null) {
            logExitToStage(i, stageTaskListener == mainStage);
        } else {
            this.mLogger.logExit(i, -1, 0, -1, 0, this.mSplitLayout.isLandscape());
        }
        if (CoreRune.MW_SA_LOGGING) {
            this.mLastPackageNameList.clear();
        }
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

    public final void clearRequestIfPresented() {
        boolean z;
        StageListenerImpl stageListenerImpl = this.mSideStageListener;
        if (stageListenerImpl.mVisible && (z = stageListenerImpl.mHasChildren) && this.mMainStageListener.mVisible && z) {
            this.mSplitRequest = null;
        }
    }

    public final void clearSplitPairedInRecents(int i) {
        if (shouldBreakPairedTaskInRecents(i) && this.mShouldUpdateRecents) {
            this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda4(this, 1));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x003f, code lost:
    
        if (r3.containsToken(r9) != false) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0057 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dismissSplitTask(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction, boolean z) {
        boolean z2 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        int i = 0;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (!z2 || !isMultiSplitActive()) {
            if (!stageTaskListener2.containsToken(windowContainerToken)) {
                if (stageTaskListener.containsToken(windowContainerToken)) {
                    i = 1;
                    r1 = 0;
                }
                r1 = -1;
            }
            if (r1 != -1 || i == -1) {
                Slog.w("StageCoordinator", "dismissSplitTask: failed, cannot find " + windowContainerToken);
            } else {
                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                    prepareAndStartDismissTransition(r1, i, windowContainerTransaction, z);
                    return;
                }
                return;
            }
        }
        int cellHostStageType = getCellHostStageType();
        int cellHostStageType2 = getCellHostStageType();
        r1 = cellHostStageType2 != 0 ? cellHostStageType2 == 1 ? 0 : -1 : 1;
        CellStage cellStage = this.mCellStage;
        StageTaskListener stageTaskListener3 = cellStage.mHost;
        if (stageTaskListener3 != stageTaskListener2) {
            stageTaskListener = stageTaskListener2;
        }
        if (cellStage.containsToken(windowContainerToken)) {
            i = 2;
            r1 = cellHostStageType;
            if (r1 != -1) {
            }
            Slog.w("StageCoordinator", "dismissSplitTask: failed, cannot find " + windowContainerToken);
        }
        if (stageTaskListener3.containsToken(windowContainerToken)) {
            r1 = cellHostStageType;
        }
        i = r1;
        if (r1 != -1) {
        }
        Slog.w("StageCoordinator", "dismissSplitTask: failed, cannot find " + windowContainerToken);
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void dump(PrintWriter printWriter, String str) {
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "  ");
        String m14m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, "  ");
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, "StageCoordinator mDisplayId=");
        m2m.append(this.mDisplayId);
        printWriter.println(m2m.toString());
        printWriter.println(m14m + "mDividerVisible=" + this.mDividerVisible);
        StringBuilder sb = new StringBuilder();
        sb.append(m14m);
        sb.append("isSplitActive=");
        MainStage mainStage = this.mMainStage;
        sb.append(mainStage.mIsActive);
        printWriter.println(sb.toString());
        printWriter.println(m14m + "isSplitVisible=" + isSplitScreenVisible());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(m14m);
        sb2.append("MainStage");
        printWriter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(m14m2);
        sb3.append("stagePosition=");
        int mainStagePosition = getMainStagePosition();
        String str2 = "UNKNOWN";
        sb3.append(mainStagePosition != -1 ? mainStagePosition != 0 ? mainStagePosition != 1 ? "UNKNOWN" : "SPLIT_POSITION_BOTTOM_OR_RIGHT" : "SPLIT_POSITION_TOP_OR_LEFT" : "SPLIT_POSITION_UNDEFINED");
        printWriter.println(sb3.toString());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(m14m2);
        sb4.append("isActive=");
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(sb4, mainStage.mIsActive, printWriter);
        mainStage.dump(printWriter, m14m2);
        printWriter.println(m14m + "MainStageListener");
        this.mMainStageListener.dump(printWriter, m14m2);
        printWriter.println(m14m + "SideStage");
        StringBuilder sb5 = new StringBuilder();
        sb5.append(m14m2);
        sb5.append("stagePosition=");
        int i = this.mSideStagePosition;
        if (i == -1) {
            str2 = "SPLIT_POSITION_UNDEFINED";
        } else if (i == 0) {
            str2 = "SPLIT_POSITION_TOP_OR_LEFT";
        } else if (i == 1) {
            str2 = "SPLIT_POSITION_BOTTOM_OR_RIGHT";
        }
        KeyboardUI$$ExternalSyntheticOutline0.m134m(sb5, str2, printWriter);
        this.mSideStage.dump(printWriter, m14m2);
        printWriter.println(m14m + "SideStageListener");
        this.mSideStageListener.dump(printWriter, m14m2);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            printWriter.println(m14m + "CellStage");
            printWriter.println(m14m2 + "stagePosition=" + WindowConfiguration.stagePositionToString(this.mCellStageWindowConfigPosition));
            this.mCellStage.dump(printWriter, m14m2);
            printWriter.println(m14m + "CellStageListener");
            this.mCellStageListener.dump(printWriter, m14m2);
        }
        if (mainStage.mIsActive) {
            printWriter.println(m14m + "SplitLayout");
            SplitLayout splitLayout = this.mSplitLayout;
            splitLayout.getClass();
            printWriter.println(m14m2 + "bounds1=" + splitLayout.mBounds1.toShortString());
            printWriter.println(m14m2 + "dividerBounds=" + splitLayout.mDividerBounds.toShortString());
            printWriter.println(m14m2 + "bounds2=" + splitLayout.mBounds2.toShortString());
            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
                StringBuilder m2m2 = AbstractC0000x2c234b15.m2m(m14m2, "bounds3=");
                m2m2.append(splitLayout.mBounds3.toShortString());
                printWriter.println(m2m2.toString());
            }
        }
        ArrayList arrayList = this.mPausingTasks;
        if (arrayList.isEmpty()) {
            return;
        }
        printWriter.println(m14m2 + "mPausingTasks=" + arrayList);
    }

    public final void exitSplitScreen(StageTaskListener stageTaskListener, int i) {
        if (this.mMainStage.mIsActive) {
            applyExitSplitScreen(stageTaskListener, new WindowContainerTransaction(), i);
            if (CoreRune.MW_SA_RUNESTONE_LOGGING) {
                RunestoneLogger.sendDismissMultiWindowState(this.mContext);
            }
        }
    }

    public final void finishEnterSplitScreen(SurfaceControl.Transaction transaction, boolean z) {
        this.mSplitLayout.update(transaction);
        MainStage mainStage = this.mMainStage;
        SplitDecorManager splitDecorManager = mainStage.mSplitDecorManager;
        getMainStageBounds();
        splitDecorManager.getClass();
        SideStage sideStage = this.mSideStage;
        SplitDecorManager splitDecorManager2 = sideStage.mSplitDecorManager;
        getSideStageBounds();
        splitDecorManager2.getClass();
        setDividerVisibility(true, transaction);
        transaction.reparent(this.mSplitLayout.getDividerLeash(), this.mRootTaskLeash);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && z) {
            SplitLayout splitLayout = this.mSplitLayout;
            splitLayout.releaseCellDivider(transaction);
            if (!splitLayout.mCellInitialized) {
                splitLayout.mCellInitialized = true;
                splitLayout.mCellSplitWindowManager.init(splitLayout, splitLayout.mInsetsState);
                splitLayout.mCellSnapAlgorithm = splitLayout.createCellSnapAlgorithm();
            }
            SplitDecorManager splitDecorManager3 = this.mCellStage.mSplitDecorManager;
            SplitLayout splitLayout2 = this.mSplitLayout;
            splitLayout2.getClass();
            new Rect(splitLayout2.mBounds3);
            splitDecorManager3.getClass();
            setCellDividerVisibility(transaction, true);
            transaction.reparent(this.mSplitLayout.getCellDividerLeash(), this.mRootTaskLeash);
        }
        updateSurfaceBounds(this.mSplitLayout, transaction, false);
        transaction.show(this.mRootTaskLeash);
        setSplitsVisible(true, false);
        this.mIsDropEntering = false;
        this.mSplitRequest = null;
        updateRecentTasksSplitPair();
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        if (splitscreenEventLogger.mLoggerSessionId != null) {
            return;
        }
        splitscreenEventLogger.logEnter(this.mSplitLayout.getDividerPositionAsFraction(), getMainStagePosition(), mainStage.getTopChildTaskUid(), this.mSideStagePosition, sideStage.getTopChildTaskUid(), this.mSplitLayout.isLandscape());
    }

    public final int getActivateSplitPosition(TaskInfo taskInfo) {
        SplitRequest splitRequest = this.mSplitRequest;
        if (splitRequest != null && taskInfo != null) {
            int i = splitRequest.mActivateTaskId;
            if (i != 0 && splitRequest.mActivateTaskId2 == taskInfo.taskId) {
                return splitRequest.mActivatePosition;
            }
            if (i == taskInfo.taskId) {
                return splitRequest.mActivatePosition;
            }
            String packageName = SplitScreenUtils.getPackageName(splitRequest.mStartIntent);
            String packageName2 = SplitScreenUtils.getPackageName(taskInfo.baseIntent);
            if (packageName != null && packageName.equals(packageName2)) {
                return this.mSplitRequest.mActivatePosition;
            }
            String packageName3 = SplitScreenUtils.getPackageName(this.mSplitRequest.mStartIntent2);
            if (packageName3 != null && packageName3.equals(packageName2)) {
                return this.mSplitRequest.mActivatePosition;
            }
        }
        return -1;
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
        CellStage cellStage = this.mCellStage;
        if (cellStage.mIsActive) {
            return getStageType(cellStage.mHost);
        }
        return -1;
    }

    public final RemoteAnimationTarget getDividerBarLegacyTarget() {
        SplitLayout splitLayout = this.mSplitLayout;
        splitLayout.getClass();
        Rect rect = new Rect(splitLayout.mDividerBounds);
        return new RemoteAnimationTarget(-1, -1, this.mSplitLayout.getDividerLeash(), false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), rect, rect, new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2034);
    }

    public final int getFocusedStageType() {
        if (this.mMainStage.isFocused()) {
            return 0;
        }
        if (this.mSideStage.isFocused()) {
            return 1;
        }
        return (CoreRune.MW_MULTI_SPLIT && this.mCellStage.isFocused()) ? 2 : -1;
    }

    public final int getInvertedCurrentPosition() {
        SplitLayout splitLayout = this.mSplitLayout;
        int i = splitLayout.mDividePosition;
        DividerSnapAlgorithm dividerSnapAlgorithm = splitLayout.getDividerSnapAlgorithm();
        int i2 = dividerSnapAlgorithm.getFirstSplitTarget().position;
        int i3 = dividerSnapAlgorithm.getLastSplitTarget().position;
        if (i2 > i || i3 < i) {
            return i;
        }
        int i4 = (i2 + i3) - i;
        return i4 == i ? i4 - 1 : i4;
    }

    public final Rect getMainStageBounds() {
        return (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && isMultiSplitActive() && getCellHostStageType() == 0) ? this.mSplitLayout.getHostBounds() : this.mSideStagePosition == 0 ? this.mSplitLayout.getBounds2() : this.mSplitLayout.getBounds1();
    }

    public final int getMainStagePosition() {
        return SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
    }

    public final int getMainStageWinConfigPosition() {
        if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            return isLandscape() ? this.mSideStagePosition == 0 ? 32 : 8 : this.mSideStagePosition == 0 ? 64 : 16;
        }
        CellStage cellStage = this.mCellStage;
        if (cellStage.mIsActive) {
            if (this.mMainStage.equals(cellStage.mHost)) {
                return isVerticalDivision() ? (this.mCellStageWindowConfigPosition & 16) != 0 ? this.mSideStagePosition == 0 ? 96 : 72 : this.mSideStagePosition == 0 ? 48 : 24 : (this.mCellStageWindowConfigPosition & 8) != 0 ? this.mSideStagePosition == 0 ? 96 : 48 : this.mSideStagePosition == 0 ? 72 : 24;
            }
        }
        return isVerticalDivision() ? this.mSideStagePosition == 0 ? 32 : 8 : this.mSideStagePosition == 0 ? 64 : 16;
    }

    public final Rect getSideStageBounds() {
        return (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && isMultiSplitActive() && getCellHostStageType() == 1) ? this.mSplitLayout.getHostBounds() : this.mSideStagePosition == 0 ? this.mSplitLayout.getBounds1() : this.mSplitLayout.getBounds2();
    }

    public final int getSideStageWinConfigPosition() {
        if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            return isLandscape() ? this.mSideStagePosition == 0 ? 8 : 32 : this.mSideStagePosition == 0 ? 16 : 64;
        }
        CellStage cellStage = this.mCellStage;
        if (cellStage.mIsActive) {
            if (this.mSideStage.equals(cellStage.mHost)) {
                return isVerticalDivision() ? (this.mCellStageWindowConfigPosition & 16) != 0 ? this.mSideStagePosition == 0 ? 72 : 96 : this.mSideStagePosition == 0 ? 24 : 48 : (this.mCellStageWindowConfigPosition & 8) != 0 ? this.mSideStagePosition == 0 ? 48 : 96 : this.mSideStagePosition == 0 ? 24 : 72;
            }
        }
        return isVerticalDivision() ? this.mSideStagePosition == 0 ? 8 : 32 : this.mSideStagePosition == 0 ? 16 : 64;
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
        MainStage mainStage = this.mMainStage;
        if (mainStage.containsToken(windowContainerToken)) {
            return getMainStagePosition();
        }
        if (this.mSideStage.containsToken(windowContainerToken)) {
            return this.mSideStagePosition;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            CellStage cellStage = this.mCellStage;
            if (cellStage.containsToken(windowContainerToken)) {
                return cellStage.mHost == mainStage ? getMainStagePosition() : this.mSideStagePosition;
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
        return (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mCellStage.containsToken(windowContainerToken)) ? 2 : -1;
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

    public final int getStageOfTask(int i) {
        if (this.mMainStage.containsTask(i)) {
            return 0;
        }
        if (this.mSideStage.containsTask(i)) {
            return 1;
        }
        return (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mCellStage.containsTask(i)) ? 2 : -1;
    }

    public final StageTaskListener getStageTaskListenerByStageType(int i) {
        if (i == 0) {
            return this.mMainStage;
        }
        if (i == 1) {
            return this.mSideStage;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 2) {
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
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && i == 2 && (runningTaskInfo = this.mCellStage.mRootTaskInfo) != null) {
            return runningTaskInfo.token;
        }
        return null;
    }

    public final int getStageType(StageTaskListener stageTaskListener) {
        if (stageTaskListener == null) {
            return -1;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageTaskListener == this.mCellStage) {
            return 2;
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
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 2) {
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
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 2) {
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
        SplitLayout splitLayout = this.mSplitLayout;
        splitLayout.getClass();
        return new Rect(splitLayout.mBounds3).bottom;
    }

    public final void handleLayoutSizeChange(SplitLayout splitLayout, boolean z) {
        if (this.mIsDividerRemoteAnimating) {
            return;
        }
        if (this.mKeyguardShowing) {
            z = false;
        }
        HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
        Handler handler = handlerExecutor.mHandler;
        StageCoordinator$$ExternalSyntheticLambda3 stageCoordinator$$ExternalSyntheticLambda3 = this.mDelayedHandleLayoutSizeChange;
        if (handler.hasCallbacks(stageCoordinator$$ExternalSyntheticLambda3)) {
            handlerExecutor.removeCallbacks(stageCoordinator$$ExternalSyntheticLambda3);
        }
        splitLayout.updateSnapAlgorithm(this.mSplitDivision);
        MainStage mainStage = this.mMainStage;
        boolean z2 = mainStage.mIsActive;
        boolean isSplitScreenVisible = isSplitScreenVisible();
        if (z2 && !isSplitScreenVisible) {
            splitLayout.update(null);
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        long j = this.mSeqForAsyncTransaction + 1;
        this.mSeqForAsyncTransaction = j;
        long max = Math.max(j, 0L);
        this.mSeqForAsyncTransaction = max;
        windowContainerTransaction.setSeqForAsyncTransaction(max);
        if (z2 && z) {
            windowContainerTransaction.setDisplayIdForChangeTransition(0, "handle_layout_size_change");
        }
        updateStagePositionIfNeeded(windowContainerTransaction);
        int i = this.mSideStagePosition;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = i == 0 ? stageTaskListener : mainStage;
        StageTaskListener stageTaskListener3 = i == 0 ? mainStage : stageTaskListener;
        overrideStageCoordinatorRootConfig(windowContainerTransaction);
        boolean z3 = CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY;
        CellStage cellStage = this.mCellStage;
        boolean applyTaskChanges = (z3 && cellStage.mIsActive) ? splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener2.mRootTaskInfo, stageTaskListener3.mRootTaskInfo, cellStage.mRootTaskInfo) : splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener2.mRootTaskInfo, stageTaskListener3.mRootTaskInfo);
        if (CoreRune.SAFE_DEBUG) {
            Slog.d("StageCoordinator", "handleLayoutSizeChange: wct=" + windowContainerTransaction + ", isSplitScreenVisible=" + isSplitScreenVisible + ", callers=" + Debug.getCallers(3));
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS && applyTaskChanges && isSplitScreenVisible) {
            this.mSplitLayout.setDividerInteractive("handleLayoutSizeChange", false, false);
            StageCoordinator$$ExternalSyntheticLambda1 stageCoordinator$$ExternalSyntheticLambda1 = new StageCoordinator$$ExternalSyntheticLambda1(this, 1);
            StageCoordinator$$ExternalSyntheticLambda1 stageCoordinator$$ExternalSyntheticLambda12 = new StageCoordinator$$ExternalSyntheticLambda1(this, 2);
            SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
            SplitScreenTransitions.TransitSession transitSession = splitScreenTransitions.mPendingResize;
            if (transitSession != null) {
                transitSession.mCanceled = true;
                transitSession.mFinishedCallback = null;
                splitScreenTransitions.mAnimations.clear();
                splitScreenTransitions.onFinish(null, null);
            }
            splitScreenTransitions.mPendingResize = new SplitScreenTransitions.TransitSession(splitScreenTransitions, splitScreenTransitions.mTransitions.startTransition(6, windowContainerTransaction, this), stageCoordinator$$ExternalSyntheticLambda12, stageCoordinator$$ExternalSyntheticLambda1);
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -522947324, 0, "  splitTransition  deduced Resize split screen", null);
            }
        } else {
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
        sendOnBoundsChanged();
        if (z2) {
            TransactionPool transactionPool = this.mTransactionPool;
            SurfaceControl.Transaction acquire = transactionPool.acquire();
            updateSurfaceBounds(splitLayout, acquire, false);
            mainStage.onResized(acquire);
            stageTaskListener.onResized(acquire);
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                cellStage.onResized(acquire);
            }
            acquire.apply();
            transactionPool.release(acquire);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:209:0x031d, code lost:
    
        if (r1 != 1) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x0346, code lost:
    
        prepareExitSplitScreen(-1, r3, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x034b, code lost:
    
        if (com.samsung.android.rune.CoreRune.MW_SPLIT_SHELL_TRANSITION == false) goto L234;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x034d, code lost:
    
        setSplitsVisible(false, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0331, code lost:
    
        if (r6.getChildCount() == r4) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0343, code lost:
    
        if (r5.getChildCount() == r4) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0117, code lost:
    
        if (r6 == 1) goto L63;
     */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        StageTaskListener stageTaskListener;
        StageTaskListener stageTaskListener2;
        int i;
        boolean z;
        boolean z2;
        boolean z3;
        AttributeCache.Entry entry;
        boolean z4;
        SplitLayout splitLayout;
        AlertDialog alertDialog;
        int i2;
        boolean z5;
        StageTaskListener stageTaskListener3;
        int stageType;
        int size;
        ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
        MainStage mainStage = this.mMainStage;
        int i3 = 1;
        if (triggerTask == null) {
            if (!mainStage.mIsActive) {
                return null;
            }
            TransitionRequestInfo.DisplayChange displayChange = transitionRequestInfo.getDisplayChange();
            if (transitionRequestInfo.getType() == 6 && displayChange != null && displayChange.getStartRotation() != displayChange.getEndRotation()) {
                this.mSplitLayout.mFreezeDividerWindow = true;
            }
            return new WindowContainerTransaction();
        }
        int i4 = triggerTask.displayId;
        int i5 = this.mDisplayId;
        if (i4 != i5) {
            return null;
        }
        int type = transitionRequestInfo.getType();
        boolean isOpeningType = TransitionUtil.isOpeningType(type);
        boolean z6 = triggerTask.getWindowingMode() == 1;
        boolean z7 = CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && triggerTask.getWindowingMode() == 5;
        if ((isOpeningType && z6) || (!mainStage.mIsActive && isOpeningType && z7)) {
            this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda16(triggerTask, i3));
        }
        this.mIsOpeningHomeDuringSplit = false;
        boolean z8 = mainStage.mIsActive;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (!z8) {
            if (!isOpeningType || getStageOfTask(triggerTask) == null) {
                return null;
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (!triggerTask.supportsMultiWindow) {
                return null;
            }
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && getStageType(getStageOfTask(triggerTask)) == 2) {
                prepareExitMultiSplitScreen(windowContainerTransaction, true);
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                return null;
            }
            setSideStagePositionByAdjacentTask(triggerTask, windowContainerTransaction);
            prepareEnterSplitScreen(windowContainerTransaction, null, -1, !this.mIsDropEntering);
            splitScreenTransitions.setEnterTransition(iBinder, transitionRequestInfo.getRemoteTransition(), VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, !this.mIsDropEntering);
            return windowContainerTransaction;
        }
        boolean z9 = ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled;
        StageTaskListener stageTaskListener4 = this.mSideStage;
        if (z9) {
            i = type;
            stageTaskListener = stageTaskListener4;
            stageTaskListener2 = mainStage;
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 165317020, 81, "  split is active so using splitTransition to handle request. triggerTask=%d type=%s mainChildren=%d sideChildren=%d", Long.valueOf(triggerTask.taskId), String.valueOf(WindowManager.transitTypeToString(type)), Long.valueOf(mainStage.getChildCount()), Long.valueOf(stageTaskListener4.getChildCount()));
        } else {
            stageTaskListener = stageTaskListener4;
            stageTaskListener2 = mainStage;
            i = type;
        }
        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
        StageTaskListener stageOfTask = getStageOfTask(triggerTask);
        CellStage cellStage = this.mCellStage;
        if (stageOfTask != null) {
            boolean isClosingType = TransitionUtil.isClosingType(i);
            StageTaskListener.RunningTaskInfoList runningTaskInfoList = stageOfTask.mRunningTaskInfoList;
            if (isClosingType) {
                if (stageOfTask.getChildCount() != 1) {
                    if (CoreRune.MW_SPLIT_STACKING) {
                        if (runningTaskInfoList == null) {
                            size = stageOfTask.mChildrenTaskInfo.size();
                        } else if (runningTaskInfoList.mClosingTaskIds.isEmpty()) {
                            size = runningTaskInfoList.mTaskIds.size();
                        } else {
                            Iterator it = runningTaskInfoList.mTaskIds.iterator();
                            int i6 = 0;
                            while (it.hasNext()) {
                                if (!runningTaskInfoList.mClosingTaskIds.contains((Integer) it.next())) {
                                    i6++;
                                }
                            }
                            size = i6;
                        }
                    }
                }
                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                    prepareSplitDismissChangeTransition(windowContainerTransaction2, getStageType(stageOfTask), transitionRequestInfo, false);
                }
                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && cellStage.mIsActive) {
                    int stageType2 = getStageType(stageOfTask);
                    if (stageType2 == 0) {
                        stageTaskListener3 = stageTaskListener2;
                        z5 = true;
                    } else {
                        z5 = true;
                        stageTaskListener3 = stageType2 == 1 ? stageTaskListener : null;
                    }
                    if (stageTaskListener3 != null) {
                        stageType = getStageType(stageTaskListener3);
                        reparentCellToMainOrSide(windowContainerTransaction2, stageTaskListener3, z5);
                    } else {
                        stageType = getStageType(cellStage);
                        prepareExitMultiSplitScreen(windowContainerTransaction2, false);
                    }
                    splitScreenTransitions.setDismissTransition(iBinder, stageType, 2, z5);
                } else {
                    int i7 = getStageType(stageOfTask) == 0 ? 1 : 0;
                    prepareExitSplitScreen(i7, windowContainerTransaction2, true);
                    splitScreenTransitions.setDismissTransition(iBinder, i7, 2, false);
                }
                if (CoreRune.MW_SPLIT_STACKING && TransitionUtil.isClosingType(i)) {
                    i2 = triggerTask.taskId;
                    if (runningTaskInfoList != null && !runningTaskInfoList.mClosingTaskIds.contains(Integer.valueOf(i2))) {
                        runningTaskInfoList.mClosingTaskIds.add(Integer.valueOf(i2));
                        Slog.d("StageTaskListener", "addToClosingTaskIds: #" + i2 + ", " + StageTaskListener.this);
                    }
                }
            }
            if (isOpeningType && !this.mPausingTasks.isEmpty()) {
                splitScreenTransitions.setDismissTransition(iBinder, getStageType(stageOfTask) == 0 ? 0 : 1, 2, false);
            } else if (!isSplitScreenVisible() && isOpeningType) {
                setSideStagePositionByAdjacentTask(triggerTask, windowContainerTransaction2);
                prepareEnterSplitScreen(windowContainerTransaction2, null, -1, !this.mIsDropEntering);
                splitScreenTransitions.setEnterTransition(iBinder, transitionRequestInfo.getRemoteTransition(), VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, true ^ this.mIsDropEntering);
            } else if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isSplitScreenVisible() && isOpeningType && !isMultiSplitActive() && getStageType(stageOfTask) == 2) {
                int i8 = this.mCellStageWindowConfigPosition;
                this.mSplitLayout.setCellDividerRatio(0.5f, i8, true, false);
                prepareEnterMultiSplitScreen(i8, windowContainerTransaction2);
                if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
                    applyCellHostResizeTransition(windowContainerTransaction2);
                }
                splitScreenTransitions.setEnterTransition(iBinder, transitionRequestInfo.getRemoteTransition(), 1100, false);
            }
            if (CoreRune.MW_SPLIT_STACKING) {
                i2 = triggerTask.taskId;
                if (runningTaskInfoList != null) {
                    runningTaskInfoList.mClosingTaskIds.add(Integer.valueOf(i2));
                    Slog.d("StageTaskListener", "addToClosingTaskIds: #" + i2 + ", " + StageTaskListener.this);
                }
            }
        } else if (isOpeningType && z6) {
            this.mSplitLayout.mSplitWindowManager.sendSplitStateChangedInfo(true);
            this.mLastSplitStateInfo = null;
            if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && this.mTopStageAfterFoldDismiss != -1) {
                int activityType = triggerTask.getActivityType();
                if (activityType == 2 || activityType == 3) {
                    z4 = true;
                } else {
                    z4 = true;
                    prepareExitSplitScreen(-1, windowContainerTransaction2, true);
                }
                setSplitsVisible(false, false);
                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                windowContainerTransaction3.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, z4);
                shellTaskOrganizer.applyTransaction(windowContainerTransaction3);
            }
            WindowContainerTransaction windowContainerTransaction4 = new WindowContainerTransaction();
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                ActivityInfo activityInfo = triggerTask.topActivityInfo;
                if (((activityInfo == null || triggerTask.numActivities != 1 || (entry = AttributeCache.instance().get(activityInfo.packageName, activityInfo.getThemeResource(), com.android.internal.R.styleable.Window)) == null) ? true : !ActivityInfo.isTranslucentOrFloating(entry.array)) && !this.mIsRecentsInSplitAnimating) {
                    char c = (stageTaskListener2.hasAppsEdgeActivityOnTop() || stageTaskListener2.getChildCount() == 0) ? (char) 0 : (stageTaskListener.hasAppsEdgeActivityOnTop() || stageTaskListener.getChildCount() == 0) ? (char) 1 : (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && cellStage.mIsActive && (cellStage.hasAppsEdgeActivityOnTop() || cellStage.getChildCount() == 0)) ? (char) 2 : (char) 65535;
                    if (c != 0 && c != 1) {
                        if (c == 2) {
                            prepareExitMultiSplitScreen(windowContainerTransaction4, false);
                            setCellSplitVisible(false);
                        }
                        z3 = true;
                    } else if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && cellStage.mIsActive) {
                        z3 = true;
                        reparentCellToMainOrSide(windowContainerTransaction4, c == 0 ? stageTaskListener2 : stageTaskListener, true);
                        setCellSplitVisible(false);
                    } else {
                        z3 = true;
                        prepareExitSplitScreen(c == 0 ? 1 : 0, windowContainerTransaction4, true);
                        setSplitsVisible(false, false);
                    }
                    windowContainerTransaction2.merge(windowContainerTransaction4, z3);
                }
            }
            int activityType2 = triggerTask.getActivityType();
            if (activityType2 == 2 || activityType2 == 3) {
                if (isSplitScreenVisible() || splitScreenTransitions.mPendingEnter != null) {
                    if (splitScreenTransitions.mPendingEnter != null) {
                        z = true;
                        this.mIsOpeningHomeDuringSplit = true;
                    } else {
                        z = true;
                    }
                    WindowContainerTransaction windowContainerTransaction5 = new WindowContainerTransaction();
                    windowContainerTransaction5.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, z);
                    shellTaskOrganizer.applyTransaction(windowContainerTransaction5);
                }
                if (windowContainerTransaction4.isEmpty()) {
                    return null;
                }
                windowContainerTransaction4.setDisplayIdForChangeTransition(i5, "evict_all_children");
                return windowContainerTransaction4;
            }
            StageTaskListener stageTaskListener5 = stageTaskListener2;
            if (stageTaskListener5.containsTask(triggerTask.taskId)) {
                int childCount = stageTaskListener5.getChildCount();
                z2 = true;
            } else {
                z2 = true;
            }
            StageTaskListener stageTaskListener6 = stageTaskListener;
            if (stageTaskListener6.containsTask(triggerTask.taskId)) {
            }
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                if (cellStage.containsTask(triggerTask.taskId)) {
                }
            }
        }
        if (isSplitScreenVisible() && isOpeningType && (splitLayout = this.mSplitLayout) != null && (alertDialog = splitLayout.mSplitWindowManager.mDividerPanel.mAddToAppPairDialog) != null) {
            alertDialog.dismiss();
        }
        if (!windowContainerTransaction2.isEmpty() || isSplitScreenVisible()) {
            return windowContainerTransaction2;
        }
        return null;
    }

    public final boolean hasSameRatioInGroupedTasks(SplitBounds splitBounds, boolean z) {
        SplitBounds splitBounds2 = this.mLastSplitStateInfo;
        boolean z2 = splitBounds2.appsStackedVertically;
        if ((!z2 || splitBounds2.topTaskPercent == splitBounds.topTaskPercent) && (z2 || splitBounds2.leftTaskPercent == splitBounds.leftTaskPercent)) {
            return !(z && z2 && splitBounds2.cellTopTaskPercent != splitBounds.cellTopTaskPercent) && (!z2 || splitBounds2.cellLeftTaskPercent == splitBounds.cellLeftTaskPercent);
        }
        return false;
    }

    public final boolean isInSubDisplay() {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        return runningTaskInfo != null ? runningTaskInfo.configuration.semDisplayDeviceType == 5 : MultiWindowUtils.isInSubDisplay(this.mContext);
    }

    public final boolean isLandscape() {
        return this.mSplitLayout.isLandscape();
    }

    public final boolean isMultiSplitActive() {
        if (CoreRune.MW_MULTI_SPLIT) {
            return this.mCellStage.mIsActive;
        }
        return false;
    }

    public final boolean isMultiSplitScreenVisible() {
        return CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isSplitScreenVisible() && this.mCellStageListener.mVisible;
    }

    public final boolean isSplitScreenVisible() {
        return this.mSideStageListener.mVisible && this.mMainStageListener.mVisible;
    }

    public final boolean isVerticalDivision() {
        return getSplitDivision() == 0;
    }

    public final void launchAsFullscreenWithRemoteAnimation(PendingIntent pendingIntent, Intent intent, ShortcutInfo shortcutInfo, Bundle bundle, RemoteAnimationAdapter remoteAnimationAdapter, WindowContainerTransaction windowContainerTransaction) {
        StageCoordinator$$ExternalSyntheticLambda12 stageCoordinator$$ExternalSyntheticLambda12 = new StageCoordinator$$ExternalSyntheticLambda12(this, remoteAnimationAdapter, 1);
        addActivityOptions(bundle, null);
        if (shortcutInfo != null) {
            windowContainerTransaction.startShortcut(this.mContext.getPackageName(), shortcutInfo, bundle);
        } else if (pendingIntent != null) {
            windowContainerTransaction.sendPendingIntent(pendingIntent, intent, bundle);
        } else {
            Slog.e("StageCoordinator", "Pending intent and shortcut are null is invalid case.");
        }
        this.mSyncQueue.queue(stageCoordinator$$ExternalSyntheticLambda12, windowContainerTransaction);
    }

    public final void logExitToStage(int i, boolean z) {
        this.mLogger.logExit(i, z ? getMainStagePosition() : -1, z ? this.mMainStage.getTopChildTaskUid() : 0, z ? -1 : this.mSideStagePosition, !z ? this.mSideStage.getTopChildTaskUid() : 0, this.mSplitLayout.isLandscape());
    }

    public final void maximizeSplitTask(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction) {
        int i = 0;
        int i2 = this.mMainStage.containsToken(windowContainerToken) ? 0 : this.mSideStage.containsToken(windowContainerToken) ? 1 : (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mCellStage.containsToken(windowContainerToken)) ? 2 : -1;
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (i2 != -1) {
            i = i2;
        } else {
            if (splitScreenTransitions.mPendingEnter == null || this.mLastTransactionType != 2) {
                Slog.w("StageCoordinator", "maximizeSplitTask: failed, cannot find " + windowContainerToken);
                return;
            }
            Slog.d("StageCoordinator", "maximizeSplitTask: during splitTransition");
        }
        if (windowContainerTransaction == null) {
            windowContainerTransaction = new WindowContainerTransaction();
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        if (runningTaskInfo != null) {
            windowContainerTransaction.setDoNotPip(runningTaskInfo.token);
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            prepareSplitMaximizeChangeTransition(windowContainerTransaction, i);
        }
        prepareExitSplitScreen(i, windowContainerTransaction, true);
        splitScreenTransitions.startDismissTransition(windowContainerTransaction, this, i, 2);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (iBinder2 != splitScreenTransitions.mAnimatingTransition) {
            return;
        }
        OneShotRemoteHandler oneShotRemoteHandler = splitScreenTransitions.mActiveRemoteHandler;
        if (oneShotRemoteHandler != null) {
            oneShotRemoteHandler.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, transitionFinishCallback);
            return;
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                if (!change.hasFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) || change.getMode() != 2) {
                    z = false;
                    break;
                }
            }
            z = true;
            if (z) {
                Log.w("SplitScreenTransitions", "mergeAnimation: keep current transition, new=" + transitionInfo);
                return;
            }
        }
        ArrayList arrayList = splitScreenTransitions.mAnimations;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Animator animator = (Animator) arrayList.get(size);
            ShellExecutor shellExecutor = splitScreenTransitions.mTransitions.mAnimExecutor;
            Objects.requireNonNull(animator);
            ((HandlerExecutor) shellExecutor).execute(new SplitScreenTransitions$$ExternalSyntheticLambda1(animator, 1));
        }
    }

    public final void moveSplitToFreeform(WindowContainerToken windowContainerToken, Rect rect, boolean z) {
        char c;
        Rect rect2;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setWindowingMode(windowContainerToken, 5);
        if (rect != null) {
            windowContainerTransaction.setBounds(windowContainerToken, rect);
        }
        int i = z ? 4 : 1;
        this.mMovingToFreeformTaskToken = windowContainerToken;
        try {
            if (this.mMainStage.containsToken(windowContainerToken)) {
                c = 0;
            } else {
                if (!this.mSideStage.containsToken(windowContainerToken)) {
                    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mCellStage.containsToken(windowContainerToken)) {
                        c = 2;
                    }
                    rect2 = null;
                    if (rect2 != null && !rect2.isEmpty()) {
                        windowContainerTransaction.setChangeTransitStartBounds(windowContainerToken, rect2);
                    }
                    windowContainerTransaction.setChangeTransitMode(windowContainerToken, i, "split_to_freeform");
                    dismissSplitTask(windowContainerToken, windowContainerTransaction, z);
                }
                c = 1;
            }
            if (c == 0) {
                rect2 = getMainStageBounds();
            } else if (c == 1) {
                rect2 = getSideStageBounds();
            } else {
                if (c == 2) {
                    SplitLayout splitLayout = this.mSplitLayout;
                    splitLayout.getClass();
                    rect2 = new Rect(splitLayout.mBounds3);
                }
                rect2 = null;
            }
            if (rect2 != null) {
                windowContainerTransaction.setChangeTransitStartBounds(windowContainerToken, rect2);
            }
            windowContainerTransaction.setChangeTransitMode(windowContainerToken, i, "split_to_freeform");
            dismissSplitTask(windowContainerToken, windowContainerTransaction, z);
        } finally {
            this.mMovingToFreeformTaskToken = null;
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        if (i != 0) {
            return;
        }
        this.mDisplayController.mChangeController.mDisplayChangeListener.add(new DisplayChangeController.OnDisplayChangingListener() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda9
            /* JADX WARN: Code restructure failed: missing block: B:21:0x0066, code lost:
            
                if ((r6.mSplitLayout.mSplitScreenFeasibleMode == 1) == false) goto L31;
             */
            /* JADX WARN: Code restructure failed: missing block: B:50:0x00d5, code lost:
            
                if ((r6.mSplitLayout.mSplitScreenFeasibleMode == 1) != false) goto L61;
             */
            /* JADX WARN: Removed duplicated region for block: B:25:0x0075  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x0084  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x0089  */
            /* JADX WARN: Removed duplicated region for block: B:39:0x00bf  */
            /* JADX WARN: Removed duplicated region for block: B:52:0x00df  */
            /* JADX WARN: Removed duplicated region for block: B:61:0x0086  */
            @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onDisplayChange(int i2, int i3, int i4, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
                boolean z;
                StageCoordinator stageCoordinator = StageCoordinator.this;
                if (i2 != 0) {
                    stageCoordinator.getClass();
                    return;
                }
                if (stageCoordinator.mMainStage.mIsActive) {
                    long j = stageCoordinator.mSeqForAsyncTransaction + 1;
                    stageCoordinator.mSeqForAsyncTransaction = j;
                    long max = Math.max(j, 0L);
                    stageCoordinator.mSeqForAsyncTransaction = max;
                    windowContainerTransaction.setSeqForAsyncTransaction(max);
                    SplitScreenTransitions splitScreenTransitions = stageCoordinator.mSplitTransitions;
                    SplitScreenTransitions.EnterSession enterSession = splitScreenTransitions.mPendingEnter;
                    Context context = stageCoordinator.mContext;
                    if (enterSession != null && i3 != i4) {
                        stageCoordinator.mDisplayController.getDisplayLayout(context.getDisplayId()).rotateTo(i4, context.getResources());
                    }
                    boolean isLandscape = stageCoordinator.isLandscape();
                    SplitLayout splitLayout = stageCoordinator.mSplitLayout;
                    int i5 = splitLayout.mRotation;
                    splitLayout.rotateTo(i4);
                    if (displayAreaInfo != null) {
                        stageCoordinator.mSplitLayout.updateConfiguration(displayAreaInfo.configuration);
                    }
                    if (CoreRune.MW_MULTI_SPLIT && !MultiWindowUtils.isInSubDisplay(context)) {
                        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
                        }
                        z = false;
                        if (z && (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !stageCoordinator.isMultiSplitActive())) {
                            if (!(stageCoordinator.mTopStageAfterFoldDismiss == -1)) {
                                SplitLayout splitLayout2 = stageCoordinator.mSplitLayout;
                                splitLayout2.setDivideRatio(1.0f - ((splitLayout2.mDividePosition + splitLayout2.mDividerSize) / (SplitLayout.isLandscape(splitLayout2.mRootBounds) ? r4.width() : r4.height())), false, false);
                                stageCoordinator.setSideStagePosition(windowContainerTransaction, SplitScreenUtils.reverseSplitPosition(stageCoordinator.mSideStagePosition));
                                if (isLandscape != stageCoordinator.isLandscape()) {
                                    if (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || !MultiWindowUtils.isInSubDisplay(context)) {
                                        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
                                        }
                                        stageCoordinator.updateStagePositionIfNeeded(windowContainerTransaction);
                                    }
                                    stageCoordinator.updateSplitDivisionIfNeeded();
                                    stageCoordinator.updateStagePositionIfNeeded(windowContainerTransaction);
                                }
                                if (z && splitScreenTransitions.mPendingEnter != null && !stageCoordinator.isSplitScreenVisible()) {
                                    stageCoordinator.handleLayoutSizeChange(stageCoordinator.mSplitLayout, false);
                                }
                                stageCoordinator.sendOnBoundsChanged();
                            }
                        }
                        stageCoordinator.updateWindowBounds(stageCoordinator.mSplitLayout, windowContainerTransaction, false);
                        if (isLandscape != stageCoordinator.isLandscape()) {
                        }
                        if (z) {
                            stageCoordinator.handleLayoutSizeChange(stageCoordinator.mSplitLayout, false);
                        }
                        stageCoordinator.sendOnBoundsChanged();
                    }
                    if (i5 != i4 && (i4 == 3 || i5 == 3)) {
                        z = true;
                        if (z) {
                            if (!(stageCoordinator.mTopStageAfterFoldDismiss == -1)) {
                            }
                        }
                        stageCoordinator.updateWindowBounds(stageCoordinator.mSplitLayout, windowContainerTransaction, false);
                        if (isLandscape != stageCoordinator.isLandscape()) {
                        }
                        if (z) {
                        }
                        stageCoordinator.sendOnBoundsChanged();
                    }
                    z = false;
                    if (z) {
                    }
                    stageCoordinator.updateWindowBounds(stageCoordinator.mSplitLayout, windowContainerTransaction, false);
                    if (isLandscape != stageCoordinator.isLandscape()) {
                    }
                    if (z) {
                    }
                    stageCoordinator.sendOnBoundsChanged();
                }
            }
        });
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        int i2;
        int i3;
        if (i != 0) {
            return;
        }
        SplitLayout splitLayout = this.mSplitLayout;
        if (splitLayout != null) {
            if ((splitLayout.mDensity != configuration.densityDpi) && this.mMainStage.mIsActive && splitLayout.updateConfiguration(configuration) && Transitions.ENABLE_SHELL_TRANSITIONS) {
                this.mSplitLayout.update(null);
                onLayoutSizeChanged(this.mSplitLayout, null);
            }
        }
        if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
            updateCornerRadiusForStages(null);
        }
        Configuration configuration2 = this.mLastConfiguration;
        if ((configuration2.updateFrom(configuration) & 4) != 0) {
            this.mSplitUnsupportedToast.setText(R.string.dock_non_resizeble_failed_to_dock_text);
        }
        if (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || (i2 = configuration.semDisplayDeviceType) != 0 || i2 == configuration2.semDisplayDeviceType || isSplitScreenVisible() || (i3 = this.mLastMainSplitDivision) == this.mSplitDivision) {
            return;
        }
        if (!(CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && this.mIsVideoControls) && setSplitDivision(i3, false)) {
            Slog.d("StageCoordinator", "Restore main Split Division=" + this.mLastMainSplitDivision);
            this.mLastMainSplitDivision = -1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1, types: [com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda11] */
    public final void onDoubleTappedDivider() {
        final SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        Rect rect = this.mTempRect1;
        rect.setEmpty();
        int i = this.mSideStagePosition;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        StageTaskListener stageTaskListener3 = i == 0 ? stageTaskListener : stageTaskListener2;
        SurfaceControl surfaceControl = stageTaskListener3.mRootLeash;
        final SurfaceControl takeScreenshot = ScreenshotUtils.takeScreenshot(acquire, surfaceControl, surfaceControl, rect, 2147483646);
        StageTaskListener stageTaskListener4 = this.mSideStagePosition == 0 ? stageTaskListener2 : stageTaskListener;
        SurfaceControl surfaceControl2 = stageTaskListener4.mRootLeash;
        final SurfaceControl takeScreenshot2 = ScreenshotUtils.takeScreenshot(acquire, surfaceControl2, surfaceControl2, rect, 2147483646);
        this.mSplitLayout.splitSwitching(acquire, stageTaskListener3.mRootLeash, stageTaskListener4.mRootLeash, new Consumer() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final StageCoordinator stageCoordinator = StageCoordinator.this;
                final SurfaceControl surfaceControl3 = takeScreenshot;
                final SurfaceControl surfaceControl4 = takeScreenshot2;
                final SurfaceControl.Transaction transaction = acquire;
                final Rect rect2 = (Rect) obj;
                stageCoordinator.getClass();
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                stageCoordinator.setSideStagePosition(windowContainerTransaction, SplitScreenUtils.reverseSplitPosition(stageCoordinator.mSideStagePosition));
                SyncTransactionQueue syncTransactionQueue = stageCoordinator.mSyncQueue;
                syncTransactionQueue.queue(windowContainerTransaction);
                syncTransactionQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda15
                    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                    public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                        final StageCoordinator stageCoordinator2 = stageCoordinator;
                        stageCoordinator2.updateSurfaceBounds(stageCoordinator2.mSplitLayout, transaction2, false);
                        Rect rect3 = rect2;
                        float f = -rect3.left;
                        float f2 = -rect3.top;
                        final SurfaceControl surfaceControl5 = surfaceControl3;
                        transaction2.setPosition(surfaceControl5, f, f2);
                        float f3 = rect3.left;
                        float f4 = rect3.top;
                        final SurfaceControl surfaceControl6 = surfaceControl4;
                        transaction2.setPosition(surfaceControl6, f3, f4);
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
                        final SurfaceControl.Transaction transaction3 = transaction;
                        ofFloat.addUpdateListener(new StageCoordinator$$ExternalSyntheticLambda10(transaction3, surfaceControl5, surfaceControl6));
                        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.6
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                transaction3.remove(surfaceControl5);
                                transaction3.remove(surfaceControl6);
                                transaction3.apply();
                                StageCoordinator.this.mTransactionPool.release(transaction3);
                            }
                        });
                        ofFloat.start();
                    }
                });
            }
        });
        if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1053080117, 0, "Switch split position: %s", "double tap");
        }
        int mainStagePosition = getMainStagePosition();
        int topChildTaskUid = stageTaskListener2.getTopChildTaskUid();
        int i2 = this.mSideStagePosition;
        int topChildTaskUid2 = stageTaskListener.getTopChildTaskUid();
        boolean isLandscape = this.mSplitLayout.isLandscape();
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        if (splitscreenEventLogger.mLoggerSessionId == null) {
            return;
        }
        splitscreenEventLogger.updateMainStageState(SplitscreenEventLogger.getMainStagePositionFromSplitPosition(mainStagePosition, isLandscape), topChildTaskUid);
        splitscreenEventLogger.updateSideStageState(SplitscreenEventLogger.getSideStagePositionFromSplitPosition(i2, isLandscape), topChildTaskUid2);
        FrameworkStatsLog.write(388, 5, 0, 0, 0.0f, splitscreenEventLogger.mLastMainStagePosition, splitscreenEventLogger.mLastMainStageUid, splitscreenEventLogger.mLastSideStagePosition, splitscreenEventLogger.mLastSideStageUid, 0, splitscreenEventLogger.mLoggerSessionId.getId());
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0060, code lost:
    
        if (isSplitScreenVisible() != false) goto L31;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006a  */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.wm.shell.splitscreen.SideStage, com.android.wm.shell.splitscreen.StageTaskListener] */
    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.wm.shell.splitscreen.StageCoordinator, com.android.wm.shell.transition.Transitions$TransitionHandler] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onFoldedStateChanged(boolean z) {
        int i = -1;
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
            this.mIsFolded = z;
            this.mTopStageAfterFoldDismiss = -1;
        }
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && z && !isInSubDisplay()) {
            Slog.d("StageCoordinator", "Save main Split Division=" + this.mSplitDivision);
            this.mLastMainSplitDivision = this.mSplitDivision;
        }
        if (!z) {
            return;
        }
        MainStage mainStage = this.mMainStage;
        if (!mainStage.mIsActive) {
            return;
        }
        boolean isFocused = mainStage.isFocused();
        ?? r3 = this.mSideStage;
        if (!isFocused) {
            if (r3.isFocused()) {
                i = 1;
            } else if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && this.mCellStage.isFocused()) {
                i = 2;
            } else if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
            }
            if (!CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
                this.mTopStageAfterFoldDismiss = i;
                return;
            }
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                prepareExitSplitScreen(i, windowContainerTransaction, true);
                this.mSplitTransitions.startDismissTransition(windowContainerTransaction, this, i, 3);
                return;
            } else {
                if (i != 0) {
                    mainStage = r3;
                }
                exitSplitScreen(mainStage, 3);
                return;
            }
        }
        i = 0;
        if (!CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
        }
    }

    public final void onFreeformToSplitRequested(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, int i, boolean z2, Rect rect, boolean z3, String str) {
        int i2;
        Bundle resolveStartCellStage;
        int i3 = runningTaskInfo.taskId;
        WindowContainerToken windowContainerToken = runningTaskInfo.token;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setChangeTransitMode(windowContainerToken, 1, str);
        if (rect != null) {
            windowContainerTransaction.setChangeTransitStartBounds(windowContainerToken, rect);
        }
        if (z) {
            int i4 = i & 64;
            startTaskAndIntent(i3, MultiWindowUtils.getEdgeAllAppsActivityIntent(runningTaskInfo.baseIntent.getComponent(), runningTaskInfo.userId, i3), (i == 0 || !(i4 != 0 || (i & 32) != 0)) ? 1 : 0, ((i & 16) == 0 && i4 == 0) ? 0 : 1, windowContainerTransaction);
            return;
        }
        int i5 = ((i != 0 || z2) && (i == 16 || i == 8)) ? 0 : 1;
        boolean z4 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        MainStage mainStage = this.mMainStage;
        if (!z4 || MultiWindowUtils.isInSubDisplay(this.mContext) || !isSplitScreenVisible() || (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && this.mIsVideoControls)) {
            windowContainerTransaction.startTask(i3, i != 0 ? resolveStartStage(-1, i5, null, null, (i == 8 || i == 32) ? 0 : 1) : resolveStartStage(-1, i5, null, null));
            if (isSplitScreenVisible()) {
                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                return;
            }
            int i6 = mainStage.mIsActive ? 1005 : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
            prepareEnterSplitScreen(windowContainerTransaction, null, i5, false);
            this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, i6, false);
            return;
        }
        if (z2) {
            int splitDivision = getSplitDivision();
            i2 = getSideStageWinConfigPosition();
            if (splitDivision == 1) {
                if (i == 8) {
                    i2 |= 32;
                } else if (i == 32) {
                    i2 |= 8;
                }
            } else if (i == 16) {
                i2 |= 64;
            } else if (i == 64) {
                i2 |= 16;
            }
            setSideStagePosition(i5, splitDivision == 1 ? 0 : 1, windowContainerTransaction, false);
            if (z3) {
                WindowContainerToken windowContainerToken2 = mainStage.getTopRunningTaskInfo().token;
                WindowContainerToken windowContainerToken3 = this.mSideStage.getTopRunningTaskInfo().token;
                windowContainerTransaction.setChangeTransitMode(windowContainerToken2, 4, "natural_swtiching");
                windowContainerTransaction.setChangeTransitMode(windowContainerToken3, 4, "natural_swtiching");
            }
            StageTaskListener stageTaskListenerByStageType = getStageTaskListenerByStageType(1);
            StageTaskListener stageTaskListenerByStageType2 = getStageTaskListenerByStageType(2);
            WindowContainerToken windowContainerToken4 = stageTaskListenerByStageType2.mRootTaskInfo.token;
            StageTaskListener.RunningTaskInfoList runningTaskInfoList = stageTaskListenerByStageType.mChildrenTaskInfo;
            int size = runningTaskInfoList.size();
            for (int i7 = 0; i7 < size; i7++) {
                windowContainerTransaction.reparent(((ActivityManager.RunningTaskInfo) runningTaskInfoList.valueAt(i7)).token, windowContainerToken4, true);
            }
            WindowContainerToken windowContainerToken5 = stageTaskListenerByStageType.mRootTaskInfo.token;
            StageTaskListener.RunningTaskInfoList runningTaskInfoList2 = stageTaskListenerByStageType2.mChildrenTaskInfo;
            int size2 = runningTaskInfoList2.size();
            for (int i8 = 0; i8 < size2; i8++) {
                windowContainerTransaction.reparent(((ActivityManager.RunningTaskInfo) runningTaskInfoList2.valueAt(i8)).token, windowContainerToken5, true);
            }
            resolveStartCellStage = resolveStartStage(1, i5, null, null);
        } else {
            int multiSplitLaunchPosition = i != 0 ? i : StageUtils.getMultiSplitLaunchPosition(this.mCellStageWindowConfigPosition, isVerticalDivision());
            i2 = multiSplitLaunchPosition;
            resolveStartCellStage = resolveStartCellStage(-1, multiSplitLaunchPosition, null, null);
        }
        windowContainerTransaction.startTask(i3, resolveStartCellStage);
        if (isMultiSplitScreenVisible()) {
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
            return;
        }
        this.mSplitLayout.setCellDividerRatio(0.5f, i2, true, false);
        prepareEnterMultiSplitScreen(i2, windowContainerTransaction);
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && !z3) {
            applyCellHostResizeTransition(windowContainerTransaction);
        }
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, 1100, false);
    }

    public final void onLayoutPositionChanging(SplitLayout splitLayout) {
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction acquire = transactionPool.acquire();
        acquire.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        updateSurfaceBounds(splitLayout, acquire, false);
        acquire.apply();
        transactionPool.release(acquire);
    }

    public final void onLayoutSizeChanged(SplitLayout splitLayout, WindowContainerTransaction windowContainerTransaction) {
        int i = 0;
        this.mShowDecorImmediately = false;
        if (!CoreRune.MW_SPLIT_SHELL_TRANSITION || windowContainerTransaction == null) {
            windowContainerTransaction = new WindowContainerTransaction();
        }
        updateStagePositionIfNeeded(windowContainerTransaction);
        boolean updateWindowBounds = updateWindowBounds(splitLayout, windowContainerTransaction, false);
        SideStage sideStage = this.mSideStage;
        MainStage mainStage = this.mMainStage;
        TransactionPool transactionPool = this.mTransactionPool;
        if (!updateWindowBounds) {
            SurfaceControl.Transaction acquire = transactionPool.acquire();
            mainStage.onResized(acquire);
            sideStage.onResized(acquire);
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                this.mCellStage.onResized(acquire);
            }
            transactionPool.release(acquire);
            return;
        }
        sendOnBoundsChanged();
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mSplitLayout.setDividerInteractive("onSplitResizeStart", false, false);
            StageCoordinator$$ExternalSyntheticLambda1 stageCoordinator$$ExternalSyntheticLambda1 = new StageCoordinator$$ExternalSyntheticLambda1(this, i);
            SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
            SplitScreenTransitions.TransitSession transitSession = splitScreenTransitions.mPendingResize;
            if (transitSession != null) {
                transitSession.mCanceled = true;
                transitSession.mFinishedCallback = null;
                splitScreenTransitions.mAnimations.clear();
                splitScreenTransitions.onFinish(null, null);
            }
            splitScreenTransitions.mPendingResize = new SplitScreenTransitions.TransitSession(splitScreenTransitions, splitScreenTransitions.mTransitions.startTransition(6, windowContainerTransaction, this), null, stageCoordinator$$ExternalSyntheticLambda1);
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -522947324, 0, "  splitTransition  deduced Resize split screen", null);
            }
        } else {
            SurfaceControl.Transaction acquire2 = transactionPool.acquire();
            mainStage.getClass();
            sideStage.getClass();
            transactionPool.release(acquire2);
            SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
            syncTransactionQueue.queue(windowContainerTransaction);
            syncTransactionQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda2(this, splitLayout, i));
        }
        float dividerPositionAsFraction = this.mSplitLayout.getDividerPositionAsFraction();
        SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
        if (splitscreenEventLogger.mLoggerSessionId != null && dividerPositionAsFraction > 0.0f && dividerPositionAsFraction < 1.0f) {
            if (Float.compare(splitscreenEventLogger.mLastSplitRatio, dividerPositionAsFraction) != 0) {
                splitscreenEventLogger.mLastSplitRatio = dividerPositionAsFraction;
                i = 1;
            }
            if (i == 0) {
                return;
            }
            FrameworkStatsLog.write(388, 4, 0, 0, splitscreenEventLogger.mLastSplitRatio, 0, 0, 0, 0, 0, splitscreenEventLogger.mLoggerSessionId.getId());
        }
    }

    public final void onLayoutSizeChanging(int i, int i2, SplitLayout splitLayout) {
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction acquire = transactionPool.acquire();
        acquire.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
        updateSurfaceBounds(splitLayout, acquire, true);
        int i3 = this.mSideStagePosition;
        Rect rect = this.mTempRect1;
        if (i3 == 0) {
            rect.set(this.mSplitLayout.mBounds2);
        } else {
            rect.set(this.mSplitLayout.mBounds1);
        }
        int i4 = this.mSideStagePosition;
        Rect rect2 = this.mTempRect2;
        if (i4 == 0) {
            rect2.set(this.mSplitLayout.mBounds1);
        } else {
            rect2.set(this.mSplitLayout.mBounds2);
        }
        this.mMainStage.getClass();
        this.mSideStage.getClass();
        acquire.apply();
        transactionPool.release(acquire);
    }

    public final void onRecentsInSplitAnimationCanceled() {
        this.mPausingTasks.clear();
        setSplitsVisible(false, false);
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            this.mIsRecentsInSplitAnimating = false;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
        this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
    }

    public final void onRecentsInSplitAnimationFinish(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        SplitBackgroundController splitBackgroundController;
        this.mPausingTasks.clear();
        int i = 0;
        while (true) {
            int size = windowContainerTransaction.getHierarchyOps().size();
            splitBackgroundController = this.mSplitBackgroundController;
            if (i >= size) {
                setSplitsVisible(false, false);
                windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
                splitBackgroundController.getClass();
                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                    this.mIsRecentsInSplitAnimating = false;
                    return;
                }
                return;
            }
            WindowContainerTransaction.HierarchyOp hierarchyOp = (WindowContainerTransaction.HierarchyOp) windowContainerTransaction.getHierarchyOps().get(i);
            IBinder container = hierarchyOp.getContainer();
            if (hierarchyOp.getType() == 1 && hierarchyOp.getToTop()) {
                MainStage mainStage = this.mMainStage;
                mainStage.getClass();
                if (mainStage.contains(new StageTaskListener$$ExternalSyntheticLambda1(container, 1))) {
                    break;
                }
                SideStage sideStage = this.mSideStage;
                sideStage.getClass();
                if (sideStage.contains(new StageTaskListener$$ExternalSyntheticLambda1(container, 1))) {
                    break;
                }
            }
            i++;
        }
        updateSurfaceBounds(this.mSplitLayout, transaction, false);
        transaction.reparent(this.mSplitLayout.getDividerLeash(), this.mRootTaskLeash);
        setDividerVisibility(true, transaction);
        if (splitBackgroundController.canShow()) {
            splitBackgroundController.updateBackgroundVisibility(true, false);
        }
    }

    public final void onRemoteAnimationFinished(RemoteAnimationTarget[] remoteAnimationTargetArr) {
        this.mIsDividerRemoteAnimating = false;
        this.mShouldUpdateRecents = true;
        clearRequestIfPresented();
        MainStage mainStage = this.mMainStage;
        if (mainStage.getChildCount() != 0) {
            SideStage sideStage = this.mSideStage;
            if (sideStage.getChildCount() != 0) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                mainStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
                sideStage.evictNonOpeningChildren(remoteAnimationTargetArr, windowContainerTransaction);
                this.mSyncQueue.queue(windowContainerTransaction);
                return;
            }
        }
        ((HandlerExecutor) this.mMainExecutor).execute(new StageCoordinator$$ExternalSyntheticLambda3(this, 6));
        this.mSplitUnsupportedToast.show();
    }

    public void onRootTaskAppeared() {
        if (this.mRootTaskInfo != null && this.mMainStageListener.mHasRootTask && this.mSideStageListener.mHasRootTask) {
            if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || this.mCellStageListener.mHasRootTask) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                MainStage mainStage = this.mMainStage;
                windowContainerTransaction.reparent(mainStage.mRootTaskInfo.token, this.mRootTaskInfo.token, true);
                SideStage sideStage = this.mSideStage;
                windowContainerTransaction.reparent(sideStage.mRootTaskInfo.token, this.mRootTaskInfo.token, true);
                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                    windowContainerTransaction.reparent(this.mCellStage.mRootTaskInfo.token, this.mRootTaskInfo.token, true);
                }
                windowContainerTransaction.setAdjacentRoots(mainStage.mRootTaskInfo.token, sideStage.mRootTaskInfo.token);
                windowContainerTransaction.setLaunchAdjacentFlagRoot(sideStage.mRootTaskInfo.token);
                setRootForceTranslucent(windowContainerTransaction, true);
                Rect rect = this.mSplitLayout.mInvisibleBounds;
                Rect rect2 = this.mTempRect1;
                rect2.set(rect);
                windowContainerTransaction.setBounds(sideStage.mRootTaskInfo.token, rect2);
                SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
                syncTransactionQueue.queue(windowContainerTransaction);
                syncTransactionQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda0(this, 0));
            }
        }
    }

    public final void onRootTaskVanished() {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        if (runningTaskInfo != null) {
            windowContainerTransaction.clearLaunchAdjacentFlagRoot(runningTaskInfo.token);
        }
        applyExitSplitScreen(null, windowContainerTransaction, 6);
        this.mDisplayInsetsController.removeInsetsChangedListener(this.mDisplayId, this.mSplitLayout);
    }

    public final void onSnappedToDismiss(int i, boolean z, boolean z2) {
        if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
            CoreSaLogger.logForAdvanced("1005", "Move divider");
        }
        boolean z3 = true;
        boolean z4 = false;
        int i2 = (!z ? this.mSideStagePosition == 0 : this.mSideStagePosition == 1) ? 0 : 1;
        StageTaskListener stageTaskListener = this.mMainStage;
        SideStage sideStage = this.mSideStage;
        StageTaskListener stageTaskListener2 = i2 != 0 ? stageTaskListener : sideStage;
        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
            exitSplitScreen(stageTaskListener2, i);
            return;
        }
        boolean z5 = CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING;
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (!z5 || !this.mDividerResizeController.mIsResizing || !isMultiSplitActive()) {
            int i3 = i2 ^ 1;
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (z2) {
                prepareSplitDismissChangeTransition(windowContainerTransaction, i2, null, false);
            }
            windowContainerTransaction.setBounds(stageTaskListener2.mRootTaskInfo.token, (Rect) null);
            windowContainerTransaction.setAppBounds(stageTaskListener2.mRootTaskInfo.token, (Rect) null);
            windowContainerTransaction.setSmallestScreenWidthDp(stageTaskListener2.mRootTaskInfo.token, 0);
            prepareExitSplitScreen(i3, windowContainerTransaction, true);
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
            if (runningTaskInfo != null) {
                windowContainerTransaction.setDoNotPip(runningTaskInfo.token);
            }
            splitScreenTransitions.startDismissTransition(windowContainerTransaction, this, i3, 4);
            return;
        }
        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mRootTaskInfo;
        if (runningTaskInfo2 != null) {
            windowContainerTransaction2.setDoNotPip(runningTaskInfo2.token);
        }
        DividerResizeController dividerResizeController = this.mDividerResizeController;
        DividerView dividerView = dividerResizeController.mDividerView;
        if (dividerView != null && dividerView.mIsCellDivider) {
            int cellHostStageType = getCellHostStageType();
            int cellSide = this.mSplitLayout.getCellSide();
            int stageWinConfigPositionByType = (cellSide == 1 || cellSide == 3 ? 40 : 80) & getStageWinConfigPositionByType(cellHostStageType);
            if (!z ? (stageWinConfigPositionByType & 24) == 0 : (stageWinConfigPositionByType & 96) == 0) {
                z3 = false;
            }
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("onSnappedToDismissMultiSplit: cell divider action, hostStageType=", cellHostStageType, ", hostPos=");
            m1m.append(WindowConfiguration.stagePositionToString(stageWinConfigPositionByType));
            m1m.append(", dismissToHostStage=");
            m1m.append(z3);
            Slog.d("StageCoordinator", m1m.toString());
            reparentCellToMainOrSide(windowContainerTransaction2, this.mCellStage.mHost, z3);
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, cellHostStageType, 2, true);
            return;
        }
        int i4 = dividerResizeController.mHalfSplitStageType;
        if (i4 != 0) {
            stageTaskListener = sideStage;
        }
        int stageWinConfigPositionByType2 = getStageWinConfigPositionByType(i4);
        if (!z ? (stageWinConfigPositionByType2 & 24) != 0 : (stageWinConfigPositionByType2 & 96) != 0) {
            z4 = true;
        }
        StringBuilder m1m2 = AbstractC0000x2c234b15.m1m("onSnappedToDismissMultiSplit: halfStageType=", i4, ", halfPos=");
        m1m2.append(WindowConfiguration.stagePositionToString(stageWinConfigPositionByType2));
        m1m2.append(", dismissToHalfStage=");
        m1m2.append(z4);
        Slog.d("StageCoordinator", m1m2.toString());
        if (z4) {
            reparentCellToMainOrSide(windowContainerTransaction2, stageTaskListener, true);
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, i4, 2, true);
        } else {
            prepareExitSplitScreen(i4, windowContainerTransaction2, true);
            splitScreenTransitions.startDismissTransition(windowContainerTransaction2, this, i4, 2);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        if (this.mRootTaskInfo != null || runningTaskInfo.hasParentTask()) {
            throw new IllegalArgumentException(this + "\n Unknown task appeared: " + runningTaskInfo);
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
            SplitLayout splitLayout = new SplitLayout("StageCoordinatorSplitDivider", this.mContext, this.mRootTaskInfo.configuration, this, this.mParentContainerCallbacks, this.mDisplayController, this.mDisplayImeController, this.mTaskOrganizer, 2, this.mSplitDivision);
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
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mRootTaskInfo;
        if (runningTaskInfo2 == null || runningTaskInfo2.taskId != runningTaskInfo.taskId) {
            throw new IllegalArgumentException(this + "\n Unknown task info changed: " + runningTaskInfo);
        }
        SplitLayout splitLayout = this.mSplitLayout;
        boolean z = (splitLayout == null || new Rect(splitLayout.mRootBounds).equals(runningTaskInfo.getConfiguration().windowConfiguration.getBounds())) ? false : true;
        boolean z2 = !this.mIsFolded ? runningTaskInfo.configuration.semDisplayDeviceType != 5 : runningTaskInfo.configuration.semDisplayDeviceType != 0;
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && z && z2 && isSplitScreenVisible()) {
            Slog.d("StageCoordinator", "onTaskInfoChanged ignore - device type is differents folded state.");
            return;
        }
        this.mRootTaskInfo = runningTaskInfo;
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && this.mTopStageAfterFoldDismiss != -1) {
            this.mTmpConfigAfterFoldDismiss = runningTaskInfo.configuration;
            return;
        }
        SplitLayout splitLayout2 = this.mSplitLayout;
        boolean z3 = splitLayout2 != null && splitLayout2.updateConfiguration(runningTaskInfo.configuration);
        if (z3 && !Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mIsDividerRemoteAnimating = false;
            this.mSplitLayout.update(null);
            onLayoutSizeChanged(this.mSplitLayout, null);
        }
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && z3 && this.mRootTaskInfo.configuration.semDisplayDeviceType == 5 && this.mTopStageAfterFoldDismiss == -1) {
            updateSplitDivisionIfNeeded();
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.mRootTaskInfo == null) {
            throw new IllegalArgumentException(this + "\n Unknown task vanished: " + runningTaskInfo);
        }
        onRootTaskVanished();
        SplitLayout splitLayout = this.mSplitLayout;
        if (splitLayout != null) {
            splitLayout.release(null);
            this.mSplitLayout = null;
            Slog.w("StageCoordinator", "mSplitLayout is set to null");
        }
        this.mRootTaskInfo = null;
        this.mRootTaskLeash = null;
        this.mIsRootTranslucent = false;
        this.mSplitBackgroundController.detach();
    }

    public final void onTransitionAnimationComplete() {
        if (!this.mMainStage.mIsActive && !this.mIsExiting) {
            this.mSplitLayout.release(null);
            if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
                this.mTopStageAfterFoldDismiss = -1;
            }
        }
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && !this.mCellStage.mIsActive && !this.mIsExiting) {
            this.mSplitLayout.releaseCellDivider(null);
        }
        DividerResizeController dividerResizeController = this.mDividerResizeController;
        if (dividerResizeController != null) {
            Log.d("DividerResizeController", "onSyncAppsReady: SyncId=" + dividerResizeController.mSyncAppsId);
            dividerResizeController.stopWaitingForSyncAppsCallback("sync_apps_ready");
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            this.mIsRecentsInSplitAnimating = false;
        }
        this.mLastTransactionType = 0;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        if (splitScreenTransitions.isPendingEnter(iBinder)) {
            if (!z) {
                splitScreenTransitions.mStageCoordinator.finishEnterSplitScreen(transaction, false);
            }
            splitScreenTransitions.mPendingEnter.onConsumed();
            splitScreenTransitions.mPendingEnter = null;
            return;
        }
        if (splitScreenTransitions.isPendingDismiss(iBinder)) {
            splitScreenTransitions.mPendingDismiss.onConsumed();
            splitScreenTransitions.mPendingDismiss = null;
        } else if (splitScreenTransitions.isPendingResize(iBinder)) {
            splitScreenTransitions.mPendingResize.onConsumed();
            splitScreenTransitions.mPendingResize = null;
        }
    }

    public final void overrideStageCoordinatorRootConfig(WindowContainerTransaction windowContainerTransaction) {
        boolean z = MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED;
        boolean z2 = MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        if (runningTaskInfo == null || runningTaskInfo.getToken() == null) {
            return;
        }
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mContext.getDisplayId());
        if (displayLayout == null || !(z || z2)) {
            windowContainerTransaction.setAppBounds(this.mRootTaskInfo.getToken(), (Rect) null);
            windowContainerTransaction.setScreenSizeDp(this.mRootTaskInfo.getToken(), 0, 0);
            return;
        }
        int i = displayLayout.mWidth;
        int i2 = displayLayout.mHeight;
        Rect rect = this.mTempRect;
        rect.set(0, 0, i, i2);
        if (!MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
            if (MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED) {
                InsetsState insetsState = displayLayout.mInsetsState;
                int displayCutout = WindowInsets.Type.displayCutout();
                Rect rect2 = displayLayout.mTempRect;
                insetsState.calculateInsets(rect2, displayCutout, true);
                rect.inset(rect2);
            } else {
                rect.inset(displayLayout.mNonDecorInsets);
            }
        }
        windowContainerTransaction.setAppBounds(this.mRootTaskInfo.getToken(), rect);
        float density = displayLayout.density();
        displayLayout.getStableBounds(rect, true);
        windowContainerTransaction.setScreenSizeDp(this.mRootTaskInfo.getToken(), (int) ((rect.width() / density) + 0.5f), (int) ((rect.height() / density) + 0.5f));
    }

    public final void prepareActiveSplit(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, int i, boolean z, float f) {
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            setSplitsVisible(false, false);
        } else {
            this.mSplitLayout.init();
        }
        if (runningTaskInfo != null) {
            setSideStagePosition(windowContainerTransaction, i);
            SideStage sideStage = this.mSideStage;
            sideStage.getClass();
            windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0).setBounds(runningTaskInfo.token, (Rect) null);
            windowContainerTransaction.reparent(runningTaskInfo.token, sideStage.mRootTaskInfo.token, true);
        }
        this.mMainStage.activate(windowContainerTransaction, true);
        prepareSplitLayout(windowContainerTransaction, z, f);
    }

    public void prepareAndStartDismissTransition(int i, int i2, WindowContainerTransaction windowContainerTransaction, boolean z) {
        StageTaskListener stageTaskListenerByStageType = getStageTaskListenerByStageType(i);
        if (windowContainerTransaction == null) {
            windowContainerTransaction = new WindowContainerTransaction();
        }
        WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
        prepareSplitDismissChangeTransition(windowContainerTransaction2, i2, null, z);
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        if (runningTaskInfo != null) {
            windowContainerTransaction2.setDoNotPip(runningTaskInfo.token);
        }
        if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !isMultiSplitActive()) {
            prepareExitSplitScreen(i, windowContainerTransaction2, true);
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, i, 2);
        } else {
            if (i2 == 2) {
                prepareExitMultiSplitScreen(windowContainerTransaction2, false);
            } else {
                reparentCellToMainOrSide(windowContainerTransaction2, stageTaskListenerByStageType, true);
            }
            this.mSplitTransitions.startDismissTransition(windowContainerTransaction2, this, i, 2, true);
        }
    }

    public final void prepareBringSplit(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, int i, boolean z, float f) {
        if (runningTaskInfo != null) {
            windowContainerTransaction.startTask(runningTaskInfo.taskId, resolveStartStage(-1, i, null, windowContainerTransaction));
        }
        if (this.mAppPairStarted) {
            Slog.d("StageCoordinator", "When the App Pair is starting, it does not reparent on the mainStage.");
            prepareSplitLayout(windowContainerTransaction, z, 0.0f);
            return;
        }
        if (isSplitScreenVisible()) {
            return;
        }
        MainStage mainStage = this.mMainStage;
        mainStage.evictAllChildren(windowContainerTransaction, true);
        windowContainerTransaction.reparentTasks((WindowContainerToken) null, mainStage.mRootTaskInfo.token, SplitScreenConstants.CONTROLLED_WINDOWING_MODES, SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, true, true);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            CellStage cellStage = this.mCellStage;
            if (cellStage.hasChild() && CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                cellStage.evictAllChildren(windowContainerTransaction, false);
            }
            if (cellStage.mIsActive) {
                prepareExitMultiSplitScreen(windowContainerTransaction, false);
            }
        }
        prepareSplitLayout(windowContainerTransaction, z, f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void prepareDismissAnimation(int i, int i2, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, boolean z) {
        boolean z2;
        SideStage sideStage = this.mSideStage;
        MainStage mainStage = this.mMainStage;
        if (i == -1) {
            if (mainStage.getChildCount() != 0) {
                StringBuilder sb = new StringBuilder();
                int i3 = 0;
                while (i3 < mainStage.getChildCount()) {
                    sb.append(i3 != 0 ? ", " : "");
                    sb.append(mainStage.mChildrenTaskInfo.keyAt(i3));
                    i3++;
                }
                Log.w("StageCoordinator", "Expected onTaskVanished on " + mainStage + " to have been called with [" + sb.toString() + "] before startAnimation().");
            }
            if (sideStage.getChildCount() != 0) {
                StringBuilder sb2 = new StringBuilder();
                int i4 = 0;
                while (i4 < sideStage.getChildCount()) {
                    sb2.append(i4 != 0 ? ", " : "");
                    sb2.append(sideStage.mChildrenTaskInfo.keyAt(i4));
                    i4++;
                }
                Log.w("StageCoordinator", "Expected onTaskVanished on " + sideStage + " to have been called with [" + sb2.toString() + "] before startAnimation().");
            }
        }
        ArrayMap arrayMap = new ArrayMap();
        for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (taskInfo != null && (getStageOfTask(taskInfo) != null || getSplitItemPosition(change.getLastParent()) != -1)) {
                arrayMap.put(Integer.valueOf(taskInfo.taskId), change.getLeash());
            }
        }
        if (shouldBreakPairedTaskInRecents(i2)) {
            z2 = false;
            this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda17(arrayMap, 0 == true ? 1 : 0));
        } else {
            z2 = false;
        }
        boolean z3 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
        CellStage cellStage = this.mCellStage;
        if (z3 && z) {
            setCellSplitVisible(z2);
            setCellDividerVisibility(transaction, z2);
            transaction.setCrop(cellStage.mRootLeash, null);
            transaction2.hide(cellStage.mDimLayer);
            return;
        }
        this.mSplitRequest = null;
        setSplitsVisible(z2, z2);
        transaction.setCrop(mainStage.mRootLeash, null);
        transaction.setCrop(sideStage.mRootLeash, null);
        if (i != -1) {
            transaction.hide(i == 0 ? sideStage.mRootLeash : mainStage.mRootLeash);
            transaction.setPosition(i == 0 ? mainStage.mRootLeash : sideStage.mRootLeash, 0.0f, 0.0f);
        } else {
            for (int size = arrayMap.keySet().size() - 1; size >= 0; size--) {
                transaction2.hide((SurfaceControl) arrayMap.valueAt(size));
            }
        }
        if (i == -1) {
            this.mLogger.logExit(i2, -1, 0, -1, 0, this.mSplitLayout.isLandscape());
        } else {
            logExitToStage(i2, i == 0);
        }
        setDividerVisibility(false, transaction);
        transaction2.hide(mainStage.mDimLayer);
        transaction2.hide(sideStage.mDimLayer);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            setCellSplitVisible(false);
            setCellDividerVisibility(transaction, false);
            transaction.setCrop(cellStage.mRootLeash, null);
            transaction2.hide(cellStage.mDimLayer);
        }
    }

    public final void prepareEnterMultiSplitScreen(int i, WindowContainerTransaction windowContainerTransaction) {
        CellStage cellStage = this.mCellStage;
        if (cellStage.mIsActive) {
            return;
        }
        cellStage.mIsActive = true;
        if (i != 0) {
            setCellStageWindowConfigPosition(i, false);
        }
        this.mSplitLayout.updateCellStageWindowConfigPosition(this.mCellStageWindowConfigPosition);
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        updateStagePositionIfNeeded(windowContainerTransaction);
    }

    public final void prepareEnterSplitScreen(WindowContainerTransaction windowContainerTransaction, ActivityManager.RunningTaskInfo runningTaskInfo, int i, boolean z) {
        onSplitScreenEnter();
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, false);
        if (this.mMainStage.mIsActive) {
            prepareBringSplit(windowContainerTransaction, runningTaskInfo, i, z, 0.0f);
        } else {
            prepareActiveSplit(windowContainerTransaction, runningTaskInfo, i, z, 0.0f);
        }
    }

    public final void prepareExitMultiSplitScreen(WindowContainerTransaction windowContainerTransaction, boolean z) {
        CellStage cellStage = this.mCellStage;
        cellStage.mIsActive = false;
        cellStage.mHost = null;
        if (cellStage.mChildrenTaskInfo.size() != 0) {
            if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && z && !cellStage.mToSplit) {
                cellStage.adjustChildTaskWindowingModeIfNeeded(windowContainerTransaction);
            }
            windowContainerTransaction.reparentTasks(cellStage.mRootTaskInfo.token, (WindowContainerToken) null, SplitScreenConstants.CONTROLLED_WINDOWING_MODES_WHEN_ACTIVE, SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, z);
        }
        windowContainerTransaction.reorder(cellStage.mRootTaskInfo.token, false);
        cellStage.mToSplit = false;
        this.mCellStageWindowConfigPosition = 0;
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        updateStagePositionIfNeeded(windowContainerTransaction);
    }

    public final void prepareExitSplitScreen(int i, WindowContainerTransaction windowContainerTransaction, boolean z) {
        MainStage mainStage = this.mMainStage;
        if (mainStage.mIsActive) {
            boolean z2 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
            SideStage sideStage = this.mSideStage;
            if (z2) {
                sideStage.removeAllTasks(windowContainerTransaction, i == 1, z);
            } else {
                sideStage.removeAllTasks(windowContainerTransaction, i == 1, true);
            }
            mainStage.deactivate(windowContainerTransaction, i == 0);
            if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && this.mTopStageAfterFoldDismiss != -1) {
                this.mTopStageAfterFoldDismiss = -1;
                updateCoverDisplaySplitLayoutIfNeeded();
            }
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mCellStage.mIsActive) {
                prepareExitMultiSplitScreen(windowContainerTransaction, i == 2);
            }
        }
    }

    public final void prepareMultiSplitDismissChangeTransition(int i, WindowContainerTransaction windowContainerTransaction, boolean z) {
        String str;
        CellStage cellStage = this.mCellStage;
        if (cellStage.mHost == null || !cellStage.mIsActive) {
            Slog.w("StageCoordinator", "prepareMultiSplitDismissChangeTransition: failed, invalid cell host");
            return;
        }
        int stageType = getStageType(cellStage);
        int stageType2 = getStageType(cellStage.mHost);
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
        boolean anyMatch = windowContainerTransaction.getChanges().values().stream().anyMatch(new StageCoordinator$$ExternalSyntheticLambda7());
        if (!anyMatch) {
            windowContainerTransaction.setChangeTransitMode(stageToken3, 2, str);
        }
        Slog.d("StageCoordinator", "prepareMultiSplitDismissChangeTransition: dismiss=" + SplitScreen.stageTypeToString(i) + ", hasMovingToFreeform=" + anyMatch);
    }

    public void prepareSplitDismissChangeTransition(WindowContainerTransaction windowContainerTransaction, int i, TransitionRequestInfo transitionRequestInfo, boolean z) {
        StageTaskListener stageTaskListener;
        int i2;
        int i3;
        ActivityManager.RunningTaskInfo triggerTask;
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION && isMultiSplitActive()) {
            prepareMultiSplitDismissChangeTransition(i, windowContainerTransaction, z);
            return;
        }
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
        Context context = this.mContext;
        if (!z2) {
            if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && context.getResources().getConfiguration().isNewDexMode()) {
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
            i3 = 0;
        } else {
            i3 = stageTaskListener.hasAppsEdgeActivityOnTop() ? 2 : 1;
        }
        if (i3 != 0) {
            if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && context.getResources().getConfiguration().isNewDexMode()) {
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

    public final void prepareSplitLayout(WindowContainerTransaction windowContainerTransaction, boolean z, float f) {
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && z) {
            if (CoreRune.SAFE_DEBUG) {
                Slog.d("StageCoordinator", "prepareSplitLayout: reset resize anim, " + Debug.getCallers(10));
            }
            z = false;
        }
        if (f != 0.0f) {
            this.mSplitLayout.setDivideRatio(f, true, true);
        } else if (z) {
            this.mSplitLayout.setDividerAtBorder(this.mSideStagePosition == 0);
        } else {
            this.mSplitLayout.resetDividerPosition();
        }
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        if (z) {
            windowContainerTransaction.setSmallestScreenWidthDp(this.mMainStage.mRootTaskInfo.token, 0);
        }
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        setRootForceTranslucent(windowContainerTransaction, false);
        updateStagePositionIfNeeded(windowContainerTransaction);
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
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
        CellStage cellStage = this.mCellStage;
        if (cellStage.mHost == null || !cellStage.mIsActive) {
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
        arrayList.add(getStageToken(2));
        arrayList.remove(stageToken3);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            WindowContainerToken windowContainerToken = (WindowContainerToken) it.next();
            if (windowContainerToken != null) {
                windowContainerTransaction.setChangeTransitMode(windowContainerToken, 2, "maximize_multi_split");
            }
        }
        windowContainerTransaction.setChangeTransitMode(stageToken3, stageTaskListenerByStageType2.hasAppsEdgeActivityOnTop() ? 2 : 1, "maximize_multi_split");
        Slog.d("StageCoordinator", "prepareMultiSplitMaximizeChangeTransition: expand=" + SplitScreen.stageTypeToString(i));
    }

    public final void reparentCellToMainOrSide(WindowContainerTransaction windowContainerTransaction, StageTaskListener stageTaskListener, boolean z) {
        CellStage cellStage = this.mCellStage;
        if (cellStage.mIsActive) {
            WindowContainerToken windowContainerToken = stageTaskListener.mRootTaskInfo.token;
            if (cellStage.mChildrenTaskInfo.size() != 0) {
                windowContainerTransaction.reparentTasks(cellStage.mRootTaskInfo.token, windowContainerToken, SplitScreenConstants.CONTROLLED_WINDOWING_MODES_WHEN_ACTIVE, SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, z);
            }
            if (!stageTaskListener.equals(cellStage.mHost)) {
                int i = this.mSideStagePosition;
                int mainStagePosition = stageTaskListener.mStageType == 1 ? getMainStagePosition() : i;
                if (isVerticalDivision()) {
                    r4 = (this.mCellStageWindowConfigPosition & 16) != 0 ? 1 : 0;
                    if ((mainStagePosition == 0 && r4 == 0) || (mainStagePosition == 1 && r4 != 0)) {
                        i = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
                    }
                    r4 = 1;
                } else {
                    boolean z2 = (this.mCellStageWindowConfigPosition & 8) != 0;
                    if ((mainStagePosition == 0 && !z2) || (mainStagePosition == 1 && z2)) {
                        i = SplitScreenUtils.reverseSplitPosition(this.mSideStagePosition);
                    }
                }
                if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
                    SplitLayout splitLayout = this.mSplitLayout;
                    int i2 = splitLayout.mSplitDivision;
                    Rect rect = splitLayout.mRootBounds;
                    if (i2 == 0) {
                        splitLayout.mDividePosition = (int) (rect.width() * (splitLayout.mCellDividePosition / rect.height()));
                    } else {
                        splitLayout.mDividePosition = (int) (rect.height() * (splitLayout.mCellDividePosition / rect.width()));
                    }
                }
                setSideStagePosition(i, r4, windowContainerTransaction, true);
            }
            cellStage.mToSplit = true;
            prepareExitMultiSplitScreen(windowContainerTransaction, z);
        }
    }

    public final Bundle resolveStartCellStage(int i, int i2, Bundle bundle, WindowContainerTransaction windowContainerTransaction) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        int i3 = 1;
        CellStage cellStage = this.mCellStage;
        if (i != -1) {
            if (i == 0) {
                addActivityOptions(bundle, this.mMainStage);
            } else if (i == 1) {
                addActivityOptions(bundle, this.mSideStage);
            } else {
                if (i != 2) {
                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown stage=", i));
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
                addActivityOptions(bundle, cellStage);
            }
        } else if (i2 == 0 || !cellStage.mIsActive) {
            resolveStartCellStage(2, i2, bundle, windowContainerTransaction);
        } else {
            if (getMainStageWinConfigPosition() == i2) {
                i3 = 0;
            } else if (getSideStageWinConfigPosition() != i2) {
                i3 = this.mCellStageWindowConfigPosition == i2 ? 2 : -1;
            }
            if (i3 != -1) {
                resolveStartCellStage(i3, i2, bundle, windowContainerTransaction);
            } else {
                Slog.w("StageCoordinator", "No stage type nor split position specified to resolve start stage");
            }
        }
        return bundle;
    }

    public final Bundle resolveStartStage(int i, int i2, Bundle bundle, WindowContainerTransaction windowContainerTransaction) {
        return resolveStartStage(i, i2, bundle, windowContainerTransaction, -1);
    }

    public final boolean rotateMultiSplitWithTransition() {
        if (isSplitScreenVisible()) {
            MainStage mainStage = this.mMainStage;
            if (mainStage.mRootTaskInfo != null) {
                SideStage sideStage = this.mSideStage;
                if (sideStage.mRootTaskInfo != null) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
                    WindowContainerToken windowContainerToken = mainStage.mRootTaskInfo.token;
                    WindowContainerToken windowContainerToken2 = sideStage.mRootTaskInfo.token;
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
                    updateMultiSplitLayout(multiSplitLayoutInfo, true, windowContainerTransaction);
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
        ArrayList arrayList = (ArrayList) this.mListeners;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            SplitScreen.SplitScreenListener splitScreenListener = (SplitScreen.SplitScreenListener) arrayList.get(size);
            SplitLayout splitLayout = this.mSplitLayout;
            splitLayout.getClass();
            splitScreenListener.onSplitBoundsChanged(new Rect(splitLayout.mRootBounds), getMainStageBounds(), getSideStageBounds());
        }
    }

    public final void sendPairLoggingLocked() {
        ArrayList arrayList = this.mLastPackageNameList;
        ArrayList arrayList2 = this.mCurrentPackageNameList;
        if (!arrayList.equals(arrayList2)) {
            CoreSaLogger.logForAdvanced("1004", arrayList2.toString());
        }
        if (arrayList.size() == arrayList2.size() && arrayList.containsAll(arrayList2) && arrayList2.containsAll(arrayList)) {
            return;
        }
        CoreSaLogger.logForAdvanced(arrayList2.size() == 3 ? "1045" : "1044");
    }

    public final void sendSplitDirectionSaLogging() {
        if (this.mDividerVisible) {
            CoreSaLogger.logForAdvanced("1025", (isVerticalDivision() ? "Vertical split" : "Horizontal split") + " + " + (this.mContext.getResources().getConfiguration().orientation == 1 ? "Vertical device" : "Horizontal device"));
        }
    }

    public final void sendStatusToListener(SplitScreen.SplitScreenListener splitScreenListener) {
        splitScreenListener.onStagePositionChanged(0, getMainStagePosition());
        splitScreenListener.onStagePositionChanged(1, this.mSideStagePosition);
        splitScreenListener.onSplitVisibilityChanged(isSplitScreenVisible());
        SplitLayout splitLayout = this.mSplitLayout;
        if (splitLayout != null) {
            splitScreenListener.onSplitBoundsChanged(new Rect(splitLayout.mRootBounds), getMainStageBounds(), getSideStageBounds());
        }
        this.mSideStage.onSplitScreenListenerRegistered(splitScreenListener, 1);
        this.mMainStage.onSplitScreenListenerRegistered(splitScreenListener, 0);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            this.mCellStage.onSplitScreenListenerRegistered(splitScreenListener, 2);
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
        StageListenerImpl stageListenerImpl = this.mCellStageListener;
        stageListenerImpl.mVisible = z;
        stageListenerImpl.mHasChildren = z;
    }

    public final void setCellStageWindowConfigPosition(int i, boolean z) {
        if (this.mCellStageWindowConfigPosition != i || z) {
            this.mCellStageWindowConfigPosition = i;
            boolean isVerticalDivision = isVerticalDivision();
            int i2 = this.mSideStagePosition;
            StageTaskListener stageTaskListener = this.mSideStage;
            StageTaskListener stageTaskListener2 = this.mMainStage;
            if (i2 != 0 ? (!isVerticalDivision || (i & 32) == 0) && (isVerticalDivision || (i & 64) == 0) : (!isVerticalDivision || (i & 8) == 0) && (isVerticalDivision || (i & 16) == 0)) {
                stageTaskListener = stageTaskListener2;
            }
            this.mCellStage.mHost = stageTaskListener;
        }
    }

    public final void setDividerSizeIfNeeded(boolean z) {
        SplitLayout splitLayout = this.mSplitLayout;
        splitLayout.updateDividerConfig(splitLayout.mContext);
        splitLayout.updateBounds(splitLayout.mDividePosition);
        if (z) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setDisplayIdForChangeTransition(splitLayout.mContext.getDisplayId(), "update_flex_panel");
            ((StageCoordinator) splitLayout.mSplitLayoutHandler).onLayoutSizeChanged(splitLayout, windowContainerTransaction);
        }
    }

    public final void setDividerVisibility(boolean z, SurfaceControl.Transaction transaction) {
        if (z != this.mDividerVisible) {
            if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -632717827, 0, "Request to %s divider bar from %s.", z ? "show" : "hide", String.valueOf(Debug.getCaller()));
            }
            if (z && this.mKeyguardShowing) {
                if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1375648146, 0, "   Defer showing divider bar due to keyguard showing.", null);
                    return;
                }
                return;
            }
            this.mDividerVisible = z;
            ArrayList arrayList = (ArrayList) this.mListeners;
            int size = arrayList.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else {
                    ((SplitScreen.SplitScreenListener) arrayList.get(size)).onSplitVisibilityChanged(this.mDividerVisible);
                }
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
            if (!this.mIsDividerRemoteAnimating) {
                applyDividerVisibility(transaction);
            } else if (ShellProtoLogCache.WM_SHELL_SPLIT_SCREEN_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1118138034, 0, "   Skip animating divider bar due to it's remote animating.", null);
            }
        }
    }

    public final void setLayoutOffsetTargetFromIme(int i, SplitLayout splitLayout) {
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME && isMultiSplitActive()) {
            splitLayout.applyLayoutOffsetTargetForMultiSplit(windowContainerTransaction, i, getBottomStages());
        } else {
            ActivityManager.RunningTaskInfo runningTaskInfo = (this.mSideStagePosition == 0 ? this.mMainStage : this.mSideStage).mRootTaskInfo;
            Rect rect = splitLayout.mBounds2;
            if (i == 0) {
                windowContainerTransaction.setBounds(runningTaskInfo.token, rect);
                windowContainerTransaction.setScreenSizeDp(runningTaskInfo.token, 0, 0);
            } else {
                Rect rect2 = splitLayout.mTempRect;
                rect2.set(rect);
                rect2.offset(0, i);
                windowContainerTransaction.setBounds(runningTaskInfo.token, rect2);
                if (runningTaskInfo.configuration.windowConfiguration.getBounds().equals(rect)) {
                    WindowContainerToken windowContainerToken = runningTaskInfo.token;
                    Configuration configuration = runningTaskInfo.configuration;
                    windowContainerTransaction.setScreenSizeDp(windowContainerToken, configuration.screenWidthDp, configuration.screenHeightDp);
                } else {
                    splitLayout.getDisplayLayout(splitLayout.mContext).getStableBounds(rect2, false);
                    rect2.intersectUnchecked(rect);
                    windowContainerTransaction.setScreenSizeDp(runningTaskInfo.token, (int) ((rect2.width() / splitLayout.getDisplayLayout(splitLayout.mContext).density()) + 0.5f), (int) ((rect2.height() / splitLayout.getDisplayLayout(splitLayout.mContext).density()) + 0.5f));
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

    public final void setSideStagePosition(WindowContainerTransaction windowContainerTransaction, int i) {
        setSideStagePosition(i, -1, windowContainerTransaction, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v7 */
    public final void setSideStagePositionByAdjacentTask(ActivityManager.RunningTaskInfo runningTaskInfo, WindowContainerTransaction windowContainerTransaction) {
        Intent intent = runningTaskInfo.baseIntent;
        if (intent == null || (intent.getFlags() & 4096) == 0) {
            this.mSplitLayout.mSplitWindowManager.getClass();
            return;
        }
        this.mSplitLayout.mSplitWindowManager.getClass();
        windowContainerTransaction.setChangeStagePosition(true);
        if (this.mSplitLayoutChangedForLaunchAdjacent) {
            this.mSplitLayoutChangedForLaunchAdjacent = false;
            return;
        }
        ?? r4 = (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && isVideoControlsTaskInfo(runningTaskInfo)) ? 1 : 0;
        this.mWillBeVideoControls = r4;
        if (CoreRune.MW_MULTI_SPLIT_LAUNCH_ADJACENT) {
            setSideStagePosition(1, r4, windowContainerTransaction, true);
        } else if (r4 != 0 || this.mSideStagePosition == 0) {
            setSideStagePosition(windowContainerTransaction, 1);
        }
        if (r4 == 0) {
            return;
        }
        this.mWillBeVideoControls = r4;
        if (r4 != 0) {
            updateVideoControlsState(true, null, false);
        }
        if (this.mMainStage.mIsActive) {
            return;
        }
        this.mSkipFlexPanelUpdate = true;
    }

    public final MultiSplitLayoutInfo setSplitCreateMode(int i, boolean z) {
        if (!(i == 2 || i == 3 || i == 4 || i == 5)) {
            return null;
        }
        MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
        multiSplitLayoutInfo.sideStagePosition = this.mSideStagePosition;
        multiSplitLayoutInfo.splitDivision = getSplitDivision();
        multiSplitLayoutInfo.cellStagePosition = this.mCellStageWindowConfigPosition;
        if (i == convertCreateMode(multiSplitLayoutInfo)) {
            return null;
        }
        while (i != rotateMultiSplitClockwise(multiSplitLayoutInfo)) {
        }
        updateMultiSplitLayout(multiSplitLayoutInfo, z, null);
        return multiSplitLayoutInfo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean setSplitDivision(int i, boolean z) {
        int i2;
        ValueAnimator valueAnimator;
        if (i == -1) {
            return false;
        }
        if (z) {
            i = !isLandscape() ? 1 : 0;
        } else if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            SplitLayout splitLayout = this.mSplitLayout;
            if ((splitLayout.mSplitScreenFeasibleMode == 1) != false && i != (i2 = splitLayout.mPossibleSplitDivision)) {
                Slog.d("StageCoordinator", "split division not feasible, so change: " + i2);
                i = i2;
            }
        }
        if (this.mSplitDivision == i) {
            return false;
        }
        if (CoreRune.SAFE_DEBUG || CoreRune.IS_DEBUG_LEVEL_MID) {
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("setSplitDivision: nextSplitDivision=", i, "   Caller=");
            m1m.append(Debug.getCallers(5));
            Slog.d("StageCoordinator", m1m.toString());
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && (valueAnimator = this.mSplitTransitions.mDividerFadeAnimation) != null) {
            valueAnimator.cancel();
        }
        int i3 = this.mSplitDivision;
        this.mSplitDivision = i;
        SplitLayout splitLayout2 = this.mSplitLayout;
        if (splitLayout2.mSplitDivision != i) {
            splitLayout2.mSplitDivision = i;
            splitLayout2.updateSnapAlgorithm(i3);
        }
        this.mSplitLayout.update(null);
        if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION_SA_LOGGING) {
            sendSplitDirectionSaLogging();
        }
        return true;
    }

    public final void setSplitsVisible(boolean z, boolean z2) {
        boolean z3;
        StageListenerImpl stageListenerImpl = this.mSideStageListener;
        stageListenerImpl.mVisible = z;
        StageListenerImpl stageListenerImpl2 = this.mMainStageListener;
        stageListenerImpl2.mVisible = z;
        stageListenerImpl.mHasChildren = z;
        stageListenerImpl2.mHasChildren = z;
        if (this.mDividerLeashHidden && z) {
            updateDividerLeashVisible(true);
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            boolean isMultiSplitActive = isMultiSplitActive();
            StageListenerImpl stageListenerImpl3 = this.mCellStageListener;
            if (isMultiSplitActive && z) {
                stageListenerImpl3.mVisible = true;
                stageListenerImpl3.mHasChildren = true;
            } else {
                stageListenerImpl3.mVisible = false;
                stageListenerImpl3.mHasChildren = false;
            }
        }
        boolean z4 = CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS;
        SplitBackgroundController splitBackgroundController = this.mSplitBackgroundController;
        if (z4 && !this.mSkipFlexPanelUpdate) {
            if (z && !this.mIsFlexPanelMode && !this.mIsVideoControls && this.mSplitLayout.mDividerSize == 0) {
                setDividerSizeIfNeeded(true);
            } else if (!z && ((z3 = this.mIsFlexPanelMode) || this.mIsVideoControls)) {
                String str = z3 ? "flex_panel_finish" : "video_controls_finish";
                if (!z3) {
                    setVideoControlsMode(null, false);
                } else if (z3) {
                    this.mIsFlexPanelMode = false;
                    if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
                        updateCornerRadiusForStages(null);
                    }
                }
                splitBackgroundController.updateBackgroundVisibility(false, false);
                if (this.mMainStage.getChildCount() == 0) {
                    Log.d("StageCoordinator", "When pip is entered in Split, there is no need to evict side children.");
                } else if (!z2) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    windowContainerTransaction.setDisplayIdForChangeTransition(this.mDisplayId, str);
                    this.mSideStage.evictAllChildren(windowContainerTransaction, false);
                    this.mSyncQueue.queue(windowContainerTransaction);
                }
            }
        }
        splitBackgroundController.mIsDividerVisible = z;
        if (splitBackgroundController.canShow()) {
            splitBackgroundController.updateBackgroundVisibility(true, false);
        } else {
            splitBackgroundController.updateBackgroundVisibility(false, true);
        }
    }

    public final void setVideoControlsMode(SurfaceControl.Transaction transaction, boolean z) {
        if (this.mIsVideoControls != z) {
            this.mIsVideoControls = z;
            if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
                updateCornerRadiusForStages(transaction);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:153:0x02b7, code lost:
    
        if (r2.taskId == r11.taskId) goto L157;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0228  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x03f9 A[EDGE_INSN: B:213:0x03f9->B:214:0x03f9 BREAK  A[LOOP:1: B:31:0x007e->B:42:0x03e9], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x040b  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0450  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x049f  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x04a6  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x069e  */
    /* JADX WARN: Type inference failed for: r12v1, types: [com.android.wm.shell.splitscreen.SideStage, com.android.wm.shell.splitscreen.StageTaskListener] */
    /* JADX WARN: Type inference failed for: r22v0, types: [com.android.wm.shell.splitscreen.StageCoordinator, com.android.wm.shell.transition.Transitions$TransitionHandler] */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.android.wm.shell.splitscreen.CellStage, com.android.wm.shell.splitscreen.StageTaskListener] */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i;
        int size;
        ShellTaskOrganizer shellTaskOrganizer;
        ?? r7;
        ?? r12;
        int size2;
        int i2;
        boolean z5;
        boolean z6;
        String str;
        MainStage mainStage;
        boolean z7;
        boolean equals;
        boolean z8;
        boolean z9;
        TransitionInfo transitionInfo2 = transitionInfo;
        SplitScreenTransitions splitScreenTransitions = this.mSplitTransitions;
        int i3 = 6;
        if (splitScreenTransitions.getPendingTransition(iBinder) != null) {
            if (this.mMixedHandler != null && KeyguardTransitionHandler.handles(transitionInfo)) {
                DefaultMixedHandler defaultMixedHandler = this.mMixedHandler;
                defaultMixedHandler.getClass();
                DefaultMixedHandler.MixedTransition mixedTransition = new DefaultMixedHandler.MixedTransition(5, iBinder);
                defaultMixedHandler.mActiveTransitions.add(mixedTransition);
                if (defaultMixedHandler.animateKeyguard(mixedTransition, transitionInfo, transaction, transaction2, transitionFinishCallback)) {
                    defaultMixedHandler.mSplitHandler.startPendingAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z2) {
                    return true;
                }
            } else if (this.mMixedHandler != null && TransitionUtil.hasDisplayChange(transitionInfo)) {
                if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
                    this.mMixedHandler.getClass();
                    TransitionInfo subCopy = DefaultMixedHandler.subCopy(transitionInfo, 6, false);
                    for (int size3 = transitionInfo.getChanges().size() - 1; size3 >= 0; size3--) {
                        TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(size3);
                        TransitionInfo.Change change2 = change;
                        while (change2 != null) {
                            if (change2.getTaskInfo() != null) {
                                z = true;
                                break;
                            }
                            if (change2.getParent() == null) {
                                break;
                            }
                            change2 = transitionInfo.getChange(change2.getParent());
                        }
                        z = false;
                        if (!z) {
                            subCopy.addChange(change);
                        }
                    }
                    if (!subCopy.getChanges().isEmpty()) {
                        if (splitScreenTransitions.isPendingResize(iBinder)) {
                            this.mSplitLayout.update(transaction);
                        }
                        this.mMixedHandler.animatePendingSplitWithDisplayChange(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
                        return true;
                    }
                } else if (this.mMixedHandler.animatePendingSplitWithDisplayChange(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback)) {
                    if (!splitScreenTransitions.isPendingResize(iBinder)) {
                        return true;
                    }
                    this.mSplitLayout.update(transaction);
                    transaction.apply();
                    return true;
                }
                if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
                    updateCornerRadiusForStages(transaction2);
                }
                return startPendingAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
            }
            if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
            }
            return startPendingAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
        }
        MainStage mainStage2 = this.mMainStage;
        if (!mainStage2.mIsActive) {
            return false;
        }
        this.mSplitLayout.mFreezeDividerWindow = false;
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && TransitionUtil.hasDisplayChange(transitionInfo)) {
            onLayoutSizeChanged(this.mSplitLayout, null);
            if (splitScreenTransitions.mPendingResize != null) {
                z3 = true;
                StageChangeRecord stageChangeRecord = new StageChangeRecord();
                if (TransitionUtil.isOpeningType(transitionInfo.getType())) {
                    for (int i4 = 0; i4 < transitionInfo.getChanges().size(); i4++) {
                        TransitionInfo.Change change3 = (TransitionInfo.Change) transitionInfo.getChanges().get(i4);
                        ActivityManager.RunningTaskInfo taskInfo = change3.getTaskInfo();
                        if (taskInfo != null && TransitionUtil.isOpeningType(change3.getMode()) && taskInfo.getWindowingMode() == 1) {
                            z4 = true;
                            break;
                        }
                    }
                }
                z4 = false;
                i = 0;
                while (true) {
                    size = transitionInfo.getChanges().size();
                    shellTaskOrganizer = this.mTaskOrganizer;
                    r7 = this.mCellStage;
                    r12 = this.mSideStage;
                    if (i < size) {
                        break;
                    }
                    TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                    SplitScreenTransitions splitScreenTransitions2 = splitScreenTransitions;
                    if (change4.getMode() == i3 && (change4.getFlags() & 32) != 0) {
                        this.mSplitLayout.update(transaction);
                    }
                    ActivityManager.RunningTaskInfo taskInfo2 = change4.getTaskInfo();
                    if (taskInfo2 == null) {
                        z5 = z3;
                        z6 = z4;
                        i2 = i;
                    } else {
                        i2 = i;
                        if (taskInfo2.token.equals(this.mRootTaskInfo.token)) {
                            if (TransitionUtil.isOpeningType(change4.getMode())) {
                                setSplitsVisible(true, false);
                                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, false);
                                shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                            } else if (TransitionUtil.isClosingType(change4.getMode())) {
                                if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && change4.getMode() == 4 && taskInfo2.isSleeping) {
                                    Slog.d("StageCoordinator", "In the process of unfolding, the visible of split should be maintained." + transitionInfo2);
                                } else {
                                    setSplitsVisible(false, false);
                                }
                                if (TransitionUtil.isOpeningType(transitionInfo.getType())) {
                                    for (int i5 = 0; i5 < transitionInfo.getChanges().size(); i5++) {
                                        TransitionInfo.Change change5 = (TransitionInfo.Change) transitionInfo.getChanges().get(i5);
                                        ActivityManager.RunningTaskInfo taskInfo3 = change5.getTaskInfo();
                                        if (taskInfo3 != null && TransitionUtil.isOpeningType(change5.getMode()) && taskInfo3.getWindowingMode() == 1 && taskInfo3.getActivityType() == 2) {
                                            z9 = true;
                                            break;
                                        }
                                    }
                                }
                                z9 = false;
                                if (z9) {
                                    updateDividerLeashVisible(false);
                                }
                                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                windowContainerTransaction2.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
                                shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
                            }
                            z5 = z3;
                            z6 = z4;
                        } else {
                            StageTaskListener stageOfTask = getStageOfTask(taskInfo2);
                            if (CoreRune.MW_SPLIT_SHELL_TRANSITION && z3) {
                                Rect rect = new Rect();
                                boolean z10 = stageOfTask == null;
                                WindowContainerToken windowContainerToken = z10 ? taskInfo2.token : stageOfTask.mRootTaskInfo.token;
                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isMultiSplitScreenVisible()) {
                                    z5 = z3;
                                    int cellHostStageType = getCellHostStageType();
                                    int stagePosition = taskInfo2.getConfiguration().windowConfiguration.getStagePosition();
                                    z6 = z4;
                                    if ((getMainStageWinConfigPosition() == stagePosition ? 0 : getSideStageWinConfigPosition() == stagePosition ? 1 : this.mCellStageWindowConfigPosition == stagePosition ? 2 : -1) == cellHostStageType) {
                                        z7 = true;
                                        SplitLayout splitLayout = this.mSplitLayout;
                                        equals = windowContainerToken.equals(splitLayout.mWinToken1);
                                        str = "StageCoordinator";
                                        Rect rect2 = splitLayout.mTempRect;
                                        if (equals) {
                                            if (windowContainerToken.equals(splitLayout.mWinToken2)) {
                                                if (z7) {
                                                    rect2.set(splitLayout.getRefHostBounds());
                                                } else {
                                                    splitLayout.getRefBounds2(rect2);
                                                }
                                            } else if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && windowContainerToken.equals(splitLayout.mWinToken3)) {
                                                rect2.set(splitLayout.mBounds3);
                                                Rect rect3 = splitLayout.mRootBounds;
                                                rect2.offset(-rect3.left, -rect3.top);
                                            } else {
                                                z8 = false;
                                                if (z8) {
                                                    rect.set(rect2);
                                                }
                                                if (z8) {
                                                    if (z10) {
                                                        change4.setEndAbsBounds(rect);
                                                        change4.setEndRelOffset(rect.left, rect.top);
                                                        change4.getTaskInfo().positionInParent.x = rect.left;
                                                        change4.getTaskInfo().positionInParent.y = rect.top;
                                                        SurfaceControl leash = change4.getLeash();
                                                        int width = change4.getEndAbsBounds().width();
                                                        int height = change4.getEndAbsBounds().height();
                                                        int i6 = change4.getEndRelOffset().x;
                                                        int i7 = change4.getEndRelOffset().y;
                                                        transaction.setWindowCrop(leash, width, height);
                                                        transaction2.setWindowCrop(leash, width, height).setPosition(leash, i6, i7);
                                                    } else {
                                                        change4.setEndAbsBounds(rect);
                                                    }
                                                }
                                            }
                                        } else if (z7) {
                                            rect2.set(splitLayout.getRefHostBounds());
                                        } else {
                                            splitLayout.getRefBounds1(rect2);
                                        }
                                        z8 = true;
                                        if (z8) {
                                        }
                                        if (z8) {
                                        }
                                    }
                                } else {
                                    z5 = z3;
                                    z6 = z4;
                                }
                                z7 = false;
                                SplitLayout splitLayout2 = this.mSplitLayout;
                                equals = windowContainerToken.equals(splitLayout2.mWinToken1);
                                str = "StageCoordinator";
                                Rect rect22 = splitLayout2.mTempRect;
                                if (equals) {
                                }
                                z8 = true;
                                if (z8) {
                                }
                                if (z8) {
                                }
                            } else {
                                z5 = z3;
                                z6 = z4;
                                str = "StageCoordinator";
                            }
                            if (stageOfTask == null) {
                                if (this.mSplitLayout.mImePositionProcessor.mYOffsetForIme != 0) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo = mainStage2.mRootTaskInfo;
                                    if (runningTaskInfo == null || runningTaskInfo.taskId != taskInfo2.taskId) {
                                        ActivityManager.RunningTaskInfo runningTaskInfo2 = r12.mRootTaskInfo;
                                        if (runningTaskInfo2 == null || runningTaskInfo2.taskId != taskInfo2.taskId) {
                                            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && (r2 = r7.mRootTaskInfo) != null) {
                                                mainStage = r7;
                                            }
                                            mainStage = null;
                                        } else {
                                            mainStage = r12;
                                        }
                                    } else {
                                        mainStage = mainStage2;
                                    }
                                    if (mainStage != null && transitionInfo.getType() == 3) {
                                        if (CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME) {
                                            int splitItemStagePosition = ((StageCoordinator) this.mSplitLayout.mSplitLayoutHandler).getSplitItemStagePosition(taskInfo2.token);
                                            if ((splitItemStagePosition & 16) != 0) {
                                                Rect rect4 = new Rect();
                                                SplitLayout splitLayout3 = this.mSplitLayout;
                                                if ((splitItemStagePosition & 40) == 0) {
                                                    splitLayout3.getRefBounds1(rect4);
                                                } else if ((splitLayout3.mCellStageWindowConfigPosition & 16) != 0) {
                                                    rect4.set(splitLayout3.mBounds3);
                                                    Rect rect5 = splitLayout3.mRootBounds;
                                                    rect4.offset(-rect5.left, -rect5.top);
                                                } else {
                                                    rect4.set(splitLayout3.getRefHostBounds());
                                                }
                                                rect4.offset(-rect4.left, -rect4.top);
                                                rect4.bottom = rect4.height() + splitLayout3.mImePositionProcessor.mYOffsetForIme;
                                                transaction2.setWindowCrop(change4.getLeash(), rect4);
                                            }
                                        } else {
                                            MainStage mainStage3 = r12;
                                            if (this.mSideStagePosition != 0) {
                                                mainStage3 = mainStage2;
                                            }
                                            if (mainStage == mainStage3) {
                                                Rect rect6 = new Rect();
                                                SplitLayout splitLayout4 = this.mSplitLayout;
                                                splitLayout4.getRefBounds1(rect6);
                                                rect6.offset(-rect6.left, -rect6.top);
                                                rect6.bottom = rect6.height() + splitLayout4.mImePositionProcessor.mYOffsetForIme;
                                                transaction2.setWindowCrop(change4.getLeash(), rect6);
                                            }
                                        }
                                    }
                                }
                            }
                            if (stageOfTask == null) {
                                if (change4.getParent() == null && !TransitionUtil.isClosingType(change4.getMode()) && taskInfo2.getWindowingMode() == 1) {
                                    stageChangeRecord.mContainShowFullscreenChange = true;
                                }
                            } else if (!TransitionUtil.isOpeningType(change4.getMode())) {
                                String str2 = str;
                                if (TransitionUtil.isClosingType(change4.getMode()) && stageOfTask.containsTask(taskInfo2.taskId) && (!CoreRune.MW_SPLIT_STACKING || !z6 || change4.getMode() != 4)) {
                                    stageChangeRecord.addRecord(stageOfTask, false, taskInfo2.taskId);
                                    Log.w(str2, "Expected onTaskVanished on " + stageOfTask + " to have been called with " + taskInfo2.taskId + " before startAnimation().");
                                }
                            } else if (!stageOfTask.containsTask(taskInfo2.taskId)) {
                                Log.w(str, "Expected onTaskAppeared on " + stageOfTask + " to have been called with " + taskInfo2.taskId + " before startAnimation().");
                                stageChangeRecord.addRecord(stageOfTask, true, taskInfo2.taskId);
                            }
                        }
                    }
                    i = i2 + 1;
                    transitionInfo2 = transitionInfo;
                    splitScreenTransitions = splitScreenTransitions2;
                    z3 = z5;
                    z4 = z6;
                    i3 = 6;
                }
                SplitScreenTransitions splitScreenTransitions3 = splitScreenTransitions;
                ArraySet arraySet = new ArraySet();
                ArrayMap arrayMap = stageChangeRecord.mChanges;
                for (size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                    StageChangeRecord.StageChange stageChange = (StageChangeRecord.StageChange) arrayMap.valueAt(size2);
                    if (stageChange.shouldDismissStage()) {
                        arraySet.add(stageChange.mStageTaskListener);
                    }
                }
                boolean z11 = !CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isMultiSplitScreenVisible() && r7.getChildCount() == 0;
                if (mainStage2.getChildCount() != 0 || r12.getChildCount() == 0 || arraySet.size() == 1 || z11) {
                    Log.e("StageCoordinator", "Somehow removed the last task in a stage outside of a proper transition.");
                    if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                        Log.d("StageCoordinator", "Dismiss Split Debugging mMainStage.getChildCount()=" + mainStage2.getChildCount() + " mSideStage.getChildCount()=" + r12.getChildCount() + " dismissStages.size()=" + arraySet.size() + " isCellStageEmpty=" + z11 + " isSplitActive=" + mainStage2.mIsActive + " isSplitScreenVisible=" + isSplitScreenVisible());
                    }
                    if (!this.mAppPairStarted) {
                        Log.d("StageCoordinator", "In the process of starting the App Pair, skip the process");
                        return false;
                    }
                    if (CoreRune.MW_SPLIT_STACKING) {
                        for (int i8 = 0; i8 < arrayMap.size(); i8++) {
                            StageChangeRecord.StageChange stageChange2 = (StageChangeRecord.StageChange) arrayMap.valueAt(i8);
                            if (!stageChange2.shouldDismissStage() && stageChange2.mStageTaskListener.getChildCount() == 0) {
                                return false;
                            }
                        }
                    }
                    clearSplitPairedInRecents(2);
                    WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                    int i9 = ((arraySet.size() == 1 && getStageType((StageTaskListener) arraySet.valueAt(0)) == 0) || mainStage2.getChildCount() == 0) ? 1 : 0;
                    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isMultiSplitScreenVisible()) {
                        int i10 = i9 ^ 1;
                        if ((arraySet.size() == 1 && getStageType((StageTaskListener) arraySet.valueAt(0)) == 2) || z11) {
                            i10 = 2;
                        }
                        int cellHostStageType2 = getCellHostStageType();
                        int cellHostStageType3 = getCellHostStageType();
                        int i11 = cellHostStageType3 == 0 ? 1 : cellHostStageType3 == 1 ? 0 : -1;
                        StageTaskListener stageTaskListener = r7.mHost;
                        if (stageTaskListener == mainStage2) {
                            mainStage2 = r12;
                        }
                        prepareMultiSplitDismissChangeTransition(i10, windowContainerTransaction3, false);
                        if (i10 == 2) {
                            prepareExitMultiSplitScreen(windowContainerTransaction3, false);
                        } else if (i10 == cellHostStageType2) {
                            reparentCellToMainOrSide(windowContainerTransaction3, stageTaskListener, true);
                        } else if (i10 == i11) {
                            reparentCellToMainOrSide(windowContainerTransaction3, mainStage2, true);
                            this.mSplitTransitions.startDismissTransition(windowContainerTransaction3, this, i11, 10, true);
                            updateRecentTasksSplitPair();
                            if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mCellDividerVisible) {
                                this.mSplitLayout.releaseCellDivider(null);
                                return false;
                            }
                        }
                        i11 = cellHostStageType2;
                        this.mSplitTransitions.startDismissTransition(windowContainerTransaction3, this, i11, 10, true);
                        updateRecentTasksSplitPair();
                        return !CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER ? false : false;
                    }
                    prepareExitSplitScreen((stageChangeRecord.mContainShowFullscreenChange || !isSplitScreenVisible()) ? -1 : i9, windowContainerTransaction3, true);
                    splitScreenTransitions3.startDismissTransition(windowContainerTransaction3, this, i9, 2);
                    if (this.mDividerVisible) {
                        this.mSplitLayout.release(null);
                    }
                }
                if (!CoreRune.MW_SPLIT_SHELL_TRANSITION || (transitionInfo.getFlags() & 256) == 0 || (transitionInfo.getFlags() & 131072) == 0 || !isSplitScreenVisible()) {
                    return false;
                }
                setSplitsVisible(false, false);
                WindowContainerTransaction windowContainerTransaction4 = new WindowContainerTransaction();
                windowContainerTransaction4.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, true);
                shellTaskOrganizer.applyTransaction(windowContainerTransaction4);
                return false;
            }
        }
        z3 = false;
        StageChangeRecord stageChangeRecord2 = new StageChangeRecord();
        if (TransitionUtil.isOpeningType(transitionInfo.getType())) {
        }
        z4 = false;
        i = 0;
        while (true) {
            size = transitionInfo.getChanges().size();
            shellTaskOrganizer = this.mTaskOrganizer;
            r7 = this.mCellStage;
            r12 = this.mSideStage;
            if (i < size) {
            }
            i = i2 + 1;
            transitionInfo2 = transitionInfo;
            splitScreenTransitions = splitScreenTransitions2;
            z3 = z5;
            z4 = z6;
            i3 = 6;
        }
        SplitScreenTransitions splitScreenTransitions32 = splitScreenTransitions;
        ArraySet arraySet2 = new ArraySet();
        ArrayMap arrayMap2 = stageChangeRecord2.mChanges;
        while (size2 >= 0) {
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
        }
        if (mainStage2.getChildCount() != 0) {
        }
        Log.e("StageCoordinator", "Somehow removed the last task in a stage outside of a proper transition.");
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
        }
        if (!this.mAppPairStarted) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00c2, code lost:
    
        if (r11 == false) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startIntent(PendingIntent pendingIntent, Intent intent, int i, Bundle bundle, int i2, int i3) {
        this.mSplitRequest = new SplitRequest(this, pendingIntent.getIntent(), i);
        boolean z = Transitions.ENABLE_SHELL_TRANSITIONS;
        MainStage mainStage = this.mMainStage;
        boolean z2 = false;
        if (!z) {
            boolean z3 = !mainStage.mIsActive;
            C41173 c41173 = new C41173(z3, i);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            Bundle resolveStartStage = resolveStartStage(-1, i, bundle, windowContainerTransaction, i2);
            if (z3) {
                if (this.mLogger.mEnterReason == 2) {
                    updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
                }
            }
            windowContainerTransaction.sendPendingIntent(pendingIntent, intent, resolveStartStage);
            this.mSyncQueue.queue(c41173, windowContainerTransaction);
            return;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i3 != 0) {
            startIntentToCell(pendingIntent, null, intent, null, i3, null);
            return;
        }
        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
        Bundle resolveStartStage2 = resolveStartStage(-1, i, bundle, null, i2);
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isMultiSplitActive() && !isMultiSplitScreenVisible()) {
            prepareExitMultiSplitScreen(windowContainerTransaction2, false);
        }
        windowContainerTransaction2.sendPendingIntent(pendingIntent, intent, resolveStartStage2);
        boolean isVisibleTaskInDexDisplay = MultiWindowManager.getInstance().isVisibleTaskInDexDisplay(pendingIntent);
        DefaultMixedHandler defaultMixedHandler = this.mMixedHandler;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        if (defaultMixedHandler != null) {
            PipTransitionController pipTransitionController = defaultMixedHandler.mPipHandler;
            if (pipTransitionController != null) {
                String packageName = SplitScreenUtils.getPackageName(pendingIntent.getIntent());
                PipTaskOrganizer pipTaskOrganizer = pipTransitionController.mPipOrganizer;
                ActivityManager.RunningTaskInfo runningTaskInfo = pipTaskOrganizer.mTaskInfo;
                if (packageName != null && runningTaskInfo != null && pipTaskOrganizer.isInPip() && packageName.equals(SplitScreenUtils.getPackageName(((TaskInfo) runningTaskInfo).baseIntent))) {
                    z2 = true;
                }
            }
        }
        if (i != -1 && !isVisibleTaskInDexDisplay) {
            if (CoreRune.MW_SPLIT_STACKING && isSplitScreenVisible()) {
                shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
                return;
            }
            int i4 = mainStage.mIsActive ? 1005 : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI;
            prepareEnterSplitScreen(windowContainerTransaction2, null, i, !this.mIsDropEntering);
            this.mSplitTransitions.startEnterTransition(windowContainerTransaction2, null, this, i4, !this.mIsDropEntering);
            return;
        }
        shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
    }

    public final void startIntentToCell(PendingIntent pendingIntent, Intent intent, Intent intent2, UserHandle userHandle, int i, Bundle bundle) {
        if (userHandle == null) {
            userHandle = UserHandle.CURRENT;
        }
        PendingIntent activityAsUser = pendingIntent != null ? pendingIntent : PendingIntent.getActivityAsUser(this.mContext, 0, intent, 1107296256, null, userHandle);
        if (MultiWindowManager.getInstance().isVisibleTaskInDexDisplay(activityAsUser)) {
            StageLaunchOptions makeStartIntentOpts = StageLaunchOptions.makeStartIntentOpts(activityAsUser.getIntent(), userHandle, this.mSideStagePosition, this.mSplitDivision, i);
            makeStartIntentOpts.mPendingIntent = pendingIntent;
            sendMessageProxyService(makeStartIntentOpts, 3);
            Slog.d("StageCoordinator", "pending split screen by start intent to cell");
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.sendPendingIntent(activityAsUser, intent2, resolveStartCellStage(-1, i, bundle, windowContainerTransaction));
        if (CoreRune.MW_SPLIT_STACKING && isMultiSplitScreenVisible()) {
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
            return;
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !isMultiSplitScreenVisible()) {
            this.mSplitLayout.setCellDividerRatio(0.5f, i, true, false);
        }
        prepareEnterMultiSplitScreen(i, windowContainerTransaction);
        if (CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION) {
            applyCellHostResizeTransition(windowContainerTransaction);
        }
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, 1100, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:205:0x0861, code lost:
    
        if (r11.mPendingDismiss.mReason == 4) goto L383;
     */
    /* JADX WARN: Removed duplicated region for block: B:314:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x01ae A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:333:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x052f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0530  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startPendingAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        MainStage mainStage;
        SideStage sideStage;
        ArrayList arrayList;
        SurfaceControl.Transaction transaction3;
        SplitScreenTransitions splitScreenTransitions;
        TransitionInfo transitionInfo2;
        SurfaceControl.Transaction transaction4;
        IBinder iBinder2;
        boolean z;
        Transitions.TransitionFinishCallback transitionFinishCallback2;
        MainStage mainStage2;
        ArrayList arrayList2;
        SideStage sideStage2;
        boolean z2;
        ArrayList arrayList3;
        SplitScreenTransitions.DismissSession dismissSession;
        Transitions transitions;
        WindowContainerToken windowContainerToken;
        WindowContainerToken windowContainerToken2;
        SurfaceControl surfaceControl;
        TransitionInfo.Change change;
        String str;
        int i;
        TransitionInfo transitionInfo3;
        WindowContainerToken windowContainerToken3;
        WindowContainerToken windowContainerToken4;
        WindowContainerToken windowContainerToken5;
        TransitionInfo.Change change2;
        TransitionInfo transitionInfo4;
        WindowContainerToken windowContainerToken6;
        int i2;
        boolean z3;
        boolean z4;
        TransitionInfo.Change change3;
        SplitLayout splitLayout;
        AlertDialog alertDialog;
        final SplitScreenTransitions.EnterSession enterSession;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        MainStage mainStage3;
        boolean z10;
        boolean z11;
        final int i3;
        Optional optional;
        SplitScreenTransitions splitScreenTransitions2 = this.mSplitTransitions;
        boolean isPendingEnter = splitScreenTransitions2.isPendingEnter(iBinder);
        ArrayList arrayList4 = splitScreenTransitions2.mAnimations;
        CellStage cellStage = this.mCellStage;
        SideStage sideStage3 = this.mSideStage;
        MainStage mainStage4 = this.mMainStage;
        if (isPendingEnter) {
            SplitScreenTransitions.EnterSession enterSession2 = splitScreenTransitions2.mPendingEnter;
            final WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            TransitionInfo.Change change4 = null;
            TransitionInfo.Change change5 = null;
            int i4 = 0;
            TransitionInfo.Change change6 = null;
            while (true) {
                enterSession = enterSession2;
                if (i4 >= transitionInfo.getChanges().size()) {
                    break;
                }
                TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo.getChanges().get(i4);
                ArrayList arrayList5 = arrayList4;
                ActivityManager.RunningTaskInfo taskInfo = change7.getTaskInfo();
                if (taskInfo != null && taskInfo.hasParentTask()) {
                    if (!this.mPausingTasks.contains(Integer.valueOf(taskInfo.taskId))) {
                        int stageType = getStageType(getStageOfTask(taskInfo));
                        if (change5 == null && stageType == 0 && (TransitionUtil.isOpeningType(change7.getMode()) || change7.getMode() == 6)) {
                            change5 = change7;
                        } else if (change4 == null && stageType == 1 && (TransitionUtil.isOpeningType(change7.getMode()) || (CoreRune.MW_SPLIT_SHELL_TRANSITION && (change7.getMode() == 6 || change7.getChangeLeash() != null)))) {
                            change4 = change7;
                        } else if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageType == 2 && (TransitionUtil.isOpeningType(change7.getMode()) || change7.getMode() == 6)) {
                            change6 = change7;
                        } else if (stageType != -1 && change7.getMode() == 4) {
                            windowContainerTransaction.reparent(taskInfo.token, (WindowContainerToken) null, false);
                        }
                    }
                }
                i4++;
                arrayList4 = arrayList5;
                enterSession2 = enterSession;
            }
            ArrayList arrayList6 = arrayList4;
            if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && change4 != null) {
                boolean isVideoControlsTaskInfo = isVideoControlsTaskInfo(change4.getTaskInfo());
                this.mWillBeVideoControls = isVideoControlsTaskInfo;
                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && isVideoControlsTaskInfo && isMultiSplitActive()) {
                    prepareExitMultiSplitScreen(new WindowContainerTransaction(), false);
                }
                updateVideoControlsState(this.mWillBeVideoControls, transaction2, true);
            }
            final boolean z12 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && splitScreenTransitions2.mPendingEnter.mExtraTransitType == 1100 && change6 != null;
            int i5 = splitScreenTransitions2.mPendingEnter.mExtraTransitType;
            final boolean z13 = this.mAppPairStarted;
            if (z13) {
                this.mAppPairStarted = false;
                Log.d("StageCoordinator", "isSkipDismissPair: mAppPairStarted=true");
            } else {
                if (this.mKeyguardShowing) {
                    if (i5 == 1004) {
                        Log.d("StageCoordinator", "isSkipDismissPair: type=TRANSIT_SPLIT_SCREEN_PAIR_OPEN mKeyguardShowing=true");
                    } else if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && i5 == 1101) {
                        Log.d("StageCoordinator", "isSkipDismissPair: type=TRANSIT_MULTI_SPLIT_SCREEN_PAIR_OPEN mKeyguardShowing=true");
                    }
                }
                z5 = false;
                if (!z5 && !z12) {
                    if (splitScreenTransitions2.mPendingEnter.mExtraTransitType == 1005) {
                        z6 = true;
                        if (change5 == null || change4 == null) {
                            TransitionInfo.Change change8 = change4;
                            TransitionInfo.Change change9 = change5;
                            mainStage = mainStage4;
                            sideStage = sideStage3;
                            transitionInfo2 = transitionInfo;
                            arrayList = arrayList6;
                            transaction3 = transaction;
                            transaction4 = transaction2;
                            z = false;
                            StringBuilder sb = new StringBuilder("  info:");
                            sb.append(" mainChild=" + change9);
                            sb.append(" sideChild=" + change8);
                            Log.w("StageCoordinator", "Launched 2 tasks in split, but didn't receive 2 tasks in transition. Possibly one of them failed to launch" + ((Object) sb));
                            if (change9 != null) {
                                i3 = 0;
                            } else if (change8 != null) {
                                i3 = 1;
                            } else {
                                splitScreenTransitions = splitScreenTransitions2;
                                i3 = -1;
                                SplitScreenTransitions.EnterSession enterSession3 = splitScreenTransitions.mPendingEnter;
                                SplitScreenTransitions.TransitionFinishedCallback transitionFinishedCallback = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda18
                                    @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                                    public final void onFinished(WindowContainerTransaction windowContainerTransaction2, SurfaceControl.Transaction transaction5) {
                                        StageCoordinator stageCoordinator = StageCoordinator.this;
                                        stageCoordinator.getClass();
                                        boolean z14 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                                        int i6 = i3;
                                        if (!z14) {
                                            stageCoordinator.prepareExitSplitScreen(i6, windowContainerTransaction2, true);
                                        } else {
                                            windowContainerTransaction2.setDisplayIdForChangeTransition(0, "enter_split_failed");
                                            stageCoordinator.prepareExitSplitScreen(i6, windowContainerTransaction2, false);
                                        }
                                    }
                                };
                                enterSession3.mCanceled = true;
                                enterSession3.mFinishedCallback = transitionFinishedCallback;
                                optional = this.mRecentTasks;
                                if (optional.isPresent() && change9 != null) {
                                    ((RecentTasksController) optional.get()).removeSplitPair(change9.getTaskInfo().taskId);
                                }
                                if (optional.isPresent() && change8 != null) {
                                    ((RecentTasksController) optional.get()).removeSplitPair(change8.getTaskInfo().taskId);
                                }
                                this.mSplitUnsupportedToast.show();
                                z2 = true;
                            }
                            splitScreenTransitions = splitScreenTransitions2;
                            SplitScreenTransitions.EnterSession enterSession32 = splitScreenTransitions.mPendingEnter;
                            SplitScreenTransitions.TransitionFinishedCallback transitionFinishedCallback2 = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda18
                                @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                                public final void onFinished(WindowContainerTransaction windowContainerTransaction2, SurfaceControl.Transaction transaction5) {
                                    StageCoordinator stageCoordinator = StageCoordinator.this;
                                    stageCoordinator.getClass();
                                    boolean z14 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                                    int i6 = i3;
                                    if (!z14) {
                                        stageCoordinator.prepareExitSplitScreen(i6, windowContainerTransaction2, true);
                                    } else {
                                        windowContainerTransaction2.setDisplayIdForChangeTransition(0, "enter_split_failed");
                                        stageCoordinator.prepareExitSplitScreen(i6, windowContainerTransaction2, false);
                                    }
                                }
                            };
                            enterSession32.mCanceled = true;
                            enterSession32.mFinishedCallback = transitionFinishedCallback2;
                            optional = this.mRecentTasks;
                            if (optional.isPresent()) {
                                ((RecentTasksController) optional.get()).removeSplitPair(change9.getTaskInfo().taskId);
                            }
                            if (optional.isPresent()) {
                                ((RecentTasksController) optional.get()).removeSplitPair(change8.getTaskInfo().taskId);
                            }
                            this.mSplitUnsupportedToast.show();
                            z2 = true;
                        }
                        z7 = (change5 != null || mainStage4.containsTask(change5.getTaskInfo().taskId)) ? false : z6;
                        z8 = z6;
                        z9 = (change4 != null || sideStage3.containsTask(change4.getTaskInfo().taskId)) ? false : z8;
                        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || change6 == null || cellStage.containsTask(change6.getTaskInfo().taskId)) {
                            z8 = false;
                        }
                        if (z7) {
                            StringBuilder sb2 = new StringBuilder("Expected onTaskAppeared on ");
                            sb2.append(mainStage4);
                            sb2.append(" to have been called with ");
                            mainStage3 = mainStage4;
                            sb2.append(change5.getTaskInfo().taskId);
                            sb2.append(" before startAnimation().");
                            Log.w("StageCoordinator", sb2.toString());
                        } else {
                            mainStage3 = mainStage4;
                        }
                        if (z9) {
                            Log.w("StageCoordinator", "Expected onTaskAppeared on " + sideStage3 + " to have been called with " + change4.getTaskInfo().taskId + " before startAnimation().");
                        }
                        if (z8) {
                            Log.w("StageCoordinator", "Expected onTaskAppeared on " + cellStage + " to have been called with " + change6.getTaskInfo().taskId + " before startAnimation().");
                        }
                        final TransitionInfo.Change change10 = change4;
                        final TransitionInfo.Change change11 = change5;
                        mainStage = mainStage3;
                        final boolean z14 = z7;
                        final boolean z15 = z9;
                        sideStage = sideStage3;
                        arrayList = arrayList6;
                        final TransitionInfo.Change change12 = change6;
                        final boolean z16 = z8;
                        transaction4 = transaction2;
                        enterSession.mFinishedCallback = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda19
                            @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                            public final void onFinished(WindowContainerTransaction windowContainerTransaction2, SurfaceControl.Transaction transaction5) {
                                TransitionInfo.Change change13;
                                StageCoordinator stageCoordinator = StageCoordinator.this;
                                stageCoordinator.getClass();
                                TransitionInfo.Change change14 = change11;
                                boolean z17 = z12;
                                boolean z18 = z13;
                                if (change14 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z17) && !z18)) {
                                    boolean z19 = z14;
                                    MainStage mainStage5 = stageCoordinator.mMainStage;
                                    if (z19) {
                                        mainStage5.evictInvisibleChildren(windowContainerTransaction2);
                                    } else {
                                        mainStage5.evictOtherChildren(windowContainerTransaction2, change14.getTaskInfo().taskId);
                                    }
                                }
                                TransitionInfo.Change change15 = change10;
                                if (change15 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z17) && !z18)) {
                                    boolean z20 = z15;
                                    SideStage sideStage4 = stageCoordinator.mSideStage;
                                    if (z20) {
                                        sideStage4.evictInvisibleChildren(windowContainerTransaction2);
                                    } else {
                                        sideStage4.evictOtherChildren(windowContainerTransaction2, change15.getTaskInfo().taskId);
                                    }
                                }
                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && (change13 = change12) != null && (!CoreRune.MW_MULTI_SPLIT_APP_PAIR || !z18)) {
                                    boolean z21 = z16;
                                    CellStage cellStage2 = stageCoordinator.mCellStage;
                                    if (z21) {
                                        cellStage2.evictInvisibleChildren(windowContainerTransaction2);
                                    } else {
                                        cellStage2.evictOtherChildren(windowContainerTransaction2, change13.getTaskInfo().taskId);
                                    }
                                }
                                WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction;
                                if (!windowContainerTransaction3.isEmpty()) {
                                    windowContainerTransaction2.merge(windowContainerTransaction3, true);
                                }
                                if (enterSession.mResizeAnim) {
                                    stageCoordinator.mShowDecorImmediately = true;
                                    stageCoordinator.mSplitLayout.flingDividerToCenter();
                                }
                                if (stageCoordinator.mIsOpeningHomeDuringSplit) {
                                    Slog.d("StageCoordinator", "skip to send reparentLeafTaskIfRelaunch");
                                    stageCoordinator.mIsOpeningHomeDuringSplit = false;
                                } else {
                                    windowContainerTransaction2.setReparentLeafTaskIfRelaunch(stageCoordinator.mRootTaskInfo.token, false);
                                }
                                stageCoordinator.mPausingTasks.clear();
                            }
                        };
                        if (z12) {
                            SplitLayout splitLayout2 = this.mSplitLayout;
                            splitLayout2.releaseCellDivider(transaction4);
                            if (!splitLayout2.mCellInitialized) {
                                splitLayout2.mCellInitialized = true;
                                splitLayout2.mCellSplitWindowManager.init(splitLayout2, splitLayout2.mInsetsState);
                                splitLayout2.mCellSnapAlgorithm = splitLayout2.createCellSnapAlgorithm();
                            }
                            cellStage = cellStage;
                            SplitDecorManager splitDecorManager = cellStage.mSplitDecorManager;
                            SplitLayout splitLayout3 = this.mSplitLayout;
                            splitLayout3.getClass();
                            new Rect(splitLayout3.mBounds3);
                            splitDecorManager.getClass();
                            z10 = true;
                            setCellDividerVisibility(transaction4, true);
                            transaction4.reparent(this.mSplitLayout.getCellDividerLeash(), this.mRootTaskLeash);
                            updateSurfaceBounds(this.mSplitLayout, transaction4, false);
                            transaction4.show(this.mRootTaskLeash);
                            setCellSplitVisible(true);
                            this.mIsDropEntering = false;
                            updateRecentTasksSplitPair();
                            transitionInfo2 = transitionInfo;
                            addCellDividerBarToTransition(transitionInfo2, true);
                            transaction3 = transaction;
                            z11 = false;
                        } else {
                            transitionInfo2 = transitionInfo;
                            cellStage = cellStage;
                            z10 = true;
                            z11 = false;
                            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !isMultiSplitActive() && this.mCellDividerVisible) {
                                setCellSplitVisible(false);
                                transaction3 = transaction;
                                setCellDividerVisibility(transaction3, false);
                                transaction3.setCrop(cellStage.mRootLeash, null);
                                transaction3.hide(cellStage.mDimLayer);
                            } else {
                                transaction3 = transaction;
                            }
                            boolean z17 = i5 == 1101;
                            finishEnterSplitScreen(transaction4, z17);
                            if (this.mDividerVisible) {
                                Log.d("StageCoordinator", "startPendingEnterAnimation: skip addDividerBarToTransition, divider is already visible.");
                            } else {
                                addDividerBarToTransition(transitionInfo2, true);
                            }
                            if (z17) {
                                addCellDividerBarToTransition(transitionInfo2, true);
                            }
                        }
                        splitScreenTransitions = splitScreenTransitions2;
                        boolean z18 = z11;
                        z2 = z10;
                        z = z18;
                    } else if (change5 == null && change4 == null) {
                        Log.w("StageCoordinator", "Launched a task in split, but didn't receive any task in transition.");
                        SplitScreenTransitions.EnterSession enterSession4 = splitScreenTransitions2.mPendingEnter;
                        z2 = true;
                        enterSession4.mCanceled = true;
                        enterSession4.mFinishedCallback = null;
                        z = false;
                        transaction3 = transaction;
                        mainStage = mainStage4;
                        sideStage = sideStage3;
                        arrayList = arrayList6;
                        splitScreenTransitions = splitScreenTransitions2;
                        transitionInfo2 = transitionInfo;
                        transaction4 = transaction2;
                    }
                    iBinder2 = iBinder;
                }
                z6 = true;
                if (change5 != null) {
                }
                z8 = z6;
                if (change4 != null) {
                }
                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                }
                z8 = false;
                if (z7) {
                }
                if (z9) {
                }
                if (z8) {
                }
                final TransitionInfo.Change change102 = change4;
                final TransitionInfo.Change change112 = change5;
                mainStage = mainStage3;
                final boolean z142 = z7;
                final boolean z152 = z9;
                sideStage = sideStage3;
                arrayList = arrayList6;
                final TransitionInfo.Change change122 = change6;
                final boolean z162 = z8;
                transaction4 = transaction2;
                enterSession.mFinishedCallback = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda19
                    @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                    public final void onFinished(WindowContainerTransaction windowContainerTransaction2, SurfaceControl.Transaction transaction5) {
                        TransitionInfo.Change change13;
                        StageCoordinator stageCoordinator = StageCoordinator.this;
                        stageCoordinator.getClass();
                        TransitionInfo.Change change14 = change112;
                        boolean z172 = z12;
                        boolean z182 = z13;
                        if (change14 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z172) && !z182)) {
                            boolean z19 = z142;
                            MainStage mainStage5 = stageCoordinator.mMainStage;
                            if (z19) {
                                mainStage5.evictInvisibleChildren(windowContainerTransaction2);
                            } else {
                                mainStage5.evictOtherChildren(windowContainerTransaction2, change14.getTaskInfo().taskId);
                            }
                        }
                        TransitionInfo.Change change15 = change102;
                        if (change15 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z172) && !z182)) {
                            boolean z20 = z152;
                            SideStage sideStage4 = stageCoordinator.mSideStage;
                            if (z20) {
                                sideStage4.evictInvisibleChildren(windowContainerTransaction2);
                            } else {
                                sideStage4.evictOtherChildren(windowContainerTransaction2, change15.getTaskInfo().taskId);
                            }
                        }
                        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && (change13 = change122) != null && (!CoreRune.MW_MULTI_SPLIT_APP_PAIR || !z182)) {
                            boolean z21 = z162;
                            CellStage cellStage2 = stageCoordinator.mCellStage;
                            if (z21) {
                                cellStage2.evictInvisibleChildren(windowContainerTransaction2);
                            } else {
                                cellStage2.evictOtherChildren(windowContainerTransaction2, change13.getTaskInfo().taskId);
                            }
                        }
                        WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction;
                        if (!windowContainerTransaction3.isEmpty()) {
                            windowContainerTransaction2.merge(windowContainerTransaction3, true);
                        }
                        if (enterSession.mResizeAnim) {
                            stageCoordinator.mShowDecorImmediately = true;
                            stageCoordinator.mSplitLayout.flingDividerToCenter();
                        }
                        if (stageCoordinator.mIsOpeningHomeDuringSplit) {
                            Slog.d("StageCoordinator", "skip to send reparentLeafTaskIfRelaunch");
                            stageCoordinator.mIsOpeningHomeDuringSplit = false;
                        } else {
                            windowContainerTransaction2.setReparentLeafTaskIfRelaunch(stageCoordinator.mRootTaskInfo.token, false);
                        }
                        stageCoordinator.mPausingTasks.clear();
                    }
                };
                if (z12) {
                }
                splitScreenTransitions = splitScreenTransitions2;
                boolean z182 = z11;
                z2 = z10;
                z = z182;
                iBinder2 = iBinder;
            }
            z5 = true;
            if (!z5) {
                if (splitScreenTransitions2.mPendingEnter.mExtraTransitType == 1005) {
                }
                iBinder2 = iBinder;
            }
            z6 = true;
            if (change5 != null) {
            }
            z8 = z6;
            if (change4 != null) {
            }
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            }
            z8 = false;
            if (z7) {
            }
            if (z9) {
            }
            if (z8) {
            }
            final TransitionInfo.Change change1022 = change4;
            final TransitionInfo.Change change1122 = change5;
            mainStage = mainStage3;
            final boolean z1422 = z7;
            final boolean z1522 = z9;
            sideStage = sideStage3;
            arrayList = arrayList6;
            final TransitionInfo.Change change1222 = change6;
            final boolean z1622 = z8;
            transaction4 = transaction2;
            enterSession.mFinishedCallback = new SplitScreenTransitions.TransitionFinishedCallback() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda19
                @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
                public final void onFinished(WindowContainerTransaction windowContainerTransaction2, SurfaceControl.Transaction transaction5) {
                    TransitionInfo.Change change13;
                    StageCoordinator stageCoordinator = StageCoordinator.this;
                    stageCoordinator.getClass();
                    TransitionInfo.Change change14 = change1122;
                    boolean z172 = z12;
                    boolean z1822 = z13;
                    if (change14 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z172) && !z1822)) {
                        boolean z19 = z1422;
                        MainStage mainStage5 = stageCoordinator.mMainStage;
                        if (z19) {
                            mainStage5.evictInvisibleChildren(windowContainerTransaction2);
                        } else {
                            mainStage5.evictOtherChildren(windowContainerTransaction2, change14.getTaskInfo().taskId);
                        }
                    }
                    TransitionInfo.Change change15 = change1022;
                    if (change15 != null && ((!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !z172) && !z1822)) {
                        boolean z20 = z1522;
                        SideStage sideStage4 = stageCoordinator.mSideStage;
                        if (z20) {
                            sideStage4.evictInvisibleChildren(windowContainerTransaction2);
                        } else {
                            sideStage4.evictOtherChildren(windowContainerTransaction2, change15.getTaskInfo().taskId);
                        }
                    }
                    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && (change13 = change1222) != null && (!CoreRune.MW_MULTI_SPLIT_APP_PAIR || !z1822)) {
                        boolean z21 = z1622;
                        CellStage cellStage2 = stageCoordinator.mCellStage;
                        if (z21) {
                            cellStage2.evictInvisibleChildren(windowContainerTransaction2);
                        } else {
                            cellStage2.evictOtherChildren(windowContainerTransaction2, change13.getTaskInfo().taskId);
                        }
                    }
                    WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction;
                    if (!windowContainerTransaction3.isEmpty()) {
                        windowContainerTransaction2.merge(windowContainerTransaction3, true);
                    }
                    if (enterSession.mResizeAnim) {
                        stageCoordinator.mShowDecorImmediately = true;
                        stageCoordinator.mSplitLayout.flingDividerToCenter();
                    }
                    if (stageCoordinator.mIsOpeningHomeDuringSplit) {
                        Slog.d("StageCoordinator", "skip to send reparentLeafTaskIfRelaunch");
                        stageCoordinator.mIsOpeningHomeDuringSplit = false;
                    } else {
                        windowContainerTransaction2.setReparentLeafTaskIfRelaunch(stageCoordinator.mRootTaskInfo.token, false);
                    }
                    stageCoordinator.mPausingTasks.clear();
                }
            };
            if (z12) {
            }
            splitScreenTransitions = splitScreenTransitions2;
            boolean z1822 = z11;
            z2 = z10;
            z = z1822;
            iBinder2 = iBinder;
        } else {
            mainStage = mainStage4;
            sideStage = sideStage3;
            arrayList = arrayList4;
            transaction3 = transaction;
            splitScreenTransitions = splitScreenTransitions2;
            transitionInfo2 = transitionInfo;
            transaction4 = transaction2;
            int i6 = -1;
            iBinder2 = iBinder;
            if (!splitScreenTransitions.isPendingDismiss(iBinder2)) {
                z = false;
                if (splitScreenTransitions.isPendingResize(iBinder2)) {
                    if (!CoreRune.MW_SPLIT_SHELL_TRANSITION || !transitionInfo.hasChangeTransition()) {
                        WindowContainerToken windowContainerToken7 = mainStage.mRootTaskInfo.token;
                        WindowContainerToken windowContainerToken8 = sideStage.mRootTaskInfo.token;
                        SplitDecorManager splitDecorManager2 = mainStage.mSplitDecorManager;
                        SplitDecorManager splitDecorManager3 = sideStage.mSplitDecorManager;
                        splitScreenTransitions.mAnimatingTransition = iBinder2;
                        splitScreenTransitions.mFinishTransaction = transaction4;
                        splitScreenTransitions.mFinishCallback = transitionFinishCallback;
                        int size = transitionInfo.getChanges().size() - 1;
                        while (size >= 0) {
                            TransitionInfo.Change change13 = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
                            if (windowContainerToken7.equals(change13.getContainer()) || windowContainerToken8.equals(change13.getContainer())) {
                                SurfaceControl leash = change13.getLeash();
                                transaction3.setPosition(leash, change13.getEndAbsBounds().left, change13.getEndAbsBounds().top);
                                transaction3.setWindowCrop(leash, change13.getEndAbsBounds().width(), change13.getEndAbsBounds().height());
                                SplitDecorManager splitDecorManager4 = windowContainerToken7.equals(change13.getContainer()) ? splitDecorManager2 : splitDecorManager3;
                                ValueAnimator valueAnimator = new ValueAnimator();
                                arrayList3 = arrayList;
                                arrayList3.add(valueAnimator);
                                SurfaceControl snapshot = change13.getSnapshot();
                                splitDecorManager4.getClass();
                                if (snapshot != null) {
                                    snapshot.isValid();
                                }
                                splitDecorManager4.onResized(transaction3, new SplitScreenTransitions$$ExternalSyntheticLambda5(splitScreenTransitions, valueAnimator));
                            } else {
                                arrayList3 = arrayList;
                            }
                            size--;
                            arrayList = arrayList3;
                        }
                        transaction.apply();
                        splitScreenTransitions.onFinish(null, null);
                        for (int size2 = transitionInfo.getChanges().size() - 1; size2 >= 0; size2--) {
                            TransitionInfo.Change change14 = (TransitionInfo.Change) transitionInfo.getChanges().get(size2);
                            if (change14.getMode() == 2 && change14.getTaskInfo() != null && change14.getTaskInfo().isSplitScreen()) {
                                ((HandlerExecutor) this.mMainExecutor).executeDelayed(500L, new StageCoordinator$$ExternalSyntheticLambda3(this, 8));
                                return true;
                            }
                        }
                        return true;
                    }
                    addDividerBarToTransition(transitionInfo2, true);
                    if (isMultiSplitScreenVisible()) {
                        addCellDividerBarToTransition(transitionInfo2, true);
                    }
                }
                transitionFinishCallback2 = transitionFinishCallback;
                mainStage2 = mainStage;
                arrayList2 = arrayList;
                sideStage2 = sideStage;
                z2 = true;
                if (z2) {
                    return z;
                }
                WindowContainerToken windowContainerToken9 = CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION ? cellStage.mRootTaskInfo.token : null;
                if (isSplitScreenVisible() && (splitLayout = this.mSplitLayout) != null && (alertDialog = splitLayout.mSplitWindowManager.mDividerPanel.mAddToAppPairDialog) != null) {
                    alertDialog.dismiss();
                }
                WindowContainerToken windowContainerToken10 = mainStage2.mRootTaskInfo.token;
                WindowContainerToken windowContainerToken11 = sideStage2.mRootTaskInfo.token;
                WindowContainerToken windowContainerToken12 = this.mRootTaskInfo.token;
                splitScreenTransitions.mAnimatingTransition = iBinder2;
                splitScreenTransitions.mFinishTransaction = transaction4;
                splitScreenTransitions.mFinishCallback = transitionFinishCallback2;
                SplitScreenTransitions.TransitSession pendingTransition = splitScreenTransitions.getPendingTransition(iBinder2);
                if (pendingTransition != null) {
                    if (pendingTransition.mCanceled) {
                        transaction.apply();
                        splitScreenTransitions.onFinish(null, null);
                        return true;
                    }
                    OneShotRemoteHandler oneShotRemoteHandler = pendingTransition.mRemoteHandler;
                    if (oneShotRemoteHandler != null) {
                        oneShotRemoteHandler.startAnimation(iBinder, transitionInfo, transaction, transaction2, splitScreenTransitions.mRemoteFinishCB);
                        splitScreenTransitions.mActiveRemoteHandler = oneShotRemoteHandler;
                        return true;
                    }
                }
                boolean isPendingEnter2 = splitScreenTransitions.isPendingEnter(iBinder2);
                int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1);
                TransitionInfo transitionInfo5 = transitionInfo2;
                while (true) {
                    transitions = splitScreenTransitions.mTransitions;
                    if (m136m < 0) {
                        break;
                    }
                    TransitionInfo.Change change15 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                    SurfaceControl leash2 = change15.getLeash();
                    int mode = ((TransitionInfo.Change) transitionInfo.getChanges().get(m136m)).getMode();
                    int rootIndexFor = TransitionUtil.rootIndexFor(change15, transitionInfo5);
                    boolean z19 = isPendingEnter2;
                    if (mode != 6 || change15.getParent() == null) {
                        windowContainerToken = windowContainerToken11;
                        windowContainerToken2 = windowContainerToken9;
                    } else {
                        TransitionInfo.Change change16 = transitionInfo5.getChange(change15.getParent());
                        if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                            windowContainerToken2 = windowContainerToken9;
                            if (change15.hasFlags(512) && !change15.hasFlags(1024) && change16 != null && change16.getSnapshot() != null) {
                                Log.d("SplitScreenTransitions", "Except TaskFragment changes, it's parent has snapshot. it replaces task fragment changes.");
                                transaction3.setWindowCrop(change15.getLeash(), null);
                                windowContainerToken = windowContainerToken11;
                                transitionInfo3 = transitionInfo;
                                transitionInfo4 = transitionInfo5;
                                i = m136m;
                                windowContainerToken3 = windowContainerToken10;
                                windowContainerToken4 = windowContainerToken2;
                                windowContainerToken5 = windowContainerToken;
                                isPendingEnter2 = z19;
                                windowContainerToken10 = windowContainerToken3;
                                transitionInfo5 = transitionInfo4;
                                windowContainerToken11 = windowContainerToken5;
                                windowContainerToken9 = windowContainerToken4;
                                m136m = i - 1;
                            }
                        } else {
                            windowContainerToken2 = windowContainerToken9;
                        }
                        transaction3.show(change16.getLeash());
                        windowContainerToken = windowContainerToken11;
                        transaction3.setAlpha(change16.getLeash(), 1.0f);
                        if ((!CoreRune.MW_SPLIT_SHELL_TRANSITION || change15.getChangeLeash() == null) && ((!change15.hasFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) || change15.hasFlags(512) || change16.getChangeLeash() == null) && (!CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION || !transitionInfo.isSeparatedFromCustomDisplayChange()))) {
                            transaction3.reparent(change16.getLeash(), transitionInfo5.getRoot(rootIndexFor).getLeash());
                            transaction3.setLayer(change16.getLeash(), transitionInfo.getChanges().size() - m136m);
                        }
                        splitScreenTransitions.mFinishTransaction.reparent(leash2, change16.getLeash());
                        splitScreenTransitions.mFinishTransaction.setPosition(leash2, change15.getEndRelOffset().x, change15.getEndRelOffset().y);
                    }
                    if (!CoreRune.MW_EMBED_ACTIVITY_ANIMATION || mode != 2 || !change15.hasFlags(512) || change15.hasFlags(1024) || change15.getParent() == null || (change3 = transitionInfo5.getChange(change15.getParent())) == null || change3.getSnapshot() == null) {
                        if (!CoreRune.MW_SPLIT_SHELL_TRANSITION || change15.getChangeLeash() == null) {
                            surfaceControl = leash2;
                            change = change15;
                            str = "SplitScreenTransitions";
                            i = m136m;
                        } else {
                            boolean z20 = TransitionUtil.isClosingType(change15.getMode()) && change15.getChangeTransitMode() == 2;
                            StringBuilder sb3 = new StringBuilder("buildChangeTransition: leash=");
                            sb3.append(change15.getLeash());
                            sb3.append(", snapshot=");
                            sb3.append(change15.getSnapshot());
                            sb3.append(z20 ? ", shouldBeHidden=true" : "");
                            Log.d("SplitScreenTransitions", sb3.toString());
                            surfaceControl = leash2;
                            change = change15;
                            str = "SplitScreenTransitions";
                            i = m136m;
                            transitions.mChangeTransitProvider.buildChangeTransitionAnimators(arrayList2, change15, new SplitScreenTransitions$$ExternalSyntheticLambda4(splitScreenTransitions, 0), transaction, transitionInfo);
                            if (z20) {
                                splitScreenTransitions.mFinishTransaction.hide(change.getLeash());
                            }
                        }
                        if (!(change.getParent() == null || windowContainerToken12.equals(change.getParent())) || windowContainerToken12.equals(change.getContainer())) {
                            transitionInfo3 = transitionInfo;
                            windowContainerToken3 = windowContainerToken10;
                            windowContainerToken4 = windowContainerToken2;
                            windowContainerToken5 = windowContainerToken;
                            if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                                if (windowContainerToken12.equals(change.getContainer())) {
                                    transaction3.setAlpha(change.getLeash(), 1.0f);
                                }
                                if (windowContainerToken3.equals(change.getParent()) || windowContainerToken5.equals(change.getParent())) {
                                    transaction3.setCrop(change.getLeash(), null);
                                }
                            }
                            if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                                TransitionInfo.Change change17 = change;
                                if (change17.hasFlags(512) && !change17.hasFlags(1024) && TransitionUtil.isOpeningType(transitionInfo.getType()) && (change2 = transitionInfo3.getChange(change17.getParent())) != null && change2.getSnapshot() != null) {
                                    transaction3.setAlpha(change17.getLeash(), 1.0f);
                                }
                            }
                        } else {
                            boolean equals = windowContainerToken12.equals(change.getContainer());
                            boolean equals2 = windowContainerToken10.equals(change.getContainer());
                            WindowContainerToken windowContainerToken13 = windowContainerToken;
                            boolean equals3 = windowContainerToken13.equals(change.getContainer());
                            boolean z21 = change.getFlags() == 4194304;
                            boolean equals4 = windowContainerToken10.equals(change.getParent());
                            boolean equals5 = windowContainerToken13.equals(change.getParent());
                            if (!CoreRune.MW_MULTI_SPLIT_SHELL_TRANSITION || windowContainerToken2 == null) {
                                windowContainerToken6 = windowContainerToken13;
                                i2 = rootIndexFor;
                                windowContainerToken4 = windowContainerToken2;
                                z3 = false;
                                z4 = false;
                            } else {
                                windowContainerToken6 = windowContainerToken13;
                                i2 = rootIndexFor;
                                windowContainerToken4 = windowContainerToken2;
                                z4 = windowContainerToken4.equals(change.getContainer());
                                z3 = windowContainerToken4.equals(change.getParent());
                            }
                            if (z19 && (equals4 || equals5 || z3)) {
                                splitScreenTransitions.mFinishTransaction.setPosition(surfaceControl, change.getEndRelOffset().x, change.getEndRelOffset().y);
                                splitScreenTransitions.mFinishTransaction.setCrop(surfaceControl, null);
                            } else if (equals) {
                                transaction3.setAlpha(surfaceControl, 1.0f);
                                transaction3.show(surfaceControl);
                            } else if ((z19 && equals2) || equals3 || z4) {
                                transaction3.setPosition(surfaceControl, change.getEndAbsBounds().left, change.getEndAbsBounds().top);
                                transaction3.setWindowCrop(surfaceControl, change.getEndAbsBounds().width(), change.getEndAbsBounds().height());
                            } else if (z21) {
                                transaction3.setPosition(surfaceControl, change.getEndAbsBounds().left, change.getEndAbsBounds().top);
                                transaction3.setLayer(surfaceControl, Integer.MAX_VALUE);
                                transaction3.show(surfaceControl);
                            }
                            if (equals || equals2 || equals3) {
                                transitionInfo3 = transitionInfo;
                                windowContainerToken3 = windowContainerToken10;
                                windowContainerToken5 = windowContainerToken6;
                            } else {
                                if ((change.getTaskInfo() != null || z21) && ((!CoreRune.MW_SHELL_CHANGE_TRANSITION || change.getChangeLeash() == null) && (!z19 || !splitScreenTransitions.mPendingEnter.mResizeAnim))) {
                                    int i7 = i2;
                                    int i8 = splitScreenTransitions.isPendingDismiss(iBinder) ? 4 : 4;
                                    boolean isOpeningType = TransitionUtil.isOpeningType(transitionInfo.getType());
                                    boolean z22 = change.getTaskInfo() != null && (change.getTaskInfo().getActivityType() == 2 || change.getTaskInfo().getActivityType() == 3);
                                    boolean z23 = CoreRune.MW_SPLIT_SHELL_TRANSITION;
                                    if (z23 && z22 && z19 && isOpeningType && (mode == 2 || mode == i8)) {
                                        transaction3.setAlpha(surfaceControl, 0.0f);
                                    } else {
                                        if (z23 && isOpeningType && ((mode == 1 || mode == 3) && !transitionInfo.isSeparatedFromCustomDisplayChange())) {
                                            Log.d(str, "buildSurfaceAnimation: leash=" + surfaceControl);
                                            SplitScreenTransitions$$ExternalSyntheticLambda4 splitScreenTransitions$$ExternalSyntheticLambda4 = new SplitScreenTransitions$$ExternalSyntheticLambda4(splitScreenTransitions, 1);
                                            Rect endAbsBounds = change.getEndAbsBounds();
                                            Animation loadAnimationFromResources = splitScreenTransitions.mMultiTaskingTransitions.loadAnimationFromResources(R.anim.split_pair_enter, endAbsBounds);
                                            Point point = new Point(endAbsBounds.left, endAbsBounds.top);
                                            Rect rect = new Rect(endAbsBounds);
                                            rect.offsetTo(0, 0);
                                            windowContainerToken3 = windowContainerToken10;
                                            splitScreenTransitions.mMultiTaskingTransitions.buildSurfaceAnimator(arrayList2, loadAnimationFromResources, surfaceControl, splitScreenTransitions$$ExternalSyntheticLambda4, point, rect, false);
                                            windowContainerToken5 = windowContainerToken6;
                                        } else {
                                            windowContainerToken3 = windowContainerToken10;
                                            windowContainerToken5 = windowContainerToken6;
                                            if (CoreRune.MW_SPLIT_SHELL_TRANSITION && transitionInfo.getType() == 6 && z21 && mode == 3) {
                                                splitScreenTransitions.startCustomFadeAnimation(surfaceControl, true, true);
                                            } else if (!isOpeningType && (mode == 2 || mode == i8)) {
                                                splitScreenTransitions.startFadeAnimation(surfaceControl);
                                            } else if (mode == 6 && change.getSnapshot() != null) {
                                                transitionInfo3 = transitionInfo;
                                                transaction3.reparent(change.getSnapshot(), transitionInfo3.getRoot(i7).getLeash());
                                                transaction3.setLayer(change.getSnapshot(), transitionInfo.getChanges().size() + 1);
                                                transaction3.setPosition(change.getSnapshot(), change.getStartAbsBounds().left, change.getStartAbsBounds().top);
                                                transaction3.show(change.getSnapshot());
                                                splitScreenTransitions.startFadeAnimation(change.getSnapshot());
                                            }
                                        }
                                        transitionInfo3 = transitionInfo;
                                    }
                                }
                                windowContainerToken3 = windowContainerToken10;
                                windowContainerToken5 = windowContainerToken6;
                                transitionInfo3 = transitionInfo;
                            }
                        }
                        transitionInfo4 = transitionInfo3;
                        isPendingEnter2 = z19;
                        windowContainerToken10 = windowContainerToken3;
                        transitionInfo5 = transitionInfo4;
                        windowContainerToken11 = windowContainerToken5;
                        windowContainerToken9 = windowContainerToken4;
                        m136m = i - 1;
                    } else {
                        transaction3.hide(change15.getLeash());
                        transitionInfo3 = transitionInfo;
                        transitionInfo4 = transitionInfo5;
                        i = m136m;
                        windowContainerToken3 = windowContainerToken10;
                        windowContainerToken4 = windowContainerToken2;
                        windowContainerToken5 = windowContainerToken;
                        isPendingEnter2 = z19;
                        windowContainerToken10 = windowContainerToken3;
                        transitionInfo5 = transitionInfo4;
                        windowContainerToken11 = windowContainerToken5;
                        windowContainerToken9 = windowContainerToken4;
                        m136m = i - 1;
                    }
                }
                transaction.apply();
                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                    Log.d("SplitScreenTransitions", "startAllAnimators: num_anim=" + arrayList2.size());
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        ((HandlerExecutor) transitions.mAnimExecutor).execute(new SplitScreenTransitions$$ExternalSyntheticLambda1((Animator) it.next(), 2));
                    }
                }
                splitScreenTransitions.onFinish(null, null);
                return true;
            }
            SplitScreenTransitions.DismissSession dismissSession2 = splitScreenTransitions.mPendingDismiss;
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                dismissSession = dismissSession2;
                prepareDismissAnimation(dismissSession2.mDismissTop, dismissSession2.mReason, transitionInfo, transaction, transaction2, dismissSession2.mIsMultiSplitDismissed);
                i6 = -1;
            } else {
                dismissSession = dismissSession2;
                prepareDismissAnimation(dismissSession.mDismissTop, dismissSession.mReason, transitionInfo, transaction, transaction2, false);
            }
            if (dismissSession.mDismissTop == i6) {
                z = false;
                setDividerVisibility(false, transaction3);
                this.mSplitLayout.release(transaction3);
                splitScreenTransitions.mPendingDismiss = null;
                z2 = false;
            } else {
                z = false;
                dismissSession.mFinishedCallback = new StageCoordinator$$ExternalSyntheticLambda12(this, dismissSession, 2);
                z2 = true;
            }
        }
        transitionFinishCallback2 = transitionFinishCallback;
        mainStage2 = mainStage;
        arrayList2 = arrayList;
        sideStage2 = sideStage;
        if (z2) {
        }
    }

    public final void startSplitScreen(int i, PendingIntent pendingIntent, Intent intent, Intent intent2, Intent intent3, UserHandle userHandle, UserHandle userHandle2, UserHandle userHandle3, int i2, int i3, float f, float f2, int i4, int i5, WindowContainerTransaction windowContainerTransaction, RemoteTransition remoteTransition) {
        PendingIntent pendingIntent2;
        PendingIntent pendingIntent3;
        Intent intent4;
        PendingIntent pendingIntent4;
        PendingIntent pendingIntent5;
        RemoteTransition remoteTransition2;
        int i6;
        boolean z;
        int i7;
        PendingIntent pendingIntent6;
        int i8 = i2;
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
            this.mTopStageAfterFoldDismiss = -1;
        }
        RemoteTransition remoteTransition3 = null;
        PendingIntent activityAsUser = pendingIntent != null ? pendingIntent : intent != null ? PendingIntent.getActivityAsUser(this.mContext, 0, intent, 167772160, null, userHandle) : null;
        PendingIntent activityAsUser2 = PendingIntent.getActivityAsUser(this.mContext, 0, intent2, 167772160, null, userHandle2);
        if (intent3 != null) {
            pendingIntent2 = activityAsUser2;
            pendingIntent3 = PendingIntent.getActivityAsUser(this.mContext, 0, intent3, 167772160, null, userHandle3);
        } else {
            pendingIntent2 = activityAsUser2;
            pendingIntent3 = null;
        }
        if (pendingIntent2 == null || (i == -1 && activityAsUser == null)) {
            Slog.w("StageCoordinator", "startSplitScreen param is wrong. taskId:" + i + ",mainIntent:" + intent + ",sideIntent:" + intent2);
            return;
        }
        if (i != -1 && MultiWindowManager.getInstance().isVisibleTaskByTaskIdInDexDisplay(i)) {
            sendMessageProxyService(StageLaunchOptions.makeStartTaskAndIntentOpts(i, intent2, i8, i5), 2);
            Slog.d("StageCoordinator", "pending split screen by recent drag and drop");
            return;
        }
        if (pendingIntent != null && MultiWindowManager.getInstance().isVisibleTaskInDexDisplay(activityAsUser)) {
            StageLaunchOptions makeStartIntentsOpts = StageLaunchOptions.makeStartIntentsOpts(pendingIntent.getIntent(), intent2, pendingIntent.getCreatorUserHandle(), userHandle2, i2, f, i5);
            makeStartIntentsOpts.mPendingIntent = pendingIntent;
            sendMessageProxyService(makeStartIntentsOpts, 1);
            Slog.d("StageCoordinator", "pending split screen by appsEdge drag and drop");
            return;
        }
        WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction != null ? windowContainerTransaction : new WindowContainerTransaction();
        int i9 = this.mDisplayId;
        SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
        SideStage sideStage = this.mSideStage;
        PendingIntent pendingIntent7 = pendingIntent2;
        CellStage cellStage = this.mCellStage;
        MainStage mainStage = this.mMainStage;
        PendingIntent pendingIntent8 = pendingIntent3;
        if (i4 == 1 && mainStage.mIsActive) {
            remoteTransition2 = (!isSplitScreenVisible() || remoteTransition == null) ? remoteTransition : null;
            this.mPausingTasks.clear();
            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
            if (!isSameIntentRequested(mainStage.getTopRunningTaskInfo(), intent, userHandle)) {
                mainStage.evictAllChildren(windowContainerTransaction3, false);
            }
            if (!isSameIntentRequested(sideStage.getTopRunningTaskInfo(), intent2, userHandle2)) {
                sideStage.evictAllChildren(windowContainerTransaction3, false);
            }
            if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && cellStage.mIsActive) {
                intent4 = intent3;
                pendingIntent4 = pendingIntent7;
                pendingIntent5 = activityAsUser;
                if (!isSameIntentRequested(cellStage.getTopRunningTaskInfo(), intent4, userHandle3)) {
                    cellStage.evictAllChildren(windowContainerTransaction3, false);
                }
            } else {
                intent4 = intent3;
                pendingIntent4 = pendingIntent7;
                pendingIntent5 = activityAsUser;
            }
            if (!windowContainerTransaction3.isEmpty()) {
                if (remoteTransition2 == null) {
                    windowContainerTransaction3.setDisplayIdForChangeTransition(i9, "evict_all_children");
                }
                syncTransactionQueue.queue(windowContainerTransaction3);
            }
        } else {
            intent4 = intent3;
            pendingIntent4 = pendingIntent7;
            pendingIntent5 = activityAsUser;
            remoteTransition2 = remoteTransition;
        }
        if (!CoreRune.MW_MULTI_SPLIT_APP_PAIR || intent4 == null) {
            i6 = i3;
            if (i8 != 0 && i8 != 1) {
                int i10 = this.mSideStagePosition;
                if (i10 == -1) {
                    z = false;
                    i8 = 1;
                    setSideStagePosition(i8, i5, windowContainerTransaction2, z);
                } else {
                    i8 = i10;
                }
            }
            z = false;
            setSideStagePosition(i8, i5, windowContainerTransaction2, z);
        } else {
            cellStage.mIsActive = true;
            MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
            multiSplitLayoutInfo.sideStagePosition = i8;
            multiSplitLayoutInfo.splitDivision = i5;
            multiSplitLayoutInfo.cellStagePosition = i3;
            updateMultiSplitLayout(multiSplitLayoutInfo, false, windowContainerTransaction2);
            i6 = i3;
            z = false;
        }
        windowContainerTransaction2.setTransactionType(i4);
        this.mLastTransactionType = i4;
        setRootForceTranslucent(windowContainerTransaction2, z);
        boolean z2 = mainStage.mIsActive;
        if (!z2) {
            mainStage.activate(windowContainerTransaction2, z);
            this.mSplitLayout.resetDividerPosition();
        }
        this.mSplitLayout.setDivideRatio(f, true, true);
        if (!CoreRune.MW_MULTI_SPLIT_APP_PAIR || intent4 == null) {
            i7 = i9;
            updateWindowBounds(this.mSplitLayout, windowContainerTransaction2, false);
        } else {
            i7 = i9;
            this.mSplitLayout.setCellDividerRatio(f2, i6, true, false);
            updateWindowBounds(this.mSplitLayout, windowContainerTransaction2, true);
        }
        windowContainerTransaction2.reorder(this.mRootTaskInfo.token, true);
        updateStagePositionIfNeeded(windowContainerTransaction2);
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        addActivityOptions(bundle, mainStage);
        addActivityOptions(bundle2, sideStage);
        if (i == -1) {
            windowContainerTransaction2.sendPendingIntent(pendingIntent5, intent, bundle);
        } else {
            if (i4 == 2 && z2 && sideStage.hasChild()) {
                bundle.putBoolean("android.activity.splitTaskDeferResume", true);
            }
            windowContainerTransaction2.startTask(i, bundle);
        }
        windowContainerTransaction2.sendPendingIntent(pendingIntent4, intent2, bundle2);
        if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            pendingIntent6 = pendingIntent8;
        } else if (pendingIntent8 != null) {
            Bundle bundle3 = new Bundle();
            addActivityOptions(bundle3, cellStage);
            pendingIntent6 = pendingIntent8;
            windowContainerTransaction2.sendPendingIntent(pendingIntent6, intent4, bundle3);
        } else {
            pendingIntent6 = pendingIntent8;
            if (isMultiSplitActive()) {
                prepareExitMultiSplitScreen(windowContainerTransaction2, false);
            }
        }
        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction2);
            syncTransactionQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda0(this, 1));
            return;
        }
        if (i4 == 1) {
            this.mAppPairStarted = true;
            if (remoteTransition2 != null) {
                for (ActivityManager.RunningTaskInfo runningTaskInfo : MultiWindowManager.getInstance().getVisibleTasks()) {
                    if (!isVisibleTask(runningTaskInfo, intent, userHandle)) {
                        Intent intent5 = intent4;
                        if (!isVisibleTask(runningTaskInfo, intent2, userHandle2) && (!CoreRune.MW_MULTI_SPLIT_APP_PAIR || !isVisibleTask(runningTaskInfo, intent5, userHandle3))) {
                            intent4 = intent5;
                        }
                    }
                    Slog.d("StageCoordinator", "startSplitScreen: If there is already a visible task, delete the remote transition because the animation does not look normal. task=" + runningTaskInfo);
                }
            }
            remoteTransition3 = remoteTransition2;
            if (remoteTransition3 == null) {
                windowContainerTransaction2.setDisplayIdForChangeTransition(i7, "app_pair");
            }
        } else {
            remoteTransition3 = remoteTransition2;
        }
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction2, remoteTransition3, this, pendingIntent6 != null ? VolteConstants.ErrorCode.CALL_SESSION_ABORT : VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI, false);
    }

    public final void startSplitTasks(int i, int i2, int i3, boolean z, int i4, float f, float f2) {
        if (checkNonResizableTaskAndStartTask(i, i2, i3)) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && isMultiSplitActive() && i3 == -1) {
            prepareExitMultiSplitScreen(windowContainerTransaction, false);
        }
        windowContainerTransaction.setTransactionType(3);
        this.mLastTransactionType = 3;
        windowContainerTransaction.setDisplayIdForChangeTransition(this.mDisplayId, "split_tasks");
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        MainStage mainStage = this.mMainStage;
        if (mainStage.mIsActive) {
            bundle.putBoolean("android.activity.splitTaskDeferResume", true);
            bundle2.putBoolean("android.activity.splitTaskDeferResume", true);
            if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && i3 != -1) {
                bundle3.putBoolean("android.activity.splitTaskDeferResume", true);
            }
        } else {
            mainStage.activate(windowContainerTransaction, false);
        }
        addActivityOptions(bundle, mainStage);
        addActivityOptions(bundle2, this.mSideStage);
        windowContainerTransaction.startTask(i, bundle);
        windowContainerTransaction.startTask(i2, bundle2);
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && i3 != -1) {
            CellStage cellStage = this.mCellStage;
            cellStage.mIsActive = true;
            addActivityOptions(bundle3, cellStage);
            windowContainerTransaction.startTask(i3, bundle3);
        }
        if (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || i3 == -1) {
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
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, this, (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || i3 == -1) ? VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI : VolteConstants.ErrorCode.CALL_SESSION_ABORT, false);
    }

    public final void startTaskAndIntent(int i, Intent intent, int i2, int i3, WindowContainerTransaction windowContainerTransaction) {
        startSplitScreen(i, null, null, intent, null, null, UserHandle.CURRENT, null, i2, 0, 0.5f, 0.0f, 2, i3, windowContainerTransaction, null);
    }

    public final void startTaskWithAllApps(int i, SplitScreenController.CallerInfo callerInfo, int i2) {
        try {
            for (ActivityManager.RecentTaskInfo recentTaskInfo : ActivityTaskManager.getInstance().getRecentTasks(Integer.MAX_VALUE, 3, -2)) {
                if (recentTaskInfo.taskId == i) {
                    break;
                }
            }
        } catch (Exception unused) {
        }
        recentTaskInfo = null;
        if (recentTaskInfo == null) {
            Slog.e("StageCoordinator", "task not found");
            return;
        }
        Slog.e("StageCoordinator", "startTaskWithAllApps from uid:" + callerInfo.mUid);
        ComponentName component = recentTaskInfo.baseIntent.getComponent();
        int i3 = recentTaskInfo.userId;
        int i4 = ((isLandscape() && this.mSplitLayout.mRotation == 3 && (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY || isInSubDisplay())) ? 1 : 0) ^ 1;
        Intent edgeAllAppsActivityIntent = MultiWindowUtils.getEdgeAllAppsActivityIntent(component, i3, recentTaskInfo.taskId);
        if (MultiWindowManager.getInstance().isVisibleTaskByTaskIdInDexDisplay(i)) {
            sendMessageProxyService(StageLaunchOptions.makeStartTaskAndIntentOpts(i, edgeAllAppsActivityIntent, i4, i2), 2);
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, false);
        startTaskAndIntent(i, edgeAllAppsActivityIntent, i4, i2, windowContainerTransaction);
        if (CoreRune.MW_SPLIT_START_EDGE_ALL_APPS_SA_LOGGING && "com.sec.android.app.launcher".equalsIgnoreCase(this.mContext.getPackageManager().getNameForUid(callerInfo.mUid))) {
            CoreSaLogger.logForAdvanced("1000", "From recent_option");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x009a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startTasks(int i, Bundle bundle, int i2, Bundle bundle2, int i3, Bundle bundle3, int i4, float f, int i5, float f2, RemoteTransition remoteTransition, InstanceId instanceId, int i6, SplitScreenController.CallerInfo callerInfo) {
        int i7;
        int i8;
        int i9 = i;
        int i10 = i2;
        if (i10 == -1) {
            startTaskWithAllApps(i9, callerInfo, i6);
            return;
        }
        if (checkNonResizableTaskAndStartTask(i9, i10, i3)) {
            return;
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        SideStage sideStage = this.mSideStage;
        MainStage mainStage = this.mMainStage;
        if (i10 == -1) {
            if (mainStage.containsTask(i9) || sideStage.containsTask(i9)) {
                prepareExitSplitScreen(-1, windowContainerTransaction, true);
            }
            Optional optional = this.mRecentTasks;
            if (optional.isPresent()) {
                ((RecentTasksController) optional.get()).removeSplitPair(i9);
            }
            Bundle bundle4 = bundle != null ? bundle : new Bundle();
            addActivityOptions(bundle4, null);
            windowContainerTransaction.startTask(i9, bundle4);
            this.mSplitTransitions.startFullscreenTransition(windowContainerTransaction, remoteTransition);
            return;
        }
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && isMultiSplitActive() && i3 == -1) {
            prepareExitMultiSplitScreen(windowContainerTransaction, false);
        }
        if (!mainStage.mIsActive || (i8 = this.mSideStagePosition) == -1) {
            i7 = i4;
        } else {
            i7 = i4;
            if (i8 != i7) {
                i10 = i9;
                i9 = i10;
                setSideStagePosition(i8, i6, windowContainerTransaction, true);
                if (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && i3 != -1) {
                    setCellStageWindowConfigPosition(i5, false);
                }
                Bundle bundle5 = bundle == null ? bundle : new Bundle();
                addActivityOptions(bundle5, sideStage);
                windowContainerTransaction.startTask(i9, bundle5);
                startWithTask(windowContainerTransaction, i10, bundle2, f, i3, bundle3, f2, i5, i6, remoteTransition, instanceId, CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY, false, bundle5);
                if (CoreRune.MW_SPLIT_RECENT_TASKS_SA_LOGGING || remoteTransition == null) {
                }
                CoreSaLogger.logForAdvanced("1000", "From recent_task");
                if (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || i3 == -1) {
                    return;
                }
                CoreSaLogger.logForAdvanced("1021", "From recent_task");
                return;
            }
        }
        i8 = i7;
        setSideStagePosition(i8, i6, windowContainerTransaction, true);
        if (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
        }
        if (bundle == null) {
        }
        addActivityOptions(bundle5, sideStage);
        windowContainerTransaction.startTask(i9, bundle5);
        startWithTask(windowContainerTransaction, i10, bundle2, f, i3, bundle3, f2, i5, i6, remoteTransition, instanceId, CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY, false, bundle5);
        if (CoreRune.MW_SPLIT_RECENT_TASKS_SA_LOGGING) {
        }
    }

    public final void startWithLegacyTransition(WindowContainerTransaction windowContainerTransaction, int i, Bundle bundle, int i2, float f, RemoteAnimationAdapter remoteAnimationAdapter, InstanceId instanceId) {
        startWithLegacyTransition(windowContainerTransaction, i, null, null, null, bundle, i2, f, remoteAnimationAdapter, instanceId);
    }

    public final void startWithTask(WindowContainerTransaction windowContainerTransaction, int i, Bundle bundle, float f, int i2, Bundle bundle2, float f2, int i3, int i4, RemoteTransition remoteTransition, InstanceId instanceId, boolean z, boolean z2, Bundle bundle3) {
        Bundle bundle4;
        MainStage mainStage = this.mMainStage;
        if (!mainStage.mIsActive) {
            mainStage.activate(windowContainerTransaction, false);
        }
        boolean z3 = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && i2 != -1;
        CellStage cellStage = this.mCellStage;
        if (z3) {
            cellStage.mIsActive = true;
        }
        this.mSplitLayout.setDivideRatio(f, z, z2);
        if (z3) {
            this.mSplitLayout.setCellDividerRatio(f2, i3, false, (i4 == 1 && (i3 & 32) != 0) || (i4 == 0 && (i3 & 64) != 0));
        }
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        updateStagePositionIfNeeded(windowContainerTransaction);
        windowContainerTransaction.setTransactionType(5);
        this.mLastTransactionType = 5;
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(this.mRootTaskInfo.token, false);
        setRootForceTranslucent(windowContainerTransaction, false);
        Bundle bundle5 = bundle != null ? bundle : new Bundle();
        addActivityOptions(bundle5, mainStage);
        windowContainerTransaction.startTask(i, bundle5);
        if (z3) {
            bundle4 = bundle2 != null ? bundle2 : new Bundle();
            addActivityOptions(bundle4, cellStage);
            windowContainerTransaction.startTask(i2, bundle4);
        } else {
            bundle4 = bundle2;
        }
        if (remoteTransition != null && ((mainStage.hasChild() || this.mSideStage.hasChild()) && bundle3 != null)) {
            bundle5.putBoolean("android.activity.splitTaskDeferResume", true);
            bundle3.putBoolean("android.activity.splitTaskDeferResume", true);
            if (z3) {
                bundle4.putBoolean("android.activity.splitTaskDeferResume", true);
            }
        }
        ArrayList arrayList = this.mPausingTasks;
        if (arrayList.contains(Integer.valueOf(i))) {
            arrayList.clear();
        }
        this.mSplitTransitions.startEnterTransition(windowContainerTransaction, remoteTransition, this, (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || i2 == -1) ? VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI : VolteConstants.ErrorCode.CALL_SESSION_ABORT, false);
        if (instanceId != null) {
            SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
            splitscreenEventLogger.mEnterSessionId = instanceId;
            splitscreenEventLogger.mEnterReason = 3;
        }
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
        int invertedCurrentPosition = getInvertedCurrentPosition();
        this.mSplitLayout.updateSnapAlgorithm(invertedCurrentPosition);
        this.mSplitLayout.setDividePosition(this.mSplitLayout.getDividerSnapAlgorithm().calculateSnapTarget(invertedCurrentPosition, 0.0f).position, windowContainerTransaction, true);
    }

    public final void updateCornerRadiusForStages(SurfaceControl.Transaction transaction) {
        if (this.mDisplayController.getDisplayContext(0) == null) {
            return;
        }
        float roundedCornerRadius = (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS && this.mIsVideoControls) ? 0.0f : MultiWindowUtils.getRoundedCornerRadius(r0);
        boolean z = transaction != null;
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction acquire = transaction != null ? transaction : transactionPool.acquire();
        boolean applyCornerRadiusToLeashIfNeeded = this.mCellStage.applyCornerRadiusToLeashIfNeeded(roundedCornerRadius, acquire, z) | false | this.mMainStage.applyCornerRadiusToLeashIfNeeded(roundedCornerRadius, acquire, z) | this.mSideStage.applyCornerRadiusToLeashIfNeeded(roundedCornerRadius, acquire, z);
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
        String str;
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
        StringBuilder sb = new StringBuilder("updateDividerLeashVisible: ");
        sb.append(dividerLeash);
        sb.append(", show=");
        sb.append(z);
        if (CoreRune.SAFE_DEBUG) {
            str = ", callers=" + Debug.getCallers(3);
        } else {
            str = "";
        }
        sb.append(str);
        Slog.d("StageCoordinator", sb.toString());
    }

    public final void updateMultiSplitLayout(MultiSplitLayoutInfo multiSplitLayoutInfo, boolean z, WindowContainerTransaction windowContainerTransaction) {
        setSideStagePosition(multiSplitLayoutInfo.sideStagePosition, multiSplitLayoutInfo.splitDivision, windowContainerTransaction, false);
        setCellStageWindowConfigPosition(multiSplitLayoutInfo.cellStagePosition, true);
        this.mSplitLayout.updateCellStageWindowConfigPosition(this.mCellStageWindowConfigPosition);
        if (this.mSideStageListener.mVisible && z) {
            onLayoutSizeChanged(this.mSplitLayout, windowContainerTransaction);
        }
    }

    public final void updateRecentTasksSplitPair() {
        if (this.mShouldUpdateRecents && this.mPausingTasks.isEmpty()) {
            this.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda4(this, 2));
        }
    }

    public final void updateSplitDivisionIfNeeded() {
        if (isInSubDisplay()) {
            if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY) {
                if (this.mTopStageAfterFoldDismiss != -1) {
                    return;
                }
            }
            int i = !isLandscape() ? 1 : 0;
            if (this.mSplitDivision == i) {
                return;
            }
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("Update split division for SubDisplay. d=", i, "  Call=");
            m1m.append(Debug.getCallers(5));
            Slog.i("StageCoordinator", m1m.toString());
            setSplitDivision(i, isInSubDisplay());
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
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                this.mLastReportedSideStageWinConfigPosition = sideStageWinConfigPosition;
                this.mLastReportedCellStageWinConfigPosition = i;
                windowContainerTransaction.setStagePosition(this.mCellStage.mRootTaskInfo.token, i);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSurfaceBounds(SplitLayout splitLayout, SurfaceControl.Transaction transaction, boolean z) {
        Rect bounds1;
        Rect refHostBounds;
        SurfaceControl cellDividerLeash;
        int i = this.mSideStagePosition;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        StageTaskListener stageTaskListener3 = i == 0 ? stageTaskListener : stageTaskListener2;
        if (i == 0) {
            stageTaskListener = stageTaskListener2;
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            CellStage cellStage = this.mCellStage;
            if (cellStage.mIsActive) {
                if (splitLayout == null) {
                    splitLayout = this.mSplitLayout;
                }
                SurfaceControl surfaceControl = stageTaskListener3.mRootLeash;
                SurfaceControl surfaceControl2 = stageTaskListener.mRootLeash;
                SurfaceControl surfaceControl3 = stageTaskListener3.mDimLayer;
                SurfaceControl surfaceControl4 = stageTaskListener.mDimLayer;
                SurfaceControl surfaceControl5 = cellStage.mRootLeash;
                SurfaceControl dividerLeash = splitLayout.getDividerLeash();
                Rect rect = splitLayout.mRootBounds;
                Rect rect2 = splitLayout.mTempRect;
                if (dividerLeash != null) {
                    Rect rect3 = new Rect(splitLayout.mDividerBounds);
                    rect3.offset(-rect.left, -rect.top);
                    rect2.set(rect3);
                    transaction.setPosition(dividerLeash, rect2.left, rect2.top);
                    transaction.setLayer(dividerLeash, Integer.MAX_VALUE);
                }
                if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && splitLayout.mStageCoordinator.isMultiSplitActive() && (cellDividerLeash = splitLayout.getCellDividerLeash()) != null) {
                    rect2.set(splitLayout.getRefCellDividerBounds());
                    transaction.setPosition(cellDividerLeash, rect2.left, rect2.top);
                    transaction.setLayer(cellDividerLeash, Integer.MAX_VALUE);
                }
                if (CellUtil.isCellInLeftOrTopBounds(splitLayout.mCellStageWindowConfigPosition, splitLayout.isVerticalDivision())) {
                    bounds1 = splitLayout.getRefHostBounds();
                    refHostBounds = splitLayout.getBounds2();
                    refHostBounds.offset(-rect.left, -rect.top);
                } else {
                    bounds1 = splitLayout.getBounds1();
                    bounds1.offset(-rect.left, -rect.top);
                    refHostBounds = splitLayout.getRefHostBounds();
                }
                Rect rect4 = new Rect(splitLayout.mBounds3);
                rect4.offset(-rect.left, -rect.top);
                transaction.setPosition(surfaceControl, bounds1.left, bounds1.top).setWindowCrop(surfaceControl, bounds1.width(), bounds1.height());
                transaction.setPosition(surfaceControl2, refHostBounds.left, refHostBounds.top).setWindowCrop(surfaceControl2, refHostBounds.width(), refHostBounds.height());
                transaction.setPosition(surfaceControl5, rect4.left, rect4.top).setWindowCrop(surfaceControl5, rect4.width(), rect4.height());
                if (!CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME || !splitLayout.mStageCoordinator.isMultiSplitActive()) {
                    splitLayout.mImePositionProcessor.adjustSurfaceLayoutForIme(transaction, dividerLeash, surfaceControl, surfaceControl2, surfaceControl3, surfaceControl4);
                    return;
                }
                SurfaceControl cellDividerLeash2 = splitLayout.getCellDividerLeash();
                SplitLayout.ImePositionProcessor imePositionProcessor = splitLayout.mImePositionProcessor;
                if (imePositionProcessor.mYOffsetForIme == 0) {
                    return;
                }
                SplitLayout splitLayout2 = SplitLayout.this;
                if (dividerLeash != null) {
                    splitLayout2.mTempRect.set(splitLayout2.mDividerBounds);
                    int i2 = imePositionProcessor.mYOffsetForIme;
                    splitLayout2.mTempRect.offset(0, i2);
                    transaction.setPosition(dividerLeash, r3.left, r3.top);
                }
                if (cellDividerLeash2 != null) {
                    splitLayout2.mTempRect.set(splitLayout2.mCellDividerBounds);
                    int i3 = imePositionProcessor.mYOffsetForIme;
                    splitLayout2.mTempRect.offset(0, i3);
                    transaction.setPosition(cellDividerLeash2, r3.left, r3.top);
                }
                boolean isVerticalDivision = splitLayout2.isVerticalDivision();
                Rect rect5 = splitLayout2.mBounds3;
                Rect rect6 = splitLayout2.mHostBounds;
                Rect rect7 = splitLayout2.mTempRect;
                if (!isVerticalDivision) {
                    int i4 = splitLayout2.mCellStageWindowConfigPosition;
                    if ((i4 & 16) != 0) {
                        transaction.setWindowCrop(surfaceControl, rect6.width(), rect6.height() + imePositionProcessor.mYOffsetForIme);
                        transaction.setWindowCrop(surfaceControl5, rect5.width(), rect5.height() + imePositionProcessor.mYOffsetForIme);
                        rect7.set(splitLayout2.mBounds2);
                        rect7.offset(0, imePositionProcessor.mYOffsetForIme);
                        transaction.setPosition(surfaceControl2, rect7.left, rect7.top);
                        return;
                    }
                    if ((i4 & 64) != 0) {
                        Rect rect8 = splitLayout2.mBounds1;
                        transaction.setWindowCrop(surfaceControl, rect8.width(), rect8.height() + imePositionProcessor.mYOffsetForIme);
                        rect7.set(rect6);
                        rect7.offset(0, imePositionProcessor.mYOffsetForIme);
                        transaction.setPosition(surfaceControl2, rect7.left, rect7.top);
                        rect7.set(rect5);
                        rect7.offset(0, imePositionProcessor.mYOffsetForIme);
                        transaction.setPosition(surfaceControl5, rect7.left, rect7.top);
                        return;
                    }
                    return;
                }
                int i5 = splitLayout2.mCellStageWindowConfigPosition;
                if ((i5 & 8) != 0) {
                    if ((i5 & 64) != 0) {
                        transaction.setWindowCrop(surfaceControl, rect6.width(), rect6.height() + imePositionProcessor.mYOffsetForIme);
                        rect7.set(rect5);
                        rect7.offset(0, imePositionProcessor.mYOffsetForIme);
                        transaction.setPosition(surfaceControl5, rect7.left, rect7.top);
                        return;
                    }
                    transaction.setWindowCrop(surfaceControl5, rect5.width(), rect5.height() + imePositionProcessor.mYOffsetForIme);
                    rect7.set(rect6);
                    rect7.offset(0, imePositionProcessor.mYOffsetForIme);
                    transaction.setPosition(surfaceControl, rect7.left, rect7.top);
                    return;
                }
                if ((i5 & 32) != 0) {
                    if ((i5 & 64) != 0) {
                        transaction.setWindowCrop(surfaceControl2, rect6.width(), rect6.height() + imePositionProcessor.mYOffsetForIme);
                        rect7.set(rect5);
                        rect7.offset(0, imePositionProcessor.mYOffsetForIme);
                        transaction.setPosition(surfaceControl5, rect7.left, rect7.top);
                        return;
                    }
                    transaction.setWindowCrop(surfaceControl5, rect5.width(), rect5.height() + imePositionProcessor.mYOffsetForIme);
                    rect7.set(rect6);
                    rect7.offset(0, imePositionProcessor.mYOffsetForIme);
                    transaction.setPosition(surfaceControl2, rect7.left, rect7.top);
                    return;
                }
                return;
            }
        }
        if (splitLayout == null) {
            splitLayout = this.mSplitLayout;
        }
        SurfaceControl surfaceControl6 = stageTaskListener3.mRootLeash;
        SurfaceControl surfaceControl7 = stageTaskListener.mRootLeash;
        SurfaceControl surfaceControl8 = stageTaskListener3.mDimLayer;
        SurfaceControl surfaceControl9 = stageTaskListener.mDimLayer;
        SurfaceControl dividerLeash2 = splitLayout.getDividerLeash();
        Rect rect9 = splitLayout.mTempRect;
        if (dividerLeash2 != null) {
            splitLayout.getRefDividerBounds(rect9);
            transaction.setPosition(dividerLeash2, rect9.left, rect9.top);
            transaction.setLayer(dividerLeash2, Integer.MAX_VALUE);
        }
        splitLayout.getRefBounds1(rect9);
        transaction.setPosition(surfaceControl6, rect9.left, rect9.top).setWindowCrop(surfaceControl6, rect9.width(), rect9.height());
        splitLayout.getRefBounds2(rect9);
        transaction.setPosition(surfaceControl7, rect9.left, rect9.top).setWindowCrop(surfaceControl7, rect9.width(), rect9.height());
        if (splitLayout.mImePositionProcessor.adjustSurfaceLayoutForIme(transaction, dividerLeash2, surfaceControl6, surfaceControl7, surfaceControl8, surfaceControl9)) {
            return;
        }
        SplitLayout.ResizingEffectPolicy resizingEffectPolicy = splitLayout.mSurfaceEffectPolicy;
        int i6 = resizingEffectPolicy.mDismissingSide;
        if (i6 != 1 && i6 != 2) {
            if (i6 != 3 && i6 != 4) {
                transaction.setAlpha(surfaceControl8, 0.0f).hide(surfaceControl8);
                transaction.setAlpha(surfaceControl9, 0.0f).hide(surfaceControl9);
                if (z) {
                    return;
                }
                SplitLayout splitLayout3 = SplitLayout.this;
                int i7 = resizingEffectPolicy.mParallaxType;
                if (i7 == 1) {
                    int i8 = resizingEffectPolicy.mDismissingSide;
                    if (i8 == 1 || i8 == 2) {
                        splitLayout3.mTempRect.set(splitLayout3.mBounds1);
                    } else {
                        if (i8 == 3 || i8 == 4) {
                            splitLayout3.mTempRect.set(splitLayout3.mBounds2);
                            surfaceControl6 = surfaceControl7;
                        }
                        surfaceControl6 = null;
                    }
                } else {
                    if (i7 == 2) {
                        int i9 = resizingEffectPolicy.mShrinkSide;
                        if (i9 == 1 || i9 == 2) {
                            splitLayout3.mTempRect.set(splitLayout3.mBounds1);
                        } else if (i9 == 3 || i9 == 4) {
                            splitLayout3.mTempRect.set(splitLayout3.mBounds2);
                            surfaceControl6 = surfaceControl7;
                        }
                    }
                    surfaceControl6 = null;
                }
                if (i7 == 0 || surfaceControl6 == null) {
                    return;
                }
                int i10 = splitLayout3.mTempRect.left;
                Point point = resizingEffectPolicy.mParallaxOffset;
                transaction.setPosition(surfaceControl6, i10 + point.x, r0.top + point.y);
                int i11 = -point.x;
                int i12 = -point.y;
                Rect rect10 = splitLayout3.mTempRect;
                rect10.offsetTo(i11, i12);
                transaction.setWindowCrop(surfaceControl6, rect10);
                return;
            }
            surfaceControl8 = surfaceControl9;
        }
        transaction.setAlpha(surfaceControl8, resizingEffectPolicy.mDismissingDimValue).setVisibility(surfaceControl8, resizingEffectPolicy.mDismissingDimValue > 0.001f);
        if (z) {
        }
    }

    public final void updateVideoControlsState(boolean z, SurfaceControl.Transaction transaction, boolean z2) {
        if (this.mIsVideoControls == z) {
            return;
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(3, RowView$$ExternalSyntheticOutline0.m49m("updateVideoControlsState: ", z, ", callers"), "StageCoordinator");
        if (!z) {
            setVideoControlsMode(transaction, false);
            setDividerSizeIfNeeded(z2);
            if (this.mMainStage.mIsActive) {
                setDividerVisibility(true, null);
                this.mSplitLayout.update(null);
                return;
            }
            return;
        }
        new WindowContainerTransaction();
        setDividerVisibility(false, null);
        setVideoControlsMode(transaction, true);
        if (this.mIsFlexPanelMode) {
            this.mIsFlexPanelMode = false;
            if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
                updateCornerRadiusForStages(null);
            }
        }
        setDividerSizeIfNeeded(z2);
    }

    public final boolean updateWindowBounds(SplitLayout splitLayout, WindowContainerTransaction windowContainerTransaction, boolean z) {
        int i = this.mSideStagePosition;
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        StageTaskListener stageTaskListener3 = i == 0 ? stageTaskListener : stageTaskListener2;
        if (i == 0) {
            stageTaskListener = stageTaskListener2;
        }
        overrideStageCoordinatorRootConfig(windowContainerTransaction);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            CellStage cellStage = this.mCellStage;
            if (cellStage.mIsActive || z) {
                return splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener3.mRootTaskInfo, stageTaskListener.mRootTaskInfo, cellStage.mRootTaskInfo);
            }
        }
        return splitLayout.applyTaskChanges(windowContainerTransaction, stageTaskListener3.mRootTaskInfo, stageTaskListener.mRootTaskInfo);
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
        StageTaskListener stageTaskListener = this.mSideStage;
        StageTaskListener stageTaskListener2 = this.mMainStage;
        if (i == 0) {
            if (i2 != -1) {
                setSideStagePosition(SplitScreenUtils.reverseSplitPosition(i2), i3, windowContainerTransaction, true);
            } else {
                i2 = getMainStagePosition();
            }
            if (bundle == null) {
                bundle = new Bundle();
            }
            if (i2 != this.mSideStagePosition) {
                stageTaskListener = stageTaskListener2;
            }
            addActivityOptions(bundle, stageTaskListener);
            return bundle;
        }
        if (i != 1) {
            throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown stage=", i));
        }
        if (i2 != -1) {
            setSideStagePosition(i2, i3, windowContainerTransaction, true);
        } else {
            i2 = this.mSideStagePosition;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (i2 != this.mSideStagePosition) {
            stageTaskListener = stageTaskListener2;
        }
        addActivityOptions(bundle, stageTaskListener);
        return bundle;
    }

    public final void setSideStagePosition(int i, int i2, WindowContainerTransaction windowContainerTransaction, boolean z) {
        boolean splitDivision = CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? setSplitDivision(i2, isInSubDisplay()) : false;
        if (this.mSideStagePosition != i || (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && splitDivision)) {
            this.mSideStagePosition = i;
            ArrayList arrayList = (ArrayList) this.mListeners;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                SplitScreen.SplitScreenListener splitScreenListener = (SplitScreen.SplitScreenListener) arrayList.get(size);
                splitScreenListener.onStagePositionChanged(0, getMainStagePosition());
                splitScreenListener.onStagePositionChanged(1, this.mSideStagePosition);
            }
            if (this.mSideStageListener.mVisible && z) {
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

    public final void startWithLegacyTransition(WindowContainerTransaction windowContainerTransaction, int i, PendingIntent pendingIntent, Intent intent, ShortcutInfo shortcutInfo, Bundle bundle, int i2, float f, final RemoteAnimationAdapter remoteAnimationAdapter, InstanceId instanceId) {
        if (!isSplitScreenVisible()) {
            exitSplitScreen(null, 10);
        }
        this.mSplitLayout.init();
        this.mSplitLayout.setDivideRatio(f, true, true);
        TransactionPool transactionPool = this.mTransactionPool;
        SurfaceControl.Transaction acquire = transactionPool.acquire();
        int i3 = 0;
        updateSurfaceBounds(this.mSplitLayout, acquire, false);
        acquire.apply();
        transactionPool.release(acquire);
        this.mShouldUpdateRecents = false;
        this.mIsDividerRemoteAnimating = true;
        if (this.mSplitRequest == null) {
            this.mSplitRequest = new SplitRequest(this, i, pendingIntent != null ? pendingIntent.getIntent() : null, i2);
        }
        setSideStagePosition(windowContainerTransaction, i2);
        MainStage mainStage = this.mMainStage;
        if (!mainStage.mIsActive) {
            mainStage.activate(windowContainerTransaction, false);
        }
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        addActivityOptions(bundle2, mainStage);
        updateWindowBounds(this.mSplitLayout, windowContainerTransaction, false);
        updateStagePositionIfNeeded(windowContainerTransaction);
        windowContainerTransaction.reorder(this.mRootTaskInfo.token, true);
        setRootForceTranslucent(windowContainerTransaction, false);
        SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
        if (i != -1) {
            final WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            if (isSplitScreenVisible()) {
                mainStage.evictAllChildren(windowContainerTransaction2, false);
                this.mSideStage.evictAllChildren(windowContainerTransaction2, false);
            }
            RemoteAnimationAdapter remoteAnimationAdapter2 = new RemoteAnimationAdapter(new IRemoteAnimationRunner.Stub() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.4
                public final void onAnimationCancelled() {
                    StageCoordinator.m2757$$Nest$monRemoteAnimationFinishedOrCancelled(StageCoordinator.this, windowContainerTransaction2);
                    StageCoordinator.this.setDividerVisibility(true, null);
                    try {
                        remoteAnimationAdapter.getRunner().onAnimationCancelled();
                    } catch (RemoteException e) {
                        Slog.e("StageCoordinator", "Error starting remote animation", e);
                    }
                }

                public final void onAnimationStart(int i4, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                    IRemoteAnimationFinishedCallback.Stub stub = new IRemoteAnimationFinishedCallback.Stub() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.4.1
                        public final void onAnimationFinished() {
                            C41184 c41184 = C41184.this;
                            StageCoordinator.m2757$$Nest$monRemoteAnimationFinishedOrCancelled(StageCoordinator.this, windowContainerTransaction2);
                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                        }
                    };
                    Transitions.setRunningRemoteTransitionDelegate(remoteAnimationAdapter.getCallingApplication());
                    try {
                        remoteAnimationAdapter.getRunner().onAnimationStart(i4, remoteAnimationTargetArr, remoteAnimationTargetArr2, (RemoteAnimationTarget[]) ArrayUtils.appendElement(RemoteAnimationTarget.class, remoteAnimationTargetArr3, StageCoordinator.this.getDividerBarLegacyTarget()), stub);
                    } catch (RemoteException e) {
                        Slog.e("StageCoordinator", "Error starting remote animation", e);
                    }
                }
            }, remoteAnimationAdapter.getDuration(), remoteAnimationAdapter.getStatusBarTransitionDelay());
            ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle2);
            fromBundle.update(ActivityOptions.makeRemoteAnimation(remoteAnimationAdapter2));
            windowContainerTransaction.startTask(i, fromBundle.toBundle());
            syncTransactionQueue.queue(windowContainerTransaction);
        } else {
            if (shortcutInfo != null) {
                windowContainerTransaction.startShortcut(this.mContext.getPackageName(), shortcutInfo, bundle2);
            } else {
                windowContainerTransaction.sendPendingIntent(pendingIntent, intent, bundle2);
            }
            syncTransactionQueue.queue(new StageCoordinator$$ExternalSyntheticLambda12(this, remoteAnimationAdapter, i3), windowContainerTransaction);
        }
        if (instanceId != null) {
            SplitscreenEventLogger splitscreenEventLogger = this.mLogger;
            splitscreenEventLogger.mEnterSessionId = instanceId;
            splitscreenEventLogger.mEnterReason = 3;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SplitRequest {
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

        public SplitRequest(StageCoordinator stageCoordinator, int i, int i2, int i3) {
            this.mActivateTaskId = i;
            this.mActivateTaskId2 = i2;
            this.mActivatePosition = i3;
        }
    }

    public final StageTaskListener getStageOfTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        CellStage cellStage;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        MainStage mainStage = this.mMainStage;
        ActivityManager.RunningTaskInfo runningTaskInfo3 = mainStage.mRootTaskInfo;
        if (runningTaskInfo3 != null && runningTaskInfo.parentTaskId == runningTaskInfo3.taskId) {
            return mainStage;
        }
        SideStage sideStage = this.mSideStage;
        ActivityManager.RunningTaskInfo runningTaskInfo4 = sideStage.mRootTaskInfo;
        if (runningTaskInfo4 != null && runningTaskInfo.parentTaskId == runningTaskInfo4.taskId) {
            return sideStage;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && (runningTaskInfo2 = (cellStage = this.mCellStage).mRootTaskInfo) != null && runningTaskInfo.parentTaskId == runningTaskInfo2.taskId) {
            return cellStage;
        }
        return null;
    }

    public void onSplitScreenEnter() {
    }

    public void onSplitScreenExit() {
    }

    public StageCoordinator(Context context, int i, SyncTransactionQueue syncTransactionQueue, ShellTaskOrganizer shellTaskOrganizer, MainStage mainStage, SideStage sideStage, DisplayController displayController, DisplayImeController displayImeController, DisplayInsetsController displayInsetsController, SplitLayout splitLayout, Transitions transitions, TransactionPool transactionPool, ShellExecutor shellExecutor, Optional<RecentTasksController> optional) {
        new SurfaceSession();
        this.mMainStageListener = new StageListenerImpl();
        this.mSideStageListener = new StageListenerImpl();
        this.mCellStageListener = new StageListenerImpl();
        int i2 = 0;
        this.mCellStageWindowConfigPosition = 0;
        this.mSideStagePosition = 1;
        ArrayList arrayList = new ArrayList();
        this.mListeners = arrayList;
        this.mPausingTasks = new ArrayList();
        this.mTempRect1 = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRect3 = new Rect();
        this.mTopStageAfterFoldDismiss = -1;
        this.mIsFolded = false;
        this.mIsFlexPanelMode = false;
        this.mSkipFlexPanelUpdate = false;
        this.mIsVideoControls = false;
        this.mWillBeVideoControls = false;
        this.mDelayedHandleLayoutSizeChange = new StageCoordinator$$ExternalSyntheticLambda3(this, 3);
        this.mLastPackageNameList = new ArrayList();
        this.mCurrentPackageNameList = new ArrayList();
        this.mExcludeLoggingPackages = Arrays.asList("com.sec.android.app.launcher", "com.android.systemui");
        this.mSeqForAsyncTransaction = -1L;
        this.mLastTransactionType = 0;
        this.mTempRect = new Rect();
        Configuration configuration = new Configuration();
        this.mLastConfiguration = configuration;
        this.mParentContainerCallbacks = new C41151();
        if (CoreRune.MW_MULTI_SPLIT_BACKGROUND) {
            new RecentsTransitionCallback(this, i2);
        }
        this.mSplitDivision = 0;
        this.mLastMainSplitDivision = 0;
        this.mSplitLayoutChangedForLaunchAdjacent = false;
        this.mAppPairStarted = false;
        new ArrayList();
        new ArrayList();
        new ArrayList();
        new ArrayList();
        this.mContext = context;
        this.mDisplayId = i;
        this.mSyncQueue = syncTransactionQueue;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mMainStage = mainStage;
        this.mSideStage = sideStage;
        this.mDisplayController = displayController;
        this.mDisplayImeController = displayImeController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mTransactionPool = transactionPool;
        this.mSplitLayout = splitLayout;
        this.mSplitTransitions = new SplitScreenTransitions(transactionPool, transitions, new StageCoordinator$$ExternalSyntheticLambda3(this, 5), this);
        this.mLogger = new SplitscreenEventLogger();
        this.mMainExecutor = shellExecutor;
        this.mRecentTasks = optional;
        displayController.addDisplayWindowListener(this);
        this.mSplitLayout.mStageCoordinator = this;
        transitions.addHandler(this);
        this.mSplitUnsupportedToast = Toast.makeText(context, R.string.dock_non_resizeble_failed_to_dock_text, 0);
        SplitBackgroundController splitBackgroundController = new SplitBackgroundController(context, this, transactionPool, shellExecutor, displayController);
        this.mSplitBackgroundController = splitBackgroundController;
        if (!arrayList.contains(splitBackgroundController)) {
            arrayList.add(splitBackgroundController);
            sendStatusToListener(splitBackgroundController);
        }
        configuration.updateFrom(context.getResources().getConfiguration());
    }
}
