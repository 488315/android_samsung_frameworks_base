package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityRecord$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityRecord f$0;

    public /* synthetic */ ActivityRecord$$ExternalSyntheticLambda1(
            int i, ActivityRecord activityRecord) {
        this.$r8$classId = i;
        this.f$0 = activityRecord;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        ActivityRecord activityRecord = this.f$0;
        switch (i) {
            case 0:
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                activityRecord.getClass();
                if (activityRecord2 == activityRecord
                        || activityRecord2.mStartingData == null
                        || !activityRecord2.showToCurrentUser()) {}
                break;
            case 1:
                ActivityRecord activityRecord3 = (ActivityRecord) obj;
                activityRecord.getClass();
                if (activityRecord3 != activityRecord) {
                    StartingData startingData = activityRecord3.mStartingData;
                    if (startingData != null
                            && startingData.mAssociatedTask == null
                            && activityRecord.mTransitionController.isCollecting(activityRecord3)
                            && (startingData instanceof SnapshotStartingData)
                            && !activityRecord3.getBounds().equals(activityRecord.getBounds())) {
                        if (activityRecord.mTransitionController.inPlayingTransition(
                                activityRecord3)) {
                            activityRecord.mTransitionController.setNoAnimation(activityRecord);
                            activityRecord.mTransitionController.setNoAnimation(activityRecord3);
                        }
                        activityRecord3.removeStartingWindow();
                        break;
                    } else if (activityRecord3.isVisibleRequested()
                            || !activityRecord.transferStartingWindow(activityRecord3)) {
                    }
                }
                break;
            case 2:
                ActivityRecord activityRecord4 = (ActivityRecord) obj;
                activityRecord.getClass();
                if (activityRecord4.finishing || activityRecord4 == activityRecord) {}
                break;
            case 3:
                ActivityRecord activityRecord5 = (ActivityRecord) obj;
                activityRecord.getClass();
                if (activityRecord5.mLaunchCookie != null
                        || activityRecord5.finishing
                        || !activityRecord5.isUid(activityRecord.getUid())) {}
                break;
            case 4:
                ActivityRecord activityRecord6 = (ActivityRecord) obj;
                activityRecord.getClass();
                if (activityRecord6.finishing
                        || activityRecord6.getUid() == activityRecord.getUid()) {}
                break;
            default:
                activityRecord.getClass();
                if (((TaskDisplayArea) obj).getFocusedActivity() == activityRecord) {}
                break;
        }
        return true;
    }
}
