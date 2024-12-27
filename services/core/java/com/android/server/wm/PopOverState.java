package com.android.server.wm;

import android.app.ActivityOptions;
import android.graphics.Point;
import android.graphics.Rect;
import com.android.server.wm.Transition;
import java.util.Arrays;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PopOverState {
    public final ActivityRecord mActivityRecord;
    public boolean mIsActivated;
    public boolean mLastOccludesParent;
    public ActivityOptions mOptions;
    public ActivityOptions mOptionsInherited;
    public int mOriginTaskId = -1;
    public int mOriginTaskIdInherited = -1;

    public PopOverState(ActivityRecord activityRecord) {
        this.mActivityRecord = activityRecord;
    }

    public final void adjustOptions(int[] iArr, int[] iArr2, Point[] pointArr, int[] iArr3) {
        WindowState findMainWindow;
        if (this.mOptions == null && this.mOptionsInherited == null) {
            return;
        }
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord.task == null) {
            return;
        }
        Rect bounds = PopOverBoundsCalculator.getBounds(activityRecord);
        if (iArr == null && iArr2 == null && pointArr == null && iArr3 == null) {
            this.mOptions = null;
            this.mOptionsInherited = null;
            this.mOriginTaskId = -1;
            this.mOriginTaskIdInherited = -1;
        } else {
            ActivityOptions activityOptions = this.mOptions;
            if (activityOptions == null) {
                activityOptions = this.mOptionsInherited;
            }
            if (iArr != null) {
                activityOptions.mPopOverWidthDp = (int[]) iArr.clone();
            }
            if (iArr2 != null) {
                activityOptions.mPopOverHeightDp = (int[]) iArr2.clone();
            }
            if (pointArr != null) {
                activityOptions.mPopOverAnchorMarginDp = (Point[]) pointArr.clone();
            }
            if (iArr3 != null) {
                if (!Arrays.equals(activityOptions.mPopOverAnchorPosition, (int[]) iArr3.clone()) && (findMainWindow = activityRecord.findMainWindow(true)) != null) {
                    findMainWindow.forceReportingResized();
                }
                activityOptions.mPopOverAnchorPosition = (int[]) iArr3.clone();
            }
        }
        activityRecord.recomputeConfiguration();
        activityRecord.ensureActivityConfiguration(true);
        Rect bounds2 = PopOverBoundsCalculator.getBounds(activityRecord);
        if (bounds.width() == bounds2.width() && bounds.height() == bounds2.height()) {
            return;
        }
        ChangeTransitionController changeTransitionController = activityRecord.mAtmService.mChangeTransitController;
        changeTransitionController.getClass();
        DisplayContent displayContent = activityRecord.mDisplayContent;
        Task task = activityRecord.task;
        if (displayContent == null || task == null || !activityRecord.mPopOverState.mIsActivated || task.isChangeTransitionBlockedByCommonPolicy()) {
            return;
        }
        Transition.ChangeInfo findCollectingChangeInfo = changeTransitionController.findCollectingChangeInfo(activityRecord);
        if (findCollectingChangeInfo == null || findCollectingChangeInfo.mChangeLeash == null) {
            Transition createTransition = !changeTransitionController.mTransitionController.isCollecting() ? changeTransitionController.mTransitionController.createTransition(6, 0) : null;
            changeTransitionController.mTransitionController.collect(activityRecord);
            changeTransitionController.updateChangeInfo(activityRecord, 5, 1, bounds, 0);
            changeTransitionController.mTransitionController.collectVisibleChange(activityRecord);
            if (createTransition != null) {
                changeTransitionController.mTransitionController.requestStartTransition(createTransition, task, null, null);
                createTransition.setReady(task, true);
            }
        }
    }

    public boolean isAboveAnotherOpaquePopOver() {
        ActivityRecord activityRecord = this.mActivityRecord;
        Task task = activityRecord.task;
        return (task == null || task.getActivity(new PopOverState$$ExternalSyntheticLambda0(0, this), activityRecord, false, true) == null) ? false : true;
    }

    public boolean isInLargeSizeTask() {
        Task task = this.mActivityRecord.task;
        return task != null && task.getConfiguration().smallestScreenWidthDp >= 600;
    }

    public boolean shouldRemoveOutlineEffect() {
        return !this.mLastOccludesParent && isAboveAnotherOpaquePopOver();
    }

    public final void toggle() {
        Task task;
        ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord.isEmbedded()) {
            if (this.mIsActivated) {
                this.mIsActivated = false;
                activityRecord.mOccludesParent = this.mLastOccludesParent;
                activityRecord.forAllWindows((Consumer) new PopOverState$$ExternalSyntheticLambda2(), true);
                return;
            }
            return;
        }
        if (!isInLargeSizeTask()) {
            if (this.mIsActivated) {
                this.mIsActivated = false;
                activityRecord.mOccludesParent = this.mLastOccludesParent;
                activityRecord.forAllWindows((Consumer) new PopOverState$$ExternalSyntheticLambda2(), true);
                return;
            }
            return;
        }
        if (this.mIsActivated || this.mOptions == null || (task = activityRecord.task) == null || task.mTaskId != this.mOriginTaskId || activityRecord.isRootOfTask() || activityRecord.mReparenting) {
            return;
        }
        this.mIsActivated = true;
        activityRecord.mOccludesParent = false;
        Task task2 = activityRecord.task;
        if (!task2.mLastDispatchedWindowFocusInTask) {
            task2.updateWindowFocusInTask();
        }
        if (!activityRecord.getDisplayContent().mWaitingForConfig && activityRecord.mVisible && activityRecord.task.shouldBeVisible(null)) {
            if (activityRecord.task.inSplitScreenWindowingMode()) {
                activityRecord.getParent().getParent().asTask().mPendingEnsureVisibleForPopOver = true;
            } else if (activityRecord.task.inFreeformWindowingMode()) {
                activityRecord.task.mPendingEnsureVisibleForPopOver = true;
            }
        }
    }
}
