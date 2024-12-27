package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class PinnedTaskController$$ExternalSyntheticLambda1
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        return activityRecord.providesOrientation() && !activityRecord.task.inMultiWindowMode();
    }
}
