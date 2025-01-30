package com.android.wm.shell.splitscreen;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.split.SplitDecorManager;
import com.android.wm.shell.common.split.SplitLayout;
import com.android.wm.shell.common.split.SplitScreenConstants;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.multiwindow.TaskOrganizerInfo;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class StageTaskListener implements ShellTaskOrganizer.TaskListener {
    public final StageListenerCallbacks mCallbacks;
    public final SparseArray mChildrenLeashes;
    public final RunningTaskInfoList mChildrenTaskInfo;
    public final Context mContext;
    public float mCornerRadiusForLeash;
    public SurfaceControl mDimLayer;
    public final IconProvider mIconProvider;
    public SurfaceControl mRootLeash;
    public ActivityManager.RunningTaskInfo mRootTaskInfo;
    public final RunningTaskInfoList mRunningTaskInfoList;
    public SplitDecorManager mSplitDecorManager;
    public final int mStageType;
    public final SurfaceSession mSurfaceSession;
    public final SyncTransactionQueue mSyncQueue;
    public Optional mWindowDecorViewModel;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RunningTaskInfoList extends SparseArray {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface StageListenerCallbacks {
    }

    public StageTaskListener(Context context, ShellTaskOrganizer shellTaskOrganizer, int i, StageListenerCallbacks stageListenerCallbacks, SyncTransactionQueue syncTransactionQueue, SurfaceSession surfaceSession, IconProvider iconProvider) {
        this(context, shellTaskOrganizer, i, stageListenerCallbacks, syncTransactionQueue, surfaceSession, iconProvider, 0);
    }

    public final void adjustChildTaskWindowingModeIfNeeded(WindowContainerTransaction windowContainerTransaction) {
        if (this.mContext.getResources().getConfiguration().isNewDexMode()) {
            RunningTaskInfoList runningTaskInfoList = this.mChildrenTaskInfo;
            for (int size = runningTaskInfoList.size() - 1; size >= 0; size--) {
                windowContainerTransaction.orderedSetWindowingMode(((ActivityManager.RunningTaskInfo) runningTaskInfoList.valueAt(size)).token, 1);
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
        if (!CoreRune.SAFE_DEBUG) {
            return true;
        }
        Slog.d("StageTaskListener", "applyCornerRadiusToLeashIfNeeded: r=" + f + ", forceApply=" + z + ", leash=" + this.mRootLeash);
        return true;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
        builder.setParent(findTaskSurface(i));
    }

    public final boolean contains(Predicate predicate) {
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRootTaskInfo;
        return (runningTaskInfo != null && predicate.test(runningTaskInfo)) || getChildTaskInfo(predicate) != null;
    }

    public final boolean containsTask(int i) {
        return this.mChildrenTaskInfo.contains(i);
    }

    public final boolean containsToken(WindowContainerToken windowContainerToken) {
        return contains(new StageTaskListener$$ExternalSyntheticLambda1(windowContainerToken, 0));
    }

    public final void doForAllChildTasks(StageCoordinator$$ExternalSyntheticLambda14 stageCoordinator$$ExternalSyntheticLambda14) {
        RunningTaskInfoList runningTaskInfoList = this.mChildrenTaskInfo;
        int size = runningTaskInfoList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                stageCoordinator$$ExternalSyntheticLambda14.accept(Integer.valueOf(((ActivityManager.RunningTaskInfo) runningTaskInfoList.valueAt(size)).taskId));
            }
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void dump(PrintWriter printWriter, String str) {
        RunningTaskInfoList runningTaskInfoList;
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "  ");
        String m14m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(m14m, "  ");
        RunningTaskInfoList runningTaskInfoList2 = this.mChildrenTaskInfo;
        if (runningTaskInfoList2.size() > 0) {
            printWriter.println(str + "Children list:");
            int size = runningTaskInfoList2.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) runningTaskInfoList2.valueAt(size);
                printWriter.println(m14m2 + "Task#" + size + " taskID=" + runningTaskInfo.taskId + " baseActivity=" + runningTaskInfo.baseActivity);
            }
        }
        if (!CoreRune.MW_SPLIT_STACKING || (runningTaskInfoList = this.mRunningTaskInfoList) == null || runningTaskInfoList.mClosingTaskIds.isEmpty()) {
            return;
        }
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(m14m, "ClosingTaskIds=");
        m2m.append(runningTaskInfoList.mClosingTaskIds);
        printWriter.println(m2m.toString());
    }

    public final void evictAllChildren(WindowContainerTransaction windowContainerTransaction, boolean z) {
        RunningTaskInfoList runningTaskInfoList = this.mChildrenTaskInfo;
        int size = runningTaskInfoList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) runningTaskInfoList.valueAt(size);
            if (z) {
                windowContainerTransaction.reparentFirst(runningTaskInfo.token, (WindowContainerToken) null, false);
            } else {
                windowContainerTransaction.reparent(runningTaskInfo.token, (WindowContainerToken) null, false);
            }
        }
    }

    public final void evictInvisibleChildren(WindowContainerTransaction windowContainerTransaction) {
        RunningTaskInfoList runningTaskInfoList = this.mChildrenTaskInfo;
        int size = runningTaskInfoList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) runningTaskInfoList.valueAt(size);
            if (!runningTaskInfo.isVisible) {
                windowContainerTransaction.reparent(runningTaskInfo.token, (WindowContainerToken) null, false);
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
        int size = clone.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                windowContainerTransaction.reparent(((ActivityManager.RunningTaskInfo) clone.valueAt(size)).token, (WindowContainerToken) null, false);
            }
        }
    }

    public final void evictOtherChildren(WindowContainerTransaction windowContainerTransaction, int i) {
        RunningTaskInfoList runningTaskInfoList = this.mChildrenTaskInfo;
        int size = runningTaskInfoList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) runningTaskInfoList.valueAt(size);
            if (i != runningTaskInfo.taskId) {
                windowContainerTransaction.reparent(runningTaskInfo.token, (WindowContainerToken) null, false);
            }
        }
    }

    public final SurfaceControl findTaskSurface(int i) {
        if (this.mRootTaskInfo.taskId == i) {
            return this.mRootLeash;
        }
        SparseArray sparseArray = this.mChildrenLeashes;
        if (sparseArray.contains(i)) {
            return (SurfaceControl) sparseArray.get(i);
        }
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("There is no surface for taskId=", i));
    }

    public final int getChildCount() {
        return this.mChildrenTaskInfo.size();
    }

    public final ActivityManager.RunningTaskInfo getChildTaskInfo(Predicate predicate) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        RunningTaskInfoList runningTaskInfoList = this.mChildrenTaskInfo;
        int size = runningTaskInfoList.size();
        do {
            size--;
            if (size < 0) {
                return null;
            }
            runningTaskInfo = (ActivityManager.RunningTaskInfo) runningTaskInfoList.valueAt(size);
        } while (!predicate.test(runningTaskInfo));
        return runningTaskInfo;
    }

    public final int getTopChildTaskUid() {
        ActivityManager.RunningTaskInfo childTaskInfo = getChildTaskInfo(new StageTaskListener$$ExternalSyntheticLambda0(2));
        if (childTaskInfo != null) {
            return childTaskInfo.topActivityInfo.applicationInfo.uid;
        }
        return 0;
    }

    public final ActivityManager.RunningTaskInfo getTopRunningTaskInfo() {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        RunningTaskInfoList runningTaskInfoList = this.mChildrenTaskInfo;
        int size = runningTaskInfoList.size();
        do {
            size--;
            if (size < 0) {
                return null;
            }
            runningTaskInfo = (ActivityManager.RunningTaskInfo) runningTaskInfoList.valueAt(size);
        } while (runningTaskInfo.topActivityInfo == null);
        return runningTaskInfo;
    }

    public final int getTopVisibleChildTaskId() {
        ActivityManager.RunningTaskInfo childTaskInfo = getChildTaskInfo(new StageTaskListener$$ExternalSyntheticLambda0(1));
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
        return this.mChildrenTaskInfo.size() > 0;
    }

    public final boolean isFocused() {
        return contains(new StageTaskListener$$ExternalSyntheticLambda0(0));
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

    /* JADX WARN: Removed duplicated region for block: B:59:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x017c  */
    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onSplitLayoutChangeRequested(TaskOrganizerInfo taskOrganizerInfo) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        ActivityManager.RunningTaskInfo runningTaskInfo3;
        int i;
        int splitFeasibleMode;
        ActivityManager.RunningTaskInfo runningTaskInfo4;
        StageCoordinator.StageListenerImpl stageListenerImpl = (StageCoordinator.StageListenerImpl) this.mCallbacks;
        stageListenerImpl.getClass();
        Slog.d("StageCoordinator", "onSplitLayoutChangeRequested: " + taskOrganizerInfo);
        boolean isChangeSplitLayoutForLaunchAdjacent = taskOrganizerInfo.isChangeSplitLayoutForLaunchAdjacent();
        int i2 = 1;
        StageCoordinator stageCoordinator = StageCoordinator.this;
        if (isChangeSplitLayoutForLaunchAdjacent) {
            if (CoreRune.MW_MULTI_SPLIT_CREATE_MODE) {
                stageCoordinator.setSplitCreateMode(taskOrganizerInfo.getSplitScreenCreateMode(), false);
            }
            stageCoordinator.mSplitLayoutChangedForLaunchAdjacent = true;
        }
        int exitSplitScreenTopTaskId = taskOrganizerInfo.getExitSplitScreenTopTaskId();
        int splitToFreeformTaskId = taskOrganizerInfo.getSplitToFreeformTaskId();
        int exitSplitScreenStageType = taskOrganizerInfo.getExitSplitScreenStageType();
        if (exitSplitScreenTopTaskId != -1 || exitSplitScreenStageType != 0) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setDisplayIdForChangeTransition(0, "split_exit");
            ActivityManager.RunningTaskInfo runningTaskInfo5 = stageCoordinator.mTaskOrganizer.getRunningTaskInfo(exitSplitScreenTopTaskId);
            if (runningTaskInfo5 != null) {
                stageCoordinator.maximizeSplitTask(runningTaskInfo5.token, windowContainerTransaction);
            } else if (exitSplitScreenStageType == 1 && (runningTaskInfo3 = stageCoordinator.mMainStage.mRootTaskInfo) != null) {
                stageCoordinator.maximizeSplitTask(runningTaskInfo3.token, windowContainerTransaction);
            } else if (exitSplitScreenStageType == 2 && (runningTaskInfo2 = stageCoordinator.mSideStage.mRootTaskInfo) != null) {
                stageCoordinator.maximizeSplitTask(runningTaskInfo2.token, windowContainerTransaction);
            } else if (exitSplitScreenStageType == 4 && (runningTaskInfo = stageCoordinator.mCellStage.mRootTaskInfo) != null) {
                stageCoordinator.maximizeSplitTask(runningTaskInfo.token, windowContainerTransaction);
            }
        } else if (splitToFreeformTaskId != -1 && (runningTaskInfo4 = stageCoordinator.mTaskOrganizer.getRunningTaskInfo(splitToFreeformTaskId)) != null) {
            stageCoordinator.moveSplitToFreeform(runningTaskInfo4.token, null, false);
        }
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && (splitFeasibleMode = taskOrganizerInfo.getSplitFeasibleMode()) != -1) {
            SplitLayout splitLayout = stageCoordinator.mSplitLayout;
            int i3 = splitLayout.mSplitScreenFeasibleMode;
            if (!CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY || !stageCoordinator.mIsFolded || i3 == 0 || splitFeasibleMode == 0) {
                splitLayout.mSplitScreenFeasibleMode = splitFeasibleMode;
                if (i3 > splitFeasibleMode) {
                    stageCoordinator.mRecentTasks.ifPresent(new StageCoordinator$StageListenerImpl$$ExternalSyntheticLambda0());
                }
                if (splitFeasibleMode == 1 && stageCoordinator.isMultiSplitActive() && !stageCoordinator.isMultiSplitScreenVisible()) {
                    WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                    stageCoordinator.prepareExitSplitScreen(-1, windowContainerTransaction2, true);
                    stageCoordinator.mSplitTransitions.startDismissTransition(windowContainerTransaction2, stageCoordinator, -1, 1);
                } else if (splitFeasibleMode == 1 && stageCoordinator.mMainStage.mIsActive && !stageCoordinator.isMultiSplitActive()) {
                    int i4 = stageCoordinator.mSplitDivision;
                    int i5 = stageCoordinator.mSplitLayout.mPossibleSplitDivision;
                    if (i4 != i5 && stageCoordinator.setSplitDivision(i5, stageCoordinator.isInSubDisplay())) {
                        WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                        stageCoordinator.onLayoutSizeChanged(stageCoordinator.mSplitLayout, windowContainerTransaction3);
                        stageCoordinator.mTaskOrganizer.applyTransaction(windowContainerTransaction3);
                    }
                }
            }
        }
        if (!CoreRune.MW_SUPPORT_ASSISTANT_HOT_KEY || taskOrganizerInfo.getAssistantActivityIntent() == null) {
            return;
        }
        Intent assistantActivityIntent = taskOrganizerInfo.getAssistantActivityIntent();
        float requestedSplitRatio = taskOrganizerInfo.getRequestedSplitRatio();
        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(stageCoordinator.mContext, 0, assistantActivityIntent, 167772160, null, UserHandle.SYSTEM);
        if (assistantActivityIntent == null) {
            Slog.w("StageCoordinator", "assistant activity null");
            return;
        }
        WindowContainerTransaction windowContainerTransaction4 = new WindowContainerTransaction();
        if (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION) {
            i2 = -1;
        } else if (stageCoordinator.isLandscape()) {
            i = 0;
            Bundle resolveStartStage = stageCoordinator.resolveStartStage(-1, 1, null, windowContainerTransaction4, i);
            if (!stageCoordinator.isLandscape()) {
                requestedSplitRatio = 0.5f;
            }
            float f = requestedSplitRatio;
            windowContainerTransaction4.sendPendingIntent(activityAsUser, assistantActivityIntent, resolveStartStage);
            if (stageCoordinator.mMainStage.mIsActive) {
                stageCoordinator.prepareActiveSplit(windowContainerTransaction4, null, -1, false, f);
            } else if (stageCoordinator.isSplitScreenVisible()) {
                stageCoordinator.prepareSplitLayout(windowContainerTransaction4, false, f);
                windowContainerTransaction4.setDisplayIdForChangeTransition(stageCoordinator.mDisplayId, "ai_hot_key");
            } else {
                stageCoordinator.prepareBringSplit(windowContainerTransaction4, null, -1, false, f);
            }
            stageCoordinator.mSplitTransitions.startEnterTransition(windowContainerTransaction4, null, stageCoordinator, 1005, false);
        }
        i = i2;
        Bundle resolveStartStage2 = stageCoordinator.resolveStartStage(-1, 1, null, windowContainerTransaction4, i);
        if (!stageCoordinator.isLandscape()) {
        }
        float f2 = requestedSplitRatio;
        windowContainerTransaction4.sendPendingIntent(activityAsUser, assistantActivityIntent, resolveStartStage2);
        if (stageCoordinator.mMainStage.mIsActive) {
        }
        stageCoordinator.mSplitTransitions.startEnterTransition(windowContainerTransaction4, null, stageCoordinator, 1005, false);
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onSplitPairUpdateRequested() {
        StageCoordinator.StageListenerImpl stageListenerImpl = (StageCoordinator.StageListenerImpl) this.mCallbacks;
        StageCoordinator stageCoordinator = StageCoordinator.this;
        if (stageCoordinator.mMainStage.mIsActive) {
            stageCoordinator.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda17(stageListenerImpl, 1));
        }
    }

    public final void onSplitScreenListenerRegistered(SplitScreen.SplitScreenListener splitScreenListener, int i) {
        RunningTaskInfoList runningTaskInfoList = this.mChildrenTaskInfo;
        int size = runningTaskInfoList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            int keyAt = runningTaskInfoList.keyAt(size);
            splitScreenListener.onTaskStageChanged(keyAt, i, ((ActivityManager.RunningTaskInfo) runningTaskInfoList.get(keyAt)).isVisible);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mRootTaskInfo;
        SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
        StageListenerCallbacks stageListenerCallbacks = this.mCallbacks;
        if (runningTaskInfo2 == null) {
            this.mRootLeash = surfaceControl;
            if (CoreRune.MW_MULTI_SPLIT_ROUNDED_CORNER) {
                this.mCornerRadiusForLeash = 0.0f;
            }
            this.mRootTaskInfo = runningTaskInfo;
            this.mSplitDecorManager = new SplitDecorManager(this.mRootTaskInfo.configuration, this.mIconProvider, this.mSurfaceSession);
            StageCoordinator.StageListenerImpl stageListenerImpl = (StageCoordinator.StageListenerImpl) stageListenerCallbacks;
            stageListenerImpl.mHasRootTask = true;
            StageCoordinator.this.onRootTaskAppeared();
            sendStatusChanged();
            syncTransactionQueue.runInSync(new StageTaskListener$$ExternalSyntheticLambda2(this, 1));
            return;
        }
        if (runningTaskInfo.parentTaskId != runningTaskInfo2.taskId) {
            throw new IllegalArgumentException(this + "\n Unknown task: " + runningTaskInfo + "\n mRootTaskInfo: " + this.mRootTaskInfo);
        }
        int i = runningTaskInfo.taskId;
        this.mChildrenLeashes.put(i, surfaceControl);
        this.mChildrenTaskInfo.put(i, runningTaskInfo);
        StageCoordinator.StageListenerImpl stageListenerImpl2 = (StageCoordinator.StageListenerImpl) stageListenerCallbacks;
        stageListenerImpl2.onChildTaskStatusChanged(i, true, runningTaskInfo.isVisible && runningTaskInfo.isVisibleRequested);
        if (CoreRune.MW_CAPTION_SHELL && this.mWindowDecorViewModel.isPresent()) {
            ((WindowDecorViewModel) this.mWindowDecorViewModel.get()).onTaskInfoChanged(runningTaskInfo);
        }
        if (this.mRootTaskInfo.isVisible) {
            stageListenerImpl2.postDividerPanelAutoOpenIfNeeded();
        }
        if (!runningTaskInfo.supportsMultiWindow) {
            ((HandlerExecutor) StageCoordinator.this.mMainExecutor).executeDelayed(500L, new StageCoordinator$$ExternalSyntheticLambda6(stageListenerImpl2, 2));
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            return;
        }
        syncTransactionQueue.runInSync(new StageTaskListener$$ExternalSyntheticLambda3(surfaceControl, runningTaskInfo, runningTaskInfo.positionInParent, true));
        StageCoordinator stageCoordinator = StageCoordinator.this;
        if (stageListenerImpl2 == stageCoordinator.mSideStageListener && !stageCoordinator.isSplitScreenVisible()) {
            MainStage mainStage = stageCoordinator.mMainStage;
            if (mainStage.mIsActive && stageCoordinator.mSplitRequest == null) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                stageCoordinator.prepareEnterSplitScreen(windowContainerTransaction, null, -1, true ^ stageCoordinator.mIsDropEntering);
                mainStage.evictAllChildren(windowContainerTransaction, false);
                stageCoordinator.mSideStage.evictOtherChildren(windowContainerTransaction, i);
                SyncTransactionQueue syncTransactionQueue2 = stageCoordinator.mSyncQueue;
                syncTransactionQueue2.queue(windowContainerTransaction);
                syncTransactionQueue2.runInSync(new StageCoordinator$$ExternalSyntheticLambda0(stageCoordinator, 6));
            }
        }
        sendStatusChanged();
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mRootTaskInfo;
        int i = runningTaskInfo2.taskId;
        int i2 = runningTaskInfo.taskId;
        SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
        if (i == i2) {
            if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                boolean z = runningTaskInfo2.isVisible;
                boolean z2 = runningTaskInfo.isVisible;
                if (z != z2) {
                    if (z2) {
                        SplitDecorManager splitDecorManager = this.mSplitDecorManager;
                        runningTaskInfo.configuration.windowConfiguration.getBounds();
                        splitDecorManager.getClass();
                    } else {
                        syncTransactionQueue.runInSync(new StageTaskListener$$ExternalSyntheticLambda2(this, 2));
                    }
                }
            }
            this.mRootTaskInfo = runningTaskInfo;
        } else {
            if (runningTaskInfo.parentTaskId != i) {
                throw new IllegalArgumentException(this + "\n Unknown task: " + runningTaskInfo + "\n mRootTaskInfo: " + this.mRootTaskInfo);
            }
            boolean z3 = runningTaskInfo.supportsMultiWindow;
            StageListenerCallbacks stageListenerCallbacks = this.mCallbacks;
            if (!z3 || !ArrayUtils.contains(SplitScreenConstants.CONTROLLED_ACTIVITY_TYPES, runningTaskInfo.getActivityType()) || !ArrayUtils.contains(SplitScreenConstants.CONTROLLED_WINDOWING_MODES_WHEN_ACTIVE, runningTaskInfo.getWindowingMode())) {
                ((StageCoordinator.StageListenerImpl) stageListenerCallbacks).onNoLongerSupportMultiWindow();
                return;
            }
            this.mChildrenTaskInfo.put(runningTaskInfo.taskId, runningTaskInfo);
            StageCoordinator.StageListenerImpl stageListenerImpl = (StageCoordinator.StageListenerImpl) stageListenerCallbacks;
            stageListenerImpl.onChildTaskStatusChanged(runningTaskInfo.taskId, true, runningTaskInfo.isVisible && runningTaskInfo.isVisibleRequested);
            if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
                syncTransactionQueue.runInSync(new StageTaskListener$$ExternalSyntheticLambda3((SurfaceControl) this.mChildrenLeashes.get(runningTaskInfo.taskId), runningTaskInfo, runningTaskInfo.positionInParent, false));
            }
            if (CoreRune.MW_CAPTION_SHELL && this.mWindowDecorViewModel.isPresent()) {
                ((WindowDecorViewModel) this.mWindowDecorViewModel.get()).onTaskInfoChanged(runningTaskInfo);
            }
            if (this.mRootTaskInfo.isVisible) {
                stageListenerImpl.postDividerPanelAutoOpenIfNeeded();
            }
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            return;
        }
        sendStatusChanged();
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = runningTaskInfo.taskId;
        int i2 = this.mRootTaskInfo.taskId;
        StageListenerCallbacks stageListenerCallbacks = this.mCallbacks;
        if (i2 == i) {
            StageCoordinator.StageListenerImpl stageListenerImpl = (StageCoordinator.StageListenerImpl) stageListenerCallbacks;
            stageListenerImpl.mHasRootTask = false;
            stageListenerImpl.mVisible = false;
            stageListenerImpl.mHasChildren = false;
            StageCoordinator.this.onRootTaskVanished();
            this.mRootTaskInfo = null;
            this.mRootLeash = null;
            this.mSyncQueue.runInSync(new StageTaskListener$$ExternalSyntheticLambda2(this, 0));
            return;
        }
        RunningTaskInfoList runningTaskInfoList = this.mChildrenTaskInfo;
        if (!runningTaskInfoList.contains(i)) {
            throw new IllegalArgumentException(this + "\n Unknown task: " + runningTaskInfo + "\n mRootTaskInfo: " + this.mRootTaskInfo);
        }
        runningTaskInfoList.remove(i);
        this.mChildrenLeashes.remove(i);
        ((StageCoordinator.StageListenerImpl) stageListenerCallbacks).onChildTaskStatusChanged(i, false, runningTaskInfo.isVisible);
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            return;
        }
        sendStatusChanged();
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void reparentChildSurfaceToTask(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.reparent(surfaceControl, findTaskSurface(i));
    }

    public final void sendStatusChanged() {
        boolean z = this.mRootTaskInfo.isVisible;
        boolean z2 = this.mChildrenTaskInfo.size() > 0;
        StageCoordinator.StageListenerImpl stageListenerImpl = (StageCoordinator.StageListenerImpl) this.mCallbacks;
        if (stageListenerImpl.mHasRootTask) {
            boolean z3 = stageListenerImpl.mHasChildren;
            int i = 5;
            StageCoordinator stageCoordinator = StageCoordinator.this;
            if (z3 != z2) {
                stageListenerImpl.mHasChildren = z2;
                stageCoordinator.getClass();
                boolean z4 = stageListenerImpl.mHasChildren;
                StageCoordinator.StageListenerImpl stageListenerImpl2 = stageCoordinator.mSideStageListener;
                boolean z5 = stageListenerImpl == stageListenerImpl2;
                StageCoordinator.StageListenerImpl stageListenerImpl3 = stageCoordinator.mMainStageListener;
                MainStage mainStage = stageCoordinator.mMainStage;
                if (z4 || stageCoordinator.mIsExiting || !mainStage.mIsActive) {
                    if (z5 && z4 && !mainStage.mIsActive) {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        stageCoordinator.prepareEnterSplitScreen(windowContainerTransaction, null, -1, !stageCoordinator.mIsDropEntering);
                        SyncTransactionQueue syncTransactionQueue = stageCoordinator.mSyncQueue;
                        syncTransactionQueue.queue(windowContainerTransaction);
                        syncTransactionQueue.runInSync(new StageCoordinator$$ExternalSyntheticLambda0(stageCoordinator, i));
                    }
                } else if (z5 && stageListenerImpl3.mVisible) {
                    stageCoordinator.mSplitLayout.flingDividerToDismiss(2, stageCoordinator.mSideStagePosition == 1);
                } else if (!z5 && stageListenerImpl2.mVisible) {
                    stageCoordinator.mSplitLayout.flingDividerToDismiss(2, stageCoordinator.mSideStagePosition != 1);
                } else if (!stageCoordinator.isSplitScreenVisible() && stageCoordinator.mSplitRequest == null) {
                    stageCoordinator.exitSplitScreen(null, 2);
                }
                if (stageListenerImpl3.mHasChildren && stageListenerImpl2.mHasChildren) {
                    stageCoordinator.mShouldUpdateRecents = true;
                    stageCoordinator.clearRequestIfPresented();
                    stageCoordinator.updateRecentTasksSplitPair();
                    SplitscreenEventLogger splitscreenEventLogger = stageCoordinator.mLogger;
                    if (!(splitscreenEventLogger.mLoggerSessionId != null)) {
                        if (!(splitscreenEventLogger.mEnterSessionId != null)) {
                            splitscreenEventLogger.mEnterSessionId = null;
                            splitscreenEventLogger.mEnterReason = 1;
                        }
                        splitscreenEventLogger.logEnter(stageCoordinator.mSplitLayout.getDividerPositionAsFraction(), stageCoordinator.getMainStagePosition(), mainStage.getTopChildTaskUid(), stageCoordinator.mSideStagePosition, stageCoordinator.mSideStage.getTopChildTaskUid(), stageCoordinator.mSplitLayout.isLandscape());
                    }
                }
            }
            if (stageListenerImpl.mVisible != z) {
                stageListenerImpl.mVisible = z;
                if (stageCoordinator.mMainStage.mIsActive) {
                    boolean z6 = stageCoordinator.mSideStageListener.mVisible;
                    boolean z7 = stageCoordinator.mMainStageListener.mVisible;
                    boolean z8 = stageCoordinator.mCellStageListener.mVisible;
                    if (z7 != z6) {
                        return;
                    }
                    if (!z7 && stageCoordinator.mExitSplitScreenOnHide) {
                        stageCoordinator.exitSplitScreen(null, 5);
                        return;
                    }
                    WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                    if (z7) {
                        stageCoordinator.clearRequestIfPresented();
                        windowContainerTransaction2.setReparentLeafTaskIfRelaunch(stageCoordinator.mRootTaskInfo.token, false);
                        stageCoordinator.setRootForceTranslucent(windowContainerTransaction2, false);
                    } else {
                        windowContainerTransaction2.setReparentLeafTaskIfRelaunch(stageCoordinator.mRootTaskInfo.token, true);
                        stageCoordinator.setRootForceTranslucent(windowContainerTransaction2, true);
                    }
                    stageCoordinator.mSyncQueue.queue(windowContainerTransaction2);
                    stageCoordinator.setDividerVisibility(z7, null);
                    if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && z7 == z8) {
                        stageCoordinator.setCellDividerVisibility(null, z8);
                    }
                }
            }
        }
    }

    public StageTaskListener(Context context, ShellTaskOrganizer shellTaskOrganizer, int i, StageListenerCallbacks stageListenerCallbacks, SyncTransactionQueue syncTransactionQueue, SurfaceSession surfaceSession, IconProvider iconProvider, int i2) {
        RunningTaskInfoList runningTaskInfoList = new RunningTaskInfoList(this, 0);
        this.mChildrenTaskInfo = runningTaskInfoList;
        this.mChildrenLeashes = new SparseArray();
        this.mContext = context;
        this.mCallbacks = stageListenerCallbacks;
        this.mSyncQueue = syncTransactionQueue;
        this.mSurfaceSession = surfaceSession;
        this.mIconProvider = iconProvider;
        this.mRunningTaskInfoList = runningTaskInfoList;
        this.mStageType = i2;
        shellTaskOrganizer.createStageRootTask(i, i2, this);
    }
}
