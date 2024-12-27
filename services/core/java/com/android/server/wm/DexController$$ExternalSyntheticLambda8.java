package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class DexController$$ExternalSyntheticLambda8 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !((ActivityRecord) obj).mAppStopped;
    }
}
