package com.android.server.pm;

import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda71 implements Supplier {
    public final /* synthetic */ Settings f$0;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda71(Settings settings) {
        this.f$0 = settings;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f$0.getPackagesLocked();
    }
}
