package com.android.server.wm;

import android.util.Slog;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultiTaskingController$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MultiTaskingController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MultiTaskingController$$ExternalSyntheticLambda2(
            MultiTaskingController multiTaskingController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = multiTaskingController;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                MultiTaskingController multiTaskingController = this.f$0;
                boolean[] zArr = (boolean[]) this.f$1;
                TaskDisplayArea taskDisplayArea = (TaskDisplayArea) obj;
                multiTaskingController.getClass();
                ActivityRecord focusedActivity = taskDisplayArea.getFocusedActivity();
                if (focusedActivity != null && focusedActivity.task != null) {
                    boolean isActivityTypeHomeOrRecents =
                            focusedActivity.isActivityTypeHomeOrRecents();
                    ActivityTaskManagerService activityTaskManagerService =
                            multiTaskingController.mAtm;
                    if (!isActivityTypeHomeOrRecents) {
                        Slog.d(
                                "MultiTaskingController",
                                "removeFocusedTask, focusedTask=" + focusedActivity.task);
                        activityTaskManagerService.removeTask(focusedActivity.task.mTaskId);
                        zArr[0] = true;
                        break;
                    } else {
                        Task rootTask = taskDisplayArea.getRootTask(5, 1);
                        if (rootTask != null && rootTask.getTopMostTask() != null) {
                            Slog.d(
                                    "MultiTaskingController",
                                    "removeFocusedTask, topMostFreeformTask="
                                            + rootTask.getTopMostTask());
                            activityTaskManagerService.removeTask(
                                    rootTask.getTopMostTask().mTaskId);
                            zArr[0] = true;
                            break;
                        }
                    }
                }
                break;
            default:
                MultiTaskingController multiTaskingController2 = this.f$0;
                ArrayList arrayList = (ArrayList) this.f$1;
                Task task = (Task) obj;
                multiTaskingController2.getClass();
                ActivityRecord topNonFinishingActivity =
                        task.getTopNonFinishingActivity(true, true);
                if (CoreRune.MT_NEW_DEX_PIP
                        && multiTaskingController2.mTmpPipCandidate == null
                        && topNonFinishingActivity != null) {
                    boolean z = topNonFinishingActivity.supportsEnterPipOnTaskSwitch;
                    topNonFinishingActivity.supportsEnterPipOnTaskSwitch = true;
                    DisplayContent displayContent = task.getDisplayContent();
                    if (displayContent != null
                            && displayContent.isNewDexMode()
                            && displayContent.getDefaultTaskDisplayArea().mRootPinnedTask == null
                            && ((task.inFullscreenWindowingMode()
                                            || (CoreRune.MT_NEW_DEX_PIP_ON_FREEFORM
                                                    && task.inFreeformWindowingMode()))
                                    && task.isVisible()
                                    && topNonFinishingActivity.checkEnterPictureInPictureState(
                                            "new_dex", true, false)
                                    && topNonFinishingActivity.pictureInPictureArgs
                                            .isAutoEnterEnabled())) {
                        multiTaskingController2.mTmpPipCandidate = topNonFinishingActivity;
                        Slog.d(
                                "MultiTaskingController",
                                "minimizeAllTasksLocked: found pipCandidate, r="
                                        + topNonFinishingActivity);
                        break;
                    } else {
                        topNonFinishingActivity.supportsEnterPipOnTaskSwitch = z;
                    }
                }
                if (task.canMinimize()) {
                    arrayList.add(task);
                    break;
                }
                break;
        }
    }
}
