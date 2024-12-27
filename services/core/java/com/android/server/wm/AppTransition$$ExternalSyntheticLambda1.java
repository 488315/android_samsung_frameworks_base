package com.android.server.wm;

import java.util.function.BiPredicate;

public final /* synthetic */ class AppTransition$$ExternalSyntheticLambda1 implements BiPredicate {
    @Override // java.util.function.BiPredicate
    public final boolean test(Object obj, Object obj2) {
        return ((Task) obj).mTaskId == ((Integer) obj2).intValue();
    }
}
