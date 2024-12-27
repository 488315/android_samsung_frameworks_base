package com.android.server.wm;

import java.util.function.BiPredicate;

public final /* synthetic */ class TaskDisplayArea$$ExternalSyntheticLambda2
        implements BiPredicate {
    @Override // java.util.function.BiPredicate
    public final boolean test(Object obj, Object obj2) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        int intValue = ((Integer) obj2).intValue();
        return activityRecord.isActivityTypeHome()
                && (intValue == -1 || activityRecord.mUserId == intValue);
    }
}
