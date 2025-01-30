package com.android.server.pm;

import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class AsecInstallHelper$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ PackageManagerService f$0;

    public /* synthetic */ AsecInstallHelper$$ExternalSyntheticLambda0(PackageManagerService packageManagerService) {
        this.f$0 = packageManagerService;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f$0.snapshotComputer();
    }
}
