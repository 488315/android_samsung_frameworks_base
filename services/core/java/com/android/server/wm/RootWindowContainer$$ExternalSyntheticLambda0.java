package com.android.server.wm;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ WindowContainer f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda0(
            ArrayList arrayList, Task task, ArrayList arrayList2) {
        this.f$0 = arrayList;
        this.f$1 = task;
        this.f$2 = arrayList2;
    }

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda0(
            boolean[] zArr, boolean[] zArr2, ActivityRecord activityRecord) {
        this.f$0 = zArr;
        this.f$2 = zArr2;
        this.f$1 = activityRecord;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ActivityRecord topNonFinishingActivity;
        ActivityRecord topNonFinishingActivity2;
        switch (this.$r8$classId) {
            case 0:
                ArrayList arrayList = (ArrayList) this.f$0;
                Task task = (Task) this.f$1;
                ArrayList arrayList2 = (ArrayList) this.f$2;
                Task task2 = (Task) obj;
                if (task2.shouldBeVisible(null)
                        && (topNonFinishingActivity = task2.getTopNonFinishingActivity(true, true))
                                != null) {
                    arrayList.clear();
                    arrayList.add(new ActivityAssistInfo(topNonFinishingActivity));
                    Task adjacentTask = topNonFinishingActivity.task.getAdjacentTask();
                    if (adjacentTask != null
                            && (topNonFinishingActivity2 =
                                            adjacentTask.getTopNonFinishingActivity(true, true))
                                    != null) {
                        arrayList.add(new ActivityAssistInfo(topNonFinishingActivity2));
                    }
                    if (task2 != task) {
                        arrayList2.addAll(arrayList);
                        break;
                    } else {
                        arrayList2.addAll(0, arrayList);
                        break;
                    }
                }
                break;
            default:
                boolean[] zArr = (boolean[]) this.f$0;
                boolean[] zArr2 = (boolean[]) this.f$2;
                ActivityRecord activityRecord = (ActivityRecord) this.f$1;
                ActivityRecord focusedActivity = ((TaskDisplayArea) obj).getFocusedActivity();
                WindowProcessController windowProcessController =
                        focusedActivity == null ? null : focusedActivity.app;
                zArr[0] = zArr[0] & (windowProcessController == null);
                if (windowProcessController != null) {
                    zArr2[0] = (!windowProcessController.equals(activityRecord.app)) & zArr2[0];
                    break;
                }
                break;
        }
    }
}
