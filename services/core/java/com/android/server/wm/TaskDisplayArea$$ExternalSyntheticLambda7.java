package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TaskDisplayArea$$ExternalSyntheticLambda7 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ TaskDisplayArea$$ExternalSyntheticLambda7(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Task task = (Task) obj;
        switch (this.$r8$classId) {
            case 0:
                return task.inFullscreenWindowingMode();
            case 1:
                return task.shouldBeVisible(null) && (task.getWindowingMode() == 1);
            default:
                return task.isFullscreenRootForStageTask();
        }
    }
}
