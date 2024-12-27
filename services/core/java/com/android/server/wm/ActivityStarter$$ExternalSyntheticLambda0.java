package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class ActivityStarter$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ ActivityStarter$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Task task = (Task) obj;
                return task.inFullscreenWindowingMode() && task.hasChild();
            default:
                return ((ActivityRecord) obj).canBeTopRunning();
        }
    }
}
