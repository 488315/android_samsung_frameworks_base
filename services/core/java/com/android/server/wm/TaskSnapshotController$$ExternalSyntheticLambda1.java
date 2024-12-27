package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
