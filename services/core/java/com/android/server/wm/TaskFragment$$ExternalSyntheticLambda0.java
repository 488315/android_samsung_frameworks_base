package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskFragment$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ TaskFragment$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityRecord activityRecord = (ActivityRecord) obj;
        switch (this.$r8$classId) {
            case 0:
                return activityRecord.inTransitionSelfOrParent() && activityRecord.allowEdgeExtension();
            case 1:
                return !activityRecord.finishing;
            case 2:
                return (activityRecord.finishing || activityRecord.mLaunchedFromBubble) ? false : true;
            case 3:
                return (activityRecord.finishing || activityRecord.mTaskOverlay) ? false : true;
            case 4:
                return (activityRecord.finishing || activityRecord.mTaskOverlay || activityRecord.mLaunchedFromBubble) ? false : true;
            case 5:
                return !activityRecord.finishing && activityRecord.hasChild();
            default:
                return activityRecord.canBeTopRunning() && activityRecord.isFocusable();
        }
    }
}
