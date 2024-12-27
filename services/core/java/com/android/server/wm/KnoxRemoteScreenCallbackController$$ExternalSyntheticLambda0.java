package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ boolean[] f$1;

    public /* synthetic */ KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda0(
            int i, boolean[] zArr) {
        this.f$0 = i;
        this.f$1 = zArr;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.f$0;
        boolean[] zArr = this.f$1;
        ActivityRecord activityRecord = (ActivityRecord) obj;
        if (activityRecord.getUid() != i || !activityRecord.isVisibleRequested()) {
            return false;
        }
        zArr[0] = true;
        return true;
    }
}
