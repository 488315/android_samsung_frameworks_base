package com.android.server.wm;

import android.util.Slog;

import java.io.PrintWriter;
import java.util.function.Consumer;

public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda29
        implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda29(
            Task[] taskArr,
            Task task,
            WindowProcessController windowProcessController,
            String str) {
        this.f$0 = taskArr;
        this.f$1 = task;
        this.f$2 = windowProcessController;
        this.f$3 = str;
    }

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda29(
            boolean[] zArr, PrintWriter printWriter, String str, boolean[] zArr2) {
        this.f$0 = zArr;
        this.f$1 = printWriter;
        this.f$3 = str;
        this.f$2 = zArr2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Task[] taskArr = (Task[]) this.f$0;
                Task task = (Task) this.f$1;
                WindowProcessController windowProcessController =
                        (WindowProcessController) this.f$2;
                String str = this.f$3;
                Task task2 = (Task) obj;
                boolean z =
                        taskArr[0] == null
                                && (task == task2.getRootTask() || task2.isVisibleRequested());
                ActivityRecord activityRecord = task2.topRunningActivity(false);
                Task task3 = null;
                if (activityRecord != null && activityRecord.app == windowProcessController) {
                    if (activityRecord.isActivityTypeHome()
                            && task2.mAtmService.mHomeProcess == windowProcessController) {
                        Slog.w(
                                "ActivityTaskManager",
                                "  Not force finishing home activity "
                                        + activityRecord
                                                .intent
                                                .getComponent()
                                                .flattenToShortString());
                    } else {
                        Slog.w(
                                "ActivityTaskManager",
                                "  Force finishing activity "
                                        + activityRecord
                                                .intent
                                                .getComponent()
                                                .flattenToShortString());
                        Task task4 = activityRecord.task;
                        DisplayContent displayContent = task2.mDisplayContent;
                        displayContent.prepareAppTransition(2, 16);
                        displayContent.mTransitionController.requestTransitionIfNeeded(
                                2, 16, null, displayContent);
                        activityRecord.finishIfPossible(str, false);
                        ActivityRecord activityBelow = task2.getActivityBelow(activityRecord);
                        if (activityBelow != null
                                && activityBelow.isState(
                                        ActivityRecord.State.STARTED,
                                        ActivityRecord.State.RESUMED,
                                        ActivityRecord.State.PAUSING,
                                        ActivityRecord.State.PAUSED)
                                && (!activityBelow.isActivityTypeHome()
                                        || task2.mAtmService.mHomeProcess != activityBelow.app)) {
                            Slog.w(
                                    "ActivityTaskManager",
                                    "  Force finishing activity "
                                            + activityBelow
                                                    .intent
                                                    .getComponent()
                                                    .flattenToShortString());
                            activityBelow.finishIfPossible(str, false);
                        }
                        task3 = task4;
                    }
                }
                if (z) {
                    taskArr[0] = task3;
                    break;
                }
                break;
            default:
                boolean[] zArr = (boolean[]) this.f$0;
                PrintWriter printWriter = (PrintWriter) this.f$1;
                zArr[0] =
                        ActivityTaskSupervisor.printThisActivity(
                                        printWriter,
                                        ((TaskDisplayArea) obj).getFocusedActivity(),
                                        this.f$3,
                                        -1,
                                        ((boolean[]) this.f$2)[0],
                                        "    Resumed: ",
                                        new RootWindowContainer$$ExternalSyntheticLambda32(
                                                2, printWriter))
                                | zArr[0];
                break;
        }
    }
}
