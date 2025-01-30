package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.p005os.IInstalld;
import android.util.IntArray;
import android.util.Slog;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.DisplayArea;
import com.android.server.wm.DisplayContent;
import com.android.server.wm.LaunchParamsController;
import com.android.server.wm.RemoteAnimationController;
import com.android.server.wm.Task;
import com.android.server.wm.TaskOrganizerController;
import com.samsung.android.multiwindow.TaskOrganizerInfo;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final class TaskDisplayArea extends DisplayArea {
    public ActivityTaskManagerService mAtmService;
    public int mBackgroundColor;
    public final boolean mCanHostHomeTask;
    public SurfaceControl mChangeLeashAnchor;
    public int mColorLayerCounter;
    public final boolean mCreatedByOrganizer;
    public DisplayContent mDisplayContent;
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
    public ArrayList mRootTaskOrderChangedCallbacks;
    public RootWindowContainer mRootWindowContainer;
    public final Configuration mTempConfiguration;
    public final ArrayList mTmpAlwaysOnTopChildren;
    public final ArrayList mTmpFreeformChildren;
    public final ArrayList mTmpFreeformPinnedChildren;
    public final ArrayList mTmpHomeChildren;
    public final IntArray mTmpNeedsZBoostIndexes;
    public final ArrayList mTmpNormalChildren;
    public final ArrayList mTmpRecentsChildren;
    public ArrayList mTmpTasks;

    public interface OnRootTaskOrderChangedListener {
        void onRootTaskOrderChanged(Task task);
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

    public static /* synthetic */ boolean lambda$getTopRootTask$1(Task task) {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    public TaskDisplayArea asTaskDisplayArea() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public TaskDisplayArea getTaskDisplayArea() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean isOnTop() {
        return true;
    }

    @Override // com.android.server.wm.DisplayArea
    public boolean isTaskDisplayArea() {
        return true;
    }

    public class LaunchRootTaskDef {
        public int[] activityTypes;
        public Task task;
        public int[] windowingModes;

        public LaunchRootTaskDef() {
        }

        public boolean contains(int i, int i2) {
            return ArrayUtils.contains(this.windowingModes, i) && ArrayUtils.contains(this.activityTypes, i2);
        }
    }

    public TaskDisplayArea(DisplayContent displayContent, WindowManagerService windowManagerService, String str, int i) {
        this(displayContent, windowManagerService, str, i, false, true);
    }

    public TaskDisplayArea(DisplayContent displayContent, WindowManagerService windowManagerService, String str, int i, boolean z) {
        this(displayContent, windowManagerService, str, i, z, true);
    }

    public TaskDisplayArea(DisplayContent displayContent, WindowManagerService windowManagerService, String str, int i, boolean z, boolean z2) {
        super(windowManagerService, DisplayArea.Type.ANY, str, i);
        this.mBackgroundColor = 0;
        this.mColorLayerCounter = 0;
        this.mTmpAlwaysOnTopChildren = new ArrayList();
        this.mTmpNormalChildren = new ArrayList();
        this.mTmpHomeChildren = new ArrayList();
        this.mTmpNeedsZBoostIndexes = new IntArray();
        this.mTmpRecentsChildren = new ArrayList();
        this.mTmpFreeformChildren = new ArrayList();
        this.mTmpFreeformPinnedChildren = new ArrayList();
        this.mTmpTasks = new ArrayList();
        this.mLaunchRootTasks = new ArrayList();
        this.mRootTaskOrderChangedCallbacks = new ArrayList();
        this.mTempConfiguration = new Configuration();
        this.mDisplayContent = displayContent;
        this.mRootWindowContainer = windowManagerService.mRoot;
        this.mAtmService = windowManagerService.mAtmService;
        this.mCreatedByOrganizer = z;
        this.mCanHostHomeTask = z2;
        this.mFreeformTaskPinningController = new FreeformTaskPinningController(this);
    }

    public Task getRootTask(final int i, final int i2) {
        if (i2 == 2) {
            return this.mRootHomeTask;
        }
        if (i == 2) {
            return this.mRootPinnedTask;
        }
        return getRootTask(new Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getRootTask$0;
                lambda$getRootTask$0 = TaskDisplayArea.lambda$getRootTask$0(i2, i, (Task) obj);
                return lambda$getRootTask$0;
            }
        });
    }

    public static /* synthetic */ boolean lambda$getRootTask$0(int i, int i2, Task task) {
        if (i == 0 && i2 == task.getWindowingMode()) {
            return true;
        }
        return task.isCompatible(i2, i);
    }

    public Task getTopRootTask() {
        return getRootTask(new Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getTopRootTask$1;
                lambda$getTopRootTask$1 = TaskDisplayArea.lambda$getTopRootTask$1((Task) obj);
                return lambda$getTopRootTask$1;
            }
        });
    }

    public Task getRootHomeTask() {
        return this.mRootHomeTask;
    }

    public Task getRootPinnedTask() {
        return this.mRootPinnedTask;
    }

    public ArrayList getVisibleTasks() {
        final ArrayList arrayList = new ArrayList();
        forAllTasks(new Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskDisplayArea.lambda$getVisibleTasks$2(arrayList, (Task) obj);
            }
        });
        return arrayList;
    }

    public static /* synthetic */ void lambda$getVisibleTasks$2(ArrayList arrayList, Task task) {
        if (task.isLeafTask() && task.isVisible()) {
            arrayList.add(task);
        }
    }

    public void onRootTaskWindowingModeChanged(Task task) {
        removeRootTaskReferenceIfNeeded(task);
        addRootTaskReferenceIfNeeded(task);
        if (task != this.mRootPinnedTask || getTopRootTask() == task) {
            return;
        }
        positionChildAt(Integer.MAX_VALUE, task, false);
    }

    public void addRootTaskReferenceIfNeeded(Task task) {
        if (task.isActivityTypeHome()) {
            Task task2 = this.mRootHomeTask;
            if (task2 != null) {
                if (!task.isDescendantOf(task2)) {
                    throw new IllegalArgumentException("addRootTaskReferenceIfNeeded: root home task=" + this.mRootHomeTask + " already exist on display=" + this + " rootTask=" + task);
                }
            } else {
                this.mRootHomeTask = task;
            }
        }
        if (task.isRootTask()) {
            int windowingMode = task.getWindowingMode();
            if (windowingMode == 2) {
                if (this.mRootPinnedTask != null) {
                    throw new IllegalArgumentException("addRootTaskReferenceIfNeeded: root pinned task=" + this.mRootPinnedTask + " already exist on display=" + this + " rootTask=" + task);
                }
                this.mRootPinnedTask = task;
                return;
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

    public void removeRootTaskReferenceIfNeeded(Task task) {
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

    @Override // com.android.server.wm.WindowContainer
    public void setInitialSurfaceControlProperties(SurfaceControl.Builder builder) {
        builder.setEffectLayer();
        super.setInitialSurfaceControlProperties(builder);
    }

    @Override // com.android.server.wm.WindowContainer
    public void addChild(WindowContainer windowContainer, int i) {
        if (windowContainer.asTaskDisplayArea() != null) {
            super.addChild(windowContainer, i);
        } else {
            if (windowContainer.asTask() != null) {
                addChildTask(windowContainer.asTask(), i);
                return;
            }
            throw new IllegalArgumentException("TaskDisplayArea can only add Task and TaskDisplayArea, but found " + windowContainer);
        }
    }

    public final void addChildTask(Task task, int i) {
        addRootTaskReferenceIfNeeded(task);
        super.addChild(task, findPositionForRootTask(i, task, true));
        if (this.mPreferredTopFocusableRootTask != null && task.isFocusable() && this.mPreferredTopFocusableRootTask.compareTo((WindowContainer) task) < 0) {
            this.mPreferredTopFocusableRootTask = null;
        }
        this.mAtmService.mTaskSupervisor.updateTopResumedActivityIfNeeded("addChildTask");
        this.mAtmService.updateSleepIfNeededLocked();
        onRootTaskOrderChanged(task);
    }

    @Override // com.android.server.wm.WindowContainer
    public void removeChild(WindowContainer windowContainer) {
        if (windowContainer.asTaskDisplayArea() != null) {
            super.removeChild(windowContainer);
        } else {
            if (windowContainer.asTask() != null) {
                removeChildTask(windowContainer.asTask());
                return;
            }
            throw new IllegalArgumentException("TaskDisplayArea can only remove Task and TaskDisplayArea, but found " + windowContainer);
        }
    }

    public final void removeChildTask(Task task) {
        super.removeChild(task);
        onRootTaskRemoved(task);
        this.mAtmService.updateSleepIfNeededLocked();
        removeRootTaskReferenceIfNeeded(task);
        task.setUnminimizedWhenRemoved();
        if (getDisplayId() == 0) {
            this.mAtmService.mFreeformController.scheduleUnbindMinimizeContainerService("removeChildTask");
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public void positionChildAt(int i, WindowContainer windowContainer, boolean z) {
        if (windowContainer.asTaskDisplayArea() != null) {
            super.positionChildAt(i, windowContainer, z);
        } else {
            if (windowContainer.asTask() != null) {
                positionChildTaskAt(i, windowContainer.asTask(), z);
                return;
            }
            throw new IllegalArgumentException("TaskDisplayArea can only position Task and TaskDisplayArea, but found " + windowContainer);
        }
    }

    public final void positionChildTaskAt(int i, Task task, boolean z) {
        ActivityRecord activityRecord;
        WindowProcessController windowProcessController;
        boolean z2 = i >= getChildCount() - 1;
        boolean z3 = i <= 0;
        int indexOf = this.mChildren.indexOf(task);
        if (task.isAlwaysOnTop() && !z2) {
            if (FreeformController.useAlwaysOnTopFreeform(task.getWindowingMode(), this.mDisplayContent)) {
                task.setAlwaysOnTop(false);
            } else {
                Slog.w(StartingSurfaceController.TAG, "Ignoring move of always-on-top root task=" + this + " to bottom");
                super.positionChildAt(indexOf, task, false);
                return;
            }
        }
        if ((!this.mDisplayContent.isTrusted() || this.mDisplayContent.mDontMoveToTop) && !getParent().isOnTop()) {
            z = false;
        }
        int findPositionForRootTask = findPositionForRootTask(i, task, false);
        super.positionChildAt(findPositionForRootTask, task, false);
        if (z && getParent() != null && (z2 || z3)) {
            if (z2 && task.isFocusableAndVisible()) {
                this.mPreferredTopFocusableRootTask = task;
            } else if (this.mPreferredTopFocusableRootTask == task) {
                this.mPreferredTopFocusableRootTask = null;
            }
            getParent().positionChildAt(z2 ? Integer.MAX_VALUE : Integer.MIN_VALUE, this, true);
        }
        task.updateTaskMovement(z2, z3, findPositionForRootTask);
        if (z2 && task.isTopActivityFocusable()) {
            this.mPreferredTopFocusableRootTask = task.shouldBeVisible(null) ? task : null;
        } else if (this.mPreferredTopFocusableRootTask == task) {
            this.mPreferredTopFocusableRootTask = null;
        }
        if (this.mPreferredTopFocusableRootTask == null && task.inFullscreenWindowingMode() && z3) {
            Task focusedRootTask = getFocusedRootTask();
            Task rootTask = getRootTask(1, 0);
            if (focusedRootTask != null && focusedRootTask.inFreeformWindowingMode() && rootTask != null && !rootTask.isFullscreenRootForStageTask() && rootTask.isFocusableAndVisible()) {
                this.mPreferredTopFocusableRootTask = rootTask;
            }
        }
        this.mAtmService.mTaskSupervisor.updateTopResumedActivityIfNeeded("positionChildTaskAt");
        if (task.getTopResumedActivity() == null && i == Integer.MIN_VALUE) {
            Task rootMainStageTask = getRootMainStageTask();
            ActivityRecord topResumedActivity = this.mRootWindowContainer.getTopResumedActivity();
            if (rootMainStageTask != null && rootMainStageTask.isDescendantOf(task) && topResumedActivity != null && !topResumedActivity.equals(this.mAtmService.mLastResumedActivity)) {
                this.mAtmService.setLastResumedActivityUncheckLocked(topResumedActivity, "positionChildAt(stageRoot)");
            }
        }
        if (task.isOnTop() && (activityRecord = task.topRunningActivity()) != null && (windowProcessController = activityRecord.app) != null) {
            windowProcessController.updateTopActivityIfNeeded(activityRecord);
        }
        if (this.mChildren.indexOf(task) != indexOf) {
            onRootTaskOrderChanged(task);
        }
    }

    public void onLeafTaskRemoved(int i) {
        if (this.mLastLeafTaskToFrontId == i) {
            this.mLastLeafTaskToFrontId = -1;
        }
    }

    public void onLeafTaskMoved(Task task, boolean z, boolean z2) {
        if (z2) {
            this.mAtmService.getTaskChangeNotificationController().notifyTaskMovedToBack(task.getTaskInfo());
        }
        if (!z) {
            if (task.mTaskId == this.mLastLeafTaskToFrontId) {
                this.mLastLeafTaskToFrontId = -1;
                ActivityRecord topMostActivity = getTopMostActivity();
                if (topMostActivity != null) {
                    this.mAtmService.getTaskChangeNotificationController().notifyTaskMovedToFront(topMostActivity.getTask().getTaskInfo());
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
        EventLogTags.writeWmTaskToFront(task.mUserId, i, getDisplayId());
        this.mAtmService.getTaskChangeNotificationController().notifyTaskMovedToFront(task.getTaskInfo());
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public void onChildPositionChanged(WindowContainer windowContainer) {
        super.onChildPositionChanged(windowContainer);
        this.mRootWindowContainer.invalidateTaskLayers();
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public boolean forAllTaskDisplayAreas(Predicate predicate, boolean z) {
        return z ? super.forAllTaskDisplayAreas(predicate, z) || predicate.test(this) : predicate.test(this) || super.forAllTaskDisplayAreas(predicate, z);
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public void forAllTaskDisplayAreas(Consumer consumer, boolean z) {
        if (z) {
            super.forAllTaskDisplayAreas(consumer, z);
            consumer.accept(this);
        } else {
            consumer.accept(this);
            super.forAllTaskDisplayAreas(consumer, z);
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public Object reduceOnAllTaskDisplayAreas(BiFunction biFunction, Object obj, boolean z) {
        if (z) {
            return biFunction.apply(this, super.reduceOnAllTaskDisplayAreas(biFunction, obj, z));
        }
        return super.reduceOnAllTaskDisplayAreas(biFunction, biFunction.apply(this, obj), z);
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public Object getItemFromTaskDisplayAreas(Function function, boolean z) {
        if (z) {
            Object itemFromTaskDisplayAreas = super.getItemFromTaskDisplayAreas(function, z);
            return itemFromTaskDisplayAreas != null ? itemFromTaskDisplayAreas : function.apply(this);
        }
        Object apply = function.apply(this);
        return apply != null ? apply : super.getItemFromTaskDisplayAreas(function, z);
    }

    public final int getPriority(WindowContainer windowContainer) {
        TaskDisplayArea asTaskDisplayArea = windowContainer.asTaskDisplayArea();
        if (asTaskDisplayArea != null) {
            return asTaskDisplayArea.getPriority(asTaskDisplayArea.getTopChild());
        }
        return getPriority(windowContainer.asTask());
    }

    public final int getPriority(Task task) {
        if (this.mWmService.mAssistantOnTopOfDream && task.isActivityTypeAssistant()) {
            return 5;
        }
        if (task.isActivityTypeDream()) {
            return 4;
        }
        if (task.inPinnedWindowingMode()) {
            return 3;
        }
        if (isDesktopModeEnabled() && task.isFreeformPinned() && !task.isMinimized()) {
            return 2;
        }
        return task.isAlwaysOnTop() ? 1 : 0;
    }

    public final int findMinPositionForRootTask(Task task) {
        int i;
        int indexOf;
        int i2 = Integer.MIN_VALUE;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            i = i2;
            i2 = i4;
            if (i2 >= this.mChildren.size() || getPriority((WindowContainer) this.mChildren.get(i2)) >= getPriority(task)) {
                break;
            }
            i3 = i2 + 1;
        }
        return (!task.isAlwaysOnTop() || (indexOf = this.mChildren.indexOf(task)) <= i) ? i : indexOf;
    }

    public final int findMaxPositionForRootTask(Task task) {
        int size = this.mChildren.size() - 1;
        while (true) {
            if (size < 0) {
                return 0;
            }
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            boolean z = windowContainer == task;
            if (getPriority(windowContainer) > getPriority(task) || z || (task.isDesktopModeEnabled() && task.isActivityTypeHomeOrRecents() && windowContainer.asTask() != null && !windowContainer.asTask().isMinimized() && windowContainer.inFreeformWindowingMode() && windowContainer.compareTo((WindowContainer) task) > 0)) {
                size--;
            }
        }
        return size;
    }

    public int getRootTaskIndex(Task task) {
        return this.mChildren.indexOf(task);
    }

    public final int findPositionForRootTask(int i, Task task, boolean z) {
        int findMaxPositionForRootTask = findMaxPositionForRootTask(task);
        int findMinPositionForRootTask = findMinPositionForRootTask(task);
        if (i == Integer.MAX_VALUE) {
            i = this.mChildren.size();
        } else if (i == Integer.MIN_VALUE) {
            i = 0;
        }
        int max = Math.max(Math.min(i, findMaxPositionForRootTask), findMinPositionForRootTask);
        return max != i ? (z || max < this.mChildren.indexOf(task)) ? max + 1 : max : max;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public int getOrientation(final int i) {
        int orientation = super.getOrientation(i);
        if (!canSpecifyOrientation(orientation)) {
            this.mLastOrientationSource = null;
            return ((Integer) reduceOnAllTaskDisplayAreas(new BiFunction() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda5
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    Integer lambda$getOrientation$3;
                    lambda$getOrientation$3 = TaskDisplayArea.this.lambda$getOrientation$3(i, (TaskDisplayArea) obj, (Integer) obj2);
                    return lambda$getOrientation$3;
                }
            }, -2)).intValue();
        }
        if (handleOrientationChangeFromSplitScreen()) {
            return -1;
        }
        if (orientation != -2 && orientation != 3) {
            if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1381227466, 5, (String) null, new Object[]{Long.valueOf(orientation), Long.valueOf(this.mDisplayContent.mDisplayId)});
            }
            return orientation;
        }
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1640436199, 5, (String) null, new Object[]{Long.valueOf(this.mDisplayContent.getLastOrientation()), Long.valueOf(this.mDisplayContent.mDisplayId)});
        }
        return this.mDisplayContent.getLastOrientation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Integer lambda$getOrientation$3(int i, TaskDisplayArea taskDisplayArea, Integer num) {
        return (taskDisplayArea == this || num.intValue() != -2) ? num : Integer.valueOf(taskDisplayArea.getOrientation(i));
    }

    @Override // com.android.server.wm.WindowContainer
    public void assignChildLayers(SurfaceControl.Transaction transaction) {
        assignRootTaskOrdering(transaction);
        for (int i = 0; i < this.mChildren.size(); i++) {
            ((WindowContainer) this.mChildren.get(i)).assignChildLayers(transaction);
        }
    }

    public void assignRootTaskOrdering(SurfaceControl.Transaction transaction) {
        SurfaceControl surfaceControl;
        SurfaceControl surfaceControl2;
        if (getParent() == null) {
            return;
        }
        this.mTmpAlwaysOnTopChildren.clear();
        this.mTmpHomeChildren.clear();
        this.mTmpNormalChildren.clear();
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_SEPARATE_RECENTS) {
            this.mTmpRecentsChildren.clear();
        }
        this.mTmpFreeformChildren.clear();
        this.mTmpFreeformPinnedChildren.clear();
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
                } else if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_SEPARATE_RECENTS && topRootTask.isActivityTypeRecents()) {
                    this.mTmpRecentsChildren.add(asTaskDisplayArea);
                } else {
                    this.mTmpNormalChildren.add(asTaskDisplayArea);
                }
            } else {
                Task asTask = windowContainer.asTask();
                if (asTask.mBoostRootTaskLayerForFreeform) {
                    this.mTmpFreeformChildren.add(asTask);
                } else if (asTask.isFreeformPinned() && !asTask.isMinimized()) {
                    this.mTmpFreeformPinnedChildren.add(asTask);
                } else if (asTask.isAlwaysOnTop()) {
                    if (asTask.inFreeformWindowingMode()) {
                        this.mTmpFreeformChildren.add(asTask);
                    } else {
                        this.mTmpAlwaysOnTopChildren.add(asTask);
                    }
                } else if (asTask.isActivityTypeHome()) {
                    this.mTmpHomeChildren.add(asTask);
                } else if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_SEPARATE_RECENTS && asTask.isActivityTypeRecents()) {
                    this.mTmpRecentsChildren.add(asTask);
                } else {
                    this.mTmpNormalChildren.add(asTask);
                }
            }
        }
        int adjustRootTaskLayer = adjustRootTaskLayer(transaction, this.mTmpHomeChildren, 0);
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_SEPARATE_RECENTS) {
            adjustRootTaskLayer = adjustRootTaskLayer(transaction, this.mTmpRecentsChildren, adjustRootTaskLayer);
        }
        int adjustRootTaskLayer2 = adjustRootTaskLayer(transaction, this.mTmpNormalChildren, adjustRootTaskLayer);
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION && (surfaceControl2 = this.mChangeLeashAnchor) != null) {
            transaction.setLayer(surfaceControl2, adjustRootTaskLayer2);
            adjustRootTaskLayer2++;
        }
        int adjustRootTaskLayer3 = adjustRootTaskLayer(transaction, this.mTmpFreeformPinnedChildren, adjustRootTaskLayer(transaction, this.mTmpFreeformChildren, adjustRootTaskLayer2));
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && (surfaceControl = this.mFloatingLeashAnchor) != null) {
            transaction.setLayer(surfaceControl, adjustRootTaskLayer3);
            adjustRootTaskLayer3++;
        }
        adjustRootTaskLayer(transaction, this.mTmpAlwaysOnTopChildren, adjustRootTaskLayer3);
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
                z = asTaskDisplayArea.childrenNeedZBoost();
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

    public final boolean childrenNeedZBoost() {
        final boolean[] zArr = new boolean[1];
        forAllRootTasks(new Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskDisplayArea.lambda$childrenNeedZBoost$5(zArr, (Task) obj);
            }
        });
        return zArr[0];
    }

    public static /* synthetic */ void lambda$childrenNeedZBoost$5(boolean[] zArr, Task task) {
        zArr[0] = task.needsZBoost() | zArr[0];
    }

    @Override // com.android.server.wm.WindowContainer
    public RemoteAnimationTarget createRemoteAnimationTarget(RemoteAnimationController.RemoteAnimationRecord remoteAnimationRecord) {
        ActivityRecord topMostActivity = getTopMostActivity();
        if (topMostActivity != null) {
            return topMostActivity.createRemoteAnimationTarget(remoteAnimationRecord);
        }
        return null;
    }

    public void setBackgroundColor(int i) {
        setBackgroundColor(i, false);
    }

    public void setBackgroundColor(int i, boolean z) {
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

    public void clearBackgroundColor() {
        int i = this.mColorLayerCounter - 1;
        this.mColorLayerCounter = i;
        if (i != 0 || this.mSurfaceControl == null) {
            return;
        }
        getPendingTransaction().unsetColor(this.mSurfaceControl);
        scheduleAnimation();
    }

    @Override // com.android.server.wm.WindowContainer
    public void migrateToNewSurfaceControl(SurfaceControl.Transaction transaction) {
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

    public void onRootTaskRemoved(Task task) {
        if (this.mPreferredTopFocusableRootTask == task) {
            this.mPreferredTopFocusableRootTask = null;
        }
        if (this.mLaunchAdjacentFlagRootTask == task) {
            this.mLaunchAdjacentFlagRootTask = null;
        }
        this.mDisplayContent.releaseSelfIfNeeded();
        onRootTaskOrderChanged(task);
    }

    public void positionTaskBehindHome(Task task) {
        WindowContainer parent = getOrCreateRootHomeTask().getParent();
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
            asTask.positionChildAtBottom(task);
        } else {
            task.reparent(asTask, false, 2, false, false, "positionTaskBehindHome");
        }
    }

    public Task getOrCreateRootTask(int i, int i2, boolean z) {
        return getOrCreateRootTask(i, i2, z, null, null, null, 0);
    }

    public Task getOrCreateRootTask(int i, int i2, boolean z, Task task, Task task2, ActivityOptions activityOptions, int i3) {
        Rect rect;
        int windowingMode = i == 0 ? getWindowingMode() : i;
        if (!DisplayContent.alwaysCreateRootTask(windowingMode, i2)) {
            Task rootTask = getRootTask(windowingMode, i2);
            if (rootTask != null) {
                return rootTask;
            }
        } else if (task != null) {
            int i4 = z ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            Task launchRootTask = getLaunchRootTask(windowingMode, i2, activityOptions, task2, i3, task);
            if (this.mDisplayContent.isDefaultDisplay && task.isDexMode() && task.getDisplayArea() != null && task.getDisplayArea() != this && (launchRootTask == null || !launchRootTask.inSplitScreenWindowingMode())) {
                task.reparent(this, z);
                task.setWindowingMode(0);
                task.mLastNonFullscreenBounds = null;
            }
            if (launchRootTask != null) {
                if (task.getParent() == null) {
                    launchRootTask.addChild(task, i4);
                } else if (task.getParent() != launchRootTask) {
                    task.reparent(launchRootTask, i4);
                }
            } else if (task.getDisplayArea() != this || task.getRootTask().mReparentLeafTaskIfRelaunch) {
                if (task.getParent() == null) {
                    addChild(task, i4);
                } else {
                    task.reparent(this, z);
                }
            } else if (!task.isRootTask() && i == 5 && task.getParent() != null) {
                if (task.isVisible() && task.inSplitScreenWindowingMode()) {
                    TaskOrganizerInfo taskOrganizerInfo = new TaskOrganizerInfo();
                    taskOrganizerInfo.setSplitToFreeformTaskId(task.mTaskId);
                    this.mAtmService.mTaskOrganizerController.onSplitLayoutChangeRequested(taskOrganizerInfo.toBundle());
                } else {
                    task.reparent(this, z);
                }
            }
            if ((i != 0 && task.isRootTask() && task.getWindowingMode() != i) || ForceLaunchWindowingModeUtils.shouldApplyForceLaunchWindowingMode(activityOptions, null, task)) {
                task.mTransitionController.collect(task);
                if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && i == 5 && !this.mAtmService.mTaskSupervisor.isRootVisibilityUpdateDeferred()) {
                    this.mAtmService.mTaskSupervisor.setDeferRootVisibilityUpdate(true);
                    try {
                        task.setWindowingMode(i);
                    } finally {
                        this.mAtmService.mTaskSupervisor.setDeferRootVisibilityUpdate(false);
                    }
                } else if (activityOptions != null && activityOptions.getForceLaunchWindowingMode() == 1 && task.inSplitScreenWindowingMode()) {
                    task.reparent(this, z);
                } else {
                    task.setWindowingMode(i);
                }
                if (i == 5 && task.getRootActivity() != null && ((rect = task.mLastNonFullscreenBounds) == null || rect.isEmpty())) {
                    ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
                    this.mAtmService.mTaskSupervisor.getLaunchParamsController().layoutTask(task, topNonFinishingActivity != null ? topNonFinishingActivity.info.windowLayout : null, topNonFinishingActivity, null, null);
                }
            }
            if (task.inFreeformWindowingMode() && task.getWindowingMode() != i && !task.isMinimized() && !task.isVisible()) {
                task.setWindowingMode(i);
                task.mLastNonFullscreenBounds = null;
                task.setBounds(null);
            }
            return task.getRootTask();
        }
        return new Task.Builder(this.mAtmService).setWindowingMode(i).setActivityType(i2).setOnTop(z).setParent(this).setSourceTask(task2).setActivityOptions(activityOptions).setLaunchFlags(i3).build();
    }

    public Task getOrCreateRootTask(ActivityRecord activityRecord, ActivityOptions activityOptions, Task task, Task task2, LaunchParamsController.LaunchParams launchParams, int i, int i2, boolean z) {
        int launchWindowingMode;
        if (launchParams != null) {
            launchWindowingMode = launchParams.mWindowingMode;
        } else {
            launchWindowingMode = activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
        }
        int resolveForceLaunchWindowingMode = ForceLaunchWindowingModeUtils.resolveForceLaunchWindowingMode(activityOptions, activityRecord, task);
        if (resolveForceLaunchWindowingMode != 0) {
            launchWindowingMode = resolveForceLaunchWindowingMode;
        }
        return getOrCreateRootTask(validateWindowingMode(launchWindowingMode, activityRecord, task), i2, z, task, task2, activityOptions, i);
    }

    public int getNextRootTaskId() {
        return this.mAtmService.mTaskSupervisor.getNextTaskIdForUser();
    }

    public Task createRootTask(int i, int i2, boolean z) {
        return createRootTask(i, i2, z, null);
    }

    public Task createRootTask(int i, int i2, boolean z, ActivityOptions activityOptions) {
        return new Task.Builder(this.mAtmService).setWindowingMode(i).setActivityType(i2).setParent(this).setOnTop(z).setActivityOptions(activityOptions).build();
    }

    public void setLaunchRootTask(Task task, int[] iArr, int[] iArr2) {
        if (!task.mCreatedByOrganizer) {
            throw new IllegalArgumentException("Can't set not mCreatedByOrganizer as launch root tr=" + task);
        }
        LaunchRootTaskDef launchRootTaskDef = getLaunchRootTaskDef(task);
        if (launchRootTaskDef != null) {
            this.mLaunchRootTasks.remove(launchRootTaskDef);
        } else {
            launchRootTaskDef = new LaunchRootTaskDef();
            launchRootTaskDef.task = task;
        }
        launchRootTaskDef.activityTypes = iArr2;
        launchRootTaskDef.windowingModes = iArr;
        if (ArrayUtils.isEmpty(iArr) && ArrayUtils.isEmpty(iArr2)) {
            return;
        }
        this.mLaunchRootTasks.add(launchRootTaskDef);
    }

    public void removeLaunchRootTask(Task task) {
        LaunchRootTaskDef launchRootTaskDef = getLaunchRootTaskDef(task);
        if (launchRootTaskDef != null) {
            this.mLaunchRootTasks.remove(launchRootTaskDef);
        }
    }

    public void setLaunchAdjacentFlagRootTask(Task task) {
        if (task != null) {
            if (!task.mCreatedByOrganizer) {
                throw new IllegalArgumentException("Can't set not mCreatedByOrganizer as launch adjacent flag root tr=" + task);
            }
            if (task.getAdjacentTaskFragment() == null) {
                throw new UnsupportedOperationException("Can't set non-adjacent root as launch adjacent flag root tr=" + task);
            }
        }
        this.mLaunchAdjacentFlagRootTask = task;
    }

    public final LaunchRootTaskDef getLaunchRootTaskDef(Task task) {
        for (int size = this.mLaunchRootTasks.size() - 1; size >= 0; size--) {
            if (((LaunchRootTaskDef) this.mLaunchRootTasks.get(size)).task.mTaskId == task.mTaskId) {
                return (LaunchRootTaskDef) this.mLaunchRootTasks.get(size);
            }
        }
        return null;
    }

    public Task getLaunchRootTask(int i, int i2, ActivityOptions activityOptions, Task task, int i3) {
        return getLaunchRootTask(i, i2, activityOptions, task, i3, null);
    }

    public Task getLaunchRootTask(int i, int i2, ActivityOptions activityOptions, Task task, int i3, Task task2) {
        Task task3;
        Task fromWindowContainerToken;
        if (activityOptions != null && (fromWindowContainerToken = Task.fromWindowContainerToken(activityOptions.getLaunchRootTask())) != null && fromWindowContainerToken.mCreatedByOrganizer) {
            return fromWindowContainerToken;
        }
        if ((i3 & IInstalld.FLAG_USE_QUOTA) != 0 && this.mLaunchAdjacentFlagRootTask != null) {
            if ((task != null && task == task2 && task.inFullscreenWindowingMode() && getTopMostTask() == task && this.mLaunchAdjacentFlagRootTask.inSplitScreenWindowingMode()) || ((task != null && (task.inFreeformWindowingMode() || task.inPinnedWindowingMode())) || isDexMode())) {
                Slog.d(StartingSurfaceController.TAG, "getLaunchRootTask: Skip adjacent flag, " + task);
            } else if (task == null || task != task2) {
                if (task != null && this.mLaunchAdjacentFlagRootTask.getAdjacentTaskFragment() != null && (task == (task3 = this.mLaunchAdjacentFlagRootTask) || task.isDescendantOf(task3))) {
                    if (task.topRunningActivity() != null && ActivityRecord.isResolverActivity(task.topRunningActivity().mActivityComponent.getClassName())) {
                        return task.getParent().asTask();
                    }
                    return this.mLaunchAdjacentFlagRootTask.getAdjacentTaskFragment().asTask();
                }
                if (task != null && task.inFullscreenWindowingMode() && !task.isResizeable() && this.mLaunchAdjacentFlagRootTask.inSplitScreenWindowingMode()) {
                    Slog.d(StartingSurfaceController.TAG, "getLaunchRootTask: Skip adjacent flag, " + task);
                } else {
                    return this.mLaunchAdjacentFlagRootTask;
                }
            }
        }
        int size = this.mLaunchRootTasks.size();
        do {
            size--;
            if (size < 0) {
                if (task != null && (task2 == null || task2.getWindowingMode() != 2)) {
                    Task createdByOrganizerTask = task.getCreatedByOrganizerTask();
                    if (createdByOrganizerTask != null && createdByOrganizerTask.inSplitScreenWindowingMode()) {
                        if (task2 != null && task2.isFocusableAndVisible()) {
                            if (task2.isRootTask()) {
                                return null;
                            }
                            Task createdByOrganizerTask2 = task2.getCreatedByOrganizerTask();
                            if (createdByOrganizerTask2 != null) {
                                return createdByOrganizerTask2;
                            }
                        } else if (createdByOrganizerTask.getTopNonFinishingActivity() == null) {
                            return null;
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
        } while (!((LaunchRootTaskDef) this.mLaunchRootTasks.get(size)).contains(i, i2));
        Task task4 = ((LaunchRootTaskDef) this.mLaunchRootTasks.get(size)).task;
        Task adjacentTask2 = task4 != null ? task4.getAdjacentTask() : null;
        return (task == null || adjacentTask2 == null || !(task == adjacentTask2 || task.isDescendantOf(adjacentTask2))) ? task4 : adjacentTask2;
    }

    public Task getFocusedRootTask() {
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

    public Task getNextFocusableRootTask(Task task, boolean z) {
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

    public ActivityRecord getFocusedActivity() {
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

    public Task getLastFocusedRootTask() {
        return this.mLastFocusedRootTask;
    }

    public void updateLastFocusedRootTask(Task task, String str) {
        Task focusedRootTask;
        if (str == null || (focusedRootTask = getFocusedRootTask()) == task) {
            return;
        }
        if (this.mDisplayContent.isSleeping() && focusedRootTask != null) {
            focusedRootTask.clearLastPausedActivity();
        }
        this.mLastFocusedRootTask = task;
        int i = this.mRootWindowContainer.mCurrentUser;
        int i2 = this.mDisplayContent.mDisplayId;
        int rootTaskId = focusedRootTask == null ? -1 : focusedRootTask.getRootTaskId();
        Task task2 = this.mLastFocusedRootTask;
        EventLogTags.writeWmFocusedRootTask(i, i2, rootTaskId, task2 != null ? task2.getRootTaskId() : -1, str);
    }

    public boolean allResumedActivitiesComplete() {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(size);
            if (windowContainer.asTaskDisplayArea() != null) {
                if (!windowContainer.asTaskDisplayArea().allResumedActivitiesComplete()) {
                    return false;
                }
            } else {
                ActivityRecord topResumedActivity = ((WindowContainer) this.mChildren.get(size)).asTask().getTopResumedActivity();
                if (topResumedActivity != null && !topResumedActivity.isState(ActivityRecord.State.RESUMED)) {
                    return false;
                }
            }
        }
        this.mLastFocusedRootTask = getFocusedRootTask();
        return true;
    }

    public boolean pauseBackTasks(final ActivityRecord activityRecord) {
        final int[] iArr = {0};
        forAllLeafTasks(new Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskDisplayArea.lambda$pauseBackTasks$6(ActivityRecord.this, iArr, (Task) obj);
            }
        }, true);
        return iArr[0] > 0;
    }

    public static /* synthetic */ void lambda$pauseBackTasks$6(ActivityRecord activityRecord, int[] iArr, Task task) {
        if (task.pauseActivityIfNeeded(activityRecord, "pauseBackTasks")) {
            iArr[0] = iArr[0] + 1;
        }
    }

    public void onStageSplitScreenDismissed(Task task) {
        onStageSplitScreenDismissed(task, true);
    }

    public void onStageSplitScreenDismissed(final Task task, boolean z) {
        Task topRootTaskInWindowingMode;
        Task topRootTaskInWindowingMode2;
        this.mAtmService.deferWindowLayout();
        final Task[] taskArr = new Task[1];
        try {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            this.mTmpTasks.clear();
            forAllTasks(new Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskDisplayArea.this.lambda$onStageSplitScreenDismissed$7(taskArr, task, (Task) obj);
                }
            });
            for (int size = this.mTmpTasks.size() - 1; size >= 0; size--) {
                Task task2 = (Task) this.mTmpTasks.get(size);
                for (int i = 0; i < task2.getChildCount(); i++) {
                    WindowContainer childAt = task2.getChildAt(i);
                    childAt.forAllActivities(new Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda7
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((ActivityRecord) obj).mLaunchRootTask = null;
                        }
                    });
                    WindowContainerToken windowContainerToken = childAt.mRemoteToken.toWindowContainerToken();
                    windowContainerTransaction.reparent(windowContainerToken, (WindowContainerToken) null, z);
                    windowContainerTransaction.setBounds(windowContainerToken, (Rect) null);
                }
            }
            this.mAtmService.mWindowOrganizerController.applyTransaction(windowContainerTransaction);
            if (task != null) {
                topRootTaskInWindowingMode2 = task;
            } else {
                try {
                    topRootTaskInWindowingMode2 = getTopRootTaskInWindowingMode(1);
                } finally {
                }
            }
            Task orCreateRootHomeTask = getOrCreateRootHomeTask();
            if (orCreateRootHomeTask != null && (((topRootTaskInWindowingMode2 != null && !isTopRootTask(orCreateRootHomeTask)) || task != null) && taskArr[0] == null)) {
                orCreateRootHomeTask.moveToFront("onStageSplitScreenDismissed");
                topRootTaskInWindowingMode2.moveToFront("onStageSplitScreenDismissed");
            }
        } catch (Throwable th) {
            if (task != null) {
                topRootTaskInWindowingMode = task;
            } else {
                try {
                    topRootTaskInWindowingMode = getTopRootTaskInWindowingMode(1);
                } finally {
                }
            }
            Task orCreateRootHomeTask2 = getOrCreateRootHomeTask();
            if (orCreateRootHomeTask2 != null && (((topRootTaskInWindowingMode != null && !isTopRootTask(orCreateRootHomeTask2)) || task != null) && taskArr[0] == null)) {
                orCreateRootHomeTask2.moveToFront("onStageSplitScreenDismissed");
                topRootTaskInWindowingMode.moveToFront("onStageSplitScreenDismissed");
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStageSplitScreenDismissed$7(Task[] taskArr, Task task, Task task2) {
        if (task2.mCreatedByOrganizer && task2.inSplitScreenWindowingMode() && task2.hasChild()) {
            if (task != null && task2.mChildren.contains(task)) {
                this.mTmpTasks.add(0, task2);
            } else {
                this.mTmpTasks.add(task2);
            }
        }
    }

    public int resolveWindowingMode(ActivityRecord activityRecord, ActivityOptions activityOptions, Task task) {
        int launchWindowingMode = activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
        if (launchWindowingMode == 0) {
            if (task != null) {
                launchWindowingMode = task.getWindowingMode();
            }
            if (launchWindowingMode == 0 && activityRecord != null) {
                launchWindowingMode = activityRecord.getWindowingMode();
            }
            if (launchWindowingMode == 0) {
                launchWindowingMode = getWindowingMode();
            }
        }
        int validateWindowingMode = validateWindowingMode(launchWindowingMode, activityRecord, task);
        if (validateWindowingMode != 0) {
            return validateWindowingMode;
        }
        return 1;
    }

    public boolean isValidWindowingMode(int i, ActivityRecord activityRecord, Task task) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        boolean z = activityTaskManagerService.mSupportsMultiWindow;
        boolean z2 = activityTaskManagerService.mSupportsFreeformWindowManagement;
        boolean z3 = activityTaskManagerService.mSupportsPictureInPicture;
        if (z) {
            if (task != null) {
                z2 = task.supportsFreeformInDisplayArea(this);
                z = task.supportsMultiWindowInDisplayArea(this) || (i == 2 && z3);
            } else if (activityRecord != null) {
                z2 = activityRecord.supportsFreeformInDisplayArea(this);
                z3 = activityRecord.supportsPictureInPicture();
                z = activityRecord.supportsMultiWindowInDisplayArea(this);
            }
            if (CoreRune.MT_SUPPORT_SIZE_COMPAT && i == 5 && !z2) {
                SizeCompatPolicyManager sizeCompatPolicyManager = SizeCompatPolicyManager.get();
                if (task == null) {
                    task = activityRecord.getTask();
                }
                SizeCompatPolicy compatPolicy = sizeCompatPolicyManager.getCompatPolicy(task);
                if (compatPolicy != null && compatPolicy.supportsFreeform()) {
                    z = true;
                    z2 = true;
                }
            }
        }
        return i != 0 && isWindowingModeSupported(i, z, z2, z3);
    }

    public int validateWindowingMode(int i, ActivityRecord activityRecord, Task task) {
        if (isValidWindowingMode(i, activityRecord, task)) {
            return i;
        }
        return 0;
    }

    public boolean supportsNonResizableMultiWindow() {
        ActivityTaskManagerService activityTaskManagerService = this.mAtmService;
        int i = activityTaskManagerService.mSupportsNonResizableMultiWindow;
        if (activityTaskManagerService.mDevEnableNonResizableMultiWindow || i == 1) {
            return true;
        }
        if (i == -1) {
            return false;
        }
        return isLargeEnoughForMultiWindow();
    }

    public final boolean isLargeEnoughForMultiWindow() {
        return getConfiguration().smallestScreenWidthDp >= 600;
    }

    public boolean isTopRootTask(Task task) {
        return task == getTopRootTask();
    }

    public ActivityRecord topRunningActivity() {
        return topRunningActivity(false);
    }

    public ActivityRecord topRunningActivity(boolean z) {
        Task focusedRootTask = getFocusedRootTask();
        ActivityRecord activityRecord = focusedRootTask != null ? focusedRootTask.topRunningActivity() : null;
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
                    if (asTask != focusedRootTask && asTask.isTopActivityFocusable() && (activityRecord = asTask.topRunningActivity()) != null) {
                        break;
                    }
                }
            }
        }
        if (activityRecord == null || !z || !this.mRootWindowContainer.mTaskSupervisor.getKeyguardController().isKeyguardLocked(activityRecord.getDisplayId()) || activityRecord.canShowWhenLocked()) {
            return activityRecord;
        }
        return null;
    }

    public int getRootTaskCount() {
        final int[] iArr = new int[1];
        forAllRootTasks(new Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskDisplayArea.lambda$getRootTaskCount$9(iArr, (Task) obj);
            }
        });
        return iArr[0];
    }

    public static /* synthetic */ void lambda$getRootTaskCount$9(int[] iArr, Task task) {
        iArr[0] = iArr[0] + 1;
    }

    public Task getOrCreateRootHomeTask() {
        return getOrCreateRootHomeTask(false);
    }

    public Task getOrCreateRootHomeTask(boolean z) {
        Task rootHomeTask = getRootHomeTask();
        return (rootHomeTask == null && canHostHomeTask()) ? createRootTask(0, 2, z) : rootHomeTask;
    }

    public boolean isSplitScreenModeActivated() {
        Task rootMainStageTask = getRootMainStageTask();
        if (rootMainStageTask == null || !rootMainStageTask.hasChild()) {
            return false;
        }
        if (rootMainStageTask.isVisible() || rootMainStageTask.isVisibleRequested()) {
            return true;
        }
        Task topRootTaskInWindowingMode = getTopRootTaskInWindowingMode(1);
        return topRootTaskInWindowingMode != null && rootMainStageTask.getRootTask() == topRootTaskInWindowingMode;
    }

    public boolean hasChildTaskInSideStage() {
        Task rootSideStageTask = getRootSideStageTask();
        return rootSideStageTask != null && rootSideStageTask.hasChild();
    }

    public Task getTopRootStageTask() {
        Task task = getRootMainStageTask() != null ? (Task) getRootMainStageTask().getParent() : null;
        if (task == null || task.mChildren.isEmpty()) {
            return null;
        }
        return (Task) task.mChildren.get(r2.size() - 1);
    }

    public Task getTopRootTaskInStageType(int i) {
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

    public Task getRootMainStageTask() {
        return this.mRootMainStageTask;
    }

    public Task getRootSideStageTask() {
        return this.mRootSideStageTask;
    }

    public void forAllRootStageTasks(Consumer consumer) {
        Task rootTask = getRootTask(new Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isFullscreenRootForStageTask;
                isFullscreenRootForStageTask = ((Task) obj).isFullscreenRootForStageTask();
                return isFullscreenRootForStageTask;
            }
        });
        if (rootTask == null) {
            return;
        }
        for (int childCount = rootTask.getChildCount() - 1; childCount >= 0; childCount--) {
            WindowContainer childAt = rootTask.getChildAt(childCount);
            if (childAt.asTask() != null && childAt.inSplitScreenWindowingMode()) {
                consumer.accept(childAt.asTask());
            }
        }
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

    public boolean isMultiSplitActive() {
        Task task = this.mRootCellStageTask;
        return task != null && task.hasChild();
    }

    public boolean isMultiSplitVisible() {
        return isMultiSplitActive() && (this.mRootCellStageTask.isVisible() || this.mRootCellStageTask.isVisibleRequested());
    }

    public Task getRootCellStageTask() {
        return this.mRootCellStageTask;
    }

    public boolean isSplitScreenVisible() {
        Task rootMainStageTask = getRootMainStageTask();
        Task rootSideStageTask = getRootSideStageTask();
        return (rootMainStageTask != null && (rootMainStageTask.isVisible() || rootMainStageTask.isVisibleRequested())) || (rootSideStageTask != null && (rootSideStageTask.isVisible() || rootSideStageTask.isVisibleRequested()));
    }

    public boolean isSplitScreenStarting() {
        Task rootMainStageTask = getRootMainStageTask();
        Task rootSideStageTask = getRootSideStageTask();
        return ((rootMainStageTask == null || !rootMainStageTask.isVisibleRequested() || rootMainStageTask.isVisible()) && (rootSideStageTask == null || !rootSideStageTask.isVisibleRequested() || rootSideStageTask.isVisible())) ? false : true;
    }

    public Task getTopRootTaskInWindowingMode(int i) {
        return getRootTask(i, 0);
    }

    public void moveHomeRootTaskToFront(String str) {
        Task orCreateRootHomeTask = getOrCreateRootHomeTask();
        if (orCreateRootHomeTask != null) {
            orCreateRootHomeTask.moveToFront(str);
        }
    }

    public void moveHomeActivityToTop(String str) {
        ActivityRecord homeActivity = getHomeActivity();
        if (homeActivity == null) {
            moveHomeRootTaskToFront(str);
        } else {
            homeActivity.moveFocusableActivityToTop(str);
        }
    }

    public ActivityRecord getHomeActivity() {
        return getHomeActivityForUser(this.mRootWindowContainer.mCurrentUser);
    }

    public ActivityRecord getHomeActivityForUser(int i) {
        Task rootHomeTask = getRootHomeTask();
        if (rootHomeTask == null) {
            return null;
        }
        PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new BiPredicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda13
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                boolean isHomeActivityForUser;
                isHomeActivityForUser = TaskDisplayArea.isHomeActivityForUser((ActivityRecord) obj, ((Integer) obj2).intValue());
                return isHomeActivityForUser;
            }
        }, PooledLambda.__(ActivityRecord.class), Integer.valueOf(i));
        ActivityRecord activity = rootHomeTask.getActivity(obtainPredicate);
        obtainPredicate.recycle();
        return activity;
    }

    public static boolean isHomeActivityForUser(ActivityRecord activityRecord, int i) {
        return activityRecord.isActivityTypeHome() && (i == -1 || activityRecord.mUserId == i);
    }

    public void moveRootTaskBehindBottomMostVisibleRootTask(Task task) {
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
                    asTask = windowContainer.asTaskDisplayArea().getBottomMostVisibleRootTask(task);
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

    public final Task getBottomMostVisibleRootTask(Task task) {
        return getRootTask(new Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getBottomMostVisibleRootTask$11;
                lambda$getBottomMostVisibleRootTask$11 = TaskDisplayArea.lambda$getBottomMostVisibleRootTask$11((Task) obj);
                return lambda$getBottomMostVisibleRootTask$11;
            }
        }, false);
    }

    public static /* synthetic */ boolean lambda$getBottomMostVisibleRootTask$11(Task task) {
        return task.shouldBeVisible(null) && (task.getWindowingMode() == 1);
    }

    public void moveRootTaskBehindRootTask(Task task, Task task2) {
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

    public boolean hasPinnedTask() {
        return getRootPinnedTask() != null;
    }

    public static Task getRootTaskAbove(Task task) {
        WindowContainer parent = task.getParent();
        int indexOf = parent.mChildren.indexOf(task) + 1;
        if (indexOf < parent.mChildren.size()) {
            return (Task) parent.mChildren.get(indexOf);
        }
        return null;
    }

    public boolean isRootTaskVisible(int i) {
        Task topRootTaskInWindowingMode = getTopRootTaskInWindowingMode(i);
        return topRootTaskInWindowingMode != null && topRootTaskInWindowingMode.isVisible();
    }

    public int getDisplayId() {
        return this.mDisplayContent.getDisplayId();
    }

    public boolean isRemoved() {
        return this.mRemoved;
    }

    public void registerRootTaskOrderChangedListener(OnRootTaskOrderChangedListener onRootTaskOrderChangedListener) {
        if (this.mRootTaskOrderChangedCallbacks.contains(onRootTaskOrderChangedListener)) {
            return;
        }
        this.mRootTaskOrderChangedCallbacks.add(onRootTaskOrderChangedListener);
    }

    public void unregisterRootTaskOrderChangedListener(OnRootTaskOrderChangedListener onRootTaskOrderChangedListener) {
        this.mRootTaskOrderChangedCallbacks.remove(onRootTaskOrderChangedListener);
    }

    public void onRootTaskOrderChanged(Task task) {
        for (int size = this.mRootTaskOrderChangedCallbacks.size() - 1; size >= 0; size--) {
            ((OnRootTaskOrderChangedListener) this.mRootTaskOrderChangedCallbacks.get(size)).onRootTaskOrderChanged(task);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public boolean canCreateRemoteAnimationTarget() {
        return WindowManagerService.sEnableShellTransitions;
    }

    public boolean canHostHomeTask() {
        return this.mDisplayContent.supportsSystemDecorations() && this.mCanHostHomeTask;
    }

    public void ensureActivitiesVisible(final ActivityRecord activityRecord, final int i, final boolean z, final boolean z2) {
        this.mAtmService.mTaskSupervisor.beginActivityVisibilityUpdate(getDisplayContent());
        try {
            forAllRootTasks(new Consumer() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Task) obj).ensureActivitiesVisible(ActivityRecord.this, i, z, z2);
                }
            });
        } finally {
            this.mAtmService.mTaskSupervisor.endActivityVisibilityUpdate();
        }
    }

    public Task remove() {
        Task task = null;
        this.mPreferredTopFocusableRootTask = null;
        boolean shouldDestroyContentOnRemove = this.mDisplayContent.shouldDestroyContentOnRemove();
        TaskDisplayArea defaultTaskDisplayArea = this.mRootWindowContainer.getDefaultTaskDisplayArea();
        int size = this.mChildren.size();
        int i = 0;
        while (i < size) {
            WindowContainer windowContainer = (WindowContainer) this.mChildren.get(i);
            if (windowContainer.asTaskDisplayArea() != null) {
                task = windowContainer.asTaskDisplayArea().remove();
            } else {
                Task asTask = ((WindowContainer) this.mChildren.get(i)).asTask();
                if (shouldDestroyContentOnRemove || !asTask.isActivityTypeStandardOrUndefined() || asTask.mCreatedByOrganizer) {
                    asTask.remove(false, "removeTaskDisplayArea");
                } else {
                    Task launchRootTask = defaultTaskDisplayArea.getLaunchRootTask(asTask.getWindowingMode(), asTask.getActivityType(), null, null, 0);
                    if (launchRootTask == null) {
                        launchRootTask = defaultTaskDisplayArea;
                    }
                    asTask.reparent(launchRootTask, Integer.MAX_VALUE);
                    asTask.setWindowingMode(0);
                    task = asTask;
                }
                i -= size - this.mChildren.size();
                size = this.mChildren.size();
            }
            i++;
        }
        if (task != null && !task.isRootTask()) {
            task.getRootTask().moveToFront("display-removed");
        }
        this.mRemoved = true;
        return task;
    }

    public boolean canSpecifyOrientation(int i) {
        return this.mDisplayContent.getOrientationRequestingTaskDisplayArea() == this && !shouldIgnoreOrientationRequest(i);
    }

    public void clearPreferredTopFocusableRootTask() {
        this.mPreferredTopFocusableRootTask = null;
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public void setWindowingMode(int i) {
        this.mTempConfiguration.setTo(getRequestedOverrideConfiguration());
        WindowConfiguration windowConfiguration = this.mTempConfiguration.windowConfiguration;
        windowConfiguration.setWindowingMode(i);
        windowConfiguration.setDisplayWindowingMode(i);
        onRequestedOverrideConfigurationChanged(this.mTempConfiguration);
    }

    public boolean isUnderHomeTask(Task task) {
        return this.mChildren.indexOf(this.mRootHomeTask) > this.mChildren.indexOf(task);
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onParentChanged(ConfigurationContainer configurationContainer, ConfigurationContainer configurationContainer2) {
        if (getParent() != null) {
            super.onParentChanged(configurationContainer, configurationContainer2, new Runnable() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    TaskDisplayArea.this.lambda$onParentChanged$13();
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onParentChanged$13() {
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            this.mChangeLeashAnchor = makeChildSurface(null).setName("ChangeLeashAnchor").setCallsite("TaskDisplayArea.onParentChanged").build();
            getPendingTransaction().show(this.mChangeLeashAnchor);
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
            this.mFloatingLeashAnchor = makeChildSurface(null).setName("FloatingLeashAnchor").setCallsite("TaskDisplayArea.onParentChanged").build();
            getPendingTransaction().show(this.mFloatingLeashAnchor);
        }
    }

    public int taskIdFromPoint(int i, int i2, DisplayContent.TaskIdFromPointSearchResult taskIdFromPointSearchResult) {
        getBounds(((WindowContainer) this).mTmpRect);
        if (!((WindowContainer) this).mTmpRect.contains(i, i2)) {
            return -1;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            Task asTask = ((WindowContainer) this.mChildren.get(size)).asTask();
            if (asTask != null && asTask.getTopVisibleAppMainWindow() != null) {
                asTask.getDimBounds(((WindowContainer) this).mTmpRect);
                if (((WindowContainer) this).mTmpRect.contains(i, i2)) {
                    if (taskIdFromPointSearchResult != null) {
                        taskIdFromPointSearchResult.mTask = asTask;
                    }
                    return asTask.mTaskId;
                }
            }
        }
        return -1;
    }

    public void startFreeformTaskPinning(Task task) {
        this.mFreeformTaskPinningController.startPinning(task);
    }

    public void stopFreeformTaskPinning(Task task, boolean z, String str) {
        this.mFreeformTaskPinningController.stopPinning(task, z, str);
    }

    public boolean hasPinnedFreeformTask() {
        return this.mFreeformTaskPinningController.hasTaskPinned();
    }

    public final boolean handleOrientationChangeFromSplitScreen() {
        if (!isSplitScreenVisible() && !isSplitScreenStarting()) {
            return false;
        }
        final Task task = getRootMainStageTask() != null ? (Task) getRootMainStageTask().getParent() : null;
        return getRootTask(new Predicate() { // from class: com.android.server.wm.TaskDisplayArea$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$handleOrientationChangeFromSplitScreen$14;
                lambda$handleOrientationChangeFromSplitScreen$14 = TaskDisplayArea.lambda$handleOrientationChangeFromSplitScreen$14(Task.this, (Task) obj);
                return lambda$handleOrientationChangeFromSplitScreen$14;
            }
        }) == task;
    }

    public static /* synthetic */ boolean lambda$handleOrientationChangeFromSplitScreen$14(Task task, Task task2) {
        if (task2 == task) {
            return true;
        }
        return task2.inFullscreenWindowingMode() && task2.isVisibleRequested();
    }

    public boolean needToEnsureLaunchSplitRootTask(ActivityRecord activityRecord, ActivityOptions activityOptions, Task task, Task task2) {
        Task topMostTask = getTopMostTask();
        if (!isSplitScreenModeActivated() || ((topMostTask != null && topMostTask.inFullscreenWindowingMode()) || !(activityOptions == null || (activityOptions.getLaunchRootTask() == null && activityOptions.getLaunchTaskFragmentToken() == null && ((activityOptions.getRemoteTransition() != null || !activityOptions.freezeRecentTasksReordering() || this.mWmService.getRecentsAnimationController() == null) && !ForceLaunchWindowingModeUtils.shouldDismissSplitBeforeLaunch(activityOptions, activityRecord)))))) {
            return false;
        }
        return (task == null || !(task.inSplitScreenWindowingMode() || task.inPinnedWindowingMode())) && activityRecord != null && !activityRecord.isActivityTypeHome() && !activityRecord.isActivityTypeRecents() && activityRecord.supportsSplitScreenWindowingMode() && activityRecord.supportsMultiWindowInDisplayArea(this) && (task2 == null || !task2.isActivityTypeHome());
    }

    public Task ensureLaunchSplitRootTask(ActivityRecord activityRecord, ActivityOptions activityOptions, Task task, Task task2, LaunchParamsController.LaunchParams launchParams, int i, int i2, boolean z) {
        int i3;
        ActivityRecord activityRecord2;
        int stageType;
        Task topRootTaskInStageType;
        if (activityOptions != null) {
            i3 = activityOptions.getLaunchWindowingMode();
            if (i3 == 0) {
                i3 = activityOptions.getForceLaunchWindowingMode();
            }
        } else {
            i3 = 0;
        }
        if ((task == null || (task.getResumedActivity() == null && !task.isMinimized())) && validateWindowingMode(i3, activityRecord, task) == 0) {
            if (task != null && (stageType = task.getWindowConfiguration().getStageType()) != 0 && (topRootTaskInStageType = getTopRootTaskInStageType(stageType)) != null && task.equals(topRootTaskInStageType.getTopMostTask())) {
                return topRootTaskInStageType;
            }
            Task topRootStageTask = getTopRootStageTask();
            if (topRootStageTask != null) {
                if (activityRecord != null && activityRecord.isLaunchAdjacent() && (activityRecord2 = this.mAtmService.mLastResumedActivity) != null) {
                    int stageType2 = activityRecord2.getStageType();
                    ActivityRecord activityRecord3 = this.mAtmService.mLastResumedActivity;
                    ComponentName componentName = activityRecord3.mActivityComponent;
                    if (activityRecord3.finishing && componentName != null && componentName.equals(activityRecord.mActivityComponent) && stageType2 == topRootStageTask.getStageType() && topRootStageTask.getAdjacentTaskFragment() != null) {
                        return topRootStageTask.getAdjacentTaskFragment().asTask();
                    }
                }
                return topRootStageTask;
            }
        }
        return getOrCreateRootTask(activityRecord, activityOptions, task, task2, launchParams, i, i2, z);
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public void dump(PrintWriter printWriter, String str, boolean z) {
        printWriter.println(str + "TaskDisplayArea " + getName());
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("  ");
        String sb2 = sb.toString();
        super.dump(printWriter, sb2, z);
        if (this.mPreferredTopFocusableRootTask != null) {
            printWriter.println(sb2 + "mPreferredTopFocusableRootTask=" + this.mPreferredTopFocusableRootTask);
        }
        if (this.mLastFocusedRootTask != null) {
            printWriter.println(sb2 + "mLastFocusedRootTask=" + this.mLastFocusedRootTask);
        }
        String str2 = sb2 + "  ";
        if (this.mLaunchRootTasks.size() > 0) {
            printWriter.println(sb2 + "mLaunchRootTasks:");
            for (int size = this.mLaunchRootTasks.size() + (-1); size >= 0; size += -1) {
                LaunchRootTaskDef launchRootTaskDef = (LaunchRootTaskDef) this.mLaunchRootTasks.get(size);
                printWriter.println(str2 + Arrays.toString(launchRootTaskDef.activityTypes) + " " + Arrays.toString(launchRootTaskDef.windowingModes) + "  task=" + launchRootTaskDef.task);
            }
        }
        printWriter.println(sb2 + "Application tokens in top down Z order:");
        for (int childCount = getChildCount() + (-1); childCount >= 0; childCount--) {
            WindowContainer childAt = getChildAt(childCount);
            if (childAt.asTaskDisplayArea() != null) {
                childAt.dump(printWriter, sb2, z);
            } else {
                Task asTask = childAt.asTask();
                printWriter.println(sb2 + "* " + asTask.toFullString());
                asTask.dump(printWriter, str2, z);
            }
        }
        this.mFreeformTaskPinningController.dump(printWriter, str);
    }
}
