package com.android.server.biometrics;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricService$Injector$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ AtomicLong f$0;

    @Override // java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(this.f$0.incrementAndGet());
    }
}
