package com.android.server.pm;

import com.android.server.LocalServices;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda42
        implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }
}
