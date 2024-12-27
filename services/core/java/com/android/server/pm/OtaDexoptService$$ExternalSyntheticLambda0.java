package com.android.server.pm;

import com.android.server.pm.pkg.PackageStateInternal;

import java.util.function.Predicate;

public final /* synthetic */ class OtaDexoptService$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return "android".equals(((PackageStateInternal) obj).getPkg().getPackageName());
    }
}
