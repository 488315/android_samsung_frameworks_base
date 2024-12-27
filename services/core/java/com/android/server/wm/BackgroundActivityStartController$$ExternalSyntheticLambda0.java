package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class BackgroundActivityStartController$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BackgroundActivityStartController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        switch (this.$r8$classId) {
            case 0:
                if (activityRecord.finishing || activityRecord.isAlwaysOnTop()) {}
                break;
            default:
                if (activityRecord.finishing || activityRecord.isAlwaysOnTop()) {}
                break;
        }
        return false;
    }
}
