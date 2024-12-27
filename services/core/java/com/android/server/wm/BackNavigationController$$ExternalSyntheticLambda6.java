package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BackNavigationController$$ExternalSyntheticLambda6
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ BackNavigationController$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        ActivityRecord topNonFinishingActivity;
        switch (this.$r8$classId) {
            case 0:
                Task task = (Task) obj;
                return task.showToCurrentUser()
                        && !task.mChildren.isEmpty()
                        && (topNonFinishingActivity = task.getTopNonFinishingActivity(true, true))
                                != null
                        && topNonFinishingActivity.showToCurrentUser();
            case 1:
                return !((ActivityRecord) obj).finishing;
            case 2:
                return !((ActivityRecord) obj).finishing;
            case 3:
                return !((ActivityRecord) obj).finishing;
            case 4:
                return !((ActivityRecord) obj).finishing;
            default:
                return ((WindowState) obj).isFocused();
        }
    }
}
