package com.android.server.wm;

import android.graphics.Rect;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PopOverState$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PopOverState$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                PopOverState popOverState = (PopOverState) obj2;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                popOverState.getClass();
                PopOverState popOverState2 = activityRecord.mPopOverState;
                if (!popOverState2.mIsActivated
                        || activityRecord.finishing
                        || popOverState2.mOptions != popOverState.mOptions
                        || !popOverState2.mLastOccludesParent) {}
                break;
            default:
                Rect rect = (Rect) obj2;
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                if (!activityRecord2.mPopOverState.mIsActivated
                        || activityRecord2.finishing
                        || !activityRecord2.getBounds().contains(rect)
                        || !activityRecord2.mPopOverState.mLastOccludesParent) {}
                break;
        }
        return false;
    }
}
