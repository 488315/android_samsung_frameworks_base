package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class ActivityMetricsLogger$$ExternalSyntheticLambda8
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        return (!activityRecord.isVisibleRequested()
                        || activityRecord.mReportedDrawn
                        || activityRecord.finishing)
                ? false
                : true;
    }
}
