package com.android.server.biometrics;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public final /* synthetic */ class BiometricService$Injector$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ AtomicLong f$0;

    @Override // java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(this.f$0.incrementAndGet());
    }
}
