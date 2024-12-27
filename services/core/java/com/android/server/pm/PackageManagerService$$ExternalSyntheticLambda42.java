package com.android.server.pm;

import com.android.server.LocalServices;

import java.util.function.Supplier;

public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda42
        implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }
}
