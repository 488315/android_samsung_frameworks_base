package com.android.server.wm;

import java.util.function.BiPredicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
