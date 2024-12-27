package com.android.server.wm;

import java.util.function.Predicate;

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
