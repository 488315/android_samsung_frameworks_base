package com.android.server.biometrics.sensors.fingerprint;

import com.android.server.biometrics.SemBiometricDisplayStateMonitor;

import java.util.function.Supplier;

public final /* synthetic */ class SemUdfpsHelper$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return SemBiometricDisplayStateMonitor.InstanceHolder.INSTANCE;
            default:
                return SemUdfpsTspManager.get();
        }
    }
}
