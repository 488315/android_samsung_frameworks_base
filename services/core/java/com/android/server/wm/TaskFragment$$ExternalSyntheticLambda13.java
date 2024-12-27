package com.android.server.wm;

import android.graphics.Point;

import java.util.function.Consumer;

public final /* synthetic */ class TaskFragment$$ExternalSyntheticLambda13 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TaskFragment$$ExternalSyntheticLambda13(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Point minDimensions;
        switch (this.$r8$classId) {
            case 0:
                TaskFragment taskFragment = (TaskFragment) this.f$0;
                Task task = (Task) this.f$1;
                Task task2 = (Task) obj;
                taskFragment.getClass();
                if (task != task2 && task2.inFreeformWindowingMode() && task2.isVisible()) {
                    taskFragment.mTransitionController.collect(task2);
                    taskFragment.mTransitionController.setResumedAffordance(task2);
                    break;
                }
                break;
            case 1:
                TaskFragment taskFragment2 = (TaskFragment) this.f$0;
                Task task3 = (Task) this.f$1;
                Task task4 = (Task) obj;
                taskFragment2.getClass();
                if (task3 != task4 && task4.inFreeformWindowingMode() && task4.isVisible()) {
                    taskFragment2.mTransitionController.collect(task4);
                    taskFragment2.mTransitionController.setResumedAffordance(task4);
                    break;
                }
                break;
            default:
                int[] iArr = (int[]) this.f$0;
                int[] iArr2 = (int[]) this.f$1;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (!activityRecord.finishing
                        && (minDimensions = activityRecord.getMinDimensions()) != null) {
                    iArr[0] = Math.max(iArr[0], minDimensions.x);
                    iArr2[0] = Math.max(iArr2[0], minDimensions.y);
                    break;
                }
                break;
        }
    }
}
