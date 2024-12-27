package com.android.server.pm;

import android.content.pm.UserPackage;

import java.util.function.Predicate;

public final /* synthetic */
class PackageManagerService$PackageManagerInternalImpl$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return !"android".equals(((UserPackage) obj).packageName);
    }
}
