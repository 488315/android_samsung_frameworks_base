package com.android.server.wm;

import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityClientController$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityRecord f$0;

    public /* synthetic */ ActivityClientController$$ExternalSyntheticLambda0(
            int i, ActivityRecord activityRecord) {
        this.$r8$classId = i;
        this.f$0 = activityRecord;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        ActivityRecord activityRecord = this.f$0;
        ActivityRecord activityRecord2 = (ActivityRecord) obj;
        switch (i) {
            case 0:
                return !activityRecord2.finishing || activityRecord2 == activityRecord;
            default:
                activityRecord.getClass();
                if (!Objects.equals(activityRecord2.taskAffinity, activityRecord.taskAffinity)) {
                    return true;
                }
                activityRecord2.finishIfPossible("request-affinity", true);
                return false;
        }
    }
}
