package com.android.server.wm;

import java.util.function.Predicate;

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
