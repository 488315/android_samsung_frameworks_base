package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class Transition$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ Transition$$ExternalSyntheticLambda2(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        switch (this.$r8$classId) {
            case 0:
                return activityRecord.mWaitForEnteringPinnedMode;
            case 1:
                return activityRecord.findMainWindow(true) != null;
            default:
                return activityRecord.occludesParent(true)
                        && activityRecord.findMainWindow(true) != null;
        }
    }
}
