package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.WindowConfiguration;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Debug;
import android.util.DisplayMetrics;
import android.util.Size;
import android.util.Slog;
import android.view.InsetsState;
import android.view.WindowInsets;
import android.window.WindowContainerToken;

import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.cover.CoverState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class MultiTaskingTaskLaunchParamsModifier
        implements LaunchParamsController.LaunchParamsModifier {
    public int mDefaultFreeformStepHorizontal;
    public int mDefaultFreeformStepVertical;
    public StringBuilder mLogBuilder;
    public final ActivityTaskSupervisor mSupervisor;
    public TaskDisplayArea mTmpDisplayArea;
    public final Rect mTmpBounds = new Rect();
    public final Rect mTmpStableBounds = new Rect();
    public final int[] mTmpDirections = new int[2];
    public final Rect mTmpBounds2 = new Rect();
    public ActivityInfo.WindowLayout mTmpLayout = null;
    public final Rect mSnappingBounds = new Rect();

    public MultiTaskingTaskLaunchParamsModifier(ActivityTaskSupervisor activityTaskSupervisor) {
        this.mSupervisor = activityTaskSupervisor;
    }

    public static boolean boundsConflict(List list, Rect rect) {
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                return false;
            }
            Rect rect2 = (Rect) it.next();
            boolean z = Math.abs(rect2.left - rect.left) < 4;
            boolean z2 = Math.abs(rect2.top - rect.top) < 4;
            boolean z3 = Math.abs(rect2.right - rect.right) < 4;
            boolean z4 = Math.abs(rect2.bottom - rect.bottom) < 4;
            if ((!z || !z2) && ((!z || !z4) && ((!z3 || !z2) && (!z3 || !z4)))) {}
        }
        return true;
    }

    public static boolean getPersistentDexBounds(
            int i, int i2, LaunchParamsController.LaunchParams launchParams, Rect rect) {
        if (i == 0) {
            return false;
        }
        DexPersistBoundsParams dexPersistBoundsParams = launchParams.mDexPersistBoundsParam;
        if (dexPersistBoundsParams.mDexWindowingMode == 1) {
            rect.setEmpty();
            return true;
        }
        Rect rect2 =
                i == 2
                        ? i2 == 2 ? dexPersistBoundsParams.mDexDualBounds : launchParams.mBounds
                        : dexPersistBoundsParams.mDexStandAloneBounds;
        if (rect2.isEmpty()) {
            return false;
        }
        rect.set(rect2);
        return true;
    }

    public static int orientationFromBounds(Rect rect) {
        return rect.width() > rect.height() ? 0 : 1;
    }

    public static boolean sizeMatches(Rect rect, Rect rect2) {
        return Math.abs(rect2.width() - rect.width()) < 2
                && Math.abs(rect2.height() - rect.height()) < 2;
    }

    public void adjustBoundsToAvoidConflict(Rect rect, List list, Rect rect2, boolean z) {
        int[] iArr;
        if (rect.contains(rect2) && boundsConflict(list, rect2)) {
            int i = 0;
            int i2 = 0;
            while (true) {
                iArr = this.mTmpDirections;
                if (i2 >= iArr.length) {
                    break;
                }
                iArr[i2] = 0;
                i2++;
            }
            if (z) {
                iArr[0] = 85;
            } else {
                int i3 = rect.left;
                int i4 = rect.right;
                int i5 = ((i3 * 2) + i4) / 3;
                int i6 = ((i4 * 2) + i3) / 3;
                int centerX = rect2.centerX();
                if (centerX < i5) {
                    iArr[0] = 5;
                } else if (centerX > i6) {
                    iArr[0] = 3;
                } else {
                    int i7 = rect.top;
                    int i8 = rect.bottom;
                    int i9 = ((i7 * 2) + i8) / 3;
                    int i10 = ((i8 * 2) + i7) / 3;
                    int centerY = rect2.centerY();
                    if (centerY < i9 || centerY > i10) {
                        iArr[0] = 5;
                        iArr[1] = 3;
                    } else {
                        iArr[0] = 85;
                        iArr[1] = 51;
                    }
                }
            }
            int length = iArr.length;
            int i11 = 0;
            while (i11 < length) {
                int i12 = iArr[i11];
                if (i12 == 0) {
                    return;
                }
                this.mTmpBounds.set(rect2);
                int i13 = i;
                while (true) {
                    if (!boundsConflict(list, this.mTmpBounds)
                            || (!rect.contains(this.mTmpBounds) && !z)) {
                        break;
                    }
                    int i14 = i12 & 7;
                    int i15 = i12 & 112;
                    this.mTmpBounds.offset(
                            i14 != 3
                                    ? i14 != 5 ? i : this.mDefaultFreeformStepHorizontal
                                    : -this.mDefaultFreeformStepHorizontal,
                            i15 != 48
                                    ? i15 != 80 ? i : this.mDefaultFreeformStepVertical
                                    : -this.mDefaultFreeformStepVertical);
                    if (z && !rect.contains(this.mTmpBounds)) {
                        Rect rect3 = this.mTmpBounds;
                        int width = rect3.width();
                        int height = rect3.height();
                        if (rect3.bottom > rect.bottom) {
                            int i16 = rect.top;
                            rect3.top = i16;
                            rect3.bottom = i16 + height;
                        }
                        if (rect3.right > rect.right) {
                            int i17 = rect.left;
                            rect3.left = i17;
                            rect3.right = i17 + width;
                        }
                    }
                    int i18 = i13 + 1;
                    if (i13 > 200) {
                        Slog.w(
                                "ActivityTaskManager",
                                "TaskLaunchParamsModifier.position: max_bounds_conflict_count,"
                                    + " inOutBounds="
                                        + rect2
                                        + ", mTmpBounds="
                                        + this.mTmpBounds
                                        + ", displayBounds="
                                        + rect
                                        + ", mDefaultFreeformStepHorizontal="
                                        + this.mDefaultFreeformStepHorizontal
                                        + ", mDefaultFreeformStepVertical="
                                        + this.mDefaultFreeformStepVertical
                                        + ", Callers="
                                        + Debug.getCallers(8));
                        rect2.set(rect);
                        StringBuilder sb =
                                new StringBuilder(
                                        "TaskLaunchParamsModifier.position:"
                                            + " max_bounds_conflict_count, adjusted proposal=");
                        sb.append(rect2);
                        sb.append(", break!");
                        Slog.w("ActivityTaskManager", sb.toString());
                        break;
                    }
                    i13 = i18;
                    i = 0;
                }
                if (!boundsConflict(list, this.mTmpBounds) && rect.contains(this.mTmpBounds)) {
                    rect2.set(this.mTmpBounds);
                    appendLog("avoid-bounds-conflict=" + rect2);
                    return;
                }
                i11++;
                i = 0;
            }
        }
    }

    public final void adjustBoundsToAvoidConflictInDisplayArea(
            TaskDisplayArea taskDisplayArea,
            Rect rect,
            final String str,
            final Rect rect2,
            final Task task) {
        final ArrayList arrayList = new ArrayList();
        taskDisplayArea.forAllRootTasks(
                new Consumer() { // from class:
                                 // com.android.server.wm.MultiTaskingTaskLaunchParamsModifier$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        String str2 = str;
                        Rect rect3 = rect2;
                        Task task2 = task;
                        List list = arrayList;
                        Task task3 = (Task) obj;
                        if (task3.inFreeformWindowingMode()
                                && !task3.isUnderHomeRootTask()
                                && !task3.isMinimized()
                                && task3.isVisible()) {
                            PooledPredicate obtainPredicate =
                                    PooledLambda.obtainPredicate(
                                            new MultiTaskingTaskLaunchParamsModifier$$ExternalSyntheticLambda3(),
                                            PooledLambda.__(Task.class),
                                            str2,
                                            rect3);
                            Task task4 = task3.getTask(obtainPredicate);
                            obtainPredicate.recycle();
                            if (task4 != null) {
                                if (task2 == null || !task2.equals(task3)) {
                                    list.add(task3.getBounds());
                                }
                            }
                        }
                    }
                },
                false);
        adjustBoundsToAvoidConflict(
                taskDisplayArea.getBounds(),
                arrayList,
                rect,
                taskDisplayArea.mDisplayContent.mDisplayId == 2);
    }

    public final void adjustBoundsToFitInDisplayArea(
            TaskDisplayArea taskDisplayArea, ActivityInfo.WindowLayout windowLayout, Rect rect) {
        int layoutDirection =
                this.mSupervisor.mRootWindowContainer.getConfiguration().getLayoutDirection();
        Rect rect2 = LaunchParamsUtil.TMP_STABLE_BOUNDS;
        if (taskDisplayArea.isDesktopModeEnabled()) {
            InsetsState insetsState =
                    taskDisplayArea.getDisplayContent()
                            .mDisplayContent
                            .mInsetsStateController
                            .mState;
            rect2.set(insetsState.getDisplayFrame());
            rect2.inset(insetsState.calculateInsets(rect2, WindowInsets.Type.systemBars(), false));
            rect2.intersect(taskDisplayArea.getBounds());
        } else {
            taskDisplayArea.getStableRect(rect2);
            int i =
                    (int)
                            (((taskDisplayArea.getConfiguration().densityDpi / 160.0f) * 27.0f)
                                    + 0.5f);
            rect2.inset(i, i);
        }
        if (rect2.width() < rect.width() || rect2.height() < rect.height()) {
            float min = Math.min(rect2.width() / rect.width(), rect2.height() / rect.height());
            int i2 = windowLayout == null ? -1 : windowLayout.minWidth;
            int i3 = windowLayout != null ? windowLayout.minHeight : -1;
            int max = Math.max(i2, (int) (rect.width() * min));
            int max2 = Math.max(i3, (int) (rect.height() * min));
            if (rect2.width() < max || rect2.height() < max2) {
                int i4 = layoutDirection == 1 ? rect2.right - max : rect2.left;
                int i5 = rect2.top;
                rect.set(i4, i5, max + i4, max2 + i5);
                return;
            } else {
                int i6 = rect.left;
                int i7 = rect.top;
                rect.set(i6, i7, max + i6, max2 + i7);
            }
        }
        int i8 = rect.right;
        int i9 = rect2.right;
        int i10 = (i8 <= i9 && (i8 = rect.left) >= (i9 = rect2.left)) ? 0 : i9 - i8;
        int i11 = rect.top;
        int i12 = rect2.top;
        rect.offset(i10, (i11 < i12 || (i11 = rect.bottom) > (i12 = rect2.bottom)) ? i12 - i11 : 0);
    }

    public final void appendLog(String str) {
        StringBuilder sb = this.mLogBuilder;
        sb.append(" ");
        sb.append(str);
    }

    public final void cascadeBounds(Rect rect, TaskDisplayArea taskDisplayArea, Rect rect2) {
        rect2.set(rect);
        int i = taskDisplayArea.getConfiguration().densityDpi;
        taskDisplayArea.getBounds(this.mTmpBounds);
        rect2.offset(
                Math.min(
                        this.mDefaultFreeformStepHorizontal,
                        Math.max(0, this.mTmpBounds.right - rect.right)),
                Math.min(
                        this.mDefaultFreeformStepVertical,
                        Math.max(0, this.mTmpBounds.bottom - rect.bottom)));
    }

    public final boolean getDexMetaDataBounds(
            TaskDisplayArea taskDisplayArea, Task task, ActivityRecord activityRecord, Rect rect) {
        Point dexMetadataLaunchSizeLocked;
        if (task != null) {
            DexController dexController = task.mAtmService.mDexController;
            DexController.DexMetaDataInfo dexMetaDataInfo = task.mDexMetaDataInfo;
            int displayId = task.getDisplayId();
            task.getWindowingMode();
            dexMetadataLaunchSizeLocked =
                    dexController.getDexMetadataLaunchSizeLocked(dexMetaDataInfo, displayId);
        } else {
            DexController dexController2 = this.mSupervisor.mService.mDexController;
            DexController.DexMetaDataInfo parseDexMetadata =
                    DexController.parseDexMetadata(activityRecord.info);
            int i = taskDisplayArea.mDisplayContent.mDisplayId;
            activityRecord.getWindowingMode();
            dexMetadataLaunchSizeLocked =
                    dexController2.getDexMetadataLaunchSizeLocked(parseDexMetadata, i);
        }
        if (dexMetadataLaunchSizeLocked == null) {
            return false;
        }
        int i2 = dexMetadataLaunchSizeLocked.x;
        if (i2 == 0 && dexMetadataLaunchSizeLocked.y == 0) {
            rect.setEmpty();
            appendLog("dex-fullscreen-metadata-bounds");
            return true;
        }
        rect.set(0, 0, i2, dexMetadataLaunchSizeLocked.y);
        ActivityInfo.WindowLayout windowLayout = this.mTmpLayout;
        if (windowLayout == null
                || windowLayout.width != dexMetadataLaunchSizeLocked.x
                || windowLayout.height != dexMetadataLaunchSizeLocked.y) {
            this.mTmpLayout =
                    new ActivityInfo.WindowLayout(
                            dexMetadataLaunchSizeLocked.x,
                            -1.0f,
                            dexMetadataLaunchSizeLocked.y,
                            -1.0f,
                            17,
                            -1,
                            -1);
        }
        getLayoutBounds(taskDisplayArea, activityRecord, this.mTmpLayout, rect);
        return true;
    }

    public final void getInitialDexBounds(
            Rect rect, ActivityRecord activityRecord, TaskDisplayArea taskDisplayArea) {
        int i;
        float f;
        float f2;
        int width = taskDisplayArea.getBounds().width();
        int height = taskDisplayArea.getBounds().height();
        if (this.mSupervisor.mService.mDexController.getDexModeLocked() == 1) {
            PointF pointF = MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO_FOR_STANDALONE;
            i = (int) (width * pointF.x);
            f = height;
            f2 = pointF.y;
        } else {
            PointF pointF2 = MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO;
            i = (int) (width * pointF2.x);
            f = height;
            f2 = pointF2.y;
        }
        int i2 = (int) (f * f2);
        int i3 = i;
        appendLog("from-default-size");
        ActivityInfo.WindowLayout windowLayout = this.mTmpLayout;
        if (windowLayout == null || windowLayout.width != i3 || windowLayout.height != i2) {
            this.mTmpLayout = new ActivityInfo.WindowLayout(i3, -1.0f, i2, -1.0f, 17, -1, -1);
        }
        getLayoutBounds(taskDisplayArea, activityRecord, this.mTmpLayout, rect);
    }

    public final void getLayoutBounds(
            TaskDisplayArea taskDisplayArea,
            ActivityRecord activityRecord,
            ActivityInfo.WindowLayout windowLayout,
            Rect rect) {
        int i;
        int i2;
        int i3 = windowLayout.gravity;
        int i4 = i3 & 112;
        int i5 = i3 & 7;
        if (!windowLayout.hasSpecifiedSize() && i4 == 0 && i5 == 0) {
            rect.setEmpty();
            return;
        }
        Rect rect2 = this.mTmpStableBounds;
        taskDisplayArea.getStableRect(rect2);
        int width = rect2.width();
        int height = rect2.height();
        float f = 1.0f;
        if (windowLayout.hasSpecifiedSize()) {
            i = windowLayout.width;
            if (i < 0 || i >= width) {
                float f2 = windowLayout.widthFraction;
                i =
                        (f2 <= FullScreenMagnificationGestureHandler.MAX_SCALE || f2 >= 1.0f)
                                ? width
                                : (int) (width * f2);
            }
            i2 = windowLayout.height;
            if (i2 < 0 || i2 >= height) {
                float f3 = windowLayout.heightFraction;
                i2 =
                        (f3 <= FullScreenMagnificationGestureHandler.MAX_SCALE || f3 >= 1.0f)
                                ? height
                                : (int) (height * f3);
            }
        } else if (rect.isEmpty()) {
            getTaskBounds(activityRecord, taskDisplayArea, windowLayout, 5, false, rect);
            i = rect.width();
            i2 = rect.height();
        } else {
            i = rect.width();
            i2 = rect.height();
        }
        float f4 = i5 != 3 ? i5 != 5 ? 0.5f : 1.0f : 0.0f;
        if (i4 == 48) {
            f = 0.0f;
        } else if (i4 != 80) {
            f = 0.5f;
        }
        rect.set(0, 0, i, i2);
        rect.offset(rect2.left, rect2.top);
        rect.offset((int) (f4 * (width - i)), (int) (f * (height - i2)));
    }

    public final TaskDisplayArea getPreferredLaunchTaskDisplayArea(
            Task task,
            ActivityOptions activityOptions,
            ActivityRecord activityRecord,
            LaunchParamsController.LaunchParams launchParams,
            ActivityRecord activityRecord2,
            ActivityStarter.Request request) {
        TaskDisplayArea taskDisplayArea;
        DisplayContent topFocusedDisplayContent;
        TaskDisplayArea taskDisplayArea2;
        TaskDisplayArea defaultTaskDisplayArea;
        WindowProcessController processController;
        int callerDisplayId;
        DisplayContent displayContent;
        Task topDisplayFocusedRootTask;
        int i;
        DisplayContent displayContent2;
        final int launchTaskDisplayAreaFeatureId;
        TaskDisplayArea taskDisplayArea3 = null;
        WindowContainerToken launchTaskDisplayArea =
                activityOptions != null ? activityOptions.getLaunchTaskDisplayArea() : null;
        if (launchTaskDisplayArea != null) {
            taskDisplayArea =
                    (TaskDisplayArea) WindowContainer.fromBinder(launchTaskDisplayArea.asBinder());
            appendLog("display-area-token-from-option=" + taskDisplayArea);
        } else {
            taskDisplayArea = null;
        }
        ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
        if (taskDisplayArea == null
                && activityOptions != null
                && (launchTaskDisplayAreaFeatureId =
                                activityOptions.getLaunchTaskDisplayAreaFeatureId())
                        != -1) {
            DisplayContent displayContent3 =
                    activityTaskSupervisor.mRootWindowContainer.getDisplayContent(
                            activityOptions.getLaunchDisplayId() == -1
                                    ? 0
                                    : activityOptions.getLaunchDisplayId());
            if (displayContent3 != null) {
                taskDisplayArea =
                        (TaskDisplayArea)
                                displayContent3.getItemFromTaskDisplayAreas(
                                        new Function() { // from class:
                                                         // com.android.server.wm.MultiTaskingTaskLaunchParamsModifier$$ExternalSyntheticLambda2
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                TaskDisplayArea taskDisplayArea4 =
                                                        (TaskDisplayArea) obj;
                                                if (taskDisplayArea4.mFeatureId
                                                        == launchTaskDisplayAreaFeatureId) {
                                                    return taskDisplayArea4;
                                                }
                                                return null;
                                            }
                                        });
                appendLog("display-area-feature-from-option=" + taskDisplayArea);
            }
        }
        if (taskDisplayArea == null) {
            int launchDisplayId =
                    activityOptions != null ? activityOptions.getLaunchDisplayId() : -1;
            if (launchDisplayId != -1
                    && (displayContent2 =
                                    activityTaskSupervisor.mRootWindowContainer.getDisplayContent(
                                            launchDisplayId))
                            != null) {
                taskDisplayArea = displayContent2.getDefaultTaskDisplayArea();
                appendLog("display-from-option=" + launchDisplayId);
            }
        }
        if (taskDisplayArea == null && activityRecord != null && activityRecord.noDisplay) {
            taskDisplayArea = activityRecord.mHandoverTaskDisplayArea;
            if (taskDisplayArea != null) {
                appendLog("display-area-from-no-display-source=" + taskDisplayArea);
            } else {
                int i2 = activityRecord.mHandoverLaunchDisplayId;
                DisplayContent displayContent4 =
                        activityTaskSupervisor.mRootWindowContainer.getDisplayContent(i2);
                if (displayContent4 != null) {
                    taskDisplayArea = displayContent4.getDefaultTaskDisplayArea();
                    appendLog("display-from-no-display-source=" + i2);
                }
            }
        }
        ActivityRecord rootActivity =
                (task == null || task.getRootActivity(true, false) == null)
                        ? activityRecord2
                        : task.getRootActivity(true, false);
        if (taskDisplayArea == null
                || (i = taskDisplayArea.mDisplayContent.mDisplayId) == 2
                || i == -1) {
            int i3 = taskDisplayArea == null ? -1 : taskDisplayArea.mDisplayContent.mDisplayId;
            if (activityTaskSupervisor.mService.mDexController.getDexModeLocked() == 2) {
                if (i3 == -1 && activityRecord != null) {
                    i3 = activityRecord.getDisplayId();
                    appendLog("display-from-source=" + i3);
                }
                if (i3 == -1
                        && task != null
                        && task.getDisplayId() == 2
                        && (taskDisplayArea2 = launchParams.mPreferredTaskDisplayArea) != null
                        && taskDisplayArea2.mDisplayContent.mDisplayId == 2) {
                    appendLog("display-from-task=2");
                    i3 = 2;
                }
                if (i3 == -1
                        && activityRecord2 != null
                        && !activityRecord2.isActivityTypeHome()
                        && !activityRecord2.isActivityTypeRecents()
                        && (topFocusedDisplayContent =
                                        activityTaskSupervisor.mService.mRootWindowContainer
                                                .getTopFocusedDisplayContent())
                                != null) {
                    i3 = topFocusedDisplayContent.mDisplayId;
                    appendLog("display-from-focused-stack=" + i3);
                }
            } else {
                TaskDisplayArea taskDisplayArea4 = launchParams.mPreferredTaskDisplayArea;
                i3 =
                        ((taskDisplayArea4 == null
                                                || taskDisplayArea4.mDisplayContent.mDisplayId != 2)
                                        && (task == null || task.getDisplayId() != 2))
                                ? -1
                                : 0;
            }
            DisplayContent displayContent5 =
                    activityTaskSupervisor.mRootWindowContainer.getDisplayContent(i3);
            TaskDisplayArea defaultTaskDisplayArea2 =
                    displayContent5 != null ? displayContent5.getDefaultTaskDisplayArea() : null;
            if (activityOptions != null && activityOptions.getLaunchTaskId() != -1) {
                Task anyTaskForId =
                        activityTaskSupervisor.mRootWindowContainer.anyTaskForId(
                                activityOptions.getLaunchTaskId(), 0, null, false);
                if (anyTaskForId != null && anyTaskForId.getDisplayArea() != null) {
                    taskDisplayArea = anyTaskForId.getDisplayArea();
                    appendLog(
                            "display-from-launch-target-task"
                                    + taskDisplayArea.mDisplayContent.mDisplayId);
                } else if (defaultTaskDisplayArea2 != null) {
                    appendLog(
                            "display-from-dex-policy"
                                    + defaultTaskDisplayArea2.mDisplayContent.mDisplayId);
                    taskDisplayArea = defaultTaskDisplayArea2;
                }
            } else if (defaultTaskDisplayArea2 != null) {
                appendLog(
                        "display-from-dex-policy"
                                + defaultTaskDisplayArea2.mDisplayContent.mDisplayId);
                taskDisplayArea = defaultTaskDisplayArea2;
            }
        }
        if (taskDisplayArea == null
                && rootActivity != null
                && activityTaskSupervisor.mService.mRemoteAppController
                        .isRemoteAppDisplayRunningLocked()) {
            taskDisplayArea = activityRecord != null ? activityRecord.getDisplayArea() : null;
            if (taskDisplayArea == null
                    && (topDisplayFocusedRootTask =
                                    activityTaskSupervisor.mService.mRootWindowContainer
                                            .getTopDisplayFocusedRootTask())
                            != null) {
                taskDisplayArea = topDisplayFocusedRootTask.getDisplayArea();
            }
            if (taskDisplayArea != null) {
                DisplayContent displayContent6 = taskDisplayArea.mDisplayContent;
                boolean z = displayContent6 != null && displayContent6.mDisplayId == 0;
                boolean z2 = displayContent6 != null && displayContent6.isRemoteAppDisplay();
                if (z
                        || (z2
                                && (rootActivity.isActivityTypeStandardOrUndefined()
                                        || rootActivity.isActivityTypeAssistant()))) {
                    appendLog("display-from-remote-app-policy=" + displayContent6.mDisplayId);
                } else {
                    taskDisplayArea = null;
                }
            }
        }
        if (taskDisplayArea == null && activityRecord != null) {
            taskDisplayArea = activityRecord.getDisplayArea();
            appendLog("display-area-from-source=" + taskDisplayArea);
        }
        Task rootTask = (taskDisplayArea != null || task == null) ? null : task.getRootTask();
        if (rootTask != null) {
            if (WmCoverState.sIsEnabled
                    && ((CoverState) WmCoverState.getInstance()).attached
                    && !(!((CoverState) WmCoverState.getInstance()).switchState)) {
                if (rootActivity != null && !rootActivity.isActivityTypeHomeOrRecents()) {
                    DisplayContent topFocusedDisplayContent2 =
                            activityTaskSupervisor.mService.mRootWindowContainer
                                    .getTopFocusedDisplayContent();
                    if (topFocusedDisplayContent2.mDisplayId == 0) {
                        appendLog("display-from-focus=" + topFocusedDisplayContent2.mDisplayId);
                        taskDisplayArea3 = topFocusedDisplayContent2.getDefaultTaskDisplayArea();
                    }
                }
                taskDisplayArea = taskDisplayArea3;
            }
            if (taskDisplayArea == null) {
                appendLog("display-from-task=" + rootTask.getDisplayId());
                taskDisplayArea = rootTask.getDisplayArea();
            }
        }
        if (taskDisplayArea == null
                && activityOptions != null
                && (displayContent =
                                activityTaskSupervisor.mRootWindowContainer.getDisplayContent(
                                        (callerDisplayId = activityOptions.getCallerDisplayId())))
                        != null) {
            taskDisplayArea = displayContent.getDefaultTaskDisplayArea();
            appendLog("display-from-caller=" + callerDisplayId);
        }
        if (taskDisplayArea == null && launchParams != null) {
            taskDisplayArea = launchParams.mPreferredTaskDisplayArea;
            appendLog("display-area-from-current-params=" + taskDisplayArea);
        }
        if (taskDisplayArea != null
                && !activityTaskSupervisor.mService.mSupportsMultiDisplay
                && taskDisplayArea.mDisplayContent.mDisplayId != 0) {
            taskDisplayArea =
                    activityTaskSupervisor.mRootWindowContainer.mDefaultDisplay
                            .getDefaultTaskDisplayArea();
            appendLog("display-area-from-no-multidisplay=" + taskDisplayArea);
        }
        if (taskDisplayArea != null
                && activityRecord2 != null
                && activityRecord2.isActivityTypeHome()
                && !activityTaskSupervisor.mRootWindowContainer.canStartHomeOnDisplayArea(
                        activityRecord2.info, taskDisplayArea, false)) {
            taskDisplayArea =
                    activityTaskSupervisor.mRootWindowContainer.mDefaultDisplay
                            .getDefaultTaskDisplayArea();
            appendLog("display-area-from-home=" + taskDisplayArea);
        }
        if (taskDisplayArea != null) {
            return taskDisplayArea;
        }
        if (activityRecord2 != null) {
            WindowProcessController processController2 =
                    activityTaskSupervisor.mService.getProcessController(
                            activityRecord2.launchedFromPid, activityRecord2.launchedFromUid);
            if (processController2 == null
                    || (defaultTaskDisplayArea = processController2.getTopActivityDisplayArea())
                            == null) {
                WindowProcessController processController3 =
                        activityTaskSupervisor.mService.getProcessController(
                                activityRecord2.getUid(),
                                activityRecord2.info.applicationInfo.processName);
                if (processController3 != null
                        && (defaultTaskDisplayArea = processController3.getTopActivityDisplayArea())
                                != null) {
                    appendLog("display-area-for-record=" + defaultTaskDisplayArea);
                }
            } else {
                appendLog("display-area-for-launching-record=" + defaultTaskDisplayArea);
            }
            int i4 = defaultTaskDisplayArea.mDisplayContent.mDisplayId;
            return ((i4 == 2 || activityTaskSupervisor.mService.mDexController.mDexDisplayActivated)
                            && !activityTaskSupervisor.mService.mWindowManager.mExt
                                    .mExtraDisplayPolicy.shouldChooseDefaultTaskDisplayArea(i4))
                    ? defaultTaskDisplayArea
                    : activityTaskSupervisor.mRootWindowContainer.mDefaultDisplay
                            .getDefaultTaskDisplayArea();
        }
        if (request == null
                || (processController =
                                activityTaskSupervisor.mService.getProcessController(
                                        request.realCallingPid, request.realCallingUid))
                        == null
                || (defaultTaskDisplayArea = processController.getTopActivityDisplayArea())
                        == null) {
            defaultTaskDisplayArea =
                    activityTaskSupervisor.mRootWindowContainer.mDefaultDisplay
                            .getDefaultTaskDisplayArea();
            appendLog("display-area-from-default-fallback=" + defaultTaskDisplayArea);
        } else {
            appendLog("display-area-source-process=" + defaultTaskDisplayArea);
        }
        int i42 = defaultTaskDisplayArea.mDisplayContent.mDisplayId;
        if (i42 == 2) {}
    }

    public final void getTaskBounds(
            ActivityRecord activityRecord,
            TaskDisplayArea taskDisplayArea,
            ActivityInfo.WindowLayout windowLayout,
            int i,
            boolean z,
            Rect rect) {
        if (i != 5 && i != 1) {
            appendLog("skip-bounds-" + WindowConfiguration.windowingModeToString(i));
            return;
        }
        int resolveOrientation = resolveOrientation(rect, activityRecord, taskDisplayArea);
        if (resolveOrientation != 1 && resolveOrientation != 0) {
            throw new IllegalStateException(
                    "Orientation must be one of portrait or landscape, but it's "
                            + ActivityInfo.screenOrientationToString(resolveOrientation));
        }
        taskDisplayArea.getStableRect(this.mTmpStableBounds);
        Rect rect2 = this.mTmpStableBounds;
        if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY) {
            this.mSupervisor.mService.mMultiWindowFoldController.getClass();
        }
        Rect bounds = taskDisplayArea.getBounds();
        Rect rect3 = new Rect();
        MultiWindowUtils.getDefaultFreeformBounds(bounds, rect3);
        taskDisplayArea.getStableRect(rect2);
        if (rect2.width() < rect3.width()) {
            rect3.right = rect2.width();
        }
        if (rect2.height() < rect3.height()) {
            rect3.bottom = rect2.height();
        }
        Size size = new Size(rect3.width(), rect3.height());
        this.mTmpBounds.set(0, 0, size.getWidth(), size.getHeight());
        if (!z && !sizeMatches(rect, this.mTmpBounds)) {
            adjustBoundsToFitInDisplayArea(taskDisplayArea, windowLayout, this.mTmpBounds);
            rect.setEmpty();
            LaunchParamsUtil.centerBounds(
                    taskDisplayArea, this.mTmpBounds.width(), this.mTmpBounds.height(), rect);
            appendLog("freeform-size-mismatch=" + rect);
        } else if (resolveOrientation == orientationFromBounds(rect)) {
            appendLog("freeform-size-orientation-match=" + rect);
        } else if (sizeMatches(rect, this.mTmpBounds)) {
            taskDisplayArea.getBounds(this.mTmpBounds2);
            LaunchParamsUtil.centerBounds(
                    taskDisplayArea,
                    this.mTmpBounds.width(),
                    this.mTmpBounds.height(),
                    this.mTmpBounds2);
            rect.set(this.mTmpBounds2);
            appendLog("freeform-orientation-ignore=" + rect);
        } else {
            LaunchParamsUtil.centerBounds(taskDisplayArea, rect.height(), rect.width(), rect);
            appendLog("freeform-orientation-mismatch=" + rect);
        }
        adjustBoundsToAvoidConflictInDisplayArea(taskDisplayArea, rect, null, null, null);
    }

    public final ActivityInfo.WindowLayout recalculateLayout(
            TaskDisplayArea taskDisplayArea,
            ActivityInfo.WindowLayout windowLayout,
            ActivityRecord activityRecord) {
        if (!windowLayout.hasSpecifiedSize()) {
            return windowLayout;
        }
        float initialDisplayDensity =
                this.mSupervisor.mService.mWindowManager.getInitialDisplayDensity(0);
        if (initialDisplayDensity <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            return windowLayout;
        }
        float f =
                ((activityRecord == null
                                        || !"android.server.wm.app"
                                                .equals(activityRecord.packageName))
                                ? taskDisplayArea.getConfiguration().densityDpi
                                : DisplayMetrics.DENSITY_DEVICE_STABLE)
                        / initialDisplayDensity;
        int i = windowLayout.width;
        int i2 = i < 0 ? -1 : (int) ((i * f) + 0.5f);
        int i3 = windowLayout.height;
        int i4 = i3 < 0 ? -1 : (int) ((i3 * f) + 0.5f);
        int i5 = windowLayout.minWidth;
        int i6 = i5 < 0 ? -1 : (int) ((i5 * f) + 0.5f);
        int i7 = windowLayout.minHeight;
        return new ActivityInfo.WindowLayout(
                i2,
                windowLayout.widthFraction,
                i4,
                windowLayout.heightFraction,
                windowLayout.gravity,
                i6,
                i7 >= 0 ? (int) ((i7 * f) + 0.5f) : -1);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int resolveOrientation(
            android.graphics.Rect r6,
            com.android.server.wm.ActivityRecord r7,
            com.android.server.wm.TaskDisplayArea r8) {
        /*
            r5 = this;
            android.content.pm.ActivityInfo r7 = r7.info
            int r7 = r7.screenOrientation
            r0 = 0
            r1 = 14
            r2 = -1
            r3 = 1
            if (r7 == 0) goto L25
            if (r7 == r3) goto L1e
            r4 = 11
            if (r7 == r4) goto L25
            r4 = 12
            if (r7 == r4) goto L1e
            if (r7 == r1) goto L1c
            switch(r7) {
                case 5: goto L1c;
                case 6: goto L25;
                case 7: goto L1e;
                case 8: goto L25;
                case 9: goto L1e;
                default: goto L1a;
            }
        L1a:
            r7 = r2
            goto L2b
        L1c:
            r7 = r1
            goto L2b
        L1e:
            java.lang.String r7 = "activity-requested-portrait"
            r5.appendLog(r7)
            r7 = r3
            goto L2b
        L25:
            java.lang.String r7 = "activity-requested-landscape"
            r5.appendLog(r7)
            r7 = r0
        L2b:
            if (r7 != r1) goto L65
            boolean r7 = r6.isEmpty()
            if (r7 == 0) goto L43
            android.content.res.Configuration r7 = r8.getConfiguration()
            int r7 = r7.orientation
            if (r7 == r3) goto L40
            r8 = 2
            if (r7 == r8) goto L41
            r0 = r2
            goto L41
        L40:
            r0 = r3
        L41:
            r7 = r0
            goto L47
        L43:
            int r7 = orientationFromBounds(r6)
        L47:
            boolean r8 = r6.isEmpty()
            if (r8 == 0) goto L54
            java.lang.String r8 = "locked-orientation-from-display="
            java.lang.String r8 = android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0.m(r7, r8)
            goto L62
        L54:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r0 = "locked-orientation-from-bounds="
            r8.<init>(r0)
            r8.append(r6)
            java.lang.String r8 = r8.toString()
        L62:
            r5.appendLog(r8)
        L65:
            if (r7 != r2) goto L8e
            boolean r7 = r6.isEmpty()
            if (r7 == 0) goto L6e
            goto L72
        L6e:
            int r3 = orientationFromBounds(r6)
        L72:
            boolean r7 = r6.isEmpty()
            if (r7 == 0) goto L7b
            java.lang.String r6 = "default-portrait"
            goto L8a
        L7b:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "orientation-from-bounds="
            r7.<init>(r8)
            r7.append(r6)
            java.lang.String r6 = r7.toString()
        L8a:
            r5.appendLog(r6)
            r7 = r3
        L8e:
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.MultiTaskingTaskLaunchParamsModifier.resolveOrientation(android.graphics.Rect,"
                    + " com.android.server.wm.ActivityRecord,"
                    + " com.android.server.wm.TaskDisplayArea):int");
    }

    public final boolean shouldLaunchUnresizableAppInFreeform(
            ActivityRecord activityRecord,
            TaskDisplayArea taskDisplayArea,
            ActivityOptions activityOptions) {
        if ((activityOptions == null || activityOptions.getLaunchWindowingMode() != 1)
                && activityRecord.supportsFreeformInDisplayArea(taskDisplayArea)
                && !activityRecord.isResizeable(true)) {
            int orientationFromBounds = orientationFromBounds(taskDisplayArea.getBounds());
            int resolveOrientation =
                    resolveOrientation(
                            taskDisplayArea.getBounds(), activityRecord, taskDisplayArea);
            if (taskDisplayArea.getWindowingMode() == 5
                    && orientationFromBounds != resolveOrientation) {
                return true;
            }
        }
        return false;
    }
}
