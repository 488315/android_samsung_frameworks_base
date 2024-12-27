package com.android.server.rollback;

import android.content.rollback.RollbackInfo;

import java.util.function.ToIntFunction;

public final /* synthetic */ class RollbackPackageHealthObserver$$ExternalSyntheticLambda3
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        return ((RollbackInfo) obj).getRollbackImpactLevel();
    }
}
