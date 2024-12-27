package com.android.server.biometrics.sensors;

import android.os.Build;

import java.util.function.BooleanSupplier;

public final /* synthetic */ class BiometricSchedulerOperation$$ExternalSyntheticLambda1
        implements BooleanSupplier {
    @Override // java.util.function.BooleanSupplier
    public final boolean getAsBoolean() {
        return Build.isDebuggable();
    }
}
