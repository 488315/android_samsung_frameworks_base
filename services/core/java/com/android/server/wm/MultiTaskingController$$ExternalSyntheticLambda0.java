package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class MultiTaskingController$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MultiTaskingController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Task task = (Task) obj;
                return (!task.inFullscreenWindowingMode()
                                || !task.isVisible()
                                || task.mCreatedByOrganizer
                                || task.getRootActivity(true, false) == null
                                || task.getRootActivity(true, false).isResolverOrDelegateActivity())
                        ? false
                        : true;
            case 1:
                Task task2 = (Task) obj;
                return task2.isVisible() && task2.isLeafTask();
            case 2:
                return ((WindowState) obj).mAttrs.type == 2274;
            default:
                Task task3 = (Task) obj;
                return (task3.inFullscreenWindowingMode() || task3.inSplitScreenWindowingMode())
                        && task3.topRunningActivity(false) != null;
        }
    }
}
