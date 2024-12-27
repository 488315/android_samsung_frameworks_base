package com.android.server.wm;

import java.util.function.BiPredicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RecentsAnimation$$ExternalSyntheticLambda0 implements BiPredicate {
    @Override // java.util.function.BiPredicate
    public final boolean test(Object obj, Object obj2) {
        RecentsAnimation recentsAnimation = (RecentsAnimation) obj;
        Task task = (Task) obj2;
        recentsAnimation.getClass();
        return task.getNonFinishingActivityCount() > 0 && task.mUserId == recentsAnimation.mUserId && task.getBaseIntent().getComponent().equals(recentsAnimation.mTargetIntent.getComponent());
    }
}
