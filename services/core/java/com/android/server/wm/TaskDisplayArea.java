package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Message;
import android.util.EventLog;
import android.util.IntArray;
import android.util.Slog;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.app.ResolverActivity;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.DisplayArea;
import com.android.server.wm.LaunchParamsController;
import com.android.server.wm.RemoteAnimationController;
import com.android.server.wm.SizeCompatPolicyManager;
import com.android.server.wm.Task;
import com.android.server.wm.TaskOrganizerController;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TaskDisplayArea extends DisplayArea {
    public final ActivityTaskManagerService mAtmService;
    public int mBackgroundColor;
    public final boolean mCanHostHomeTask;
    public SurfaceControl mChangeLeashAnchor;
    public int mColorLayerCounter;
    public final boolean mCreatedByOrganizer;
    public final DisplayContent mDisplayContent;
    public SurfaceControl mFloatingLeashAnchor;
    public final FreeformTaskPinningController mFreeformTaskPinningController;
    public Task mLastFocusedRootTask;
    public int mLastLeafTaskToFrontId;
    Task mLaunchAdjacentFlagRootTask;
    public final ArrayList mLaunchRootTasks;
    Task mPreferredTopFocusableRootTask;
    public boolean mRemoved;
    public Task mRootCellStageTask;
    public Task mRootHomeTask;
    public Task mRootMainStageTask;
    public Task mRootPinnedTask;
    public Task mRootSideStageTask;
    public final ArrayList mRootTaskOrderChangedCallbacks;
    public final RootWindowContainer mRootWindowContainer;
    public final Configuration mTempConfiguration;
    public final ArrayList mTmpAlwaysOnTopChildren;
    public final ArrayList mTmpFreeformChildren;
    public final ArrayList mTmpFreeformPinnedChildren;
    public final ArrayList mTmpHomeChildren;
    public final IntArray mTmpNeedsZBoostIndexes;
    public final ArrayList mTmpNormalChildren;
    public final ArrayList mTmpTasks;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LaunchRootTaskDef {
        public int[] activityTypes;
        public Task task;
        public int[] windowingModes;
    }

    public TaskDisplayArea(DisplayContent displayContent, WindowManagerService windowManagerService, String str, int i, boolean z) {
        super(windowManagerService, DisplayArea.Type.ANY, str, i);
        this.mBackgroundColor = 0;
        this.mColorLayerCounter = 0;
        this.mTmpAlwaysOnTopChildren = new ArrayList();
        this.mTmpNormalChildren = new ArrayList();
        this.mTmpHomeChildren = new ArrayList();
        this.mTmpNeedsZBoostIndexes = new IntArray();
        this.mTmpTasks = new ArrayList();
        this.mTmpFreeformChildren = new ArrayList();
        this.mTmpFreeformPinnedChildren = new ArrayList();
        this.mLaunchRootTasks = new ArrayList();
        this.mRootTaskOrderChangedCallbacks = new ArrayList();
        this.mTempConfiguration = new Configuration();
        this.mDisplayContent = displayContent;
        this.mRootWindowContainer = windowManagerService.mRoot;
        this.mAtmService = windowManagerService.mAtmService;
        this.mCreatedByOrganizer = z;
        this.mCanHostHomeTask = true;
        this.mFreeformTaskPinningController = CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING ? new FreeformTaskPinningController(this) : null;
    }

    public static Task getRootTaskAbove(Task task) {
        WindowContainer parent = task.getParent();
        int indexOf = parent.mChildren.indexOf(task) + 1;
        if (indexOf < parent.mChildren.size()) {
            return (Task) parent.mChildren.get(indexOf);
        }
        return null;
    }

    public static boolean isWindowingModeSupported(int i, boolean z, boolean z2, boolean z3) {
        if (i != 0 && i != 1) {
            if (!z) {
                return false;
            }
            if (i == 6) {
                return true;
            }
            if (!z2 && i == 5) {
                return false;
            }
            if (!z3 && i == 2) {
                return false;
            }
        }
        return true;
    }

    public static void moveRootTaskBehindRootTask(Task task, Task task2) {
        WindowContainer parent;
        if (task2 == null || task2 == task || (parent = task.getParent()) == null || parent != task2.getParent()) {
            return;
        }
        int indexOf = parent.mChildren.indexOf(task);
        int indexOf2 = parent.mChildren.indexOf(task2);
        if (indexOf <= indexOf2) {
            indexOf2--;
        }
        parent.positionChildAt(Math.max(0, indexOf2), task, false);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void addChild(WindowContainer windowContainer, int i) {
        if (windowContainer.asTaskDisplayArea() != null) {
            super.addChild(windowContainer, i);
            return;
        }
        if (windowContainer.asTask() == null) {
            throw new IllegalArgumentException("TaskDisplayArea can only add Task and TaskDisplayArea, but found " + windowContainer);
        }
        Task asTask = windowContainer.asTask();
        addRootTaskReferenceIfNeeded(asTask);
        super.addChild(asTask, findPositionForRootTask(i, asTask, true));
        if (this.mPreferredTopFocusableRootTask != null && asTask.isFocusable() && this.mPreferredTopFocusableRootTask.compareTo((WindowContainer) asTask) < 0) {
            this.mPreferredTopFocusableRootTask = null;
        }
        this.mAtmService.mTaskSupervisor.updateTopResumedActivityIfNeeded("addChildTask");
        this.mAtmService.updateSleepIfNeededLocked();
        onRootTaskOrderChanged(asTask);
    }

    public final void addRootTaskReferenceIfNeeded(Task task) {
        if (task.isActivityTypeHome()) {
            Task task2 = this.mRootHomeTask;
            if (task2 == null) {
                this.mRootHomeTask = task;
            } else if (!task.isDescendantOf(task2)) {
                throw new IllegalArgumentException("addRootTaskReferenceIfNeeded: root home task=" + this.mRootHomeTask + " already exist on display=" + this + " rootTask=" + task);
            }
        }
        if (task.isRootTask()) {
            int windowingMode = task.getWindowingMode();
            if (windowingMode == 2) {
                if (this.mRootPinnedTask == null) {
                    this.mRootPinnedTask = task;
                    return;
                }
                throw new IllegalArgumentException("addRootTaskReferenceIfNeeded: root pinned task=" + this.mRootPinnedTask + " already exist on display=" + this + " rootTask=" + task);
            }
            if (windowingMode == 6 && task.isOrganized()) {
                int stageType = task.getWindowConfiguration().getStageType();
                if (stageType == 1) {
                    Task task3 = this.mRootMainStageTask;
                    if (task3 != null) {
                        handleExceptionForRootStageTask(task3, task);
                    }
                    this.mRootMainStageTask = task;
                    return;
                }
                if (stageType == 2) {
                    Task task4 = this.mRootSideStageTask;
                    if (task4 != null) {
                        handleExceptionForRootStageTask(task4, task);
                    }
                    this.mRootSideStageTask = task;
                    return;
                }
                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageType == 4) {
                    Task task5 = this.mRootCellStageTask;
                    if (task5 != null) {
                        handleExceptionForRootStageTask(task5, task);
                    }
                    this.mRootCellStageTask = task;
                }
            }
        }
    }

    public final int adjustRootTaskLayer(SurfaceControl.Transaction transaction, ArrayList arrayList, int i) {
        this.mTmpNeedsZBoostIndexes.clear();
        int size = arrayList.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            WindowContainer windowContainer = (WindowContainer) arrayList.get(i3);
            TaskDisplayArea asTaskDisplayArea = windowContainer.asTaskDisplayArea();
            boolean z = true;
            boolean z2 = windowContainer.inFreeformWindowingMode() && windowContainer.asTask() != null && windowContainer.asTask().mBoostRootTaskLayerForFreeform;
            if (asTaskDisplayArea != null) {
                boolean[] zArr = new boolean[1];
                asTaskDisplayArea.forAllRootTasks(new TaskDisplayArea$$ExternalSyntheticLambda0(1, zArr));
                z = zArr[0];
            } else if (!windowContainer.needsZBoost() && !z2) {
                z = false;
            }
            if (z) {
                this.mTmpNeedsZBoostIndexes.add(i3);
            } else {
                windowContainer.assignLayer(transaction, i);
                i++;
            }
        }
        int size2 = this.mTmpNeedsZBoostIndexes.size();
        while (i2 < size2) {
            ((WindowContainer) arrayList.get(this.mTmpNeedsZBoostIndexes.get(i2))).assignLayer(transaction, i);
            i2++;
            i++;
        }
        return i;
    }

    public final boolean allResumedActivitiesComplete() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskDisplayArea() == null) {
                ActivityRecord topResumedActivity = ((WindowContainer) this.mChildren.get(size)).asTask().getTopResumedActivity();
                if (topResumedActivity != null && !topResumedActivity.isState(ActivityRecord.State.RESUMED)) {
                    return false;
                }
            } else if (!windowContainer.asTaskDisplayArea().allResumedActivitiesComplete()) {
                return false;
            }
        }
        this.mLastFocusedRootTask = getFocusedRootTask();
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public final TaskDisplayArea asTaskDisplayArea() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void assignChildLayers(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl;
        SurfaceControl surfaceControl2;
        if (getParent() != null) {
            this.mTmpAlwaysOnTopChildren.clear();
            this.mTmpHomeChildren.clear();
            this.mTmpNormalChildren.clear();
            this.mTmpFreeformChildren.clear();
            if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING) {
                this.mTmpFreeformPinnedChildren.clear();
            }
            for (int i = 0; i < this.mChildren.size(); i++) {
                WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i);
                TaskDisplayArea asTaskDisplayArea = windowContainer.asTaskDisplayArea();
                if (asTaskDisplayArea != null) {
                    Task topRootTask = asTaskDisplayArea.getTopRootTask();
                    if (topRootTask == null) {
                        this.mTmpNormalChildren.add(asTaskDisplayArea);
                    } else if (topRootTask.isAlwaysOnTop()) {
                        if (topRootTask.inFreeformWindowingMode()) {
                            this.mTmpFreeformChildren.add(asTaskDisplayArea);
                        } else {
                            this.mTmpAlwaysOnTopChildren.add(asTaskDisplayArea);
                        }
                    } else if (topRootTask.isActivityTypeHome()) {
                        this.mTmpHomeChildren.add(asTaskDisplayArea);
                    } else {
                        this.mTmpNormalChildren.add(asTaskDisplayArea);
                    }
                } else {
                    Task asTask = windowContainer.asTask();
                    if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING && asTask.isFreeformPinned() && !asTask.isMinimized()) {
                        this.mTmpFreeformPinnedChildren.add(asTask);
                    } else if (asTask.mBoostRootTaskLayerForFreeform) {
                        this.mTmpFreeformChildren.add(asTask);
                    } else if (asTask.isAlwaysOnTop()) {
                        if (asTask.inFreeformWindowingMode()) {
                            this.mTmpFreeformChildren.add(asTask);
                        } else {
                            this.mTmpAlwaysOnTopChildren.add(asTask);
                        }
                    } else if (asTask.isActivityTypeHome()) {
                        this.mTmpHomeChildren.add(asTask);
                    } else {
                        this.mTmpNormalChildren.add(asTask);
                    }
                }
            }
            int adjustRootTaskLayer = adjustRootTaskLayer(transaction, this.mTmpNormalChildren, adjustRootTaskLayer(transaction, this.mTmpHomeChildren, 0));
            if (CoreRune.MW_SHELL_CHANGE_TRANSITION && (surfaceControl2 = this.mChangeLeashAnchor) != null) {
                transaction.setLayer(surfaceControl2, adjustRootTaskLayer);
                adjustRootTaskLayer++;
            }
            int adjustRootTaskLayer2 = adjustRootTaskLayer(transaction, this.mTmpFreeformChildren, adjustRootTaskLayer);
            if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING) {
                adjustRootTaskLayer2 = adjustRootTaskLayer(transaction, this.mTmpFreeformPinnedChildren, adjustRootTaskLayer2);
            }
            if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && (surfaceControl = this.mFloatingLeashAnchor) != null) {
                transaction.setLayer(surfaceControl, adjustRootTaskLayer2);
                adjustRootTaskLayer2++;
            }
            adjustRootTaskLayer(transaction, this.mTmpAlwaysOnTopChildren, adjustRootTaskLayer2);
        }
        for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
            ((WindowContainer) this.mChildren.get(i2)).assignChildLayers(transaction);
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final boolean canCreateRemoteAnimationTarget() {
        return WindowManagerService.sEnableShellTransitions;
    }

    public final boolean canHostHomeTask() {
        return this.mDisplayContent.isHomeSupported() && this.mCanHostHomeTask;
    }

    @Override // com.android.server.wm.WindowContainer
    public final RemoteAnimationTarget createRemoteAnimationTarget(RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        ActivityRecord topMostActivity = getTopMostActivity();
        if (topMostActivity != null) {
            return topMostActivity.createRemoteAnimationTarget(remoteAnimationRecord);
        }
        return null;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final void dump(PrintWriter printWriter, String str, boolean z) {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "TaskDisplayArea ");
        m.append(getName());
        printWriter.println(m.toString());
        String str2 = str + "  ";
        super.dump(printWriter, str2, z);
        if (this.mPreferredTopFocusableRootTask != null) {
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(str2, "mPreferredTopFocusableRootTask=");
            m2.append(this.mPreferredTopFocusableRootTask);
            printWriter.println(m2.toString());
        }
        if (this.mLastFocusedRootTask != null) {
            StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(str2, "mLastFocusedRootTask=");
            m3.append(this.mLastFocusedRootTask);
            printWriter.println(m3.toString());
        }
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str2, "  ");
        if (this.mLaunchRootTasks.size() > 0) {
            printWriter.println(str2 + "mLaunchRootTasks:");
            for (int size = this.mLaunchRootTasks.size() - 1; size >= 0; size--) {
                LaunchRootTaskDef launchRootTaskDef = (LaunchRootTaskDef) this.mLaunchRootTasks.get(size);
                StringBuilder m4 = BootReceiver$$ExternalSyntheticOutline0.m(m$1);
                m4.append(Arrays.toString(launchRootTaskDef.activityTypes));
                m4.append(" ");
                m4.append(Arrays.toString(launchRootTaskDef.windowingModes));
                m4.append("  task=");
                m4.append(launchRootTaskDef.task);
                printWriter.println(m4.toString());
            }
        }
        printWriter.println(str2 + "Application tokens in top down Z order:");
        for (int childCount = getChildCount() + (-1); childCount >= 0; childCount--) {
            WindowContainer childAt = getChildAt(childCount);
            if (childAt.asTaskDisplayArea() != null) {
                childAt.dump(printWriter, str2, z);
            } else {
                Task asTask = childAt.asTask();
                StringBuilder m5 = Preconditions$$ExternalSyntheticOutline0.m(str2, "* ");
                m5.append(asTask.toFullString());
                printWriter.println(m5.toString());
                asTask.dump(printWriter, m$1, z);
            }
        }
        if (CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING) {
            FreeformTaskPinningController freeformTaskPinningController = this.mFreeformTaskPinningController;
            if (freeformTaskPinningController.mPinnedTask == null) {
                return;
            }
            String m$12 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  ");
            printWriter.println(str + "FreeformTaskPinningController :");
            printWriter.println(m$12 + "mPinnedTask :" + freeformTaskPinningController.mPinnedTask);
        }
    }

    public final void ensureActivitiesVisible(boolean z, ActivityRecord activityRecord) {
        this.mAtmService.mTaskSupervisor.beginActivityVisibilityUpdate(getDisplayContent());
        try {
            forAllRootTasks(new Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda8
                public final /* synthetic */ ActivityRecord f$0 = null;
                public final /* synthetic */ boolean f$1 = true;

                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Task task = (Task) obj;
                    task.ensureActivitiesVisible(this.f$1, this.f$0);
                }
            });
        } finally {
            this.mAtmService.mTaskSupervisor.endActivityVisibilityUpdate();
        }
    }

    public final int findPositionForRootTask(int i, Task task, boolean z) {
        int indexOf;
        int size = this.mChildren.size() - 1;
        while (true) {
            if (size < 0) {
                size = 0;
                break;
            }
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            boolean z2 = windowContainer == task;
            if (getPriority(windowContainer) <= getPriority(task) && !z2 && (!task.isDesktopModeEnabled() || !task.isActivityTypeHomeOrRecents() || windowContainer.asTask() == null || windowContainer.asTask().isMinimized() || !windowContainer.inFreeformWindowingMode() || windowContainer.compareTo((WindowContainer) task) <= 0)) {
                break;
            }
            size--;
        }
        int i2 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < this.mChildren.size() && getPriority((WindowContainer) this.mChildren.get(i3)) < getPriority(task); i3++) {
            i2 = i3;
        }
        if (task.isAlwaysOnTop() && (indexOf = this.mChildren.indexOf(task)) > i2) {
            i2 = indexOf;
        }
        if (i == Integer.MAX_VALUE) {
            i = this.mChildren.size();
        } else if (i == Integer.MIN_VALUE) {
            i = 0;
        }
        int max = Math.max(Math.min(i, size), i2);
        return max != i ? (z || max < this.mChildren.indexOf(task)) ? max + 1 : max : max;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final void forAllTaskDisplayAreas(Consumer consumer, boolean z) {
        if (z) {
            super.forAllTaskDisplayAreas(consumer, z);
            consumer.accept(this);
        } else {
            consumer.accept(this);
            super.forAllTaskDisplayAreas(consumer, z);
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final boolean forAllTaskDisplayAreas(Predicate predicate, boolean z) {
        return z ? super.forAllTaskDisplayAreas(predicate, z) || predicate.test(this) : predicate.test(this) || super.forAllTaskDisplayAreas(predicate, z);
    }

    public final ActivityRecord getFocusedActivity() {
        Task focusedRootTask = getFocusedRootTask();
        if (focusedRootTask == null) {
            return null;
        }
        ActivityRecord topResumedActivity = focusedRootTask.getTopResumedActivity();
        if (topResumedActivity != null && topResumedActivity.app != null) {
            return topResumedActivity;
        }
        ActivityRecord topPausingActivity = focusedRootTask.getTopPausingActivity();
        return (topPausingActivity == null || topPausingActivity.app == null) ? focusedRootTask.topRunningActivity(true) : topPausingActivity;
    }

    public final Task getFocusedRootTask() {
        Task task = this.mPreferredTopFocusableRootTask;
        if (task != null) {
            return task;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskDisplayArea() != null) {
                Task focusedRootTask = windowContainer.asTaskDisplayArea().getFocusedRootTask();
                if (focusedRootTask != null) {
                    return focusedRootTask;
                }
            } else {
                Task asTask = ((WindowContainer) this.mChildren.get(size)).asTask();
                if (asTask.isFocusableAndVisible()) {
                    return asTask;
                }
            }
        }
        return null;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final Object getItemFromTaskDisplayAreas(Function function, boolean z) {
        if (z) {
            Object itemFromTaskDisplayAreas = super.getItemFromTaskDisplayAreas(function, z);
            return itemFromTaskDisplayAreas != null ? itemFromTaskDisplayAreas : function.apply(this);
        }
        Object apply = function.apply(this);
        return apply != null ? apply : super.getItemFromTaskDisplayAreas(function, z);
    }

    public final Task getLaunchRootTask(int i, int i2, ActivityOptions activityOptions, Task task, int i3, Task task2) {
        Task task3;
        Task fromWindowContainerToken;
        if (activityOptions != null && (fromWindowContainerToken = Task.fromWindowContainerToken(activityOptions.getLaunchRootTask())) != null && fromWindowContainerToken.mCreatedByOrganizer) {
            return fromWindowContainerToken;
        }
        if ((i3 & 4096) != 0 && this.mLaunchAdjacentFlagRootTask != null) {
            if ((task != null && task == task2 && task.inFullscreenWindowingMode() && getTopMostTask() == task && this.mLaunchAdjacentFlagRootTask.inSplitScreenWindowingMode()) || ((task != null && (task.inFreeformWindowingMode() || task.inPinnedWindowingMode())) || isDexMode())) {
                Slog.d("WindowManager", "getLaunchRootTask: Skip adjacent flag, " + task);
            } else if (task == null || task != task2) {
                if (task != null && this.mLaunchAdjacentFlagRootTask.getAdjacentTask() != null && (task == (task3 = this.mLaunchAdjacentFlagRootTask) || task.isDescendantOf(task3))) {
                    if (task.topRunningActivity(false) != null) {
                        if (ResolverActivity.class.getName().equals(task.topRunningActivity(false).mActivityComponent.getClassName())) {
                            return task.getParent().asTask();
                        }
                    }
                    return this.mLaunchAdjacentFlagRootTask.getAdjacentTask();
                }
                if (task == null || !task.inFullscreenWindowingMode() || task.isResizeable(true) || !this.mLaunchAdjacentFlagRootTask.inSplitScreenWindowingMode()) {
                    return this.mLaunchAdjacentFlagRootTask;
                }
                Slog.d("WindowManager", "getLaunchRootTask: Skip adjacent flag, " + task);
            }
        }
        int size = this.mLaunchRootTasks.size() - 1;
        while (true) {
            if (size < 0) {
                if (task != null && (task2 == null || task2.getWindowingMode() != 2)) {
                    Task createdByOrganizerTask = task.getCreatedByOrganizerTask();
                    if (createdByOrganizerTask != null && createdByOrganizerTask.inSplitScreenWindowingMode()) {
                        if (task2 == null || !task2.isFocusableAndVisible()) {
                            if (createdByOrganizerTask.getTopNonFinishingActivity(true, true) == null) {
                                return null;
                            }
                        } else {
                            if (task2.isRootTask()) {
                                return null;
                            }
                            Task createdByOrganizerTask2 = task2.getCreatedByOrganizerTask();
                            if (createdByOrganizerTask2 != null) {
                                return createdByOrganizerTask2;
                            }
                        }
                        return createdByOrganizerTask;
                    }
                    Task adjacentTask = task.getAdjacentTask();
                    if (adjacentTask != null) {
                        return (task2 == null || !(task2 == adjacentTask || task2.isDescendantOf(adjacentTask))) ? task.getCreatedByOrganizerTask() : adjacentTask;
                    }
                }
                return null;
            }
            LaunchRootTaskDef launchRootTaskDef = (LaunchRootTaskDef) this.mLaunchRootTasks.get(size);
            if (ArrayUtils.contains(launchRootTaskDef.windowingModes, i) && ArrayUtils.contains(launchRootTaskDef.activityTypes, i2)) {
                Task task4 = ((LaunchRootTaskDef) this.mLaunchRootTasks.get(size)).task;
                Task adjacentTask2 = task4 != null ? task4.getAdjacentTask() : null;
                return (task == null || adjacentTask2 == null || !(task == adjacentTask2 || task.isDescendantOf(adjacentTask2))) ? task4 : adjacentTask2;
            }
            size--;
        }
    }

    public final LaunchRootTaskDef getLaunchRootTaskDef(Task task) {
        for (int size = this.mLaunchRootTasks.size() - 1; size >= 0; size--) {
            if (((LaunchRootTaskDef) this.mLaunchRootTasks.get(size)).task.mTaskId == task.mTaskId) {
                return (LaunchRootTaskDef) this.mLaunchRootTasks.get(size);
            }
        }
        return null;
    }

    public final Task getNextFocusableRootTask(Task task, boolean z) {
        int windowingMode = task != null ? task.getWindowingMode() : 0;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskDisplayArea() != null) {
                Task nextFocusableRootTask = windowContainer.asTaskDisplayArea().getNextFocusableRootTask(task, z);
                if (nextFocusableRootTask != null) {
                    return nextFocusableRootTask;
                }
            } else {
                Task asTask = ((WindowContainer) this.mChildren.get(size)).asTask();
                if ((!z || asTask != task) && asTask.isFocusableAndVisible() && (!asTask.isAlwaysOnTopFreeform() || windowingMode == 5)) {
                    return asTask;
                }
            }
        }
        return null;
    }

    public int getNextRootTaskId() {
        ActivityTaskSupervisor activityTaskSupervisor = this.mAtmService.mTaskSupervisor;
        return activityTaskSupervisor.getNextTaskIdForUser(activityTaskSupervisor.mRootWindowContainer.mCurrentUser);
    }

    public final Task getOrCreateRootHomeTask(boolean z) {
        Task task = this.mRootHomeTask;
        if (task != null || !canHostHomeTask()) {
            return task;
        }
        Task.Builder builder = new Task.Builder(this.mAtmService);
        builder.mWindowingMode = 0;
        builder.mActivityType = 2;
        builder.mParent = this;
        builder.mOnTop = z;
        builder.mActivityOptions = null;
        return builder.build();
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.wm.Task getOrCreateRootTask(int r17, int r18, boolean r19, com.android.server.wm.Task r20, com.android.server.wm.Task r21, android.app.ActivityOptions r22, int r23) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.TaskDisplayArea.getOrCreateRootTask(int, int, boolean, com.android.server.wm.Task, com.android.server.wm.Task, android.app.ActivityOptions, int):com.android.server.wm.Task");
    }

    public final Task getOrCreateRootTask(ActivityRecord activityRecord, ActivityOptions activityOptions, Task task, Task task2, LaunchParamsController.LaunchParams launchParams, int i, int i2, boolean z) {
        int launchWindowingMode = launchParams != null ? launchParams.mWindowingMode : activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
        int resolveForceLaunchWindowingMode = ForceLaunchWindowingModeUtils.resolveForceLaunchWindowingMode(activityOptions, activityRecord, task);
        if (resolveForceLaunchWindowingMode != 0) {
            launchWindowingMode = resolveForceLaunchWindowingMode;
        }
        if (!isValidWindowingMode(launchWindowingMode, activityRecord, task)) {
            launchWindowingMode = 0;
        }
        return getOrCreateRootTask(launchWindowingMode, i2, z, task, task2, activityOptions, i);
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final int getOrientation(final int i) {
        Task rootTask;
        int orientation = super.getOrientation(i);
        if (!(this.mDisplayContent.mOrientationRequestingTaskDisplayArea == this && !shouldIgnoreOrientationRequest(orientation))) {
            this.mLastOrientationSource = null;
            return ((Integer) reduceOnAllTaskDisplayAreas(new BiFunction() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda6
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    TaskDisplayArea taskDisplayArea = this;
                    int i2 = i;
                    TaskDisplayArea taskDisplayArea2 = (TaskDisplayArea) obj;
                    Integer num = (Integer) obj2;
                    taskDisplayArea.getClass();
                    return (taskDisplayArea2 == taskDisplayArea || num.intValue() != -2) ? num : Integer.valueOf(taskDisplayArea2.getOrientation(i2));
                }
            }, -2)).intValue();
        }
        if (CoreRune.MW_SPLIT_FLEX_PANEL_ORIENTATION_POLICY && this.mDisplayContent.isFlexPanelRunning() && (rootTask = this.mDisplayContent.getRootTask(new TaskDisplayArea$$ExternalSyntheticLambda7(0))) != null && rootTask.isFullscreenRootForStageTask()) {
            return 6;
        }
        if (isSplitScreenVisible() || isSplitScreenStarting()) {
            Task task = this.mRootMainStageTask;
            final Task task2 = task != null ? (Task) task.getParent() : null;
            if (getRootTask(new Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda13
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    Task task3 = (Task) obj;
                    if (task3 == Task.this) {
                        return true;
                    }
                    return task3.inFullscreenWindowingMode() && task3.isVisibleRequested();
                }
            }) == task2) {
                return -1;
            }
        }
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled;
        if (orientation != -2 && orientation != 3) {
            if (zArr[1]) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 7378236902389922467L, 5, null, Long.valueOf(orientation), Long.valueOf(this.mDisplayContent.mDisplayId));
            }
            return orientation;
        }
        if (zArr[1]) {
            DisplayContent displayContent = this.mDisplayContent;
            ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 2005499548343677845L, 5, null, Long.valueOf(displayContent.mDisplayRotation.mLastOrientation), Long.valueOf(displayContent.mDisplayId));
        }
        return this.mDisplayContent.mDisplayRotation.mLastOrientation;
    }

    public final int getPriority(WindowContainer windowContainer) {
        TaskDisplayArea asTaskDisplayArea = windowContainer.asTaskDisplayArea();
        if (asTaskDisplayArea != null) {
            return asTaskDisplayArea.getPriority(asTaskDisplayArea.getTopChild());
        }
        Task asTask = windowContainer.asTask();
        if (!CoreRune.MW_CAPTION_SHELL_FREEFORM_PINNING) {
            if (this.mWmService.mAssistantOnTopOfDream && asTask.isActivityTypeAssistant()) {
                return 4;
            }
            if (asTask.isActivityTypeDream()) {
                return 3;
            }
            if (asTask.inPinnedWindowingMode()) {
                return 2;
            }
            return asTask.isAlwaysOnTop() ? 1 : 0;
        }
        if (this.mWmService.mAssistantOnTopOfDream && asTask.isActivityTypeAssistant()) {
            return 5;
        }
        if (asTask.isActivityTypeDream()) {
            return 4;
        }
        if (asTask.inPinnedWindowingMode()) {
            return 3;
        }
        if (!asTask.isFreeformPinned() || asTask.isMinimized()) {
            return asTask.isAlwaysOnTop() ? 1 : 0;
        }
        return 2;
    }

    public final Task getRootTask(final int i, final int i2) {
        return i2 == 2 ? this.mRootHomeTask : i == 2 ? this.mRootPinnedTask : getRootTask(new Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i3 = i2;
                int i4 = i;
                Task task = (Task) obj;
                if (i3 == 0 && i4 == task.getWindowingMode()) {
                    return true;
                }
                return task.isCompatible(i4, i3);
            }
        });
    }

    @Override // com.android.server.wm.WindowContainer
    public final TaskDisplayArea getTaskDisplayArea() {
        return this;
    }

    public final Task getTopRootStageTask() {
        Task task = this.mRootMainStageTask;
        Task task2 = task != null ? (Task) task.getParent() : null;
        if (task2 == null || task2.mChildren.isEmpty()) {
            return null;
        }
        return (Task) task2.mChildren.get(r2.size() - 1);
    }

    public Task getTopRootTask() {
        return getRootTask(WindowContainer.alwaysTruePredicate());
    }

    public final Task getTopRootTaskInStageType(int i) {
        if (i == 1) {
            return this.mRootMainStageTask;
        }
        if (i == 2) {
            return this.mRootSideStageTask;
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && i == 4) {
            return this.mRootCellStageTask;
        }
        return null;
    }

    public final void handleExceptionForRootStageTask(Task task, Task task2) {
        if (task.isOrganized()) {
            TaskOrganizerController.TaskOrganizerState taskOrganizerState = this.mAtmService.mTaskOrganizerController.getTaskOrganizerState(task.mTaskOrganizer.asBinder());
            if (taskOrganizerState != null) {
                taskOrganizerState.dispose();
            }
            Slog.d("TaskDisplayArea", "addRootTaskReferenceIfNeeded: root stage task=" + task + " already exist on display=" + this + " rootTask=" + task2);
        }
    }

    public final boolean hasChildTaskInSideStage() {
        Task task = this.mRootSideStageTask;
        return task != null && task.hasChild();
    }

    public final boolean hasPinnedTask() {
        return this.mRootPinnedTask != null;
    }

    public final boolean isMultiSplitActive() {
        Task task = this.mRootCellStageTask;
        return task != null && task.hasChild();
    }

    @Override // com.android.server.wm.WindowContainer
    public final boolean isOnTop() {
        return true;
    }

    public final boolean isSplitScreenModeActivated() {
        Task task = this.mRootMainStageTask;
        if (task == null || !task.hasChild()) {
            return false;
        }
        if (task.isVisible() || task.isVisibleRequested()) {
            return true;
        }
        Task rootTask = getRootTask(1, 0);
        return rootTask != null && task.getRootTask() == rootTask;
    }

    public final boolean isSplitScreenStarting() {
        Task task = this.mRootMainStageTask;
        Task task2 = this.mRootSideStageTask;
        return ((task == null || !task.isVisibleRequested() || task.isVisible()) && (task2 == null || !task2.isVisibleRequested() || task2.isVisible())) ? false : true;
    }

    public final boolean isSplitScreenVisible() {
        Task task = this.mRootMainStageTask;
        Task task2 = this.mRootSideStageTask;
        return (task != null && (task.isVisible() || task.isVisibleRequested())) || (task2 != null && (task2.isVisible() || task2.isVisibleRequested()));
    }

    @Override // com.android.server.wm.DisplayArea
    public final boolean isTaskDisplayArea() {
        return true;
    }

    public final boolean isUnderHomeTask(Task task) {
        return this.mChildren.indexOf(this.mRootHomeTask) > this.mChildren.indexOf(task);
    }

    public final boolean isValidWindowingMode(int i, ActivityRecord activityRecord, Task task) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        boolean z = activityTaskManagerService.mSupportsMultiWindow;
        boolean z2 = activityTaskManagerService.mSupportsFreeformWindowManagement;
        boolean z3 = activityTaskManagerService.mSupportsPictureInPicture;
        if (z) {
            if (task != null) {
                z2 = task.mAtmService.mSupportsFreeformWindowManagement && task.supportsMultiWindowInDisplayArea(this, false);
                z = task.supportsMultiWindowInDisplayArea(this, false) || (i == 2 && z3);
            } else if (activityRecord != null) {
                z2 = activityRecord.supportsFreeformInDisplayArea(this);
                z3 = activityRecord.supportsPictureInPicture();
                z = activityRecord.supportsMultiWindowInDisplayArea(this);
            }
            if (CoreRune.MT_SIZE_COMPAT_POLICY && i == 5 && !z2) {
                SizeCompatPolicyManager sizeCompatPolicyManager = SizeCompatPolicyManager.LazyHolder.sManager;
                if (task == null) {
                    task = activityRecord.task;
                }
                sizeCompatPolicyManager.getClass();
                if (SizeCompatPolicyManager.getCompatPolicy(task, false) != null) {
                    z = true;
                    z2 = true;
                }
            }
        }
        return i != 0 && isWindowingModeSupported(i, z, z2, z3);
    }

    @Override // com.android.server.wm.WindowContainer
    public final void migrateToNewSurfaceControl(SurfaceControl.Transaction transaction) {
        super.migrateToNewSurfaceControl(transaction);
        if (this.mColorLayerCounter > 0) {
            setBackgroundColor(this.mBackgroundColor, true);
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            transaction.reparent(this.mChangeLeashAnchor, this.mSurfaceControl);
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
            transaction.reparent(this.mFloatingLeashAnchor, this.mSurfaceControl);
        }
        reassignLayer(transaction);
        scheduleAnimation();
    }

    public final void moveHomeRootTaskToFront(String str) {
        Task orCreateRootHomeTask = getOrCreateRootHomeTask(false);
        if (orCreateRootHomeTask != null) {
            orCreateRootHomeTask.moveToFront(str, null);
        }
    }

    public final void moveRootTaskBehindBottomMostVisibleRootTask(Task task) {
        Task asTask;
        if (task.shouldBeVisible(null)) {
            return;
        }
        task.getParent().positionChildAt(Integer.MIN_VALUE, task, false);
        boolean isRootTask = task.isRootTask();
        int size = isRootTask ? this.mChildren.size() : task.getParent().getChildCount();
        for (int i = 0; i < size; i++) {
            if (isRootTask) {
                WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i);
                if (windowContainer.asTaskDisplayArea() != null) {
                    TaskDisplayArea asTaskDisplayArea = windowContainer.asTaskDisplayArea();
                    asTaskDisplayArea.getClass();
                    asTask = asTaskDisplayArea.getRootTask((Predicate) new TaskDisplayArea$$ExternalSyntheticLambda7(1), false);
                } else {
                    asTask = windowContainer.asTask();
                }
            } else {
                asTask = task.getParent().getChildAt(i).asTask();
            }
            if (asTask != task && asTask != null) {
                boolean z = asTask.getWindowingMode() == 1;
                if (asTask.shouldBeVisible(null) && z) {
                    task.getParent().positionChildAt(Math.max(0, i - 1), task, false);
                    return;
                }
            }
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public final void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent.mMagnificationSpec != null) {
            displayContent.applyMagnificationSpec(displayContent.getPendingTransaction(), displayContent.mMagnificationSpec);
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final void onChildPositionChanged(WindowContainer windowContainer) {
        super.onChildPositionChanged(windowContainer);
        RootWindowContainer rootWindowContainer = this.mRootWindowContainer;
        if (rootWindowContainer.mTaskLayersChanged) {
            return;
        }
        rootWindowContainer.mTaskLayersChanged = true;
        rootWindowContainer.mService.mH.post(rootWindowContainer.mRankTaskLayersRunnable);
    }

    public final void onLeafTaskMoved(Task task, boolean z, boolean z2) {
        if (z2) {
            TaskChangeNotificationController taskChangeNotificationController = this.mAtmService.mTaskChangeNotificationController;
            Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(27, task.getTaskInfo());
            taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyTaskMovedToBack, obtainMessage);
            obtainMessage.sendToTarget();
        }
        if (!z) {
            if (task.mTaskId == this.mLastLeafTaskToFrontId) {
                this.mLastLeafTaskToFrontId = -1;
                ActivityRecord topMostActivity = getTopMostActivity();
                if (topMostActivity != null) {
                    this.mAtmService.mTaskChangeNotificationController.notifyTaskMovedToFront(topMostActivity.task.getTaskInfo());
                    return;
                }
                return;
            }
            return;
        }
        if (task.mTaskId == this.mLastLeafTaskToFrontId || task.topRunningActivityLocked() == null) {
            return;
        }
        int i = task.mTaskId;
        this.mLastLeafTaskToFrontId = i;
        EventLog.writeEvent(30002, Integer.valueOf(task.mUserId), Integer.valueOf(i), Integer.valueOf(this.mDisplayContent.mDisplayId));
        this.mAtmService.mTaskChangeNotificationController.notifyTaskMovedToFront(task.getTaskInfo());
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public final void onParentChanged(ConfigurationContainer configurationContainer, ConfigurationContainer configurationContainer2) {
        if (getParent() != null) {
            super.onParentChanged(configurationContainer, configurationContainer2, new Runnable() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    TaskDisplayArea taskDisplayArea = TaskDisplayArea.this;
                    taskDisplayArea.getClass();
                    if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                        taskDisplayArea.mChangeLeashAnchor = taskDisplayArea.makeChildSurface(null).setName("ChangeLeashAnchor").setCallsite("TaskDisplayArea.onParentChanged").build();
                        taskDisplayArea.getPendingTransaction().show(taskDisplayArea.mChangeLeashAnchor);
                    }
                    if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
                        taskDisplayArea.mFloatingLeashAnchor = taskDisplayArea.makeChildSurface(null).setName("FloatingLeashAnchor").setCallsite("TaskDisplayArea.onParentChanged").build();
                        taskDisplayArea.getPendingTransaction().show(taskDisplayArea.mFloatingLeashAnchor);
                    }
                }
            });
            return;
        }
        super.onParentChanged(configurationContainer, configurationContainer2);
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get()).remove(this.mChangeLeashAnchor).apply();
            this.mChangeLeashAnchor = null;
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
            ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get()).remove(this.mFloatingLeashAnchor).apply();
            this.mFloatingLeashAnchor = null;
        }
    }

    public final void onRootTaskOrderChanged(final Task task) {
        WindowManagerService windowManagerService;
        RecentsAnimationController recentsAnimationController;
        for (int size = this.mRootTaskOrderChangedCallbacks.size() - 1; size >= 0; size--) {
            RecentsAnimation recentsAnimation = (RecentsAnimation) this.mRootTaskOrderChangedCallbacks.get(size);
            recentsAnimation.getClass();
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_RECENTS_ANIMATIONS_enabled[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_RECENTS_ANIMATIONS, 4515487264815398694L, 0, null, String.valueOf(task));
            }
            if (recentsAnimation.mDefaultTaskDisplayArea.getRootTask(new Predicate() { // from class: com.android.server.wm.RecentsAnimation$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((Task) obj) == Task.this;
                }
            }) != null && task.shouldBeVisible(null) && (recentsAnimationController = (windowManagerService = recentsAnimation.mWindowManager).mRecentsAnimationController) != null && ((!recentsAnimationController.isAnimatingTask(task.getTopMostTask()) || recentsAnimationController.isTargetApp(task.getTopNonFinishingActivity(true, true))) && recentsAnimationController.mRequestDeferCancelUntilNextTransition)) {
                windowManagerService.prepareAppTransitionNone();
                recentsAnimationController.mCancelOnNextTransitionStart = true;
            }
        }
    }

    public final void onStageSplitScreenDismissed(final Task task, boolean z) {
        Task rootTask;
        Task rootTask2;
        this.mAtmService.deferWindowLayout();
        final Task[] taskArr = new Task[1];
        try {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            this.mTmpTasks.clear();
            forAllTasks(new Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ActivityRecord activityRecord;
                    TaskDisplayArea taskDisplayArea = TaskDisplayArea.this;
                    Task[] taskArr2 = taskArr;
                    Task task2 = task;
                    Task task3 = (Task) obj;
                    taskDisplayArea.getClass();
                    if (task3.mCreatedByOrganizer && task3.inSplitScreenWindowingMode() && task3.hasChild()) {
                        if (CoreRune.MW_SPLIT_FLEX_PANEL_LAUNCH_POLICY && (activityRecord = task3.topRunningActivity(false)) != null && activityRecord.mIsFlexPanel) {
                            taskArr2[0] = task3;
                        }
                        if (task2 == null || !task3.mChildren.contains(task2)) {
                            taskDisplayArea.mTmpTasks.add(task3);
                        } else {
                            taskDisplayArea.mTmpTasks.add(0, task3);
                        }
                    }
                }
            });
            for (int size = this.mTmpTasks.size() - 1; size >= 0; size--) {
                Task task2 = (Task) this.mTmpTasks.get(size);
                for (int i = 0; i < task2.getChildCount(); i++) {
                    WindowContainer childAt = task2.getChildAt(i);
                    childAt.forAllActivities(new TaskDisplayArea$$ExternalSyntheticLambda10());
                    WindowContainerToken windowContainerToken = childAt.mRemoteToken.toWindowContainerToken();
                    windowContainerTransaction.reparent(windowContainerToken, (WindowContainerToken) null, z);
                    windowContainerTransaction.setBounds(windowContainerToken, (Rect) null);
                }
            }
            this.mAtmService.mWindowOrganizerController.applyTransaction(windowContainerTransaction);
            if (task != null) {
                rootTask2 = task;
            } else {
                try {
                    rootTask2 = getRootTask(1, 0);
                } finally {
                }
            }
            Task orCreateRootHomeTask = getOrCreateRootHomeTask(false);
            if (orCreateRootHomeTask != null && (((rootTask2 != null && orCreateRootHomeTask != getTopRootTask()) || task != null) && taskArr[0] == null)) {
                orCreateRootHomeTask.moveToFront("onStageSplitScreenDismissed", null);
                rootTask2.moveToFront("onStageSplitScreenDismissed", null);
            }
        } catch (Throwable th) {
            if (task != null) {
                rootTask = task;
            } else {
                try {
                    rootTask = getRootTask(1, 0);
                } finally {
                }
            }
            Task orCreateRootHomeTask2 = getOrCreateRootHomeTask(false);
            if (orCreateRootHomeTask2 != null && (((rootTask != null && orCreateRootHomeTask2 != getTopRootTask()) || task != null) && taskArr[0] == null)) {
                orCreateRootHomeTask2.moveToFront("onStageSplitScreenDismissed", null);
                rootTask.moveToFront("onStageSplitScreenDismissed", null);
            }
            throw th;
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final void positionChildAt(int i, WindowContainer windowContainer, boolean z) {
        ActivityRecord activityRecord;
        WindowProcessController windowProcessController;
        if (windowContainer.asTaskDisplayArea() != null) {
            super.positionChildAt(i, windowContainer, z);
            return;
        }
        if (windowContainer.asTask() == null) {
            throw new IllegalArgumentException("TaskDisplayArea can only position Task and TaskDisplayArea, but found " + windowContainer);
        }
        Task asTask = windowContainer.asTask();
        boolean z2 = i >= getChildCount() - 1;
        boolean z3 = i <= 0;
        int indexOf = this.mChildren.indexOf(asTask);
        if (asTask.isAlwaysOnTop() && !z2) {
            ActivityRecord rootActivity = asTask.getRootActivity(true, false);
            if (!FreeformController.useAlwaysOnTopFreeform(asTask.getWindowingMode(), this.mDisplayContent) && (rootActivity == null || !rootActivity.mLaunchedFromBubble)) {
                Slog.w("WindowManager", "Ignoring move of always-on-top root task=" + this + " to bottom");
                super.positionChildAt(indexOf, asTask, false);
                return;
            }
            asTask.setAlwaysOnTop(false);
        }
        if ((!this.mDisplayContent.mDisplay.isTrusted() || this.mDisplayContent.mDontMoveToTop) && !getParent().isOnTop()) {
            z = false;
        }
        int findPositionForRootTask = findPositionForRootTask(i, asTask, false);
        super.positionChildAt(findPositionForRootTask, asTask, false);
        if (z && getParent() != null && (z2 || z3)) {
            if (z2 && asTask.isFocusableAndVisible()) {
                this.mPreferredTopFocusableRootTask = asTask;
            } else if (this.mPreferredTopFocusableRootTask == asTask) {
                this.mPreferredTopFocusableRootTask = null;
            }
            getParent().positionChildAt(z2 ? Integer.MAX_VALUE : Integer.MIN_VALUE, this, true);
        }
        asTask.updateTaskMovement(findPositionForRootTask, z2, z3);
        if (z2 && asTask != this.mRootPinnedTask && asTask.isTopActivityFocusable()) {
            this.mPreferredTopFocusableRootTask = asTask.shouldBeVisible(null) ? asTask : null;
        } else if (this.mPreferredTopFocusableRootTask == asTask) {
            this.mPreferredTopFocusableRootTask = null;
        }
        if (this.mPreferredTopFocusableRootTask == null && asTask.inFullscreenWindowingMode() && z3) {
            Task focusedRootTask = getFocusedRootTask();
            Task rootTask = getRootTask(1, 0);
            if (focusedRootTask != null && focusedRootTask.inFreeformWindowingMode() && rootTask != null && !rootTask.isFullscreenRootForStageTask() && rootTask.isFocusableAndVisible()) {
                this.mPreferredTopFocusableRootTask = rootTask;
            }
        }
        this.mAtmService.mTaskSupervisor.updateTopResumedActivityIfNeeded("positionChildTaskAt");
        if (asTask.getTopResumedActivity() == null && i == Integer.MIN_VALUE) {
            Task task = this.mRootMainStageTask;
            ActivityRecord topResumedActivity = this.mRootWindowContainer.getTopResumedActivity();
            if (task != null && task.isDescendantOf(asTask) && topResumedActivity != null && !topResumedActivity.equals(this.mAtmService.mLastResumedActivity)) {
                this.mAtmService.setLastResumedActivityUncheckLocked(topResumedActivity, "positionChildAt(stageRoot)");
            }
        }
        if (asTask.isOnTop() && (activityRecord = asTask.topRunningActivity(false)) != null && (windowProcessController = activityRecord.app) != null) {
            windowProcessController.updateActivityConfigurationListener(activityRecord);
        }
        if (this.mChildren.indexOf(asTask) != indexOf) {
            onRootTaskOrderChanged(asTask);
        }
    }

    public final void positionTaskBehindHome(Task task) {
        WindowContainer parent = getOrCreateRootHomeTask(false).getParent();
        Task asTask = parent != null ? parent.asTask() : null;
        if (asTask == null) {
            if (task.getParent() == this) {
                positionChildAt(Integer.MIN_VALUE, task, false);
                return;
            } else {
                task.reparent(this, false);
                return;
            }
        }
        if (asTask == task.getParent()) {
            asTask.positionChildAtBottom(task, asTask.getDisplayArea().getNextFocusableRootTask(task.getRootTask(), true) == null);
        } else {
            task.reparent(asTask, false, 2, false, false, "positionTaskBehindHome");
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public final Object reduceOnAllTaskDisplayAreas(BiFunction biFunction, Object obj, boolean z) {
        return z ? biFunction.apply(this, super.reduceOnAllTaskDisplayAreas(biFunction, obj, z)) : super.reduceOnAllTaskDisplayAreas(biFunction, biFunction.apply(this, obj), z);
    }

    public final Task remove() {
        this.mPreferredTopFocusableRootTask = null;
        boolean shouldDestroyContentOnRemove = this.mDisplayContent.shouldDestroyContentOnRemove();
        TaskDisplayArea defaultTaskDisplayArea = this.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
        int size = this.mChildren.size();
        int i = 0;
        Task task = null;
        while (i < size) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i);
            if (windowContainer.asTaskDisplayArea() != null) {
                task = windowContainer.asTaskDisplayArea().remove();
            } else {
                Task asTask = ((WindowContainer) this.mChildren.get(i)).asTask();
                if (shouldDestroyContentOnRemove || !asTask.isActivityTypeStandardOrUndefined() || asTask.mCreatedByOrganizer) {
                    asTask.remove("removeTaskDisplayArea", false);
                } else {
                    Task launchRootTask = defaultTaskDisplayArea.getLaunchRootTask(asTask.getWindowingMode(), asTask.getActivityType(), null, null, 0, null);
                    asTask.reparent(launchRootTask == null ? defaultTaskDisplayArea : launchRootTask, Integer.MAX_VALUE);
                    if (launchRootTask != null || asTask.getRequestedOverrideWindowingMode() != 1 || defaultTaskDisplayArea.getWindowingMode() == 1) {
                        asTask.setWindowingMode(0);
                    }
                    task = asTask;
                }
                i -= size - this.mChildren.size();
                size = this.mChildren.size();
            }
            i++;
        }
        if (task != null && !task.isRootTask()) {
            task.getRootTask().moveToFront("display-removed", null);
        }
        this.mRemoved = true;
        return task;
    }

    @Override // com.android.server.wm.WindowContainer
    public final void removeChild(WindowContainer windowContainer) {
        if (windowContainer.asTaskDisplayArea() != null) {
            super.removeChild(windowContainer);
            return;
        }
        if (windowContainer.asTask() == null) {
            throw new IllegalArgumentException("TaskDisplayArea can only remove Task and TaskDisplayArea, but found " + windowContainer);
        }
        Task asTask = windowContainer.asTask();
        super.removeChild(asTask);
        if (this.mPreferredTopFocusableRootTask == asTask) {
            this.mPreferredTopFocusableRootTask = null;
        }
        if (this.mLaunchAdjacentFlagRootTask == asTask) {
            this.mLaunchAdjacentFlagRootTask = null;
        }
        this.mDisplayContent.releaseSelfIfNeeded();
        onRootTaskOrderChanged(asTask);
        this.mAtmService.updateSleepIfNeededLocked();
        removeRootTaskReferenceIfNeeded(asTask);
        if (asTask.mIsMinimized) {
            asTask.mIsMinimized = false;
            if (asTask.isDexMode()) {
                DexController dexController = asTask.mAtmService.mDexController;
                if (((ArrayList) dexController.mMinimizedToggleTasks).contains(asTask)) {
                    ((ArrayList) dexController.mMinimizedToggleTasks).remove(asTask);
                }
            }
            asTask.mAtmService.mFreeformController.notifyFreeformMinimizeStateChanged(-1, -1, asTask, true);
            if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY && asTask.getDisplayId() == 0) {
                asTask.mLastMinimizedDisplayType = -1;
                asTask.mLastMinimizedRotation = -1;
            }
        }
        if (this.mDisplayContent.mDisplayId == 0) {
            this.mAtmService.mFreeformController.scheduleUnbindMinimizeContainerService("removeChildTask");
        }
    }

    public final void removeRootTaskReferenceIfNeeded(Task task) {
        if (task == this.mRootHomeTask) {
            this.mRootHomeTask = null;
            return;
        }
        if (task == this.mRootPinnedTask) {
            this.mRootPinnedTask = null;
            return;
        }
        if (task.mTaskOrganizer == null) {
            if (task == this.mRootMainStageTask) {
                this.mRootMainStageTask = null;
                return;
            }
            if (task == this.mRootSideStageTask) {
                this.mRootSideStageTask = null;
            } else if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && task == this.mRootCellStageTask) {
                this.mRootCellStageTask = null;
            }
        }
    }

    public final void setBackgroundColor(int i, boolean z) {
        this.mBackgroundColor = i;
        Color valueOf = Color.valueOf(i);
        if (!z) {
            this.mColorLayerCounter++;
        }
        if (this.mSurfaceControl != null) {
            getPendingTransaction().setColor(this.mSurfaceControl, new float[]{valueOf.red(), valueOf.green(), valueOf.blue()});
            scheduleAnimation();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public final void setInitialSurfaceControlProperties(SurfaceControl.Builder builder) {
        builder.setEffectLayer();
        super.setInitialSurfaceControlProperties(builder);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public final void setWindowingMode(int i) {
        this.mTempConfiguration.setTo(getRequestedOverrideConfiguration());
        this.mTempConfiguration.windowConfiguration.setWindowingMode(i);
        onRequestedOverrideConfigurationChanged(this.mTempConfiguration);
    }

    public final void stopFreeformTaskPinning(Task task) {
        FreeformTaskPinningController freeformTaskPinningController = this.mFreeformTaskPinningController;
        Task task2 = freeformTaskPinningController.mPinnedTask;
        TaskDisplayArea taskDisplayArea = freeformTaskPinningController.mTaskDisplayArea;
        if (task2 != task && CoreRune.MT_NEW_DEX_TASK_PINNING && !taskDisplayArea.isNewDexMode()) {
            android.util.secutil.Slog.d("FreeformTaskPinningController", "Failed to stop freeform task pinning, task requested isn't in pinning mode.");
            return;
        }
        if (CoreRune.MT_NEW_DEX_TASK_PINNING && !taskDisplayArea.isNewDexMode()) {
            taskDisplayArea.setFreeformTaskPinning(1);
        }
        task.setFreeformTaskPinning(0);
        freeformTaskPinningController.mPinnedTask = null;
    }

    public final ActivityRecord topRunningActivity(boolean z) {
        Task focusedRootTask = getFocusedRootTask();
        ActivityRecord activityRecord = focusedRootTask != null ? focusedRootTask.topRunningActivity(false) : null;
        if (activityRecord == null) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
                if (windowContainer.asTaskDisplayArea() != null) {
                    activityRecord = windowContainer.asTaskDisplayArea().topRunningActivity(z);
                    if (activityRecord != null) {
                        break;
                    }
                } else {
                    Task asTask = ((WindowContainer) this.mChildren.get(size)).asTask();
                    if (asTask != focusedRootTask && asTask.isTopActivityFocusable() && (activityRecord = asTask.topRunningActivity(false)) != null) {
                        break;
                    }
                }
            }
        }
        if (activityRecord == null || !z || !this.mRootWindowContainer.mTaskSupervisor.mKeyguardController.isKeyguardLocked(activityRecord.getDisplayId()) || activityRecord.canShowWhenLocked()) {
            return activityRecord;
        }
        return null;
    }

    public final void updateLastFocusedRootTask(String str, Task task) {
        Task focusedRootTask;
        if (str == null || (focusedRootTask = getFocusedRootTask()) == task) {
            return;
        }
        if (this.mDisplayContent.mSleeping && focusedRootTask != null) {
            focusedRootTask.clearLastPausedActivity();
        }
        this.mLastFocusedRootTask = task;
        int i = this.mRootWindowContainer.mCurrentUser;
        int i2 = this.mDisplayContent.mDisplayId;
        int i3 = focusedRootTask == null ? -1 : focusedRootTask.getRootTask().mTaskId;
        Task task2 = this.mLastFocusedRootTask;
        EventLog.writeEvent(30044, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(task2 != null ? task2.getRootTask().mTaskId : -1), str);
    }
}
