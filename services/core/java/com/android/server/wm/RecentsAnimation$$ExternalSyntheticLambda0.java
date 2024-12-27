package com.android.server.wm;

import java.util.function.BiPredicate;

public final /* synthetic */ class RecentsAnimation$$ExternalSyntheticLambda0
        implements BiPredicate {
    @Override // java.util.function.BiPredicate
    public final boolean test(Object obj, Object obj2) {
        RecentsAnimation recentsAnimation = (RecentsAnimation) obj;
        Task task = (Task) obj2;
        recentsAnimation.getClass();
        return task.getNonFinishingActivityCount() > 0
                && task.mUserId == recentsAnimation.mUserId
                && task.getBaseIntent()
                        .getComponent()
                        .equals(recentsAnimation.mTargetIntent.getComponent());
    }
}
