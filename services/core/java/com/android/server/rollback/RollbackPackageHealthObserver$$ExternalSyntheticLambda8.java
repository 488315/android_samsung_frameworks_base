package com.android.server.rollback;

import android.content.rollback.PackageRollbackInfo;
import android.content.rollback.RollbackInfo;

import java.util.function.Function;

public final /* synthetic */ class RollbackPackageHealthObserver$$ExternalSyntheticLambda8
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((PackageRollbackInfo) ((RollbackInfo) obj).getPackages().get(0)).getPackageName();
    }
}
