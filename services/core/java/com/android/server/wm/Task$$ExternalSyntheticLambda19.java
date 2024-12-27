package com.android.server.wm;

import android.content.ComponentName;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class Task$$ExternalSyntheticLambda19 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Task$$ExternalSyntheticLambda19(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ActivityRecord[] activityRecordArr = (ActivityRecord[]) obj2;
                ActivityRecord topNonFinishingActivity =
                        ((TaskFragment) obj).getTopNonFinishingActivity(true, true);
                if (topNonFinishingActivity == null
                        || !topNonFinishingActivity.isState(
                                ActivityRecord.State.RESUMED, ActivityRecord.State.PAUSING)
                        || !topNonFinishingActivity.supportsPictureInPicture()) {
                    return false;
                }
                activityRecordArr[0] = topNonFinishingActivity;
                return true;
            case 1:
                ActivityRecord activityRecord = (ActivityRecord) obj2;
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                if (activityRecord2 == activityRecord) {
                    return true;
                }
                if (activityRecord2.occludesParent(false)) {
                    TaskFragment taskFragment = activityRecord2.getTaskFragment();
                    if (taskFragment == activityRecord.getTaskFragment()) {
                        return true;
                    }
                    if (taskFragment != null && taskFragment.asTask() != null) {
                        return true;
                    }
                    TaskFragment asTaskFragment = taskFragment.getParent().asTaskFragment();
                    while (true) {
                        TaskFragment taskFragment2 = taskFragment;
                        taskFragment = asTaskFragment;
                        if (taskFragment != null
                                && taskFragment2.getBounds().equals(taskFragment.getBounds())) {
                            if (taskFragment.asTask() != null) {
                                return true;
                            }
                            asTaskFragment = taskFragment.getParent().asTaskFragment();
                        }
                    }
                }
                return false;
            case 2:
                ComponentName componentName = (ComponentName) obj2;
                ActivityRecord activityRecord3 = (ActivityRecord) obj;
                return activityRecord3.info.packageName.equals(componentName.getPackageName())
                        && activityRecord3.info.name.equals(componentName.getClassName());
            default:
                Task task = (Task) obj2;
                task.getClass();
                return ((ActivityRecord) obj)
                        .packageName.equals(task.realActivity.getPackageName());
        }
    }
}
