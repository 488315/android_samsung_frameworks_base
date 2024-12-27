package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class WindowContainer$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WindowContainer$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        switch (this.$r8$classId) {
            case 0:
                return !activityRecord.mTaskOverlay;
            case 1:
                return !activityRecord.finishing;
            case 2:
                return (activityRecord.finishing || activityRecord.mTaskOverlay) ? false : true;
            default:
                return activityRecord.isAnimating(3);
        }
    }
}
