package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class DimAnimator$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        return activityRecord.mVisible
                && (!activityRecord.occludesParent(true) || activityRecord.showWallpaper());
    }
}
