package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;

import java.util.function.Predicate;

public final /* synthetic */ class TaskSnapshotController$$ExternalSyntheticLambda1
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        if (!activityRecord.mLastSurfaceShowing
                || activityRecord.findMainWindow(true) == null
                || activityRecord.mPopOverState.mIsActivated) {
            return false;
        }
        return activityRecord.forAllWindows(
                (ToBooleanFunction) new ActivityRecord$$ExternalSyntheticLambda18(0), true);
    }
}
