package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.ArrayUtils;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.split.SplitDecorManager;
import com.android.wm.shell.common.split.SplitLayout;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.split.SplitScreenConstants;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.multiwindow.TaskOrganizerInfo;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StageTaskListener implements ShellTaskOrganizer.TaskListener {
    public final StageListenerCallbacks mCallbacks;
    public final SparseArray mChildrenLeashes;
    public final RunningTaskInfoList mChildrenTaskInfo;
    public final Context mContext;
    public float mCornerRadiusForLeash;
    public SurfaceControl mDimLayer;
    public StageTaskListener mHost;
    public final IconProvider mIconProvider;
    public final int mId;
    public boolean mIsActive;
    public SurfaceControl mRootLeash;
    public ActivityManager.RunningTaskInfo mRootTaskInfo;
    public final RunningTaskInfoList mRunningTaskInfoList;
    public SplitDecorManager mSplitDecorManager;
    public final int mStageType;
    public final SyncTransactionQueue mSyncQueue;
    public boolean mToSplit;
    public final Optional mWindowDecorViewModel;
    public boolean mHasRootTask = false;
    public boolean mVisible = false;
    public boolean mHasChildren = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RunningTaskInfoList extends SparseArray {
        public final ArraySet mClosingTaskIds;
        public final ArrayList mInfos;
        public final ArrayList mTaskIds;

        public /* synthetic */ RunningTaskInfoList(StageTaskListener stageTaskListener, int i) {
            this();
        }

        @Override // android.util.SparseArray
        public final boolean contains(int i) {
            return this.mTaskIds.contains(Integer.valueOf(i));
        }

        @Override // android.util.SparseArray
        public final Object get(int i) {
            return (ActivityManager.RunningTaskInfo) this.mInfos.get(this.mTaskIds.indexOf(Integer.valueOf(i)));
        }

        @Override // android.util.SparseArray
        public final int keyAt(int i) {
            return ((Integer) this.mTaskIds.get(i)).intValue();
        }

        @Override // android.util.SparseArray
        public final void put(int i, Object obj) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
            if (contains(i)) {
                int indexOf = this.mTaskIds.indexOf(Integer.valueOf(i));
                this.mTaskIds.remove(indexOf);
                this.mInfos.remove(indexOf);
                if (!runningTaskInfo.isTopTaskInStage) {
                    this.mTaskIds.add(indexOf, Integer.valueOf(i));
                    this.mInfos.add(indexOf, runningTaskInfo);
                    return;
                }
            }
            this.mTaskIds.add(Integer.valueOf(i));
            this.mInfos.add(runningTaskInfo);
        }

        @Override // android.util.SparseArray
        public final void remove(int i) {
            int indexOf = this.mTaskIds.indexOf(Integer.valueOf(i));
            this.mTaskIds.remove(indexOf);
            this.mInfos.remove(indexOf);
            if (CoreRune.MW_SPLIT_STACKING && this.mClosingTaskIds.contains(Integer.valueOf(i))) {
                this.mClosingTaskIds.remove(Integer.valueOf(i));
                Slog.d("StageTaskListener", "removeToClosingTaskIds: #" + i + ", " + StageTaskListener.this);
            }
        }

        @Override // android.util.SparseArray
        public final int size() {
            return this.mTaskIds.size();
        }

        @Override // android.util.SparseArray
        public final Object valueAt(int i) {
            return (ActivityManager.RunningTaskInfo) this.mInfos.get(i);
        }

        private RunningTaskInfoList() {
            this.mTaskIds = new ArrayList();
            this.mInfos = new ArrayList();
            this.mClosingTaskIds = new ArraySet();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface StageListenerCallbacks {
        void onRootTaskAppeared();
    }

    public StageTaskListener(Context context, ShellTaskOrganizer shellTaskOrganizer, int i, StageListenerCallbacks stageListenerCallbacks, SyncTransactionQueue syncTransactionQueue, IconProvider iconProvider, Optional<WindowDecorViewModel> optional, int i2) {
        RunningTaskInfoList runningTaskInfoList = new RunningTaskInfoList(this, 0);
        this.mChildrenTaskInfo = runningTaskInfoList;
        this.mChildrenLeashes = new SparseArray();
        this.mToSplit = false;
        this.mContext = context;
        this.mCallbacks = stageListenerCallbacks;
        this.mSyncQueue = syncTransactionQueue;
        this.mIconProvider = iconProvider;
        this.mWindowDecorViewModel = optional;
        this.mRunningTaskInfoList = runningTaskInfoList;
        if (i2 == 0) {
            this.mStageType = 1;
        } else if (i2 == 1) {
            this.mStageType = 2;
        } else if (i2 == 5) {
            this.mStageType = 4;
        }
        shellTaskOrganizer.createStageRootTask(i, this.mStageType, this);
        this.mId = i2;
    }

    public static void evictChild(WindowContainerTransaction windowContainerTransaction, TaskInfo taskInfo, String str) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -126229337257133247L, 1, Long.valueOf(taskInfo.taskId), str);
        }
        taskInfo.isVisible = false;
        taskInfo.isVisibleRequested = false;
        windowContainerTransaction.reparent(taskInfo.token, (WindowContainerToken) null, false);
    }

    public final void activate(WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (this.mIsActive) {
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 1229395835454307803L, 3, Boolean.valueOf(z), String.valueOf(SplitScreen.stageTypeToString(this.mId)));
        }
        if (z) {
            windowContainerTransaction.reparentTasks((WindowContainerToken) null, this.mRootTaskInfo.token, SplitScreenConstants.CONTROLLED_WINDOWING_MODES, SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, true, true);
        }
        this.mIsActive = true;
    }

    public final void adjustChildTaskWindowingModeIfNeeded(WindowContainerTransaction windowContainerTransaction) {
        if (this.mContext.getResources().getConfiguration().isNewDexMode()) {
            for (int size = this.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size--) {
                windowContainerTransaction.orderedSetWindowingMode(((ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.valueAt(size)).token, 1);
            }
        }
    }

    public final boolean applyCornerRadiusToLeashIfNeeded(float f, SurfaceControl.Transaction transaction, boolean z) {
        SurfaceControl surfaceControl = this.mRootLeash;
        if (surfaceControl == null) {
            return false;
        }
        if (this.mCornerRadiusForLeash == f && !z) {
            return false;
        }
        this.mCornerRadiusForLeash = f;
        transaction.setCornerRadius(surfaceControl, f);
        return true;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
        builder.setParent(findTaskSurface$3(i));
    }

    public final boolean contains(Predicate predicate) {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        return (runningTaskInfo != null && predicate.test(runningTaskInfo)) || getChildTaskInfo(predicate) != null;
    }

    public final boolean containsToken(WindowContainerToken windowContainerToken) {
        return contains(new StageTaskListener$$ExternalSyntheticLambda0(windowContainerToken, 0));
    }

    public final void deactivate(WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (this.mIsActive) {
            boolean z2 = ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0];
            int i = this.mId;
            if (z2) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 3842565199568450392L, 3, Boolean.valueOf(z), String.valueOf(this.mRootTaskInfo), String.valueOf(SplitScreen.stageTypeToString(i)));
            }
            this.mIsActive = false;
            if (this.mRootTaskInfo == null) {
                return;
            }
            if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && i == 5 && z && !this.mToSplit) {
                adjustChildTaskWindowingModeIfNeeded(windowContainerTransaction);
            }
            windowContainerTransaction.reparentTasks(this.mRootTaskInfo.token, (WindowContainerToken) null, (int[]) null, (int[]) null, z);
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 5) {
                this.mHost = null;
                windowContainerTransaction.reorder(this.mRootTaskInfo.token, false);
            }
        }
    }

    public final void doForAllChildTasks(Consumer consumer) {
        for (int size = this.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size--) {
            consumer.accept(Integer.valueOf(((ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.valueAt(size)).taskId));
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void dump$2(PrintWriter printWriter, String str) {
        RunningTaskInfoList runningTaskInfoList;
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        String m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "  ");
        if (this.mChildrenTaskInfo.mTaskIds.size() > 0) {
            QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "Children list:");
            for (int size = this.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size += -1) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.valueAt(size);
                printWriter.println(m2 + "Task#" + size + " taskID=" + runningTaskInfo.taskId + " baseActivity=" + runningTaskInfo.baseActivity);
            }
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "mHasRootTask="), this.mHasRootTask, printWriter, str, "mVisible="), this.mVisible, printWriter, str, "mHasChildren="), this.mHasChildren, printWriter);
        if (!CoreRune.MW_SPLIT_STACKING || (runningTaskInfoList = this.mRunningTaskInfoList) == null || runningTaskInfoList.mClosingTaskIds.isEmpty()) {
            return;
        }
        StringBuilder m3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m, "ClosingTaskIds=");
        m3.append(runningTaskInfoList.mClosingTaskIds);
        printWriter.println(m3.toString());
    }

    public final void evictAllChildren(WindowContainerTransaction windowContainerTransaction) {
        for (int size = this.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size--) {
            evictChild(windowContainerTransaction, (ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.valueAt(size), SystemUIAnalytics.QPNE_VID_COVER_ALL);
        }
    }

    public final void evictInvisibleChildren(WindowContainerTransaction windowContainerTransaction) {
        for (int size = this.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size--) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.valueAt(size);
            if (!runningTaskInfo.isVisible) {
                evictChild(windowContainerTransaction, runningTaskInfo, "invisible");
            }
        }
    }

    public final void evictNonOpeningChildren(RemoteAnimationTarget[] remoteAnimationTargetArr, WindowContainerTransaction windowContainerTransaction) {
        SparseArray clone = this.mChildrenTaskInfo.clone();
        for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
            if (remoteAnimationTarget.mode == 0) {
                clone.remove(remoteAnimationTarget.taskId);
            }
        }
        for (int size = clone.size() - 1; size >= 0; size--) {
            evictChild(windowContainerTransaction, (ActivityManager.RunningTaskInfo) clone.valueAt(size), "non-opening");
        }
    }

    public final void evictOtherChildren(WindowContainerTransaction windowContainerTransaction, int i) {
        for (int size = this.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size--) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.valueAt(size);
            if (i != runningTaskInfo.taskId) {
                evictChild(windowContainerTransaction, runningTaskInfo, "other");
            }
        }
    }

    public final SurfaceControl findTaskSurface$3(int i) {
        if (this.mRootTaskInfo.taskId == i) {
            return this.mRootLeash;
        }
        if (this.mChildrenLeashes.contains(i)) {
            return (SurfaceControl) this.mChildrenLeashes.get(i);
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "There is no surface for taskId="));
    }

    public final int getChildCount() {
        return this.mChildrenTaskInfo.mTaskIds.size();
    }

    public final ActivityManager.RunningTaskInfo getChildTaskInfo(Predicate predicate) {
        for (int size = this.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size--) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.valueAt(size);
            if (predicate.test(runningTaskInfo)) {
                return runningTaskInfo;
            }
        }
        return null;
    }

    public final int getTopChildTaskUid() {
        ActivityManager.RunningTaskInfo childTaskInfo = getChildTaskInfo(new StageTaskListener$$ExternalSyntheticLambda1(2));
        if (childTaskInfo != null) {
            return childTaskInfo.topActivityInfo.applicationInfo.uid;
        }
        return 0;
    }

    public final ActivityManager.RunningTaskInfo getTopRunningTaskInfo() {
        for (int size = this.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size--) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.valueAt(size);
            if (runningTaskInfo.topActivityInfo != null) {
                return runningTaskInfo;
            }
        }
        return null;
    }

    public final int getTopVisibleChildTaskId() {
        ActivityManager.RunningTaskInfo childTaskInfo = getChildTaskInfo(new StageTaskListener$$ExternalSyntheticLambda1(0));
        if (childTaskInfo != null) {
            return childTaskInfo.taskId;
        }
        return -1;
    }

    public final boolean hasAppsEdgeActivityOnTop() {
        ComponentName componentName;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        return (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null || !MultiWindowUtils.isAppsEdgeActivity(componentName)) ? false : true;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final boolean hasChild() {
        return this.mChildrenTaskInfo.mTaskIds.size() > 0;
    }

    public final boolean isFocused() {
        return contains(new StageTaskListener$$ExternalSyntheticLambda1(1));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final boolean isMultiWindow() {
        return true;
    }

    public final void onResized(SurfaceControl.Transaction transaction) {
        SplitDecorManager splitDecorManager = this.mSplitDecorManager;
        if (splitDecorManager != null) {
            splitDecorManager.onResized(transaction, null);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onSplitLayoutChangeRequested(TaskOrganizerInfo taskOrganizerInfo) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        ActivityManager.RunningTaskInfo runningTaskInfo3;
        WindowContainerTransaction windowContainerTransaction;
        int splitFeasibleMode;
        ActivityManager.RunningTaskInfo runningTaskInfo4;
        boolean z;
        StageCoordinator stageCoordinator = (StageCoordinator) this.mCallbacks;
        stageCoordinator.getClass();
        Slog.d("StageCoordinator", "onSplitLayoutChangeRequested: " + taskOrganizerInfo);
        boolean isChangeToHorizontalSplitLayout = taskOrganizerInfo.isChangeToHorizontalSplitLayout();
        StageTaskListener stageTaskListener = stageCoordinator.mSideStage;
        StageTaskListener stageTaskListener2 = stageCoordinator.mCellStage;
        StageTaskListener stageTaskListener3 = stageCoordinator.mMainStage;
        ShellTaskOrganizer shellTaskOrganizer = stageCoordinator.mTaskOrganizer;
        if (isChangeToHorizontalSplitLayout) {
            if (stageTaskListener3.mIsActive && (stageTaskListener2 == null || !stageTaskListener2.mIsActive)) {
                SplitLayout splitLayout = stageCoordinator.mSplitLayout;
                if (splitLayout.mDividerPosition != splitLayout.getDividePositionByRatio()) {
                    stageCoordinator.mSplitLayout.setDivideRatio(0.5f, true, true);
                    z = true;
                } else {
                    z = false;
                }
                if (stageCoordinator.isVerticalDivision()) {
                    stageCoordinator.rotateMultiSplitWithTransition();
                } else if (z) {
                    WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                    stageCoordinator.updateWindowBounds(stageCoordinator.mSplitLayout, windowContainerTransaction2, false);
                    windowContainerTransaction2.setChangeTransitMode(stageTaskListener3.mRootTaskInfo.token, 1, "change_to_horizontal_split_layout");
                    windowContainerTransaction2.setChangeTransitMode(stageTaskListener.mRootTaskInfo.token, 1, "change_to_horizontal_split_layout");
                    shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
                    SurfaceControl.Transaction acquire = stageCoordinator.mTransactionPool.acquire();
                    stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, acquire, false);
                    stageTaskListener3.onResized(acquire);
                    stageTaskListener.onResized(acquire);
                    acquire.apply();
                }
            }
        } else if (taskOrganizerInfo.isChangeSplitLayoutForLaunchAdjacent()) {
            if (CoreRune.MW_MULTI_SPLIT_CREATE_MODE) {
                stageCoordinator.setSplitCreateMode(taskOrganizerInfo.getSplitScreenCreateMode(), false);
            }
            stageCoordinator.mSplitLayoutChangedForLaunchAdjacent = true;
        }
        int exitSplitScreenTopTaskId = taskOrganizerInfo.getExitSplitScreenTopTaskId();
        int splitToFreeformTaskId = taskOrganizerInfo.getSplitToFreeformTaskId();
        int exitSplitScreenStageType = taskOrganizerInfo.getExitSplitScreenStageType();
        if (exitSplitScreenTopTaskId != -1 || exitSplitScreenStageType != 0) {
            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
            windowContainerTransaction3.setDisplayIdForChangeTransition(0, "split_exit");
            ActivityManager.RunningTaskInfo runningTaskInfo5 = shellTaskOrganizer.getRunningTaskInfo(exitSplitScreenTopTaskId);
            if (runningTaskInfo5 != null) {
                stageCoordinator.maximizeSplitTask(runningTaskInfo5.token, windowContainerTransaction3);
            } else if (exitSplitScreenStageType == 1 && (runningTaskInfo3 = stageTaskListener3.mRootTaskInfo) != null) {
                stageCoordinator.maximizeSplitTask(runningTaskInfo3.token, windowContainerTransaction3);
            } else if (exitSplitScreenStageType == 2 && (runningTaskInfo2 = stageTaskListener.mRootTaskInfo) != null) {
                stageCoordinator.maximizeSplitTask(runningTaskInfo2.token, windowContainerTransaction3);
            } else if (exitSplitScreenStageType == 4 && (runningTaskInfo = stageTaskListener2.mRootTaskInfo) != null) {
                stageCoordinator.maximizeSplitTask(runningTaskInfo.token, windowContainerTransaction3);
            }
        } else if (splitToFreeformTaskId != -1 && (runningTaskInfo4 = shellTaskOrganizer.getRunningTaskInfo(splitToFreeformTaskId)) != null) {
            stageCoordinator.moveSplitToFreeform(runningTaskInfo4.token, null, false);
        }
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && (splitFeasibleMode = taskOrganizerInfo.getSplitFeasibleMode()) != -1) {
            SplitLayout splitLayout2 = stageCoordinator.mSplitLayout;
            int i = splitLayout2.mSplitScreenFeasibleMode;
            if (!CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY || !stageCoordinator.mIsFolded || i == 0 || splitFeasibleMode == 0) {
                if (i == 2 && splitFeasibleMode == 1 && splitLayout2.getDisplayLayout(splitLayout2.mContext) != null) {
                    Rect rect = new Rect();
                    splitLayout2.getDisplayLayout(splitLayout2.mContext).getStableBounds(rect, true);
                    if (splitLayout2.mRootBounds.width() != rect.width() && splitLayout2.mRootBounds.height() != rect.height()) {
                        boolean isSplitScreenFeasible = splitLayout2.isSplitScreenFeasible(true);
                        boolean isSplitScreenFeasible2 = splitLayout2.isSplitScreenFeasible(false);
                        if (isSplitScreenFeasible) {
                            splitLayout2.mPossibleSplitDivision = 1;
                        } else if (isSplitScreenFeasible2) {
                            splitLayout2.mPossibleSplitDivision = 0;
                        }
                        Slog.d("SplitLayout", "possibleSplitDivision=" + splitLayout2.mPossibleSplitDivision);
                    }
                }
                splitLayout2.mSplitScreenFeasibleMode = splitFeasibleMode;
                if (i > splitFeasibleMode) {
                    stageCoordinator.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda11());
                }
                if (splitFeasibleMode == 1 && stageCoordinator.isMultiSplitActive() && !stageCoordinator.isMultiSplitScreenVisible()) {
                    WindowContainerTransaction windowContainerTransaction4 = new WindowContainerTransaction();
                    stageCoordinator.prepareExitSplitScreen(-1, 0, windowContainerTransaction4, true);
                    stageCoordinator.mSplitTransitions.startDismissTransition(windowContainerTransaction4, stageCoordinator, -1, 1, false);
                    stageCoordinator = stageCoordinator;
                } else if (splitFeasibleMode == 1 && stageTaskListener3.mIsActive && !stageCoordinator.isMultiSplitActive()) {
                    int i2 = stageCoordinator.mSplitDivision;
                    int i3 = stageCoordinator.mSplitLayout.mPossibleSplitDivision;
                    if (i2 != i3 && stageCoordinator.setSplitDivision(i3, stageCoordinator.isInSubDisplay(), true)) {
                        WindowContainerTransaction windowContainerTransaction5 = new WindowContainerTransaction();
                        stageCoordinator.onLayoutSizeChanged(stageCoordinator.mSplitLayout, windowContainerTransaction5);
                        shellTaskOrganizer.applyTransaction(windowContainerTransaction5);
                    }
                }
            }
        }
        if (taskOrganizerInfo.getAssistantActivityIntent() != null) {
            Intent assistantActivityIntent = taskOrganizerInfo.getAssistantActivityIntent();
            float requestedSplitRatio = taskOrganizerInfo.getRequestedSplitRatio();
            boolean deferSplitRotationInPort = taskOrganizerInfo.getDeferSplitRotationInPort();
            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(stageCoordinator.mContext, 0, assistantActivityIntent, 201326592, null, UserHandle.SYSTEM);
            if (activityAsUser == null) {
                Slog.w("StageCoordinator", "assistantPendingIntent null");
                return;
            }
            WindowContainerTransaction windowContainerTransaction6 = new WindowContainerTransaction();
            Bundle resolveStartStage = stageCoordinator.resolveStartStage(-1, 1, null, windowContainerTransaction6, CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? (stageCoordinator.isLandscape() || deferSplitRotationInPort) ? 0 : 1 : -1);
            float f = (stageCoordinator.isLandscape() || deferSplitRotationInPort) ? requestedSplitRatio : 0.5f;
            windowContainerTransaction6.sendPendingIntent(activityAsUser, assistantActivityIntent, resolveStartStage);
            if (!stageTaskListener3.mIsActive) {
                windowContainerTransaction = windowContainerTransaction6;
                stageCoordinator.prepareActiveSplit(windowContainerTransaction, null, -1, false, f);
            } else {
                if (stageCoordinator.isSplitScreenVisible()) {
                    assistantActivityIntent.setAiKeyAppLaunch(false);
                    stageCoordinator.mSplitLayout.setDivideRatio(f, true, true);
                    windowContainerTransaction6.setDisplayIdForChangeTransition(stageCoordinator.mDisplayId, "ai_hot_key");
                    stageCoordinator.onLayoutSizeChanged(stageCoordinator.mSplitLayout, windowContainerTransaction6);
                    shellTaskOrganizer.applyTransaction(windowContainerTransaction6);
                    return;
                }
                windowContainerTransaction = windowContainerTransaction6;
                stageCoordinator.prepareBringSplit(windowContainerTransaction, null, -1, false, f);
            }
            if (CoreRune.MW_SPLIT_SHELL_TRANSITION && deferSplitRotationInPort) {
                windowContainerTransaction.setChangeTransitionRequest(3);
            }
            StageCoordinator stageCoordinator2 = stageCoordinator;
            stageCoordinator2.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, stageCoordinator2, VolteConstants.ErrorCode.CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE, false, 1);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onSplitPairUpdateRequested() {
        StageCoordinator stageCoordinator = (StageCoordinator) this.mCallbacks;
        if (stageCoordinator.mMainStage.mIsActive) {
            stageCoordinator.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda14(2, stageCoordinator));
        }
    }

    public final void onSplitScreenListenerRegistered(SplitScreen.SplitScreenListener splitScreenListener, int i) {
        for (int size = this.mChildrenTaskInfo.mTaskIds.size() - 1; size >= 0; size--) {
            int keyAt = this.mChildrenTaskInfo.keyAt(size);
            splitScreenListener.onTaskStageChanged(keyAt, i, ((ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.get(keyAt)).isVisible);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskAppeared(final ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        boolean z;
        boolean z2 = false;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -8560201308705507262L, 21, Long.valueOf(runningTaskInfo.taskId), Long.valueOf(runningTaskInfo.parentTaskId), Long.valueOf(this.mRootTaskInfo != null ? r0.taskId : -1L), String.valueOf(SplitScreen.stageTypeToString(this.mId)), String.valueOf(runningTaskInfo.baseActivity));
        }
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mRootTaskInfo;
        StageListenerCallbacks stageListenerCallbacks = this.mCallbacks;
        if (runningTaskInfo2 != null) {
            if (runningTaskInfo.parentTaskId != runningTaskInfo2.taskId) {
                throw new IllegalArgumentException(this + "\n Unknown task: " + runningTaskInfo + "\n mRootTaskInfo: " + this.mRootTaskInfo);
            }
            int i = runningTaskInfo.taskId;
            this.mChildrenLeashes.put(i, surfaceControl);
            this.mChildrenTaskInfo.put(i, runningTaskInfo);
            if (runningTaskInfo.isVisible && runningTaskInfo.isVisibleRequested) {
                z2 = true;
            }
            final StageCoordinator stageCoordinator = (StageCoordinator) stageListenerCallbacks;
            stageCoordinator.onChildTaskStatusChanged(this, i, true, z2);
            if (this.mRootTaskInfo.isVisible) {
                stageCoordinator.postDividerPanelAutoOpenIfNeeded();
            }
            if (runningTaskInfo.supportsMultiWindow) {
                return;
            }
            stageCoordinator.getClass();
            ((HandlerExecutor) stageCoordinator.mMainExecutor).executeDelayed(new Runnable() { // from class: com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    StageCoordinator.this.onNoLongerSupportMultiWindow(this, runningTaskInfo);
                }
            }, 500L);
            return;
        }
        this.mRootLeash = surfaceControl;
        if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
            this.mCornerRadiusForLeash = 0.0f;
        }
        this.mRootTaskInfo = runningTaskInfo;
        this.mSplitDecorManager = new SplitDecorManager(this.mRootTaskInfo.configuration, this.mIconProvider);
        this.mHasRootTask = true;
        stageListenerCallbacks.onRootTaskAppeared();
        boolean z3 = this.mVisible;
        boolean z4 = this.mRootTaskInfo.isVisible;
        if (z3 != z4) {
            this.mVisible = z4;
            StageCoordinator stageCoordinator2 = (StageCoordinator) stageListenerCallbacks;
            StageTaskListener stageTaskListener = stageCoordinator2.mMainStage;
            if (stageTaskListener.mIsActive) {
                StageTaskListener stageTaskListener2 = stageCoordinator2.mSideStage;
                boolean z5 = stageTaskListener2.mVisible;
                boolean z6 = stageTaskListener.mVisible;
                boolean z7 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator2.mCellStage.mVisible;
                if (z6 == z5) {
                    if (z6 || !stageCoordinator2.mExitSplitScreenOnHide) {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        if (z6) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 4039662621566186722L, 0, null);
                            }
                            if (stageTaskListener2.mVisible && (z = stageTaskListener2.mHasChildren) && stageTaskListener.mVisible && z) {
                                stageCoordinator2.mSplitRequest = null;
                            }
                            windowContainerTransaction.setReparentLeafTaskIfRelaunch(stageCoordinator2.mRootTaskInfo.token, false);
                            stageCoordinator2.setRootForceTranslucent(windowContainerTransaction, false);
                        } else {
                            windowContainerTransaction.setReparentLeafTaskIfRelaunch(stageCoordinator2.mRootTaskInfo.token, true);
                            stageCoordinator2.setRootForceTranslucent(windowContainerTransaction, true);
                        }
                        stageCoordinator2.mSyncQueue.queue(windowContainerTransaction);
                        stageCoordinator2.setDividerVisibility(null, z6);
                        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && z6 == z7) {
                            stageCoordinator2.setCellDividerVisibility(null, z7);
                        }
                    } else {
                        stageCoordinator2.exitSplitScreen(null, 5);
                    }
                }
            }
        }
        this.mSyncQueue.runInSync(new StageTaskListener$$ExternalSyntheticLambda3(this, 0));
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        boolean z = false;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -6240756311100804611L, 1, Long.valueOf(runningTaskInfo.taskId), String.valueOf(runningTaskInfo.baseActivity), String.valueOf(SplitScreen.stageTypeToString(this.mId)));
        }
        this.mWindowDecorViewModel.ifPresent(new StageTaskListener$$ExternalSyntheticLambda4(runningTaskInfo, 0));
        int i = this.mRootTaskInfo.taskId;
        if (i == runningTaskInfo.taskId) {
            this.mRootTaskInfo = runningTaskInfo;
            return;
        }
        if (runningTaskInfo.parentTaskId != i) {
            throw new IllegalArgumentException(this + "\n Unknown task: " + runningTaskInfo + "\n mRootTaskInfo: " + this.mRootTaskInfo);
        }
        boolean z2 = runningTaskInfo.supportsMultiWindow;
        StageListenerCallbacks stageListenerCallbacks = this.mCallbacks;
        if (!z2 || !ArrayUtils.contains(SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, runningTaskInfo.getActivityType()) || !ArrayUtils.contains(SplitScreenConstants.CONTROLLED_WINDOWING_MODES_WHEN_ACTIVE, runningTaskInfo.getWindowingMode())) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -113865461001536373L, 1, Long.valueOf(runningTaskInfo.taskId));
            }
            ((StageCoordinator) stageListenerCallbacks).onNoLongerSupportMultiWindow(this, runningTaskInfo);
            return;
        }
        this.mChildrenTaskInfo.put(runningTaskInfo.taskId, runningTaskInfo);
        int i2 = runningTaskInfo.taskId;
        if (runningTaskInfo.isVisible && runningTaskInfo.isVisibleRequested) {
            z = true;
        }
        StageCoordinator stageCoordinator = (StageCoordinator) stageListenerCallbacks;
        stageCoordinator.onChildTaskStatusChanged(this, i2, true, z);
        if (this.mRootTaskInfo.isVisible) {
            stageCoordinator.postDividerPanelAutoOpenIfNeeded();
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -5853317746957599452L, 1, Long.valueOf(runningTaskInfo.taskId), String.valueOf(SplitScreen.stageTypeToString(this.mId)));
        }
        int i = runningTaskInfo.taskId;
        this.mWindowDecorViewModel.ifPresent(new StageTaskListener$$ExternalSyntheticLambda4(runningTaskInfo, 1));
        int i2 = this.mRootTaskInfo.taskId;
        StageListenerCallbacks stageListenerCallbacks = this.mCallbacks;
        if (i2 == i) {
            this.mHasRootTask = false;
            this.mVisible = false;
            this.mHasChildren = false;
            ((StageCoordinator) stageListenerCallbacks).onRootTaskVanished();
            this.mRootTaskInfo = null;
            this.mRootLeash = null;
            this.mSyncQueue.runInSync(new StageTaskListener$$ExternalSyntheticLambda3(this, 1));
            return;
        }
        if (this.mChildrenTaskInfo.contains(i)) {
            this.mChildrenTaskInfo.remove(i);
            this.mChildrenLeashes.remove(i);
            ((StageCoordinator) stageListenerCallbacks).onChildTaskStatusChanged(this, i, false, runningTaskInfo.isVisible);
            return;
        }
        throw new IllegalArgumentException(this + "\n Unknown task: " + runningTaskInfo + "\n mRootTaskInfo: " + this.mRootTaskInfo);
    }

    public final void removeAllTasks(WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 7445483628048563389L, 13, Long.valueOf(this.mChildrenTaskInfo.mTaskIds.size()), Boolean.valueOf(z), String.valueOf(SplitScreen.stageTypeToString(this.mId)));
        }
        removeAllTasks(windowContainerTransaction, z, true);
    }

    public final void reparentAllChildren(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction) {
        int size = this.mChildrenTaskInfo.mTaskIds.size();
        for (int i = 0; i < size; i++) {
            windowContainerTransaction.reparent(((ActivityManager.RunningTaskInfo) this.mChildrenTaskInfo.valueAt(i)).token, windowContainerToken, true);
        }
    }

    public final void reparentAllTasks(WindowContainerToken windowContainerToken, WindowContainerTransaction windowContainerTransaction, boolean z) {
        if (this.mChildrenTaskInfo.mTaskIds.size() == 0) {
            return;
        }
        windowContainerTransaction.reparentTasks(this.mRootTaskInfo.token, windowContainerToken, SplitScreenConstants.CONTROLLED_WINDOWING_MODES_WHEN_ACTIVE, SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, z);
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void reparentChildSurfaceToTask(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.reparent(surfaceControl, findTaskSurface$3(i));
    }

    public final String toString() {
        return "mId: " + SplitScreen.stageTypeToString(this.mId) + " mVisible: " + this.mVisible + " mActive: " + this.mIsActive + " mHasRootTask: " + this.mHasRootTask + " childSize: " + this.mChildrenTaskInfo.mTaskIds.size();
    }

    public final boolean removeAllTasks(WindowContainerTransaction windowContainerTransaction, boolean z, boolean z2) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 4354007016852765196L, 13, Long.valueOf(this.mChildrenTaskInfo.mTaskIds.size()), Boolean.valueOf(z));
        }
        if ((!CoreRune.MW_SPLIT_SHELL_TRANSITION || z2) && this.mChildrenTaskInfo.mTaskIds.size() == 0) {
            return false;
        }
        if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && z) {
            adjustChildTaskWindowingModeIfNeeded(windowContainerTransaction);
        }
        windowContainerTransaction.reparentTasks(this.mRootTaskInfo.token, (WindowContainerToken) null, (int[]) null, (int[]) null, z);
        return true;
    }
}
