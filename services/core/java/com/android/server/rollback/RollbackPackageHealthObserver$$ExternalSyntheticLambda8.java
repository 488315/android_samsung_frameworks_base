package com.android.server.rollback;

import android.content.rollback.PackageRollbackInfo;
import android.content.rollback.RollbackInfo;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RollbackPackageHealthObserver$$ExternalSyntheticLambda8
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((PackageRollbackInfo) ((RollbackInfo) obj).getPackages().get(0)).getPackageName();
    }
}
