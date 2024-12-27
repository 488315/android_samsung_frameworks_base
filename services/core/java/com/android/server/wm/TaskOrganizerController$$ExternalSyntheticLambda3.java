package com.android.server.wm;

import android.view.SurfaceControl;
import android.window.TaskAppearedInfo;

import com.android.internal.util.ArrayUtils;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TaskOrganizerController$$ExternalSyntheticLambda3
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ ArrayList f$1;

    public /* synthetic */ TaskOrganizerController$$ExternalSyntheticLambda3(
            ArrayList arrayList, int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = arrayList;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                TaskOrganizerController.TaskOrganizerState taskOrganizerState =
                        (TaskOrganizerController.TaskOrganizerState) this.f$0;
                ArrayList arrayList = this.f$1;
                Task task = (Task) obj;
                boolean z = !task.mCreatedByOrganizer;
                task.updateTaskOrganizerState(z);
                if (task.isOrganized() && z) {
                    taskOrganizerState.getClass();
                    task.mTaskAppearedSent = true;
                    if (!taskOrganizerState.mOrganizedTasks.contains(task)) {
                        taskOrganizerState.mOrganizedTasks.add(task);
                    }
                    taskOrganizerState.mOrganizer.getClass();
                    arrayList.add(
                            new TaskAppearedInfo(
                                    task.getTaskInfo(),
                                    new SurfaceControl(
                                            task.mSurfaceControl,
                                            "TaskOrganizerController.registerTaskOrganizer")));
                    break;
                }
                break;
            default:
                int[] iArr = (int[]) this.f$0;
                ArrayList arrayList2 = this.f$1;
                Task task2 = (Task) obj;
                if (iArr == null || ArrayUtils.contains(iArr, task2.getActivityType())) {
                    arrayList2.add(task2.getTaskInfo());
                    break;
                }
                break;
        }
    }
}
