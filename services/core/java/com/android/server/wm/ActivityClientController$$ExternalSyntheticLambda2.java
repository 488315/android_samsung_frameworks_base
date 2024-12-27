package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class ActivityClientController$$ExternalSyntheticLambda2
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((ActivityRecord) obj).finishing;
    }
}
